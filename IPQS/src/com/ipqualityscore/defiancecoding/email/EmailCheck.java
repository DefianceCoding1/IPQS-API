package com.ipqualityscore.defiancecoding.email;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by DefianceCoding on 11/8/2017.
 */
public final class EmailCheck {


    private String api_key;
    private String api_url = "https://ipqualityscore.com/api/json/email/";
    private int api_timeout = 5000;
    private int api_strictness = 1;

    public EmailCheck(String key){
        this.api_key = key;
    }

    public EmailCheck(String key, int timeout){
        this.api_key = key;
        this.api_timeout = timeout;
    }

    public EmailCheck(String key, int timeout, int strictness){
        this.api_key = key;
        this.api_timeout = timeout;
        this.api_strictness = strictness;
    }


    /**
     * Here is where you can set your API Key from your user dashboard
     * @param key
     */

    public void set_api_key(String key){
        this.api_key = key;
    }

    /**
     *  Units are measured in milliseconds and is the max time the API will try to poll info.
     *
     * @param timeout
     */

    public void set_api_timeout(int timeout){
        this.api_timeout = timeout;
    }

    /**
     *
     *  Unit is an int ranging from 0 to 5, they recommend using 1
     *  higher values will result in longer times for api to respond
     *
     *
     * @param level
     */

    public void set_strictness_level(int level){
        this.api_strictness = level;
    }

    /**
     *
     * Determines weather or not to use SSL when querying from the API Host
     *
     *
     */

    public void useSSL(){
        this.api_url = this.api_url.replace("http://", "https://");
    }


    /**
     *  Pulls data from API URL, pulls info as JSON data and parses it with GSON API
     *
     * @param email
     * @return
     * @throws IOException
     */

    public EmailResp getResponse(String email) throws IOException {
        String query_url = this.get_query_url(email);
        String query_result = this.query(query_url, this.api_timeout, "IpQualityScore Java-Library");

        return new Gson().fromJson(query_result, EmailResp.class);
    }

    /**
     * Generates the URL used for Query
     *
     * @param email
     * @return
     */

    public String get_query_url(String email) {
        String query_url = this.api_url + this.api_key + "/" + email + "?strictness=" + this.api_strictness;
        return query_url;
    }

    /**
     * Function that will pull data from the API URL.
     *
     *
     * @param url
     * @param timeout
     * @param userAgent
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */

    public String query(String url, int timeout, String userAgent)
            throws MalformedURLException, IOException {
        StringBuilder response = new StringBuilder();
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", userAgent);
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()))) {
            while ((url = in.readLine()) != null) {
                response.append(url);
            }

            in.close();
        }

        return response.toString();
    }
}
