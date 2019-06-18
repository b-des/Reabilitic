package com.reabilitic.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reabilitic.R;
import com.reabilitic.models.ArticleModel;
import com.reabilitic.models.ChanelModel;
import com.reabilitic.models.WifiClientModel;

import java.util.ArrayList;
import java.util.List;

public class WifiClientsAdapter extends RecyclerView.Adapter {

    List<WifiClientModel> clients = new ArrayList<>();
    Context mContext;
    protected ItemListener mListener;

    public WifiClientsAdapter(Context context) {

        this.clients = clients;
        mContext = context;

    }

    public void addItem(WifiClientModel item){
        clients.add(item);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvClient;

        WifiClientModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvClient = (TextView) v.findViewById(R.id.tvClient);


        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public WifiClientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_view_wifi_client, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder)  viewHolder;
        holder.tvClient.setText(clients.get(i).getMac()+" ("+clients.get(i).getIp()+")");
        holder.tvClient.setOnClickListener(v -> mListener.onItemClick(clients.get(i)));
    }


    @Override
    public int getItemCount() {
        return clients.size();
    }

    public interface ItemListener {
        void onItemClick(WifiClientModel item);
    }

    public void setOnItemClickListener(ItemListener itemListener) {
        mListener = itemListener;
    }
}
