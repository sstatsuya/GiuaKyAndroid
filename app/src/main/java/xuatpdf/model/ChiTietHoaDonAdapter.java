package xuatpdf.model;

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

/**
 * Project: GiuaKyAndroid
 * Author: Tran Van Tu
 * Date: 4/17/2022 3:56 PM
 * Desc:
 */
public class ChiTietHoaDonAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ChiTietHoaDon> chiTietHoaDons;

    public ChiTietHoaDonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ChiTietHoaDon> chiTietHoaDons) {
        super(context, resource, chiTietHoaDons);
        this.context = context;
        this.resource = resource;
        this.chiTietHoaDons = chiTietHoaDons;
    }

    @Override
    public int getCount() {
        return this.chiTietHoaDons.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        ImageView hinhAnhSanPham = convertView.findViewById(R.id.iv_item_pdf_image);
        TextView tenSanPham = convertView.findViewById(R.id.tv_item_pdf_tenSanPham);
        TextView maSanPham = convertView.findViewById(R.id.tv_item_pdf_maSanPham);
        TextView soLuong = convertView.findViewById(R.id.tv_item_pdf_soLuong);
        TextView donGia = convertView.findViewById(R.id.tv_item_pdf_donGia);
        TextView thanhTien = convertView.findViewById(R.id.tv_item_pdf_thanhTien);

        ChiTietHoaDon chiTietHoaDon = this.chiTietHoaDons.get(position);
        tenSanPham.setText(chiTietHoaDon.getTenSP());
        maSanPham.setText("Mã sản phẩm: " + chiTietHoaDon.getMaSP());
        soLuong.setText("Số lượng: " + Math.round(chiTietHoaDon.getSoLuong()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(chiTietHoaDon.getHinh(), 0, chiTietHoaDon.getHinh().length);
        hinhAnhSanPham.setImageBitmap(bitmap);
        donGia.setText("Đơn giá: " + Math.round(chiTietHoaDon.getDonGia()) + "VNĐ");
        thanhTien.setText("Thành tiền: " + Math.round(chiTietHoaDon.getThanhTien()) + "VNĐ");
        return convertView;
    }
}
