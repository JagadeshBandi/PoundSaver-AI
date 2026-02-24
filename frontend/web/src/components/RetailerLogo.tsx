import { cn } from '@/lib/utils'

interface RetailerLogoProps {
  retailer: string
  size?: 'sm' | 'md' | 'lg'
  showName?: boolean
  className?: string
}

const retailerConfig = {
  'TESCO': {
    name: 'Tesco',
    logo: '🛒',
    color: 'text-blue-600',
    bgColor: 'bg-blue-100',
    borderColor: 'border-blue-200'
  },
  'ASDA': {
    name: 'Asda',
    logo: '🏪',
    color: 'text-green-600',
    bgColor: 'bg-green-100',
    borderColor: 'border-green-200'
  },
  'LIDL': {
    name: 'Lidl',
    logo: '🏬',
    color: 'text-red-600',
    bgColor: 'bg-red-100',
    borderColor: 'border-red-200'
  },
  'COSTCO': {
    name: 'Costco',
    logo: '🏭',
    color: 'text-purple-600',
    bgColor: 'bg-purple-100',
    borderColor: 'border-purple-200'
  },
  'BM': {
    name: 'B&M',
    logo: '🏪',
    color: 'text-orange-600',
    bgColor: 'bg-orange-100',
    borderColor: 'border-orange-200'
  },
  'ICELAND': {
    name: 'Iceland',
    logo: '🧊',
    color: 'text-cyan-600',
    bgColor: 'bg-cyan-100',
    borderColor: 'border-cyan-200'
  },
  'WHITE_ROSE': {
    name: 'White Rose',
    logo: '🌹',
    color: 'text-pink-600',
    bgColor: 'bg-pink-100',
    borderColor: 'border-pink-200'
  },
  'HOTDEALS': {
    name: 'HotDeals',
    logo: '🔥',
    color: 'text-orange-600',
    bgColor: 'bg-orange-100',
    borderColor: 'border-orange-200'
  }
}

const sizeConfig = {
  sm: {
    logo: 'text-lg',
    text: 'text-xs',
    padding: 'px-2 py-1'
  },
  md: {
    logo: 'text-xl',
    text: 'text-sm',
    padding: 'px-3 py-1'
  },
  lg: {
    logo: 'text-2xl',
    text: 'text-base',
    padding: 'px-4 py-2'
  }
}

export function RetailerLogo({ retailer, size = 'md', showName = true, className }: RetailerLogoProps) {
  const config = retailerConfig[retailer as keyof typeof retailerConfig] || {
    name: retailer,
    logo: '🏪',
    color: 'text-gray-600',
    bgColor: 'bg-gray-100',
    borderColor: 'border-gray-200'
  }
  
  const sizeStyle = sizeConfig[size]

  return (
    <div className={cn(
      'inline-flex items-center gap-1 rounded-full border font-medium transition-colors hover:shadow-md',
      config.bgColor,
      config.borderColor,
      config.color,
      sizeStyle.padding,
      className
    )}>
      <span className={cn(sizeStyle.logo, 'leading-none')}>{config.logo}</span>
      {showName && <span className={cn(sizeStyle.text, 'font-semibold')}>{config.name}</span>}
    </div>
  )
}

export function RetailerGrid() {
  return (
    <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-7 gap-3">
      {Object.entries(retailerConfig).map(([key, config]) => (
        <div
          key={key}
          className={cn(
            'flex flex-col items-center justify-center p-3 rounded-lg border text-center hover:shadow-lg transition-shadow',
            config.bgColor,
            config.borderColor,
            config.color
          )}
        >
          <div className="text-2xl mb-1">{config.logo}</div>
          <div className="font-bold text-sm">{config.name}</div>
        </div>
      ))}
    </div>
  )
}

export function RetailerList() {
  return (
    <div className="flex flex-wrap justify-center gap-4">
      {Object.entries(retailerConfig).map(([key, config]) => (
        <div
          key={key}
          className={cn(
            'flex items-center gap-2 px-3 py-2 rounded-lg border',
            config.bgColor,
            config.borderColor,
            config.color
          )}
        >
          <span className="text-lg">{config.logo}</span>
          <span className="font-medium">{config.name}</span>
        </div>
      ))}
    </div>
  )
}
