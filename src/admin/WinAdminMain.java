package admin;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MenuItem;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Pattern;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToolBar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;

public class WinAdminMain extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel dtm;
	private JToolBar toolBar;
	private JMenuItem mnStudentInsert;
	private JMenuItem mnStudentDelete;
	private JMenuItem mnStudentUpdate;
	private JMenuItem mnStudentSelect;
	private JMenu mnStudentInfo;
	private JTextField textField;
	private String numpattern = "^[0-9]*$";
	private JMenuBar menuBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinAdminMain dialog = new WinAdminMain();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinAdminMain() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				ShowTable("");
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});		
		setTitle("학생 관리 프로그램");
		setBounds(100, 100, 914, 472);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				String header[] = {"학번","이름","전화번호","주민번호","이메일","파일경로","주소"};
				dtm = new DefaultTableModel(header, 0);
				
				JPopupMenu popupMenu = new JPopupMenu();
					
				
				table = new JTable(dtm);
				scrollPane.setViewportView(table);
				
				
				addPopup(table, popupMenu);
				{
					JMenuItem mnuStudentInsert = new JMenuItem("추가...");
					mnuStudentInsert.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							WinStudentInsert winStudentInsert = new WinStudentInsert();
							winStudentInsert.setModal(true);
							winStudentInsert.setVisible(true);
						}
					});
					popupMenu.add(mnuStudentInsert);
				}
				{
					JMenuItem mnuStudentSelect = new JMenuItem("조회...");
					mnuStudentSelect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int row = table.getSelectedRow();
							WinStudentDelete studentDelete = new WinStudentDelete(table.getValueAt(row, 0).toString(),true);
							studentDelete.setModal(true);
							studentDelete.setVisible(true);
						}
					});
					popupMenu.add(mnuStudentSelect);
				}
				{
					JMenuItem mnuStudentDelete = new JMenuItem("삭제");
					mnuStudentDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int row = table.getSelectedRow();
							System.out.println("날라갈 값=>" + table.getValueAt(row, 0)); //ID
							WinStudentDelete winStudentDelete = 
									new WinStudentDelete(table.getValueAt(row, 0).toString(), false);
							winStudentDelete.setModal(true);
							winStudentDelete.setVisible(true);
						}
					});
					popupMenu.add(mnuStudentDelete);
				}
				{
					JMenuItem mnuStudentUpdate = new JMenuItem("변경");
					mnuStudentUpdate.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int row = table.getSelectedRow();
							String sID = table.getValueAt(row, 0).toString();
							WinStudentUpdate studentUpdate = new WinStudentUpdate(sID);
							studentUpdate.setModal(true);
							studentUpdate.setVisible(true);
						}
					});
					popupMenu.add(mnuStudentUpdate);
				}
			}
		}
		{
			toolBar = new JToolBar();
			getContentPane().add(toolBar, BorderLayout.NORTH);
			{
				JButton btninsert = new JButton("");
				btninsert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						WinStudentInsert winStudentInsert = new WinStudentInsert();
						winStudentInsert.setModal(true);
						winStudentInsert.setVisible(true);
					}
				});
				btninsert.setIcon(new ImageIcon(WinAdminMain.class.getResource("/image/memberAdd.png")));
				toolBar.add(btninsert);
			}
			{
				JButton btndelete = new JButton("");
				btndelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int row = table.getSelectedRow();
						if(row != -1) {
							WinStudentDelete winStudentDelete = 
									new WinStudentDelete(table.getValueAt(row, 0).toString(), false);
							winStudentDelete.setModal(true);
							winStudentDelete.setVisible(true);
						}
						else {
						String sName = JOptionPane.showInputDialog("삭제할 이름 입력하시오");
						if(sName!=null) {
							WinLookup winLookup = new WinLookup(sName);
							winLookup.setModal(true);
							winLookup.setVisible(true);
						}
						}
					}
				});
				btndelete.setIcon(new ImageIcon(WinAdminMain.class.getResource("/image/memberRemove.png")));
				toolBar.add(btndelete);
			}
			{
				JButton btnUpdate = new JButton("");
				btnUpdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int row = table.getSelectedRow();
						if(row != -1) {
							String sID = table.getValueAt(row, 0).toString();
							WinStudentUpdate studentUpdate = new WinStudentUpdate(sID);
							studentUpdate.setModal(true);
							studentUpdate.setVisible(true);
						}
						else {
						WinStudentUpdate studentUpdate = new WinStudentUpdate();
						studentUpdate.setModal(true);
						studentUpdate.setVisible(true);
					}
					}
				});
				btnUpdate.setIcon(new ImageIcon(WinAdminMain.class.getResource("/image/memberUpdate.png")));
				toolBar.add(btnUpdate);
				
				
				
				
				
			}
			{
				JButton btnselect = new JButton("");
				btnselect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int row = table.getSelectedRow();
						if(row != -1) {
							WinStudentDelete studentDelete = new WinStudentDelete(table.getValueAt(row, 0).toString(),true);
							studentDelete.setModal(true);
							studentDelete.setVisible(true);
						}
						else {
							WinSearchWord winSearchWord = new WinSearchWord();
							winSearchWord.setModal(true);
							winSearchWord.setVisible(true);
						}
					}
				});
				btnselect.setIcon(new ImageIcon(WinAdminMain.class.getResource("/image/memberSearch.png")));
				toolBar.add(btnselect);
			}
			{
				textField = new JTextField();
				textField.setToolTipText("search id");
				textField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							String sId = textField.getText(); // 검색할 id값
							if(Pattern.matches(numpattern, sId)) { // 숫자인지
								ShowTable(sId);
							}							
							
						}
					}
				});
				toolBar.add(textField);
				textField.setColumns(10);
			}
		}
		{
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnFile = new JMenu("파일(F)");
				mnFile.setMnemonic('F');
				menuBar.add(mnFile);
				{
					JMenuItem mnExit = new JMenuItem("종료");
					mnExit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							System.exit(DISPOSE_ON_CLOSE);
						}
					});
					mnExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_DOWN_MASK));
					mnFile.add(mnExit);
				}
			}
			{
				mnStudentInfo = new JMenu("학생정보(I)");
				mnStudentInfo.setMnemonic('I');
				menuBar.add(mnStudentInfo);
				{
					mnStudentInsert = new JMenuItem("학생정보 추가...");
					mnStudentInsert.setIcon(new ImageIcon(WinAdminMain.class.getResource("/image/memberAdd.png")));
					mnStudentInsert.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							WinStudentInsert winStudentInsert = new WinStudentInsert();
							winStudentInsert.setModal(true);
							winStudentInsert.setVisible(true);
						}
					});
					mnStudentInfo.add(mnStudentInsert);
				}
				{
					mnStudentDelete = new JMenuItem("학생정보 삭제...");
					mnStudentDelete.setIcon(new ImageIcon(WinAdminMain.class.getResource("/image/memberRemove.png")));
					mnStudentDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							String sName = JOptionPane.showInputDialog("삭제할 이름 입력하시오");
							if(sName!=null) {
								WinLookup winLookup = new WinLookup(sName);
								winLookup.setModal(true);
								winLookup.setVisible(true);
							}
						}
					});
					mnStudentInfo.add(mnStudentDelete);
				}
				{
					 mnStudentUpdate = new JMenuItem("학생정보 변경...");
					 mnStudentUpdate.setIcon(new ImageIcon(WinAdminMain.class.getResource("/image/memberUpdate.png")));
					mnStudentInfo.add(mnStudentUpdate);
				}
				{
					mnStudentSelect = new JMenuItem("학생정보 조회...");
					mnStudentSelect.setIcon(new ImageIcon(WinAdminMain.class.getResource("/image/memberSearch.png")));
					mnStudentSelect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							WinSearchWord winSearchWord = new WinSearchWord();
							winSearchWord.setModal(true);
							winSearchWord.setVisible(true);
						}
					});
					mnStudentInfo.add(mnStudentSelect);
				}
			}
			{
				JMenu mnNewMenu = new JMenu("학생성적(S)");
				mnNewMenu.setMnemonic('S');
				menuBar.add(mnNewMenu);
				{
					JMenuItem mnScoreInsert = new JMenuItem("성적입력...");
					mnScoreInsert.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							WinInsertScore winInsertScore = new WinInsertScore();
							winInsertScore.setModal(true);
							winInsertScore.setVisible(true);
						}
					});
					mnNewMenu.add(mnScoreInsert);
				}
			}
		}
		
		
		Component[] com = toolBar.getComponents();
		for(Component c : com) {
			if (c instanceof JButton) {
			
				ImageIcon icon = (ImageIcon)(((JButton) c).getIcon());
				Image img =icon.getImage();
				img =img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				icon = new ImageIcon(img);
				
				((JButton) c).setIcon(icon);
				
			}
		}
		
		Component[] comMenu = mnStudentInfo.getMenuComponents();
		for(Component c : comMenu) {
			if (c instanceof JMenuItem) {
				if((((JMenuItem)c).getIcon())!=null) {
				ImageIcon icon = (ImageIcon)(((JMenuItem)c).getIcon());
				Image img =icon.getImage();
				img =img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				icon = new ImageIcon(img);
				
				((JMenuItem) c).setIcon(icon);
				}
			}
		}
		
		
		
		
		//ShowTable(); // 컴포넌트를 모두 배치한 후에 테이블에서 레코드를 가져와 표에 넣는다.
	}
	
	protected void ShowTable(String text) {
		dtm.setRowCount(0); // reset
		ResultSet rs=null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================	
			if(text==null) {
				String sql = "select * from studentTBL";
				pstmt=con.prepareStatement(sql);			
				rs = pstmt.executeQuery();
			}
			else {
				String sql = "select * from studentTBL WHERE id like ? ";
				pstmt=con.prepareStatement(sql);	
				pstmt.setString(1, "%"+text+"%");
				rs = pstmt.executeQuery();
			}
			
			while(rs.next()) {  // 각 레코드의 필드들을 읽어서 벡터에 저장한 후, DefaultTableModel 에 추가한다.
				Vector<String> vector = new Vector<>();
				
				for(int i=1; i<=dtm.getColumnCount(); i++) {
					String temp = rs.getString(i);
					if(i == 4) {	// 전화번호 010-1111-1234 표현되게 변경
						String sJumin = temp.substring(0, 6);
						sJumin = sJumin + "-" + temp.substring(6);
						vector.add(sJumin);
					}else if(i == 3) {
						String sPhone = "";
						if(temp.substring(0,2).equals("02")) {							
							sPhone = temp.substring(0,2) + "-";
							sPhone = sPhone + temp.substring(2,temp.length()-4) + "-";
							sPhone = sPhone + temp.substring(temp.length()-4);
						}else {
							sPhone = temp.substring(0,3) + "-";
							sPhone = sPhone + temp.substring(3,temp.length()-4) + "-";
							sPhone = sPhone + temp.substring(temp.length()-4);
						}
						vector.add(sPhone);
					}
					else
						vector.add(temp);
				}
				dtm.addRow(vector);				
			}		
//			System.out.println(dtm.getRowCount());
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
