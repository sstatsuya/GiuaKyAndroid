package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giuakyandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.DBDonHang;
import database.model.DonHang;
import database.model.SanPhamDonHang;
import donhang.model.AdapterSanPham;
import donhang.model.AdapterThemDonHangDSSanPham;

public class ThongTinDonHangActivity extends AppCompatActivity {

    TextView tvMaDonHang, tvTenKhachHang, tvNgayDatHang;
    ListView lvSanPhamDonHang;

    AdapterSanPham adapterSanPham;
    DBDonHang dbDonHang;

    ArrayList<SanPhamDonHang> sanPhamDonHangs;
    DonHang donHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_don_hang);

        int maDH = getIntent().getIntExtra("madonhang", 0);

//        System.out.println("---------------------------------" + maDH);
        dbDonHang = new DBDonHang(getApplicationContext());
        this.donHang = dbDonHang.get(maDH);

        setControl();
        setEvent();
    }



    private void setControl() {
        this.tvMaDonHang = findViewById(R.id.tv_thongtindonhang_madonhang);
        this.tvTenKhachHang = findViewById(R.id.tv_thongtindonhang_tenkhachhang);
        this.tvNgayDatHang = findViewById(R.id.tv_thongtindonhang_ngaydathang);
        this.lvSanPhamDonHang = findViewById(R.id.lv_ttdh_dssp);

        this.tvMaDonHang.setText("Mã đơn hàng: " + String.valueOf(this.donHang.getMaDH()));
        this.tvTenKhachHang.setText("Tên khách hàng: " + String.valueOf(this.donHang.getTenKH()));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.tvNgayDatHang.setText(df.format(this.donHang.getNgayDatHang()));

        try{
            this.sanPhamDonHangs = this.donHang.getSanPhamDonHangs();
            this.sanPhamDonHangs.forEach(System.out::println);
            this.adapterSanPham = new AdapterSanPham(this, R.layout.layout_ttdh_sanpham, this.sanPhamDonHangs);
            this.lvSanPhamDonHang.setAdapter(adapterSanPham);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setEvent() {
    }
}