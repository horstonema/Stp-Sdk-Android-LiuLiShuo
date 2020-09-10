package com.aiedevice.sdkdemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.aiedevice.basic.error.AuthFailureError;
import com.aiedevice.basic.error.HttpStatusError;
import com.aiedevice.basic.error.NetError;
import com.aiedevice.basic.error.NetworkError;
import com.aiedevice.basic.error.ParseError;
import com.aiedevice.basic.error.RequestError;
import com.aiedevice.basic.error.ServerError;
import com.aiedevice.basic.error.TimeoutError;
import com.aiedevice.sdkdemo.R;

public class ErrorUtils {
    public static int handleError(Context context, NetError error) {
        return handleError(context, error, "");
    }

    public static int handleError(Context context, NetError error, String additionalMsg) {

        int errCode = -1;
        String errMsg = "";
        if (context != null && error != null) {
            if (error instanceof AuthFailureError) {
                //登录超时操作
            }
            if (error instanceof TimeoutError) {
                errMsg = context.getString(R.string.timeout_error);
            } else if (error instanceof NetworkError) {
                errMsg = context.getString(R.string.no_connection_error);
            } else if (error instanceof ServerError) {
                errMsg = context.getString(R.string.common_server_error);
            } else if (error instanceof ParseError) {
                errMsg = context.getString(R.string.parse_error);
            } else if (error instanceof RequestError || error instanceof HttpStatusError) {
                HttpStatusError statusError = (HttpStatusError) error;
                errMsg = statusError.getMsg();
                errCode = statusError.getCode();
            } else if (error instanceof HttpStatusError) {
                errMsg = context.getString(R.string.status_code_error);
            } else {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    errMsg = error.getMessage();
                } else {
                    errMsg = context.getString(R.string.unknown_error);
                }
            }
            Toast.makeText(context, errMsg + additionalMsg, Toast.LENGTH_LONG).show();
        }
        return errCode;
    }
}
