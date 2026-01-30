

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class FlightInfo extends JFrame{
    
    public FlightInfo() {
        
        setLayout(null);
        
        JTable table = new JTable();
        
        try {
            Conn conn = new Conn();
            
            ResultSet rs = conn.s.executeQuery("select * from flight");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 0, 800, 200);
        add(jsp);
        
        setSize(750, 150);
        setLocation(400, 200);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
