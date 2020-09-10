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

public class ForgetPasswordActivity extends StpBaseActivity {
    private static final String TAG = ForgetPasswordActivity.class.getSimpleName();

    // logic
    private LoginManager mLoginManager;

    // views
    @BindView(R.id.et_username)
    public EditText etPhone;
    @BindView(R.id.et_verify_code)
    public EditText etVerifyCode;
    @BindView(R.id.et_new_passwd)
    public EditText etNewPassword;

    public static void launch(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("重置密码");
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reset_password;
    }

    private void initLogic() {
        mLoginManager = new LoginManager(mContext);
    }

    @OnClick({R.id.btn_send_code, R.id.btn_reset_passwd})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                sendCode();
                break;

            case R.id.btn_reset_passwd:
                reset();
                break;
        }
    }

    private void sendCode() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toaster.show("信息不能为空");
            return;
        }

        mLoginManager.sendValidCode(phone, Base.VCODE_USAGE_PASSWORD, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Log.d(TAG, "resultSupport = " + resultSupport.getModel("data"));
                Toaster.show("发送成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("发送失败. 错误：" + message);
            }
        });
    }

    private void reset() {
        String phone = etPhone.getText().toString();
        String code = etVerifyCode.getText().toString();
        String newPass = etNewPassword.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) || TextUtils.isEmpty(newPass)) {
            Toaster.show("信息不能为空");
            return;
        }

        mLoginManager.resetPwd(phone, code, newPass, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                if (resultSupport.getResult() == 0) {
                    Toaster.show("密码重置成功");
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("密码重置失败 错误:" + message);
            }
        });
    }
}
