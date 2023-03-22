import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import admin.MyTableCellRenderer;
import admin.WinInsertScore;
import admin.WinStudentUpdate;

import javax.swing.JMenuItem;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;

public class WinStudentMain extends JDialog {
	private JScrollPane SchedulePane;
	private static JTable tableSchedule;
	private JMenuItem mnuReg;
	private JMenuItem mnuSearch;
	private JMenuItem mnuModify;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinStudentMain dialog = new WinStudentMain("199910111");
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
	public WinStudentMain(String id) {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				ScheduleReset();
				showtable(id);
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		
		setBounds(100, 100, 628, 530);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
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
		SchedulePane.add(tableSchedule);
		tableSchedule.getColumnModel().getColumn(0).setMinWidth(120);
		
		tableSchedule.setRowHeight(SchedulePane.getHeight()/(tableSchedule.getRowCount()+1)+1);
		SchedulePane.setViewportView(tableSchedule);
		
		for(int i = 0; i<tableSchedule.getColumnCount();i++) { 

				tableSchedule.getColumn(ScheduleColumns[i]).setCellRenderer(new MyTableCellRenderer()); // 특정 셀의 색을 변경시키기 위한 renderer 셋팅

		}
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnuReg = new JMenuItem("\uC218\uAC15 \uC2E0\uCCAD/\uBCC0\uACBD...");
		mnuReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registration registration = new Registration(id);
				registration.setModal(true);
				registration.setVisible(true);
				
				
				
			}
		});
		menuBar.add(mnuReg);
		
		mnuSearch = new JMenuItem("\uC131\uC801 \uC870\uD68C...");
		mnuSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinInsertScore insertScore = new WinInsertScore(id);
				insertScore.setModal(true);
				insertScore.setVisible(true);
				
			}
		});
		menuBar.add(mnuSearch);
		
		mnuModify = new JMenuItem("\uC815\uBCF4 \uBCC0\uACBD...");
		mnuModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinStudentUpdate studentUpdate = new WinStudentUpdate(id);
				studentUpdate.setModal(true);
				studentUpdate.setVisible(true);
				
			}
		});
		menuBar.add(mnuModify);
		showtable(id);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}
	private void showtable(String id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
//			select * from lecture where name=(SELECT name,professor from lecture where num in (select lecturenum from registration where studentid =?)) and professor =(SELECT name,professor from lecture where num in (select lecturenum from registration where studentid =?))
			String sql = "select * from lecture where name in (SELECT name from lecture where num in (select lecturenum from registration where studentid =?)) and professor in (SELECT professor from lecture where num in (select lecturenum from registration where studentid =?))"; // 이미 수강신청한 강의의 이름과 교수명 가져오기
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, id);
			ResultSet rs = pstmt.executeQuery();				
			while(rs.next()) {
				System.out.println(rs.getInt(1));
				Scheduleshow(rs.getInt(1));// 가져온 값을 선택한 테이블에 표시하기
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
	private static void ScheduleReset() {
		for(int row =0;row<tableSchedule.getModel().getRowCount();row++) {
			for(int col=1;col< tableSchedule.getModel().getColumnCount();col++) {
				tableSchedule.setValueAt(null, row, col);				
			}
			
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
}
