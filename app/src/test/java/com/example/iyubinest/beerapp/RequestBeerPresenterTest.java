package com.example.iyubinest.beerapp;

import com.example.iyubinest.beerapp.beer.request.domain.RequestBeerInteractor;
import com.example.iyubinest.beerapp.beer.request.domain.RequestBeerInteractorCallback;
import com.example.iyubinest.beerapp.beer.request.view.RequestBeerPresenter;
import com.example.iyubinest.beerapp.beer.request.view.RequestBeerView;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by iyubinest on 3/13/16.
 */
public class RequestBeerPresenterTest implements RequestBeerView {

  private static final int BEER_VALUE = 2000;
  private int totalValue;
  private boolean error;

  @Test public void testRequestBeer() {
    RequestBeerPresenter presenter = new RequestBeerPresenter(this, getDefaultValueInteractor());
    presenter.requestBeer();
    Assert.assertEquals(BEER_VALUE, totalValue);
  }

  @Test public void testRequestTwoBeer() {
    RequestBeerPresenter presenter = new RequestBeerPresenter(this, getDefaultValueInteractor());
    presenter.requestBeer();
    presenter.requestBeer();
    Assert.assertEquals(BEER_VALUE * 2, totalValue);
  }

  @Test public void testRequestError() {
    RequestBeerPresenter presenter = new RequestBeerPresenter(this, getDefaultFailureInteractor());
    presenter.requestBeer();
    Assert.assertTrue(error);
  }

  private RequestBeerInteractor getDefaultValueInteractor() {
    return new RequestBeerInteractor() {
      @Override public void requestBeer(RequestBeerInteractorCallback callback) {
        callback.onBeerRequested(BEER_VALUE);
      }
    };
  }

  private RequestBeerInteractor getDefaultFailureInteractor() {
    return new RequestBeerInteractor() {
      @Override public void requestBeer(RequestBeerInteractorCallback callback) {
        callback.onFailure();
      }
    };
  }

  @Override public void onBeerRequested(int beerValue) {
    this.totalValue = beerValue;
  }

  @Override public void onFailure() {
    error = true;
  }
}
