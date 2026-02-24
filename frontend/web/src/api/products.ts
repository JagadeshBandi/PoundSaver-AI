import axios from 'axios'
import { Product } from '@/types'

const api = axios.create({
  baseURL: '/api/v1',
})

export async function searchProducts(query: string): Promise<Product[]> {
  const response = await api.get(`/products/search`, {
    params: { query },
  })
  return response.data
}

export async function getProductById(id: string): Promise<Product> {
  const response = await api.get(`/products/${id}`)
  return response.data
}
