package admin;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import java.awt.BorderLayout;
import javax.swing.JList;
import java.awt.Font;

public class WinDoroList extends JDialog {
	private String doro ;
	private JList list;
	public String getDoro() {
		return doro;
	}
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					WinDoroList dialog = new WinDoroList(dorolist);
//					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//					dialog.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	


	/**
	 * Create the dialog.
	 */
	public WinDoroList(Vector <String> dorolist) {
		setBounds(100, 100, 450, 300);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		list = new JList(dorolist);
		list.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
		scrollPane.setViewportView(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() ==2) {
					doro = (String) list.getSelectedValue();
					System.out.println(doro);
					
					if(doro!=null) {
						setVisible(false);
						
					}
					
				}
			}
			
		});
		
		
	}
	





	








	
	
	
	
	

}
