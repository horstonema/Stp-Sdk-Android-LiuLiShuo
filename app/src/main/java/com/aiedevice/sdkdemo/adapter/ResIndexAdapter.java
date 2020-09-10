package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiedevice.sdkdemo.bean.HomePageCateItem;
import com.aiedevice.sdkdemo.activity.resource.ResourceListActivity;
import com.aiedevice.appcommon.util.GlideUtils;
import com.aiedevice.sdkdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<HomePageCateItem> mItems;

    public ResIndexAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void clearItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<HomePageCateItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public HomePageCateItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = mLayoutInflater.inflate(R.layout.item_resource_index, parent, false);
        ResItemViewHolder holder = new ResItemViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HomePageCateItem detail = getItem(position);
        ResItemViewHolder viewHolder = (ResItemViewHolder) holder;

        viewHolder.tvResTitle.setText(detail.getTitle());
        viewHolder.tvResCount.setText(detail.getTotal() + "");
        GlideUtils.loadImageView(mContext, detail.getThumb(), viewHolder.ivThumb);

        viewHolder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ResourceListActivity.class);
                intent.putExtra("cid", detail.getId());
                intent.putExtra("src", detail.getSrc());
                intent.putExtra("page", 1);
                mContext.startActivity(intent);
            }
        });
    }

    class ResItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_res_title)
        TextView tvResTitle;
        @BindView(R.id.tv_res_count)
        TextView tvResCount;
        @BindView(R.id.root_layout)
        LinearLayout root_layout;

        public ResItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
