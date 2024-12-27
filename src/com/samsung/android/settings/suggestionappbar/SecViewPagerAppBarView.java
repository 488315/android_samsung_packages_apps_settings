package com.samsung.android.settings.suggestionappbar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Keep;
import androidx.appcompat.widget.SeslIndicator;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.model.view.ViewPagerAppBarView;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Keep
@Metadata(
        d1 = {
            "\u00003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000*\u0001\b\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n"
                + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\n"
                + "\u001a\u00020\u000bH\u0002J\u0006\u0010\f\u001a\u00020\r"
                + "J\u0016\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\r"
                + "2\u0006\u0010\u0010\u001a\u00020\r"
                + "J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\r"
                + "J\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015R\u0010\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0004\n"
                + "\u0002\u0010\t¨\u0006\u0016"
        },
        d2 = {
            "Lcom/samsung/android/settings/suggestionappbar/SecViewPagerAppBarView;",
            "Lcom/google/android/material/appbar/model/view/ViewPagerAppBarView;",
            "context",
            "Landroid/content/Context;",
            "attrs",
            "Landroid/util/AttributeSet;",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "pageChangeCallback",
            "com/samsung/android/settings/suggestionappbar/SecViewPagerAppBarView$pageChangeCallback$1",
            "Lcom/samsung/android/settings/suggestionappbar/SecViewPagerAppBarView$pageChangeCallback$1;",
            "addIndicator",
            ApnSettings.MVNO_NONE,
            "getDefaultPosition",
            ApnSettings.MVNO_NONE,
            "initIndicator",
            "count",
            "defaultPosition",
            "removeIndicator",
            "position",
            "setAdapter",
            "viewPagerAdapter",
            "Lcom/samsung/android/settings/suggestionappbar/SecViewPagerAdapter;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes4.dex */
public final class SecViewPagerAppBarView extends ViewPagerAppBarView {
    public static final int $stable = 0;
    private final SecViewPagerAppBarView$pageChangeCallback$1 pageChangeCallback;

    public /* synthetic */ SecViewPagerAppBarView(
            Context context,
            AttributeSet attributeSet,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    private final void addIndicator() {
        SeslIndicator indicator;
        SeslIndicator indicator2 = getIndicator();
        if (indicator2 != null) {
            indicator2.addIndicator();
        }
        SeslIndicator indicator3 = getIndicator();
        if (indicator3 != null && ((ArrayList) indicator3.indicator).size() == 1) {
            SeslIndicator indicator4 = getIndicator();
            if (indicator4 == null) {
                return;
            }
            indicator4.setVisibility(8);
            return;
        }
        SeslIndicator indicator5 = getIndicator();
        Integer valueOf =
                indicator5 != null
                        ? Integer.valueOf(((ArrayList) indicator5.indicator).size())
                        : null;
        Intrinsics.checkNotNull(valueOf);
        if (valueOf.intValue() <= 1 || (indicator = getIndicator()) == null) {
            return;
        }
        indicator.setVisibility(0);
    }

    public final int getDefaultPosition() {
        ViewPager2 viewpager = getViewpager();
        if (viewpager != null) {
            return viewpager.mCurrentItem;
        }
        return 0;
    }

    public final void initIndicator(int count, int defaultPosition) {
        ViewPager2 viewpager;
        for (int i = 0; i < count; i++) {
            addIndicator();
        }
        ViewPager2 viewpager2 = getViewpager();
        if (viewpager2 != null) {
            viewpager2.registerOnPageChangeCallback(this.pageChangeCallback);
        }
        if (defaultPosition >= count || (viewpager = getViewpager()) == null) {
            return;
        }
        viewpager.setCurrentItem(defaultPosition, false);
    }

    public final void removeIndicator(int position) {
        SeslIndicator indicator;
        SeslIndicator indicator2 = getIndicator();
        if (indicator2 != null) {
            indicator2.removeIndicator(position);
        }
        SeslIndicator indicator3 = getIndicator();
        if (indicator3 == null
                || ((ArrayList) indicator3.indicator).size() != 1
                || (indicator = getIndicator()) == null) {
            return;
        }
        indicator.setVisibility(8);
    }

    public final void setAdapter(SecViewPagerAdapter viewPagerAdapter) {
        Intrinsics.checkNotNullParameter(viewPagerAdapter, "viewPagerAdapter");
        ViewPager2 viewpager = getViewpager();
        if (viewpager == null) {
            return;
        }
        viewpager.setAdapter(viewPagerAdapter);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.settings.suggestionappbar.SecViewPagerAppBarView$pageChangeCallback$1] */
    public SecViewPagerAppBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.pageChangeCallback =
                new ViewPager2
                        .OnPageChangeCallback() { // from class:
                                                  // com.samsung.android.settings.suggestionappbar.SecViewPagerAppBarView$pageChangeCallback$1
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public final void onPageSelected(int i) {
                        SeslIndicator indicator;
                        indicator = SecViewPagerAppBarView.this.getIndicator();
                        if (indicator == null) {
                            return;
                        }
                        indicator.setSelectedPosition(i);
                    }
                };
    }
}
