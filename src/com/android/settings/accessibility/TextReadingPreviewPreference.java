package com.android.settings.accessibility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.viewpager.widget.ViewPager;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.display.PreviewPagerAdapter;
import com.android.settings.widget.DotsPageIndicator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TextReadingPreviewPreference extends Preference {
    public int mBackgroundMinHorizontalPadding;
    public int mCurrentItem;
    public int mLastLayerIndex;
    public int mLayoutMinHorizontalPadding;
    public final AnonymousClass1 mPageChangeListener;
    public PreviewPagerAdapter mPreviewAdapter;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.settings.accessibility.TextReadingPreviewPreference$1] */
    public TextReadingPreviewPreference(Context context) {
        super(context);
        this.mLayoutMinHorizontalPadding = 0;
        this.mBackgroundMinHorizontalPadding = 0;
        this.mPageChangeListener =
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.android.settings.accessibility.TextReadingPreviewPreference.1
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i) {
                        TextReadingPreviewPreference.this.mCurrentItem = i;
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i, int i2, float f) {}
                };
        init$11();
    }

    @VisibleForTesting
    public void adjustPaddings(FrameLayout frameLayout, LinearLayout linearLayout) {
        frameLayout.setPadding(
                Math.max(frameLayout.getPaddingStart(), this.mLayoutMinHorizontalPadding),
                frameLayout.getPaddingTop(),
                Math.max(frameLayout.getPaddingEnd(), this.mLayoutMinHorizontalPadding),
                frameLayout.getPaddingBottom());
        linearLayout.setPadding(
                Math.max(linearLayout.getPaddingStart(), this.mBackgroundMinHorizontalPadding),
                linearLayout.getPaddingTop(),
                Math.max(linearLayout.getPaddingEnd(), this.mBackgroundMinHorizontalPadding),
                linearLayout.getPaddingBottom());
    }

    public final void init$11() {
        this.mLayoutMinHorizontalPadding =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(
                                R.dimen.text_reading_preview_layout_padding_horizontal_min);
        this.mBackgroundMinHorizontalPadding =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(
                                R.dimen.text_reading_preview_background_padding_horizontal_min);
        setLayoutResource(R.layout.accessibility_text_reading_preview);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        FrameLayout frameLayout = (FrameLayout) preferenceViewHolder.itemView;
        adjustPaddings(
                frameLayout, (LinearLayout) frameLayout.findViewById(R.id.preview_background));
        ViewPager viewPager = (ViewPager) preferenceViewHolder.findViewById(R.id.preview_pager);
        viewPager.addOnPageChangeListener(this.mPageChangeListener);
        DotsPageIndicator dotsPageIndicator =
                (DotsPageIndicator) preferenceViewHolder.findViewById(R.id.page_indicator);
        PreviewPagerAdapter previewPagerAdapter = this.mPreviewAdapter;
        if (viewPager.getAdapter() != previewPagerAdapter) {
            viewPager.setAdapter(previewPagerAdapter);
            if (previewPagerAdapter != null) {
                dotsPageIndicator.setViewPager(viewPager);
            } else {
                this.mCurrentItem = 0;
            }
        }
        if (viewPager.getAdapter() == null) {
            return;
        }
        int currentItem = viewPager.getCurrentItem();
        int i = this.mCurrentItem;
        if (currentItem != i) {
            viewPager.setCurrentItem(i);
        }
        dotsPageIndicator.setVisibility(viewPager.getAdapter().getCount() <= 1 ? 8 : 0);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.settings.accessibility.TextReadingPreviewPreference$1] */
    public TextReadingPreviewPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayoutMinHorizontalPadding = 0;
        this.mBackgroundMinHorizontalPadding = 0;
        this.mPageChangeListener =
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.android.settings.accessibility.TextReadingPreviewPreference.1
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i) {
                        TextReadingPreviewPreference.this.mCurrentItem = i;
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i, int i2, float f) {}
                };
        init$11();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.settings.accessibility.TextReadingPreviewPreference$1] */
    public TextReadingPreviewPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLayoutMinHorizontalPadding = 0;
        this.mBackgroundMinHorizontalPadding = 0;
        this.mPageChangeListener =
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.android.settings.accessibility.TextReadingPreviewPreference.1
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i2) {
                        TextReadingPreviewPreference.this.mCurrentItem = i2;
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i2) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i2, int i22, float f) {}
                };
        init$11();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.settings.accessibility.TextReadingPreviewPreference$1] */
    public TextReadingPreviewPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayoutMinHorizontalPadding = 0;
        this.mBackgroundMinHorizontalPadding = 0;
        this.mPageChangeListener =
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.android.settings.accessibility.TextReadingPreviewPreference.1
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i22) {
                        TextReadingPreviewPreference.this.mCurrentItem = i22;
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i22) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i22, int i222, float f) {}
                };
        init$11();
    }
}
