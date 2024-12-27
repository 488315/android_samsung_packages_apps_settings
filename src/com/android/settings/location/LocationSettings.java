package com.android.settings.location;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.settings.bixby.AppContext;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationSettings extends DashboardFragment
        implements LocationEnabler.LocationModeChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.sec_location_settings);
    public AnonymousClass1 mContentObserver;
    public RecentLocationAccessPreferenceController mController;
    public LocationEnabler mLocationEnabler;

    public static void addPreferencesSorted(List list, PreferenceGroup preferenceGroup) {
        Collections.sort(
                list, Comparator.comparing(new LocationSettings$$ExternalSyntheticLambda0()));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            preferenceGroup.addPreference((Preference) it.next());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final AppContext getAppContext() {
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        if (settingsMainSwitchBar == null) {
            return null;
        }
        AppContext.Builder builder = new AppContext.Builder();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(
                "locationEnabeld",
                ((SeslSwitchBar) settingsMainSwitchBar).mSwitch.isChecked() ? "true" : "false");
        jSONArray.put(jSONObject2);
        JSONArray jSONArray2 = builder.mLLMContext;
        jSONObject.put(
                "type",
                jSONArray.length() > 1
                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                "LocationSettings", "[]")
                        : "LocationSettings");
        int length = jSONArray.length();
        JSONObject jSONObject3 = jSONArray;
        if (length <= 1) {
            jSONObject3 = jSONObject2;
        }
        jSONObject.put("value", jSONObject3);
        jSONArray2.put(jSONObject);
        return builder.build();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LocationSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 11001;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_location_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return SemEmergencyManager.isEmergencyMode(context)
                ? "top_level_location_upsm"
                : "top_level_location";
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean isPreferenceAnimationAllowed() {
        return false;
    }

    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.settings.location.LocationSettings$1] */
    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        if (getContext().getResources().getBoolean(R.bool.config_disable_location_toggle_for_chrome)
                && SystemProperties.getBoolean("ro.boot.enable_privacy_hub_for_chrome", false)) {
            Log.i("LocationSettings", "Disabling location toggle for chrome devices");
            settingsMainSwitchBar.setClickable(false);
            settingsMainSwitchBar.setTooltipText(
                    getResources().getString(R.string.location_settings_tooltip_text_for_chrome));
        }
        settingsMainSwitchBar.show();
        new LocationSwitchBarController(
                settingsActivity, settingsMainSwitchBar, getSettingsLifecycle());
        this.mLocationEnabler = new LocationEnabler(getContext(), this, getSettingsLifecycle());
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.location.LocationSettings.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        LocationSettings.this.mController.updateShowSystem();
                    }
                };
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("locationShowSystemOps"),
                        false,
                        this.mContentObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((AppLocationPermissionPreferenceController)
                        use(AppLocationPermissionPreferenceController.class))
                .init(this);
        RecentLocationAccessPreferenceController recentLocationAccessPreferenceController =
                (RecentLocationAccessPreferenceController)
                        use(RecentLocationAccessPreferenceController.class);
        this.mController = recentLocationAccessPreferenceController;
        recentLocationAccessPreferenceController.init(this);
        ((RecentLocationAccessSeeAllButtonPreferenceController)
                        use(RecentLocationAccessSeeAllButtonPreferenceController.class))
                .init(this);
        ((LocationForWorkPreferenceController) use(LocationForWorkPreferenceController.class))
                .init(this);
        ((LocationForPrivateProfilePreferenceController)
                        use(LocationForPrivateProfilePreferenceController.class))
                .init(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        replaceEnterpriseStringTitle(
                "managed_profile_location_switch",
                "Settings.WORK_PROFILE_LOCATION_SWITCH_TITLE",
                R.string.managed_profile_location_switch_title);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        if (this.mContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.mContentObserver);
        }
    }

    @Override // com.android.settings.location.LocationEnabler.LocationModeChangeListener
    public final void onLocationModeChanged(int i, boolean z) {
        if (this.mLocationEnabler.isEnabled(i)) {
            scrollToPreference("recent_location_access");
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
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
                                                                        .sec_app_list_item_icon_min_width)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_item_padding_start)),
                                R.id.icon_frame,
                                android.R.id.icon));
    }
}
