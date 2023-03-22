import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class WinLectSearch extends JDialog {
	private JTextField textField;
	private String Searchword;
	private String SearchTag;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinLectSearch dialog = new WinLectSearch();
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
	public WinLectSearch() {
		setTitle("\uAC15\uC758 \uAC80\uC0C9");
		setBounds(100, 100, 325, 147);
		getContentPane().setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\uAC15\uC758\uCF54\uB4DC", "\uAC15\uC758\uBA85", "\uAD50\uC218", "\uC694\uC77C"}));
		comboBox.setBounds(12, 30, 87, 33);
		getContentPane().add(comboBox);
		
		textField = new JTextField();
		textField.setToolTipText("\uC694\uC77C\uC758 \uACBD\uC6B0 \uC694\uC77C\uBE7C\uACE0 \uAE30\uC220(ex \uC6D4 \uD654 )");
		textField.setBounds(111, 30, 184, 33);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==10) {
					search();
				}
			}
			
		});
		
		JButton btnNewButton = new JButton("\uAC80\uC0C9");
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
				
			}
		});
		
		btnNewButton.setBounds(102, 75, 97, 23);
		getContentPane().add(btnNewButton);
		
		
		

	}

	protected void search() {
		Searchword = textField.getText();
		SearchTag = comboBox.getSelectedItem().toString();
		dispose();
		
	}

	public String getSearchword() {
		return Searchword;
	}

	public String getSearchTag() {
		return SearchTag;
	}
	
}
