package khachhang.model;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private int id;
    private String avatar, name, phone, address;

    public KhachHang() {
    }

    public KhachHang(String avatar, String name, String phone, String address) {
        this.avatar = avatar;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public KhachHang(int id, String avatar, String name, String phone, String address) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
