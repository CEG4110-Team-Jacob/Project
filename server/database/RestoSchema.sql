-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: restoschema
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary view structure for view `host view`
--

DROP TABLE IF EXISTS `host view`;
/*!50001 DROP VIEW IF EXISTS `host view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `host view` AS SELECT 
 1 AS `TableNumber`,
 1 AS `TableOccupancy`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `ItemID` int NOT NULL,
  `ItemType` enum('Food','Beverage') NOT NULL,
  `ItemName` varchar(45) NOT NULL,
  `ItemDesc` varchar(255) NOT NULL,
  `Price` int NOT NULL,
  `InStock` tinyint NOT NULL,
  `IsAvailable` tinyint NOT NULL,
  PRIMARY KEY (`ItemID`),
  UNIQUE KEY `ItemID_UNIQUE` (`ItemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `manager view active worker`
--

DROP TABLE IF EXISTS `manager view active worker`;
/*!50001 DROP VIEW IF EXISTS `manager view active worker`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `manager view active worker` AS SELECT 
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `Job`,
 1 AS `Age`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `manager view inactive worker`
--

DROP TABLE IF EXISTS `manager view inactive worker`;
/*!50001 DROP VIEW IF EXISTS `manager view inactive worker`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `manager view inactive worker` AS SELECT 
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `Job`,
 1 AS `Age`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `OrderID` int NOT NULL,
  `Time_Ordered` time NOT NULL,
  `Time_Completed` time DEFAULT NULL,
  `Status` enum('Cooked','Ordered','InProgress','Delivered','Completed','Canceled') NOT NULL,
  `Worker_WorkerID` int NOT NULL,
  `Tables_Table_TableId` int NOT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `fk_Orders_Worker1_idx` (`Worker_WorkerID`),
  KEY `fk_Orders_Tables_Table1_idx` (`Tables_Table_TableId`),
  CONSTRAINT `fk_Orders_Tables_Table1` FOREIGN KEY (`Tables_Table_TableId`) REFERENCES `tables_table` (`TableId`),
  CONSTRAINT `fk_Orders_Worker1` FOREIGN KEY (`Worker_WorkerID`) REFERENCES `worker` (`WorkerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orders_has_items`
--

DROP TABLE IF EXISTS `orders_has_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_has_items` (
  `Orders_OrderID` int NOT NULL,
  `Items_ItemID` int NOT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`Orders_OrderID`,`Items_ItemID`),
  KEY `fk_Orders_has_Items_Items1_idx` (`Items_ItemID`),
  KEY `fk_Orders_has_Items_Orders_idx` (`Orders_OrderID`),
  CONSTRAINT `fk_Orders_has_Items_Items1` FOREIGN KEY (`Items_ItemID`) REFERENCES `items` (`ItemID`),
  CONSTRAINT `fk_Orders_has_Items_Orders` FOREIGN KEY (`Orders_OrderID`) REFERENCES `orders` (`OrderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tables_table`
--

DROP TABLE IF EXISTS `tables_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tables_table` (
  `TableId` int NOT NULL,
  `TableNumber` int NOT NULL,
  `TableOccupancy` int NOT NULL,
  `IsOccupied` tinyint NOT NULL,
  `IsActive` varchar(45) NOT NULL,
  `Worker_WorkerID` int(10) unsigned zerofill NOT NULL,
  PRIMARY KEY (`TableId`),
  UNIQUE KEY `TableId_UNIQUE` (`TableId`),
  KEY `fk_Tables_Table_Worker1_idx` (`Worker_WorkerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `total cost`
--

DROP TABLE IF EXISTS `total cost`;
/*!50001 DROP VIEW IF EXISTS `total cost`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `total cost` AS SELECT 
 1 AS `OrderID`,
 1 AS `ItemName`,
 1 AS `Price`,
 1 AS `Quantity`,
 1 AS `Price Sum`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `waiter view`
--

DROP TABLE IF EXISTS `waiter view`;
/*!50001 DROP VIEW IF EXISTS `waiter view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `waiter view` AS SELECT 
 1 AS `WorkerID`,
 1 AS `FirstName`,
 1 AS `LastName`,
 1 AS `TableNumber`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `worker`
--

DROP TABLE IF EXISTS `worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worker` (
  `WorkerID` int NOT NULL,
  `UserName` varchar(60) NOT NULL,
  `PasswordHash` varchar(45) NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `Age` int NOT NULL,
  `Job` enum('Waiter','Cook','Host','Manager') NOT NULL,
  `IsActive` tinyint NOT NULL,
  PRIMARY KEY (`WorkerID`),
  UNIQUE KEY `WorkerID_UNIQUE` (`WorkerID`),
  UNIQUE KEY `UserName_UNIQUE` (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `host view`
--

/*!50001 DROP VIEW IF EXISTS `host view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `host view` AS select `tables_table`.`TableNumber` AS `TableNumber`,`tables_table`.`TableOccupancy` AS `TableOccupancy` from `tables_table` where (`tables_table`.`IsOccupied` = 0) order by `tables_table`.`TableOccupancy` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `manager view active worker`
--

/*!50001 DROP VIEW IF EXISTS `manager view active worker`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `manager view active worker` AS select `worker`.`LastName` AS `LastName`,`worker`.`FirstName` AS `FirstName`,`worker`.`Job` AS `Job`,`worker`.`Age` AS `Age` from `worker` where (`worker`.`IsActive` = 1) order by `worker`.`Job`,`worker`.`LastName` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `manager view inactive worker`
--

/*!50001 DROP VIEW IF EXISTS `manager view inactive worker`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `manager view inactive worker` AS select `worker`.`LastName` AS `LastName`,`worker`.`FirstName` AS `FirstName`,`worker`.`Job` AS `Job`,`worker`.`Age` AS `Age` from `worker` where (`worker`.`IsActive` = 0) order by `worker`.`Job`,`worker`.`LastName` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `total cost`
--

/*!50001 DROP VIEW IF EXISTS `total cost`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `total cost` AS select `orders_has_items`.`Orders_OrderID` AS `OrderID`,`items`.`ItemName` AS `ItemName`,`items`.`Price` AS `Price`,`orders_has_items`.`Quantity` AS `Quantity`,sum((`items`.`Price` * `orders_has_items`.`Quantity`)) AS `Price Sum` from (`items` join `orders_has_items`) where (`orders_has_items`.`Items_ItemID` = `items`.`ItemID`) group by `orders_has_items`.`Orders_OrderID`,`items`.`ItemName`,`items`.`Price`,`orders_has_items`.`Quantity` with rollup */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `waiter view`
--

/*!50001 DROP VIEW IF EXISTS `waiter view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `waiter view` AS select `worker`.`WorkerID` AS `WorkerID`,`worker`.`FirstName` AS `FirstName`,`worker`.`LastName` AS `LastName`,`tables_table`.`TableNumber` AS `TableNumber` from (`tables_table` join `worker`) where ((`worker`.`Job` = 'Waiter') and (`tables_table`.`Worker_WorkerID` = `worker`.`WorkerID`)) order by `tables_table`.`TableNumber` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-11 17:55:47
