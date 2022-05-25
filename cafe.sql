-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 25, 2022 at 10:09 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 7.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `deskripsi` text DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `image` varchar(225) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id`, `nama`, `deskripsi`, `harga`, `image`) VALUES
(5, 'Seblak', 'seblak dengan berbagai topping pilihan', 150000, 'http://10.10.13.88/cafe/upload/image_seblak.jpg'),
(6, 'pentol', 'kenyal kenyal bermutu', 10000, 'http://10.10.13.88/cafe/upload/image_pentol.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `order_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `nomor_meja` varchar(2) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `jumlah_order` int(11) DEFAULT NULL,
  `status` enum('Dalam Proses','Selesai') DEFAULT 'Dalam Proses'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `order_date`, `nomor_meja`, `menu_id`, `jumlah_order`, `status`) VALUES
(21, '2022-05-25 07:36:28', '1', 6, 1, 'Dalam Proses'),
(22, '2022-05-25 07:42:07', '2', 6, 2, 'Dalam Proses'),
(23, '2022-05-25 07:42:45', '5', 5, 5, 'Dalam Proses'),
(24, '2022-05-25 07:43:00', '6', 6, 6, 'Dalam Proses'),
(25, '2022-05-25 07:51:45', '7', 6, 7, 'Dalam Proses');

-- --------------------------------------------------------

--
-- Stand-in structure for view `view_cart`
-- (See below for the actual view)
--
CREATE TABLE `view_cart` (
`nomor_meja` varchar(2)
,`nama` varchar(30)
,`harga` int(11)
,`jumlah_order` int(11)
,`jumlah_harga` bigint(21)
);

-- --------------------------------------------------------

--
-- Structure for view `view_cart`
--
DROP TABLE IF EXISTS `view_cart`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_cart`  AS SELECT `orders`.`nomor_meja` AS `nomor_meja`, `menu`.`nama` AS `nama`, `menu`.`harga` AS `harga`, `orders`.`jumlah_order` AS `jumlah_order`, `menu`.`harga`* `orders`.`jumlah_order` AS `jumlah_harga` FROM (`orders` join `menu` on(`orders`.`menu_id` = `menu`.`id`)) WHERE `orders`.`status` = 'Dalam Proses' GROUP BY `orders`.`nomor_meja`, `orders`.`menu_id` ORDER BY `orders`.`order_date` ASC ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
