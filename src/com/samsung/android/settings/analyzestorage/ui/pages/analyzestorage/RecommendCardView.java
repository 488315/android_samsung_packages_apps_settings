package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.R;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.samsung.android.settings.analyzestorage.data.model.RecommendCardState;
import com.samsung.android.settings.analyzestorage.presenter.controllers.RecommendCardController;
import com.samsung.android.settings.analyzestorage.presenter.managers.SamsungAnalyticsConvertManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsIndicatorAdapter;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.RecommendCardViewPagerAdapter;
import com.samsung.android.settings.logging.LoggingHelper;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Job;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecommendCardView {
    public final FragmentActivity activity;
    public RecommendCardViewPagerAdapter adapter;
    public RecommendCardController controller;
    public Job delayLoadingJob;
    public final FrameLayout dummyText1line;
    public final FrameLayout dummyText2line;
    public boolean inLoadingProgress;
    public final GridView indicator;
    public int layoutState;
    public final ShimmerFrameLayout loadingView;
    public final LinearLayout loadingViewPager;
    public final RecommendCardView$pageChangeCallback$1 pageChangeCallback;
    public final Lazy recommendCardIndicatorAdapter$delegate;
    public final View root;
    public final ViewPager2 viewPager;
    public final LinearLayout viewPagerLayout;

    /* JADX WARN: Type inference failed for: r2v3, types: [com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$pageChangeCallback$1] */
    public RecommendCardView(View view, FragmentActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.root = view;
        this.activity = activity;
        this.recommendCardIndicatorAdapter$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$recommendCardIndicatorAdapter$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Context baseContext =
                                        RecommendCardView.this.activity.getBaseContext();
                                Intrinsics.checkNotNullExpressionValue(
                                        baseContext, "getBaseContext(...)");
                                return new AsIndicatorAdapter(baseContext);
                            }
                        });
        View requireViewById = view.requireViewById(R.id.view_pager_layout);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        this.viewPagerLayout = (LinearLayout) requireViewById;
        View requireViewById2 = view.requireViewById(R.id.recommend_card_indicator);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        this.indicator = (GridView) requireViewById2;
        View requireViewById3 = view.requireViewById(R.id.as_home_recommend_card_viewpager);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
        this.viewPager = (ViewPager2) requireViewById3;
        View requireViewById4 = view.requireViewById(R.id.loading_view);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, "requireViewById(...)");
        this.loadingView = (ShimmerFrameLayout) requireViewById4;
        View requireViewById5 = view.requireViewById(R.id.loading_view_pager);
        Intrinsics.checkNotNullExpressionValue(requireViewById5, "requireViewById(...)");
        this.loadingViewPager = (LinearLayout) requireViewById5;
        View requireViewById6 = view.requireViewById(R.id.text1);
        Intrinsics.checkNotNullExpressionValue(requireViewById6, "requireViewById(...)");
        this.dummyText1line = (FrameLayout) requireViewById6;
        View requireViewById7 = view.requireViewById(R.id.text2);
        Intrinsics.checkNotNullExpressionValue(requireViewById7, "requireViewById(...)");
        this.dummyText2line = (FrameLayout) requireViewById7;
        this.pageChangeCallback =
                new ViewPager2
                        .OnPageChangeCallback() { // from class:
                                                  // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$pageChangeCallback$1
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public final void onPageSelected(int i) {
                        Integer num;
                        HashMap hashMap;
                        HashMap hashMap2;
                        MutableLiveData mutableLiveData;
                        RecommendCardState recommendCardState;
                        ArrayList arrayList;
                        RecommendCardView recommendCardView = RecommendCardView.this;
                        RecommendCardController recommendCardController =
                                recommendCardView.controller;
                        if (i
                                < ((recommendCardController == null
                                                || (mutableLiveData =
                                                                recommendCardController
                                                                        .recommendCardState)
                                                        == null
                                                || (recommendCardState =
                                                                (RecommendCardState)
                                                                        mutableLiveData.getValue())
                                                        == null
                                                || (arrayList =
                                                                recommendCardState
                                                                        .supportedCardList)
                                                        == null)
                                        ? 0
                                        : arrayList.size())) {
                            boolean z = true;
                            boolean z2 =
                                    recommendCardView.getRecommendCardIndicatorAdapter()
                                                    .currentIndex
                                            != i;
                            recommendCardView.getRecommendCardIndicatorAdapter().currentIndex = i;
                            recommendCardView
                                    .getRecommendCardIndicatorAdapter()
                                    .notifyDataSetChanged();
                            Integer currentSupportedType =
                                    recommendCardView
                                            .getRecommendCardIndicatorAdapter()
                                            .getCurrentSupportedType();
                            if (currentSupportedType != null && z2) {
                                int intValue = currentSupportedType.intValue();
                                RecommendCardController recommendCardController2 =
                                        recommendCardView.controller;
                                if (recommendCardController2 == null
                                        || (hashMap2 = recommendCardController2.fileTypeCard)
                                                == null
                                        || (num = (Integer) hashMap2.get(currentSupportedType))
                                                == null) {
                                    num = -1;
                                }
                                int intValue2 = num.intValue();
                                RecommendCardController recommendCardController3 =
                                        recommendCardView.controller;
                                String str =
                                        (recommendCardController3 == null
                                                        || (hashMap =
                                                                        recommendCardController3
                                                                                .pathCard)
                                                                == null)
                                                ? null
                                                : (String) hashMap.get(currentSupportedType);
                                if (str != null && str.length() != 0) {
                                    z = false;
                                }
                                Integer convertCardToSAEventId =
                                        SamsungAnalyticsConvertManager.convertCardToSAEventId(
                                                intValue, intValue2, z, 0);
                                if (convertCardToSAEventId != null) {
                                    LoggingHelper.insertEventLogging(
                                            PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA(),
                                            convertCardToSAEventId.intValue());
                                }
                            }
                        }
                        RecommendCardViewPagerAdapter recommendCardViewPagerAdapter =
                                recommendCardView.adapter;
                        if (recommendCardViewPagerAdapter != null) {
                            recommendCardViewPagerAdapter.updateLayout(
                                    recommendCardView
                                            .getRecommendCardIndicatorAdapter()
                                            .getCurrentSupportedType());
                        }
                        recommendCardView.updateHeightCard(i);
                    }
                };
    }

    public final AsIndicatorAdapter getRecommendCardIndicatorAdapter() {
        return (AsIndicatorAdapter) this.recommendCardIndicatorAdapter$delegate.getValue();
    }

    public final void updateHeightCard(int i) {
        RecyclerView.LayoutManager layoutManager;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "updateHeightCard: position - ", "RecommendCardView");
        View childAt = this.viewPager.getChildAt(0);
        final View view = null;
        RecyclerView recyclerView = childAt instanceof RecyclerView ? (RecyclerView) childAt : null;
        if (recyclerView != null && (layoutManager = recyclerView.getLayoutManager()) != null) {
            view = layoutManager.findViewByPosition(i);
        }
        if (view != null) {
            view.post(
                    new Runnable() { // from class:
                        // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView$updateHeightCard$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            view.measure(
                                    View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824),
                                    View.MeasureSpec.makeMeasureSpec(0, 0));
                            if (this.viewPager.getLayoutParams().height
                                    != view.getMeasuredHeight()) {
                                ViewPager2 viewPager2 = this.viewPager;
                                ViewGroup.LayoutParams layoutParams = viewPager2.getLayoutParams();
                                Intrinsics.checkNotNull(
                                        layoutParams,
                                        "null cannot be cast to non-null type"
                                            + " android.widget.LinearLayout.LayoutParams");
                                LinearLayout.LayoutParams layoutParams2 =
                                        (LinearLayout.LayoutParams) layoutParams;
                                layoutParams2.height = view.getMeasuredHeight();
                                viewPager2.setLayoutParams(layoutParams2);
                            }
                        }
                    });
        }
    }

    public final void updateLoadingLayout(boolean z) {
        if (z) {
            boolean z2 = this.layoutState == 1;
            ViewGroup.LayoutParams layoutParams = this.loadingViewPager.getLayoutParams();
            FragmentActivity fragmentActivity = this.activity;
            layoutParams.height =
                    fragmentActivity
                            .getBaseContext()
                            .getResources()
                            .getDimensionPixelSize(
                                    z2
                                            ? R.dimen.as_recommend_loading_min_height_landscape
                                            : R.dimen.as_recommend_loading_content_area_height);
            if (z2) {
                this.dummyText1line.setVisibility(8);
                this.dummyText2line.getLayoutParams().width =
                        fragmentActivity
                                .getBaseContext()
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.as_recommend_loading_textview_landscape);
            }
        }
    }
}
