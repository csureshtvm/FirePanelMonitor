package com.vision.alarmmonitor.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import com.vision.alarmmonitor.dto.AlarmDevicesDto;
import com.vision.alarmmonitor.dto.AlarmEventDTO;
import com.vision.alarmmonitor.monitor.AlarmMonitor;
import com.vision.alarmmonitor.util.WSCommUtil;

/**
 *  The ButtonColumn class provides a renderer and an editor that looks like a
 *  JButton. The renderer and editor will then be used for a specified column
 *  in the table. The TableModel will contain the String to be displayed on
 *  the button.
 *
 *  The button can be invoked by a mouse click or by pressing the space bar
 *  when the cell has focus. Optionally a mnemonic can be set to invoke the
 *  button. When the button is invoked the provided Action is invoked. The
 *  source of the Action will be the table. The action command will contain
 *  the model row number of the button that was clicked.
 *
 */
public class ButtonColumn extends AbstractCellEditor
    implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener
{
    private JTable table;
    private Action action;
    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;

    private JButton renderButton;
    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;
    TableColumnModel columnModel;
    private String buttonLabel=null;
    String alarmType;
    /**
     *  Create the ButtonColumn to be used as a renderer and editor. The
     *  renderer and editor will automatically be installed on the TableColumn
     *  of the specified column.
     *
     *  @param table the table containing the button renderer/editor
     *  @param action the Action to be invoked when the button is invoked
     *  @param column the column to which the button renderer/editor is added
     */
    public ButtonColumn(JTable table, Action action, int column,String label,String alarmType)
    {
        this.table = table;
        this.action = action;
        this.alarmType=alarmType;
        renderButton = new JButton();
        renderButton.setForeground(Color.BLUE);
        editButton = new JButton();
       // editButton.setFocusPainted( false );
        editButton.addActionListener( this );
        //editButton.setBorderPainted(false);
       editButton.setFocusPainted(false);
       // editButton.setContentAreaFilled(false);
        editButton.setForeground(Color.BLACK);
        editButton.setBackground(Color.BLUE);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        editButton.setBorder(compound);
        this.buttonLabel=label;
        
        originalBorder = editButton.getBorder();
        setFocusBorder( new LineBorder(Color.BLUE) );

        initializeTableModel(table,column);
        
    }


    /**
     *  Get foreground color of the button when the cell has focus
     *
     *  @return the foreground color
     */
    public Border getFocusBorder()
    {
        return focusBorder;
    }

    /**
     *  The foreground color of the button when the cell has focus
     *
     *  @param focusBorder the foreground color
     */
    public void setFocusBorder(Border focusBorder)
    {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public int getMnemonic()
    {
        return mnemonic;
    }

    /**
     *  The mnemonic to activate the button when the cell has focus
     *
     *  @param mnemonic the mnemonic
     */
    public void setMnemonic(int mnemonic)
    {
        this.mnemonic = mnemonic;
        renderButton.setMnemonic(mnemonic);
        editButton.setMnemonic(mnemonic);
    }

    @Override
    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column)
    {
        /*if (value == null)
        {
            editButton.setText( "" );
            editButton.setIcon( null );
        }
        else if (value instanceof Icon)
        {
            editButton.setText( "" );
            editButton.setIcon( (Icon)value );
        }
        else
        {
            editButton.setText( value.toString() );
            editButton.setIcon( null );
        }*/
    	editButton.setForeground(Color.BLUE);
    	editButton.setText("<HTML><U>"+buttonLabel+"</U></HTML>");
    	editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	editButton.setBorderPainted(false);
    	editButton.setOpaque(true);
    	editButton.setBackground(Color.LIGHT_GRAY);
    	editButton.setBackground(Color.WHITE);
    	//editButton.setHorizontalAlignment(SwingConstants.LEFT);
        this.editorValue = value;
        return editButton;
    }

    @Override
    public Object getCellEditorValue()
    {
        return editorValue;
    }

//
//  Implement TableCellRenderer interface
//
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
    	//System.out.println("----------------------------------------------------");;
       /* if (isSelected)
        {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        }
        else
        {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus)
        {
            renderButton.setBorder( focusBorder );
        }
        else
        {
            renderButton.setBorder( originalBorder );
        }


        if (value == null)
        {
            renderButton.setText( "" );
            renderButton.setIcon( null );
        }
        else if (value instanceof Icon)
        {
            renderButton.setText( "" );
            renderButton.setIcon( (Icon)value );
        }
        else
        {
            renderButton.setText( value.toString() );
            renderButton.setIcon( null );
        }*/
        renderButton.setForeground(Color.BLUE);
        renderButton.setText("<HTML><U>"+buttonLabel+"</U></HTML>");
        renderButton.setBorderPainted(false);
        renderButton.setBackground(Color.WHITE);
        renderButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //renderButton.setOpaque(true);
        //renderButton.setOpaque(false);
        renderButton.setBackground(Color.LIGHT_GRAY);
       // renderButton.setHorizontalAlignment(SwingConstants.LEFT);
        return renderButton;
    }

