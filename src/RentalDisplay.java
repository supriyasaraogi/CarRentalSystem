import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;

public class RentalDisplay extends JFrame{
	
	JFrame frame = new JFrame();
	JPanel panel;
	JPanel addVehiclePanel;

	public void makeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));
		setContentPane(panel);
		panel.setLayout(null);
		frame.setVisible(true);
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 484, 361);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new CardLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, "name_2326424398309135");
		panel_2.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(180, 161, 89, 23);
		panel_2.add(btnNewButton);
		
		
		JLabel lblThisIsPanel = new JLabel("This is Panel 1");
		lblThisIsPanel.setBounds(198, 63, 89, 14);
		panel_2.add(lblThisIsPanel);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, "name_2326427798455386");
		panel_3.setLayout(null);
		
		JButton btnToPanel = new JButton("To Panel 3");
		btnToPanel.setBounds(92, 111, 89, 23);
		panel_3.add(btnToPanel);
		
		JLabel lblThisIsPanel_1 = new JLabel("This is Panel 2");
		lblThisIsPanel_1.setBounds(160, 42, 99, 14);
		panel_3.add(lblThisIsPanel_1);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, "name_2326431148145840");
		panel_4.setLayout(null);
		
		JButton btnToPanel_1 = new JButton("To Panel 1");
		btnToPanel_1.setBounds(142, 129, 89, 23);
		panel_4.add(btnToPanel_1);
		
		JLabel lblThisIsPanel_2 = new JLabel("This is Panel 3");
		lblThisIsPanel_2.setBounds(166, 65, 96, 14);
		panel_4.add(lblThisIsPanel_2);
		
		//Actions
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.removeAll();
				panel_1.add(panel_3);
				panel_1.repaint();
				panel_1.validate();
			}
		});
		
		btnToPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.removeAll();
				panel_1.add(panel_4);
				panel_1.repaint();
				panel_1.validate();
			}
		});
		
		btnToPanel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.removeAll();
				panel_1.add(panel_2);
				panel_1.repaint();
				panel_1.validate();
			}
		});
	}
	private void addComponents()
	{

	}
	public RentalDisplay() {	
		makeFrame();
		addComponents();
	}
}
