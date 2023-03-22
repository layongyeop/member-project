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
		
		String ScheduleColumns[] = {"�ð�","��","ȭ","��","��","��"}; // �ð�ǥ Į��
		
		DefaultTableModel Schedulemodel = new DefaultTableModel(ScheduleColumns,0){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		
		
		for (int i=14;i>0;i--) {
			Vector<String> vector = new Vector<>();
			vector.add(i+"����("+(8+i)+":00 ~"+(8+i)+":50)"); // �ð�ǥ ��ù��° ��
			
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

				tableSchedule.getColumn(ScheduleColumns[i]).setCellRenderer(new MyTableCellRenderer()); // Ư�� ���� ���� �����Ű�� ���� renderer ����

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
			String sql = "select * from lecture where num in (select lecturenum from registration where studentId=? ) "; // ������ ������ �����ڵ�(pk)�� �˻�
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,id);
			
			ResultSet rs = pstmt.executeQuery();
			
			DefaultTableModel scheduleModel = (DefaultTableModel) tableSchedule.getModel();
			while(rs.next()) {
				
				
				String name= rs.getString("name");
				String professor = rs.getString("professor");
				int startHour=rs.getInt("starthour");
				int Hour = rs.getInt("hour")-1;
				int row = 14-startHour; // �Ʒ����� 1���� �̹Ƿ� 14-���۽ð� �� ���� ��
				int col=0;
				if(rs.getInt("count")==1) {
					switch(rs.getString("day")) { // �����¿����� �Ϸ��
					case "��" : col=1; break;
					case "ȭ" : col=2; break;
					case "��" : col=3; break;
					case "��" : col=4; break;
					case "��" : col=5; break;
					}
					if(col!=0) {
						for(int i=row;i>=row-Hour;i--) {
//							System.out.println(i);
							if(scheduleModel.getValueAt(i, col)==null) { // �����Ϸ��� ���� ���� ������
								scheduleModel.setValueAt(name+"("+professor+")", i, col); // ���Ǹ�(������)���� ����
							}else if(scheduleModel.getValueAt(i, col).toString().indexOf("(")!=-1){ // ���� ������
								scheduleModel.setValueAt(scheduleModel.getValueAt(i, col).toString()+name+"("+professor+")", i, col); // ������ + ���Ǹ�(������)
							}
						}
						
					}
					
				}else {
					String day[] = rs.getString("day").split(","); // �Ϸ� �ʰ��̸�
					for(int i=0;i<day.length;i++) {
						switch(day[i]) {
						case "��" : col=1; break;
						case "ȭ" : col=2; break;
						case "��" : col=3; break;
						case "��" : col=4; break;
						case "��" : col=5; break;
						}
						if(col!=0) {
							for(int j=row;j>=row-Hour;j--) {
								
								String celltext = (String) scheduleModel.getValueAt(j, col);
													
								if(celltext==null||celltext.equals("")) {
									scheduleModel.setValueAt(name+"("+professor+")",j, col);


								}else{ 
									scheduleModel.setValueAt(celltext+name+"("+professor+")", j, col); // ���͵���


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
			System.out.println("JDBC ����̹� �ε� ����");
		} catch (SQLException e1) {
			System.out.println("DB ���� ����");
			e1.printStackTrace();
		}
		
	}
}
