package com.example.iyubinest.beerapp.beer.request.view;

import com.example.iyubinest.beerapp.beer.request.domain.RequestBeerInteractorCallback;
import com.example.iyubinest.beerapp.beer.request.domain.RequestBeerInteractor;

/**
 * Created by iyubinest on 3/13/16.
 */
public class RequestBeerPresenter {
  private final RequestBeerView view;
  private final RequestBeerInteractor interactor;
  private int totalValue = 0;

  public RequestBeerPresenter(RequestBeerView view, RequestBeerInteractor interactor) {
    this.view = view;
    this.interactor = interactor;
  }

  public void requestBeer() {
    interactor.requestBeer(new RequestBeerInteractorCallback() {
      @Override public void onBeerRequested(int beerValue) {
        totalValue += beerValue;
        view.onBeerRequested(totalValue);
      }

      @Override public void onFailure() {
        view.onFailure();
      }
    });
  }
}
