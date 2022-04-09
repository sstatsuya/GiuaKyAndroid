package thongke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import database.dbSanPham;
import database.model.SanPham;
import thongke.model.AdapterThongKe;

public class ThongKeActivity extends AppCompatActivity {

    ArrayList<SanPham> matHangs = new ArrayList<>();

    ListView lvMatHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setControl() {
        init();

        this.lvMatHang = findViewById(R.id.lv_thongke_danhsachmathangbanchay);

        AdapterThongKe adapterThongKe = new AdapterThongKe(this, R.layout.layout_item_san_pham_ban_chay, this.matHangs);
        this.lvMatHang.setAdapter(adapterThongKe);
    }

    private void setEvent() {
    }

    public void init(){
        dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
        this.matHangs.clear();
        this.matHangs.addAll(dbSanPham.docDL());
    }

}