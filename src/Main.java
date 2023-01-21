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
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        FileOperations fileOperations = new FileOperations();
        ArrayList<InvoiceHeader> invoiceHeaderList = fileOperations.readFile();//Read from CSV file
        FileOperations.print(invoiceHeaderList);// Method to test reading from CSV
        if (isContinueInputHeader()) {
            String input = "";

            do {// Header loop

                int invoiceNum = FileOperations.getCurrentInvoiceNumber() + 1;//Get invoice number for new invoice
                FileOperations.updateCurrentInvoiceNumber(invoiceNum);//Update current invoice number
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                Date invoiceDate = today.getTime();//Get date.now
                String customerName = getCustomerName();// Get customer name from user
                ArrayList<InvoiceLine> invoiceLineList = new ArrayList<InvoiceLine>();

                do {// Item loop
                    String itemName = getItemName();// Get item name from user
                    double itemPrice = getItemPrice();// Get item price from user
                    int itemQuantity = getItemQuantity();// Get item quantity from User
                    InvoiceLine invoiceLine = new InvoiceLine(invoiceNum, itemName, itemPrice, itemQuantity);
                    invoiceLineList.add(invoiceLine);//Add to parent object
                }
                while (isContinueInputItem());

                //Create new object to append in the csv file
                InvoiceHeader invoiceHeader = new InvoiceHeader(invoiceNum, invoiceDate, customerName, invoiceLineList);
                ArrayList<InvoiceHeader> invoiceHeaderListNew = new ArrayList<InvoiceHeader>();
                invoiceHeaderListNew.add(invoiceHeader);//Add to parent object
                fileOperations.writeFile(invoiceHeaderListNew);//Save/update CSV file.
            }
            while (isContinueInputHeader());
        }
    }
    public static boolean isContinueInputHeader() {
        String input = "";
        boolean isContinue=false;
        boolean isOK = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to enter another invoice? (Y/N)");
        do {
            input = scanner.next();
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
                isOK = true;
                if (input.equalsIgnoreCase("Y")) {
                    isContinue = true;
                }
            }
            else
                System.out.println("Input incorrect,Do you want to enter another invoice? (Y/N)");
        } while (!isOK);
        return isContinue;
    }
    public static boolean isContinueInputItem()
    {
        String input = "";
        boolean isContinue=false;
        boolean isOK = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to enter another item? (Y/N)");
        do {
            input = scanner.next();
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
                isOK = true;
                if (input.equalsIgnoreCase("Y")) {
                    isContinue = true;
                }
            }
            else
                System.out.println("Input incorrect,Do you want to enter another item? (Y/N)");
        } while (!isOK);
        return isContinue;
    }
    public static String getCustomerName() {
        String input = "";
        boolean isOK = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter customer name");
        do {
            input = scanner.next();
            if (!input.equals("")) {
                isOK = true;
            } else
                System.out.println("Input incorrect,Please enter customer name.");
        } while (!isOK);
        return input;
    }
    public static String getItemName()
    {
        String input = "";
        boolean isOK = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter item name");
        do {
            input = scanner.next();
            if (!input.equals("")) {
                isOK = true;
            } else
                System.out.println("Input incorrect,Please enter item name.");
        } while (!isOK);
        return input;
    }
    public static double getItemPrice() {
        double input = 0;
        boolean isOK = false;
        System.out.println("Please enter item price");
        do {
            try {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextDouble();
                if (input > 0)
                    isOK = true;
                else
                    System.out.println("Input incorrect,Please enter item price.");
            } catch (InputMismatchException e) {
                System.out.println("Input incorrect,Please enter item price.");
            }
        } while (!isOK);
        return input;
    }
    public static int getItemQuantity()
    {
        int input = 0;
        boolean isOK = false;
        System.out.println("Please enter item quantity");
        do {
            try {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                if (input > 0)
                    isOK = true;
                else
                    System.out.println("Input incorrect,Please enter item quantity.");
            } catch (InputMismatchException e) {
                System.out.println("Input incorrect,Please enter item quantity.");
            }
        } while (!isOK);
        return input;
    }
}