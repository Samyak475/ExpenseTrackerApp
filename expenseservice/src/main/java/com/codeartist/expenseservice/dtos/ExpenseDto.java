package com.codeartist.expenseservice.dtos;

import com.codeartist.expenseservice.enums.Category;
import com.codeartist.expenseservice.enums.PaymentType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private Integer userId;
    private LocalDate payDate;
    private Category category;
    private Integer amountPaid;
    private PaymentType paymentType;
    private String comment;
    private String paidTo;
}
