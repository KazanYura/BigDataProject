package com.spring.entities;

import javax.persistence.*;

@Entity
@Table(name="statesstat")
public class WindowOutSecondEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long timestampID;

    public long getTimestampID() {
        return timestampID;
    }

    public void setTimestampID(long timestampID) {
        this.timestampID = timestampID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="state")
    private String state;

    @Column(name="nameV")
    private String name;
}