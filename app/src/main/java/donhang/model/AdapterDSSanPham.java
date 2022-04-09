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
import database.model.SanPham;

import java.util.ArrayList;


public class AdapterDSSanPham extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<SanPham> sanPhams;

    public AdapterDSSanPham(@NonNull Context context, int resource, @NonNull ArrayList<SanPham> sanPhams) {
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

        ImageView hinhAnhSanPham = convertView.findViewById(R.id.ivHinhAnhSanPham);
        TextView tenSanPham = convertView.findViewById(R.id.tvTenSanPham),
                maSanPham = convertView.findViewById(R.id.tvMaSanPham),
                soLuongSanPham = convertView.findViewById(R.id.tvSoLuongSanPham),
                giaTienSanPham = convertView.findViewById(R.id.tvGiaTienSanPham);

        SanPham sp = this.sanPhams.get(position);

        tenSanPham.setText(sp.getTenSP());
        maSanPham.setText(String.valueOf(sp.getMaSP()));
        soLuongSanPham.setText("0");
        giaTienSanPham.setText(sp.getDonGia().toString());

        Bitmap bitmap = BitmapFactory.decodeByteArray(sp.getHinh(), 0, sp.getHinh().length);
        hinhAnhSanPham.setImageBitmap(bitmap);

        return convertView;
    }
}
