package com.hqumath.androidmvvm.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.databinding.ItemNetworkStateBinding;
import com.hqumath.androidmvvm.databinding.ItemUserPagingBinding;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.utils.StringUtil;
import com.hqumath.androidmvvm.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: UserPagedListAdapter
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 16:05
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class UserPagedListAdapter extends PagedListAdapter<UserInfoEntity, RecyclerView.ViewHolder> {

    private ClickCallback clickCallback;
    private NetworkState networkState = null;

    public UserPagedListAdapter(@NonNull ClickCallback clickCallback) {
        super(new DiffUtil.ItemCallback<UserInfoEntity>() {
            //这个是用来判断是否是一个对象的
            @Override
            public boolean areItemsTheSame(@NonNull UserInfoEntity oldItem, @NonNull UserInfoEntity newItem) {
                return oldItem.getId() == (newItem.getId());
            }

            //这个是用来判断相同对象的内容是否相同 和UI展示的相同
            @Override
            public boolean areContentsTheSame(@NonNull UserInfoEntity oldItem, @NonNull UserInfoEntity newItem) {
                return StringUtil.equals(oldItem.getLogin(), newItem.getLogin())
                        && StringUtil.equals(oldItem.getAvatar_url(), newItem.getAvatar_url());
            }
        });
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == R.layout.item_network_state) {
            return new NetworkStateItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_network_state, parent, false));
        } else {
            return new MyViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_user_paging, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NetworkStateItemViewHolder) {
            ((NetworkStateItemViewHolder) holder).binding.setData(networkState);
            ((NetworkStateItemViewHolder) holder).binding.btnRetry.setOnClickListener(v -> clickCallback.onRetry());
        } else {
            UserInfoEntity data = getItem(position);
            ((MyViewHolder) holder).binding.setData(data);
            ((MyViewHolder) holder).binding.setCallback(clickCallback);
            if (data != null && !TextUtils.isEmpty(data.getAvatar_url())) {
                Glide.with(CommonUtil.getContext())
                        .load(data.getAvatar_url())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))//圆形
                        .into(((MyViewHolder) holder).binding.ivHead);
            }
        }
    }

    /**
     * true 加载中或失败
     * false 加载成功
     *
     * @return 是否有其他行
     */
    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.item_network_state;
        } else {
            return R.layout.item_user_paging;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean hadExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {
        private ItemNetworkStateBinding binding;

        private NetworkStateItemViewHolder(ItemNetworkStateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemUserPagingBinding binding;

        private MyViewHolder(ItemUserPagingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ClickCallback {
        void onClick(@NonNull UserInfoEntity data);

        void onRetry();
    }
}
