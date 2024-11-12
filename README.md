# AG-MW-MQTT - MQTT Demo Project for Spring Boot 🚀

Welcome to the **AG-MW-MQTT** project! This is a powerful MQTT broker configuration setup integrated with Spring Boot. Whether you're looking to spin up your own MQTT broker or subscribe to topics with SSL encryption, this project has you covered.

![Umair Ali Bhutto](https://img.shields.io/badge/%40Author-Umair_Ali_Bhutto-green?style=plastic)

---

## Table of Contents 🗂️

- [Overview](#overview)
- [Features](#features)
- [Setup and Installation](#setup-and-installation)
- [Configuration](#configuration)
- [How to Use](#how-to-use)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## Overview 📡

This project sets up an MQTT broker using Moquette, integrated with Spring Boot. It allows you to start and stop the broker programmatically, and configure it to handle both secure and non-secure MQTT connections. This demo project is perfect for experimenting with message-driven architectures or IoT applications.

**Key Technologies:**  
🔧 **Spring Boot**  
🔑 **MQTT** (via Moquette Broker and Eclipse Paho)  
🔒 **SSL/TLS Security**  
⚙️ **Maven** for build automation

---

## Features 🌟

- **Fully Configurable MQTT Broker**: Easily configure the broker's settings, including host, port, and SSL encryption.
- **Spring Boot Integration**: Seamlessly integrate MQTT with Spring Boot to run your broker within your Spring application.
- **SSL/TLS Encryption**: Secure your MQTT communication with SSL using custom JKS files.
- **Message Subscription**: Use the Paho MQTT client to subscribe to topics and receive messages with ease.
- **Anonymous Connection Support**: Optionally allow anonymous connections.
- **Password File Support**: Secure MQTT client authentication using a password file.

---

## Setup and Installation ⚙️

### Prerequisites 📝

- JDK 8 or higher ☕
- Maven 🛠️
- A Spring Boot 2.x environment 🚀
- SSL certificates (if using SSL) 🔐

### Clone the Repository 🔄

```bash
git clone https://github.com/umair-ali-bhutto/mqtt_Implementation.git
cd mqtt_Implementation
```

### Install Dependencies 📦

Run the following Maven command to install all the required dependencies:

```bash
mvn clean install
```

### Configuration ⚙️

You can configure the MQTT broker's settings through the `application.properties` file:

```properties
mqtt.host=localhost
mqtt.port=1883
allow.anonymous=true
mqtt.password.file=/path/to/password/file
mqtt.ssl.port=8883
mqtt.jks.path=/path/to/keystore.jks
mqtt.key.store.password=your-keystore-password
mqtt.key.manager.password=your-key-manager-password
```

You can also update the properties at runtime using Java environment variables or system properties.

---

## How to Use 🛠️

### Starting the MQTT Broker 🚀

The MQTT broker will start automatically when your Spring Boot application runs, thanks to the `MqttBrokerConfig` class. This is managed by Spring Boot's `@Component` annotation, ensuring that the broker starts on application startup.

Simply run the Spring Boot application:

```bash
mvn spring-boot:run
```

---

## Testing 🧪

Run tests using the following Maven command:

```bash
mvn test
```

This will execute all the unit tests to ensure the MQTT broker and subscriber/client interactions are working as expected.

---

## Contributing 🙌

Contributions are welcome! Here's how you can get started:

1. Fork this repository 🍴
2. Clone your fork locally 📂
3. Create a new feature branch 🌿
4. Make your changes ✍️
5. Test your changes 🧑‍🔬
6. Submit a pull request! 🔄

Please ensure your changes don't break existing functionality and that all tests pass.

---

## License 📜

This project is licensed under the **Apache License 2.0** - see the [LICENSE](LICENSE) file for details.

---

## Authors 👨‍💻

- **Umair Ali Bhutto** - [![Umair Ali Bhutto](https://img.shields.io/badge/%40Github-Umair_Ali_Bhutto-green?style=plastic&logo=github&logoColor=green&logoSize=auto)](https://github.com/umair-ali-bhutto)

- **Email**: [![Umair Ali Bhutto](https://img.shields.io/badge/%40Mail-Umair_Ali_Bhutto-green?style=plastic)](mailto:umair2101f@aptechgdn.net?subject=Query%20About%20MQTT%20Project&body=Hi%20Umair,%0A%0AI%20have%20a%20question%20regarding%20the%20MQTT%20Project.)

Feel free to reach out if you have any questions or need help getting started.

---

Happy coding with MQTT! 🚀🌐
