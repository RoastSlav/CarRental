package org.rostislav.sqlProviders;

import java.util.Map;

public class PaymentReportProvider {

    public String buildPaymentReportQuery(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.full_name AS userName, p.method AS paymentMethod, p.amount, p.date, p.status ");
        sql.append("FROM payments p ");
        sql.append("JOIN rentals r ON p.rental_id = r.id ");
        sql.append("JOIN users u ON r.user_id = u.id ");

        if (params.get("userName") != null) {
            sql.append("WHERE u.full_name LIKE CONCAT('%', #{userName}, '%') ");
        }

        if (params.get("method") != null) {
            sql.append("WHERE p.method = #{method} ");
        }

        if (params.get("status") != null) {
            sql.append("WHERE p.status = #{status} ");
        }

        if (params.get("start") != null) {
            sql.append("WHERE p.date >= #{start} ");
        }

        if (params.get("end") != null) {
            sql.append("WHERE p.date <= #{end} ");
        }

        return sql.toString();
    }
}
