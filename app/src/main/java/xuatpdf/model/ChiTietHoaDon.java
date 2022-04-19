package xuatpdf.model;

import java.io.Serializable;

/**
 * Project: GiuaKyAndroid
 * Author: Tran Van Tu
 * Date: 4/17/2022 3:17 PM
 * Desc:
 */
public class ChiTietHoaDon implements Serializable {
    Integer maSP;
    String tenSP;
    int soLuong;
    Double donGia;
    byte[] hinh;
    String xuatXu;
    double thanhTien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(Integer maSP, String tenSP, int soLuong, Double donGia, byte[] hinh, String xuatXu, double thanhTien) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.hinh = hinh;
        this.xuatXu = xuatXu;
        this.thanhTien = thanhTien;
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

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public double getSoLuong() {
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
