package com.example.iyubinest.beerapp.beer.request.domain;

/**
 * Created by iyubinest on 3/13/16.
 */
public interface RequestBeerInteractorCallback {
  void onBeerRequested(int beerValue);

  void onFailure();
}
