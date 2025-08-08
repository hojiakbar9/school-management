package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.marburgermoschee.schoolmanagement.dtos.PaymentDto;
import org.marburgermoschee.schoolmanagement.entities.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);
}
