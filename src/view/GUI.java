package src.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.model.GenerateKML;

public class GUI {

    private static JFrame frame = new JFrame();
    private static JPanel panel = new JPanel();
    private static JLabel label = new JLabel("Name: ");
    private static JButton button = new JButton("Search");
    private static ImageIcon img = new ImageIcon("src\\assets\\google-earth-icon.jpg");
    private static JTextField textField = new JTextField(20);

    static {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Generate KML");
        frame.setSize(350, 70);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.showOpenDialog(chooser);
                    chooser.setVisible(true);
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        GenerateKML.create(textField.getText(), path);
                    } catch (RuntimeException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Generated KML");
                } catch (NullPointerException exc) {
                    System.out.println("Import canceled");
                }
            }
        });
    }

    public static void run() {
        panel.add(label);
        panel.add(textField);
        panel.add(button);
        frame.add(panel);
        frame.setIconImage(img.getImage());
        frame.setVisible(true);
    }


}