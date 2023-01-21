import com.company.controller.FileOperations;
import com.company.model.InvoiceHeader;
import com.company.model.InvoiceLine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    //    Invoice1Num
//    {
//        Invoice1Date (DD/MM/YYYY), Customer1Name
//        Item1Name, Item1Price, Count1
//
//    }
    public static void main(String[] args) {
        FileOperations fileOperations = new FileOperations();
        ArrayList<InvoiceHeader> invoiceHeaderList = fileOperations.readFile();
        for (InvoiceHeader invoiceHeader:invoiceHeaderList
             ) {
            System.out.println("Invoice "+invoiceHeader.getInvoiceNum());
            System.out.println("{");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String invoiceDate = dateFormat.format(invoiceHeader.getInvoiceDate());
            System.out.println(invoiceDate+", "+invoiceHeader.getCustomerName());
            for (InvoiceLine invoiceLine:invoiceHeader.getInvoiceLines()
                 ) {
                System.out.println(invoiceLine.getItemName()+", "+invoiceLine.getItemPrice()+", "+invoiceLine.getItemQuantity());
            }
            System.out.println("}");
        }
        String input = "";

        do {// Header loop

            int invoiceNum=FileOperations.getCurrentInvoiceNumber()+1;
            FileOperations.updateCurrentInvoiceNumber(invoiceNum);
            Date invoiceDate= new Date();
            String customerName=getCustomerName();
            ArrayList<InvoiceLine> invoiceLineList= new ArrayList<InvoiceLine>();
            do {// Item loop
                String itemName=getItemName();
                double itemPrice= getItemPrice();
                int itemQuantity=getItemQuantity();
                InvoiceLine invoiceLine = new InvoiceLine(invoiceNum,itemName,itemPrice,itemQuantity);
                invoiceLineList.add(invoiceLine);
            }
            while(isContinueInputItem().equals("Y"));
            //Create new object to append in the csv file

            InvoiceHeader invoiceHeader= new InvoiceHeader(invoiceNum,invoiceDate,customerName,invoiceLineList);
            ArrayList<InvoiceHeader> invoiceHeaderListNew= new ArrayList<InvoiceHeader>();
            invoiceHeaderListNew.add(invoiceHeader);
            fileOperations.writeFile(invoiceHeaderListNew);

        }while(isContinueInputHeader().equals("Y"));

    }

    public static String isContinueInputHeader()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to enter another invoice? (Y/N)");
        return scanner.next();
    }
    public static String isContinueInputItem()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to enter another item? (Y/N)");
        return scanner.next();
    }
    public static String getCustomerName()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter customer name");
        return scanner.next();
    }
    public static String getItemName()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter item name");
        return scanner.next();
    }
    public static double getItemPrice()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter item price");
        return scanner.nextDouble();
    }
    public static int getItemQuantity()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter item quantity");
        return scanner.nextInt();
    }
}