package com.android.settings.development.compat;

import android.compat.Compatibility;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArraySet;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.internal.compat.CompatibilityChangeConfig;
import com.android.internal.compat.CompatibilityChangeInfo;
import com.android.internal.compat.IPlatformCompat;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PlatformCompatDashboard extends DashboardFragment {
    public CompatibilityChangeInfo[] mChanges;
    public IPlatformCompat mPlatformCompat;
    String mSelectedApp;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CompatChangePreferenceChangeListener
            implements Preference.OnPreferenceChangeListener {
        public final long changeId;

        public CompatChangePreferenceChangeListener(long j) {
            this.changeId = j;
        }

        @Override // androidx.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            PlatformCompatDashboard platformCompatDashboard = PlatformCompatDashboard.this;
            try {
                ArraySet arraySet = new ArraySet();
                ArraySet arraySet2 = new ArraySet();
                boolean booleanValue = ((Boolean) obj).booleanValue();
                long j = this.changeId;
                if (booleanValue) {
                    arraySet.add(Long.valueOf(j));
                } else {
                    arraySet2.add(Long.valueOf(j));
                }
                platformCompatDashboard
                        .getPlatformCompat()
                        .setOverrides(
                                new CompatibilityChangeConfig(
                                        new Compatibility.ChangeConfig(arraySet, arraySet2)),
                                platformCompatDashboard.mSelectedApp);
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public final void addPreferences(ApplicationInfo applicationInfo) {
        List list;
        getPreferenceScreen().removeAll();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Context context = getPreferenceScreen().getContext();
        Drawable loadIcon = applicationInfo.loadIcon(context.getPackageManager());
        Preference preference = new Preference(context);
        preference.setIcon(loadIcon);
        preference.setSummary(
                getString(
                        R.string.platform_compat_selected_app_summary,
                        this.mSelectedApp,
                        Integer.valueOf(applicationInfo.targetSdkVersion)));
        preferenceScreen.addPreference(preference);
        try {
            CompatibilityChangeConfig appConfig =
                    getPlatformCompat()
                            .getAppConfig(
                                    getPackageManager().getApplicationInfo(this.mSelectedApp, 0));
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            TreeMap treeMap = new TreeMap();
            for (CompatibilityChangeInfo compatibilityChangeInfo : this.mChanges) {
                if (compatibilityChangeInfo.getEnableSinceTargetSdk() > 0) {
                    if (treeMap.containsKey(
                            Integer.valueOf(compatibilityChangeInfo.getEnableSinceTargetSdk()))) {
                        list =
                                (List)
                                        treeMap.get(
                                                Integer.valueOf(
                                                        compatibilityChangeInfo
                                                                .getEnableSinceTargetSdk()));
                    } else {
                        list = new ArrayList();
                        treeMap.put(
                                Integer.valueOf(compatibilityChangeInfo.getEnableSinceTargetSdk()),
                                list);
                    }
                    list.add(compatibilityChangeInfo);
                } else if (compatibilityChangeInfo.getDisabled()) {
                    arrayList2.add(compatibilityChangeInfo);
                } else {
                    arrayList.add(compatibilityChangeInfo);
                }
            }
            createChangeCategoryPreference(
                    arrayList,
                    appConfig,
                    getString(R.string.platform_compat_default_enabled_title));
            createChangeCategoryPreference(
                    arrayList2,
                    appConfig,
                    getString(R.string.platform_compat_default_disabled_title));
            for (Integer num : treeMap.keySet()) {
                createChangeCategoryPreference(
                        (List) treeMap.get(num),
                        appConfig,
                        getString(R.string.platform_compat_target_sdk_title, num));
            }
        } catch (PackageManager.NameNotFoundException | RemoteException e) {
            throw new RuntimeException("Could not get app config!", e);
        }
    }

    public final void createChangeCategoryPreference(
            List list, CompatibilityChangeConfig compatibilityChangeConfig, String str) {
        String str2;
        PreferenceCategory preferenceCategory =
                new PreferenceCategory(getPreferenceScreen().getContext());
        preferenceCategory.setTitle(str);
        getPreferenceScreen().addPreference(preferenceCategory);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CompatibilityChangeInfo compatibilityChangeInfo = (CompatibilityChangeInfo) it.next();
            Context context = getPreferenceScreen().getContext();
            boolean isChangeEnabled =
                    compatibilityChangeConfig.isChangeEnabled(compatibilityChangeInfo.getId());
            SwitchPreferenceCompat switchPreferenceCompat = new SwitchPreferenceCompat(context);
            if (compatibilityChangeInfo.getName() != null) {
                str2 = compatibilityChangeInfo.getName();
            } else {
                str2 = "Change_" + compatibilityChangeInfo.getId();
            }
            switchPreferenceCompat.setSummary(str2);
            switchPreferenceCompat.setKey(str2);
            try {
                switchPreferenceCompat.setEnabled(
                        getPlatformCompat()
                                        .getOverrideValidator()
                                        .getOverrideAllowedState(
                                                compatibilityChangeInfo.getId(), this.mSelectedApp)
                                        .state
                                == 0);
                switchPreferenceCompat.setChecked(isChangeEnabled);
                switchPreferenceCompat.setOnPreferenceChangeListener(
                        new CompatChangePreferenceChangeListener(compatibilityChangeInfo.getId()));
                preferenceCategory.addPreference(switchPreferenceCompat);
            } catch (RemoteException e) {
                throw new RuntimeException(
                        "Could not check if change can be overridden for app.", e);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PlatformCompatDashboard";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1805;
    }

    public final IPlatformCompat getPlatformCompat() {
        if (this.mPlatformCompat == null) {
            this.mPlatformCompat =
                    IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
        }
        return this.mPlatformCompat;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.platform_compat_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            this.mChanges = getPlatformCompat().listUIChanges();
        } catch (RemoteException e) {
            throw new RuntimeException("Could not list changes!", e);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (isFinishingOrDestroyed()) {
            return;
        }
        Bundle arguments = getArguments();
        if (arguments == null) {
            finish();
            return;
        }
        this.mSelectedApp = arguments.getString("compat_app");
        try {
            addPreferences(getPackageManager().getApplicationInfo(this.mSelectedApp, 0));
        } catch (PackageManager.NameNotFoundException unused) {
            finish();
        }
    }
}
