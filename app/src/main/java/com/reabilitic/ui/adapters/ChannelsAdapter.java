package com.reabilitic.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reabilitic.R;
import com.reabilitic.models.ChanelModel;
import com.reabilitic.utils.Utils;

import java.util.List;

public class ChannelsAdapter extends RecyclerView.Adapter {

    List<ChanelModel> chanels;
    Context mContext;
    protected ItemListener mListener;

    public ChannelsAdapter(Context context, List<ChanelModel> chanels) {

        this.chanels = chanels;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public ImageView imageView;
        ChanelModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvName = (TextView) v.findViewById(R.id.tvName);
            imageView = (ImageView) v.findViewById(R.id.ivPoster);

        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ChannelsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_view_chanel, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder)  viewHolder;
        holder.tvName.setText(chanels.get(i).getName());
        if (mListener != null) {
            holder.tvName.setOnClickListener(v -> mListener.onItemClick(chanels.get(i)));
        }
    }


    @Override
    public int getItemCount() {
        return chanels.size();
    }

    public interface ItemListener {
        void onItemClick(ChanelModel item);
    }

    public void setOnItemClickListener(ItemListener itemListener) {
        mListener = itemListener;
    }
}
