package com.pluralsight.orders;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.XMLConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

@ApplicationScoped
public class SchemaValidator {
    private Schema schema;

    private Schema loadXsdSchemas(String... xsdFiles) {
        Source[] schemaSources = Arrays.stream(xsdFiles)
                .map(this::readFile)
                .map(StreamSource::new)
                .toArray(Source[]::new);
        try {
            SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
//            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
//            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            return factory.newSchema(schemaSources);
        } catch (SAXException e) {
            throw createSoapException(e);
        }
    }

    private StringReader readFile(String filePath) {
        try {
            String result = IOUtils.toString(SchemaValidator.class.getResourceAsStream(filePath), StandardCharsets.UTF_8);
            return new StringReader(result);
        } catch (IOException e) {
            throw createSoapException(e);
        }
    }

    private Validator getValidator() {
        Validator validator;

        if (schema == null) {
            schema = loadXsdSchemas("/xsd/Order.xsd");
        }

        try {
            validator = schema.newValidator();
//            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
//            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
//        } catch (SAXException | NullPointerException e) {
        } catch (NullPointerException e) {
             throw createSoapException(e);
        }
        return validator;
    }

    private WebServiceException createSoapException(Exception message) {
        try {
            SOAPFault soapFault = SOAPFactory.newInstance().createFault();
            soapFault.setFaultString(message.getMessage());
            return new WebServiceException(new SOAPFaultException(soapFault));
        } catch (SOAPException soapException) {
            return new WebServiceException(soapException);
        }
    }

    public List<String> validatePayload(Source payload) {
        try {
            Validator validator = getValidator();
            SchemaErrorHandler handler = new SchemaErrorHandler();
            validator.setErrorHandler(handler);
            validator.validate(payload);
            return handler.getSchemaErrors();
        } catch (IOException | SAXException e) {
            throw createSoapException(e);
        }
    }
}
