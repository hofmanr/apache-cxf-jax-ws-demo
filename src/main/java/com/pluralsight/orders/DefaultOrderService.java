package com.pluralsight.orders;

import com.pluralsight.service.order.*;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.GregorianCalendar;

public class DefaultOrderService implements OrderService {

    @Override
    public OrderInquiryResponseType processOrder(int uniqueOrderId, int orderQuantity, int accountId, long ean13) {
        ObjectFactory factory = new ObjectFactory();
        OrderInquiryResponseType response = factory.createOrderInquiryResponseType();

        AccountType account = factory.createAccountType();
        account.setAccountId(1);
        response.setAccount(account);

        OrderItemType orderItem = factory.createOrderItemType();
        BookType book = factory.createBookType();
        book.setEan13(ean13);
        book.setTitle("A CXF Book");
        orderItem.setBook(book);

        orderItem.setExpectedShippingDate(LocalDate.now());
        orderItem.setLineNumber(Integer.valueOf(1));
        orderItem.setPrice(new BigDecimal(5));
        orderItem.setQuantityShipped(orderQuantity);

//        try {
//            GregorianCalendar cal = new GregorianCalendar();
//            cal.setTimeInMillis(System.currentTimeMillis());
//            XMLGregorianCalendar estimatingShippingDate =
//                    DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
//            orderItem.setExpectedShippingDate(estimatingShippingDate);
//            orderItem.setLineNumber(Integer.valueOf(1));
//            orderItem.setPrice(new BigDecimal(5));
//            orderItem.setQuantityShipped(orderQuantity);
//        } catch (Exception e) {
//            // Ignore this exception
//        }

        OrderType order = factory.createOrderType();
        order.setOrderStatus(OrderStatusType.READY);
        order.getOrderItems().add(orderItem);

        response.setOrder(order);
        return response;

    }
}
