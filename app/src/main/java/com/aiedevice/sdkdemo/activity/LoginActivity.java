package com.aiedevice.sdkdemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.jssdk.StpSDK;
import com.aiedevice.sdk.SDKConfig;
import com.aiedevice.sdk.SharedPreferencesUtil;
import com.aiedevice.sdk.account.AccountManager;
import com.aiedevice.sdk.account.LoginManager;
import com.aiedevice.sdk.account.bean.LoginData;
import com.aiedevice.sdk.bean.GlobalVars;
import com.aiedevice.sdkdemo.AppConstant;
import com.aiedevice.sdkdemo.BuildConfig;
import com.aiedevice.sdkdemo.R;
import com.aiedevice.sdkdemo.activity.account.ForgetPasswordActivity;
import com.aiedevice.sdkdemo.activity.account.RegisterActivity;
import com.aiedevice.sdkdemo.utils.PermissionHelper;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends StpBaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    // logic
    private PermissionHelper permissionHelper;
    private LoginManager mLoginManager;
    private AccountManager mAccountManager;

    // views
    @BindView(R.id.et_username)
    public EditText etUsername;
    @BindView(R.id.et_password)
    public EditText etPassword;
    @BindView(R.id.tv_sdk_version)
    public TextView tvSdkVersion;
    @BindView(R.id.rb_online)
    RadioButton rbOnline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("登录");
        initLogic();
        initView();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_login;
    }

    private void chooseEnvironment(int envType) {
        Log.d(TAG, "[chooseEnvironment] envType=" + envType);
        switch (envType) {
            case SDKConfig.ONLINE_ENV:
                SDKConfig.chooseEnvCfg(SDKConfig.ONLINE_ENV);
                break;
            case SDKConfig.TEST_ENV:
                SDKConfig.chooseEnvCfg(SDKConfig.TEST_ENV);
                break;
        }
    }

    private void initView() {
        String text = "APP Version: " + BuildConfig.VERSION_NAME + "\n";
        text += "SDK Version:" + StpSDK.getInstance().getVersionName();
        tvSdkVersion.setText(text);

        loadLoginInfo();
    }

    private void initLogic() {
        // 初始化SDK
        initStpSDK();

        mLoginManager = new LoginManager(mContext);
        mAccountManager = new AccountManager(mContext);

        //申请APP所需各种权限
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
        permissionHelper.requestCameraAndMicrophonePermission(perReqCallback);
        permissionHelper.requestPhonePermission(perReqCallback);
        permissionHelper.requestSmsPermission(perReqCallback);
        permissionHelper.requestStoragePermission(perReqCallback);
    }

    private void initStpSDK() {
        StpSDK.getInstance().init(this, AppConstant.PACKAGE_ID);
    }

    @OnClick({R.id.et_username, R.id.et_password, R.id.tv_register, R.id.tv_forget_passwd, R.id.btn_login, R.id.btn_loginex,
            R.id.rb_online, R.id.rb_test})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                RegisterActivity.launch(LoginActivity.this);
                break;

            case R.id.tv_forget_passwd:
                ForgetPasswordActivity.launch(LoginActivity.this);
                break;

            case R.id.btn_login:
                String username = etUsername.getText().toString().trim();
                String passwd = etPassword.getText().toString().trim();
                login(username, passwd);
                break;

            case R.id.btn_loginex:
                String userid = etUsername.getText().toString().trim();
                String thirdToken = etPassword.getText().toString().trim();
                loginex(userid, thirdToken);
                break;

            case R.id.rb_online:
                chooseEnvironment(SDKConfig.ONLINE_ENV);
                break;

            case R.id.rb_test:
                chooseEnvironment(SDKConfig.TEST_ENV);
                break;
        }
    }

    private void login(final String phone, final String password) {
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            Toaster.show("输入信息不能为空");
            return;
        }

        Log.d(TAG, "[login] phone=" + phone + " password=" + password);
        mLoginManager.login(phone, password, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                try {
                    saveLogininfo(phone, password);

                    Gson gson = GsonUtils.getGson();
                    LoginData loginData = gson.fromJson(resultSupport.getData(), LoginData.class);
                    mAccountManager.setUserInfo(loginData.getUserId(), loginData.getToken());
                    ///////////////////////////////////////////////////////////////////////////////////////
                    //设置推送token，若不用推送服务，可省略
                    GlobalVars.setLoginStatus(true);
                    if (!TextUtils.isEmpty(GlobalVars.getPushToken())) {
                        new AccountManager((mContext)).setPushToken(GlobalVars.getPushToken(), new ResultListener() {
                            @Override
                            public void onSuccess(ResultSupport result) {
                                Log.d(TAG, "[setPushToken-succ] result=" + result.getResult() + " pushToken=" + GlobalVars.getPushToken());
                            }

                            @Override
                            public void onError(int code, String message) {
                                Log.d(TAG, "[setPushToken-fail] code=" + code + " message=" + message);
                            }
                        });
                    }
                    ////////////////////////////////////////////////////////////////////////////////////////
                    MainActivity.launch(mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show(getString(R.string.login_activity_login_failed));
            }
        });
    }

    private void loginex(final String userid, final String thirdCode) {
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(thirdCode)) {
            Toaster.show("输入信息不能为空");
            return;
        }

        Log.d(TAG, "[loginex] userid=" + userid + " thirdcode=" + thirdCode);
        String pushid = "";
        mLoginManager.loginEx(userid, thirdCode, pushid, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                try {
                    Gson gson = GsonUtils.getGson();
                    LoginData loginData = gson.fromJson(resultSupport.getData(), LoginData.class);
                    mAccountManager.setUserInfo(loginData.getUserId(), loginData.getToken());
                    ///////////////////////////////////////////////////////////////////////////////////////
                    //设置推送token，若不用推送服务，可省略
                    GlobalVars.setLoginStatus(true);
                    if (!TextUtils.isEmpty(GlobalVars.getPushToken())) {
                        new AccountManager((mContext)).setPushToken(GlobalVars.getPushToken(), new ResultListener() {
                            @Override
                            public void onSuccess(ResultSupport result) {
                                Log.d(TAG, "[setPushToken-succ] result=" + result.getResult() + " pushToken=" + GlobalVars.getPushToken());
                            }

                            @Override
                            public void onError(int code, String message) {
                                Log.d(TAG, "[setPushToken-fail] code=" + code + " message=" + message);
                            }
                        });
                    }
                    ////////////////////////////////////////////////////////////////////////////////////////
                    MainActivity.launch(mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show(getString(R.string.login_activity_login_failed));
            }
        });
    }

    private void saveLogininfo(String phone, String passwd) {
        SharedPreferencesUtil.setStringValue("login-phone", phone);
        SharedPreferencesUtil.setStringValue("login-passwd", passwd);
    }

    private void loadLoginInfo() {
        String phone = SharedPreferencesUtil.getStringValue("login-phone", "");
        String passwd = SharedPreferencesUtil.getStringValue("login-passwd", "");
        etUsername.setText(phone);
        etPassword.setText(passwd);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