//
//  Implement ActionListener interface
//
    /*
     *  The button has been pressed. Stop editing and invoke the custom Action
     */
    public void actionPerformed(ActionEvent e)
    {
    	if("Reset".equalsIgnoreCase(buttonLabel)){
    	try{
    		
       int row = table.convertRowIndexToModel( table.getEditingRow() );
       AlarmEventDTO alarmEventDTO=null;
       
       if("fire".equalsIgnoreCase(alarmType)){
    	   if(AlarmMonitorMainUI.alarmEventUI.fireAlarmTabUI.alarmEventList.size()>=row){
        	   alarmEventDTO=AlarmMonitorMainUI.alarmEventUI.fireAlarmTabUI.alarmEventList.get(row);
           }
       }else{
    	   if(AlarmMonitorMainUI.alarmEventUI.alarmOtherTabUI.alarmEventList.size()>=row){
        	   alarmEventDTO=AlarmMonitorMainUI.alarmEventUI.alarmOtherTabUI.alarmEventList.get(row);
           }
       }
      
        if(alarmEventDTO!=null){
        	AlarmMonitorMainUI.alarmResetUI.setAlwaysOnTop(true);
        	AlarmMonitorMainUI.alarmResetUI.setVisible(true);
        	AlarmMonitorMainUI.alarmResetUI.alarmEventId=alarmEventDTO.getAlarmEventId();
        	/*
        	String confirmMessage="Are You sure to reset Alarm with Details : Alarm Type - "+alarmEventDTO.getEventType()+" , Generated Time - "+alarmEventDTO.getEventGeneratedTime();
        	
        	int response=JOptionPane.showConfirmDialog(null, confirmMessage,"Reset Confirmation",JOptionPane.YES_NO_OPTION);
        	if(response==JOptionPane.YES_OPTION){
        		fireEditingStopped();
        		WSCommUtil.resetAlarm(alarmEventDTO.getAlarmEventId());
        		AlarmMonitor.getInstance().lastAlarmEventRefreshTime=0;
        		JOptionPane.showMessageDialog(null, "Alarm Resetted Successfully");
        		
        	}*/
        	
        }
     
    	}
    	catch(Exception  ex){
    		ex.printStackTrace();
    	}
    	}
    	else if("Edit".equalsIgnoreCase(buttonLabel)){
    		AlarmDevicesDto devDTO=null;
    		int row = table.convertRowIndexToModel( table.getEditingRow() );
    		 if(AlarmMonitorMainUI.alarmDevStatusUI.alarmDevicestList.size()>=row){
    			 devDTO=AlarmMonitorMainUI.alarmDevStatusUI.alarmDevicestList.get(row);
    			 AlarmDevicesEditUI alarmDevEditUI=new AlarmDevicesEditUI(devDTO.getBuildingId(),devDTO.getDeviceId(),devDTO.getDeviceName(),devDTO.getBuildingName(),devDTO.getDeviceLoc(),devDTO.getInstalledFloorNo());
    			 alarmDevEditUI.setVisible(true);
    			 alarmDevEditUI.setAlwaysOnTop(true);
    			 
    		 }
    		
    	}
    }

//
//  Implement MouseListener interface
//
    /*
     *  When the mouse is pressed the editor is invoked. If you then then drag
     *  the mouse to another cell before releasing it, the editor is still
     *  active. Make sure editing is stopped when the mouse is released.
     */
    public void mousePressed(MouseEvent e)
    {
        if (table.isEditing()
        &&  table.getCellEditor() == this)
            isButtonColumnEditor = true;
    }

    public void mouseReleased(MouseEvent e)
    {
    	try{
        if (isButtonColumnEditor
        &&  table.isEditing())
            table.getCellEditor().stopCellEditing();

        isButtonColumnEditor = false;
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    public void initializeTableModel(JTable table,int column){
    	columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer( this );
        columnModel.getColumn(column).setCellEditor( this );
        table.addMouseListener( this );
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
