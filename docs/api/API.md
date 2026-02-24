# PoundSaver-AI API Documentation

## Base URL

```
http://localhost:8080/api/v1
```

## Authentication

Currently, the API is open for development. Production deployment will require JWT authentication.

## Endpoints

### Product Service

#### Search Products

```http
GET /products/search?query={query}
```

**Parameters**:
- `query` (required): Search term

**Response**:
```json
[
  {
    "id": "uuid",
    "name": "Tesco British Whole Milk 2 Pints",
    "normalizedName": "whole milk 2 pints",
    "brand": "Tesco",
    "retailer": "TESCO",
    "price": 1.45,
    "pricePerUnit": 0.51,
    "unit": "per litre",
    "loyaltyPrice": 1.30,
    "loyaltyScheme": "Clubcard",
    "inStock": true,
    "imageUrl": "https://...",
    "productUrl": "https://...",
    "scrapedAt": "2024-02-24T00:00:00",
    "lastUpdated": "2024-02-24T00:00:00"
  }
]
```

#### Get Product by ID

```http
GET /products/{id}
```

**Response**: Single product object

#### Get In-Stock Products

```http
GET /products/in-stock?query={query}
```

**Response**: Array of in-stock products

#### Get Products by Retailer

```http
GET /products/retailer/{retailer}
```

**Parameters**:
- `retailer`: TESCO, ASDA, LIDL, COSTCO, BM, ICELAND, WHITE_ROSE

### Price Service

#### Compare Products

```http
GET /prices/compare?query={query}&sortBy={sortOrder}
```

**Parameters**:
- `query` (required): Search term
- `sortBy` (optional): PRICE_ASC, PRICE_DESC, PRICE_PER_UNIT_ASC, etc.

**Response**:
```json
{
  "results": [...],
  "totalResults": 42,
  "cheapestRetailer": "LIDL",
  "averagePrice": 1.52,
  "priceRange": 0.50,
  "maxSavings": 0.50,
  "searchQuery": "milk",
  "searchTimeMs": 150
}
```

#### Get Price History

```http
GET /prices/history/{productId}?days={days}
```

**Parameters**:
- `productId` (required): Product UUID
- `days` (optional, default: 30): Number of days

**Response**:
```json
{
  "productId": "uuid",
  "productName": "Product Name",
  "retailer": "TESCO",
  "priceHistory": [
    {
      "price": 1.45,
      "timestamp": "2024-02-24T00:00:00"
    }
  ],
  "currentPrice": 1.45,
  "lowestPrice": 1.30,
  "highestPrice": 1.60,
  "averagePrice": 1.45
}
```

### Scraper Service

#### Scrape All Retailers

```http
POST /scraper/scrape?query={query}
```

**Parameters**:
- `query` (required): Product to search

**Response**:
```json
[
  {
    "jobId": "uuid",
    "retailer": "TESCO",
    "searchQuery": "milk",
    "status": "COMPLETED",
    "productsScraped": 25,
    "startedAt": "2024-02-24T00:00:00",
    "completedAt": "2024-02-24T00:01:00",
    "durationMs": 60000
  }
]
```

#### Scrape Single Retailer

```http
POST /scraper/scrape/{retailer}?query={query}
```

**Parameters**:
- `retailer`: TESCO, ASDA, LIDL, COSTCO, BM, ICELAND, WHITE_ROSE
- `query` (required): Product to search

### AI Service

#### Normalize Product Name

```http
POST /ai/normalize
Content-Type: application/json

{
  "productName": "Tesco British Whole Milk 2 Pints"
}
```

**Response**:
```json
{
  "normalized": "whole milk 2 pints"
}
```

#### Extract Brand

```http
POST /ai/extract-brand
Content-Type: application/json

{
  "productName": "Tesco British Whole Milk 2 Pints"
}
```

**Response**:
```json
{
  "brand": "Tesco"
}
```

#### Calculate Match Confidence

```http
POST /ai/match-confidence
Content-Type: application/json

{
  "product1": "Tesco Whole Milk 2L",
  "product2": "Asda Fresh Milk 2L"
}
```

**Response**:
```json
{
  "confidence": 0.85
}
```

## Error Responses

### 400 Bad Request

```json
{
  "error": "Invalid request",
  "message": "Query parameter is required"
}
```

### 404 Not Found

```json
{
  "error": "Product not found",
  "message": "No product found with ID: uuid"
}
```

### 500 Internal Server Error

```json
{
  "error": "Internal server error",
  "message": "An unexpected error occurred"
}
```

### 503 Service Unavailable

```json
{
  "error": "Service unavailable",
  "message": "The service is currently unavailable. Please try again later."
}
```

## Rate Limiting

- **Rate Limit**: 100 requests per minute per IP
- **Headers**:
  - `X-RateLimit-Limit`: Maximum requests
  - `X-RateLimit-Remaining`: Remaining requests
  - `X-RateLimit-Reset`: Reset timestamp

## Pagination

For endpoints returning large datasets:

```http
GET /products/search?query=milk&page=0&size=20
```

**Parameters**:
- `page` (default: 0): Page number
- `size` (default: 20): Items per page

## Sorting

Available sort orders:
- `PRICE_ASC`: Price low to high
- `PRICE_DESC`: Price high to low
- `PRICE_PER_UNIT_ASC`: Price per unit low to high
- `PRICE_PER_UNIT_DESC`: Price per unit high to low
- `NAME_ASC`: Name A-Z
- `NAME_DESC`: Name Z-A
- `RETAILER_ASC`: Retailer A-Z
- `RETAILER_DESC`: Retailer Z-A

## WebSocket Endpoints

### Real-Time Price Updates

```javascript
const ws = new WebSocket('ws://localhost:8080/ws/prices');

ws.onmessage = (event) => {
  const priceUpdate = JSON.parse(event.data);
  console.log('Price updated:', priceUpdate);
};
```

## Health Checks

```http
GET /actuator/health
```

**Response**:
```json
{
  "status": "UP",
  "components": {
    "db": { "status": "UP" },
    "redis": { "status": "UP" },
    "kafka": { "status": "UP" }
  }
}
```

## Metrics

```http
GET /actuator/prometheus
```

Returns Prometheus-formatted metrics for monitoring.
