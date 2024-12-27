package com.android.settings.applications;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DecimalFormat;
import android.icu.text.MeasureFormat;
import android.icu.text.NumberFormat;
import android.icu.text.UnicodeSetSpanner;
import android.icu.util.Measure;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.IconDrawableFactory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.CancellablePreference;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.EntityHeaderController;

import com.samsung.android.settingslib.applications.AppFileSizeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProcessStatsDetail extends SettingsPreferenceFragment {
    public static final AnonymousClass2 sEntryCompare = new AnonymousClass2();
    public ProcStatsPackageEntry mApp;
    public DevicePolicyManager mDpm;
    public MenuItem mForceStop;
    public PackageManager mPm;
    public PreferenceCategory mProcGroup;
    public final ArrayMap mServiceMap = new ArrayMap();
    public double mTotalScale;
    public long mTotalTime;
    public double mWeightToRam;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.ProcessStatsDetail$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ ComponentName val$service;

        public AnonymousClass1(ComponentName componentName) {
            this.val$service = componentName;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.ProcessStatsDetail$2, reason: invalid class name */
    public final class AnonymousClass2 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            double d = ((ProcStatsEntry) obj).mRunWeight;
            double d2 = ((ProcStatsEntry) obj2).mRunWeight;
            if (d < d2) {
                return 1;
            }
            return d > d2 ? -1 : 0;
        }
    }

    public static String capitalize(String str) {
        char charAt = str.charAt(0);
        if (!Character.isLowerCase(charAt)) {
            return str;
        }
        return Character.toUpperCase(charAt) + str.substring(1);
    }

    public final void checkForceStop() {
        ProcStatsPackageEntry procStatsPackageEntry;
        if (this.mForceStop == null || (procStatsPackageEntry = this.mApp) == null) {
            return;
        }
        if (((ProcStatsEntry) procStatsPackageEntry.mEntries.get(0)).mUid < 10000) {
            this.mForceStop.setVisible(false);
            return;
        }
        boolean z = false;
        for (int i = 0; i < this.mApp.mEntries.size(); i++) {
            ProcStatsEntry procStatsEntry = (ProcStatsEntry) this.mApp.mEntries.get(i);
            for (int i2 = 0; i2 < procStatsEntry.mPackages.size(); i2++) {
                String str = (String) procStatsEntry.mPackages.get(i2);
                if (this.mDpm.packageHasActiveAdmins(str)) {
                    this.mForceStop.setEnabled(false);
                    return;
                } else {
                    try {
                        if ((this.mPm.getApplicationInfo(str, 0).flags & 2097152) == 0) {
                            z = true;
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
            }
        }
        if (z) {
            this.mForceStop.setVisible(true);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 21;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        String charSequence;
        super.onCreate(bundle);
        this.mPm = getActivity().getPackageManager();
        this.mDpm = (DevicePolicyManager) getActivity().getSystemService("device_policy");
        Bundle arguments = getArguments();
        ProcStatsPackageEntry procStatsPackageEntry =
                (ProcStatsPackageEntry) arguments.getParcelable("package_entry");
        this.mApp = procStatsPackageEntry;
        if (procStatsPackageEntry == null) {
            return;
        }
        procStatsPackageEntry.retrieveUiData(getActivity(), this.mPm);
        this.mWeightToRam = arguments.getDouble("weight_to_ram");
        this.mTotalTime = arguments.getLong("total_time");
        arguments.getDouble("max_memory_usage");
        this.mTotalScale = arguments.getDouble("total_scale");
        this.mServiceMap.clear();
        if (this.mApp != null) {
            addPreferencesFromResource(R.xml.sec_app_memory_settings);
            PreferenceCategory preferenceCategory =
                    (PreferenceCategory) findPreference("processes");
            this.mProcGroup = preferenceCategory;
            preferenceCategory.removeAll();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.mApp.mEntries.size(); i++) {
                ProcStatsEntry procStatsEntry = (ProcStatsEntry) this.mApp.mEntries.get(i);
                if (procStatsEntry.mPackage.equals("os")) {
                    procStatsEntry.mLabel = procStatsEntry.mName;
                } else {
                    String str = this.mApp.mUiLabel;
                    String str2 = procStatsEntry.mName;
                    if (str2.contains(":")) {
                        str = capitalize(str2.substring(str2.lastIndexOf(58) + 1));
                    } else if (!str2.startsWith(procStatsEntry.mPackage)) {
                        str = str2;
                    } else if (str2.length() != procStatsEntry.mPackage.length()) {
                        int length = procStatsEntry.mPackage.length();
                        if (str2.charAt(length) == '.') {
                            length++;
                        }
                        str = capitalize(str2.substring(length));
                    }
                    procStatsEntry.mLabel = str;
                }
                arrayList.add(procStatsEntry);
            }
            Collections.sort(arrayList, sEntryCompare);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                ProcStatsEntry procStatsEntry2 = (ProcStatsEntry) arrayList.get(i2);
                Preference preference = new Preference(getPrefContext());
                preference.setTitle(procStatsEntry2.mLabel);
                preference.setSelectable(false);
                long max = Math.max(procStatsEntry2.mRunDuration, procStatsEntry2.mBgDuration);
                double d = procStatsEntry2.mRunWeight;
                double d2 = this.mWeightToRam;
                preference.setSummary(
                        getString(
                                R.string.memory_use_running_format,
                                AppFileSizeFormatter.formatFileSize(
                                        1,
                                        Math.max(
                                                (long) (d * d2),
                                                (long) (procStatsEntry2.mBgWeight * d2)),
                                        getActivity()),
                                ProcStatsPackageEntry.getFrequency(
                                        max / this.mTotalTime, getActivity())));
                this.mProcGroup.addPreference(preference);
            }
            if (this.mProcGroup.getPreferenceCount() < 2) {
                getPreferenceScreen().removePreference(this.mProcGroup);
            }
            ProcStatsPackageEntry procStatsPackageEntry2 = this.mApp;
            double d3 = procStatsPackageEntry2.mRunWeight;
            double d4 = procStatsPackageEntry2.mBgWeight;
            if (d3 <= d4) {
                d3 = d4;
            }
            double d5 = d3 * this.mWeightToRam;
            Resources resources = getActivity().getResources();
            UnicodeSetSpanner unicodeSetSpanner = AppFileSizeFormatter.SPACES_AND_CONTROLS;
            AppFileSizeFormatter.RoundedBytesResult roundBytes =
                    AppFileSizeFormatter.RoundedBytesResult.roundBytes(1, (long) d5);
            Locale locale = resources.getConfiguration().getLocales().get(0);
            NumberFormat numberFormat = NumberFormat.getInstance(locale);
            int i3 = roundBytes.fractionDigits;
            numberFormat.setMinimumFractionDigits(i3);
            numberFormat.setMaximumFractionDigits(i3);
            numberFormat.setGroupingUsed(false);
            if (numberFormat instanceof DecimalFormat) {
                numberFormat.setRoundingMode(4);
            }
            float f = roundBytes.value;
            String format = numberFormat.format(f);
            if (AppFileSizeFormatter.isFileSize(roundBytes.units)) {
                charSequence = AppFileSizeFormatter.getSuffixOverride(resources, roundBytes.units);
            } else {
                String format2 =
                        MeasureFormat.getInstance(
                                        locale, MeasureFormat.FormatWidth.SHORT, numberFormat)
                                .format(new Measure(Float.valueOf(f), roundBytes.units));
                int indexOf = format2.indexOf(format);
                if (indexOf != -1) {
                    format2 =
                            format2.substring(0, indexOf)
                                    + format2.substring(
                                            format.length() + indexOf, format2.length());
                }
                charSequence = AppFileSizeFormatter.SPACES_AND_CONTROLS.trim(format2).toString();
            }
            Preference findPreference = findPreference("status_header");
            Locale.getDefault();
            findPreference.setSummary(format + " " + charSequence);
            ProcStatsPackageEntry procStatsPackageEntry3 = this.mApp;
            findPreference("frequency")
                    .setSummary(
                            ProcStatsPackageEntry.getFrequency(
                                    ((float)
                                                    Math.max(
                                                            procStatsPackageEntry3.mRunDuration,
                                                            procStatsPackageEntry3.mBgDuration))
                                            / ((float) this.mTotalTime),
                                    getActivity()));
            ProcStatsPackageEntry procStatsPackageEntry4 = this.mApp;
            findPreference("max_usage")
                    .setSummary(
                            AppFileSizeFormatter.formatFileSize(
                                    1,
                                    (long)
                                            (((double)
                                                            Math.max(
                                                                    procStatsPackageEntry4
                                                                            .mMaxBgMem,
                                                                    procStatsPackageEntry4
                                                                            .mMaxRunMem))
                                                    * this.mTotalScale
                                                    * 1024.0d),
                                    getContext()));
        }
        setHasOptionsMenu(true);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.mForceStop = menu.add(0, 1, 0, R.string.force_stop);
        checkForceStop();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return false;
        }
        ProcStatsPackageEntry procStatsPackageEntry = this.mApp;
        if (procStatsPackageEntry != null && procStatsPackageEntry.mEntries != null) {
            ActivityManager activityManager =
                    (ActivityManager) getActivity().getSystemService("activity");
            for (int i = 0; i < this.mApp.mEntries.size(); i++) {
                ProcStatsEntry procStatsEntry = (ProcStatsEntry) this.mApp.mEntries.get(i);
                for (int i2 = 0; i2 < procStatsEntry.mPackages.size(); i2++) {
                    activityManager.forceStopPackage((String) procStatsEntry.mPackages.get(i2));
                }
            }
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        checkForceStop();
        updateRunningServices();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ProcStatsPackageEntry procStatsPackageEntry = this.mApp;
        if (procStatsPackageEntry == null
                || procStatsPackageEntry.mPackage == null
                || procStatsPackageEntry.mUiTargetApp == null) {
            finish();
            return;
        }
        FragmentActivity activity = getActivity();
        try {
            PackageInfo packageInfo = null;
            EntityHeaderController entityHeaderController =
                    new EntityHeaderController(activity, this, null);
            entityHeaderController.setIcon(
                    this.mApp.mUiTargetApp != null
                            ? IconDrawableFactory.newInstance(activity)
                                    .getBadgedIcon(this.mApp.mUiTargetApp)
                            : new ColorDrawable(0));
            ProcStatsPackageEntry procStatsPackageEntry2 = this.mApp;
            entityHeaderController.mLabel = procStatsPackageEntry2.mUiLabel;
            String str = procStatsPackageEntry2.mPackage;
            entityHeaderController.mPackageName = str;
            ApplicationInfo applicationInfo = procStatsPackageEntry2.mUiTargetApp;
            entityHeaderController.mUid = applicationInfo != null ? applicationInfo.uid : -10000;
            entityHeaderController.mHasAppInfoLink = true;
            entityHeaderController.mAction1 = 0;
            entityHeaderController.mAction2 = 0;
            if (!str.equals("os")) {
                packageInfo = this.mPm.getPackageInfo(this.mApp.mPackage, 0);
            }
            if (packageInfo != null) {
                entityHeaderController.mSummary = packageInfo.versionName;
            }
            getPreferenceScreen().addPreference(entityHeaderController.done(getPrefContext()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public final void updateRunningServices() {
        List<ActivityManager.RunningServiceInfo> runningServices =
                ((ActivityManager) getActivity().getSystemService("activity"))
                        .getRunningServices(Preference.DEFAULT_ORDER);
        int size = this.mServiceMap.size();
        for (int i = 0; i < size; i++) {
            CancellablePreference cancellablePreference =
                    (CancellablePreference) this.mServiceMap.valueAt(i);
            cancellablePreference.mCancellable = false;
            cancellablePreference.notifyChanged();
        }
        int size2 = runningServices.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ActivityManager.RunningServiceInfo runningServiceInfo = runningServices.get(i2);
            if ((runningServiceInfo.started || runningServiceInfo.clientLabel != 0)
                    && (runningServiceInfo.flags & 8) == 0) {
                ComponentName componentName = runningServiceInfo.service;
                CancellablePreference cancellablePreference2 =
                        (CancellablePreference) this.mServiceMap.get(componentName);
                if (cancellablePreference2 != null) {
                    cancellablePreference2.mListener = new AnonymousClass1(componentName);
                    cancellablePreference2.mCancellable = true;
                    cancellablePreference2.notifyChanged();
                }
            }
        }
    }
}
