package com.project.ims.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.ims.Dto.OrderReportDTO;
import com.project.ims.Repository.OrdersRepository;

@Service
public class OrdersReportService {

    @Autowired
    private OrdersRepository ordersRepository;

    public Page<OrderReportDTO> searchOrders(String searchTerm, int page, int size) {
        int startRow = page * size;
        int endRow = startRow + size;
        List<Object[]> rows = ordersRepository.searchOrdersNative(searchTerm, startRow, endRow);
        long total = ordersRepository.countOrdersNative(searchTerm);

        List<OrderReportDTO> dtos = new ArrayList<>();
        for (Object[] row : rows) {
            dtos.add(new OrderReportDTO(
                    row[0] == null ? null : ((Number) row[0]).intValue(),             // orderId
                    row[1] == null ? null : ((Timestamp) row[1]).toLocalDateTime(),   // orderDate
                    row[2] == null ? null : row[2].toString(),                        // productName
                    row[3] == null ? null : row[3].toString(),                        // categoryName
                    row[4] == null ? null : row[4].toString(),                        // username
                    row[5] == null ? null : row[5].toString(),                        // address
                    row[6] == null ? null : ((Number) row[6]).intValue(),             // quantity
                    row[7] == null ? null : ((Number) row[7]).floatValue()            // totalPrice
            ));
        }
        return new PageImpl<>(dtos, PageRequest.of(page, size), total);
    }
}