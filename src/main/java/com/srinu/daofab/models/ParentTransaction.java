package com.srinu.daofab.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ParentTransaction {
    Integer id;
    String sender;
    String receiver;
    BigDecimal totalAmount;
    List<ChildTransaction> childTransactions;
}
