package com.example.iyubinest.beerapp;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.iyubinest.beerapp.beer.request.domain.RequestBeerInteractor;
import com.example.iyubinest.beerapp.beer.request.domain.RequestBeerInteractorCallback;
import com.example.iyubinest.beerapp.beer.request.view.RequestBeerActivity;
import com.example.iyubinest.beerapp.beer.request.view.RequestBeerPresenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.test.ViewAsserts.assertHorizontalCenterAligned;
import static android.test.ViewAsserts.assertVerticalCenterAligned;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by iyubinest on 3/13/16.
 */
@RunWith(AndroidJUnit4.class) @LargeTest public class RequestBeerActivityTest
    extends ActivityInstrumentationTestCase2<RequestBeerActivity> {
  public static final int BEER_VALUE = 2000;
  private RequestBeerActivity activity;
  private Button requestBeerButton;
  private TextView resultTextView;
  private View failMessage;

  public RequestBeerActivityTest() {
    super(RequestBeerActivity.class);
  }

  @Before protected void setUp() throws Exception {
    activity = getActivity();
    requestBeerButton = (Button) activity.findViewById(R.id.request);
    resultTextView = (TextView) activity.findViewById(R.id.result);
    failMessage = activity.getToast().getView();
  }

  @Test public void testButtonUI() {
    assertHorizontalCenterAligned(activity.findViewById(android.R.id.content), requestBeerButton);
    assertVerticalCenterAligned(activity.findViewById(android.R.id.content), requestBeerButton);
    assertEquals(WRAP_CONTENT, requestBeerButton.getLayoutParams().height);
    assertEquals(WRAP_CONTENT, requestBeerButton.getLayoutParams().width);
    assertEquals(getActivity().getString(R.string.get_beer), requestBeerButton.getText());
  }

  @UiThreadTest public void testShowResult() {
    activity.setPresenter(getMockSuccessPresenter());
    activity.onBeerRequested(BEER_VALUE);
    assertEquals(getExpectedBeerValue(), resultTextView.getText());
  }

  @UiThreadTest public void testInteraction() {
    activity.setPresenter(getMockSuccessPresenter());
    requestBeerButton.performClick();
    assertEquals(getExpectedBeerValue(), resultTextView.getText());
  }

  @UiThreadTest public void testShowFailure() throws InterruptedException {
    activity.setPresenter(getMockFailurePresenter());
    requestBeerButton.performClick();
    assertEquals(View.VISIBLE, failMessage.getVisibility());
    assertEquals(activity.getString(R.string.error),
        ((TextView) ((LinearLayout) failMessage).getChildAt(0)).getText().toString());
  }

  @NonNull private String getExpectedBeerValue() {
    return String.valueOf(BEER_VALUE) + "$";
  }

  private RequestBeerPresenter getMockSuccessPresenter() {
    return new RequestBeerPresenter(activity, new RequestBeerInteractor() {
      @Override public void requestBeer(RequestBeerInteractorCallback callback) {
        callback.onBeerRequested(BEER_VALUE);
      }
    });
  }

  private RequestBeerPresenter getMockFailurePresenter() {
    return new RequestBeerPresenter(activity, new RequestBeerInteractor() {
      @Override public void requestBeer(RequestBeerInteractorCallback callback) {
        callback.onFailure();
      }
    });
  }
}
