package com.paygo.controller;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.azure.eventhubs.*;
import com.paygo.entity.Event;
import com.paygo.service.EventService;

import java.nio.charset.Charset;
import java.time.*;
import java.util.function.*;

import java.util.HashMap;
import java.util.Map;


@Component
public class MyBean {

	private static Logger logger = LoggerFactory.getLogger(MyBean.class);

	private ExecutorService executorService;
	
    @Autowired
    EventService eventService;

	@PostConstruct
	public void init() {

		executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new Runnable() {
			String connStr = "Endpoint=sb://iothub-ns-myiothubke-127396-21ae7790a0.servicebus.windows.net/;EntityPath=myiothubke;SharedAccessKeyName=iothubowner;SharedAccessKey=s2a0QtY5u6bFyV+tN7HPE6fWV1OLIbkb9CR0RwXLwVc=";
	
			EventHubClient client0 = null;
			EventHubClient client1 = null;

			private EventHubClient receiveMessages(final String partitionId) {
				EventHubClient client = null;
				try {
					client = EventHubClient.createFromConnectionStringSync(connStr);
				} catch (Exception e) {
					logger.debug("Failed to create client: " + e.getMessage());
					// System.exit(1);
				}
				try {
					client.createReceiver(EventHubClient.DEFAULT_CONSUMER_GROUP_NAME, partitionId, Instant.now())
							.thenAccept(new Consumer<PartitionReceiver>() {
								public void accept(PartitionReceiver receiver) {
									logger.debug("** Created receiver on partition " + partitionId);
									try {
										while (true) {
											Iterable<EventData> receivedEvents = receiver.receive(100).get();
											int batchSize = 0;
											if (receivedEvents != null) {
												for (EventData receivedEvent : receivedEvents) {
													logger.debug(String.format(
															"Offset: %s, SeqNo: %s, EnqueueTime: %s",
															receivedEvent.getSystemProperties().getOffset(),
															receivedEvent.getSystemProperties().getSequenceNumber(),
															receivedEvent.getSystemProperties().getEnqueuedTime()));
													logger.debug(String.format("| Device ID: %s", receivedEvent
															.getSystemProperties().get("iothub-connection-device-id")));
													logger.debug(String.format("| Message Payload: %s",
															new String(receivedEvent.getBody(),
																	Charset.defaultCharset())));
													
													
													Event event = new Event();
													event.setOffset(receivedEvent.getSystemProperties().getOffset());
													event.setSeqno(receivedEvent.getSystemProperties().getSequenceNumber());		
													event.setEnqueuetime(Date.from(receivedEvent.getSystemProperties().getEnqueuedTime()));
													event.setDeviceid(receivedEvent.getSystemProperties().get("iothub-connection-device-id").toString());
													
													
													ObjectMapper mapper = new ObjectMapper();
													
													Map<String, Object> map = new HashMap<String, Object>();
													
													map = mapper.readValue(new String(receivedEvent.getBody(),Charset.defaultCharset()), new TypeReference<Map<String, String>>(){});
												    
													event.setPayload(map.get("gasLeak").toString());
													
													eventService.addEvent(event);
													batchSize++;
												}
											}
											logger.debug(String.format("Partition: %s, ReceivedBatch Size: %s",
													partitionId, batchSize));
										}
									} catch (Exception e) {
										logger.debug("Failed to receive messages: " + e.getMessage());
									}
								}
							});
				} catch (Exception e) {
					logger.debug("Failed to create receiver: " + e.getMessage());
				}
				return client;
			}

			public void run() {
				try {
					logger.debug("Thread started");
					client0 = receiveMessages("0");
					client1 = receiveMessages("1");




				} catch (Exception e) {
					logger.error("error: ", e);
				}
			}
		});

		executorService.shutdown();

	}

	@PreDestroy
	public void beandestroy() {
		if (executorService != null) {
/*			client0.closeSync();
			client1.closeSync();*/
			executorService.shutdownNow();
		}
	}

}
