package com.example.iyubinest.beerapp.beer.request.data;

import com.example.iyubinest.beerapp.beer.Beer;
import com.example.iyubinest.beerapp.beer.request.domain.RequestBeerInteractorCallback;
import com.example.iyubinest.beerapp.beer.request.domain.RequestBeerInteractor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iyubinest on 3/13/16.
 */
public class RetrofitRequestBeerInretactor implements RequestBeerInteractor {
  private final BeerService service;

  public RetrofitRequestBeerInretactor(String baseUrl) {
    OkHttpClient client = new OkHttpClient();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
    service = retrofit.create(BeerService.class);
  }

  @Override public void requestBeer(final RequestBeerInteractorCallback callback) {
    service.request().enqueue(new Callback<Beer>() {
      @Override public void onResponse(Call<Beer> call, Response<Beer> response) {
        if (response.isSuccess()) {
          callback.onBeerRequested(response.body().getValue());
        } else {
          callback.onFailure();
        }
      }

      @Override public void onFailure(Call<Beer> call, Throwable t) {
        callback.onFailure();
      }
    });
  }
}
