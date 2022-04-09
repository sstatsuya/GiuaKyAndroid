package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giuakyandroid.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import donhang.model.AdapterDonHang;
import donhang.model.DonHang;
import others.Others;

public class DonHangActivity extends AppCompatActivity {
    ListView lvDSDonHang;
    TextView btnThemDonHang;
    ArrayList<DonHang> donHangs = new ArrayList<>();
    Others others;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        AdapterDonHang adapterDonHang = new AdapterDonHang(this, R.layout.layout_item_donhang, donHangs);
        lvDSDonHang.setAdapter(adapterDonHang);

        this.btnThemDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonHangActivity.this, ThemDonHangActivity.class);
                startActivity(intent);
            }
        });

    }

    private void khoiTao() {
        Date date = new Date(2022, 4, 3);
        donHangs.add(new DonHang("DH0001", date, "KH0001"));
        donHangs.add(new DonHang("DH0002", date, "KH0001"));
        donHangs.add(new DonHang("DH0003", date, "KH0001"));
        donHangs.add(new DonHang("DH0004", date, "KH0001"));
        donHangs.add(new DonHang("DH0005", date, "KH0001"));
    }

    private void setControl() {
        btnThemDonHang = findViewById(R.id.btn_them_don_hang);
        lvDSDonHang = findViewById(R.id.lvDSDonHang);
    }


}