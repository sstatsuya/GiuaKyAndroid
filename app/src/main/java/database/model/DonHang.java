package database.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DonHang implements Serializable {
    int maDH;
    int maKH;
    String tenKH;
    Date ngayDatHang;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<SanPhamDonHang> sanPhamDonHangs;

    public DonHang() {
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("DonHang{" +
                "maDH=" + maDH +
                ", maKH=" + maKH +
                ", ngayDatHang=" + df.format(ngayDatHang) +
                ", [");
        if(sanPhamDonHangs != null) {
            for (SanPhamDonHang i : sanPhamDonHangs)
                string.append(i.getMaSP() + " - " + i.getSoLuong() + ", ");
        }
        string.append("]}");
        return string.toString();
    }

    public String searchValue() {
        return this.maDH + " " + this.maKH + " " + this.tenKH;
    }

    public DonHang(int maDH, int maKH, Date ngayDatHang) {
        this.maDH = maDH;
        this.maKH = maKH;
        this.ngayDatHang = ngayDatHang;
    }

    public DonHang(int maDH, int maKH, Date ngayDatHang, ArrayList<SanPhamDonHang> sanPhamDonHangs) {
        this.maDH = maDH;
        this.maKH = maKH;
        this.ngayDatHang = ngayDatHang;
        this.sanPhamDonHangs = sanPhamDonHangs;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public Date getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(Date ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public ArrayList<SanPhamDonHang> getSanPhamDonHangs() {
        return sanPhamDonHangs;
    }

    public void setSanPhamDonHangs(ArrayList<SanPhamDonHang> sanPhamDonHangs) {
        this.sanPhamDonHangs = sanPhamDonHangs;
    }
}
