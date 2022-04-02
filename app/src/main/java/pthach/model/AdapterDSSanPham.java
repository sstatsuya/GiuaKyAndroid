package pthach.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.NotificationCompat;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.tien.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


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

        ImageView hinhAnhSanPham =convertView.findViewById(R.id.ivHinhAnhSanPham);
        TextView tenSanPham = convertView.findViewById(R.id.tvTenSanPham),
                maSanPham = convertView.findViewById(R.id.tvMaSanPham),
                soLuongSanPham = convertView.findViewById(R.id.tvSoLuongSanPham),
                giaTienSanPham = convertView.findViewById(R.id.tvGiaTienSanPham);

        SanPham sp = this.sanPhams.get(position);

        tenSanPham.setText(sp.getTenSP());
        maSanPham.setText(sp.getMaSP());
        soLuongSanPham.setText("0");
        giaTienSanPham.setText(sp.getDonGia().toString());

        Picasso.get().load(sp.getMaSP()).into(hinhAnhSanPham);

        return convertView;
    }
}
