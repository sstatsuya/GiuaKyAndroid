package ngocthach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import ngocthach.model.KhachHang;

public class KhachHangActivity extends AppCompatActivity {
    // set cung data de lam giao dien
    ArrayList<KhachHang> DS_khachHangs = new ArrayList<>();
    ImageView iv_MoGD_ThemKH;
    ListView lv_DSKhachHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setdata();
        setControl();
        setEvent();
    }

    private void setdata() {
        KhachHang kh1 = new KhachHang("KH1","Nguyễn Ngọc Thạch1","0888182717","14/7 đường 359, Đỗ Xuân Hợp, Q9. TPHCM");
        KhachHang kh2 = new KhachHang("KH2","Nguyễn Ngọc Thạch2","0888182717","14/7 đường 359, Đỗ Xuân Hợp, Q9. TPHCM");
        KhachHang kh3 = new KhachHang("KH3","Nguyễn Ngọc Thạch3","0888182717","14/7 đường 359, Đỗ Xuân Hợp, Q9. TPHCM");
        KhachHang kh4 = new KhachHang("KH4","Nguyễn Ngọc Thạch4","0888182717","14/7 đường 359, Đỗ Xuân Hợp, Q9. TPHCM");
        DS_khachHangs.add(kh1);
        DS_khachHangs.add(kh2);
        DS_khachHangs.add(kh3);
        DS_khachHangs.add(kh4);
    }


    private void setEvent() {
        AdapterKhachHang adapterKhachHang = new AdapterKhachHang(this, R.layout.activity_items_khachhang,DS_khachHangs);
        lv_DSKhachHang.setAdapter(adapterKhachHang);

        iv_MoGD_ThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KhachHangActivity.this, ActivityThemKhachHang.class);
                startActivity(intent);
            }
        });

    }

    private void setControl() {
        iv_MoGD_ThemKH = findViewById(R.id.iv_MoGD_ThemKH);
        lv_DSKhachHang = findViewById(R.id.lv_DSKhachHang);
    }
}