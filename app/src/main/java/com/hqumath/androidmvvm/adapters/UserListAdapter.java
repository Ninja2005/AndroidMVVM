package com.hqumath.androidmvvm.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.databinding.ItemUserBinding;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.utils.Utils;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MyReposListAdapter
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 16:05
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private List<UserInfoEntity> mList;
    private ClickCallback clickCallback;

    public UserListAdapter(@NonNull ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public void setData(List<UserInfoEntity> list) {
        if (mList == null) {
            mList = list;
            notifyItemRangeInserted(0, list.size());
        } else {
            //增量刷新
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                @Override
                public int getOldListSize() {
                    return mList.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mList.get(oldItemPosition).getId() == list.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    UserInfoEntity oldItem = mList.get(oldItemPosition);
                    UserInfoEntity newItem = list.get(newItemPosition);
                    return oldItem.getLogin().equals(newItem.getLogin())
                            && oldItem.getAvatar_url().equals(newItem.getAvatar_url());
                }
            });
            mList = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_user, parent, false);
        binding.setCallback(clickCallback);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyViewHolder holder, int position) {
        UserInfoEntity data = mList.get(position);
        holder.binding.setData(data);
        if (!TextUtils.isEmpty(data.getAvatar_url())) {
            Glide.with(Utils.getContext()).load(data.getAvatar_url()).into(holder.binding.ivHead);
        }
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public interface ClickCallback {
        void onClick(@NonNull UserInfoEntity data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemUserBinding binding;

        private MyViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
