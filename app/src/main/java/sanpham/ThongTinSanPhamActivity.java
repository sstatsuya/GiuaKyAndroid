package sanpham;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import others.Others;
import sanpham.model.SanPham;
import sanpham.model.dbSanPham;

import com.squareup.picasso.Picasso;

public class ThongTinSanPhamActivity extends AppCompatActivity {
    SanPham sanPham;
    TextView txtTTSPMa, txtTTSPTen, txtTTSPGia, txtTTSPXuatXu;
    ImageView imgTTSPHinh;
    TextView btnSPSua, btnSPXoa;
    LinearLayout llSuaTTSP;

    Others others = new Others();

    final Integer REQUEST_CODE_SUA_TTSP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_san_pham);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        sanPham = (SanPham) getIntent().getSerializableExtra("sanPham");
        setControl();
        ganDuLieuVao();
        setEvent();
    }

    private void setEvent() {
        dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
        btnSPXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaSanPham();
            }
        });
        btnSPSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTinSanPhamActivity.this, SuaThongTinSanPhamActivity.class);
                intent.putExtra("sanPham", sanPham);
                startActivityForResult(intent, REQUEST_CODE_SUA_TTSP);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(0, intent);
        finish();
    }

    //    Ánh xạ
    private void setControl() {
//      Trang thông tin sản phẩm
        txtTTSPMa = findViewById(R.id.txtTTSPMa);
        txtTTSPTen = findViewById(R.id.txtTTSPTen);
        txtTTSPGia = findViewById(R.id.txtTTSPGia);
        txtTTSPXuatXu = findViewById(R.id.txtTTSPXuatXu);
        imgTTSPHinh = findViewById(R.id.imgTTSPHinh);
        llSuaTTSP = findViewById(R.id.ll_suattsp);
        btnSPSua = findViewById(R.id.btn_sua_sp);
        btnSPXoa = findViewById(R.id.btn_xoa_sp);
//
//
    }

    private void ganDuLieuVao() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinh(), 0, sanPham.getHinh().length);
//        Thông tin sản phẩm
        txtTTSPMa.setText(sanPham.getMaSP().toString());
        txtTTSPTen.setText(sanPham.getTenSP());
        txtTTSPXuatXu.setText(sanPham.getXuatXu());
        txtTTSPGia.setText(sanPham.getDonGia().toString());
        imgTTSPHinh.setImageBitmap(bitmap);
    }

//    private void suaSanPham() {
//        Dialog confirmDialog = others.openConfirmDialog(ThongTinSanPhamActivity.this, "Bạn có muốn sửa sản phẩm này?");
//        confirmDialog.show();
//        TextView btnConfirmHuyBo = confirmDialog.findViewById(R.id.btn_confirm_huy_bo);
//        TextView btnConfirmDongY = confirmDialog.findViewById(R.id.btn_confirm_dong_y);
//        btnConfirmHuyBo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                confirmDialog.dismiss();
//            }
//        });
//        btnConfirmDongY.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Sua san pham
//                dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
//                dbSanPham.suaDL(layDuLieuInput());
//                sanPham = layDuLieuInput();
//                ganDuLieuVao();
//                confirmDialog.dismiss();
//                Dialog successDialog = others.openSuccessDialog(ThongTinSanPhamActivity.this, "Sửa sản phẩm thành công");
//                successDialog.show();
//                TextView btnSuccessDongY = successDialog.findViewById(R.id.btn_success_dong_y);
//                btnSuccessDongY.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        successDialog.dismiss();
//                        btnSTTSPHuy.callOnClick();
//                    }
//                });
//            }
//        });
//    }

    private void xoaSanPham() {
        Dialog confirmDialog = others.openConfirmDialog(ThongTinSanPhamActivity.this, "Bạn có muốn xóa sản phẩm này?");
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
                //Xoa san pham
                dbSanPham dbSanPham = new dbSanPham(getApplicationContext());
                dbSanPham.xoaDL(sanPham);
                confirmDialog.dismiss();
                Dialog successDialog = others.openSuccessDialog(ThongTinSanPhamActivity.this, "Xóa sản phẩm thành công");
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SanPham sanPhamTraVe = (SanPham) data.getSerializableExtra("sanPham");
        if (requestCode == REQUEST_CODE_SUA_TTSP) {
            if (resultCode == 1) {
                sanPham = sanPhamTraVe;
                ganDuLieuVao();
            }
        }
    }
}