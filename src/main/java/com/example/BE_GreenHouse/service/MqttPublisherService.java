package com.example.BE_GreenHouse.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MqttPublisherService {
    private String BROKER_URL = "tcp://io.adafruit.com:1883";

    private String USERNAME = "htann04";

    private String API_KEY = "aio_NCVK16TWkQxaq3kfASVY3Yj0toCJ";

    private String CLIENT_ID = "be_greenhouse";

    private MqttClient client;

    public MqttPublisherService() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(USERNAME);
            options.setPassword(API_KEY.toCharArray());

            client = new MqttClient(BROKER_URL, CLIENT_ID);
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(String device, String command) {
        try {
            String topic = USERNAME + "/feeds/" + device;
            client.publish(topic, new MqttMessage(command.getBytes()));
            log.info("Sent command: " + device + " -> " + command);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
