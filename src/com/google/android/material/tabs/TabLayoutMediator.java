package com.google.android.material.tabs;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TabLayoutMediator {
    public RecyclerView.Adapter adapter;
    public boolean attached;
    public final boolean autoRefresh;
    public ViewPagerOnTabSelectedListener onTabSelectedListener;
    public PagerAdapterObserver pagerAdapterObserver;
    public final boolean smoothScroll;
    public final TabConfigurationStrategy tabConfigurationStrategy;
    public final TabLayout tabLayout;
    public final ViewPager2 viewPager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PagerAdapterObserver extends RecyclerView.AdapterDataObserver {
        public PagerAdapterObserver() {}

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onChanged() {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeInserted(int i, int i2) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeMoved(int i, int i2) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeRemoved(int i, int i2) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2, Object obj) {
            TabLayoutMediator.this.populateTabsFromPagerAdapter();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface TabConfigurationStrategy {
        void onConfigureTab(TabLayout.Tab tab, int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TabLayoutOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        public final WeakReference tabLayoutRef;
        public int scrollState = 0;
        public int previousScrollState = 0;

        public TabLayoutOnPageChangeCallback(TabLayout tabLayout) {
            this.tabLayoutRef = new WeakReference(tabLayout);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public final void onPageScrollStateChanged(int i) {
            this.previousScrollState = this.scrollState;
            this.scrollState = i;
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null) {
                tabLayout.viewPagerScrollState = this.scrollState;
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public final void onPageScrolled(int i, int i2, float f) {
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null) {
                int i3 = this.scrollState;
                tabLayout.setScrollPosition(
                        i,
                        f,
                        i3 != 2 || this.previousScrollState == 1,
                        (i3 == 2 && this.previousScrollState == 0) ? false : true,
                        false);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public final void onPageSelected(int i) {
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout == null
                    || tabLayout.getSelectedTabPosition() == i
                    || i >= tabLayout.tabs.size()) {
                return;
            }
            int i2 = this.scrollState;
            tabLayout.selectTab(
                    tabLayout.getTabAt(i), i2 == 0 || (i2 == 2 && this.previousScrollState == 0));
        }
    }

    public TabLayoutMediator(
            TabLayout tabLayout,
            ViewPager2 viewPager2,
            TabConfigurationStrategy tabConfigurationStrategy) {
        this(tabLayout, viewPager2, true, true, tabConfigurationStrategy);
    }

    public final void attach() {
        if (this.attached) {
            throw new IllegalStateException("TabLayoutMediator is already attached");
        }
        ViewPager2 viewPager2 = this.viewPager;
        RecyclerView.Adapter adapter = viewPager2.mRecyclerView.getAdapter();
        this.adapter = adapter;
        if (adapter == null) {
            throw new IllegalStateException(
                    "TabLayoutMediator attached before ViewPager2 has an adapter");
        }
        this.attached = true;
        TabLayout tabLayout = this.tabLayout;
        viewPager2.registerOnPageChangeCallback(new TabLayoutOnPageChangeCallback(tabLayout));
        tabLayout.addOnTabSelectedListener$1(
                new ViewPagerOnTabSelectedListener(viewPager2, this.smoothScroll));
        if (this.autoRefresh) {
            this.adapter.registerAdapterDataObserver(new PagerAdapterObserver());
        }
        populateTabsFromPagerAdapter();
        tabLayout.setScrollPosition(viewPager2.mCurrentItem, 0.0f, true, true, true);
    }

    public final void populateTabsFromPagerAdapter() {
        TabLayout tabLayout = this.tabLayout;
        tabLayout.removeAllTabs();
        RecyclerView.Adapter adapter = this.adapter;
        if (adapter != null) {
            int itemCount = adapter.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                TabLayout.Tab newTab = tabLayout.newTab();
                this.tabConfigurationStrategy.onConfigureTab(newTab, i);
                tabLayout.addTab(newTab, false);
            }
            if (itemCount > 0) {
                int min = Math.min(this.viewPager.mCurrentItem, tabLayout.tabs.size() - 1);
                if (min != tabLayout.getSelectedTabPosition()) {
                    tabLayout.selectTab(tabLayout.getTabAt(min), true);
                }
            }
        }
    }

    public TabLayoutMediator(
            TabLayout tabLayout,
            ViewPager2 viewPager2,
            boolean z,
            boolean z2,
            TabConfigurationStrategy tabConfigurationStrategy) {
        this.tabLayout = tabLayout;
        this.viewPager = viewPager2;
        this.autoRefresh = z;
        this.smoothScroll = z2;
        this.tabConfigurationStrategy = tabConfigurationStrategy;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
        public final boolean smoothScroll;
        public final ViewPager2 viewPager;

        public ViewPagerOnTabSelectedListener(ViewPager2 viewPager2, boolean z) {
            this.viewPager = viewPager2;
            this.smoothScroll = z;
        }

        @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
        public final void onTabSelected(TabLayout.Tab tab) {
            this.viewPager.setCurrentItem(tab.position, this.smoothScroll);
        }

        @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
        public final void onTabUnselected(TabLayout.Tab tab) {}
    }
}
