package thongke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.giuakyandroid.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import database.dbSanPham;
import database.model.SanPham;
import thongke.model.AdapterThongKe;

public class ThongKeActivity extends AppCompatActivity {
    BarChart barChart;
    ArrayList<SanPham> matHangs = new ArrayList<>();

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

        this.lvMatHang = findViewById(R.id.lv_thongke_danhsachmathangbanchay);
        barChart = findViewById(R.id.barChart);

        //Cái này nên để ra hàm riêng nha Thạch
        AdapterThongKe adapterThongKe = new AdapterThongKe(this, R.layout.layout_item_san_pham_ban_chay, this.matHangs);
        this.lvMatHang.setAdapter(adapterThongKe);
    }

    private void setEvent() {
        xuLyBieuDo();
    }

    public void init(){
        dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
        this.matHangs.clear();
        this.matHangs.addAll(dbSanPham.docDL());
    }

    public void xuLyBieuDo(){

        //Này là tạo dữ liệu cho biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 420));
        entries.add(new BarEntry(1, 670));
        entries.add(new BarEntry(2, 780));
        entries.add(new BarEntry(3, 850));
        BarDataSet barDataSet = new BarDataSet(entries, "Sản phẩm bán được");

        //Tạo mỗi cột có mỗi màu khác nhau
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);

        //Tên biểu đồ
        barChart.getDescription().setText("Số lượng sản phẩm được bán năm 2018");

        //Đưa dữ liệu vào biểu đồ
        barChart.setData(barData);

        //Cho kích cỡ biểu đồ phù hợp
        barChart.setFitBars(true);

        //Hiệu ứng
        barChart.animateY(1200);

        //Tạo chú thích cho mỗi cột
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Tháng 1");
        labels.add("Tháng 2");
        labels.add("Tháng 3");
        labels.add("Tháng 4");

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