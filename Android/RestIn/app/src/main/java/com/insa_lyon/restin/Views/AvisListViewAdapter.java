package com.insa_lyon.restin.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.insa_lyon.restin.Modeles.Avis;
import com.insa_lyon.restin.Modeles.Restaurant;
import com.insa_lyon.restin.R;

import java.util.List;

/**
 * Created by Jack on 10/01/2017.
 */

public class AvisListViewAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Avis> avis = null;

    public AvisListViewAdapter(Context context, List<Avis> avis) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.avis = avis;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layoutItem;

        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "information_layout.xml"
            layoutItem = (LinearLayout) layoutInflater.inflate(R.layout.avis_list_view_layout, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }


        TextView avisTextView = (TextView)layoutItem.findViewById(R.id.avisTextView);
        RatingBar ratingBar = (RatingBar)layoutItem.findViewById(R.id.ratingBar);



        Avis itemAvis = avis.get(position);

        avisTextView.setText(itemAvis.getAvis());
        ratingBar.setRating((float)itemAvis.getNote());
        return layoutItem;
    }

    @Override
    public int getCount() {
        return avis.size();
    }

    @Override
    public Object getItem(int position) {
        return avis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
