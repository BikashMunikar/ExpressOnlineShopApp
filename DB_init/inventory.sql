CREATE DATABASE IF NOT EXISTS "inventoryservice";
CREATE USER 'inventory'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'inventory'@'%' WITH GRANT OPTION;