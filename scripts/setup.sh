#!/bin/bash

set -e

echo "Setting up PoundSaver-AI development environment..."

echo "Checking prerequisites..."
command -v java >/dev/null 2>&1 || { echo "Java 21 is required but not installed. Aborting." >&2; exit 1; }
command -v node >/dev/null 2>&1 || { echo "Node.js 20 is required but not installed. Aborting." >&2; exit 1; }
command -v docker >/dev/null 2>&1 || { echo "Docker is required but not installed. Aborting." >&2; exit 1; }

echo "Creating .env file..."
if [ ! -f .env ]; then
    cat > .env << EOF
# Database
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=poundsaver
POSTGRES_USER=admin
POSTGRES_PASSWORD=password

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379

# Kafka
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# OpenAI
OPENAI_API_KEY=your_openai_api_key_here

# Application
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
EOF
    echo ".env file created. Please update with your actual values."
else
    echo ".env file already exists."
fi

echo "Installing Playwright browsers..."
cd tests/e2e
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
cd ../..

echo "Installing frontend dependencies..."
cd frontend/web
npm install
cd ../..

echo "Building backend services..."
mvn clean install -DskipTests

echo "Setup complete!"
echo ""
echo "To start the application:"
echo "1. Start infrastructure: docker-compose up -d postgres redis kafka zookeeper prometheus grafana loki"
echo "2. Start backend services: ./scripts/start-backend.sh"
echo "3. Start frontend: cd frontend/web && npm run dev"
echo ""
echo "Access points:"
echo "- Frontend: http://localhost:3000"
echo "- API Gateway: http://localhost:8080"
echo "- Grafana: http://localhost:3001 (admin/admin)"
echo "- Prometheus: http://localhost:9090"
