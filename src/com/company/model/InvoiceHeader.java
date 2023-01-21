package com.company.model;
import java.util.ArrayList;
import java.util.Date;

//Invoice header
public class InvoiceHeader {
    int invoiceNum;// Invoice Number
    Date invoiceDate;//Date of the invoice
    String customerName;//Customer name
    ArrayList<InvoiceLine> invoiceLines;// Array of the items on this invoice

    public InvoiceHeader(int invoiceNum,Date invoiceDate,String customerName,ArrayList<InvoiceLine> invoiceLines) {
        this.invoiceNum= invoiceNum;
        this.invoiceDate= invoiceDate;
        this.customerName= customerName;
        this.invoiceLines = invoiceLines;
    }
    public InvoiceHeader() {
        this.invoiceNum= 0;
        this.invoiceDate= new Date();
        this.customerName= "";
        this.invoiceLines = new ArrayList<InvoiceLine>();
    }
    public int getInvoiceNum() {
        return invoiceNum;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }


    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setInvoiceLines(ArrayList<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }
}
