package com.aiedevice.sdkdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.account.LoginManager;
import com.aiedevice.sdk.bean.GlobalVars;
import com.aiedevice.sdk.device.DeviceManager;
import com.aiedevice.sdkdemo.activity.account.AccountManagerActivity;
import com.aiedevice.sdkdemo.activity.chat.ChatManagerActivity;
import com.aiedevice.sdkdemo.activity.device.DeviceManageActivity;
import com.aiedevice.sdkdemo.activity.sdcard.PicBookManagerActiviy;
import com.aiedevice.sdkdemo.activity.resource.ResourceManagerActivity;
import com.aiedevice.sdkdemo.activity.study.ClassroomActivity;
import com.aiedevice.sdkdemo.activity.study.StudyManageAcitivy;
import com.aiedevice.sdkdemo.adapter.DeviceListAdapter;
import com.aiedevice.sdkdemo.bean.DeviceDetail;
import com.aiedevice.sdkdemo.bean.MainctrlListData;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends StpBaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    // logic
    private LoginManager mLoginManager;
    private DeviceListAdapter mDeviceListAdapter;
    private DeviceManager mDeviceManager;
    private DeviceListAdapter.DeviceSelectListener mDevSelectListener;

    // views
    @BindView(R.id.tv_cur_device)
    public TextView tvCurDevice;

    @BindView(R.id.rv_device_list)
    public RecyclerView mRvDeviceList;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("主界面");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_main;
    }

    private void initView() {
        mDevSelectListener = new DeviceListAdapter.DeviceSelectListener() {
            @Override
            public void onDeviceSelected(DeviceDetail deviceDetail) {
                if (deviceDetail != null) {
                    tvCurDevice.setText("当前选中设备：" + deviceDetail.getName() + " [" + deviceDetail.getId() + "]");
                }
            }
        };

        mRvDeviceList.setLayoutManager(new LinearLayoutManager(this));
        mDeviceListAdapter = new DeviceListAdapter(mContext);
        mDeviceListAdapter.setDeviceSelectListener(mDevSelectListener);
        mRvDeviceList.setAdapter(mDeviceListAdapter);
    }

    private void initLogic() {
        mDeviceManager = new DeviceManager(mContext);
        mDeviceManager.getDeviceList(true, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                MainctrlListData listData = gson.fromJson(resultSupport.getModel("data").toString(), MainctrlListData.class);
                mDeviceListAdapter.setItems(listData.getMasters());
                Toaster.show("获取设备列表成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取设备列表失败 错误:" + message);
            }
        });

        mLoginManager = new LoginManager(mContext);
    }

    @OnClick({R.id.btn_account, R.id.btn_device, R.id.btn_resource, R.id.btn_wechat,
            R.id.btn_study, R.id.btn_picbook, R.id.btn_logout, R.id.btn_classroom})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_account:
                DeviceDetail devDetail = getDeviceDetail();
                if (devDetail != null) {
                    AccountManagerActivity.launch(mContext, devDetail.getId());
                }
                break;

            case R.id.btn_device:
                devDetail = mDeviceListAdapter.getCurDeviceDetail();
                DeviceManageActivity.launch(mContext, devDetail);
                break;

            case R.id.btn_resource:
                devDetail = getDeviceDetail();
                if (devDetail != null) {
                    ResourceManagerActivity.launch(mContext, devDetail);
                }
                break;

            case R.id.btn_wechat:
                devDetail = getDeviceDetail();
                if (devDetail != null) {
                    ChatManagerActivity.launch(mContext, devDetail);
                }
                break;

            case R.id.btn_study:
                devDetail = getDeviceDetail();
                if (devDetail != null) {
                    StudyManageAcitivy.launch(mContext);
                }
                break;

            case R.id.btn_picbook:
                devDetail = getDeviceDetail();
                if (devDetail != null) {
                    PicBookManagerActiviy.launch(mContext);
                }
                break;

            case R.id.btn_classroom:
                ClassroomActivity.launch(mContext);
                break;

            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private DeviceDetail getDeviceDetail() {
        if (mDeviceListAdapter.getCurDeviceDetail() == null) {
            Toaster.show("请先选择设备");
            return null;
        } else {
            return mDeviceListAdapter.getCurDeviceDetail();
        }
    }

    private void logout() {
        mLoginManager.logout(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[logout-succ] result=" + result.getResult());
                GlobalVars.setLoginStatus(false);
                Toaster.show("注销成功");
                finish();
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "[logout-err] code=" + code + " message=" + message);
            }
        });
    }

}
