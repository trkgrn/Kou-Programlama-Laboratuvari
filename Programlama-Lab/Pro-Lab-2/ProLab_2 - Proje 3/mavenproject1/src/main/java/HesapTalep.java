/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trkgrn
 */
public class HesapTalep {
    
    private int hesap_talep_id;
    private int musteri_id;
    private int temsilci_id;
    private String hesap_adi;
    private int hesap_tur_id;
    private String hesap_tur_adi;
    private String talep_tur;
    private int hesap_id;
    private boolean silindi_mi;

    public boolean isSilindi_mi() {
        return silindi_mi;
    }

    public void setSilindi_mi(boolean silindi_mi) {
        this.silindi_mi = silindi_mi;
    }

    public int getHesap_id() {
        return hesap_id;
    }

    public void setHesap_id(int hesap_id) {
        this.hesap_id = hesap_id;
    }

    public String getTalep_tur() {
        return talep_tur;
    }

    public void setTalep_tur(String talep_tur) {
        this.talep_tur = talep_tur;
    }

    public String getHesap_tur_adi() {
        return hesap_tur_adi;
    }

    public void setHesap_tur_adi(String hesap_tur_adi) {
        this.hesap_tur_adi = hesap_tur_adi;
    }
    private String onay_durum;

    public int getHesap_talep_id() {
        return hesap_talep_id;
    }

    public void setHesap_talep_id(int hesap_talep_id) {
        this.hesap_talep_id = hesap_talep_id;
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

    public String getHesap_adi() {
        return hesap_adi;
    }

    public void setHesap_adi(String hesap_adi) {
        this.hesap_adi = hesap_adi;
    }

    public int getHesap_tur_id() {
        return hesap_tur_id;
    }

    public void setHesap_tur_id(int hesap_tur_id) {
        this.hesap_tur_id = hesap_tur_id;
    }

    public String getOnay_durum() {
        return onay_durum;
    }

    public void setOnay_durum(String onay_durum) {
        this.onay_durum = onay_durum;
    }
  
}
