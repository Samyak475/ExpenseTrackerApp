package com.codeartist.expenseservice.controllers;

import com.codeartist.expenseservice.dtos.ExpenseDto;
import com.codeartist.expenseservice.enums.Category;
import com.codeartist.expenseservice.services.ExpenseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping
public class ExpenseController {

    ExpenseService expenseService ;
    @Autowired
    public ExpenseController(ExpenseService expenseService){
        this.expenseService=expenseService;
    }

    @PostMapping("/v1/expense/me")
    public ResponseEntity<ExpenseDto> addExpense(@RequestHeader("X-User-Id") Integer userId, @RequestBody ExpenseDto expenseDto){
        // responsible for adding expense for userId .
      ExpenseDto expenseDto1 =   expenseService.addExpense(userId , expenseDto);
        return  new ResponseEntity<>(expenseDto1,HttpStatus.ACCEPTED);
    }


    @DeleteMapping("v1/expense/me")
    public ResponseEntity deleteExpense(@RequestHeader("X-User-Id")Integer userId){
        // delete expense for the given user Id
        try {
            expenseService.deleteExpense(userId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("v1/expense/me")
    public ResponseEntity<Page<ExpenseDto>> getExpenses(@RequestHeader("X-User-Id") Integer userId,
                                                        @RequestParam(value = "Category",required = false) Category category,
                                                        @RequestParam(value = "StartDate", required = false)LocalDate startDate,
                                                        @RequestParam(value = "EndDate",required = false) LocalDate endDate,
                                                   @RequestParam(defaultValue="0")int page,
                                                   @RequestParam(defaultValue = "10")int size,
                                                   @RequestParam(defaultValue = "payDate")String sortBy,
                                                   @RequestParam(defaultValue = "ASC")String sortOrder
                                                  ){
        // this is responsible for getting expense of user with id.
        // for user with given category , startDate , endDate
      Page<ExpenseDto> expenseDtoList=  expenseService.getExpenseSummary(userId, category, startDate ,endDate,page,size,sortBy,sortOrder);
      return new ResponseEntity<>(expenseDtoList,HttpStatus.OK);
    }


}
