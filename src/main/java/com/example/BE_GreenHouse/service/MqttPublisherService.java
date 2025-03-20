package com.example.BE_GreenHouse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttPublisherService {
    @Value("${mqtt.brokerUrl}")
    private String BROKER_URL;

    @Value("${mqtt.username}")
    private String USERNAME;

    @Value("${mqtt.apiKey}")
    private String API_KEY;

    @Value("${mqtt.clientId}")
    private String CLIENT_ID;

    private MqttClient client;

    @PostConstruct
    public void init() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(USERNAME);
            options.setPassword(API_KEY.toCharArray());

            client = new MqttClient(BROKER_URL, CLIENT_ID + "-publisher");
            client.connect(options);

            log.info("Connected to MQTT and ready to publish");
        } catch (MqttException e) {
            log.error("Error connecting to MQTT broker", e);
            e.printStackTrace();
        }
    }

    public void sendCommand(String device, String command) {
        try {
            String topic = USERNAME + "/feeds/" + device;
            client.publish(topic, new MqttMessage(command.getBytes()));
            log.info("Sent command: {} -> {} ", topic, command);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
