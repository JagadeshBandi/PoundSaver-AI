import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { HomePage } from './pages/HomePage'
import { ComparisonPage } from './pages/ComparisonPage'
import { HistoryPage } from './pages/HistoryPage'
import { Layout } from './components/Layout'

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/compare" element={<ComparisonPage />} />
          <Route path="/history/:productId" element={<HistoryPage />} />
        </Routes>
      </Layout>
    </Router>
  )
}

export default App
