package com.android.settings.applications;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;

import com.android.settings.R;
import com.android.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProcessStatsUi extends ProcessStatsBase {
    public static final AnonymousClass1 sMaxPackageEntryCompare;
    public static final AnonymousClass1 sPackageEntryCompare;
    public PreferenceGroup mAppListGroup;
    public MenuItem mMenuAvg;
    public MenuItem mMenuMax;
    public PackageManager mPm;
    public boolean mShowMax;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.applications.ProcessStatsUi$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.applications.ProcessStatsUi$1] */
    static {
        final int i = 0;
        sPackageEntryCompare =
                new Comparator() { // from class: com.android.settings.applications.ProcessStatsUi.1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i) {
                            case 0:
                                ProcStatsPackageEntry procStatsPackageEntry =
                                        (ProcStatsPackageEntry) obj;
                                ProcStatsPackageEntry procStatsPackageEntry2 =
                                        (ProcStatsPackageEntry) obj2;
                                double max =
                                        Math.max(
                                                procStatsPackageEntry2.mRunWeight,
                                                procStatsPackageEntry2.mBgWeight);
                                double max2 =
                                        Math.max(
                                                procStatsPackageEntry.mRunWeight,
                                                procStatsPackageEntry.mBgWeight);
                                if (max2 != max) {
                                    if (max2 < max) {}
                                }
                                break;
                            default:
                                ProcStatsPackageEntry procStatsPackageEntry3 =
                                        (ProcStatsPackageEntry) obj;
                                ProcStatsPackageEntry procStatsPackageEntry4 =
                                        (ProcStatsPackageEntry) obj2;
                                double max3 =
                                        Math.max(
                                                procStatsPackageEntry4.mMaxBgMem,
                                                procStatsPackageEntry4.mMaxRunMem);
                                double max4 =
                                        Math.max(
                                                procStatsPackageEntry3.mMaxBgMem,
                                                procStatsPackageEntry3.mMaxRunMem);
                                if (max4 != max3) {
                                    if (max4 < max3) {}
                                }
                                break;
                        }
                        return -1;
                    }
                };
        final int i2 = 1;
        sMaxPackageEntryCompare =
                new Comparator() { // from class: com.android.settings.applications.ProcessStatsUi.1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i2) {
                            case 0:
                                ProcStatsPackageEntry procStatsPackageEntry =
                                        (ProcStatsPackageEntry) obj;
                                ProcStatsPackageEntry procStatsPackageEntry2 =
                                        (ProcStatsPackageEntry) obj2;
                                double max =
                                        Math.max(
                                                procStatsPackageEntry2.mRunWeight,
                                                procStatsPackageEntry2.mBgWeight);
                                double max2 =
                                        Math.max(
                                                procStatsPackageEntry.mRunWeight,
                                                procStatsPackageEntry.mBgWeight);
                                if (max2 != max) {
                                    if (max2 < max) {}
                                }
                                break;
                            default:
                                ProcStatsPackageEntry procStatsPackageEntry3 =
                                        (ProcStatsPackageEntry) obj;
                                ProcStatsPackageEntry procStatsPackageEntry4 =
                                        (ProcStatsPackageEntry) obj2;
                                double max3 =
                                        Math.max(
                                                procStatsPackageEntry4.mMaxBgMem,
                                                procStatsPackageEntry4.mMaxRunMem);
                                double max4 =
                                        Math.max(
                                                procStatsPackageEntry3.mMaxBgMem,
                                                procStatsPackageEntry3.mMaxRunMem);
                                if (max4 != max3) {
                                    if (max4 < max3) {}
                                }
                                break;
                        }
                        return -1;
                    }
                };
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 23;
    }

    @Override // com.android.settings.applications.ProcessStatsBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPm = getActivity().getPackageManager();
        addPreferencesFromResource(R.xml.process_stats_ui);
        this.mAppListGroup = (PreferenceGroup) findPreference("app_list");
        setHasOptionsMenu(true);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        this.mMenuAvg = menu.add(0, 1, 0, R.string.sort_avg_use);
        MenuItem add = menu.add(0, 2, 0, R.string.sort_max_use);
        this.mMenuMax = add;
        add.setVisible(!this.mShowMax);
        this.mMenuAvg.setVisible(this.mShowMax);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != 1 && itemId != 2) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.mShowMax = !this.mShowMax;
        refreshUi();
        this.mMenuMax.setVisible(!this.mShowMax);
        this.mMenuAvg.setVisible(this.mShowMax);
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!(preference instanceof ProcessStatsPreference)) {
            return false;
        }
        ProcessStatsBase.launchMemoryDetail(
                (SettingsActivity) getActivity(),
                this.mStatsManager.mMemInfo,
                ((ProcessStatsPreference) preference).mEntry);
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.applications.ProcessStatsBase
    public final void refreshUi() {
        double d;
        this.mAppListGroup.removeAll();
        PreferenceGroup preferenceGroup = this.mAppListGroup;
        preferenceGroup.mOrderingAsAdded = false;
        preferenceGroup.setTitle(
                this.mShowMax ? R.string.maximum_memory_use : R.string.average_memory_use);
        FragmentActivity activity = getActivity();
        ProcStatsData procStatsData = this.mStatsManager;
        ProcStatsData.MemInfo memInfo = procStatsData.mMemInfo;
        ArrayList arrayList = procStatsData.pkgEntries;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((ProcStatsPackageEntry) arrayList.get(i)).updateMetrics();
        }
        Collections.sort(arrayList, this.mShowMax ? sMaxPackageEntryCompare : sPackageEntryCompare);
        double d2 = this.mShowMax ? memInfo.realTotalRam : memInfo.usedWeight * memInfo.weightToRam;
        int i2 = 0;
        while (i2 < arrayList.size()) {
            ProcStatsPackageEntry procStatsPackageEntry = (ProcStatsPackageEntry) arrayList.get(i2);
            ProcessStatsPreference processStatsPreference =
                    new ProcessStatsPreference(getPrefContext(), null);
            procStatsPackageEntry.retrieveUiData(activity, this.mPm);
            PackageManager packageManager = this.mPm;
            double d3 = memInfo.weightToRam;
            boolean z = !this.mShowMax;
            processStatsPreference.mEntry = procStatsPackageEntry;
            String str =
                    TextUtils.isEmpty(procStatsPackageEntry.mUiLabel)
                            ? procStatsPackageEntry.mPackage
                            : procStatsPackageEntry.mUiLabel;
            processStatsPreference.setTitle(str);
            if (TextUtils.isEmpty(str)) {
                Log.d(
                        "ProcessStatsPreference",
                        "PackageEntry contained no package name or uiLabel");
            }
            ApplicationInfo applicationInfo = procStatsPackageEntry.mUiTargetApp;
            if (applicationInfo != null) {
                processStatsPreference.setIcon(applicationInfo.loadIcon(packageManager));
            } else {
                processStatsPreference.setIcon(packageManager.getDefaultActivityIcon());
            }
            double d4 = procStatsPackageEntry.mRunWeight;
            ArrayList arrayList2 = arrayList;
            double d5 = procStatsPackageEntry.mBgWeight;
            boolean z2 = d4 > d5;
            if (z) {
                if (!z2) {
                    d4 = d5;
                }
                d = d4 * d3;
            } else {
                d =
                        (z2 ? procStatsPackageEntry.mMaxRunMem : procStatsPackageEntry.mMaxBgMem)
                                * memInfo.totalScale
                                * 1024.0d;
            }
            processStatsPreference.setSummary(
                    Formatter.formatShortFileSize(processStatsPreference.getContext(), (long) d));
            processStatsPreference.mProgress = (int) ((d * 100.0d) / d2);
            processStatsPreference.mProgressVisible = true;
            processStatsPreference.notifyChanged();
            processStatsPreference.setOrder(i2);
            this.mAppListGroup.addPreference(processStatsPreference);
            i2++;
            arrayList = arrayList2;
        }
    }
}
