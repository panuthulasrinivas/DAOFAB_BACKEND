package com.srinu.daofab.controller;

import com.srinu.daofab.models.ParentTransaction;
import com.srinu.daofab.service.TransactionService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0")
public class TransactionController {

  @Autowired
  TransactionService transactionService;

  @GetMapping
  public ResponseEntity<Page<ParentTransaction>> get(
      @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
      @RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize)
      throws IOException {
    return ResponseEntity.ok(transactionService.get(pageNo, pageSize));
  }
}
