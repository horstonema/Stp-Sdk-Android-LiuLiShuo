package com.aiedevice.sdkdemo.activity.resource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.adapter.CategoryDividerItemDecoration;
import com.aiedevice.sdkdemo.adapter.ModuleListAdapter;
import com.aiedevice.sdkdemo.bean.ModuleDetail;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.resource.ResourceManager;

import butterknife.BindView;

public class ModuleListActivity extends StpBaseActivity {
    private static final String TAG = ModuleListActivity.class.getSimpleName();

    // logic
    private ResourceManager mResourceManager;
    private ModuleListAdapter mModAdapter;

    // view
    @BindView(R.id.rv_module_list)
    public RecyclerView mRvModList;

    public static void launch(Context context) {
        Intent intent = new Intent(context, ModuleListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("分类列表");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_module_list;
    }

    private void initView() {
        mModAdapter = new ModuleListAdapter(this);
        mRvModList.setAdapter(mModAdapter);
        mRvModList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initLogic() {
        mResourceManager = new ResourceManager(this);
        mRvModList.addItemDecoration(new CategoryDividerItemDecoration(this,
                CategoryDividerItemDecoration.VERTICAL_LIST));

        refreshModList();
    }

    private void refreshModList() {
        mResourceManager.getCategoryList(0, new ResultListener() {

            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                ModuleDetail datail = gson.fromJson(resultSupport.getModel("data").toString(), ModuleDetail.class);
                Log.d(TAG, "moudle count = " + datail.getTotal());
                mModAdapter.setItems(datail.getModules());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取列表失败 错误：" + message);
            }
        });
    }
}
