package com.pluralsight.orders;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SchemaErrorHandler implements ErrorHandler  {
    private List<String> schemaErrors = new ArrayList<>();

    private void addFault(SAXParseException e){
        if (e.getMessage() != null) {
            schemaErrors.add(e.getMessage());
            return;
        }

        if (e.getCause() != null && e.getCause().getCause() instanceof SAXParseException) {
            SAXParseException spe = (SAXParseException) e.getCause().getCause();
            schemaErrors.add(spe.getMessage());
            return;
        }

        if (e.getException() != null && e.getException().getCause() != null) {
            schemaErrors.add(e.getException().getCause().getMessage());
        }
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        addFault(exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        addFault(exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        addFault(exception);
    }

    public List<String> getSchemaErrors() {
        return schemaErrors;
    }
}
