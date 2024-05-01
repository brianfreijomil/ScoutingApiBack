package com.microservice.user.services.interfaces;

public interface IKafkaEventsService {

    public void emitKafkaEvent(Object event);

}
