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

            client = new MqttClient(BROKER_URL, CLIENT_ID);
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
            e.printStackTrace();
        }
    }

    private void handleIncomingData(String topic, String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payload);

            String createdAtStr = jsonNode.get("created_at").asText();
            String valueStr = jsonNode.get("value").asText();

            // Chuyển đổi thời gian từ Adafruit IO về LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ssa");
            LocalDateTime createdAt = LocalDateTime.parse(createdAtStr, formatter);

            // Lấy tên thiết bị hoặc cảm biến
            String feed = topic.substring(topic.lastIndexOf("/") + 1);

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

            log.info("Saved data: {} | Time: {} | Value: {}", feed, createdAtStr, valueStr);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error parsing MQTT message: {}", payload);
        }
    }
}
