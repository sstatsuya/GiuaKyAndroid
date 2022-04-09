package khachhang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuakyandroid.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import database.model.KhachHang;

public class AdapterKhachHang extends ArrayAdapter<KhachHang> {
    private Context context;
    private int resource;
    private ArrayList<KhachHang> DS_khachHang;
    private ArrayList<KhachHang> DS_khachHangBK = new ArrayList<>();

    public AdapterKhachHang(@NonNull Context context, int resource, @NonNull ArrayList<KhachHang> DS_khachHang) {
        super(context, resource, DS_khachHang);
        this.context = context;
        this.resource = resource;
        this.DS_khachHang = DS_khachHang;
        this.DS_khachHangBK.addAll(DS_khachHang);
    }

    @Override
    public int getCount() {
        return DS_khachHang.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        TextView tv_Name = convertView.findViewById(R.id.tv_Name);
        ImageView iv_avatar = convertView.findViewById(R.id.iv_avatar);
        TextView tv_Phone = convertView.findViewById(R.id.tv_Phone);
        TextView tv_Address = convertView.findViewById(R.id.tv_Address);
        TextView tv_MaKH = convertView.findViewById(R.id.tv_MaKH);
        KhachHang khachHang = DS_khachHang.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(khachHang.getHINHANH(),0,khachHang.getHINHANH().length);
        iv_avatar.setImageBitmap(bitmap);
        tv_MaKH.setText("Mã KH: " + khachHang.getMAKH() + "");
        tv_Name.setText("Họ tên: " + khachHang.getTENKH().trim());
        tv_Phone.setText("SĐT: " + khachHang.getDIENTHOAI().trim());
        tv_Address.setText("Địa chỉ: " + khachHang.getDIACHI().trim());
        return convertView;
    }

    public void FindKhachHang(String strTENKH) {
        DS_khachHang.clear();
        strTENKH = strTENKH.trim().toLowerCase();
        if(strTENKH.equals("")) DS_khachHang.addAll(DS_khachHangBK);
        else {
            for (KhachHang khachHang: DS_khachHangBK
                 ) {
                if(khachHang.getTENKH().toLowerCase().contains(strTENKH))
                    DS_khachHang.add(khachHang);
            }
        }
        notifyDataSetChanged();
    }

    public void locBangSpinner(int option) {
        switch (option){
            case 0:{
                Collections.sort(DS_khachHang, new Comparator<KhachHang>() {
                    @Override
                    public int compare(KhachHang khachHang1, KhachHang khachHang2) {
                        return (khachHang1.getMAKH() > khachHang2.getMAKH()) ? 1 : -1;
                    }
                });


                break;
            }
            case  1:{
                Collections.sort(DS_khachHang, new Comparator<KhachHang>() {
                    @Override
                    public int compare(KhachHang khachHang1, KhachHang khachHang2) {
                        return (khachHang1.getMAKH() > khachHang2.getMAKH()) ? -1 : 1;
                    }
                });
                break;
            } case  2:{
                Collections.sort(DS_khachHang, new Comparator<KhachHang>() {
                    @Override
                    public int compare(KhachHang khachHang1, KhachHang khachHang2) {
                        String Name1 = khachHang1.getTENKH();
                        String Name2 = khachHang2.getTENKH();
                        String lastName1 = Name1;
                        String lastName2 = Name2;
                        if(Name1.contains(" ")){
                            lastName1 = Name1.substring(Name1.lastIndexOf(" "));

                        }
                        if(Name2.contains(" ")){
                            lastName2 = Name2.substring(Name2.lastIndexOf(" "));

                        }
                        return lastName1.compareToIgnoreCase(lastName2);
                    }
                });
                break;
            }
            case 3: {
                Collections.sort(DS_khachHang, new Comparator<KhachHang>() {
                    @Override
                    public int compare(KhachHang khachHang1, KhachHang khachHang2) {
                        String Name1 = khachHang1.getTENKH();
                        String Name2 = khachHang2.getTENKH();
                        String lastName1 = Name1;
                        String lastName2 = Name2;
                        if(Name1.contains(" ")){
                            lastName1 = Name1.substring(Name1.lastIndexOf(" "));

                        }
                        if(Name2.contains(" ")){
                            lastName2 = Name2.substring(Name2.lastIndexOf(" "));

                        }
                        return lastName2.compareToIgnoreCase(lastName1);
                    }
                });
                break;
            }
        }
        notifyDataSetChanged();
    }
}
