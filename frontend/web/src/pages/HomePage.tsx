import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Search, Loader2, Tag, Bot, TrendingUp } from 'lucide-react'
import { useQuery } from '@tanstack/react-query'
import { searchProducts } from '@/api/products'
import { ProductCard } from '@/components/ProductCard'
import { RetailerGrid } from '@/components/RetailerLogo'

export function HomePage() {
  const [searchQuery, setSearchQuery] = useState('')
  const [activeSearch, setActiveSearch] = useState('')
  const navigate = useNavigate()

  const { data, isLoading, error } = useQuery({
    queryKey: ['products', activeSearch],
    queryFn: () => searchProducts(activeSearch),
    enabled: activeSearch.length > 0,
  })

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault()
    if (searchQuery.trim()) {
      setActiveSearch(searchQuery.trim())
    }
  }

  return (
    <div className="max-w-6xl mx-auto relative">
      {/* Animated Background */}
      <div className="fixed inset-0 -z-10 overflow-hidden pointer-events-none">
        <div className="absolute top-0 -left-4 w-72 h-72 bg-purple-300 dark:bg-purple-900 rounded-full mix-blend-multiply dark:mix-blend-soft-light filter blur-xl opacity-70 animate-blob"></div>
        <div className="absolute top-0 -right-4 w-72 h-72 bg-yellow-300 dark:bg-yellow-900 rounded-full mix-blend-multiply dark:mix-blend-soft-light filter blur-xl opacity-70 animate-blob animation-delay-2000"></div>
        <div className="absolute -bottom-8 left-20 w-72 h-72 bg-pink-300 dark:bg-pink-900 rounded-full mix-blend-multiply dark:mix-blend-soft-light filter blur-xl opacity-70 animate-blob animation-delay-4000"></div>
      </div>
      <div className="text-center mb-12">
        <h1 className="text-5xl font-bold text-gray-900 dark:text-white mb-4">
          Compare UK Grocery Prices in Real-Time
        </h1>
        <p className="text-xl text-gray-600 dark:text-gray-300">
          Find the cheapest prices across Tesco, Sainsbury's, Asda, Morrisons, Aldi, Lidl, Costco, B&M, Co-op, Poundland, Iceland, White Rose, and HotDeals
        </p>
      </div>

      <form onSubmit={handleSearch} className="mb-12">
        <div className="relative max-w-2xl mx-auto">
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search for products (e.g., milk, bread, eggs)..."
            className="w-full px-6 py-4 text-lg rounded-full border-2 border-gray-300 dark:border-gray-600 focus:border-blue-500 dark:focus:border-blue-400 focus:outline-none bg-white dark:bg-gray-800 text-gray-900 dark:text-white shadow-lg"
          />
          <button
            type="submit"
            disabled={isLoading}
            className="absolute right-2 top-1/2 -translate-y-1/2 bg-blue-600 hover:bg-blue-700 text-white px-8 py-3 rounded-full font-semibold transition-colors disabled:opacity-50 disabled:cursor-not-allowed flex items-center space-x-2"
          >
            {isLoading ? (
              <Loader2 className="h-5 w-5 animate-spin" />
            ) : (
              <Search className="h-5 w-5" />
            )}
            <span>Search</span>
          </button>
        </div>
      </form>

      {error && (
        <div className="bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-lg p-4 mb-8">
          <p className="text-red-800 dark:text-red-200">
            Failed to fetch products. Please try again.
          </p>
        </div>
      )}

      {data && data.length > 0 && (
        <div>
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold text-gray-900 dark:text-white">
              {data.length > 10 ? `Price Comparison: ${data[0]?.name || 'Products'}` : `Found ${data.length} products`}
            </h2>
            <div className="text-sm text-gray-600 dark:text-gray-400">
              {data.length > 10 ? 'All retailers sorted by price (low to high)' : 'Sorted by price (low to high)'}
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {data.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </div>
      )}

      {data && data.length === 0 && (
        <div className="text-center py-12">
          <p className="text-xl text-gray-600 dark:text-gray-400">
            No products found for "{activeSearch}"
          </p>
        </div>
      )}

      {!activeSearch && (
        <div className="mt-16">
          <div className="text-center mb-8">
            <h2 className="text-3xl font-bold text-gray-900 dark:text-white mb-4">
              Compare Prices Across All Major UK Supermarkets
            </h2>
            <p className="text-lg text-gray-600 dark:text-gray-300">
              We search through 429+ products from 13 leading retailers to find you the best deals
            </p>
          </div>

          <div className="mb-12">
            <RetailerGrid />
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg border border-gray-200 dark:border-gray-700 hover:shadow-xl transition-shadow duration-300">
              <div className="flex items-center justify-center w-12 h-12 mb-4 rounded-lg bg-blue-100 dark:bg-blue-900">
                <Tag className="w-6 h-6 text-blue-600 dark:text-blue-300" />
              </div>
              <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-3">
                Real-Time Prices
              </h3>
              <p className="text-gray-600 dark:text-gray-400">
                Live price scraping from all major UK supermarkets updated continuously
              </p>
            </div>
            <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg border border-gray-200 dark:border-gray-700 hover:shadow-xl transition-shadow duration-300">
              <div className="flex items-center justify-center w-12 h-12 mb-4 rounded-lg bg-green-100 dark:bg-green-900">
                <Bot className="w-6 h-6 text-green-600 dark:text-green-300" />
              </div>
              <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-3">
                AI-Powered Matching
              </h3>
              <p className="text-gray-600 dark:text-gray-400">
                Smart product matching across retailers for accurate comparisons
              </p>
            </div>
            <div className="bg-white dark:bg-gray-800 rounded-lg p-6 shadow-lg border border-gray-200 dark:border-gray-700 hover:shadow-xl transition-shadow duration-300">
              <div className="flex items-center justify-center w-12 h-12 mb-4 rounded-lg bg-purple-100 dark:bg-purple-900">
                <TrendingUp className="w-6 h-6 text-purple-600 dark:text-purple-300" />
              </div>
              <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-3">
                Price History
              </h3>
              <p className="text-gray-600 dark:text-gray-400">
                Track price trends and get alerts when prices drop
              </p>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
