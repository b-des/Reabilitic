package com.reabilitic.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ComplexColorCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reabilitic.R;
import com.reabilitic.models.ArticleModel;
import com.reabilitic.models.ChanelModel;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter {

    List<ArticleModel> articles;
    Context mContext;
    protected ItemListener mListener;

    public ArticlesAdapter(Context context, List<ArticleModel> articles) {

        this.articles = articles;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public TextView tvDescription;
        public LinearLayout container;
        public Button btnRead;
        ChanelModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            container = (LinearLayout) v.findViewById(R.id.container);
            btnRead = (Button) v.findViewById(R.id.btnRead);

        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_view_article, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder)  viewHolder;
        holder.tvName.setText(articles.get(i).getName());
        holder.tvDescription.setText(articles.get(i).getShort_desc());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvDescription.setText(Html.fromHtml(articles.get(i).getShort_desc(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvDescription.setText(Html.fromHtml(articles.get(i).getShort_desc()));
        }

        if (mListener != null) {
            holder.tvName.setOnClickListener(v -> mListener.onItemClick(articles.get(i)));
        }
        holder.btnRead.setOnClickListener(v -> mListener.onItemClick(articles.get(i)));

        if(i % 2 == 0){
            holder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            holder.tvDescription.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            holder.container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhiteTransparent));

        }else{
            holder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlack));
            holder.tvDescription.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlack));
            holder.container.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
        }
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface ItemListener {
        void onItemClick(ArticleModel item);
    }

    public void setOnItemClickListener(ItemListener itemListener) {
        mListener = itemListener;
    }
}
