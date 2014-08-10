-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 08, 2014 at 10:19 AM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `a8933784_ehr`
--
CREATE DATABASE IF NOT EXISTS `a8933784_ehr` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `a8933784_ehr`;

-- --------------------------------------------------------

--
-- Table structure for table `adult`
--

CREATE TABLE IF NOT EXISTS `adult` (
  `curp` varchar(18) NOT NULL,
  `name` varchar(30) NOT NULL,
  `phone` int(10) NOT NULL,
  `email` varchar(30) NOT NULL,
  `zip` int(10) NOT NULL,
  `colony` varchar(50) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `cancer` varchar(5) NOT NULL,
  `diabetes` varchar(5) NOT NULL,
  `hypertension` varchar(5) NOT NULL,
  `created_on` varchar(30) NOT NULL,
  `updated_on` varchar(30) NOT NULL,
  `created_by` varchar(30) NOT NULL,
  `updated_by` varchar(30) NOT NULL,
  `temperature` varchar(30) NOT NULL,
  `weight` varchar(11) NOT NULL,
  `height` varchar(11) NOT NULL,
  `waist_circumference` varchar(11) NOT NULL,
  `blood_pressure` varchar(11) NOT NULL,
  `pregnancy_status` varchar(11) NOT NULL,
  `symptoms` text NOT NULL,
  `notes` varchar(700) NOT NULL,
  `pysical_exploration1` varchar(30) NOT NULL,
  `pe_details1` text NOT NULL,
  `pysical_exploration2` varchar(30) NOT NULL,
  `pe_details2` text NOT NULL,
  `pysical_exploration3` varchar(11) NOT NULL,
  `pe_details3` text NOT NULL,
  `improvement` varchar(6) NOT NULL,
  `recovery` varchar(5) NOT NULL,
  `aggravate` varchar(5) NOT NULL,
  `diagnosis1` varchar(30) NOT NULL,
  `d_details1` text NOT NULL,
  `diagnosis2` varchar(30) NOT NULL,
  `d_details2` text NOT NULL,
  `diagnosis3` varchar(30) NOT NULL,
  `d_details3` text NOT NULL,
  `d_notes` text NOT NULL,
  `treatement1` varchar(11) NOT NULL,
  `dosis1` varchar(11) NOT NULL,
  `via1` varchar(11) NOT NULL,
  `periodicity1` varchar(11) NOT NULL,
  `treatement2` varchar(11) NOT NULL,
  `dosis2` varchar(11) NOT NULL,
  `via2` varchar(11) NOT NULL,
  `periodicity2` varchar(11) NOT NULL,
  `treatement3` varchar(11) NOT NULL,
  `dosis3` varchar(11) NOT NULL,
  `via3` varchar(11) NOT NULL,
  `periodicity3` varchar(11) NOT NULL,
  `labrequest1` varchar(11) NOT NULL,
  `labrequest2` varchar(11) NOT NULL,
  `labrequest3` varchar(11) NOT NULL,
  `labrequest4` varchar(11) NOT NULL,
  `labrequest5` varchar(11) NOT NULL,
  `tdetails1` text NOT NULL,
  `tdetails2` text NOT NULL,
  `tdetails3` text NOT NULL,
  `labresult1` text NOT NULL,
  `labresult2` text NOT NULL,
  `labresult3` text NOT NULL,
  `labresult4` text NOT NULL,
  `labresult5` text NOT NULL,
  `clinic_no` varchar(30) NOT NULL,
  `clinic_name` varchar(30) NOT NULL,
  `clinic_address` varchar(30) NOT NULL,
  `nurse_phone` int(10) NOT NULL,
  `med1` varchar(11) NOT NULL,
  `med2` varchar(11) NOT NULL,
  `med3` varchar(11) NOT NULL,
  `date1` varchar(11) NOT NULL,
  `time1` varchar(11) NOT NULL,
  `date2` varchar(11) NOT NULL,
  `time2` varchar(11) NOT NULL,
  `dust` varchar(11) NOT NULL,
  `metals` varchar(11) NOT NULL,
  `food` varchar(11) NOT NULL,
  `animals` varchar(11) NOT NULL,
  `medicine` varchar(11) NOT NULL,
  `other` varchar(111) NOT NULL,
  `immunization` varchar(11) NOT NULL,
  `pre` varchar(111) NOT NULL,
  `relation` varchar(30) NOT NULL,
  `pysical_exploration4` varchar(30) NOT NULL,
  `pysical_exploration5` varchar(11) NOT NULL,
  `pysical_exploration6` varchar(30) NOT NULL,
  `diagnosis4` varchar(11) NOT NULL,
  `diagnosis5` varchar(30) NOT NULL,
  `diagnosis6` varchar(500) NOT NULL,
  `treatement4` varchar(500) NOT NULL,
  PRIMARY KEY (`curp`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adult`
--

INSERT INTO `adult` (`curp`, `name`, `phone`, `email`, `zip`, `colony`, `gender`, `cancer`, `diabetes`, `hypertension`, `created_on`, `updated_on`, `created_by`, `updated_by`, `temperature`, `weight`, `height`, `waist_circumference`, `blood_pressure`, `pregnancy_status`, `symptoms`, `notes`, `pysical_exploration1`, `pe_details1`, `pysical_exploration2`, `pe_details2`, `pysical_exploration3`, `pe_details3`, `improvement`, `recovery`, `aggravate`, `diagnosis1`, `d_details1`, `diagnosis2`, `d_details2`, `diagnosis3`, `d_details3`, `d_notes`, `treatement1`, `dosis1`, `via1`, `periodicity1`, `treatement2`, `dosis2`, `via2`, `periodicity2`, `treatement3`, `dosis3`, `via3`, `periodicity3`, `labrequest1`, `labrequest2`, `labrequest3`, `labrequest4`, `labrequest5`, `tdetails1`, `tdetails2`, `tdetails3`, `labresult1`, `labresult2`, `labresult3`, `labresult4`, `labresult5`, `clinic_no`, `clinic_name`, `clinic_address`, `nurse_phone`, `med1`, `med2`, `med3`, `date1`, `time1`, `date2`, `time2`, `dust`, `metals`, `food`, `animals`, `medicine`, `other`, `immunization`, `pre`, `relation`, `pysical_exploration4`, `pysical_exploration5`, `pysical_exploration6`, `diagnosis4`, `diagnosis5`, `diagnosis6`, `treatement4`) VALUES
('1', 'sdd', 2147483647, 'askjas', 21, '24', '', '', '', '', 'Thu Apr 24 07:21:07 GMT+00:00 ', '', 'irlanda', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('454', '', 0, '', 0, '', '', '', '', '', 'Sun Apr 27 12:56:21 GMT 2014', '', 'irlanda', '', '', '', '', '', '', '', '', '', 'None', '', 'None', '', 'None', '', 'No', 'No', 'No', 'None', '', 'None', '', 'None', '', '', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', '', '', '', '', '', '', '', '', '35', 'fd', 'gd', 343, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('233', 'jhjhv', 98794654, 'hvjhv@gamil.com', 789, '546', 'Male', 'No', 'Yes', 'No', 'Fri May 02 23:19:23 GMT 2014', '', 'irlanda', '', '12', '89', '7', '78', '54', '', '', '', 'None', '', 'Back pain', '', 'Heart Paint', '', 'Yes', 'Yes', 'No', 'Heart Paint', '', 'Chest paint', '', 'Heart Paint', '', '', 'Heart Paint', 'Heart Paint', 'Chest paint', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'None', 'Chest paint', 'None', 'Heart Paint', 'None', '', '', '', '', '', '', '', '', '', '54s', '', 0, '', '', '', '', '', '', '', 'yes', 'no', 'no', 'yes', 'yes', '87561', 'ON TIME', '', '', '', '', '', '', '', '', ''),
('9626', 'RANI', 784512369, 'rani@lol.com', 789456, '98 ran hui', 'Femaal', 'No', 'No', 'No', 'Tue May 06 22:55:42 GMT 2014', 'Wed May 07 06:38:05 GMT 2014', 'irlanda', 'irlanda', '098', '7999999', '5665', '54535454', '88hh', '', '', '', 'Yes', '', 'No', '', 'Yes', '', 'Yes', 'No', 'Yes', 'Yes', '', 'No', '', 'Yes', '', '', 'Yes', '3 times', 'milk', '1', 'No', '2 times', 'milk', '1', 'Yes', '2 times', 'milk', '1', 'No', 'Yes', 'No', 'Yes', 'No', '', '', '', '', '', '', '', '', '', 'SANT', '', 0, '', '', '', '7-4-2014', '6:38', '7-4-2014', '6:38', 'no', 'yes', 'yes', 'no', 'no', 'nostril block', 'LATE', '', '', 'Yes', 'No', 'gsdjkdfkj', 'Yes', 'No', 'gsdjkdfkj', ''),
('3933', 'MAMA', 74125896, 'lks2lk.com', 78964, 'uikjnkjb', 'Male', 'Yes', 'No', 'Yes', 'Wed May 07 06:47:44 GMT 2014', '', 'irlanda', '', '67', '56', '98', '89', '87', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'idsjds', '', 0, '', '', '', '', '', '', '', 'no', 'yes', 'yes', 'no', 'no', 'jxkxn kc', 'ON TIME', '', '', '', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `children`
--

CREATE TABLE IF NOT EXISTS `children` (
  `curp` varchar(18) NOT NULL DEFAULT '',
  `name` varchar(30) NOT NULL,
  `phone` int(10) NOT NULL,
  `email` varchar(30) NOT NULL,
  `relation` varchar(30) NOT NULL,
  `zip` int(10) NOT NULL,
  `colony` text NOT NULL,
  `gender` varchar(6) NOT NULL,
  `cancer` varchar(5) NOT NULL,
  `diabetes` varchar(5) NOT NULL,
  `hypertension` varchar(5) NOT NULL,
  `created_on` varchar(30) NOT NULL,
  `updated_on` varchar(30) NOT NULL,
  `created_by` varchar(30) NOT NULL,
  `updated_by` varchar(30) NOT NULL,
  `temperature` varchar(20) NOT NULL,
  `weight` varchar(11) NOT NULL,
  `height` varchar(11) NOT NULL,
  `waist_circumference` varchar(11) NOT NULL,
  `blood_pressure` varchar(11) NOT NULL,
  `pregnancy_status` varchar(11) NOT NULL,
  `symptoms` text NOT NULL,
  `notes` text NOT NULL,
  `pysical_exploration1` varchar(30) NOT NULL,
  `pe_details1` text NOT NULL,
  `pysical_exploration2` varchar(30) NOT NULL,
  `pe_details2` text NOT NULL,
  `pysical_exploration3` varchar(30) NOT NULL,
  `pe_details3` text NOT NULL,
  `improvement` varchar(10) NOT NULL,
  `recovery` varchar(10) NOT NULL,
  `aggravate` varchar(10) NOT NULL,
  `diagnosis1` varchar(30) NOT NULL,
  `d_details1` text NOT NULL,
  `diagnosis2` varchar(30) NOT NULL,
  `d_details2` text NOT NULL,
  `diagnosis3` varchar(30) NOT NULL,
  `d_details3` text NOT NULL,
  `d_notes` text NOT NULL,
  `treatement1` varchar(11) NOT NULL,
  `dosis1` varchar(11) NOT NULL,
  `via1` varchar(11) NOT NULL,
  `periodicity1` varchar(30) NOT NULL,
  `treatement2` varchar(30) NOT NULL,
  `dosis2` varchar(30) NOT NULL,
  `via2` varchar(30) NOT NULL,
  `periodicity2` varchar(11) NOT NULL,
  `treatement3` varchar(11) NOT NULL,
  `dosis3` varchar(11) NOT NULL,
  `via3` varchar(11) NOT NULL,
  `periodicity3` varchar(11) NOT NULL,
  `labrequest1` varchar(11) NOT NULL,
  `labrequest2` varchar(11) NOT NULL,
  `labrequest3` varchar(11) NOT NULL,
  `labrequest4` varchar(11) NOT NULL,
  `labrequest5` varchar(11) NOT NULL,
  `tdetails1` text NOT NULL,
  `tdetails2` text NOT NULL,
  `tdetails3` text NOT NULL,
  `labresult1` text NOT NULL,
  `labresult2` text NOT NULL,
  `labresult3` text NOT NULL,
  `labresult4` text NOT NULL,
  `labresult5` text NOT NULL,
  `clinic_no` varchar(30) NOT NULL,
  `clinic_name` varchar(30) NOT NULL,
  `clinic_address` text NOT NULL,
  `nurse_phone` varchar(10) NOT NULL,
  `med1` varchar(11) NOT NULL,
  `med2` varchar(11) NOT NULL,
  `med3` varchar(11) NOT NULL,
  `date1` varchar(30) NOT NULL,
  `time1` varchar(30) NOT NULL,
  `date2` varchar(11) NOT NULL,
  `time2` varchar(11) NOT NULL,
  `dust` varchar(5) NOT NULL,
  `metals` varchar(5) NOT NULL,
  `food` varchar(5) NOT NULL,
  `animals` varchar(5) NOT NULL,
  `medicine` varchar(5) NOT NULL,
  `other` varchar(300) NOT NULL,
  `immunization` varchar(20) NOT NULL,
  `pre` varchar(200) NOT NULL,
  `pysical_exploration4` varchar(20) NOT NULL,
  `pysical_exploration5` varchar(30) NOT NULL,
  `pysical_exploration6` varchar(11) NOT NULL,
  `diagnosis4` varchar(11) NOT NULL,
  `diagnosis5` varchar(11) NOT NULL,
  `diagnosis6` varchar(11) NOT NULL,
  `treatement4` varchar(100) NOT NULL,
  PRIMARY KEY (`curp`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `children`
--

INSERT INTO `children` (`curp`, `name`, `phone`, `email`, `relation`, `zip`, `colony`, `gender`, `cancer`, `diabetes`, `hypertension`, `created_on`, `updated_on`, `created_by`, `updated_by`, `temperature`, `weight`, `height`, `waist_circumference`, `blood_pressure`, `pregnancy_status`, `symptoms`, `notes`, `pysical_exploration1`, `pe_details1`, `pysical_exploration2`, `pe_details2`, `pysical_exploration3`, `pe_details3`, `improvement`, `recovery`, `aggravate`, `diagnosis1`, `d_details1`, `diagnosis2`, `d_details2`, `diagnosis3`, `d_details3`, `d_notes`, `treatement1`, `dosis1`, `via1`, `periodicity1`, `treatement2`, `dosis2`, `via2`, `periodicity2`, `treatement3`, `dosis3`, `via3`, `periodicity3`, `labrequest1`, `labrequest2`, `labrequest3`, `labrequest4`, `labrequest5`, `tdetails1`, `tdetails2`, `tdetails3`, `labresult1`, `labresult2`, `labresult3`, `labresult4`, `labresult5`, `clinic_no`, `clinic_name`, `clinic_address`, `nurse_phone`, `med1`, `med2`, `med3`, `date1`, `time1`, `date2`, `time2`, `dust`, `metals`, `food`, `animals`, `medicine`, `other`, `immunization`, `pre`, `pysical_exploration4`, `pysical_exploration5`, `pysical_exploration6`, `diagnosis4`, `diagnosis5`, `diagnosis6`, `treatement4`) VALUES
('', 'ds', 0, '', '', 0, '', 'Male', '', '', '', 'Sun Apr 27 11:50:22 GMT 2014', '', 'irlanda', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('007', '', 0, '', '', 0, '', 'Male', '', '', '', 'Sun Apr 27 11:47:26 GMT 2014', '', 'irlanda', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('12', '', 0, '', '', 0, '', '', '', 'Yes', 'Yes', 'Sun Apr 27 12:47:48 GMT 2014', '', 'irlanda', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '23as', '892389', 'sadasd', '82398782', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('13', 'jd', 9865, 'ldsd@msd/cpv ', 'paaa', 2213, 'kjdskjds', 'Femaal', 'No', 'No', 'No', '0000-00-00', '', 'irlanda', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'mnv', '', '', '', '', '', '', '', '', '', '', '', 'kcv', '', '', 'zxcz', 'cc', 'c', 'zxczxcxzc', 'zxczc', '', '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('17', 'cxx', 898, '78yhjjh', 'vgv', 666, '7y7', 'Male', 'No', 'Yes', 'Yes', 'Thu Apr 24 06:58:48 GMT+00:00 ', 'Sun Apr 27 17:27:25 GMT 2014', 'arpit', 'irlanda', '', '', '', '', '', '', '', '', 'Chest paint', '', 'Toothache', '', 'Insomania', '', 'No', 'No', 'No', 'Lungs Faliure', '', 'Muscles Damage', '', 'Asthama', '', '', 'Disprin', '3 times', 'Hot water', '2', 'TZ norflox', '2 times', 'milk', '1', 'Asthama', '2 times', 'milk', '1', 'Heart Paint', 'Back pain', 'Back pain', 'Back pain', 'Back pain', '', '', '', 'gajbsjhhhbabbsbbhbfyuba', 'bbahuh', '', '', '', '', '', '', '0', '', '', '', '21-5-2013', '13:40', '27-11-2013', '17:53', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('433', '', 0, '', '', 0, '', 'Male', '', '', '', 'Sun Apr 27 11:50:14 GMT 2014', '', 'irlanda', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('45', 'kaka', 2147483647, 'haha@j.com', 'nai apata', 789456, 'jbhbtdj hgctxth', 'Male', 'Yes', 'No', 'No', 'Thu Apr 24 21:55:13 GMT 2014', 'Thu Apr 24 21:55:21 GMT 2014', 'arpit', 'arpit', '78', '15', '789', '64', '45', '', 'hgvy', 'vvgvg', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('454', 'jkdjks', 433, 'jksd', 'jkkjv', 3243, '034', 'Femaal', 'No', 'No', 'No', '0000-00-00', '0000-00-00', 'arpit', '', 'm,f', '', '', '', ' mv', ',mv', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('9340934', 'kkkc', 909, 'kjdk', 'k ddc', 932023, 'nd', 'dm', '9wei', 'nd', 'jsnd', '0000-00-00', '0000-00-00', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('9626333', 'gkkamina', 2147483647, 'dfdd@gmail.com', 'kaka', 98398, 'kmdskjd', 'Male', 'Yes', 'No', 'Yes', '0000-00-00', '0000-00-00', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('as', 'ds', 0, 'smdsm', 'msd', 0, 'nmsd', 'nmsd', 'msd', 's,d', 'msd', '0000-00-00', '0000-00-00', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '0', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('xczxczczcz', '', 0, '', '', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('11', 'boss', 2147483647, 'll@yahoo.com', 'brother', 224, 'dala', 'Male', 'Yes', 'Yes', 'Yes', 'Mon Apr 28 11:28:51 EDT 2014', '', 'irlanda', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '111', 'lol', '232', '2323345', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('123', 'irlanda ', 12345678, 'lol@lol.com', 'sasa ', 123, 'col', 'Femaal', 'No', 'No', 'No', 'Mon Apr 28 23:59:04 GMT+08:00 ', 'Thu May 01 01:36:07 GMT+08:00 ', 'irlanda', 'irlanda', '', '', '', '', '', '', '', '', 'Heart Paint', '', 'Stomache', '', 'No clue', '', 'No', 'No', 'No', '', '', '', '', '', '', '', '', '', '', '1', '', '', '', '1', '', '', '', '1', 'Chest paint', '', '', '', '', '', '', '', 'lol', 'non\n', 'save', 'lol', 'lol', '123', 'clinic', 'no 2, road', '3443', '', '', '', '29-3-2014', '16:15', '29-3-2014', '16:15', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('98', '', 0, '', '', 0, '', '', 'No', '', '', 'Fri May 02 09:21:47 GMT 2014', '', 'irlanda', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'no', 'no', 'no', 'no', 'no', '', 'ON TI', '', '', '', '', '', '', '', ''),
('89', '', 0, '', '', 0, '', '', '', '', '', 'Fri May 02 09:24:46 GMT 2014', '', 'irlanda', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'no', 'no', 'no', 'no', 'no', '', '', '', '', '', '', '', '', '', ''),
('786', '', 0, '', '', 0, '', '', '', '', '', 'Fri May 02 20:51:47 GMT 2014', '', 'irlanda', '', '67', '', '13', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'yes', 'no', 'no', 'yes', 'no', '', 'ON TI', '', '', '', '', '', '', '', ''),
('989', '', 0, '', '', 0, '', 'Femaal', '', '', 'No', 'Fri May 02 20:53:52 GMT 2014', '', 'irlanda', '', '', '98', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'Parivar Seva', '', '', '', '', '', '', '', '', '', 'yes', 'no', 'no', 'no', 'no', '', 'ON TIME', '', '', '', '', '', '', '', ''),
('9888', 'AK', 879456622, 'mom@mom.com', 'papa', 789456, 'lkkkj', 'Male', 'Yes', 'No', 'No', 'Fri May 02 21:18:11 GMT 2014', 'Wed May 07 12:48:27 GMT 2014', 'irlanda', 'irlanda', '87', '12', '67', '99', '99', '', 'no symptoms', 'take rest', 'Yes', 'gooo', 'No', 'voooo', 'Yes', 'Bye', 'No', 'Yes', 'No', 'Yes', '', 'No', '', 'Yes', '', 'go to hell', 'Yes', '3 times', 'milk', '1', 'Yes', '2 times', 'milk', '2', 'No', '2 times', 'milk', '1', 'No', 'Yes', 'Yes', 'No', 'No', '', '', '', '', '', '', '', '', '', '7778', '', '', '', '', '', '', '', '', '', 'no', 'yes', 'no', 'yes', 'yes', '74515555', 'LATE', '', 'No', 'Yes', 'What to do', 'No', 'Yes', 'What to do', '');

-- --------------------------------------------------------

--
-- Table structure for table `doctor_login`
--

CREATE TABLE IF NOT EXISTS `doctor_login` (
  `name` varchar(30) NOT NULL,
  `licence` varchar(18) NOT NULL,
  PRIMARY KEY (`licence`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `doctor_login`
--

INSERT INTO `doctor_login` (`name`, `licence`) VALUES
('espinosa', 'azd9626'),
('sufi', 'junior');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE IF NOT EXISTS `feedback` (
  `name` varchar(10) NOT NULL,
  `mail` varchar(25) NOT NULL,
  `rate` int(5) NOT NULL,
  `comment` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`name`, `mail`, `rate`, `comment`) VALUES
('manas', 'manas.rai2011@vit.ac.in', 5, '  aa'),
('manas', 'manas.rai2011@vit.ac.in', 5, '  aa');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `Name` varchar(30) NOT NULL,
  `Licence` varchar(30) NOT NULL,
  `clinic_no` varchar(33) NOT NULL,
  `clinic_name` varchar(30) NOT NULL,
  `address` text NOT NULL,
  `phone_no` int(10) NOT NULL,
  PRIMARY KEY (`Licence`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`Name`, `Licence`, `clinic_no`, `clinic_name`, `address`, `phone_no`) VALUES
('irlanda', 'azd9626', '12', 'APJ', 'streets', 789456123),
('arpit', 'azd96263', '34', 'KKR', 'road', 789456321),
('espinosa', 'azd962639', '46', 'qwert', 'qas', 741258963);

-- --------------------------------------------------------

--
-- Table structure for table `technician`
--

CREATE TABLE IF NOT EXISTS `technician` (
  `Name` varchar(30) NOT NULL,
  `Licence` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `technician`
--

INSERT INTO `technician` (`Name`, `Licence`) VALUES
('saint', 'qwerty');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
