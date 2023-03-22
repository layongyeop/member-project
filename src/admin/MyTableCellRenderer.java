package admin;
import java.awt.Color;
import java.awt.Component;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableCellRenderer extends DefaultTableCellRenderer {

	





 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

	Component cell =super.getTableCellRendererComponent(table, value, false, false, row, column);
	cell.setFocusable(false);
	
	String ob = (String) table.getValueAt(row, column);
	
	
	
	
	if(ob==null||column==0) { // ���� ���ų� ù��(�ð�)�ϰ�� 
		
		cell.setBackground(Color.white);
		
		
	}
	else if(ob.split("\\(").length>=3) {// �ߺ��� ���ǰ� ���������
		
		
		
		cell.setBackground(Color.red);
		
		
	}
	else { // �ϳ��� ���� �ϰ��
		
		cell.setBackground(Color.cyan);
	
	}
		

	
	
	return cell;
	
	}
 	

}