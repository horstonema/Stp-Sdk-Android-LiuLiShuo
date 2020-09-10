package com.aiedevice.sdkdemo.activity.device;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.activity.account.ChangePasswordActivity;
import com.aiedevice.sdkdemo.adapter.DeviceListAdapter;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.bean.MainctrlListData;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.SharedPreferencesUtil;
import com.aiedevice.sdk.device.DeviceManager;

public class MasterListActivity extends StpBaseActivity {
    public static final String TAG = MasterListActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private DeviceListAdapter masterAdapter;
    private DeviceManager mDeviceManager;
    private Button mUserId;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MasterListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.master_list_main);
        setTitle(getString(R.string.master_activity_list));
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_res_list);
        mUserId = (Button) findViewById(R.id.text_user_id);
        mDeviceManager = new DeviceManager(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        masterAdapter = new DeviceListAdapter(this);
        mRecyclerView.setAdapter(masterAdapter);
        init();
        mUserId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", SharedPreferencesUtil.getAccountId());
                cm.setPrimaryClip(mClipData);
            }
        });

        findViewById(R.id.bind_qbean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BindDeviceActivity.launch(MasterListActivity.this);
            }
        });

        findViewById(R.id.change_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordActivity.launch(MasterListActivity.this);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.master_list_main;
    }

    private void init() {
        mDeviceManager.getDeviceList(true, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                MainctrlListData listData = gson.fromJson(resultSupport.getModel("data").toString(), MainctrlListData.class);
                Log.d("resultSupport", listData.toString());
                masterAdapter.setItems(listData.getMasters());
                Toaster.show("获取设备列表成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取设备列表失败 错误：" + message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
