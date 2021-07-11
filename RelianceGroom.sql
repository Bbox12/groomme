-- phpMyAdmin SQL Dump
-- version 4.0.10deb1ubuntu0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 11, 2021 at 07:41 PM
-- Server version: 5.5.62-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Groom`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_login_data`
--

CREATE TABLE IF NOT EXISTS `admin_login_data` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(200) NOT NULL,
  `LastName` varchar(200) NOT NULL,
  `Role` int(11) NOT NULL,
  `StaffID` varchar(200) NOT NULL,
  `Email` varchar(200) NOT NULL,
  `Photo` varchar(200) NOT NULL DEFAULT 'logo.png',
  `PhoneNo` varchar(200) NOT NULL,
  `Password` varchar(200) NOT NULL,
  `loginDate` date NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  `isStaff` tinyint(1) NOT NULL DEFAULT '0',
  `isOffice` tinyint(1) NOT NULL DEFAULT '0',
  `isVerified` tinyint(1) NOT NULL DEFAULT '0',
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `D_Date` date NOT NULL,
  `D_Time` time NOT NULL,
  `D_User` varchar(200) NOT NULL,
  `D_IP` varchar(200) NOT NULL,
  `Reference_Code` varchar(200) DEFAULT NULL,
  `AppInstallation_Date` date NOT NULL,
  `AppInstallation_Time` time NOT NULL,
  `FirebaseToken` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `User` varchar(200) NOT NULL,
  `Time` time NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `admin_login_data`
--

INSERT INTO `admin_login_data` (`ID`, `FirstName`, `LastName`, `Role`, `StaffID`, `Email`, `Photo`, `PhoneNo`, `Password`, `loginDate`, `isAdmin`, `isStaff`, `isOffice`, `isVerified`, `isDeleted`, `D_Date`, `D_Time`, `D_User`, `D_IP`, `Reference_Code`, `AppInstallation_Date`, `AppInstallation_Time`, `FirebaseToken`, `Date`, `User`, `Time`, `IP`) VALUES
(2, 'Admin', '123', 0, 'ADMIN123', 'admin@hmail.com', 'logo.png', '9999999999', '123456', '2019-10-26', 1, 0, 0, 1, 0, '0000-00-00', '00:00:00', '', '', NULL, '0000-00-00', '00:00:00', '', '0000-00-00', '', '00:00:00', ''),
(7, 'Parag', 'Deka', 2, 'Parag241', 'parag.moni44@gmsil.com', 'artboard.png', '7002608241', '123456', '0000-00-00', 0, 0, 1, 0, 0, '0000-00-00', '00:00:00', '', '', NULL, '0000-00-00', '00:00:00', '', '2020-02-18', 'ADMIN123', '17:11:41', '47.29.140.135');

-- --------------------------------------------------------

--
-- Table structure for table `app_version`
--

CREATE TABLE IF NOT EXISTS `app_version` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `Version` int(11) NOT NULL,
  `Importance` tinyint(4) NOT NULL,
  `cancellationcharge` float NOT NULL DEFAULT '0',
  `Date` date DEFAULT NULL,
  `Time` time DEFAULT NULL,
  `User` varchar(255) DEFAULT NULL,
  `IP` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `app_version`
--

