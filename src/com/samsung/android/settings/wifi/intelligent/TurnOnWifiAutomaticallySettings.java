package com.samsung.android.settings.wifi.intelligent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class TurnOnWifiAutomaticallySettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public int mCurrentConnectedNetworkId;
    public PreferenceCategory mFavoriteApCandidateCategory;
    public PreferenceCategory mFavoriteApCategory;
    public IntentFilter mIntentFilter;
    public SemWifiManager mSemWifiManager;
    public SettingsMainSwitchBar mSwitchBar;
    public TelephonyManager mTelephonyManager;
    public WifiManager mWifiManager;
    public final List mCombinedWifiConfigList = new ArrayList();
    public boolean mIsAutoWifiEnabed = false;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.intelligent.TurnOnWifiAutomaticallySettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)
                            || "android.intent.action.SIM_STATE_CHANGED".equals(action)
                            || "android.location.MODE_CHANGED".equals(action)) {
                        TurnOnWifiAutomaticallySettings turnOnWifiAutomaticallySettings =
                                TurnOnWifiAutomaticallySettings.this;
                        if (turnOnWifiAutomaticallySettings.mSwitchBar != null) {
                            turnOnWifiAutomaticallySettings.initSwitchBar$1();
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutoWifiCandidatePreference extends Preference {
        public ImageView mAddFavoriteAp;
        public final WifiConfiguration mWifiConfig;

        public AutoWifiCandidatePreference(Context context, WifiConfiguration wifiConfiguration) {
            super(context);
            this.mWifiConfig = wifiConfiguration;
            setLayoutResource(R.layout.sec_preference_auto_wifi_candidate);
        }

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            this.mAddFavoriteAp =
                    (ImageView) preferenceViewHolder.findViewById(R.id.add_favorite_ap);
            if (isEnabled()) {
                this.mAddFavoriteAp.clearColorFilter();
            } else {
                this.mAddFavoriteAp.setColorFilter(
                        TurnOnWifiAutomaticallySettings.this
                                .getResources()
                                .getColor(R.color.sec_wifi_icon_gray_color));
            }
            WifiConfiguration wifiConfiguration = this.mWifiConfig;
            if (wifiConfiguration != null) {
                TurnOnWifiAutomaticallySettings turnOnWifiAutomaticallySettings =
                        TurnOnWifiAutomaticallySettings.this;
                if (turnOnWifiAutomaticallySettings.mCombinedWifiConfigList != null) {
                    final SemWifiConfiguration m1345$$Nest$mgetSemWifiConfigFromCombinedList =
                            TurnOnWifiAutomaticallySettings
                                    .m1345$$Nest$mgetSemWifiConfigFromCombinedList(
                                            turnOnWifiAutomaticallySettings, wifiConfiguration);
                    if (m1345$$Nest$mgetSemWifiConfigFromCombinedList != null) {
                        this.mAddFavoriteAp.setOnClickListener(
                                new View
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.settings.wifi.intelligent.TurnOnWifiAutomaticallySettings$AutoWifiCandidatePreference$$ExternalSyntheticLambda0
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i;
                                        TurnOnWifiAutomaticallySettings.AutoWifiCandidatePreference
                                                autoWifiCandidatePreference =
                                                        TurnOnWifiAutomaticallySettings
                                                                .AutoWifiCandidatePreference.this;
                                        SemWifiConfiguration semWifiConfiguration =
                                                m1345$$Nest$mgetSemWifiConfigFromCombinedList;
                                        autoWifiCandidatePreference.getClass();
                                        HashMap hashMap = new HashMap();
                                        hashMap.put(
                                                "sem_score",
                                                String.valueOf(semWifiConfiguration.networkScore));
                                        WifiConfiguration wifiConfiguration2 =
                                                autoWifiCandidatePreference.mWifiConfig;
                                        if (wifiConfiguration2.allowedKeyManagement.get(8)) {
                                            i = 5;
                                        } else {
                                            int i2 = 2;
                                            if (!wifiConfiguration2.allowedKeyManagement.get(1)) {
                                                if (wifiConfiguration2.allowedKeyManagement.get(
                                                        10)) {
                                                    i = 6;
                                                } else {
                                                    i2 = 3;
                                                    if (!wifiConfiguration2.allowedKeyManagement
                                                                    .get(2)
                                                            && !wifiConfiguration2
                                                                    .allowedKeyManagement.get(3)) {
                                                        if (wifiConfiguration2.allowedKeyManagement
                                                                .get(9)) {
                                                            i = 4;
                                                        } else {
                                                            int i3 =
                                                                    wifiConfiguration2
                                                                            .wepTxKeyIndex;
                                                            if (i3 >= 0) {
                                                                String[] strArr =
                                                                        wifiConfiguration2.wepKeys;
                                                                if (i3 < strArr.length
                                                                        && strArr[i3] != null) {
                                                                    i = 1;
                                                                }
                                                            }
                                                            i = 0;
                                                        }
                                                    }
                                                }
                                            }
                                            i = i2;
                                        }
                                        hashMap.put("security_type", String.valueOf(i));
                                        SALogging.insertSALog("WIFI_270", "1286", hashMap, 0);
                                        TurnOnWifiAutomaticallySettings
                                                .m1346$$Nest$mupdateNetworkScore(
                                                        TurnOnWifiAutomaticallySettings.this,
                                                        11,
                                                        autoWifiCandidatePreference
                                                                .mWifiConfig
                                                                .networkId);
                                        TurnOnWifiAutomaticallySettings.this.setFavoriteApList();
                                        TurnOnWifiAutomaticallySettings.this
                                                .mFavoriteApCandidateCategory.removePreference(
                                                autoWifiCandidatePreference);
                                    }
                                });
                        return;
                    } else {
                        Log.d("TurnOnWifiAutomaticallySettings", "Candidate's semConfig is NULL");
                        return;
                    }
                }
            }
            Log.d(
                    "TurnOnWifiAutomaticallySettings",
                    "Candidate's WifiConfig or CombinedWifiConfigList is NULL");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutoWifiPreference extends SecSwitchPreference {
        public final SemWifiConfiguration mSemWifiConfig;
        public final WifiConfiguration mWifiConfig;

        public AutoWifiPreference(Context context, WifiConfiguration wifiConfiguration) {
            super(context);
            JSONArray jSONArray;
            this.mWifiConfig = wifiConfiguration;
            if (wifiConfiguration == null
                    || TurnOnWifiAutomaticallySettings.this.mCombinedWifiConfigList == null) {
                Log.d(
                        "TurnOnWifiAutomaticallySettings",
                        "WifiConfig or CombinedWifiConfigList is null");
                return;
            }
            SemWifiConfiguration m1345$$Nest$mgetSemWifiConfigFromCombinedList =
                    TurnOnWifiAutomaticallySettings.m1345$$Nest$mgetSemWifiConfigFromCombinedList(
                            TurnOnWifiAutomaticallySettings.this, wifiConfiguration);
            this.mSemWifiConfig = m1345$$Nest$mgetSemWifiConfigFromCombinedList;
            boolean z = true;
            if (m1345$$Nest$mgetSemWifiConfigFromCombinedList != null) {
                onSetInitialValue(
                        Boolean.valueOf(
                                m1345$$Nest$mgetSemWifiConfigFromCombinedList.getNetworkScore()
                                        >= 9));
            } else {
                Log.d("TurnOnWifiAutomaticallySettings", "Cannot init switch - semConfig is null");
            }
            try {
                jSONArray = WifiBadgeUtils.getBadgeJSONObject(context).getJSONArray("added");
            } catch (JSONException unused) {
                jSONArray = null;
            }
            WifiBadgeUtils.favoriteNetworkArray = jSONArray == null ? new JSONArray() : jSONArray;
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < WifiBadgeUtils.favoriteNetworkArray.length(); i++) {
                try {
                    arrayList.add(
                            WifiBadgeUtils.favoriteNetworkArray
                                    .getJSONObject(i)
                                    .getString(FieldName.CONFIG));
                } catch (JSONException unused2) {
                }
            }
            Iterator it = arrayList.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (wifiConfiguration.getKey().equals((String) it.next())) {
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            setDotVisibility(z);
        }

        @Override // androidx.preference.TwoStatePreference
        public final void setChecked(boolean z) {
            SemWifiConfiguration semWifiConfiguration;
            super.setChecked(z);
            if (!z && this.mSemWifiConfig != null) {
                HashMap hashMap = new HashMap();
                TurnOnWifiAutomaticallySettings turnOnWifiAutomaticallySettings =
                        TurnOnWifiAutomaticallySettings.this;
                int i = this.mSemWifiConfig.networkScore;
                int i2 = TurnOnWifiAutomaticallySettings.$r8$clinit;
                turnOnWifiAutomaticallySettings.getClass();
                hashMap.put("registrant", i == 11 ? "MANUAL" : i == 12 ? "KT" : "AUTO");
                SALogging.insertSALog("WIFI_200", "1287", hashMap, 0);
            }
            WifiConfiguration wifiConfiguration = this.mWifiConfig;
            if (wifiConfiguration == null || (semWifiConfiguration = this.mSemWifiConfig) == null) {
                Log.d(
                        "TurnOnWifiAutomaticallySettings",
                        "failed to updateScore - SemWifiConfig is null");
                return;
            }
            int i3 = semWifiConfiguration.networkScore;
            if (!(z && i3 == -1) && (z || i3 < 9)) {
                return;
            }
            TurnOnWifiAutomaticallySettings.m1346$$Nest$mupdateNetworkScore(
                    TurnOnWifiAutomaticallySettings.this, z ? 11 : -1, wifiConfiguration.networkId);
            StringBuilder sb = new StringBuilder("update score : ");
            sb.append(this.mWifiConfig.getKey());
            sb.append("/");
            Preference$$ExternalSyntheticOutline0.m(
                    sb, this.mSemWifiConfig.networkScore, "TurnOnWifiAutomaticallySettings");
            SALogging.insertSALog(z ? 1L : 0L, "WIFI_270", "1285");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CombinedWifiConfiguration implements Comparable {
        public SemWifiConfiguration mSemWifiConfig;
        public final WifiConfiguration mWifiConfig;

        public CombinedWifiConfiguration(
                WifiConfiguration wifiConfiguration, SemWifiConfiguration semWifiConfiguration) {
            this.mWifiConfig = wifiConfiguration;
            this.mSemWifiConfig = semWifiConfiguration;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            CombinedWifiConfiguration combinedWifiConfiguration = (CombinedWifiConfiguration) obj;
            int i = this.mWifiConfig.networkId;
            int i2 = TurnOnWifiAutomaticallySettings.this.mCurrentConnectedNetworkId;
            if (i == i2) {
                return -1;
            }
            if (combinedWifiConfiguration.mWifiConfig.networkId != i2) {
                int i3 = this.mSemWifiConfig.networkScore;
                int i4 = combinedWifiConfiguration.mSemWifiConfig.networkScore;
                if (i3 > i4) {
                    return -1;
                }
                if (i3 >= i4) {
                    return 0;
                }
            }
            return 1;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof CombinedWifiConfiguration)
                    && this.mWifiConfig.networkId
                            == ((CombinedWifiConfiguration) obj).mWifiConfig.networkId;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mWifiConfig.networkId));
        }
    }

    /* renamed from: -$$Nest$mgetSemWifiConfigFromCombinedList, reason: not valid java name */
    public static SemWifiConfiguration m1345$$Nest$mgetSemWifiConfigFromCombinedList(
            TurnOnWifiAutomaticallySettings turnOnWifiAutomaticallySettings,
            WifiConfiguration wifiConfiguration) {
        Iterator it =
                ((ArrayList) turnOnWifiAutomaticallySettings.mCombinedWifiConfigList).iterator();
        while (it.hasNext()) {
            CombinedWifiConfiguration combinedWifiConfiguration =
                    (CombinedWifiConfiguration) it.next();
            if (combinedWifiConfiguration.mWifiConfig.getKey().equals(wifiConfiguration.getKey())) {
                return combinedWifiConfiguration.mSemWifiConfig;
            }
        }
        Log.d("TurnOnWifiAutomaticallySettings", "Fail to get SemWifiConfig from CombinedList");
        return null;
    }

    /* renamed from: -$$Nest$mupdateNetworkScore, reason: not valid java name */
    public static void m1346$$Nest$mupdateNetworkScore(
            TurnOnWifiAutomaticallySettings turnOnWifiAutomaticallySettings, int i, int i2) {
        Iterator it =
                ((ArrayList) turnOnWifiAutomaticallySettings.mCombinedWifiConfigList).iterator();
        while (it.hasNext()) {
            CombinedWifiConfiguration combinedWifiConfiguration =
                    (CombinedWifiConfiguration) it.next();
            if (combinedWifiConfiguration.mWifiConfig.networkId == i2) {
                if (combinedWifiConfiguration.mSemWifiConfig == null) {
                    combinedWifiConfiguration.mSemWifiConfig =
                            new SemWifiConfiguration(
                                    combinedWifiConfiguration.mWifiConfig.getKey());
                }
                SemWifiConfiguration semWifiConfiguration =
                        combinedWifiConfiguration.mSemWifiConfig;
                semWifiConfiguration.networkScore = i;
                turnOnWifiAutomaticallySettings.mSemWifiManager.addOrUpdateNetwork(
                        semWifiConfiguration);
            }
        }
    }

    static {
        Debug.semIsProductDev();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 106;
    }

    public final void initSwitchBar$1() {
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        boolean z = false;
        if (SemWifiUtils.isSimCardReady(this.mTelephonyManager)
                && Settings.Secure.getInt(this.mContext.getContentResolver(), "location_mode", 0)
                        == 3
                && Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0)
                        == 0) {
            z = true;
        }
        settingsMainSwitchBar.setEnabled(z);
        this.mSwitchBar.setChecked(SemWifiUtils.isAutoWifiEnabled(this.mContext));
        boolean isAutoWifiEnabled = SemWifiUtils.isAutoWifiEnabled(this.mContext);
        this.mFavoriteApCategory.setEnabled(isAutoWifiEnabled);
        this.mFavoriteApCandidateCategory.setEnabled(isAutoWifiEnabled);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        super.onActivityCreated(bundle);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "sem_auto_wifi_control_enabled", z ? 1 : 0);
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_270", "1281");
        this.mFavoriteApCategory.setEnabled(z);
        this.mFavoriteApCandidateCategory.setEnabled(z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_auto_wifi_settings);
        this.mContext = getContext();
        this.mFavoriteApCategory = (PreferenceCategory) findPreference("favorite_ap_list");
        this.mFavoriteApCandidateCategory =
                (PreferenceCategory) findPreference("favorite_ap_candidate_list");
        this.mWifiManager = (WifiManager) getActivity().getSystemService(ImsProfile.PDN_WIFI);
        this.mSemWifiManager =
                (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        IntentFilter intentFilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.mIntentFilter.addAction(
                "com.samsung.android.server.wifi.AutoWifiNotificationController.SETTINGS");
        this.mIntentFilter.addAction("android.location.MODE_CHANGED");
        this.mCurrentConnectedNetworkId = -1;
        ((SettingsActivity) getActivity()).getClass();
        SALogging.insertSALog(
                WifiBadgeUtils.hasNewFavoriteNetwork(this.mContext) ? 1L : 0L,
                "WIFI_270",
                "1284",
                (String) null);
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "sem_auto_wifi_bubbletip_do_not_show_again", 1);
        this.mIsAutoWifiEnabed = SemWifiUtils.isAutoWifiEnabled(this.mContext);
        if (!WifiBadgeUtils.isAbTestCurrentlyAvailable(this.mContext) || this.mIsAutoWifiEnabed) {
            return;
        }
        String aBTestParam = WifiBadgeUtils.getABTestParam(this.mContext);
        if (!((SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                        .getAutoWifiDefaultValue()
                && Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "sem_auto_wifi_abtest_report",
                                0)
                        != 1) {
            SemWifiManager semWifiManager = this.mSemWifiManager;
            if (semWifiManager != null) {
                semWifiManager.reportAbTestResult("autowifi", "enteredAutoWifiMenu", aBTestParam);
            } else {
                Log.w(
                        "TurnOnWifiAutomaticallySettings",
                        "abtest: reportAbTestResult - mSemWifiManager is null. ");
            }
        }
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "sem_auto_wifi_abtest_report", 1);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        ((SettingsActivity) this.mContext).getClass();
        this.mSwitchBar.hide();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        initSwitchBar$1();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Settings.Global.putString(
                    this.mContext.getContentResolver(),
                    "sem_auto_wifi_added_removed_list",
                    ApnSettings.MVNO_NONE);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Settings.Global.putString(
                this.mContext.getContentResolver(),
                "sem_auto_wifi_added_removed_list",
                ApnSettings.MVNO_NONE);
        this.mSwitchBar.removeOnSwitchChangeListener(this);
        try {
            this.mContext.unregisterReceiver(this.mReceiver);
        } catch (IllegalArgumentException unused) {
            Log.d("TurnOnWifiAutomaticallySettings", "Receiver not registered");
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        SALogging.insertSALog("WIFI_270", "1282");
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SALogging.insertSALog("WIFI_270");
        if (!this.mSemWifiManager.isSupportedAutoWifi()) {
            ((SettingsActivity) getActivity()).finishPreferencePanel(null);
            return;
        }
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mContext.registerReceiver(this.mReceiver, this.mIntentFilter, 2);
        initSwitchBar$1();
        setFavoriteApList();
        this.mFavoriteApCandidateCategory.removeAll();
        refreshCombinedWifiConfigList();
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            this.mCurrentConnectedNetworkId = connectionInfo.getNetworkId();
        }
        Iterator it =
                ((List)
                                this.mCombinedWifiConfigList.stream()
                                        .filter(
                                                new TurnOnWifiAutomaticallySettings$$ExternalSyntheticLambda0(
                                                        this, 1))
                                        .sorted()
                                        .distinct()
                                        .limit(5L)
                                        .collect(Collectors.toList()))
                        .iterator();
        while (it.hasNext()) {
            WifiConfiguration wifiConfiguration =
                    ((CombinedWifiConfiguration) it.next()).mWifiConfig;
            String removeDoubleQuotes = SemWifiUtils.removeDoubleQuotes(wifiConfiguration.SSID);
            AutoWifiCandidatePreference autoWifiCandidatePreference =
                    new AutoWifiCandidatePreference(
                            new ContextThemeWrapper(this.mContext, R.style.PreferenceThemeOverlay),
                            wifiConfiguration);
            autoWifiCandidatePreference.setTitle(removeDoubleQuotes);
            autoWifiCandidatePreference.setKey(removeDoubleQuotes);
            autoWifiCandidatePreference.setSelectable(false);
            this.mFavoriteApCandidateCategory.addPreference(autoWifiCandidatePreference);
        }
        setEmptyFavoriteApItem(this.mFavoriteApCandidateCategory);
    }

    public final void refreshCombinedWifiConfigList() {
        ((ArrayList) this.mCombinedWifiConfigList).clear();
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        final int i = 0;
        final int i2 = 1;
        Map map =
                (Map)
                        this.mSemWifiManager.getConfiguredNetworks().stream()
                                .collect(
                                        Collectors.toMap(
                                                new Function() { // from class:
                                                                 // com.samsung.android.settings.wifi.intelligent.TurnOnWifiAutomaticallySettings$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        SemWifiConfiguration semWifiConfiguration =
                                                                (SemWifiConfiguration) obj;
                                                        switch (i) {
                                                            case 0:
                                                                int i3 =
                                                                        TurnOnWifiAutomaticallySettings
                                                                                .$r8$clinit;
                                                                return semWifiConfiguration
                                                                        .configKey;
                                                            default:
                                                                int i4 =
                                                                        TurnOnWifiAutomaticallySettings
                                                                                .$r8$clinit;
                                                                return semWifiConfiguration;
                                                        }
                                                    }
                                                },
                                                new Function() { // from class:
                                                                 // com.samsung.android.settings.wifi.intelligent.TurnOnWifiAutomaticallySettings$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        SemWifiConfiguration semWifiConfiguration =
                                                                (SemWifiConfiguration) obj;
                                                        switch (i2) {
                                                            case 0:
                                                                int i3 =
                                                                        TurnOnWifiAutomaticallySettings
                                                                                .$r8$clinit;
                                                                return semWifiConfiguration
                                                                        .configKey;
                                                            default:
                                                                int i4 =
                                                                        TurnOnWifiAutomaticallySettings
                                                                                .$r8$clinit;
                                                                return semWifiConfiguration;
                                                        }
                                                    }
                                                }));
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            SemWifiConfiguration semWifiConfiguration =
                    (SemWifiConfiguration) map.get(wifiConfiguration.getKey());
            if (semWifiConfiguration != null) {
                ((ArrayList) this.mCombinedWifiConfigList)
                        .add(
                                new CombinedWifiConfiguration(
                                        wifiConfiguration, semWifiConfiguration));
            }
        }
    }

    public final void setEmptyFavoriteApItem(PreferenceCategory preferenceCategory) {
        if (preferenceCategory.getPreferenceCount() == 0) {
            Preference preference = new Preference(this.mContext);
            preference.setLayoutResource(R.layout.sec_preference_wifi_no_network);
            preference.setSelectable(false);
            preference.setTitle(R.string.wifi_autowifi_empty_pref_title);
            preferenceCategory.addPreference(preference);
        }
    }

    public final void setFavoriteApList() {
        this.mFavoriteApCategory.removeAll();
        refreshCombinedWifiConfigList();
        Iterator it =
                ((List)
                                this.mCombinedWifiConfigList.stream()
                                        .filter(
                                                new TurnOnWifiAutomaticallySettings$$ExternalSyntheticLambda0(
                                                        this, 0))
                                        .sorted()
                                        .distinct()
                                        .collect(Collectors.toList()))
                        .iterator();
        while (it.hasNext()) {
            WifiConfiguration wifiConfiguration =
                    ((CombinedWifiConfiguration) it.next()).mWifiConfig;
            String removeDoubleQuotes = SemWifiUtils.removeDoubleQuotes(wifiConfiguration.SSID);
            AutoWifiPreference autoWifiPreference =
                    new AutoWifiPreference(
                            new ContextThemeWrapper(this.mContext, R.style.PreferenceThemeOverlay),
                            wifiConfiguration);
            autoWifiPreference.setTitle(removeDoubleQuotes);
            autoWifiPreference.setKey(removeDoubleQuotes);
            autoWifiPreference.setSelectable(false);
            this.mFavoriteApCategory.addPreference(autoWifiPreference);
        }
        setEmptyFavoriteApItem(this.mFavoriteApCategory);
    }
}
