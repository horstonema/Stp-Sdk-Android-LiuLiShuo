package com.aiedevice.sdkdemo.activity.resource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.adapter.ResourcePageAdapter;
import com.aiedevice.sdkdemo.bean.SearchDataBean;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.resource.ResourceManager;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends StpBaseActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();

    // logic
    private ResourceManager mResourceManager;
    private ResourcePageAdapter resourcePageAdapter;

    // view
    @BindView(R.id.et_search_keyword)
    public EditText mSearchKeyword;
    @BindView(R.id.rv_res_list)
    public RecyclerView mRvResList;

    public static void launch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("搜索资源");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_search;
    }

    @OnClick(R.id.btn_search)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                doSeach();
                break;
        }
    }

    private void doSeach() {
        String text = mSearchKeyword.getText().toString();
        mResourceManager.searchResource(text, 1, 1, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                SearchDataBean data = gson.fromJson(resultSupport.getModel("data").toString(), SearchDataBean.class);
                resourcePageAdapter.clearItems();
                resourcePageAdapter.setItems(data.getResources());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("搜索资源失败 错误：" + message);
            }
        });
    }

    private void initView() {
        mRvResList.setLayoutManager(new LinearLayoutManager(mContext));
        resourcePageAdapter = new ResourcePageAdapter(mContext);
        mRvResList.setAdapter(resourcePageAdapter);
    }

    private void initLogic() {
        mResourceManager = new ResourceManager(mContext);
    }
}
