package com.example.giuakyandroid.tien;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.tien.model.SanPham;
import com.squareup.picasso.Picasso;

public class ThemSanPhamActivity extends AppCompatActivity {
    EditText txtTSPTen, txtTSPGia, txtTSPHinh, txtTSPXuatXu;
    TextView btnTSPDongY, btnTSPHuy;
    ImageView imgTSPHinh;
    Button btnXemTruocHinh;

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
//        Nut xem truoc hinh bam vao
        btnXemTruocHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTSPHinh.getText().toString().equals("")){
                    Toast.makeText(ThemSanPhamActivity.this, "Link hình không được trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                Picasso.get()
                        .load(txtTSPHinh.getText().toString()).into(imgTSPHinh);
            }
        });

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
                if(!checkInput()){
                    Toast.makeText(ThemSanPhamActivity.this, "Thông tin không được trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                openSuccessDialog(Gravity.CENTER, "Thêm sản phẩm mới thành công");
            }
        });

    }

    private SanPham getDuLieu(){
        SanPham sanPham = new SanPham();
        sanPham.setMaSP("tam");
        sanPham.setTenSP(txtTSPTen.getText().toString());
        sanPham.setDonGia(Integer.parseInt(txtTSPGia.getText().toString()));
        sanPham.setXuatXu(txtTSPXuatXu.getText().toString());
        sanPham.setLinkHinhAnh(txtTSPHinh.getText().toString());
        return sanPham;
    }

    private void setControl() {
        txtTSPTen = findViewById(R.id.txtTSPTen);
        txtTSPGia = findViewById(R.id.txtTSPGia);
        txtTSPHinh = findViewById(R.id.txtTSPHinh);
        txtTSPXuatXu = findViewById(R.id.txtTSPXuatXu);
        btnTSPDongY = findViewById(R.id.btn_them_sp_dong_y);
        btnTSPHuy = findViewById(R.id.btn_them_sp_huy);
        imgTSPHinh = findViewById(R.id.imgTSPHinh);
        btnXemTruocHinh = findViewById(R.id.btn_xem_truoc_hinh);
    }

    private void openSuccessDialog(int gravity, String noiDung) {
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
                Intent intent = new Intent();
                intent.putExtra("sanPham", getDuLieu());
                setResult(1, intent);
                finish();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

//    Check input trước khi thêm sản phẩm
    private boolean checkInput(){
        if(txtTSPGia.getText().toString().equals(""))return false;
        if(txtTSPTen.getText().toString().equals(""))return false;
        if(txtTSPXuatXu.getText().toString().equals(""))return false;
        if(txtTSPHinh.getText().toString().equals(""))return false;
        return true;
    }
}