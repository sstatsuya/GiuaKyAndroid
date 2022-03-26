package com.example.giuakyandroid.tien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import ngocthach.KhachHangActivity;

public class MenuActivity extends AppCompatActivity {
    LinearLayout btnSanPham,btnKhachHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SanPhamActivity.class);
                startActivity(intent);
            }
        });
        btnKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, KhachHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnSanPham = findViewById(R.id.btnSanPham);
        btnKhachHang = findViewById(R.id.btnKhachHang);
    }
}