package com.pluralsight.orders;

import com.pluralsight.service.order.OrderInquiryResponseType;

public interface OrderService {

    OrderInquiryResponseType processOrder(int uniqueOrderId, int orderQuantity,
                                          int accountId, long ean13);

}
