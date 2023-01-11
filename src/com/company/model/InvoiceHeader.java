package com.company.model;
import java.util.ArrayList;
import java.util.Date;

//Invoice header
public class InvoiceHeader {
    int invoiceNum;// Invoice Number
    Date invoiceDate;//Date of the invoice
    String customerName;//Customer name
    ArrayList<InvoiceLine> invoiceLines;// Array of the items on this invoice
}
