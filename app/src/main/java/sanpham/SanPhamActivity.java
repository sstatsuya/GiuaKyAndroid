package sanpham;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import sanpham.model.AdapterSanPham;
import database.model.SanPham;
import database.DBSanPham;

import java.util.ArrayList;

public class SanPhamActivity extends AppCompatActivity {
    GridView gvSanPham;
    ArrayList<SanPham> sanPhams = new ArrayList<>();
    AdapterSanPham adapterSanPham;
    ArrayAdapter sanPhamPhoBienAA;
    String[] sanPhamPhoBien;
    LinearLayout btnThemSanPham;
    Spinner sn_loc_sp;
    SearchView svLocSP;
    AutoCompleteTextView actvSearchSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setEvent() {
//        Adapter
        layDuLieuDatabase();
        adapterSanPham = new AdapterSanPham(this, R.layout.layout_san_pham, sanPhams);
        gvSanPham.setAdapter(adapterSanPham);

//        Search
        goiY();


//        Khi sản phẩm được click vào
        gvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SanPhamActivity.this, ThongTinSanPhamActivity.class);
                SanPham sanPham = sanPhams.get(position);
                intent.putExtra("sanPham", sanPham);
                intent.putExtra("mode", "full");
                startActivityForResult(intent, 1);
            }
        });

//        Khi bấm nút thêm sản phẩm
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SanPhamActivity.this, ThemSanPhamActivity.class);
                startActivityForResult(intent, 1);
            }
        });

//        Khi tìm kiếm trên autocompletetextview
        actvSearchSanPham.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapterSanPham.locBangInput(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Khi bấm vào loc trên spinner
        sn_loc_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)view).setText(null);
                adapterSanPham.locBangSpinner(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private String[] getTenSanPhams(){
        String[] res = new String[sanPhams.size()];
        for(int i = 0; i<sanPhams.size(); i++){
            res[i] = sanPhams.get(i).getTenSP();
        }
        return res;
    }

    private void goiY() {
        sanPhamPhoBienAA = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getTenSanPhams());
        actvSearchSanPham.setAdapter(sanPhamPhoBienAA);
    }

    private void layDuLieuDatabase() {
        DBSanPham dbSanPham = new DBSanPham(getApplicationContext());
        sanPhams.clear();
        sanPhams.addAll(dbSanPham.docDL());
        SanPham tam = new SanPham();
        tam.setMaSP(16);
        dbSanPham.xoaDL(tam);
    }

    private void setControl() {
        gvSanPham = findViewById(R.id.gvSanPham);
        btnThemSanPham = findViewById(R.id.btnThemSanPham);
        sn_loc_sp = findViewById(R.id.sn_loc_sp);
        actvSearchSanPham = findViewById(R.id.actvSearchSanPham);
        khoiTaoSpinnerLoc();
    }

    private void khoiTaoSpinnerLoc() {
        ArrayList<String> kieuLoc = new ArrayList<>();
        kieuLoc.add("Lọc theo mã tăng dần");
        kieuLoc.add("Lọc theo mã giảm dần");
        kieuLoc.add("Lọc theo giá tăng dần");
        kieuLoc.add("Lọc theo giá giảm dần");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, kieuLoc);
        sn_loc_sp.setAdapter(arrayAdapter);
        sn_loc_sp.setSelection(0);

    }

    private void themSanPham(SanPham sanPham) {
        sanPhams.add(sanPham);
        adapterSanPham.notifyDataSetChanged(); //Reload adapter
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SanPham sanPham = (SanPham) data.getSerializableExtra("sanPham");
        layDuLieuDatabase();
        adapterSanPham.notifyDataSetChanged();
    }

}