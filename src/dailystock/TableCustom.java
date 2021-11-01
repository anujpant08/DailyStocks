/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ANUJ
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailystock;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;

/**
 *
 * @author ANUJ
 */
public class TableCustom extends DefaultTableCellRenderer  {
    
    int clmn=0;
    
    TableCustom(int col)
    {
        clmn=col;
    }
    
    public Component getTableCellRendererComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus,
                                                 int row,
                                                 int column) {
    Component c = 
      super.getTableCellRendererComponent(table, value,
                                          isSelected, hasFocus,
                                          row, column);
        String s = "";
    if(table.getModel().getValueAt(row, column ) != null){
        s =  table.getModel().getValueAt(row, column ).toString().replaceAll("\\s+","").replaceAll(",", "");
    }else{
        s = "0.00";
    }

    //System.out.println(d);
    double d=Double.parseDouble(s);
    if(d<0)
    {
        c.setForeground(Color.red);
    }
    else if(d>0)
    {
        c.setForeground(Color.green);
    }
    else
        c.setForeground(Color.black);
    
    
    return c;
    
  }
    
}

