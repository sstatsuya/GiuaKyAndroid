package pthach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.tien.model.SanPham;

import java.util.ArrayList;

import pthach.model.AdapterDSSanPham;

public class ThemDonHangActivity extends AppCompatActivity {

    ListView lsSanPham;
    ArrayList<SanPham> sanPhams = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_don_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setControl() {
        this.lsSanPham = findViewById(R.id.lvSanPham);
        AdapterDSSanPham adapterDSSanPham =new AdapterDSSanPham(this, R.layout.layout_item_them_mat_hang, this.sanPhams);
        this.lsSanPham.setAdapter(adapterDSSanPham);
    }

    private void setEvent() {
        init();
    }

    public void init(){
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
}