INSERT INTO `app_version` (`ID`, `Version`, `Importance`, `cancellationcharge`, `Date`, `Time`, `User`, `IP`) VALUES
(1, 7, 0, 200, '2018-03-14', '15:30:00', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `booking_details_service`
--

CREATE TABLE IF NOT EXISTS `booking_details_service` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDbooking` int(11) NOT NULL,
  `IDservice` int(11) NOT NULL,
  `Price` float(10,2) NOT NULL,
  `Duration` int(11) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `booking_details_service`
--

INSERT INTO `booking_details_service` (`ID`, `IDbooking`, `IDservice`, `Price`, `Duration`, `Date`, `Time`) VALUES
(1, 1, 9, 350.00, 30, '2020-04-29', '16:10:00'),
(2, 1, 10, 500.00, 45, '2020-04-29', '16:10:00'),
(3, 2, 10, 650.00, 60, '2020-04-29', '20:31:00'),
(4, 4, 9, 350.00, 30, '2020-05-06', '05:51:00'),
(5, 5, 10, 500.00, 45, '2020-05-07', '20:23:00'),
(7, 7, 10, 500.00, 45, '2020-10-27', '20:33:00'),
(8, 7, 9, 350.00, 30, '2020-10-27', '20:33:00'),
(9, 6, 4, 45.00, 45, '2020-10-27', '22:08:00'),
(10, 8, 11, 200.00, 45, '2021-02-14', '23:33:00');

-- --------------------------------------------------------

--
-- Table structure for table `final_services`
--

CREATE TABLE IF NOT EXISTS `final_services` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SecondaryServiceID` int(11) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Image` varchar(200) NOT NULL DEFAULT 'logo.png',
  `isActive` tinyint(4) NOT NULL DEFAULT '1',
  `Ladies` tinyint(1) NOT NULL DEFAULT '0',
  `Gents` tinyint(1) NOT NULL DEFAULT '0',
  `Bridal` tinyint(1) NOT NULL DEFAULT '0',
  `Tattoo` tinyint(4) NOT NULL DEFAULT '0',
  `Kids` tinyint(1) NOT NULL DEFAULT '0',
  `Highlighted1` tinyint(1) NOT NULL DEFAULT '0',
  `Highlighted2` tinyint(1) NOT NULL DEFAULT '0',
  `Highlighted3` tinyint(1) NOT NULL DEFAULT '0',
  `Highlighted4` tinyint(1) NOT NULL DEFAULT '0',
  `Highlighted5` tinyint(1) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=45 ;

--
-- Dumping data for table `final_services`
--

INSERT INTO `final_services` (`ID`, `SecondaryServiceID`, `Name`, `Image`, `isActive`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `Highlighted1`, `Highlighted2`, `Highlighted3`, `Highlighted4`, `Highlighted5`, `Date`, `Time`, `User`, `IP`) VALUES
(1, 2, 'Facials and Clean-Ups', '', 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, '2020-03-16', '07:50:59', 'ADMIN123', '47.29.242.111'),
(2, 2, 'Detanning', 'Detanning.jpg', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '0000-00-00', '00:00:00', '', ''),
(3, 2, 'Bleach', '', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '07:51:10', 'ADMIN123', '47.29.242.111'),
(4, 7, 'Messages', '', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '19:58:52', 'ADMIN123', '47.29.195.212'),
(5, 7, 'Body Spa', '', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '07:51:57', 'ADMIN123', '47.29.242.111'),
(6, 4, 'Pedicure', 'Pedicure.jpg', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '0000-00-00', '00:00:00', '', ''),
(7, 1, 'Manicure', 'logo.png', 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '0000-00-00', '00:00:00', '', ''),
(8, 7, 'Waxing', '', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '07:51:39', 'ADMIN123', '47.29.242.111'),
(9, 6, 'Threading', 'logo.png', 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, '0000-00-00', '00:00:00', '', ''),
(10, 1, 'Hair Style', 'logo.png', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '0000-00-00', '00:00:00', '', ''),
(11, 6, 'Hair Colour', '', 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, '2020-03-15', '22:32:54', 'ADMIN123', '47.29.198.80'),
(12, 1, 'Hair Cut', 'logo.png', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '0000-00-00', '00:00:00', '', ''),
(13, 7, 'Hair Spa', '', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '20:08:16', 'ADMIN123', '47.29.195.212'),
(14, 1, 'Hair Straightening', 'logo.png', 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, '0000-00-00', '00:00:00', '', ''),
(23, 7, 'FACE MASSAGE', '', 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, '2020-03-15', '22:32:22', 'ADMIN123', '47.29.198.80'),
(24, 3, 'Light Blonde with Side Swept Bangs', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '15:58:43', 'ADMIN123', '47.29.195.212'),
(25, 3, 'Light Brunette', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '15:58:56', 'ADMIN123', '47.29.195.212'),
(26, 3, 'Purple with Asymmetrical Bangs', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '15:59:07', 'ADMIN123', '47.29.195.212'),
(27, 3, 'Light Blonde with Layered Bangs', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '15:59:39', 'ADMIN123', '47.29.195.212'),
(28, 9, 'Brunette', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '16:00:36', 'ADMIN123', '47.29.195.212'),
(29, 9, 'Light Blonde with Asymmetrical Bangs', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '16:00:50', 'ADMIN123', '47.29.195.212'),
(30, 9, 'Light Golden Blonde with Side Swept Bangs', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '16:01:02', 'ADMIN123', '47.29.195.212'),
(31, 10, 'Layered Bangs and Light Blonde Highlights', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '16:01:53', 'ADMIN123', '47.29.195.212'),
(32, 10, 'Black with Blunt Cut Bangs', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '16:02:19', 'ADMIN123', '47.29.195.212'),
(33, 11, 'Light Platinum Blonde', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '16:02:59', 'ADMIN123', '47.29.195.212'),
(34, 3, 'Brunette', '', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '2020-03-16', '20:09:06', 'ADMIN123', '47.29.195.212'),
(35, 11, 'Light Blonde', 'logo.png', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '16:03:27', 'ADMIN123', '47.29.195.212'),
(36, 12, 'Basic Manicure', 'logo.png', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '2020-03-16', '20:00:05', 'ADMIN123', '47.29.195.212'),
(37, 12, 'French Manicure', 'logo.png', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '2020-03-16', '20:00:22', 'ADMIN123', '47.29.195.212'),
(38, 12, 'Reverse French Manicure', 'logo.png', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '2020-03-16', '20:00:38', 'ADMIN123', '47.29.195.212'),
(39, 12, 'Paraffin Manicure', 'logo.png', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '2020-03-16', '20:00:54', 'ADMIN123', '47.29.195.212'),
(40, 2, 'Fruit Facial', 'logo.png', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '2020-03-16', '20:21:11', 'ADMIN123', '47.29.195.212'),
(41, 4, 'Regular Pedicure', '', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '2020-03-16', '20:53:24', 'ADMIN123', '47.29.195.212'),
(42, 4, 'White Tea Vitality Pedicure', '', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '2020-03-16', '20:53:36', 'ADMIN123', '47.29.195.212'),
(43, 4, 'Candy Crush Pedicure', '', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, '2020-03-16', '20:53:07', 'ADMIN123', '47.29.195.212'),
(44, 4, 'Chocolate Pedicure', '', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2020-03-16', '20:52:55', 'ADMIN123', '47.29.195.212');

-- --------------------------------------------------------

--
-- Table structure for table `jobrole`
--

CREATE TABLE IF NOT EXISTS `jobrole` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Role` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `jobrole`
--

INSERT INTO `jobrole` (`ID`, `Role`) VALUES
(1, 'Administrator'),
(2, 'Office Staff'),
(3, 'Field Executive');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDBooking` int(11) NOT NULL,
  `IDUser` int(11) NOT NULL,
  `IDSalon` int(11) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `PhoneNo` varchar(200) NOT NULL,
  `Msg` mediumtext NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `parlour_service`
--

CREATE TABLE IF NOT EXISTS `parlour_service` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ParlourID` int(11) NOT NULL,
  `SecondayID` int(11) NOT NULL,
  `pservice` varchar(100) NOT NULL,
  `pservice_pic` varchar(200) DEFAULT 'logo.png',
  `Ladies` tinyint(4) DEFAULT '0',
  `Gents` tinyint(4) NOT NULL DEFAULT '0',
  `Bridal` tinyint(4) NOT NULL DEFAULT '0',
  `Tattoo` tinyint(4) NOT NULL DEFAULT '0',
  `Kids` tinyint(4) NOT NULL DEFAULT '0',
  `Price` float(10,2) NOT NULL,
  `Discount` float(10,2) NOT NULL,
  `FinalPrice` float(10,2) NOT NULL,
  `qoutes` varchar(200) NOT NULL,
  `Popular` tinyint(1) NOT NULL DEFAULT '0',
  `Deals` tinyint(1) NOT NULL DEFAULT '0',
  `new` tinyint(1) NOT NULL DEFAULT '0',
  `Duration` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `policies`
--

CREATE TABLE IF NOT EXISTS `policies` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(200) NOT NULL,
  `Details` varchar(200) NOT NULL,
  `Links` varchar(200) NOT NULL,
  `uploadToServer` varchar(200) NOT NULL,
  `isActive` tinyint(1) NOT NULL DEFAULT '0',
  `D_Date` date NOT NULL,
  `D_Time` time NOT NULL,
  `D_User` varchar(200) NOT NULL,
  `D_IP` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `policies`
--

INSERT INTO `policies` (`ID`, `Title`, `Details`, `Links`, `uploadToServer`, `isActive`, `D_Date`, `D_Time`, `D_User`, `D_IP`, `Date`, `Time`, `User`, `IP`) VALUES
(3, 'App usage', 'App usage new policy', 'http://139.59.38.160/Groom/Dashboard/NewPolicy.php', '', 1, '0000-00-00', '00:00:00', '', '', '2020-02-18', '00:00:00', '19:22:32', '47.29.140.135'),
(4, 'SUPER SAVER', 'SAVE 50% ON SUBSCRIPTION', '90', '47.29.210.26', 1, '0000-00-00', '00:00:00', '', '', '2020-03-06', '14:04:39', '', 'ADMIN123'),
(5, 'SUPER SAVER', 'SAVE 50% ON SUBSCRIPTION', '90', '47.29.210.26', 1, '0000-00-00', '00:00:00', '', '', '2020-03-06', '14:05:12', '', 'ADMIN123');

-- --------------------------------------------------------

--
-- Table structure for table `primary_services`
--

CREATE TABLE IF NOT EXISTS `primary_services` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(200) NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `isActive` tinyint(4) NOT NULL DEFAULT '1',
  `Ladies` tinyint(1) NOT NULL DEFAULT '0',
  `Gents` tinyint(1) NOT NULL DEFAULT '0',
  `Bridal` tinyint(1) NOT NULL DEFAULT '0',
  `Tattoo` tinyint(1) NOT NULL DEFAULT '0',
  `Kids` tinyint(1) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `primary_services`
--

INSERT INTO `primary_services` (`ID`, `Name`, `Photo`, `isActive`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `Date`, `Time`, `User`, `IP`) VALUES
(1, 'HAIR', 'hair-icon-png-29.png', 1, 1, 1, 0, 0, 1, '2020-04-25', '21:10:14', 'ADMIN123', '47.29.146.79'),
(2, 'SKIN', 'skin.png', 1, 1, 1, 1, 1, 1, '0000-00-00', '00:00:00', '', ''),
(3, 'BODY', 'body.png', 1, 1, 1, 1, 0, 1, '0000-00-00', '00:00:00', '', ''),
(4, 'MAKEUP', 'makeup.png', 1, 1, 0, 1, 0, 0, '0000-00-00', '00:00:00', '', ''),
(5, 'HAND', 'hand.png', 0, 0, 0, 0, 1, 0, '2020-04-30', '00:00:00', '22:18:04', '102.182.107.248'),
(13, 'FEET', 'ic_feet.png', 0, 1, 1, 1, 0, 1, '2020-04-30', '00:00:00', '22:17:28', '102.182.107.248'),
(14, 'FACE', 'face.png', 1, 1, 1, 1, 0, 1, '2020-03-06', '06:36:01', 'ADMIN123', '47.29.219.240'),
(15, 'Manicure', 'spa1.png', 0, 1, 1, 1, 0, 1, '2020-04-30', '00:00:00', '22:19:21', '102.182.107.248');

-- --------------------------------------------------------

--
-- Table structure for table `push_message`
--

CREATE TABLE IF NOT EXISTS `push_message` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `StaffID` int(11) NOT NULL,
  `Message` varchar(200) NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `push_message`
--

INSERT INTO `push_message` (`ID`, `StaffID`, `Message`, `Photo`, `Date`, `Time`, `User`, `IP`) VALUES
(1, 5, 'hi', '', '2020-05-02', '12:07:31', '', '157.42.251.76');

-- --------------------------------------------------------

--
-- Table structure for table `salon_category`
--

CREATE TABLE IF NOT EXISTS `salon_category` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Category` varchar(200) NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `salon_category`
--

INSERT INTO `salon_category` (`ID`, `Category`, `Photo`, `Date`) VALUES
(1, 'Ladies', 'http://139.59.38.160/Groom/Dashboard/category/grl.png', '2020-02-27 09:58:52'),
(2, 'Gents', 'http://139.59.38.160/Groom/Dashboard/category/man.png', '2020-02-27 09:59:26'),
(4, 'Kids', 'http://139.59.38.160/Groom/Dashboard/category/kid.png', '2020-03-17 03:26:34'),
(6, 'Bridal', 'http://139.59.38.160/Groom/Dashboard/category/bride.png', '2020-04-19 16:36:00');

-- --------------------------------------------------------

--
-- Table structure for table `salon_gallery`
--

CREATE TABLE IF NOT EXISTS `salon_gallery` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDsalon` int(11) NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `Details` varchar(200) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `salon_gallery`
--

INSERT INTO `salon_gallery` (`ID`, `IDsalon`, `Photo`, `Details`, `Date`) VALUES
(1, 1, 'IMG_20200428_18135322.jpg', 'NICE', '2020-04-28 12:44:03'),
(2, 2, 'IMG_20200429_16065922.jpg', 'NICE', '2020-04-29 10:38:03'),
(3, 11, 'IMG_20210214_19503722.jpg', 'TEST IMAGE', '2021-02-14 17:50:40');

-- --------------------------------------------------------

--
-- Table structure for table `salon_registration`
--

CREATE TABLE IF NOT EXISTS `salon_registration` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `parlour_name` varchar(100) NOT NULL,
  `parlour_about` varchar(200) NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `parlour_email` varchar(100) NOT NULL,
  `parlour_mobile` varchar(11) NOT NULL,
  `Password` varchar(200) NOT NULL,
  `Role` int(11) NOT NULL DEFAULT '2',
  `parlour_registration` varchar(200) NOT NULL,
  `parlour_address` varchar(200) NOT NULL,
  `parlour_city` varchar(200) NOT NULL,
  `parlour_locality` varchar(200) NOT NULL,
  `parlour_pin` varchar(11) NOT NULL,
  `websites` mediumtext NOT NULL,
  `service_location` int(11) NOT NULL DEFAULT '0',
  `latitude` float(10,6) DEFAULT '0.000000',
  `longitude` float(10,6) DEFAULT '0.000000',
  `isVerified` tinyint(1) NOT NULL DEFAULT '0',
  `VerifiedBy` int(11) NOT NULL,
  `VerifiedDate` date NOT NULL,
  `isHold` tinyint(1) NOT NULL DEFAULT '0',
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `Ladies` tinyint(4) NOT NULL DEFAULT '0',
  `Gents` tinyint(4) NOT NULL DEFAULT '0',
  `Bridal` tinyint(4) NOT NULL DEFAULT '0',
  `Tattoo` tinyint(4) NOT NULL DEFAULT '0',
  `Kids` tinyint(4) NOT NULL DEFAULT '0',
  `discountType` int(11) NOT NULL,
  `discountAmt` float(10,2) NOT NULL DEFAULT '0.00',
  `PopularSalon` tinyint(4) NOT NULL DEFAULT '0',
  `isNew` tinyint(4) NOT NULL DEFAULT '1',
  `serviceRating` float(10,2) NOT NULL DEFAULT '0.00',
  `serviceTotalRating` int(11) NOT NULL,
  `Reference_Code` varchar(200) NOT NULL,
  `FirebaseToken` varchar(254) NOT NULL,
  `loginDate` date NOT NULL,
  `AppInstallation_Date` date NOT NULL,
  `AppInstallation_Time` time NOT NULL,
  `isSechedule` tinyint(4) NOT NULL DEFAULT '0',
  `isSpecialist` tinyint(4) NOT NULL DEFAULT '0',
  `isLocation` tinyint(4) NOT NULL DEFAULT '0',
  `isServiceAt` tinyint(4) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `salon_registration`
--

INSERT INTO `salon_registration` (`ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `Password`, `Role`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `websites`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `isNew`, `serviceRating`, `serviceTotalRating`, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP`) VALUES
(1, 'Babylon Beauty Salon', 'THIS IS FOR Muktixcel EMAIL ADDRESS', 'IMG_20200429_183209.jpg', 'multixcel.shillong@gmail.com', 'NA', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, 'TEST556', 'CHANDMARI', 'GUWAHATI', 'CHANDMARI', '781019', '', 1, 26.094950, 91.795624, 1, 0, '0000-00-00', 0, 0, 1, 0, 0, 0, 1, 1, 10.00, 1, 1, 0.00, 0, '', 'dMwCoLM9Yok:APA91bF26cEbRzZ10H5CMB8e0p_uSKllLDMJxVimMGcJ-QROx_rhbamdSSIwA3OmRG_gnDoUARDR_EagSvTNrMT3HCAjLAA_NLcJzETOZ7mDH0Tk2RAkpH3BM0QkW0mqfN_X_Cb6c6QO', '0000-00-00', '2020-04-29', '15:02:13', 1, 1, 1, 1, '2020-04-29', '15:02:13', 'fe80::54b8:2ff:fe0d:aeeb%wlan0'),
(2, 'Changes Hair & Spa Beauty Salon', 'TEST2 FOR APP', 'IMG_20200428_202438.jpg', 'NA', '7002986817', '7c222fb2927d828af22f592134e8932480637c0d', 2, 'TEST12345', 'BELTOLA', 'Guwahati', 'Latakata', '781022', '', 2, 26.094725, 91.795731, 1, 0, '0000-00-00', 0, 0, 0, 1, 0, 0, 1, 1, 12.00, 1, 1, 0.00, 0, '', '', '0000-00-00', '2020-04-28', '16:55:00', 1, 1, 1, 1, '2020-04-28', '16:55:00', 'fe80::54b8:2ff:fe0d:aeeb%wlan0'),
(4, 'Elle spa and salon', 'TEST FOR INOW AGAIN', 'IMG_20200429_183740.jpg', 'inow.ani@gmail.com', 'NA', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, 'TEST456', 'SIXMILE', 'Guwahati', 'Latakata', '781022', '', 1, 26.094725, 91.795731, 1, 0, '0000-00-00', 0, 0, 1, 0, 0, 0, 1, 1, 15.00, 1, 1, 0.00, 0, '', 'fTrEUCq8z3U:APA91bGedm6_qfBSZagOaKwdp7FMTDCIkM4Xn7pnSe3eODbRMto8vafOe-WrBe9NQij1Jy4LV_AvlM1EMYooSrd0_T_nKvFkalI_15We2LcG40Wnnc_gR2wCyS4Rgvs3z6mY6ZvbyC3n', '0000-00-00', '2020-04-29', '15:07:49', 1, 1, 1, 1, '2020-04-29', '15:07:49', 'fe80::54b8:2ff:fe0d:aeeb%wlan0'),
(5, 'LAKME SALON BELTOLA', 'THIS IA ANOTHER TEST', 'IMG_20200515_131805.jpg', 'P@gmail.com', '9508453444', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, 'TEST78', 'INJAL SHOPPING COMPLEX', 'AMBIKAGIRINAGAR', 'GUWAHATI', '781005', '', 3, 26.094725, 91.795731, 1, 0, '0000-00-00', 0, 0, 1, 1, 1, 0, 1, 1, 8.00, 1, 1, 4.00, 1, '', 'eLPSUurd9y8:APA91bGfjVsu102b1xhMi-gH54yNnJhD9VRvCeZEACcSHKFK6a-DrFycc_uM8j9AmQyt1M9Rtg3YV9BCUAbl20olAnOJnbHbtaYAvBLolVivlK1BGyunoUH9X0lbfvEKu12Q6UOX3rJ9', '0000-00-00', '2020-05-15', '09:48:08', 1, 1, 1, 1, '2020-05-15', '09:48:08', 'fe80::4a09:b80c%rmnet2'),
(11, 'TESTMO1', 'MOE SALON OFFERING THE TRENDIEST CUTS AND STYLES', 'IMG_20210214_201846.jpg', 'NA', '0827865662', '7c222fb2927d828af22f592134e8932480637c0d', 2, '123456', '1', 'SANDTON', 'MAGALIESSIG', '2191', '', 3, -26.035568, 28.019852, 1, 2, '2020-07-12', 0, 0, 1, 1, 0, 0, 1, 0, 0.00, 0, 1, 5.00, 1, '', 'eLPSUurd9y8:APA91bGfjVsu102b1xhMi-gH54yNnJhD9VRvCeZEACcSHKFK6a-DrFycc_uM8j9AmQyt1M9Rtg3YV9BCUAbl20olAnOJnbHbtaYAvBLolVivlK1BGyunoUH9X0lbfvEKu12Q6UOX3rJ9', '0000-00-00', '2021-02-14', '20:18:49', 1, 1, 1, 1, '2021-02-14', '20:18:49', 'fe80::4f4e:990d:faeb:e612%wlan0');

-- --------------------------------------------------------

--
-- Table structure for table `schedule_clinic`
--

CREATE TABLE IF NOT EXISTS `schedule_clinic` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `salonID` int(11) NOT NULL,
  `sun_open_close` tinyint(1) DEFAULT '0',
  `sun_open_time` varchar(10) DEFAULT NULL,
  `sun_close_time` varchar(10) DEFAULT NULL,
  `mon_open_close` tinyint(1) DEFAULT '0',
  `mon_open_time` varchar(10) DEFAULT NULL,
  `mon_close_time` varchar(10) DEFAULT NULL,
  `tue_open_close` tinyint(1) DEFAULT '0',
  `tue_open_time` varchar(10) DEFAULT NULL,
  `tue_close_time` varchar(10) DEFAULT NULL,
  `wed_open_close` tinyint(1) DEFAULT '0',
  `wed_open_time` varchar(10) DEFAULT NULL,
  `wed_close_time` varchar(10) DEFAULT NULL,
  `thr_open_close` tinyint(1) DEFAULT '0',
  `thr_open_time` varchar(10) DEFAULT NULL,
  `thr_close_time` varchar(10) DEFAULT NULL,
  `fri_open_close` tinyint(1) DEFAULT '0',
  `fri_open_time` varchar(10) DEFAULT NULL,
  `fri_close_time` varchar(10) DEFAULT NULL,
  `sat_open_close` tinyint(1) DEFAULT '0',
  `sat_open_time` varchar(10) DEFAULT NULL,
  `sat_close_time` varchar(10) DEFAULT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `schedule_clinic`
--

INSERT INTO `schedule_clinic` (`ID`, `salonID`, `sun_open_close`, `sun_open_time`, `sun_close_time`, `mon_open_close`, `mon_open_time`, `mon_close_time`, `tue_open_close`, `tue_open_time`, `tue_close_time`, `wed_open_close`, `wed_open_time`, `wed_close_time`, `thr_open_close`, `thr_open_time`, `thr_close_time`, `fri_open_close`, `fri_open_time`, `fri_close_time`, `sat_open_close`, `sat_open_time`, `sat_close_time`, `Date`, `Time`) VALUES
(1, 1, 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', '2020-04-28', '14:34:09'),
(2, 2, 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', '2020-04-29', '11:59:31'),
(3, 3, 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', '2020-04-29', '14:12:38'),
(4, 4, 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 0, '', '', '2020-04-29', '15:08:35'),
(5, 5, 0, '', '', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', 0, '', '', '2020-04-29', '15:18:09'),
(6, 6, 0, '', '', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', '2020-04-30', '22:13:16'),
(7, 6, 0, 'CLOSE', 'CLOSE', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, 'CLOSE', 'CLOSE', '2020-04-30', '22:26:12'),
(8, 11, 0, '', '', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', '2020-06-13', '13:27:16');

-- --------------------------------------------------------

--
-- Table structure for table `schedule_parlour`
--

CREATE TABLE IF NOT EXISTS `schedule_parlour` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `salonID` int(11) NOT NULL,
  `sun_open_close` tinyint(1) DEFAULT '0',
  `sun_open_time` varchar(10) DEFAULT NULL,
  `sun_close_time` varchar(10) DEFAULT NULL,
  `mon_open_close` tinyint(1) DEFAULT '0',
  `mon_open_time` varchar(10) DEFAULT NULL,
  `mon_close_time` varchar(10) DEFAULT NULL,
  `tue_open_close` tinyint(1) DEFAULT '0',
  `tue_open_time` varchar(10) DEFAULT NULL,
  `tue_close_time` varchar(10) DEFAULT NULL,
  `wed_open_close` tinyint(1) DEFAULT '0',
  `wed_open_time` varchar(10) DEFAULT NULL,
  `wed_close_time` varchar(10) DEFAULT NULL,
  `thr_open_close` tinyint(1) DEFAULT '0',
  `thr_open_time` varchar(10) DEFAULT NULL,
  `thr_close_time` varchar(10) DEFAULT NULL,
  `fri_open_close` tinyint(1) DEFAULT '0',
  `fri_open_time` varchar(10) DEFAULT NULL,
  `fri_close_time` varchar(10) DEFAULT NULL,
  `sat_open_close` tinyint(1) DEFAULT '0',
  `sat_open_time` varchar(10) DEFAULT NULL,
  `sat_close_time` varchar(10) DEFAULT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `schedule_parlour`
--

INSERT INTO `schedule_parlour` (`ID`, `salonID`, `sun_open_close`, `sun_open_time`, `sun_close_time`, `mon_open_close`, `mon_open_time`, `mon_close_time`, `tue_open_close`, `tue_open_time`, `tue_close_time`, `wed_open_close`, `wed_open_time`, `wed_close_time`, `thr_open_close`, `thr_open_time`, `thr_close_time`, `fri_open_close`, `fri_open_time`, `fri_close_time`, `sat_open_close`, `sat_open_time`, `sat_close_time`, `Date`, `Time`) VALUES
(1, 1, 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', '2020-04-28', '14:34:09'),
(2, 2, 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', '2020-04-29', '11:59:31'),
(3, 3, 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', '2020-04-29', '14:12:38'),
(4, 4, 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', 0, '', '', 1, '8:00 AM', '5:00 PM', 0, '', '', 0, '', '', '2020-04-29', '15:08:35'),
(5, 5, 0, '', '', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', 0, '', '', '2020-04-29', '15:18:09'),
(6, 6, 0, '', '', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, '', '', '2020-04-30', '22:13:16'),
(7, 6, 0, 'CLOSE', 'CLOSE', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 0, 'CLOSE', 'CLOSE', '2020-04-30', '22:26:12'),
(8, 11, 0, '', '', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', '2020-06-13', '13:27:16'),
(9, 11, 0, 'CLOSED', 'CLOSED', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', 1, '8:00 AM', '5:00 PM', '2021-02-14', '19:47:27');

-- --------------------------------------------------------

--
-- Table structure for table `secondary_service`
--

CREATE TABLE IF NOT EXISTS `secondary_service` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDPrimaryService` int(11) NOT NULL,
  `Service` varchar(200) NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `isActive` tinyint(4) NOT NULL DEFAULT '1',
  `Ladies` int(11) NOT NULL DEFAULT '0',
  `Gents` int(11) NOT NULL DEFAULT '0',
  `Bridal` int(11) NOT NULL DEFAULT '0',
  `Kids` int(11) NOT NULL DEFAULT '0',
  `Tattoo` int(11) NOT NULL DEFAULT '0',
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `secondary_service`
--

INSERT INTO `secondary_service` (`ID`, `IDPrimaryService`, `Service`, `Photo`, `isActive`, `Ladies`, `Gents`, `Bridal`, `Kids`, `Tattoo`, `Date`) VALUES
(1, 1, 'HAIR HIGHLIGHT', 'istockphoto-888267866-170667a.png', 0, 1, 1, 0, 1, 0, '2020-02-11 11:29:36'),
(2, 2, 'FACIAL', '', 1, 1, 1, 1, 1, 0, '2020-02-11 11:29:36'),
(3, 1, 'HAIR CUT', '', 1, 1, 1, 1, 1, 0, '2020-02-11 11:30:13'),
(4, 13, 'PEDICURE', '', 1, 1, 1, 1, 1, 0, '2020-02-11 11:30:13'),
(5, 1, 'HAIR STRAIGHT', '', 0, 1, 1, 1, 1, 0, '2020-02-11 11:30:28'),
(6, 1, 'HAIR STYLE', '', 1, 1, 1, 0, 1, 0, '2020-02-18 19:52:44'),
(7, 3, 'SPA AND MASSAGE', '', 1, 1, 1, 0, 0, 0, '2020-03-15 19:32:07'),
(8, 3, 'SPIKES', '', 0, 1, 1, 0, 1, 0, '2020-03-15 19:57:36'),
(9, 1, 'Asymmetrical Haircuts', '', 1, 1, 1, 0, 0, 0, '2020-03-16 14:00:18'),
(10, 1, 'Layered Hairstyles', '', 1, 1, 0, 0, 0, 0, '2020-03-16 14:01:33'),
(11, 1, 'Mohawks', '', 0, 1, 0, 0, 0, 0, '2020-03-16 14:02:40'),
(12, 5, 'MANICURE', '', 1, 1, 1, 0, 0, 0, '2020-03-16 17:59:30'),
(13, 13, 'Pedicure', '', 0, 1, 1, 1, 0, 0, '2020-03-16 18:50:19');

-- --------------------------------------------------------

--
-- Table structure for table `servicesdetails`
--

CREATE TABLE IF NOT EXISTS `servicesdetails` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SalonID` int(11) NOT NULL,
  `FinalServiceID` int(11) NOT NULL,
  `Details_salon` varchar(200) NOT NULL,
  `Price_salon` float(10,2) NOT NULL,
  `Time_salon` int(11) NOT NULL,
  `Details_home` varchar(200) NOT NULL,
  `Price_home` float(10,2) NOT NULL,
  `Time_home` int(11) NOT NULL,
  `Highlighted1` tinyint(1) NOT NULL DEFAULT '0',
  `Highlighted2` tinyint(1) NOT NULL DEFAULT '0',
  `Highlighted3` tinyint(1) NOT NULL DEFAULT '0',
  `Highlighted4` tinyint(1) NOT NULL DEFAULT '0',
  `Highlighted5` tinyint(1) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `servicesdetails`
--

INSERT INTO `servicesdetails` (`ID`, `SalonID`, `FinalServiceID`, `Details_salon`, `Price_salon`, `Time_salon`, `Details_home`, `Price_home`, `Time_home`, `Highlighted1`, `Highlighted2`, `Highlighted3`, `Highlighted4`, `Highlighted5`, `Date`, `Time`, `User`, `IP`) VALUES
(1, 1, 24, 'GOOD', 120.00, 30, '', 0.00, 0, 0, 0, 0, 0, 0, '2020-04-28', '14:45:02', '1', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(2, 1, 9, 'GOOD', 450.00, 45, '', 0.00, 0, 0, 0, 0, 0, 0, '2020-04-28', '14:45:22', '1', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(3, 1, 5, '', 300.00, 30, '', 0.00, 0, 0, 0, 0, 0, 0, '2020-04-28', '14:52:00', '1', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(4, 2, 25, '', 0.00, 0, '120', 45.00, 45, 0, 0, 0, 0, 0, '2020-04-29', '12:24:36', '2', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(5, 2, 9, '', 0.00, 0, 'NICE', 420.00, 45, 0, 0, 0, 0, 0, '2020-04-29', '12:47:40', '2', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(6, 4, 28, 'NICE', 120.00, 30, '', 0.00, 0, 0, 0, 0, 0, 0, '2020-04-29', '15:09:42', '4', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(7, 4, 11, '', 350.00, 35, '', 0.00, 0, 0, 0, 0, 0, 0, '2020-04-29', '15:09:57', '4', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(8, 4, 30, '', 500.00, 60, '', 0.00, 0, 0, 0, 0, 0, 0, '2020-04-29', '15:10:18', '4', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(9, 5, 25, '', 350.00, 30, '', 0.00, 0, 0, 0, 0, 0, 0, '2020-04-29', '15:20:39', '5', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(10, 5, 40, '', 500.00, 45, '', 650.00, 60, 0, 0, 0, 0, 0, '2020-04-29', '15:21:39', '5', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(11, 11, 1, '', 200.00, 45, '', 350.00, 60, 0, 0, 0, 0, 0, '2021-02-14', '19:48:52', '11', 'FE80::4F4E:990D:FAEB:E612%WLAN0');

-- --------------------------------------------------------

--
-- Table structure for table `service_location_at`
--

CREATE TABLE IF NOT EXISTS `service_location_at` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Location` varchar(200) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `service_location_at`
--

INSERT INTO `service_location_at` (`ID`, `Location`, `Date`) VALUES
(1, 'SALON', '2020-02-08 07:01:30'),
(2, 'HOME', '2020-02-08 07:01:30'),
(3, 'BOTH', '2020-02-08 07:01:35');

-- --------------------------------------------------------

--
-- Table structure for table `specialist_detail`
--

CREATE TABLE IF NOT EXISTS `specialist_detail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `crew_name` varchar(45) DEFAULT NULL,
  `crew_detail` varchar(100) DEFAULT NULL,
  `crew_pic` varchar(100) DEFAULT NULL,
  `ParlourID` int(11) NOT NULL,
  `service` varchar(200) NOT NULL,
  `available` tinyint(1) DEFAULT '0',
  `isPopular` tinyint(4) NOT NULL DEFAULT '0',
  `Rating` float(10,2) NOT NULL,
  `TotalRating` int(11) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `specialist_detail`
--

INSERT INTO `specialist_detail` (`ID`, `crew_name`, `crew_detail`, `crew_pic`, `ParlourID`, `service`, `available`, `isPopular`, `Rating`, `TotalRating`, `Date`) VALUES
(1, 'Evadne Baker', '', 'IMG_20200429_18423422.jpg', 1, 'SKIN,BODY,HAND,FACE', 1, 1, 4.10, 5, '2020-04-28 12:35:09'),
(2, 'Michelle Botes', '', 'IMG_20200428_18125222.jpg', 1, 'HAIR,MAKEUP,FEET', 1, 0, 0.00, 0, '2020-04-28 12:42:58'),
(3, 'Ashley Callie', '', 'IMG_20200429_15295122.jpg', 2, 'SKIN,HAND,FACE,BODY', 1, 1, 3.80, 3, '2020-04-29 10:00:04'),
(4, 'Antoinette Louw', '', 'IMG_20200429_18390722.jpg', 4, 'SKIN,MAKEUP,BODY', 1, 1, 4.50, 4, '2020-04-29 13:09:14'),
(5, 'Michelle Mosalakae', '', 'IMG_20200429_18500522.jpg', 5, 'MAKEUP,FEET,HAIR', 1, 1, 4.00, 7, '2020-04-29 13:20:18'),
(7, 'TESTNATASHA', 'AMAZING HAIRDRESSER', 'IMG_20200510_15504122.jpg', 6, 'HAIR,MAKEUP', 1, 0, 0.00, 0, '2020-05-10 13:50:44'),
(8, 'DESIGNER 1', 'HDIBD', 'IMG_20210214_19521822.jpg', 11, 'HAIR,MAKEUP', 1, 0, 5.00, 1, '2020-06-13 11:30:07');

-- --------------------------------------------------------

--
-- Table structure for table `storeReview`
--

CREATE TABLE IF NOT EXISTS `storeReview` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDBooking` int(11) NOT NULL,
  `rating_i` float(10,2) NOT NULL,
  `rating_c` float(10,2) NOT NULL,
  `rating_s` float(10,2) NOT NULL,
  `userRating` float(10,2) NOT NULL,
  `UserReview` varchar(200) NOT NULL,
  `SalonReview` mediumtext NOT NULL,
  `Image1` varchar(200) NOT NULL,
  `Image2` varchar(200) NOT NULL,
  `Image3` varchar(200) NOT NULL,
  `Image4` varchar(200) NOT NULL,
  `salonImage1` varchar(200) NOT NULL,
  `salonImage2` varchar(200) NOT NULL,
  `salonImage3` varchar(200) NOT NULL,
  `salonImage4` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `storeReview`
--

INSERT INTO `storeReview` (`ID`, `IDBooking`, `rating_i`, `rating_c`, `rating_s`, `userRating`, `UserReview`, `SalonReview`, `Image1`, `Image2`, `Image3`, `Image4`, `salonImage1`, `salonImage2`, `salonImage3`, `salonImage4`, `Date`, `Time`) VALUES
(1, 1, 0.00, 4.00, 4.00, 5.00, 'good customer', 'good salon', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', '2020-04-29', '16:13:01'),
(2, 8, 0.00, 5.00, 5.00, 0.00, 'ugugufu', 'Great', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', 'profile_image.png', '2021-02-14', '23:36:31');

-- --------------------------------------------------------

--
-- Table structure for table `store_booking`
--

CREATE TABLE IF NOT EXISTS `store_booking` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderDate` varchar(200) NOT NULL,
  `OrderID` int(11) NOT NULL,
  `IDuser` int(11) NOT NULL,
  `IDsalon` int(11) NOT NULL,
  `IDspecialist` int(11) NOT NULL,
  `IDslot` int(11) NOT NULL,
  `IDserviceAt` int(11) NOT NULL,
  `Discount` float(10,2) NOT NULL,
  `NoofItems` int(11) NOT NULL,
  `Payable` float(10,2) NOT NULL,
  `PaymentMode` int(11) NOT NULL DEFAULT '0',
  `addressd` varchar(200) DEFAULT NULL,
  `houseno` varchar(200) DEFAULT NULL,
  `landmark` varchar(200) DEFAULT NULL,
  `Latitude` float NOT NULL,
  `Longitude` float NOT NULL,
  `isAccepted` tinyint(4) NOT NULL DEFAULT '0',
  `AccpetedDate` date NOT NULL,
  `AccpetedTime` time NOT NULL,
  `isRunning` tinyint(4) NOT NULL DEFAULT '0',
  `StartDate` date NOT NULL,
  `StartTime` time NOT NULL,
  `isCompleted` tinyint(4) NOT NULL DEFAULT '0',
  `EndDate` date NOT NULL,
  `EndTime` time NOT NULL,
  `isCancelled` tinyint(4) NOT NULL DEFAULT '0',
  `CancelledBy` int(11) NOT NULL DEFAULT '0',
  `reasonCancelled` mediumtext NOT NULL,
  `completeDate` date NOT NULL,
  `completeTime` time NOT NULL,
  `UserReview` int(11) NOT NULL,
  `UserComment` mediumtext NOT NULL,
  `SalonReview` int(11) NOT NULL,
  `SalonComments` mediumtext NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `store_booking`
--

INSERT INTO `store_booking` (`ID`, `OrderDate`, `OrderID`, `IDuser`, `IDsalon`, `IDspecialist`, `IDslot`, `IDserviceAt`, `Discount`, `NoofItems`, `Payable`, `PaymentMode`, `addressd`, `houseno`, `landmark`, `Latitude`, `Longitude`, `isAccepted`, `AccpetedDate`, `AccpetedTime`, `isRunning`, `StartDate`, `StartTime`, `isCompleted`, `EndDate`, `EndTime`, `isCancelled`, `CancelledBy`, `reasonCancelled`, `completeDate`, `completeTime`, `UserReview`, `UserComment`, `SalonReview`, `SalonComments`, `Date`, `Time`) VALUES
(2, '30-04-2020', 345312, 3, 5, 5, 2, 2, 8.00, 1, 598.00, 1, '29C Troupant Ave, Magaliessig, Sandton, 2191, South Africa', '1', '', -26.0355, 28.0199, 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 1, '0000-00-00', '00:00:00', 1, 2, 'ADD CANCELLATION CHARGE', '0000-00-00', '00:00:00', 0, '', 0, '', '2020-04-29', '20:31:00'),
(3, '30-04-2020', 345312, 3, 5, 5, 3, 1, 8.00, 1, 598.00, 1, '29C Troupant Ave, Magaliessig, Sandton, 2191, South Africa', '1', '', -26.0355, 28.0199, 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 1, 0, '', '0000-00-00', '00:00:00', 0, '', 0, '', '2020-04-29', '20:31:00'),
(4, '6-05-02020', 157194, 3, 5, 5, 7, 1, 8.00, 1, 322.00, 0, NULL, NULL, NULL, 0, 0, 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 1, 2, 'ADD CANCELLATION CHARGE', '0000-00-00', '00:00:00', 0, '', 0, '', '2020-05-06', '05:51:00'),
(5, '11-05-2020', 578465, 3, 5, 5, 2, 1, 8.00, 1, 460.00, 1, NULL, NULL, NULL, 0, 0, 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 1, 2, 'ADD CANCELLATION CHARGE', '0000-00-00', '00:00:00', 0, '', 0, '', '2020-05-07', '20:23:00'),
(6, '28-010-2020', 784192, 5, 2, 3, 1, 2, 12.00, 1, 39.00, 0, '29C Troupant Ave, Magaliessig, Sandton, 2191, South Africa', '1', '', -26.0356, 28.0199, 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 1, 2, 'ADD CANCELLATION CHARGE', '0000-00-00', '00:00:00', 0, '', 0, '', '2020-10-27', '22:08:00'),
(7, '28-010-2020', 580268, 1, 5, 5, 11, 1, 8.00, 2, 782.00, 1, NULL, NULL, NULL, 0, 0, 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 0, '0000-00-00', '00:00:00', 0, 0, '', '0000-00-00', '00:00:00', 0, '', 0, '', '2020-10-27', '20:33:00'),
(8, '15-02-2021', 936517, 7, 11, 8, 2, 1, 0.00, 1, 200.00, 0, NULL, NULL, NULL, 0, 0, 1, '2021-02-14', '23:34:51', 0, '2021-02-14', '23:35:54', 1, '2021-02-14', '23:36:31', 0, 0, '', '0000-00-00', '00:00:00', 1, '', 0, '', '2021-02-14', '23:33:00');

-- --------------------------------------------------------

--
-- Table structure for table `subscribe`
--

CREATE TABLE IF NOT EXISTS `subscribe` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(200) NOT NULL,
  `Details` varchar(200) NOT NULL,
  `Validity` varchar(200) NOT NULL,
  `Price` float(10,2) NOT NULL,
  `User` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `subscribe`
--

INSERT INTO `subscribe` (`ID`, `Name`, `Details`, `Validity`, `Price`, `User`, `Date`, `Time`) VALUES
(1, 'SUPER SAVER', 'SAVE 50% ON NEW SUBSCRIPTION', '90', 1200.00, 'ADMIN123', '2020-03-06', '14:06:21'),
(2, 'POCKET FRIENDLY', 'SAVE 25%', '30', 800.00, 'ADMIN123', '2020-03-06', '14:06:46'),
(3, 'ALL THE YEAR', 'This sucription is for the whole year', '365', 1500.00, 'ADMIN123', '2020-05-01', '13:23:45'),
(4, 'NEW TEST', 'test', '300', 1500.00, 'ADMIN123', '2020-05-01', '14:22:06');

-- --------------------------------------------------------

--
-- Table structure for table `timeslot`
--

CREATE TABLE IF NOT EXISTS `timeslot` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Slot` varchar(200) NOT NULL,
  `ActualTime` time NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IDSalon` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `timeslot`
--

INSERT INTO `timeslot` (`ID`, `Slot`, `ActualTime`, `Date`, `IDSalon`) VALUES
(1, '9:30AM', '09:30:00', '2020-02-28 09:20:48', 1),
(2, '10:00AM', '10:00:00', '2020-02-28 09:20:48', 1),
(3, '10:30AM', '10:30:00', '2020-02-28 09:21:09', 1),
(4, '11:00AM', '11:00:00', '2020-02-28 09:21:09', 1),
(5, '11:30AM', '11:30:00', '2020-02-28 09:21:20', 1),
(6, '12:00PM', '12:00:00', '2020-02-28 09:21:20', 1),
(7, '12:30PM', '12:30:00', '2020-02-28 09:21:53', 1),
(8, '1:00PM', '13:00:00', '2020-02-28 09:21:53', 1),
(9, '1:30PM', '13:30:00', '2020-02-28 09:22:04', 1),
(10, '2:00PM', '14:00:00', '2020-02-28 09:22:04', 1),
(11, '2:30PM', '14:30:00', '2020-02-28 09:22:16', 1),
(12, '3:00PM', '15:00:00', '2020-02-28 09:22:16', 1),
(13, '3:30PM', '15:30:00', '2020-02-28 09:22:30', 1),
(14, '4:00PM', '16:00:00', '2020-02-28 09:22:30', 1),
(15, '4:30PM', '16:30:00', '2020-02-28 09:22:47', 1),
(16, '5:00PM', '17:00:00', '2020-02-28 09:22:47', 1);

-- --------------------------------------------------------

--
-- Table structure for table `userfeedback`
--

CREATE TABLE IF NOT EXISTS `userfeedback` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Feedback` varchar(200) NOT NULL,
  `UserID` int(11) NOT NULL,
  `Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `userfeedback`
--

INSERT INTO `userfeedback` (`ID`, `Feedback`, `UserID`, `Time`) VALUES
(1, 'Blessed are the hairstylists, for they bring out the beauty in others.', 1, '2020-02-21 12:06:20');

-- --------------------------------------------------------

--
-- Table structure for table `user_details`
--

CREATE TABLE IF NOT EXISTS `user_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Photo` varchar(255) DEFAULT 'profile_image.png',
  `PhoneNo` varchar(20) NOT NULL,
  `Role` int(11) NOT NULL DEFAULT '1',
  `Password` varchar(200) NOT NULL,
  `Gender` varchar(11) NOT NULL,
  `DOB` date NOT NULL,
  `Latitude` float(10,6) DEFAULT NULL,
  `Longitude` float(10,6) DEFAULT NULL,
  `Rating` float(10,2) NOT NULL,
  `TotalRating` int(11) NOT NULL,
  `Is_Blocked` tinyint(1) NOT NULL DEFAULT '0',
  `User_Referrence_Code` varchar(20) DEFAULT NULL,
  `FirebaseToken` varchar(255) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Time` time DEFAULT NULL,
  `User` varchar(255) DEFAULT NULL,
  `IP` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `user_details`
--

INSERT INTO `user_details` (`ID`, `Name`, `Email`, `Photo`, `PhoneNo`, `Role`, `Password`, `Gender`, `DOB`, `Latitude`, `Longitude`, `Rating`, `TotalRating`, `Is_Blocked`, `User_Referrence_Code`, `FirebaseToken`, `Date`, `Time`, `User`, `IP`) VALUES
(1, 'YEST', 'NA', 'IMG_20200428_200238.jpg', '7002608241', 1, '7c222fb2927d828af22f592134e8932480637c0d', '', '0000-00-00', NULL, NULL, 0.00, 0, 0, NULL, NULL, '2020-04-28', '16:33:00', NULL, NULL),
(2, 'PARAG', 'NA', 'IMG_20200429_190546.jpg', '9954156505', 1, '7c4a8d09ca3762af61e59520943dc26494f8941b', '', '0000-00-00', NULL, NULL, 5.00, 1, 0, NULL, NULL, '2020-04-29', '15:36:00', NULL, NULL),
(4, 'MINTU', 'Mint@m8n.com', 'IMG_20200502_204254.jpg', '9706295356', 1, '8cb2237d0679ca88db6464eac60da96345513964', '', '0000-00-00', NULL, NULL, 0.00, 0, 0, NULL, NULL, '2020-05-02', '17:12:00', NULL, NULL),
(5, 'MOTEST', 'NA', 'IMG_20200814_202723.jpg', '0827865662', 1, '7c222fb2927d828af22f592134e8932480637c0d', '', '0000-00-00', NULL, NULL, 0.00, 0, 0, NULL, NULL, '2020-08-14', '20:27:00', NULL, NULL),
(7, 'MOECUSTOMER', 'NA', 'IMG_20210214_231801.jpg', '0747865662', 1, '7c222fb2927d828af22f592134e8932480637c0d', '', '0000-00-00', NULL, NULL, 0.00, 1, 0, NULL, NULL, '2021-02-14', '23:18:00', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_selfie`
--

CREATE TABLE IF NOT EXISTS `user_selfie` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDUser` int(11) NOT NULL,
  `IDSalon` int(11) NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `user_selfie`
--

INSERT INTO `user_selfie` (`ID`, `IDUser`, `IDSalon`, `Photo`, `Date`, `Time`, `IP`) VALUES
(1, 0, 1, 'http://139.59.38.160/Groom/App/selfie/a.jpg', '0000-00-00', '00:00:00', ''),
(2, 0, 2, 'http://139.59.38.160/Groom/App/selfie/b.jpg', '0000-00-00', '00:00:00', ''),
(3, 0, 3, 'http://139.59.38.160/Groom/App/selfie/c.jpg', '0000-00-00', '00:00:00', ''),
(5, 0, 4, 'http://139.59.38.160/Groom/App/selfie/d.jpg', '0000-00-00', '00:00:00', '');

-- --------------------------------------------------------

--
-- Table structure for table `viewpager`
--

CREATE TABLE IF NOT EXISTS `viewpager` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Photo` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `viewpager`
--

INSERT INTO `viewpager` (`ID`, `Photo`, `Date`, `Time`, `IP`) VALUES
(1, 'a.png', '0000-00-00', '00:00:00', ''),
(2, 'b.png', '0000-00-00', '00:00:00', ''),
(3, 'c.png', '0000-00-00', '00:00:00', ''),
(5, 'd.png', '0000-00-00', '00:00:00', ''),
(6, 'e.png', '0000-00-00', '00:00:00', ''),
(7, 'f.png', '0000-00-00', '00:00:00', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
