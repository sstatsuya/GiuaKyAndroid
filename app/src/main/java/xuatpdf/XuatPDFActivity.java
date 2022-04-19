package xuatpdf;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.giuakyandroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.DBDonHang;
import database.DBSanPham;
import database.DBThongTinDatHang;
import database.model.DonHang;
import database.model.SanPham;
import database.model.ThongTinDonHang;
import thongke.model.AdapterBanChay;
import xuatpdf.model.ChiTietHoaDon;
import xuatpdf.model.ChiTietHoaDonAdapter;

/**
 * We have thong tin don hang activity,
 * First I will try to get MaDH,
 * Second I store this into a pdf
 */
public class XuatPDFActivity extends AppCompatActivity {
    //Giao dien
    Button btnXuatPDF, btnTimHoaDon;
    ListView lv_pdf_sanPhams;
    EditText txt_pdf_mahoadon;
    TextView tvPDFMaHD, tvPDFNgayXuat, tvPDFTenKhachHang, tvPDFTongTien;


    //Variable

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Integer maHoaDon;
    ArrayList<ThongTinDonHang> thongTinDonHangs = new ArrayList<>();

    //This is data I need
    //And we have this after getThongTinDonHang function
    ArrayList<ChiTietHoaDon> listChiTietHoaDons = new ArrayList<>();
    DonHang donHang;
    ChiTietHoaDonAdapter chiTietHoaDonAdapter;
    int tongTien;

    DBDonHang dbDonHang;
    DBSanPham dbSanPham;
    DBThongTinDatHang dbThongTinDatHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuat_pdfactivity);
        btnXuatPDF = findViewById(R.id.btn_xuat_pdf);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        init();
        setControl();
