package sig.model;
public class InvoiceLine {

    private InvoiceHeader Invoice;
    private String Name;
    private int Count;
    private double price;
    
    public InvoiceLine() {
    }
    public InvoiceLine(InvoiceHeader Invoice, String Name, int Count, double price) {
        this.Invoice = Invoice;
        this.Name = Name;
        this.Count = Count;
        this.price = price;
    }
    public double getLineTotal() {
        return price * Count;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public InvoiceHeader getInvoice() {
        return Invoice;
    }

    public void setInvoice(InvoiceHeader Invoice) {
        this.Invoice = Invoice;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }
    
    
    @Override
    public String toString() {
        return "Line{" + "num=" + Invoice.getNum() + ", item=" + Name + ", price=" + price + ", count=" + Count + '}';
    }
    public String getAsCSV() {
        return Invoice.getNum() + "," + Name + "," + price + "," + Count;
    }
}
