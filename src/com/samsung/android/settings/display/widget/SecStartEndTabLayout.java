package com.samsung.android.settings.display.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.arch.core.util.Function;

import com.android.settings.R;

import com.google.android.material.tabs.TabLayout;

import java.util.Locale;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
class SecStartEndTabLayout extends TabLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mTabIndex;
    public Function mTimeFormatter;
    public final int[] mTimes;

    public SecStartEndTabLayout(Context context) {
        this(context, null);
    }

    public final void updateTime(int i, int i2) {
        this.mTimes[i] = i2;
        String str = (String) this.mTimeFormatter.apply(Integer.valueOf(i2));
        TabLayout.Tab tabAt = getTabAt(i);
        Objects.requireNonNull(tabAt);
        tabAt.subText = str;
        tabAt.updateView();
        if (tabAt.text == null) {
            tabAt.contentDesc = str;
            tabAt.updateView();
            return;
        }
        tabAt.contentDesc = ((Object) tabAt.text) + " " + str;
        tabAt.updateView();
    }

    public SecStartEndTabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.SecDarkModeTimePickerTab);
    }

    public SecStartEndTabLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTimes = new int[2];
        this.mTabIndex = 0;
        this.mTimeFormatter =
                new Function() { // from class:
                                 // com.samsung.android.settings.display.widget.SecStartEndTabLayout$$ExternalSyntheticLambda0
                    @Override // androidx.arch.core.util.Function
                    public final Object apply(Object obj) {
                        int intValue = ((Integer) obj).intValue();
                        int i2 = SecStartEndTabLayout.$r8$clinit;
                        SecStartEndTabLayout.this.getClass();
                        return String.format(
                                Locale.getDefault(),
                                "%02d:%02d",
                                Integer.valueOf(intValue / 60),
                                Integer.valueOf(intValue % 60));
                    }
                };
        seslSetSubTabStyle();
    }
}
