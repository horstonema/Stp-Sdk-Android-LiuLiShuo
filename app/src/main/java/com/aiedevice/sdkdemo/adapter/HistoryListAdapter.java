package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aiedevice.sdkdemo.bean.HistoryDetail;
import com.aiedevice.sdkdemo.activity.resource.HistoryActivity;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.sdk.resource.ResourceManager;
import com.aiedevice.sdkdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = HistoryListAdapter.class.getSimpleName();

    private List<HistoryDetail.HistoryInfo> mItems;
    private LayoutInflater mLayoutInflater;
    private ResourceManager mResourceModule;
    private Handler mHandler;

    public HistoryListAdapter(Context context, Handler handler) {
        mLayoutInflater = LayoutInflater.from(context);
        mResourceModule = new ResourceManager(context);
        mHandler = handler;
    }

    public void setItems(List<HistoryDetail.HistoryInfo> items) {
        mItems = new ArrayList<HistoryDetail.HistoryInfo>();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void cleanItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public HistoryDetail.HistoryInfo getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.history_item_layout, parent, false);
        HistoryListAdapter.ViewHolder holder = new HistoryListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HistoryDetail.HistoryInfo detail = getItem(position);
        HistoryListAdapter.ViewHolder viewHolder = (HistoryListAdapter.ViewHolder) holder;
        viewHolder.tvCateName.setText(detail.getName());
        viewHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Long> idList = new ArrayList<Long>();
                idList.add(Long.parseLong(detail.getId() + ""));
                mResourceModule.deletePlayHistory(idList, new ResultListener() {
                    @Override
                    public void onSuccess(ResultSupport resultSupport) {
                        mHandler.sendEmptyMessage(HistoryActivity.MSG_DELETE_HISTORY);
                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d(TAG, "code = " + code + "；message = " + message);
                        Toaster.show("删除失败 错误：" + message);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cate_name)
        TextView tvCateName;

        @BindView(R.id.btn_delete)
        Button mBtnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
