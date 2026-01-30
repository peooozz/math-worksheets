import javax.swing.*;
import java.awt.event.*;

public class MouseEventExample extends JFrame {
    public MouseEventExample() {
        // Create a JPanel
        JPanel panel = new JPanel();
        
        // Add MouseListener to the panel
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse Clicked at: " + e.getX() + ", " + e.getY());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse Entered the panel.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse Exited the panel.");
            }
        });
        
        // Add MouseMotionListener to detect dragging
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("Mouse Dragged at: " + e.getX() + ", " + e.getY());
            }
        });
        
        add(panel);
        setTitle("MouseEvent Example");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MouseEventExample();
    }
}
