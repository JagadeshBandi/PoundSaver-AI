# PoundSaver AI - Product Management Test Script

This script demonstrates how to test the product management functionality.

## Prerequisites

1. Start the application services:
```bash
cd /Users/jagadeeshkumarreddybandi/IdeaProjects/PoundSaver-AI
docker-compose up -d postgres redis kafka
```

2. Start the product service:
```bash
cd backend/product-service
mvn spring-boot:run
```

## API Endpoints

### Base URL
```
http://localhost:8082/v1/products
```

### 1. Search Products
```bash
curl -X GET "http://localhost:8082/v1/products/search?query=milk"
```

### 2. Create a Single Product
```bash
curl -X POST "http://localhost:8082/v1/products" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "test_001",
    "name": "Test Product",
    "normalizedName": "test product",
    "brand": "Test Brand",
    "category": "Test Category",
    "retailer": "Test Retailer",
    "price": 1.99,
    "pricePerUnit": 3.98,
    "unit": "kg",
    "loyaltyPrice": 1.79,
    "loyaltyScheme": "Test Scheme",
    "quantity": 1,
    "size": "500g",
    "inStock": true,
    "imageUrl": "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    "productUrl": "https://example.com/test",
    "ean": "0123456789999",
    "scrapedAt": "2024-02-24T12:00:00",
    "lastUpdated": "2024-02-24T12:00:00",
    "matchConfidence": 0.95
  }'
```

### 3. Create Multiple Products (Bulk Import)
```bash
curl -X POST "http://localhost:8082/v1/products/bulk" \
  -H "Content-Type: application/json" \
  -d '[
    {
      "id": "bulk_001",
      "name": "Organic Avocados",
      "normalizedName": "organic avocados",
      "brand": "Nature'\''s Way",
      "category": "Fruit",
      "retailer": "Tesco",
      "price": 1.5,
      "pricePerUnit": 3.0,
      "unit": "kg",
      "loyaltyPrice": 1.35,
      "loyaltyScheme": "Clubcard",
      "quantity": 2,
      "size": "500g",
      "inStock": true,
      "imageUrl": "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
      "productUrl": "https://tesco.com/avocados",
      "ean": "0123456789023",
      "scrapedAt": "2024-02-24T12:00:00",
      "lastUpdated": "2024-02-24T12:00:00",
      "matchConfidence": 0.91
    },
    {
      "id": "bulk_002",
      "name": "Wholegrain Rice",
      "normalizedName": "wholegrain rice",
      "brand": "Tilda",
      "category": "Pantry",
      "retailer": "Sainsbury'\''s",
      "price": 2.2,
      "pricePerUnit": 4.4,
      "unit": "kg",
      "loyaltyPrice": 1.98,
      "loyaltyScheme": "Nectar",
      "quantity": 1,
      "size": "500g",
      "inStock": true,
      "imageUrl": "https://images.unsplash.com/photo-1551183053-bf91a1d81141?w=400&h=300&fit=crop",
      "productUrl": "https://sainsburys.com/rice",
      "ean": "0123456789024",
      "scrapedAt": "2024-02-24T12:00:00",
      "lastUpdated": "2024-02-24T12:00:00",
      "matchConfidence": 0.89
    }
  ]'
```

### 4. Get Product by ID
```bash
curl -X GET "http://localhost:8082/v1/products/prod_001"
```

### 5. Get Products by Retailer
```bash
curl -X GET "http://localhost:8082/v1/products/retailer/Tesco"
```

### 6. Get In-Stock Products
```bash
curl -X GET "http://localhost:8082/v1/products/in-stock?query=milk"
```

## Frontend Testing

1. Start the frontend:
```bash
cd frontend/web
npm run dev
```

2. Open http://localhost:3000 in your browser

3. Try searching for products like:
   - "milk"
   - "bread"
   - "chicken"
   - "apples"

You should now see product images displayed in the search results with:
- Real product images from Unsplash
- Loading states while images load
- Fallback icons if images fail to load
- Enhanced visual indicators for loyalty savings
- Better responsive design

## Database Setup

The sample data is automatically loaded via the `data.sql` file in:
```
backend/product-service/src/main/resources/data.sql
```

If you need to reload the data:
1. Stop the product service
2. Drop and recreate the database
3. Restart the product service

## Troubleshooting

- **Images not loading**: Check the imageUrl field in the database
- **Compilation errors**: Ensure Lombok dependencies are removed from all modules
- **Connection errors**: Verify PostgreSQL, Redis, and Kafka are running
- **Empty search results**: Check if the data.sql script was executed properly
