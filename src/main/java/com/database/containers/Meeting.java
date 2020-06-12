package com.database.containers;

import com.database.containers.pcontainers.Event;
import com.database.containers.pcontainers.Group;
import com.database.containers.pcontainers.Member;
import com.database.containers.pcontainers.Venue;

import java.math.BigInteger;

public class Meeting {
    public Venue venue;
    public String visibility;
    public String response;
    public int guests;
    public Member member;
    public int rsvp_id;
    public BigInteger mtime;
    public Event event;
    public Group group;
}
