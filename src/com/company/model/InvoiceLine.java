package com.company.model;
// Invoice lines of the invoice
public class InvoiceLine {

    int invoiceNum;// Invoice Number
    String itemName;// Item Name
    double itemPrice;// Item price
    int itemQuantity;//number of items purchased


    public InvoiceLine(int invoiceNum,String itemName,double itemPrice,int itemQuantity) {
        this.invoiceNum=invoiceNum;
        this.itemName = itemName;
        this.itemPrice= itemPrice;
        this.itemQuantity=itemQuantity;
    }
    public int getInvoiceNum() {
        return invoiceNum;
    }
    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }
}
