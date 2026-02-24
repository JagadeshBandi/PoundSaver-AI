import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts'
import { format } from 'date-fns'

interface PricePoint {
  price: number
  timestamp: string
}

interface PriceHistoryChartProps {
  data: PricePoint[]
}

export function PriceHistoryChart({ data }: PriceHistoryChartProps) {
  const chartData = data.map(point => ({
    date: format(new Date(point.timestamp), 'MMM dd'),
    price: parseFloat(point.price.toFixed(2)),
  }))

  return (
    <ResponsiveContainer width="100%" height={400}>
      <LineChart data={chartData}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="date" />
        <YAxis />
        <Tooltip />
        <Line type="monotone" dataKey="price" stroke="#3b82f6" strokeWidth={2} />
      </LineChart>
    </ResponsiveContainer>
  )
}
