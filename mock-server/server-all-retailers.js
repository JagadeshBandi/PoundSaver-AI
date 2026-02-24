const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

const PORT = 8080;

// Enhanced product data with ALL retailers for comprehensive comparison
const mockProducts = [
  // Milk products from ALL retailers
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
    id: "prod_001_2",
    name: "Organic Whole Milk",
    normalizedName: "organic whole milk",
    brand: "Yazoo",
    category: "Dairy",
    retailer: "Sainsbury's",
    price: 1.25,
    pricePerUnit: 0.62,
    unit: "litre",
    loyaltyPrice: 1.12,
    loyaltyScheme: "Nectar",
    quantity: 1,
    size: "1 litre",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    productUrl: "https://sainsburys.com/milk",
    ean: "0123456789012",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.95
  },
  {
    id: "prod_001_3",
    name: "Organic Whole Milk",
    normalizedName: "organic whole milk",
    brand: "Yazoo",
    category: "Dairy",
    retailer: "Asda",
    price: 1.15,
    pricePerUnit: 0.57,
    unit: "litre",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "1 litre",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    productUrl: "https://asda.com/milk",
    ean: "0123456789012",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.95
  },
  {
    id: "prod_001_4",
    name: "Organic Whole Milk",
    normalizedName: "organic whole milk",
    brand: "Yazoo",
    category: "Dairy",
    retailer: "Morrisons",
    price: 1.18,
    pricePerUnit: 0.59,
    unit: "litre",
    loyaltyPrice: 1.08,
    loyaltyScheme: "Morrisons More",
    quantity: 1,
    size: "1 litre",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    productUrl: "https://morrisons.com/milk",
    ean: "0123456789012",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.95
  },
  {
    id: "prod_001_5",
    name: "Organic Whole Milk",
    normalizedName: "organic whole milk",
    brand: "Yazoo",
    category: "Dairy",
    retailer: "Aldi",
    price: 1.10,
    pricePerUnit: 0.55,
    unit: "litre",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "1 litre",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    productUrl: "https://aldi.com/milk",
    ean: "0123456789012",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.95
  },
  {
    id: "prod_001_6",
    name: "Organic Whole Milk",
    normalizedName: "organic whole milk",
    brand: "Yazoo",
    category: "Dairy",
    retailer: "Lidl",
    price: 1.08,
    pricePerUnit: 0.54,
    unit: "litre",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "1 litre",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    productUrl: "https://lidl.com/milk",
    ean: "0123456789012",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.95
  },
  {
    id: "prod_001_7",
    name: "Organic Whole Milk",
    normalizedName: "organic whole milk",
    brand: "Yazoo",
    category: "Dairy",
    retailer: "Costco",
    price: 0.95,
    pricePerUnit: 0.48,
    unit: "litre",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 6,
    size: "6 litres",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    productUrl: "https://costco.com/milk",
    ean: "0123456789012",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.95
  },
  {
    id: "prod_001_8",
    name: "Organic Whole Milk",
    normalizedName: "organic whole milk",
    brand: "Yazoo",
    category: "Dairy",
    retailer: "B&M",
    price: 1.05,
    pricePerUnit: 0.52,
    unit: "litre",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "1 litre",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    productUrl: "https://bmstores.com/milk",
    ean: "0123456789012",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.95
  },
  {
    id: "prod_001_9",
    name: "Organic Whole Milk",
    normalizedName: "organic whole milk",
    brand: "Yazoo",
    category: "Dairy",
    retailer: "Co-op",
    price: 1.22,
    pricePerUnit: 0.61,
    unit: "litre",
    loyaltyPrice: 1.10,
    loyaltyScheme: "Co-op Membership",
    quantity: 1,
    size: "1 litre",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
    productUrl: "https://coop.co.uk/milk",
    ean: "0123456789012",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.95
  },

  // Bread products from ALL retailers
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
    id: "prod_002_2",
    name: "Fresh White Bread",
    normalizedName: "fresh white bread",
    brand: "Hovis",
    category: "Bakery",
    retailer: "Tesco",
    price: 0.85,
    pricePerUnit: 0.42,
    unit: "loaf",
    loyaltyPrice: 0.76,
    loyaltyScheme: "Clubcard",
    quantity: 1,
    size: "800g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
    productUrl: "https://tesco.com/bread",
    ean: "0123456789013",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.92
  },
  {
    id: "prod_002_3",
    name: "Fresh White Bread",
    normalizedName: "fresh white bread",
    brand: "Hovis",
    category: "Bakery",
    retailer: "Aldi",
    price: 0.75,
    pricePerUnit: 0.37,
    unit: "loaf",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "800g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
    productUrl: "https://aldi.com/bread",
    ean: "0123456789013",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.92
  },
  {
    id: "prod_002_4",
    name: "Fresh White Bread",
    normalizedName: "fresh white bread",
    brand: "Hovis",
    category: "Bakery",
    retailer: "Asda",
    price: 0.78,
    pricePerUnit: 0.39,
    unit: "loaf",
    loyaltyPrice: 0.70,
    loyaltyScheme: "Asda Rewards",
    quantity: 1,
    size: "800g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
    productUrl: "https://asda.com/bread",
    ean: "0123456789013",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.92
  },
  {
    id: "prod_002_5",
    name: "Fresh White Bread",
    normalizedName: "fresh white bread",
    brand: "Hovis",
    category: "Bakery",
    retailer: "Morrisons",
    price: 0.82,
    pricePerUnit: 0.41,
    unit: "loaf",
    loyaltyPrice: 0.74,
    loyaltyScheme: "Morrisons More",
    quantity: 1,
    size: "800g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
    productUrl: "https://morrisons.com/bread",
    ean: "0123456789013",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.92
  },
  {
    id: "prod_002_6",
    name: "Fresh White Bread",
    normalizedName: "fresh white bread",
    brand: "Hovis",
    category: "Bakery",
    retailer: "Lidl",
    price: 0.72,
    pricePerUnit: 0.36,
    unit: "loaf",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "800g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
    productUrl: "https://lidl.com/bread",
    ean: "0123456789013",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.92
  },
  {
    id: "prod_002_7",
    name: "Fresh White Bread",
    normalizedName: "fresh white bread",
    brand: "Hovis",
    category: "Bakery",
    retailer: "Costco",
    price: 0.65,
    pricePerUnit: 0.32,
    unit: "loaf",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 6,
    size: "6 x 800g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
    productUrl: "https://costco.com/bread",
    ean: "0123456789013",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.92
  },
  {
    id: "prod_002_8",
    name: "Fresh White Bread",
    normalizedName: "fresh white bread",
    brand: "Hovis",
    category: "Bakery",
    retailer: "B&M",
    price: 0.70,
    pricePerUnit: 0.35,
    unit: "loaf",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "800g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
    productUrl: "https://bmstores.com/bread",
    ean: "0123456789013",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.92
  },
  {
    id: "prod_002_9",
    name: "Fresh White Bread",
    normalizedName: "fresh white bread",
    brand: "Hovis",
    category: "Bakery",
    retailer: "Co-op",
    price: 0.83,
    pricePerUnit: 0.41,
    unit: "loaf",
    loyaltyPrice: 0.75,
    loyaltyScheme: "Co-op Membership",
    quantity: 1,
    size: "800g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
    productUrl: "https://coop.co.uk/bread",
    ean: "0123456789013",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.92
  },

  // Chicken products from ALL retailers
  {
    id: "prod_003",
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
    imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
    productUrl: "https://tesco.com/chicken",
    ean: "0123456789014",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.90
  },
  {
    id: "prod_003_2",
    name: "Chicken Breast Fillets",
    normalizedName: "chicken breast fillets",
    brand: "Tesco",
    category: "Meat",
    retailer: "Sainsbury's",
    price: 3.75,
    pricePerUnit: 7.50,
    unit: "kg",
    loyaltyPrice: 3.38,
    loyaltyScheme: "Nectar",
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
    productUrl: "https://sainsburys.com/chicken",
    ean: "0123456789014",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.90
  },
  {
    id: "prod_003_3",
    name: "Chicken Breast Fillets",
    normalizedName: "chicken breast fillets",
    brand: "Tesco",
    category: "Meat",
    retailer: "Morrisons",
    price: 3.25,
    pricePerUnit: 6.50,
    unit: "kg",
    loyaltyPrice: 2.92,
    loyaltyScheme: "Morrisons More",
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
    productUrl: "https://morrisons.com/chicken",
    ean: "0123456789014",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.90
  },
  {
    id: "prod_003_4",
    name: "Chicken Breast Fillets",
    normalizedName: "chicken breast fillets",
    brand: "Tesco",
    category: "Meat",
    retailer: "Asda",
    price: 3.40,
    pricePerUnit: 6.80,
    unit: "kg",
    loyaltyPrice: 3.06,
    loyaltyScheme: "Asda Rewards",
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
    productUrl: "https://asda.com/chicken",
    ean: "0123456789014",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.90
  },
  {
    id: "prod_003_5",
    name: "Chicken Breast Fillets",
    normalizedName: "chicken breast fillets",
    brand: "Tesco",
    category: "Meat",
    retailer: "Aldi",
    price: 3.20,
    pricePerUnit: 6.40,
    unit: "kg",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
    productUrl: "https://aldi.com/chicken",
    ean: "0123456789014",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.90
  },
  {
    id: "prod_003_6",
    name: "Chicken Breast Fillets",
    normalizedName: "chicken breast fillets",
    brand: "Tesco",
    category: "Meat",
    retailer: "Lidl",
    price: 3.15,
    pricePerUnit: 6.30,
    unit: "kg",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
    productUrl: "https://lidl.com/chicken",
    ean: "0123456789014",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.90
  },
  {
    id: "prod_003_7",
    name: "Chicken Breast Fillets",
    normalizedName: "chicken breast fillets",
    brand: "Tesco",
    category: "Meat",
    retailer: "Costco",
    price: 2.80,
    pricePerUnit: 5.60,
    unit: "kg",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 5,
    size: "2.5kg",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
    productUrl: "https://costco.com/chicken",
    ean: "0123456789014",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.90
  },
  {
    id: "prod_003_8",
    name: "Chicken Breast Fillets",
    normalizedName: "chicken breast fillets",
    brand: "Tesco",
    category: "Meat",
    retailer: "B&M",
    price: 3.30,
    pricePerUnit: 6.60,
    unit: "kg",
    loyaltyPrice: null,
    loyaltyScheme: null,
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
    productUrl: "https://bmstores.com/chicken",
    ean: "0123456789014",
    scrapedAt: "2024-02-24T12:00:00",
    lastUpdated: "2024-02-24T12:00:00",
    matchConfidence: 0.90
  },
  {
    id: "prod_003_9",
    name: "Chicken Breast Fillets",
    normalizedName: "chicken breast fillets",
    brand: "Tesco",
    category: "Meat",
    retailer: "Co-op",
    price: 3.55,
    pricePerUnit: 7.10,
    unit: "kg",
    loyaltyPrice: 3.20,
    loyaltyScheme: "Co-op Membership",
    quantity: 1,
    size: "500g",
    inStock: true,
    imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
    productUrl: "https://coop.co.uk/chicken",
    ean: "0123456789014",
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
    product.retailer.toLowerCase().includes(query) ||
    product.normalizedName.includes(query)
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
  const products = mockProducts.filter(product => 
    product.retailer.toLowerCase() === retailer.toLowerCase()
  );
  res.json(products);
});

// Get in-stock products with optional search
app.get('/v1/products/in-stock', (req, res) => {
  const query = req.query.query?.toLowerCase() || '';
  const results = mockProducts.filter(product => 
    product.inStock && (
      product.name.toLowerCase().includes(query) ||
      product.brand.toLowerCase().includes(query) ||
      product.category.toLowerCase().includes(query) ||
      product.retailer.toLowerCase().includes(query) ||
      product.normalizedName.includes(query)
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
  console.log('\nTry searching for: milk, bread, chicken, apples, pasta, bananas, ketchup, juice');
  console.log('Now shows ALL 9 supermarkets for comprehensive price comparison!');
  console.log(`Total products available: ${mockProducts.length}`);
});
