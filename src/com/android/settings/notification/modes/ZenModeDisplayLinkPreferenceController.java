package com.android.settings.notification.modes;

import android.content.Context;
import android.icu.text.MessageFormat;
import android.os.Bundle;
import android.service.notification.ZenDeviceEffects;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeDisplayLinkPreferenceController
        extends AbstractZenModePreferenceController {
    public final ZenModeSummaryHelper mSummaryHelper;

    public ZenModeDisplayLinkPreferenceController(
            Context context, ZenModesBackend zenModesBackend) {
        super(context, "mode_display_settings", zenModesBackend);
        this.mSummaryHelper = new ZenModeSummaryHelper(context, zenModesBackend);
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final CharSequence getSummary(ZenMode zenMode) {
        boolean z;
        ZenModeSummaryHelper zenModeSummaryHelper = this.mSummaryHelper;
        zenModeSummaryHelper.getClass();
        ArrayList arrayList = new ArrayList();
        if (zenMode.getPolicy().shouldShowAllVisualEffects()) {
            z = true;
        } else {
            arrayList.add(zenModeSummaryHelper.getBlockedEffectsSummary(zenMode));
            z = false;
        }
        ZenDeviceEffects deviceEffects = zenMode.mRule.getDeviceEffects();
        if (deviceEffects != null) {
            if (deviceEffects.shouldDisplayGrayscale()) {
                if (z) {
                    arrayList.add(
                            zenModeSummaryHelper.mContext.getString(R.string.mode_grayscale_title));
                } else {
                    arrayList.add(
                            zenModeSummaryHelper.mContext.getString(
                                    R.string.mode_grayscale_title_secondary_list));
                }
                z = false;
            }
            if (deviceEffects.shouldSuppressAmbientDisplay()) {
                if (z) {
                    arrayList.add(zenModeSummaryHelper.mContext.getString(R.string.mode_aod_title));
                } else {
                    arrayList.add(
                            zenModeSummaryHelper.mContext.getString(
                                    R.string.mode_aod_title_secondary_list));
                }
                z = false;
            }
            if (deviceEffects.shouldDimWallpaper()) {
                if (z) {
                    arrayList.add(
                            zenModeSummaryHelper.mContext.getString(R.string.mode_wallpaper_title));
                } else {
                    arrayList.add(
                            zenModeSummaryHelper.mContext.getString(
                                    R.string.mode_wallpaper_title_secondary_list));
                }
                z = false;
            }
            if (deviceEffects.shouldUseNightMode()) {
                if (z) {
                    arrayList.add(
                            zenModeSummaryHelper.mContext.getString(
                                    R.string.mode_dark_theme_title));
                } else {
                    arrayList.add(
                            zenModeSummaryHelper.mContext.getString(
                                    R.string.mode_dark_theme_title_secondary_list));
                }
            }
        }
        int size = arrayList.size();
        MessageFormat messageFormat =
                new MessageFormat(
                        zenModeSummaryHelper.mContext.getString(
                                R.string.mode_display_settings_summary),
                        Locale.getDefault());
        HashMap hashMap = new HashMap();
        hashMap.put("count", Integer.valueOf(size));
        if (size >= 1) {
            hashMap.put("effect_1", arrayList.get(0));
            if (size >= 2) {
                hashMap.put("effect_2", arrayList.get(1));
                if (size == 3) {
                    hashMap.put("effect_3", arrayList.get(2));
                }
            }
        }
        return messageFormat.format(hashMap);
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        Bundle bundle = new Bundle();
        bundle.putString("MODE_ID", zenMode.mId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ZenModeDisplayFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        preference.setIntent(subSettingLauncher.toIntent());
    }
}
