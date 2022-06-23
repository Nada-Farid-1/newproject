package sig.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.TableView;
import sig.model.InoviceTableModel;
import sig.model.InvoiceHeader;
import sig.model.InvoiceLine;
import sig.model.LinesTableModel;
import sig.view.InvoiceFrame;
import sig.view.InvoiceInputDialog;
import sig.view.LineInputDialog;

public class ActionHandler implements ActionListener,ListSelectionListener{
private InvoiceFrame frame;
private InvoiceInputDialog invoiceDialog;
private LineInputDialog lineDialog;
public ActionHandler(InvoiceFrame frame) {
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //Add Actions
        String ActionCommand = ae.getActionCommand();
        //Print to trace the actions 
        switch (ActionCommand) {
            case "Load File ":
                loadFile();
                break;   
            case "Save File":
                saveFile();
                break;
                
            case "Create New Invoice ":
                createNewInvoice();
                break;
                
            case "Delete Invoice ":
                deleteInvoice();
                break;
            case "Save New item":
                createNewItem();
                break;
            case "Delete  Item":
                deleteItem();
                break;
            case "cancelInvoice":
                cancelInvoice();
                break;
            case "createInvocieOk":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "cancelLine":
                cancelLine();
                break;
               
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            InvoiceHeader currentInvoice = frame.getInvoices().get(selectedIndex);
            frame.getInvoiceNumLabel().setText("" + currentInvoice.getNum());
            frame.getInvoiceDateLabel().setText(currentInvoice.getDate());
            frame.getCustomerNameLabel().setText(currentInvoice.getCustomer());
            frame.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            LinesTableModel linesTableModel = new LinesTableModel(currentInvoice.getLines());
      
           frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        } 
    }

    private void loadFile() {
    JFileChooser fc = new JFileChooser();        
        try {
                          /*  loadindication=false;
                            System.out.println(loadindication);*/
            int result = fc.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());

                List<String> headerLines = Files.readAllLines(headerPath,StandardCharsets.UTF_8);
                ArrayList<InvoiceHeader> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];

