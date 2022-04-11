package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import database.dbSanPham;
import database.model.SanPham;
import donhang.model.AdapterDSMatHang;
import donhang.model.AdapterDSSanPham;

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
    }

    private void setControl() {
        init();

        this.lvMatHang = findViewById(R.id.lv_chonmathang_danhsachmathang);

        AdapterDSMatHang adapterDSMatHang = new AdapterDSMatHang(this, R.layout.layout_item_chon_mat_hang, this.matHangs);
        this.lvMatHang.setAdapter(adapterDSMatHang);
    }

    private void setEvent() {
    }

    public void init(){
        dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
        this.matHangs.clear();
        this.matHangs.addAll(dbSanPham.docDL());
    }
}