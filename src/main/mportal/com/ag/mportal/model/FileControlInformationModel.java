package com.ag.mportal.model;

public class FileControlInformationModel {

    private String aid;
    private byte[] aidByte;
    private String applicationLabel;
    private String applicationPriorityIndicator;
    private byte[] fciBytes;
    private String languagePrefrence;
    private String issuerCodeTableIndex;
    private String applicationPreferredName;
    private String dedicatedFileName;


    public String getAid() {
        return aid;
    }
    public void setAid(String aid) {
        this.aid = aid;
    }
    public byte[] getAidByte() {
        return aidByte;
    }
    public void setAidByte(byte[] aidByte) {
        this.aidByte = aidByte;
    }
    public String getApplicationLabel() {
        return applicationLabel;
    }
    public void setApplicationLabel(String applicationLabel) {
        this.applicationLabel = applicationLabel;
    }
    public String getApplicationPriorityIndicator() {
        return applicationPriorityIndicator;
    }
    public void setApplicationPriorityIndicator(String applicationPriorityIndicator) {
        this.applicationPriorityIndicator = applicationPriorityIndicator;
    }
    public byte[] getFciBytes() {
        return fciBytes;
    }
    public void setFciBytes(byte[] fciBytes) {
        this.fciBytes = fciBytes;
    }
    public String getLanguagePrefrence() {
        return languagePrefrence;
    }
    public void setLanguagePrefrence(String languagePrefrence) {
        this.languagePrefrence = languagePrefrence;
    }
    public String getIssuerCodeTableIndex() {
        return issuerCodeTableIndex;
    }
    public void setIssuerCodeTableIndex(String issuerCodeTableIndex) {
        this.issuerCodeTableIndex = issuerCodeTableIndex;
    }
    public String getApplicationPreferredName() {
        return applicationPreferredName;
    }
    public void setApplicationPreferredName(String applicationPreferredName) {
        this.applicationPreferredName = applicationPreferredName;
    }

    public String getDedicatedFileName() {
        return dedicatedFileName;
    }

    public void setDedicatedFileName(String dedicatedFileName) {
        this.dedicatedFileName = dedicatedFileName;
    }



}
