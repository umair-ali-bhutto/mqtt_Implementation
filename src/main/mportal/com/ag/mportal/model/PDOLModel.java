package com.ag.mportal.model;

public class PDOLModel {

    private String terminalTransactionQualifiers;
    private String amountAuthorised;
    private String amountOther;
    private String terminalCountryCode;
    private String terminalVerficationResult;
    private String TransactionCurrencyCode;
    private String transactionDate;
    private String transactionType;
    private String unpredictibleNumber;
    private byte[] pdolBytes;

    public byte[] getPdolBytes() {
        return pdolBytes;
    }

    public void setPdolBytes(byte[] pdolBytes) {
        this.pdolBytes = pdolBytes;
    }



    public String getTerminalTransactionQualifiers() {
        return terminalTransactionQualifiers;
    }

    public void setTerminalTransactionQualifiers(String terminalTransactionQualifiers) {
        this.terminalTransactionQualifiers = terminalTransactionQualifiers;
    }

    public String getAmountAuthorised() {
        return amountAuthorised;
    }

    public void setAmountAuthorised(String amountAuthorised) {
        this.amountAuthorised = amountAuthorised;
    }

    public String getAmountOther() {
        return amountOther;
    }

    public void setAmountOther(String amountOther) {
        this.amountOther = amountOther;
    }

    public String getTerminalCountryCode() {
        return terminalCountryCode;
    }

    public void setTerminalCountryCode(String terminalCountryCode) {
        this.terminalCountryCode = terminalCountryCode;
    }

    public String getTerminalVerficationResult() {
        return terminalVerficationResult;
    }

    public void setTerminalVerficationResult(String terminalVerficationResult) {
        this.terminalVerficationResult = terminalVerficationResult;
    }

    public String getTransactionCurrencyCode() {
        return TransactionCurrencyCode;
    }

    public void setTransactionCurrencyCode(String transactionCurrencyCode) {
        TransactionCurrencyCode = transactionCurrencyCode;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getUnpredictibleNumber() {
        return unpredictibleNumber;
    }

    public void setUnpredictibleNumber(String unpredictibleNumber) {
        this.unpredictibleNumber = unpredictibleNumber;
    }
}
