package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiedevice.sdkdemo.bean.ModuleDetail;
import com.aiedevice.sdkdemo.activity.resource.ResourceIndexActivity;
import com.aiedevice.sdkdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ModuleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ModuleListAdapter.class.getSimpleName();

    private Context mContext;
    private List<ModuleDetail.ModulesInfo> mItems;
    private LayoutInflater mLayoutInflater;

    public ModuleListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void clearItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public ModuleDetail.ModulesInfo getItem(int position) {
        return mItems.get(position);
    }

    public void setItems(List<ModuleDetail.ModulesInfo> items) {
        mItems = new ArrayList<ModuleDetail.ModulesInfo>();
        for (ModuleDetail.ModulesInfo item : items) {
            if (item.getAttr().equals("mod")) {
                mItems.add(item);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = mLayoutInflater.inflate(R.layout.module_item_layout, parent, false);
        ModuleListAdapter.ViewHolder holder = new ModuleListAdapter.ViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ModuleDetail.ModulesInfo detail = getItem(position);
        ModuleListAdapter.ViewHolder mainWordViewHolder = (ModuleListAdapter.ViewHolder) holder;
        mainWordViewHolder.tvCateTitle.setText(detail.getTitle());
        mainWordViewHolder.tvCateTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResourceIndexActivity.launch(mContext, detail.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCateTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCateTitle = (TextView) itemView.findViewById(R.id.tv_cate_name);
        }
    }
}
