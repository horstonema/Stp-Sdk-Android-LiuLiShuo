package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiedevice.sdkdemo.bean.DeviceDetail;
import com.aiedevice.sdk.account.AccountManager;
import com.aiedevice.sdkdemo.R;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = DeviceListAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<DeviceDetail> mItems;
    private AccountManager mAccountManager;
    private DeviceDetail mCurDeviceDetail;
    private DeviceSelectListener mDevSelectListener;

    public DeviceListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mAccountManager = new AccountManager(context);
    }

    public void setDeviceSelectListener(DeviceSelectListener listener) {
        mDevSelectListener = listener;
    }

    public void clearItems() {
        if (mItems == null) return;
        mItems.clear();
        mCurDeviceDetail = null;
        notifyDataSetChanged();
    }

    public void setItems(List<DeviceDetail> items) {
        mItems = items;
        mCurDeviceDetail = null;
        notifyDataSetChanged();
    }

    public DeviceDetail getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = mLayoutInflater.inflate(R.layout.master_item_layout, parent, false);
        MasterViewHolder holder = new MasterViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DeviceDetail detail = getItem(position);
        MasterViewHolder viewHolder = (MasterViewHolder) holder;
        viewHolder.id.setText(" 设备ID :" + detail.getId());
        viewHolder.name.setText(" 设备名称 :" + detail.getName());
//        Log.i(TAG, "test [onBindViewHolder] id=" + detail.getId() + " detail=" + detail.getDetail());
        viewHolder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurDeviceDetail = detail;
                mAccountManager.setDeviceId(detail.getId());
                if (mDevSelectListener != null) {
                    mDevSelectListener.onDeviceSelected(detail);
                }
            }
        });
    }

    public DeviceDetail getCurDeviceDetail() {
        return mCurDeviceDetail;
    }

    class MasterViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        LinearLayout root_layout;

        public MasterViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.tv_device_id);
            name = (TextView) itemView.findViewById(R.id.tv_res_title);
            root_layout = (LinearLayout) itemView.findViewById(R.id.root_layout);
        }
    }

    public static interface DeviceSelectListener {
        void onDeviceSelected(DeviceDetail deviceDetail);
    }
}
