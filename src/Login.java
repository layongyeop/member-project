import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import admin.WinAdminMain;

import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JDialog {
	private JTextField tfID;
	private JTextField tfPw;
	private int manager ;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login dialog = new Login();
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
	public Login() {
		setTitle("\uB85C\uADF8\uC778");
		setBounds(100, 100, 330, 171);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(38, 20, 79, 21);
		getContentPane().add(lblNewLabel);
		
		tfID = new JTextField();
		tfID.setBounds(129, 20, 173, 21);
		getContentPane().add(tfID);
		tfID.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(38, 67, 79, 21);
		getContentPane().add(lblPassword);
		
		tfPw = new JTextField();
		tfPw.addKeyListener(new KeyAdapter() {
			

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) { // enter 면{
					String id= tfID.getText();
					String pw= tfPw.getText();
					
					if(id.length()==9 &&(pw.length()>= 4 && pw.length() <= 10 )) { // id 9자리 pw 4~10자리
						if(check(id,pw)) {
							dispose();
							if(manager ==1) {
								WinAdminMain adminMain = new WinAdminMain();
								adminMain.setModal(true);
								adminMain.setVisible(true);
							}else {
								
							
							
							WinStudentMain main = new WinStudentMain(id);
							main.setModal(true);
							main.setVisible(true);
							}
							
							
						}else {
							JOptionPane.showMessageDialog(null, "id또는 password 를 확인해주세요","ERROR",JOptionPane.ERROR_MESSAGE);
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "id또는 password 를 확인해주세요","ERROR",JOptionPane.ERROR_MESSAGE);
						
					}
						
				}
			}
		});
		tfPw.setColumns(10);
		tfPw.setBounds(129, 67, 173, 21);
		getContentPane().add(tfPw);
		
		JButton btnNewButton = new JButton("\uD68C\uC6D0\uAC00\uC785...");
		btnNewButton.setBounds(108, 99, 97, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\uB85C\uADF8\uC778");
		btnNewButton_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) { // enter 면{
					String id= tfID.getText();
					String pw= tfPw.getText();
					
					if(id.length()==9 &&(pw.length()>= 4 && pw.length() <= 10 )) { // id 9자리 pw 4~10자리
						if(check(id,pw)) {
							dispose();
							if(manager ==1) {
								WinAdminMain adminMain = new WinAdminMain();
								adminMain.setModal(true);
								adminMain.setVisible(true);
							}else {
								
							
							
							WinStudentMain main = new WinStudentMain(id);
							main.setModal(true);
							main.setVisible(true);
							}
							
							
						}else {
							JOptionPane.showMessageDialog(null, "id또는 password 를 확인해주세요","ERROR",JOptionPane.ERROR_MESSAGE);
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "id또는 password 를 확인해주세요","ERROR",JOptionPane.ERROR_MESSAGE);
						
					}
						
				}
				
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id= tfID.getText();
				String pw= tfPw.getText();
				
				if(id.length()==9 &&(pw.length()>= 4 && pw.length() <= 10 )) { // id 9자리 pw 4~10자리
					if(check(id,pw)) {
						dispose();
						if(manager ==1) {
							
							WinAdminMain adminMain = new WinAdminMain();
							adminMain.setModal(true);
							adminMain.setVisible(true);
							
						}else {
							
						
						
						WinStudentMain main = new WinStudentMain(id);
						main.setModal(true);
						main.setVisible(true);
						}
						
						
					}else {
						JOptionPane.showMessageDialog(null, "id또는 password 를 확인해주세요","ERROR",JOptionPane.ERROR_MESSAGE);
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "id또는 password 를 확인해주세요","ERROR",JOptionPane.ERROR_MESSAGE);
					
				}
			}
		});
		btnNewButton_1.setBounds(205, 99, 97, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\uCC3E\uAE30...");
		btnNewButton_2.setBounds(12, 99, 97, 23);
		getContentPane().add(btnNewButton_2);

	}

	protected boolean check(String id, String pw) {
		boolean check = false;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from studentlogin where id = ? and password =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				check = true;
				manager = rs.getInt("manager");
			}
			
			
			
			
			rs.close();
			pstmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
			e1.printStackTrace();
		} 
		
		return check;
	}
}
