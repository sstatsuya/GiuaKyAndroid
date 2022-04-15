package database.model;

import java.io.Serializable;

public class SanPhamDonHang implements Serializable {
    Integer maSP;
    String tenSP;
    int soLuong;
    Double donGia;
    byte[] hinh;
    String xuatXu;

    public SanPhamDonHang() {}

    public SanPhamDonHang(Integer maSP, int soLuong) {
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    public SanPhamDonHang(Integer maSP, String tenSP, int soLuong, Double donGia, byte[] hinh, String xuatXu) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.hinh = hinh;
        this.xuatXu = xuatXu;
    }

    public Integer getMaSP() {
        return maSP;
    }

    public void setMaSP(Integer maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }
}
