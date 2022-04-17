package donhang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import chucnang.Dialog;
import database.DBDonHang;
import database.DBKhachHang;
import database.model.DonHang;
import database.model.SanPhamDonHang;
import donhang.model.AdapterSuaDonHangDSSanPham;
import donhang.model.AdapterThemDonHangDSSanPham;

public class SuaDonHangActivity extends AppCompatActivity {
    ListView lsSanPham;
    TextView tvTenKhachHang, tvTongTien;
    EditText etMaKhachHang, etNgayDatHang;
    Button btnChonKhachHang, btnLuu, btnHuy;
    //Essential Variable
    ArrayList<SanPhamDonHang> sanPhamDonHangs;
    AdapterSuaDonHangDSSanPham adapterSuaDonHangDSSanPham;
    DBKhachHang dbKhachHang;
    DBDonHang dbDonHang;
    TextWatcher datePattern;
    int maKhachHang;
    int maDH;
    DonHang donHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_don_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        //generate database connector
        this.dbKhachHang = new DBKhachHang(this.getApplicationContext());
        this.dbDonHang = new DBDonHang(this.getApplicationContext());
        //get MaDH send from preActivities
        maDH = getIntent().getIntExtra("madonhang", 0);

        generate();
        setControl();
        setEvent();
    }

    private void generate() {
        //get DonHang detail from database
        this.donHang = dbDonHang.get(maDH);
        //Set list of SanPhamMatHang
        this.sanPhamDonHangs = this.donHang.getSanPhamDonHangs();
        //Set maKhachHang;
        this.maKhachHang = this.donHang.getMaKH();
        //Date pattern when click to editText NgayDatHang
        this.datePattern = new TextWatcher() {
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
        };
    }

    private void setControl() {
        this.etMaKhachHang = findViewById(R.id.et_suadonhang_MaKhachHang);
        this.tvTenKhachHang = findViewById(R.id.tv_suadonhang_TenKhachHang);
        this.btnChonKhachHang = findViewById(R.id.btn_suadonhang_ChonKhachHang);
        this.etNgayDatHang = findViewById(R.id.et_suadonhang_NgayDatHang);
        this.lsSanPham = findViewById(R.id.lv_suadonhang_danhsachmathang);
        this.tvTongTien = findViewById(R.id.tv_suadonhang_TongTien);
        this.btnHuy = findViewById(R.id.btn_suadonhang_huy);
        this.btnLuu = findViewById(R.id.btn_suadonhang_Luu);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.etNgayDatHang.setText(df.format(donHang.getNgayDatHang()));
        this.etMaKhachHang.setText(String.valueOf(this.donHang.getMaKH()));
        this.tvTenKhachHang.setText(this.donHang.getTenKH());
        this.adapterSuaDonHangDSSanPham = new AdapterSuaDonHangDSSanPham(this, R.layout.layout_item_sua_mat_hang, this.sanPhamDonHangs);
        this.lsSanPham.setAdapter(adapterSuaDonHangDSSanPham);
        updateTongTien();
    }

    private void setEvent() {
        this.etNgayDatHang.addTextChangedListener(datePattern);

        this.btnChonKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int temp = Integer.parseInt(String.valueOf(etMaKhachHang.getText()));
                    tvTenKhachHang.setText(dbKhachHang.getTenKhachHangByID(temp));
                    maKhachHang = temp;
                } catch (Exception e){
                    Toast.makeText(SuaDonHangActivity.this, "Không tìm thấy khách hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.etMaKhachHang.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                // If the event is a key-down event on the "enter" button
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    try {
                        int temp = Integer.parseInt(String.valueOf(etMaKhachHang.getText()));
                        tvTenKhachHang.setText(dbKhachHang.getTenKhachHangByID(temp));
                        maKhachHang = temp;
                    } catch (Exception e){
                        Toast.makeText(SuaDonHangActivity.this, "Không tìm thấy khách hàng", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        this.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(1);
                finish();
            }
        });

        this.btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataAndUpdateDonHang();
            }
        });

        this.adapterSuaDonHangDSSanPham.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                updateTongTien();
            }
        });
    }

    private void updateTongTien() {
        double temp = 0.0;
        for(SanPhamDonHang i: sanPhamDonHangs) {
            temp += i.getDonGia() * i.getSoLuong();
        }
        tvTongTien.setText(String.valueOf(temp) + "vnd");
    }

    private void getDataAndUpdateDonHang() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date ngayDatHang;
        try {
            ngayDatHang = df.parse(String.valueOf(etNgayDatHang.getText()));
            this.donHang.setNgayDatHang(ngayDatHang);
            this.donHang.setMaKH(this.maKhachHang);
        } catch (Exception e) {
            Toast.makeText(SuaDonHangActivity.this, "Vui lòng nhập ngày đặt hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        int n = dbDonHang.update(donHang);

        android.app.Dialog dialog = Dialog.openSuccessDialog(SuaDonHangActivity.this, "Chỉnh sửa đơn hàng " + String.valueOf(maDH) + " thành công");
        dialog.show();
        TextView btnSuccessDongY = dialog.findViewById(R.id.btn_success_dong_y);
        btnSuccessDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(1);
                finish();
            }
        });
    }
}