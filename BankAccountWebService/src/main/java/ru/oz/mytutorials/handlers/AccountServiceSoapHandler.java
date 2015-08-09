package ru.oz.mytutorials.handlers;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Set;

/**
 * User: Igor
 * Date: 09.08.2015
 */
public class AccountServiceSoapHandler implements SOAPHandler<SOAPMessageContext> {
    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        System.out.println("Прилетел мессадж");
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        System.out.println("Прилетел эксепшн");
        return true;
    }

    @Override
    public void close(MessageContext context) {

    }
}
