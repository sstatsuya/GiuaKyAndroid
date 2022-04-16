package database.model;

import java.io.Serializable;

public class ThongTinDonHang implements Serializable {
    private Integer maHD;
    private Integer maSP;
    private Integer soLuongDat;

    public ThongTinDonHang() {
    }

    public ThongTinDonHang(Integer maHD, Integer maSP, Integer soLuongDat) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuongDat = soLuongDat;
    }

    public Integer getMaHD() {
        return maHD;
    }

    public void setMaHD(Integer maHD) {
        this.maHD = maHD;
    }

    public Integer getMaSP() {
        return maSP;
    }

    public void setMaSP(Integer maSP) {
        this.maSP = maSP;
    }

    public Integer getSoLuongDat() {
        return soLuongDat;
    }

    public void setSoLuongDat(Integer soLuongDat) {
        this.soLuongDat = soLuongDat;
    }
}
