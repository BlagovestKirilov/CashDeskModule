package com.fibank.cashdeskmodule.repository;

import com.fibank.cashdeskmodule.exception.BalanceNotFoundException;
import com.fibank.cashdeskmodule.model.dto.request.CashBalanceRequest;
import com.fibank.cashdeskmodule.enums.CurrencyType;
import com.fibank.cashdeskmodule.enums.DenominationValue;
import com.fibank.cashdeskmodule.model.Balance;
import com.fibank.cashdeskmodule.model.Cashier;
import com.fibank.cashdeskmodule.model.Denomination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CashierRepository {

    private static final Logger logger = LoggerFactory.getLogger(CashierRepository.class);
    private static final String BALANCES_FILE = "data/balances.txt";

    public void save(Cashier cashier) {
        logger.info("Saving cashier '{}' to file", cashier.getName());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BALANCES_FILE, true))) {
            StringBuilder record = new StringBuilder();

            record.append(cashier.getLastUpdate()).append("|")
                    .append(cashier.getName()).append("|");

            for (Balance balance : cashier.getBalances()) {
                record.append(balance.getCurrency().name()).append("|")
                        .append(String.format("%.2f", balance.getAmount())).append("|");

                for (Denomination denomination : balance.getDenominations()) {
                    record.append(denomination.getDenominationValue().name()).append("|")
                            .append(denomination.getQuantity()).append("|");
                }
            }

            if (!record.isEmpty()) {
                record.setLength(record.length() - 1);
            }
            record.append("\n");

            writer.write(record.toString());
            logger.info("Cashier '{}' successfully saved", cashier.getName());
        } catch (IOException e) {
            logger.error("Failed to save balances to file for cashier '{}'", cashier.getName(), e);
            throw new RuntimeException("Failed to save balances to file", e);
        }
    }

    public List<Cashier> findByRequest(CashBalanceRequest request) {
        logger.info("Searching balances by request: {}", request);
        List<Cashier> cashiers = new ArrayList<>();
        Path balancePath = Paths.get(BALANCES_FILE);
        if (!Files.exists(balancePath)) {
            logger.warn("Balances file not found: {}", BALANCES_FILE);
            return cashiers;
        }

        try (BufferedReader reader = Files.newBufferedReader(balancePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (validateByRequest(parts, request)) {
                    Cashier cashier = initCashierByParts(parts);
                    cashiers.add(cashier);
                    logger.debug("Matching cashier found: {}", cashier.getName());
                }
            }
        } catch (IOException e) {
            logger.error("Failed to load balances from file", e);
            throw new BalanceNotFoundException();
        }

        logger.info("Found {} matching cashiers", cashiers.size());
        return cashiers;
    }

    public Boolean validateByRequest(String[] parts, CashBalanceRequest request) {
        if (parts.length != 62) return Boolean.FALSE;

        LocalDate balanceDate = LocalDateTime.parse(parts[0]).toLocalDate();
        String cashierName = parts[1];
        return (request.getCashierName() == null || cashierName.equals(request.getCashierName())) &&
                (request.getDateFrom() == null || !balanceDate.isBefore(request.getDateFrom())) &&
                (request.getDateTo() == null || !balanceDate.isAfter(request.getDateTo()));
    }

    public Optional<Cashier> findByName(String name) {
        logger.info("Looking for cashier by name: {}", name);
        Cashier resultCashier = null;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(BALANCES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String cashierName = parts[1];
                if (cashierName.equals(name)) {
                    resultCashier = initCashierByParts(parts);
                    logger.debug("Cashier '{}' found in file", name);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to load balances from file", e);
            throw new BalanceNotFoundException();
        }

        if (resultCashier == null) {
            logger.warn("Cashier '{}' not found in file", name);
        }

        return Optional.ofNullable(resultCashier);
    }

    private Cashier initCashierByParts(String[] parts) {
        if (parts.length != 62) {
            throw new IllegalArgumentException("Invalid data format");
        }

        Cashier cashier = new Cashier();
        cashier.setName(parts[1]);
        cashier.setLastUpdate(LocalDateTime.parse(parts[0]));

        int eurStartIndex = -1;
        for (int i = 3; i < parts.length; i++) {
            if ("EUR".equals(parts[i])) {
                eurStartIndex = i;
                break;
            }
        }

        Balance balanceBGN = parseBalance(parts, 2, eurStartIndex - 2, CurrencyType.BGN);

        Balance balanceEUR = parseBalance(parts, eurStartIndex, parts.length - eurStartIndex, CurrencyType.EUR);

        cashier.setBalances(Arrays.asList(balanceBGN, balanceEUR));
        return cashier;
    }

    private Balance parseBalance(String[] parts, int startIndex, int length, CurrencyType currency) {
        Balance balance = new Balance();
        balance.setCurrency(currency);
        balance.setAmount(new BigDecimal(parts[startIndex + 1]));

        List<Denomination> denominations = new ArrayList<>();

        for (int i = startIndex + 2; i < startIndex + length; i += 2) {
            if (i + 1 >= parts.length) break;

            try {
                Denomination denomination = new Denomination();
                denomination.setDenominationValue(DenominationValue.valueOf(parts[i]));
                denomination.setQuantity(Integer.parseInt(parts[i + 1]));
                denominations.add(denomination);
            } catch (IllegalArgumentException e) {
                logger.warn("Skipping invalid denomination {}: {}", parts[i], e.getMessage());
            }
        }

        balance.setDenominations(denominations);
        return balance;
    }
}
