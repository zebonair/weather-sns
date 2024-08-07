--src\main\resources\sql\create_notification_status_table.sql

-- Create table
CREATE TABLE notification_status (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50) UNIQUE NOT NULL
);

-- Insert default statuses
INSERT INTO notification_status (status) VALUES ('sent');
INSERT INTO notification_status (status) VALUES ('pending');
INSERT INTO notification_status (status) VALUES ('failed');
