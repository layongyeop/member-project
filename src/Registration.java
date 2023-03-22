import java.awt.EventQueue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import admin.MyTableCellRenderer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import javax.swing.ScrollPaneConstants;


public class Registration extends JDialog {
	private static JTable lecture;
	private static JTable tableSchedule;
	private static JTable tableSelectLecture;
	private static JScrollPane SchedulePane;
	private static int sum=0;
	private static JLabel lblhakjum;
	private static String id;
	private static TrayIcon trayIcon;

	/**
	 * Launch the application.
	 */
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registration dialog = new Registration("199910111"); // 확인을위해 있는값 설정 기본 null
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					
					myTrayIcon(Toolkit.getDefaultToolkit().getImage("src/images/book.png"), "수강신청", new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							dialog.setVisible(true);
							
						}
					} );
				
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JButton btnLectSearch;



	/**
	 * Create the dialog.
	 */
	
	
	
	public Registration(String id) {
		this.id = id;
		
		
		
		
		setBounds(100, 100, 1138, 816);
		getContentPane().setLayout(null);
		
		JScrollPane lecturePane = new JScrollPane();
		lecturePane.setBounds(12, 34, 507, 428);
		getContentPane().add(lecturePane);
		
		String lectureColumns[] = {"강의코드","강의명","교수","요일","시간","학점","정원"}; // 강의 테이블 칼럼
		
		DefaultTableModel lecturemodel = new DefaultTableModel(lectureColumns,0){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		
		
		lecture = new JTable(lecturemodel);
		lecture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
//					int row = lecture.getSelectedRow();
//					String name = (String) lecture.getValueAt(row, 0);
//					String professor = (String) lecture.getValueAt(row, 1);
//					selectedTable(name,professor);
					int[] rows = lecture.getSelectedRows();
					selectedTable(rows);
				}
			}
		});
		
		
		
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(lecture, popupMenu);
		
		JMenuItem mnuselect = new JMenuItem("\uCD94\uAC00");
		mnuselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				int row = lecture.getSelectedRow();
//				String name = (String) lecture.getValueAt(row, 1);
//				String professor = (String) lecture.getValueAt(row, 2);
//				
//				
//				selectedTable(name,professor);
				
				int[] rows = lecture.getSelectedRows();
				selectedTable(rows);
