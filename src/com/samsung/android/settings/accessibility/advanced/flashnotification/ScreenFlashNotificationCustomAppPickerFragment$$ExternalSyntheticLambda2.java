package com.samsung.android.settings.accessibility.advanced.flashnotification;

import androidx.picker.widget.SeslAppPickerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class ScreenFlashNotificationCustomAppPickerFragment$$ExternalSyntheticLambda2
        implements SeslAppPickerView.OnSearchFilterListener {
    public final /* synthetic */ ScreenFlashNotificationCustomAppPickerFragment f$0;

    @Override // androidx.picker.widget.SeslAppPickerView.OnSearchFilterListener
    public final void onSearchFilterCompleted(int i) {
        ScreenFlashNotificationCustomAppPickerFragment
                screenFlashNotificationCustomAppPickerFragment = this.f$0;
        if (i < 1) {
            screenFlashNotificationCustomAppPickerFragment.loadingViewController.showEmpty();
        } else {
            screenFlashNotificationCustomAppPickerFragment.loadingViewController.showContent(false);
        }
    }
}
