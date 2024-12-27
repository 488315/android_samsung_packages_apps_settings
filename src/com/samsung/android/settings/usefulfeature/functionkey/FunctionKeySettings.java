package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.SearchIndexableResource;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FunctionKeySettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public final SettingsObserver mSettingsObserver = new SettingsObserver(new Handler());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.functionkey.FunctionKeySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!UsefulfeatureUtils.hasSideKeyDedicatedAppEnable(context)) {
                ((ArrayList) nonIndexableKeys).add("short_press_dedicated_app");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_function_key_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return !KnoxUtils.isApplicationRestricted(
                    context, Arrays.asList("top_level_advanced_features", "function_key_setting"));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri FUNC_KEY_DOUBLE_PRESS_OPEN_APP_TYPE;
        public final Uri FUNC_KEY_DOUBLE_PRESS_SWITCH;
        public final Uri FUNC_KEY_DOUBLE_PRESS_TYPE;
        public final Uri FUNC_KEY_LONG_PRESS_TYPE;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.FUNC_KEY_DOUBLE_PRESS_SWITCH =
                    Settings.Global.getUriFor("function_key_config_doublepress");
            this.FUNC_KEY_DOUBLE_PRESS_TYPE =
                    Settings.Global.getUriFor("function_key_config_doublepress_type");
            this.FUNC_KEY_DOUBLE_PRESS_OPEN_APP_TYPE =
                    Settings.Global.getUriFor("function_key_config_doublepress_value");
            this.FUNC_KEY_LONG_PRESS_TYPE =
                    Settings.Global.getUriFor("function_key_config_longpress_type");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            FunctionKeySettings functionKeySettings = FunctionKeySettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    FunctionKeySettings.SEARCH_INDEX_DATA_PROVIDER;
            functionKeySettings.updatePreferenceStates();
        }

        public final void setListening(boolean z) {
            FunctionKeySettings functionKeySettings = FunctionKeySettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    FunctionKeySettings.SEARCH_INDEX_DATA_PROVIDER;
            ContentResolver contentResolver = functionKeySettings.getContentResolver();
            if (!z) {
                contentResolver.unregisterContentObserver(this);
                return;
            }
            contentResolver.registerContentObserver(this.FUNC_KEY_DOUBLE_PRESS_SWITCH, false, this);
            contentResolver.registerContentObserver(this.FUNC_KEY_DOUBLE_PRESS_TYPE, false, this);
            contentResolver.registerContentObserver(
                    this.FUNC_KEY_DOUBLE_PRESS_OPEN_APP_TYPE, false, this);
            contentResolver.registerContentObserver(this.FUNC_KEY_LONG_PRESS_TYPE, false, this);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "FunctionKeySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7613;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_function_key_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((FunctionKeyTipsForPowerOffPreferenceController)
                        use(FunctionKeyTipsForPowerOffPreferenceController.class))
                .init(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mSettingsObserver.setListening(true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mSettingsObserver.setListening(false);
    }
}
