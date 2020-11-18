package com.moringaschool.myrestaurant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.myrestaurant.R;
import com.moringaschool.myrestaurant.models.Business;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ResaurantViewHolder> {
    private List<Business>mRestaurants;
    private Context mContext;

    public RestaurantListAdapter(Context context, List<Business> restaurants){
        mContext = context;
        mRestaurants = restaurants;
    }

    @Override
    public RestaurantListAdapter.ResaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        ResaurantViewHolder viewHolder = new ResaurantViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.ResaurantViewHolder holder, int position){
        holder.bindRestaurant(mRestaurants.get(position));
    }

    @Override
    public int getItemCount(){
        return  mRestaurants.size();
    }

    public class ResaurantViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.restaurantImageView)
        ImageView mRestaurantImageView;
        @BindView(R.id.restaurantNameTextView)
        TextView mNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;

        private Context context;

        public ResaurantViewHolder (View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindRestaurant(Business restaurant){
            mNameTextView.setText(restaurant.getName());
            mCategoryTextView.setText((restaurant.getCategories().get(0).getTitle()));
            mRatingTextView.setText("Rating: " + restaurant.getRating() + "/5");
        }
    }
}
