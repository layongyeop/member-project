package admin;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinInsertScore extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfKor;
	private JTextField tfHistory;
	private JTextField tfTotal;
	private JTextField tfMath;
	private JTextField tfSocial;
	private JTextField tfAverage;
	private JTextField tfEng;
	private JTextField tfScience;
	private JTable table;
	private JTextField tfNum;
	private JTextField tfName;

	private int kor, math, eng, social, history, science, total;
	private double average;
	private JComboBox cbYear;
	private JComboBox cbMonth;
	private DefaultTableModel dtm;
	private JButton btnSelect;
	private JButton btnRemove;
	private JButton btnModify;
	private ArrayList< JTextField> fields;
	private JButton btnAdd;
	private String id=null;
	private int month=0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinInsertScore dialog = new WinInsertScore();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Create the dialog.
	 */
	public WinInsertScore() {
		setTitle("ICI 성적 입력 창");
		setBounds(100, 100, 766, 556);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblKor = new JLabel("국어:");
		lblKor.setBounds(34, 54, 57, 15);
		contentPanel.add(lblKor);
		
		tfKor = new JTextField();
		tfKor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfKor.setSelectionStart(0);
				tfKor.setSelectionEnd(tfKor.getText().length());
			}
		});
		tfKor.setText("0");
		tfKor.setHorizontalAlignment(SwingConstants.RIGHT);
		tfKor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					// 숫자인지 아닌지를 처리하는 함수를 직접 만들어 사용
					String temp = tfKor.getText();
					boolean bDigit = true;
					for(int i=0;i<temp.length();i++)
						if(temp.charAt(i) >= '0' && temp.charAt(i) <= '9')
							;
						else {
							bDigit = false;
							break;
						}
					// 여기까지
					
					if(bDigit) {
						tfMath.requestFocus();
						scoreCalc();
					}else {
						tfKor.setSelectionStart(0);
						tfKor.setSelectionEnd(tfKor.getText().length());
					}
				}
			}
		});
		tfKor.setBounds(105, 51, 116, 21);
		contentPanel.add(tfKor);
		tfKor.setColumns(10);
		
		tfHistory = new JTextField();
		tfHistory.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfHistory.setSelectionStart(0);
				tfHistory.setSelectionEnd(tfHistory.getText().length());
			}
		});
		tfHistory.setText("0");
		tfHistory.setHorizontalAlignment(SwingConstants.RIGHT);
		tfHistory.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String temp = tfHistory.getText();
					if(isInteger(temp)) {
						scoreCalc();
						tfSocial.requestFocus();
					}else {
						tfHistory.setSelectionStart(0);
						tfHistory.setSelectionEnd(tfHistory.getText().length());
					}
				}
			}
		});
		tfHistory.setColumns(10);
		tfHistory.setBounds(105, 91, 116, 21);
		contentPanel.add(tfHistory);
		
		JLabel lblHistory = new JLabel("한국사:");
		lblHistory.setBounds(34, 94, 57, 15);
		contentPanel.add(lblHistory);
		
		tfTotal = new JTextField();
		tfTotal.setText("0");
		tfTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotal.setEnabled(false);
		tfTotal.setColumns(10);
		tfTotal.setBounds(105, 134, 116, 21);
		contentPanel.add(tfTotal);
		
		JLabel lblTotal = new JLabel("총점:");
		lblTotal.setBounds(34, 137, 57, 15);
		contentPanel.add(lblTotal);
		
		tfMath = new JTextField();
		tfMath.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {				
				tfMath.setSelectionStart(0);
				tfMath.setSelectionEnd(tfMath.getText().length());
			}
		});
		tfMath.setText("0");
		tfMath.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMath.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {	
					String temp = tfMath.getText();
					if(isInteger(temp)) {
						scoreCalc();
						tfEng.requestFocus();
					}else {
						tfMath.setSelectionStart(0);
						tfMath.setSelectionEnd(tfMath.getText().length());
					}
					
				}
			}
		});
		tfMath.setColumns(10);
		tfMath.setBounds(336, 54, 116, 21);
		contentPanel.add(tfMath);
		
		JLabel lblMath = new JLabel("수학:");
		lblMath.setBounds(265, 57, 57, 15);
		contentPanel.add(lblMath);
		
		tfSocial = new JTextField();
		tfSocial.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfSocial.setSelectionStart(0);
				tfSocial.setSelectionEnd(tfSocial.getText().length());
			}
		});
		tfSocial.setText("0");
		tfSocial.setHorizontalAlignment(SwingConstants.RIGHT);
		tfSocial.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String temp = tfSocial.getText();
					if(isInteger(temp)) {
						scoreCalc();
						tfScience.requestFocus();
					}else {
						tfSocial.setSelectionStart(0);
						tfSocial.setSelectionEnd(tfSocial.getText().length());
					}
					
				}
			}
		});
		tfSocial.setColumns(10);
		tfSocial.setBounds(336, 91, 116, 21);
		contentPanel.add(tfSocial);
		
		JLabel lblSocial = new JLabel("사회탐구:");
		lblSocial.setBounds(265, 94, 57, 15);
		contentPanel.add(lblSocial);
		
		tfAverage = new JTextField();
		tfAverage.setText("0");
		tfAverage.setHorizontalAlignment(SwingConstants.RIGHT);
		tfAverage.setEnabled(false);
		tfAverage.setColumns(10);
		tfAverage.setBounds(336, 134, 116, 21);
		contentPanel.add(tfAverage);
		
		JLabel lblAverage = new JLabel("평균:");
		lblAverage.setBounds(265, 137, 57, 15);
		contentPanel.add(lblAverage);
		
		tfEng = new JTextField();
		tfEng.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfEng.setSelectionStart(0);
				tfEng.setSelectionEnd(tfEng.getText().length());
			}
		});
		tfEng.setText("0");
		tfEng.setHorizontalAlignment(SwingConstants.RIGHT);
		tfEng.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String temp = tfEng.getText();
					if(isInteger(temp)) {
						scoreCalc();
						tfHistory.requestFocus();
					}else {
						tfEng.setSelectionStart(0);
						tfEng.setSelectionEnd(tfEng.getText().length());
					}
					
				}
			}
		});
		tfEng.setColumns(10);
		tfEng.setBounds(565, 54, 116, 21);
		contentPanel.add(tfEng);
		
		JLabel lblEng = new JLabel("영어:");
		lblEng.setBounds(494, 57, 57, 15);
		contentPanel.add(lblEng);
		
		tfScience = new JTextField();
		tfScience.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {	
				tfScience.setSelectionStart(0);
				tfScience.setSelectionEnd(tfScience.getText().length());
			}
		});
		tfScience.setText("0");
		tfScience.setHorizontalAlignment(SwingConstants.RIGHT);
		tfScience.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {

					String temp = tfScience.getText();
					if(isInteger(temp)) {
						scoreCalc();
					}else {
						tfScience.setSelectionStart(0);
						tfScience.setSelectionEnd(tfEng.getText().length());
					}
					
				}
			}
		});
		tfScience.setColumns(10);
		tfScience.setBounds(565, 91, 116, 21);
		contentPanel.add(tfScience);
		
		JLabel lblScience = new JLabel("과학탐구:");
		lblScience.setBounds(494, 94, 57, 15);
		contentPanel.add(lblScience);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// DB에 저장
				InsertRecord();
				
				// JTable에 저장
				AddRecord();
				
				// 초기화
				ClearTextFields();
			}
		});
		btnAdd.setBounds(494, 133, 97, 23);
		contentPanel.add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 197, 732, 310);
		contentPanel.add(scrollPane);
		
		String header[] = {"학번","이름","국어","수학","영어","한국사","사회탐구","과학탐구","총점","평균","연도","학기"};
		dtm = new DefaultTableModel(header, 0);
		
		table = new JTable(dtm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = table.getSelectedRow();
				
				tfNum.setText(table.getValueAt(row, 0).toString());
				tfName.setText(table.getValueAt(row, 1).toString());
				tfKor.setText(table.getValueAt(row, 2).toString());
				tfMath.setText(table.getValueAt(row, 3).toString());
				tfEng.setText(table.getValueAt(row, 4).toString());
				tfHistory.setText(table.getValueAt(row, 5).toString());
				tfSocial.setText(table.getValueAt(row, 6).toString());
				tfScience.setText(table.getValueAt(row, 7).toString());
				tfTotal.setText(table.getValueAt(row, 8).toString());
				tfAverage.setText(table.getValueAt(row, 9).toString());
				cbYear.setSelectedItem(table.getValueAt(row, 10).toString());
				cbMonth.setSelectedItem(table.getValueAt(row, 11).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNum = new JLabel("학번:");
		lblNum.setFont(new Font("굴림", Font.BOLD, 12));
		lblNum.setBounds(262, 13, 57, 15);
		contentPanel.add(lblNum);
		
		tfNum = new JTextField();
		tfNum.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfNum.setSelectionStart(0);
				tfNum.setSelectionEnd(tfNum.getText().length());
			}
		});
		tfNum.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfName.requestFocus();
				}
			}
		});
		tfNum.setColumns(10);
		tfNum.setBounds(333, 10, 116, 21);
		contentPanel.add(tfNum);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(491, 10, 57, 15);
		contentPanel.add(lblName);
		
		tfName = new JTextField();
		tfName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfName.setSelectionStart(0);
				tfName.setSelectionEnd(tfName.getText().length());
			}
		});
		tfName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfKor.requestFocus();					
				}
			}
		});
		tfName.setColumns(10);
		tfName.setBounds(562, 7, 116, 21);
		contentPanel.add(tfName);
		
		cbYear = new JComboBox();
		cbYear.setBounds(12, 9, 81, 23);
		contentPanel.add(cbYear);
		
		cbMonth = new JComboBox();
		cbMonth.setModel(new DefaultComboBoxModel(new String[] {"1학기 중간고사", "1학기 기말고사", "여름학기", "2학기 중간고사", "2학기 기말고사", "겨울학기"}));
		cbMonth.setBounds(102, 9, 137, 23);
		contentPanel.add(cbMonth);
		
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		for(int y=2000;y<=year;y++)
			cbYear.addItem(y);
		cbYear.setSelectedItem(year);
		
		btnSelect = new JButton("조회");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(id!=null) {
					ShowTable(id);
				}
				else{
				ShowTable();
				}
			}
		});
		btnSelect.setBounds(603, 133, 97, 23);
		contentPanel.add(btnSelect);
		
		btnRemove = new JButton("삭제");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				if(index != -1) {
					int num = Integer.parseInt(table.getValueAt(index, 0).toString());
					dtm.removeRow(index);  // 테이블 행 삭제
					DeleteRecord(num);
				}else
					JOptionPane.showMessageDialog(null, "삭제할 레코드를 선택하시오");
				ClearTextFields();				
			}
		});
		btnRemove.setBounds(494, 164, 97, 23);
		contentPanel.add(btnRemove);
		
		btnModify = new JButton("수정");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				if(index != -1) {
					int num = Integer.parseInt(table.getValueAt(index, 0).toString()); //PK
					UpdateRecord(num);
					ShowTable();
				}else
					JOptionPane.showMessageDialog(null, "변경할 레코드를 선택하시오");
				ClearTextFields();			
			}
		});
		btnModify.setBounds(603, 164, 97, 23);
		contentPanel.add(btnModify);
		fields = new ArrayList<>();
		fields.add(tfKor);
		fields.add(tfMath);
		fields.add(tfEng);
		fields.add(tfHistory);
		fields.add(tfSocial);
		fields.add(tfScience);
		fields.add(tfTotal);
		fields.add(tfAverage);
	}
	
	
	public void combovalue() {
		switch(cbMonth.getSelectedItem().toString()) {
			case "1학기 중간고사"	: month = 1; break;
			case "1학기 기말고사"	: month = 2; break;
			case "여름학기"	: month = 3; break;
			case "2학기 중간고사"	: month = 4; break;
			case "2학기 기말고사"	: month = 5; break;
			case "겨울학기"	: month = 6; break;
		}
		
	}
	public String setcombovalue(String month) {
		String semester=null;
		switch(month) {
			case "1"	: semester = "1학기 중간고사"; break;
			case "2"	: semester = "1학기 기말고사"; break;
			case "3"	: semester = "여름학기"; break;
			case "4"	: semester = "2학기 중간고사"; break;
			case "5"	:semester = "2학기 기말고사"; break;
			case "6"	: semester = "겨울학기"; break;
		}
		return semester;
	}
	
	
	public WinInsertScore(String id) {
		this();
		this.id = id;
		tfNum.setText(id);
		ShowTable(id);
		btnModify.setVisible(false);
		btnRemove.setVisible(false);
		btnAdd.setVisible(false);
	}
	protected void UpdateRecord(int num) {
		combovalue();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "update scoreTBL ";
			sql = sql + "set year=?, month=?,kor=?,math=?,";
			sql = sql + "eng=?,history=?,social=?,science=?,total=?,average=?";
			sql = sql + " where num=?";
			
			PreparedStatement pstmt=con.prepareStatement(sql);
						
			pstmt.setString(1, cbYear.getSelectedItem().toString());
			pstmt.setInt(2, month);
			pstmt.setString(3, tfKor.getText());
			pstmt.setString(4, tfMath.getText());
			pstmt.setString(5, tfEng.getText());
			pstmt.setString(6, tfHistory.getText());
			pstmt.setString(7, tfSocial.getText());
			pstmt.setString(8, tfScience.getText());
			pstmt.setString(9, tfTotal.getText());
			pstmt.setString(10, tfAverage.getText());
			pstmt.setString(11, tfNum.getText());
			pstmt.executeUpdate();
								
			pstmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		}		
	}

	protected void DeleteRecord(int num) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "delete from scoreTBL where num=?";			
			PreparedStatement pstmt=con.prepareStatement(sql);			
			pstmt.setInt(1, num);
			pstmt.executeUpdate();			
			
			pstmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		}		
	}

	protected void ShowTable() {
		combovalue();
		dtm.setRowCount(0);  // reset
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select a.* , name from scoretbl a join studenttbl b on a.scoreid = b.id where scoreid =? and month=? and year=?";			
			PreparedStatement pstmt=con.prepareStatement(sql);		
			pstmt.setString(1, tfNum.getText());
			pstmt.setLong(2, month);
			pstmt.setString(3, cbYear.getSelectedItem().toString());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {  // 각 레코드의 필드들을 읽어서 벡터에 저장한 후, DefaultTableModel 에 추가한다.
				Vector<String> vector = new Vector<>();
				vector.add(rs.getString(2));
				vector.add(rs.getString(13));
				tfName.setText(rs.getString(13));
				for(int i=5; i<=12; i++) {
					vector.add(rs.getString(i));
					
					fields.get(i-5).setText(rs.getString(i));
				}
				vector.add(rs.getString(2));
				vector.add(setcombovalue( rs.getString(3)));
				
				dtm.addRow(vector);
			}
			rs.close();		
			pstmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		}	
	}
	protected void ShowTable(String id) {
		dtm.setRowCount(0);  // reset
		combovalue();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select a.* , name from scoretbl a join studenttbl b on a.scoreid = b.id where scoreid =? and month=? and year=?";			
			PreparedStatement pstmt=con.prepareStatement(sql);		
			pstmt.setString(1, id);
			pstmt.setLong(2, month);
			pstmt.setString(3, cbYear.getSelectedItem().toString());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {  // 각 레코드의 필드들을 읽어서 벡터에 저장한 후, DefaultTableModel 에 추가한다.
				Vector<String> vector = new Vector<>();
				vector.add(rs.getString(2));
				vector.add(rs.getString(13));
				tfName.setText(rs.getString(13));
				for(int i=5; i<=12; i++) {
					vector.add(rs.getString(i));
				
					fields.get(i-5).setText(rs.getString(i));
				}
				vector.add(rs.getString(3));
				vector.add(setcombovalue( rs.getString(4)));
				
				
				
				dtm.addRow(vector);
			}

			rs.close();		
			pstmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
			e1.printStackTrace();
		}	
	}

	protected void AddRecord() {
		Vector<String> vector = new Vector<String>();
		vector.add(tfNum.getText());
		vector.add(tfName.getText());
		vector.add(Integer.toString(kor));
		vector.add(Integer.toString(math));
		vector.add(Integer.toString(eng));
		vector.add(Integer.toString(history));
		vector.add(Integer.toString(social));
		vector.add(Integer.toString(science));
		vector.add(Integer.toString(total));
		vector.add(Double.toString(average));
		vector.add(cbYear.getSelectedItem().toString());
		vector.add(cbMonth.getSelectedItem().toString());
		dtm.addRow(vector);
	}

	protected void InsertRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "insert into scoreTBL values(?,?,?,?,?,?,?,?,?,?,?)";	
			
			PreparedStatement pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(tfNum.getText()));
			pstmt.setInt(2, (int)cbYear.getSelectedItem());
			pstmt.setString(3,cbMonth.getSelectedItem().toString());
			pstmt.setInt(4, kor);
			pstmt.setInt(5, math);
			pstmt.setInt(6, eng);
			pstmt.setInt(7, history);
			pstmt.setInt(8, social);
			pstmt.setInt(9, science);
			pstmt.setInt(10, total);
			pstmt.setDouble(11,average);
			
			pstmt.executeUpdate();
								
			pstmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		}	
		/*
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			Statement stmt=con.createStatement();
			//=============================================		
			String sql = "insert into scoreTBL values(";
			sql = sql + tfNum.getText() + "," + cbYear.getSelectedItem() + ",'";
			sql = sql + cbMonth.getSelectedItem() + "','" + tfName.getText() + "',";
			sql = sql + kor + "," + math + "," + eng + "," + history + ",";
			sql = sql + social + "," + science + "," + total + "," + average + ")";
			
			System.out.println(sql);
			if(stmt.executeUpdate(sql)==0)
				System.out.println("입력 실패");						
			stmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 
		 */
			
	}

	protected void ClearTextFields() {
		/*
		tfNum.setText("");
		tfName.setText("");
		tfKor.setText("");
		tfMath.setText("");
		tfEng.setText("");
		tfHistory.setText("");
		tfSocial.setText("");
		tfScience.setText("");
		tfTotal.setText("");
		tfAverage.setText("");
		*/
		
		// 텍스트필드만 공백으로 변경하기
		Component [] componentList = contentPanel.getComponents();
		for(Component c : componentList) {
			if(c instanceof JTextField)
				((JTextField) c).setText("0");
		}
		tfNum.requestFocus();
	}

	protected void scoreCalc() {
		kor = Integer.parseInt(tfKor.getText());
		math = Integer.parseInt(tfMath.getText());
		eng = Integer.parseInt(tfEng.getText());
		history = Integer.parseInt(tfHistory.getText());
		science = Integer.parseInt(tfScience.getText());
		social = Integer.parseInt(tfSocial.getText());
		
		total = 0;
		total = total + kor;
		total = total + math;
		total = total + eng;
		total = total + history;
		total = total + social;
		total = total + science;
		
		average = Math.round((double)total / 6 *100)/100.;
		tfTotal.setText(Integer.toString(total));
		tfAverage.setText(String.format("%.2f", average));	
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
