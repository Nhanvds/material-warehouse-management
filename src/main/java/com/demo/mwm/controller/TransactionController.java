package com.demo.mwm.controller;

import com.demo.mwm.dto.TransactionDto;
import com.demo.mwm.service.ITransactionService;
import com.demo.mwm.utils.AESUtils;
import com.demo.mwm.utils.RSAUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/transactions")
@RestController
public class TransactionController {
    private final ITransactionService transactionService;
    private final RSAUtils rsaUtils;

    public TransactionController(ITransactionService transactionService, RSAUtils rsaUtils) {
        this.transactionService = transactionService;
        this.rsaUtils = rsaUtils;
    }

    @Operation(description = "Create a new Transaction",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Created transaction successfully. Return transaction created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
            })
    @PostMapping("/save")
    public ResponseEntity<TransactionDto> createTransaction(
            @RequestParam("transactionId") String transactionId,
            @RequestParam("account") String account,
            @RequestParam("indebted") String indebted,
            @RequestParam("have") String have,
            @RequestParam("time") String time

    ) {
        // giả sử đã được mã hóa:
        transactionId = rsaUtils.encrypt(transactionId);
        account = rsaUtils.encrypt(account);
        indebted = rsaUtils.encrypt(indebted);
        have = rsaUtils.encrypt(have);
        time = rsaUtils.encrypt(time);
        //-----------------------------
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.createTransaction(transactionId, account, indebted, have, time));
    }
}
