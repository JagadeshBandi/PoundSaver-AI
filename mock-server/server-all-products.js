const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

const PORT = 8080;

// Comprehensive product data with ALL retailers for ALL products
const mockProducts = [];

// Helper function to add product for all retailers
function addProductToAllRetailers(baseProduct) {
  const retailers = [
    { name: "Tesco", priceMultiplier: 1.0, loyaltyScheme: "Clubcard", loyaltyMultiplier: 0.9 },
    { name: "Sainsbury's", priceMultiplier: 1.04, loyaltyScheme: "Nectar", loyaltyMultiplier: 0.9 },
    { name: "Asda", priceMultiplier: 0.96, loyaltyScheme: "Asda Rewards", loyaltyMultiplier: 0.92 },
    { name: "Morrisons", priceMultiplier: 0.98, loyaltyScheme: "Morrisons More", loyaltyMultiplier: 0.91 },
    { name: "Aldi", priceMultiplier: 0.92, loyaltyScheme: null, loyaltyMultiplier: null },
    { name: "Lidl", priceMultiplier: 0.90, loyaltyScheme: null, loyaltyMultiplier: null },
    { name: "Costco", priceMultiplier: 0.85, loyaltyScheme: null, loyaltyMultiplier: null, bulkSize: true },
    { name: "B&M", priceMultiplier: 0.88, loyaltyScheme: null, loyaltyMultiplier: null },
    { name: "Co-op", priceMultiplier: 1.02, loyaltyScheme: "Co-op Membership", loyaltyMultiplier: 0.93 },
    { name: "Poundland", priceMultiplier: 0.75, loyaltyScheme: null, loyaltyMultiplier: null }
  ];

  retailers.forEach((retailer, index) => {
    const product = {
      ...baseProduct,
      id: `${baseProduct.id}_${index + 1}`,
      retailer: retailer.name,
      price: baseProduct.price * retailer.priceMultiplier,
      pricePerUnit: baseProduct.pricePerUnit * retailer.priceMultiplier,
      loyaltyPrice: retailer.loyaltyScheme ? (baseProduct.price * retailer.loyaltyMultiplier) : null,
      loyaltyScheme: retailer.loyaltyScheme,
      quantity: retailer.bulkSize ? (baseProduct.quantity * 6) : baseProduct.quantity,
      size: retailer.bulkSize ? `6 x ${baseProduct.size}` : baseProduct.size,
      productUrl: `https://${retailer.name.toLowerCase().replace(/[^a-z0-9]/g, '')}.com/${baseProduct.name.toLowerCase().replace(/\s+/g, '-')}`,
      scrapedAt: "2024-02-24T12:00:00",
      lastUpdated: "2024-02-24T12:00:00"
    };
    mockProducts.push(product);
  });
}

// Dairy Products
addProductToAllRetailers({
  id: "dairy_001",
  name: "Organic Whole Milk",
  normalizedName: "organic whole milk",
  brand: "Yazoo",
  category: "Dairy",
  price: 1.20,
  pricePerUnit: 0.60,
  unit: "litre",
  quantity: 1,
  size: "1 litre",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop",
  ean: "0123456789012",
  matchConfidence: 0.95
});

addProductToAllRetailers({
  id: "dairy_002",
  name: "British Butter",
  normalizedName: "british butter",
  brand: "Country Life",
  category: "Dairy",
  price: 1.80,
  pricePerUnit: 3.60,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1586201375761-83865001e31c?w=400&h=300&fit=crop",
  ean: "0123456789013",
  matchConfidence: 0.94
});

addProductToAllRetailers({
  id: "dairy_003",
  name: "Mature Cheddar Cheese",
  normalizedName: "mature cheddar cheese",
  brand: "Cathedral City",
  category: "Dairy",
  price: 3.50,
  pricePerUnit: 7.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1483695028939-5bb13f8648b0?w=400&h=300&fit=crop",
  ean: "0123456789014",
  matchConfidence: 0.96
});

addProductToAllRetailers({
  id: "dairy_004",
  name: "Greek Style Yogurt",
  normalizedName: "greek style yogurt",
  brand: "Fage",
  category: "Dairy",
  price: 2.20,
  pricePerUnit: 4.40,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1488477181946-6428a0291777?w=400&h=300&fit=crop",
  ean: "0123456789015",
  matchConfidence: 0.93
});

addProductToAllRetailers({
  id: "dairy_005",
  name: "Free Range Eggs",
  normalizedName: "free range eggs",
  brand: "Happy Eggs",
  category: "Dairy",
  price: 2.50,
  pricePerUnit: 0.42,
  unit: "egg",
  quantity: 1,
  size: "12 eggs",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1518569656558-1f25e69393e7?w=400&h=300&fit=crop",
  ean: "0123456789016",
  matchConfidence: 0.95
});

