
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress;
    JRadioButton rbmale, rbfemale;
    String err_msg;

    public AddCustomer()  {
        setLayout(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon("Images/bookflightnew.jpg");
        Image backgroundImage = backgroundIcon.getImage();

        Image scaledImage = backgroundImage.getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledBackgroundIcon);
        setContentPane(background);
        background.setLayout(null);

        Font labelFont = new Font("Calisto MT", Font.BOLD, 20);
        Color labelColor = Color.white;

        JLabel heading = new JLabel("Add Customer Details");
        heading.setFont(new Font("Calisto MT", Font.BOLD, 28));
        heading.setForeground(Color.BLACK);
        heading.setBounds(160, 30, 500, 35);
        background.add(heading);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 80, 150, 25);
        lblname.setFont(labelFont);
        lblname.setForeground(labelColor);
        background.add(lblname);

        tfname = new JTextField();
        tfname.setBounds(220, 80, 150, 25);
        background.add(tfname);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 130, 150, 25);
        lblnationality.setFont(labelFont);
        lblnationality.setForeground(labelColor);
        background.add(lblnationality);

        tfnationality = new JTextField();
        tfnationality.setBounds(220, 130, 150, 25);
        background.add(tfnationality);

        JLabel lblaadhar = new JLabel("Aadhar Number");
        lblaadhar.setBounds(60, 180, 150, 25);
        lblaadhar.setFont(labelFont);
        lblaadhar.setForeground(labelColor);
        background.add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 180, 150, 25);
        background.add(tfaadhar);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(labelFont);
        lbladdress.setForeground(labelColor);
        background.add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(220, 230, 150, 25);
        background.add(tfaddress);

        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(labelFont);
        lblgender.setForeground(labelColor);
        lblgender.setBackground(Color.BLUE);
        background.add(lblgender);

        ButtonGroup gendergroup = new ButtonGroup();

        rbmale = new JRadioButton("Male");
        rbmale.setBounds(220, 280, 70, 25);
        rbmale.setBackground(Color.cyan);
        background.add(rbmale);

        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(300, 280, 70, 25);
        rbfemale.setBackground(Color.cyan);
        background.add(rbfemale);

        gendergroup.add(rbmale);
        gendergroup.add(rbfemale);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(60, 330, 150, 25);
        lblphone.setFont(labelFont);
        lblphone.setForeground(labelColor);
        background.add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(220, 330, 150, 25);
        background.add(tfphone);

        JButton save = new JButton("SAVE");
        save.setBackground(Color.cyan);
        save.setForeground(Color.black);
        save.setBounds(220, 380, 150, 30);
        save.addActionListener(this);
        background.add(save);

        setSize(590, 590);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String phone = tfphone.getText();
        String address = tfaddress.getText();
        String aadhar = tfaadhar.getText();
        String gender = rbmale.isSelected() ? "Male" : "Female";

        try {
            Conn conn = new Conn();
            
            String query = "insert into passenger values('"+name+"', '"+nationality+"', '"+phone+"', '"+address+"', '"+aadhar+"', '"+gender+"')";
            
                if (err_msg == null) {
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
            } else {
                JOptionPane.showMessageDialog(this,err_msg);
            }
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
