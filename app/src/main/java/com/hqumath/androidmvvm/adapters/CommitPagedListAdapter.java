package com.hqumath.androidmvvm.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.databinding.ItemCommitPagingBinding;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.utils.StringUtils;

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
public class CommitPagedListAdapter extends PagedListAdapter<CommitEntity, CommitPagedListAdapter.MyViewHolder> {

    private ClickCallback clickCallback;

    public CommitPagedListAdapter(@NonNull ClickCallback clickCallback) {
        super(new DiffUtil.ItemCallback<CommitEntity>() {
            //这个是用来判断是否是一个对象的
            @Override
            public boolean areItemsTheSame(@NonNull CommitEntity oldItem, @NonNull CommitEntity newItem) {
                return oldItem.getSha().equals(newItem.getSha());
            }

            //这个是用来判断相同对象的内容是否相同 和UI展示的相同
            @Override
            public boolean areContentsTheSame(@NonNull CommitEntity oldItem, @NonNull CommitEntity newItem) {
                return StringUtils.equals(oldItem.getCommit().getCommitter().getName(),
                        newItem.getCommit().getCommitter().getName())
                        && StringUtils.equals(oldItem.getCommit().getMessage(), newItem.getCommit().getMessage());
            }
        });
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommitPagingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_commit_paging, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setData(getItem(position));
        holder.binding.setCallback(clickCallback);
        holder.binding.executePendingBindings();
    }

    public interface ClickCallback {
        void onClick(@NonNull CommitEntity data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemCommitPagingBinding binding;

        private MyViewHolder(ItemCommitPagingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
