package org.rostislav.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.rostislav.models.RentalReport;
import org.rostislav.sqlProviders.RentalReportSqlProvider;

@Mapper
public interface RentalReportRepository {

    @SelectProvider(type = RentalReportSqlProvider.class, method = "buildGetRentalReportQuery")
    Collection<RentalReport> getRentalReport(
            @Param("userName") String userName,
            @Param("carLicensePlate") String carLicensePlate,
            @Param("status") String status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}