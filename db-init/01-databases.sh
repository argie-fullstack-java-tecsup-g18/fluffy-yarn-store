#!/bin/bash
# Runs once, when the MySQL data volume is empty.
# Creates the database for customer-service.
set -e
mysql -uroot -p"${MYSQL_ROOT_PASSWORD}" <<SQL
CREATE DATABASE IF NOT EXISTS fluffy_yarn_customer_db;
GRANT ALL PRIVILEGES ON fluffy_yarn_db.* TO '${MYSQL_USER}'@'%';

CREATE DATABASE IF NOT EXISTS fluffy_yarn_commercial_db;
GRANT ALL PRIVILEGES ON fluffy_yarn_commercial_db.* TO '${MYSQL_USER}'@'%';
SQL