//        layDuLieuDatabase();
//        createPDF();
        setEvent();
    }

    private void init() {
        dbDonHang = new DBDonHang(getApplicationContext());
        dbSanPham = new DBSanPham(getApplicationContext());
        dbThongTinDatHang = new DBThongTinDatHang(getApplicationContext());
    }

    private void setControl() {
        btnTimHoaDon = findViewById(R.id.btn_pdf_timkiem);
        txt_pdf_mahoadon = findViewById(R.id.txt_pdf_mahoadon);
        tvPDFMaHD = findViewById(R.id.tv_pdf_maHD);
        tvPDFNgayXuat = findViewById(R.id.tv_pdf_ngayXuat);
        tvPDFTenKhachHang = findViewById(R.id.tv_pdf_TenKhachHang);
        lv_pdf_sanPhams = findViewById(R.id.lv_pdf_sanPhams);
        tvPDFTongTien = findViewById(R.id.tv_pdf_tongTien);
        chiTietHoaDonAdapter = new ChiTietHoaDonAdapter(this, R.layout.activity_items_pdf_chi_tiet_hoa_don, listChiTietHoaDons);
        lv_pdf_sanPhams.setAdapter(chiTietHoaDonAdapter);

    }

    private void setEvent() {
        btnTimHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maHoaDon = Integer.parseInt(txt_pdf_mahoadon.getText().toString());
                getThongTinDonHang(maHoaDon);
                tvPDFMaHD.setText("Mã đơn hàng: " + String.valueOf(donHang.getMaDH()));
                tvPDFNgayXuat.setText("Ngày xuất: " + df.format(donHang.getNgayDatHang()));
                tvPDFTenKhachHang.setText("Tên khách hàng: " + String.valueOf(donHang.getTenKH()));
                tvPDFTongTien.setText("Tổng tiền: " + String.valueOf(tongTien));
            }
        });
        btnXuatPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPDF();
            }
        });
    }

    private void getThongTinDonHang(int maHoaDon) {
        tongTien = 0;
        listChiTietHoaDons.clear();
        thongTinDonHangs.clear();
        donHang = dbDonHang.get(maHoaDon);
        thongTinDonHangs.addAll(dbThongTinDatHang.getAllThongTinDonHangByMaDH(maHoaDon));
        for (ThongTinDonHang item : thongTinDonHangs) {
            SanPham tempSanPham = dbSanPham.getSanPhamByMaSP(item.getMaSP());
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
            chiTietHoaDon.setDonGia(tempSanPham.getDonGia());
            chiTietHoaDon.setMaSP(tempSanPham.getMaSP());
            chiTietHoaDon.setHinh(tempSanPham.getHinh());
            chiTietHoaDon.setSoLuong(item.getSoLuongDat());
            chiTietHoaDon.setTenSP(tempSanPham.getTenSP());
            chiTietHoaDon.setThanhTien(item.getSoLuongDat() * tempSanPham.getDonGia());
            tongTien += chiTietHoaDon.getThanhTien();
            listChiTietHoaDons.add(chiTietHoaDon);
        }
        chiTietHoaDonAdapter.notifyDataSetChanged();
    }

    private void createPDF() {
        //    PDF
        int leftX = 20, rightX = 220;
        int pageNumber = 1;
        int width = 360;
        int height = 720;
        int sanPhamX = 20, soLuongX = 120, donGiaX = 170, thanhhTienX = 280, currentY = 240;
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(width, height, pageNumber).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.icon);
        Bitmap scaleIcon = Bitmap.createScaledBitmap(icon, 100, 90, false);
        canvas.drawBitmap(scaleIcon, leftX, 20, myPaint);

        myPaint.setTextSize(24);
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        myPaint.setColor(getResources().getColor(R.color.primary));
        canvas.drawText("HÓA ĐƠN", 150, 68, myPaint);

        myPaint.setTextSize(12);
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Mã đơn: " + donHang.getMaDH(), leftX, 140, myPaint);
        myPaint.setColor(Color.GRAY);
        canvas.drawText("Ngày lập: " + df.format(donHang.getNgayDatHang()), rightX, 140, myPaint);
        myPaint.setColor(getResources().getColor(R.color.green));
        canvas.drawText("Khách hàng: " + donHang.getTenKH(), leftX, 170, myPaint);
        myPaint.setColor(Color.BLACK);
        canvas.drawLine(0, 180, 360, 180, myPaint);

        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("SẢN PHẨM", leftX, 210, myPaint);
        canvas.drawText("SỐ LƯỢNG", 100, 210, myPaint);
        canvas.drawText("ĐƠN GIÁ", 180, 210, myPaint);
        canvas.drawText("THÀNH TIỀN(VNĐ)", 245, 210, myPaint);
        myPaint.setColor(Color.BLACK);

        ChiTietHoaDon temp;
        for (int i = 0; i < listChiTietHoaDons.size(); i++) {
            temp = listChiTietHoaDons.get(i);
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(temp.getTenSP(), sanPhamX, currentY, myPaint);
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp.getHinh(), 0, temp.getHinh().length);
            Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, 60, 40, false);
            canvas.drawBitmap(scaleBitmap, sanPhamX, currentY + 10, myPaint);

            canvas.drawText(String.valueOf(Math.round(temp.getSoLuong())), soLuongX, currentY + 30, myPaint);
            canvas.drawText(String.valueOf(Math.round(temp.getDonGia())), donGiaX, currentY + 30, myPaint);
            canvas.drawText(String.valueOf(Math.round(temp.getThanhTien())), thanhhTienX, currentY + 30, myPaint);
            currentY += 80;
            if (currentY > 650) {
                myPdfDocument.finishPage(myPage);
                currentY = 20;
                pageNumber += 1;
                myPageInfo = new PdfDocument.PageInfo.Builder(width, height, pageNumber).create();
                myPage = myPdfDocument.startPage(myPageInfo);
                canvas = myPage.getCanvas();
            }
        }

        myPaint.setColor(Color.BLACK);
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawLine(40, currentY, 320, currentY, myPaint);
        myPaint.setTextSize(16);
        canvas.drawText("Tổng cộng: " + tongTien + "VNĐ", 150, currentY + 30, myPaint);
        myPdfDocument.finishPage(myPage);
        String fileName = "hoadon_" + maHoaDon + ".pdf";
        File file = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
        //check if fileName is existed
        if (file.exists()) {
            file.delete();
        }
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(XuatPDFActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(XuatPDFActivity.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
        }
        myPdfDocument.close();
    }
}