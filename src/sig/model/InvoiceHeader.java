package sig.model;
import java.util.ArrayList;
//import java.util.Date;
public class InvoiceHeader {
    private int num;
    private String Customer;
   // private Date Date;
    private String Date;
    private ArrayList<InvoiceLine> Line;
    
    //Double total
    public InvoiceHeader() {
    }
    public InvoiceHeader(int num, String Customer, String Date) {
        this.num = num;
        this.Customer = Customer;
        this.Date = Date;
    }

    
public double getInvoiceTotal() {
        double total = 0.0;
        for (InvoiceLine InvoiceLine : getLines()) {
            total += InvoiceLine.getLineTotal();
        }
        return total;
    }
    
    public ArrayList<InvoiceLine> getLines() {
       if (Line == null) {
            Line = new ArrayList<>();
        }
        return Line;
    }
    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String Customer) {
        this.Customer = Customer;
    }

    

    public void setLine(ArrayList<InvoiceLine> Line) {
        this.Line = Line;
    }
    
    
    @Override
    public String toString() {
        return "Invoice{" + "num=" + num + ", date=" + Date + ", customer=" + Customer + '}';
    }
    
    public String getAsCSV() {
        return num + "," + Date + "," + Customer;
    }
}
