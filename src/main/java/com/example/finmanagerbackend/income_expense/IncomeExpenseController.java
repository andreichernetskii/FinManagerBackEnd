package com.example.finmanagerbackend.income_expense;

import com.example.finmanagerbackend.account.Account;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Api Controller
@RestController
@RequestMapping( path = "api/v1/operations" )
public class IncomeExpenseController {
    private final IncomeExpenseService incomeExpenseService;

    public IncomeExpenseController( IncomeExpenseService incomeExpenseService ) {
        this.incomeExpenseService = incomeExpenseService;
    }

    // ++
    @PostMapping( "/" )
    public void addNewIncomeExpense( Account account,
                                     @RequestBody IncomeExpenseDTO incomeExpenseDTO ) {

        incomeExpenseService.addIncomeExpense( account, incomeExpenseDTO );
    }

    // ++
    @PutMapping( "/" )
    public void updateIncomeExpense( Account account,
                                     @RequestBody IncomeExpense incomeExpense ) {

        incomeExpenseService.updateIncomeExpense( account, incomeExpense );
    }

    // ++
    @DeleteMapping( "/{operationId}" )
    public void deleteIncomeExpense( Account account,
                                     @PathVariable( "operationId" ) Long operationId ) {
        incomeExpenseService.deleteIncomeExpense( account, operationId );
    }

    // ++
    @GetMapping( "/" )
    public List<IncomeExpense> getOperationsOfPeriod( Account account,
                                                      @RequestParam( name = "year", required = false ) Integer year,
                                                      @RequestParam( name = "month", required = false ) Integer month,
                                                      @RequestParam( name = "operationType", required = false ) OperationType operationType,
                                                      @RequestParam( name = "category", required = false ) String category ) {

        List<IncomeExpense> list = incomeExpenseService.getOperationsByCriteria(
                account,
                year,
                month,
                operationType,
                category );

        return list;
    }

    // ++
    @GetMapping( "/annual" )
    public Double getAnnualBalance( Account account,
                                    @RequestParam( name = "year", required = false ) Integer year,
                                    @RequestParam( name = "month", required = false ) Integer month,
                                    @RequestParam( name = "operationType", required = false ) OperationType operationType,
                                    @RequestParam( name = "category", required = false ) String category ) {

        Double totalAmount = incomeExpenseService.getAnnualBalance( account, year, month, operationType, category );
        return totalAmount;
    }

    // ++
    @GetMapping( "/categories" )
    public List<String> getCategories( Account account ) {
        return incomeExpenseService.getCategories( account );
    }
}
