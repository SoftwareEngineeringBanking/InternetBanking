package br.com.xurebinhaBanking.model;

import lombok.Data;

@Data
public class AccountType {
    private String nameAccountType;
    private int numberOfTransferFree;
    private int daysCountForTransfer;
    private int valuePerTransfer;
    private int limitWeekendDays;
    private int limitNormalDays;
}
