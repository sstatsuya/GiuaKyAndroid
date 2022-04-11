package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import database.dbSanPham;
import database.model.SanPham;
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
                intent.putExtra("sanpham", matHangs.get(i));
                setResult(1, intent);
                finish();
            }
        });
    }

    public void init(){
        dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
        this.matHangs.clear();
        this.matHangs.addAll(dbSanPham.docDL());
    }
}