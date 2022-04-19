package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import database.DBSanPham;
import database.model.SanPham;
import database.model.SanPhamDonHang;
import donhang.model.AdapterChonMatHang;

public class ChonMatHangActivity extends AppCompatActivity {
    ArrayList<SanPham> matHangs = new ArrayList<>();
    AdapterChonMatHang adapterChonMatHang;

    ListView lvMatHang;
    AutoCompleteTextView actvTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mat_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));

        generate();
        setControl();
        setEvent();
    }

    private void generate() {
        DBSanPham dbSanPham = new DBSanPham(getApplicationContext());
        this.matHangs.clear();
        this.matHangs.addAll(dbSanPham.docDL());
    }

    private void setControl() {
        //link java variable to layout id
        this.lvMatHang = findViewById(R.id.lv_chonmathang_danhsachmathang);
        this.actvTimKiem = findViewById(R.id.actv_chonmathang_timkiem);
        //setValue for layout element
        adapterChonMatHang = new AdapterChonMatHang(this, R.layout.layout_item_chon_mat_hang, this.matHangs);
        this.lvMatHang.setAdapter(adapterChonMatHang);
    }

    private void setEvent() {
        this.actvTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapterChonMatHang.searchFunction(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        this.lvMatHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                SanPham temp = matHangs.get(i);
                intent.putExtra("sanpham", new SanPhamDonHang(temp.getMaSP(), temp.getTenSP(), 1, temp.getDonGia(), temp.getHinh(), temp.getXuatXu()));
                setResult(1, intent);
                finish();
            }
        });
    }
}