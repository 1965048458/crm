package com.xuebei.crm.company;

import com.google.gson.annotations.Expose;

public class CompanyData {

    @Expose
    private int successRateA=0;

    @Expose
    private String coverageRateA="一";

    @Expose
    private String permeabilityA="0.00";

    @Expose
    private int successRateB=0;

    @Expose
    private String coverageRateB="0.00";

    @Expose
    private String permeabilityB="0.00";
    @Expose
    private int successRateC=0;

    @Expose
    private String coverageRateC="0.00";


    @Expose
    private String permeabilityC="0.00";

    @Expose
    private int successRateD=0;

    @Expose
    private String coverageRateD="0.00";

    @Expose
    private String permeabilityD="0.00";
     @Expose
     private int dCount=0;
    @Expose
    private double amount=0;
    public int getSuccessRateA() {
        return successRateA;
    }

    public void setSuccessRateA(int successRateA) {
        this.successRateA = successRateA;
    }

    public String getCoverageRateA() {
        return coverageRateA;
    }

    public void setCoverageRateA(String coverageRateA) {
        this.coverageRateA = coverageRateA;
    }

    public String getPermeabilityA() {
        return permeabilityA;
    }

    public void setPermeabilityA(String permeabilityA) {
        this.permeabilityA = permeabilityA;
    }

    public int getSuccessRateB() {
        return successRateB;
    }

    public void setSuccessRateB(int successRateB) {
        this.successRateB = successRateB;
    }

    public String getCoverageRateB() {
        return coverageRateB;
    }

    public void setCoverageRateB(String coverageRateB) {
        this.coverageRateB = coverageRateB;
    }

    public String getPermeabilityB() {
        return permeabilityB;
    }

    public void setPermeabilityB(String permeabilityB) {
        this.permeabilityB = permeabilityB;
    }

    public int getSuccessRateC() {
        return successRateC;
    }

    public void setSuccessRateC(int successRateC) {
        this.successRateC = successRateC;
    }

    public String getCoverageRateC() {
        return coverageRateC;
    }

    public void setCoverageRateC(String coverageRateC) {
        this.coverageRateC = coverageRateC;
    }

    public String getPermeabilityC() {
        return permeabilityC;
    }

    public void setPermeabilityC(String permeabilityC) {
        this.permeabilityC = permeabilityC;
    }

    public int getSuccessRateD() {
        return successRateD;
    }

    public void setSuccessRateD(int successRateD) {
        this.successRateD = successRateD;
    }

    public String getCoverageRateD() {
        return coverageRateD;
    }

    public void setCoverageRateD(String coverageRateD) {
        this.coverageRateD = coverageRateD;
    }

    public String getPermeabilityD() {
        return permeabilityD;
    }

    public void setPermeabilityD(String permeabilityD) {
        this.permeabilityD = permeabilityD;
    }

    public double getAmount() {
        return amount;
    }

    public int getdCount() {
        return dCount;
    }

    public void setdCount(int dCount) {
        this.dCount = dCount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CompanyData{" +
                "successRateA=" + successRateA +
                ", coverageRateA='" + coverageRateA + '\'' +
                ", permeabilityA='" + permeabilityA + '\'' +
                ", successRateB=" + successRateB +
                ", coverageRateB='" + coverageRateB + '\'' +
                ", permeabilityB='" + permeabilityB + '\'' +
                ", successRateC=" + successRateC +
                ", coverageRateC='" + coverageRateC + '\'' +
                ", permeabilityC='" + permeabilityC + '\'' +
                ", successRateD=" + successRateD +
                ", coverageRateD='" + coverageRateD + '\'' +
                ", permeabilityD='" + permeabilityD + '\'' +
                '}';
    }
}
