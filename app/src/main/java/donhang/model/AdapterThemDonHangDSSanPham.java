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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuakyandroid.R;
import database.model.SanPham;
import database.model.SanPhamDonHang;

import java.util.ArrayList;


public class AdapterThemDonHangDSSanPham extends ArrayAdapter {
    Context context;
    int resource;
    public ArrayList<SanPhamDonHang> sanPhams;

    ImageView hinhAnhSanPham;
    TextView tenSanPham, maSanPham, soLuongSanPham, giaTienSanPham;
    Button btnXemSanPham, btnCongSoLuongSanPham, btnTruSoLuongSanPham;

    public AdapterThemDonHangDSSanPham(@NonNull Context context, int resource, @NonNull ArrayList<SanPhamDonHang> sanPhams) {
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
        this.hinhAnhSanPham = convertView.findViewById(R.id.iv_itemthemmathang_hinhanhsanpham);
        this.tenSanPham = convertView.findViewById(R.id.tv_itemthemmathang_tensanpham);
        this.maSanPham = convertView.findViewById(R.id.tv_itemthemmathang_masanpham);
        this.soLuongSanPham = convertView.findViewById(R.id.tv_itemthemmathang_soluongsanpham);
        this.giaTienSanPham = convertView.findViewById(R.id.tv_itemthemmathang_giatiensanpham);
        this.btnXemSanPham = convertView.findViewById(R.id.btn_itemthemmathang_xoasanpham);
        this.btnCongSoLuongSanPham = convertView.findViewById(R.id.btn_itemthemmathang_soluongsanpham_cong);
        this.btnTruSoLuongSanPham = convertView.findViewById(R.id.btn_itemthemmathang_soluongsanpham_tru);
        //Set value
        this.tenSanPham.setText(sp.getTenSP());
        this.maSanPham.setText("Mã sản phẩm " + String.valueOf(sp.getMaSP()));
        this.soLuongSanPham.setText(String.valueOf(sp.getSoLuong()));
        this.giaTienSanPham.setText("Giá " + String.valueOf(sp.getDonGia()) + "vnd");
        Bitmap bitmap = BitmapFactory.decodeByteArray(sp.getHinh(), 0, sp.getHinh().length);
        this.hinhAnhSanPham.setImageBitmap(bitmap);
        //Event
        this.btnCongSoLuongSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sanPhams.get(position).setSoLuong(sp.getSoLuong() + 1);
                soLuongSanPham.setText(String.valueOf(sp.getSoLuong()));
                notifyDataSetChanged();
            }
        });

        this.btnTruSoLuongSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sanPhams.get(position).getSoLuong() != 1)
                    sanPhams.get(position).setSoLuong(sanPhams.get(position).getSoLuong() - 1);
                soLuongSanPham.setText(String.valueOf(sanPhams.get(position).getSoLuong()));
                notifyDataSetChanged();
            }
        });

        this.btnXemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sanPhams.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
