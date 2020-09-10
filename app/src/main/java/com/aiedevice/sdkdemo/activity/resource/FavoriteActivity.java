package com.aiedevice.sdkdemo.activity.resource;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aiedevice.sdkdemo.AppConstant;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.adapter.FavoritePageAdapter;
import com.aiedevice.sdkdemo.bean.CollectionReponseData;
import com.aiedevice.sdkdemo.bean.CollectionResponse;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.resource.ResourceManager;

import java.util.ArrayList;

import butterknife.BindView;

public class FavoriteActivity extends StpBaseActivity {
    private static final String TAG = FavoriteActivity.class.getSimpleName();

    // logic
    private FavoritePageAdapter mFavResAdapter;
    private FavoritePageAdapter mFavAlbumAdapter;
    private ResourceManager mResourceManager;

    // view
    @BindView(R.id.rv_album_list)
    public RecyclerView mRvAlbumList;
    @BindView(R.id.rv_res_list)
    public RecyclerView mRvResList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("收藏页面");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.favorite_index_layout;
    }

    private void initView() {
        //init resource list
        mRvResList.setLayoutManager(new LinearLayoutManager(mContext));
        mFavResAdapter = new FavoritePageAdapter(mContext);
        mRvResList.setAdapter(mFavResAdapter);
        mFavResAdapter.setOnPlayOnClickListener(new FavoritePageAdapter.onPlayOnClickListener() {
            @Override
            public void delFavorite(CollectionResponse detail) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(detail.getId());
                FavoriteActivity.this.delFavorite(list, 1);
            }
        });

        //init album list
        mRvAlbumList.setLayoutManager(new LinearLayoutManager(mContext));
        mFavAlbumAdapter = new FavoritePageAdapter(mContext);
        mRvAlbumList.setAdapter(mFavAlbumAdapter);
        mFavAlbumAdapter.setOnPlayOnClickListener(new FavoritePageAdapter.onPlayOnClickListener() {
            @Override
            public void delFavorite(CollectionResponse detail) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(detail.getId());
                FavoriteActivity.this.delFavorite(list, 2);
            }
        });
    }

    private void initLogic() {
        mResourceManager = new ResourceManager(this);
        refreshSingleFavList();
        refreshAlbumFavList();
    }

    private void refreshSingleFavList() {
        mResourceManager.getCollectionList(AppConstant.FAVORITE_TYPE_SINGLE, 1, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                CollectionReponseData data = gson.fromJson(resultSupport.getModel("data").toString(), CollectionReponseData.class);
                mFavResAdapter.setItems(data.getList());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取失败 错误:" + message);
            }
        });
    }

    private void refreshAlbumFavList() {
        mResourceManager.getCollectionList(AppConstant.FAVORITE_TYPE_ALBUM, 1, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                CollectionReponseData data = gson.fromJson(resultSupport.getModel("data").toString(), CollectionReponseData.class);
                mFavAlbumAdapter.setItems(data.getList());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取失败 错误：" + message);
            }
        });
    }

    private void delFavorite(final ArrayList<Integer> ids, final int type) {
        mResourceManager.deleteCollection(ids, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("取消收藏成功");
                if (type == 1) {
                    refreshSingleFavList();
                } else {
                    refreshAlbumFavList();
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("取消收藏失败 错误:" + message);
            }
        });
    }
}
