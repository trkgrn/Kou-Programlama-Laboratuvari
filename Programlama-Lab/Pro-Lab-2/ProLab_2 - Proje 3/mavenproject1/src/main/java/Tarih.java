
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author trkgrn
 */
public class Tarih {

    private static Tarih instance;
    private static Date anlikTarih;
    public static DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String currentDate;

    private Tarih() {

    }

    public static Tarih getInstance() {
        if (instance == null) {
            instance = new Tarih();
            anlikTarih = new Date();
            currentDate = myFormat.format(anlikTarih);
        }
        return instance;
    }

    public static Date getAnlikTarih() {
        return anlikTarih;
    }

    public void tarihiIleriAl() {
        Calendar temp = Calendar.getInstance();
        temp.setTime(anlikTarih);
        temp.add(Calendar.MONTH, 1);
        anlikTarih = temp.getTime();
    }

    public static Date ayBasi() {
        Calendar temp = Calendar.getInstance();
        temp.setTime(Tarih.getAnlikTarih());
        temp.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), 1);
        Date ayBasi = temp.getTime();
        return ayBasi;
    }

    public static Date aySonu() {
        Calendar temp = Calendar.getInstance();
        temp.setTime(Tarih.getAnlikTarih());
        if (temp.get(Calendar.MONTH) == 3 || temp.get(Calendar.MONTH) == 5
                || temp.get(Calendar.MONTH) == 8 || temp.get(Calendar.MONTH) == 10) {
            temp.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), 30);
        } else if (temp.get(Calendar.MONTH) == 1) {
            temp.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), 28);
        } else {
            temp.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), 31);
        }

        Date aySonu = temp.getTime();
        return aySonu;
    }

}
