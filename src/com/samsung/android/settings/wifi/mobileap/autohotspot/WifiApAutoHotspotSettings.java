package com.samsung.android.settings.wifi.mobileap.autohotspot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApFamilyMember;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApAutoHotspotSettings extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FragmentActivity mActivity;
    public LayoutPreference mAutoHotspotDescriptionLayoutPreference;
    public Context mContext;
    public LayoutPreference mFamilySharingDescriptionLayoutPreference;
    public SecPreferenceCategory mMyAccountPreferenceCategory;
    public final AnonymousClass1 mOnAutoHotspotStateChangeListener = new AnonymousClass1();
    public SettingsMainSwitchBar mSwitchBar;
    public WifiApAutoHotspotSwitchEnabler mWifiApAutoHotspotSwitchEnabler;
    public WifiApFamilySharingSwitchController mWifiApFamilySharingSwitchController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSettings$1, reason: invalid class name */
    public final class AnonymousClass1
            implements WifiApAutoHotspotSwitchEnabler.OnStateChangeListener,
                    Preference.OnPreferenceClickListener {
        public /* synthetic */ AnonymousClass1() {}

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public boolean onPreferenceClick(Preference preference) {
            int i = WifiApAutoHotspotSettings.$r8$clinit;
            Log.i("WifiApAutoHotspotSettings", "AutoHotspot account owner profile clicked.");
            FragmentActivity fragmentActivity = WifiApAutoHotspotSettings.this.mActivity;
            boolean z = WifiApSettingsUtils.DBG;
            Log.i("WifiApSettingsUtils", "Launching Samsung Account Settings Activity");
            Intent intent =
                    new Intent("com.samsung.android.mobileservice.action.ACTION_OPEN_SASETTINGS");
            intent.setPackage("com.osp.app.signin");
            intent.setFlags(603979776);
            fragmentActivity.startActivity(intent);
            return true;
        }

        @Override // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSwitchEnabler.OnStateChangeListener, com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler.OnStateChangeListener
        public void onStateChanged(int i) {
            int i2 = WifiApAutoHotspotSettings.$r8$clinit;
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    i, "AutoHotspot onStateChanged() - resultCode: ", "WifiApAutoHotspotSettings");
            WifiApAutoHotspotSettings wifiApAutoHotspotSettings = WifiApAutoHotspotSettings.this;
            wifiApAutoHotspotSettings.mSwitchBar.setChecked(
                    WifiApFrameworkUtils.isAutoHotspotSetOn(wifiApAutoHotspotSettings.mContext));
            if (i == 5) {
                Log.d(
                        "WifiApAutoHotspotSettings",
                        "AutoHotspot feature can`t be used: RESULT_ERROR_TETHERING_RESTRICTED");
                wifiApAutoHotspotSettings.finish();
                return;
            }
            if (i == 1) {
                Log.d(
                        "WifiApAutoHotspotSettings",
                        "AutoHotspot feature can`t be used: RESULT_ERROR_AIRPLANE_MODE_ON");
                Toast.makeText(
                                wifiApAutoHotspotSettings.mContext,
                                R.string.flight_mode_enabled_toast,
                                0)
                        .show();
                wifiApAutoHotspotSettings.finish();
                return;
            }
            if (i == 2) {
                Log.d(
                        "WifiApAutoHotspotSettings",
                        "AutoHotspot feature can`t be used: RESULT_ERROR_NO_SIM");
            }
            if (i == 3) {
                Log.d(
                        "WifiApAutoHotspotSettings",
                        "AutoHotspot feature can`t be used: RESULT_ERROR_NO_ACTIVE_NETWORK");
                wifiApAutoHotspotSettings.finish();
            } else if (i == 6) {
                Log.d(
                        "WifiApAutoHotspotSettings",
                        "AutoHotspot feature can`t be used: RESULT_ERROR_DISABLED_NEARBY_SCANNING");
                Toast.makeText(
                                wifiApAutoHotspotSettings.mContext,
                                R.string.smart_tethering_nearby_can_not_available,
                                1)
                        .show();
                wifiApAutoHotspotSettings.finish();
            } else {
                wifiApAutoHotspotSettings.updateAutoHotspotSection();
                Log.i("WifiApAutoHotspotSettings", "Updating Family Sharing section.");
                wifiApAutoHotspotSettings.mWifiApFamilySharingSwitchController.updateState(
                        wifiApAutoHotspotSettings.getPreferenceScreen());
            }
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.wifi_ap_smart_tethering_title;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApAutoHotspotSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3400;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_autohotspot_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        Log.i("WifiApAutoHotspotSettings", "onActivityCreated");
        super.onActivityCreated(bundle);
        WifiApFeatureUtils.isSamsungAccountFamilySupported();
        setAutoRemoveInsetCategory(false);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.show();
        }
        getListView().semSetRoundedCorners(15);
        getListView()
                .semSetRoundedCornerColor(
                        15,
                        getResources()
                                .getColor(
                                        R.color.sec_widget_round_and_bgcolor,
                                        this.mContext.getTheme()));
        WifiApAutoHotspotSwitchEnabler wifiApAutoHotspotSwitchEnabler =
                new WifiApAutoHotspotSwitchEnabler(this);
        this.mWifiApAutoHotspotSwitchEnabler = wifiApAutoHotspotSwitchEnabler;
        wifiApAutoHotspotSwitchEnabler.mOnStateChangeListener =
                this.mOnAutoHotspotStateChangeListener;
        this.mAutoHotspotDescriptionLayoutPreference.seslSetSubheaderRoundedBackground(0);
        ((TextView) this.mAutoHotspotDescriptionLayoutPreference.mRootView.findViewById(R.id.title))
                .setText(
                        WifiApUtils.getString(this.mContext, R.string.smart_tethering_description));
        updateAutoHotspotSection();
        Log.i("WifiApAutoHotspotSettings", "Updating Family Sharing section.");
        this.mWifiApFamilySharingSwitchController.updateState(getPreferenceScreen());
        this.mFamilySharingDescriptionLayoutPreference.seslSetSubheaderRoundedBackground(0);
        if (WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(this.mContext)) {
            ((TextView)
                            this.mFamilySharingDescriptionLayoutPreference.mRootView.findViewById(
                                    R.id.title))
                    .setText(
                            "Auto Hotspot only works with family members devices running on One UI"
                                + " 7 or later.");
        } else {
            this.mFamilySharingDescriptionLayoutPreference.setVisible(false);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult() - requestCode: ",
                ", resultCode(-1 for RESULT_OK) : ",
                i,
                i2,
                "WifiApAutoHotspotSettings");
        if (i != 500) {
            this.mWifiApFamilySharingSwitchController.onActivityResult(i, i2, intent);
        } else if (i2 == -1) {
            WifiApSmartTetheringApkUtils.startSmartTetheringApk(this.mContext, 1, null);
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
        WifiApFamilySharingSwitchController wifiApFamilySharingSwitchController =
                (WifiApFamilySharingSwitchController)
                        use(WifiApFamilySharingSwitchController.class);
        this.mWifiApFamilySharingSwitchController = wifiApFamilySharingSwitchController;
        wifiApFamilySharingSwitchController.setHost(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onSwitchChanged: ", "WifiApAutoHotspotSettings", z);
        if (!z) {
            WifiApSmartTetheringApkUtils.setFamilySharingSwitchChangedAutomatically(
                    this.mContext, WifiApFrameworkUtils.isFamilySharingSetOn(this.mContext));
        }
        this.mWifiApAutoHotspotSwitchEnabler.setChecked(z);
        if (WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext)
                && WifiApSmartTetheringApkUtils.isFamilySharingSwitchChangedAutomatically(
                        this.mContext)) {
            this.mWifiApFamilySharingSwitchController.setChecked(true);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("WifiApAutoHotspotSettings", "onCreate");
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mContext = activity;
        Context applicationContext = activity.getApplicationContext();
        this.mContext = applicationContext;
        this.mActivity.setTitle(
                WifiApUtils.getString(applicationContext, R.string.wifi_ap_smart_tethering_title));
        WifiApSmartTetheringApkUtils.launchFamilyServiceRegisterActivityForResult(this, 500);
        this.mAutoHotspotDescriptionLayoutPreference =
                (LayoutPreference) findPreference("auto_hotspot_description_layout_preference");
        this.mMyAccountPreferenceCategory =
                (SecPreferenceCategory) findPreference("my_account_preference_category");
        this.mFamilySharingDescriptionLayoutPreference =
                (LayoutPreference) findPreference("family_sharing_description_preference");
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        Log.i("WifiApAutoHotspotSettings", "onDestroyView");
        super.onDestroyView();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.i("WifiApAutoHotspotSettings", "onPause");
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.i("WifiApAutoHotspotSettings", "onResume");
        this.mWifiApAutoHotspotSwitchEnabler.updateSwitchState(false);
        Log.i("WifiApAutoHotspotSettings", "Updating Family Sharing section.");
        this.mWifiApFamilySharingSwitchController.updateState(getPreferenceScreen());
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.addOnSwitchChangeListener(this);
        }
    }

    public final void updateAutoHotspotSection() {
        Log.i("WifiApAutoHotspotSettings", "Updating AutoHotspot section.");
        WifiApFamilyMember familyOwner = WifiApSmartTetheringApkUtils.getFamilyOwner(this.mContext);
        WifiApPreference wifiApPreference = new WifiApPreference(getPrefContext());
        wifiApPreference.setTitle(familyOwner.mName);
        wifiApPreference.setSecondaryIcon(familyOwner.getPhoto());
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
        scaleType.getClass();
        if (wifiApPreference.mSecondaryIconScaleType != scaleType) {
            wifiApPreference.mSecondaryIconScaleType = scaleType;
            wifiApPreference.notifyChanged();
        }
        wifiApPreference.mPreferenceType = 3;
        wifiApPreference.notifyChanged();
        wifiApPreference.setKey(familyOwner.mGuid);
        wifiApPreference.setSummary(WifiApSettingsUtils.getSamsungAccount(this.mContext));
        wifiApPreference.mSecondaryIconWidthInPx =
                WifiApSettingsUtils.convertDpToPixel(wifiApPreference.mContext, 56);
        wifiApPreference.mSecondaryIconHeightInPx =
                WifiApSettingsUtils.convertDpToPixel(wifiApPreference.mContext, 56);
        wifiApPreference.notifyChanged();
        wifiApPreference.setOnPreferenceClickListener(new AnonymousClass1());
        if (this.mMyAccountPreferenceCategory.getPreferenceCount() <= 0) {
            this.mMyAccountPreferenceCategory.addPreference(wifiApPreference);
            return;
        }
        String key = wifiApPreference.getKey();
        if (key == null) {
            key = ApnSettings.MVNO_NONE;
        }
        Preference findPreference = this.mMyAccountPreferenceCategory.findPreference(key);
        Preference preference = this.mMyAccountPreferenceCategory.getPreference(0);
        if (findPreference != null
                && preference.getTitle().equals(wifiApPreference.getTitle())
                && preference.getKey().equals(wifiApPreference.getKey())) {
            return;
        }
        this.mMyAccountPreferenceCategory.removeAll();
        this.mMyAccountPreferenceCategory.addPreference(wifiApPreference);
    }
}
