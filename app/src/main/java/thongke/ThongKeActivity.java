package thongke;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import database.DBDonHang;
import database.DBSanPham;
import database.DBThongTinDatHang;
import database.model.DonHang;
import database.model.SanPham;
import database.model.ThongTinDonHang;

public class ThongKeActivity extends AppCompatActivity {
    BarChart barChart;
    Calendar cal = Calendar.getInstance();
    ArrayList<DonHang> donHangs = new ArrayList<>();
    //    ArrayList<SanPham> sanPhams = new ArrayList<>();
    ArrayList<ThongTinDonHang> thongTinDonHangs = new ArrayList<>();

    HashMap<Integer, Integer> dataDonHangHash = new HashMap<>();


    DBDonHang dbDonHang;
    DBSanPham dbSanPham;
    DBThongTinDatHang dbThongTinDatHang;

    ListView lvMatHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setControl() {
        init();
//        this.lvMatHang = findViewById(R.id.lv_thongke_danhsachmathangbanchay);
        barChart = findViewById(R.id.barChart);

        //Cái này nên để ra hàm riêng nha Thạch
//        AdapterThongKe adapterThongKe = new AdapterThongKe(this, R.layout.layout_item_san_pham_ban_chay, this.matHangs);
//        this.lvMatHang.setAdapter(adapterThongKe);
    }

    private void setEvent() {
        xuLyBieuDo();
    }

    public void init() {
//        DBSanPham dbSanPham = new DBSanPham(getApplicationContext());
//        this.matHangs.clear();
//        this.matHangs.addAll(dbSanPham.docDL());
    }

    public void xuLyBieuDo() {

        //get dbDonHang data
        dbDonHang = new DBDonHang(getApplicationContext());
        donHangs.clear();
        donHangs.addAll(dbDonHang.getAll());

        //get dbSanPham data
        dbSanPham = new DBSanPham(getApplicationContext());

        //get dbThongTinDatHang
        //Init dataDonHangHash
        //first set all value = 0 with each month, month value from 0 -> 11
        dbThongTinDatHang = new DBThongTinDatHang(getApplicationContext());
        for (int i = 0; i < 12; i++) {
            dataDonHangHash.put(i, 0);
        }


        int tongTien;
        int month;
        //Iterator donHangs array to calculate money of each month
        for (DonHang item : donHangs) {
            cal.setTime(item.getNgayDatHang());
            month = cal.get(Calendar.MONTH);
            tongTien = dataDonHangHash.get(month);
            thongTinDonHangs.clear();
            thongTinDonHangs.addAll(dbThongTinDatHang.getAllThongTinDonHangByMaDH(item.getMaDH()));
            for (ThongTinDonHang thongTinDonHang : thongTinDonHangs) {
                int maSP = thongTinDonHang.getMaSP();
                SanPham sanPham = dbSanPham.getSanPhamByMaSP(maSP);
                tongTien += thongTinDonHang.getSoLuongDat() * sanPham.getDonGia();
            }

            //update dataDonHangHash value
            dataDonHangHash.put(month, tongTien);
        }
        //Set data for chart
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, dataDonHangHash.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(entries, "Sản phẩm bán được");

        //Tạo mỗi cột có mỗi màu khác nhau
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);

        //Tên biểu đồ
        barChart.getDescription().setText("Số lượng sản phẩm được theo tháng");

        //Đưa dữ liệu vào biểu đồ
        barChart.setData(barData);

        //Cho kích cỡ biểu đồ phù hợp
        barChart.setFitBars(true);

        //Hiệu ứng
        barChart.animateY(1200);

        //Tạo chú thích cho mỗi cột
        ArrayList<String> labels = new ArrayList<>();
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        labels.add("7");
        labels.add("8");
        labels.add("9");
        labels.add("10");
        labels.add("11");
        labels.add("12");

        //Lấy trục hoành ra
        XAxis xAxis = barChart.getXAxis();

        //Cho trục hoành ở dưới đáy
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //Đưa mấy cái chú thích ở trên vào trục hoành
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        //Này để cho các chú thích nằm chính xác
        xAxis.setGranularity(1f);

    }

}