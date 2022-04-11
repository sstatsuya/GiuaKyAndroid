package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giuakyandroid.R;
import database.model.SanPham;

import java.util.ArrayList;
import java.util.Calendar;

import donhang.model.AdapterThemDonHangDSSanPham;
import database.dbSanPham;

public class ThemDonHangActivity extends AppCompatActivity {

    ListView lsSanPham;
    TextView tvMaKhachHang, tvTongTien;
    EditText etNgayDatHang;
    Button btnChonKhachHang, btnChonMatHang, btnLuu, btnHuy;
    ArrayList<SanPham> sanPhams = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_don_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setControl() {
        init();
        this.tvMaKhachHang = findViewById(R.id.tv_themdonhang_MaKhachHang);
        this.btnChonKhachHang = findViewById(R.id.btn_themdonhang_ChonKhachHang);
        this.etNgayDatHang = findViewById(R.id.et_themdonhang_NgayDatHang);
        this.btnChonMatHang = findViewById(R.id.btn_themdonhang_ChonMatHang);
        this.lsSanPham = findViewById(R.id.lv_themdonhang_danhsachmathang);
        this.tvTongTien = findViewById(R.id.tv_themdonhang_TongTien);
        this.btnHuy = findViewById(R.id.btn_themdonhang_huy);
        this.btnLuu = findViewById(R.id.btn_themdonhang_Luu);

        this.etNgayDatHang.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    etNgayDatHang.setText(current);
                    etNgayDatHang.setSelection(sel < current.length() ? sel : current.length());

                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        AdapterThemDonHangDSSanPham adapterThemDonHangDSSanPham = new AdapterThemDonHangDSSanPham(this, R.layout.layout_item_them_mat_hang, this.sanPhams);
        this.lsSanPham.setAdapter(adapterThemDonHangDSSanPham);
    }

    private void setEvent() {
        this.btnChonKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        this.btnChonMatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemDonHangActivity.this, ChonMatHangActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, 1);
            }

        });

        this.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ThemDonHangActivity.this, DonHangActivity.class);
//                startActivity(intent);
                Intent intent = new Intent();
                setResult(0);
                finish();
            }
        });

        this.btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ThemDonHangActivity.this, DonHangActivity.class);
//                startActivity(intent);
                Intent intent = new Intent();
                setResult(1);
                finish();
            }
        });
    }

    public void init(){
        dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
        sanPhams.clear();
        sanPhams.addAll(dbSanPham.docDL());
    }


}