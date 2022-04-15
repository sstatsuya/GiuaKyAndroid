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
    //database
    DBDonHang dbDonHang;

    ListView lvDSDonHang;
    TextView btnThemDonHang;
    AdapterDonHang adapterDonHang;
    ArrayList<DonHang> donHangs = new ArrayList<>();
    Others others;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.gray));

        this.dbDonHang = new DBDonHang(this.getApplicationContext());

        setControl();
        setEvent();
    }

    private void setEvent() {
        init();
        adapterDonHang = new AdapterDonHang(this, R.layout.layout_item_donhang, donHangs);
        lvDSDonHang.setAdapter(adapterDonHang);

        this.btnThemDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonHangActivity.this, ThemDonHangActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

    }

    private void init() {
        donHangs = dbDonHang.getAll();
    }

    private void setControl() {
        btnThemDonHang = findViewById(R.id.btn_them_don_hang);
        lvDSDonHang = findViewById(R.id.lvDSDonHang);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 0) {
//                Toast.makeText(this, "bam nut huy", Toast.LENGTH_SHORT).show();
            } else if(resultCode == 1) {
                donHangs.clear();
                donHangs.addAll(dbDonHang.getAll());
                adapterDonHang.notifyDataSetChanged();
//                Toast.makeText(this, "bam nut luu", Toast.LENGTH_SHORT).show();
            }
        }
    }


}