//				System.out.println(name+professor);
				
				
			}
		});
		popupMenu.add(mnuselect);
		lecturePane.setViewportView(lecture);
		
		SchedulePane = new JScrollPane();
		SchedulePane.setWheelScrollingEnabled(false);
		SchedulePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		SchedulePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		SchedulePane.setBorder(null);
		SchedulePane.setBounds(531, 34, 579, 428);
		getContentPane().add(SchedulePane);
		
		
		String ScheduleColumns[] = {"시간","월","화","수","목","금"}; // 시간표 칼럼
		
		DefaultTableModel Schedulemodel = new DefaultTableModel(ScheduleColumns,0){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		
		
		for (int i=14;i>0;i--) {
			Vector<String> vector = new Vector<>();
			vector.add(i+"교시("+(8+i)+":00 ~"+(8+i)+":50)"); // 시간표 열첫번째 값
			
			vector.add(null);
			vector.add(null);
			vector.add(null);
			vector.add(null);
			
			Schedulemodel.addRow(vector);
		}
		
		tableSchedule = new JTable(Schedulemodel);
		
		
		
		
		tableSchedule.getColumnModel().getColumn(0).setMinWidth(120);
		
		tableSchedule.setRowHeight(SchedulePane.getHeight()/(tableSchedule.getRowCount()+1)+1);
		SchedulePane.setViewportView(tableSchedule);
		
		for(int i = 0; i<tableSchedule.getColumnCount();i++) { 

				tableSchedule.getColumn(ScheduleColumns[i]).setCellRenderer(new MyTableCellRenderer()); // 특정 셀의 색을 변경시키기 위한 renderer 셋팅

		}
		

		
		
		
		JScrollPane selectLecture = new JScrollPane();
		selectLecture.setBounds(12, 512, 1098, 186);
		getContentPane().add(selectLecture);
		
		
		String selLecturecol[] = {"강의코드","강의명","교수","시간","요일","학점","정원"}; // 선택한강의 테이블 칼럼
		
		DefaultTableModel selcolmodel = new DefaultTableModel(selLecturecol,0){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		
		tableSelectLecture = new JTable(selcolmodel);
		
		JPopupMenu jPopupMenu = new JPopupMenu();
		addPopup(tableSelectLecture, jPopupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("삭제");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableSelectLecture.getSelectedRow();
				
				deletelecture(row);
				
				int rows[] = tableSelectLecture.getSelectedRows();
				deletelecture(rows);
			}
		});
		jPopupMenu.add(mntmNewMenuItem);
		
	
		
		
		
		
		
		selectLecture.setViewportView(tableSelectLecture);
		
		JLabel lblNewLabel_2 = new JLabel("\uBAA8\uB4E0 \uAC15\uC758"); 
		lblNewLabel_2.setBounds(226, 9, 57, 15);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("\uC2DC\uAC04\uD45C");
		lblNewLabel_2_1.setBounds(823, 9, 57, 15);
		getContentPane().add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_3 = new JLabel("\uC120\uD0DD\uD55C \uAC15\uC758");
		lblNewLabel_3.setBounds(12, 487, 87, 15);
		getContentPane().add(lblNewLabel_3);
		
		lblhakjum = new JLabel("\uCD1D \uD559\uC810 :");
		lblhakjum.setFont(new Font("Gulim", Font.PLAIN, 16));
		lblhakjum.setBounds(879, 720, 122, 47);
		getContentPane().add(lblhakjum);
		
		JButton btnSelect = new JButton("\uC218\uAC15\uC2E0\uCCAD");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(check()) { // 학점 or 시간표 중복 확인
					registartion(); // 수강신청(insert)
				}
			}
		});
		btnSelect.setBounds(1013, 720, 97, 47);
		getContentPane().add(btnSelect);
		
		btnLectSearch = new JButton("\uAC80\uC0C9...");
		btnLectSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinLectSearch lectSearch = new WinLectSearch();
				lectSearch.setModal(true);
				lectSearch.setVisible(true);
				String lectSearchText=lectSearch.getSearchword();
				String lectSearchTag =lectSearch.getSearchTag();
				
				lectSearch(lectSearchTag,lectSearchText);
				
			}
		});
		btnLectSearch.setBounds(368, 6, 87, 23);
		getContentPane().add(btnLectSearch);
		
		showlecture();	
