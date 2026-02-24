# PoundSaver-AI Quick Start Guide

Compare prices across 7 major UK supermarkets: Tesco, Asda, Lidl, Costco, B&M, Iceland, and White Rose.

## Prerequisites

Ensure you have the following installed:
- **Java 21** or higher
- **Node.js 20** or higher
- **Docker** and **Docker Compose**
- **Maven 3.9+**
- **Git**

## Quick Start (5 Minutes)

### 1. Clone and Setup

```bash
git clone https://github.com/JagadeshBandi/PoundSaver-AI.git
cd PoundSaver-AI
chmod +x scripts/*.sh
./scripts/setup.sh
```

### 2. Configure Environment

Copy the example environment file and update with your values:

```bash
cp .env.example .env
```

Edit `.env` and add your OpenAI API key:
```
OPENAI_API_KEY=sk-your-actual-api-key-here
```

### 3. Start Infrastructure

```bash
docker-compose up -d postgres redis kafka zookeeper prometheus grafana loki
```

Wait 30 seconds for services to initialize.

### 4. Start Backend Services

```bash
./scripts/start-backend.sh
```

This starts all 6 microservices:
- API Gateway (8080)
- Scraper Service (8081)
- Product Service (8082)
- Price Service (8083)
- AI Service (8084)
- Analytics Service (8085)

### 5. Start Frontend

```bash
cd frontend/web
npm install
npm run dev
```

### 6. Access the Application

- **Frontend**: http://localhost:3000
- **API Gateway**: http://localhost:8080
- **Grafana**: http://localhost:3001 (admin/admin)
- **Prometheus**: http://localhost:9090

## Testing the Application

### Search for Products

1. Open http://localhost:3000
2. Enter a product name (e.g., "milk", "bread", "eggs")
3. Click Search
4. View results sorted by price

### View Price Comparison

Navigate to the Compare page to see:
- Cheapest retailer
- Average price
- Price range
- Max savings
- Price gap chart

### Monitor with Grafana

1. Open http://localhost:3001
2. Login with admin/admin
3. View dashboards for:
   - API performance
   - Scraping health
   - Price gap analysis
   - System metrics

## Running Tests

### Unit Tests

```bash
mvn test
```

### Integration Tests

```bash
mvn verify
```

### E2E Tests (Cucumber)

```bash
cd tests/e2e
mvn test
```

### Frontend Tests

```bash
cd frontend/web
npm test
```

## Stopping Services

### Stop Backend

```bash
./scripts/stop-backend.sh
```

### Stop Infrastructure

```bash
docker-compose down
```

## Docker Deployment

### Build All Services

```bash
docker-compose build
```

### Start Everything

```bash
docker-compose up -d
```

### View Logs

```bash
docker-compose logs -f
```

## Kubernetes Deployment

### Deploy to Kubernetes

```bash
kubectl apply -f infrastructure/kubernetes/namespace.yml
kubectl apply -f infrastructure/kubernetes/
```

### Check Status

```bash
kubectl get pods -n poundsaver
kubectl get services -n poundsaver
```

## Troubleshooting

### Port Already in Use

If a port is already in use, update the port in the respective `application.yml` file.

### Database Connection Issues

Ensure PostgreSQL is running:
```bash
docker-compose ps postgres
```

### Kafka Connection Issues

Ensure Kafka and Zookeeper are running:
```bash
docker-compose ps kafka zookeeper
```

### Frontend Build Issues

Clear cache and reinstall:
```bash
cd frontend/web
rm -rf node_modules package-lock.json
npm install
```

### Backend Build Issues

Clean and rebuild:
```bash
mvn clean install -DskipTests
```

## Next Steps

- Read the [Architecture Documentation](architecture/ARCHITECTURE.md)
- Explore the [API Documentation](api/API.md)
- Check out [Contributing Guidelines](../CONTRIBUTING.md)
- Review [Deployment Guide](deployment/DEPLOYMENT.md)

## Support

For issues and questions:
- Open an issue on GitHub
- Check existing documentation
- Review logs in `./logs/` directory
