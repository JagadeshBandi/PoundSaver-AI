#!/bin/bash

echo "Stopping PoundSaver-AI backend services..."

stop_service() {
    local service_name=$1
    
    if [ -f logs/$service_name.pid ]; then
        pid=$(cat logs/$service_name.pid)
        echo "Stopping $service_name (PID: $pid)..."
        kill $pid 2>/dev/null || echo "$service_name already stopped"
        rm logs/$service_name.pid
    else
        echo "$service_name is not running"
    fi
}

stop_service "analytics-service"
stop_service "ai-service"
stop_service "price-service"
stop_service "product-service"
stop_service "scraper-service"
stop_service "api-gateway"

echo ""
echo "All backend services stopped!"
