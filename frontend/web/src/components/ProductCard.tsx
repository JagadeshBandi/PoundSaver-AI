import { ExternalLink, TrendingDown, Tag } from 'lucide-react'
import { Product } from '@/types'
import { RetailerLogo } from './RetailerLogo'

interface ProductCardProps {
  product: Product
}

export function ProductCard({ product }: ProductCardProps) {
  const hasLoyaltyPrice = product.loyaltyPrice && product.loyaltyPrice < product.price

  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow border border-gray-200 dark:border-gray-700">
      <div className="relative">
        {product.imageUrl && (
          <img
            src={product.imageUrl}
            alt={product.name}
            className="w-full h-48 object-cover"
          />
        )}
        <div className="absolute top-2 right-2">
          <RetailerLogo retailer={product.retailer} size="sm" />
        </div>
      </div>

      <div className="p-4">
        <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-2 line-clamp-2">
          {product.name}
        </h3>

        <div className="space-y-2 mb-4">
          <div className="flex items-baseline justify-between">
            <span className="text-3xl font-bold text-gray-900 dark:text-white">
              £{product.price.toFixed(2)}
            </span>
            {product.pricePerUnit && (
              <span className="text-sm text-gray-600 dark:text-gray-400">
                £{product.pricePerUnit.toFixed(2)} {product.unit}
              </span>
            )}
          </div>

          {hasLoyaltyPrice && (
            <div className="flex items-center space-x-2 bg-green-50 dark:bg-green-900/20 px-3 py-2 rounded-lg">
              <Tag className="h-4 w-4 text-green-600" />
              <span className="text-sm font-semibold text-green-700 dark:text-green-400">
                {product.loyaltyScheme}: £{product.loyaltyPrice.toFixed(2)}
              </span>
            </div>
          )}
        </div>

        <div className="flex items-center justify-between">
          <div className={`px-3 py-1 rounded-full text-sm font-semibold ${
            product.inStock
              ? 'bg-green-100 text-green-800 dark:bg-green-900/20 dark:text-green-400'
              : 'bg-red-100 text-red-800 dark:bg-red-900/20 dark:text-red-400'
          }`}>
            {product.inStock ? 'In Stock' : 'Out of Stock'}
          </div>

          {product.productUrl && (
            <a
              href={product.productUrl}
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center space-x-1 text-blue-600 hover:text-blue-700 dark:text-blue-400 dark:hover:text-blue-300 transition-colors"
            >
              <span className="text-sm">View</span>
              <ExternalLink className="h-4 w-4" />
            </a>
          )}
        </div>
      </div>
    </div>
  )
}