// Bakery Products
addProductToAllRetailers({
  id: "bakery_001",
  name: "Fresh White Bread",
  normalizedName: "fresh white bread",
  brand: "Hovis",
  category: "Bakery",
  price: 0.80,
  pricePerUnit: 0.40,
  unit: "loaf",
  quantity: 1,
  size: "800g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
  ean: "0123456789020",
  matchConfidence: 0.92
});

addProductToAllRetailers({
  id: "bakery_002",
  name: "Wholemeal Bread",
  normalizedName: "wholemeal bread",
  brand: "Warburtons",
  category: "Bakery",
  price: 0.85,
  pricePerUnit: 0.42,
  unit: "loaf",
  quantity: 1,
  size: "800g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
  ean: "0123456789021",
  matchConfidence: 0.92
});

addProductToAllRetailers({
  id: "bakery_003",
  name: "Croissants",
  normalizedName: "croissants",
  brand: "Pain au Chocolat",
  category: "Bakery",
  price: 1.50,
  pricePerUnit: 0.75,
  unit: "croissant",
  quantity: 1,
  size: "4 pack",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1559329007-40df8a9345d8?w=400&h=300&fit=crop",
  ean: "0123456789022",
  matchConfidence: 0.90
});

addProductToAllRetailers({
  id: "bakery_004",
  name: "Bagels",
  normalizedName: "bagels",
  brand: "New York Bagel",
  category: "Bakery",
  price: 1.80,
  pricePerUnit: 0.36,
  unit: "bagel",
  quantity: 1,
  size: "5 pack",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1586444248902-2f64eddc13df?w=400&h=300&fit=crop",
  ean: "0123456789023",
  matchConfidence: 0.91
});

addProductToAllRetailers({
  id: "bakery_005",
  name: "Sliced Ham",
  normalizedName: "sliced ham",
  brand: "Hovis",
  category: "Bakery",
  price: 1.20,
  pricePerUnit: 2.40,
  unit: "loaf",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop",
  ean: "0123456789024",
  matchConfidence: 0.89
});

// Meat Products
addProductToAllRetailers({
  id: "meat_001",
  name: "Chicken Breast Fillets",
  normalizedName: "chicken breast fillets",
  brand: "Tesco",
  category: "Meat",
  price: 3.50,
  pricePerUnit: 7.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1582994139343-5c362d3a73b7?w=400&h=300&fit=crop",
  ean: "0123456789030",
  matchConfidence: 0.90
});

addProductToAllRetailers({
  id: "meat_002",
  name: "British Beef Mince",
  normalizedName: "british beef mince",
  brand: "British Beef",
  category: "Meat",
  price: 4.20,
  pricePerUnit: 8.40,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=400&h=300&fit=crop",
  ean: "0123456789031",
  matchConfidence: 0.92
});

addProductToAllRetailers({
  id: "meat_003",
  name: "Pork Sausages",
  normalizedName: "pork sausages",
  brand: "Cumberland",
  category: "Meat",
  price: 2.80,
  pricePerUnit: 5.60,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1529692174972-865a29fc1b7c?w=400&h=300&fit=crop",
  ean: "0123456789032",
  matchConfidence: 0.91
});

addProductToAllRetailers({
  id: "meat_004",
  name: "British Lamb Chops",
  normalizedName: "british lamb chops",
  brand: "British Lamb",
  category: "Meat",
  price: 6.50,
  pricePerUnit: 13.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1588168333945-31b8536b75ea?w=400&h=300&fit=crop",
  ean: "0123456789033",
  matchConfidence: 0.89
});

addProductToAllRetailers({
  id: "meat_005",
  name: "Bacon Medallions",
  normalizedName: "bacon medallions",
  brand: "Danish",
  category: "Meat",
  price: 3.20,
  pricePerUnit: 12.80,
  unit: "kg",
  quantity: 1,
  size: "250g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1527707947623-a3eefb1c4bb3?w=400&h=300&fit=crop",
  ean: "0123456789034",
  matchConfidence: 0.90
});

// Fruit & Vegetables
addProductToAllRetailers({
  id: "fruit_001",
  name: "British Apples",
  normalizedName: "british apples",
  brand: "British Growers",
  category: "Fruit",
  price: 1.50,
  pricePerUnit: 3.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=400&h=300&fit=crop",
  ean: "0123456789040",
  matchConfidence: 0.88
});

