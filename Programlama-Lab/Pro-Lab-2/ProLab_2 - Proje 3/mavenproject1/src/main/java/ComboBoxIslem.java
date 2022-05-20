
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author trkgrn
 */
public class ComboBoxIslem {

    public static void getComboData(JComboBox comboBox, String sorgu, String column) {
        ConnectDB.connect();
        comboBox.removeAllItems();
        try {
            PreparedStatement preparedStatement = ConnectDB.con.prepareStatement(sorgu);
            ResultSet resultSet = preparedStatement.executeQuery();
            comboBox.addItem("<Seçiniz>");
            while (resultSet.next()) {
                comboBox.addItem(resultSet.getString(column));
            }

            preparedStatement.close();
            ConnectDB.con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    // hesapTurComboBox

}
