package com.database.databasewriter;

import com.database.containers.Meeting;
import com.database.containers.Window2Container;
import com.database.containers.Window3Container;
import com.database.containers.WindowPerMinuteContainer;
import com.database.containers.pcontainers.GroupTopic;

import java.sql.*;
import java.util.List;

public class DatabaseWriter {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/BigDataProject";
    private static final String user = "root";
    private static final String password = "initial";
    private static final String[] simpleTables = {"Countries", "Cities", "GroupTopic"};
    // JDBC variables for opening and managing connection
    private static Connection con;

    static {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static Statement stmt;

    public void write(Meeting meeting) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // opening database connection to MySQL server

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            ResultSet rs = writeCountry(meeting);
            int countryID = rs.getInt("ID");
            rs = writeCity(meeting, countryID);
            int cityID = rs.getInt("ID");
            writeGroup(meeting,cityID);
            writeGroupTopic(meeting);

            writeEvent(meeting);
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }
    public void writeWindow3(Window3Container container) throws ClassNotFoundException, SQLException, InterruptedException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement select = con.prepareStatement("SELECT * from countryTopic where timestampID = ? and country = ? and topic = ?");
        select.setLong(1, container.time);
        select.setString(2,container.country);
        select.setString(3,container.topic);
        ResultSet rs = select.executeQuery();
        PreparedStatement preparedStatement;
        if (!rs.next()) {
            // opening database connection to MySQL server

            preparedStatement = con.prepareStatement("INSERT into countryTopic (timestampID,country,topic,frequency) values (?,?,?,?);");
            preparedStatement.setLong(1, container.time);
            preparedStatement.setString(2, container.country);
            preparedStatement.setString(3, container.topic);
            preparedStatement.setInt(4, container.acc);

        }
        else{
            int f = rs.getInt("frequency");
            preparedStatement = con.prepareStatement("update countryTopic set frequency = ? where timestampID = ? and country = ? and topic = ?;");
            preparedStatement.setInt(1, f + container.acc);
            preparedStatement.setLong(2, container.time);
            preparedStatement.setString(3, container.country);
            preparedStatement.setString(4, container.topic);

        }
        preparedStatement.executeUpdate();
        Thread.sleep(100);

    }
    public void writeWindow2(Window2Container container) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        // opening database connection to MySQL server
        PreparedStatement preparedStatement = con.prepareStatement("INSERT into statesstat (timestampID,state,nameV) values (?,?,?);");
        preparedStatement.setLong(1,container.time);
        preparedStatement.setString(2,container.state);
        preparedStatement.setString(3,container.name);
        preparedStatement.executeUpdate();
    }
    public void writeWindow(WindowPerMinuteContainer<Integer> container) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        // opening database connection to MySQL server
        PreparedStatement preparedStatement = con.prepareStatement("INSERT into windowstorage (timestampID,countriesJSON) values (?,?);");
        preparedStatement.setLong(1,container.time);
        preparedStatement.setString(2,container.countries);
        preparedStatement.executeUpdate();
    }
    private void writeEvent(Meeting meeting) throws SQLException {
        PreparedStatement select = con.prepareStatement("SELECT * from EventTable where ID = ?");
        select.setString(1, meeting.event.event_id);
        ResultSet rs = select.executeQuery();
        if (!rs.next()) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO EventTable (ID,eventName,eventTime,groupID) VALUES (?,?,?,?);");
            preparedStatement.setString(1, meeting.event.event_id);
            preparedStatement.setString(2, meeting.event.event_name);
            preparedStatement.setLong(3, meeting.event.time);
            preparedStatement.setInt(4, meeting.group.group_id);
            preparedStatement.executeUpdate();
        }
    }
    private void writeGroup(Meeting meeting,int cityID) throws SQLException {
        PreparedStatement select = con.prepareStatement("SELECT * from GroupTable where ID = ?");
        select.setInt(1, meeting.group.group_id);
        ResultSet rs = select.executeQuery();
        if (!rs.next()) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO GroupTable (ID,groupName,groupCityID) VALUES (?,?,?);");
            preparedStatement.setInt(1, meeting.group.group_id);
            preparedStatement.setString(2, meeting.group.group_name);
            preparedStatement.setInt(3, cityID);
            preparedStatement.executeUpdate();
        }
    }

    private void writeGroupTopic(Meeting meeting) throws SQLException {
        for (GroupTopic topic : meeting.group.group_topics) {
            PreparedStatement select = con.prepareStatement("SELECT * from GroupTopic where topicName = ? and groupID = ?");
            select.setString(1, topic.topic_name);
            select.setInt(2, meeting.group.group_id);
            ResultSet rs = select.executeQuery();
            if (!rs.next()) {
                PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO GroupTopic (topicName,groupID) VALUES (?,?);");
                preparedStatement.setString(1, topic.topic_name);
                preparedStatement.setInt(2, meeting.group.group_id);
                preparedStatement.executeUpdate();
            }
        }

    }

    private ResultSet writeCity(Meeting meeting, int countryID) throws SQLException {
        PreparedStatement select = con.prepareStatement("SELECT * from Cities where cityName = ?");
        select.setString(1, meeting.group.group_city);
        ResultSet rs = select.executeQuery();
        if (!rs.next()) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Cities (cityName, countryID) VALUES (?,?);");
            preparedStatement.setString(1, meeting.group.group_city);
            preparedStatement.setInt(2, countryID);
            preparedStatement.executeUpdate();
        }
        rs = select.executeQuery();
        rs.next();
        return rs;
    }

    private ResultSet writeCountry(Meeting meeting) throws SQLException {
        PreparedStatement select = con.prepareStatement("SELECT * from Countries where countryName = ?");
        select.setString(1, meeting.group.group_country);
        ResultSet rs = select.executeQuery();
        if (!rs.next()) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Countries (countryName) VALUES (?);");
            preparedStatement.setString(1, meeting.group.group_country);
            preparedStatement.executeUpdate();
        }
        rs = select.executeQuery();
        rs.next();
        return rs;
    }
}