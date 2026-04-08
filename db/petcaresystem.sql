-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 30, 2025 at 04:04 AM
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
-- Database: `petcaresystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_appointment`
--

CREATE TABLE `tbl_appointment` (
  `app_id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `pet_id` int(20) NOT NULL,
  `owner_id` int(20) NOT NULL,
  `appointment_date` date NOT NULL,
  `appointment_time` time(6) NOT NULL,
  `service_type` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  `remarks` text NOT NULL,
  `medications` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_appointment`
--

INSERT INTO `tbl_appointment` (`app_id`, `user_id`, `pet_id`, `owner_id`, `appointment_date`, `appointment_time`, `service_type`, `status`, `remarks`, `medications`) VALUES
(1, 2, 1, 1, '2025-05-24', '08:00:00.000000', 'Grooming', 'Completed', 'hehe', 'hehe'),
(2, 2, 2, 4, '2025-05-23', '20:30:00.000000', 'Grooming', 'Approved', '', ''),
(3, 2, 3, 3, '2025-05-24', '19:30:00.000000', 'Check up', 'Approved', '', ''),
(4, 2, 4, 2, '2025-05-24', '10:30:00.000000', 'Check up', 'Pending', '', ''),
(5, 2, 4, 2, '2025-05-30', '08:00:00.000000', 'Vaccine', 'Completed', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_logs`
--

CREATE TABLE `tbl_logs` (
  `l_id` int(20) NOT NULL,
  `u_id` int(20) NOT NULL,
  `actions` varchar(150) NOT NULL,
  `date_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_logs`
--

INSERT INTO `tbl_logs` (`l_id`, `u_id`, `actions`, `date_time`) VALUES
(1, 1, 'Registered a new account: 1', '2025-05-23 09:35:34'),
(2, 1, 'Logged in to the system', '2025-05-23 09:36:52'),
(3, 1, 'Logged in to the system', '2025-05-23 09:39:34'),
(4, 2, 'Registered a new account: 2', '2025-05-23 10:18:52'),
(5, 2, 'Logged in to the system', '2025-05-23 10:19:40'),
(6, 2, 'Logged out from the system', '2025-05-23 10:19:58'),
(7, 1, 'Logged in to the system', '2025-05-23 10:20:06'),
(8, 1, 'Logged out from the system', '2025-05-23 10:21:52'),
(9, 1, 'Logged in to the system', '2025-05-23 10:22:05'),
(10, 1, 'Logged in to the system', '2025-05-23 10:23:44'),
(11, 2, 'Logged in to the system', '2025-05-23 10:56:47'),
(12, 2, 'Added a pet with pet ID: 1', '2025-05-23 10:58:17'),
(13, 2, 'Logged in to the system', '2025-05-23 11:01:34'),
(14, 2, 'Logged in to the system', '2025-05-23 11:05:55'),
(15, 2, 'Logged in to the system', '2025-05-23 11:07:22'),
(16, 1, 'Logged in to the system', '2025-05-23 11:08:38'),
(17, 1, 'Logged in to the system', '2025-05-23 11:09:59'),
(18, 1, 'Logged in to the system', '2025-05-23 14:17:20'),
(19, 2, 'Logged in to the system', '2025-05-23 14:32:50'),
(20, 2, 'Logged in to the system', '2025-05-23 14:36:10'),
(21, 2, 'Logged out from the system', '2025-05-23 14:37:39'),
(22, 1, 'Logged in to the system', '2025-05-23 14:37:54'),
(23, 2, 'Logged in to the system', '2025-05-23 14:56:06'),
(24, 1, 'Logged in to the system', '2025-05-23 15:15:51'),
(25, 1, 'Added a pet with pet ID: 2', '2025-05-23 15:18:36'),
(26, 1, 'Added a pet with pet ID: 3', '2025-05-23 15:19:03'),
(27, 1, 'Added a pet with pet ID: 4', '2025-05-23 15:19:31'),
(28, 2, 'Logged in to the system', '2025-05-23 15:20:27'),
(29, 2, 'Logged in to the system', '2025-05-23 15:23:31'),
(30, 2, 'Logged in to the system', '2025-05-23 15:24:48'),
(31, 1, 'Logged in to the system', '2025-05-23 16:25:56'),
(32, 2, 'Logged in to the system', '2025-05-23 16:26:21'),
(33, 1, 'Logged in to the system', '2025-05-23 16:30:46'),
(34, 2, 'Logged in to the system', '2025-05-23 16:31:03'),
(35, 1, 'Logged in to the system', '2025-05-25 22:43:26'),
(36, 2, 'Logged in to the system', '2025-05-25 22:44:23'),
(37, 2, 'Logged in to the system', '2025-05-25 22:53:44'),
(38, 2, 'Logged in to the system', '2025-05-26 21:34:07'),
(39, 2, 'Logged in to the system', '2025-05-26 21:37:11'),
(40, 1, 'Logged in to the system', '2025-05-26 21:41:56'),
(41, 2, 'Logged in to the system', '2025-05-26 21:43:07'),
(42, 2, 'Added a pet with pet ID: 5', '2025-05-26 21:53:23'),
(43, 2, 'Logged in to the system', '2025-05-26 21:56:41'),
(44, 1, 'Logged in to the system', '2025-05-26 22:02:48'),
(45, 1, 'Logged in to the system', '2025-05-26 22:05:20'),
(46, 1, 'Logged in to the system', '2025-05-26 22:17:45'),
(47, 1, 'Logged in to the system', '2025-05-26 22:21:02'),
(48, 1, 'Logged in to the system', '2025-05-26 22:25:19'),
(49, 1, 'Logged in to the system', '2025-05-26 22:28:28'),
(50, 1, 'Logged in to the system', '2025-05-26 22:41:20'),
(51, 1, 'Logged in to the system', '2025-05-26 22:46:37'),
(52, 1, 'Logged in to the system', '2025-05-26 22:52:04'),
(53, 1, 'Logged in to the system', '2025-05-26 22:56:34'),
(54, 1, 'Logged in to the system', '2025-05-26 22:58:40'),
(55, 1, 'Logged in to the system', '2025-05-27 10:49:30'),
(56, 1, 'Logged in to the system', '2025-05-27 10:53:24'),
(57, 1, 'Logged in to the system', '2025-05-27 11:00:37'),
(58, 1, 'Logged in to the system', '2025-05-27 11:02:32'),
(59, 1, 'Logged in to the system', '2025-05-27 11:13:03'),
(60, 1, 'Logged in to the system', '2025-05-27 11:17:25'),
(61, 1, 'Logged in to the system', '2025-05-27 11:20:29'),
(62, 1, 'Logged in to the system', '2025-05-27 11:22:37'),
(63, 1, 'Logged in to the system', '2025-05-27 11:24:23'),
(64, 1, 'Logged in to the system', '2025-05-27 11:27:38'),
(65, 1, 'Logged in to the system', '2025-05-27 11:33:42'),
(66, 1, 'Logged in to the system', '2025-05-27 11:35:43'),
(67, 2, 'Logged in to the system', '2025-05-27 11:35:54'),
(68, 1, 'Logged in to the system', '2025-05-27 11:36:45'),
(69, 2, 'Logged in to the system', '2025-05-27 11:48:17'),
(70, 2, 'Logged in to the system', '2025-05-27 11:49:52'),
(71, 2, 'Logged in to the system', '2025-05-27 11:55:25');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_owner`
--

CREATE TABLE `tbl_owner` (
  `owner_id` int(20) NOT NULL,
  `owner_fname` varchar(50) NOT NULL,
  `owner_lname` varchar(50) NOT NULL,
  `owner_contact` varchar(50) NOT NULL,
  `owner_address` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_owner`
--

INSERT INTO `tbl_owner` (`owner_id`, `owner_fname`, `owner_lname`, `owner_contact`, `owner_address`) VALUES
(1, 'as', 'as', '09253256325', 'as'),
(2, 'haha', 'haha', '09877685768', 'haha'),
(3, 'hehe', 'hehe', '09675867485', 'hehe'),
(4, 'huhu', 'huhu', '09876787586', 'huhu'),
(5, 'yashmil', 'tapasao', '09879876789', 'tuyan, naga city');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_pet`
--

CREATE TABLE `tbl_pet` (
  `pet_id` int(20) NOT NULL,
  `owner_id` int(20) NOT NULL,
  `pet_name` varchar(50) NOT NULL,
  `pet_breed` varchar(50) NOT NULL,
  `pet_color` varchar(50) NOT NULL,
  `pet_age` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_pet`
--

INSERT INTO `tbl_pet` (`pet_id`, `owner_id`, `pet_name`, `pet_breed`, `pet_color`, `pet_age`) VALUES
(1, 1, 'as', 'as', 'as', 'as'),
(2, 4, 'iro', 'iro', 'black', '1 month'),
(3, 3, 'dog', 'dog', 'pink', '2 months'),
(4, 2, 'tukoy', 'tukoy', 'black', '3 motnhs'),
(5, 5, 'yunyun', 'pitbull', 'black', '6 months');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `u_id` int(20) NOT NULL,
  `u_fname` varchar(50) NOT NULL,
  `u_lname` varchar(50) NOT NULL,
  `u_username` varchar(50) NOT NULL,
  `u_email` varchar(50) NOT NULL,
  `u_password` varchar(50) NOT NULL,
  `u_type` varchar(50) NOT NULL,
  `u_status` varchar(50) NOT NULL,
  `u_image` varchar(50) NOT NULL,
  `pets_name` varchar(50) NOT NULL,
  `favorite_food` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`u_id`, `u_fname`, `u_lname`, `u_username`, `u_email`, `u_password`, `u_type`, `u_status`, `u_image`, `pets_name`, `favorite_food`) VALUES
(1, 'yisha', 'yisha', 'yisha', 'yisha@gmail.com', 'p+BR+yfBEgry/WbNT1vDszH8VUFQ/DnBRyq5gUTi5QM=', 'Admin', 'Active', 'src/userimages/Screenshot 2024-11-15 132248.png', 'jOQj28Gh9g5FCiX3gb/IRci0uSqPanrYfsI5R4OIcx0=', 'jOQj28Gh9g5FCiX3gb/IRci0uSqPanrYfsI5R4OIcx0='),
(2, 'qw', 'qw', 'qw', 'qw@gmail.com', 'mpyqK1kB6F5rIhxg8c4a11mWH+eoFZ41ny6tbiOkNJU=', 'User', 'Active', '', '2HbVkJXxMFTBIPdyAsU3iqJdd4fUrfcJgNuz8qcSWsE=', '2HbVkJXxMFTBIPdyAsU3iqJdd4fUrfcJgNuz8qcSWsE=');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_appointment`
--
ALTER TABLE `tbl_appointment`
  ADD PRIMARY KEY (`app_id`),
  ADD KEY `u_id` (`user_id`),
  ADD KEY `pet_id` (`pet_id`),
  ADD KEY `owner_id` (`owner_id`);

--
-- Indexes for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  ADD PRIMARY KEY (`l_id`),
  ADD KEY `u_id` (`u_id`);

--
-- Indexes for table `tbl_owner`
--
ALTER TABLE `tbl_owner`
  ADD PRIMARY KEY (`owner_id`);

--
-- Indexes for table `tbl_pet`
--
ALTER TABLE `tbl_pet`
  ADD PRIMARY KEY (`pet_id`),
  ADD KEY `owner_id` (`owner_id`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`u_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_appointment`
--
ALTER TABLE `tbl_appointment`
  MODIFY `app_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  MODIFY `l_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;

--
-- AUTO_INCREMENT for table `tbl_owner`
--
ALTER TABLE `tbl_owner`
  MODIFY `owner_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_pet`
--
ALTER TABLE `tbl_pet`
  MODIFY `pet_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `u_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_appointment`
--
ALTER TABLE `tbl_appointment`
  ADD CONSTRAINT `tbl_appointment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`u_id`),
  ADD CONSTRAINT `tbl_appointment_ibfk_2` FOREIGN KEY (`pet_id`) REFERENCES `tbl_pet` (`pet_id`),
  ADD CONSTRAINT `tbl_appointment_ibfk_3` FOREIGN KEY (`owner_id`) REFERENCES `tbl_owner` (`owner_id`);

--
-- Constraints for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  ADD CONSTRAINT `tbl_logs_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `tbl_user` (`u_id`);

--
-- Constraints for table `tbl_pet`
--
ALTER TABLE `tbl_pet`
  ADD CONSTRAINT `tbl_pet_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `tbl_owner` (`owner_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
