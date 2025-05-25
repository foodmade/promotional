const express = require('express')
const cors = require('cors')
const path = require('path')
const app = express()
const PORT = process.env.PORT || 3000

// 中间件
app.use(cors())
app.use(express.json())
app.use(express.urlencoded({ extended: true }))

// 静态文件服务
app.use(express.static(path.join(__dirname, '../dist')))

// 模拟数据
const brands = [
  {
    id: 1,
    name: 'Helium 10',
    slug: 'helium-10',
    description: '全方位的亚马逊卖家工具套件',
    logo: 'https://via.placeholder.com/140x60?text=Helium10',
    rating: 5
  },
  {
    id: 2,
    name: 'Jungle Scout',
    slug: 'jungle-scout',
    description: '亚马逊产品研究和市场分析工具',
    logo: 'https://via.placeholder.com/140x60?text=JungleScout',
    rating: 5
  },
  {
    id: 3,
    name: 'Viral Launch',
    slug: 'viral-launch',
    description: '亚马逊卖家产品发布和营销工具',
    logo: 'https://via.placeholder.com/140x60?text=ViralLaunch',
    rating: 4
  },
  {
    id: 4,
    name: 'SmartScout',
    slug: 'smart-scout',
    description: '亚马逊品牌和收购市场分析工具',
    logo: 'https://via.placeholder.com/140x60?text=SmartScout',
    rating: 4
  }
]

// 亚马逊卖家工具数据
const amazonSellerTools = [
  {
    id: 1,
    name: 'Jungle Scout',
    image: 'https://via.placeholder.com/150x60?text=JungleScout',
    rating: 4.8,
    description: 'Jungle Scout是亚马逊卖家的首选产品研究工具，提供准确的销售估计和市场趋势分析。',
    features: [
      '准确的销售估计',
      '关键词跟踪器',
      '产品数据库',
      '供应商数据库',
      'FBA利润计算器'
    ],
    price: '$39/月起',
    website: 'https://www.junglescout.com',
    reviewLink: '/article/jungle-scout-review'
  },
  {
    id: 2,
    name: 'Helium 10',
    image: 'https://via.placeholder.com/150x60?text=Helium10',
    rating: 4.7,
    description: 'Helium 10提供全面的亚马逊卖家工具套件，包括关键词研究、产品研究和列表优化。',
    features: [
      '黑魔法关键词工具',
      'Cerebro逆向ASIN研究',
      'Scribbles列表优化',
      '库存管理',
      '产品跟踪'
    ],
    price: '$37/月起',
    website: 'https://www.helium10.com',
    reviewLink: '/article/helium10-review'
  },
  {
    id: 3,
    name: 'Sellics',
    image: 'https://via.placeholder.com/150x60?text=Sellics',
    rating: 4.5,
    description: 'Sellics是一个全能的亚马逊平台，集成了PPC、SEO、评论管理等功能于一体。',
    features: [
      'PPC管理器',
      '关键词排名跟踪',
      '评论监控',
      '利润分析',
      '竞争对手监控'
    ],
    price: '$57/月起',
    website: 'https://www.sellics.com',
    reviewLink: '/article/sellics-review'
  },
  {
    id: 4,
    name: 'AMZScout',
    image: 'https://via.placeholder.com/150x60?text=AMZScout',
    rating: 4.3,
    description: 'AMZScout为亚马逊卖家提供产品研究和市场分析工具，帮助发现有利可图的产品机会。',
    features: [
      '产品数据库',
      'FBA计算器',
      '关键词工具',
      '产品追踪器',
      '库存管理'
    ],
    price: '$29.99/月起',
    website: 'https://amzscout.net',
    reviewLink: '/article/amzscout-review'
  },
  {
    id: 5,
    name: 'Viral Launch',
    image: 'https://via.placeholder.com/150x60?text=ViralLaunch',
    rating: 4.4,
    description: 'Viral Launch提供数据驱动的软件工具，帮助亚马逊卖家做出明智的产品决策。',
    features: [
      '产品发现',
      '市场情报',
      '关键词研究',
      '列表优化',
      '流量洞察'
    ],
    price: '$58/月起',
    website: 'https://viral-launch.com',
    reviewLink: '/article/viral-launch-review'
  }
]

const articles = [
  {
    id: 1,
    title: 'Helium 10 vs SellerAmp: 2025年获胜者揭晓',
    slug: 'helium-10-vs-selleramp-winner-2025',
    image: 'https://via.placeholder.com/400x200?text=Article+1',
    tag: 'comparison',
    readTime: 5,
    date: '4月 17, 2025',
    content: '这是文章内容...'
  },
  {
    id: 2,
    title: 'Viral Launch优惠码2025 - 节省40%以上 [已验证]',
    slug: 'viral-launch-coupon-code-2025',
    image: 'https://via.placeholder.com/400x200?text=Article+2',
    tag: 'coupon',
    readTime: 3,
    date: '4月 15, 2025',
    content: '这是文章内容...'
  },
  {
    id: 3,
    title: 'SmartScout品牌猎手: 2025年亚马逊卖家指南',
    slug: 'smartscout-brand-hunter-guide-2025',
    image: 'https://via.placeholder.com/400x200?text=Article+3',
    tag: 'guide',
    readTime: 7,
    date: '4月 10, 2025',
    content: '这是文章内容...'
  },
  {
    id: 4,
    title: 'Helium 10论坛 - 最佳亚马逊卖家社区？',
    slug: 'helium-10-forum-review',
    image: 'https://via.placeholder.com/400x200?text=Article+4',
    tag: 'review',
    readTime: 6,
    date: '4月 5, 2025',
    content: '这是文章内容...'
  }
]

