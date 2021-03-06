package sanpham;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import others.Others;
import database.model.SanPham;
import database.DBSanPham;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThemSanPhamActivity extends AppCompatActivity {
    EditText txtTSPTen, txtTSPGia, txtTSPXuatXu;
    TextView btnTSPDongY, btnTSPHuy, txtTSPHinh;
    ImageView imgTSPHinh;
    LinearLayout btnFolder, btnCamera;
    boolean chonHinh = false;
    Others others = new Others();

    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(0, intent);
        finish();
    }

    private void setEvent() {
        DBSanPham dbSanPham = new DBSanPham(getApplicationContext());
//        Bam nut huy
        btnTSPHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        Bam dong y
        btnTSPDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInput()) {
                    Toast.makeText(ThemSanPhamActivity.this, "Th??ng tin kh??ng ???????c tr???ng", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbSanPham.themDL(getDuLieuInput());
                Dialog successDialog = others.openSuccessDialog(ThemSanPhamActivity.this, "Th??m s???n ph???m th??nh c??ng");
                successDialog.show();
                TextView btnSuccessDongY = successDialog.findViewById(R.id.btn_success_dong_y);
                btnSuccessDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        successDialog.dismiss();
                        onBackPressed();
                    }
                });
            }
        });

//        Khi b???m v??o camera
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });
//        Nut ch???n h??nh t??? th?? m???c
        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

    }

    private SanPham getDuLieuInput() {
        SanPham sanPham = new SanPham();
        sanPham.setTenSP(txtTSPTen.getText().toString());
        Double donGia = Double.parseDouble(txtTSPGia.getText().toString());
        sanPham.setDonGia(donGia);
        sanPham.setXuatXu(txtTSPXuatXu.getText().toString());
//        L??u h??nh
        sanPham.setHinh(others.luuHinh(imgTSPHinh));
        return sanPham;
    }

    private void setControl() {
        txtTSPTen = findViewById(R.id.txtTSPTen);
        txtTSPGia = findViewById(R.id.txtTSPGia);
        txtTSPXuatXu = findViewById(R.id.txtTSPXuatXu);
        btnTSPDongY = findViewById(R.id.btn_them_sp_dong_y);
        btnTSPHuy = findViewById(R.id.btn_them_sp_huy);
        imgTSPHinh = findViewById(R.id.imgTSPHinh);
        txtTSPHinh = findViewById(R.id.txtTSPHinh);
        btnFolder = findViewById(R.id.btn_them_sp_folder);
        btnCamera = findViewById(R.id.btn_them_sp_camera);
    }

    //    Check input tr?????c khi th??m s???n ph???m
    private boolean checkInput() {
        if (txtTSPGia.getText().toString().equals("")) return false;
        if (txtTSPTen.getText().toString().equals("")) return false;
        if (txtTSPXuatXu.getText().toString().equals("")) return false;
        if (!chonHinh) return false;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Tr?????ng h???p l???y h??nh t??? camera
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK && data != null) {
                chonHinh = true;
                txtTSPHinh.setText("???? c?? h??nh");
                txtTSPHinh.setTextColor(getResources().getColor(R.color.green));
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgTSPHinh.setImageBitmap(bitmap);
            } else Toast.makeText(this, "Ch??a c?? h??nh", Toast.LENGTH_SHORT).show();
        }
        //Tr?????ng h???p l???y h??nh t??? folder
        if (requestCode == REQUEST_CODE_FOLDER) {
            if (resultCode == RESULT_OK && data != null) {
                Uri uri = data.getData();
                try {
                    chonHinh = true;
                    txtTSPHinh.setText("???? c?? h??nh");
                    txtTSPHinh.setTextColor(getResources().getColor(R.color.green));
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgTSPHinh.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}