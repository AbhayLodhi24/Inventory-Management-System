package com.project.ims.Service;

import com.project.ims.Dto.OrderReportDTO;
import com.project.ims.Repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersReportService {

    @Autowired
    private OrdersRepository ordersRepository;

    public List<OrderReportDTO> searchOrders(String searchTerm) {
        List<Object[]> rows = ordersRepository.searchOrdersNative(searchTerm);
        List<OrderReportDTO> dtos = new ArrayList<>();
        for (Object[] row : rows) {
            dtos.add(new OrderReportDTO(
                    row[0] == null ? null : ((Number) row[0]).intValue(),
                    row[1] == null ? null : ((Timestamp) row[1]).toLocalDateTime(),
                    row[2] == null ? null : row[2].toString(),
                    row[3] == null ? null : row[3].toString(),
                    row[4] == null ? null : row[4].toString(),
                    row[5] == null ? null : row[5].toString(),
                    row[6] == null ? null : ((Number) row[6]).intValue(),
                    row[7] == null ? null : ((Number) row[7]).floatValue()
            ));
        }
        return dtos;
    }
}