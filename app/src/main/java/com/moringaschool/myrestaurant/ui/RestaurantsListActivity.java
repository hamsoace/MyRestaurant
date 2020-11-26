package com.moringaschool.myrestaurant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moringaschool.myrestaurant.adapters.RestaurantListAdapter;
import com.moringaschool.myrestaurant.models.Business;
import com.moringaschool.myrestaurant.R;
import com.moringaschool.myrestaurant.models.YelpBusinessesSearchResponse;

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
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private RestaurantListAdapter mAdapter;

    public List<Business> restaurants;

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
                    showFailureMessage();
            }
        });

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, restaurants);
//        mListView.setAdapter(adapter);
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////            Log.v(TAG, "In the onItemClickListener!");
//            String restaurant = ((TextView)view).getText().toString();
//                Toast.makeText(RestaurantsActivity.this, restaurant, Toast.LENGTH_LONG).show();
//            }
//        });
//        Intent intent = getIntent();
//        String location = intent.getStringExtra("location");
////        mLocationTextView.setText("Here are all the restaurants near: " + location);
////        Log.d(TAG, "In the onCreate method!");
//
//        YelpApi client = YelpClient.getClient();
//        Call<YelpBusinessesSearchResponse> call = client.getRestaurants(location, "restaurants");
//
//        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
//            @Override
//            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
//                if (response.isSuccessful()){
//                    List<Business> restaurantList = response.body().getBusinesses();
//                    String[] restaurants = new String[restaurantList.size()];
//                    String[] categories = new String[restaurantList.size()];
//
//                    for (int i = 0; i < restaurants.length; i++){
//                        restaurants[i] = restaurantList.get(i).getName();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
//
//            }
//        });
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