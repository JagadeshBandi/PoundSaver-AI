# PoundSaver-AI Architecture

## System Overview

PoundSaver-AI is built using a microservices architecture with event-driven communication, reactive programming, and comprehensive observability.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         Frontend Layer                          │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  React 18 + TypeScript + Tailwind CSS + shadcn/ui       │  │
│  │  - TanStack Query for server state                       │  │
│  │  - Zustand for client state                             │  │
│  │  - Recharts for data visualization                      │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      API Gateway Layer                          │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  Spring Cloud Gateway                                    │  │
│  │  - Request routing and load balancing                    │  │
│  │  - Circuit breakers (Resilience4j)                       │  │
│  │  - Rate limiting and throttling                          │  │
│  │  - Redis caching                                         │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        ▼                     ▼                     ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   Scraper    │    │   Product    │    │    Price     │
│   Service    │───▶│   Service    │◀───│   Service    │
│  Port 8081   │    │  Port 8082   │    │  Port 8083   │
└──────────────┘    └──────────────┘    └──────────────┘
        │                   │                    │
        └───────────────────┼────────────────────┘
                            ▼
                    ┌──────────────┐
                    │  Apache      │
                    │  Kafka       │
                    └──────────────┘
                            │
        ┌───────────────────┼───────────────────┐
        ▼                   ▼                   ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│      AI      │    │  Analytics   │    │  PostgreSQL  │
│   Service    │    │   Service    │    │   Database   │
│  Port 8084   │    │  Port 8085   │    │              │
└──────────────┘    └──────────────┘    └──────────────┘
                                                │
                                        ┌───────┴───────┐
                                        ▼               ▼
                                    ┌────────┐    ┌────────┐
                                    │ Redis  │    │ Kafka  │
                                    │ Cache  │    │ Events │
                                    └────────┘    └────────┘
```

## Microservices

### 1. API Gateway (Port 8080)
**Technology**: Spring Cloud Gateway

**Responsibilities**:
- Route requests to appropriate services
- Load balancing across service instances
- Circuit breaking and fault tolerance
- Rate limiting and throttling
- Request/response transformation
- CORS handling

**Key Features**:
- Reactive, non-blocking architecture
- Redis-based rate limiting
- Resilience4j circuit breakers
- Prometheus metrics export

### 2. Scraper Service (Port 8081)
**Technology**: Spring Boot + Playwright

**Responsibilities**:
- Web scraping from UK retailers
- Distributed scraping with job queue
- Self-healing selectors using AI
- Proxy rotation and anti-bot measures
- Product data extraction

**Key Features**:
- Playwright for browser automation
- Strategy pattern for retailer-specific logic
- Kafka event publishing
- Prometheus metrics for scraping health
- Retry logic with exponential backoff

### 3. Product Service (Port 8082)
**Technology**: Spring WebFlux + PostgreSQL

**Responsibilities**:
- Product catalog management
- Product search and filtering
- AI-powered product matching
- Duplicate detection
- Product normalization

**Key Features**:
- Reactive WebFlux endpoints
- Redis caching for search results
- Kafka consumer for scraped products
- Full-text search capabilities
- JPA with PostgreSQL

### 4. Price Service (Port 8083)
**Technology**: Spring WebFlux + PostgreSQL

**Responsibilities**:
- Price aggregation and comparison
- Historical price tracking
- Price-per-unit calculations
- Sorting and filtering algorithms
- Price trend analysis

**Key Features**:
- Reactive price comparison
- Historical price storage
- Statistical calculations
- WebClient for inter-service communication
- Time-series data management

### 5. AI Service (Port 8084)
**Technology**: Spring Boot + LangChain4j + OpenAI

**Responsibilities**:
- Product name normalization
- Brand extraction
- Product matching confidence scoring
- Self-healing selector suggestions
- Natural language processing

**Key Features**:
- LangChain4j orchestration
- OpenAI GPT-4 integration
- Fallback logic for API failures
- Caching for repeated queries
- Confidence scoring algorithms

### 6. Analytics Service (Port 8085)
**Technology**: Spring Boot + PostgreSQL

**Responsibilities**:
- Usage metrics collection
- Price trend analysis
- User behavior tracking
- Dashboard data aggregation
- Reporting

**Key Features**:
- Metrics aggregation
- Time-series analysis
- Custom reporting endpoints
- Data export capabilities

## Data Flow

### Product Search Flow

1. User enters search query in frontend
2. Frontend sends request to API Gateway
3. API Gateway routes to Product Service
4. Product Service queries PostgreSQL
5. Results cached in Redis
6. Response returned to frontend
7. Frontend displays sorted results

### Scraping Flow

1. Scraper Service receives scraping job
2. Playwright launches browser
3. Navigates to retailer website
4. Extracts product data
5. Publishes to Kafka topic
6. Product Service consumes event
7. AI Service normalizes product name
8. Product saved to PostgreSQL
9. Price history updated

### Price Comparison Flow

1. User requests price comparison
2. API Gateway routes to Price Service
3. Price Service calls Product Service
4. Aggregates prices from all retailers
5. Calculates statistics
6. Sorts by price
7. Returns comparison data
8. Frontend displays charts

## Event-Driven Architecture

### Kafka Topics

- **product-scraped**: New products from scraper
- **product-updated**: Product updates
- **price-changed**: Price change events
- **scraping-job**: Scraping job requests
- **analytics-event**: Analytics tracking

### Event Flow

```
Scraper → product-scraped → Product Service
                          ↓
                    AI Service (normalize)
                          ↓
                    PostgreSQL
                          ↓
                    price-changed → Price Service
                          ↓
                    Analytics Service
