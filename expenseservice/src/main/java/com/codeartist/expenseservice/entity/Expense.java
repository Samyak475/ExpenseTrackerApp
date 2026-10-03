package com.codeartist.expenseservice.entity;

import com.codeartist.expenseservice.enums.Category;
import com.codeartist.expenseservice.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.pl.NIP;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Expense {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer expenseId;

private Integer userId;
@CreatedDate
private LocalDate payDate;
private String paidTo;
@Enumerated(EnumType.STRING)
private Category category;
private Integer amountPaid;
@Enumerated (EnumType.STRING)
private PaymentType paymentType;
private String comment;



}
