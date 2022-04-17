package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.DBDonHang;
import database.model.DonHang;
import database.model.SanPham;
import database.model.SanPhamDonHang;
import donhang.model.AdapterSanPham;
import donhang.model.AdapterThemDonHangDSSanPham;

public class ThongTinDonHangActivity extends AppCompatActivity {
    TextView tvMaDonHang, tvTenKhachHang, tvNgayDatHang, tvTongTien;
    ListView lvSanPhamDonHang;
    Button btnThoat, btnSua;

    AdapterSanPham adapterSanPham;
    DBDonHang dbDonHang;

    ArrayList<SanPhamDonHang> sanPhamDonHangs;
    DonHang donHang;
    int maDH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_don_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        //get MaDH send from preActivities
        maDH = getIntent().getIntExtra("madonhang", 0);
        //generate database connector
        dbDonHang = new DBDonHang(getApplicationContext());

        generate();
        setControl();
        setEvent();
    }

    private void generate() {
        //get DonHang detail from database
        this.donHang = dbDonHang.get(maDH);
        //Set list of SanPhamMatHang
        this.sanPhamDonHangs = this.donHang.getSanPhamDonHangs();
//        this.sanPhamDonHangs.clear();
//        this.sanPhamDonHangs.addAll(this.donHang.getSanPhamDonHangs());
    }

    //link from java variable to Layout
    private void setControl() {
        this.tvMaDonHang = findViewById(R.id.tv_thongtindonhang_madonhang);
        this.tvTenKhachHang = findViewById(R.id.tv_thongtindonhang_tenkhachhang);
        this.tvNgayDatHang = findViewById(R.id.tv_thongtindonhang_ngaydathang);
        this.lvSanPhamDonHang = findViewById(R.id.lv_ttdh_dssp);
        this.tvTongTien = findViewById(R.id.tv_thongtindonhang_TongTien);
//        this.btnSua = findViewById(R.id.btn_thongtindonhang_sua);
        this.btnThoat = findViewById(R.id.btn_thongtindonhang_thoat);

        this.tvMaDonHang.setText("Mã đơn hàng: " + String.valueOf(this.donHang.getMaDH()));
        this.tvTenKhachHang.setText("Tên khách hàng: " + String.valueOf(this.donHang.getTenKH()));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.tvNgayDatHang.setText(df.format(this.donHang.getNgayDatHang()));
        this.tvTongTien.setText(String.valueOf(calculatorTongTien()) + "vnd");
        //Generate list of SanPhamDonHang
        this.adapterSanPham = new AdapterSanPham(this, R.layout.layout_ttdh_sanpham, this.sanPhamDonHangs);
        this.lvSanPhamDonHang.setAdapter(adapterSanPham);
    }


    private void setEvent() {
        this.btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(0);
                finish();
            }
        });
        
//        this.btnSua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ThongTinDonHangActivity.this, "Tính năng này chưa phát triển", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private double calculatorTongTien() {
        double temp = 0.0;
        for (SanPhamDonHang i : sanPhamDonHangs) {
            temp += i.getDonGia() * i.getSoLuong();
        }
        return temp;
    }



}