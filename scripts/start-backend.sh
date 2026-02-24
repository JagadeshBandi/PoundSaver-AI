#!/bin/bash

set -e

echo "Starting PoundSaver-AI backend services..."

source .env

start_service() {
    local service_name=$1
    local port=$2
    
    echo "Starting $service_name on port $port..."
    cd backend/$service_name
    mvn spring-boot:run > ../../logs/$service_name.log 2>&1 &
    echo $! > ../../logs/$service_name.pid
    cd ../..
    echo "$service_name started (PID: $(cat logs/$service_name.pid))"
}

mkdir -p logs

start_service "api-gateway" 8080
sleep 5

start_service "scraper-service" 8081
start_service "product-service" 8082
start_service "price-service" 8083
start_service "ai-service" 8084
start_service "analytics-service" 8085

echo ""
echo "All backend services started successfully!"
echo "Check logs in ./logs/ directory"
echo ""
echo "To stop services, run: ./scripts/stop-backend.sh"
