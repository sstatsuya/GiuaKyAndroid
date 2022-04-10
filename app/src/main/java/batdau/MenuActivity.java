package batdau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.giuakyandroid.R;

import donhang.DonHangActivity;
import khachhang.KhachHangActivity;
import sanpham.SanPhamActivity;
import thongke.ThongKeActivity;

public class MenuActivity extends AppCompatActivity {
    LinearLayout btnSanPham,btnKhachHang, btnDonDatHang, btnThongKe;
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

        btnDonDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DonHangActivity.class);
                startActivity(intent);
            }
        });

        this.btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ThongKeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setControl() {
        btnSanPham = findViewById(R.id.btnSanPham);
        btnKhachHang = findViewById(R.id.btnKhachHang);
        this.btnDonDatHang = findViewById(R.id.btnDonDatHang);
        this.btnThongKe = findViewById(R.id.btnThongKe);
    }
}