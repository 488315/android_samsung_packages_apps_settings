package com.samsung.android.settings.navigationbar;

import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ NavigationBarGestureDetailedPreference f$0;
    public final /* synthetic */ PreferenceViewHolder f$1;

    public /* synthetic */ NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda0(
            NavigationBarGestureDetailedPreference navigationBarGestureDetailedPreference,
            PreferenceViewHolder preferenceViewHolder) {
        this.f$0 = navigationBarGestureDetailedPreference;
        this.f$1 = preferenceViewHolder;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.setAnimationProperties(this.f$1);
    }
}
