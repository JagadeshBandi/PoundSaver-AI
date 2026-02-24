import axios from 'axios'
import { PriceComparison, PriceHistory } from '@/types'

const api = axios.create({
  baseURL: '/api/v1',
})

export async function compareProducts(
  query: string,
  sortBy: string
): Promise<PriceComparison> {
  const response = await api.get(`/prices/compare`, {
    params: { query, sortBy },
  })
  return response.data
}

export async function getPriceHistory(
  productId: string,
  days: number
): Promise<PriceHistory> {
  const response = await api.get(`/prices/history/${productId}`, {
    params: { days },
  })
  return response.data
}
