package com.fibank.cashdeskmodule.config;

import com.fibank.cashdeskmodule.enums.CurrencyType;
import com.fibank.cashdeskmodule.enums.DenominationValue;
import com.fibank.cashdeskmodule.model.Balance;
import com.fibank.cashdeskmodule.model.Cashier;
import com.fibank.cashdeskmodule.model.Denomination;
import com.fibank.cashdeskmodule.repository.CashierRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.fibank.cashdeskmodule.enums.DenominationValue.BGN_DENOMINATIONS;
import static com.fibank.cashdeskmodule.enums.DenominationValue.EUR_DENOMINATIONS;

@Configuration
public class DataInitializer {

    private final CashierRepository cashierRepository;

    @Autowired
    public DataInitializer(CashierRepository cashierRepository) {
        this.cashierRepository = cashierRepository;
    }

    @PostConstruct
    private void initializeCashiers() {
        List<String> cashierNames = Stream.of("MARTINA", "PETER", "LINDA")
                .filter(cashierName -> cashierRepository.findByName(cashierName).isEmpty())
                .toList();

        for (String name : cashierNames) {
            Cashier cashier = new Cashier();
            cashier.setName(name);
            cashier.setLastUpdate(LocalDateTime.now());

            Balance balanceBGN = new Balance();
            balanceBGN.setCurrency(CurrencyType.BGN);
            balanceBGN.setAmount(BigDecimal.valueOf(1000));
            balanceBGN.setDenominations(createDenominations(
                    Map.of(
                            DenominationValue.TEN_LEVA, 50,
                            DenominationValue.FIFTY_LEVA, 10
                    ),
                    CurrencyType.BGN
            ));

            Balance balanceEUR = new Balance();
            balanceEUR.setCurrency(CurrencyType.EUR);
            balanceEUR.setAmount(BigDecimal.valueOf(2000));
            balanceEUR.setDenominations(createDenominations(
                    Map.of(
                            DenominationValue.TEN_EURO, 100,
                            DenominationValue.FIFTY_EURO, 20
                    ),
                    CurrencyType.EUR
            ));

            cashier.setBalances(Arrays.asList(balanceBGN, balanceEUR));
            cashierRepository.save(cashier);
        }
    }

    private List<Denomination> createDenominations(Map<DenominationValue, Integer> nonZeroDenominations,
                                                   CurrencyType currency) {
        List<Denomination> denominations = new ArrayList<>();

        DenominationValue[] allDenominations = (currency == CurrencyType.BGN)
                ? BGN_DENOMINATIONS
                : EUR_DENOMINATIONS;

        for (DenominationValue dv : allDenominations) {
            Denomination d = new Denomination();
            d.setDenominationValue(dv);
            d.setQuantity(nonZeroDenominations.getOrDefault(dv, 0));
            denominations.add(d);
        }

        return denominations;
    }
}
