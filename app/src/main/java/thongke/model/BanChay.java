package thongke.model;

import java.io.Serializable;

/**
 * Project: GiuaKyAndroid
 * Author: Tran Van Tu
 * Date: 4/16/2022 4:10 PM
 * Desc:
 */
public class BanChay implements Serializable {
    Integer maSP;
    String tenSP;
    String xuatXu;
    Double donGia;
    byte[] hinh;
    int soLuong;

    public BanChay() {
    }

    public BanChay(Integer maSP, String tenSP, String xuatXu, Double donGia, byte[] hinh, int soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.xuatXu = xuatXu;
        this.donGia = donGia;
        this.hinh = hinh;
        this.soLuong = soLuong;
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

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
