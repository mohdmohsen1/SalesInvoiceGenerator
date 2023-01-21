package com.company.controller;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.company.model.*;

//Class to help with file manipulation
public class FileOperations {
    private static final String headerFileName="InvoiceHeader.csv";
    private static final String itemFileName="InvoiceLine.csv";
    static int currentInvoiceNumber = 0;

    public static int getCurrentInvoiceNumber() {
        return currentInvoiceNumber;
    }

    public static void updateCurrentInvoiceNumber(int invoiceNum) {
        currentInvoiceNumber = invoiceNum;
    }

    public ArrayList<InvoiceHeader> readFile() {// Read invoice data from CSV file

        ArrayList<InvoiceHeader> invoiceHeaderList = new ArrayList<>();
        try {
            File fileHeader = getFileFromResource(headerFileName);
            File fileItem = getFileFromResource(itemFileName);
            Scanner scannerHeader = new Scanner(fileHeader);
            InvoiceHeader invoiceHeader;
            while (scannerHeader.hasNextLine()) {
                String[] headerLine = scannerHeader.nextLine().split(",");
                int invoiceNum = Integer.parseInt(headerLine[0]);
                Date invoiceDate = new Date();
                try {
                    new SimpleDateFormat("dd-MM-yyyy").parse(headerLine[1]);
                } catch (Exception e) {
                    System.out.println("Wrong date format " + e);
                }
                String customerName = headerLine[2];
                Scanner scannerItem = new Scanner(fileItem);
                ArrayList<InvoiceLine> invoiceLineList = new ArrayList<>();
                while (scannerItem.hasNextLine()) {
                    String[] itemLine = scannerItem.nextLine().split(",");
                    int invoiceNumber = Integer.parseInt(itemLine[0]);
                    if (invoiceNumber == invoiceNum) {
                        String itemName = itemLine[1];
                        double itemPrice = Double.parseDouble(itemLine[2]);
                        int itemQuantity = Integer.parseInt(itemLine[3]);
                        InvoiceLine invoiceLine = new InvoiceLine(invoiceNumber, itemName, itemPrice, itemQuantity);
                        invoiceLineList.add(invoiceLine);
                    }
                }
                invoiceHeader = new InvoiceHeader(invoiceNum, invoiceDate, customerName, invoiceLineList);
                invoiceHeaderList.add(invoiceHeader);
                currentInvoiceNumber++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();
        }
        return invoiceHeaderList;
    }

    public void writeFile(ArrayList<InvoiceHeader> invoiceHeaderList) {// Write invoice data to CSV file
        try {
            File fileHeader = getFileFromResource(headerFileName);
            File fileItem = getFileFromResource(itemFileName);
            FileWriter fileWriterHeader = new FileWriter(fileHeader.getPath(), true);
            for (InvoiceHeader invoiceHeader : invoiceHeaderList
            ) {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String invoiceDate = dateFormat.format(invoiceHeader.getInvoiceDate());
                fileWriterHeader.write(invoiceHeader.getInvoiceNum() + "," + invoiceDate + "," + invoiceHeader.getCustomerName() + "\n");
                FileWriter fileWriterItem = new FileWriter(fileItem.getPath(), true);
                for (InvoiceLine invoiceLine : invoiceHeader.getInvoiceLines())
                    fileWriterItem.write(invoiceLine.getInvoiceNum() + "," + invoiceLine.getItemName() + "," + invoiceLine.getItemPrice() + "," + invoiceLine.getItemQuantity() + "\n");
                fileWriterItem.close();
            }
            fileWriterHeader.close();
        } catch (Exception e) {
            System.out.println("File not Found : " + e.getMessage());
        }
    }

    public static void print(ArrayList<InvoiceHeader> invoiceHeaderList) {
        try {
            for (InvoiceHeader invoiceHeader : invoiceHeaderList) {
                System.out.println("Invoice " + invoiceHeader.getInvoiceNum());
                System.out.println("{");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String invoiceDate = dateFormat.format(invoiceHeader.getInvoiceDate());
                System.out.println(invoiceDate + ", " + invoiceHeader.getCustomerName());
                for (InvoiceLine invoiceLine : invoiceHeader.getInvoiceLines()) {
                    System.out.println(invoiceLine.getItemName() + ", " + invoiceLine.getItemPrice() + ", " + invoiceLine.getItemQuantity());
                }
                System.out.println("}");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private File getFileFromResource(String fileName) {
        File file = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(".//resources//" + fileName);
            if (resource != null) {
                file = new File(resource.toURI());
            }
        } catch (URISyntaxException e) {
            System.out.println("File not found : " + e.getMessage());
        }
        return file;
    }
}