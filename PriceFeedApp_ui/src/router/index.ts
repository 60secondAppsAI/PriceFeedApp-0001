import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import PriceFeeds from '../components/PriceFeeds.vue';
import PriceFeedDetail from '../components/PriceFeedDetail.vue';
import PriceSubscriptions from '../components/PriceSubscriptions.vue';
import PriceSubscriptionDetail from '../components/PriceSubscriptionDetail.vue';
import PriceTicks from '../components/PriceTicks.vue';
import PriceTickDetail from '../components/PriceTickDetail.vue';
import FeedHealths from '../components/FeedHealths.vue';
import FeedHealthDetail from '../components/FeedHealthDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/priceFeeds',
    name: 'PriceFeeds',
    component: PriceFeeds
  },
  {
    path: '/priceFeed/:id',
    name: 'PriceFeedDetail',
    component: PriceFeedDetail,
    props: true
  },
  {
    path: '/priceSubscriptions',
    name: 'PriceSubscriptions',
    component: PriceSubscriptions
  },
  {
    path: '/priceSubscription/:id',
    name: 'PriceSubscriptionDetail',
    component: PriceSubscriptionDetail,
    props: true
  },
  {
    path: '/priceTicks',
    name: 'PriceTicks',
    component: PriceTicks
  },
  {
    path: '/priceTick/:id',
    name: 'PriceTickDetail',
    component: PriceTickDetail,
    props: true
  },
  {
    path: '/feedHealths',
    name: 'FeedHealths',
    component: FeedHealths
  },
  {
    path: '/feedHealth/:id',
    name: 'FeedHealthDetail',
    component: FeedHealthDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
