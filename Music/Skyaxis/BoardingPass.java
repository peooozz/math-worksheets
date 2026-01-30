
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BoardingPass extends JFrame implements ActionListener {

    JTextField tfpnr;
    JLabel tfname, tfnationality, lblsrc, lbldest, labelfname, labelfcode, labeldate;
    JButton fetchButton;
    Image backgroundImage;

    public BoardingPass() {
        try {
            backgroundImage = new ImageIcon("Images/boardingpass.jpg").getImage();
            
            setContentPane(new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            });

            setLayout(null);

            int width = 750;

            int labelWidth = 150;
            int labelHeight = 25;
            int centerX = (width - labelWidth) / 2;
         Color x = Color.black;
            

            JLabel lblaadhar = new JLabel("PNR DETAILS");
            lblaadhar.setBounds(centerX - 160, 50, labelWidth, labelHeight);
            lblaadhar.setFont(new Font("Times new roman", Font.BOLD, 16));
            lblaadhar.setForeground(x);
            add(lblaadhar);

            tfpnr = new JTextField();
            tfpnr.setBounds(centerX, 50, labelWidth, labelHeight);
            add(tfpnr);

            fetchButton = new JButton("Enter");
            fetchButton.setBackground(Color.red);
            fetchButton.setForeground(Color.WHITE);
            fetchButton.setBounds(centerX + 160, 50, 100, 25);
            fetchButton.addActionListener(this);
            add(fetchButton);

            JLabel lblname = new JLabel("Name");
            lblname.setBounds(centerX - 160, 90, labelWidth, labelHeight);
            lblname.setFont(new Font("Times new roman", Font.BOLD, 16));
            lblname.setForeground(x);
            add(lblname);

            tfname = new JLabel();
            tfname.setBounds(centerX, 90, labelWidth, labelHeight);
            tfname.setForeground(x);
            add(tfname);

            JLabel lblnationality = new JLabel("Nationality");
            lblnationality.setBounds(centerX - 160, 130, labelWidth, labelHeight);
            lblnationality.setFont(new Font("Times new roman", Font.BOLD, 16));
            lblnationality.setForeground(x);
            add(lblnationality);

            tfnationality = new JLabel();
            tfnationality.setBounds(centerX, 130, labelWidth, labelHeight);
            tfnationality.setForeground(x);
            add(tfnationality);

            JLabel lbladdress = new JLabel("Source");
            lbladdress.setBounds(centerX - 160, 170, labelWidth, labelHeight);
            lbladdress.setFont(new Font("Times new roman", Font.BOLD, 16));
            lbladdress.setForeground(x);
            add(lbladdress);

            lblsrc = new JLabel();
            lblsrc.setBounds(centerX, 170, labelWidth, labelHeight);
            lblsrc.setForeground(x);
            add(lblsrc);

            JLabel lblgender = new JLabel("Destination");
            lblgender.setBounds(centerX + 160, 170, labelWidth, labelHeight);
            lblgender.setFont(new Font("Times new roman", Font.BOLD, 16));
            lblgender.setForeground(x);
            add(lblgender);

            lbldest = new JLabel();
            lbldest.setBounds(centerX + 320, 170, labelWidth, labelHeight);
            lbldest.setForeground(x);
            add(lbldest);

            JLabel lblfname = new JLabel("Flight Name");
            lblfname.setBounds(centerX - 160, 210, labelWidth, labelHeight);
            lblfname.setFont(new Font("Times new roman", Font.BOLD, 16));
            lblfname.setForeground(x);
            add(lblfname);

            labelfname = new JLabel();
            labelfname.setBounds(centerX, 210, labelWidth, labelHeight);
            labelfname.setForeground(x);
            add(labelfname);

            JLabel lblfcode = new JLabel("Flight Code");
            lblfcode.setBounds(centerX + 160, 210, labelWidth, labelHeight);
            lblfcode.setFont(new Font("Times new roman", Font.BOLD, 16));
            lblfcode.setForeground(x);
            add(lblfcode);

            labelfcode = new JLabel();
            labelfcode.setBounds(centerX + 320, 210, labelWidth, labelHeight);
            labelfcode.setForeground(x);
            add(labelfcode);

            JLabel lbldate = new JLabel("Date");
            lbldate.setBounds(centerX - 160, 250, labelWidth, labelHeight);
            lbldate.setFont(new Font("Times new roman", Font.BOLD, 16));
            lbldate.setForeground(x);
            add(lbldate);

            labeldate = new JLabel();
            labeldate.setBounds(centerX, 250, labelWidth, labelHeight);
            labeldate.setForeground(x);
            add(labeldate);

           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setSize(750, 320);
        setLocation(300, 150);
        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent ae) {
        String pnr = tfpnr.getText();

        try {
            Conn conn = new Conn();

            String query = "select * from reservation where PNR = '" + pnr + "'";

            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tfnationality.setText(rs.getString("nationality"));
                lblsrc.setText(rs.getString("src"));
                lbldest.setText(rs.getString("des"));
                labelfname.setText(rs.getString("flightname"));
                labelfcode.setText(rs.getString("flightcode"));
                labeldate.setText(rs.getString("ddate"));
            } else {
                JOptionPane.showMessageDialog(null, "Please enter correct PNR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
