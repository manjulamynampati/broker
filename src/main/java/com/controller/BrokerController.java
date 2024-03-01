package com.controller;


import com.model.EventData;
import com.service.BrokerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.model.SubscriberModel;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class BrokerController {

    private HashMap<String, String> publisherStatusMap = new HashMap<>();

    // edited by @Manjula Mynampati
    private HashMap<String, List<SubscriberModel>> publisherSubscriberMap = new HashMap<>();
    private HashMap<String, EventData> publisherEventDataMap = new HashMap<>();
    //added @Manjula Mynampati
    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    private BrokerService brokerService;
    // ended by @Manjula Mynampati


    // This method is coming from subscriber to fetch list of active publishers @Manjula Mynampati
    @GetMapping(value = "/getPublishers")
    public ResponseEntity<List<String>> getPublishers() {
        List<String> activePublishers = new ArrayList<>();

        for (Map.Entry<String, String> entry : publisherStatusMap.entrySet()) {
            String publisherId = entry.getKey();
            String status = entry.getValue();

            if ("active".equals(status)) {
                activePublishers.add(publisherId);
            }
        }

        return ResponseEntity.ok(activePublishers);
    }

    // This method is coming from subscriber to subscribe list of subscribers @Manjula Mynampati
    @PostMapping(value = "/subscribe")
    public ResponseEntity<HttpStatus> subscribe(@RequestBody SubscriberModel subscriberModel) {

        int subscriberId = subscriberModel.getSubscriberId();
        List<String> selectedPublishers = subscriberModel.getPublishers();

        lock.lock();
        try {
            for (String publisherId : selectedPublishers) {
                if (!publisherSubscriberMap.containsKey(publisherId)) {
                    publisherSubscriberMap.put(publisherId, new ArrayList<>());
                }
                List<SubscriberModel> subscribers = publisherSubscriberMap.get(publisherId);
                subscribers.add(subscriberModel);
            }

            System.out.println("Subscriber with ID " + subscriberId + " subscribed to publishers: " + selectedPublishers);

        } finally {
            lock.unlock();
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //this method should be called inside receiveEventDataFromPublisherAndSend
    public boolean notify (List<SubscriberModel> subscribers, EventData event) {

        boolean flag = brokerService.notify(subscribers,event);

        return flag;

    }



    }
    // send to Publishers
    // consistency and replica


}
