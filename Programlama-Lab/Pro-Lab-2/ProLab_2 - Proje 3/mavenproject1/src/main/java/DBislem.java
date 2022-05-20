
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author trkgrn
 */
public class DBislem {

    private User user = null;

    public User getUser(String sorgu, JFrame frame) {
        ConnectDB.connect();

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setAd(resultSet.getString("Ad"));
                user.setSoyad(resultSet.getString("Soyad"));
                user.setTcNo(resultSet.getString("TC_no"));
                user.setTelefon(resultSet.getString("Telefon"));
                user.setePosta(resultSet.getString("E_posta"));
                user.setKisiId(resultSet.getInt("kisi_id"));
                user.setRolId(resultSet.getInt("rol_id"));
                user.setSifre(resultSet.getString("sifre"));
                user.setAdres(resultSet.getString("Adres"));

            }

            ConnectDB.con.close();
            return user;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Hatali sorgu: " + e.getMessage());
        }
        return null;
    }

    public User getMusteri(String sorgu, JFrame frame) {
        ConnectDB.connect();

        try {
            //   String sorgu ="SELECT * FROM kisi where tc_no='"+tcNo+"' and sifre='"+sifre+"'";         
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setAd(resultSet.getString("Ad"));
                user.setSoyad(resultSet.getString("Soyad"));
                user.setTcNo(resultSet.getString("TC_no"));
                user.setTelefon(resultSet.getString("Telefon"));
                user.setePosta(resultSet.getString("E_posta"));
                user.setKisiId(resultSet.getInt("kisi_id"));
                user.setRolId(resultSet.getInt("rol_id"));
                user.setSifre(resultSet.getString("sifre"));
                user.setAdres(resultSet.getString("Adres"));
                user.setMusteri_id(resultSet.getInt("musteri_id"));
                user.setMusterinin_temsilci_id(resultSet.getInt("temsilci_id"));

            }

            ConnectDB.con.close();
            return user;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Hatali sorgu: " + e.getMessage());
        }
        return null;
    }

    public static void insertQuery(String sorgu) {
        ConnectDB.connect();

        try {
            Statement st = ConnectDB.con.createStatement();
            st.executeUpdate(sorgu);

            ConnectDB.con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "TCK no , Telefon No veya Mail Adresi "
                    + "diğer müşterilerle çakışıyor!");
            e.printStackTrace();
        }
    }

    public static void deleteQuery(String sorgu) {
        ConnectDB.connect();

        try {
            Statement st = ConnectDB.con.createStatement();
            st.executeUpdate(sorgu);
            ConnectDB.con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateQuery(String sorgu) {
        ConnectDB.connect();

        try {
            Statement st = ConnectDB.con.createStatement();
            st.executeUpdate(sorgu);
            JOptionPane.showMessageDialog(null, "Güncelleme başalarılı!");
            ConnectDB.con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void paraYatir(String sorgu) {
        ConnectDB.connect();
        try {
            Statement st = ConnectDB.con.createStatement();
            st.executeUpdate(sorgu);
            ConnectDB.con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int paraCek(String sorgu, JFrame frame) {
        ConnectDB.connect();
        try {
            Statement st = ConnectDB.con.createStatement();
            st.executeUpdate(sorgu);
            ConnectDB.con.close();
            return 1;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Yetersiz bakiye!");
            return 0;
        }

    }

    public static int getID(String sorgu, JFrame frame, String column) {
        ConnectDB.connect();
        int id_no = 0;
        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id_no = resultSet.getInt(column);
            }

            ConnectDB.con.close();
            return id_no;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Hatali sorgu: " + e.getMessage());
        }
        return 0;
    }

    public static double getKurSabiti(String sorgu, JFrame frame, String column) {
        ConnectDB.connect();
        double kurSabiti = 0;
        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                kurSabiti = resultSet.getInt(column);
            }

            ConnectDB.con.close();
            return kurSabiti;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Hatali sorgu: " + e.getMessage());
        }
        return 0;
    }

    public static double getToplamBakiye(int musteri_id) {
        double toplamBakiye = 0.0;
        ArrayList<Hesap> hesapList = TabloIslem.getAllHesap(musteri_id);
        for (Hesap h : hesapList) {
            String sorgu = "SELECT * FROM para_birim pb, hesaplar h, hesap_turu ht "
                    + "WHERE h.hesap_tur_id=ht.hesap_tur_id AND ht.para_birim_id=pb.para_birim_id AND h.hesap_adi= '" + h.getHesap_adi() + "'";
            double kurSabiti = getKurSabiti(sorgu, null, "kur_sabiti");
            h.setHesap_bakiye_TL(kurSabiti * h.getHesap_bakiye());
            toplamBakiye += h.getHesap_bakiye_TL();
        }
        return toplamBakiye;
    }

    public static double getHesapBakiye(int hesap_id) {
        double toplamBakiye = 0.0;
        ConnectDB.connect();
        String sorgu = "SELECT * FROM hesaplar WHERE hesap_id=" + hesap_id;

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                toplamBakiye = resultSet.getDouble("hesap_bakiye");
            }
            ConnectDB.con.close();
        } catch (Exception e) {

        }

        return toplamBakiye;
    }

    public static double getFaiz(String faiz_turu) {
        double faiz = 0.0;
        ConnectDB.connect();
        String sorgu = "SELECT * FROM faiz_oranlari where faiz_turu= '" + faiz_turu + "'";

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                faiz = resultSet.getDouble("faiz_oran");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Faiz: Hatali sorgu: " + e.getMessage());
        }
        return faiz;
    }

    public static String getAd(String sorgu, JFrame frame, String column) {
        ConnectDB.connect();
        String ad = "";
        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ad = resultSet.getString(column);
            }

            ConnectDB.con.close();
            return ad;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Hatali sorgu: " + e.getMessage());
        }
        return ad;
    }

    public static double getFaizsiz(String kredi_id) {
        double aylikFaizsiz = 0.0;
        int toplamAy = 0;
        ConnectDB.connect();
        String sorgu = "SELECT * FROM kredi WHERE kredi_id = " + kredi_id;

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                aylikFaizsiz = resultSet.getDouble("alinan_kredi_toplam");
                toplamAy = resultSet.getInt("toplam_ay");
                aylikFaizsiz = aylikFaizsiz / toplamAy;
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FaizsizKredi : Hatali sorgu: " + e.getMessage());
        }
        return aylikFaizsiz;
    }

    public static double getTemsilciMaas() {
        double temsilciMaas = 0.0;

        ConnectDB.connect();
        String sorgu = "SELECT maas FROM temsilci";

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                temsilciMaas = resultSet.getDouble("maas");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Maas : Hatali sorgu: " + e.getMessage());
        }
        return temsilciMaas;
    }

    public static int getTemsilciMusteriSayisi(int temsilci_id) {
        int musteriSayisi = 0;

        ConnectDB.connect();
        String sorgu = "SELECT temsilci_id, COUNT(temsilci_id) as musteri_sayisi"
                + " FROM temsilcinin_musterileri"
                + " WHERE temsilci_id = " + temsilci_id
                + " group by temsilci_id";

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                musteriSayisi = resultSet.getInt("musteri_sayisi");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Musteri Sayisi : Hatali sorgu: " + e.getMessage());
        }
        return musteriSayisi;
    }

    public static int getMusteriSayisi() {
        int musteriSayisi = 0;

        ConnectDB.connect();
        String sorgu = "SELECT COUNT(musteri_id) as musteri_sayisi FROM musteri";

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                musteriSayisi = resultSet.getInt("musteri_sayisi");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Musteri Sayisi : Hatali sorgu: " + e.getMessage());
        }
        return musteriSayisi;
    }

    public static int getTemsilciSayisi() {
        int temsilciSayisi = 0;

        ConnectDB.connect();
        String sorgu = "SELECT COUNT(temsilci_id) as temsilci_sayisi FROM temsilci";

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                temsilciSayisi = resultSet.getInt("temsilci_sayisi");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Musteri Sayisi : Hatali sorgu: " + e.getMessage());
        }
        return temsilciSayisi;
    }

    public static double getKalanBorc(String kredi_odeme_id) {
        double kalanBorc = 0.0;
        ConnectDB.connect();
        String sorgu = "SELECT * FROM kredi_odeme WHERE kredi_odeme_id = " + kredi_odeme_id;

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                kalanBorc = resultSet.getDouble("kalan_aylik_borc");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FaizsizKredi : Hatali sorgu: " + e.getMessage());
        }
        return kalanBorc;
    }

    public static double getAylikGelir(int musteri_id) {
        double gelir = 0.0;
        ConnectDB.connect();
        String sorgu = "SELECT * FROM musteri WHERE musteri_id = " + musteri_id;

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                gelir = resultSet.getDouble("gelir");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "gelir : Hatali sorgu: " + e.getMessage());
        }
        return gelir;
    }

    public static int getTarihDurum(String tarihAralik) {
        tarihAralik = tarihAralik.replace("|", "X");
        String[] dizi = tarihAralik.split("X");
        String start = dizi[0];
        String end = dizi[1];

        Date islemZamani = Tarih.getAnlikTarih();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date d1, d2;
        try {
            d1 = Tarih.myFormat.parse(start);
            d2 = Tarih.myFormat.parse(end);

            // compareTo() ile tarihleri karşılaştırma
            if (islemZamani.compareTo(d1) >= 0 && islemZamani.compareTo(d2) < 0) {
                //System.out.println("İşlem Zamanı ödeme aralığında ise (Normal Faiz)");
                return 1;
            } else if (islemZamani.compareTo(d1) < 0) {
                //System.out.println("İşlem Zamanı ödemenin başlangıcından önceyse (Faiz almıyoruz)");
                return 2;
            } else if (islemZamani.compareTo(d2) > 0) {
                //System.out.println("İşlem Zamanı ödemenin son tarihini geçmiş ise (Faiz+Gecikme Faizi)");
                return 3;
            }

        } catch (Exception e) {

        }
        return 0;
    }

    public static long getTarihFark(String end) {

        try {
            Date tarih1 = Tarih.myFormat.parse(Tarih.myFormat.format(Tarih.getAnlikTarih()));
            Date tarih2 = Tarih.myFormat.parse(end);

            long sonuc = tarih1.getTime() - tarih2.getTime();
            // System.out.println("iki tarih arasındaki gün farkı=" + TimeUnit.DAYS.convert(sonuc, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(sonuc, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static long getTarihFarkAyBasi() {

        try {
            Date tarih1 = Tarih.myFormat.parse(Tarih.myFormat.format(Tarih.ayBasi()));
            Date tarih2 = Tarih.myFormat.parse(Tarih.currentDate);
            System.out.println(Tarih.myFormat.format(Tarih.ayBasi()));

            long sonuc = tarih1.getTime() - tarih2.getTime();
            // System.out.println("iki tarih arasındaki gün farkı=" + TimeUnit.DAYS.convert(sonuc, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(sonuc, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static long getTarihFarkAySonu() {

        try {
            Date tarih1 = Tarih.myFormat.parse(Tarih.myFormat.format(Tarih.aySonu()));
            Date tarih2 = Tarih.myFormat.parse(Tarih.currentDate);

            System.out.println(Tarih.myFormat.format(Tarih.aySonu()));
            long sonuc = tarih1.getTime() - tarih2.getTime();
            // System.out.println("iki tarih arasındaki gün farkı=" + TimeUnit.DAYS.convert(sonuc, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(sonuc, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getUygunTemsilciID() {
        int temsilci_id = 0;
        ConnectDB.connect();

        String sorgu2 = "SELECT temsilci_id  \n"
                + "FROM temsilci\n"
                + "WHERE temsilci_id NOT IN (SELECT distinct temsilci_id FROM temsilcinin_musterileri)";
        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu2);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                temsilci_id = resultSet.getInt("temsilci_id");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "TemsilciSecim : Hatali sorgu: " + e.getMessage());
        }
        
    if(temsilci_id<=0)
    {
                String sorgu = "SELECT temsilci_id, COUNT(temsilci_id) as toplam\n"
                + "	FROM temsilcinin_musterileri\n"
                + "	Group By temsilci_id\n"
                + "	having COUNT(temsilci_id)<=MIN(temsilci_id)";

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                temsilci_id = resultSet.getInt("temsilci_id");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "TemsilciSecim : Hatali sorgu: " + e.getMessage());
        }
    }


        return temsilci_id;
    }

    public static int getUygunHesapID(int musteri_id) {
        int hesap_id = 0;
        ConnectDB.connect();
        String sorgu = "SELECT hesap_id from hesaplar "
                + " WHERE musteri_id = " + musteri_id + "  and hesap_tur_id = 1"
                + " order by hesap_bakiye LIMIT 1";

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                hesap_id = resultSet.getInt("hesap_id");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "TemsilciSecim : Hatali sorgu: " + e.getMessage());
        }

        return hesap_id;
    }

    public static int[] getMusteriler(int musteri_sayisi) {
        int[] musteri_id;
        musteri_id = new int[musteri_sayisi];
        ConnectDB.connect();
        String sorgu = "SELECT musteri_id from musteri";

        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                musteri_id[i] = resultSet.getInt("musteri_id");
                i++;
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "TemsilciSecim : Hatali sorgu: " + e.getMessage());
        }

        return musteri_id;
    }

    public static double getBankaGider() {
        double toplamGider = 0;
        long fark2 = DBislem.getTarihFarkAyBasi();
        long fark1 = DBislem.getTarihFarkAySonu();
        ConnectDB.connect();
        String sorgu = "SELECT SUM(islem_tutari) as banka_gider \n"
                + "FROM islemler i, islem_turleri it\n"
                + "WHERE i.islem_tur_id = it.islem_tur_id AND\n"
                + "		i.islem_tur_id IN (4,6) AND\n"
                + "		i.islem_tarihi< CURRENT_DATE + " + fark1 + " AND i.islem_tarihi> CURRENT_DATE+" + fark2;
        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                toplamGider = resultSet.getDouble("banka_gider");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "BankaGider : Hatali sorgu: " + e.getMessage());
        }
        return toplamGider;
    }

    public static double getBankaGelir() {
        double toplamGelir = 0;
        long fark2 = DBislem.getTarihFarkAyBasi();
        long fark1 = DBislem.getTarihFarkAySonu();
        System.out.println("F1=" + fark1);
        System.out.println("F2=" + fark2);
        ConnectDB.connect();
        String sorgu = "SELECT SUM(islem_tutari) as banka_gelir \n"
                + "FROM islemler i, islem_turleri it\n"
                + "WHERE i.islem_tur_id = it.islem_tur_id AND\n"
                + "		i.islem_tur_id IN (5) AND\n"
                + "		i.islem_tarihi< CURRENT_DATE + " + fark1 + " AND i.islem_tarihi> CURRENT_DATE+" + fark2;
        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                toplamGelir = resultSet.getDouble("banka_gelir");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "BankaGelir : Hatali sorgu: " + e.getMessage());
        }
        return toplamGelir;
    }

    public static double getMusteriGider(int musteri_id) {
        double toplamGider = 0;
        long fark2 = DBislem.getTarihFarkAyBasi();
        long fark1 = DBislem.getTarihFarkAySonu();
        ConnectDB.connect();
        String sorgu = "SELECT SUM(islem_tutari) as musteri_gider \n"
                + "FROM islemler i, islem_turleri it\n"
                + "WHERE i.islem_tur_id = it.islem_tur_id AND\n"
                + " i.islem_tur_id IN (5) AND\n"
                + " i.islem_tarihi<= CURRENT_DATE + " + fark1 + " AND i.islem_tarihi>= CURRENT_DATE +" + fark2
                + " AND i.musteri_id =" + musteri_id;
        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                toplamGider = resultSet.getDouble("musteri_gider");
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "MusteriGider : Hatali sorgu: " + e.getMessage());
        }
        return toplamGider;
    }

}
