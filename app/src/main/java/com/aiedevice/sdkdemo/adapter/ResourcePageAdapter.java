package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiedevice.sdkdemo.bean.PlayResourceEntity;
import com.aiedevice.sdkdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResourcePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<PlayResourceEntity> mItems;
    private onPlayOnClickListener onPlayOnClickListener;

    public void setOnPlayOnClickListener(ResourcePageAdapter.onPlayOnClickListener onPlayOnClickListener) {
        this.onPlayOnClickListener = onPlayOnClickListener;
    }

    public ResourcePageAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void clearItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<PlayResourceEntity> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public PlayResourceEntity getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = mLayoutInflater.inflate(R.layout.resource_item_layout, parent, false);
        ResItemViewHolder holder = new ResItemViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PlayResourceEntity detail = getItem(position);
        ResItemViewHolder viewHolder = (ResItemViewHolder) holder;

        viewHolder.tvResTitle.setText(detail.getName());
        viewHolder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPlayOnClickListener != null) {
                    onPlayOnClickListener.play(detail);
                }
            }
        });

        if (onPlayOnClickListener == null) {
            viewHolder.add_favorite.setVisibility(View.GONE);
            viewHolder.del_favorite.setVisibility(View.GONE);
        }

        viewHolder.add_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPlayOnClickListener != null) {
                    onPlayOnClickListener.addFavorite(detail);
                }
            }
        });

        viewHolder.del_favorite.setVisibility(View.GONE);
    }

    class ResItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_res_title)
        TextView tvResTitle;

        @BindView(R.id.root_layout)
        LinearLayout root_layout;

        @BindView(R.id.tv_del_favorite)
        TextView del_favorite;

        @BindView(R.id.tv_add_favorite)
        TextView add_favorite;

        public ResItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface onPlayOnClickListener {
        void play(PlayResourceEntity detail);

        void addFavorite(PlayResourceEntity detail);

    }
}
