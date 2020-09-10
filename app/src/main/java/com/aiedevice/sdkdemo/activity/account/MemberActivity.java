package com.aiedevice.sdkdemo.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.adapter.MemberAdapter;
import com.aiedevice.sdkdemo.bean.DeviceDetail;
import com.aiedevice.sdkdemo.bean.User;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.account.AccountManager;
import com.aiedevice.sdk.base.utils.Util;
import com.aiedevice.sdk.device.DeviceManager;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberActivity extends StpBaseActivity {
    private static final String TAG = MemberActivity.class.getSimpleName();

    // logic
    private MemberAdapter mMemberAdapter;
    private DeviceManager mDeviceManager;
    private AccountManager mAccountManager;

    // views
    @BindView(R.id.rv_res_list)
    public RecyclerView rvUserList;
    @BindView(R.id.btn_refresh)
    public Button btnRefresh;
    @BindView(R.id.btn_add_member)
    public Button btnAddMember;
    @BindView(R.id.et_username)
    public EditText etUsername;

    public static void launch(Context context, String deviceId) {
        Intent intent = new Intent(context, MemberActivity.class);
        intent.putExtra("DeviceId", deviceId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("成员管理");

        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.member_list_main;
    }

    private void initView() {
        rvUserList.setLayoutManager(new LinearLayoutManager(this));
        mMemberAdapter = new MemberAdapter(mContext);
        mMemberAdapter.setMemberListener(new MemberAdapter.MemberListener() {
            @Override
            public void onDelMember(User user) {
                //删除用户
                delMember(user);
            }

            @Override
            public void onTransferManager(User user) {
                //转移管理员
                transferManager(user);
            }
        });
        rvUserList.setAdapter(mMemberAdapter);
    }

    private void initLogic() {
        mAccountManager = new AccountManager(mContext);

        mDeviceManager = new DeviceManager(mContext);
        refreshData();
    }

    @OnClick({R.id.btn_add_member, R.id.btn_refresh})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_member:
                addMember();
                break;

            case R.id.btn_refresh:
                refreshData();
                break;
        }
    }

    private void addMember() {
        String phone = etUsername.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toaster.show("请输入要添加用户的手机号");
            return;
        }

        mAccountManager.inviteUser(Util.getPhone(phone), new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                if (resultSupport.getResult() == 0) {
                    Toaster.show("邀请成功");
                    refreshData();
                } else {
                    Toaster.show("邀请失败");
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取设备详情失败 错误:" + message);
            }
        });
    }

    private void transferManager(User otherUser) {
        mAccountManager.changeManager(otherUser.getUserId(), new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("转移成功");
                refreshData();
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("转移失败 错误：" + message);
            }
        });
    }

    private void delMember(User user) {
        mAccountManager.deleteUser(user.getUserId(), new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                if (resultSupport.getResult() == 0) {
                    Toaster.show("删除成功");
                    refreshData();
                } else {
                    Toaster.show(resultSupport.getMsg());
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("删除失败 错误:" + message);
            }
        });

    }

    private void refreshData() {
        mDeviceManager.getDeviceDetail(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                DeviceDetail masterDetail = gson.fromJson(resultSupport.getModel("data").toString(), DeviceDetail.class);
                mMemberAdapter.setItems(masterDetail.getUsers());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取成员信息失败 错误：" + message);
            }
        });
    }
}
