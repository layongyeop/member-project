package admin;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JTable;

public class Wintable extends JDialog {
	private JTable tableSchedule;
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 * 
	 */
	
//	public Wintable(String id) {
//		super();
//		this.id = id;
//		showtable(id);
//	}
	public Wintable(String id) {
		setBounds(100, 100, 612, 508);
		getContentPane().setLayout(null);
		
		JScrollPane SchedulePane = new JScrollPane();
		SchedulePane.setWheelScrollingEnabled(false);
		SchedulePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		SchedulePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		SchedulePane.setBorder(null);
		SchedulePane.setBounds(12, 35, 579, 428);
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
		SchedulePane.setViewportView(tableSchedule);
		tableSchedule.getColumnModel().getColumn(0).setMinWidth(120);
		
		tableSchedule.setRowHeight(SchedulePane.getHeight()/(tableSchedule.getRowCount()+1)+1);
		SchedulePane.setViewportView(tableSchedule);
		
		for(int i = 0; i<tableSchedule.getColumnCount();i++) { 

				tableSchedule.getColumn(ScheduleColumns[i]).setCellRenderer(new MyTableCellRenderer()); // 특정 셀의 색을 변경시키기 위한 renderer 셋팅

		}
		
		JLabel lblNewLabel_2_1 = new JLabel("\uC2DC\uAC04\uD45C");
		lblNewLabel_2_1.setBounds(304, 10, 57, 15);
		getContentPane().add(lblNewLabel_2_1);
		showtable(id);

	}
	protected void showtable(String id) {
		
		
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from lecture where num in (select lecturenum from registration where studentId=? ) "; // 선택한 강의의 강의코드(pk)로 검색
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,id);
			
			ResultSet rs = pstmt.executeQuery();
			
			DefaultTableModel scheduleModel = (DefaultTableModel) tableSchedule.getModel();
			while(rs.next()) {
				
				
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
