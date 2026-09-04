package com.paytracker.analytics_service.Client;

import com.paytracker.analytics_service.Dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "ledger-service")
public interface LedgerClient {

    @GetMapping("/api/transactions/user/{userId}")
    List<TransactionDto> getTransactionsByUser(@PathVariable("userId") Long userId);

    @GetMapping("/api/transactions/user/{userId}/range")
    List<TransactionDto> getTransactionsByDateRange(
            @PathVariable("userId") Long userId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    );
}