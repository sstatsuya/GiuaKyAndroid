package com.example.giuakyandroid.tien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.giuakyandroid.R;

public class SanPhamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
    }
}