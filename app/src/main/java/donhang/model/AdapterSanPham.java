package donhang.model;

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

import sanpham.model.SanPham;

public class AdapterSanPham extends ArrayAdapter<SanPham> {
    Context context;
    int resource;
    ArrayList<SanPham> data;

    public AdapterSanPham(@NonNull Context context, int resource, @NonNull ArrayList<SanPham> data) {
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

        ImageView ivTTSPHinh = convertView.findViewById(R.id.iv_ttdh_hinh_sp);
        TextView tvTTSPMa = convertView.findViewById(R.id.tv_ttdh_ma_sp);
        TextView tvTTSPTen = convertView.findViewById(R.id.tv_ttdh_ten_sp);
//        TextView tvTTSPSoLuong = convert
//        View.findViewById(R.id.tv_ttdh_soluong_sp);
//        SanPham sanPham = data.get(position);
//
//        tvTTSPMa.setText(sanPham.getMaSP());
//        tvTTSPTen.setText(sanPham.getTenSP());
//        tvTTSPSoLuong.setText("Chua biet so luong");


//        Picasso.get().load(sanPham.getLinkHinhAnh()).into(ivTTSPHinh);
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
