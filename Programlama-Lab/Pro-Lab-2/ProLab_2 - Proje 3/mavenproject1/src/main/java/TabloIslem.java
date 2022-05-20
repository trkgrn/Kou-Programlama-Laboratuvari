
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author trkgrn
 */
public class TabloIslem {

    private static TabloIslem instance;

    private TabloIslem() {

    }

    public static TabloIslem getInstance() {
        if (instance == null) {
            instance = new TabloIslem();
        }
        return instance;
    }

    public void deleteRowKrediTalepTable(JTable talepTable, int musteri_id, int temsilci_id, int selectedRow) {
        String basvuru_id = talepTable.getModel().getValueAt(selectedRow, 0).toString();
        String sorgu = "UPDATE kredi_basvuru SET silindi_mi=true where kredi_basvuru_id=" + basvuru_id;
        DBislem.updateQuery(sorgu);
    }

    public void showKrediTalepTable(JTable talepTable, int musteri_id, int temsilci_id) {
        ArrayList<KrediTalep> talepList = getAllKrediTalep(musteri_id, temsilci_id);

        DefaultTableModel model = (DefaultTableModel) talepTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Başvuru No", "Temsilci", "Miktar", "Toplam Ay", "Başvuru Durumu"
        });
        Object[] row = new Object[5];

        for (int i = 0; i < talepList.size(); i++) {
            row[0] = talepList.get(i).getKredi_basvuru_id();
            row[1] = talepList.get(i).getTemsilci_id();
            row[2] = talepList.get(i).getIstenen_kredi_miktar();
            row[3] = talepList.get(i).getToplam_ay();
            row[4] = talepList.get(i).getOnay_durum();

            if (!talepList.get(i).isSilindi_mi()) {
                model.addRow(row);
            }
        }
        talepTable.removeAll();
        talepTable.setModel(model);

    }

    private ArrayList<KrediTalep> getAllKrediTalep(int musteri_id, int temsilci_id) {
        ArrayList<KrediTalep> talepList = new ArrayList<>();
        KrediTalep talep;
        try {
            ConnectDB.connect();
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement("SELECT * FROM kredi_basvuru where musteri_id=" + musteri_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                talep = new KrediTalep();
                talep.setMusteri_id(musteri_id);
                talep.setKredi_basvuru_id(resultSet.getInt("kredi_basvuru_id"));
                talep.setTemsilci_id(resultSet.getInt("temsilci_id"));
                talep.setOnay_durum(resultSet.getString("onay_durum"));
                talep.setIstenen_kredi_miktar(resultSet.getInt("istenen_kredi_miktar"));
                talep.setToplam_ay(resultSet.getInt("toplam_ay"));
                talep.setTemsilci_id(temsilci_id);
                talep.setSilindi_mi(resultSet.getBoolean("silindi_mi"));

                talepList.add(talep);
            }
            ConnectDB.con.close();
        } catch (SQLException e) {

        }

        return talepList;

    }

    public void showHesapTable(JTable hesapTable, int musteri_id) {
        ArrayList<Hesap> hesapList = getAllHesap(musteri_id);
        DefaultTableModel model = (DefaultTableModel) hesapTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Hesap No", "Hesap Adı", "Hesap Türü", "Hesap Bakiyesi"
        });
        Object[] row = new Object[4];

        for (int i = 0; i < hesapList.size(); i++) {
            row[0] = hesapList.get(i).getHesap_id();
            row[1] = hesapList.get(i).getHesap_adi();
            row[2] = hesapList.get(i).getHesap_tur_adi();
            row[3] = hesapList.get(i).getHesap_bakiye();

            model.addRow(row);
        }
        hesapTable.removeAll();
        hesapTable.setModel(model);

    }

    public static ArrayList<Hesap> getAllHesap(int musteri_id) {
        ArrayList<Hesap> hesapList = new ArrayList<>();
        Hesap hesap;
        try {
            ConnectDB.connect();
            String sorgu = "SELECT * "
                    + "FROM hesaplar h, hesap_turu ht, para_birim pb"
                    + " WHERE silindi_mi = false and h.musteri_id =" + musteri_id + " and h.hesap_tur_id = ht.hesap_tur_id and ht.para_birim_id=pb.para_birim_id";
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                hesap = new Hesap();
                hesap.setHesap_adi(resultSet.getString("hesap_adi"));
                hesap.setHesap_bakiye(resultSet.getDouble("hesap_bakiye"));
                hesap.setHesap_id(resultSet.getInt("hesap_id"));
                hesap.setPara_birim_adi("para_birim_adi");
                hesap.setHesap_tur_adi(resultSet.getString("hesap_tur_adi"));
                hesap.setHesap_tur_id(resultSet.getInt("hesap_tur_id"));
                hesap.setHesap_para_birim_id(resultSet.getInt("para_birim_id"));
                hesap.setKur_sabiti(resultSet.getDouble("kur_sabiti"));
                hesap.setMusteri_id(musteri_id);

                hesapList.add(hesap);
            }
            preparedStatement.close();
            ConnectDB.con.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return hesapList;
    }

    public void showHesapTalepTable(JTable hesapTalepTable, int id, int kontrol) {
        ArrayList<HesapTalep> hesapTalepList = getAllHesapTalep(id, kontrol);
        DefaultTableModel model = (DefaultTableModel) hesapTalepTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Talep No", "Müşteri No", "Talep Türü", "Hesap Adı", "Hesap Türü", "Durum"
        });
        Object[] row = new Object[6];

        for (int i = 0; i < hesapTalepList.size(); i++) {
            row[0] = hesapTalepList.get(i).getHesap_talep_id();
            row[1] = hesapTalepList.get(i).getMusteri_id();
            row[2] = hesapTalepList.get(i).getTalep_tur();
            row[3] = hesapTalepList.get(i).getHesap_adi();
            row[4] = hesapTalepList.get(i).getHesap_tur_adi();
            row[5] = hesapTalepList.get(i).getOnay_durum();

            if (!hesapTalepList.get(i).isSilindi_mi()) {
                model.addRow(row);
            }
        }
        hesapTalepTable.removeAll();
        hesapTalepTable.setModel(model);

    }

    private ArrayList<HesapTalep> getAllHesapTalep(int id, int kontrol) {
        ArrayList<HesapTalep> hesapTalepList = new ArrayList<>();
        HesapTalep talep;
        String sorgu = "";
        try {
            ConnectDB.connect();
            if (kontrol == 1) {
                sorgu = "SELECT * FROM hesap_talep h,hesap_turu ht "
                        + "where ht.hesap_tur_id=h.hesap_tur_id and h.musteri_id=" + id;
            } else if (kontrol == 2) {
                sorgu = "SELECT * FROM hesap_talep h,hesap_turu ht "
                        + "where ht.hesap_tur_id=h.hesap_tur_id and h.temsilci_id=" + id;
            }

            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                talep = new HesapTalep();

                talep.setHesap_talep_id(resultSet.getInt("hesap_talep_id"));
                talep.setHesap_adi(resultSet.getString("hesap_adi"));
                talep.setHesap_tur_id(resultSet.getInt("hesap_tur_id"));
                talep.setMusteri_id(resultSet.getInt("musteri_id"));
                talep.setTemsilci_id(resultSet.getInt("temsilci_id"));
                talep.setOnay_durum(resultSet.getString("onay_durum"));
                talep.setHesap_tur_adi(resultSet.getString("hesap_tur_adi"));
                talep.setHesap_id(resultSet.getInt("hesap_id"));
                talep.setSilindi_mi(resultSet.getBoolean("silindi_mi"));

                if (talep.getHesap_id() > 0) {
                    talep.setTalep_tur("Hesap Silme");
                } else {
                    talep.setTalep_tur("Hesap Açma");
                }

                hesapTalepList.add(talep);
            }
            preparedStatement.close();
            ConnectDB.con.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return hesapTalepList;
    }

    public void showMusteriTable(JTable musteriTable, int temsilci_id, int kontrol) {
        ArrayList<User> musteriList = getAllMusteri(temsilci_id, kontrol);
        DefaultTableModel model = (DefaultTableModel) musteriTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Müşteri No", "Temsilci No", "Ad-Soyad", "TCK No", "Telefon No", "E-Mail Adresi", "Adres", "Toplam Bakiye", "Toplam Gelir"
        });
        Object[] row = new Object[9];

        for (int i = 0; i < musteriList.size(); i++) {
            row[0] = musteriList.get(i).getMusteri_id();
            row[1] = musteriList.get(i).getMusterinin_temsilci_id();
            row[2] = musteriList.get(i).getAd() + " " + musteriList.get(i).getSoyad();
            row[3] = musteriList.get(i).getTcNo();
            row[4] = musteriList.get(i).getTelefon();
            row[5] = musteriList.get(i).getePosta();
            row[6] = musteriList.get(i).getAdres();
            row[7] = musteriList.get(i).getToplam_bakiye();
            row[8] = musteriList.get(i).getToplam_gelir();

            model.addRow(row);
        }
        musteriTable.removeAll();
        musteriTable.setModel(model);

    }

    private ArrayList<User> getAllMusteri(int temsilci_id, int kontrol) {
        ArrayList<User> musteriList = new ArrayList<>();
        User user;
        try {
            ConnectDB.connect();
            String sorgu = "";
            if (kontrol == 1) {
                sorgu = "SELECT * "
                        + "FROM kisi k, musteri m, temsilcinin_musterileri tm"
                        + " WHERE k.kisi_id=m.kisi_id AND"
                        + " tm.temsilci_id= " + temsilci_id + " AND"
                        + " tm.musteri_id = m.musteri_id";
            } else if (kontrol == 2) {
                sorgu = "SELECT * "
                        + "FROM kisi k, musteri m, temsilcinin_musterileri tm"
                        + " WHERE k.kisi_id=m.kisi_id AND"
                        + " tm.musteri_id = m.musteri_id";
            }

            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setAd(resultSet.getString("ad"));
                user.setSoyad(resultSet.getString("soyad"));
                user.setTcNo(resultSet.getString("tc_no"));
                user.setTelefon(resultSet.getString("telefon"));
                user.setePosta(resultSet.getString("e_posta"));
                user.setKisiId(resultSet.getInt("kisi_id"));
                user.setRolId(resultSet.getInt("rol_id"));
                user.setSifre(resultSet.getString("sifre"));
                user.setAdres(resultSet.getString("adres"));
                user.setMusteri_id(resultSet.getInt("musteri_id"));
                user.setMusterinin_temsilci_id(resultSet.getInt("temsilci_id"));
                user.setToplam_bakiye(DBislem.getToplamBakiye(user.getMusteri_id()));
                user.setToplam_gelir(resultSet.getDouble("gelir"));

                musteriList.add(user);
            }
            preparedStatement.close();
            ConnectDB.con.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return musteriList;
    }

    public void showKrediTalepTemsilciTable(JTable talepTable, int temsilci_id) {
        ArrayList<KrediTalep> talepList = getAllKrediTalepTemsilci(temsilci_id);

        DefaultTableModel model = (DefaultTableModel) talepTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Başvuru No", "Müşteri No", "Miktar", "Toplam Ay", "Başvuru Durumu"
        });
        Object[] row = new Object[5];

        for (int i = 0; i < talepList.size(); i++) {
            row[0] = talepList.get(i).getKredi_basvuru_id();
            row[1] = talepList.get(i).getMusteri_id();
            row[2] = talepList.get(i).getIstenen_kredi_miktar();
            row[3] = talepList.get(i).getToplam_ay();
            row[4] = talepList.get(i).getOnay_durum();
            model.addRow(row);
        }
        talepTable.removeAll();
        talepTable.setModel(model);

    }

    private ArrayList<KrediTalep> getAllKrediTalepTemsilci(int temsilci_id) {
        ArrayList<KrediTalep> talepList = new ArrayList<>();
        KrediTalep talep;
        try {
            ConnectDB.connect();
            String sorgu = "SELECT * FROM kredi_basvuru kb, musteri ms "
                    + "where ms.musteri_id=kb.musteri_id AND"
                    + " kb.temsilci_id=" + temsilci_id;
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                talep = new KrediTalep();
                talep.setMusteri_id(resultSet.getInt("musteri_id"));
                talep.setKredi_basvuru_id(resultSet.getInt("kredi_basvuru_id"));
                talep.setTemsilci_id(resultSet.getInt("temsilci_id"));
                talep.setOnay_durum(resultSet.getString("onay_durum"));
                talep.setIstenen_kredi_miktar(resultSet.getInt("istenen_kredi_miktar"));
                talep.setToplam_ay(resultSet.getInt("toplam_ay"));
                talep.setSilindi_mi(resultSet.getBoolean("silindi_mi"));
                talep.setTemsilci_id(temsilci_id);

                if (!talep.isSilindi_mi()) {
                    talepList.add(talep);
                }
            }
            ConnectDB.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return talepList;

    }

    public void showKrediOdemeTable(JTable krediOdemeTable, int musteri_id, int kredi_id, int kontrol) {
        ArrayList<KrediOdeme> krediOdemeList = getAllKrediOdeme(musteri_id, kredi_id, kontrol);
        DefaultTableModel model = (DefaultTableModel) krediOdemeTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Ödeme No", "Kredi No", "Ödenen Ay", "Aylık Taksit", "Gecikme Faizi", "Kalan Borç", "Ödeme Aralığı", "Durum"
        });
        Object[] row = new Object[8];

        for (int i = 0; i < krediOdemeList.size(); i++) {
            row[0] = krediOdemeList.get(i).getKredi_odeme_id();
            row[1] = krediOdemeList.get(i).getKredi_id();
            row[2] = krediOdemeList.get(i).getOdenen_ay();
            row[3] = krediOdemeList.get(i).getAylik_borc();
            row[4] = krediOdemeList.get(i).getGecikme_zammi();
            row[5] = krediOdemeList.get(i).getKalan_aylik_borc();
            row[6] = krediOdemeList.get(i).getOdeme_aralik();
            row[7] = krediOdemeList.get(i).isOdeme_durum();

            if (krediOdemeList.get(i).isOdeme_durum() == false) {
                row[7] = "Ödenmedi";
            }

            model.addRow(row);
        }

        krediOdemeTable.removeAll();
        krediOdemeTable.setModel(model);
        krediOdemeTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        krediOdemeTable.getColumnModel().getColumn(1).setPreferredWidth(25);
        krediOdemeTable.getColumnModel().getColumn(2).setPreferredWidth(25);
        krediOdemeTable.getColumnModel().getColumn(3).setPreferredWidth(40);
        krediOdemeTable.getColumnModel().getColumn(4).setPreferredWidth(40);
        krediOdemeTable.getColumnModel().getColumn(5).setPreferredWidth(40);
        krediOdemeTable.getColumnModel().getColumn(7).setPreferredWidth(40);
        krediOdemeTable.getColumnModel().getColumn(6).setPreferredWidth(150);
    }

    public ArrayList<KrediOdeme> getAllKrediOdeme(int musteri_id, int kredi_id, int kontrol) {
        ArrayList<KrediOdeme> krediOdemeList = new ArrayList<>();
        KrediOdeme odeme;

        try {
            ConnectDB.connect();
            String sorgu = "";
            if (kontrol == 1) {
                sorgu = "SELECT * FROM kredi_odeme ko,kredi k "
                        + "where ko.odendi_mi = false and ko.kredi_id=k.kredi_id and k.musteri_id=" + musteri_id;
            } else if (kontrol == 2) {
                sorgu = "SELECT * FROM kredi_odeme ko,kredi k "
                        + "where ko.kredi_id=k.kredi_id and"
                        + " k.kredi_id =" + kredi_id + " and"
                        + " ko.odendi_mi=false and"
                        + " k.musteri_id=" + musteri_id;
            }
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                odeme = new KrediOdeme();

                odeme.setKredi_odeme_id(resultSet.getInt("kredi_odeme_id"));
                odeme.setKredi_id(resultSet.getInt("kredi_id"));
                odeme.setAylik_borc(resultSet.getDouble("aylik_borc"));
                odeme.setOdenen_ay(resultSet.getInt("odenen_ay"));
                odeme.setKalan_aylik_borc(resultSet.getDouble("kalan_aylik_borc"));
                odeme.setOdeme_durum(resultSet.getBoolean("odendi_mi"));
                odeme.setOdeme_aralik(resultSet.getString("odeme_aralik"));
                odeme.setGecikme_zammi(resultSet.getDouble("gecikme_zammi"));

                String str = odeme.getOdeme_aralik();
                String[] dizi = str.split(",");
                String start = dizi[0].replace("[", "");
                String end = dizi[1].replace(")", "");
                odeme.setOdeme_aralik(start + "|" + end);
                odeme.setAsgari_odeme_tarih(start);
                odeme.setSon_odeme_tarih(end);
                int durum = DBislem.getTarihDurum(odeme.getOdeme_aralik());

                if (durum == 3) {
                    long temp = DBislem.getTarihFark(end);
                    double gecikenGun = (double) temp;
                    double gecikmeFaizOran = DBislem.getFaiz("Gecikme Faizi");
                    double gecikmeFaizi = odeme.getKalan_aylik_borc() * (gecikenGun / 30) * (gecikmeFaizOran / 100);
                    odeme.setGecikme_zammi(gecikmeFaizi);
                } else if (durum == 2) {
                    String k_id = "" + odeme.getKredi_id();
                    odeme.setKalan_aylik_borc(DBislem.getFaizsiz(k_id));
                    odeme.setGecikme_zammi(0);
                }
                krediOdemeList.add(odeme);
            }
            preparedStatement.close();
            ConnectDB.con.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return krediOdemeList;
    }

    public void showIslemTable(JTable islemTable, int musteri_id, int kontrol, int limit) {
        ArrayList<Islem> islemList = getAllIslem(musteri_id, kontrol, limit);
        DefaultTableModel model = (DefaultTableModel) islemTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "İşlem No", "Kaynak", "Hedef", "İşlem Türü", "İşlem Tutarı", "Kaynak Bakiye", "Hedef Bakiye", "İşlem Tarihi"
        });
        Object[] row = new Object[8];

        for (int i = 0; i < islemList.size(); i++) {
            String kaynak_ad = DBislem.getAd("SELECT * FROM hesaplar where hesap_id=" + islemList.get(i).getKaynak_id(), null, "hesap_adi");
            String hedef_ad = DBislem.getAd("SELECT * FROM hesaplar where hesap_id=" + islemList.get(i).getHedef_id(), null, "hesap_adi");
            String islem_ad = DBislem.getAd("SELECT * FROM islem_turleri where islem_tur_id=" + islemList.get(i).getIslem_tur_id(), null, "islem_adi");
            row[0] = islemList.get(i).getIslem_id();
            row[1] = kaynak_ad;
            row[2] = hedef_ad;
            row[3] = islem_ad;
            row[4] = islemList.get(i).getIslem_tutari();
            row[5] = islemList.get(i).getKaynak_bakiye();
            row[6] = islemList.get(i).getHedef_bakiye();
            row[7] = islemList.get(i).getIslem_tarihi();

            int islem_tur = islemList.get(i).getIslem_tur_id();

            if (hedef_ad.equals("") && (islem_tur == 4 || islem_tur == 5)) {
                row[2] = "Banka";
            } else if (kaynak_ad.equals("") && (islem_tur == 4 || islem_tur == 5)) {
                row[1] = "Banka";
            } else if (kaynak_ad.equals("") && (islem_tur == 1 || islem_tur == 2)) {
                int kisi_id = DBislem.getID("Select * from musteri where musteri_id=" + musteri_id, null, "kisi_id");
                String adSoyad = DBislem.getAd("Select * from kisi where kisi_id=" + kisi_id, null, "ad") + " "
                        + DBislem.getAd("Select * from kisi where kisi_id=" + kisi_id, null, "soyad");
                row[1] = adSoyad;

            } else if (hedef_ad.equals("") && (islem_tur == 1 || islem_tur == 2)) {
                int kisi_id = DBislem.getID("Select * from musteri where musteri_id=" + musteri_id, null, "kisi_id");
                String adSoyad = DBislem.getAd("Select * from kisi where kisi_id=" + kisi_id, null, "ad") + " "
                        + DBislem.getAd("Select * from kisi where kisi_id=" + kisi_id, null, "soyad");
                row[2] = adSoyad;

            }

            model.addRow(row);
        }

        islemTable.removeAll();
        islemTable.setModel(model);
        islemTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        islemTable.getColumnModel().getColumn(1).setPreferredWidth(25);
        islemTable.getColumnModel().getColumn(2).setPreferredWidth(25);
        islemTable.getColumnModel().getColumn(3).setPreferredWidth(40);
        islemTable.getColumnModel().getColumn(4).setPreferredWidth(40);
        islemTable.getColumnModel().getColumn(5).setPreferredWidth(40);
        islemTable.getColumnModel().getColumn(7).setPreferredWidth(40);
        islemTable.getColumnModel().getColumn(6).setPreferredWidth(40);
    }

    public void showIslemTemsilciTable(JTable islemTable, int temsilci_id, int kontrol) {
        ArrayList<Islem> islemList = getAllIslem(temsilci_id, kontrol, 0);
        DefaultTableModel model = (DefaultTableModel) islemTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "İşlem No", "Ad-Soyad", "Kaynak", "Hedef", "İşlem Türü", "İşlem Tutarı", "Kaynak Bakiye", "Hedef Bakiye", "İşlem Tarihi"
        });
        Object[] row = new Object[9];

        for (int i = 0; i < islemList.size(); i++) {
            String kaynak_ad = DBislem.getAd("SELECT * FROM hesaplar where hesap_id=" + islemList.get(i).getKaynak_id(), null, "hesap_adi");
            String hedef_ad = DBislem.getAd("SELECT * FROM hesaplar where hesap_id=" + islemList.get(i).getHedef_id(), null, "hesap_adi");
            String islem_ad = DBislem.getAd("SELECT * FROM islem_turleri where islem_tur_id=" + islemList.get(i).getIslem_tur_id(), null, "islem_adi");
            int kisi_id = DBislem.getID("SELECT * FROM musteri m, kisi k where m.kisi_id=k.kisi_id and m.musteri_id= " + islemList.get(i).getMusteri_id(), null, "kisi_id");
            String adSoyad = DBislem.getAd("SELECT * FROM kisi where kisi_id=" + kisi_id, null, "ad") + " "
                    + DBislem.getAd("SELECT * FROM kisi where kisi_id=" + kisi_id, null, "soyad");
            row[0] = islemList.get(i).getIslem_id();
            row[1] = adSoyad;
            row[2] = kaynak_ad;
            row[3] = hedef_ad;
            row[4] = islem_ad;
            row[5] = islemList.get(i).getIslem_tutari();
            row[6] = islemList.get(i).getKaynak_bakiye();
            row[7] = islemList.get(i).getHedef_bakiye();
            row[8] = islemList.get(i).getIslem_tarihi();

            int islem_tur = islemList.get(i).getIslem_tur_id();
            int musteri_id = islemList.get(i).getMusteri_id();
            if (hedef_ad.equals("") && (islem_tur == 4 || islem_tur == 5)) {
                row[3] = "Banka";
            } else if (kaynak_ad.equals("") && (islem_tur == 4 || islem_tur == 5)) {
                row[2] = "Banka";
            } else if (kaynak_ad.equals("") && (islem_tur == 1 || islem_tur == 2)) {
                int k_id = DBislem.getID("Select * from musteri where musteri_id=" + musteri_id, null, "kisi_id");
                String ad_Soyad = DBislem.getAd("Select * from kisi where kisi_id=" + k_id, null, "ad") + " "
                        + DBislem.getAd("Select * from kisi where kisi_id=" + k_id, null, "soyad");
                row[2] = adSoyad;

            } else if (hedef_ad.equals("") && (islem_tur == 1 || islem_tur == 2)) {
                int k_id = DBislem.getID("Select * from musteri where musteri_id=" + musteri_id, null, "kisi_id");
                String ad_Soyad = DBislem.getAd("Select * from kisi where kisi_id=" + k_id, null, "ad") + " "
                        + DBislem.getAd("Select * from kisi where kisi_id=" + k_id, null, "soyad");
                row[3] = ad_Soyad;

            }

            model.addRow(row);
        }

        islemTable.removeAll();
        islemTable.setModel(model);
        islemTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        islemTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        islemTable.getColumnModel().getColumn(2).setPreferredWidth(25);
        islemTable.getColumnModel().getColumn(3).setPreferredWidth(40);
        islemTable.getColumnModel().getColumn(4).setPreferredWidth(40);
        islemTable.getColumnModel().getColumn(5).setPreferredWidth(40);
        islemTable.getColumnModel().getColumn(7).setPreferredWidth(40);
        islemTable.getColumnModel().getColumn(6).setPreferredWidth(40);
    }

    private ArrayList<Islem> getAllIslem(int id, int kontrol, int limit) {
        ArrayList<Islem> islemList = new ArrayList<>();
        Islem islem;
        String sorgu = "";
        try {
            ConnectDB.connect();

            if (kontrol == 1) {
                sorgu = "SELECT * FROM islemler i"
                        + " WHERE musteri_id=" + id
                        + " order by islem_id desc";
            } else if (kontrol == 2) {
                sorgu = "SELECT * FROM islemler i, temsilcinin_musterileri tm"
                        + " WHERE tm.musteri_id=i.musteri_id and tm.temsilci_id=" + id
                        + " order by islem_id desc";
            } else if (kontrol == 3) {
                sorgu = "SELECT * FROM islemler"
                        + " order by islem_id desc LIMIT " + limit;
            }

            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                islem = new Islem();
                islem.setMusteri_id(resultSet.getInt("musteri_id"));
                islem.setKaynak_id(resultSet.getInt("kaynak_hesap_id"));
                islem.setHedef_id(resultSet.getInt("hedef_hesap_id"));
                islem.setKaynak_bakiye(resultSet.getDouble("kaynak_bakiye"));
                islem.setHedef_bakiye(resultSet.getDouble("hedef_bakiye"));
                islem.setIslem_tutari(resultSet.getDouble("islem_tutari"));
                islem.setIslem_tur_id(resultSet.getInt("islem_tur_id"));
                islem.setIslem_tarihi(resultSet.getString("islem_tarihi"));
                islem.setIslem_id(resultSet.getInt("islem_id"));

                islemList.add(islem);
            }
            preparedStatement.close();
            ConnectDB.con.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return islemList;
    }

    public void showKrediOdemeGecmisTable(JTable odemeGecmisTable, int musteri_id) {
        ArrayList<KrediOdeme> krediOdemeList = getAllKrediOdemeGecmis(musteri_id);
        DefaultTableModel model = (DefaultTableModel) odemeGecmisTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Kredi No", "Ödenen Ay", "Ödenen Ana Para", "Ödenen Toplam Faiz", "Ödenen Toplam Tutar", "Ödeme Tarihi"
        });
        Object[] row = new Object[6];

        for (int i = 0; i < krediOdemeList.size(); i++) {
            String kredi_id = "" + krediOdemeList.get(i).getKredi_id();
            double anaPara = DBislem.getFaizsiz(kredi_id);
            double toplamFaiz = krediOdemeList.get(i).getGecikme_zammi() + (krediOdemeList.get(i).getAylik_borc() - anaPara);
            row[0] = krediOdemeList.get(i).getKredi_id();
            row[1] = krediOdemeList.get(i).getOdenen_ay();
            row[2] = anaPara;
            row[3] = toplamFaiz;
            row[4] = anaPara + toplamFaiz;
            row[5] = krediOdemeList.get(i).getOdeme_tarih();

            model.addRow(row);
        }

        odemeGecmisTable.removeAll();
        odemeGecmisTable.setModel(model);
    }

    private ArrayList<KrediOdeme> getAllKrediOdemeGecmis(int musteri_id) {
        ArrayList<KrediOdeme> krediOdemeList = new ArrayList<>();
        KrediOdeme odeme;

        try {
            ConnectDB.connect();

            String sorgu = "SELECT * "
                    + "FROM kredi_odeme ko, kredi k "
                    + "where  ko.kredi_id = k.kredi_id and "
                    + "ko.odendi_mi= true and"
                    + " k.musteri_id=" + musteri_id
                    + " order by kredi_odeme_id desc";

            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                odeme = new KrediOdeme();

                odeme.setKredi_odeme_id(resultSet.getInt("kredi_odeme_id"));
                odeme.setKredi_id(resultSet.getInt("kredi_id"));
                odeme.setAylik_borc(resultSet.getDouble("aylik_borc"));
                odeme.setOdenen_ay(resultSet.getInt("odenen_ay"));
                odeme.setGecikme_zammi(resultSet.getDouble("gecikme_zammi"));
                odeme.setOdeme_tarih(resultSet.getString("odeme_tarih"));

                krediOdemeList.add(odeme);
            }
            preparedStatement.close();
            ConnectDB.con.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return krediOdemeList;
    }

    public void showParaBirimTable(JTable paraBirimTable) {
        ArrayList<ParaBirim> paraBirimList = getAllParaBirim();
        DefaultTableModel model = (DefaultTableModel) paraBirimTable.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Para Birim No", "Para Birim Adı", "Kur Sabiti"
        });
        Object[] row = new Object[3];

        for (int i = 0; i < paraBirimList.size(); i++) {
            row[0] = paraBirimList.get(i).getPara_birim_id();
            row[1] = paraBirimList.get(i).getPara_birim_adi();
            row[2] = paraBirimList.get(i).getKur_sabiti();

            model.addRow(row);
        }

        paraBirimTable.removeAll();
        paraBirimTable.setModel(model);
    }

    private ArrayList<ParaBirim> getAllParaBirim() {
        ArrayList<ParaBirim> paraBirimList = new ArrayList<>();
        ParaBirim paraBirim;

        try {
            ConnectDB.connect();

            String sorgu = "SELECT * FROM para_birim";

            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                paraBirim = new ParaBirim();
                paraBirim.setPara_birim_id(resultSet.getInt("para_birim_id"));
                paraBirim.setPara_birim_adi(resultSet.getString("para_birim_adi"));
                paraBirim.setKur_sabiti(resultSet.getDouble("kur_sabiti"));

                paraBirimList.add(paraBirim);
            }
            preparedStatement.close();
            ConnectDB.con.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return paraBirimList;
    }

}
