package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDto {
    private Integer id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private LocalDate dueDate;
    private String note;

}
