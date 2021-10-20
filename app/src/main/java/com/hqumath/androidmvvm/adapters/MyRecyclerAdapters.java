package com.hqumath.androidmvvm.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseRecyclerAdapter;
import com.hqumath.androidmvvm.base.BaseRecyclerViewHolder;
import com.hqumath.androidmvvm.bean.ReposEntity;
import com.hqumath.androidmvvm.bean.UserInfoEntity;
import com.hqumath.androidmvvm.utils.CommonUtil;

import java.util.List;

public class MyRecyclerAdapters {

    //我的跟随
    public static class FollowRecyclerAdapter extends BaseRecyclerAdapter<UserInfoEntity> {
        public FollowRecyclerAdapter(Context context, List<UserInfoEntity> mData) {
            super(context, mData, R.layout.recycler_item_followers);
        }

        @Override
        public void convert(BaseRecyclerViewHolder holder, int position) {
            UserInfoEntity data = mData.get(position);
            holder.setText(R.id.tv_name, data.getLogin());
            ImageView ivHead = holder.getView(R.id.iv_head);
            if (!TextUtils.isEmpty(data.getAvatar_url())) {
                Glide.with(CommonUtil.getContext())
                        .load(data.getAvatar_url())
                        .circleCrop()
                        .into(ivHead);
            }
        }
    }

    //我的仓库
    public static class ReposRecyclerAdapter extends BaseRecyclerAdapter<ReposEntity> {
        public ReposRecyclerAdapter(Context context, List<ReposEntity> mData) {
            super(context, mData, R.layout.recycler_item_repos);
        }

        @Override
        public void convert(BaseRecyclerViewHolder holder, int position) {
            ReposEntity data = mData.get(position);
            holder.setText(R.id.tv_name, data.getName());
            holder.setText(R.id.tv_description, data.getDescription());
            holder.setText(R.id.tv_author, data.getOwner().getLogin());
        }
    }
}


