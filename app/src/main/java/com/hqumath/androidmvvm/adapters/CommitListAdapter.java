package com.hqumath.androidmvvm.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.databinding.ItemCommitBinding;
import com.hqumath.androidmvvm.entity.CommitEntity;
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
public class CommitListAdapter extends RecyclerView.Adapter<CommitListAdapter.MyViewHolder> {

    private List<CommitEntity> mList;
    private ClickCallback clickCallback;

    public CommitListAdapter(@NonNull ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public void setData(List<CommitEntity> list) {
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
                    return mList.get(oldItemPosition).getSha().equals(list.get(newItemPosition).getSha());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CommitEntity oldItem = mList.get(oldItemPosition);
                    CommitEntity newItem = list.get(newItemPosition);
                    return StringUtils.equals(oldItem.getCommit().getCommitter().getName(), newItem.getCommit().getCommitter().getName())
                            && StringUtils.equals(oldItem.getCommit().getMessage(), newItem.getCommit().getMessage());
                }
            });
            mList = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public CommitListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommitBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_commit, parent, false);
        binding.setCallback(clickCallback);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitListAdapter.MyViewHolder holder, int position) {
        CommitEntity data = mList.get(position);
        holder.binding.setData(data);
        holder.binding.tvTime.setText(data.getCommit().getCommitter().getDate()
                .replace("T", " ").replace("Z", ""));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public interface ClickCallback {
        void onClick(@NonNull CommitEntity data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemCommitBinding binding;

        private MyViewHolder(ItemCommitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
