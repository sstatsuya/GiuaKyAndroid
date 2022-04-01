package ngocthach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuakyandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ngocthach.model.KhachHang;

public class AdapterKhachHang extends ArrayAdapter<KhachHang> {
    private Context context;
    private int resource;
    private ArrayList<KhachHang> DS_khachHang;

    public AdapterKhachHang(@NonNull Context context, int resource, @NonNull ArrayList<KhachHang> DS_khachHang) {
        super(context, resource, DS_khachHang);
        this.context = context;
        this.resource = resource;
        this.DS_khachHang = DS_khachHang;
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
        Picasso.get().load(khachHang.getAvatar()).into(iv_avatar);
        tv_MaKH.setText("Mã KH: " + khachHang.getId() + "");
        tv_Name.setText("Họ tên: " + khachHang.getName().trim());
        tv_Phone.setText("SĐT: " + khachHang.getPhone().trim());
        tv_Address.setText("Địa chỉ: " + khachHang.getAddress().trim());
        return convertView;
    }
}
