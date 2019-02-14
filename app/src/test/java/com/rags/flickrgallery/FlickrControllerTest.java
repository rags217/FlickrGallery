package com.rags.flickrgallery;

import com.rags.flickrgallery.api.home.FlickrController;
import com.rags.flickrgallery.api.home.FlickrResponse;

import org.junit.Test;
import okhttp3.OkHttpClient;

import static org.junit.Assert.*;


public class FlickrControllerTest {

    @Test
    public void testIfWebServiceIsReachable() throws Exception {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient(), "");
        assertTrue(response.hasItemsResponse());
    }

    @Test
    public void testNullResponse() throws Exception {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(1)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.data_error);
    }


}