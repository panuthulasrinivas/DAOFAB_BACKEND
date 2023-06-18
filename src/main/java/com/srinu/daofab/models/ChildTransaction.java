package com.srinu.daofab.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChildTransaction {
    Integer id;
    Integer parentId;
    BigDecimal paidAmount;
}
