import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class login extends JFrame implements ActionListener {
    JButton submit, reset, close;
    JTextField tfusername;
    JPasswordField tfpassword;

    public login() {
      
        setTitle("Login");
        setSize(400, 350);
        setLocation(600, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        BackgroundPanel backgroundPanel = new BackgroundPanel(new ImageIcon("Images/airplane.png").getImage());
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

       
        JLabel lblusername = new JLabel("Username");
        lblusername.setFont(new Font("Times new roman", Font.BOLD, 20));
        lblusername.setBounds(50, 100, 100, 30);
        lblusername.setForeground(Color.BLACK);
        backgroundPanel.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150, 100, 200, 30);
        backgroundPanel.add(tfusername);

        
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setFont(new Font("Times new roman", Font.BOLD, 20));
        lblpassword.setBounds(50, 150, 100, 30);
        lblpassword.setForeground(Color.BLACK);
        backgroundPanel.add(lblpassword);

     
        tfpassword = new JPasswordField();
        tfpassword.setBounds(150, 150, 200, 30);
        backgroundPanel.add(tfpassword);

       
        reset = new JButton("Clear");
        reset.setBounds(50, 200, 120, 30);
        reset.addActionListener(this);
        reset.setForeground(Color.black);
        reset.setBackground(Color.cyan);
        backgroundPanel.add(reset);

     
        submit = new JButton("Submit");
        submit.setBounds(230, 200, 120, 30);
        submit.addActionListener(this);
        submit.setForeground(Color.black);
        submit.setBackground(Color.cyan);
        backgroundPanel.add(submit);

        close = new JButton("Close");
        close.setBounds(140, 250, 120, 30);
        close.addActionListener(this);
        close.setForeground(Color.black);
        close.setBackground(Color.cyan);
        backgroundPanel.add(close);

        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());

            try {
                Conn c = new Conn();
                String query = "select * from login where username = '" + username + "' and password = '" + password + "'";
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    new Home();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == close) {
            setVisible(false);
        } else if (ae.getSource() == reset) {
            tfusername.setText("");
            tfpassword.setText("");
        }
    }

    public static void main(String[] args) {
        new login();
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
