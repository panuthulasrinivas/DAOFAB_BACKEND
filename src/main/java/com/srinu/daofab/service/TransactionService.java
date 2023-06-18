package com.srinu.daofab.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srinu.daofab.models.ChildTransaction;
import com.srinu.daofab.models.ChildTransactionData;
import com.srinu.daofab.models.ParentTransaction;
import com.srinu.daofab.models.ParentTransactionData;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class TransactionService {

  @Value("${file.path}")
  String filePath;
  List<ParentTransaction> transactions = new ArrayList<>();

  ObjectMapper mapper = new ObjectMapper();


  public Page<ParentTransaction> get(int pageNo, int pageSize) throws IOException {
    pageNo = pageNo - 1;
    if (CollectionUtils.isEmpty(transactions)) {
      ParentTransactionData parentTransactionData = mapper.readValue(
          new File(filePath + "Parent.json"), ParentTransactionData.class);
      ChildTransactionData childTransactionData = mapper.readValue(
          new File(filePath + "Child.json"), ChildTransactionData.class);
      Map<Integer, List<ChildTransaction>> childTransactionMap = new HashMap<>();
      for (ChildTransaction childTransaction : childTransactionData.getData()) {
        if (childTransactionMap.containsKey(childTransaction.getParentId())) {
          childTransactionMap.get(childTransaction.getParentId()).add(childTransaction);
        } else {
          List<ChildTransaction> childTransactionList = new ArrayList<>();
          childTransactionList.add(childTransaction);
          childTransactionMap.put(childTransaction.getParentId(), childTransactionList);
        }
      }
      transactions.addAll(parentTransactionData.getData());
      for (ParentTransaction parentTransaction : parentTransactionData.getData()) {
        parentTransaction.setChildTransactions(childTransactionMap.get(parentTransaction.getId()));
      }
    }

    int startIndex = pageNo * pageSize;
    int endIndex = Math.min((pageNo + 1) * pageSize, transactions.size());
    Pageable pageable = PageRequest.of(pageNo+1, pageSize);
    Page<ParentTransaction> result = new PageImpl<ParentTransaction>(
        transactions.subList(startIndex, endIndex), pageable, transactions.size());
    return result;
  }

}
