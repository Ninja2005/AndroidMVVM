package com.hqumath.androidmvvm.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.hqumath.androidmvvm.databinding.ItemCommitBinding;
import com.hqumath.androidmvvm.databinding.ItemNetworkStateBinding;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.NetworkState;

/**
 * ****************************************************************
 * 文件名称: CommitListAdapter
 * 作    者: Created by gyd
 * 创建时间: 2019/6/28 14:36
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class CommitListAdapter extends PagedListAdapter<CommitEntity, RecyclerView.ViewHolder> {

    private static final int TYPE_PROGRESS = 0;//进度条
    private static final int TYPE_ITEM = 1;//ITEM

    private NetworkState networkState;
    private ClickCallback clickCallback;


    public CommitListAdapter(@NonNull ClickCallback clickCallback) {
        super(new DiffUtil.ItemCallback<CommitEntity>() {
            //这个是用来判断是否是一个对象的
            @Override
            public boolean areItemsTheSame(@NonNull CommitEntity oldItem, @NonNull CommitEntity newItem) {
                return oldItem.getNode_id().equals(newItem.getNode_id());
            }

            //这个是用来判断相同对象的内容是否相同 和UI展示的相同
            @Override
            public boolean areContentsTheSame(@NonNull CommitEntity oldItem, @NonNull CommitEntity newItem) {
                return oldItem.getCommit().getMessage().equals(newItem.getCommit().getMessage());
            }
        });
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_PROGRESS) {
            ItemNetworkStateBinding itemNetworkStateBinding = ItemNetworkStateBinding.inflate(layoutInflater, parent,
                    false);
            return new ItemNetworkStateHolder(itemNetworkStateBinding);

        } else {
            ItemCommitBinding itemCommitBinding = ItemCommitBinding.inflate(layoutInflater, parent, false);
            itemCommitBinding.setCallback(clickCallback);
            return new ItemCommitHolder(itemCommitBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemNetworkStateHolder) {
            ((ItemNetworkStateHolder) holder).binding.setNetwork(networkState);
        } else {
            ((ItemCommitHolder) holder).binding.setCommit(getItem(position));
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState.getStatus() != NetworkState.Status.SUCCESS) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public class ItemNetworkStateHolder extends RecyclerView.ViewHolder {
        private ItemNetworkStateBinding binding;

        private ItemNetworkStateHolder(ItemNetworkStateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class ItemCommitHolder extends RecyclerView.ViewHolder {
        private ItemCommitBinding binding;

        private ItemCommitHolder(ItemCommitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ClickCallback {
        void onItemClick(@NonNull CommitEntity data);
    }
}
