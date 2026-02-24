# Quick Start Guide - PoundSaver AI

## Step 1: Start Docker Services
First, start Docker Desktop and then run:

```bash
cd /Users/jagadeeshkumarreddybandi/IdeaProjects/PoundSaver-AI
docker-compose up -d postgres redis kafka
```

## Step 2: Start Backend Services

### Option A: Start with Mock Data (Recommended for testing)
Since the backend services need database connections, let's create a simple mock server first:

```bash
cd /Users/jagadeeshkumarreddybandi/IdeaProjects/PoundSaver-AI
mkdir -p mock-server
cd mock-server
```

Create a simple mock server file:
```bash
cat > server.js << 'EOF'
const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

const PORT = 8080;

// Mock product data
const mockProducts = [
  {
    id: "prod_001",
    name: "Organic Whole Milk",
    normalizedName: "organic whole milk",
    brand: "Yazoo",
    category: "Dairy",
    retailer: "Tesco",
    price: 1.20,
    pricePerUnit: 0.60,
    unit: "litre",
    loyaltyPrice: 1.10,
    loyaltyScheme: "Clubcard",
    quantity: 1,
    size: "1 litre",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    productUrl: "https://tesco.com/milk",
    ean: "0123456789012",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.95
  },
  {
    id: "prod_002",
    name: "Fresh White Bread",
    normalizedName: "fresh white bread",
    brand: "Hovis",
    category: "Bakery",
    retailer: "Sainsbury's",
    price: 0.80,
    pricePerUnit: 0.40,
    unit: "loaf",
    loyaltyPrice: 0.72,
    loyaltyScheme: "Nectar",
    quantity: 1,
    size: "800g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
    productUrl: "https://sainsburys.com/bread",
    ean: "0123456789013",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.92
  },
  {
    id: "prod_003",
    name: "Bananas (Fairtrade)",
    normalizedName: "bananas fairtrade",
    brand: "Fairtrade",
    category: "Fruit",
    retailer: "Asda",
    price: 0.68,
    pricePerUnit: 1.36,
    unit: "kg",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1566395113783-56a693d6d4e8?w=400&h=300&fit=crop",
    productUrl: "https://asda.com/bananas",
    ean: "0123456789014",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.88
  }
];

// Search endpoint
app.get('/v1/products/search', (req, res) => {
  const query = req.query.query?.toLowerCase() || '';
  const results = mockProducts.filter(product => 
    product.name.toLowerCase().includes(query) ||
    product.brand.toLowerCase().includes(query) ||
    product.category.toLowerCase().includes(query)
  );
  res.json(results);
});

// Get all products
app.get('/v1/products', (req, res) => {
  res.json(mockProducts);
});

// Get product by ID
app.get('/v1/products/:id', (req, res) => {
  const product = mockProducts.find(p => p.id === req.params.id);
  if (product) {
    res.json(product);
  } else {
    res.status(404).json({ error: 'Product not found' });
  }
});

// Create product
app.post('/v1/products', (req, res) => {
  const newProduct = { ...req.body, id: 'prod_' + Date.now() };
  mockProducts.push(newProduct);
  res.json(newProduct);
});

// Bulk create products
app.post('/v1/products/bulk', (req, res) => {
  const newProducts = req.body.map(product => ({ ...product, id: 'prod_' + Date.now() + '_' + Math.random() }));
  mockProducts.push(...newProducts);
  res.json(newProducts);
});

// Health check
app.get('/health', (req, res) => {
  res.json({ status: 'ok', timestamp: new Date().toISOString() });
});

app.listen(PORT, () => {
  console.log(\`Mock server running on http://localhost:\${PORT}\`);
  console.log('Available endpoints:');
  console.log('  GET  /v1/products/search?query=<search-term>');
  console.log('  GET  /v1/products');
  console.log('  GET  /v1/products/:id');
  console.log('  POST /v1/products');
  console.log('  POST /v1/products/bulk');
});
EOF
```

Install dependencies and start mock server:
```bash
npm init -y
npm install express cors
node server.js
```

### Option B: Start Real Backend (Advanced)
If you want to run the real Spring Boot services:

```bash
# Terminal 1: Start product service
cd /Users/jagadeeshkumarreddybandi/IdeaProjects/PoundSaver-AI/backend/product-service
mvn spring-boot:run

# Terminal 2: Start API gateway
cd /Users/jagadeeshkumarreddybandi/IdeaProjects/PoundSaver-AI/backend/api-gateway
mvn spring-boot:run
```

## Step 3: Start Frontend

In a new terminal:
```bash
cd /Users/jagadeeshkumarreddybandi/IdeaProjects/PoundSaver-AI/frontend/web
npm run dev
```

## Step 4: Access the Application

Open your browser and go to: **http://localhost:3000**

## Testing the Search

Try searching for:
- "milk" - should show the Organic Whole Milk
- "bread" - should show Fresh White Bread  
- "bananas" - should show Bananas
- "dairy" - should show milk and other dairy products

## Troubleshooting

### If localhost:3000 doesn't work:
1. Make sure the frontend dev server is running (check for "VITE" in terminal)
2. Try http://localhost:5173 (Vite sometimes uses this port)
3. Check if another app is using port 3000

### If no search results:
1. Check if the mock server is running on port 8080
2. Visit http://localhost:8080/health to test
3. Check browser console for API errors

### If images don't load:
1. Check your internet connection (images are from Unsplash)
2. Right-click and "Inspect Element" to see image loading errors

## Quick Test Commands

```bash
# Test mock server
curl http://localhost:8080/v1/products/search?query=milk

# Test frontend
curl http://localhost:3000
```

This setup should get you running quickly with mock data and working product images!
