import { useParams } from 'react-router-dom'
import { useQuery } from '@tanstack/react-query'
import { getPriceHistory } from '@/api/prices'
import { PriceHistoryChart } from '@/components/PriceHistoryChart'

export function HistoryPage() {
  const { productId } = useParams<{ productId: string }>()

  const { data, isLoading } = useQuery({
    queryKey: ['priceHistory', productId],
    queryFn: () => getPriceHistory(productId!, 30),
    enabled: !!productId,
  })

  if (isLoading) {
    return <div>Loading...</div>
  }

  if (!data) {
    return <div>No price history available</div>
  }

  return (
    <div className="max-w-6xl mx-auto">
      <h1 className="text-4xl font-bold text-gray-900 dark:text-white mb-8">
        Price History
      </h1>

      <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg mb-8">
        <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-2">
          {data.productName}
        </h2>
        <p className="text-gray-600 dark:text-gray-400">{data.retailer}</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
        <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
          <div className="text-sm text-gray-600 dark:text-gray-400 mb-1">
            Current Price
          </div>
          <div className="text-2xl font-bold text-gray-900 dark:text-white">
            £{data.currentPrice.toFixed(2)}
          </div>
        </div>
        <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
          <div className="text-sm text-gray-600 dark:text-gray-400 mb-1">
            Lowest Price
          </div>
          <div className="text-2xl font-bold text-green-600">
            £{data.lowestPrice.toFixed(2)}
          </div>
        </div>
        <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
          <div className="text-sm text-gray-600 dark:text-gray-400 mb-1">
            Highest Price
          </div>
          <div className="text-2xl font-bold text-red-600">
            £{data.highestPrice.toFixed(2)}
          </div>
        </div>
        <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
          <div className="text-sm text-gray-600 dark:text-gray-400 mb-1">
            Average Price
          </div>
          <div className="text-2xl font-bold text-gray-900 dark:text-white">
            £{data.averagePrice.toFixed(2)}
          </div>
        </div>
      </div>

      <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg">
        <h2 className="text-xl font-bold text-gray-900 dark:text-white mb-4">
          30-Day Price Trend
        </h2>
        <PriceHistoryChart data={data.priceHistory} />
      </div>
    </div>
  )
}
