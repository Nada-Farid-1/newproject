package sig.model;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class InoviceTableModel extends AbstractTableModel{
    private ArrayList<InvoiceHeader> invoces;
    private String[] col = {"No. ","Date ","Cutomer ","Total "};
    
    public InoviceTableModel(ArrayList<InvoiceHeader> invoces) {
        this.invoces = invoces;
    }
    @Override
    
    public int getRowCount() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //invoces.lastIndexOf(col);
    return  invoces.size();
    }

    @Override
    public int getColumnCount() {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    return col.length;
    }
    @Override
    public String getColumnName(int colum)
    {
        return col[colum];
    }

    @Override
    public Object getValueAt(int rowIndex, int colndex) {
      // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    InvoiceHeader invoice = invoces.get(rowIndex);
    switch (colndex)
    {
        case 0 :return invoice.getNum();
        case 1 :return invoice.getDate();
       case 2 :return invoice.getCustomer();
           case 3 :return invoice.getInvoiceTotal();
               default: return "";
    }
    }
}
