package donhang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import database.DBDonHang;
import database.model.DonHang;
import donhang.model.AdapterDonHang;

public class DonHangActivity extends AppCompatActivity {
    ListView lvDSDonHang;
    TextView tvThemDonHang;
    AutoCompleteTextView actvSearchDonHang;
    //database
    DBDonHang dbDonHang;
    AdapterDonHang adapterDonHang;
    ArrayList<DonHang> donHangs, donHangsBackup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        //generate database connector
        this.dbDonHang = new DBDonHang(this.getApplicationContext());

//        generate();
        setControl();
        setEvent();
    }

    private void generate() {
        //get list DonHang
        this.donHangs = new ArrayList<>();
        this.donHangs.clear();
        this.donHangs.addAll(this.dbDonHang.getAll());
        this.donHangsBackup = new ArrayList<>();
        this.donHangsBackup.clear();
        this.donHangsBackup.addAll(this.donHangs);
    }

    private void setControl() {
        //link to layout element
        this.tvThemDonHang = findViewById(R.id.tv_them_don_hang);
        this.lvDSDonHang = findViewById(R.id.lvDSDonHang);
        this.actvSearchDonHang = findViewById(R.id.actv_donhang_timkiem);
        //generate data
        generate();
        //Set data to layout element
        this.adapterDonHang = new AdapterDonHang(this, R.layout.layout_item_donhang, this.donHangs);
        this.lvDSDonHang.setAdapter(this.adapterDonHang);
    }

    private void setEvent() {
        this.tvThemDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonHangActivity.this, ThemDonHangActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        this.actvSearchDonHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                searchFunction(charSequence.toString());
                adapterDonHang.searchFunction(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

//    private void searchFunction(String text) {
//        this.donHangs.clear();
//        System.out.println("-----------------------" + text);
//        text = text.toLowerCase();
//        if (text.length() == 0) this.donHangs.addAll(donHangsBackup);
//        else {
//            for (DonHang temp : donHangsBackup) {
//                if (temp.searchValue().toLowerCase().contains(text)) {
//                    donHangs.add(temp);
//                }
//
//            }
//        }
//        this.donHangs.forEach(System.out::println);
//        this.adapterDonHang.notifyDataSetChanged();
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { //tu trang them don hang tro ve
            if (resultCode == 1) { //click Luu button
                this.adapterDonHang.refresh();
            }
            return;
        } else {
            this.adapterDonHang.refresh();
        }
    }

    //Khi x??a kh??ch h??ng hay s???n ph???m r???i b???m qua ????n h??ng n?? kh???i b??? l???i
    @Override
    protected void onResume() {
        super.onResume();
        generate();
    }
}