/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trkgrn
 */
public class KrediTalep {
    
    private int kredi_basvuru_id;
    private int musteri_id;
    private int temsilci_id;
    private int istenen_kredi_miktar;
    private String onay_durum;
    private int toplam_ay;
    private String adSoyad;
    private boolean silindi_mi;

    public boolean isSilindi_mi() {
        return silindi_mi;
    }

    public void setSilindi_mi(boolean silindi_mi) {
        this.silindi_mi = silindi_mi;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    KrediTalep() {
           }

    

    public int getToplam_ay() {
        return toplam_ay;
    }

    public void setToplam_ay(int toplam_ay) {
        this.toplam_ay = toplam_ay;
    }
    
    public KrediTalep(int musteri_id)
    {
        this.musteri_id = musteri_id; 
    }

    public int getKredi_basvuru_id() {
        return kredi_basvuru_id;
    }

    public void setKredi_basvuru_id(int kredi_basvuru_id) {
        this.kredi_basvuru_id = kredi_basvuru_id;
    }

    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }

    public int getTemsilci_id() {
        return temsilci_id;
    }

    public void setTemsilci_id(int temsilci_id) {
        this.temsilci_id = temsilci_id;
    }

    public int getIstenen_kredi_miktar() {
        return istenen_kredi_miktar;
    }

    public void setIstenen_kredi_miktar(int istenen_kredi_miktar) {
        this.istenen_kredi_miktar = istenen_kredi_miktar;
    }

    public String getOnay_durum() {
        return onay_durum;
    }

    public void setOnay_durum(String onay_durum) {
        this.onay_durum = onay_durum;
    }
    
}
