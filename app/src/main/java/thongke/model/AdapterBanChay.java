package thongke.model;

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

import database.model.SanPham;

public class AdapterBanChay extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<BanChay> banChays;

    public AdapterBanChay(@NonNull Context context, int resource, @NonNull ArrayList<BanChay> banChays) {
        super(context, resource, banChays);
        this.context = context;
        this.resource = resource;
        this.banChays = banChays;
    }

    @Override
    public int getCount() {
        return this.banChays.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        ImageView hinhAnhSanPham = convertView.findViewById(R.id.iv_itemsanphambanchay_hinhanhsanpham);
        TextView tenSanPham = convertView.findViewById(R.id.tv_itemsanphambanchay_tensanpham);
        TextView maSanPham = convertView.findViewById(R.id.tv_itemsanphambanchay_masanpham);
        TextView soLuong = convertView.findViewById(R.id.tv_itemsanphambanchay_soluong);

        BanChay banChay = this.banChays.get(position);
        tenSanPham.setText("Tên sản phẩm: " + banChay.getTenSP());
        maSanPham.setText("Mã sản phẩm: " + String.valueOf(banChay.getMaSP()));
        soLuong.setText("Số lượng: " + String.valueOf(banChay.getSoLuong()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(banChay.getHinh(), 0, banChay.getHinh().length);
        hinhAnhSanPham.setImageBitmap(bitmap);

        return convertView;
    }
}
