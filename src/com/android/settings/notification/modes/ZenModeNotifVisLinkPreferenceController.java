package com.android.settings.notification.modes;

import android.content.Context;
import android.os.Bundle;

import androidx.preference.Preference;

import com.android.settings.core.SubSettingLauncher;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeNotifVisLinkPreferenceController
        extends AbstractZenModePreferenceController {
    public final ZenModeSummaryHelper mSummaryBuilder;

    public ZenModeNotifVisLinkPreferenceController(
            Context context, ZenModesBackend zenModesBackend) {
        super(context, "notification_visibility", zenModesBackend);
        this.mSummaryBuilder = new ZenModeSummaryHelper(context, zenModesBackend);
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final CharSequence getSummary(ZenMode zenMode) {
        return this.mSummaryBuilder.getBlockedEffectsSummary(zenMode);
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        Bundle bundle = new Bundle();
        bundle.putString("MODE_ID", zenMode.mId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ZenModeNotifVisFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        preference.setIntent(subSettingLauncher.toIntent());
    }
}
