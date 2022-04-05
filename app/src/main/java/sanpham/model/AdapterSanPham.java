package sanpham.model;

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

import com.squareup.picasso.Picasso;
import java.util.ArrayList;

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
        ImageView imgSanPham = convertView.findViewById(R.id.imgSanPham);
        TextView tenSanPham = convertView.findViewById(R.id.txtTenSanPham);
        TextView maSanPham = convertView.findViewById(R.id.txtMaSanPham);
        SanPham sanPham = data.get(position);

        tenSanPham.setText(sanPham.getTenSP());
        maSanPham.setText("Mã SP: "+ sanPham.getMaSP().toString());

        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinh(), 0, sanPham.getHinh().length);
        imgSanPham.setImageBitmap(bitmap);

        return convertView;
    }
}
