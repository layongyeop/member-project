package admin;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class WinStudentDelete extends JDialog {
	private JTextField tfID;
	private JTextField tfName;
	private JTextField tfPhoneNum2;
	private JTextField tfJumin1;
	private JTextField tfEmail;
	private JTextField tfAddress;
	private JTextField tfPhoneNum1;

	private JLabel lblDept;
	private JComboBox cbLocal;
	private JComboBox cbEmailCompany;
	protected String fileName;
	private JPasswordField tfJumin2;
	private JLabel lblPic;
	private JButton btnDelete;
	private JTextField textField;
	private JButton btnAddrSearch;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinStudentDelete dialog = new WinStudentDelete();
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
	 * @param type 
	 * @param sID 
	 */
	public WinStudentDelete() {
		setBounds(100, 100, 662, 335);
		getContentPane().setLayout(null);
		
		lblPic = new JLabel("");	
		lblPic.setBackground(new Color(255, 255, 0));
		lblPic.setOpaque(true);
		lblPic.setToolTipText("더블클릭하여 선택하시오.");
		lblPic.setBounds(12, 25, 150, 180);
		getContentPane().add(lblPic);
		
		JLabel lblID = new JLabel("학번(ID):");
		lblID.setBounds(217, 31, 57, 15);
		getContentPane().add(lblID);
		
		tfID = new JTextField();
		tfID.setEditable(false);
		tfID.setBackground(new Color(255, 255, 255));

		tfID.setBounds(305, 25, 116, 21);
		getContentPane().add(tfID);
		tfID.setColumns(10);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(217, 65, 57, 15);
		getContentPane().add(lblName);
		
		tfName = new JTextField();
	
		tfName.setColumns(10);
		tfName.setBounds(305, 59, 116, 21);
		getContentPane().add(tfName);
		
		JLabel lblPhone = new JLabel("전화번호:");
		lblPhone.setBounds(217, 96, 57, 15);
		getContentPane().add(lblPhone);
		
		tfPhoneNum2 = new JTextField();

		tfPhoneNum2.setBackground(new Color(255, 255, 255));
		tfPhoneNum2.setColumns(10);
		tfPhoneNum2.setBounds(515, 93, 93, 21);
		getContentPane().add(tfPhoneNum2);
		
		JLabel lblJumin = new JLabel("주민번호:");
		lblJumin.setBounds(217, 130, 57, 15);
		getContentPane().add(lblJumin);
		
		tfJumin1 = new JTextField();

		tfJumin1.setBackground(new Color(255, 255, 255));
		tfJumin1.setColumns(10);
		tfJumin1.setBounds(305, 124, 116, 21);
		getContentPane().add(tfJumin1);
		
		JLabel lblEmail = new JLabel("이메일:");
		lblEmail.setBounds(217, 166, 57, 15);
		getContentPane().add(lblEmail);
		
		tfEmail = new JTextField();
	
		tfEmail.setColumns(10);
		tfEmail.setBounds(305, 160, 116, 21);
		getContentPane().add(tfEmail);
		
		JLabel lblAddress = new JLabel("주소:");
		lblAddress.setBounds(12, 222, 39, 15);
		getContentPane().add(lblAddress);
		
		tfAddress = new JTextField();
		tfAddress.setColumns(10);
		tfAddress.setBounds(47, 219, 257, 21);
		getContentPane().add(tfAddress);
		
		tfPhoneNum1 = new JTextField();		
		tfPhoneNum1.setBackground(new Color(255, 255, 255));
		tfPhoneNum1.setColumns(10);
		tfPhoneNum1.setBounds(410, 93, 93, 21);
		getContentPane().add(tfPhoneNum1);
		
		cbLocal = new JComboBox();
		
		cbLocal.setModel(new DefaultComboBoxModel(new String[] {"010", "02", "032", "031"}));
		cbLocal.setBounds(305, 92, 93, 23);
		getContentPane().add(cbLocal);
		
		JLabel lblAt = new JLabel("@");
		lblAt.setBounds(424, 166, 12, 15);
		getContentPane().add(lblAt);
		
		cbEmailCompany = new JComboBox();		
		cbEmailCompany.setEditable(true);
		cbEmailCompany.setModel(new DefaultComboBoxModel(new String[] {"직접 입력", "gmail.com", "daum.net", "naver.com", "ici.or.kr"}));
		cbEmailCompany.setBounds(443, 159, 175, 23);
		getContentPane().add(cbEmailCompany);
		
		JLabel lblLine = new JLabel("-");
		lblLine.setBounds(426, 131, 12, 15);
		getContentPane().add(lblLine);
		
		JLabel lblLine_1 = new JLabel("-");
		lblLine_1.setBounds(400, 96, 12, 15);
		getContentPane().add(lblLine_1);
		
		JLabel lblLine_1_1 = new JLabel("-");
		lblLine_1_1.setBounds(504, 96, 12, 15);
		getContentPane().add(lblLine_1_1);
		
		btnDelete = new JButton("학생 삭제");
	
		btnDelete.setBounds(443, 222, 175, 46);
		getContentPane().add(btnDelete);
		
		lblDept = new JLabel("학과:");
		lblDept.setForeground(new Color(255, 0, 0));
		lblDept.setFont(new Font("굴림", Font.BOLD, 14));
		lblDept.setBounds(446, 59, 162, 21);
		getContentPane().add(lblDept);
		
		tfJumin2 = new JPasswordField();
		
		tfJumin2.setBackground(new Color(255, 255, 255));
		tfJumin2.setBounds(443, 124, 136, 21);
		getContentPane().add(tfJumin2);
		
		
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(47, 247, 366, 21);
		getContentPane().add(textField);

	} 
	protected void dept() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			//=============================================		
			Statement stmt = con.createStatement();
			String sql = "select deptname from depttbL where deptid='" + tfID.getText().substring(4,6) + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				lblDept.setText("학과: " + rs.getString(1));
			}
			
			
			rs.close();
			stmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
			e1.printStackTrace();
		}	
		
	
		
		
	}

	protected void deleteRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			//=============================================		
			Statement stmt = con.createStatement();
			String sql = "delete from studentTBL where id='" + tfID.getText() + "'";
			if(stmt.executeUpdate(sql) > 0)
				JOptionPane.showMessageDialog(null, tfName.getText()+"(" + tfID.getText() + ")님이 삭제되었습니다");			
			stmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		}	
		
	}

	public WinStudentDelete(String sID) {
		this();
		showStudentInfomation(sID);
	}
	public WinStudentDelete(String sID, boolean type) {
		this(sID);
		if(type) {
			setTitle("학생 정보 상세창");
			btnDelete.setText("시간표 조회");
			btnDelete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Wintable wintable = new Wintable(sID);
					wintable.setModal(true);
					wintable.setVisible(true);
					
					
				}
			});
			
		}
		else {
			setTitle("학생 정보 삭제창");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int ret = JOptionPane.showConfirmDialog(null, "정말 삭제할까요?", "삭제",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
					System.out.println(ret);
					if(ret == JOptionPane.OK_OPTION) {
						deleteRecord();
						setVisible(false);
					}
				}
			});
			
		}
		dept();
		
	}

	protected void showStudentInfomation(String sID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			//=============================================		
			Statement stmt = con.createStatement();
			String sql = "select * from studentTBL where id='" + sID + "'";
//			System.out.println(sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			String record[] = new String[6];
			if(rs.next()) {				
				for(int i=0;i<record.length;i++) {
					record[i] = rs.getString(i+2); // name부터 가져온다
					//System.out.println(record[i]);
				}
				tfID.setText(sID); // 추가				
				tfName.setText(record[0]);				
				String sPhone = record[1];
				if(sPhone.substring(0,2).equals("02")) {
					cbLocal.setSelectedItem(sPhone.substring(0,2));
					tfPhoneNum1.setText(sPhone.substring(2,sPhone.length()-4));
				}else {
					cbLocal.setSelectedItem(sPhone.substring(0,3));
					tfPhoneNum1.setText(sPhone.substring(3,sPhone.length()-4));
				}
				tfPhoneNum2.setText(record[1].substring(record[1].length()-4));					
				tfJumin1.setText(record[2].substring(0,6)); // 주민번호 앞글자는 6글자
				tfJumin2.setText(record[2].substring(6));  // 주민번호 뒷글자의 시작은 6번부터				
				tfEmail.setText(record[3].substring(0, record[3].indexOf("@")));
				cbEmailCompany.setSelectedItem(record[3].substring(record[3].indexOf("@")+1));
				tfAddress.setText(record[5]);				
				fileName = record[4];
				ImageIcon icon = new ImageIcon(fileName);
				Image img = icon.getImage();
				img = img.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
				ImageIcon image = new ImageIcon(img);
				lblPic.setIcon(image);
				
			}
			rs.close();
			stmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		}		
	}	
}
