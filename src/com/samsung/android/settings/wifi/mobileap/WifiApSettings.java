package com.samsung.android.settings.wifi.mobileap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.bixby.AppContext;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecTipLinkView;
import com.samsung.android.settings.wifi.mobileap.autohotspot.lite.WifiApLiteFamilySharingPreferenceController;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSettings;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApConfiguration;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.otp.WifiApOtpSwitchController;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApDescriptionPreference;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApSettings extends DashboardFragment implements OnActivityResultListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public static final IntentFilter WIFIAP_STATE_CHANGE_FILTER;
    public FragmentActivity mActivity;
    public WifiApDashBoardBandController mBandController;
    public Context mContext;
    public DataSaverBackend mDataSaverBackend;
    public WifiApDescriptionPreference mHelpDescriptionLayoutPreference;
    public LayoutPreference mHotspotDescriptionLayoutPreference;
    public long mLastConfigureButtonClickTime;
    public PreferenceCategory mMobileDataUsagePreferenceCategory;
    public WifiApDashboardNetworkNameController mNetworkNameController;
    public WifiApDashboardPasswordController mPasswordController;
    public PreferenceScreen mPreferenceScreen;
    public SemWifiManager mSemWifiManager;
    public WifiApSwitchEnabler mSwitchEnabler;
    public SecTipLinkView mTipLinkView;
    public WifiApAutoHotspotController mWifiApAutoHotspotController;
    public WifiApConfiguration mWifiApConfiguration;
    public PreferenceCategory mWifiApConnectedClientsPreferenceCategory;
    public WifiApDataSaver mWifiApDataSaver;
    public WifiApMobileDataSharedTodayPreferenceController
            mWifiApMobileDataSharedTodayPreferenceController;
    public WifiApOtpSwitchController mWifiApOtpSwitchController;
    public WifiApQrInfoBottomView mWifiApSettingQrInfoBottomView;
    public WifiApStateChangeReceiver mWifiApStateChangeReceiver;
    public SettingsMainSwitchBar switchBar = null;
    public boolean mDataUsageAllowed = true;
    public boolean mIsHotspotResetRequired = false;
    public RestrictionPolicy mRestrictionPolicy = null;
    public final AnonymousClass2 mSemWifiApDataUsageUpdateCallback =
            new SemWifiManager
                    .SemWifiApClientListUpdateCallback() { // from class:
                                                           // com.samsung.android.settings.wifi.mobileap.WifiApSettings.2
                public final void onClientListUpdated(List list, long j) {
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            WifiApSettings.SEARCH_INDEX_DATA_PROVIDER;
                    Log.i(
                            "WifiApSettings",
                            "onClientListUpdated() : "
                                    + list.size()
                                    + "totalDataUsageInBytes : "
                                    + j);
                    WifiApSettings.this.refreshConnectedPreferenceCategory(
                            WifiApConnectedDeviceUtils.getTopHotspotClientConfigListToday(
                                    WifiApSettings.this.mContext, list),
                            new WifiApDataUsageConfig(
                                    System.currentTimeMillis(), System.currentTimeMillis(), j));
                }

                public final void onOverallDataLimitChanged(long j) {
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            WifiApSettings.SEARCH_INDEX_DATA_PROVIDER;
                    Log.i("WifiApSettings", "onOverallDataLimitChanged() : " + j);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    WifiApSettings.SEARCH_INDEX_DATA_PROVIDER;
            Log.d("WifiApSettings", "getNonIndexableKeys");
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (WifiApSettingsUtils.isTetheringRestricted(context)
                    || !WifiApFeatureUtils.isOneTimePasswordSupported(context)
                    || WifiApSoftApUtils.getSoftApConfiguration(context).getSecurityType() == 1) {
                ((ArrayList) nonIndexableKeys).remove("guest_mode_switch_preference");
            } else {
                ((ArrayList) nonIndexableKeys).add("guest_mode_switch_preference");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            BaseSearchIndexProvider baseSearchIndexProvider =
                    WifiApSettings.SEARCH_INDEX_DATA_PROVIDER;
            Log.d("WifiApSettings", "getRawDataToIndex");
            if (!WifiApSettingsUtils.isTetheringRestricted(context)
                    && ((!Utils.isTablet() || !"ATT".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP))
                            && !com.android.settingslib.Utils.isWifiOnly(context)
                            && Utils.SPF_SupportMobileApEnhanced)) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                searchIndexableRaw.title =
                        String.valueOf(
                                WifiApUtils.getStringID(R.string.wifi_ap_smart_tethering_title));
                searchIndexableRaw.screenTitle =
                        resources.getString(WifiApUtils.getStringID(R.string.mobileap));
                ((SearchIndexableData) searchIndexableRaw).key =
                        "auto_hotspot_switch_preference_screen";
                arrayList.add(searchIndexableRaw);
            }
            if (!WifiApSettingsUtils.isTetheringRestricted(context)
                    && WifiApFeatureUtils.isOneTimePasswordSupported(context)) {
                SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
                searchIndexableRaw2.title = String.valueOf(R.string.wifi_ap_one_time_password);
                searchIndexableRaw2.screenTitle =
                        resources.getString(WifiApUtils.getStringID(R.string.mobileap));
                ((SearchIndexableData) searchIndexableRaw2).key = "guest_mode_switch_preference";
                arrayList.add(searchIndexableRaw2);
            }
            if (!WifiApSettingsUtils.isTetheringRestricted(context)) {
                SearchIndexableRaw searchIndexableRaw3 = new SearchIndexableRaw(context);
                searchIndexableRaw3.title = String.valueOf(R.string.wifi_ap_smart_priority_title);
                searchIndexableRaw3.screenTitle =
                        resources.getString(WifiApUtils.getStringID(R.string.mobileap));
                ((SearchIndexableData) searchIndexableRaw3).key =
                        "smart_priority_switch_preference";
                arrayList.add(searchIndexableRaw3);
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiApStateChangeReceiver extends BroadcastReceiver {
        public WifiApStateChangeReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BaseSearchIndexProvider baseSearchIndexProvider =
                    WifiApSettings.SEARCH_INDEX_DATA_PROVIDER;
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                    "updating display config due to receiving broadcast action ",
                    action,
                    "WifiApSettings",
                    WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra("wifi_state", 0);
                if (intExtra == 11) {
                    WifiApSettings.this.refreshPreferenceStates$1();
                    return;
                } else {
                    if (intExtra == 13) {
                        WifiApSettings.this.refreshPreferenceStates$1();
                        return;
                    }
                    return;
                }
            }
            if (!action.equals("android.intent.action.AIRPLANE_MODE")) {
                if (!action.equals("com.samsung.android.net.wifi.WIFI_AP_STA_STATE_CHANGED")
                        || WifiApFeatureUtils.isMobileDataUsageSupported(
                                WifiApSettings.this.mContext)) {
                    return;
                }
                WifiApSettings.this.refreshConnectedPreferenceCategory(null, null);
                return;
            }
            boolean z =
                    Settings.Global.getInt(
                                    WifiApSettings.this.mContext.getContentResolver(),
                                    "airplane_mode_on",
                                    0)
                            != 0;
            AbsAdapter$$ExternalSyntheticOutline0.m("Airplane mode changed:", "WifiApSettings", z);
            if (z) {
                Toast.makeText(WifiApSettings.this.mContext, R.string.flight_mode_enabled_toast, 0)
                        .show();
                if (Utils.isTablet()) {
                    WifiApSettings.this.finishFragment();
                } else {
                    WifiApSettings.this.getActivity().finish();
                }
            }
        }
    }

    static {
        StringBuilder sb = Utils.sBuilder;
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
        IntentFilter intentFilter =
                new IntentFilter("com.samsung.android.net.wifi.WIFI_AP_STA_STATE_CHANGED");
        WIFIAP_STATE_CHANGE_FILTER = intentFilter;
        intentFilter.addAction(
                WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED);
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.DATE_CHANGED");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.TIME_SET");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final AppContext getAppContext() {
        if (this.switchBar == null) {
            return null;
        }
        AppContext.Builder builder = new AppContext.Builder();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(
                "mobileHotspotEnabled",
                ((SeslSwitchBar) this.switchBar).mSwitch.isChecked() ? "true" : "false");
        jSONArray.put(jSONObject2);
        JSONArray jSONArray2 = builder.mLLMContext;
        jSONObject.put(
                "type",
                jSONArray.length() > 1
                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                "MobileHotspot", "[]")
                        : "MobileHotspot");
        int length = jSONArray.length();
        JSONObject jSONObject3 = jSONArray;
        if (length <= 1) {
            jSONObject3 = jSONObject2;
        }
        jSONObject.put("value", jSONObject3);
        jSONArray2.put(jSONObject);
        return builder.build();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        Log.i("WifiApSettings", "getFragmentTitleResId()");
        return Rune.isJapanModel() ? R.string.mobileap_jpn : R.string.mobileap;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3400;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_settings;
    }

    public final WifiApConfiguration getWifiApConfiguration() {
        if (this.mWifiApConfiguration == null) {
            updateWifiApConfiguration();
        }
        return this.mWifiApConfiguration;
    }

    public final void launchWifiApEditSettingsActivity(int i, String str) {
        Log.i(
                "WifiApSettings",
                "Launching WifiApEditSettings activity with title: " + this.mContext.getString(i));
        if (SystemClock.elapsedRealtime() - this.mLastConfigureButtonClickTime < 500) {
            Log.i("WifiApSettings", "Avoiding Launching WifiApEditSettings activity within 500ms");
            return;
        }
        this.mLastConfigureButtonClickTime = SystemClock.elapsedRealtime();
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("intent_key_focus_item", str);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 3400;
        launchRequest.mDestinationName = WifiApEditSettings.class.getCanonicalName();
        launchRequest.mArguments = m;
        subSettingLauncher.setTitleRes(i, null);
        subSettingLauncher.setResultListener(this, 4);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.d("WifiApSettings", "onActivityCreated ");
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        this.switchBar = settingsActivity.mMainSwitch;
        this.mSwitchEnabler = new WifiApSwitchEnabler(getActivity(), this.switchBar);
        getSettingsLifecycle().addObserver(this.mSwitchEnabler);
        this.switchBar.show();
        if (((!Utils.isTablet() && "TMO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP))
                        || "NEWCO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP))
                && settingsActivity.getActionBar() != null) {
            settingsActivity.getActionBar().setIcon(R.drawable.sec_wifi_ap_shortcut_mobileap);
        }
        Context context = this.mContext;
        WifiApQrInfoBottomView wifiApQrInfoBottomView = new WifiApQrInfoBottomView();
        ArrayList arrayList = new ArrayList();
        wifiApQrInfoBottomView.mMHSCustomerList = arrayList;
        Log.i("WifiApQrInfoBottomView", "WifiApQrInfoBottomView() - Start");
        wifiApQrInfoBottomView.mContext = context;
        wifiApQrInfoBottomView.mActivity = (Activity) context;
        wifiApQrInfoBottomView.mPrimaryTextColor = WifiApSettingsUtils.removeAlphaColor(context);
        wifiApQrInfoBottomView.initBottomBar();
        wifiApQrInfoBottomView.initBottomNavigation();
        wifiApQrInfoBottomView.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        AccessibilitySettings$$ExternalSyntheticOutline0.m(arrayList, "TMO", "VZW", "MTR", "ATT");
        arrayList.add("SPRINT");
        this.mWifiApSettingQrInfoBottomView = wifiApQrInfoBottomView;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "onActivityResult() hit - requestCode: ",
                ", resultCode: ",
                i,
                i2,
                "WifiApSettings");
        if (i == 4 && i2 == -1) {
            this.mIsHotspotResetRequired =
                    intent.getBooleanExtra("is_hotspot_rest_happened", false);
            SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "onActivityResult() hit - is reset required after resuming activity :"
                                + " "),
                    this.mIsHotspotResetRequired,
                    "WifiApSettings");
        } else if (i == 600) {
            WifiApAutoHotspotController wifiApAutoHotspotController =
                    this.mWifiApAutoHotspotController;
            if (wifiApAutoHotspotController != null) {
                wifiApAutoHotspotController.onActivityResult(i, i2, intent);
            }
        } else if (i == 610) {
            Log.e("WifiApSettings", "Cannot create Family sharing group from here.");
        } else {
            this.mSwitchEnabler.onActivityResult(i, i2, intent);
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
        this.mContext = context;
        ((WifiApLiteFamilySharingPreferenceController)
                        use(WifiApLiteFamilySharingPreferenceController.class))
                .setHost(this);
        this.mWifiApAutoHotspotController =
                (WifiApAutoHotspotController) use(WifiApAutoHotspotController.class);
        ((WifiApAutoHotspotController) use(WifiApAutoHotspotController.class)).setHost(this);
        WifiApOtpSwitchController wifiApOtpSwitchController =
                (WifiApOtpSwitchController) use(WifiApOtpSwitchController.class);
        this.mWifiApOtpSwitchController = wifiApOtpSwitchController;
        wifiApOtpSwitchController.setHost(this);
        this.mWifiApStateChangeReceiver = new WifiApStateChangeReceiver();
        ((WifiApMobileDataSharedTodayPreferenceController)
                        use(WifiApMobileDataSharedTodayPreferenceController.class))
                .setHost(this);
        this.mWifiApMobileDataSharedTodayPreferenceController =
                (WifiApMobileDataSharedTodayPreferenceController)
                        use(WifiApMobileDataSharedTodayPreferenceController.class);
        ((WifiApDashboardPasswordController) use(WifiApDashboardPasswordController.class))
                .setHost(this);
        this.mPasswordController =
                (WifiApDashboardPasswordController) use(WifiApDashboardPasswordController.class);
        ((WifiApDashBoardBandController) use(WifiApDashBoardBandController.class)).setHost(this);
        this.mBandController =
                (WifiApDashBoardBandController) use(WifiApDashBoardBandController.class);
        ((WifiApDashboardNetworkNameController) use(WifiApDashboardNetworkNameController.class))
                .setHost(this);
        this.mNetworkNameController =
                (WifiApDashboardNetworkNameController)
                        use(WifiApDashboardNetworkNameController.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        Log.i("WifiApSettings", "onCreate()");
        Utils.initMHSFeature(getContext());
        getActivity().setTitle(WifiApUtils.getStringID(R.string.mobileap));
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mSemWifiManager =
                (SemWifiManager) activity.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mWifiApConfiguration = new WifiApConfiguration(this.mContext);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mPreferenceScreen = preferenceScreen;
        this.mHelpDescriptionLayoutPreference =
                (WifiApDescriptionPreference)
                        preferenceScreen.findPreference("hotspot_help_description_preference");
        LayoutPreference layoutPreference =
                (LayoutPreference)
                        this.mPreferenceScreen.findPreference(
                                "hotspot_description_layout_preference");
        this.mHotspotDescriptionLayoutPreference = layoutPreference;
        layoutPreference.seslSetSubheaderRoundedBackground(0);
        setHelpTextDescription();
        String string = WifiApUtils.getString(this.mContext, R.string.wifi_ap_enable_help_text);
        Log.d(
                "WifiApSettings",
                "setHotspotHelpDescriptionLayoutPreferenceText: wifiApEnableHelpTextLength"
                        + string.length());
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(string, " ");
        m.append(getString(R.string.learn_more));
        SpannableString spannableString = new SpannableString(m.toString());
        spannableString.setSpan(
                new ClickableSpan() { // from class:
                                      // com.samsung.android.settings.wifi.mobileap.WifiApSettings.3
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        WifiApSettings.this.mWifiApSettingQrInfoBottomView.showInfoDialog();
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public final void updateDrawState(TextPaint textPaint) {
                        super.updateDrawState(textPaint);
                        textPaint.setUnderlineText(true);
                        textPaint.setColor(
                                WifiApSettings.this.mContext.getColor(
                                        R.color.sec_widget_body_text_color));
                    }
                },
                string.length() + 1,
                spannableString.length(),
                33);
        this.mHelpDescriptionLayoutPreference.setTitle(spannableString);
        WifiApDescriptionPreference wifiApDescriptionPreference =
                this.mHelpDescriptionLayoutPreference;
        MovementMethod linkMovementMethod = LinkMovementMethod.getInstance();
        wifiApDescriptionPreference.getClass();
        Log.i("WifiApPreference", "setMovementMethodForTitle() - " + linkMovementMethod);
        wifiApDescriptionPreference.mMovementMethodForTitle = linkMovementMethod;
        wifiApDescriptionPreference.notifyChanged();
        this.mHelpDescriptionLayoutPreference.setPreferenceColor(3);
        this.mHelpDescriptionLayoutPreference.setPaddingInDp(0, 16);
        this.mWifiApConnectedClientsPreferenceCategory =
                (PreferenceCategory)
                        this.mPreferenceScreen.findPreference(
                                "connected_clients_preference_category");
        this.mMobileDataUsagePreferenceCategory =
                (PreferenceCategory)
                        this.mPreferenceScreen.findPreference(
                                "mobile_data_shared_preference_category");
        this.mPreferenceScreen.findPreference("dummy_end_preference_category");
        boolean z = UserHandle.myUserId() != 0;
        try {
            str =
                    this.mContext
                            .getPackageManager()
                            .getApplicationInfo("com.samsung.android.mdx", 128)
                            .metaData
                            .getString("LinkToWindowsRelativeLinkActivity");
            if (TextUtils.isEmpty(str)) {
                Log.e("WifiApSettings", "getActivityName: Landing activity is null or empty.");
            } else {
                Log.d("WifiApSettings", "getActivityName: retrieved landing page = " + str);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("WifiApSettings", "getActivityName: Exception = " + e.getMessage());
            str = null;
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            this.mContext.getPackageManager().getPackageInfo("com.samsung.android.mdx", 0);
            if (z
                    || Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "ltw_instant_hotspot_tip_enabled",
                                    0)
                            != 1) {
                return;
            }
            if (this.mTipLinkView == null) {
                FragmentActivity activity2 = getActivity();
                SecTipLinkView secTipLinkView = new SecTipLinkView(activity2, null, 0);
                secTipLinkView.mContext = activity2;
                secTipLinkView.mParentView =
                        LayoutInflater.from(activity2)
                                .inflate(R.layout.sec_widget_tip_link_footerview, secTipLinkView);
                secTipLinkView.mParentView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                secTipLinkView.mTipContainer =
                        (LinearLayout) secTipLinkView.mParentView.findViewById(R.id.tip_container);
                this.mTipLinkView = secTipLinkView;
            }
            this.mTipLinkView.mTipContainer.removeAllViews();
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData.topLevelKey = "top_level_wifi_settings";
            settingsPreferenceFragmentLinkData.titleRes = R.string.wifi_ap_instant_hotspot_tip_desc;
            this.mTipLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                    new SettingsPreferenceFragmentLinkData();
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.mdx", str);
            intent.putExtra("flowId", "9030");
            intent.setFlags(335544320);
            settingsPreferenceFragmentLinkData2.intent = intent;
            settingsPreferenceFragmentLinkData2.topLevelKey = "top_level_wifi_settings";
            settingsPreferenceFragmentLinkData2.titleRes = R.string.link_to_windows_title;
            this.mTipLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
            if (getFooterView() == null) {
                SecTipLinkView secTipLinkView2 = this.mTipLinkView;
                LinearLayout linearLayout = secTipLinkView2.mTipContainer;
                if (linearLayout == null || linearLayout.getChildCount() > 0) {
                    setFooterView(secTipLinkView2.mParentView, true);
                } else {
                    Log.d("SecTipLinkView", "The current screen doesn't have link data.");
                }
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("WifiApSettings", "checkTargetAppInstalled: Exception = " + e2.getMessage());
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        Log.i("WifiApSettings", "onCreateDialog() - dialogId" + i);
        return super.onCreateDialog(i);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        Log.i("WifiApSettings", "onDestroy");
        super.onDestroy();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        Log.d("WifiApSettings", "onDestroyView");
        getSettingsLifecycle().removeObserver(this.mSwitchEnabler);
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.hide();
        }
        super.onDestroyView();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Log.i("WifiApSettings", "Navigate Up clicked");
            SALogging.insertSALog("TETH_010", "8101");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.i("WifiApSettings", "onPause");
        if (WifiApFeatureUtils.isMobileDataUsageSupported(this.mContext)) {
            this.mSemWifiManager.unregisterClientListDataUsageCallback(
                    this.mSemWifiApDataUsageUpdateCallback);
        }
        WifiApFrameworkUtils.setIsHotspotScreenOpened(this.mContext, false);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Log.i("WifiApSettings", "onResume");
        super.onResume();
        if (WifiApFeatureUtils.isMobileDataUsageSupported(this.mContext)) {
            this.mSemWifiManager.registerClientListDataUsageCallback(
                    this.mSemWifiApDataUsageUpdateCallback, this.mContext.getMainExecutor(), 3, 3);
        }
        refreshPreferenceStates$1();
        if (this.mIsHotspotResetRequired) {
            this.mIsHotspotResetRequired = false;
            WifiApFrameworkUtils.resetSoftAp(this.mContext);
        }
        this.mWifiApOtpSwitchController.updateState(getPreferenceScreen());
        WifiApFrameworkUtils.setIsHotspotScreenOpened(this.mContext, true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        Log.i("WifiApSettings", "onStart");
        super.onStart();
        WifiApSwitchEnabler wifiApSwitchEnabler = this.mSwitchEnabler;
        wifiApSwitchEnabler.getClass();
        Log.i(WifiApSwitchEnabler.TAG, "addSwitchChangeListener");
        wifiApSwitchEnabler.mSwitchBar.addOnSwitchChangeListener(wifiApSwitchEnabler);
        this.mContext.registerReceiver(
                this.mWifiApStateChangeReceiver, WIFIAP_STATE_CHANGE_FILTER, 2);
        DataSaverBackend dataSaverBackend = new DataSaverBackend(this.mContext);
        this.mDataSaverBackend = dataSaverBackend;
        if (dataSaverBackend.mPolicyManager.getRestrictBackground()) {
            finish();
        }
        WifiApDataSaver wifiApDataSaver = new WifiApDataSaver();
        this.mWifiApDataSaver = wifiApDataSaver;
        this.mDataSaverBackend.addListener(wifiApDataSaver);
        this.mDataUsageAllowed = DataUsageUtils.isDataAllowed(this.mContext);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("mDataAllowed : "), this.mDataUsageAllowed, "WifiApSettings");
        if (!this.mDataUsageAllowed) {
            finish();
        }
        if (((UserManager) getSystemService("user")).hasUserRestriction("no_config_tethering")
                || UserHandle.myUserId() != 0) {
            Log.i(
                    "WifiApSettings",
                    "onStart: DISALLOW_CONFIG_TETHERING > this activity is finished.");
            if (Utils.isTablet()) {
                finishFragment();
            } else {
                getActivity().finish();
            }
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0)
                != 0) {
            Toast.makeText(this.mContext, R.string.flight_mode_enabled_toast, 0).show();
            if (Utils.isTablet()) {
                finishFragment();
            } else {
                getActivity().finish();
            }
        }
        if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableSprintExtension")) {
            if (this.mRestrictionPolicy == null) {
                this.mRestrictionPolicy =
                        EnterpriseDeviceManager.getInstance(this.mContext.getApplicationContext())
                                .getRestrictionPolicy();
            }
            RestrictionPolicy restrictionPolicy = this.mRestrictionPolicy;
            if (restrictionPolicy == null || restrictionPolicy.isWifiTetheringEnabled()) {
                return;
            }
            Log.i(
                    "WifiApSettings",
                    "onStart: isWifiTetheringEnabled false EDM, this activity is finished.");
            finish();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        Log.i("WifiApSettings", "onStop");
        WifiApSwitchEnabler wifiApSwitchEnabler = this.mSwitchEnabler;
        wifiApSwitchEnabler.getClass();
        Log.i(WifiApSwitchEnabler.TAG, "removeSwitchChangeListener");
        wifiApSwitchEnabler.mSwitchBar.removeOnSwitchChangeListener(wifiApSwitchEnabler);
        this.mContext.unregisterReceiver(this.mWifiApStateChangeReceiver);
        WifiApDataSaver wifiApDataSaver = this.mWifiApDataSaver;
        if (wifiApDataSaver != null) {
            this.mDataSaverBackend.remListener(wifiApDataSaver);
            this.mWifiApDataSaver = null;
            this.mDataSaverBackend = null;
        }
        super.onStop();
    }

    public final void refreshConnectedPreferenceCategory(
            List list, WifiApDataUsageConfig wifiApDataUsageConfig) {
        if (WifiApFeatureUtils.isMobileDataUsageSupported(this.mContext)) {
            if (list == null) {
                this.mWifiApMobileDataSharedTodayPreferenceController.updateState(
                        getPreferenceScreen());
            } else {
                this.mWifiApMobileDataSharedTodayPreferenceController.updateState(
                        list, wifiApDataUsageConfig);
            }
            this.mMobileDataUsagePreferenceCategory.setVisible(true);
            this.mHotspotDescriptionLayoutPreference.setVisible(false);
            this.mHelpDescriptionLayoutPreference.setVisible(
                    WifiApFrameworkUtils.isWifiApStateEnabled(this.mContext));
            return;
        }
        this.mMobileDataUsagePreferenceCategory.setVisible(false);
        this.mHotspotDescriptionLayoutPreference.setVisible(true);
        this.mHelpDescriptionLayoutPreference.setVisible(false);
        if (!WifiApFrameworkUtils.isWifiApStateEnabled(this.mContext)) {
            this.mWifiApConnectedClientsPreferenceCategory.setVisible(false);
            return;
        }
        this.mWifiApConnectedClientsPreferenceCategory.setVisible(true);
        this.mWifiApConnectedClientsPreferenceCategory.removeAll();
        List wifiApStaListDetail =
                WifiApFrameworkUtils.getSemWifiManager(this.mContext).getWifiApStaListDetail();
        int size = wifiApStaListDetail != null ? wifiApStaListDetail.size() : 0;
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                size, "mConnectedDevices.size():", "WifiApSettings");
        if (size == 0) {
            Preference preference = new Preference(this.mContext);
            preference.setSelectable(false);
            preference.setTitle(R.string.wifi_ap_no_devices);
            this.mWifiApConnectedClientsPreferenceCategory.addPreference(preference);
            return;
        }
        for (int i = 0; i < size; i++) {
            String[] split = ((String) wifiApStaListDetail.get(i)).split("\n");
            String str = split[2];
            if (TextUtils.isEmpty(str) || str.equals("(null)")) {
                str = this.mContext.getResources().getString(R.string.wifi_ap_connected_device);
            }
            Context context = this.mContext;
            String str2 = split[0];
            String str3 = split[1];
            long parseLong = Long.parseLong(split[3]);
            WifiApConnectedClient wifiApConnectedClient = new WifiApConnectedClient(context);
            wifiApConnectedClient.dateFormat = null;
            wifiApConnectedClient.mContext = context;
            wifiApConnectedClient.mDeviceName = str;
            wifiApConnectedClient.mMac = str2;
            wifiApConnectedClient.mIp = str3;
            wifiApConnectedClient.mConnectedTime = Long.valueOf(parseLong);
            wifiApConnectedClient.setKey(str2);
            wifiApConnectedClient.setTitle(str);
            wifiApConnectedClient.mSecondaryIconClickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.mobileap.WifiApConnectedClient.1
                        public AnonymousClass1() {}

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString(
                                    "intent_key_connected_client_name_detail",
                                    WifiApConnectedClient.this.mDeviceName);
                            bundle.putString(
                                    "intent_key_connected_client_mac_detail",
                                    WifiApConnectedClient.this.mMac);
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(WifiApConnectedClient.this.mContext);
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mSourceMetricsCategory = 3400;
                            launchRequest.mArguments = bundle;
                            launchRequest.mDestinationName =
                                    WifiApClientSettings.class.getCanonicalName();
                            subSettingLauncher.launch();
                        }
                    };
            wifiApConnectedClient.notifyChanged();
            String format = DateFormat.getTimeFormat(context).format(new Date(parseLong));
            wifiApConnectedClient.dateFormat =
                    DateFormat.format(
                                    DateFormat.getBestDateTimePattern(
                                            Locale.getDefault(), "MMM dd"),
                                    new Date(parseLong))
                            .toString();
            wifiApConnectedClient.setSummary(
                    wifiApConnectedClient.dateFormat
                            + context.getString(R.string.comma)
                            + " "
                            + format);
            wifiApConnectedClient.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.samsung.android.settings.wifi.mobileap.WifiApConnectedClient.2
                        public AnonymousClass2() {}

                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference2) {
                            SALogging.insertSALog("TETH_010", "8026");
                            int i2 = WifiApConnectedClient.$r8$clinit;
                            WifiApConnectedClient wifiApConnectedClient2 =
                                    WifiApConnectedClient.this;
                            wifiApConnectedClient2.getClass();
                            Context context2 = wifiApConnectedClient2.mContext;
                            boolean z = WifiApSettingsUtils.DBG;
                            AlertDialog.Builder builder = new AlertDialog.Builder(context2, 0);
                            wifiApConnectedClient2.customView =
                                    builder.create()
                                            .getLayoutInflater()
                                            .inflate(
                                                    R.layout.sec_wifi_ap_deviceinfo_dialog,
                                                    (ViewGroup) null);
                            if (Utils.isRTL(wifiApConnectedClient2.mContext)) {
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.tv_ip_addr_text))
                                        .setText(
                                                wifiApConnectedClient2.mContext.getString(
                                                                R.string.wifi_ip_address)
                                                        + "\u200f");
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.wifi_ap_ip))
                                        .setText("\u200e" + wifiApConnectedClient2.mIp);
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.tv_mac_addr_text))
                                        .setText(
                                                wifiApConnectedClient2.mContext.getString(
                                                                R.string.wifi_ap_mac)
                                                        + "\u200f");
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.wifi_ap_mac))
                                        .setText(
                                                "\u200e"
                                                        + wifiApConnectedClient2.mMac
                                                                .toUpperCase());
                            } else {
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.tv_ip_addr_text))
                                        .setText(
                                                wifiApConnectedClient2.mContext.getString(
                                                        R.string.wifi_ip_address));
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.wifi_ap_ip))
                                        .setText(wifiApConnectedClient2.mIp);
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.tv_mac_addr_text))
                                        .setText(
                                                wifiApConnectedClient2.mContext.getString(
                                                        R.string.wifi_ap_mac));
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.wifi_ap_mac))
                                        .setText(wifiApConnectedClient2.mMac.toUpperCase());
                            }
                            String format2 =
                                    DateFormat.getTimeFormat(wifiApConnectedClient2.mContext)
                                            .format(
                                                    new Date(
                                                            wifiApConnectedClient2.mConnectedTime
                                                                    .longValue()));
                            wifiApConnectedClient2.dateFormat =
                                    DateFormat.getDateFormat(wifiApConnectedClient2.mContext)
                                            .format(
                                                    new Date(
                                                            wifiApConnectedClient2.mConnectedTime
                                                                    .longValue()));
                            String m =
                                    BackStackRecord$$ExternalSyntheticOutline0.m(
                                            new StringBuilder(),
                                            wifiApConnectedClient2.dateFormat,
                                            " ",
                                            format2);
                            ((TextView)
                                            wifiApConnectedClient2.customView.findViewById(
                                                    R.id.wifi_ap_connection_time))
                                    .setText(m);
                            if (Utils.isRTL(wifiApConnectedClient2.mContext)) {
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.tv_ip_addr_text))
                                        .setGravity(5);
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.tv_mac_addr_text))
                                        .setGravity(5);
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.wifi_ap_connection_time))
                                        .setText(m);
                                ((TextView)
                                                wifiApConnectedClient2.customView.findViewById(
                                                        R.id.wifi_ap_connection_time))
                                        .setGravity(5);
                            }
                            TextView textView =
                                    (TextView)
                                            wifiApConnectedClient2.customView.findViewById(
                                                    R.id.wifi_ap_connection_duration);
                            long currentTimeMillis =
                                    (System.currentTimeMillis()
                                                    - wifiApConnectedClient2.mConnectedTime
                                                            .longValue())
                                            / 1000;
                            long j = currentTimeMillis / 3600;
                            long j2 = currentTimeMillis % 3600;
                            textView.setText(
                                    ApnSettings.MVNO_NONE
                                            + String.format("%02d", Long.valueOf(j))
                                            + ":"
                                            + String.format("%02d", Long.valueOf(j2 / 60))
                                            + ":"
                                            + String.format("%02d", Long.valueOf(j2 % 60)));
                            builder.setTitle(wifiApConnectedClient2.mDeviceName);
                            builder.setView(wifiApConnectedClient2.customView);
                            builder.setPositiveButton(R.string.dlg_ok, new AnonymousClass3())
                                    .show();
                            return true;
                        }
                    });
            this.mWifiApConnectedClientsPreferenceCategory.addPreference(wifiApConnectedClient);
        }
    }

    public final void refreshPreferenceStates$1() {
        Log.i("WifiApSettings", "refreshPreferenceStates()");
        setHelpTextDescription();
        updateWifiApConfiguration();
        this.mNetworkNameController.updateState(getPreferenceScreen());
        this.mPasswordController.updateState(getPreferenceScreen());
        this.mBandController.updateState(getPreferenceScreen());
        refreshConnectedPreferenceCategory(null, null);
        WifiApQrInfoBottomView wifiApQrInfoBottomView = this.mWifiApSettingQrInfoBottomView;
        wifiApQrInfoBottomView.initBottomBar();
        wifiApQrInfoBottomView.initBottomNavigation();
        int wifiApState = wifiApQrInfoBottomView.mSemWifiManager.getWifiApState();
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                wifiApState, "refresh() - wifiApState: ", "WifiApQrInfoBottomView");
        if (wifiApState == 13) {
            wifiApQrInfoBottomView.mButtonBar.setVisibility(0);
        } else {
            wifiApQrInfoBottomView.mButtonBar.setVisibility(8);
        }
    }

    public final void setHelpTextDescription() {
        String quantityString;
        TextView textView =
                (TextView)
                        this.mHotspotDescriptionLayoutPreference.mRootView.findViewById(R.id.title);
        if (WifiApFrameworkUtils.isWifiApStateEnabled(this.mContext)) {
            textView.setText(
                    WifiApUtils.getString(this.mContext, R.string.wifi_ap_enable_help_text));
            return;
        }
        int wifiApMaxClientFromFramework =
                WifiApFrameworkUtils.getWifiApMaxClientFromFramework(this.mContext);
        if (Utils.SUPPORT_MOBILEAP_WIFISHARING) {
            quantityString =
                    getContext()
                            .getResources()
                            .getQuantityString(
                                    Utils.isTablet()
                                            ? R.plurals
                                                    .wifi_ap_instruction_changeable_maxclient_plurals_tablet
                                            : R.plurals
                                                    .wifi_ap_instruction_changeable_maxclient_plurals_beyond,
                                    wifiApMaxClientFromFramework,
                                    Integer.valueOf(wifiApMaxClientFromFramework));
        } else {
            quantityString =
                    getContext()
                            .getResources()
                            .getQuantityString(
                                    Utils.isTablet()
                                            ? R.plurals
                                                    .wifi_ap_instruction_changeable_maxclient_plurals_no_wifisharing_tablet
                                            : R.plurals
                                                    .wifi_ap_instruction_changeable_maxclient_plurals_no_wifisharing,
                                    wifiApMaxClientFromFramework,
                                    Integer.valueOf(wifiApMaxClientFromFramework));
        }
        textView.setText(quantityString);
    }

    public final void updateWifiApConfiguration() {
        this.mWifiApConfiguration = new WifiApConfiguration(this.mContext);
        Log.i(
                "WifiApSettings",
                "updated WifiApConfiguration: " + this.mWifiApConfiguration.toString());
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiApDataSaver implements DataSaverBackend.Listener {
        public WifiApDataSaver() {}

        @Override // com.android.settings.datausage.DataSaverBackend.Listener
        public final void onDataSaverChanged(boolean z) {
            if (z) {
                BaseSearchIndexProvider baseSearchIndexProvider =
                        WifiApSettings.SEARCH_INDEX_DATA_PROVIDER;
                Log.e("WifiApSettings", "Data saver is enabled");
                WifiApSettings.this.finish();
            }
        }

        @Override // com.android.settings.datausage.DataSaverBackend.Listener
        public final void onAllowlistStatusChanged(int i, boolean z) {}

        @Override // com.android.settings.datausage.DataSaverBackend.Listener
        public final void onDenylistStatusChanged(int i, boolean z) {}
    }
}
