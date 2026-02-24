import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'
import { Product } from '@/types'

interface PriceGapChartProps {
  products: Product[]
}

export function PriceGapChart({ products }: PriceGapChartProps) {
  const data = products.map(product => ({
    retailer: product.retailer,
    price: parseFloat(product.price.toFixed(2)),
    loyaltyPrice: product.loyaltyPrice ? parseFloat(product.loyaltyPrice.toFixed(2)) : null,
  }))

  return (
    <ResponsiveContainer width="100%" height={300}>
      <BarChart data={data}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="retailer" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Bar dataKey="price" fill="#3b82f6" name="Regular Price" />
        <Bar dataKey="loyaltyPrice" fill="#10b981" name="Loyalty Price" />
      </BarChart>
    </ResponsiveContainer>
  )
}
