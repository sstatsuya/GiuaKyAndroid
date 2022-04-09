package sanpham;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.giuakyandroid.R;

import sanpham.model.AdapterSanPham;
import database.model.SanPham;
import database.dbSanPham;

import java.util.ArrayList;

public class SanPhamActivity extends AppCompatActivity {
    GridView gvSanPham;
    ArrayList<SanPham> sanPhams = new ArrayList<>();
    AdapterSanPham adapterSanPham;
    LinearLayout btnThemSanPham;
    Spinner sn_loc_sp;
    SearchView svLocSP;

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
        layDuLieuDatabase();
        adapterSanPham = new AdapterSanPham(this, R.layout.layout_san_pham, sanPhams);
        gvSanPham.setAdapter(adapterSanPham);

//        Khi sản phẩm được click vào
        gvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SanPhamActivity.this, ThongTinSanPhamActivity.class);
                SanPham sanPham = sanPhams.get(position);
                intent.putExtra("sanPham", sanPham);
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

//        Khi tìm kiếm
        svLocSP.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterSanPham.locBangInput(s);
                return false;
            }
        });

        //Khi bấm vào loc trên spinner
        sn_loc_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)view).setText(null);
                Object item = adapterView.getItemAtPosition(i);
                int option = item.toString().equals("Lọc theo mã tăng dần")?1:2;
                adapterSanPham.locBangSpinner(option);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void layDuLieuDatabase() {
        dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
        sanPhams.clear();
        sanPhams.addAll(dbSanPham.docDL());
    }

    private void setControl() {
        gvSanPham = findViewById(R.id.gvSanPham);
        btnThemSanPham = findViewById(R.id.btnThemSanPham);
        sn_loc_sp = findViewById(R.id.sn_loc_sp);
        svLocSP = findViewById(R.id.sv_sp_loc);
        khoiTaoSpinnerLoc();
    }

    private void khoiTaoSpinnerLoc() {
        ArrayList<String> kieuLoc = new ArrayList<>();
        kieuLoc.add("Lọc theo mã tăng dần");
        kieuLoc.add("Lọc theo mã giảm dần");
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