addProductToAllRetailers({
  id: "fruit_002",
  name: "Bananas Fairtrade",
  normalizedName: "bananas fairtrade",
  brand: "Fairtrade",
  category: "Fruit",
  price: 0.68,
  pricePerUnit: 1.36,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1543286986-6961d3bfba2e?w=400&h=300&fit=crop",
  ean: "0123456789041",
  matchConfidence: 0.87
});

addProductToAllRetailers({
  id: "fruit_003",
  name: "Strawberries",
  normalizedName: "strawberries",
  brand: "British Berries",
  category: "Fruit",
  price: 2.50,
  pricePerUnit: 5.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1464965911861-746a04b4bca6?w=400&h=300&fit=crop",
  ean: "0123456789042",
  matchConfidence: 0.91
});

addProductToAllRetailers({
  id: "fruit_004",
  name: "Oranges",
  normalizedName: "oranges",
  brand: "Spanish Citrus",
  category: "Fruit",
  price: 1.20,
  pricePerUnit: 2.40,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1547514701-42782101795e?w=400&h=300&fit=crop",
  ean: "0123456789043",
  matchConfidence: 0.86
});

addProductToAllRetailers({
  id: "fruit_005",
  name: "Grapes",
  normalizedName: "grapes",
  brand: "International",
  category: "Fruit",
  price: 2.00,
  pricePerUnit: 4.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=400&h=300&fit=crop",
  ean: "0123456789044",
  matchConfidence: 0.89
});

addProductToAllRetailers({
  id: "veg_001",
  name: "Potatoes",
  normalizedName: "potatoes",
  brand: "British Farm",
  category: "Vegetables",
  price: 0.80,
  pricePerUnit: 1.60,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1518977676601-ba8447b9cf91?w=400&h=300&fit=crop",
  ean: "0123456789045",
  matchConfidence: 0.85
});

addProductToAllRetailers({
  id: "veg_002",
  name: "Carrots",
  normalizedName: "carrots",
  brand: "British Farm",
  category: "Vegetables",
  price: 0.60,
  pricePerUnit: 1.20,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1445282768818-728615cc910a?w=400&h=300&fit=crop",
  ean: "0123456789046",
  matchConfidence: 0.84
});

addProductToAllRetailers({
  id: "veg_003",
  name: "Broccoli",
  normalizedName: "broccoli",
  brand: "British Farm",
  category: "Vegetables",
  price: 1.20,
  pricePerUnit: 2.40,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1459411621453-7b03977f4bfc?w=400&h=300&fit=crop",
  ean: "0123456789047",
  matchConfidence: 0.87
});

addProductToAllRetailers({
  id: "veg_004",
  name: "Tomatoes",
  normalizedName: "tomatoes",
  brand: "British Farm",
  category: "Vegetables",
  price: 1.50,
  pricePerUnit: 3.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1546470427-e92b2c9c5d61?w=400&h=300&fit=crop",
  ean: "0123456789048",
  matchConfidence: 0.86
});

addProductToAllRetailers({
  id: "veg_005",
  name: "Lettuce",
  normalizedName: "lettuce",
  brand: "British Farm",
  category: "Vegetables",
  price: 0.90,
  pricePerUnit: 0.90,
  unit: "each",
  quantity: 1,
  size: "1 lettuce",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1525373612132-b3e820b87cea?w=400&h=300&fit=crop",
  ean: "0123456789049",
  matchConfidence: 0.83
});

// Pantry Products
addProductToAllRetailers({
  id: "pantry_001",
  name: "Pasta Penne",
  normalizedName: "pasta penne",
  brand: "Napolina",
  category: "Pantry",
  price: 0.95,
  pricePerUnit: 1.90,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1551183053-bf91a1d81141?w=400&h=300&fit=crop",
  ean: "0123456789050",
  matchConfidence: 0.92
});

addProductToAllRetailers({
  id: "pantry_002",
  name: "Rice Basmati",
  normalizedName: "rice basmati",
  brand: "Tilda",
  category: "Pantry",
  price: 2.50,
  pricePerUnit: 5.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1536304663581-5e31d9d6d1dd?w=400&h=300&fit=crop",
  ean: "0123456789051",
  matchConfidence: 0.91
});

addProductToAllRetailers({
  id: "pantry_003",
  name: "Baked Beans",
  normalizedName: "baked beans",
  brand: "Heinz",
  category: "Pantry",
  price: 0.85,
  pricePerUnit: 1.70,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1585230919162-385efabefb4e?w=400&h=300&fit=crop",
  ean: "0123456789052",
  matchConfidence: 0.94
});

