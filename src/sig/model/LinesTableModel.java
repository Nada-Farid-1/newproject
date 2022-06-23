package sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class LinesTableModel extends  AbstractTableModel{
    
    private ArrayList<InvoiceLine> lines;
    private String[] col ={"No. ","Item Name ","Item Price ","Item Total Price "};
    public LinesTableModel(ArrayList<InvoiceLine> lines)
    {
        this.lines=lines;
    }
   
    public ArrayList<InvoiceLine> getlines()
    {
        return lines;
    }
    
    @Override
    public int getRowCount() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    return lines.size();
    }

    @Override
    public int getColumnCount() {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    return col.length;
    }
    @Override
    public String getColumnName(int i)
    {
        return col[i];
    }
    @Override
    public Object getValueAt(int rowindex ,int colIndex) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    InvoiceLine line = lines.get(rowindex);
    switch(colIndex)
    {
       case 0 :return line.getInvoice().getNum();
       case 1 :return line.getName();
       case 2 :return line.getPrice();
       case 3 :return line.getCount();
       case 4: return line.getLineTotal();
               default: return "";
    }
    }
    
}
