package com.rags.flickrgallery;

import com.rags.flickrgallery.api.home.FlickrController;
import com.rags.flickrgallery.api.home.FlickrResponse;
import com.rags.flickrgallery.model.home.Item;

import org.junit.Test;
import okhttp3.OkHttpClient;

import static org.junit.Assert.*;


public class FlickrControllerTest {

    @Test
    public void testIfWebServiceIsReachable() {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient(), "");
        assertTrue(response.hasItemsResponse());
    }

    @Test
    public void testNullResponse() {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(1)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.data_error);
    }

    @Test
    public void testBlankResponse() {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(2)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.data_error);
    }

    @Test
    public void testBadResponse() {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(3)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.data_error);
    }

    @Test
    public void testEmptyResponse() {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(4)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.data_error);
    }

    @Test
    public void testEmptyListResponse() {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(5)).build(), "");
        assertTrue(response.hasItemsResponse());
        assertEquals(response.getItems().size(), 0);
    }

    @Test
    public void testResponseParsing() {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(6)).build(), "");
        Item i = response.getItems().get(0);
        assertEquals(i.getTitle(), "Test Title");
        assertEquals(i.getAuthor(), "Test Author");
        assertEquals(i.getPublished(), "Test Published On");
        assertEquals(i.getLink(), "Test Link");
        assertEquals(i.getMedia().getM(), "Test Media Link");
    }

    @Test
    public void testNetworkError() {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(7)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.network_error);
    }

    @Test
    public void testUnknownError() {
        FlickrResponse response = FlickrController.downloadItems(new OkHttpClient.Builder().addInterceptor(new TestInterceptor(8)).build(), "");
        assertFalse(response.hasItemsResponse());
        assertEquals(response.getErrorMessageResId(), R.string.unknown_error);
    }
}