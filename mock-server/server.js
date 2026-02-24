const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

const PORT = 8080;

// Mock product data with real images
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
  },
  {
    id: "prod_004",
    name: "Chicken Breast Fillets",
    normalizedName: "chicken breast fillets",
    brand: "Tesco",
    category: "Meat",
    retailer: "Tesco",
    price: 3.50,
    pricePerUnit: 7.00,
    unit: "kg",
    loyaltyPrice: 3.15,
    loyaltyScheme: "Clubcard",
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1587593815257-3c40958120ea?w=400&h=300&fit=crop",
    productUrl: "https://tesco.com/chicken",
    ean: "0123456789015",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.94
  },
  {
    id: "prod_005",
    name: "British Apples",
    normalizedName: "british apples",
    brand: "British Growers",
    category: "Fruit",
    retailer: "Tesco",
    price: 1.50,
    pricePerUnit: 3.00,
    unit: "kg",
    loyaltyPrice: 1.35,
    loyaltyScheme: "Clubcard",
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=400&h=300&fit=crop",
    productUrl: "https://tesco.com/apples",
    ean: "0123456789017",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.89
  },
  {
    id: "prod_006",
    name: "Pasta Penne",
    normalizedName: "pasta penne",
    brand: "Napolina",
    category: "Pantry",
    retailer: "Asda",
    price: 0.95,
    pricePerUnit: 1.90,
    unit: "kg",
    loyaltyPrice: 0.85,
    loyaltyScheme: "Asda Rewards",
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1551183053-bf91a1d81141?w=400&h=300&fit=crop",
    productUrl: "https://asda.com/pasta",
    ean: "0123456789018",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.93
  },
  {
    id: "prod_007",
    name: "Tomato Ketchup",
    normalizedName: "tomato ketchup",
    brand: "Heinz",
    category: "Condiments",
    retailer: "Tesco",
    price: 2.00,
    pricePerUnit: 4.00,
    unit: "kg",
    loyaltyPrice: 1.80,
    loyaltyScheme: "Clubcard",
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1527235519533-1e300a0b6943?w=400&h=300&fit=crop",
    productUrl: "https://tesco.com/ketchup",
    ean: "0123456789019",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.96
  },
  {
    id: "prod_008",
    name: "Orange Juice",
    normalizedName: "orange juice",
    brand: "Tropicana",
    category: "Drinks",
    retailer: "Sainsbury's",
    price: 1.80,
    pricePerUnit: 1.80,
    unit: "litre",
    loyaltyPrice: 1.62,
    loyaltyScheme: "Nectar",
    quantity: 1,
    size: "1 litre",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400&h=300&fit=crop",
    productUrl: "https://sainsburys.com/juice",
    ean: "0123456789020",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.90
  }
];

// Search endpoint
app.get('/v1/products/search', (req, res) => {
  const query = req.query.query?.toLowerCase() || '';
  const results = mockProducts.filter(product => 
    product.name.toLowerCase().includes(query) ||
    product.brand.toLowerCase().includes(query) ||
    product.category.toLowerCase().includes(query) ||
    product.retailer.toLowerCase().includes(query)
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

// Get products by retailer
app.get('/v1/products/retailer/:retailer', (req, res) => {
  const retailer = req.params.retailer;
  const results = mockProducts.filter(product => 
    product.retailer.toLowerCase() === retailer.toLowerCase()
  );
  res.json(results);
});

// Get in-stock products
app.get('/v1/products/in-stock', (req, res) => {
  const query = req.query.query?.toLowerCase() || '';
  const results = mockProducts.filter(product => 
    product.inStock && (
      product.name.toLowerCase().includes(query) ||
      product.brand.toLowerCase().includes(query) ||
      product.category.toLowerCase().includes(query)
    )
  );
  res.json(results);
});

// Health check
app.get('/health', (req, res) => {
  res.json({ 
    status: 'ok', 
    timestamp: new Date().toISOString(),
    totalProducts: mockProducts.length 
  });
});

app.listen(PORT, () => {
  console.log(`Mock server running on http://localhost:${PORT}`);
  console.log('Available endpoints:');
  console.log('  GET  /v1/products/search?query=<search-term>');
  console.log('  GET  /v1/products');
  console.log('  GET  /v1/products/:id');
  console.log('  GET  /v1/products/retailer/:retailer');
  console.log('  GET  /v1/products/in-stock?query=<search-term>');
  console.log('  POST /v1/products');
  console.log('  POST /v1/products/bulk');
  console.log('  GET  /health');
  console.log('\nTry searching for: milk, bread, chicken, apples, pasta, ketchup, juice');
});
