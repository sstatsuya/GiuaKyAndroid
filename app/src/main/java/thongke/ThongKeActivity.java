package thongke;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import com.example.giuakyandroid.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.HashMap;

import database.DBDonHang;
import database.DBSanPham;
import database.DBThongTinDatHang;
import database.model.DonHang;
import database.model.SanPham;
import database.model.ThongTinDonHang;
import others.Others;
import thongke.model.AdapterBanChay;
import thongke.model.BanChay;

public class ThongKeActivity extends AppCompatActivity {
    BarChart barChart;
    EditText txtNam;
    Button btnThongKe;
    ListView lvBanChay;
    Calendar cal = Calendar.getInstance();
    ArrayList<DonHang> donHangs = new ArrayList<>();
    ArrayList<ThongTinDonHang> thongTinDonHangs = new ArrayList<>();
    int inputYear = 2022;
    DBDonHang dbDonHang;
    DBSanPham dbSanPham;
    DBThongTinDatHang dbThongTinDatHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        init();
        setControl();
        setEvent();
    }

    private void setControl() {
        barChart = findViewById(R.id.barChart);
        txtNam = findViewById(R.id.txt_ThongKe_Nam);
        txtNam.setText("2022");
        btnThongKe = findViewById(R.id.btn_ThongKe_ThongKe);
        lvBanChay = findViewById(R.id.lv_thongke_danhsachmathangbanchay);
    }

    private void setEvent() {
        xuLyBieuDo(inputYear);
        getTopSellerProduct(inputYear);
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputYear = Integer.parseInt(txtNam.getText().toString());
                xuLyBieuDo(Integer.parseInt(txtNam.getText().toString()));
                getTopSellerProduct(inputYear);

            }
        });
    }

    public void init() {
        dbDonHang = new DBDonHang(getApplicationContext());
        dbSanPham = new DBSanPham(getApplicationContext());
        dbThongTinDatHang = new DBThongTinDatHang(getApplicationContext());
    }

    public void xuLyBieuDo(int year) {
        HashMap<Integer, Integer> dataDonHangHash = new HashMap<>();
        //get dbDonHang data
        donHangs.clear();
        donHangs.addAll(dbDonHang.getAllByYear(year));
        if (donHangs.isEmpty()) {
            Toast.makeText(this, "Kh??ng co?? d???? li????u", Toast.LENGTH_LONG).show();
        } else {
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
            BarDataSet barDataSet = new BarDataSet(entries, "S???? ti????n ba??n ????????c");
            //T??n bi???u ?????
            barChart.getDescription().setText("Tha??ng");
            //T???o m???i c???t c?? m???i m??u kh??c nhau
            BarData barData = new BarData(barDataSet);
            //????a d??? li???u v??o bi???u ?????
            barChart.setData(barData);
            //Cho k??ch c??? bi???u ????? ph?? h???p
            barChart.setFitBars(true);
            //Hi???u ???ng
            barChart.animateY(1200);
            //T???o ch?? th??ch cho m???i c???t
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
            //L???y tr???c ho??nh ra
            XAxis xAxis = barChart.getXAxis();
            //Cho tr???c ho??nh ??? d?????i ????y
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelCount(12);
            xAxis.setSpaceMax(0.5f);
            xAxis.setSpaceMin(0.5f);
            //????a m???y c??i ch?? th??ch ??? tr??n v??o tr???c ho??nh
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            //N??y ????? cho c??c ch?? th??ch n???m ch??nh x??c
            xAxis.setGranularity(0.5f);
        }
    }

    private void getTopSellerProduct(int year) {
        HashMap<Integer, Integer> bestSeller = new HashMap<>();
        donHangs.clear();
        donHangs.addAll(dbDonHang.getAllByYear(year));
        //get all product in year
        for (DonHang item : donHangs) {
            thongTinDonHangs.clear();
            thongTinDonHangs.addAll(dbThongTinDatHang.getAllThongTinDonHangByMaDH(item.getMaDH()));
            for (ThongTinDonHang thongTinDonHang : thongTinDonHangs) {
                int maSP = thongTinDonHang.getMaSP();
                if (bestSeller.containsKey(maSP)) {
                    int temp = bestSeller.get(maSP);
                    bestSeller.put(maSP, temp + thongTinDonHang.getSoLuongDat());
                } else {
                    bestSeller.put(maSP, thongTinDonHang.getSoLuongDat());
                }
            }
        }
        //sort product in desc direct
        HashMap<Integer, Integer> bestSellerAfterSort = Others.sortHashMapByValues(bestSeller);
        ArrayList<BanChay> banChays = new ArrayList<>();
        int count = 0;
        for (Integer key : bestSellerAfterSort.keySet()) {
            if (count < 5) {
                SanPham sanPham = dbSanPham.getSanPhamByMaSP(key);
                BanChay temp = new BanChay();
                temp.setHinh(sanPham.getHinh());
                temp.setDonGia(sanPham.getDonGia());
                temp.setMaSP(sanPham.getMaSP());
                temp.setTenSP(sanPham.getTenSP());
                temp.setSoLuong(bestSellerAfterSort.get(key));
                banChays.add(temp);
                count++;
            } else {
                break;
            }
        }
        //Set value for ListView
        AdapterBanChay adapterBanChay = new AdapterBanChay(this, R.layout.layout_item_san_pham_ban_chay, banChays);
        lvBanChay.setAdapter(adapterBanChay);

    }
}