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

public class AdapterDSMatHang extends ArrayAdapter {
    ///Chọn 1 sản phẩm sẽ phải hỏi số lượng bằng dialog
    Context context;
    int resource;
    ArrayList<SanPham> matHangs;

    public AdapterDSMatHang(@NonNull Context context, int resource, @NonNull ArrayList<SanPham> matHangs) {
        super(context, resource, matHangs);
        this.context = context;
        this.resource = resource;
        this.matHangs = matHangs;
    }

    @Override
    public int getCount() {
        return this.matHangs.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        ImageView hinhAnhSanPham = convertView.findViewById(R.id.iv_itemchonmathang_hinhanhsanpham);
        TextView tenSanPham = convertView.findViewById(R.id.tv_itemchonmathang_tensanpham),
                maSanPham = convertView.findViewById(R.id.tv_itemchonmathang_masanpham),
                giaTienSanPham = convertView.findViewById(R.id.tv_itemchonmathang_giatiensanpham);

        SanPham sp = this.matHangs.get(position);

        tenSanPham.setText(sp.getTenSP());
        maSanPham.setText(String.valueOf(sp.getMaSP()));
        giaTienSanPham.setText(sp.getDonGia().toString());

        Bitmap bitmap = BitmapFactory.decodeByteArray(sp.getHinh(), 0, sp.getHinh().length);
        hinhAnhSanPham.setImageBitmap(bitmap);

        return convertView;
    }
}
