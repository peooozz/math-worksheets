import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Cancel extends JFrame implements ActionListener {
    
    JTextField tfpnr;
    JLabel tfname, cancellationno, lblfcode, lbldateoftravel;
    JButton fetchButton, flight;
    
    public Cancel() {
        setLayout(new BorderLayout());
        
        
        ImageIcon backgroundIcon = new ImageIcon("Images/tick.jpg");
        Image backgroundImage = backgroundIcon.getImage();
        Image scaledImage = backgroundImage.getScaledInstance(800, 450, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledBackgroundIcon);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);  
        panel.setLayout(null);
        
        
        Font labelFont = new Font("Times New Roman", Font.BOLD, 16);
        Color textColor = Color.white;

        
        JLabel heading = new JLabel("Ticket Cancellation");
        heading.setBounds(250, 13, 250, 35);
        heading.setFont(new Font("Calisto MT", Font.BOLD, 28));
        heading.setForeground(Color.cyan);
        
        panel.add(heading);
        
        JLabel lblaadhar = new JLabel("PNR Number");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(labelFont);
        lblaadhar.setForeground(textColor);
        panel.add(lblaadhar);
        
        tfpnr = new JTextField();
        tfpnr.setBounds(220, 80, 150, 25);
        panel.add(tfpnr);
        
        fetchButton = new JButton("Show Details");
        fetchButton.setBackground(Color.blue);
        fetchButton.setForeground(Color.black);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        panel.add(fetchButton);
        
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(labelFont);
        lblname.setForeground(textColor);
        panel.add(lblname);
        
        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        tfname.setForeground(textColor);
        panel.add(tfname);
        
        JLabel lblnationality = new JLabel("Cancellation No");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(labelFont);
        lblnationality.setForeground(textColor);
        panel.add(lblnationality);
        
        cancellationno = new JLabel("" + new Random().nextInt(1000000));
        cancellationno.setBounds(220, 180, 150, 25);
        cancellationno.setForeground(textColor);
        panel.add(cancellationno);
        
        JLabel lbladdress = new JLabel("Flight Code");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(labelFont);
        lbladdress.setForeground(textColor);
        panel.add(lbladdress);
        
        lblfcode = new JLabel();
        lblfcode.setBounds(220, 230, 150, 25);
        lblfcode.setForeground(textColor);
        panel.add(lblfcode);
        
        JLabel lblgender = new JLabel("Date");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(labelFont);
        lblgender.setForeground(textColor);
        panel.add(lblgender);
        
        lbldateoftravel = new JLabel();
        lbldateoftravel.setBounds(220, 280, 150, 25);
        lbldateoftravel.setForeground(textColor);
        panel.add(lbldateoftravel);
        
        flight = new JButton("Cancel");
        flight.setBackground(Color.blue);
        flight.setForeground(Color.black);
        flight.setBounds(220, 330, 120, 25);
        flight.addActionListener(this);
        panel.add(flight);
        
        background.setLayout(new BorderLayout());
        background.add(panel, BorderLayout.CENTER);
        
        add(background);
        
        setSize(800, 450);
        setLocation(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String pnr = tfpnr.getText();
            
            try {
                Conn conn = new Conn();

                String query = "select * from reservation where PNR = '"+pnr+"'";

                ResultSet rs = conn.s.executeQuery(query);
                
                if (rs.next()) {
                    tfname.setText(rs.getString("name")); 
                    lblfcode.setText(rs.getString("flightcode")); 
                    lbldateoftravel.setText(rs.getString("ddate"));
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct PNR");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == flight) {
            String name = tfname.getText();
            String pnr = tfpnr.getText();
            String cancelno = cancellationno.getText();
            String fcode = lblfcode.getText();
            String date = lbldateoftravel.getText();
            
            try {
                Conn conn = new Conn();

                String query = "insert into cancel values('"+pnr+"', '"+name+"', '"+cancelno+"', '"+fcode+"', '"+date+"')";

                conn.s.executeUpdate(query);
                conn.s.executeUpdate("delete from reservation where PNR = '"+pnr+"'");
                
                JOptionPane.showMessageDialog(null, "Ticket Cancelled");
                setVisible(false);
            
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
    }

    public static void main(String[] args) {
        new Cancel();
    }
}
