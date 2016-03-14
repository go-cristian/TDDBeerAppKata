package com.example.iyubinest.beerapp.beer.request.view;

/**
 * Created by iyubinest on 3/13/16.
 */
public interface RequestBeerView {
  void onBeerRequested(int beerValue);

  void onFailure();
}
