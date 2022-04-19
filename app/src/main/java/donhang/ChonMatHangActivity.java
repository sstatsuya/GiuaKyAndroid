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

        generate();
        setControl();
        setEvent();
    }

    private void generate() {
        DBSanPham dbSanPham = new DBSanPham(getApplicationContext());
        this.matHangs.clear();
        this.matHangs.addAll(dbSanPham.docDL());
    }

    private void setControl() {
        //link java variable to layout id
        this.lvMatHang = findViewById(R.id.lv_chonmathang_danhsachmathang);
        //setValue for layout element
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
}