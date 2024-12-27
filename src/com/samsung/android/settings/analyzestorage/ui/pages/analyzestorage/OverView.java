package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsIndicatorAdapter;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter;
import com.samsung.android.settings.logging.LoggingHelper;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class OverView {
    public final FragmentActivity activity;
    public ViewPagerAdapter adapter;
    public final OverViewController controller;
    public final GridView indicator;
    public final Lazy indicatorAdapter$delegate;
    public int layoutState;
    public final OverView$pageChangeCallback$1 pageChangeCallback;
    public final View root;
    public MutableLiveData supportedStorageList;
    public final ViewPager2 viewPager;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.OverView$pageChangeCallback$1] */
    public OverView(View view, FragmentActivity activity, OverViewController controller, int i) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(controller, "controller");
        this.root = view;
        this.activity = activity;
        this.controller = controller;
        this.indicatorAdapter$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.OverView$indicatorAdapter$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Context baseContext = OverView.this.activity.getBaseContext();
                                Intrinsics.checkNotNullExpressionValue(
                                        baseContext, "getBaseContext(...)");
                                return new AsIndicatorAdapter(baseContext);
                            }
                        });
        this.supportedStorageList = new MutableLiveData();
        View requireViewById = view.requireViewById(R.id.indicator);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        this.indicator = (GridView) requireViewById;
        View requireViewById2 = view.requireViewById(R.id.as_home_overview_viewpager);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        this.viewPager = (ViewPager2) requireViewById2;
        this.pageChangeCallback =
                new ViewPager2
                        .OnPageChangeCallback() { // from class:
                                                  // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.OverView$pageChangeCallback$1
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public final void onPageSelected(int i2) {
                        OverView overView = OverView.this;
                        ArrayList arrayList = (ArrayList) overView.supportedStorageList.getValue();
                        if (i2 < (arrayList != null ? arrayList.size() : 0)) {
                            String screenIdForSA = PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA();
                            ArrayList arrayList2 =
                                    (ArrayList) overView.supportedStorageList.getValue();
                            Integer num = arrayList2 != null ? (Integer) arrayList2.get(i2) : null;
                            LoggingHelper.insertEventLogging(
                                    screenIdForSA,
                                    (num != null && num.intValue() == 0)
                                            ? 8810
                                            : (num != null && num.intValue() == 1)
                                                    ? 8840
                                                    : (num != null && num.intValue() == 101)
                                                            ? 8880
                                                            : 8860);
                            overView.getIndicatorAdapter().currentIndex = i2;
                            overView.getIndicatorAdapter().notifyDataSetChanged();
                        }
                    }
                };
    }

    public final AsIndicatorAdapter getIndicatorAdapter() {
        return (AsIndicatorAdapter) this.indicatorAdapter$delegate.getValue();
    }

    public final void initIndicator() {
        ArrayList arrayList = (ArrayList) this.supportedStorageList.getValue();
        int size = arrayList != null ? arrayList.size() : 0;
        int dimensionPixelSize =
                this.activity
                        .getResources()
                        .getDimensionPixelSize(R.dimen.as_home_viewpager_indicator_width);
        ViewGroup.LayoutParams layoutParams = this.indicator.getLayoutParams();
        layoutParams.width = dimensionPixelSize * size;
        this.indicator.setLayoutParams(layoutParams);
        this.indicator.setOnItemClickListener(
                new AdapterView
                        .OnItemClickListener() { // from class:
                                                 // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.OverView$initIndicator$1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(
                            AdapterView adapterView, View view, int i, long j) {
                        OverView overView = OverView.this;
                        ViewPager2 viewPager2 = overView.viewPager;
                        FragmentActivity context = overView.activity;
                        Intrinsics.checkNotNullParameter(context, "context");
                        viewPager2.setCurrentItem(
                                i,
                                Settings.System.getInt(
                                                context.getContentResolver(),
                                                "remove_animations",
                                                0)
                                        != 1);
                    }
                });
    }
}
