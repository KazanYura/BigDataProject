create schema BigDataProject default charset 'utf8';
create table cities
(
    ID        int auto_increment
        primary key,
    cityName  varchar(255) not null,
    countryID int          null,
    constraint cities_ibfk_1
        foreign key (countryID) references countries (ID)
);
create table countries
(
    ID          int auto_increment
        primary key,
    countryName varchar(255) not null
);
create table countryTopic
(
    timestampID bigint       not null,
    country     varchar(255) not null,
    topic       varchar(255) not null,
    frequency   int          not null,
    primary key (timestampID, country, topic, frequency)
);
create table eventtable
(
    ID        varchar(255) not null
        primary key,
    eventName varchar(255) not null,
    eventTime bigint       not null,
    groupID   int          not null,
    constraint eventtable_ibfk_1
        foreign key (groupID) references grouptable (ID)
);
create table grouptable
(
    ID          int          not null
        primary key,
    groupName   varchar(255) not null,
    groupCityID int          not null,
    constraint grouptable_ibfk_1
        foreign key (groupCityID) references cities (ID)
);
create table grouptopic
(
    ID        int auto_increment
        primary key,
    topicName varchar(255) not null,
    groupID   int          not null,
    constraint grouptopic_ibfk_1
        foreign key (groupID) references grouptable (ID)
);
create table statesstat
(
    timestampID bigint       not null,
    state       varchar(255) not null,
    nameV       varchar(255) not null,
    primary key (timestampID, state, nameV)
);
create table windowstorage
(
    timestampID   bigint       not null
        primary key,
    countriesJSON varchar(255) not null
);
