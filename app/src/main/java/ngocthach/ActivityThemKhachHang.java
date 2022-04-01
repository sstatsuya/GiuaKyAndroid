package ngocthach;

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

import ngocthach.model.DBKhachHang;
import ngocthach.model.KhachHang;

public class ActivityThemKhachHang extends AppCompatActivity {
    private ImageView iv_avatarKH;
    private  Button btn_themKH, btn_huyThemKH;
    private  EditText ed_avatarKH, ed_tenKH, ed_soDT_KH, ed_diaChiKH;
    private String avatar ="", name="", phone="", address= "";
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
                        intent.putExtra("khachhang", khachHang);
                        setResult(RESULT_OK, intent);
                        openSuccessDialog(Gravity.CENTER, "Thêm mới khách hàng thành công!");
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
                dialog.dismiss();
                finish();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}