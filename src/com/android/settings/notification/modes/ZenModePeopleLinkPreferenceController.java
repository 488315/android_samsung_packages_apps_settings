package com.android.settings.notification.modes;

import android.content.Context;
import android.os.Bundle;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModePeopleLinkPreferenceController
        extends AbstractZenModePreferenceController {
    public final ZenModeSummaryHelper mSummaryHelper;

    public ZenModePeopleLinkPreferenceController(Context context, ZenModesBackend zenModesBackend) {
        super(context, "zen_mode_people", zenModesBackend);
        this.mSummaryHelper = new ZenModeSummaryHelper(this.mContext, this.mBackend);
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        Bundle bundle = new Bundle();
        bundle.putString("MODE_ID", zenMode.mId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ZenModePeopleFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        preference.setIntent(subSettingLauncher.toIntent());
        ZenModeSummaryHelper zenModeSummaryHelper = this.mSummaryHelper;
        zenModeSummaryHelper.getClass();
        int priorityCallSenders = zenMode.getPolicy().getPriorityCallSenders();
        int priorityMessageSenders = zenMode.getPolicy().getPriorityMessageSenders();
        int priorityConversationSenders = zenMode.getPolicy().getPriorityConversationSenders();
        preference.setSummary(
                (priorityCallSenders == 1
                                && priorityMessageSenders == 1
                                && priorityConversationSenders == 1)
                        ? zenModeSummaryHelper
                                .mContext
                                .getResources()
                                .getString(R.string.zen_mode_people_all)
                        : (priorityCallSenders == 4
                                        && priorityMessageSenders == 4
                                        && priorityConversationSenders == 3
                                        && !zenMode.getPolicy().isCategoryAllowed(4, false))
                                ? zenModeSummaryHelper
                                        .mContext
                                        .getResources()
                                        .getString(R.string.zen_mode_people_none)
                                : zenModeSummaryHelper
                                        .mContext
                                        .getResources()
                                        .getString(R.string.zen_mode_people_some));
    }
}
