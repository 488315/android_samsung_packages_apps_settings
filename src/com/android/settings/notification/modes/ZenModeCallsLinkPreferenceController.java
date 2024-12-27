package com.android.settings.notification.modes;

import android.content.Context;
import android.os.Bundle;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeCallsLinkPreferenceController
        extends AbstractZenModePreferenceController {
    public final ZenModeSummaryHelper mSummaryHelper;

    public ZenModeCallsLinkPreferenceController(Context context, ZenModesBackend zenModesBackend) {
        super(context, "zen_mode_people_calls", zenModesBackend);
        this.mSummaryHelper = new ZenModeSummaryHelper(context, zenModesBackend);
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        Bundle bundle = new Bundle();
        bundle.putString("MODE_ID", zenMode.mId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ZenModeCallsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        preference.setIntent(subSettingLauncher.toIntent());
        ZenModeSummaryHelper zenModeSummaryHelper = this.mSummaryHelper;
        zenModeSummaryHelper.getClass();
        ArrayList arrayList =
                (ArrayList)
                        zenModeSummaryHelper.getEnabledCategories(
                                zenMode.getPolicy(),
                                new ZenModeSummaryHelper$$ExternalSyntheticLambda0(1));
        int size = arrayList.size();
        preference.setSummary(
                size == 0
                        ? zenModeSummaryHelper.mContext.getString(R.string.zen_mode_none_calls)
                        : size == 1
                                ? zenModeSummaryHelper.mContext.getString(
                                        R.string.zen_mode_calls_summary_one, arrayList.get(0))
                                : zenModeSummaryHelper.mContext.getString(
                                        R.string.zen_mode_calls_summary_two,
                                        arrayList.get(0),
                                        arrayList.get(1)));
    }
}
