import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Config extends JFrame {

	{

	}

	Config() {
		JButton confirm = new JButton("Confirm");
		JButton help = new JButton("Help");
		JPanel panelinput = new JPanel();

		JLabel labelinput = new JLabel("Configuration");
		labelinput.setFont(new Font("TimesRoman", Font.BOLD, 22));
		JLabel vars = new JLabel("Number of Variables");
		JLabel ones = new JLabel("Number of Ones");
		JLabel zeroes = new JLabel("Number of Zeroes");
		JLabel p1 = new JLabel("Player 1 Name");
		JLabel p2 = new JLabel("Player 2 Name");
		JTextField variables = new JTextField();
		JTextField numberof0s = new JTextField();
		JTextField numberof1s = new JTextField();
		JTextField player1name = new JTextField();
		JTextField player2name = new JTextField();
	
JPanel buttonpanelinput = new JPanel(new GridLayout(1,2));
		panelinput.setLayout(new BoxLayout(panelinput, BoxLayout.Y_AXIS));
		panelinput.add(labelinput);	
		panelinput.add(vars);
		panelinput.add(variables);
		panelinput.add(zeroes);
		panelinput.add(numberof0s);
		panelinput.add(ones);
		panelinput.add(numberof1s);
	
		panelinput.add(p1);
		panelinput.add(player1name);
		panelinput.add(p2);
		panelinput.add(player2name);
		buttonpanelinput.add(confirm);
		buttonpanelinput.add(help);
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
			int	pvariables = Integer.parseInt(variables.getText());
				int pzeroes = Integer.parseInt(numberof0s.getText());
				int pones = Integer.parseInt(numberof1s.getText());
				String pplayer1 = player1name.getText();
				String pplayer2 = player2name.getText();
			
					try {
						MainGame.intialize(pvariables,pzeroes,pones,pplayer1,pplayer2);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			

			}
		});
		help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			Help h;
			try {
				h = new Help();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			}
		});
		add(panelinput);
panelinput.add(buttonpanelinput);
		setSize(400, 300);
		setTitle("Configuration");
		setVisible(true);

		;
	}

}