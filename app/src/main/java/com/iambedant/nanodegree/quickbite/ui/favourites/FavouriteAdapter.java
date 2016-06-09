package com.iambedant.nanodegree.quickbite.ui.favourites;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.local.persistent.DataContract;
import com.iambedant.nanodegree.quickbite.util.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kuliza-193 on 5/16/2016.
 */
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteAdapterViewHolder> {

    private Cursor mCursor;
    final private Context mContext;
    private final String TAG = FavouriteAdapter.class.getSimpleName();
    // final private FavouriteAdapterOnClickHandler mClickHandler;

    public FavouriteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public FavouriteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_restaurant, parent, false);
        view.setFocusable(true);
        return new FavouriteAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        int columnIndexName = mCursor.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_NAME);
        int columnIndexRating = mCursor.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_RATINGE);
        int columnIndexLat = mCursor.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_LAT);
        int columnIndexLong = mCursor.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_LONG);
        int columnIndexAddress = mCursor.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_ADDRESS);
        int columnIndexCoverImage = mCursor.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_COVER_IMAGE);
        int columnIndexCuisine = mCursor.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_CUISINE);
        int columnIndexrice = mCursor.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_PRICE);

        holder.mTextViewName.setText(mCursor.getString(columnIndexName));
        holder.mTextViewAddress.setText(mCursor.getString(columnIndexAddress));
        holder.mTextViewCuisines.setText(mCursor.getString(columnIndexCuisine));
        holder.mTextViewRating.setText(mCursor.getFloat(columnIndexRating) + "");
        //todo: Add Localization for Price 
        holder.mTextViewPrice.setText("Rs. "+mCursor.getInt(columnIndexrice) + " For two People (Approx)");
        Glide.with(mContext)
                .load(mCursor.getString(columnIndexCoverImage))
                .into(holder.mImageViewCover);
        holder.mImageButtonDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: Open Map intent with long and lat
                Logger.d(TAG,"Get Direction Clicked");
            }
        });
        holder.mImageButtonFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG,"Favourite Clicked");
                //todo Remove from Favourite,
                //Remove from Recyclerview,
                //Remove from Firebase as well,
            }
        });
        holder.mImageButtonZomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG,"Zomato Clicked");
                //todo open zomato Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public class FavouriteAdapterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView mTextViewName;
        @Bind(R.id.tv_rating)
        TextView mTextViewRating;
        @Bind(R.id.tv_address)
        TextView mTextViewAddress;
        @Bind(R.id.tv_cuisines)
        TextView mTextViewCuisines;

        @Bind(R.id.img_btn_direction)
        ImageButton mImageButtonDirection;
        @Bind(R.id.img_btn_favourite)
        ImageButton mImageButtonFavourite;
        @Bind(R.id.img_btn_zomato)
        ImageButton mImageButtonZomato;
        @Bind(R.id.iv_cover_image)
        ImageView mImageViewCover;

        @Bind(R.id.tv_price)
        TextView mTextViewPrice;

        public FavouriteAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }


    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
        //  mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public static interface FavouriteAdapterOnClickHandler {
        void onClick(Long date, FavouriteAdapterViewHolder vh);
    }

}
