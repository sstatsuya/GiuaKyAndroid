package database.model;

import java.util.Comparator;

public class SanPhamComparator implements Comparator<SanPham> {
    @Override
    public int compare(SanPham sp1, SanPham sp2) {
        return sp1.getMaSP().compareTo(sp2.getMaSP());
    }
}
