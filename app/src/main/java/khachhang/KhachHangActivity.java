package khachhang;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    KhachHang khachHangTemp ;
    public static final int REQUEST_TO_THEMKH = 1;
    public static final int REQUEST_TO_FOLDER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_khachhang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setEvent() {
        //
        adapterKhachHang = new AdapterKhachHang(this, R.layout.layout_items_khachhang, DS_khachHang);
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
                ViewSwitcher sw_layoutItemKH ;
                LinearLayout ll_itemEditThongTin ;
                KhachHang khachhang = DS_khachHang.get(i);
                try {
                    sw_layoutItemKH = view.findViewById(R.id.sw_layoutItemKH);
                    ll_itemEditThongTin = sw_layoutItemKH.findViewById(R.id.ll_itemEditThongTin);
                    if(sw_layoutItemKH.getCurrentView() != ll_itemEditThongTin){
                        sw_layoutItemKH.showNext();
                    }
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
                Button btn_HuyEditKH = view.findViewById(R.id.btn_HuyEditKH);
                // set data
                Bitmap bitmap = BitmapFactory.decodeByteArray(khachhang.getHINHANH(), 0, khachhang.getHINHANH().length);
                iv_avatarKH.setImageBitmap(bitmap);
                tv_editMaKH.setText("M?? KH: " + khachhang.getMAKH());
                et_editName.setText(khachhang.getTENKH());
                et_editPhone.setText(khachhang.getDIENTHOAI());
                et_editAddress.setText(khachhang.getDIACHI());
                //set event
                btn_HuyEditKH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(sw_layoutItemKH.getCurrentView() != ll_itemEditThongTin)
                            sw_layoutItemKH.showNext();
                    }
                });
                iv_avatarKH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        khachHangTemp = khachhang;
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_TO_FOLDER);
                    }
                });
                btn_XoaThongTinKH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openConfirmDialog(khachhang, "B???n c?? ch???c mu???n x??a kh??ch h??ng n??y?", 1);
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
                            openConfirmDialog(khachhang, "B???n c?? ch???c mu???n C???p nh???t th??ng tin kh??ch h??ng n??y?", 2);
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
        ListItems.add("L???c theo m?? t??ng d???n");
        ListItems.add("L???c theo m?? gi???m d???n");
        ListItems.add("L???c theo t??n A-Z");
        ListItems.add("L???c theo t??n Z-A");
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
            Toast.makeText(this, "T??n kh??ch h??ng kh??ng h???p l???!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!others.checkPhone(phone)) {
            Toast.makeText(this, "S??? ??i???n tho???i kh??ng h???p l???!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!others.checkAddress(address)) {
            Toast.makeText(this, "?????a ch??? kh??ng h???p l???!", Toast.LENGTH_SHORT).show();
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
                    openSuccessDialog("???? X??a Kh??ch h??ng th??nh c??ng!");
                }
            });
        } else if (tag == 2) {
            btnConfirmDongY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbKhachHang.updateData(khachhang);
                    confirmDialog.dismiss();
                    openSuccessDialog("C???p nh???t th??ng tin kh??ch h??ng th??nh c??ng!");
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

//         TH lay anh tu folder cap nhat anh khach hang
        if(requestCode == REQUEST_TO_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                khachHangTemp.setHINHANH(byteArrayOutputStream.toByteArray());
                dbKhachHang.updateData(khachHangTemp);
                RefreshData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}