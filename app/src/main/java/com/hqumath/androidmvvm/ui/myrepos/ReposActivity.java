package com.hqumath.androidmvvm.ui.myrepos;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelActivity;
import com.hqumath.androidmvvm.databinding.ActivityReposBinding;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.utils.StringUtils;

import java.util.Locale;

public class ReposActivity extends BaseViewModelActivity<ActivityReposBinding, ReposViewModel> {


    @Override
    public ReposViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ReposViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_repos;
    }

    @Override
    public void initView() {
        setSupportActionBar(binding.toolbar);

        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData());

        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initData() {
        //data
        String avatar_url = getIntent().getStringExtra("avatar_url");
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String full_name = getIntent().getStringExtra("full_name");
        String created_at = getIntent().getStringExtra("created_at");
        String language = getIntent().getStringExtra("language");
        int size = getIntent().getIntExtra("size",0);
        language = String.format(Locale.getDefault(), "Language %s, size %s",
                language, StringUtils.getSizeString(size * 1024));
        ReposEntity reposEntity = new ReposEntity();
        reposEntity.setArchive_url(avatar_url);
        reposEntity.setName(name);
        reposEntity.setDescription(description);
        reposEntity.setFull_name(full_name);
        reposEntity.setCreated_at(created_at);
        reposEntity.setLanguage(language);
        viewModel.data = reposEntity;
        //ui
        setTitle(name);
        binding.setViewModel(viewModel);
        Glide.with(mContext).load(avatar_url).into(binding.ivAvatarBg);

//        viewModel.getData();
//        binding.swipeRefreshLayout.setRefreshing(true);
    }

    public void initViewObservable() {
        viewModel.isLoading.observe(this, b -> {
            if (!b) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        /*viewModel.data.getArchive_url().observe(this, url -> {
            Glide.with(mContext).load(url).into(binding.ivAvatarBg);
            Glide.with(Utils.getContext())
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))//圆形
                    .into(binding.ivAvatar);
        });*/
    }


}
