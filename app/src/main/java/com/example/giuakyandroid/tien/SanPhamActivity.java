package com.example.giuakyandroid.tien;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.tien.model.AdapterSanPham;
import com.example.giuakyandroid.tien.model.SanPham;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SanPhamActivity extends AppCompatActivity {
    GridView gvSanPham;
    ArrayList<SanPham> sanPhams = new ArrayList<>();
    AdapterSanPham adapterSanPham;
    LinearLayout btnThemSanPham;
    LinearLayout ll_loc_sp;
    Spinner sn_loc_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        adapterSanPham = new AdapterSanPham(this, R.layout.layout_san_pham, sanPhams);
        gvSanPham.setAdapter(adapterSanPham);

//        Khi sản phẩm được click vào
        gvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SanPhamActivity.this, ThongTinSanPhamActivity.class);
                SanPham sanPham = sanPhams.get(position);
                intent.putExtra("sanPham", sanPham);
                startActivityForResult(intent, 1);
            }
        });

//        Khi bấm nút thêm sản phẩm
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SanPhamActivity.this, ThemSanPhamActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //Khi bấm vào loc
        sn_loc_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                Toast.makeText(SanPhamActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setControl() {
        gvSanPham = findViewById(R.id.gvSanPham);
        btnThemSanPham = findViewById(R.id.btnThemSanPham);
//        ll_loc_sp = findViewById(R.id.ll_loc_sp);
        sn_loc_sp = findViewById(R.id.sn_loc_sp);
        khoiTaoSpinnerLoc();
    }

    private void khoiTaoSpinnerLoc() {
        ArrayList<String> kieuLoc = new ArrayList<>();
        kieuLoc.add("Lọc theo mã tăng dần");
        kieuLoc.add("Lọc theo mã giảm dần");
        kieuLoc.add("Lọc theo tên tăng dần");
        kieuLoc.add("Lọc theo tên giảm dần");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, kieuLoc);
        sn_loc_sp.setAdapter(arrayAdapter);
        sn_loc_sp.setSelection(0);

    }

    private void khoiTao() {
        SanPham sanPham1 = new SanPham("SP001", "Bia Tiger thùng 24 lon", "Đức", 320000, "https://grovefresh.vn/Data/Sites/1/Product/7114/0005631_thung-24-bia-tiger-nau-330ml.png");
        SanPham sanPham2 = new SanPham("SP002", "Thịt heo", "Việt Nam", 320000, "https://cafefcdn.com/thumb_w/650/203337114487263232/2021/9/28/photo1632798710845-1632798710943181451693.png");
        SanPham sanPham3 = new SanPham("SP003", "Lốc 4 hộp sữa tươi Vinamilk", "Hà Lan", 320000, "https://cdn.tgdd.vn/Products/Images/2386/80483/bhx/loc-4-hop-sua-tuoi-tiet-trung-khong-duong-vinamilk-100-sua-tuoi-180ml-202104091038151238.jpg");
        SanPham sanPham4 = new SanPham("SP004", "Bia Tiger thùng 24 lon", "Việt Nam", 320000, "https://cf.shopee.vn/file/91a7824459296b99f40caa4d1bc7a619");
        sanPhams.add(sanPham1);
        sanPhams.add(sanPham2);
        sanPhams.add(sanPham3);
        sanPhams.add(sanPham4);
        sanPhams.add(sanPham1);
        sanPhams.add(sanPham2);
        sanPhams.add(sanPham3);
        sanPhams.add(sanPham4);
    }

    private void themSanPham(SanPham sanPham){
        sanPhams.add(sanPham);
        adapterSanPham.notifyDataSetChanged(); //Reload adapter
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SanPham sanPham = (SanPham) data.getSerializableExtra("sanPham");
        if (requestCode == 1) {
            if (resultCode == 1) {
                themSanPham(sanPham);
            }

        } else if (requestCode == 2) {
            if (resultCode == 2) {
                Toast.makeText(this, "Sửa", Toast.LENGTH_SHORT).show();
            } else if (resultCode == 1) {
                Toast.makeText(this, "Xóa", Toast.LENGTH_SHORT).show();
            }
        }
    }



}