package com.ag.generic.model;

import javax.naming.ldap.LdapContext;

public class LDAPModel {
	
	private String code;
	private String message;
	private LdapContext conldap;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public LdapContext getConldap() {
		return conldap;
	}
	public void setConldap(LdapContext conldap) {
		this.conldap = conldap;
	}

}
