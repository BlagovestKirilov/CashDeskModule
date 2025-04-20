package com.fibank.cashdeskmodule.repository;

import com.fibank.cashdeskmodule.model.Denomination;
import com.fibank.cashdeskmodule.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {

    private static final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);
    private static final String TRANSACTION_HISTORY_FILE = "data/transaction_history.txt";

    public void save(Transaction transaction) {
        String line = formatTransaction(transaction);
        Path filePath = Paths.get(TRANSACTION_HISTORY_FILE);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(line);
            writer.newLine();
            logger.info("Transaction saved: {}", line);
        } catch (IOException e) {
            logger.error("Failed to save transaction: {}", line, e);
        }
    }

    public String formatTransaction(Transaction transaction) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<String> fields = new ArrayList<>();
        fields.add(dtf.format(transaction.getTimestamp()));
        fields.add(transaction.getTransactionStatus().toString());
        fields.add(transaction.getCashierName());
        fields.add(transaction.getOperationType().toString());
        fields.add(transaction.getAmount().toString());
        fields.add(transaction.getCurrency().toString());

        for (Denomination d : transaction.getDenominations()) {
            fields.add(d.getDenominationValue().toString());
            fields.add(String.valueOf(d.getQuantity()));
        }

        return String.join("|", fields);
    }
}