const team = [
  {
    id: 1,
    name: 'Adam Wood',
    title: '联合创始人 / 主编',
    bio: '亚马逊FBA专家，环球数字游民工作者，致力于分享电商工具和策略。',
    avatar: 'https://via.placeholder.com/120x120?text=Adam',
    socialLinks: [
      { name: 'Tw', url: '#' },
      { name: 'Fb', url: '#' },
      { name: 'In', url: '#' }
    ]
  },
  {
    id: 2,
    name: 'Keren Dinkin',
    title: '资深作家',
    bio: '拥有7年数字营销和电子商务经验，现为全职内容创作者，热爱文字的力量。',
    avatar: 'https://via.placeholder.com/120x120?text=Keren',
    socialLinks: [
      { name: 'Tw', url: '#' },
      { name: 'In', url: '#' }
    ]
  },
  {
    id: 3,
    name: 'Elisa Bender',
    title: '联合创始人',
    bio: '电商爱好者，致力于使在线销售变得简单易懂，主要负责RevenueGeeks的销售方面。',
    avatar: 'https://via.placeholder.com/120x120?text=Elisa',
    socialLinks: [
      { name: 'Tw', url: '#' },
      { name: 'Fb', url: '#' },
      { name: 'In', url: '#' }
    ]
  }
]

// 订阅者列表
const subscribers = []

// API路由
app.get('/api/brands', (req, res) => {
  res.json(brands)
})

app.get('/api/brands/:id', (req, res) => {
  const brand = brands.find(b => b.id === parseInt(req.params.id))
  if (!brand) return res.status(404).json({ message: '未找到品牌' })
  res.json(brand)
})

// 亚马逊卖家工具API端点
app.get('/api/amazon-seller-tools', (req, res) => {
  res.json(amazonSellerTools)
})

app.get('/api/amazon-seller-tools/:id', (req, res) => {
  const tool = amazonSellerTools.find(t => t.id === parseInt(req.params.id))
  if (!tool) return res.status(404).json({ message: '未找到工具' })
  res.json(tool)
})

app.get('/api/articles', (req, res) => {
  res.json(articles)
})

app.get('/api/articles/:id', (req, res) => {
  const article = articles.find(a => a.id === parseInt(req.params.id))
  if (!article) return res.status(404).json({ message: '未找到文章' })
  res.json(article)
})

app.get('/api/articles/category/:tag', (req, res) => {
  const filteredArticles = articles.filter(a => a.tag === req.params.tag)
  res.json(filteredArticles)
})

app.get('/api/team', (req, res) => {
  res.json(team)
})

app.get('/api/search', (req, res) => {
  const { q } = req.query
  if (!q) return res.json([])
  
  const query = q.toLowerCase()
  
  // 搜索品牌和文章
  const brandResults = brands.filter(
    b => b.name.toLowerCase().includes(query) || 
    b.description.toLowerCase().includes(query)
  )
  
  const articleResults = articles.filter(
    a => a.title.toLowerCase().includes(query) || 
    a.content.toLowerCase().includes(query) ||
    a.tag.toLowerCase().includes(query)
  )
  
  // 搜索亚马逊卖家工具
  const toolResults = amazonSellerTools.filter(
    t => t.name.toLowerCase().includes(query) || 
    t.description.toLowerCase().includes(query)
  )
  
  res.json({
    brands: brandResults,
    articles: articleResults,
    tools: toolResults
  })
})

app.post('/api/subscribe', (req, res) => {
  const { name, email } = req.body
  
  if (!name || !email) {
    return res.status(400).json({ message: '名称和邮箱都是必填项' })
  }
  
  // 检查是否已订阅
  const existingSubscriber = subscribers.find(s => s.email === email)
  if (existingSubscriber) {
    return res.status(400).json({ message: '该邮箱已订阅' })
  }
  
  // 添加新订阅者
  const newSubscriber = { id: subscribers.length + 1, name, email, date: new Date() }
  subscribers.push(newSubscriber)
  
  res.status(201).json({ message: '订阅成功', subscriber: newSubscriber })
})

// 处理前端路由
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, '../dist/index.html'))
})

// 启动服务器
app.listen(PORT, () => {
  console.log(`服务器运行在 http://localhost:${PORT}`)
}) 