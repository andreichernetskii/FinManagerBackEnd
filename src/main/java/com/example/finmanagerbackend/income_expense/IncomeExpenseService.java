package com.example.finmanagerbackend.income_expense;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class IncomeExpenseService {
    private final IIncomeExpenseRepository iIncomeExpenseRepository;

    public IncomeExpenseService( IIncomeExpenseRepository iIncomeExpenseRepository ) {
        this.iIncomeExpenseRepository = iIncomeExpenseRepository;
    }

    public void addIncomeExpense( IncomeExpenseDTO incomeExpenseDTO ) {
        iIncomeExpenseRepository.save( new IncomeExpense(
                incomeExpenseDTO.getOperationType(),
                incomeExpenseDTO.getAmount(),
                incomeExpenseDTO.getCategory(),
                LocalDate.parse( incomeExpenseDTO.getDate() ) )
        );
    }

    public List<IncomeExpense> getOperations() {
        return iIncomeExpenseRepository.findAll();
    }

    public void deleteIncomeExpense( Long operationId ) {
        boolean exists = iIncomeExpenseRepository.existsById( operationId );
        if ( !exists ) {
            throw new IllegalStateException( "Operations with id " + operationId + " is not exists!" );
        }
        iIncomeExpenseRepository.deleteById( operationId );
    }

    // todo: dorobić
    public Map<String, BigDecimal> getAnnualBalance() {
        return new HashMap<String, BigDecimal>();
    }

    public void updateIncomeExpense( IncomeExpense incomeExpense ) {
        Optional<IncomeExpense> incomeExpenseOptional = iIncomeExpenseRepository.findById( incomeExpense.getId() );
        if ( !incomeExpenseOptional.isPresent() ) {
            throw new IllegalStateException( "Operacji z id " + incomeExpense.getId() + " nie istnieje w bazie!" );
        }
        iIncomeExpenseRepository.save( incomeExpense );
    }

    public List<IncomeExpense> getOperationsByCriteria( Integer year, Integer month, OperationType operationType, String category ) {
        List<IncomeExpense> list = iIncomeExpenseRepository.findOperationsByCriteria( year, month, operationType, category );
        return list;
    }
}
