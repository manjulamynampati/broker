package com.service;

import com.model.SubscriberModel;
import org.springframework.stereotype.Repository;
import org.springframework.http.HttpStatus;
import com.model.EventData;

import java.util.List;

@Repository
public interface BrokerService {

    public boolean notify (List<SubscriberModel> subscribers, EventData event);
}
