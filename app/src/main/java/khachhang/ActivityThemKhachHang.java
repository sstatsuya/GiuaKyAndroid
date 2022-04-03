package khachhang;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.squareup.picasso.Picasso;

import khachhang.model.DBKhachHang;
import khachhang.model.KhachHang;
import others.Others;

public class ActivityThemKhachHang extends AppCompatActivity {
    private ImageView iv_avatarKH;
    private  Button btn_themKH, btn_huyThemKH;
    private  EditText ed_avatarKH, ed_tenKH, ed_soDT_KH, ed_diaChiKH;
    private String avatar ="", name="", phone="", address= "";
    Others others = new Others();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));

        setControl();
        setEvent();

    }

    private void setControl() {
        iv_avatarKH = findViewById(R.id.iv_avatarKH);
        btn_themKH = findViewById(R.id.btn_themKH);
        btn_huyThemKH = findViewById(R.id.btn_huyThemKH);
        ed_avatarKH = findViewById(R.id.ed_avatarKH);
        ed_tenKH = findViewById(R.id.ed_tenKH);
        ed_diaChiKH = findViewById(R.id.ed_diaChiKH);
        ed_soDT_KH = findViewById(R.id.ed_soDT_KH);
    }

    private boolean checkInput(){
        if (avatar.equals("")) {
            Toast.makeText(this, "Hãy chọn ảnh đại diện khách hàng!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (name.equals("")) {
            Toast.makeText(this, "Tên khách hàng không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (phone.equals("")) {
            Toast.makeText(this, "Số điện thoại không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (address.equals("")) {
            Toast.makeText(this, "Địa chỉ không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setEvent() {

        ed_avatarKH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                avatar = editable.toString();
                if(avatar != "")  Picasso.get().load(avatar).into(iv_avatarKH);
            }
        });

        btn_themKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    avatar = ed_avatarKH.getText().toString().trim();
                    name = ed_tenKH.getText().toString().trim();
                    phone = ed_soDT_KH.getText().toString().trim();
                    address = ed_diaChiKH.getText().toString().trim();
                    if (checkInput()){
                        Intent intent = new Intent();
                        KhachHang khachHang = new KhachHang ( avatar, name, phone, address);
                        new DBKhachHang(ActivityThemKhachHang.this).InsertData(khachHang);
                        intent.putExtra("donhang", khachHang);
                        setResult(RESULT_OK, intent);
                        openSuccessDialog();
                    }

            }
        });
        btn_huyThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void openSuccessDialog() {
        Dialog successDialog = others.openSuccessDialog(ActivityThemKhachHang.this, "Thêm khách hàng mới thành công");
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
}