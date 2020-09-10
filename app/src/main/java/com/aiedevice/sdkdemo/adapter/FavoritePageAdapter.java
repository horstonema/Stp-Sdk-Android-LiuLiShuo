package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiedevice.sdkdemo.bean.CollectionResponse;
import com.aiedevice.sdkdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<CollectionResponse> mItems;
    private onPlayOnClickListener onPlayOnClickListener;

    public void setOnPlayOnClickListener(FavoritePageAdapter.onPlayOnClickListener onPlayOnClickListener) {
        this.onPlayOnClickListener = onPlayOnClickListener;
    }

    public FavoritePageAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void clearItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<CollectionResponse> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public CollectionResponse getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = mLayoutInflater.inflate(R.layout.resource_item_layout, parent, false);
        MasterViewHolder holder = new MasterViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CollectionResponse detail = getItem(position);
        MasterViewHolder mainWordViewHolder = (MasterViewHolder) holder;
        mainWordViewHolder.tvAddfavorite.setVisibility(View.INVISIBLE);
        mainWordViewHolder.tvResTitle.setText(detail.getTitle());
        mainWordViewHolder.tvDelFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPlayOnClickListener != null) {
                    onPlayOnClickListener.delFavorite(detail);
                }
            }
        });
    }

    class MasterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_res_title)
        TextView tvResTitle;

        @BindView(R.id.root_layout)
        LinearLayout root_layout;

        @BindView(R.id.tv_del_favorite)
        TextView tvDelFavorite;

        @BindView(R.id.tv_add_favorite)
        TextView tvAddfavorite;

        public MasterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface onPlayOnClickListener {
        void delFavorite(CollectionResponse detail);
    }
}