addProductToAllRetailers({
  id: "pantry_004",
  name: "Tomato Ketchup",
  normalizedName: "tomato ketchup",
  brand: "Heinz",
  category: "Pantry",
  price: 2.00,
  pricePerUnit: 4.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1525373612132-b3e820b87cea?w=400&h=300&fit=crop",
  ean: "0123456789053",
  matchConfidence: 0.93
});

addProductToAllRetailers({
  id: "pantry_005",
  name: "Olive Oil Extra Virgin",
  normalizedName: "olive oil extra virgin",
  brand: "Filippo Berio",
  category: "Pantry",
  price: 4.50,
  pricePerUnit: 9.00,
  unit: "litre",
  quantity: 1,
  size: "500ml",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1478369402113-1fd53f17e8b4?w=400&h=300&fit=crop",
  ean: "0123456789054",
  matchConfidence: 0.90
});

// Drinks
addProductToAllRetailers({
  id: "drinks_001",
  name: "Orange Juice",
  normalizedName: "orange juice",
  brand: "Tropicana",
  category: "Drinks",
  price: 1.80,
  pricePerUnit: 1.80,
  unit: "litre",
  quantity: 1,
  size: "1 litre",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400&h=300&fit=crop",
  ean: "0123456789060",
  matchConfidence: 0.89
});

addProductToAllRetailers({
  id: "drinks_002",
  name: "Apple Juice",
  normalizedName: "apple juice",
  brand: "Copella",
  category: "Drinks",
  price: 1.60,
  pricePerUnit: 1.60,
  unit: "litre",
  quantity: 1,
  size: "1 litre",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400&h=300&fit=crop",
  ean: "0123456789061",
  matchConfidence: 0.88
});

addProductToAllRetailers({
  id: "drinks_003",
  name: "Still Water",
  normalizedName: "still water",
  brand: "Evian",
  category: "Drinks",
  price: 0.80,
  pricePerUnit: 0.80,
  unit: "litre",
  quantity: 1,
  size: "1 litre",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1548839149-22cfda2c67cd?w=400&h=300&fit=crop",
  ean: "0123456789062",
  matchConfidence: 0.85
});

addProductToAllRetailers({
  id: "drinks_004",
  name: "Cola",
  normalizedName: "cola",
  brand: "Coca-Cola",
  category: "Drinks",
  price: 1.50,
  pricePerUnit: 1.50,
  unit: "litre",
  quantity: 1,
  size: "2 litres",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1597262802233-53e32e77f160?w=400&h=300&fit=crop",
  ean: "0123456789063",
  matchConfidence: 0.92
});

addProductToAllRetailers({
  id: "drinks_005",
  name: "Tea Bags",
  normalizedName: "tea bags",
  brand: "PG Tips",
  category: "Drinks",
  price: 2.80,
  pricePerUnit: 0.04,
  unit: "bag",
  quantity: 1,
  size: "80 bags",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1576092768247-deb238919dc4?w=400&h=300&fit=crop",
  ean: "0123456789064",
  matchConfidence: 0.91
});

// Frozen Foods
addProductToAllRetailers({
  id: "frozen_001",
  name: "Frozen Peas",
  normalizedName: "frozen peas",
  brand: "Birds Eye",
  category: "Frozen",
  price: 1.20,
  pricePerUnit: 2.40,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1574342499650-9aba13b131a5?w=400&h=300&fit=crop",
  ean: "0123456789070",
  matchConfidence: 0.87
});

addProductToAllRetailers({
  id: "frozen_002",
  name: "Frozen Chips",
  normalizedName: "frozen chips",
  brand: "McCain",
  category: "Frozen",
  price: 1.50,
  pricePerUnit: 3.00,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1576177660403-f1b4a852f1a8?w=400&h=300&fit=crop",
  ean: "0123456789071",
  matchConfidence: 0.89
});

addProductToAllRetailers({
  id: "frozen_003",
  name: "Ice Cream",
  normalizedName: "ice cream",
  brand: "Ben & Jerry's",
  category: "Frozen",
  price: 4.50,
  pricePerUnit: 9.00,
  unit: "litre",
  quantity: 1,
  size: "500ml",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1488900128323-21503983a07e?w=400&h=300&fit=crop",
  ean: "0123456789072",
  matchConfidence: 0.90
});

addProductToAllRetailers({
  id: "frozen_004",
  name: "Frozen Pizza",
  normalizedName: "frozen pizza",
  brand: "Goodfella's",
  category: "Frozen",
  price: 3.00,
  pricePerUnit: 6.00,
  unit: "each",
  quantity: 1,
  size: "1 pizza",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400&h=300&fit=crop",
  ean: "0123456789073",
  matchConfidence: 0.88
});

