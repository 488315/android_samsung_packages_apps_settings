package com.android.settings.applications;

import android.content.Context;
import android.icu.text.MessageFormat;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.SwitchPreference;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.SummaryPreference;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.development.DeveloperOptionAwareMixin;
import com.android.settings.development.DisableDevSettingsDialogFragment;

import com.samsung.android.settings.applications.SizeFormatterUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProcessStatsSummary extends ProcessStatsBase
        implements Preference.OnPreferenceClickListener, DeveloperOptionAwareMixin {
    public Preference mAppListPreference;
    public Preference mAverageUsed;
    public SwitchPreference mForceEnablePssProfiling;
    public Preference mFree;
    public PreferenceCategory mMemoryInfoPrefCategory;
    public Preference mPerformance;
    public Preference mReservedMemory;
    public SummaryPreference mSummaryPref;
    public Preference mTotalMemory;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 202;
    }

    @Override // com.android.settings.applications.ProcessStatsBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.process_stats_summary);
        this.mMemoryInfoPrefCategory = (PreferenceCategory) findPreference("memory_info");
        this.mSummaryPref = (SummaryPreference) findPreference("status_header");
        this.mPerformance = findPreference("performance");
        this.mTotalMemory = findPreference("total_memory");
        this.mReservedMemory = findPreference("reserved_memory");
        this.mAverageUsed = findPreference("average_used");
        this.mFree = findPreference("free");
        Preference findPreference = findPreference("apps_list");
        this.mAppListPreference = findPreference;
        findPreference.setOnPreferenceClickListener(this);
        this.mForceEnablePssProfiling =
                (SwitchPreference) findPreference("force_enable_pss_profiling");
        if (!Flags.removeAppProfilerPssCollection()) {
            this.mForceEnablePssProfiling.setVisible(false);
        } else {
            this.mForceEnablePssProfiling.setOnPreferenceClickListener(this);
            this.mForceEnablePssProfiling.setChecked(
                    Settings.Global.getInt(
                                    getContext().getContentResolver(),
                                    "force_enable_pss_profiling",
                                    0)
                            == 1);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (preference != this.mAppListPreference) {
            if (preference == this.mForceEnablePssProfiling) {
                DisableDevSettingsDialogFragment disableDevSettingsDialogFragment =
                        new DisableDevSettingsDialogFragment();
                disableDevSettingsDialogFragment.setTargetFragment(this, 0);
                disableDevSettingsDialogFragment.setCancelable(false);
                disableDevSettingsDialogFragment.show(
                        getActivity().getSupportFragmentManager(), "DisableDevSettingDlg");
            }
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("transfer_stats", true);
        bundle.putInt("duration_index", this.mDurationIndex);
        ProcStatsData.sStatsXfer = this.mStatsManager.mStats;
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = ProcessStatsUi.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.memory_usage_apps, null);
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 202;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.applications.ProcessStatsBase
    public final void refreshUi() {
        Context context = getContext();
        if (Flags.removeAppProfilerPssCollection()) {
            this.mMemoryInfoPrefCategory.setVisible(this.mForceEnablePssProfiling.mChecked);
        }
        if (!Flags.removeAppProfilerPssCollection()
                || Settings.Global.getInt(
                                context.getContentResolver(), "force_enable_pss_profiling", 0)
                        == 1) {
            ProcStatsData.MemInfo memInfo = this.mStatsManager.mMemInfo;
            double d = memInfo.realUsedRam;
            long j = memInfo.hwRam;
            double d2 = memInfo.realTotalRam;
            long j2 = (long) d;
            Formatter.formatBytes(context.getResources(), j2, 1);
            String gbFormattedSizeString = SizeFormatterUtils.getGbFormattedSizeString(context, j);
            double d3 = memInfo.realFreeRam;
            String gbFormattedSizeString2 =
                    SizeFormatterUtils.getGbFormattedSizeString(context, (long) d3);
            this.mReservedMemory.setSummary(
                    SizeFormatterUtils.getGbFormattedSizeString(context, memInfo.realReservedRam));
            CharSequence[] textArray = getResources().getTextArray(R.array.ram_states);
            int i = this.mStatsManager.mStats.mMemFactor;
            if (i == -1) {
                i = 0;
            } else if (i >= 4) {
                i -= 4;
            }
            CharSequence charSequence =
                    (i < 0 || i >= textArray.length - 1)
                            ? textArray[textArray.length - 1]
                            : textArray[i];
            SummaryPreference summaryPreference = this.mSummaryPref;
            String numberFormattedString =
                    SizeFormatterUtils.getNumberFormattedString(j2, RoundingMode.HALF_UP);
            summaryPreference.mAmount = numberFormattedString;
            if (numberFormattedString != null && summaryPreference.mUnits != null) {
                summaryPreference.setTitle(
                        TextUtils.expandTemplate(
                                summaryPreference.getContext().getText(R.string.storage_size_large),
                                summaryPreference.mAmount,
                                summaryPreference.mUnits));
            }
            SummaryPreference summaryPreference2 = this.mSummaryPref;
            String string =
                    j2 == 0
                            ? context.getString(R.string.megabyteShort)
                            : j2 < 1024
                                    ? context.getString(R.string.byteShort)
                                    : j2 < 1000000
                                            ? context.getString(R.string.kilobyteShort)
                                            : j2 < 1000000000
                                                    ? context.getString(R.string.megabyteShort)
                                                    : context.getString(R.string.gigabyteShort);
            summaryPreference2.mUnits = string;
            if (summaryPreference2.mAmount != null && string != null) {
                summaryPreference2.setTitle(
                        TextUtils.expandTemplate(
                                summaryPreference2
                                        .getContext()
                                        .getText(R.string.storage_size_large),
                                summaryPreference2.mAmount,
                                summaryPreference2.mUnits));
            }
            SummaryPreference summaryPreference3 = this.mSummaryPref;
            summaryPreference3.mLeftRatio = (float) (d / (d3 + d));
            summaryPreference3.mMiddleRatio = 0.0f;
            summaryPreference3.notifyChanged();
            this.mPerformance.setSummary(charSequence);
            this.mTotalMemory.setSummary(gbFormattedSizeString);
            this.mAverageUsed.setSummary(
                    NumberFormat.getPercentInstance().format(j2 / ((long) d2)));
            this.mFree.setSummary(gbFormattedSizeString2);
            String string2 = getString(ProcessStatsBase.sDurationLabels[this.mDurationIndex]);
            int size = this.mStatsManager.pkgEntries.size();
            MessageFormat messageFormat =
                    new MessageFormat(
                            getResources().getString(R.string.memory_usage_apps_summary),
                            Locale.getDefault());
            HashMap hashMap = new HashMap();
            hashMap.put("count", Integer.valueOf(size));
            hashMap.put("time", string2);
            this.mAppListPreference.setSummary(messageFormat.format(hashMap));
        }
    }
}
