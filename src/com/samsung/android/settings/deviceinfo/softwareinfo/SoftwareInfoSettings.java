package com.samsung.android.settings.deviceinfo.softwareinfo;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.deviceinfo.BuildNumberPreferenceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.MinorModeUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SoftwareInfoSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_software_info_settings);
    public BuildNumberPreferenceController mBuildNumberPreferenceController;
    public final Uri mDevelopEnabled = Settings.Global.getUriFor("development_settings_enabled");
    public final AnonymousClass2 mDeveloperSettingsObserver =
            new ContentObserver(new Handler(Looper.getMainLooper())) { // from class:
                // com.samsung.android.settings.deviceinfo.softwareinfo.SoftwareInfoSettings.2
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    super.onChange(z, uri);
                    SoftwareInfoSettings softwareInfoSettings = SoftwareInfoSettings.this;
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            SoftwareInfoSettings.SEARCH_INDEX_DATA_PROVIDER;
                    LocalBroadcastManager.getInstance(softwareInfoSettings.getPrefContext())
                            .sendBroadcast(
                                    new Intent(
                                            "com.android.settingslib.development.DevelopmentSettingsEnabler.SETTINGS_CHANGED"));
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.softwareinfo.SoftwareInfoSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    SoftwareInfoSettings.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new CarrierConfigPreferenceController(context));
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        getActivity();
        getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CarrierConfigPreferenceController(context));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SoftwareInfoSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4809;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_software_info_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (this.mBuildNumberPreferenceController.onActivityResult(i, i2, intent)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        BuildNumberPreferenceController buildNumberPreferenceController =
                (BuildNumberPreferenceController) use(BuildNumberPreferenceController.class);
        this.mBuildNumberPreferenceController = buildNumberPreferenceController;
        buildNumberPreferenceController.setHost(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        Context prefContext = getPrefContext();
        Log.d("SettingsLib/MinorModeUtils", "develop options restriction:");
        if (MinorModeUtils.isCHNMinorModeRestrictionEnabled(
                prefContext,
                "get_enable_developer_mode_restrict",
                "enable_developer_mode_restrict")) {
            getPrefContext()
                    .getContentResolver()
                    .registerContentObserver(
                            this.mDevelopEnabled, false, this.mDeveloperSettingsObserver);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        Context prefContext = getPrefContext();
        Log.d("SettingsLib/MinorModeUtils", "develop options restriction:");
        if (MinorModeUtils.isCHNMinorModeRestrictionEnabled(
                prefContext,
                "get_enable_developer_mode_restrict",
                "enable_developer_mode_restrict")) {
            getPrefContext()
                    .getContentResolver()
                    .unregisterContentObserver(this.mDeveloperSettingsObserver);
        }
    }
}
