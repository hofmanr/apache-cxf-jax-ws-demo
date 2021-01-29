package com.pluralsight.orders;

import java.io.StringReader;
import java.util.List;

import javax.inject.Inject;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

public class LogicalValidationHandler implements LogicalHandler<LogicalMessageContext>{

    private static final String SOAP_FAULT = "<soap:Fault xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <faultcode>soap:Server</faultcode>\n" +
            "   <faultstring>%s</faultstring>\n" +
            "</soap:Fault>\n";

    @Inject
    private SchemaValidator schemaValidator;

    @Override
    public boolean handleMessage(LogicalMessageContext context) {
        if (!(boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
            List<String> validationErrors = schemaValidator.validatePayload(context.getMessage().getPayload());
            if (!validationErrors.isEmpty()) {
                // Replace content with fault
                String fault = String.format(SOAP_FAULT, validationErrors.get(0));
                Source src = new StreamSource(new StringReader(fault));
                context.getMessage().setPayload(src);

                // Don't go further; return SOAP Message
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean handleFault(LogicalMessageContext context) {
        System.out.println("[SchemaValidationHandler] - Handle Fault: " + context.getMessage().getPayload());
        return true;
    }

    @Override
    public void close(MessageContext messageContext) {
        // Not used
    }

}
