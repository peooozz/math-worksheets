import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;

public class BookFlight extends JFrame implements ActionListener {

    JTextField tfaadhar;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfname, labelfcode;
    JButton bookflight, fetchButton, flight;
    Choice source, destination;
    JDateChooser dcdate;

    public BookFlight() {
        setLayout(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon("Images/book.jpg");
        Image backgroundImage = backgroundIcon.getImage();
        

        Image scaledImage = backgroundImage.getScaledInstance(1100, 700, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledBackgroundIcon);
        setContentPane(background);
        background.setLayout(null);

        Font labelFont = new Font("Calisto MT", Font.BOLD, 18);
        Color labelColor = Color.black;

        JLabel lblaadhar = new JLabel("Aadhar");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(labelFont);
        lblaadhar.setForeground(labelColor);
        background.add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 80, 150, 25);
        background.add(tfaadhar);

        fetchButton = new JButton("Fetch User");
        fetchButton.setBackground(Color.yellow);
        fetchButton.setForeground(Color.black);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        background.add(fetchButton);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(labelFont);
        lblname.setForeground(labelColor);
        background.add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        tfname.setFont(labelFont);
        tfname.setForeground(labelColor);
        background.add(tfname);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(labelFont);
        lblnationality.setForeground(labelColor);
        background.add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        tfnationality.setFont(labelFont);
        tfnationality.setForeground(labelColor);
        background.add(tfnationality);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(labelFont);
        lbladdress.setForeground(labelColor);
        background.add(lbladdress);

        tfaddress = new JLabel();
        tfaddress.setBounds(220, 230, 150, 25);
        tfaddress.setFont(labelFont);
        tfaddress.setForeground(labelColor);
        background.add(tfaddress);

        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(labelFont);
        lblgender.setForeground(labelColor);
        background.add(lblgender);

        labelgender = new JLabel();
        labelgender.setBounds(220, 280, 150, 25);
        labelgender.setFont(labelFont);
        labelgender.setForeground(labelColor);
        background.add(labelgender);

        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(60, 330, 150, 25);
        lblsource.setFont(labelFont);
        lblsource.setForeground(labelColor);
        background.add(lblsource);

        source = new Choice();
        source.setBounds(220, 330, 150, 25);
        background.add(source);

        JLabel lbldest = new JLabel("Destination");
        lbldest.setBounds(60, 380, 150, 25);
        lbldest.setFont(labelFont);
        lbldest.setForeground(labelColor);
        background.add(lbldest);

        destination = new Choice();
        destination.setBounds(220, 380, 150, 25);
        background.add(destination);

        try {
            Conn c = new Conn();
            String query = "select * from flight";
            ResultSet rs = c.s.executeQuery(query);
            
            while (rs.next()) {
                source.add(rs.getString("source"));
                destination.add(rs.getString("destination"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        flight = new JButton("Fetch Flights");
        flight.setBackground(Color.yellow);
        flight.setForeground(Color.black);
        flight.setBounds(380, 380, 120, 25);
        flight.addActionListener(this);
        background.add(flight);

        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(60, 430, 150, 25);
        lblfname.setFont(labelFont);
        lblfname.setForeground(labelColor);
        background.add(lblfname);

        labelfname = new JLabel();
        labelfname.setBounds(220, 430, 150, 25);
        labelfname.setFont(labelFont);
        labelfname.setForeground(labelColor);
        background.add(labelfname);

        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(60, 480, 150, 25);
        lblfcode.setFont(labelFont);
        lblfcode.setForeground(labelColor);
        background.add(lblfcode);

        labelfcode = new JLabel();
        labelfcode.setBounds(220, 480, 150, 25);
        labelfcode.setFont(labelFont);
        labelfcode.setForeground(labelColor);
        background.add(labelfcode);

        JLabel lbldate = new JLabel("Date of Travel");
        lbldate.setBounds(60, 530, 150, 25);
        lbldate.setFont(labelFont);
        lbldate.setForeground(labelColor);
        background.add(lbldate);

        dcdate = new JDateChooser();
        dcdate.setBounds(220, 530, 150, 25);
        background.add(dcdate);

        bookflight = new JButton("Book Flight");
        bookflight.setBackground(Color.yellow);
        bookflight.setForeground(Color.black);
        bookflight.setBounds(220, 580, 150, 25);
        bookflight.addActionListener(this);
        background.add(bookflight);

        setSize(580, 680);
        setLocation(200, 50);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String aadhar = tfaadhar.getText();
            
            try {
                Conn conn = new Conn();
                String query = "select * from passenger where aadhar = '"+aadhar+"'";
                ResultSet rs = conn.s.executeQuery(query);
                
                if (rs.next()) {
                    tfname.setText(rs.getString("name")); 
                    tfnationality.setText(rs.getString("nationality")); 
                    tfaddress.setText(rs.getString("address"));
                    labelgender.setText(rs.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct aadhar");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == flight) {
            String src = source.getSelectedItem();
            String dest = destination.getSelectedItem();
            try {
                Conn conn = new Conn();
                String query = "select * from flight where source = '"+src+"' and destination = '"+dest+"'";
                ResultSet rs = conn.s.executeQuery(query);
                
                if (rs.next()) {
                    labelfname.setText(rs.getString("f_name")); 
                    labelfcode.setText(rs.getString("f_code")); 
                } else {
                    JOptionPane.showMessageDialog(null, "No Flights Found");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Random random = new Random();
            
            String aadhar = tfaadhar.getText();
            String name = tfname.getText(); 
            String nationality = tfnationality.getText();
            String flightname = labelfname.getText(); 
            String flightcode = labelfcode.getText();
            String src = source.getSelectedItem(); 
            String des = destination.getSelectedItem();
            String ddate = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();
            
            try {
                Conn conn = new Conn();
                String query = "insert into reservation values('PNR-"+random.nextInt(1000000)+"', 'TIC-"+random.nextInt(10000)+"', '"+aadhar+"', '"+name+"', '"+nationality+"', '"+flightname+"', '"+flightcode+"', '"+src+"', '"+des+"', '"+ddate+"')";
                conn.s.executeUpdate(query);
                
                JOptionPane.showMessageDialog(null, "Ticket Booked Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new BookFlight();
    }
}
