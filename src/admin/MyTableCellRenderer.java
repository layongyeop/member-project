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
	
	
	
	
	if(ob==null||column==0) { // 값이 없거나 첫행(시간)일경우 
		
		cell.setBackground(Color.white);
		
		
	}
	else if(ob.split("\\(").length>=3) {// 중복된 강의가 들어왔을경우
		
		
		
		cell.setBackground(Color.red);
		
		
	}
	else { // 하나의 강의 일경우
		
		cell.setBackground(Color.cyan);
	
	}
		

	
	
	return cell;
	
	}
 	

}