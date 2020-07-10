/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.handlers;

import java.io.Serializable;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 *
 * @author sherl
 */
public class JAXBValidationHandler implements Serializable, ValidationEventHandler {

    @Override
    public boolean handleEvent(ValidationEvent event) {
        if (event.getSeverity() == ValidationEvent.ERROR || event.getSeverity() == ValidationEvent.FATAL_ERROR) {
            System.out.println("Error: " + event.getMessage());
        }
        return true;
    }

}
