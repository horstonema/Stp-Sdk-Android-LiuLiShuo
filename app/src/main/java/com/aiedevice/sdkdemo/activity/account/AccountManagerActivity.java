package com.aiedevice.sdkdemo.activity.account;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.sdk.account.AccountManager;
import com.aiedevice.sdk.account.LoginManager;
import com.aiedevice.sdkdemo.R;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.bean.UploadUserAvatarData;
import com.aiedevice.sdkdemo.utils.FileUtil;
import com.aiedevice.sdkdemo.utils.PermissionHelper;
import com.google.gson.Gson;

import butterknife.OnClick;

public class AccountManagerActivity extends StpBaseActivity {
    private static final String TAG = AccountManagerActivity.class.getSimpleName();
    private static final int REQ_CHOOSE_PHOTO = 1;

    // logic
    private LoginManager mLoginManager;
    private AccountManager mAccountManager;
    private PermissionHelper permissionHelper;

    public static void launch(Context context, String deviceId) {
        Intent intent = new Intent(context, AccountManagerActivity.class);
        intent.putExtra("DeviceId", deviceId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("账户管理");
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_account_manager;
    }

    private void initLogic() {
        mAccountManager = new AccountManager(mContext);
        mLoginManager = new LoginManager(mContext);

        PermissionHelper.PermissionRequestCallBack perReqCallback = new PermissionHelper.PermissionRequestCallBack() {
            @Override
            public void onPermissionGrant(String[] permissionNames) {
                if (permissionNames != null) {
                    for (String perName : permissionNames) {
                        Log.d(TAG, "permission [" + perName + "] is granted");
                    }
                }
            }

            @Override
            public void onPermissionNoGrant(String[] permissionNames) {
            }
        };

        permissionHelper = new PermissionHelper(this);
        permissionHelper.requestStoragePermission(perReqCallback);
    }

    @OnClick({R.id.btn_modify_passwd, R.id.btn_member_manage, R.id.btn_upload_figure,
            R.id.btn_baby_info})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_modify_passwd:
                ChangePasswordActivity.launch(mContext);
                break;

            case R.id.btn_member_manage:
                Intent intent = getIntent();
                String deviceId = intent.getStringExtra("DeviceId");
                if (TextUtils.isEmpty(deviceId)) {
                    Toaster.show("获取设备ID失败");
                    return;
                }

                MemberActivity.launch(mContext, deviceId);
                break;

            case R.id.btn_upload_figure:
                choosePhoto();
                break;

            case R.id.btn_baby_info:
                BabyListActivity.launch(mContext);
                break;
        }
    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, REQ_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;

        switch (requestCode) {
            case REQ_CHOOSE_PHOTO:
                Uri uri = data.getData();
                String filePath = FileUtil.getFilePathByUri(this, uri);
                uploadFigure(filePath);
                break;
        }
    }

    //上传头像
    private void uploadFigure(final String filePath) {
        Log.d(TAG, "[uploadFigure] filePath=" + filePath);
        if (TextUtils.isEmpty(filePath)) {
            return;
        }

        mAccountManager.addAvatar(filePath, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Log.d(TAG, "[addAvatar-succ] data=" + resultSupport.getData());
                Gson gson = GsonUtils.getGson();
                UploadUserAvatarData avatarData = gson.fromJson(resultSupport.getData(), UploadUserAvatarData.class);
                if (avatarData != null) {
                    saveUserHeadimg(avatarData.getImg());
                } else {
                    Log.d(TAG, "[addAvatar-succ] avatarData is null");
                    Toaster.show("上传失败");
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("上传失败");
            }
        });
    }

    //保存和更新用户头像
    private void saveUserHeadimg(String imageName) {
        mLoginManager.updateAvatar(imageName, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[saveUserHeadimg-succ] result=" + result.getResult());
                Toaster.show("上传成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "[saveUserHeadimg-err] code=" + code + " message=" + message);
            }
        });
    }

}
