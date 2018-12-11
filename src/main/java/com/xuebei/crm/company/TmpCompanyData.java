package com.xuebei.crm.company;

public class TmpCompanyData {
    private int id;
    private String status;
    private String newStatus;
    private  Double amount;

    public int getId() {
        return id;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TmpCompanyData{" +
                "status='" + status + '\'' +
                ", newStatus='" + newStatus + '\'' +
                '}';
    }
}
