package com.moringaschool.myrestaurant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moringaschool.myrestaurant.Constants;
import com.moringaschool.myrestaurant.adapters.RestaurantListAdapter;
import com.moringaschool.myrestaurant.models.Business;
import com.moringaschool.myrestaurant.R;
import com.moringaschool.myrestaurant.models.YelpBusinessesSearchResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.moringaschool.myrestaurant.network.YelpApi;
import com.moringaschool.myrestaurant.network.YelpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantsListActivity extends AppCompatActivity {
    private static final String TAG = RestaurantsListActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private String mRecentAddress;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar)
   ProgressBar mProgressBar;
    private RestaurantListAdapter mAdapter;

    public List<Business> restaurants = new ArrayList<>();
    public ArrayList<Business> mRestaurants;

//    private String[] restaurants = new String[]{"Mi Mero Mole", "Mother's Bistro",
//        "Life of Pie", "Screen Door", "Luc Lac", "Sweet Basil",
//        "Slappy Cakes", "Equinox", "Miss Delta's", "Andina",
//        "Lardo", "Portland City Grill", "Fat Head's Brewery",
//        "Chipotle", "Subway"};
//
//    public static final String TAG = RestaurantsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        YelpApi client = YelpClient.getClient();

        Call<YelpBusinessesSearchResponse> call = client.getRestaurants(location, "restaurants");

        //pulling data from it by calling getString()
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);//default value null will be returned if the getString() method is unable to find a value
        Log.d("Shared Pref Location", mRecentAddress);

        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
                hideProgressBar();

                if (response.isSuccessful()){
                    restaurants = response.body().getBusinesses();
                    mAdapter = new RestaurantListAdapter(RestaurantsListActivity.this, restaurants);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RestaurantsListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showRestaurants();
                }else{

                    showUnsuccessfulMessage();
                    }
                }


            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                    hideProgressBar();
                    Log.e(TAG, "In the onFailure method ", t);
                    Log.e(TAG, "In throwable", t);
                    showFailureMessage();
            }
        });

    }

    private void getRestaurants(){
        final YelpClient yelpClient = new YelpClient();
    }

    private void showFailureMessage(){
        mErrorTextView.setText(("Something went wrong. Please check your Internet connection and try again later!"));
        mErrorTextView.setVisibility(View.GONE);
    }

    private void showUnsuccessfulMessage(){
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.GONE);
    }

    private void showRestaurants(){
        mRecyclerView.setVisibility(View.GONE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

}