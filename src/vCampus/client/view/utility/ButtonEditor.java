package vCampus.client.view.utility;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import vCampus.client.view.*;
public class ButtonEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;
	public JButton button;
	private String label;
	private boolean isPushed;
	private Integer courseID;

	public ButtonEditor(JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.setText("点击修改");
		button.setFont(new Font("微软雅黑", Font.BOLD, 20));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//点击之后重新渲染，此步不可省略
				fireEditingStopped();
				Object[][] objectGrade ={
						{11,"小米",null},
						{22,"Uzi",null},
						{33,"Jimi",null}
				};
			    GradeListFrame jf = new GradeListFrame( objectGrade, courseID);
				
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		isPushed = true;
		courseID = TeacherGradeTable.getCourseId(row);
		return button;
	}

	public Object getCellEditorValue() {
		if (isPushed) {
		}
		isPushed = false;
		return "开始修改";
	}

	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public Integer getCourseID() {
		return courseID;
	}

	public void setCourseID(Integer courseID) {
		this.courseID = courseID;
	}
	
	
}
