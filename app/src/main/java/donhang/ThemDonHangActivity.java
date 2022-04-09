package donhang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.giuakyandroid.R;
import database.model.SanPham;

import java.util.ArrayList;

import donhang.model.AdapterDSSanPham;
import database.dbSanPham;

public class ThemDonHangActivity extends AppCompatActivity {

    ListView lsSanPham;
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
        this.lsSanPham = findViewById(R.id.lvSanPham);
        AdapterDSSanPham adapterDSSanPham =new AdapterDSSanPham(this, R.layout.layout_item_them_mat_hang, this.sanPhams);
        this.lsSanPham.setAdapter(adapterDSSanPham);
    }

    private void setEvent() {

    }

    public void init(){
        dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
        sanPhams.clear();
        sanPhams.addAll(dbSanPham.docDL());
        System.out.println("àdhasjdkflahsdfolikasdufhaolisdfjhasdlfkjasgdfoiasdufawse");
        for(SanPham i: sanPhams){
            System.out.println("1");
        }
    }
}