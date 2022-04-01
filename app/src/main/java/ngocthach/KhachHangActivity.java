package ngocthach;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import ngocthach.model.DBKhachHang;
import ngocthach.model.KhachHang;

public class KhachHangActivity extends AppCompatActivity {
    // set cung data de lam giao dien
    ArrayList<KhachHang> DS_khachHang = new ArrayList<>();
    ImageView iv_MoGD_ThemKH;
    ListView lv_DSKhachHang;
    AdapterKhachHang adapterKhachHang;
    DBKhachHang dbKhachHang;
    public static final int REQUEST_TO_THEMKH = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }
    private void setEvent() {
        adapterKhachHang = new AdapterKhachHang(this, R.layout.activity_items_khachhang,DS_khachHang);
        lv_DSKhachHang.setAdapter(adapterKhachHang);

        iv_MoGD_ThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KhachHangActivity.this, ActivityThemKhachHang.class);
                startActivityForResult(intent,REQUEST_TO_THEMKH);
            }
        });

    }

    private void setControl() {
        dbKhachHang = new DBKhachHang(this);
        DS_khachHang = dbKhachHang.GetData();
        iv_MoGD_ThemKH = findViewById(R.id.iv_MoGD_ThemKH);
        lv_DSKhachHang = findViewById(R.id.lv_DSKhachHang);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == REQUEST_TO_THEMKH && resultCode == RESULT_OK){
            KhachHang khachHang = (KhachHang) intent.getSerializableExtra("khachhang");
            if (khachHang != null){
                DS_khachHang.add(khachHang);
                adapterKhachHang.notifyDataSetChanged(); //Reload adapter
            }

        }
    }
}