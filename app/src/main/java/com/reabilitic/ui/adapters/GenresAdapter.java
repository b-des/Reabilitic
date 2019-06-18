package com.reabilitic.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dd.ShadowLayout;
import com.reabilitic.R;
import com.reabilitic.models.ChanelModel;
import com.reabilitic.models.GenreModel;
import com.reabilitic.utils.Const;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter {

    private List<GenreModel> genres;
    private Context mContext;
    protected ItemListener mListener;
    private Const.SUBJECT_TYPE SUBJECT_TYPE;
    public GenresAdapter(Context context, List<GenreModel> genres, Const.SUBJECT_TYPE subject_type) {

        this.genres = genres;
        this.mContext = context;
        SUBJECT_TYPE = subject_type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public ImageView ivIcon;
        public ImageView ivGenreTitle;
        public RelativeLayout root;
        public ConstraintLayout container;
        ChanelModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            root = (RelativeLayout) v.findViewById(R.id.root);
            container = (ConstraintLayout) v.findViewById(R.id.container);
            tvName = (TextView) v.findViewById(R.id.tvName);
            ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
            ivGenreTitle = (ImageView) v.findViewById(R.id.ivGenreTitle);
            switch (SUBJECT_TYPE){
                case BOOKS:
                    ivGenreTitle.setImageDrawable(mContext.getDrawable(R.drawable.books_genres));
                    break;
                case FILMS:
                    ivGenreTitle.setImageDrawable(mContext.getDrawable(R.drawable.film_genres));
                    break;
            }
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public GenresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_view_genre, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder)  viewHolder;
        GenreModel item = genres.get(i);
        if (mListener != null) {
            holder.root.setOnClickListener(v -> mListener.onItemClick(genres.get(i)));
        }
        holder.tvName.setText(item.getName());
        try{
            holder.container.setBackgroundColor(Color.parseColor(item.getColor()));
        }catch (Exception e){
            holder.container.setBackgroundColor(Color.parseColor("#2ab267"));
        }
        //holder.ivIcon.setImageURI(Uri.parse(item.getIcon()));

        Glide.with(mContext).load(item.getIcon()).into(holder.ivIcon);

        if(i == 0){
            holder.root.setVisibility(View.GONE);
            holder.ivGenreTitle.setVisibility(View.VISIBLE);
        }else{
            holder.root.setVisibility(View.VISIBLE);
            holder.ivGenreTitle.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return genres.size();
    }

    public interface ItemListener {
        void onItemClick(GenreModel item);
    }

    public void setOnItemClickListener(ItemListener itemListener) {
        mListener = itemListener;
    }
}
