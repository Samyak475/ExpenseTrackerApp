package com.codeartist.expenseservice.services;

import com.codeartist.expenseservice.dtos.ExpenseDto;
import com.codeartist.expenseservice.entity.Expense;
import com.codeartist.expenseservice.enums.Category;
import com.codeartist.expenseservice.repository.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    ExpenseRepo expenseRepo;

    @Autowired
    public ExpenseService(ExpenseRepo expenseRepo){
        this.expenseRepo = expenseRepo;
    }

    @Transactional
    public ExpenseDto addExpense(Integer userId , ExpenseDto expenseDto){
        ExpenseDto responseExpenseDto = new ExpenseDto();
        try {
            expenseDto.setUserId(userId);
            responseExpenseDto = getExpenseDtoFrmExpense( expenseRepo.save(getExpenseFrmDto(expenseDto)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseExpenseDto;
    }

    @Transactional
    public void deleteExpense(Integer userId){
        try {
            expenseRepo.deleteById(userId);
            System.out.println("user expense deleted ");
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }
    public Page<ExpenseDto> getExpenseSummary(Integer userId, Category category , LocalDate startDate , LocalDate endTime,
                                         Integer page, Integer size , String sortBy ,String sortOrder){
       Sort sort= Sort.by( sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())?
               Sort.Direction.ASC: Sort.Direction.DESC,sortBy);

       Pageable pageRequest = PageRequest.of(page,size,sort);

      Page<Expense>page1 = expenseRepo.getExpense(userId,category,startDate,endTime,pageRequest);
        return page1.map(this::getExpenseDtoFrmExpense);
    }


    public Expense getExpenseFrmDto(ExpenseDto expenseDto){
        return  Expense.builder()
                .userId(expenseDto.getUserId())
                .amountPaid(expenseDto.getAmountPaid())
                .category(expenseDto.getCategory())
                .paidTo(expenseDto.getPaidTo())
                .comment(expenseDto.getComment())
                .payDate(expenseDto.getPayDate())
                .paymentType(expenseDto.getPaymentType())
                .build();
    }

    public ExpenseDto getExpenseDtoFrmExpense(Expense expense){
        return  ExpenseDto.builder()
                .userId(expense.getUserId())
                .amountPaid(expense.getAmountPaid())
                .category(expense.getCategory())
                .paidTo(expense.getPaidTo())
                .comment(expense.getComment())
                .payDate(expense.getPayDate())
                .paymentType(expense.getPaymentType())
                .build();
    }
}
