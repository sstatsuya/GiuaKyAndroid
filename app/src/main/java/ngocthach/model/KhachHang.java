package ngocthach.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KhachHang implements Parcelable {
    private String MaKH, name, phone, address;

    public KhachHang() {
    }

    public KhachHang(String maKH, String name, String phone, String address) {
        this.MaKH = maKH;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    protected KhachHang(Parcel in) {
        MaKH = in.readString();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
    }

    public static final Creator<KhachHang> CREATOR = new Creator<KhachHang>() {
        @Override
        public KhachHang createFromParcel(Parcel in) {
            return new KhachHang(in);
        }

        @Override
        public KhachHang[] newArray(int size) {
            return new KhachHang[size];
        }
    };

    public String getMaKH() {
        return MaKH;
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

    public void setMaKH(String maKH) {
        MaKH = maKH;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(MaKH);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(address);
    }
}
