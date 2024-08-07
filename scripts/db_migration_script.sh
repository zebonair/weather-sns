#!/bin/bash

# Define database connection details with prompt
read -p "Enter the database name: " DB_NAME
read -p "Enter the database user : " DB_USER
read -p "Enter the database host [default localhost]: " DB_HOST
read -sp "Enter the database password: " DB_PASSWORD
echo # for a newline after the password input
read -p "Enter the database port [default 5432]: " DB_PORT

# Set default values if not provided
DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-5432}

#export variables
export DB_NAME
export DB_USER
export DB_HOST
export DB_PASSWORD
export DB_PORT

# Path to SQL files
SQL_DIR="src/main/resources/sql" # whole path

# Check for SQL directory
if [ ! -d "$SQL_DIR" ]; then
    echo "SQL directory $SQL_DIR not found!"
    exit 1
fi

# List of SQL files to execute
SQL_FILES=("create_users_table.sql" "create_notification_status_table.sql" create_notifications_table.sql)


# Loop through and execute each SQL file
for sql_file in "${SQL_FILES[@]}"
do
    echo ">>>> Executing $sql_file..."
    psql -h "$DB_HOST" -U "$DB_USER" -d "$DB_NAME" -p "$DB_PORT" -f "$SQL_DIR/$sql_file"
done

echo "All SQL files executed."

unset DB_PASSWORD