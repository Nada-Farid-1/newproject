
package sig.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LineInputDialog extends JDialog{
    private JTextField itemName;
    private JTextField itemCount;
    private JTextField itemPrice;
    private JLabel nameLabel;
    private JLabel countLabel;
    private JLabel priceLabel;
    private JButton okButton;
    private JButton cancelButton;
    
    public LineInputDialog (InvoiceFrame frame)
    {
        itemName=new JTextField(20);
        nameLabel= new JLabel("  Item Name : ");
        
         itemCount=new JTextField(20);
         countLabel = new JLabel("  Item Couunt : ");
        
        itemPrice=new JTextField(20);
        priceLabel= new JLabel("  Item Price : ");
        
         okButton = new JButton("OK");
         cancelButton = new JButton("Cancel");
         
         okButton.setActionCommand("createLineOK");
         cancelButton.setActionCommand("cancelLine");
         okButton.addActionListener(frame.getController());
         cancelButton.addActionListener(frame.getController());
         setLayout(new GridLayout(4,2));
         setLocationRelativeTo(null);
         add(nameLabel);
         add(itemName);
         add(countLabel);
         add(itemCount);
         add(priceLabel);
         add(itemPrice);
         add(okButton);
         add(cancelButton);
         pack();
         
    }
    public JTextField getItemName()
    {
        return itemName;
    }
    public JTextField getItemCount()
    {
        return itemCount;
    }
    public JTextField getItemPrice()
    {
        return itemPrice;
    }
}
