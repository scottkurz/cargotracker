package org.eclipse.cargotracker.infrastructure.messaging.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(
    activationConfig = {
      @ActivationConfigProperty(
          propertyName = "destinationType",
          propertyValue = "javax.jms.Queue"),
      @ActivationConfigProperty(
          propertyName = "destination",
          propertyValue = "java:app/jms/MisdirectedCargoQueue")
    })
public class MisdirectedCargoConsumer implements MessageListener {

  @Inject private Logger logger;

  @Override
  public void onMessage(Message message) {
    try {
      logger.log(
          Level.INFO, "Cargo with tracking ID {0} misdirected.", message.getBody(String.class));
    } catch (JMSException ex) {
      logger.log(Level.WARNING, "Error processing message.", ex);
    }
  }
}
