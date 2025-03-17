# CREATE PASSWORD HASH

```bash
echo -n "ag-test" | openssl dgst -sha256
```

# Test Through Mosquitto Client On Linux

If you want to install Mosquitto globally on Ubuntu so that it's available for all users, you can follow these steps:

1. **Install Mosquitto:**
   Open a terminal and run the following commands to update the package list and install Mosquitto:

   ```bash
   sudo apt-get update
   sudo apt-get install mosquitto
   ```

   This will install the Mosquitto broker.

2. **Start Mosquitto:**
   After installation, Mosquitto should start automatically. If it doesn't, you can start it manually using:

   ```bash
   sudo systemctl start mosquitto
   ```

   You can also enable Mosquitto to start on boot with:

   ```bash
   sudo systemctl enable mosquitto
   ```

3. **Verify Installation:**
   You can verify that Mosquitto is running by checking its status:

   ```bash
   sudo systemctl status mosquitto
   ```

   You should see a status message indicating that Mosquitto is active.

4. **Install Mosquitto Clients (Optional):**
   If you also want to install the Mosquitto clients (like `mosquitto_pub` and `mosquitto_sub`), you can install them with:

   ```bash
   sudo apt-get install mosquitto-clients
   ```

   This step is optional since the clients are included with the Mosquitto broker installation.

Now, Mosquitto should be installed globally on your system and accessible to all users. Users can use the `mosquitto_pub` and `mosquitto_sub` commands to publish and subscribe to MQTT messages.

If you encounter any issues, ensure that the Mosquitto service is running (`sudo systemctl status mosquitto`) and check for any error messages in the logs (`sudo journalctl -xe`).

```bash
mosquitto_sub -h 172.191.1.223 -p 1884 -u ag-test -P ag-test -t test

mosquitto_pub -h 172.191.1.223 -p 1884 -u ag-test -P ag-test -t test -m "{\"request_id\":\"723014563899\",\"biz_type\":\"1\",\"broadcast_type\":\"1\",\"money\":\"60.23\"}"
```

# SSL Configuration
To accept SSL connection in Moquette broker, first you have to set up the key store in the broker. Then you need to export a certificate from the broker’s keystore and improt in the client’s one. The last thing is to write a simple client that connect to the server using the SSL.

So how do you get create a keystore? We suppose to use the crypto tools provided by the JDK.

First create server’s key store, answering the questions presented to you. The first password the keytool asks to you is the keystore’s one. Then after questions (you could skip almost all but your first name and the confirmation of data (type in yes). Then you need to fill the password for alias you are creating(testserver in our case).

```bash
keytool -keystore serverkeystore.jks -alias testserver -genkey -keyalg RSA
```

Now go to your broker config file (/config/moquette.conf) and provide the path to you just created keystore (jks_path) and the passwords you have just filled (key_store_password is the password of your keystore and key_manager_password is the one of the alias)

Export the server certificate
The next step is to export the certificate, so you need to:

```bash
keytool -export -alias testserver -keystore serverkeystore.jks -file testserver.crt
```

This command generate the certificate file that you need to import into your client’s keystore.

Import the certificate into the client’s keystore
In this step you need to import the server’s certificate into the client’s keystore.

To create the client key store, if not yet done issue this:

```bash
keytool -keystore clientkeystore.jks -genkey -keyalg RSA
```

Once created the key store, import the certificate with:

```bash
keytool -keystore clientkeystore.jks -import -alias testserver -file testserver.crt -trustcacerts
```

It will ask you if the certificate is trusted, answer yes, because this certificate is not produced by a certifcation authority (you’ve created it ;-))

The steps you followed generally work for setting up a secure connection between a client and a broker, but if you’re still encountering the `No subject alternative names present` error, it’s likely due to the fact that the certificate created this way does not include a Subject Alternative Name (SAN) for the broker’s IP address or hostname.

Here’s how you can modify the process to ensure the SAN is included, resolving the issue:

### Revised Steps to Include SAN in the Certificate

1. **Create a configuration file for OpenSSL** (e.g., `server_san.cnf`) with SAN information:

```plaintext
   [ req ]
   distinguished_name = req_distinguished_name
   req_extensions = v3_req
   prompt = no

   [ req_distinguished_name ]
   CN = testserver

   [ v3_req ]
   keyUsage = keyEncipherment, dataEncipherment
   extendedKeyUsage = serverAuth
   subjectAltName = @alt_names

   [ alt_names ]
   DNS.1 = testserver
   IP.1 = 172.191.1.223  # Replace with your broker's IP
   ```

2. **Generate the server key and certificate with SAN**:

```bash
   keytool -genkeypair -alias testserver -keyalg RSA -keystore serverkeystore.jks -dname "CN=testserver" -ext "SAN=IP:172.191.1.223"
   ```

   Alternatively, if the keytool doesn’t allow SAN configuration directly, you can generate the certificate using OpenSSL and then import it into the keystore.

3. **Export the Server Certificate with SAN**:

```bash
   keytool -export -alias testserver -keystore serverkeystore.jks -file testserver.crt
   ```

4. **Import the Certificate into the Client Keystore**:

   - First, create the client keystore (if not created already):

     ```bash
     keytool -keystore clientkeystore.jks -genkey -keyalg RSA
     ```

   - Then, import the certificate:

     ```bash
     keytool -keystore clientkeystore.jks -import -alias testserver -file testserver.crt -trustcacerts
     ```

5. **Verify the SAN Information in the Certificate**:

   Use the following command to verify that the SAN has been correctly included in `testserver.crt`:

```bash
   keytool -printcert -file testserver.crt
   ```

   Look for `X509v3 Subject Alternative Name` in the output to confirm that the IP address is present.

6. **Update the Moquette Broker Configuration**:

   Make sure the broker configuration (`moquette.conf`) points to `serverkeystore.jks` and the correct password values:

```plaintext
   jks_path = path/to/serverkeystore.jks
   key_store_password = your_keystore_password
   key_manager_password = your_key_manager_password
   ssl_port = 8886
   ```

### Summary

Following these steps should ensure that the certificate contains the SAN, and the client can validate the server's IP address during the SSL handshake. If there are still issues, try verifying the connection with a tool like `openssl s_client` to see if the certificate is presented as expected.
