package com.reabilitic.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reabilitic.R;
import com.reabilitic.models.ChanelModel;
import com.reabilitic.models.GameModel;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter {

    List<GameModel> games;
    Context mContext;
    protected ItemListener mListener;

    public GamesAdapter(Context context, List<GameModel> games) {

        this.games = games;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout root;
        public TextView tvName;
        public ImageView ivIcon;
        public ImageView ivGamesIcon;
        ChanelModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            root = (LinearLayout) v.findViewById(R.id.root);
            tvName = (TextView) v.findViewById(R.id.tvName);
            ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
            ivGamesIcon = (ImageView) v.findViewById(R.id.ivGamesIcon);

        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_view_game, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder)  viewHolder;
        GameModel item = games.get(i);
        holder.tvName.setText(item.getName());
        if (mListener != null) {
            holder.tvName.setOnClickListener(v -> mListener.onItemClick(item));
        }
        holder.ivIcon.setImageDrawable(mContext.getDrawable(item.getIcon()));
        holder.root.setOnClickListener(v -> mListener.onItemClick(item));

        if(i == 0){
            holder.root.setVisibility(View.GONE);
            holder.ivGamesIcon.setVisibility(View.VISIBLE);
        }else{
            holder.root.setVisibility(View.VISIBLE);
            holder.ivGamesIcon.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return games.size();
    }

    public interface ItemListener {
        void onItemClick(GameModel item);
    }

    public void setOnItemClickListener(ItemListener itemListener) {
        mListener = itemListener;
    }
}
