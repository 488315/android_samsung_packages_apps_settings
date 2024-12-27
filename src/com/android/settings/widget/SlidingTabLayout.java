package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SlidingTabLayout extends FrameLayout implements View.OnClickListener {
    public final View mIndicatorView;
    public final LayoutInflater mLayoutInflater;
    public int mSelectedPosition;
    public float mSelectionOffset;
    public final LinearLayout mTitleView;
    public RtlCompatibleViewPager mViewPager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        public int mScrollState;

        public InternalViewPagerListener() {}

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrollStateChanged(int i) {
            this.mScrollState = i;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrolled(int i, int i2, float f) {
            SlidingTabLayout slidingTabLayout = SlidingTabLayout.this;
            int childCount = slidingTabLayout.mTitleView.getChildCount();
            if (childCount == 0 || i < 0 || i >= childCount) {
                return;
            }
            SlidingTabLayout.m1023$$Nest$monViewPagerPageChanged(slidingTabLayout, i, f);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageSelected(int i) {
            SlidingTabLayout slidingTabLayout = SlidingTabLayout.this;
            int rtlAwareIndex = slidingTabLayout.mViewPager.getRtlAwareIndex(i);
            if (this.mScrollState == 0) {
                SlidingTabLayout.m1023$$Nest$monViewPagerPageChanged(
                        slidingTabLayout, rtlAwareIndex, 0.0f);
            }
            int childCount = slidingTabLayout.mTitleView.getChildCount();
            int i2 = 0;
            while (i2 < childCount) {
                slidingTabLayout.mTitleView.getChildAt(i2).setSelected(rtlAwareIndex == i2);
                i2++;
            }
        }
    }

    /* renamed from: -$$Nest$monViewPagerPageChanged, reason: not valid java name */
    public static void m1023$$Nest$monViewPagerPageChanged(
            SlidingTabLayout slidingTabLayout, int i, float f) {
        slidingTabLayout.mSelectedPosition = i;
        slidingTabLayout.mSelectionOffset = f;
        slidingTabLayout.mIndicatorView.setTranslationX(
                slidingTabLayout.getLayoutDirection() == 1
                        ? -slidingTabLayout.getIndicatorLeft()
                        : slidingTabLayout.getIndicatorLeft());
    }

    public SlidingTabLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater from = LayoutInflater.from(context);
        this.mLayoutInflater = from;
        LinearLayout linearLayout = new LinearLayout(context);
        this.mTitleView = linearLayout;
        linearLayout.setGravity(1);
        View inflate = from.inflate(R.layout.sliding_tab_indicator_view, (ViewGroup) this, false);
        this.mIndicatorView = inflate;
        addView(linearLayout, -1, -2);
        addView(inflate, inflate.getLayoutParams());
    }

    private int getIndicatorLeft() {
        int left = this.mTitleView.getChildAt(this.mSelectedPosition).getLeft();
        if (this.mSelectionOffset <= 0.0f || this.mSelectedPosition >= getChildCount() - 1) {
            return left;
        }
        return (int)
                (((1.0f - this.mSelectionOffset) * left)
                        + (this.mSelectionOffset
                                * this.mTitleView
                                        .getChildAt(this.mSelectedPosition + 1)
                                        .getLeft()));
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int childCount = this.mTitleView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (view == this.mTitleView.getChildAt(i)) {
                this.mViewPager.setCurrentItem(i);
                return;
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mTitleView.getChildCount() > 0) {
            int measuredHeight = getMeasuredHeight();
            int measuredHeight2 = this.mIndicatorView.getMeasuredHeight();
            int measuredWidth = this.mIndicatorView.getMeasuredWidth();
            int measuredWidth2 = getMeasuredWidth();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            LinearLayout linearLayout = this.mTitleView;
            linearLayout.layout(
                    paddingLeft,
                    0,
                    linearLayout.getMeasuredWidth() + paddingRight,
                    this.mTitleView.getMeasuredHeight());
            if (getLayoutDirection() == 1) {
                this.mIndicatorView.layout(
                        measuredWidth2 - measuredWidth,
                        measuredHeight - measuredHeight2,
                        measuredWidth2,
                        measuredHeight);
            } else {
                this.mIndicatorView.layout(
                        0, measuredHeight - measuredHeight2, measuredWidth, measuredHeight);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int childCount = this.mTitleView.getChildCount();
        if (childCount > 0) {
            this.mIndicatorView.measure(
                    View.MeasureSpec.makeMeasureSpec(
                            this.mTitleView.getMeasuredWidth() / childCount, 1073741824),
                    View.MeasureSpec.makeMeasureSpec(
                            this.mIndicatorView.getMeasuredHeight(), 1073741824));
        }
    }

    public void setViewPager(RtlCompatibleViewPager rtlCompatibleViewPager) {
        this.mTitleView.removeAllViews();
        this.mViewPager = rtlCompatibleViewPager;
        if (rtlCompatibleViewPager != null) {
            rtlCompatibleViewPager.addOnPageChangeListener(new InternalViewPagerListener());
            PagerAdapter adapter = this.mViewPager.getAdapter();
            int i = 0;
            while (i < adapter.getCount()) {
                TextView textView =
                        (TextView)
                                this.mLayoutInflater.inflate(
                                        R.layout.sliding_tab_title_view,
                                        (ViewGroup) this.mTitleView,
                                        false);
                textView.setText(adapter.getPageTitle(i));
                textView.setOnClickListener(this);
                this.mTitleView.addView(textView);
                textView.setSelected(i == this.mViewPager.getCurrentItem());
                i++;
            }
        }
    }
}
