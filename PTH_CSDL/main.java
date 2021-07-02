import java.util.ArrayList;
import java.util.List;

public class main {
    public static String Hop(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            if (!b.contains(a.substring(i, i + 1))) {
                b = b + a.substring(i, i + 1);
            }
        }
        return b;
    }

    public static boolean KiemTraTapCon(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            if (!b.contains(a.substring(i, i + 1))) {
                return false;
            }
        }
        return true;
    }

    public static void BaoDong(String X, List<PhuThuocHam> F) {
        String temp = "";
        while (temp != X) {
            temp = X;
            for (int i = 0; i < F.size(); i++) {
                if (KiemTraTapCon(F.get(i).veTrai, X)) {
                    X = Hop(F.get(i).vePhai, X);
                }
            }
        }
        System.out.println("X+: " + X);
    }
    public static void main(String[] args) {
        PhuThuocHam pth1 = new PhuThuocHam("AB", "C");
        PhuThuocHam pth2 = new PhuThuocHam("G", "D");
        PhuThuocHam pth3 = new PhuThuocHam("H", "E");
        PhuThuocHam pth4 = new PhuThuocHam("ED", "B");
        PhuThuocHam pth5 = new PhuThuocHam("E", "G");
        List<PhuThuocHam> F = new ArrayList(); // F: tập phụ thuộc hàm
        F.add(pth1);
        F.add(pth2);
        F.add(pth3);
        F.add(pth4);
        F.add(pth5);
        String X = "AE"; // X: tập thuộc tính cần tìm bao đóng
        System.out.println("--------------------------");
        BaoDong(X, F);
        System.out.println("--------------------------");
    }
}
