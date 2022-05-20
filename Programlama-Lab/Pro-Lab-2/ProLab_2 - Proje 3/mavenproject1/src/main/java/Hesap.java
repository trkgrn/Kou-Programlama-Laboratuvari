/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trkgrn
 */
public class Hesap {
    
    private int hesap_id;
    private int musteri_id;
    private String hesap_adi;
    private int hesap_tur_id;
    private double hesap_bakiye;
    private String hesap_tur_adi;
    private int hesap_para_birim_id;
    private String para_birim_adi;
    private double kur_sabiti;
    private double hesap_bakiye_TL;

    public double getHesap_bakiye_TL() {
        return hesap_bakiye_TL;
    }

    public void setHesap_bakiye_TL(double hesap_bakiye_TL) {
        this.hesap_bakiye_TL = hesap_bakiye_TL;
    }

    public int getHesap_id() {
        return hesap_id;
    }

    public void setHesap_id(int hesap_id) {
        this.hesap_id = hesap_id;
    }

    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
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

    public double getHesap_bakiye() {
        return hesap_bakiye;
    }

    public void setHesap_bakiye(double hesap_bakiye) {
        this.hesap_bakiye = hesap_bakiye;
    }

    public String getHesap_tur_adi() {
        return hesap_tur_adi;
    }

    public void setHesap_tur_adi(String hesap_tur_adi) {
        this.hesap_tur_adi = hesap_tur_adi;
    }

    public int getHesap_para_birim_id() {
        return hesap_para_birim_id;
    }

    public void setHesap_para_birim_id(int hesap_para_birim_id) {
        this.hesap_para_birim_id = hesap_para_birim_id;
    }

    public String getPara_birim_adi() {
        return para_birim_adi;
    }

    public void setPara_birim_adi(String hesap_para_birim_adi) {
        this.para_birim_adi = hesap_para_birim_adi;
    }

    public double getKur_sabiti() {
        return kur_sabiti;
    }

    public void setKur_sabiti(double kur_sabiti) {
        this.kur_sabiti = kur_sabiti;
    }
    
}
