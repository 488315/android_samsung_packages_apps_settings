package com.samsung.android.settings.widget;

import com.android.settings.widget.HomepagePreference;

import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class HomepagePreferenceHelper {
    public final HomepagePreference mPreference;
    public Optional mVisible = Optional.empty();
    public Optional mOrder = Optional.empty();

    public HomepagePreferenceHelper(HomepagePreference homepagePreference) {
        this.mPreference = homepagePreference;
    }

    public final int getOrder() {
        return ((Integer) this.mOrder.orElse(Integer.valueOf(this.mPreference.getOrder())))
                .intValue();
    }

    public final boolean isVisible() {
        return ((Boolean) this.mVisible.orElse(Boolean.valueOf(this.mPreference.isVisible())))
                .booleanValue();
    }
}
