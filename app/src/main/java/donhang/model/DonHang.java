package donhang.model;

import java.io.Serializable;
import java.time.LocalDate;

public class DonHang implements Serializable {
    String maDH;
    LocalDate ngayDatHang;
    String maKH;

    public DonHang() {}

    public DonHang(String maDH, LocalDate ngayDatHang, String maKH) {
        this.maDH = maDH;
        this.ngayDatHang = ngayDatHang;
        this.maKH = maKH;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public LocalDate getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(LocalDate ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
}
