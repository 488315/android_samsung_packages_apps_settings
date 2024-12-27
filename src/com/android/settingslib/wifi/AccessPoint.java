package com.android.settingslib.wifi;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.NetworkScoreManager;
import android.net.NetworkScorerAppData;
import android.net.ScoredNetwork;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.ProvisioningCallback;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.CollectionUtils;
import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccessPoint implements Comparable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String bssid;
    public WifiConfiguration mConfig;
    public final Context mContext;
    public int mEapType;
    public final ArraySet mExtraScanResults;
    public final String mFqdn;
    public WifiInfo mInfo;
    public boolean mIsOweTransitionMode;
    public boolean mIsPskSaeTransitionMode;
    public boolean mIsScoredNetworkMetered;
    public String mKey;
    public final Object mLock;
    public NetworkInfo mNetworkInfo;
    public final OsuProvider mOsuProvider;
    public final int mPasspointConfigurationVersion;
    public final String mPasspointUniqueId;
    public final String mProviderFriendlyName;
    public int mRssi;
    public final ArraySet mScanResults;
    public final Map mScoredNetworkCache;
    public int mSpeed;
    public final long mSubscriptionExpirationTimeInMillis;
    public WifiManager mWifiManager;
    public int networkId;
    public int pskType;
    public int security;
    public String ssid;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @VisibleForTesting
    class AccessPointProvisioningCallback extends ProvisioningCallback {
        public final void onProvisioningComplete() {
            int i = AccessPoint.$r8$clinit;
            throw null;
        }

        public final void onProvisioningFailure(int i) {
            throw null;
        }

        public final void onProvisioningStatus(int i) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    throw null;
                case 8:
                case 9:
                case 10:
                case 11:
                    throw null;
                default:
                    throw null;
            }
        }
    }

    static {
        new AtomicInteger(0);
    }

    public AccessPoint(Context context, Bundle bundle) {
        this.mLock = new Object();
        ArraySet arraySet = new ArraySet();
        this.mScanResults = arraySet;
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        if (bundle.containsKey("key_config")) {
            this.mConfig = (WifiConfiguration) bundle.getParcelable("key_config");
        }
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            loadConfig(wifiConfiguration);
        }
        if (bundle.containsKey("key_ssid")) {
            this.ssid = bundle.getString("key_ssid");
        }
        if (bundle.containsKey("key_security")) {
            this.security = bundle.getInt("key_security");
        }
        if (bundle.containsKey("key_speed")) {
            this.mSpeed = bundle.getInt("key_speed");
        }
        if (bundle.containsKey("key_psktype")) {
            this.pskType = bundle.getInt("key_psktype");
        }
        if (bundle.containsKey("eap_psktype")) {
            this.mEapType = bundle.getInt("eap_psktype");
        }
        this.mInfo = (WifiInfo) bundle.getParcelable("key_wifiinfo");
        if (bundle.containsKey("key_networkinfo")) {
            this.mNetworkInfo = (NetworkInfo) bundle.getParcelable("key_networkinfo");
        }
        if (bundle.containsKey("key_scanresults")) {
            Parcelable[] parcelableArray = bundle.getParcelableArray("key_scanresults");
            arraySet.clear();
            for (Parcelable parcelable : parcelableArray) {
                this.mScanResults.add((ScanResult) parcelable);
            }
        }
        if (bundle.containsKey("key_scorednetworkcache")) {
            Iterator it = bundle.getParcelableArrayList("key_scorednetworkcache").iterator();
            while (it.hasNext()) {
                TimestampedScoredNetwork timestampedScoredNetwork =
                        (TimestampedScoredNetwork) it.next();
                ((HashMap) this.mScoredNetworkCache)
                        .put(
                                timestampedScoredNetwork.mScore.networkKey.wifiKey.bssid,
                                timestampedScoredNetwork);
            }
        }
        if (bundle.containsKey("key_passpoint_unique_id")) {
            this.mPasspointUniqueId = bundle.getString("key_passpoint_unique_id");
        }
        if (bundle.containsKey("key_fqdn")) {
            this.mFqdn = bundle.getString("key_fqdn");
        }
        if (bundle.containsKey("key_provider_friendly_name")) {
            this.mProviderFriendlyName = bundle.getString("key_provider_friendly_name");
        }
        if (bundle.containsKey("key_subscription_expiration_time_in_millis")) {
            this.mSubscriptionExpirationTimeInMillis =
                    bundle.getLong("key_subscription_expiration_time_in_millis");
        }
        if (bundle.containsKey("key_passpoint_configuration_version")) {
            this.mPasspointConfigurationVersion =
                    bundle.getInt("key_passpoint_configuration_version");
        }
        if (bundle.containsKey("key_is_psk_sae_transition_mode")) {
            this.mIsPskSaeTransitionMode = bundle.getBoolean("key_is_psk_sae_transition_mode");
        }
        if (bundle.containsKey("key_is_owe_transition_mode")) {
            this.mIsOweTransitionMode = bundle.getBoolean("key_is_owe_transition_mode");
        }
        update(this.mConfig, this.mInfo, this.mNetworkInfo);
        updateKey();
        updateBestRssiInfo();
    }

    public static String convertToQuotedString(String str) {
        return ComposerKt$$ExternalSyntheticOutline0.m("\"", str, "\"");
    }

    public static String getKey(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder("AP:");
        if (TextUtils.isEmpty(str)) {
            sb.append(str2);
        } else {
            sb.append(str);
        }
        sb.append(',');
        sb.append(i);
        return sb.toString();
    }

    public static int getSecurity(Context context, ScanResult scanResult) {
        boolean contains = scanResult.capabilities.contains("WEP");
        boolean contains2 = scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE);
        boolean contains3 = scanResult.capabilities.contains("PSK");
        boolean contains4 = scanResult.capabilities.contains("EAP_SUITE_B_192");
        boolean contains5 = scanResult.capabilities.contains("EAP");
        boolean contains6 = scanResult.capabilities.contains("OWE");
        boolean contains7 = scanResult.capabilities.contains("OWE_TRANSITION");
        if (contains2 && contains3) {
            return ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI))
                            .isWpa3SaeSupported()
                    ? 5
                    : 2;
        }
        if (contains7) {
            return ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI))
                            .isEnhancedOpenSupported()
                    ? 4
                    : 0;
        }
        if (contains) {
            return 1;
        }
        if (contains2) {
            return 5;
        }
        if (contains3) {
            return 2;
        }
        if (contains4) {
            return 6;
        }
        if (contains5) {
            return 3;
        }
        return contains6 ? 4 : 0;
    }

    public static String getSpeedLabel(Context context, int i) {
        if (i == 5) {
            return context.getString(R.string.speed_label_slow);
        }
        if (i == 10) {
            return context.getString(R.string.speed_label_okay);
        }
        if (i == 20) {
            return context.getString(R.string.speed_label_fast);
        }
        if (i != 30) {
            return null;
        }
        return context.getString(R.string.speed_label_very_fast);
    }

    public static String getSummary(
            Context context,
            String str,
            NetworkInfo.DetailedState detailedState,
            boolean z,
            String str2) {
        NetworkCapabilities networkCapabilities;
        NetworkInfo.DetailedState detailedState2 = NetworkInfo.DetailedState.CONNECTED;
        CharSequence charSequence = ApnSettings.MVNO_NONE;
        if (detailedState == detailedState2) {
            if (z && !TextUtils.isEmpty(str2)) {
                PackageManager packageManager = context.getPackageManager();
                try {
                    ApplicationInfo applicationInfoAsUser =
                            packageManager.getApplicationInfoAsUser(
                                    str2, 0, UserHandle.getUserId(-2));
                    if (applicationInfoAsUser != null) {
                        charSequence = applicationInfoAsUser.loadLabel(packageManager);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("SettingsLib.AccessPoint", "Failed to get app info", e);
                }
                return context.getString(R.string.connected_via_app, charSequence);
            }
            if (z) {
                NetworkScorerAppData activeScorer =
                        ((NetworkScoreManager) context.getSystemService(NetworkScoreManager.class))
                                .getActiveScorer();
                return (activeScorer == null
                                || activeScorer.getRecommendationServiceLabel() == null)
                        ? context.getString(R.string.connected_via_network_scorer_default)
                        : String.format(
                                context.getString(R.string.connected_via_network_scorer),
                                activeScorer.getRecommendationServiceLabel());
            }
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        if (detailedState == detailedState2
                && (networkCapabilities =
                                connectivityManager.getNetworkCapabilities(
                                        ((WifiManager) context.getSystemService(WifiManager.class))
                                                .getCurrentNetwork()))
                        != null) {
            if (networkCapabilities.hasCapability(17)) {
                return context.getString(
                        context.getResources()
                                .getIdentifier(
                                        "network_available_sign_in",
                                        "string",
                                        RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
            }
            if (networkCapabilities.hasCapability(24)) {
                return context.getString(R.string.wifi_limited_connection);
            }
            if (!networkCapabilities.hasCapability(16)) {
                Settings.Global.getString(context.getContentResolver(), "private_dns_mode");
                return networkCapabilities.isPrivateDnsBroken()
                        ? context.getString(R.string.private_dns_broken)
                        : context.getString(R.string.wifi_connected_no_internet);
            }
        }
        if (detailedState == null) {
            Log.w("SettingsLib.AccessPoint", "state is null, returning empty summary");
            return ApnSettings.MVNO_NONE;
        }
        String[] stringArray =
                context.getResources()
                        .getStringArray(
                                str == null ? R.array.wifi_status : R.array.wifi_status_with_ssid);
        int ordinal = detailedState.ordinal();
        return (ordinal >= stringArray.length || stringArray[ordinal].length() == 0)
                ? ApnSettings.MVNO_NONE
                : String.format(stringArray[ordinal], str);
    }

    public static boolean isVerboseLoggingEnabled() {
        return WifiTracker.sVerboseLogging || Log.isLoggable("SettingsLib.AccessPoint", 2);
    }

    public static String removeDoubleQuotes(String str) {
        if (TextUtils.isEmpty(str)) {
            return ApnSettings.MVNO_NONE;
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        return str.charAt(i) == '\"' ? str.substring(1, i) : str;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof AccessPoint) && compareTo((AccessPoint) obj) == 0;
    }

    public final int getLevel() {
        return getWifiManager().calculateSignalLevel(this.mRssi);
    }

    public final String getTitle() {
        if (isPasspoint() && !TextUtils.isEmpty(this.mConfig.providerFriendlyName)) {
            return this.mConfig.providerFriendlyName;
        }
        if (isPasspointConfig() && !TextUtils.isEmpty(this.mProviderFriendlyName)) {
            return this.mProviderFriendlyName;
        }
        OsuProvider osuProvider = this.mOsuProvider;
        return (osuProvider == null || TextUtils.isEmpty(osuProvider.getFriendlyName()))
                ? !TextUtils.isEmpty(this.ssid) ? this.ssid : ApnSettings.MVNO_NONE
                : this.mOsuProvider.getFriendlyName();
    }

    public final WifiManager getWifiManager() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        }
        return this.mWifiManager;
    }

    public final int hashCode() {
        WifiInfo wifiInfo = this.mInfo;
        return (this.ssid.hashCode() * 29)
                + (this.networkId * 23)
                + (this.mRssi * 19)
                + (wifiInfo != null ? wifiInfo.hashCode() * 13 : 0);
    }

    public final boolean isActive() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        return (networkInfo == null
                        || (this.networkId == -1
                                && networkInfo.getState() == NetworkInfo.State.DISCONNECTED))
                ? false
                : true;
    }

    public final boolean isPasspoint() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        return wifiConfiguration != null && wifiConfiguration.isPasspoint();
    }

    public final boolean isPasspointConfig() {
        return this.mPasspointUniqueId != null && this.mConfig == null;
    }

    public final boolean isReachable() {
        return this.mRssi != Integer.MIN_VALUE;
    }

    public final boolean isSaved() {
        return this.mConfig != null;
    }

    @VisibleForTesting
    public void loadConfig(WifiConfiguration wifiConfiguration) {
        String str = wifiConfiguration.SSID;
        this.ssid = str == null ? ApnSettings.MVNO_NONE : removeDoubleQuotes(str);
        this.bssid = wifiConfiguration.BSSID;
        this.security = getSecurity(wifiConfiguration);
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
    }

    public final boolean matches(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.isPasspoint()) {
            return isPasspoint() && wifiConfiguration.getKey().equals(this.mConfig.getKey());
        }
        if (!this.ssid.equals(removeDoubleQuotes(wifiConfiguration.SSID))) {
            return false;
        }
        WifiConfiguration wifiConfiguration2 = this.mConfig;
        if (wifiConfiguration2 != null && wifiConfiguration2.shared != wifiConfiguration.shared) {
            return false;
        }
        int security = getSecurity(wifiConfiguration);
        if (this.mIsPskSaeTransitionMode
                && ((security == 5 && getWifiManager().isWpa3SaeSupported()) || security == 2)) {
            return true;
        }
        return (this.mIsOweTransitionMode
                        && ((security == 4 && getWifiManager().isEnhancedOpenSupported())
                                || security == 0))
                || this.security == getSecurity(wifiConfiguration);
    }

    public final void saveWifiState(Bundle bundle) {
        String str = this.ssid;
        if (str != null) {
            bundle.putString("key_ssid", str);
        }
        bundle.putInt("key_security", this.security);
        bundle.putInt("key_speed", this.mSpeed);
        bundle.putInt("key_psktype", this.pskType);
        bundle.putInt("eap_psktype", this.mEapType);
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            bundle.putParcelable("key_config", wifiConfiguration);
        }
        bundle.putParcelable("key_wifiinfo", this.mInfo);
        synchronized (this.mLock) {
            ArraySet arraySet = this.mScanResults;
            bundle.putParcelableArray(
                    "key_scanresults",
                    (Parcelable[])
                            arraySet.toArray(
                                    new Parcelable
                                            [arraySet.size() + this.mExtraScanResults.size()]));
        }
        bundle.putParcelableArrayList(
                "key_scorednetworkcache",
                new ArrayList<>(((HashMap) this.mScoredNetworkCache).values()));
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo != null) {
            bundle.putParcelable("key_networkinfo", networkInfo);
        }
        String str2 = this.mPasspointUniqueId;
        if (str2 != null) {
            bundle.putString("key_passpoint_unique_id", str2);
        }
        String str3 = this.mFqdn;
        if (str3 != null) {
            bundle.putString("key_fqdn", str3);
        }
        String str4 = this.mProviderFriendlyName;
        if (str4 != null) {
            bundle.putString("key_provider_friendly_name", str4);
        }
        bundle.putLong(
                "key_subscription_expiration_time_in_millis",
                this.mSubscriptionExpirationTimeInMillis);
        bundle.putInt("key_passpoint_configuration_version", this.mPasspointConfigurationVersion);
        bundle.putBoolean("key_is_psk_sae_transition_mode", this.mIsPskSaeTransitionMode);
        bundle.putBoolean("key_is_owe_transition_mode", this.mIsOweTransitionMode);
    }

    @VisibleForTesting
    public void setRssi(int i) {
        this.mRssi = i;
    }

    public final void setScanResults(Collection collection) {
        if (CollectionUtils.isEmpty(collection)) {
            Log.d("SettingsLib.AccessPoint", "Cannot set scan results to empty list");
            return;
        }
        if (this.mKey != null && !isPasspoint() && this.mOsuProvider == null) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                ScanResult scanResult = (ScanResult) it.next();
                if (!matches(scanResult)) {
                    Context context = this.mContext;
                    String key =
                            getKey(
                                    getSecurity(context, scanResult),
                                    scanResult.SSID,
                                    scanResult.BSSID);
                    String str = this.mKey;
                    StringBuilder sb = new StringBuilder("ScanResult ");
                    sb.append(scanResult);
                    sb.append("\nkey of ");
                    sb.append(key);
                    sb.append(" did not match current AP key ");
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            sb, str, "SettingsLib.AccessPoint");
                    return;
                }
            }
        }
        int level = getLevel();
        synchronized (this.mLock) {
            this.mScanResults.clear();
            this.mScanResults.addAll(collection);
        }
        updateBestRssiInfo();
        int level2 = getLevel();
        if (level2 > 0 && level2 != level) {
            updateSpeed();
            ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this));
        }
        ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this));
    }

    public final void setScanResultsPasspoint(Collection collection, Collection collection2) {
        synchronized (this.mLock) {
            try {
                this.mExtraScanResults.clear();
                if (!CollectionUtils.isEmpty(collection)) {
                    if (!CollectionUtils.isEmpty(collection2)) {
                        this.mExtraScanResults.addAll(collection2);
                    }
                    setScanResults(collection);
                } else if (!CollectionUtils.isEmpty(collection2)) {
                    setScanResults(collection2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String toString() {
        NetworkInfo.DetailedState detailedState;
        NetworkInfo networkInfo;
        StringBuilder sb = new StringBuilder("AccessPoint(");
        sb.append(this.ssid);
        if (isSaved()) {
            sb.append(",saved");
        }
        if (isActive()) {
            sb.append(",active");
        }
        WifiInfo wifiInfo = this.mInfo;
        if (wifiInfo != null
                && wifiInfo.isEphemeral()
                && (networkInfo = this.mNetworkInfo) != null
                && networkInfo.getState() != NetworkInfo.State.DISCONNECTED) {
            sb.append(",ephemeral");
        }
        if (getLevel() != -1) {
            NetworkInfo networkInfo2 = this.mNetworkInfo;
            if (networkInfo2 != null) {
                detailedState = networkInfo2.getDetailedState();
            } else {
                Log.w(
                        "SettingsLib.AccessPoint",
                        "NetworkInfo is null, cannot return detailed state");
                detailedState = null;
            }
            if (detailedState == null) {
                sb.append(",connectable");
            }
        }
        int i = this.security;
        boolean z = true;
        if (i != 0 && i != 4) {
            sb.append(',');
            int i2 = this.security;
            int i3 = this.pskType;
            sb.append(
                    i2 == 1
                            ? "WEP"
                            : i2 == 2
                                    ? i3 == 1
                                            ? "WPA"
                                            : i3 == 2 ? "WPA2" : i3 == 3 ? "WPA_WPA2" : "PSK"
                                    : i2 == 3
                                            ? "EAP"
                                            : i2 == 5
                                                    ? WifiPolicy.SECURITY_TYPE_SAE
                                                    : i2 == 6
                                                            ? "SUITE_B"
                                                            : i2 == 4 ? "OWE" : "NONE");
        }
        sb.append(",level=");
        sb.append(getLevel());
        if (this.mSpeed != 0) {
            sb.append(",speed=");
            sb.append(this.mSpeed);
        }
        sb.append(",metered=");
        if (!this.mIsScoredNetworkMetered
                && !WifiConfiguration.isMetered(this.mConfig, this.mInfo)) {
            z = false;
        }
        sb.append(z);
        if (isVerboseLoggingEnabled()) {
            sb.append(",rssi=");
            sb.append(this.mRssi);
            synchronized (this.mLock) {
                sb.append(",scan cache size=");
                sb.append(this.mScanResults.size() + this.mExtraScanResults.size());
            }
        }
        sb.append(')');
        return sb.toString();
    }

    public final boolean update(WifiNetworkScoreCache wifiNetworkScoreCache, boolean z, long j) {
        boolean z2;
        WifiInfo wifiInfo;
        if (z) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            synchronized (this.mLock) {
                try {
                    Iterator it = this.mScanResults.iterator();
                    while (it.hasNext()) {
                        ScanResult scanResult = (ScanResult) it.next();
                        ScoredNetwork scoredNetwork =
                                wifiNetworkScoreCache.getScoredNetwork(scanResult);
                        if (scoredNetwork != null) {
                            TimestampedScoredNetwork timestampedScoredNetwork =
                                    (TimestampedScoredNetwork)
                                            this.mScoredNetworkCache.get(scanResult.BSSID);
                            if (timestampedScoredNetwork == null) {
                                Map map = this.mScoredNetworkCache;
                                String str = scanResult.BSSID;
                                TimestampedScoredNetwork timestampedScoredNetwork2 =
                                        new TimestampedScoredNetwork();
                                timestampedScoredNetwork2.mScore = scoredNetwork;
                                timestampedScoredNetwork2.mUpdatedTimestampMillis = elapsedRealtime;
                                map.put(str, timestampedScoredNetwork2);
                            } else {
                                timestampedScoredNetwork.mScore = scoredNetwork;
                                timestampedScoredNetwork.mUpdatedTimestampMillis = elapsedRealtime;
                            }
                        }
                    }
                } finally {
                }
            }
            final long j2 = elapsedRealtime - j;
            final Iterator it2 = this.mScoredNetworkCache.values().iterator();
            it2.forEachRemaining(
                    new Consumer() { // from class:
                                     // com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            long j3 = j2;
                            Iterator it3 = it2;
                            if (((TimestampedScoredNetwork) obj).mUpdatedTimestampMillis < j3) {
                                it3.remove();
                            }
                        }
                    });
            z2 = updateSpeed();
        } else {
            z2 = false;
        }
        boolean z3 = this.mIsScoredNetworkMetered;
        this.mIsScoredNetworkMetered = false;
        if (!isActive() || (wifiInfo = this.mInfo) == null) {
            synchronized (this.mLock) {
                try {
                    Iterator it3 = this.mScanResults.iterator();
                    while (it3.hasNext()) {
                        ScoredNetwork scoredNetwork2 =
                                wifiNetworkScoreCache.getScoredNetwork((ScanResult) it3.next());
                        if (scoredNetwork2 != null) {
                            this.mIsScoredNetworkMetered =
                                    scoredNetwork2.meteredHint | this.mIsScoredNetworkMetered;
                        }
                    }
                } finally {
                }
            }
        } else {
            ScoredNetwork scoredNetwork3 =
                    wifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(wifiInfo));
            if (scoredNetwork3 != null) {
                this.mIsScoredNetworkMetered =
                        scoredNetwork3.meteredHint | this.mIsScoredNetworkMetered;
            }
        }
        return z3 != this.mIsScoredNetworkMetered || z2;
    }

    public final void updateBestRssiInfo() {
        ScanResult scanResult;
        int i;
        int i2;
        int i3;
        if (isActive()) {
            return;
        }
        synchronized (this.mLock) {
            try {
                Iterator it = this.mScanResults.iterator();
                scanResult = null;
                i = Integer.MIN_VALUE;
                while (it.hasNext()) {
                    ScanResult scanResult2 = (ScanResult) it.next();
                    int i4 = scanResult2.level;
                    if (i4 > i) {
                        scanResult = scanResult2;
                        i = i4;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (i == Integer.MIN_VALUE || (i3 = this.mRssi) == Integer.MIN_VALUE) {
            this.mRssi = i;
        } else {
            this.mRssi = (i3 + i) / 2;
        }
        if (scanResult != null) {
            this.ssid = scanResult.SSID;
            this.bssid = scanResult.BSSID;
            int security = getSecurity(this.mContext, scanResult);
            this.security = security;
            if (security == 2 || security == 5) {
                boolean contains = scanResult.capabilities.contains("WPA-PSK");
                boolean contains2 = scanResult.capabilities.contains("RSN-PSK");
                boolean contains3 = scanResult.capabilities.contains("RSN-SAE");
                if (contains2 && contains) {
                    i2 = 3;
                } else if (contains2) {
                    i2 = 2;
                } else if (contains) {
                    i2 = 1;
                } else {
                    if (!contains3) {
                        MainClear$$ExternalSyntheticOutline0.m$1(
                                new StringBuilder("Received abnormal flag string: "),
                                scanResult.capabilities,
                                "SettingsLib.AccessPoint");
                    }
                    i2 = 0;
                }
                this.pskType = i2;
            }
            if (this.security == 3) {
                this.mEapType =
                        scanResult.capabilities.contains("RSN-EAP")
                                ? 2
                                : scanResult.capabilities.contains("WPA-EAP") ? 1 : 0;
            }
            this.mIsPskSaeTransitionMode =
                    scanResult.capabilities.contains("PSK")
                            && scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE);
            this.mIsOweTransitionMode = scanResult.capabilities.contains("OWE_TRANSITION");
        }
        if (isPasspoint()) {
            this.mConfig.SSID = convertToQuotedString(this.ssid);
        }
    }

    public final void updateKey() {
        String key;
        if (isPasspoint()) {
            WifiConfiguration wifiConfiguration = this.mConfig;
            if (wifiConfiguration.isPasspoint()) {
                key =
                        AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "PASSPOINT:", wifiConfiguration.getKey());
            } else {
                key =
                        getKey(
                                getSecurity(wifiConfiguration),
                                removeDoubleQuotes(wifiConfiguration.SSID),
                                wifiConfiguration.BSSID);
            }
            this.mKey = key;
            return;
        }
        if (isPasspointConfig()) {
            this.mKey =
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "PASSPOINT:", this.mPasspointUniqueId);
            return;
        }
        OsuProvider osuProvider = this.mOsuProvider;
        if (osuProvider == null) {
            this.mKey = getKey(this.security, this.ssid, this.bssid);
        } else {
            this.mKey = "OSU:" + osuProvider.getFriendlyName() + ',' + osuProvider.getServerUri();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateSpeed() {
        /*
            r8 = this;
            int r0 = r8.mSpeed
            java.util.Map r1 = r8.mScoredNetworkCache
            java.util.HashMap r1 = (java.util.HashMap) r1
            boolean r1 = r1.isEmpty()
            r2 = 0
            java.lang.String r3 = "SettingsLib.AccessPoint"
            if (r1 == 0) goto L12
        Lf:
            r1 = r2
            goto L88
        L12:
            r1 = 3
            boolean r1 = android.util.Log.isLoggable(r3, r1)
            if (r1 == 0) goto L2a
            java.lang.String r1 = r8.ssid
            java.util.Map r4 = r8.mScoredNetworkCache
            java.lang.Object[] r1 = new java.lang.Object[]{r1, r4}
            java.lang.String r4 = "Generating fallbackspeed for %s using cache: %s"
            java.lang.String r1 = java.lang.String.format(r4, r1)
            android.util.Log.d(r3, r1)
        L2a:
            java.util.Map r1 = r8.mScoredNetworkCache
            java.util.HashMap r1 = (java.util.HashMap) r1
            java.util.Collection r1 = r1.values()
            java.util.Iterator r1 = r1.iterator()
            r4 = r2
            r5 = r4
        L38:
            boolean r6 = r1.hasNext()
            if (r6 == 0) goto L52
            java.lang.Object r6 = r1.next()
            com.android.settingslib.wifi.TimestampedScoredNetwork r6 = (com.android.settingslib.wifi.TimestampedScoredNetwork) r6
            android.net.ScoredNetwork r6 = r6.mScore
            int r7 = r8.mRssi
            int r6 = r6.calculateBadge(r7)
            if (r6 == 0) goto L38
            int r4 = r4 + 1
            int r5 = r5 + r6
            goto L38
        L52:
            if (r4 != 0) goto L56
            r5 = r2
            goto L57
        L56:
            int r5 = r5 / r4
        L57:
            boolean r1 = isVerboseLoggingEnabled()
            if (r1 == 0) goto L70
            java.lang.String r1 = r8.ssid
            java.lang.Integer r4 = java.lang.Integer.valueOf(r5)
            java.lang.Object[] r1 = new java.lang.Object[]{r1, r4}
            java.lang.String r4 = "%s generated fallback speed is: %d"
            java.lang.String r1 = java.lang.String.format(r4, r1)
            android.util.Log.i(r3, r1)
        L70:
            r1 = 5
            if (r5 >= r1) goto L74
            goto Lf
        L74:
            r4 = 7
            if (r5 >= r4) goto L78
            goto L88
        L78:
            r1 = 15
            if (r5 >= r1) goto L7f
            r1 = 10
            goto L88
        L7f:
            r1 = 25
            if (r5 >= r1) goto L86
            r1 = 20
            goto L88
        L86:
            r1 = 30
        L88:
            r8.mSpeed = r1
            if (r0 == r1) goto L8d
            r2 = 1
        L8d:
            boolean r0 = isVerboseLoggingEnabled()
            if (r0 == 0) goto Laa
            if (r2 == 0) goto Laa
            java.lang.String r0 = r8.ssid
            int r8 = r8.mSpeed
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            java.lang.Object[] r8 = new java.lang.Object[]{r0, r8}
            java.lang.String r0 = "%s: Set speed to %d"
            java.lang.String r8 = java.lang.String.format(r0, r8)
            android.util.Log.i(r3, r8)
        Laa:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.wifi.AccessPoint.updateSpeed():boolean");
    }

    @Override // java.lang.Comparable
    public final int compareTo(AccessPoint accessPoint) {
        if (isActive() && !accessPoint.isActive()) {
            return -1;
        }
        if (!isActive() && accessPoint.isActive()) {
            return 1;
        }
        if (isReachable() && !accessPoint.isReachable()) {
            return -1;
        }
        if (!isReachable() && accessPoint.isReachable()) {
            return 1;
        }
        if (isSaved() && !accessPoint.isSaved()) {
            return -1;
        }
        if (!isSaved() && accessPoint.isSaved()) {
            return 1;
        }
        int i = this.mSpeed;
        int i2 = accessPoint.mSpeed;
        if (i != i2) {
            return i2 - i;
        }
        WifiManager wifiManager = getWifiManager();
        int calculateSignalLevel =
                wifiManager.calculateSignalLevel(accessPoint.mRssi)
                        - wifiManager.calculateSignalLevel(this.mRssi);
        if (calculateSignalLevel != 0) {
            return calculateSignalLevel;
        }
        int compareToIgnoreCase = getTitle().compareToIgnoreCase(accessPoint.getTitle());
        return compareToIgnoreCase != 0
                ? compareToIgnoreCase
                : this.ssid.compareTo(accessPoint.ssid);
    }

    @VisibleForTesting
    public boolean matches(ScanResult scanResult) {
        String str;
        if (scanResult == null) {
            return false;
        }
        if (!isPasspoint() && this.mOsuProvider == null) {
            if (!TextUtils.equals(this.ssid, scanResult.SSID)
                    && ((str = scanResult.BSSID) == null || !TextUtils.equals(this.bssid, str))) {
                return false;
            }
            if (this.mIsPskSaeTransitionMode) {
                if ((scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE)
                                && getWifiManager().isWpa3SaeSupported())
                        || scanResult.capabilities.contains("PSK")) {
                    return true;
                }
            } else {
                int i = this.security;
                if ((i == 5 || i == 2)
                        && scanResult.capabilities.contains("PSK")
                        && scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE)) {
                    return true;
                }
            }
            if (this.mIsOweTransitionMode) {
                int security = getSecurity(this.mContext, scanResult);
                if ((security == 4 && getWifiManager().isEnhancedOpenSupported())
                        || security == 0) {
                    return true;
                }
            } else {
                int i2 = this.security;
                if ((i2 == 4 || i2 == 0) && scanResult.capabilities.contains("OWE_TRANSITION")) {
                    return true;
                }
            }
            return this.security == getSecurity(this.mContext, scanResult);
        }
        throw new IllegalStateException("Should not matches a Passpoint by ScanResult");
    }

    public static int getSecurity(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(8)) {
            return 5;
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return 2;
        }
        if (wifiConfiguration.allowedKeyManagement.get(10)) {
            return 6;
        }
        if (wifiConfiguration.allowedKeyManagement.get(2)
                || wifiConfiguration.allowedKeyManagement.get(3)) {
            return 3;
        }
        if (wifiConfiguration.allowedKeyManagement.get(9)) {
            return 4;
        }
        int i = wifiConfiguration.wepTxKeyIndex;
        if (i >= 0) {
            String[] strArr = wifiConfiguration.wepKeys;
            if (i < strArr.length && strArr[i] != null) {
                return 1;
            }
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0023, code lost:

       if (r2 == r6.getNetworkId()) goto L15;
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0025, code lost:

       r2 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x008d, code lost:

       if (android.text.TextUtils.equals(r6.getPasspointProviderFriendlyName(), r4.mConfig.providerFriendlyName) != false) goto L15;
    */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean update(
            android.net.wifi.WifiConfiguration r5,
            android.net.wifi.WifiInfo r6,
            android.net.NetworkInfo r7) {
        /*
            r4 = this;
            r4.getLevel()
            r0 = 1
            r1 = 0
            if (r6 == 0) goto Ld6
            boolean r2 = r6.isOsuAp()
            if (r2 != 0) goto L90
            boolean r2 = r6.isPasspointAp()
            if (r2 != 0) goto L67
            boolean r2 = r4.isPasspoint()
            if (r2 == 0) goto L1a
            goto L67
        L1a:
            int r2 = r4.networkId
            r3 = -1
            if (r2 == r3) goto L28
            int r3 = r6.getNetworkId()
            if (r2 != r3) goto L93
        L25:
            r2 = r0
            goto L94
        L28:
            if (r5 == 0) goto L58
            boolean r2 = r5.isPasspoint()
            if (r2 != 0) goto L53
            java.lang.String r2 = r4.ssid
            java.lang.String r3 = r6.getSSID()
            java.lang.String r3 = removeDoubleQuotes(r3)
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 == 0) goto L41
            goto L53
        L41:
            java.lang.String r2 = r6.getBSSID()
            if (r2 == 0) goto L93
            java.lang.String r2 = r4.bssid
            java.lang.String r3 = r6.getBSSID()
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 == 0) goto L93
        L53:
            boolean r2 = r4.matches(r5)
            goto L94
        L58:
            java.lang.String r2 = r6.getSSID()
            java.lang.String r2 = removeDoubleQuotes(r2)
            java.lang.String r3 = r4.ssid
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            goto L94
        L67:
            boolean r2 = r6.isPasspointAp()
            if (r2 == 0) goto L93
            boolean r2 = r4.isPasspoint()
            if (r2 == 0) goto L93
            java.lang.String r2 = r6.getPasspointFqdn()
            android.net.wifi.WifiConfiguration r3 = r4.mConfig
            java.lang.String r3 = r3.FQDN
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 == 0) goto L93
            java.lang.String r2 = r6.getPasspointProviderFriendlyName()
            android.net.wifi.WifiConfiguration r3 = r4.mConfig
            java.lang.String r3 = r3.providerFriendlyName
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 == 0) goto L93
            goto L25
        L90:
            r6.isOsuAp()
        L93:
            r2 = r1
        L94:
            if (r2 == 0) goto Ld6
            android.net.wifi.WifiInfo r2 = r4.mInfo
            if (r2 != 0) goto L9b
            r1 = r0
        L9b:
            boolean r2 = r4.isPasspoint()
            if (r2 != 0) goto La8
            android.net.wifi.WifiConfiguration r2 = r4.mConfig
            if (r2 == r5) goto La8
            r4.update(r5)
        La8:
            int r5 = r4.mRssi
            int r2 = r6.getRssi()
            if (r5 == r2) goto Lbf
            int r5 = r6.getRssi()
            r2 = -127(0xffffffffffffff81, float:NaN)
            if (r5 == r2) goto Lbf
            int r5 = r6.getRssi()
            r4.mRssi = r5
            goto Ld1
        Lbf:
            android.net.NetworkInfo r5 = r4.mNetworkInfo
            if (r5 == 0) goto Ld0
            if (r7 == 0) goto Ld0
            android.net.NetworkInfo$DetailedState r5 = r5.getDetailedState()
            android.net.NetworkInfo$DetailedState r2 = r7.getDetailedState()
            if (r5 == r2) goto Ld0
            goto Ld1
        Ld0:
            r0 = r1
        Ld1:
            r4.mInfo = r6
            r4.mNetworkInfo = r7
            goto Le1
        Ld6:
            android.net.wifi.WifiInfo r5 = r4.mInfo
            if (r5 == 0) goto Le0
            r5 = 0
            r4.mInfo = r5
            r4.mNetworkInfo = r5
            goto Le1
        Le0:
            r0 = r1
        Le1:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.wifi.AccessPoint.update(android.net.wifi.WifiConfiguration,"
                    + " android.net.wifi.WifiInfo, android.net.NetworkInfo):boolean");
    }

    public final void update(WifiConfiguration wifiConfiguration) {
        this.mConfig = wifiConfiguration;
        if (wifiConfiguration != null && !isPasspoint()) {
            this.ssid = removeDoubleQuotes(this.mConfig.SSID);
        }
        this.networkId = wifiConfiguration != null ? wifiConfiguration.networkId : -1;
        ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this));
    }

    public AccessPoint(Context context, WifiConfiguration wifiConfiguration) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        loadConfig(wifiConfiguration);
        updateKey();
    }

    public AccessPoint(Context context, PasspointConfiguration passpointConfiguration) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.mPasspointUniqueId = passpointConfiguration.getUniqueId();
        this.mFqdn = passpointConfiguration.getHomeSp().getFqdn();
        this.mProviderFriendlyName = passpointConfiguration.getHomeSp().getFriendlyName();
        this.mSubscriptionExpirationTimeInMillis =
                passpointConfiguration.getSubscriptionExpirationTimeMillis();
        if (passpointConfiguration.isOsuProvisioned()) {
            this.mPasspointConfigurationVersion = 2;
        } else {
            this.mPasspointConfigurationVersion = 1;
        }
        updateKey();
    }

    public AccessPoint(
            Context context,
            WifiConfiguration wifiConfiguration,
            Collection collection,
            Collection collection2) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
        this.mPasspointUniqueId = wifiConfiguration.getKey();
        this.mFqdn = wifiConfiguration.FQDN;
        setScanResultsPasspoint(collection, collection2);
        updateKey();
    }

    public AccessPoint(Context context, OsuProvider osuProvider, Collection collection) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.mOsuProvider = osuProvider;
        setScanResults(collection);
        updateKey();
    }

    public AccessPoint(Context context, Collection collection) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        setScanResults(collection);
        updateKey();
    }
}
