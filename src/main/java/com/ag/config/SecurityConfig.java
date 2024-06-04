package com.ag.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
				.authorizeRequests().antMatchers("/login").permitAll().
				antMatchers("/process").permitAll().
				antMatchers("/FileAuth").permitAll().
				antMatchers("/BaflFileAuth").permitAll().
				antMatchers("/BaflFetchLOVS").permitAll().
				antMatchers("/FetchLOVS").permitAll().
				antMatchers("/FileDown").permitAll().
				antMatchers("/BaflFileDown").permitAll().
				antMatchers("/servicetxn").permitAll().
				antMatchers("/baflservicetxn").permitAll().
				antMatchers("/servicecomplaintclosure").permitAll().
				antMatchers("/baflservicecomplaintclosure").permitAll().
				antMatchers("/servicefileack").permitAll().
				antMatchers("/baflservicefileack").permitAll().
				antMatchers("/serviceprofileack").permitAll().
				antMatchers("/baflserviceprofileack").permitAll().
				antMatchers("/serviceprofile").permitAll().
				antMatchers("/baflserviceprofile").permitAll().
				antMatchers("/heartbeat").permitAll().
				antMatchers("/baflheartbeat").permitAll().
				antMatchers("/serviceuserreg").permitAll().
				antMatchers("/baflserviceuserreg").permitAll().
				antMatchers("/servicesettlement").permitAll().
				antMatchers("/baflservicesettlement").permitAll().
				antMatchers("/process/attachments").permitAll().
				antMatchers("/process/nrt").permitAll().
				antMatchers("/process/images").permitAll().
				antMatchers("/retrieveDiscountRate").permitAll().
				antMatchers("/availdDiscountAck").permitAll().
				antMatchers("/qrPaymentAck").permitAll().
				antMatchers("/process/RocDown").permitAll().
				antMatchers("/ecrEnquiry").permitAll().
				antMatchers("/ecrPayment").permitAll().
				antMatchers("/generateToken").permitAll().
				antMatchers("/ecrGeneratePayment").permitAll().
				antMatchers("/ecrEnquiryAll").permitAll().
				antMatchers("/retrieveProviders").permitAll().
				antMatchers("/api/decode").permitAll().
				antMatchers("/processRequest").permitAll().
				antMatchers("/validateCompletion").permitAll().
				antMatchers("/posApiConfiguration").permitAll().
				antMatchers("/retrieveAdvertisment").permitAll().
				antMatchers("/imgDownload").permitAll().
				antMatchers("/fetchApps").permitAll().
				antMatchers("/FileAuthUpd").permitAll().
				antMatchers("/pdfDownload").permitAll().
				antMatchers("/reportDownload").permitAll().
				antMatchers("/ecrEnquiryAlfa").permitAll().
				antMatchers("/ecrPaymentAlfa").permitAll().
				antMatchers("/ecrGetPaymentStatus").permitAll().
				antMatchers("/LoyaltyConfig").permitAll().
				antMatchers("/LoyaltyLaunchComplaint").permitAll().
				antMatchers("/LoyaltyUpdateKyc").permitAll().
				antMatchers("/LoyaltyGenerateOtp").permitAll().
				antMatchers("/LoyaltyValidateOtp").permitAll().
				antMatchers("/LoyaltyViewComplaints").permitAll().
				antMatchers("/LoyaltyViewCustomerKyc").permitAll().
				antMatchers("/LoyaltyViewNotification").permitAll().
				antMatchers("/serviceOfflinetxn").permitAll().
				antMatchers("/baflserviceOfflinetxn").permitAll().
				antMatchers("/generateEmvQrAG").permitAll().
				antMatchers("/FuelConfig").permitAll().
				antMatchers("/FuelViewNotification").permitAll().
				antMatchers("/FileAuthApk").permitAll().
				antMatchers("/BaflFileAuthApk").permitAll().
				antMatchers("/FuelFileAuthApk").permitAll().
				antMatchers("/FuelBaflFileAuthApk").permitAll().
				antMatchers("/FuelCP").permitAll().
				antMatchers("/FuelFileDown").permitAll().
				antMatchers("/FuelBaflFileDown").permitAll().
				antMatchers("/FuelQrPaymentAck").permitAll().
				antMatchers("/digitalRock").permitAll().
				antMatchers("/mqttHeartbeat").permitAll().
				antMatchers("/LoyaltyQrGen").permitAll().
				
				
				anyRequest().authenticated().and().
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		//http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
