import { useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import { compareProducts } from '@/api/prices'
import { ProductCard } from '@/components/ProductCard'
import { PriceGapChart } from '@/components/PriceGapChart'

export function ComparisonPage() {
  const [query, setQuery] = useState('milk')

  const { data, isLoading } = useQuery({
    queryKey: ['comparison', query],
    queryFn: () => compareProducts(query, 'PRICE_ASC'),
  })

  return (
    <div className="max-w-7xl mx-auto">
      <h1 className="text-4xl font-bold text-gray-900 dark:text-white mb-8">
        Price Comparison
      </h1>

      {data && (
        <div className="space-y-8">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
              <div className="text-sm text-gray-600 dark:text-gray-400 mb-1">
                Cheapest Retailer
              </div>
              <div className="text-2xl font-bold text-blue-600">
                {data.cheapestRetailer}
              </div>
            </div>
            <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
              <div className="text-sm text-gray-600 dark:text-gray-400 mb-1">
                Average Price
              </div>
              <div className="text-2xl font-bold text-gray-900 dark:text-white">
                £{data.averagePrice?.toFixed(2)}
              </div>
            </div>
            <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
              <div className="text-sm text-gray-600 dark:text-gray-400 mb-1">
                Price Range
              </div>
              <div className="text-2xl font-bold text-gray-900 dark:text-white">
                £{data.priceRange?.toFixed(2)}
              </div>
            </div>
            <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
              <div className="text-sm text-gray-600 dark:text-gray-400 mb-1">
                Max Savings
              </div>
              <div className="text-2xl font-bold text-green-600">
                £{data.maxSavings?.toFixed(2)}
              </div>
            </div>
          </div>

          <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
            <h2 className="text-xl font-bold text-gray-900 dark:text-white mb-4">
              Price Gap Analysis
            </h2>
            <PriceGapChart products={data.results} />
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {data.results.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </div>
      )}
    </div>
  )
}
