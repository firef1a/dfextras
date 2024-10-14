package dev.fire.sitemod.network;

public class ResponseData {
    public int responseCode;
    public String responseData;

    public ResponseData(int responseCode, String responseData) {
        this.responseCode = responseCode;
        this.responseData = responseData;
    }
}
