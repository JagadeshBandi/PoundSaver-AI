import { ReactNode } from 'react'
import { Link } from 'react-router-dom'
import { Search, TrendingDown, BarChart3 } from 'lucide-react'
import { RetailerList } from './RetailerLogo'

interface LayoutProps {
  children: ReactNode
}

export function Layout({ children }: LayoutProps) {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 dark:from-gray-900 dark:to-gray-800">
      <nav className="bg-white dark:bg-gray-800 shadow-lg">
        <div className="container mx-auto px-4">
          <div className="flex items-center justify-between h-16">
            <Link to="/" className="flex items-center space-x-2">
              <TrendingDown className="h-8 w-8 text-blue-600" />
              <span className="text-2xl font-bold text-gray-900 dark:text-white">
                PoundSaver AI
              </span>
            </Link>
            
            <div className="flex items-center space-x-6">
              <Link
                to="/"
                className="flex items-center space-x-1 text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400 transition-colors"
              >
                <Search className="h-5 w-5" />
                <span>Search</span>
              </Link>
              <Link
                to="/compare"
                className="flex items-center space-x-1 text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400 transition-colors"
              >
                <BarChart3 className="h-5 w-5" />
                <span>Compare</span>
              </Link>
            </div>
          </div>
        </div>
      </nav>

      <main className="container mx-auto px-4 py-8">
        {children}
      </main>

      <footer className="bg-white dark:bg-gray-800 mt-16 border-t border-gray-200 dark:border-gray-700">
        <div className="container mx-auto px-4 py-8">
          <div className="text-center text-gray-600 dark:text-gray-400 mb-6">
            <p className="text-lg font-semibold mb-2">Built with precision for UK consumers. Saving pounds, one comparison at a time.</p>
            <p className="text-sm">Comparing prices from all major UK supermarkets</p>
          </div>
          
          <div className="mb-6">
            <RetailerList />
          </div>
          
          <div className="text-center text-sm text-gray-500 dark:text-gray-500 border-t border-gray-200 dark:border-gray-700 pt-4">
            <p>© 2024 PoundSaver AI. All rights reserved. | 1,152+ products tracked in real-time</p>
          </div>
        </div>
      </footer>
    </div>
  )
}
