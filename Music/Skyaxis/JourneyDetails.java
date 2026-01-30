import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
public class JourneyDetails extends JFrame implements ActionListener {
    JTable table;
    JTextField pnr;
    JButton show;

    public JourneyDetails() {
        setLayout(new BorderLayout());

        
        ImageIcon backgroundIcon = new ImageIcon("Images/image.png");
        Image backgroundImage = backgroundIcon.getImage();
        
        if (backgroundImage == null) {
            System.err.println("Image not found or could not be loaded.");
            return;
        }

        Image scaledImage = backgroundImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledBackgroundIcon);
        setContentPane(background);
        background.setLayout(null);

        JLabel lblpnr = new JLabel("PNR Details");
        lblpnr.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblpnr.setBounds(50, 50, 100, 25);
        lblpnr.setForeground(Color.yellow);
        background.add(lblpnr);

        pnr = new JTextField();
        pnr.setBounds(160, 50, 120, 25);
        background.add(pnr);

        show = new JButton("Show Details");
        show.setBackground(Color.orange);
        show.setForeground(Color.black);
        show.setBounds(290, 50, 120, 25);
        show.addActionListener(this);
        background.add(show);

        table = new JTable();

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 800, 150);
        jsp.setBackground(Color.WHITE);
        background.add(jsp);
        
        setSize(800, 400);
        setLocation(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from reservation where PNR = '" + pnr.getText() + "'");

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No Information Found");
                return;
            }
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
