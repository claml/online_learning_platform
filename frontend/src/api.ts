import axios from 'axios'
import { useAuthStore } from './store/auth'

const instance = axios.create({
  baseURL: '/api'
})

instance.interceptors.request.use((config) => {
  const auth = useAuthStore()
  if (auth.token) {
    config.headers = config.headers || {}
    config.headers.Authorization = `Bearer ${auth.token}`
  }
  return config
})

export default instance
