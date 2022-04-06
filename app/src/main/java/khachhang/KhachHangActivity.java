package khachhang;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import khachhang.model.DBKhachHang;
import khachhang.model.KhachHang;
import others.Others;
import sanpham.model.dbSanPham;

public class KhachHangActivity extends AppCompatActivity {
    // set cung data de lam giao dien
    ArrayList<KhachHang> DS_khachHang = new ArrayList<>();
    ImageView iv_MoGD_ThemKH;
    ListView lv_DSKhachHang;
    AdapterKhachHang adapterKhachHang;
    DBKhachHang dbKhachHang;
    public static final int REQUEST_TO_THEMKH = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }
    private void setEvent() {
        adapterKhachHang = new AdapterKhachHang(this, R.layout.activity_items_khachhang,DS_khachHang);
        lv_DSKhachHang.setAdapter(adapterKhachHang);

        iv_MoGD_ThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KhachHangActivity.this, ActivityThemKhachHang.class);
                startActivityForResult(intent,REQUEST_TO_THEMKH);
            }
        });

        lv_DSKhachHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewSwitcher sw_layoutItemKH = view.findViewById(R.id.sw_layoutItemKH);
                KhachHang khachhang = DS_khachHang.get(i);
                sw_layoutItemKH.showNext();
                // anh xa
                ImageView iv_avatarKH = view.findViewById(R.id.iv_editAvatarKh);
                TextView tv_editMaKH = view.findViewById(R.id.tv_editMaKH);
                EditText et_editName = view.findViewById(R.id.et_editName);
                EditText et_editPhone = view.findViewById(R.id.et_editPhone);
                EditText et_editAddress = view.findViewById(R.id.et_editAddress);
                Button btn_LuuThongTinKH = view.findViewById(R.id.btn_LuuThongTinKH);
                Button btn_XoaThongTinKH = view.findViewById(R.id.btn_XoaThongTinKH);
                // set data
                Bitmap bitmap = BitmapFactory.decodeByteArray(khachhang.getAvatar(),0,khachhang.getAvatar().length);
                iv_avatarKH.setImageBitmap(bitmap);
                tv_editMaKH.setText("Mã KH: " + khachhang.getId());
                et_editName.setText(khachhang.getName());
                et_editPhone.setText(khachhang.getPhone());
                et_editAddress.setText(khachhang.getAddress());

                // set event // chua xu ly show dialog
                btn_XoaThongTinKH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbKhachHang.DeleteData(khachhang.getId());

                        RefreshData();
                    }
                });

                // chua xu ly show dialog
                btn_LuuThongTinKH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = et_editName.getText().toString();
                        String phone = et_editPhone.getText().toString();
                        String address = et_editAddress.getText().toString();
                        if(checkData(name,phone,address)){
                            khachhang.setName(name);
                            khachhang.setPhone(phone);
                            khachhang.setAddress(address);
                            dbKhachHang.updateData(khachhang);
                            RefreshData();
                        }

                    }
                });

                return false;
            }
        });
    }

    private boolean checkData(String name, String phone, String address){
        if (!name.matches("^[a-zA-Z\\s]+")) {
            Toast.makeText(this, "Tên khách hàng không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false ;
        }
        else if (!phone.matches("^[0]\\d{9}$")) {
            Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false ;
        }
        else if (address.equals("")) {
            Toast.makeText(this, "Địa chỉ không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false ;
        }
        return true;
    }
    private void setControl() {
        dbKhachHang = new DBKhachHang(this);
        DS_khachHang = dbKhachHang.GetData();
        iv_MoGD_ThemKH = findViewById(R.id.iv_MoGD_ThemKH);
        lv_DSKhachHang = findViewById(R.id.lv_DSKhachHang);
    }

    private void RefreshData(){
        DS_khachHang.clear();
        DS_khachHang.addAll(dbKhachHang.GetData());
        adapterKhachHang.notifyDataSetChanged();
    }

    private void openConfirmDialog() {
        //others.
    }

    private void openSuccessDialog() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        RefreshData();

//        if(requestCode == REQUEST_TO_THEMKH && resultCode == RESULT_OK){
//            KhachHang khachHang = (KhachHang) intent.getSerializableExtra("khachhang");
//            if (khachHang != null){
//                DS_khachHang.add(khachHang);
//                adapterKhachHang.notifyDataSetChanged(); //Reload adapter
//            }
//        }

    }
}