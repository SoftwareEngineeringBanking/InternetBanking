package br.com.xurebinhaBanking.accounts;

public class AccountType {
    private String nameAccountType;
    private int numberOfTransferFree;
    private int daysCountForTransfer;
    private int valuePerTransfer;
    private int limitWeekendDays;
    private int limitNormalDays;

    public String getNameAccountType() {
        return nameAccountType;
    }
    public void setNameAccountType(String nameAccountType) {
        this.nameAccountType = nameAccountType;
    }

    public int getNumberOfTransferFree(){
        return numberOfTransferFree;
    }
    public void setNumberOfTransferFree(int numberOfTransferFree){
        this.numberOfTransferFree = numberOfTransferFree;
    }

    public int getDaysCountForTransfer() {
        return daysCountForTransfer;
    }
    public void setDaysCountForTransfer(int daysCountForTransfer) {
        this.daysCountForTransfer = daysCountForTransfer;
    }

    public int getValuePerTransfer() {
        return valuePerTransfer;
    }
    public void setValuePerTransfer(int valuePerTransfer) {
        this.valuePerTransfer = valuePerTransfer;
    }

    public int getLimitWeekendDays() {
        return limitWeekendDays;
    }
    public void setLimitWeekendDays(int limitWeekendDays) {
        this.limitWeekendDays = limitWeekendDays;
    }

    public int getLimitNormalDays() {
        return limitNormalDays;
    }
    public void setLimitNormalDays(int limitNormalDays) {
        this.limitNormalDays = limitNormalDays;
    }
}
