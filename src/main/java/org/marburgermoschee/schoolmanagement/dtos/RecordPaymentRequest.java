package org.marburgermoschee.schoolmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RecordPaymentRequest {
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "The amount should be at least 1 Euro")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;
}
