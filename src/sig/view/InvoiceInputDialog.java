package sig.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceInputDialog extends JDialog{
    private JTextField customerName;
    private JTextField invDate;
    private JLabel nameLabel;
    private JLabel dateLabel;
    private JButton okButton;
    private JButton cancelButton;
    
    public InvoiceInputDialog (InvoiceFrame frame)
    {
        nameLabel =new JLabel(" Customer Name : ");
        customerName = new JTextField(20);
        dateLabel = new JLabel(" Invoice Date : ");
        invDate = new JTextField(20);
        okButton = new JButton("OK");
        cancelButton =new JButton("Cancel");
        okButton.setActionCommand("createInvocieOk");
        cancelButton.setActionCommand("cancelInvoice");
        okButton.addActionListener(frame.getController());
        cancelButton.addActionListener(frame.getController());
        setLayout(new GridLayout(3,2));
        setLocationRelativeTo(null);
        add(nameLabel);
        add(customerName);
        add(dateLabel);
        add(invDate);
        add(okButton);
        add(cancelButton);
        pack();
        
    }
    public JTextField getCustomerName()
    {
        return customerName;
    }
    public JTextField getinvDate()
    {
        return invDate;
    }
    
}
