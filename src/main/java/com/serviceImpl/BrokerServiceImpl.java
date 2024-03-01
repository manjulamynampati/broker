package com.serviceImpl;

import com.model.EventData;
import com.model.SubscriberModel;
import com.service.BrokerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerServiceImpl implements BrokerService {

    @Override
    public boolean notify (List<SubscriberModel> subscribers, EventData event){

        boolean flag = false;

        for (SubscriberModel node : subscribers) {
            String url = "http://" + node.getUrl() + ":" + "/notify";

        }

        return flag;
    }

}
