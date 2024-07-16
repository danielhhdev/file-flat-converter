package com.dhh.file_flat_converter.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

@Record(minOccurs = 1)
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentIO {

    @Field(ordinal = 1, at = 0, length = 1, minOccurs = 1)
    private String lineType;

    @Field(ordinal = 1, at = 0, length = 1, minOccurs = 1)
    private String referencePayment;

    @Field(ordinal = 2, at = 1, length = 70, minOccurs = 1)
    private String debtorName;

    @Field(ordinal = 3, at = 70, length = 70, minOccurs = 1)
    private String creditorName;

    @Field(ordinal = 4, at = 0, length = 1, minOccurs = 1)
    private String debtorIBAN;

    @Field(ordinal = 5, at = 0, length = 1, minOccurs = 1)
    private String creditorIBAN;

    @Field(ordinal = 6, at = 0, length = 1, minOccurs = 1)
    private String amount;

    @Field(ordinal = 7, at = 0, length = 1, minOccurs = 1)
    private String currencyType;

    @Field(ordinal = 8, at = 0, length = 1, minOccurs = 1)
    private String dateTransaction;

    @Field(ordinal = 9, at = 0, length = 1, minOccurs = 1)
    private String filler;

}
