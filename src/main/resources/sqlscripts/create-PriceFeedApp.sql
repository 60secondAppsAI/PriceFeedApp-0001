CREATE DATABASE IF NOT EXISTS PriceFeedApp;

CREATE TABLE PriceFeedApp.price_feeds (
    `price_feed_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `provider` VARCHAR(255),
    `feed_type` VARCHAR(255),
    `status` VARCHAR(255),
PRIMARY KEY (price_feed_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PriceFeedApp.price_subscriptions (
    `price_subscription_id` INT AUTO_INCREMENT,
    `price_feed_id` INT,
    `instrument_id` INT,
    `subscription_type` VARCHAR(255),
    `active` BOOLEAN,
PRIMARY KEY (price_subscription_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PriceFeedApp.price_ticks (
    `price_tick_id` INT AUTO_INCREMENT,
    `price_feed_id` INT,
    `instrument_id` INT,
    `price` DOUBLE,
    `size` DOUBLE,
    `received_at` DATETIME,
PRIMARY KEY (price_tick_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PriceFeedApp.feed_healths (
    `feed_health_id` INT AUTO_INCREMENT,
    `price_feed_id` INT,
    `status` VARCHAR(255),
    `latency_ms` INT,
    `measured_at` DATETIME,
PRIMARY KEY (feed_health_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
