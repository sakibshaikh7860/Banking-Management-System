-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 24, 2026 at 11:51 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bankingsystem_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `account_no` varchar(20) NOT NULL,
  `pin` varchar(10) DEFAULT NULL,
  `balance` decimal(15,2) DEFAULT 0.00,
  `name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`account_no`, `pin`, `balance`, `name`) VALUES
('4548581883593370', '3478', 10000.00, 'Rohan Nandu Joshi');

-- --------------------------------------------------------

--
-- Table structure for table `bank`
--

CREATE TABLE `bank` (
  `pin` varchar(10) DEFAULT NULL,
  `account_no` varchar(20) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `amount` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bank`
--

INSERT INTO `bank` (`pin`, `account_no`, `date`, `type`, `amount`) VALUES
('1111', '', 'Tue Mar 17 16:34:03 IST 2026', 'Deposit', 1111.00),
('1111', '', 'Tue Mar 17 16:34:19 IST 2026', 'Deposit', 1111.00),
('1111', '8212091523420152', 'Tue Mar 17 16:35:13 IST 2026', 'Deposit', 1000.00),
('1111', '', 'Tue Mar 17 16:45:03 IST 2026', 'Deposit', 111.00),
('1111', '', 'Tue Mar 17 16:45:12 IST 2026', 'Deposit', 111.00),
('1111', '', 'Tue Mar 17 16:45:14 IST 2026', 'Deposit', 111.00),
('1111', '', 'Tue Mar 17 16:45:15 IST 2026', 'Deposit', 111.00),
('1111', '', 'Tue Mar 17 16:47:27 IST 2026', 'Deposit', 222.00),
('1111', '', 'Tue Mar 17 16:47:34 IST 2026', 'Deposit', 111.00),
('1111', '', 'Tue Mar 17 16:47:54 IST 2026', 'Deposit', 111.00),
('1111', '', 'Tue Mar 17 16:51:12 IST 2026', 'Deposit', 111.00),
('1111', '8212091523420152', 'Tue Mar 17 16:52:16 IST 2026', 'Deposit', 1000.00),
('1111', '', 'Tue Mar 17 17:06:45 IST 2026', 'Withdrawl', 1111.00),
('1111', '8212091523420152', 'Tue Mar 17 17:07:15 IST 2026', 'Withdrawl', 1000.00),
('1111', '8212091523420152', 'Tue Mar 17 17:10:32 IST 2026', 'Deposit', 1000.00),
('1111', '', 'Tue Mar 17 19:36:31 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Tue Mar 17 19:37:51 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Tue Mar 17 19:41:33 IST 2026', 'Deposit', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 12:28:21 IST 2026', 'Deposit', 500.00),
('1111', '8212091523420152', 'Wed Mar 18 12:28:37 IST 2026', 'Deposit', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 12:43:29 IST 2026', 'Deposit', 1000.00),
('1111', '8212091523420152', 'Wed Mar 18 12:44:14 IST 2026', 'Deposit', 1500.00),
('1111', '8212091523420152', 'Wed Mar 18 12:52:42 IST 2026', 'Deposit', 111.00),
('1111', '', 'Wed Mar 18 12:56:35 IST 2026', 'Withdrawl', 1000.00),
('1111', '', 'Wed Mar 18 12:58:02 IST 2026', 'Withdrawl', 100.00),
('1111', '', 'Wed Mar 18 12:58:09 IST 2026', 'Withdrawl', 500.00),
('1111', '', 'Wed Mar 18 12:58:19 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:00:10 IST 2026', 'Withdrawl', 111.00),
('1111', '8212091523420152', 'Wed Mar 18 13:00:19 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:00:41 IST 2026', 'Deposit', 1600.00),
('1111', '8212091523420152', 'Wed Mar 18 13:02:27 IST 2026', 'Deposit', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:02:37 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:02:57 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:04:18 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:04:54 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:04:59 IST 2026', 'Deposit', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:05:25 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:07:53 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:10:04 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:10:09 IST 2026', 'Deposit', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:10:12 IST 2026', 'Withdrawl', 500.00),
('1111', '', 'Wed Mar 18 13:13:29 IST 2026', 'Withdrawl', 111.00),
('1111', '8212091523420152', 'Wed Mar 18 13:13:58 IST 2026', 'Withdrawl', 300.00),
('1111', '8212091523420152', 'Wed Mar 18 13:14:09 IST 2026', 'Withdrawl', 100.00),
('1111', '', 'Wed Mar 18 13:16:12 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:16:41 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 13:16:59 IST 2026', 'Withdrawl', 1000.00),
('1111', '8212091523420152', 'Wed Mar 18 13:17:08 IST 2026', 'Deposit', 1000.00),
('1111', '8212091523420152', 'Wed Mar 18 13:17:20 IST 2026', 'Deposit', 2000.00),
('1111', '8212091523420152', 'Wed Mar 18 13:18:12 IST 2026', 'Withdrawl', 1800.00),
('1111', '8212091523420152', 'Wed Mar 18 13:18:25 IST 2026', 'Withdrawl', 100.00),
('1111', '8212091523420152', 'Wed Mar 18 14:57:00 IST 2026', 'Withdrawl', 100.00),
('1111', '3943445535177388', 'Wed Mar 18 15:32:53 IST 2026', 'Deposit', 10000.00),
('', '', 'Wed Mar 18 15:35:48 IST 2026', 'Deposit', 111.00),
('', '', 'Wed Mar 18 15:36:08 IST 2026', 'Deposit', 111.00),
('1111', '3943445535177388', 'Wed Mar 18 15:37:14 IST 2026', 'Deposit', 1111.00),
('1111', '3943445535177388', 'Wed Mar 18 15:37:18 IST 2026', 'Withdrawl', 111.00),
('1111', '3943445535177388', 'Wed Mar 18 15:37:33 IST 2026', 'Withdrawl', 500.00),
('1111', '3943445535177388', 'Wed Mar 18 15:37:43 IST 2026', 'Withdrawl', 5000.00),
('1111', '3943445535177388', 'Wed Mar 18 15:37:48 IST 2026', 'Deposit', 1111.00),
('1111', '3943445535177388', 'Wed Mar 18 15:39:44 IST 2026', 'Deposit', 1111.00),
('1111', '3943445535177388', 'Wed Mar 18 15:40:13 IST 2026', 'Withdrawl', 5000.00),
('1111', '3943445535177388', 'Wed Mar 18 15:41:00 IST 2026', 'Withdrawl', 111.00),
('1111', '3943445535177388', 'Wed Mar 18 15:41:56 IST 2026', 'Deposit', 1111.00),
('1111', '3943445535177388', 'Wed Mar 18 15:42:02 IST 2026', 'Withdrawl', 1000.00),
('1111', '3943445535177388', 'Wed Mar 18 19:58:16 IST 2026', 'Deposit', 1000.00),
('1111', '3943445535177388', 'Wed Mar 18 19:58:24 IST 2026', 'Withdrawl', 1000.00),
('1111', '3943445535177388', 'Wed Mar 18 19:58:53 IST 2026', 'Withdrawl', 1000.00),
('1111', '3943445535177388', 'Wed Mar 18 19:59:10 IST 2026', 'Withdrawl', 111.00),
('1111', '3943445535177388', 'Wed Mar 18 20:00:43 IST 2026', 'Deposit', 1000.00),
('1111', '7389001241433362', 'Thu Mar 19 13:10:05 IST 2026', 'Deposit', 6000.00),
('1111', '7389001241433362', 'Thu Mar 19 13:10:11 IST 2026', 'Withdrawl', 100.00),
('1111', '7389001241433362', 'Thu Mar 19 13:10:17 IST 2026', 'Withdrawl', 100.00),
('1111', '4261536290251541', 'Thu Mar 19 13:36:10 IST 2026', 'Deposit', 6000.00),
('1111', '4261536290251541', 'Thu Mar 19 13:36:16 IST 2026', 'Withdrawl', 100.00),
('1111', '4261536290251541', 'Thu Mar 19 13:36:22 IST 2026', 'Withdrawl', 100.00),
('', '', 'Fri Mar 20 20:09:57 IST 2026', 'Deposit', 1000.00),
('1111', '7389001241433362', 'Fri Mar 20 22:20:29 IST 2026', 'Deposit', 100.00),
('1111', '7389001241433362', 'Fri Mar 20 22:20:37 IST 2026', 'Withdrawl', 100.00),
('1111', '7389001241433362', 'Fri Mar 20 22:20:45 IST 2026', 'Withdrawl', 100.00),
('1111', '7389001241433362', 'Fri Mar 20 22:21:12 IST 2026', 'Withdrawl', 100.00),
('1111', '7389001241433362', 'Sun Mar 22 22:55:07 IST 2026', 'Deposit', 200.00),
('1111', '7389001241433362', 'Sun Mar 22 22:55:14 IST 2026', 'Withdrawl', 400.00),
('1111', '7389001241433362', 'Sun Mar 22 22:55:21 IST 2026', 'Deposit', 300.00),
('1111', '7389001241433362', 'Sun Mar 22 22:55:28 IST 2026', 'Withdrawl', 100.00),
('1111', '7389001241433362', 'Sun Mar 22 22:56:04 IST 2026', 'Withdrawl', 50.00),
('1111', '7389001241433362', 'Sun Mar 22 22:58:51 IST 2026', 'Deposit', 1250.00),
('1111', '7389001241433362', 'Sun Mar 22 22:59:48 IST 2026', 'Withdrawl', 1250.00),
('1111', '7389001241433362', 'Sun Mar 22 23:02:51 IST 2026', 'Withdrawl', 1250.00),
('3478', '4548581883593370', 'Sun Mar 22 23:25:36 IST 2026', 'Deposit', 10000.00);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `formno` varchar(20) DEFAULT NULL,
  `Account_No` varchar(20) NOT NULL,
  `pin` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`formno`, `Account_No`, `pin`) VALUES
('1000', '4548581883593370', '3478');

-- --------------------------------------------------------

--
-- Table structure for table `signup1`
--

CREATE TABLE `signup1` (
  `formno` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `mname` varchar(100) DEFAULT NULL,
  `dob` varchar(20) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `marital` varchar(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `nat` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `signup1`
--

INSERT INTO `signup1` (`formno`, `name`, `fname`, `mname`, `dob`, `gender`, `email`, `marital`, `address`, `city`, `state`, `nat`) VALUES
('1000', 'Rohan Nandu Joshi', 'Nandu Joshi', 'Priyanka Joshi', '23/12/2004', 'Male', 'rohanjoshi565@gmail.com', 'Unmarried', 'Flat no 234, Abhilasha Hights, Savedi, Ahmednagar, Maharashtra - 414003', 'Ahmednagar', 'Maharshtra', 'Indian');

-- --------------------------------------------------------

--
-- Table structure for table `signup2`
--

CREATE TABLE `signup2` (
  `formno` varchar(20) NOT NULL,
  `religion` varchar(50) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `income` varchar(50) DEFAULT NULL,
  `education` varchar(50) DEFAULT NULL,
  `occupation` varchar(50) DEFAULT NULL,
  `pan` varchar(20) DEFAULT NULL,
  `aadhar` varchar(20) DEFAULT NULL,
  `senior` varchar(10) DEFAULT NULL,
  `existing` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `signup2`
--

INSERT INTO `signup2` (`formno`, `religion`, `category`, `income`, `education`, `occupation`, `pan`, `aadhar`, `senior`, `existing`) VALUES
('1000', 'Hindu', 'ST', '< ₹1,50,000', 'Non-Graduate', 'Student', 'NHDE4566O', '566544352536', 'No', 'No');

-- --------------------------------------------------------

--
-- Table structure for table `signup3`
--

CREATE TABLE `signup3` (
  `formno` varchar(20) NOT NULL,
  `account_type` varchar(50) DEFAULT NULL,
  `account_no` varchar(20) DEFAULT NULL,
  `pin` varchar(10) DEFAULT NULL,
  `services` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `signup3`
--

INSERT INTO `signup3` (`formno`, `account_type`, `account_no`, `pin`, `services`) VALUES
('1000', 'Current Account', '4548581883593370', '3478', 'ATM Card, Internet Banking, Mobile Banking, Email Alerts, ');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`account_no`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`Account_No`);

--
-- Indexes for table `signup1`
--
ALTER TABLE `signup1`
  ADD PRIMARY KEY (`formno`);

--
-- Indexes for table `signup2`
--
ALTER TABLE `signup2`
  ADD PRIMARY KEY (`formno`);

--
-- Indexes for table `signup3`
--
ALTER TABLE `signup3`
  ADD PRIMARY KEY (`formno`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
