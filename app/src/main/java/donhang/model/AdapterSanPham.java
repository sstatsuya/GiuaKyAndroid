package donhang.model;

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
import database.model.SanPhamDonHang;

public class AdapterSanPham extends ArrayAdapter<SanPhamDonHang> {
    Context context;
    int resource;
    ArrayList<SanPhamDonHang> data;

    public AdapterSanPham(@NonNull Context context, int resource, @NonNull ArrayList<SanPhamDonHang> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        SanPhamDonHang sp = this.data.get(position);

        System.out.println("----------------" + this.data.size());

        ImageView ivTTSPHinh = convertView.findViewById(R.id.iv_ttdh_hinh_sp);
        TextView tvTTSPMa = convertView.findViewById(R.id.tv_ttdh_ma_sp);
        TextView tvTTSPTen = convertView.findViewById(R.id.tv_ttdh_ten_sp);
        TextView tvTTSPSoLuong = convertView.findViewById(R.id.tv_ttdh_soluong_sp);
        TextView tvTTSPGiaTien = convertView.findViewById(R.id.tv_ttdh_giatien_sp);

        ivTTSPHinh.setImageBitmap(BitmapFactory.decodeByteArray(sp.getHinh(), 0, sp.getHinh().length));
        tvTTSPMa.setText("Mã sản phẩm " + String.valueOf(sp.getMaSP()));
        tvTTSPTen.setText(sp.getTenSP());
        tvTTSPSoLuong.setText(String.valueOf(sp.getSoLuong()));
        tvTTSPGiaTien.setText(String.valueOf(sp.getDonGia()));

//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, EditDanhLamActivity.class);
//                intent.putExtra("danhLam", danhLam);
//                ((MainActivity) context).startActivityForResult(intent, 2);
//            }
//        });

        return convertView;
    }
}
