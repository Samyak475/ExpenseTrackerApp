package com.codeartist.expenseservice.repository;

import com.codeartist.expenseservice.entity.Expense;
import com.codeartist.expenseservice.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Integer> {

    @Query(value = "SELECT e from Expense e where e.userId = :userid " +
            "and (:category is NULL or e.category = :category" +
            " and (:startDate is NULL or e.expenseDate = :startDate) " +
            " and (:endDate is NULL or e.expenseDate = :endDate) " ,
            countQuery = "Select COUNT(e) from Expense e where e.userId = :userid" +
                    "and (:category is NULL or e.category = :category" +
                    " and (:startDate is NULL or e.expenseDate = :startDate) " +
                    " and (:endDate is NULL or e.expenseDate = :endDate) " )
    Page<Expense> getExpense(Integer userid , Category category , LocalDate startDate , LocalDate endDate, Pageable pageable);
}
