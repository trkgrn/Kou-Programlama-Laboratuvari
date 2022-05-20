/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trkgrn
 */
public class KrediOdeme {
    
    private int kredi_odeme_id;
    private int kredi_id;
    private int musteri_id;
    private double aylik_borc;
    private int odenen_ay;
    private boolean odendi_mi;
    private double gecikme_zammi;
    private double kalan_aylik_borc;
    private String odeme_aralik;
    private String asgari_odeme_tarih;
    private String son_odeme_tarih;
    private boolean odeme_durum;
    private String odeme_tarih;

    public String getOdeme_tarih() {
        return odeme_tarih;
    }

    public void setOdeme_tarih(String odeme_tarih) {
        this.odeme_tarih = odeme_tarih;
    }

    public boolean isOdeme_durum() {
        return odeme_durum;
    }

    public void setOdeme_durum(boolean odeme_durum) {
        this.odeme_durum = odeme_durum;
    }

    public int getKredi_id() {
        return kredi_id;
    }

    public void setKredi_id(int kredi_id) {
        this.kredi_id = kredi_id;
    }
    public int getKredi_odeme_id() {
        return kredi_odeme_id;
    }

    public void setKredi_odeme_id(int kredi_odeme_id) {
        this.kredi_odeme_id = kredi_odeme_id;
    }

    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }

    public double getAylik_borc() {
        return aylik_borc;
    }

    public void setAylik_borc(double aylik_borc) {
        this.aylik_borc = aylik_borc;
    }

    public int getOdenen_ay() {
        return odenen_ay;
    }

    public void setOdenen_ay(int odenen_ay) {
        this.odenen_ay = odenen_ay;
    }

    public boolean isOdendi_mi() {
        return odendi_mi;
    }

    public void setOdendi_mi(boolean odendi_mi) {
        this.odendi_mi = odendi_mi;
    }

    public double getGecikme_zammi() {
        return gecikme_zammi;
    }

    public void setGecikme_zammi(double gecikme_zammi) {
        this.gecikme_zammi = gecikme_zammi;
    }

    public double getKalan_aylik_borc() {
        return kalan_aylik_borc;
    }

    public void setKalan_aylik_borc(double kalan_aylik_borc) {
        this.kalan_aylik_borc = kalan_aylik_borc;
    }

    public String getOdeme_aralik() {
        return odeme_aralik;
    }

    public void setOdeme_aralik(String odeme_aralik) {
        this.odeme_aralik = odeme_aralik;
    }

    public String getAsgari_odeme_tarih() {
        return asgari_odeme_tarih;
    }

    public void setAsgari_odeme_tarih(String asgari_odeme_tarih) {
        this.asgari_odeme_tarih = asgari_odeme_tarih;
    }

    public String getSon_odeme_tarih() {
        return son_odeme_tarih;
    }

    public void setSon_odeme_tarih(String son_odeme_tarih) {
        this.son_odeme_tarih = son_odeme_tarih;
    }
}
