package database.model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class SanPham implements Serializable {
    Integer maSP;
    String tenSP, xuatXu;
    Double donGia;
    byte[] hinh;

    public SanPham() {
    }

    public SanPham(Integer maSP, String tenSP, String xuatXu, Double donGia, byte[] hinh) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.xuatXu = xuatXu;
        this.donGia = donGia;
        this.hinh = hinh;
    }

    public String searchValue() {
        return this.maSP + " " + this.tenSP + " " + this.xuatXu;
    }
    //get set

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
}
