import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Help extends JFrame {
	Help() throws IOException {
		
		ImageIcon help = new ImageIcon("/resources/help.PNG");
	JLabel image = new JLabel();
	JPanel helppanel = new JPanel();
	image.setIcon(help);
	JLabel not = new JLabel("Input expressions into the textbox on the top of screen. To express NOT use ! or ~");

	helppanel.add(image);
helppanel.add(not);
	add(helppanel);
		setSize(1500, 1000);
		setTitle("Help");
		setVisible(true);

		;
	}
}
