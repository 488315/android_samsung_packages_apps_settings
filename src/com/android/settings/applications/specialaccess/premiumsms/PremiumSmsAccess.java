package com.android.settings.applications.specialaccess.premiumsms;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.applications.AppStateBaseBridge;
import com.android.settings.applications.AppStateSmsPremBridge;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.EmptyTextSettings;
import com.android.settingslib.RestrictedDropDownPreference;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.widget.FooterPreference;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PremiumSmsAccess extends EmptyTextSettings
        implements AppStateBaseBridge.Callback,
                ApplicationsState.Callbacks,
                Preference.OnPreferenceChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.premium_sms_settings);
    public ApplicationsState mApplicationsState;
    public ApplicationsState.Session mSession;
    public AppStateSmsPremBridge mSmsBackend;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PremiumSmsPreference extends RestrictedDropDownPreference {
        public final ApplicationsState.AppEntry mAppEntry;

        public PremiumSmsPreference(ApplicationsState.AppEntry appEntry, Context context) {
            super(context);
            this.mAppEntry = appEntry;
            appEntry.ensureLabel(context);
            setTitle(appEntry.label);
            Drawable drawable = appEntry.icon;
            if (drawable != null) {
                setIcon(drawable);
            }
            setEntries(R.array.security_settings_premium_sms_values);
            this.mEntryValues =
                    new CharSequence[] {String.valueOf(1), String.valueOf(2), String.valueOf(3)};
            Object obj = appEntry.extraInfo;
            setValue(
                    String.valueOf(
                            obj instanceof AppStateSmsPremBridge.SmsState
                                    ? ((AppStateSmsPremBridge.SmsState) obj).smsState
                                    : 0));
            setSummary("%s");
            this.mHelper.checkEcmRestrictionAndSetDisabled(
                    "android:premium_sms_access", appEntry.info.packageName);
        }

        @Override // com.android.settingslib.RestrictedDropDownPreference,
                  // androidx.preference.DropDownPreference, androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            if (getIcon() == null) {
                preferenceViewHolder.itemView.post(
                        new Runnable() { // from class:
                                         // com.android.settings.applications.specialaccess.premiumsms.PremiumSmsAccess.PremiumSmsPreference.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                PremiumSmsPreference premiumSmsPreference =
                                        PremiumSmsPreference.this;
                                PremiumSmsAccess.this.mApplicationsState.ensureIcon(
                                        premiumSmsPreference.mAppEntry);
                                PremiumSmsPreference premiumSmsPreference2 =
                                        PremiumSmsPreference.this;
                                premiumSmsPreference2.setIcon(premiumSmsPreference2.mAppEntry.icon);
                            }
                        });
            }
            super.onBindViewHolder(preferenceViewHolder);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 388;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.premium_sms_settings;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void logSpecialPermissionChange(int r9, java.lang.String r10) {
        /*
            r8 = this;
            r0 = 1
            r1 = 0
            if (r9 == r0) goto L13
            r0 = 2
            if (r9 == r0) goto L10
            r0 = 3
            if (r9 == r0) goto Lc
            r4 = r1
            goto L16
        Lc:
            r0 = 780(0x30c, float:1.093E-42)
        Le:
            r4 = r0
            goto L16
        L10:
            r0 = 779(0x30b, float:1.092E-42)
            goto Le
        L13:
            r0 = 778(0x30a, float:1.09E-42)
            goto Le
        L16:
            if (r4 == 0) goto L3b
            com.android.settings.overlay.FeatureFactoryImpl r0 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r0 == 0) goto L33
            com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider r2 = r0.getMetricsFeatureProvider()
            androidx.fragment.app.FragmentActivity r8 = r8.getActivity()
            r2.getClass()
            int r3 = com.android.settingslib.core.instrumentation.MetricsFeatureProvider.getAttribution(r8)
            r5 = 388(0x184, float:5.44E-43)
            r6 = r9
            r7 = r10
            r2.action(r3, r4, r5, r6, r7)
            goto L3b
        L33:
            java.lang.UnsupportedOperationException r8 = new java.lang.UnsupportedOperationException
            java.lang.String r9 = "No feature factory configured"
            r8.<init>(r9)
            throw r8
        L3b:
            java.util.HashMap r8 = new java.util.HashMap
            r8.<init>()
            java.lang.String r0 = "pkgname"
            r8.put(r0, r10)
            java.lang.String r10 = "newValue"
            java.lang.String r9 = java.lang.Integer.toString(r9)
            r8.put(r10, r9)
            r9 = 388(0x184, float:5.44E-43)
            java.lang.String r9 = java.lang.Integer.toString(r9)
            r10 = 3919(0xf4f, float:5.492E-42)
            java.lang.String r10 = java.lang.Integer.toString(r10)
            com.samsung.android.settings.logging.SALogging.insertSALog(r9, r10, r8, r1)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.specialaccess.premiumsms.PremiumSmsAccess.logSpecialPermissionChange(int,"
                    + " java.lang.String):void");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ApplicationsState applicationsState =
                ApplicationsState.getInstance((Application) getContext().getApplicationContext());
        this.mApplicationsState = applicationsState;
        this.mSession = applicationsState.newSession(this, getSettingsLifecycle());
        getContext();
        this.mSmsBackend = new AppStateSmsPremBridge(this.mApplicationsState, this);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mSmsBackend.release();
        super.onDestroy();
    }

    @Override // com.android.settings.applications.AppStateBaseBridge.Callback
    public final void onExtraInfoUpdated() {
        this.mSession.rebuild(
                AppStateSmsPremBridge.FILTER_APP_PREMIUM_SMS,
                ApplicationsState.ALPHA_COMPARATOR,
                true);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        this.mSmsBackend.pause();
        super.onPause();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        PremiumSmsPreference premiumSmsPreference = (PremiumSmsPreference) preference;
        int parseInt = Integer.parseInt((String) obj);
        logSpecialPermissionChange(parseInt, premiumSmsPreference.mAppEntry.info.packageName);
        AppStateSmsPremBridge appStateSmsPremBridge = this.mSmsBackend;
        appStateSmsPremBridge.mSmsManager.setPremiumSmsConsent(
                premiumSmsPreference.mAppEntry.info.packageName, parseInt);
        return true;
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRebuildComplete(ArrayList arrayList) {
        if (arrayList == null) {
            return;
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        preferenceScreen.mOrderingAsAdded = true;
        for (int i = 0; i < arrayList.size(); i++) {
            PremiumSmsPreference premiumSmsPreference =
                    new PremiumSmsPreference(
                            (ApplicationsState.AppEntry) arrayList.get(i), getPrefContext());
            premiumSmsPreference.setOnPreferenceChangeListener(this);
            preferenceScreen.addPreference(premiumSmsPreference);
        }
        if (arrayList.size() != 0) {
            FooterPreference footerPreference = new FooterPreference(getPrefContext());
            footerPreference.setTitle(R.string.premium_sms_warning);
            preferenceScreen.addPreference(footerPreference);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSmsBackend.resume(true);
    }

    @Override // com.android.settings.widget.EmptyTextSettings,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setEmptyText(R.string.premium_sms_none);
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLauncherInfoChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLoadEntriesCompleted() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageIconChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageListChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageSizeChanged(String str) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRunningStateChanged(boolean z) {}
}
