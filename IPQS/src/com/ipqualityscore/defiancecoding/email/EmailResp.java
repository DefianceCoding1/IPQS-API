package com.ipqualityscore.defiancecoding.email;

/**
 * Created by DefianceCoding on 11/8/2017.
 */
public class EmailResp {

    public boolean valid;
    public boolean disposable;
    public int smtp_score;
    public int overall_score;
    public String first_name;
    public boolean common;
    public boolean generic;
    public boolean dns_valid;
    public boolean honeypot;
    public String request_id;
    public boolean success;
    public String message;
    public String[] errors;
}
