package sanpham.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    String maSP, tenSP, xuatXu, linkHinhAnh;
    Integer donGia;

    public SanPham() {
    }

    public void setLinkHinhAnh(String linkHinhAnh) {
        this.linkHinhAnh = linkHinhAnh;
    }

    public String getLinkHinhAnh() {
        return linkHinhAnh;
    }

    public SanPham(String maSP, String tenSP, String xuatXu, Integer donGia, String linkHinhAnh) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.xuatXu = xuatXu;
        this.donGia = donGia;
        this.linkHinhAnh = linkHinhAnh;
    }

    public String getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public Integer getDonGia() {
        return donGia;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public void setDonGia(Integer donGia) {
        this.donGia = donGia;
    }
}
