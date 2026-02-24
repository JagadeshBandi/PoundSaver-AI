export interface Product {
  id: string
  name: string
  normalizedName?: string
  brand?: string
  category?: string
  retailer: string
  price: number
  pricePerUnit?: number
  unit?: string
  loyaltyPrice?: number
  loyaltyScheme?: string
  quantity?: number
  size?: string
  inStock: boolean
  imageUrl?: string
  productUrl?: string
  ean?: string
  scrapedAt: string
  lastUpdated: string
  matchConfidence?: number
}

export interface PriceComparison {
  results: Product[]
  totalResults: number
  cheapestRetailer: string
  averagePrice: number
  priceRange: number
  maxSavings: number
  searchQuery: string
  searchTimeMs: number
}

export interface PricePoint {
  price: number
  timestamp: string
}

export interface PriceHistory {
  productId: string
  productName: string
  retailer: string
  priceHistory: PricePoint[]
  currentPrice: number
  lowestPrice: number
  highestPrice: number
  averagePrice: number
}
