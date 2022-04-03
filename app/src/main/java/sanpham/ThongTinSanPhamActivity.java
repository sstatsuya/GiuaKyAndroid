package sanpham;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
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

import com.example.giuakyandroid.R;

import others.Others;
import sanpham.model.SanPham;

import com.squareup.picasso.Picasso;

public class ThongTinSanPhamActivity extends AppCompatActivity {
    SanPham sanPham;
    TextView txtTTSPMa, txtTTSPTen, txtTTSPGia, txtTTSPXuatXu;
    ImageView imgTTSPHinh;
    TextView btnSPSua, btnSPXoa;
    LinearLayout llSuaTTSP, llTTSP;

    TextView txtSTTSPMa, btnSTTSPDongY, btnSTTSPHuy;
    EditText txtSTTSPTen, txtSTTSPGia, txtSTTSPXuatXu;
    ImageView imgSTTSPHinh;

    Others others = new Others();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_san_pham);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnSPXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaSanPham();
            }
        });
        btnSPSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llTTSP.setVisibility(View.GONE);
                llSuaTTSP.setVisibility(View.VISIBLE);
            }
        });
        btnSTTSPDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaSanPham();
            }
        });
        btnSTTSPHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llTTSP.setVisibility(View.VISIBLE);
                llSuaTTSP.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(0, intent);
        finish();
    }

    private void setControl() {
        getDuLieu();
        txtTTSPMa = findViewById(R.id.txtTTSPMa);
        txtTTSPTen = findViewById(R.id.txtTTSPTen);
        txtTTSPGia = findViewById(R.id.txtTTSPGia);
        txtTTSPXuatXu = findViewById(R.id.txtTTSPXuatXu);

        txtSTTSPMa = findViewById(R.id.txtSTTSPMa);
        txtSTTSPTen = findViewById(R.id.txtSTTSPTen);
        txtSTTSPGia = findViewById(R.id.txtSTTSPGia);
        txtSTTSPXuatXu = findViewById(R.id.txtSTTSPXuatXu);
        btnSTTSPDongY = findViewById(R.id.btn_sua_ttsp_dong_y);
        btnSTTSPHuy = findViewById(R.id.btn_sua_ttsp_huy);
        ImageView imgSTTSPHinh = findViewById(R.id.imgSTTSPHinh);
        Picasso.get().load(sanPham.getLinkHinhAnh()).into(imgSTTSPHinh);
        txtSTTSPTen.setText(sanPham.getTenSP());
        txtSTTSPGia.setText(sanPham.getDonGia().toString());
        txtSTTSPXuatXu.setText(sanPham.getXuatXu());
        txtSTTSPMa.setText(sanPham.getMaSP());


        imgTTSPHinh = findViewById(R.id.imgTTSPHinh);
        txtTTSPMa.setText(sanPham.getMaSP());
        txtTTSPTen.setText(sanPham.getTenSP());
        txtTTSPXuatXu.setText(sanPham.getXuatXu());
        txtTTSPGia.setText(sanPham.getDonGia().toString());
        Picasso.get().load(sanPham.getLinkHinhAnh()).into(imgTTSPHinh);
        btnSPSua = findViewById(R.id.btn_sua_sp);
        btnSPXoa = findViewById(R.id.btn_xoa_sp);
        llTTSP = findViewById(R.id.ll_ttsp);
        llSuaTTSP = findViewById(R.id.ll_suattsp);
    }

    private void getDuLieu() {
        sanPham = (SanPham) getIntent().getSerializableExtra("sanPham");
    }

    public void suaSanPham(){
        Dialog confirmDialog = others.openConfirmDialog(ThongTinSanPhamActivity.this, "Bạn có muốn sửa sản phẩm này?");
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
                confirmDialog.dismiss();
                Dialog successDialog = others.openSuccessDialog(ThongTinSanPhamActivity.this, "Sửa sản phẩm thành công");
                successDialog.show();
                TextView btnSuccessDongY = successDialog.findViewById(R.id.btn_success_dong_y);
                btnSuccessDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        successDialog.dismiss();
                        btnSTTSPHuy.callOnClick();
                    }
                });
            }
        });
    }

    public void xoaSanPham(){
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
}