package admin;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import javax.swing.JPopupMenu;

public class WinLookup extends JDialog {
	private JTable table;
	private DefaultTableModel dtm;
	private boolean type;
	private JMenuItem mnudelete;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinLookup dialog = new WinLookup("ȫ","1");
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
	public WinLookup() {		
		
		setBounds(100, 100, 450, 300);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String header[] = {"�й�","�̸�","�а�","��ȭ��ȣ"};
		dtm = new DefaultTableModel(header, 0);
		
		table = new JTable(dtm);
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int row = table.getSelectedRow();
//				String sID = table.getValueAt(row, 0).toString();
//				//System.out.println(sID);
//				
//				setVisible(false); // �ڼ��� ������ ǥ���ϴ� WinStudentDelete â�� ����, �� â�� �����.
//				
//				WinStudentDelete winStudentDelete = new WinStudentDelete(sID, type);
//				winStudentDelete.setModal(true);
//				winStudentDelete.setVisible(true);
//			}
//		});
		scrollPane.setViewportView(table);
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem mnuinfo = new JMenuItem("�󼼺���...");
		mnuinfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String sID = table.getValueAt(row, 0).toString();
				//System.out.println(sID);
				
				setVisible(false); // �ڼ��� ������ ǥ���ϴ� WinStudentDelete â�� ����, �� â�� �����.
				
				WinStudentDelete winStudentDelete = new WinStudentDelete(sID, true);
				winStudentDelete.setModal(true);
				winStudentDelete.setVisible(true);
				
			}
		});
		mnudelete = new JMenuItem("����");
		mnudelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String sID = table.getValueAt(row, 0).toString();
				
				WinStudentDelete delete = new WinStudentDelete(sID, false);
				delete.deleteRecord();
				dtm.removeRow(row);
				
			}
		});
		
		popupMenu.add(mnuinfo);
		popupMenu.add(mnudelete);
		addPopup( table, popupMenu);
		

	}

	public WinLookup(String sName) {
		// TODO Auto-generated constructor stub
		this();
		// DB���� select where �̸� like
		showLookupName(sName,""); // �̸����� �˻��Ͽ� ���̺� ǥ���Ѵ�.	
		setTitle("������ ��� ǥ��");
		type = false;
		mnudelete.setVisible(!type);
	}

	public WinLookup(String sName, String sPhone) {
		// TODO Auto-generated constructor stub
		this();
		setTitle("��ȸ�� ��� ǥ��");
		showLookupName(sName, sPhone);
		
		type = true;
		mnudelete.setVisible(!type);
		
	}

	private void showLookupName(String sName, String sPhone) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================	
			String sql="";
			PreparedStatement pstmt=null;
			if(sPhone.equals("") && !sName.equals("")) {
				sql = "select * from studentTBL where name like ?";			
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, "%" + sName + "%");
			}else if(!sPhone.equals("") && sName.equals("")) {
				sql = "select * from studentTBL where phone like ?";			
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, "%" + sPhone + "%");
			}else if(sPhone.equals("") && sName.equals("")) {
				sql = "select * from studentTBL";			
				pstmt=con.prepareStatement(sql);
			}
			else {
				sql = "select * from studentTBL where name like ? and phone like ?";			
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, "%" + sName  + "%");
				pstmt.setString(2, "%" + sPhone + "%");
			}
			ResultSet rs = pstmt.executeQuery();			
			while(rs.next()) {  // �� ���ڵ��� �ʵ���� �о ���Ϳ� ������ ��, DefaultTableModel �� �߰��Ѵ�.
				Vector<String> vector = new Vector<>();
				vector.add(rs.getString("id"));
				vector.add(rs.getString("name"));
				
				String sql2 = "select deptName from deptTBL where deptId=?";
				PreparedStatement pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, rs.getString("id").substring(4,6));
//				System.out.println(sql2);
				
				ResultSet rs2 = pstmt2.executeQuery();
				if(rs2.next())
					vector.add(rs2.getString("deptName"));
				rs2.close();
				pstmt2.close();
				
				vector.add(rs.getString("phone"));
				dtm.addRow(vector);
			}
			rs.close();		
			pstmt.close();
			con.close();
			//==============================================
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC ����̹� �ε� ����");
		} catch (SQLException e1) {
			System.out.println("DB ���� ����");
		}				
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
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

}
