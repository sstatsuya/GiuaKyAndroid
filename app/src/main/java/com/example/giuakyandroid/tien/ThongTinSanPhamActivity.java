package com.example.giuakyandroid.tien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.tien.model.SanPham;
import com.squareup.picasso.Picasso;

public class ThongTinSanPhamActivity extends AppCompatActivity {
    SanPham sanPham;
    TextView txtTTSPMa, txtTTSPTen, txtTTSPGia, txtTTSPXuatXu;
    ImageView imgTTSPHinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_san_pham);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setEvent() {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(0, intent);
        finish();
    }

    private void setControl() {
        getDuLieu();
        txtTTSPMa = findViewById(R.id.txtTTSPMa);
        txtTTSPTen = findViewById(R.id.txtTTSPTen);
        txtTTSPGia = findViewById(R.id.txtTTSPGia);
        txtTTSPXuatXu = findViewById(R.id.txtTTSPXuatXu);
        imgTTSPHinh = findViewById(R.id.imgTTSPHinh);
        txtTTSPMa.setText(sanPham.getMaSP());
        txtTTSPTen.setText(sanPham.getTenSP());
        txtTTSPXuatXu.setText(sanPham.getXuatXu());
        txtTTSPGia.setText(sanPham.getDonGia().toString());
        Picasso.get().load(sanPham.getLinkHinhAnh()).into(imgTTSPHinh);
    }

    private void getDuLieu() {
        sanPham = (SanPham) getIntent().getSerializableExtra("sanPham");
    }


}