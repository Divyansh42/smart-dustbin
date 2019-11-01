package com.jss.smartdustbin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.nfc.cardemulation.CardEmulation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.vision.L;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.activity.DustbinDetailsActivity;
import com.jss.smartdustbin.activity.DustbinListActivity;
import com.jss.smartdustbin.activity.LoginActivity;
import com.jss.smartdustbin.model.Dustbin;
import com.jss.smartdustbin.utils.Helper;
import com.jss.smartdustbin.utils.SmartDustbinApplication;

import java.util.ArrayList;
import java.util.List;

public class DustbinsAdapter extends RecyclerView.Adapter<DustbinsAdapter.MyViewHolder> {
    private Context context;
    private List<Dustbin> dustbinList;
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView garbageLevelTv;
        TextView lastUpdatedTv;
        ImageView alertIcon;
        View dustbinDetailsCard;

        public MyViewHolder(View view) {
            super(view);
            garbageLevelTv = view.findViewById(R.id.dustbin_level_tv);
            lastUpdatedTv = view.findViewById(R.id.last_updated_tv);
            alertIcon = view.findViewById(R.id.alert_icon);
            dustbinDetailsCard = view.findViewById(R.id.dustbin_details_card);
            context = view.getContext();

        }
    }
    public DustbinsAdapter(Context context, List<Dustbin> dustbinList) {
        this.context = context;
        this.dustbinList = new ArrayList<>(dustbinList);
    }

    public void setItems(List<Dustbin> dustbins){
        this.dustbinList = dustbins;
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
        int garbageStatus = Helper.getGarbageStatusFromLevel(dustbin.getGarbageLevel());
        if(garbageStatus == 1)
            holder.garbageLevelTv.setTextColor(Color.parseColor("#44A849"));
        else if(garbageStatus == 2)
            holder.garbageLevelTv.setTextColor(Color.parseColor("#FF8922"));
        else if(garbageStatus == 3)
            holder.garbageLevelTv.setTextColor(Color.parseColor("#E2574C"));


        holder.garbageLevelTv.setText(dustbin.getGarbageLevel()+ "%" + " full");
        holder.lastUpdatedTv.setText("Last updated at " + dustbin.getLastUpdated());
        holder.alertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert");
                builder.setMessage("Garbage bin is full.");
                builder.setPositiveButton("OK", null);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.dustbinDetailsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DustbinDetailsActivity.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dustbinList.size();
    }

}
