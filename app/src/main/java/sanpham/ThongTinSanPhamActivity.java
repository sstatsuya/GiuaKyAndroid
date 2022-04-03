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
import sanpham.model.SanPham;
import com.squareup.picasso.Picasso;

public class ThongTinSanPhamActivity extends AppCompatActivity {
    SanPham sanPham;
    TextView txtTTSPMa, txtTTSPTen, txtTTSPGia, txtTTSPXuatXu;
    ImageView imgTTSPHinh;
    TextView btnSPSua, btnSPXoa;
    LinearLayout llSuaTTSP, llTTSP;

    TextView txtSTTSPMa,  btnSTTSPDongY, btnSTTSPHuy;
    EditText txtSTTSPTen, txtSTTSPGia, txtSTTSPXuatXu;
    ImageView imgSTTSPHinh;

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
                openConfirmDialog(Gravity.CENTER);
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
                openSuccessDialog(Gravity.CENTER, "Sửa thành công", 2);
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

    private void openConfirmDialog(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_confirm);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        TextView btnConfirmDongY, btnConfirmHuyBo, txtConfirmNoiDung;
        txtConfirmNoiDung = dialog.findViewById(R.id.txt_confirm_noi_dung);
        btnConfirmHuyBo = dialog.findViewById(R.id.btn_confirm_huy_bo);
        btnConfirmDongY = dialog.findViewById(R.id.btn_confirm_dong_y);

        txtConfirmNoiDung.setText("Bạn có muốn xóa sản phẩm " + sanPham.getTenSP());

        btnConfirmHuyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnConfirmDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openSuccessDialog(Gravity.CENTER, "Xóa thành công", 1);
            }
        });


        dialog.show();
    }

    //mode:1 xem, 2: sua
    private void openSuccessDialog(int gravity, String noiDung, int mode) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_success);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        TextView txtSuccessNoiDung = dialog.findViewById(R.id.txt_success_noi_dung);
        TextView txtSucessDongY = dialog.findViewById(R.id.btn_success_dong_y);

        txtSuccessNoiDung.setText(noiDung);
        txtSucessDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(mode==1){
                    onBackPressed();
                }
                else if(mode==2){
                    btnSTTSPHuy.callOnClick();
                }
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}