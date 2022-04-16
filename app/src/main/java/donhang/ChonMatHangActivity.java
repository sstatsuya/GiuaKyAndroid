package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import database.DBSanPham;
import database.model.SanPham;
import database.model.SanPhamDonHang;
import donhang.model.AdapterChonMatHang;

public class ChonMatHangActivity extends AppCompatActivity {
    ArrayList<SanPham> matHangs = new ArrayList<>();

    ListView lvMatHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mat_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();

//        DBDonHang dbDonHang = new DBDonHang(getApplicationContext());
//        System.out.println("GetAll");
//        ArrayList<DonHang> donHangs = dbDonHang.getAll();
//        donHangs.forEach(System.out::println);
//        System.out.println("Insert");
//        ArrayList<SanPhamDonHang> dsSanPham = new ArrayList<SanPhamDonHang>();
//        dsSanPham.add(new SanPhamDonHang(1, 24));
//        dsSanPham.add(new SanPhamDonHang(2, 12));
//        int n = dbDonHang.insert(new DonHang(1,2, new Date(), dsSanPham));
//        System.out.println(n);
//        System.out.println("GetAll");
//        donHangs.clear();
//        donHangs = dbDonHang.getAll();
//        donHangs.forEach(System.out::println);
//        System.out.println("GetAll ThongTinDatHang");
//        new DBThongTinDatHang(getApplicationContext()).getAll();

    }

    private void setControl() {
        init();

        this.lvMatHang = findViewById(R.id.lv_chonmathang_danhsachmathang);

        AdapterChonMatHang adapterChonMatHang = new AdapterChonMatHang(this, R.layout.layout_item_chon_mat_hang, this.matHangs);
        this.lvMatHang.setAdapter(adapterChonMatHang);
    }

    private void setEvent() {
        this.lvMatHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                SanPham temp = matHangs.get(i);
                intent.putExtra("sanpham", new SanPhamDonHang(temp.getMaSP(), temp.getTenSP(), 1, temp.getDonGia(), temp.getHinh(), temp.getXuatXu()));
                setResult(1, intent);
                finish();
            }
        });
    }

    public void init(){
        DBSanPham dbSanPham = new DBSanPham(getApplicationContext());
        this.matHangs.clear();
        this.matHangs.addAll(dbSanPham.docDL());
    }
}