addProductToAllRetailers({
  id: "frozen_005",
  name: "Frozen Fish Fillets",
  normalizedName: "frozen fish fillets",
  brand: "Birds Eye",
  category: "Frozen",
  price: 2.80,
  pricePerUnit: 5.60,
  unit: "kg",
  quantity: 1,
  size: "500g",
  inStock: true,
  imageUrl: "https://images.unsplash.com/photo-1542754151-b92ba5785b1c?w=400&h=300&fit=crop",
  ean: "0123456789074",
  matchConfidence: 0.86
});

// Search endpoint
app.get('/v1/products/search', (req, res) => {
  const query = req.query.query?.toLowerCase() || '';
  
  // For broad search terms, return diverse products from different categories
  if (query === '' || query === 'all' || query === 'products') {
    // Return one of each product type (showing different products, not same product from different retailers)
    const diverseProducts = [];
    const seenProducts = new Set();
    
    mockProducts.forEach(product => {
      const productKey = product.name;
      if (!seenProducts.has(productKey) && seenProducts.size < 35) {
        seenProducts.add(productKey);
        diverseProducts.push(product);
      }
    });
    
    return res.json(diverseProducts);
  }
  
  // For specific search terms, check if it matches a product name exactly
  const exactMatches = mockProducts.filter(product => 
    product.name.toLowerCase().includes(query) ||
    product.normalizedName.includes(query)
  );
  
  // If we found exact matches, return ALL retailers for those products (for price comparison)
  if (exactMatches.length > 0) {
    // Group by product name and return all retailers for each matching product
    const groupedResults = {};
    exactMatches.forEach(product => {
      if (!groupedResults[product.name]) {
        groupedResults[product.name] = [];
      }
      groupedResults[product.name].push(product);
    });
    
    // Sort each group by price and flatten
    const comparisonResults = [];
    Object.values(groupedResults).forEach(group => {
      group.sort((a, b) => a.price - b.price);
      comparisonResults.push(...group);
    });
    
    return res.json(comparisonResults);
  }
  
  // For category or brand searches, return diverse results
  const categoryResults = mockProducts.filter(product => 
    product.category.toLowerCase().includes(query) ||
    product.brand.toLowerCase().includes(query)
  );
  
  if (categoryResults.length > 0) {
    // Group by product name and take one of each for diversity
    const groupedCategory = {};
    categoryResults.forEach(product => {
      if (!groupedCategory[product.name]) {
        groupedCategory[product.name] = [];
      }
      groupedCategory[product.name].push(product);
    });
    
    const diverseCategory = Object.values(groupedCategory).map(group => group[0]).slice(0, 35);
    return res.json(diverseCategory);
  }
  
  // If no results found, return popular products (one of each type)
  const popularProducts = mockProducts.filter(p => 
    ['milk', 'bread', 'chicken', 'apples', 'pasta', 'bananas', 'ketchup', 'juice', 'eggs', 'cheese'].some(term => 
      p.name.toLowerCase().includes(term)
    )
  );
  
  // Group popular products by name and take one of each
  const groupedPopular = {};
  popularProducts.forEach(product => {
    if (!groupedPopular[product.name]) {
      groupedPopular[product.name] = [];
    }
    groupedPopular[product.name].push(product);
  });
  
  const diversePopular = Object.values(groupedPopular).map(group => group[0]).slice(0, 35);
  res.json(diversePopular);
});

// Get all products
app.get('/v1/products', (req, res) => {
  res.json(mockProducts);
});

// Compare prices for a specific product across all retailers
app.get('/v1/products/compare/:productName', (req, res) => {
  const productName = req.params.productName.toLowerCase();
  const comparisonResults = mockProducts.filter(product => 
    product.name.toLowerCase().includes(productName) ||
    product.normalizedName.includes(productName)
  );
  
  // Sort by price (low to high)
  comparisonResults.sort((a, b) => a.price - b.price);
  
  res.json(comparisonResults);
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
  console.log('  GET  /v1/products/compare/<product-name>');
  console.log('  GET  /v1/products/:id');
  console.log('  GET  /v1/products/retailer/:retailer');
  console.log('  GET  /v1/products/in-stock?query=<search-term>');
  console.log('  POST /v1/products');
  console.log('  POST /v1/products/bulk');
  console.log('  GET  /health');
  console.log('\nTry searching for: milk, bread, chicken, apples, pasta, bananas, ketchup, juice, eggs, cheese, yogurt, rice, beans, peas, pizza, tea, water, cola');
  console.log('Now shows ALL 10 supermarkets with 40 different products!');
  console.log(`Total products available: ${mockProducts.length}`);
});
