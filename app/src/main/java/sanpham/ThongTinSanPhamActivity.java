package sanpham;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import others.Others;
import database.model.SanPham;
import database.DBSanPham;

public class ThongTinSanPhamActivity extends AppCompatActivity {
    SanPham sanPham;
    TextView txtTTSPMa, txtTTSPTen, txtTTSPGia, txtTTSPXuatXu;
    ImageView imgTTSPHinh;
    TextView btnSPSua, btnSPXoa;
    LinearLayout llSuaTTSP;
    String mode = "full";

    Others others = new Others();

    final Integer REQUEST_CODE_SUA_TTSP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_san_pham);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        sanPham = (SanPham) getIntent().getSerializableExtra("sanPham");
        mode = getIntent().getStringExtra("mode");
        setControl();
        ganDuLieuVao();
        setEvent();
    }

    private void setEvent() {
        DBSanPham dbSanPham = new DBSanPham(getApplicationContext());
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

    //    ??nh x???
    private void setControl() {
//      Trang th??ng tin s???n ph???m
        txtTTSPMa = findViewById(R.id.txtTTSPMa);
        txtTTSPTen = findViewById(R.id.txtTTSPTen);
        txtTTSPGia = findViewById(R.id.txtTTSPGia);
        txtTTSPXuatXu = findViewById(R.id.txtTTSPXuatXu);
        imgTTSPHinh = findViewById(R.id.imgTTSPHinh);
        llSuaTTSP = findViewById(R.id.ll_suattsp);
        btnSPSua = findViewById(R.id.btn_sua_sp);
        btnSPXoa = findViewById(R.id.btn_xoa_sp);
        if(mode.equals("xem")){
            btnSPSua.setVisibility(View.INVISIBLE);
            btnSPXoa.setVisibility(View.INVISIBLE);
        }
    }

    private void ganDuLieuVao() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinh(), 0, sanPham.getHinh().length);
//        Th??ng tin s???n ph???m
        txtTTSPMa.setText(sanPham.getMaSP().toString());
        txtTTSPTen.setText(sanPham.getTenSP());
        txtTTSPXuatXu.setText(sanPham.getXuatXu());
        txtTTSPGia.setText(others.numberToVND(sanPham.getDonGia()));
        imgTTSPHinh.setImageBitmap(bitmap);
    }

    private void xoaSanPham() {
        Dialog confirmDialog = others.openConfirmDialog(ThongTinSanPhamActivity.this, "B???n c?? mu???n x??a s???n ph???m n??y?");
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
                DBSanPham dbSanPham = new DBSanPham(getApplicationContext());
                dbSanPham.xoaDL(sanPham);
                confirmDialog.dismiss();
                Dialog successDialog = others.openSuccessDialog(ThongTinSanPhamActivity.this, "X??a s???n ph???m th??nh c??ng");
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