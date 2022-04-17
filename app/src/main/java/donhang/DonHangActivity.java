package donhang;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.example.giuakyandroid.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import database.DBDonHang;
import database.model.DonHang;
import donhang.model.AdapterDonHang;
import others.Others;

public class DonHangActivity extends AppCompatActivity {
    ListView lvDSDonHang;
    TextView tvThemDonHang;
    //database
    DBDonHang dbDonHang;
    AdapterDonHang adapterDonHang;
    ArrayList<DonHang> donHangs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        //generate database connector
        this.dbDonHang = new DBDonHang(this.getApplicationContext());

        generate();
        setControl();
        setEvent();
    }

    private void generate() {
        //get list DonHang
        this.donHangs.clear();
        this.donHangs.addAll(this.dbDonHang.getAll());
    }

    private void setControl() {
        //link to layout element
        this.tvThemDonHang = findViewById(R.id.btn_them_don_hang);
        this.lvDSDonHang = findViewById(R.id.lvDSDonHang);
        //Set data to layout element
        adapterDonHang = new AdapterDonHang(this, R.layout.layout_item_donhang, donHangs);
        lvDSDonHang.setAdapter(adapterDonHang);
    }

    private void setEvent() {
        this.tvThemDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonHangActivity.this, ThemDonHangActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { //click Huy button
            if (resultCode == 0) {
                Toast.makeText(this, "bam nut huy", Toast.LENGTH_SHORT).show();
            } else if (resultCode == 1) { //click Luu button
                generate();
                adapterDonHang.notifyDataSetChanged();
            }
        }
    }

    //Khi xóa khách hàng hay sản phẩm rồi bấm qua đơn hàng nó khỏi bị lỗi
    @Override
    protected void onResume() {
        super.onResume();
        generate();
    }
}