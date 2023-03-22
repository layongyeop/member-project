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
					Registration dialog = new Registration("199910111"); // Ȯ�������� �ִ°� ���� �⺻ null
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					
					myTrayIcon(Toolkit.getDefaultToolkit().getImage("src/images/book.png"), "������û", new ActionListener() {
						
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
		
		String lectureColumns[] = {"�����ڵ�","���Ǹ�","����","����","�ð�","����","����"}; // ���� ���̺� Į��
		
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
		
		
		
		
		tableSchedule.getColumnModel().getColumn(0).setMinWidth(120);
		
		tableSchedule.setRowHeight(SchedulePane.getHeight()/(tableSchedule.getRowCount()+1)+1);
		SchedulePane.setViewportView(tableSchedule);
		
		for(int i = 0; i<tableSchedule.getColumnCount();i++) { 

				tableSchedule.getColumn(ScheduleColumns[i]).setCellRenderer(new MyTableCellRenderer()); // Ư�� ���� ���� �����Ű�� ���� renderer ����

		}
		

		
		
		
		JScrollPane selectLecture = new JScrollPane();
		selectLecture.setBounds(12, 512, 1098, 186);
		getContentPane().add(selectLecture);
		
		
		String selLecturecol[] = {"�����ڵ�","���Ǹ�","����","�ð�","����","����","����"}; // �����Ѱ��� ���̺� Į��
		
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
		
		JMenuItem mntmNewMenuItem = new JMenuItem("����");
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
				if(check()) { // ���� or �ð�ǥ �ߺ� Ȯ��
					registartion(); // ������û(insert)
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
		lecture.getColumnModel().getColumn(4).setMinWidth(100); // ���� �ð� ���� ����
		
		if(id!=null)
			showtable(id);// id���� ������ ������ �����Ѱ� ǥ�� ������ ǥ��x
		
		

	}
	

	protected void lectSearch(String lectSearchTag, String lectSearchText) {
		String lectSearchTextList[] = null;
		if(lectSearchText.indexOf("����")!=-1) {
			lectSearchText=lectSearchText.replaceAll("����", "");
		}
		if(lectSearchText.indexOf(",")!=-1) {
			lectSearchTextList=lectSearchText.split(",");
		}
		
		switch(lectSearchTag) {
		case "�����ڵ�": lectSearchTag="num"; break;
		case "���Ǹ�": lectSearchTag="name"; break;
		case "����": lectSearchTag="professor"; break;
		case "����": lectSearchTag="day"; break;
		
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
				//String lectureColumns[] = {"�����ڵ�","�����","����","����","�ð�","����","����"};
				DefaultTableModel lecturemodel = (DefaultTableModel) lecture.getModel(); // ���� ���̺��� �� ��������
				lecturemodel.setRowCount(0);
				while(rs.next()) {
					Vector<String> vector = new Vector<>();
					vector.add(rs.getString("num"));
					vector.add(rs.getString("name"));
					vector.add(rs.getString("professor"));
					vector.add(rs.getString("day"));
					vector.add(rs.getInt("starthour")+"���� ~ "+(rs.getInt("starthour")+rs.getInt("hour")-1)+"����");
					vector.add(Integer.toString(rs.getInt("hour")*rs.getInt("count")));
					
					vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
					lecturemodel.addRow(vector); // �𵨿� �߰�(���̺� ����)
					
					
					
				}
				rs.close();
			}else {
				for(String daytext:lectSearchTextList) {
					String sql = "select * from lecture where day  like ?";
					pstmt = con.prepareStatement(sql);
		//			pstmt.setString(1, lectSearchTag);
					pstmt.setString(1, "%"+daytext+"%");
					ResultSet rs = pstmt.executeQuery();
					//String lectureColumns[] = {"�����ڵ�","�����","����","����","�ð�","����","����"};
					DefaultTableModel lecturemodel = (DefaultTableModel) lecture.getModel(); // ���� ���̺��� �� ��������
					lecturemodel.setRowCount(0);
					while(rs.next()) {
						Vector<String> vector = new Vector<>();
						vector.add(rs.getString("num"));
						vector.add(rs.getString("name"));
						vector.add(rs.getString("professor"));
						vector.add(rs.getString("day"));
						vector.add(rs.getInt("starthour")+"���� ~ "+(rs.getInt("starthour")+rs.getInt("hour")-1)+"����");
						vector.add(Integer.toString(rs.getInt("hour")*rs.getInt("count")));
						
						vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
						lecturemodel.addRow(vector); // �𵨿� �߰�(���̺� ����)
					}
					rs.close();
				}
				
			}
			
			
			
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


	protected void deletelecture(int[] rows) {
		
		DefaultTableModel dtm =(DefaultTableModel) tableSelectLecture.getModel(); // �����Ѱ��� ���̺� ���� ������ tablemodel
		for(int row=rows.length-1;row >= 0;row--) { // �ڿ������� 
			
			Vector<Object> vector =	dtm.getDataVector().get(row); // ���ϴ� ���� �� �� ���� ����
			removeSchedule(vector); // ���Ͱ��� �Ѱܼ� �ð�ǥ���� ����
			dtm.removeRow(row);
			
		}
	
	
		int hakjum=0; // ����
		for(int hrow =0;hrow< dtm.getRowCount();hrow++) { // selecttable ���� ���� ������ ����
			hakjum += Integer.parseInt(dtm.getValueAt(hrow, 5).toString());
		}
		sum=hakjum; // db���� ���������� ���� ����
		lblhakjum.setText("�� ���� : "+hakjum+"/10"); // ������ ���� ����
		if(hakjum>10) {
			lblhakjum.setForeground(Color.red); // ������ ������ ũ�� ����
		}else {
			lblhakjum.setForeground(Color.black); // �ƴϸ� ����
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
				String sql = "select * from lecture where name=? and professor =?"; // ������ ���Ǹ� ���������� ���� ã��
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, professor);
				ResultSet rs = pstmt.executeQuery();
				//String selLecturecol[] = {"�����","����","�ð�","����","����","�ο�"};
				DefaultTableModel selmodel = (DefaultTableModel) tableSelectLecture.getModel();
				if(rs.next()) {
					Vector<Object> vector = new Vector<>();
					num= rs.getInt("num"); // ������ �����ڵ� ��
	//				System.out.println(num);
					
					for(int i =0;i< selmodel.getRowCount();i++){ // �� ���� �̹� ���̺� �ִ��� Ȯ��
						if(num == (int)selmodel.getValueAt(i, 0)) {
							doublecheck = false; // ������ false ������ true
							break;
						}
						
					}
					
					
	//				System.out.println(doublecheck);
					
					if(doublecheck) { // �ߺ����� ������ 
					
					
					
					
					vector.add(num);
					vector.add(rs.getString("name"));
					vector.add(rs.getString("professor"));
					int startHour=rs.getInt("starthour");
					int Hour = rs.getInt("hour")-1; // ���۽ð� + �����ð� -1 = ����ð�
					
					vector.add(startHour+"���� ~ "+(startHour+Hour)+"����");
					vector.add(rs.getString("day"));
					vector.add(rs.getInt("hakjum"));
					
					vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
					
					selmodel.addRow(vector);
					Scheduleshow(num);
					
					sum += rs.getInt("hakjum");
					if(sum>10) {
						lblhakjum.setForeground(Color.red); 
					}
					lblhakjum.setText("�� ���� : "+sum+"/10");
					
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
			} finally {
				
				
			}
		}
	}



	protected static void deletelecture(int row) {
		DefaultTableModel dtm =(DefaultTableModel) tableSelectLecture.getModel(); // �����Ѱ��� ���̺� ���� ������ tablemodel
		
		Vector<Object> vector =dtm.getDataVector().get(row); // ���ϴ� ���� �� �� ���� ����
		
		dtm.removeRow(row); // ���ϴ� �� ����
		
		removeSchedule(vector); // ���Ͱ��� �Ѱܼ� �ð�ǥ���� ����
		int hakjum=0; // ����
		for(int hrow =0;hrow< dtm.getRowCount();hrow++) { // selecttable ���� ���� ������ ����
			hakjum += Integer.parseInt(dtm.getValueAt(hrow, 5).toString());
		}
		sum=hakjum; // db���� ���������� ���� ����
		lblhakjum.setText("�� ���� : "+hakjum+"/10"); // ������ ���� ����
		if(hakjum>10) {
			lblhakjum.setForeground(Color.red); // ������ ������ ũ�� ����
		}else {
			lblhakjum.setForeground(Color.black); // �ƴϸ� ����
		}
		
	}

	private static void removeSchedule(Vector<Object> vector) {
		String name= (String) vector.get(1); // ������ ���� 2��° �� (���Ǹ�)
		String professor= (String) vector.get(2);// ������
		String scheduleName = name + "("+professor+")"; // �ð�ǥ�� ������ ������ ��ȯ
		String study= (String) vector.get(3); // ���۽ð� ~ ����ð�
		String days=(String) vector.get(4); // ����
		study =study.replaceAll("[^0-9|\\s]", ""); // ���� ���� ���� ����
		int starthour = 14-Integer.parseInt(study.split("  ")[0]); // ����� �߶� ������ ù��°��(���۽ð�)
 		int hour = Integer.parseInt(study.split("  ")[1])-Integer.parseInt(study.split("  ")[0]); // �����½ð� - ���۽ð� = ���ǽð�-1
		
		DefaultTableModel dtm=(DefaultTableModel)tableSchedule.getModel();
		int col=0; // ���� ����
		for(String day : days.split(",")) { // ������ ���� ,�� ���� ������ �ٷ�����
//			System.out.println(day);
			switch(day) {  // ���Ͽ����� Į������
			case "��" : col=1; break;
			case "ȭ" : col=2; break;
			case "��" : col=3; break;
			case "��" : col=4; break;
			case "��" : col=5; break; 
			}

			for(int shour = starthour;shour>=starthour-hour;shour--) { // ���� �ð� \ ���۽ð� + (���ǽð�-1) = �����½ð� 
				// �ð�ǥ ���̺��� �� �� ��������
				Component cmp = tableSchedule.getCellRenderer(shour, col).getTableCellRendererComponent(tableSchedule, tableSchedule.getModel().getValueAt(shour, col), false, false, shour, col); 
//				System.out.println(cmp.getBackground());
				if(cmp.getBackground()==Color.red) { // �׼��� ���� �����̸�(�ߺ��̸�)
					
					dtm.setValueAt(dtm.getValueAt(shour, col).toString().replace(scheduleName, ""), shour, col); // ���� ������� ���Ǹ�(������) ����
					
				}	
				else 
					dtm.setValueAt(null, shour, col); // �ߺ��� �ƴϸ� null��
						
					
				
				
				
				
			}
		}
		
		
		
		
	}

	private void showtable(String id2) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			
			String sql = "SELECT name,professor from lecture where num in (select lecturenum from registration where studentid =?)"; // �̹� ������û�� ������ �̸��� ������ ��������
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();				
			while(rs.next()) {
				selectedTable(rs.getString(1), rs.getString(2)); // ������ ���� ������ ���̺� ǥ���ϱ�
			}
				
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

	protected void registartion() {
		int check=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			String sql = "delete from registration where studentid ='"+id+"'"; // ���� �����ϱ��� ���� ���� ����
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			//=============================================		
			for(int i =0;i<tableSelectLecture.getModel().getRowCount();i++) { // ������ ���� ��ŭ �ݺ�
				
				
				
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
			if(check==tableSelectLecture.getModel().getRowCount()) // ������ ���Ǹ�ŭ ������
				reset(); // �����Ұ����� �����
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC ����̹� �ε� ����");
		} catch (SQLException e1) {
			System.out.println("DB ���� ����");
			e1.printStackTrace();
		} 
		
		
	}

	private void reset() {
		
		
		if(JOptionPane.showConfirmDialog(null,"�����Ͻðڽ��ϱ�?","exit" ,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
			dispose();
		}
		
	}

	protected boolean check() {
		boolean check = true;
		if(lblhakjum.getForeground()==Color.red) { // ������ �ʰ��Ǿ�����
			JOptionPane.showMessageDialog(null, "������ Ȯ�����ּ���","ERROR",JOptionPane.ERROR_MESSAGE);
			check = false;
		}else { 
			for(int i=0;i<tableSchedule.getRowCount();i++) {
				for(int j=1;j<tableSchedule.getColumnCount();j++) {
					//��� ���̺� �� ��������
					Component cmp = tableSchedule.getCellRenderer(i, j).getTableCellRendererComponent(tableSchedule, tableSchedule.getModel().getValueAt(i, j), false, false, i, j);
					
//					System.out.println(Color.red);
					if(cmp.getBackground()==Color.red) { // ���� ���� �����̸�(�ߺ��̸�)
						JOptionPane.showMessageDialog(null, "�ð�ǥ�� Ȯ�����ּ���","ERROR",JOptionPane.ERROR_MESSAGE);
						check=false;
						
						
					}
				}
			}
			
		}
		return check; // ������ �ʰ��ǰų� �ߺ��Ƚð��� ������ false ������ true
		
		
		
	}

	protected static void selectedTable(String name, String professor) {
		int num=0;
		boolean doublecheck = true;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from lecture where name=? and professor =?"; // ������ ���Ǹ� ���������� ���� ã��
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, professor);
			ResultSet rs = pstmt.executeQuery();
			//String selLecturecol[] = {"�����","����","�ð�","����","����","�ο�"};
			DefaultTableModel selmodel = (DefaultTableModel) tableSelectLecture.getModel();
			if(rs.next()) {
				Vector<Object> vector = new Vector<>();
				num= rs.getInt("num"); // ������ �����ڵ� ��
//				System.out.println(num);
				
				for(int i =0;i< selmodel.getRowCount();i++){ // �� ���� �̹� ���̺� �ִ��� Ȯ��
					if(num == (int)selmodel.getValueAt(i, 0)) {
						doublecheck = false; // ������ false ������ true
						break;
					}
					
				}
				
				
//				System.out.println(doublecheck);
				
				if(doublecheck) { // �ߺ����� ������ 
				
				
				
				
				vector.add(num);
				vector.add(rs.getString("name"));
				vector.add(rs.getString("professor"));
				int startHour=rs.getInt("starthour");
				int Hour = rs.getInt("hour")-1; // ���۽ð� + �����ð� -1 = ����ð�
				
				vector.add(startHour+"���� ~ "+(startHour+Hour)+"����");
				vector.add(rs.getString("day"));
				vector.add(rs.getInt("hakjum"));
				
				vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
				
				selmodel.addRow(vector);
				Scheduleshow(num);
				
				sum += rs.getInt("hakjum");
				if(sum>10) {
					lblhakjum.setForeground(Color.red); 
				}
				lblhakjum.setText("�� ���� : "+sum+"/10");
				
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
		} finally {
			
			
		}
		
	}

	private static void Scheduleshow(int num) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from lecture where num=? "; // ������ ������ �����ڵ�(pk)�� �˻�
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			ResultSet rs = pstmt.executeQuery();
			
			DefaultTableModel scheduleModel = (DefaultTableModel) tableSchedule.getModel();
			if(rs.next()) {
				
				
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
	
	
	
	

	private void showlecture() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from lecture";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			//String lectureColumns[] = {"�����ڵ�","�����","����","����","�ð�","����","����"};
			DefaultTableModel lecturemodel = (DefaultTableModel) lecture.getModel(); // ���� ���̺��� �� ��������
			while(rs.next()) {
				Vector<String> vector = new Vector<>();
				vector.add(rs.getString("num"));
				vector.add(rs.getString("name"));
				vector.add(rs.getString("professor"));
				vector.add(rs.getString("day"));
				vector.add(rs.getInt("starthour")+"���� ~ "+(rs.getInt("starthour")+rs.getInt("hour")-1)+"����");
				vector.add(Integer.toString(rs.getInt("hour")*rs.getInt("count")));
				
				vector.add(rs.getString("personcount")+"/"+rs.getString("personnel"));
				lecturemodel.addRow(vector); // �𵨿� �߰�(���̺� ����)
				
				
				
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
		MenuItem item = new MenuItem("����");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		trayIcon.setPopupMenu(new PopupMenu("����") {
			@Override
			public MenuItem add(MenuItem mi) {
				mi = new MenuItem("����");
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
