package com.hqumath.androidmvvm.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.databinding.ActivityMainBinding;
import com.hqumath.androidmvvm.ui.activitylist.ActivityListActivity;
import com.hqumath.androidmvvm.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView(){
        binding.setLifecycleOwner(this);
        binding.btnLogin.setOnClickListener(o -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
        binding.btnList.setOnClickListener(o -> {
            startActivity(new Intent(MainActivity.this, ActivityListActivity.class));
        });
    }
}
