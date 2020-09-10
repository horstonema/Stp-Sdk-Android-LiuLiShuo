package com.aiedevice.sdkdemo.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.sdk.account.LoginManager;
import com.aiedevice.sdk.base.Base;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends StpBaseActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    // logic
    private LoginManager mLoginManager;

    // views
    @BindView(R.id.et_username)
    public EditText etPhone;
    @BindView(R.id.et_password)
    public EditText etPassword;
    @BindView(R.id.et_verify_code)
    public EditText etVerifyCode;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("注册");
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_register;
    }

    private void initLogic() {
        mLoginManager = new LoginManager(this);
    }

    @OnClick({R.id.btn_register, R.id.btn_send_code})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                register();
                break;

            case R.id.btn_send_code:
                sendVerifyCode();
                break;
        }
    }

    private void register() {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        String code = etVerifyCode.getText().toString();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(code)) {
            Toaster.show("注册信息不能为空");
            return;
        }
        mLoginManager.register(phone, password, code, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("注册成功");
                finish();
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("注册失败");
            }
        });

    }

    private void sendVerifyCode() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toaster.show("注册信息不能为空");
            return;
        }

        mLoginManager.sendValidCode(phone, Base.VCODE_USAGE_REGIST_PHONE, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Log.d(TAG, "resultSupport = " + resultSupport.getModel("data").toString());
                Toaster.show("发送成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("发送失败. 错误：" + message);
            }
        });
    }
}
