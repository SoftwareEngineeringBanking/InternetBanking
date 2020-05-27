package br.com.xurebinhaBanking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountType {
    private String nameAccountType;
    private int numberOfTransferFree;
    private int daysCountForTransfer;
    private int valuePerTransfer;
    private int limitWeekendDays;
    private int limitNormalDays;
}
