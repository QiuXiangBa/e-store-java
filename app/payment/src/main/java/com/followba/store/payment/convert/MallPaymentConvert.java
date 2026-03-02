package com.followba.store.payment.convert;

import com.followba.store.payment.dto.PaymentCreateIntentDTO;
import com.followba.store.payment.vo.in.PaymentCreateIntentIn;
import com.followba.store.payment.vo.out.PaymentCreateIntentOut;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MallPaymentConvert {

    MallPaymentConvert INSTANCE = Mappers.getMapper(MallPaymentConvert.class);

    PaymentCreateIntentOut toPaymentCreateIntentOut(PaymentCreateIntentDTO dto);
}
