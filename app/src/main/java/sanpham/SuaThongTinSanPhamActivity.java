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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import others.Others;
import sanpham.model.SanPham;
import sanpham.model.dbSanPham;

public class SuaThongTinSanPhamActivity extends AppCompatActivity {
    EditText etSTTSPTen, etSTTPGia, etSTTSPXuatXu;
    TextView txtSTTSPMa;
    ImageView ivSTTSPHinh;
    TextView btnSTTSPDongY, btnSTTSPHuy;
    LinearLayout btnSTTSPCamera, getBtnSTTSPThuMuc;


    SanPham sanPham = new SanPham();
    Others others = new Others();
    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 124;
    boolean chonHinh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin_san_pham);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        sanPham = (SanPham) getIntent().getSerializableExtra("sanPham");
        setControl();
        ganDuLieuVao();
        setEvent();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(0, intent);
        finish();
    }

    private void setEvent() {
        //Bấm hủy
        btnSTTSPHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //Bấm đồng ý
        btnSTTSPDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaSanPham();
            }
        });
        //        Khi bấm vào camera
        btnSTTSPCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

//        Nut chọn hình từ thư mục
        getBtnSTTSPThuMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });
    }

    private SanPham layDuLieuInput() {
        SanPham sanPhamInput = new SanPham();
        sanPhamInput.setMaSP(sanPham.getMaSP());
        sanPhamInput.setTenSP(etSTTSPTen.getText().toString());
        sanPhamInput.setXuatXu(etSTTSPXuatXu.getText().toString());
        sanPhamInput.setDonGia(new Double(etSTTPGia.getText().toString()));
//        sanPhamInput.setHinh(sanPham.getHinh());
        sanPhamInput.setHinh(others.luuHinh(ivSTTSPHinh));
        return sanPhamInput;
    }

    private void ganDuLieuVao() {
        txtSTTSPMa.setText(sanPham.getMaSP().toString());
        etSTTSPTen.setText(sanPham.getTenSP().toString());
        etSTTSPXuatXu.setText(sanPham.getXuatXu().toString());
        etSTTPGia.setText(sanPham.getDonGia().toString());
        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinh(), 0, sanPham.getHinh().length);
        ivSTTSPHinh.setImageBitmap(bitmap);
    }

    private void setControl() {
        etSTTSPTen = findViewById(R.id.txtSTTSPTen);
        etSTTPGia = findViewById(R.id.txtSTTSPGia);
        etSTTSPXuatXu = findViewById(R.id.txtSTTSPXuatXu);
        txtSTTSPMa = findViewById(R.id.txtSTTSPMa);
        ivSTTSPHinh = findViewById(R.id.iv_sttsp_hinh);
        btnSTTSPDongY = findViewById(R.id.btn_sua_ttsp_dong_y);
        btnSTTSPHuy = findViewById(R.id.btn_sua_ttsp_huy);
        btnSTTSPCamera = findViewById(R.id.btn_sttsp_camera);
        getBtnSTTSPThuMuc = findViewById(R.id.btn_sttsp_thu_muc);
    }

    private void suaSanPham() {
        Dialog confirmDialog = others.openConfirmDialog(SuaThongTinSanPhamActivity.this, "Bạn có muốn sửa sản phẩm này?");
        confirmDialog.show();
        TextView btnConfirmHuyBo = confirmDialog.findViewById(R.id.btn_confirm_huy_bo);
        TextView btnConfirmDongY = confirmDialog.findViewById(R.id.btn_confirm_dong_y);
        btnConfirmHuyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
            }
        });
        btnConfirmDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sua san pham
                dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
                dbSanPham.suaDL(layDuLieuInput());
                sanPham = layDuLieuInput();
                confirmDialog.dismiss();
                Dialog successDialog = others.openSuccessDialog(SuaThongTinSanPhamActivity.this, "Sửa sản phẩm thành công");
                successDialog.show();
                TextView btnSuccessDongY = successDialog.findViewById(R.id.btn_success_dong_y);
                btnSuccessDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        successDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("sanPham", sanPham);
                        setResult(1, intent);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Trường hợp lấy hình từ camera
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK && data != null) {
                chonHinh = true;
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ivSTTSPHinh.setImageBitmap(bitmap);
            } else Toast.makeText(this, "Chua co hinh", Toast.LENGTH_SHORT).show();
        }

        //Trường hợp lấy hình từ folder
        if (requestCode == REQUEST_CODE_FOLDER) {
            if (resultCode == RESULT_OK && data != null) {
                Uri uri = data.getData();
                try {
                    chonHinh = true;
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ivSTTSPHinh.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}