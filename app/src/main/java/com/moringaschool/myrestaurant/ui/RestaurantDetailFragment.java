package com.moringaschool.myrestaurant.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.myrestaurant.R;
import com.moringaschool.myrestaurant.models.Business;
import com.moringaschool.myrestaurant.models.Category;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.restaurantImageView)
    ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView)
    TextView mNameLabel;
    @BindView(R.id.cuisineTextView) TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.saveRestaurantButton) TextView mSaveRestaurantButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public Business mRestaurant;
    public RestaurantDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantDetailFragment newInstance(Business restaurant) {
        RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurant", Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestaurant = Parcels.unwrap(getArguments().getParcelable("restaurant"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.get().load(mRestaurant.getImageUrl()).into(mImageLabel);

        List<String> categories = new ArrayList<>();

        for (Category category: mRestaurant.getCategories()){
            categories.add(category.getTitle());
        }

        mNameLabel.setText(mRestaurant.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", categories));
        mRatingLabel.setText(Double.toString(mRestaurant.getRating()) + "/5");
        mPhoneLabel.setText(mRestaurant.getPhone());
        mAddressLabel.setText(mRestaurant.getLocation().toString());

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        if (v == mWebsiteLabel){
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRestaurant.getUrl()));
            startActivity(webIntent);
        }
        if (v==mPhoneLabel){
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mRestaurant.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel){
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + mRestaurant.getCoordinates().getLatitude()
            + "," + mRestaurant.getCoordinates().getLongitude()
            + "?q=(" + mRestaurant.getName() + ")"));
            startActivity(mapIntent);
        }
    }

//    @Override
//    public String toString(){
//        return String.format("%s, %s, %s, %s", this.address1, this.city, this.state, this.zipCode);
//    }
}