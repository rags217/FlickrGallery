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

    @Test
    public void testBlankResponse() throws Exception {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(2)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.data_error);
    }

    @Test
    public void testBadResponse() throws Exception {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(3)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.data_error);
    }

    @Test
    public void testEmptyResponse() throws Exception {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(4)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.data_error);
    }

    @Test
    public void testEmptyListResponse() throws Exception {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(5)).build(), "");
        assertTrue(response.hasItemsResponse());
        assertEquals(response.getItems().size(), 0);
    }


}