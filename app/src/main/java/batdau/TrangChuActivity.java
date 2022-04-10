package batdau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import donhang.DonHangActivity;
import khachhang.KhachHangActivity;
import sanpham.SanPhamActivity;
import thongke.ThongKeActivity;
import xuatpdf.XuatPDFActivity;

public class TrangChuActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        TabHost tabHost = getTabHost();
        TabHost.TabSpec specSP = tabHost.newTabSpec("sp");
        specSP.setIndicator("",getResources().getDrawable(R.drawable.home_icon));
        specSP.setContent(new Intent(this, SanPhamActivity.class));
        tabHost.addTab(specSP);

        TabHost.TabSpec specKH = tabHost.newTabSpec("kh");
        specKH.setIndicator("",getResources().getDrawable(R.drawable.customer_icon));
        specKH.setContent(new Intent(this, KhachHangActivity.class));
        tabHost.addTab(specKH);

        TabHost.TabSpec specDH = tabHost.newTabSpec("dh");
        specDH.setIndicator("",getResources().getDrawable(R.drawable.order_icon));
        specDH.setContent(new Intent(this, DonHangActivity.class));
        tabHost.addTab(specDH);

        TabHost.TabSpec specTK = tabHost.newTabSpec("tk");
        specTK.setIndicator("",getResources().getDrawable(R.drawable.chart_icon));
        specTK.setContent(new Intent(this, ThongKeActivity.class));
        tabHost.addTab(specTK);

        TabHost.TabSpec specPDF = tabHost.newTabSpec("pdf");
        specPDF.setIndicator("",getResources().getDrawable(R.drawable.pdf_icon));
        specPDF.setContent(new Intent(this, XuatPDFActivity.class));
        tabHost.addTab(specPDF);

        tabHost.getTabWidget().getChildAt(0)
                .setBackgroundResource(R.drawable.tab_select);


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    tabHost.getTabWidget().getChildAt(i)
                        .setBackgroundResource(R.drawable.tab_unselect);
                }
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
                        .setBackgroundResource(R.drawable.tab_select);
            }
        });
    }

}