package donhang.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuakyandroid.R;

import java.util.ArrayList;

import database.model.SanPhamDonHang;


public class AdapterSuaDonHangDSSanPham extends ArrayAdapter {
    Context context;
    int resource;
    public ArrayList<SanPhamDonHang> sanPhams;

    ImageView hinhAnhSanPham;
    TextView tenSanPham, maSanPham, soLuongSanPham, giaTienSanPham;

    public AdapterSuaDonHangDSSanPham(@NonNull Context context, int resource, @NonNull ArrayList<SanPhamDonHang> sanPhams) {
        super(context, resource, sanPhams);
        this.context = context;
        this.resource = resource;
        this.sanPhams = sanPhams;
    }

    @Override
    public int getCount() {
        return this.sanPhams.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        SanPhamDonHang sp = this.sanPhams.get(position);
        //Match Control
        this.hinhAnhSanPham = convertView.findViewById(R.id.iv_itemsuamathang_hinhanhsanpham);
        this.tenSanPham = convertView.findViewById(R.id.tv_itemsuamathang_tensanpham);
        this.maSanPham = convertView.findViewById(R.id.tv_itemsuamathang_masanpham);
        this.soLuongSanPham = convertView.findViewById(R.id.tv_itemsuamathang_soluongsanpham);
        this.giaTienSanPham = convertView.findViewById(R.id.tv_itemsuamathang_giatiensanpham);
        //Set value
        this.tenSanPham.setText(sp.getTenSP());
        this.maSanPham.setText("Mã sản phẩm " + String.valueOf(sp.getMaSP()));
        this.soLuongSanPham.setText("Số lượng: " + String.valueOf(sp.getSoLuong()));
        this.giaTienSanPham.setText("Giá " + String.valueOf(sp.getDonGia()) + "vnd");
        Bitmap bitmap = BitmapFactory.decodeByteArray(sp.getHinh(), 0, sp.getHinh().length);
        this.hinhAnhSanPham.setImageBitmap(bitmap);
        return convertView;
    }
}
