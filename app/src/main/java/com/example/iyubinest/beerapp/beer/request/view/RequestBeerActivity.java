package com.example.iyubinest.beerapp.beer.request.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.iyubinest.beerapp.R;
import com.example.iyubinest.beerapp.beer.request.data.RetrofitRequestBeerInretactor;

public class RequestBeerActivity extends AppCompatActivity implements RequestBeerView {

  private RequestBeerPresenter presenter;
  private View.OnClickListener clickCallback = new View.OnClickListener() {
    @Override public void onClick(View v) {
      presenter.requestBeer();
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_request_beer);
    presenter = getPresenter();
    findViewById(R.id.request).setOnClickListener(clickCallback);
  }

  private RequestBeerPresenter getPresenter() {
    return new RequestBeerPresenter(this,
        new RetrofitRequestBeerInretactor("http://demo0316031.mockable.io/"));
  }

  @Override public void onBeerRequested(int beerValue) {
    ((TextView) findViewById(R.id.result)).setText(String.format("%d$", beerValue));
  }

  @Override public void onFailure() {
    getToast().show();
  }

  public void setPresenter(RequestBeerPresenter presenter) {
    this.presenter = presenter;
  }

  public Toast getToast() {
    return Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT);
  }
}
