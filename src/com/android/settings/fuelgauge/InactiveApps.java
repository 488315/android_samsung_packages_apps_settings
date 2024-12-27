package com.android.settings.fuelgauge;

import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.development.DeveloperOptionAwareMixin;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InactiveApps extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener, DeveloperOptionAwareMixin {
    public static final CharSequence[] FULL_SETTABLE_BUCKETS_NAMES = {
        "ACTIVE", "WORKING_SET", "FREQUENT", "RARE", "RESTRICTED"
    };
    public static final CharSequence[] FULL_SETTABLE_BUCKETS_VALUES = {
        Integer.toString(10),
        Integer.toString(20),
        Integer.toString(30),
        Integer.toString(40),
        Integer.toString(45)
    };
    public UsageStatsManager mUsageStats;

    public final CharSequence[] getAllowableBuckets(String str, CharSequence[] charSequenceArr) {
        int appMinStandbyBucket = this.mUsageStats.getAppMinStandbyBucket(str);
        if (appMinStandbyBucket > 45) {
            return charSequenceArr;
        }
        if (appMinStandbyBucket < 10) {
            return new CharSequence[0];
        }
        int binarySearch =
                Arrays.binarySearch(
                        FULL_SETTABLE_BUCKETS_VALUES, Integer.toString(appMinStandbyBucket));
        return binarySearch < 0
                ? charSequenceArr
                : (CharSequence[]) Arrays.copyOfRange(charSequenceArr, 0, binarySearch + 1);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUsageStats =
                (UsageStatsManager) getActivity().getSystemService(UsageStatsManager.class);
        addPreferencesFromResource(R.xml.placeholder_preference_screen);
        getActivity().setTitle(R.string.inactive_apps_title);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.mUsageStats.setAppStandbyBucket(preference.getKey(), Integer.parseInt((String) obj));
        updateSummary((ListPreference) preference);
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        preferenceScreen.mOrderingAsAdded = false;
        FragmentActivity activity = getActivity();
        PackageManager packageManager = activity.getPackageManager();
        String packageName = activity.getPackageName();
        CharSequence[] charSequenceArr = FULL_SETTABLE_BUCKETS_NAMES;
        CharSequence[] charSequenceArr2 = FULL_SETTABLE_BUCKETS_VALUES;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            String str = resolveInfo.activityInfo.applicationInfo.packageName;
            ListPreference listPreference = new ListPreference(getPrefContext(), null);
            listPreference.setTitle(resolveInfo.loadLabel(packageManager));
            listPreference.setIcon(resolveInfo.loadIcon(packageManager));
            listPreference.setKey(str);
            listPreference.mEntries = getAllowableBuckets(str, charSequenceArr);
            listPreference.mEntryValues = getAllowableBuckets(str, charSequenceArr2);
            updateSummary(listPreference);
            if (TextUtils.equals(str, packageName)) {
                listPreference.setEnabled(false);
            }
            listPreference.setOnPreferenceChangeListener(this);
            preferenceScreen.addPreference(listPreference);
        }
    }

    public final void updateSummary(ListPreference listPreference) {
        Resources resources = getActivity().getResources();
        int appStandbyBucket = this.mUsageStats.getAppStandbyBucket(listPreference.getKey());
        listPreference.setSummary(
                resources.getString(
                        R.string.standby_bucket_summary,
                        appStandbyBucket != 5
                                ? appStandbyBucket != 10
                                        ? appStandbyBucket != 20
                                                ? appStandbyBucket != 30
                                                        ? appStandbyBucket != 40
                                                                ? appStandbyBucket != 45
                                                                        ? appStandbyBucket != 50
                                                                                ? ApnSettings
                                                                                        .MVNO_NONE
                                                                                : "NEVER"
                                                                        : "RESTRICTED"
                                                                : "RARE"
                                                        : "FREQUENT"
                                                : "WORKING_SET"
                                        : "ACTIVE"
                                : "EXEMPTED"));
        boolean z = appStandbyBucket >= 10 && appStandbyBucket <= 45;
        if (z) {
            listPreference.setValue(Integer.toString(appStandbyBucket));
        }
        listPreference.setEnabled(z);
    }
}
