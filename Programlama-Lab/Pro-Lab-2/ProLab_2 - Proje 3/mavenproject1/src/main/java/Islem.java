/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trkgrn
 */
public class Islem {

    private int islem_id;
    private int kaynak_id;
    private int hedef_id;
    private int islem_tur_id;
    private String islem_tarihi;
    private double islem_tutari;
    private double kaynak_bakiye;
    private double hedef_bakiye;
    private int musteri_id;

    public int getIslem_id() {
        return islem_id;
    }

    public void setIslem_id(int islem_id) {
        this.islem_id = islem_id;
    }

    public int getKaynak_id() {
        return kaynak_id;
    }

    public void setKaynak_id(int kaynak_id) {
        this.kaynak_id = kaynak_id;
    }

    public int getHedef_id() {
        return hedef_id;
    }

    public void setHedef_id(int hedef_id) {
        this.hedef_id = hedef_id;
    }

    public int getIslem_tur_id() {
        return islem_tur_id;
    }

    public void setIslem_tur_id(int islem_tur_id) {
        this.islem_tur_id = islem_tur_id;
    }

    public String getIslem_tarihi() {
        return islem_tarihi;
    }

    public void setIslem_tarihi(String islem_tarihi) {
        this.islem_tarihi = islem_tarihi;
    }

    public double getIslem_tutari() {
        return islem_tutari;
    }

    public void setIslem_tutari(double islem_tutari) {
        this.islem_tutari = islem_tutari;
    }

    public double getKaynak_bakiye() {
        return kaynak_bakiye;
    }

    public void setKaynak_bakiye(double kaynak_bakiye) {
        this.kaynak_bakiye = kaynak_bakiye;
    }

    public double getHedef_bakiye() {
        return hedef_bakiye;
    }

    public void setHedef_bakiye(double hedef_bakiye) {
        this.hedef_bakiye = hedef_bakiye;
    }

    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }

}
