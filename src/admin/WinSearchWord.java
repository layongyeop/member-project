package admin;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WinSearchWord extends JDialog {
	private JTextField tfName;
	private JTextField tfPhone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinSearchWord dialog = new WinSearchWord();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinSearchWord() {
		setTitle("검색어 입력");
		setBounds(100, 100, 410, 157);
		getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(40, 35, 57, 15);
		getContentPane().add(lblName);
		
		tfName = new JTextField();
		tfName.setBounds(127, 32, 116, 21);
		getContentPane().add(tfName);
		tfName.setColumns(10);
		
		tfPhone = new JTextField();
		tfPhone.setColumns(10);
		tfPhone.setBounds(127, 71, 116, 21);
		getContentPane().add(tfPhone);
		
		JLabel lblPhone = new JLabel("전화번호:");
		lblPhone.setBounds(40, 74, 57, 15);
		getContentPane().add(lblPhone);
		
		JButton btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sName = tfName.getText();
				String sPhone = tfPhone.getText();
				
				setVisible(false);
				WinLookup winLookup = new WinLookup(sName,sPhone);
				winLookup.setModal(true);
				winLookup.setVisible(true);
			}
		});
		btnSearch.setBounds(257, 31, 97, 61);
		getContentPane().add(btnSearch);

	}

	
}
