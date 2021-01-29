package com.pluralsight.orders;

import com.pluralsight.service.order.OrderInquiryResponseType;
import com.pluralsight.service.order.OrderInquiryType;
import com.pluralsight.service.order.OrderStatusType;
import com.pluralsight.service.orders.Orders;
import com.pluralsight.service.orders.ProcessOrderPlacementFault;
import org.apache.commons.httpclient.HttpStatus;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService(portName="OrdersSOAP", serviceName="Orders",
        endpointInterface="com.pluralsight.service.orders.Orders",
        targetNamespace = "http://www.pluralsight.com/service/Orders/")
@HandlerChain(file = "handler-chain.xml")
public class DefaultOrdersEndpoint implements Orders {

    @Inject
    private OrderService orderService;

    @Resource
    private WebServiceContext wsContext;

    @Override
    public OrderInquiryResponseType processOrderPlacement(OrderInquiryType orderInquiry) throws ProcessOrderPlacementFault {
        MessageContext messageContext = wsContext.getMessageContext();

        if (orderInquiry.getUniqueOrderId() < 1) {
            // Sets http response code 500
            throw new ProcessOrderPlacementFault("Order ID must be greater than zero.");
        }
        OrderInquiryResponseType response = orderService.processOrder(
                orderInquiry.getUniqueOrderId(),
                orderInquiry.getOrderQuantity(),
                orderInquiry.getAccountId(), orderInquiry.getEan13());
        if (response.getOrder().getOrderStatus() == OrderStatusType.BACKORDER) {
            messageContext.put(MessageContext.HTTP_RESPONSE_CODE, HttpStatus.SC_PAYMENT_REQUIRED);
       }
        return response;
    }
}
