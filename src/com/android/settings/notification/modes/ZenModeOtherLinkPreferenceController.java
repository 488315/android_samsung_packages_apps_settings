package com.android.settings.notification.modes;

import android.content.Context;
import android.icu.text.MessageFormat;
import android.os.Bundle;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeOtherLinkPreferenceController
        extends AbstractZenModePreferenceController {
    public final ZenModeSummaryHelper mSummaryHelper;

    public ZenModeOtherLinkPreferenceController(Context context, ZenModesBackend zenModesBackend) {
        super(context, "zen_other_settings", zenModesBackend);
        this.mSummaryHelper = new ZenModeSummaryHelper(this.mContext, this.mBackend);
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        Bundle bundle = new Bundle();
        bundle.putString("MODE_ID", zenMode.mId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ZenModeOtherFragment.class.getName();
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
                                new ZenModeSummaryHelper$$ExternalSyntheticLambda0(2));
        int size = arrayList.size();
        MessageFormat messageFormat =
                new MessageFormat(
                        zenModeSummaryHelper.mContext.getString(
                                R.string.zen_mode_other_sounds_summary),
                        Locale.getDefault());
        HashMap hashMap = new HashMap();
        hashMap.put("count", Integer.valueOf(size));
        if (size >= 1) {
            hashMap.put("sound_category_1", arrayList.get(0));
            if (size >= 2) {
                hashMap.put("sound_category_2", arrayList.get(1));
                if (size == 3) {
                    hashMap.put("sound_category_3", arrayList.get(2));
                }
            }
        }
        preference.setSummary(messageFormat.format(hashMap));
    }
}
