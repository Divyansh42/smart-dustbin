package com.jss.smartdustbin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.vision.L;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.model.Dustbin;

import java.util.List;

public class DustbinsAdapter extends RecyclerView.Adapter<DustbinsAdapter.MyViewHolder> {
    private Context context;
    private List<Dustbin> dustbinList;
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView garbageLevelTv;
        public TextView lastUpdatedTv;

        public MyViewHolder(View view) {
            super(view);
            garbageLevelTv = view.findViewById(R.id.dustbin_level_tv);
            lastUpdatedTv = view.findViewById(R.id.last_updated_tv);

        }
    }
    public DustbinsAdapter(Context context, List<Dustbin> dustbinList) {
        this.context = context;
        this.dustbinList = dustbinList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dustbin_details_card_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Dustbin dustbin =  dustbinList.get(position);
        if(Integer.parseInt(dustbin.getGarbageLevel()) < 25)
            holder.garbageLevelTv.setTextColor(Color.parseColor("#44A849"));
        else if(Integer.parseInt(dustbin.getGarbageLevel()) >= 25 && Integer.parseInt(dustbin.getGarbageLevel()) <= 74)
            holder.garbageLevelTv.setTextColor(Color.parseColor("#FF8922"));
        else if(Integer.parseInt(dustbin.getGarbageLevel()) > 74)
            holder.garbageLevelTv.setTextColor(Color.parseColor("#E2574C"));


        holder.garbageLevelTv.setText(dustbin.getGarbageLevel()+ "%" + " full");
        holder.lastUpdatedTv.setText("Last updated at " + dustbin.getLastUpdated());

    }

    @Override
    public int getItemCount() {
        return dustbinList.size();
    }

}
