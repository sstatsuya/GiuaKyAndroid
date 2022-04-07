package khachhang.model;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private int MAKH;
    private byte[] HINHANH;
    private String TENKH, DIENTHOAI, DIACHI;

    public KhachHang() {

    }

    public KhachHang(byte[] HINHANH, String TENKH, String DIENTHOAI, String DIACHI) {
        this.HINHANH = HINHANH;
        this.TENKH = TENKH;
        this.DIENTHOAI = DIENTHOAI;
        this.DIACHI = DIACHI;
    }

    public KhachHang(int MAKH, byte[] HINHANH, String TENKH, String DIENTHOAI, String DIACHI) {
        this.MAKH = MAKH;
        this.HINHANH = HINHANH;
        this.TENKH = TENKH;
        this.DIENTHOAI = DIENTHOAI;
        this.DIACHI = DIACHI;
    }

    public int getMAKH() {
        return MAKH;
    }

    public byte[] getHINHANH() {
        return HINHANH;
    }

    public String getTENKH() {
        return TENKH;
    }

    public String getDIENTHOAI() {
        return DIENTHOAI;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setMAKH(int MAKH) {
        this.MAKH = MAKH;
    }

    public void setHINHANH(byte[] HINHANH) {
        this.HINHANH = HINHANH;
    }

    public void setTENKH(String TENKH) {
        this.TENKH = TENKH;
    }

    public void setDIENTHOAI(String DIENTHOAI) {
        this.DIENTHOAI = DIENTHOAI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;

    }
}
