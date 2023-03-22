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
import java.awt.event.WindowFocusListener;
import java.awt.im.InputContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class WinStudentInsert extends JDialog {

	private JTextField tfID;
	private JTextField tfName;
	private JTextField tfPhoneNum2;
	private JTextField tfJumin1;
	private JTextField tfEmail;
	private JTextField tfAddress;
	private JTextField tfPhoneNum1;

	private JLabel lblDept;
	private JButton btnDupCheck;
	private JComboBox cbLocal;
	private JComboBox cbEmailCompany;
	protected String fileName;
	private JPasswordField tfJumin2;
	private JTextField tfAddr2;
	private JButton btnAddrSearch;
	private JLabel lblemail;
	private Vector<String> doroVect;
	private String doro;
	private JTextField textField;
	private JLabel lblAddress_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinStudentInsert dialog = new WinStudentInsert();
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
	public WinStudentInsert() {
		setTitle("학생 정보 입력창");
		setBounds(100, 100, 662, 361);
		getContentPane().setLayout(null);
		
		JLabel lblPic = new JLabel("");
		lblPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) { // 더블클릭하면...
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = 
							new FileNameExtensionFilter("그림파일","png","bmp","gif","jpg");
					chooser.setFileFilter(filter);					
					int ret = chooser.showOpenDialog(null);
					if( ret == JFileChooser.APPROVE_OPTION ) {
						fileName = chooser.getSelectedFile().getPath();
						ImageIcon icon = new ImageIcon(fileName);
						Image img = icon.getImage();
						img = img.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
						ImageIcon image = new ImageIcon(img);
						lblPic.setIcon(image);
					}	
				}
			}
		});
		lblPic.setBackground(new Color(0, 255, 128));
		lblPic.setOpaque(true);
		lblPic.setToolTipText("더블클릭하여 선택하시오.");
		lblPic.setBounds(12, 25, 150, 180);
		getContentPane().add(lblPic);
		
		JLabel lblID = new JLabel("학번(ID):");
		lblID.setBounds(217, 31, 57, 15);
		getContentPane().add(lblID);
		
		tfID = new JTextField();
		tfID.setBackground(new Color(0, 255, 255));
		tfID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					
					String temp = tfID.getText();
					if(temp.length() == 9 && isInteger(temp)) {						
						checkDuplicate();
					}

				}
			}
		});
		tfID.setBounds(305, 25, 116, 21);
		getContentPane().add(tfID);
		tfID.setColumns(10);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(217, 65, 57, 15);
		getContentPane().add(lblName);
		
		tfName = new JTextField();
		tfName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if( e.getSource() == tfName) {
	                  try{
	                   InputContext inCtx2 = tfAddress.getInputContext(); // comp는 text component
	                   Character.Subset[] subset2 = { Character.UnicodeBlock.HANGUL_SYLLABLES  };
	                   inCtx2.setCharacterSubsets( subset2 );
	                  }catch(Exception ee) { ee.printStackTrace();}
	            }
				
			}
		});
		tfName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPhoneNum1.requestFocus();
				}
			}
		});
		tfName.setColumns(10);
		tfName.setBounds(305, 59, 116, 21);
		getContentPane().add(tfName);
		
		JLabel lblPhone = new JLabel("전화번호:");
		lblPhone.setBounds(217, 96, 57, 15);
		getContentPane().add(lblPhone);
		
		tfPhoneNum2 = new JTextField();
		tfPhoneNum2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String temp = tfPhoneNum2.getText();
					if(temp.length() == 4 && isInteger(temp))
						tfJumin1.requestFocus();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				String temp = tfPhoneNum2.getText();
				if(temp.length() == 4 && isInteger(temp))
					tfJumin1.requestFocus();
			}
		});
		tfPhoneNum2.setBackground(new Color(0, 255, 255));
		tfPhoneNum2.setColumns(10);
		tfPhoneNum2.setBounds(515, 93, 93, 21);
		getContentPane().add(tfPhoneNum2);
		
		JLabel lblJumin = new JLabel("주민번호:");
		lblJumin.setBounds(217, 130, 57, 15);
		getContentPane().add(lblJumin);
		
		tfJumin1 = new JTextField();
		tfJumin1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String temp = tfJumin1.getText();
					if(temp.length() == 6 && isInteger(temp))
						tfJumin2.requestFocus();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				String temp = tfJumin1.getText();
				if(temp.length() == 6 && isInteger(temp))
					tfJumin2.requestFocus();
			}
		});
		tfJumin1.setBackground(new Color(0, 255, 255));
		tfJumin1.setColumns(10);
		tfJumin1.setBounds(305, 124, 116, 21);
		getContentPane().add(tfJumin1);
		
		JLabel lblEmail = new JLabel("이메일:");
		lblEmail.setBounds(217, 166, 57, 15);
		getContentPane().add(lblEmail);
		
		tfEmail = new JTextField();
		tfEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if( e.getSource() == tfEmail ) {
		               try{
		                   InputContext inCtx = tfEmail.getInputContext(); // comp는 text component
		                   Character.Subset[] subset = null;
		                   inCtx.setCharacterSubsets( subset );
		                  }catch(Exception ee) {ee.printStackTrace(); }
		         }
				
			}
		});
		tfEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {					
					cbEmailCompany.requestFocus();
				}
			}
		});
		tfEmail.setColumns(10);
		tfEmail.setBounds(305, 160, 116, 21);
		getContentPane().add(tfEmail);
		
		JLabel lblAddress = new JLabel("주소:");
		lblAddress.setBounds(37, 222, 39, 15);
		getContentPane().add(lblAddress);
		
		tfAddress = new JTextField();
		tfAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					
				}
			}
		});
		tfAddress.setColumns(10);
		tfAddress.setBounds(81, 219, 228, 21);
		getContentPane().add(tfAddress);
		
		tfPhoneNum1 = new JTextField();
		tfPhoneNum1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String temp = tfPhoneNum1.getText();
					if(temp.length() >=3 && temp.length() <= 4 && isInteger(temp))
						tfPhoneNum2.requestFocus();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				String temp = tfPhoneNum1.getText();
				if(temp.length() == 4 && isInteger(temp))
					tfPhoneNum2.requestFocus();
			}
		});
		tfPhoneNum1.setBackground(new Color(0, 255, 255));
		tfPhoneNum1.setColumns(10);
		tfPhoneNum1.setBounds(410, 93, 93, 21);
		getContentPane().add(tfPhoneNum1);
		
		cbLocal = new JComboBox();
		cbLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfPhoneNum1.requestFocus();
			}
		});
		cbLocal.setModel(new DefaultComboBoxModel(new String[] {"010", "02", "032", "031"}));
		cbLocal.setBounds(305, 92, 93, 23);
		getContentPane().add(cbLocal);
		
		JLabel lblAt = new JLabel("@");
		lblAt.setBounds(424, 166, 12, 15);
		getContentPane().add(lblAt);
		
		cbEmailCompany = new JComboBox();
		cbEmailCompany.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(cbEmailCompany.getSelectedItem().equals("직접 입력")) {
					cbEmailCompany.setSelectedItem("");
				}
				
				
				
				  try{
	                   InputContext inCtx = tfEmail.getInputContext(); // comp는 text component
	                   Character.Subset[] subset = null;
	                   inCtx.setCharacterSubsets( subset );
	                  }catch(Exception ee) {ee.printStackTrace(); }
				  
				
			}
		});
		cbEmailCompany.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					if(cbEmailCompany.getSelectedItem().toString().matches("^[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
						tfAddress.requestFocus();
						lblemail.setText("");
						lblemail.setForeground(Color.red);	
					}else {
						lblemail.setText("이메일 형식이 맞지않습니다");
						lblemail.setForeground(Color.red);					}
			}
		});
		cbEmailCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cbEmailCompany.getSelectedItem().equals("직접 입력")&&cbEmailCompany.getSelectedItem().toString().matches("^[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"))
					tfAddress.requestFocus();
				else if (cbEmailCompany.getSelectedItem().equals("직접 입력")) {
							
					cbEmailCompany.setSelectedItem("");
					cbEmailCompany.requestFocus();
				}else {
					lblemail.setText("이메일 형식이 맞지않습니다");
					lblemail.setForeground(Color.red);
				}
					
			}
		});
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
		
		JButton btnInsert = new JButton("학생 추가");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isOK())
					InsertStudentInfomation();
				else
					JOptionPane.showMessageDialog(null, "필수 입력을 꼭 확인하세요");
			}
		});
		btnInsert.setBounds(443, 265, 175, 46);
		getContentPane().add(btnInsert);
		
		btnDupCheck = new JButton("중복체크");
		btnDupCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkDuplicate();
			}
		});
		btnDupCheck.setBounds(443, 24, 97, 23);
		getContentPane().add(btnDupCheck);
		
		lblDept = new JLabel("학과:");
		lblDept.setForeground(new Color(255, 0, 0));
		lblDept.setFont(new Font("굴림", Font.BOLD, 14));
		lblDept.setBounds(446, 59, 162, 21);
		getContentPane().add(lblDept);
		
		tfJumin2 = new JPasswordField();
		tfJumin2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String temp = new String(tfJumin2.getPassword()); // String class 생성자를 확인할 것
				if(temp.length() == 7 && isInteger(temp))
					tfEmail.requestFocus();				
			}
		});
		tfJumin2.setBackground(new Color(0, 255, 255));
		tfJumin2.setBounds(443, 124, 136, 21);
		getContentPane().add(tfJumin2);
		
		btnAddrSearch = new JButton("\uAC80\uC0C9...");
		btnAddrSearch.addActionListener(new ActionListener() {
			private String searchAddrText;

			public void actionPerformed(ActionEvent e) {
				if(tfAddress.getText().trim()==null || tfAddress.getText().equals("")) {
					searchAddrText = JOptionPane.showInputDialog(null, "검색할 도로명 주소");
				}else {
					searchAddrText = tfAddress.getText();
					
				}
				searchAddr(searchAddrText);
				
				if(doroVect !=null) {
					WinDoroList doroList= new WinDoroList(doroVect);
					doroList.setModal(true);
					doroList.setVisible(true);
					
					doro = doroList.getDoro();
					
						
				}else {
					JOptionPane.showMessageDialog(null,"주소를 확인해주세요","ERROR",JOptionPane.ERROR_MESSAGE);
				}
				
				tfAddr2.setText(doro);
			
			}
		});
		
		btnAddrSearch.setBounds(324, 218, 97, 23);
		getContentPane().add(btnAddrSearch);
		
		tfAddr2 = new JTextField();
		tfAddr2.setEditable(false);
		tfAddr2.setColumns(10);
		tfAddr2.setBounds(81, 259, 340, 21);
		getContentPane().add(tfAddr2);
		
		lblemail = new JLabel("");
		lblemail.setBounds(453, 185, 162, 10);
		getContentPane().add(lblemail);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(81, 290, 340, 21);
		getContentPane().add(textField);
		
		lblAddress_1 = new JLabel("\uC0C1\uC138\uC8FC\uC18C:");
		lblAddress_1.setBounds(12, 296, 57, 15);
		getContentPane().add(lblAddress_1);

	}
	protected void searchAddr(String searchAddrText) {
		doroVect=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			//=============================================		
			String sql = "select * from (select distinct concat( si , gu , dong, road ) as addr from addresstbl) A where A.addr like '%" + searchAddrText + "%'";				
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(sql);
			doroVect = new Vector<>();
			while(rs.next()) { 
				System.out.println(rs.getString("addr"));
				doroVect.add(rs.getString("addr"));
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

	protected boolean isOK() { // 레코드를 insert하기 전에 필수 입력값 확인하는 함수
		if(tfID.getText().length()==9 && !tfName.getText().equals("") 
				&& tfPhoneNum1.getText().length()>=3 && tfPhoneNum2.getText().length()==4
				&& tfJumin1.getText().length()==6 && new String(tfJumin2.getPassword()).length()==7)
			return true;
		else
			return false;
	}

	protected void checkDuplicate() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			//=============================================		
			String sql = "select * from studentTBL where id='" + tfID.getText() + "'";				
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {  // 학번이 존재한다(PK)
				lblDept.setText("학번(ID) 존재");
				tfID.setSelectionStart(0);
				tfID.setSelectionEnd(tfID.getText().length());
				tfID.requestFocus();
			}else {
				HashMap<String, String> dept = new HashMap<String, String>();
				dept.put("01","국문학과");
				dept.put("02","영문학과");
				dept.put("10","컴퓨터학과");
				dept.put("11","전자공학과");
				dept.put("20","연극영화학과");
				dept.put("21","음악학과");
				
				String temp = tfID.getText();
				String sID = temp.substring(4,6);
				lblDept.setText("학과: " + dept.get(sID));
				tfName.requestFocus();
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

	protected void InsertStudentInfomation() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			//=============================================		
			String sql = "insert into studentTBL values(?,?,?,?,?,?,?)";				
			PreparedStatement pstmt=con.prepareStatement(sql);			
			pstmt.setString(1, tfID.getText());
			pstmt.setString(2, tfName.getText());
			pstmt.setString(3, cbLocal.getSelectedItem() + tfPhoneNum1.getText() + tfPhoneNum2.getText());
			pstmt.setString(4, tfJumin1.getText() + new String(tfJumin2.getPassword()));
			pstmt.setString(5, tfEmail.getText()+"@"+cbEmailCompany.getSelectedItem());
			pstmt.setString(6, fileName);
			pstmt.setString(7, tfAddress.getText());			
			pstmt.executeUpdate();								
			pstmt.close();
			con.close();
			
			// 텍스트필드만 공백으로 변경하기
			Component [] componentList = getContentPane().getComponents();
			for(Component c : componentList) {
				if(c instanceof JTextField)
					((JTextField) c).setText("");
			}
			cbLocal.setSelectedIndex(0);  // 010
			cbEmailCompany.setSelectedIndex(0); //직접 입력
			tfID.requestFocus();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		}			
	}

	public static boolean isInteger(String strValue) {
	    try {
	      Integer.parseInt(strValue);
	      return true;
	    } catch (NumberFormatException ex) {
	      return false;
	    }
	}
}
