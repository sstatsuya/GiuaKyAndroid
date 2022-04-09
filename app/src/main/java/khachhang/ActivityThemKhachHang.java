package khachhang;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import khachhang.model.DBKhachHang;
import khachhang.model.KhachHang;
import others.Others;

public class ActivityThemKhachHang extends AppCompatActivity {
    private ImageView iv_avatarKH;
    private  Button btn_themKH, btn_huyThemKH;
    private LinearLayout btn_ChonAnh;
    private  EditText ed_tenKH, ed_soDT_KH, ed_diaChiKH;
    private  TextView tv_avatarKH;
    private String name="", phone="", address= "";
    private byte[] avatar = null;
    public static final int REQUEST_TO_FOLDER = 3;
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
        btn_ChonAnh = findViewById(R.id.btn_ChonAnh);
        tv_avatarKH = findViewById(R.id.tv_avatarKH);
        ed_tenKH = findViewById(R.id.ed_tenKH);
        ed_diaChiKH = findViewById(R.id.ed_diaChiKH);
        ed_soDT_KH = findViewById(R.id.ed_soDT_KH);
    }

    private boolean checkInput(){
        if (avatar == null) {
            Toast.makeText(this, "Hãy chọn ảnh đại diện khách hàng!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!others.checkName(name)) {
            Toast.makeText(this, "Tên khách hàng không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!others.checkPhone(phone)) {
            Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!others.checkAddress(address)) {
            Toast.makeText(this, "Địa chỉ không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setEvent() {

        btn_ChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_TO_FOLDER);

            }
        });

        btn_themKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(!tv_avatarKH.getText().equals("")){
                        // chuyen doi hinh tu image view sang mang byte [] de luu
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) iv_avatarKH.getDrawable();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        avatar = byteArrayOutputStream.toByteArray();
                    }
                    // luu cac thuoc tinh con lai cua khach hang
                    name = ed_tenKH.getText().toString().trim();
                    phone = ed_soDT_KH.getText().toString().trim();
                    address = ed_diaChiKH.getText().toString().trim();
                    // kiem tra input trc khi them vao db
                    if (checkInput()){
                        Intent intent = new Intent();
                        KhachHang khachHang = new KhachHang ( avatar, name, phone, address);
                        new DBKhachHang(ActivityThemKhachHang.this).InsertData(khachHang);
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


    // result lay anh tu gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_TO_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                tv_avatarKH.setText(getPath(uri));
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                iv_avatarKH.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    // lay path image tu uri data
    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return uri.getPath();
    }

    private void openSuccessDialog() {
        Dialog successDialog = others.openSuccessDialog(ActivityThemKhachHang.this, "Thêm khách hàng mới thành công");
        successDialog.show();
        TextView btnSuccessDongY = successDialog.findViewById(R.id.btn_success_dong_y);
        btnSuccessDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                successDialog.dismiss();
                onBackPressed();
            }
        });
    }
}