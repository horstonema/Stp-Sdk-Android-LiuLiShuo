package com.aiedevice.sdkdemo.activity.resource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.bean.DeviceDetail;
import com.aiedevice.sdkdemo.R;

import butterknife.OnClick;

public class ResourceManagerActivity extends StpBaseActivity {

    public static void launch(Context context, DeviceDetail devDetail) {
        Intent intent = new Intent(context, ResourceManagerActivity.class);
        intent.putExtra("DeviceDetail", devDetail);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("资源管理");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_resource_manager;
    }

    @OnClick({R.id.btn_category_list, R.id.btn_ablum_res, R.id.btn_play_history,
            R.id.btn_search_res})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_category_list:
                ModuleListActivity.launch(mContext);
                break;
            case R.id.btn_ablum_res:
                ResourceIndexActivity.launch(mContext, -1);
                break;

            case R.id.btn_play_history:
                HistoryActivity.launch(mContext);
                break;

            case R.id.btn_search_res:
                SearchActivity.launch(mContext);
                break;
        }
    }
}
