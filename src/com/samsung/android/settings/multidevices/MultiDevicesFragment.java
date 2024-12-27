package com.samsung.android.settings.multidevices;

import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.Utils;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.connection.tether.SecTetherUtils;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.multidevices.continuity.ContinuitySettings;
import com.samsung.android.settings.multidevices.smartthings.SmartThingsPreferenceController;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;
import com.samsung.android.settings.widget.SecRelativeLinkView;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MultiDevicesFragment extends SecDynamicFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.sec_multi_devices);
    public SecRelativeLinkView mRelativeLinkView;
    public final AnonymousClass1 mSettingObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.multidevices.MultiDevicesFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    Uri uri2;
                    Preference findPreference;
                    Log.i(
                            "MultiDevicesFragment",
                            "Continuity setting Changed: selfChange?=" + z + ", uri=" + uri);
                    if (uri == null
                            || z
                            || (uri2 = MultiDevicesFragment.this.mUriContinuity) == null
                            || !uri2.equals(uri)
                            || (findPreference =
                                            MultiDevicesFragment.this
                                                    .getPreferenceScreen()
                                                    .findPreference("continuity_setting"))
                                    == null) {
                        return;
                    }
                    ((SecMultiDevicesSwitchPreferenceScreen) findPreference)
                            .setChecked(
                                    ContinuitySettings.getContinuitySettingValue(
                                            MultiDevicesFragment.this.getContext()));
                }
            };
    public Uri mUriContinuity;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.multidevices.MultiDevicesFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("music_share_setting");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            BaseSearchIndexProvider baseSearchIndexProvider =
                    MultiDevicesFragment.SEARCH_INDEX_DATA_PROVIDER;
            Log.i("MultiDevicesFragment", "appendSmartThingsRaw: ");
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "smartthings_settings";
            searchIndexableRaw.title = SmartThingsPreferenceController.getAppTitle(context);
            searchIndexableRaw.keywords = context.getString(R.string.smartthings_keywords);
            arrayList.add(searchIndexableRaw);
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw2).key = "autoswitch_setting";
            searchIndexableRaw2.title = String.valueOf(R.string.autoswitch_title);
            searchIndexableRaw2.keywords = context.getString(R.string.autoswitch_search_word);
            arrayList.add(searchIndexableRaw2);
            SearchIndexableRaw searchIndexableRaw3 = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw3).key = "continuity_setting";
            searchIndexableRaw3.title = String.valueOf(R.string.continuity_title);
            searchIndexableRaw3.keywords = context.getString(R.string.keywords_continuity_settings);
            arrayList.add(searchIndexableRaw3);
            return arrayList;
        }
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment
    public final Preference createPreference(Tile tile) {
        String string =
                tile.mMetaData.getString(
                        "com.android.settings.PREFERENCE_TYPE", ApnSettings.MVNO_NONE);
        Log.d("MultiDevicesFragment", "createPreference type : " + string);
        string.getClass();
        switch (string) {
            case "SwitchPreferenceScreen":
                return new SecMultiDevicesSwitchPreferenceScreen(getPrefContext());
            case "SwitchPreference":
                return new SecMultiDevicesSwitchPreference(getPrefContext());
            case "SecInsetCategoryPreference":
            case "PreferenceCategory":
                return super.createPreference(tile);
            default:
                return new SecMultiDevicesPreference(getPrefContext());
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "MultiDevicesFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8200;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_multi_devices;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getContext();
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        setAnimationAllowed(true);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecCustomDividerItemDecorator(
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider),
                                getContext(),
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_multi_devices_item_end_padding)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_multi_devices_item_image_size)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_multi_devices_item_start_padding)),
                                R.id.icon_frame,
                                android.R.id.icon));
        Uri uriFor = Settings.System.getUriFor("mcf_continuity");
        this.mUriContinuity = uriFor;
        if (uriFor != null) {
            getContext()
                    .getContentResolver()
                    .registerContentObserver(this.mUriContinuity, false, this.mSettingObserver);
        }
        return onCreateView;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        if (this.mUriContinuity != null) {
            getContext().getContentResolver().unregisterContentObserver(this.mSettingObserver);
        }
        this.mUriContinuity = null;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity();
        if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData.callerMetric = 8200;
            settingsPreferenceFragmentLinkData.intent =
                    new Intent("android.settings.BLUETOOTH_SETTINGS");
            settingsPreferenceFragmentLinkData.titleRes = R.string.bluetooth_settings;
            settingsPreferenceFragmentLinkData.topLevelKey = "top_level_connections";
            this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
            if (SecTetherUtils.isAvailableTetherMenu(getContext())) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData2.callerMetric = 8200;
                settingsPreferenceFragmentLinkData2.intent =
                        new Intent("android.settings.TETHER_SETTINGS");
                if (Rune.isJapanModel() || Utils.isWifiOnly(getContext())) {
                    settingsPreferenceFragmentLinkData2.titleRes =
                            R.string.tether_settings_title_usb_bluetooth;
                } else {
                    settingsPreferenceFragmentLinkData2.titleRes =
                            R.string.sec_tether_settings_title_all;
                }
                settingsPreferenceFragmentLinkData2.topLevelKey = "top_level_connections";
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
            }
            this.mRelativeLinkView.create(this);
        }
    }
}
