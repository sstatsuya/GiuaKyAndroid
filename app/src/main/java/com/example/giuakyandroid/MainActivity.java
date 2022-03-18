package com.example.giuakyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Tạo ra brand mới", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Thêm 1 cái toast nữa", Toast.LENGTH_SHORT).show();
    }
}