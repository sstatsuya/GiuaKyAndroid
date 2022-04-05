package donhang.model;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuakyandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import donhang.DonHangActivity;
import others.Others;
import sanpham.model.SanPham;

public class AdapterDonHang extends ArrayAdapter<DonHang> {
    Context context;
    int resource;
    ArrayList<DonHang> data;

    public AdapterDonHang(@NonNull Context context, int resource, @NonNull ArrayList<DonHang> data) {
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
        DonHang donHang = data.get(position);


        TextView tvMaDonHang = convertView.findViewById(R.id.tv_donhang_ma);
        TextView tvNgayDonHang = convertView.findViewById(R.id.tv_donhang_ngay);
        TextView tvTenKhachHang = convertView.findViewById(R.id.tv_donhang_ten_khachhang);
        TextView btnXoaDonHang = convertView.findViewById(R.id.btn_donhang_xoa);
        TextView btnXemDonHang = convertView.findViewById(R.id.btn_donhang_xem);

        tvMaDonHang.setText(donHang.getMaDH());
        String ngayDatHang = donHang.getNgayDatHang().getDay() + "/" + donHang.getNgayDatHang().getMonth() + "/" + donHang.getNgayDatHang().getYear();
        tvNgayDonHang.setText(ngayDatHang);
        tvMaDonHang.setText("Khách hàng chưa xác định");

        //Event
        btnXoaDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaDonHang(donHang);
            }
        });

        return convertView;
    }

//    Xoa don hang
    public void xoaDonHang(DonHang donHang){
        Others others = new Others();
        Dialog confirmDialog = others.openConfirmDialog(context, "Bạn có muốn xóa đơn hàng "+donHang.getMaDH());
        confirmDialog.show();
        TextView btnConfirmHuyBo = confirmDialog.findViewById(R.id.btn_confirm_huy_bo);
        TextView btnConfirmDongY = confirmDialog.findViewById(R.id.btn_confirm_dong_y);
        btnConfirmHuyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
            }
        });

        btnConfirmDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
                Dialog successDialog = others.openSuccessDialog(context, "Xóa đơn hàng thành công");
                successDialog.show();
                TextView btnSuccessDongY = successDialog.findViewById(R.id.btn_success_dong_y);
                btnSuccessDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        successDialog.dismiss();
                    }
                });
            }
        });
    }
}