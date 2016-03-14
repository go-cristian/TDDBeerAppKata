package com.example.iyubinest.beerapp;

import com.example.iyubinest.beerapp.beer.request.domain.RequestBeerInteractorCallback;
import com.example.iyubinest.beerapp.beer.request.data.RetrofitRequestBeerInretactor;
import java.io.IOException;
import junit.framework.Assert;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by iyubinest on 3/13/16.
 */
public class RetrofitRequestBeerInretactorTest {

  private static final double BEER_VALUE = 2000;
  private RequestBeerInteractorCallback callback = new RequestBeerInteractorCallback() {
    @Override public void onBeerRequested(int beerValue) {
      RetrofitRequestBeerInretactorTest.this.beerValue = beerValue;
    }

    @Override public void onFailure() {
      error = true;
    }
  };
  private double beerValue;
  private MockWebServer server;
  private boolean error;

  @Before public void setup() throws IOException {
    server = new MockWebServer();
    server.start();
  }

  @After public void tearDown() throws IOException {
    server.shutdown();
  }

  @Test public void testSuccessRequest() throws InterruptedException {
    enqueue(200, 2000);
    RetrofitRequestBeerInretactor interactor =
        new RetrofitRequestBeerInretactor(server.url("/").toString());
    interactor.requestBeer(callback);
    Thread.sleep(500);
    Assert.assertEquals(BEER_VALUE, beerValue);
  }

  @Test public void testFailureRequest() throws Exception {
    enqueue(500, 0);
    RetrofitRequestBeerInretactor interactor =
        new RetrofitRequestBeerInretactor(server.url("/").toString());
    interactor.requestBeer(callback);
    Thread.sleep(500);
    Assert.assertTrue(error);
  }

  private void enqueue(int code, int mockBeerValue) throws InterruptedException {
    MockResponse response = new MockResponse();
    response.setResponseCode(code);
    response.setBody("{\"value\":" + mockBeerValue + "}");
    server.enqueue(response);
  }
}
