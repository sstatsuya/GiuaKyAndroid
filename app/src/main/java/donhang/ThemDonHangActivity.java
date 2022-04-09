package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giuakyandroid.R;
import database.model.SanPham;

import java.util.ArrayList;

import donhang.model.AdapterDSSanPham;
import database.dbSanPham;

public class ThemDonHangActivity extends AppCompatActivity {

    ListView lsSanPham;
    TextView tvMaKhachHang, tvTongTien;
    EditText etNgayDatHang;
    Button btnChonKhachHang, btnChonMatHang, btnLuu, btnHuy;
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
        init();
        this.tvMaKhachHang = findViewById(R.id.tv_themdonhang_MaKhachHang);
        this.btnChonKhachHang = findViewById(R.id.btn_themdonhang_ChonKhachHang);
        this.etNgayDatHang = findViewById(R.id.et_themdonhang_NgayDatHang);
        this.btnChonMatHang = findViewById(R.id.btn_themdonhang_ChonMatHang);
        this.lsSanPham = findViewById(R.id.lv_themdonhang_danhsachmathang);
        this.tvTongTien = findViewById(R.id.tv_themdonhang_TongTien);
        this.btnHuy = findViewById(R.id.btn_themdonhang_huy);
        this.btnLuu = findViewById(R.id.btn_themdonhang_Luu);

        AdapterDSSanPham adapterDSSanPham = new AdapterDSSanPham(this, R.layout.layout_item_them_mat_hang, this.sanPhams);
        this.lsSanPham.setAdapter(adapterDSSanPham);
    }

    private void setEvent() {
        this.btnChonKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        this.btnChonMatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemDonHangActivity.this, ChonMatHangActivity.class);
                startActivity(intent);
            }
        });

        this.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemDonHangActivity.this, DonHangActivity.class);
                startActivity(intent);
            }
        });

        this.btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemDonHangActivity.this, DonHangActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init(){
        dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
        sanPhams.clear();
        sanPhams.addAll(dbSanPham.docDL());
    }
}