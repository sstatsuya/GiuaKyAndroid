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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import database.model.SanPham;
import others.Others;

public class AdapterSanPham extends ArrayAdapter<SanPham> {
    Context context;
    int resource;
    ArrayList<SanPham> data;
    ArrayList<SanPham> dataBackup = new ArrayList<>();
    Others others = new Others();

    public AdapterSanPham(@NonNull Context context, int resource, @NonNull ArrayList<SanPham> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        dataBackup.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void locBangInput(String text){
        data.clear();
        text = text.toLowerCase();
        if(text.length() == 0)data.addAll(dataBackup);
        else{
            for (SanPham sanPham: dataBackup){
                if(sanPham.searchValue().toLowerCase().contains(text)){
                    data.add(sanPham);
                }
            }
        }
        notifyDataSetChanged();
    }

    //option : 1 ma tang dan
    //option : 2 ma giam dan
    //option : 3 gia tang dan
    //option : 4 gia giam dan
    public void locBangSpinner(int option) {
        data.clear();
        data.addAll(dataBackup);
        if(option < 3){
            for(int i = 0; i<data.size()-1; i++){
                Collections.sort(data, new Comparator<SanPham>() {
                    @Override
                    public int compare(SanPham sp1, SanPham sp2) {
                        return sp1.getMaSP() > sp2.getMaSP() ? 1:-1;
                    }
                });
            }
            if(option == 2){
                Collections.reverse(data);
            }
        }
        else{
            for(int i = 0; i<data.size()-1; i++){
                Collections.sort(data, new Comparator<SanPham>() {
                    @Override
                    public int compare(SanPham sp1, SanPham sp2) {
                        return sp1.getDonGia() > sp2.getDonGia() ? 1:-1;
                    }
                });
            }
            if(option == 4){
                Collections.reverse(data);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        ImageView imgSanPham = convertView.findViewById(R.id.imgSanPham);
        TextView tenSanPham = convertView.findViewById(R.id.txtTenSanPham);
        TextView maSanPham = convertView.findViewById(R.id.txtMaSanPham);
        TextView giaSanPham = convertView.findViewById(R.id.txtGiaSanPham);
        SanPham sanPham = data.get(position);

        tenSanPham.setText(sanPham.getTenSP());
        maSanPham.setText("Mã SP: "+ sanPham.getMaSP().toString());
        giaSanPham.setText(others.numberToVND(sanPham.getDonGia()));

        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinh(), 0, sanPham.getHinh().length);
        imgSanPham.setImageBitmap(bitmap);

        return convertView;
    }
}
