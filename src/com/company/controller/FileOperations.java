package com.company.controller;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.company.model.*;

//Class to help with file manipulation
public class FileOperations {
    static int currentInvoiceNumber = 0;
    String pathHeader = "D:\\Testing Course\\Code\\Sales Invoice Generator\\src\\resources\\InvoiceHeader.csv";
    String pathItem = "D:\\Testing Course\\Code\\Sales Invoice Generator\\src\\resources\\InvoiceLine.csv";

    public static int getCurrentInvoiceNumber() {
        return currentInvoiceNumber;
    }

    public static void updateCurrentInvoiceNumber(int invoiceNum) {
        currentInvoiceNumber = invoiceNum;
    }

    public ArrayList<InvoiceHeader> readFile() {// Read invoice data from CSV file

        ArrayList<InvoiceHeader> invoiceHeaderList = new ArrayList<InvoiceHeader>();
        try {
            Scanner scannerHeader = new Scanner(new File(pathHeader));
            InvoiceHeader invoiceHeader = null;
            while (scannerHeader.hasNextLine()) {
                String[] headerLine = scannerHeader.nextLine().split(",");
                int invoiceNum = Integer.valueOf(headerLine[0]);
                Date invoiceDate = new SimpleDateFormat("dd-MM-yyyy").parse(headerLine[1]);
                String customerName = headerLine[2];
                Scanner scannerItem = new Scanner(new File(pathItem));
                ArrayList<InvoiceLine> invoiceLineList = new ArrayList<InvoiceLine>();
                while (scannerItem.hasNextLine()) {
                    String[] itemLine = scannerItem.nextLine().split(",");
                    int invoiceNumber = Integer.valueOf(itemLine[0]);
                    if (invoiceNumber == invoiceNum) {
                        String itemName = itemLine[1];
                        double itemPrice = Double.valueOf(itemLine[2]);
                        int itemQuantity = Integer.valueOf(itemLine[3]);
                        InvoiceLine invoiceLine = new InvoiceLine(invoiceNumber, itemName, itemPrice, itemQuantity);
                        invoiceLineList.add(invoiceLine);
                    }
                }
                invoiceHeader = new InvoiceHeader(invoiceNum, invoiceDate, customerName, invoiceLineList);
                invoiceHeaderList.add(invoiceHeader);
                currentInvoiceNumber++;
            }


        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            return invoiceHeaderList;
        }
    }

    public void writeFile(ArrayList<InvoiceHeader> invoiceHeaderList) {// Write invoice data to CSV file
        try {


            FileWriter fileWriterHeader = new FileWriter(pathHeader, true);
            for (InvoiceHeader invoiceHeader : invoiceHeaderList
            ) {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String invoiceDate = dateFormat.format(invoiceHeader.getInvoiceDate());

                fileWriterHeader.write( invoiceHeader.getInvoiceNum() + "," + invoiceDate + "," + invoiceHeader.getCustomerName());
                FileWriter fileWriterItem = new FileWriter(pathItem, true);
                for (InvoiceLine invoiceLine : invoiceHeader.getInvoiceLines()
                ) {
                    fileWriterItem.write( invoiceLine.getInvoiceNum() + "," + invoiceLine.getItemName() + "," + invoiceLine.getItemPrice() + "," + invoiceLine.getItemQuantity());
                }
                fileWriterItem.close();
            }
            fileWriterHeader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}