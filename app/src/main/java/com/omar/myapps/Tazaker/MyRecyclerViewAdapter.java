package com.omar.myapps.Tazaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.myapps.Tazaker.ui.list.ListFragment;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private Fragment fragment;
    private List<String> mData;
    private List<String> FavorNotList;
    private LayoutInflater mInflater;
    //  private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<String> data, List<String> favor_not_list, Fragment fragment) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        FavorNotList = favor_not_list;
        this.context = context;
        this.fragment = fragment;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (Utils.Day_Night_Mode.equals("day")) {
            view = mInflater.inflate(R.layout.day_recycle_view_row, parent, false);
        } else {
            view = mInflater.inflate(R.layout.night_recycle_view_row, parent, false);
        }

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String prayerNameStr = mData.get(position);
        holder.myTextView.setText(prayerNameStr);

        // hiding fav icon in fav list
        if (Utils.Openedlist == DataBaseHelper.FAVORITE_ZKR_TABLE) {
            holder.favImageView.setVisibility(View.GONE);
        } else {
            holder.favImageView.setVisibility(View.VISIBLE);
            String FavOrNot = FavorNotList.get(position);

            if (FavOrNot.equals("isFavorite")) {
                holder.favImageView.setImageResource(R.drawable.ic_favorite_on);
                holder.favImageView.setTag("isFavorite");
            } else {
                holder.favImageView.setImageResource(R.drawable.ic_favorite_off);
                holder.favImageView.setTag("isNotFavorite");
            }
        }


        holder.prayerindex.setText(Utils.replaceToArabicNumbers(String.valueOf(position + 1)));

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView, prayerindex;
        ImageView favImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.prayerNametextView);
            prayerindex = itemView.findViewById(R.id.prayerindextextView);
            favImageView = itemView.findViewById(R.id.favImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // when click the item row: take prayer name for the text view to be used lator
                    ((ListFragment) fragment).passData(myTextView.getText().toString());

                    if (context instanceof MainActivity) {
                        ((MainActivity) context).switchToFragment3();
                    }
                }
            });

            favImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ("isNotFavorite".equals(favImageView.getTag().toString())) {
                        //    add  it  to fav table and add it as change fav or not to is favorited in its table

                        new DataBaseHelper(context).addRecordTo_FavoriteTABLE(
                                myTextView.getText().toString(), "isFavorite");

                        new DataBaseHelper(context).updateDataFor(Utils.Openedlist, "favorite_or_not",
                                "isFavorite", myTextView.getText().toString());


                        favImageView.setTag("ic_favorite_on");
                        favImageView.setImageResource(R.drawable.ic_favorite_on);
                        return;
                    } else {
                        new DataBaseHelper(context).deleteRecordTo_FavoriteTABLE(myTextView.getText().toString());

                        new DataBaseHelper(context).updateDataFor(Utils.Openedlist, "favorite_or_not",
                                "isNotFavorite", myTextView.getText().toString());

                        favImageView.setTag("ic_favorite_off");
                        favImageView.setImageResource(R.drawable.ic_favorite_off);
                        return;
                    }

                }


            });


        }


    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

}