package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.CategoryCacheKeys;
import com.smartcity.urban_management.modules.category.dto.ActiveCategoryResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryDetailResponse;
import com.smartcity.urban_management.modules.category.dto.CategorySummaryResponse;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisCacheService cacheService;

    private static final Duration DETAIL_TTL = Duration.ofMinutes(30);
    private static final Duration PAGE_TTL = Duration.ofMinutes(10);
    private static final Duration ACTIVE_LIST_TTL = Duration.ofMinutes(30);

    /* =========================================================
       CATEGORY DETAIL
       ========================================================= */
    public Optional<CategoryDetailResponse> getCategoryBySlug(String slug) {
        String key = CategoryCacheKeys.categoryBySlug(slug);
        return cacheService.get(key, CategoryDetailResponse.class);
    }

    public void cacheCategoryBySlug(String slug, CategoryDetailResponse data) {
        String key = CategoryCacheKeys.categoryBySlug(slug);
        cacheService.set(key, data, DETAIL_TTL);
    }

    public void evictCategoryBySlug(String slug) {
        cacheService.delete(CategoryCacheKeys.categoryBySlug(slug));
    }

    /* =========================================================
       CATEGORY PAGE (Pagination)
       ========================================================= */

    public Optional<PageResponse<CategorySummaryResponse>> getCategoryPage(
            int page, int size, String sort, String filterKey) {

        String key = CategoryCacheKeys.categoryPage(page, size, sort, filterKey);
        return cacheService.get(key, (Class) PageResponse.class);
    }

    public void cacheCategoryPage(
            int page, int size, String sort, String filterKey,
            PageResponse<CategorySummaryResponse> data) {

        String key = CategoryCacheKeys.categoryPage(page, size, sort, filterKey);
        cacheService.set(key, data, PAGE_TTL);
    }

    public void evictAllPages() {
        cacheService.deleteByPattern(CategoryCacheKeys.categoryPagePattern());
    }

    /* =========================================================
   CATEGORY ACTIVE LIST
   ========================================================= */

    public Optional<ActiveCategoryResponse> getAllActiveCategories() {
        String key = CategoryCacheKeys.categoryActiveList();
        return cacheService.get(key, ActiveCategoryResponse.class);
    }

    public void cacheAllActiveCategories(ActiveCategoryResponse data) {
        String key = CategoryCacheKeys.categoryActiveList();
        cacheService.set(key, data, ACTIVE_LIST_TTL);
    }

    public void evictAllActiveCategories() {
        cacheService.delete(CategoryCacheKeys.categoryActiveList());
    }

}