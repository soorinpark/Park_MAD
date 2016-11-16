package com.example.soorinpark.beerbelly.adapter;

/**
 * Created by soorinpark on 11/10/16.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.soorinpark.beerbelly.R;
import com.example.soorinpark.beerbelly.model.Brewery;

import java.util.List;

public class BreweriesAdapter extends RecyclerView.Adapter<BreweriesAdapter.BreweryViewHolder> {

    private List<Brewery> brews;
    private int rowLayout;
    private Context context;

    public static class BreweryViewHolder extends RecyclerView.ViewHolder {

        LinearLayout brewLayout;
        TextView brewName;
        TextView brewStreet;
        TextView brewCityStateZip;
        TextView brewPhone;
        TextView brewWeb;

        public BreweryViewHolder(View v) {
            super(v);
            brewLayout = (LinearLayout) v.findViewById(R.id.brewery_layout);
            brewName = (TextView) v.findViewById(R.id.brewery_name);
            brewStreet = (TextView) v.findViewById(R.id.brewery_street);
            brewCityStateZip = (TextView) v.findViewById(R.id.brewery_city_state_zipcode);
            brewPhone = (TextView) v.findViewById(R.id.brewery_phone);
            brewWeb = (TextView) v.findViewById(R.id.brewery_web);

            Typeface headFont = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Bungee-Regular.ttf");
            Typeface bodyFont = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/OpenSans-Regular.ttf");
            this.brewName.setTypeface(headFont);
            this.brewStreet.setTypeface(bodyFont);
            this.brewCityStateZip.setTypeface(bodyFont);
            this.brewPhone.setTypeface(bodyFont);
            this.brewWeb.setTypeface(bodyFont);
        }
    }

    public BreweriesAdapter(List<Brewery> brews, int rowLayout, Context context) {
        this.brews = brews;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public BreweriesAdapter.BreweryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BreweryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BreweryViewHolder holder, final int position) {

        Brewery brewObj = brews.get(position).getBrewery();

        holder.brewName.setText(brewObj.getName());

        if (brewObj.getWebsite() == null) {
            holder.brewWeb.setVisibility(View.GONE);
        }
        else {
            holder.brewWeb.setText(brewObj.getWebsite());
        }

        String street = brews.get(position).getStreet();
        String city = brews.get(position).getCity();
        String state = brews.get(position).getState();
        String zip = brews.get(position).getZipcode();
        String csz = city + ", " + state + " " + zip;


        holder.brewStreet.setText(street);
        holder.brewCityStateZip.setText(csz);

        if (brews.get(position).getPhone() == null) {
            holder.brewPhone.setVisibility(View.GONE);
        }
        else {
            holder.brewPhone.setText(brews.get(position).getPhone());
        }

//        if (brews.get(position).getWebsite() == null) {
//            holder.brewWeb.setVisibility(View.GONE);
//        }
//        else {
//            holder.brewWeb.setText(brews.get(position).getWebsite());
//        }

    }

    @Override
    public int getItemCount() {
        return brews.size();
    }

}
