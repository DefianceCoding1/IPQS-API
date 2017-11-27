package com.ipqualityscore.defiancecoding.vpn;

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
public final class VPNDetection {

    /**
     * Allows you to lookup and determine info of a specified IPv4 or IPv6 Address and check
     * if it belongs to a VPN Network, Proxy Server, Tor Network, and or Mobile Network
     *
     *
     * This class facilitates and simplifies using the web API, and allows you to
     * easily implement the functionality in your applications.
     *
     * API Homepage: https://ipqualityscore.com
     *
     *
     * @author DefianceCoding
     */


    private String api_key;
    private String api_url = "http://ipqualityscore.com/api/json/ip/";
    private int api_timeout = 5000;
    private int api_strictness = 1;

    public VPNDetection(String key){
        this.api_key = key;
    }

    public VPNDetection(String key, int timeout){
        this.api_key = key;
        this.api_timeout = timeout;
    }

    public VPNDetection(String key, int timeout, int strictness){
        this.api_key = key;
        this.api_timeout = timeout;
        this.api_strictness = strictness;
    }

    /**
     * Here is where you can set your API Key from your user dashboard
     * @param key APIKey from RESTful API
     */

    public void set_api_key(String key){
        this.api_key = key;
    }

    /**
     *  Units are measured in milliseconds and is the max time the API will try to poll info.
     *
     * @param timeout Time in Milliseconds the query will take before timing out
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
     * @param level set strength of check 0-5
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
     *
     * Queries the API server to get results via JSON VPNResponse and pulls data via GSON API
     *
     * @param ip = Returns IP Checked via API
     * @return GSON Result
     * @throws IOException
     */

    public VPNResponse getResponse(String ip) throws IOException{
        String query_url = this.get_query_url(ip);
        String query_result = this.query(query_url, this.api_timeout, "IpQualityScore Java-Library");

        return new Gson().fromJson(query_result, VPNResponse.class);
    }

    /**
     * Generates the URL used for Query
     *
     * @param ip IP To query
     * @return String URL
     */

    public String get_query_url(String ip) {
            String query_url = this.api_url + this.api_key + "/" + ip + "?strictness=" + this.api_strictness;
            return query_url;
    }

    /**
     * Function that will pull data from the API URL.
     *
     *
     * @param url full based url from arguments given
     * @param timeout timeout in milliseconds before api will give up
     * @param userAgent Useragent from custom based web response for api to recognize
     * @return GSON Response
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
