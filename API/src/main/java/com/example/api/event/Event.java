package com.example.api.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;

@NoArgsConstructor
@Getter
public class Event<K, T> {

    public enum Type {
        CREATE,
        DELETE
    }

    private Type eventType;
    private K key;
    private T data;

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime eventCreatedAt;

    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = now();
    }
}

