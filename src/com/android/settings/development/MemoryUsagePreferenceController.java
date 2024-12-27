package com.android.settings.development;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.applications.ProcStatsData;
import com.android.settings.applications.ProcessStatsBase;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.applications.SizeFormatterUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MemoryUsagePreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin {
    public ProcStatsData mProcStatsData;

    public static void $r8$lambda$bttyscuSUXMpljDiTNR0ymFc0qI(
            final MemoryUsagePreferenceController memoryUsagePreferenceController) {
        memoryUsagePreferenceController.mProcStatsData.refreshStats(true);
        ProcStatsData.MemInfo memInfo = memoryUsagePreferenceController.mProcStatsData.mMemInfo;
        final String gbFormattedSizeString =
                SizeFormatterUtils.getGbFormattedSizeString(
                        memoryUsagePreferenceController.mContext, (long) memInfo.realUsedRam);
        final String gbFormattedSizeString2 =
                SizeFormatterUtils.getGbFormattedSizeString(
                        memoryUsagePreferenceController.mContext, memInfo.hwRam);
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.development.MemoryUsagePreferenceController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        r0.mPreference.setSummary(
                                MemoryUsagePreferenceController.this.mContext.getString(
                                        R.string.memory_summary,
                                        gbFormattedSizeString,
                                        gbFormattedSizeString2));
                    }
                });
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mProcStatsData = getProcStatsData();
        ThreadUtils.postOnBackgroundThread(
                new MemoryUsagePreferenceController$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "memory";
    }

    public ProcStatsData getProcStatsData() {
        return new ProcStatsData(this.mContext, false);
    }

    public void setDuration() {
        this.mProcStatsData.mDuration = ProcessStatsBase.sDurations[0];
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ThreadUtils.postOnBackgroundThread(
                new MemoryUsagePreferenceController$$ExternalSyntheticLambda0(this, 0));
    }
}
