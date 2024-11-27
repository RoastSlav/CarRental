package org.rostislav.sqlProviders;

import java.util.Map;

public class RentalReportSqlProvider {

    public static String buildGetRentalReportQuery(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.full_name AS userName, ")
           .append("       c.license_plate AS carLicensePlate, ")
           .append("       r.pickup_date AS pickupDate, ")
           .append("       r.dropoff_date AS dropoffDate, ")
           .append("       r.total_price AS totalPrice, ")
           .append("       r.status AS status ")
           .append("FROM rentals r ")
           .append("JOIN users u ON r.user_id = u.id ")
           .append("JOIN cars c ON r.car_id = c.id ")
           .append("WHERE 1=1 ");

        if (params.get("userName") != null && !((String) params.get("userName")).trim().isEmpty()) {
            sql.append(" AND u.full_name = #{userName} ");
        }

        if (params.get("carLicensePlate") != null && !((String) params.get("carLicensePlate")).trim().isEmpty()) {
            sql.append(" AND c.license_plate = #{carLicensePlate} ");
        }

        if (params.get("status") != null && !((String) params.get("status")).trim().isEmpty()) {
            sql.append(" AND r.status = #{status} ");
        }

        if (params.get("startDate") != null) {
            sql.append(" AND r.pickup_date >= #{startDate} ");
        }

        if (params.get("endDate") != null) {
            sql.append(" AND r.dropoff_date <= #{endDate} ");
        }

        return sql.toString();
    }
}
