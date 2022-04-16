package donhang.model;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuakyandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.DBDonHang;
import database.DBThongTinDatHang;
import database.model.DonHang;
import donhang.DonHangActivity;
import donhang.ThongTinDonHangActivity;
import others.Others;

public class AdapterDonHang extends ArrayAdapter<DonHang> {
    Context context;
    int resource;
    ArrayList<DonHang> data;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

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

        tvMaDonHang.setText(String.valueOf(donHang.getMaDH()));
        tvNgayDonHang.setText(df.format(donHang.getNgayDatHang()));
        tvMaDonHang.setText("Mã đơn hàng: " + String.valueOf(donHang.getMaDH()));
        tvTenKhachHang.setText("Tên khách hàng : " + String.valueOf(donHang.getTenKH()));

        //Event
        btnXoaDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaDonHang(donHang);
            }
        });

        btnXemDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value=data.get(position).getMaDH();
                Intent intent = new Intent(view.getContext(), ThongTinDonHangActivity.class);
                intent.putExtra("madonhang",value);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

//    Xoa don hang
    public void xoaDonHang(DonHang donHang){
        Dialog confirmDialog = chucnang.Dialog.openConfirmDialog(context, "Bạn có muốn xóa đơn hàng "+donHang.getMaDH());
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
                Dialog successDialog = chucnang.Dialog.openSuccessDialog(context, "Xóa đơn hàng thành công");
                successDialog.show();
                TextView btnSuccessDongY = successDialog.findViewById(R.id.btn_success_dong_y);
                btnSuccessDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DBDonHang(context).getAll().forEach(System.out::println);
                        new DBDonHang(context).delete(donHang.getMaDH());
                        new DBThongTinDatHang(context).delete(donHang.getMaDH());
                        new DBThongTinDatHang(context).getAll();
                        new DBDonHang(context).getAll().forEach(System.out::println);
                        data.remove(donHang);
                        notifyDataSetChanged();

                        successDialog.dismiss();
                    }
                });
            }
        });
    }
}