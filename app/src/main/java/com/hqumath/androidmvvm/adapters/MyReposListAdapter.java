package com.hqumath.androidmvvm.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.databinding.ItemMyreposBinding;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.utils.StringUtils;

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
public class MyReposListAdapter extends RecyclerView.Adapter<MyReposListAdapter.MyViewHolder> {

    private List<ReposEntity> mList;
    private ClickCallback clickCallback;

    public MyReposListAdapter(@NonNull ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public void setData(List<ReposEntity> list) {
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
                    ReposEntity oldItem = mList.get(oldItemPosition);
                    ReposEntity newItem = list.get(newItemPosition);
                    return StringUtils.equals(oldItem.getName(), newItem.getName())
                            && StringUtils.equals(oldItem.getDescription(), newItem.getDescription())
                            && StringUtils.equals(oldItem.getOwner().getLogin(), newItem.getOwner().getLogin());
                }
            });
            mList = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public MyReposListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyreposBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_myrepos, parent, false);
        binding.setCallback(clickCallback);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReposListAdapter.MyViewHolder holder, int position) {
        ReposEntity data = mList.get(position);
        holder.binding.setData(data);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public interface ClickCallback {
        void onClick(@NonNull ReposEntity data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemMyreposBinding binding;

        private MyViewHolder(ItemMyreposBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
