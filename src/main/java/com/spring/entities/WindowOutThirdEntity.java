package com.spring.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="countryTopic")
public class WindowOutThirdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long timestampID;

    @Column(name="country")
    private String country;

    @Column(name="topic")
    private String topic;

    @Column(name="frequency")
    private int frequency;

    public long getTimestampID() {
        return timestampID;
    }

    public void setTimestampID(long timestampID) {
        this.timestampID = timestampID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WindowOutThirdEntity that = (WindowOutThirdEntity) o;
        return timestampID == that.timestampID &&
                frequency == that.frequency &&
                country.equals(that.country) &&
                topic.equals(that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestampID, country, topic, frequency);
    }
}
