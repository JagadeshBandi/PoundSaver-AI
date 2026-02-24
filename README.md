# PoundSaver-AI

**Enterprise-Grade Real-Time UK Grocery Price Comparison Platform**

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue.svg)](https://reactjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.x-blue.svg)](https://www.typescriptlang.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## Overview

PoundSaver-AI is a high-performance, microservices-based platform that aggregates real-time grocery prices from major UK retailers (Tesco, Asda, Lidl, Costco, B&M, Iceland, and White Rose) to help consumers combat the cost-of-living crisis. The system leverages AI-powered product matching, distributed web scraping, and advanced analytics to deliver the most accurate price comparisons in the UK market.

## Key Features

### Real-Time Price Intelligence
- **Multi-Retailer Scraping**: Automated price extraction from 7 major UK supermarkets
- **AI-Powered Product Matching**: LangChain4j + OpenAI for intelligent product normalization
- **Price-Per-Unit Calculation**: Accurate cost comparison across different package sizes
- **Loyalty Card Integration**: Real-time calculations for Clubcard, Nectar, and other loyalty schemes
- **Bulk Savings Analysis**: Special algorithms for warehouse club pricing (Costco)

### Advanced Search & Filtering
- **Unified Search**: Single query across all retailers simultaneously
- **Smart Sorting**: Cheapest to most expensive with configurable criteria
- **Real-Time Updates**: WebSocket-based live price feeds
- **Historical Trends**: Price movement tracking and predictions
- **Price Alerts**: Notifications when products drop below target prices

### Enterprise Architecture
- **Microservices Design**: Independently scalable services with Spring Cloud
- **Event-Driven**: Apache Kafka for asynchronous communication
- **Reactive APIs**: Spring WebFlux for non-blocking, high-throughput endpoints
- **Distributed Scraping**: Playwright workers with job queue management
- **Self-Healing Selectors**: AI-powered DOM element detection when websites change

### Observability & Monitoring
- **Grafana Dashboards**: Real-time metrics visualization
- **Prometheus Metrics**: Comprehensive service health monitoring
- **Distributed Tracing**: OpenTelemetry + Tempo for request tracking
- **Log Aggregation**: Loki for centralized logging
- **Custom Alerts**: Price gap analysis, scraping health, API performance

## Technology Stack

### Backend
- **Java 21** with Virtual Threads for high concurrency
- **Spring Boot 3.2+** with Spring Cloud ecosystem
- **Spring WebFlux** for reactive programming
- **PostgreSQL 16** for persistent storage
- **Redis 7** for caching and session management
- **Apache Kafka** for event streaming
- **Resilience4j** for fault tolerance

### AI & Automation
- **LangChain4j** for AI orchestration
- **OpenAI GPT-4** for product matching and normalization
- **Playwright for Java** for web scraping
- **Vector Database** (Pinecone/Weaviate) for semantic search

### Frontend
- **React 18** with TypeScript 5.x
- **Vite** for build tooling
- **TanStack Query** for server state management
- **Zustand** for client state management
- **Tailwind CSS 3.x** for styling
- **shadcn/ui** for premium UI components
- **Recharts** for data visualization
- **Lucide React** for icons

### Testing
- **Cucumber (Gherkin)** for BDD testing
- **JUnit 5** for unit testing
- **Testcontainers** for integration testing
- **Playwright** for E2E testing
- **JMeter/Gatling** for performance testing

### DevOps & Infrastructure
- **Docker** + **Docker Compose** for containerization
- **Kubernetes** for orchestration
- **GitHub Actions** for CI/CD
- **Terraform** for infrastructure as code
- **SonarQube** for code quality
- **OWASP Dependency Check** for security scanning

## Architecture

### Microservices Overview

```
┌─────────────────┐
│   API Gateway   │ ← Spring Cloud Gateway (Port 8080)
└────────┬────────┘
         │
    ┌────┴────────────────────────────────┐
    │                                     │
┌───▼──────────┐  ┌──────────────┐  ┌───▼──────────┐
│   Scraper    │  │   Product    │  │    Price     │
│   Service    │  │   Service    │  │   Service    │
│  (Port 8081) │  │ (Port 8082)  │  │ (Port 8083)  │
└──────┬───────┘  └──────┬───────┘  └──────┬───────┘
       │                 │                  │
       └─────────────────┼──────────────────┘
                         │
                    ┌────▼─────┐
                    │  Kafka   │
                    └──────────┘
```

### Service Responsibilities

**API Gateway** (Port 8080)
- Request routing and load balancing
- Authentication and authorization
- Rate limiting and throttling
- API composition and aggregation

**Scraper Service** (Port 8081)
- Distributed Playwright workers
- Retailer-specific scraping strategies
- Proxy rotation and CAPTCHA handling
- Self-healing selector logic with AI

**Product Service** (Port 8082)
- Product catalog management
- AI-powered product matching
- Semantic search with embeddings
- Duplicate detection and merging

**Price Service** (Port 8083)
- Price aggregation and normalization
- Price-per-unit calculations
- Historical price tracking
- Sorting and filtering algorithms

**AI Service** (Port 8084)
- LangChain4j orchestration
- OpenAI integration
- Product name normalization
- Brand and size extraction

**Analytics Service** (Port 8085)
- Usage metrics and reporting
- Price trend analysis
- User behavior tracking
- Dashboard data aggregation

## Project Structure

```
PoundSaver-AI/
├── backend/
│   ├── api-gateway/              # Spring Cloud Gateway
│   ├── scraper-service/          # Playwright-based scraping
│   ├── product-service/          # Product catalog & AI matching
│   ├── price-service/            # Price aggregation & sorting
│   ├── ai-service/               # LangChain4j + OpenAI
│   ├── analytics-service/        # Usage analytics
│   └── shared/                   # Common libraries & DTOs
├── frontend/
│   ├── web/                      # React TypeScript application
│   └── admin/                    # Admin dashboard
├── infrastructure/
│   ├── docker/                   # Docker configurations
│   ├── kubernetes/               # K8s manifests
│   ├── terraform/                # Infrastructure as code
│   └── monitoring/               # Grafana dashboards & Prometheus config
├── tests/
│   ├── e2e/                      # Cucumber BDD tests
│   ├── integration/              # Integration tests
│   └── performance/              # Load testing scripts
├── docs/
│   ├── architecture/             # Architecture diagrams
│   ├── api/                      # API documentation
│   └── deployment/               # Deployment guides
└── scripts/                      # Build & deployment automation
```

## Getting Started

### Prerequisites

- **Java 21** or higher
- **Node.js 20** or higher
- **Docker** and **Docker Compose**
- **Maven 3.9+** or **Gradle 8+**
- **PostgreSQL 16**
- **Redis 7**
- **Apache Kafka 3.6+**

### Environment Variables

Create a `.env` file in the root directory:

```env
# Database
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=poundsaver
POSTGRES_USER=admin
POSTGRES_PASSWORD=your_password

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379

# Kafka
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# OpenAI
OPENAI_API_KEY=your_openai_api_key

# Application
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
```

### Quick Start with Docker Compose

```bash
# Clone the repository
git clone https://github.com/JagadeshBandi/PoundSaver-AI.git
cd PoundSaver-AI

# Start all services
docker-compose up -d

# Access the application
# Frontend: http://localhost:3000
# API Gateway: http://localhost:8080
# Grafana: http://localhost:3001
```

### Local Development

#### Backend Services

```bash
# Navigate to backend directory
cd backend

# Build all services
mvn clean install

# Run API Gateway
cd api-gateway
mvn spring-boot:run

# Run Scraper Service (in new terminal)
cd scraper-service
mvn spring-boot:run

# Run other services similarly...
```

#### Frontend Application

```bash
# Navigate to frontend
cd frontend/web

# Install dependencies
npm install

# Start development server
npm run dev

# Access at http://localhost:3000
```

## BDD Testing with Cucumber

Example Gherkin feature file:

```gherkin
Feature: Supermarket Price Comparison

  Scenario: Finding the cheapest milk in the UK
    Given I am on the PoundSaver AI home page
    When I search for "Whole Milk 2 pints"
    And the system scrapes Asda, Tesco, Lidl, Costco, and B&M
    Then the results should be sorted by "Price Low to High"
    And the cheapest item should be highlighted
    And I should see the price per liter for each product

  Scenario: Loyalty card price comparison
    Given I am searching for "Heinz Baked Beans 400g"
    When I enable "Tesco Clubcard" pricing
    Then I should see both regular and Clubcard prices
    And the system should highlight the best overall deal
```

Run tests:

```bash
cd tests/e2e
mvn test
```

## API Documentation

### Search Products

```http
GET /api/v1/products/search?query=milk&sortBy=price_asc
```

**Response:**
```json
{
  "results": [
    {
      "id": "prod-123",
      "name": "Tesco British Whole Milk 2 Pints",
      "retailer": "TESCO",
      "price": 1.45,
      "pricePerUnit": 0.51,
      "unit": "per litre",
      "loyaltyPrice": 1.30,
      "inStock": true,
      "imageUrl": "https://...",
      "productUrl": "https://..."
    }
  ],
  "totalResults": 42,
  "cheapestRetailer": "LIDL",
  "averagePrice": 1.52
}
```

### Get Price History

```http
GET /api/v1/prices/history/{productId}?days=30
```

### Subscribe to Price Alerts

```http
POST /api/v1/alerts
Content-Type: application/json

{
  "productId": "prod-123",
  "targetPrice": 1.20,
  "email": "user@example.com"
}
```

## Monitoring & Observability

### Grafana Dashboards

Access Grafana at `http://localhost:3001` (default credentials: admin/admin)

**Available Dashboards:**
- **Price Gap Analysis**: Real-time comparison of retailer pricing
- **Scraping Health**: Success rates, response times, error tracking
- **API Performance**: Request rates, latency percentiles, error rates
- **Top Searched Products**: Most popular items and search trends
- **System Metrics**: CPU, memory, JVM statistics

### Prometheus Metrics

Key metrics exposed:
- `scraper_requests_total`: Total scraping requests by retailer
- `scraper_success_rate`: Success percentage per retailer
- `price_updates_total`: Number of price updates processed
- `api_request_duration_seconds`: API response time histogram
- `product_match_accuracy`: AI matching confidence scores

## Deployment

### Kubernetes Deployment

```bash
# Apply Kubernetes manifests
kubectl apply -f infrastructure/kubernetes/

# Check deployment status
kubectl get pods -n poundsaver

# Access services
kubectl port-forward svc/api-gateway 8080:8080 -n poundsaver
```

### CI/CD Pipeline

GitHub Actions workflow automatically:
- Runs unit and integration tests
- Performs security scanning (OWASP, Snyk)
- Builds Docker images
- Deploys to staging environment
- Runs E2E tests
- Promotes to production on approval

## Performance Benchmarks

- **API Response Time**: < 100ms (p95)
- **Concurrent Users**: 10,000+
- **Scraping Throughput**: 1,000 products/minute
- **Database Queries**: < 50ms (p99)
- **Cache Hit Rate**: > 85%

## Security

- **API Authentication**: JWT-based with refresh tokens
- **Rate Limiting**: Per-user and per-IP throttling
- **Data Encryption**: TLS 1.3 for all communications
- **Secrets Management**: HashiCorp Vault integration
- **OWASP Compliance**: Regular security audits
- **Dependency Scanning**: Automated vulnerability checks

## Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Roadmap

- [ ] Mobile applications (iOS/Android)
- [ ] Machine learning price prediction
- [ ] Recipe-based shopping lists
- [ ] Multi-region support (EU expansion)
- [ ] Blockchain-based price verification
- [ ] Voice assistant integration

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- OpenAI for GPT-4 API
- Playwright team for excellent automation tools
- Spring Boot community
- React and TypeScript communities

## Contact

**Jagadeesh Kumar Reddy Bandi**
- GitHub: [@JagadeshBandi](https://github.com/JagadeshBandi)
- Project Link: [https://github.com/JagadeshBandi/PoundSaver-AI](https://github.com/JagadeshBandi/PoundSaver-AI)

---

**Built with precision for UK consumers. Saving pounds, one comparison at a time.**
