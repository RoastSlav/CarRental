package org.rostislav.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.rostislav.models.PaymentReport;
import org.rostislav.sqlProviders.PaymentReportProvider;

@Mapper
public interface PaymentReportRepository {

    @SelectProvider(type = PaymentReportProvider.class, method = "buildPaymentReportQuery")
    Collection<PaymentReport> getPaymentReport(@Param("userName") String userName,
                                               @Param("method") String method,
                                               @Param("status") String status,
                                               @Param("start") LocalDate start,
                                               @Param("end") LocalDate end);
}