//		lecture.getColumnModel().getColumn(2).setMinWidth(100);
		lecture.getColumnModel().getColumn(4).setMinWidth(100); // 강의 시간 길이 조절
		
		if(id!=null)
			showtable(id);// id값이 있으면 기존에 수강한것 표시 없으면 표시x
		
		

	}
	

	protected void lectSearch(String lectSearchTag, String lectSearchText) {
		String lectSearchTextList[] = null;
		if(lectSearchText.indexOf("요일")!=-1) {
			lectSearchText=lectSearchText.replaceAll("요일", "");
		}
		if(lectSearchText.indexOf(",")!=-1) {
			lectSearchTextList=lectSearchText.split(",");
		}
		
		switch(lectSearchTag) {
		case "강의코드": lectSearchTag="num"; break;
		case "강의명": lectSearchTag="name"; break;
		case "교수": lectSearchTag="professor"; break;
		case "요일": lectSearchTag="day"; break;
		
		};
		PreparedStatement pstmt = null;
		Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			if(!(lectSearchTextList.length>1)) {
			//=============================================		
				String sql = "select * from lecture where "+lectSearchTag  +" like ?";
				pstmt = con.prepareStatement(sql);
	//			pstmt.setString(1, lectSearchTag);
				pstmt.setString(1, "%"+lectSearchText+"%");
				System.out.println(lectSearchTag);
				System.out.println(lectSearchText);
				ResultSet rs = pstmt.executeQuery();
				//String lectureColumns[] = {"과목코드","과목명","교수","요일","시간","학점","정원"};
				DefaultTableModel lecturemodel = (DefaultTableModel) lecture.getModel(); // 강의 테이블의 모델 가져오기
				lecturemodel.setRowCount(0);
				while(rs.next()) {
					Vector<String> vector = new Vector<>();
					vector.add(rs.getString("num"));
					vector.add(rs.getString("name"));
					vector.add(rs.getString("professor"));
					vector.add(rs.getString("day"));
					vector.add(rs.getInt("starthour")+"교시 ~ "+(rs.getInt("starthour")+rs.getInt("hour")-1)+"교시");
					vector.add(Integer.toString(rs.getInt("hour")*rs.getInt("count")));
					
					vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
					lecturemodel.addRow(vector); // 모델에 추가(테이블에 셋팅)
					
					
					
				}
				rs.close();
			}else {
				for(String daytext:lectSearchTextList) {
					String sql = "select * from lecture where day  like ?";
					pstmt = con.prepareStatement(sql);
		//			pstmt.setString(1, lectSearchTag);
					pstmt.setString(1, "%"+daytext+"%");
					ResultSet rs = pstmt.executeQuery();
					//String lectureColumns[] = {"과목코드","과목명","교수","요일","시간","학점","정원"};
					DefaultTableModel lecturemodel = (DefaultTableModel) lecture.getModel(); // 강의 테이블의 모델 가져오기
					lecturemodel.setRowCount(0);
					while(rs.next()) {
						Vector<String> vector = new Vector<>();
						vector.add(rs.getString("num"));
						vector.add(rs.getString("name"));
						vector.add(rs.getString("professor"));
						vector.add(rs.getString("day"));
						vector.add(rs.getInt("starthour")+"교시 ~ "+(rs.getInt("starthour")+rs.getInt("hour")-1)+"교시");
						vector.add(Integer.toString(rs.getInt("hour")*rs.getInt("count")));
						
						vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
						lecturemodel.addRow(vector); // 모델에 추가(테이블에 셋팅)
					}
					rs.close();
				}
				
			}
			
			
			
			pstmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
			e1.printStackTrace();
		} 
		
	}


	protected void deletelecture(int[] rows) {
		
		DefaultTableModel dtm =(DefaultTableModel) tableSelectLecture.getModel(); // 선택한강의 테이블 에서 가져온 tablemodel
		for(int row=rows.length-1;row >= 0;row--) { // 뒤에서부터 
			
			Vector<Object> vector =	dtm.getDataVector().get(row); // 원하는 값의 한 행 값의 벡터
			removeSchedule(vector); // 벡터값을 넘겨서 시간표에서 삭제
			dtm.removeRow(row);
			
		}
	
	
		int hakjum=0; // 학점
		for(int hrow =0;hrow< dtm.getRowCount();hrow++) { // selecttable 에서 학점 가져온 누적
			hakjum += Integer.parseInt(dtm.getValueAt(hrow, 5).toString());
		}
		sum=hakjum; // db에서 가져오기전 총점 셋팅
		lblhakjum.setText("총 학점 : "+hakjum+"/10"); // 누적한 학점 셋팅
		if(hakjum>10) {
			lblhakjum.setForeground(Color.red); // 설정한 값보다 크면 빨강
		}else {
			lblhakjum.setForeground(Color.black); // 아니면 검정
		}
	}



	protected void selectedTable(int[] rows) {
		
		for(int row : rows) {
			int num=0;
			boolean doublecheck = true;
			String name = (String) lecture.getValueAt(row, 1);
			String professor = (String) lecture.getValueAt(row, 2);
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = 
						DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
				
				//=============================================		
				String sql = "select * from lecture where name=? and professor =?"; // 가져온 강의명 교수명으로 강의 찾기
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, professor);
				ResultSet rs = pstmt.executeQuery();
				//String selLecturecol[] = {"과목명","교수","시간","요일","학점","인원"};
				DefaultTableModel selmodel = (DefaultTableModel) tableSelectLecture.getModel();
				if(rs.next()) {
					Vector<Object> vector = new Vector<>();
					num= rs.getInt("num"); // 가져온 강의코드 값
	//				System.out.println(num);
					
					for(int i =0;i< selmodel.getRowCount();i++){ // 그 값이 이미 테이블에 있는지 확인
						if(num == (int)selmodel.getValueAt(i, 0)) {
							doublecheck = false; // 있으면 false 없으면 true
							break;
						}
						
					}
					
					
	//				System.out.println(doublecheck);
					
					if(doublecheck) { // 중복되지 않으면 
					
					
					
					
					vector.add(num);
					vector.add(rs.getString("name"));
					vector.add(rs.getString("professor"));
					int startHour=rs.getInt("starthour");
					int Hour = rs.getInt("hour")-1; // 시작시간 + 수업시간 -1 = 종료시간
					
					vector.add(startHour+"교시 ~ "+(startHour+Hour)+"교시");
					vector.add(rs.getString("day"));
					vector.add(rs.getInt("hakjum"));
					
					vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
					
					selmodel.addRow(vector);
					Scheduleshow(num);
					
					sum += rs.getInt("hakjum");
					if(sum>10) {
						lblhakjum.setForeground(Color.red); 
					}
					lblhakjum.setText("총 학점 : "+sum+"/10");
					
					}
					
					
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
			} finally {
				
				
			}
		}
	}



	protected static void deletelecture(int row) {
		DefaultTableModel dtm =(DefaultTableModel) tableSelectLecture.getModel(); // 선택한강의 테이블 에서 가져온 tablemodel
		
		Vector<Object> vector =dtm.getDataVector().get(row); // 원하는 값의 한 행 값의 벡터
		
		dtm.removeRow(row); // 원하는 행 삭제
		
		removeSchedule(vector); // 벡터값을 넘겨서 시간표에서 삭제
		int hakjum=0; // 학점
		for(int hrow =0;hrow< dtm.getRowCount();hrow++) { // selecttable 에서 학점 가져온 누적
			hakjum += Integer.parseInt(dtm.getValueAt(hrow, 5).toString());
		}
		sum=hakjum; // db에서 가져오기전 총점 셋팅
		lblhakjum.setText("총 학점 : "+hakjum+"/10"); // 누적한 학점 셋팅
		if(hakjum>10) {
			lblhakjum.setForeground(Color.red); // 설정한 값보다 크면 빨강
		}else {
			lblhakjum.setForeground(Color.black); // 아니면 검정
		}
		
	}

	private static void removeSchedule(Vector<Object> vector) {
		String name= (String) vector.get(1); // 가져온 벡터 2번째 값 (강의명)
		String professor= (String) vector.get(2);// 교수명
		String scheduleName = name + "("+professor+")"; // 시간표에 셋팅한 값으로 변환
		String study= (String) vector.get(3); // 시작시간 ~ 종료시간
		String days=(String) vector.get(4); // 요일
		study =study.replaceAll("[^0-9|\\s]", ""); // 숫자 띄어쓰기 제외 삭제
		int starthour = 14-Integer.parseInt(study.split("  ")[0]); // 띄어쓰기로 잘라서 가져온 첫번째값(시작시간)
 		int hour = Integer.parseInt(study.split("  ")[1])-Integer.parseInt(study.split("  ")[0]); // 끝나는시간 - 시작시간 = 강의시간-1
		
		DefaultTableModel dtm=(DefaultTableModel)tableSchedule.getModel();
		int col=0; // 요일 설정
		for(String day : days.split(",")) { // 가져온 요일 ,로 구분 없으면 바로진행
//			System.out.println(day);
			switch(day) {  // 요일에따라 칼럼설정
			case "월" : col=1; break;
			case "화" : col=2; break;
			case "수" : col=3; break;
			case "목" : col=4; break;
			case "금" : col=5; break; 
			}

			for(int shour = starthour;shour>=starthour-hour;shour--) { // 시작 시간 \ 시작시간 + (강의시간-1) = 끝나는시간 
				// 시간표 테이블의 한 셀 가져오기
				Component cmp = tableSchedule.getCellRenderer(shour, col).getTableCellRendererComponent(tableSchedule, tableSchedule.getModel().getValueAt(shour, col), false, false, shour, col); 
//				System.out.println(cmp.getBackground());
				if(cmp.getBackground()==Color.red) { // 그셀의 색이 빨강이면(중복이면)
					
					dtm.setValueAt(dtm.getValueAt(shour, col).toString().replace(scheduleName, ""), shour, col); // 내가 지우려는 강의명(교수명) 삭제
					
				}	
				else 
					dtm.setValueAt(null, shour, col); // 중복이 아니면 null로
						
					
				
				
				
				
			}
		}
		
		
		
		
	}

	private void showtable(String id2) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			
			String sql = "SELECT name,professor from lecture where num in (select lecturenum from registration where studentid =?)"; // 이미 수강신청한 강의의 이름과 교수명 가져오기
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();				
			while(rs.next()) {
				selectedTable(rs.getString(1), rs.getString(2)); // 가져온 값을 선택한 테이블에 표시하기
			}
				
			pstmt.close();
			
			//==============================================
			
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
			e1.printStackTrace();
		} 
		
	}

	protected void registartion() {
		int check=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			String sql = "delete from registration where studentid ='"+id+"'"; // 수강 변경하기전 기존 수강 삭제
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			//=============================================		
			for(int i =0;i<tableSelectLecture.getModel().getRowCount();i++) { // 수강한 강의 만큼 반복
				
				
				
				sql = "INSERT INTO registration values(?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, tableSelectLecture.getModel().getValueAt(i, 0).toString());
				pstmt.setString(3, tableSelectLecture.getModel().getValueAt(i, 5).toString());
				
				pstmt.executeUpdate();				
				
				
				check++;
			
				pstmt.close();
			}
			stmt.close();
			//==============================================
			if(check==tableSelectLecture.getModel().getRowCount()) // 수강한 강의만큼 했으면
				reset(); // 종료할것인지 물어보기
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
			e1.printStackTrace();
		} 
		
		
	}

	private void reset() {
		
		
		if(JOptionPane.showConfirmDialog(null,"종료하시겠습니까?","exit" ,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
			dispose();
		}
		
	}

	protected boolean check() {
		boolean check = true;
		if(lblhakjum.getForeground()==Color.red) { // 학점이 초과되었으면
			JOptionPane.showMessageDialog(null, "학점을 확인해주세요","ERROR",JOptionPane.ERROR_MESSAGE);
			check = false;
		}else { 
			for(int i=0;i<tableSchedule.getRowCount();i++) {
				for(int j=1;j<tableSchedule.getColumnCount();j++) {
					//모든 테이블 셀 가져오기
					Component cmp = tableSchedule.getCellRenderer(i, j).getTableCellRendererComponent(tableSchedule, tableSchedule.getModel().getValueAt(i, j), false, false, i, j);
					
//					System.out.println(Color.red);
					if(cmp.getBackground()==Color.red) { // 셀의 색이 빨강이면(중복이면)
						JOptionPane.showMessageDialog(null, "시간표를 확인해주세요","ERROR",JOptionPane.ERROR_MESSAGE);
						check=false;
						
						
					}
				}
			}
			
		}
		return check; // 학점이 초과되거나 중복된시간이 있으면 false 없으면 true
		
		
		
	}

	protected static void selectedTable(String name, String professor) {
		int num=0;
		boolean doublecheck = true;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from lecture where name=? and professor =?"; // 가져온 강의명 교수명으로 강의 찾기
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, professor);
			ResultSet rs = pstmt.executeQuery();
			//String selLecturecol[] = {"과목명","교수","시간","요일","학점","인원"};
			DefaultTableModel selmodel = (DefaultTableModel) tableSelectLecture.getModel();
			if(rs.next()) {
				Vector<Object> vector = new Vector<>();
				num= rs.getInt("num"); // 가져온 강의코드 값
//				System.out.println(num);
				
				for(int i =0;i< selmodel.getRowCount();i++){ // 그 값이 이미 테이블에 있는지 확인
					if(num == (int)selmodel.getValueAt(i, 0)) {
						doublecheck = false; // 있으면 false 없으면 true
						break;
					}
					
				}
				
				
//				System.out.println(doublecheck);
				
				if(doublecheck) { // 중복되지 않으면 
				
				
				
				
				vector.add(num);
				vector.add(rs.getString("name"));
				vector.add(rs.getString("professor"));
				int startHour=rs.getInt("starthour");
				int Hour = rs.getInt("hour")-1; // 시작시간 + 수업시간 -1 = 종료시간
				
				vector.add(startHour+"교시 ~ "+(startHour+Hour)+"교시");
				vector.add(rs.getString("day"));
				vector.add(rs.getInt("hakjum"));
				
				vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
				
				selmodel.addRow(vector);
				Scheduleshow(num);
				
				sum += rs.getInt("hakjum");
				if(sum>10) {
					lblhakjum.setForeground(Color.red); 
				}
				lblhakjum.setText("총 학점 : "+sum+"/10");
				
				}
				
				
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
		} finally {
			
			
		}
		
	}

	private static void Scheduleshow(int num) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from lecture where num=? "; // 선택한 강의의 강의코드(pk)로 검색
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			ResultSet rs = pstmt.executeQuery();
			
			DefaultTableModel scheduleModel = (DefaultTableModel) tableSchedule.getModel();
			if(rs.next()) {
				
				
				String name= rs.getString("name");
				String professor = rs.getString("professor");
				int startHour=rs.getInt("starthour");
				int Hour = rs.getInt("hour")-1;
				int row = 14-startHour; // 아래부터 1교시 이므로 14-시작시간 이 시작 행
				int col=0;
				if(rs.getInt("count")==1) {
					switch(rs.getString("day")) { // 가져온요일이 하루면
					case "월" : col=1; break;
					case "화" : col=2; break;
					case "수" : col=3; break;
					case "목" : col=4; break;
					case "금" : col=5; break;
					}
					if(col!=0) {
						for(int i=row;i>=row-Hour;i--) {
//							System.out.println(i);
							if(scheduleModel.getValueAt(i, col)==null) { // 셋팅하려는 셀의 값이 없으면
								scheduleModel.setValueAt(name+"("+professor+")", i, col); // 강의명(교수명)으로 셋팅
							}else if(scheduleModel.getValueAt(i, col).toString().indexOf("(")!=-1){ // 값이 있으면
								scheduleModel.setValueAt(scheduleModel.getValueAt(i, col).toString()+name+"("+professor+")", i, col); // 기존값 + 강의명(교수명)
							}
						}
						
					}
					
				}else {
					String day[] = rs.getString("day").split(","); // 하루 초과이면
					for(int i=0;i<day.length;i++) {
						switch(day[i]) {
						case "월" : col=1; break;
						case "화" : col=2; break;
						case "수" : col=3; break;
						case "목" : col=4; break;
						case "금" : col=5; break;
						}
						if(col!=0) {
							for(int j=row;j>=row-Hour;j--) {
								
								String celltext = (String) scheduleModel.getValueAt(j, col);
													
								if(celltext==null||celltext.equals("")) {
									scheduleModel.setValueAt(name+"("+professor+")",j, col);


								}else{ 
									scheduleModel.setValueAt(celltext+name+"("+professor+")", j, col); // 위와동일


								}
							}
							
							
						}
					}
					
				}
				
				
			
				
				
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
		
	}
	
	
	
	

	private void showlecture() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from lecture";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			//String lectureColumns[] = {"과목코드","과목명","교수","요일","시간","학점","정원"};
			DefaultTableModel lecturemodel = (DefaultTableModel) lecture.getModel(); // 강의 테이블의 모델 가져오기
			while(rs.next()) {
				Vector<String> vector = new Vector<>();
				vector.add(rs.getString("num"));
				vector.add(rs.getString("name"));
				vector.add(rs.getString("professor"));
				vector.add(rs.getString("day"));
				vector.add(rs.getInt("starthour")+"교시 ~ "+(rs.getInt("starthour")+rs.getInt("hour")-1)+"교시");
				vector.add(Integer.toString(rs.getInt("hour")*rs.getInt("count")));
				
				vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
				lecturemodel.addRow(vector); // 모델에 추가(테이블에 셋팅)
				
				
				
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
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getClickCount()==2) {
					if(lecture == (JTable)e.getSource()) {
						int row = lecture.getSelectedRow();
						String name = (String) lecture.getValueAt(row, 1);
						String professor = (String) lecture.getValueAt(row, 2);
						selectedTable(name,professor);
					}
					else if(tableSelectLecture ==(JTable)e.getSource() ) {
						int row = tableSelectLecture.getSelectedRow();
						deletelecture(row);
					}
					
				}
			}
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					JTable source = (JTable)e.getSource();
					int row = source.rowAtPoint(e.getPoint());
					int col = source.columnAtPoint(e.getPoint());
					if(!source.isRowSelected(row))
						source.changeSelection(row, col, false,false);
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private static void myTrayIcon(Image image, String tooltip, ActionListener action ) {
		if(SystemTray.isSupported()) {
			if(trayIcon != null) {
				trayIcon=null;
			}
			trayIcon = new TrayIcon(image);
			trayIcon.setImageAutoSize(true);
			if (tooltip != null) {
				trayIcon.setToolTip(tooltip);
			}

			if (action != null) {
				trayIcon.addActionListener(action);
			}

			try {
				for (TrayIcon registeredTrayIcon : SystemTray.getSystemTray()
						.getTrayIcons()) {
					SystemTray.getSystemTray().remove(registeredTrayIcon);
				}

				SystemTray.getSystemTray().add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("System tray is not supported !");

		}
		
		PopupMenu menu = new PopupMenu();
		MenuItem item = new MenuItem("종료");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		trayIcon.setPopupMenu(new PopupMenu("종료") {
			@Override
			public MenuItem add(MenuItem mi) {
				mi = new MenuItem("종료");
				mi.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
				});
				return super.add(mi);
			}
		});
		
		
		
		trayIcon.setPopupMenu(menu);
	}
	
}
