package com.hqumath.androidmvvm.ui.activitylist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.databinding.ActivityItemBinding;
import com.hqumath.androidmvvm.entity.ActivityEntity;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: ActivityListAdapter
 * 作    者: Created by gyd
 * 创建时间: 2019/6/4 17:03
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.MyViewHolder> {
    private List<ActivityEntity> mList;

    private final ClickCallback clickCallback;

    public ActivityListAdapter(@NonNull ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
        setHasStableIds(true);
    }

    public void setData(List<ActivityEntity> list) {
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
                    return mList.get(oldItemPosition).getID() == list.get(newItemPosition).getID();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ActivityEntity oldItem = mList.get(oldItemPosition);
                    ActivityEntity newItem = list.get(newItemPosition);
                    return oldItem.getID() == newItem.getID()
                            && oldItem.getTitle().endsWith(newItem.getTitle())
                            && oldItem.getTimeStart().endsWith(newItem.getTimeStart())
                            && oldItem.getAddTime().endsWith(newItem.getAddTime());
                }
            });
            mList = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.activity_item, parent, false);
        binding.setCallback(clickCallback);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setActivity(mList.get(position));

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public interface ClickCallback {
        /**
         * 点击名单.
         *
         * @param data 活动信息.
         */
        void onPersonListClick(@NonNull ActivityEntity data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ActivityItemBinding binding;

        private MyViewHolder(ActivityItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

