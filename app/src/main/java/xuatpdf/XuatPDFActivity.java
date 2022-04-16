package xuatpdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.Toast;

import com.example.giuakyandroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import database.DBSanPham;
import database.model.SanPham;

public class XuatPDFActivity extends AppCompatActivity {
    //    Giao dien
    Button btnXuatPDF;

    //    Du Lieu
    ArrayList<SanPham> sanPhams = new ArrayList<>();

    //    PDF
    PdfDocument myPdfDocument = new PdfDocument();
    int leftX = 20, rightX = 220;
    int pageNumber = 1;
    int width = 360;
    int height = 720;
    int sanPhamX = 20, soLuongX = 120, donGiaX = 170, thanhhTienX = 280, currentY = 240;
    int tongCongX = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuat_pdfactivity);
        btnXuatPDF = findViewById(R.id.btn_xuat_pdf);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        layDuLieuDatabase();
        createPDF();
    }

    private void createPDF() {

        btnXuatPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                canvas.drawText("Mã đơn: DH0001", leftX, 140, myPaint);
                myPaint.setColor(Color.GRAY);
                canvas.drawText("Ngày lập: 11/04/2022", rightX, 140, myPaint);
                myPaint.setColor(getResources().getColor(R.color.green));
                canvas.drawText("Khách hàng: Lương Minh tiến", leftX, 170, myPaint);
                myPaint.setColor(Color.BLACK);
                canvas.drawLine(0, 180, 360, 180, myPaint);

                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                canvas.drawText("SẢN PHẨM", leftX, 210, myPaint);
                canvas.drawText("SỐ LƯỢNG", 100, 210, myPaint);
                canvas.drawText("ĐƠN GIÁ", 180, 210, myPaint);
                canvas.drawText("THÀNH TIỀN", 280, 210, myPaint);
                myPaint.setColor(Color.BLACK);

                for (int i = 0; i < sanPhams.size(); i++) {
                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    canvas.drawText(sanPhams.get(i).getTenSP(), sanPhamX, currentY, myPaint);
                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                    Bitmap bitmap = BitmapFactory.decodeByteArray(sanPhams.get(i).getHinh(), 0, sanPhams.get(i).getHinh().length);
                    Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, 60, 40, false);
                    canvas.drawBitmap(scaleBitmap, sanPhamX, currentY + 10, myPaint);

                    canvas.drawText("3", soLuongX, currentY + 30, myPaint);
                    canvas.drawText("120.000vnd", donGiaX, currentY + 30, myPaint);
                    canvas.drawText("360.000vnd", thanhhTienX, currentY + 30, myPaint);
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
                canvas.drawText("Tổng cộng: 1.120.000vnd", 150, currentY+30 , myPaint);


                myPdfDocument.finishPage(myPage);


                File file = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "hoadon.pdf");

                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(XuatPDFActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(XuatPDFActivity.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }

                myPdfDocument.close();
            }
        });
    }

    private void layDuLieuDatabase() {
        DBSanPham dbSanPham = new DBSanPham(getApplicationContext());
        sanPhams.clear();
        sanPhams.addAll(dbSanPham.docDL());
    }
}