```

## Data Storage

### PostgreSQL Schema

**products**
- id (UUID)
- name, normalized_name, brand
- retailer, category
- price, price_per_unit, unit
- loyalty_price, loyalty_scheme
- in_stock, image_url, product_url
- scraped_at, last_updated

**price_history**
- id (UUID)
- product_id (FK)
- price, price_per_unit
- timestamp

### Redis Cache

- Product search results (TTL: 1 hour)
- API rate limiting counters
- Session data

## Observability Stack

### Prometheus
- Scrapes metrics from all services
- Stores time-series data
- Alerts on anomalies

### Grafana
- Visualizes Prometheus metrics
- Custom dashboards
- Real-time monitoring

### Loki
- Log aggregation
- Centralized logging
- Query interface

### OpenTelemetry
- Distributed tracing
- Request flow tracking
- Performance analysis

## Security

### Authentication & Authorization
- JWT-based authentication
- Role-based access control
- API key management

### Data Protection
- TLS 1.3 for all communications
- Encrypted database connections
- Secrets management with environment variables

### Rate Limiting
- Per-user rate limits
- Per-IP throttling
- Circuit breakers for service protection

## Scalability

### Horizontal Scaling
- All services are stateless
- Kubernetes pod autoscaling
- Load balancing via API Gateway

### Caching Strategy
- Redis for frequently accessed data
- CDN for static assets
- Browser caching for frontend

### Database Optimization
- Indexed queries
- Connection pooling
- Read replicas for scaling

## Deployment

### Docker
- Multi-stage builds
- Optimized images
- Docker Compose for local development

### Kubernetes
- Deployment manifests
- Service discovery
- ConfigMaps and Secrets
- Persistent volumes

### CI/CD
- GitHub Actions pipeline
- Automated testing
- Docker image building
- Kubernetes deployment

## Performance Targets

- **API Response Time**: < 100ms (p95)
- **Concurrent Users**: 10,000+
- **Scraping Throughput**: 1,000 products/minute
- **Database Queries**: < 50ms (p99)
- **Cache Hit Rate**: > 85%
- **Uptime**: 99.9%
