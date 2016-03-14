package com.example.iyubinest.beerapp.beer.request.data;

import com.example.iyubinest.beerapp.beer.Beer;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by iyubinest on 3/13/16.
 */
public interface BeerService {
  @GET("request") Call<Beer> request();
}
