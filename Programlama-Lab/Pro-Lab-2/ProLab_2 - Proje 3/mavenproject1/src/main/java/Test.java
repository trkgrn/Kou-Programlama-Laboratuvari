
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author takoy
 */
public class Test {



    public static void main(String[] args) throws ParseException, SQLException {
        
       

        //  Graph test =  createGraph();
        //   System.out.println(test.printGraph());
        /*    String str = "2022-05-04|2022-05-31";
        str = str.replace("|", "X");
        String[] dizi = str.split("X");
        String x = dizi[0];
        String y = dizi[1];
        System.out.println(x + "  " + y);
        Date islemZamani = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(df.format(islemZamani));

        Date d1, d2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d1 = simpleDateFormat.parse(x);
            d2 = simpleDateFormat.parse(y);

            // compareTo() ile tarihleri karşılaştırma
            if (islemZamani.compareTo(d1) >= 0 && islemZamani.compareTo(d2) < 0) {
                System.out.println("İşlem Zamanı ödeme aralığında ise (Normal Faiz)");
            } else if (islemZamani.compareTo(d1) < 0) {
                System.out.println("İşlem Zamanı ödemenin başlangıcından önceyse (Faiz almıyoruz)");
            } else if (islemZamani.compareTo(d2) > 0) {
                System.out.println("İşlem Zamanı ödemenin son tarihini geçmiş ise (Faiz+Gecikme Faizi)");
            }

        } catch (Exception e) {

        }

        DBislem.getTarihFark("2023-02-04");*/
 /*  DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Tarih.getInstance();
        System.out.println(myFormat.format(Tarih.getAnlikTarih()));
        Tarih.getInstance().tarihiIleriAl();
        DBislem.getTarihFark("2023-02-04");
        System.out.println(myFormat.format(Tarih.getAnlikTarih()));
        Tarih.getInstance().tarihiIleriAl();
        System.out.println(myFormat.format(Tarih.getAnlikTarih()));
        DBislem.getTarihFark("2023-02-04");
        
        
        long fark = DBislem.getTarihFark(Tarih.currentDate);
        System.out.println("FARK: "+fark);
        System.out.println(Tarih.currentDate); 
        String query = "UPDATE a SET ax = CURRENT_DATE +"+fark +" WHERE ax is null";
        DBislem.updateQuery(query);
        
        for (int i = 0; i < DBislem.getMusteriSayisi(); i++) {
            System.out.println(DBislem.getMusteriler(DBislem.getMusteriSayisi())[i]);
        }*/
     /*   Tarih.getInstance();
        System.out.println(Tarih.myFormat.format(Tarih.ayBasi()));
        System.out.println(Tarih.myFormat.format(Tarih.aySonu()));
        System.out.println(DBislem.getTarihFarkAyBasi());
        System.out.println(DBislem.getTarihFarkAySonu());
        Tarih.getInstance().tarihiIleriAl();
        System.out.println(Tarih.myFormat.format(Tarih.ayBasi()));
        System.out.println(Tarih.myFormat.format(Tarih.aySonu()));
        System.out.println(DBislem.getTarihFarkAyBasi());
        System.out.println(DBislem.getTarihFarkAySonu());*/

    }
}
