package com.example.BE_GreenHouse.service;

import com.example.BE_GreenHouse.model.DeviceStatus;
import com.example.BE_GreenHouse.model.SensorData;
import com.example.BE_GreenHouse.repository.DeviceStatusRepository;
import com.example.BE_GreenHouse.repository.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttSubscriberService {
    @Value("${mqtt.brokerUrl}")
    private String BROKER_URL;

    @Value("${mqtt.username}")
    private String USERNAME;

    @Value("${mqtt.apiKey}")
    private String API_KEY;

    @Value("${mqtt.clientId}")
    private String CLIENT_ID;

    private final SensorDataRepository sensorDataRepository;

    private final DeviceStatusRepository deviceStatusRepository;

    private MqttClient client;

    @PostConstruct
    public void connectAndSubscribe() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(USERNAME);
            options.setPassword(API_KEY.toCharArray());

            client = new MqttClient(BROKER_URL, CLIENT_ID + "-subscriber");

            client.connect(options);

            String[] topics = {
                    USERNAME + "/feeds/air-humidity",
                    USERNAME + "/feeds/light-intensity",
                    USERNAME + "/feeds/soil-humidity",
                    USERNAME + "/feeds/temperature",
                    USERNAME + "/feeds/fan",
                    USERNAME + "/feeds/led",
                    USERNAME + "/feeds/water-pump"
            };

            for (String topic : topics) {
                client.subscribe(topic, (topic1, message) -> {
                    String payload = new String(message.getPayload());
                    log.info("Received message: {} -> {}", topic1, payload);

                    // Handle the incoming data
                    handleIncomingData(topic1, payload);
                });
            }

            log.info("Connected to MQTT and subscribed to topics.");
        } catch (MqttException e) {
            log.error("Error connecting to MQTT broker", e);
        }
    }

    private void handleIncomingData(String topic, String payload) {
        try {
            // Lấy giá trị thời gian hiện tại
            LocalDateTime createdAt = LocalDateTime.now();

            // Chuyển payload sang số hoặc trạng thái
            String feed = topic.substring(topic.lastIndexOf("/") + 1);
            String valueStr = payload.trim();

            if (feed.equals("fan") || feed.equals("led") || feed.equals("water-pump")) {
                DeviceStatus device = new DeviceStatus();
                device.setDevice(feed);
                device.setStatus(valueStr);
                device.setUpdatedAt(createdAt);
                deviceStatusRepository.save(device);
            } else {
                SensorData sensor = new SensorData();
                sensor.setType(feed);
                sensor.setValue(Double.parseDouble(valueStr));
                sensor.setCreatedAt(createdAt);
                sensorDataRepository.save(sensor);
            }

            log.info("Saved data: {} | Time: {} | Value: {}", feed, createdAt, valueStr);
        } catch (Exception e) {
            log.error("Error parsing MQTT message: {}", payload, e);
        }
    }

}