                        InvoiceHeader invoice = new InvoiceHeader(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                result = fc.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath,StandardCharsets.UTF_8);
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoiceNum = Integer.parseInt(lineParts[0]);
                            String itemName = lineParts[1];
                            double itemPrice = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            InvoiceHeader inv = null;
                            for (InvoiceHeader invoice : invoicesArray) {
                                if (invoice.getNum() == invoiceNum) {
                                    inv = invoice;
                                    break;
                                }
                            }
                        //    InvoiceLine lin1 = new InvoiceLine(item,price ,inv,c);
                            InvoiceLine line = new InvoiceLine(inv,itemName,count,itemPrice);
                            inv.getLines().add(line);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                frame.setInvoices(invoicesArray);
                InoviceTableModel invoicesTableModel = new InoviceTableModel(invoicesArray);
                frame.setInvoicesTableModel(invoicesTableModel);
                frame.getInvoiceTable().setModel(invoicesTableModel);
                frame.getInovicesTableModel().fireTableDataChanged();
                /*loadindication=true;
                        System.out.println(loadindication);*/
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }

    private void saveFile() {
     ArrayList<InvoiceHeader> invoices = frame.getInvoices();
        String headers = "";
        String lines = "";
        for (InvoiceHeader invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (InvoiceLine line : invoice.getLines()) {
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoice() {
      invoiceDialog = new InvoiceInputDialog(frame);
        int x=frame.getInvoiceTable().getModel().getRowCount();
       // String y =frame.getInvoiceTable().getModel().getValueAt(0, 0).toString();
      //  String y = frame.getInovicesTableModel().getValueAt(0,0).toString();
        //System.out.println(y);
        
         if ( x>0)
         {
             invoiceDialog.setVisible(true);
         }else
         {
             JOptionPane.showMessageDialog(frame,"Please load the CSV Files for Headers and lines for the invoices has data in the first and select one of them to show ","Invoice ",JOptionPane.ERROR_MESSAGE);
         }
         
    }

    private void deleteInvoice() {
     int selectedRow = frame.getInvoiceTable().getSelectedRow();
        int x=frame.getInvoiceTable().getModel().getRowCount();
        if ((x>0) ){
        if ((selectedRow != -1)) {
            frame.getInvoices().remove(selectedRow);
            frame.getInovicesTableModel().fireTableDataChanged();
        }
        }
        else
         {
           JOptionPane.showMessageDialog(frame,"Please load the CSV Files for Headers and lines for the invoices in the first and select one of them to show ","Invoice ",JOptionPane.ERROR_MESSAGE); 
         
         }
        
       // System.out.println(x);
         
    }

    private void createNewItem() {
     lineDialog = new LineInputDialog(frame);
        int rowOfInvoiceTable=frame.getInvoiceTable().getModel().getRowCount();
        //int rowOflineTable = frame.getLineTable().getModel().getRowCount();
        //System.out.println(z);
        //System.out.println(rowOfInvoiceTable+""+rowOflineTable);
         if ((rowOfInvoiceTable>0) )
         {
           lineDialog.setVisible(true);
           
          // frame.getInvoiceTotalLabel();
          // frame.getCustomerNameLabel();
         }
         else
         {
           JOptionPane.showMessageDialog(frame,"Please load the CSV Files for Headers and lines for the invoices in the first and select one of them to show ","Invoice ",JOptionPane.ERROR_MESSAGE); 
         
         }
    }

    private void deleteItem() {
      int selectedRow = frame.getLineTable().getSelectedRow();
int x=frame.getInvoiceTable().getModel().getRowCount();
       // int y = frame.getLineTable().getModel().getRowCount();
       // System.out.println(x+""+y);
         if ((x>0) )
         {
        if ((selectedRow != -1) ){
            LinesTableModel linesTableModel = (LinesTableModel) frame.getLineTable().getModel();
            linesTableModel.getlines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frame.getInovicesTableModel().fireTableDataChanged();
        }
         }else
         {
           JOptionPane.showMessageDialog(frame,"Please load the CSV Files for Headers and lines for the invoices in the first and select one of them to show ","Invoice ",JOptionPane.ERROR_MESSAGE); 
         
         }
    }

    private void cancelInvoice() {
     invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createInvoiceOK() {
      String date = invoiceDialog.getinvDate().getText();
             
        String customer = invoiceDialog.getCustomerName().getText();
        int num = frame.getNextInvoiceNum();
        try {
            String[] dateParts = date.split("-");  // "22-05-2013" -> {"22", "05", "2013"}  xy-qw-20ij
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                Calendar cal = Calendar.getInstance();
                Date d=new Date();  
                //int actualyear =(d.getYear())+1900;
                //int actualMonth =d.getMonth()+1;
                //int actualDay = d.getDay()+19;
                int CurrentDay =cal.get(Calendar.DAY_OF_MONTH);
                int CurrentMonth =(cal.get(Calendar.MONTH)+1);
                int CurrentYear =cal.get(Calendar.YEAR);

               // System.out.println("*******************************"+day5+"*******"+year5+"*********"+month5);
                if (((day > 31)|| (day>CurrentDay)) || ((month > 12)|| (month >CurrentMonth) ) || year>CurrentYear ){
                    JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    InvoiceHeader invoice = new InvoiceHeader(num, customer, date);
                    frame.getInvoices().add(invoice);
                    frame.getInovicesTableModel().fireTableDataChanged();
                    invoiceDialog.setVisible(false);
                    invoiceDialog.dispose();
                    invoiceDialog = null;
                    
                    
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    

    private void createLineOK() {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     String item = lineDialog.getItemName().getText();
        String countStr = lineDialog.getItemCount().getText();
        String priceStr = lineDialog.getItemPrice().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvoiceHeader invoice = frame.getInvoices().get(selectedInvoice);
            InvoiceLine line = new InvoiceLine(invoice, item, count, price);
            invoice.getLines().add(line);
            LinesTableModel linesTableModel = (LinesTableModel) frame.getLineTable().getModel();
            //linesTableModel.getLines().add(line);
            
            linesTableModel.fireTableDataChanged();
            frame.getInovicesTableModel().fireTableDataChanged();
          //invoice.getInvoiceTotal();
          //  frame.getInvoiceTotalLabel().updateUI();
          // frame.getInvoiceTotalLabel().firePropertyChange(item, count, count);
                                        //frame.getInvoiceDateLabel().updateUI();

        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void cancelLine() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
    
}
