package com.rags.flickrgallery;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by development on 25/03/2018.
 */

public class TestInterceptor implements Interceptor {

    private int testType;

    private String response1 = null;
    private String response2 = "";
    private String response3 = "asdasdasd";
    private String response4 = "{}";
    private String response5 = "{\"title\": \"Uploads from everyone\",\"description\": \"Uploads from everyone\", \"items\":[]}";
    private String response6 = "{\"title\": \"Uploads\",\"description\": \"Uploads\", \"items\":[{\"title\": \"Test Title\", \"author\": \"Test Author\", \"published\": \"Test Published On\", \"link\": \"Test Link\", \"media\": {\"m\": \"Test Media Link\"}}]}";

    TestInterceptor(int testType) {
        this.testType = testType;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {

        String responseString = "";
        int responseCode = 200;

        if(testType == 1)
            responseString = response1;

        if(testType == 2)
            responseString = response2;

        if(testType == 3)
            responseString = response3;

        if(testType == 4)
            responseString = response4;

        if(testType == 5)
            responseString = response5;

        if(testType == 6)
            responseString = response6;

        if(testType == 7)
            responseCode = 404;

        if(testType == 8)
            responseCode = 123;

        return new Response.Builder()
                .code(responseCode)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
    }
}
