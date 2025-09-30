import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  // Включаем перезапись маршрутов
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://localhost:8080/api/:path*', // ваш Spring Boot бэкенд
      },
    ];
  },
  // Опционально: отключите автоматическую генерацию API-роутов Next.js
  // (если вы не используете pages/api или app/api)
  experimental: {
    // nothing special needed
  },
};

export default nextConfig;