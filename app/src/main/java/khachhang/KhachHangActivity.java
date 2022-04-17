package khachhang;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import database.DBKhachHang;
import database.model.KhachHang;
import others.Others;

public class KhachHangActivity extends AppCompatActivity {
    ArrayList<KhachHang> DS_khachHang = new ArrayList<>();
    ImageView iv_MoGD_ThemKH;
    ListView lv_DSKhachHang;
    SearchView sv_TimKiemKH;
    Spinner sn_LocKH;
    AdapterKhachHang adapterKhachHang;
    DBKhachHang dbKhachHang;
    Others others = new Others();
    public static final int REQUEST_TO_THEMKH = 1;

    //    private static final int REQUEST_TO_FOLDER = 2;
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
        //
        adapterKhachHang = new AdapterKhachHang(this, R.layout.activity_items_khachhang, DS_khachHang);
        lv_DSKhachHang.setAdapter(adapterKhachHang);

        //
        iv_MoGD_ThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KhachHangActivity.this, ActivityThemKhachHang.class);
                startActivityForResult(intent, REQUEST_TO_THEMKH);
            }
        });

        //
        sv_TimKiemKH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterKhachHang.FindKhachHang(s);
                return false;
            }
        });
        //
        sn_LocKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int option, long l) {
                ((TextView) view).setText(null);
                adapterKhachHang.locBangSpinner(option);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //
        lv_DSKhachHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewSwitcher sw_layoutItemKH = view.findViewById(R.id.sw_layoutItemKH);
                KhachHang khachhang = DS_khachHang.get(i);
                try {
                    sw_layoutItemKH.showNext();
                } catch (Exception ex) {
                    RefreshData();
                    return false;
                }

                // set control
                ImageView iv_avatarKH = view.findViewById(R.id.iv_editAvatarKh);
                TextView tv_editMaKH = view.findViewById(R.id.tv_editMaKH);
                EditText et_editName = view.findViewById(R.id.et_editName);
                EditText et_editPhone = view.findViewById(R.id.et_editPhone);
                EditText et_editAddress = view.findViewById(R.id.et_editAddress);
                Button btn_LuuThongTinKH = view.findViewById(R.id.btn_LuuThongTinKH);
                Button btn_XoaThongTinKH = view.findViewById(R.id.btn_XoaThongTinKH);
                // set data
                Bitmap bitmap = BitmapFactory.decodeByteArray(khachhang.getHINHANH(), 0, khachhang.getHINHANH().length);
                iv_avatarKH.setImageBitmap(bitmap);
                tv_editMaKH.setText("Mã KH: " + khachhang.getMAKH());
                et_editName.setText(khachhang.getTENKH());
                et_editPhone.setText(khachhang.getDIENTHOAI());
                et_editAddress.setText(khachhang.getDIACHI());

                //set event
                btn_XoaThongTinKH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openConfirmDialog(khachhang, "Bạn có chắc muốn xóa khách hàng này?", 1);
                    }
                });

                btn_LuuThongTinKH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = et_editName.getText().toString();
                        String phone = et_editPhone.getText().toString();
                        String address = et_editAddress.getText().toString();
                        if (checkData(name, phone, address)) {
                            khachhang.setTENKH(name);
                            khachhang.setDIENTHOAI(phone);
                            khachhang.setDIACHI(address);
                            openConfirmDialog(khachhang, "Bạn có chắc muốn Cập nhật thông tin khách hàng này?", 2);
                        }

                    }
                });
                return false;
            }
        });

    }


    private void setControl() {
        dbKhachHang = new DBKhachHang(this);
        DS_khachHang = dbKhachHang.GetData();
        iv_MoGD_ThemKH = findViewById(R.id.iv_MoGD_ThemKH);
        lv_DSKhachHang = findViewById(R.id.lv_DSKhachHang);
        sv_TimKiemKH = findViewById(R.id.sv_TimKiemKH);
        sn_LocKH = findViewById(R.id.sn_LocKH);
        setItemsSpinner();
    }

    private void setItemsSpinner() {
        ArrayList<String> ListItems = new ArrayList<>();
        ListItems.add("Lọc theo mã tăng dần");
        ListItems.add("Lọc theo mã giảm dần");
        ListItems.add("Lọc theo tên A-Z");
        ListItems.add("Lọc theo tên Z-A");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ListItems);
        sn_LocKH.setAdapter(arrayAdapter);
        sn_LocKH.setSelection(0);
    }

    private void RefreshData() {
        DS_khachHang.clear();
        DS_khachHang.addAll(dbKhachHang.GetData());
        adapterKhachHang.notifyDataSetChanged();
    }

    private boolean checkData(String name, String phone, String address) {
        if (!others.checkName(name)) {
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

    private void openConfirmDialog(KhachHang khachhang, String title, int tag) {
        Dialog confirmDialog = others.openConfirmDialog(KhachHangActivity.this, title);
        confirmDialog.show();
        TextView btnConfirmDongY = confirmDialog.findViewById(R.id.btn_confirm_dong_y);
        TextView btnConfirmHuy = confirmDialog.findViewById(R.id.btn_confirm_huy_bo);
        btnConfirmHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
            }
        });

        if (tag == 1) {
            btnConfirmDongY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbKhachHang.deleteData(khachhang.getMAKH());
                    confirmDialog.dismiss();
                    openSuccessDialog("Đã Xóa Khách hàng thành công!");
                }
            });
        } else if (tag == 2) {
            btnConfirmDongY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbKhachHang.updateData(khachhang);
                    confirmDialog.dismiss();
                    openSuccessDialog("Cập nhật thông tin khách hàng thành công!");
                }
            });
        }
    }

    private void openSuccessDialog(String title) {
        Dialog successDialog = others.openSuccessDialog(KhachHangActivity.this, title);
        successDialog.show();
        TextView btnSuccessDongY = successDialog.findViewById(R.id.btn_success_dong_y);
        btnSuccessDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefreshData();
                successDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // truong hop them du lieu chi can refesh la DS KHACH HANG
        if (requestCode == REQUEST_TO_THEMKH && resultCode == RESULT_OK) {
            RefreshData();
        }

        // TH lay anh tu folder cap nhat anh khach hang
//        if(requestCode == REQUEST_TO_FOLDER && resultCode == RESULT_OK && data != null){
//
//            Uri uri = data.getData();
//            try {
//                tv_avatarKH.setText(getPath(uri));
//                InputStream inputStream = getContentResolver().openInputStream(uri);
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                iv_avatarKH.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

    }
}