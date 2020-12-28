-- @Author: Cristiano Almeida (cjcalmeida)
-- @Date: 30/09/2019
-- @Description: Database schema and data for Car Loan Project - a microservice architecture PoC.
-- 				 This architecture uses microservice pattern 'Database per Service'.

-- USERS
CREATE USER user_liquibase_consumer WITH PASSWORD 'l1qu1b4s3_c0nsum3r';
CREATE USER user_liquibase_locale WITH PASSWORD 'l1qu1b4s3_l0c4le';
CREATE USER user_liquibase_notification WITH PASSWORD 'l1qu1b4s3_n0t1f1c4t10n';
CREATE USER user_liquibase_proposal WITH PASSWORD 'l1qu1b4s3_pr0p0s4l';
CREATE USER user_liquibase_vehicle WITH PASSWORD 'l1qu1b4s3_v3h1cl3';

CREATE USER user_consumer WITH PASSWORD 'c0nsum3r';
CREATE USER user_locale WITH PASSWORD 'l0c4le';
CREATE USER user_notification WITH PASSWORD 'n0t1f1c4t10n';
CREATE USER user_proposal WITH PASSWORD 'pr0p0s4l';
CREATE USER user_vehicle WITH PASSWORD 'v3h1cl3';

-- DATABASES

CREATE DATABASE consumer OWNER user_liquibase_consumer;
CREATE DATABASE locale OWNER user_liquibase_locale;
CREATE DATABASE notification OWNER user_liquibase_notification;
CREATE DATABASE proposal OWNER user_liquibase_proposal;
CREATE DATABASE vehicle OWNER user_liquibase_vehicle;
