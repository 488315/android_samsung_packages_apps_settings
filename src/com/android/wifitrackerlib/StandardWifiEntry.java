package com.android.wifitrackerlib;

import android.app.admin.DevicePolicyManager;
import android.app.admin.WifiSsidPolicy;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.MloLink;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiSsid;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;

import androidx.core.os.BuildCompat;

import com.android.settings.R;

import com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.samsung.android.wifitrackerlib.WifiQoSScoredCache;
import com.samsung.android.wifitrackerlib.WifiScoredNetwork;
import com.sec.ims.extensions.WiFiManagerExt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StandardWifiEntry extends WifiEntry {
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mHasAddConfigUserRestriction;
    public boolean mIsAdminRestricted;
    public final boolean mIsEnhancedOpenSupported;
    public boolean mIsOweTransitionMode;
    public boolean mIsPskSaeTransitionMode;
    public boolean mIsUserShareable;
    public final boolean mIsWpa3SaeSupported;
    public final boolean mIsWpa3SuiteBSupported;
    public final StandardWifiEntryKey mKey;
    public final Map mMatchingScanResults;
    public final Map mMatchingWifiConfigs;
    public SemWifiManager mSemWifiManager;
    public final boolean mShowBandSummary;
    public boolean mSupportWpaEap;
    public final List mTargetScanResults;
    public final List mTargetSecurityTypes;
    public WifiConfiguration mTargetWifiConfig;
    public final UserManager mUserManager;

    public StandardWifiEntry(
            WifiTrackerInjector wifiTrackerInjector,
            Handler handler,
            StandardWifiEntryKey standardWifiEntryKey,
            WifiManager wifiManager,
            boolean z) {
        super(wifiTrackerInjector, handler, wifiManager);
        boolean z2;
        Context context;
        this.mMatchingScanResults = new ArrayMap();
        this.mMatchingWifiConfigs = new ArrayMap();
        this.mTargetScanResults = new ArrayList();
        this.mTargetSecurityTypes = new ArrayList();
        boolean z3 = false;
        this.mIsUserShareable = false;
        this.mIsAdminRestricted = false;
        this.mHasAddConfigUserRestriction = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mSupportWpaEap = false;
        this.mKey = standardWifiEntryKey;
        this.mIsWpa3SaeSupported = SemWifiEntryFlags.isWpa3SaeSupported(this.mWifiManager);
        WifiManager wifiManager2 = this.mWifiManager;
        if (SemWifiEntryFlags.isWpa3SuiteBSupported == -1 && wifiManager2.isWifiEnabled()) {
            SemWifiEntryFlags.isWpa3SuiteBSupported = wifiManager2.isWpa3SuiteBSupported() ? 1 : 0;
        }
        this.mIsWpa3SuiteBSupported = SemWifiEntryFlags.isWpa3SuiteBSupported == 1;
        this.mIsEnhancedOpenSupported =
                SemWifiEntryFlags.isEnhancedOpenSupported(this.mWifiManager);
        if (SemWifiEntryFlags.isWifiDeveloperOptionOn(this.mContext)) {
            Context context2 = this.mContext;
            if (SemWifiEntryFlags.isShowBandSummaryOn == -1) {
                SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                SemWifiEntryFlags.isShowBandSummaryOn =
                        Settings.Global.getInt(
                                                context2.getContentResolver(),
                                                "sec_wifi_developer_show_band",
                                                0)
                                        == 1
                                ? 1
                                : 0;
            }
            if (SemWifiEntryFlags.isShowBandSummaryOn == 1) {
                z2 = true;
                this.mShowBandSummary = z2;
                SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
                context = this.mContext;
                semWifiEntryFlags.getClass();
                if (SemWifiUtils.isSupportedWifi7(context)
                        && Settings.Secure.getInt(
                                        context.getContentResolver(), "sec_wifi_7_mode_enabled", 1)
                                == 1) {
                    z3 = true;
                }
                semWifiEntryFlags.isSupportedWifi7 = z3;
                this.mUserManager = wifiTrackerInjector.mUserManager;
                this.mDevicePolicyManager = wifiTrackerInjector.mDevicePolicyManager;
                updateSecurityTypes();
                updateAdminRestrictions();
            }
        }
        z2 = false;
        this.mShowBandSummary = z2;
        SemWifiEntryFlags semWifiEntryFlags2 = this.mSemFlags;
        context = this.mContext;
        semWifiEntryFlags2.getClass();
        if (SemWifiUtils.isSupportedWifi7(context)) {
            z3 = true;
        }
        semWifiEntryFlags2.isSupportedWifi7 = z3;
        this.mUserManager = wifiTrackerInjector.mUserManager;
        this.mDevicePolicyManager = wifiTrackerInjector.mDevicePolicyManager;
        updateSecurityTypes();
        updateAdminRestrictions();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean canConnect() {
        WifiConfiguration wifiConfiguration;
        WifiEnterpriseConfig wifiEnterpriseConfig;
        if (this.mLevel != -1 && getConnectedState() == 0) {
            if (hasAdminRestrictions()) {
                return false;
            }
            if (!((ArrayList) this.mTargetSecurityTypes).contains(3)
                    || (wifiConfiguration = this.mTargetWifiConfig) == null
                    || (wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig) == null) {
                return true;
            }
            if (!wifiEnterpriseConfig.isAuthenticationSimBased()) {
                return true;
            }
            List<SubscriptionInfo> activeSubscriptionInfoList =
                    ((SubscriptionManager)
                                    this.mContext.getSystemService(SubscriptionManager.class))
                            .getActiveSubscriptionInfoList();
            if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() != 0) {
                if (this.mTargetWifiConfig.carrierId == -1) {
                    return true;
                }
                Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
                while (it.hasNext()) {
                    if (it.next().getCarrierId() == this.mTargetWifiConfig.carrierId) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean canDisconnect() {
        return getConnectedState() == 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x005a, code lost:

       if (((java.util.ArrayList) r5.mTargetSecurityTypes).contains(4) != false) goto L28;
    */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean canEasyConnect() {
        /*
            r5 = this;
            monitor-enter(r5)
            com.android.wifitrackerlib.WifiTrackerInjector r0 = r5.mInjector     // Catch: java.lang.Throwable -> L3c
            boolean r0 = r0.mIsDemoMode     // Catch: java.lang.Throwable -> L3c
            r1 = 0
            if (r0 == 0) goto La
            monitor-exit(r5)
            return r1
        La:
            android.net.wifi.WifiConfiguration r0 = r5.getWifiConfiguration()     // Catch: java.lang.Throwable -> L3c
            if (r0 != 0) goto L12
            monitor-exit(r5)
            return r1
        L12:
            android.net.wifi.WifiManager r2 = r5.mWifiManager     // Catch: java.lang.Throwable -> L3c
            boolean r2 = r2.isEasyConnectSupported()     // Catch: java.lang.Throwable -> L3c
            if (r2 != 0) goto L1c
            monitor-exit(r5)
            return r1
        L1c:
            int r2 = androidx.core.os.BuildCompat.$r8$clinit     // Catch: java.lang.Throwable -> L3c
            android.os.UserManager r2 = r5.mUserManager     // Catch: java.lang.Throwable -> L3c
            java.lang.String r3 = "no_sharing_admin_configured_wifi"
            int r4 = r0.creatorUid     // Catch: java.lang.Throwable -> L3c
            android.os.UserHandle r4 = android.os.UserHandle.getUserHandleForUid(r4)     // Catch: java.lang.Throwable -> L3c
            boolean r2 = r2.hasUserRestrictionForUser(r3, r4)     // Catch: java.lang.Throwable -> L3c
            if (r2 == 0) goto L3e
            int r2 = r0.creatorUid     // Catch: java.lang.Throwable -> L3c
            java.lang.String r0 = r0.creatorName     // Catch: java.lang.Throwable -> L3c
            android.content.Context r3 = r5.mContext     // Catch: java.lang.Throwable -> L3c
            boolean r0 = com.android.wifitrackerlib.Utils.isDeviceOrProfileOwner(r3, r2, r0)     // Catch: java.lang.Throwable -> L3c
            if (r0 == 0) goto L3e
            monitor-exit(r5)
            return r1
        L3c:
            r0 = move-exception
            goto L5f
        L3e:
            java.util.List r0 = r5.mTargetSecurityTypes     // Catch: java.lang.Throwable -> L3c
            r2 = 2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Throwable -> L3c
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: java.lang.Throwable -> L3c
            boolean r0 = r0.contains(r2)     // Catch: java.lang.Throwable -> L3c
            if (r0 != 0) goto L5c
            java.util.List r0 = r5.mTargetSecurityTypes     // Catch: java.lang.Throwable -> L3c
            r2 = 4
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Throwable -> L3c
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: java.lang.Throwable -> L3c
            boolean r0 = r0.contains(r2)     // Catch: java.lang.Throwable -> L3c
            if (r0 == 0) goto L5d
        L5c:
            r1 = 1
        L5d:
            monitor-exit(r5)
            return r1
        L5f:
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.StandardWifiEntry.canEasyConnect():boolean");
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public boolean canForget() {
        return getWifiConfiguration() != null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public boolean canSetAutoJoinEnabled() {
        return isSaved() || isSuggestion();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public boolean canSetMeteredChoice() {
        return getWifiConfiguration() != null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public boolean canSetPrivacy() {
        return isSaved();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean canShare() {
        if (this.mInjector.mIsDemoMode) {
            return false;
        }
        WifiConfiguration wifiConfiguration = getWifiConfiguration();
        if (wifiConfiguration == null) {
            return false;
        }
        int i = BuildCompat.$r8$clinit;
        if (this.mUserManager.hasUserRestrictionForUser(
                "no_sharing_admin_configured_wifi",
                UserHandle.getUserHandleForUid(wifiConfiguration.creatorUid))) {
            if (Utils.isDeviceOrProfileOwner(
                    this.mContext, wifiConfiguration.creatorUid, wifiConfiguration.creatorName)) {
                return false;
            }
        }
        Iterator it = ((ArrayList) this.mTargetSecurityTypes).iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (intValue == 0 || intValue == 1 || intValue == 2 || intValue == 4 || intValue == 6) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canSignIn() {
        boolean z;
        NetworkCapabilities networkCapabilities;
        if (this.mNetwork != null && (networkCapabilities = this.mNetworkCapabilities) != null) {
            z = networkCapabilities.hasCapability(17);
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x016e  */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void connect(
            final com.android.wifitrackerlib.WifiEntry.ConnectCallback r9) {
        /*
            Method dump skipped, instructions count: 440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.StandardWifiEntry.connect(com.android.wifitrackerlib.WifiEntry$ConnectCallback):void");
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean connectionInfoMatches(WifiInfo wifiInfo) {
        if (!wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            Iterator it = ((ArrayMap) this.mMatchingWifiConfigs).values().iterator();
            while (it.hasNext()) {
                if (((WifiConfiguration) it.next()).networkId == wifiInfo.getNetworkId()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void disconnect(
            final WifiEntry.DisconnectCallback disconnectCallback) {
        if (canDisconnect()) {
            this.mCalledDisconnect = true;
            this.mDisconnectCallback = disconnectCallback;
            this.mCallbackHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            StandardWifiEntry standardWifiEntry = StandardWifiEntry.this;
                            WifiEntry.DisconnectCallback disconnectCallback2 = disconnectCallback;
                            if (disconnectCallback2 == null) {
                                standardWifiEntry.getClass();
                            } else if (standardWifiEntry.mCalledDisconnect) {
                                disconnectCallback2.onDisconnectResult(1);
                            }
                        }
                    },
                    10000L);
            Context context = this.mContext;
            ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                    .reportIssue(
                            100,
                            WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                    -1,
                                    "disconnect",
                                    context.getPackageManager().getNameForUid(context.getUserId()),
                                    context.getPackageName()));
            this.mWifiManager.disableEphemeralNetwork("\"" + this.mKey.mScanResultKey.mSsid + "\"");
            this.mWifiManager.disconnect();
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized void forget(WifiEntry.ForgetCallback forgetCallback) {
        if (canForget()) {
            this.mForgetCallback = forgetCallback;
            Context context = this.mContext;
            String packageName = context.getPackageName();
            ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                    .reportIssue(
                            102,
                            WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                    this.mTargetWifiConfig.networkId,
                                    "forget",
                                    context.getPackageManager().getNameForUid(context.getUserId()),
                                    packageName));
            this.mWifiManager.forget(
                    this.mTargetWifiConfig.networkId, new WifiEntry.ForgetActionListener(this, 0));
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getBandString() {
        return Utils.getBandString(this.mContext, this.mBand);
    }

    public final String getBandSummary$1(int i, WifiInfo wifiInfo) {
        String stringJoiner;
        if (i == 2) {
            setBand(wifiInfo.getFrequency());
            return getBandString();
        }
        if (((ArrayList) this.mTargetScanResults).isEmpty()) {
            return getBandString();
        }
        Context context = this.mContext;
        ScanResult scanResult = (ScanResult) ((ArrayList) this.mTargetScanResults).get(0);
        List list = Utils.defaultSsidList;
        synchronized (Utils.class) {
            try {
                StringJoiner stringJoiner2 = new StringJoiner(" + ");
                int wifiStandard = scanResult.getWifiStandard();
                int i2 = BuildCompat.$r8$clinit;
                if (wifiStandard == 8) {
                    Iterator<MloLink> it = scanResult.getAffiliatedMloLinks().iterator();
                    while (it.hasNext()) {
                        int band = it.next().getBand();
                        int i3 = 1;
                        if (band == 1) {
                            i3 = 0;
                        } else if (band != 2) {
                            i3 = band != 8 ? band != 16 ? -1 : 3 : 2;
                        }
                        stringJoiner2.add(Utils.getBandString(context, i3));
                    }
                }
                stringJoiner = stringJoiner2.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        return TextUtils.isEmpty(stringJoiner) ? getBandString() : stringJoiner;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized WifiEntry.CertificateInfo getCertificateInfo() {
        WifiEnterpriseConfig wifiEnterpriseConfig;
        WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
        if (wifiConfiguration != null
                && (wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig) != null) {
            return Utils.getCertificateInfo(wifiEnterpriseConfig);
        }
        return null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getMacAddress() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo != null) {
            String macAddress = wifiInfo.getMacAddress();
            if (!TextUtils.isEmpty(macAddress)
                    && !TextUtils.equals(macAddress, "02:00:00:00:00:00")) {
                return macAddress;
            }
        }
        if (this.mTargetWifiConfig != null && getPrivacy() == 1) {
            return this.mTargetWifiConfig.getRandomizedMacAddress().toString();
        }
        String[] factoryMacAddresses = this.mWifiManager.getFactoryMacAddresses();
        if (factoryMacAddresses.length <= 0) {
            return null;
        }
        return factoryMacAddresses[0];
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized int getMeteredChoice() {
        WifiConfiguration wifiConfiguration;
        if (!isSuggestion() && (wifiConfiguration = this.mTargetWifiConfig) != null) {
            int i = wifiConfiguration.meteredOverride;
            if (i == 1) {
                return 1;
            }
            if (i == 2) {
                return 2;
            }
        }
        return 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getNetworkSelectionDescription() {
        return Utils.getNetworkSelectionDescription(getWifiConfiguration());
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized int getPrivacy() {
        WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
        if (wifiConfiguration != null) {
            if (wifiConfiguration.macRandomizationSetting == 0) {
                return 0;
            }
        }
        return 1;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getScanResultDescription() {
        if (((ArrayList) this.mTargetScanResults).size() == 0) {
            return ApnSettings.MVNO_NONE;
        }
        return "["
                + getScanResultDescription(2400, KnoxAiManagerInternal.CONN_MAX_WAIT_TIME)
                + ";"
                + getScanResultDescription(4900, 5900)
                + ";"
                + getScanResultDescription(5925, 7125)
                + ";"
                + getScanResultDescription(58320, 70200)
                + "]";
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSecurityString(boolean z) {
        try {
            if (((ArrayList) this.mTargetSecurityTypes).size() == 0) {
                return z
                        ? ApnSettings.MVNO_NONE
                        : this.mContext.getString(R.string.wifitrackerlib_wifi_security_none);
            }
            if (this.mIsPskSaeTransitionMode) {
                return z
                        ? this.mContext.getString(
                                R.string.wifitrackerlib_wifi_security_short_wpa2_wpa3)
                        : this.mContext.getString(R.string.wifitrackerlib_wifi_security_wpa2_wpa3);
            }
            if (((ArrayList) this.mTargetSecurityTypes).size() == 1) {
                int intValue =
                        ((Integer) ((ArrayList) this.mTargetSecurityTypes).get(0)).intValue();
                if (intValue == 9) {
                    return z
                            ? this.mContext.getString(
                                    R.string.wifitrackerlib_wifi_security_short_eap_wpa3)
                            : this.mContext.getString(
                                    R.string.wifitrackerlib_wifi_security_eap_wpa3);
                }
                switch (intValue) {
                    case 0:
                        return z
                                ? ApnSettings.MVNO_NONE
                                : this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_none);
                    case 1:
                        return this.mContext.getString(R.string.wifitrackerlib_wifi_security_wep);
                    case 2:
                        return z
                                ? this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_short_wpa_wpa2)
                                : this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_wpa_wpa2);
                    case 3:
                        if (this.mSupportWpaEap) {
                            return z
                                    ? this.mContext.getString(
                                            R.string
                                                    .wifitrackerlib_wifi_security_short_eap_wpa_wpa2)
                                    : this.mContext.getString(
                                            R.string.wifitrackerlib_wifi_security_eap_wpa_wpa2);
                        }
                        return z
                                ? this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_short_eap_wpa2_wpa3)
                                : this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_eap_wpa2_wpa3);
                    case 4:
                        return z
                                ? this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_short_sae)
                                : this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_sae);
                    case 5:
                        return z
                                ? this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_short_eap_suiteb)
                                : this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_eap_suiteb);
                    case 6:
                        return z
                                ? this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_short_owe)
                                : this.mContext.getString(
                                        R.string.wifitrackerlib_wifi_security_owe);
                }
            }
            if (((ArrayList) this.mTargetSecurityTypes).size() == 2) {
                if (((ArrayList) this.mTargetSecurityTypes).contains(0)
                        && ((ArrayList) this.mTargetSecurityTypes).contains(6)) {
                    return z
                            ? this.mContext.getString(R.string.wifi_security_short_none_owe)
                            : this.mContext.getString(R.string.wifi_security_none_owe);
                }
                if (((ArrayList) this.mTargetSecurityTypes).contains(2)
                        && ((ArrayList) this.mTargetSecurityTypes).contains(4)) {
                    return z
                            ? this.mContext.getString(
                                    R.string.wifitrackerlib_wifi_security_short_wpa2_wpa3)
                            : this.mContext.getString(
                                    R.string.wifitrackerlib_wifi_security_wpa2_wpa3);
                }
                if (((ArrayList) this.mTargetSecurityTypes).contains(3)
                        && ((ArrayList) this.mTargetSecurityTypes).contains(9)) {
                    return z
                            ? this.mContext.getString(
                                    R.string.wifitrackerlib_wifi_security_short_eap_wpa_wpa2_wpa3)
                            : this.mContext.getString(
                                    R.string.wifitrackerlib_wifi_security_eap_wpa_wpa2_wpa3);
                }
            }
            Log.e(
                    "StandardWifiEntry",
                    "Couldn't get string for security types: " + this.mTargetSecurityTypes);
            return z
                    ? ApnSettings.MVNO_NONE
                    : this.mContext.getString(R.string.wifitrackerlib_wifi_security_none);
        } finally {
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized List getSecurityTypes() {
        return new ArrayList(this.mTargetSecurityTypes);
    }

    public final SemWifiManager getSemWifiManager$2() {
        if (this.mSemWifiManager == null) {
            this.mSemWifiManager =
                    (SemWifiManager)
                            this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        }
        return this.mSemWifiManager;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getSsid() {
        return this.mKey.mScanResultKey.mSsid;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getStandardString() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo != null) {
            return Utils.getStandardString(this.mContext, wifiInfo.getWifiStandard());
        }
        if (((ArrayList) this.mTargetScanResults).isEmpty()) {
            return ApnSettings.MVNO_NONE;
        }
        return Utils.getStandardString(
                this.mContext,
                ((ScanResult) ((ArrayList) this.mTargetScanResults).get(0)).getWifiStandard());
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x014a A[Catch: all -> 0x0014, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:10:0x0017, B:14:0x0031, B:15:0x0064, B:17:0x0068, B:18:0x0071, B:20:0x0077, B:22:0x0080, B:24:0x008c, B:25:0x008f, B:28:0x0095, B:30:0x009b, B:32:0x00a7, B:33:0x00aa, B:35:0x00b0, B:37:0x00b9, B:39:0x00bf, B:41:0x00c5, B:43:0x00cb, B:46:0x00d0, B:55:0x00e1, B:56:0x00e9, B:57:0x00f1, B:58:0x00f9, B:59:0x0100, B:61:0x0106, B:62:0x0109, B:64:0x010f, B:66:0x0115, B:68:0x011b, B:70:0x011f, B:73:0x0126, B:75:0x012a, B:76:0x0144, B:78:0x014a, B:79:0x0136, B:82:0x014f, B:84:0x0155, B:86:0x0169, B:87:0x016d, B:89:0x0173, B:90:0x0177, B:92:0x0183, B:93:0x0186, B:95:0x018e, B:97:0x0194, B:100:0x019b, B:102:0x01a4, B:103:0x01a9, B:105:0x01af, B:106:0x01b7, B:108:0x01bd, B:109:0x01c1, B:111:0x01c7, B:113:0x01cd, B:114:0x01e3, B:116:0x01ef, B:117:0x01f2, B:120:0x0044, B:121:0x0051, B:122:0x005a), top: B:3:0x0003 }] */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.lang.String getSummary(boolean r10) {
        /*
            Method dump skipped, instructions count: 506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.StandardWifiEntry.getSummary(boolean):java.lang.String");
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getTitle() {
        return this.mKey.mScanResultKey.mSsid;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized WifiConfiguration getWifiConfiguration() {
        return this.mTargetWifiConfig;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean hasAdminRestrictions() {
        try {
            if (this.mHasAddConfigUserRestriction) {
                if (!isSaved()) {
                    if (isSuggestion()) {}
                    return true;
                }
            }
            if (!this.mIsAdminRestricted) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean isAutoJoinEnabled() {
        WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
        if (wifiConfiguration == null) {
            return true;
        }
        return wifiConfiguration.allowAutojoin;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000e, code lost:

       if (r0.meteredHint != false) goto L13;
    */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isMetered() {
        /*
            r2 = this;
            monitor-enter(r2)
            int r0 = r2.getMeteredChoice()     // Catch: java.lang.Throwable -> L11
            r1 = 1
            if (r0 == r1) goto L14
            android.net.wifi.WifiConfiguration r0 = r2.mTargetWifiConfig     // Catch: java.lang.Throwable -> L11
            if (r0 == 0) goto L13
            boolean r0 = r0.meteredHint     // Catch: java.lang.Throwable -> L11
            if (r0 == 0) goto L13
            goto L14
        L11:
            r0 = move-exception
            goto L16
        L13:
            r1 = 0
        L14:
            monitor-exit(r2)
            return r1
        L16:
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.StandardWifiEntry.isMetered():boolean");
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean isSaved() {
        return this.mTargetWifiConfig != null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean isSuggestion() {
        boolean z;
        WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
        if (wifiConfiguration != null) {
            z = wifiConfiguration.fromWifiNetworkSuggestion;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void onNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        super.onNetworkCapabilitiesChanged(network, networkCapabilities);
        canSignIn();
    }

    public final void refreshTargetWifiConfig() {
        for (WifiConfiguration wifiConfiguration :
                this.mWifiManager.getPrivilegedConfiguredNetworks()) {
            if (wifiConfiguration.networkId == this.mTargetWifiConfig.networkId) {
                this.mTargetWifiConfig = wifiConfiguration;
                return;
            }
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean semCanShowPassword() {
        WifiConfiguration wifiConfiguration;
        if (this.mInjector.mIsDemoMode || (wifiConfiguration = getWifiConfiguration()) == null) {
            return false;
        }
        if ((isSaved() && isSuggestion()) || this.mSemFlags.isCarrierNetwork) {
            return false;
        }
        int i = BuildCompat.$r8$clinit;
        if (this.mUserManager.hasUserRestrictionForUser(
                "no_sharing_admin_configured_wifi",
                UserHandle.getUserHandleForUid(wifiConfiguration.creatorUid))) {
            return !Utils.isDeviceOrProfileOwner(
                    this.mContext, wifiConfiguration.creatorUid, wifiConfiguration.creatorName);
        }
        return true;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized List semGetAllScanResult() {
        return this.mTargetScanResults;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized int semGetFrequency(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        Iterator it = ((ArrayList) this.mTargetScanResults).iterator();
        while (it.hasNext()) {
            ScanResult scanResult = (ScanResult) it.next();
            if (str.equals(scanResult.BSSID)) {
                return scanResult.frequency;
            }
        }
        return 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String semGetSecurityString(final String str) {
        ScanResult scanResult;
        try {
            if (!TextUtils.isEmpty(str)
                    && (scanResult =
                                    (ScanResult)
                                            this.mTargetScanResults.stream()
                                                    .filter(
                                                            new Predicate() { // from class:
                                                                              // com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda8
                                                                @Override // java.util.function.Predicate
                                                                public final boolean test(
                                                                        Object obj) {
                                                                    return str.equals(
                                                                            ((ScanResult) obj)
                                                                                    .BSSID);
                                                                }
                                                            })
                                                    .findFirst()
                                                    .orElse(null))
                            != null) {
                this.mIsPskSaeTransitionMode =
                        scanResult.capabilities.contains("PSK")
                                && scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE);
                this.mIsOweTransitionMode = scanResult.capabilities.contains("OWE_TRANSITION");
                this.mSupportWpaEap = scanResult.capabilities.contains("WPA-EAP");
            }
        } catch (Throwable th) {
            throw th;
        }
        return getSecurityString(false);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean semIs6GHzOnly() {
        return this.mTargetScanResults.stream()
                .filter(new StandardWifiEntry$$ExternalSyntheticLambda7())
                .findAny()
                .isEmpty();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean semIsAdminRestricted() {
        return hasAdminRestrictions();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean semIsMobileHotspot() {
        semUpdateFlags(Utils.getBestScanResultByLevel(this.mTargetScanResults));
        return this.mSemFlags.isSamsungMobileHotspot;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean semMatches(WifiConfiguration wifiConfiguration) {
        WifiConfiguration wifiConfiguration2;
        try {
            if (wifiConfiguration.isPasspoint()) {
                return false;
            }
            if (getSsid().equals(SemWifiUtils.removeDoubleQuotes(wifiConfiguration.SSID))
                    && ((wifiConfiguration2 = this.mTargetWifiConfig) == null
                            || wifiConfiguration2.shared == wifiConfiguration.shared)) {
                List securityTypesFromWifiConfiguration =
                        Utils.getSecurityTypesFromWifiConfiguration(wifiConfiguration);
                if (this.mIsPskSaeTransitionMode
                        && ((securityTypesFromWifiConfiguration.contains(4)
                                        && SemWifiEntryFlags.isWpa3SaeSupported(this.mWifiManager))
                                || securityTypesFromWifiConfiguration.contains(2))) {
                    return true;
                }
                if (this.mIsOweTransitionMode
                        && ((securityTypesFromWifiConfiguration.contains(6)
                                        && SemWifiEntryFlags.isEnhancedOpenSupported(
                                                this.mWifiManager))
                                || securityTypesFromWifiConfiguration.contains(0))) {
                    return true;
                }
                return new HashSet(Utils.getSecurityTypesFromWifiConfiguration(wifiConfiguration))
                        .containsAll(this.mTargetSecurityTypes);
            }
            return false;
        } finally {
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void semUpdateConfig(WifiConfiguration wifiConfiguration) {
        this.mTargetWifiConfig = wifiConfiguration;
        notifyOnUpdated();
    }

    public final synchronized void semUpdateScores(WifiQoSScoredCache wifiQoSScoredCache) {
        WifiScoredNetwork wifiScoredNetwork;
        Iterator it = ((ArrayList) this.mTargetScanResults).iterator();
        while (it.hasNext()) {
            ScanResult scanResult = (ScanResult) it.next();
            wifiQoSScoredCache.getClass();
            String str = scanResult.BSSID;
            if (TextUtils.isEmpty(str)) {
                wifiScoredNetwork = null;
            } else {
                synchronized (wifiQoSScoredCache.mLock) {
                    wifiScoredNetwork =
                            (WifiScoredNetwork) ((HashMap) wifiQoSScoredCache.mCache).get(str);
                }
            }
            if (wifiScoredNetwork != null) {
                ((HashMap) this.mSemFlags.qosScoredNetworkCache)
                        .put(scanResult.BSSID, wifiScoredNetwork);
            }
        }
    }

    public final synchronized void semUpdateSemWifiConfig(Map map) {
        try {
            WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
            if (wifiConfiguration != null) {
                semUpdateFlags(
                        (SemWifiConfiguration) ((HashMap) map).get(wifiConfiguration.getKey()));
            }
            notifyOnUpdated();
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized void setAutoJoinEnabled(boolean z) {
        if (this.mTargetWifiConfig != null && canSetAutoJoinEnabled()) {
            this.mWifiManager.allowAutojoin(this.mTargetWifiConfig.networkId, z);
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized void setMeteredChoice(int i) {
        try {
            if (canSetMeteredChoice()) {
                refreshTargetWifiConfig();
                if (i == 0) {
                    this.mTargetWifiConfig.meteredOverride = 0;
                } else if (i == 1) {
                    this.mTargetWifiConfig.meteredOverride = 1;
                } else if (i == 2) {
                    this.mTargetWifiConfig.meteredOverride = 2;
                }
                this.mWifiManager.save(this.mTargetWifiConfig, null);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized void setPrivacy(int i) {
        if (canSetPrivacy()) {
            refreshTargetWifiConfig();
            WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
            wifiConfiguration.macRandomizationSetting = i == 1 ? 3 : 0;
            this.mWifiManager.save(wifiConfiguration, null);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0031, code lost:

       if (r0.getDisableReasonCounter(5) > 0) goto L24;
    */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean shouldEditBeforeConnect() {
        /*
            r3 = this;
            monitor-enter(r3)
            android.net.wifi.WifiConfiguration r0 = r3.getWifiConfiguration()     // Catch: java.lang.Throwable -> L1b
            r1 = 0
            if (r0 != 0) goto La
            monitor-exit(r3)
            return r1
        La:
            android.net.wifi.WifiConfiguration$NetworkSelectionStatus r0 = r0.getNetworkSelectionStatus()     // Catch: java.lang.Throwable -> L1b
            int r2 = r0.getNetworkSelectionStatus()     // Catch: java.lang.Throwable -> L1b
            if (r2 != 0) goto L1d
            boolean r2 = r0.hasEverConnected()     // Catch: java.lang.Throwable -> L1b
            if (r2 != 0) goto L34
            goto L1d
        L1b:
            r0 = move-exception
            goto L39
        L1d:
            r2 = 2
            int r2 = r0.getDisableReasonCounter(r2)     // Catch: java.lang.Throwable -> L1b
            if (r2 > 0) goto L36
            r2 = 8
            int r2 = r0.getDisableReasonCounter(r2)     // Catch: java.lang.Throwable -> L1b
            if (r2 > 0) goto L36
            r2 = 5
            int r0 = r0.getDisableReasonCounter(r2)     // Catch: java.lang.Throwable -> L1b
            if (r0 <= 0) goto L34
            goto L36
        L34:
            monitor-exit(r3)
            return r1
        L36:
            monitor-exit(r3)
            r3 = 1
            return r3
        L39:
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.StandardWifiEntry.shouldEditBeforeConnect():boolean");
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final void signIn() {
        if (canSignIn()) {
            ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class))
                    .startCaptivePortalApp(this.mNetwork);
        }
    }

    public final void updateAdminRestrictions() {
        int i;
        int i2 = BuildCompat.$r8$clinit;
        UserManager userManager = this.mUserManager;
        if (userManager != null) {
            this.mHasAddConfigUserRestriction =
                    userManager.hasUserRestriction("no_add_wifi_config");
        }
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            int minimumRequiredWifiSecurityLevel =
                    devicePolicyManager.getMinimumRequiredWifiSecurityLevel();
            if (minimumRequiredWifiSecurityLevel != 0) {
                Iterator it = ((ArrayList) getSecurityTypes()).iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    List list = Utils.defaultSsidList;
                    switch (intValue) {
                        case 0:
                        case 6:
                            i = 0;
                            break;
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                            i = 1;
                            break;
                        case 3:
                        case 8:
                        case 9:
                        case 11:
                        case 12:
                            i = 2;
                            break;
                        case 5:
                            i = 3;
                            break;
                        case 10:
                        default:
                            i = -1;
                            break;
                    }
                    if (i != -1 && minimumRequiredWifiSecurityLevel <= i) {}
                }
                this.mIsAdminRestricted = true;
                return;
            }
            DevicePolicyManager devicePolicyManager2 = this.mDevicePolicyManager;
            int i3 = BuildCompat.$r8$clinit;
            WifiSsidPolicy wifiSsidPolicy = devicePolicyManager2.getWifiSsidPolicy();
            if (wifiSsidPolicy != null) {
                int policyType = wifiSsidPolicy.getPolicyType();
                Set<WifiSsid> ssids = wifiSsidPolicy.getSsids();
                if (policyType == 0
                        && !ssids.contains(
                                WifiSsid.fromBytes(getSsid().getBytes(StandardCharsets.UTF_8)))) {
                    this.mIsAdminRestricted = true;
                    return;
                } else if (policyType == 1
                        && ssids.contains(
                                WifiSsid.fromBytes(getSsid().getBytes(StandardCharsets.UTF_8)))) {
                    this.mIsAdminRestricted = true;
                    return;
                }
            }
        }
        this.mIsAdminRestricted = false;
    }

    public final synchronized void updateConfig(List list) {
        if (list == null) {
            try {
                list = Collections.emptyList();
            } catch (Throwable th) {
                throw th;
            }
        }
        ScanResultKey scanResultKey = this.mKey.mScanResultKey;
        String str = scanResultKey.mSsid;
        Set set = scanResultKey.mSecurityTypes;
        ((ArrayMap) this.mMatchingWifiConfigs).clear();
        Iterator it = list.iterator();
        while (true) {
            boolean z = true;
            if (it.hasNext()) {
                WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
                if (!TextUtils.equals(str, WifiInfo.sanitizeSsid(wifiConfiguration.SSID))) {
                    throw new IllegalArgumentException(
                            "Attempted to update with wrong SSID! Expected: "
                                    + str
                                    + ", Actual: "
                                    + WifiInfo.sanitizeSsid(wifiConfiguration.SSID)
                                    + ", Config: "
                                    + wifiConfiguration);
                }
                for (Integer num : Utils.getSecurityTypesFromWifiConfiguration(wifiConfiguration)) {
                    int intValue = num.intValue();
                    ArraySet arraySet = (ArraySet) set;
                    if (!arraySet.contains(num)) {
                        throw new IllegalArgumentException(
                                "Attempted to update with wrong security! Expected one of: "
                                        + arraySet
                                        + ", Actual: "
                                        + intValue
                                        + ", Config: "
                                        + wifiConfiguration);
                    }
                    if (intValue != 4
                            ? intValue != 5
                                    ? intValue != 6 ? true : this.mIsEnhancedOpenSupported
                                    : this.mIsWpa3SuiteBSupported
                            : this.mIsWpa3SaeSupported) {
                        ((ArrayMap) this.mMatchingWifiConfigs).put(num, wifiConfiguration);
                    }
                }
            } else {
                updateSecurityTypes();
                updateTargetScanResultInfo();
                WifiConfiguration wifiConfiguration2 = this.mTargetWifiConfig;
                if (wifiConfiguration2 != null) {
                    if (wifiConfiguration2.carrierId == -1
                            || !wifiConfiguration2.isEphemeral()
                            || !wifiConfiguration2.fromWifiNetworkSuggestion) {
                        z = false;
                    }
                    this.mSemFlags.isCarrierNetwork = z;
                }
                notifyOnUpdated();
            }
        }
    }

    public final synchronized void updateScanResultInfo(List list) {
        if (list == null) {
            try {
                list = new ArrayList();
            } catch (Throwable th) {
                throw th;
            }
        }
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        int i = 0;
        semWifiEntryFlags.wifiStandard = 0;
        semWifiEntryFlags.has6EStandard = false;
        semWifiEntryFlags.staCount = -1;
        String str = this.mKey.mScanResultKey.mSsid;
        for (ScanResult scanResult : list) {
            if (!TextUtils.equals(scanResult.SSID, str)) {
                throw new IllegalArgumentException(
                        "Attempted to update with wrong SSID! Expected: "
                                + str
                                + ", Actual: "
                                + scanResult.SSID
                                + ", ScanResult: "
                                + scanResult);
            }
            semUpdateFlags(scanResult);
            int i2 = scanResult.frequency;
            if (i < i2) {
                setBand(i2);
                this.mFrequency = i2;
                i = i2;
            }
        }
        ((ArrayMap) this.mMatchingScanResults).clear();
        Set set = this.mKey.mScanResultKey.mSecurityTypes;
        for (ScanResult scanResult2 : list) {
            Iterator it =
                    ((ArrayList) Utils.getSecurityTypesFromScanResult(scanResult2)).iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                int intValue = num.intValue();
                if (((ArraySet) set).contains(num)) {
                    if (intValue != 4
                            ? intValue != 5
                                    ? intValue != 6 ? true : this.mIsEnhancedOpenSupported
                                    : this.mIsWpa3SuiteBSupported
                            : this.mIsWpa3SaeSupported) {
                        if (!((ArrayMap) this.mMatchingScanResults).containsKey(num)) {
                            ((ArrayMap) this.mMatchingScanResults).put(num, new ArrayList());
                        }
                        ((List) ((ArrayMap) this.mMatchingScanResults).get(num)).add(scanResult2);
                    }
                }
            }
        }
        updateSecurityTypes();
        updateTargetScanResultInfo();
        notifyOnUpdated();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void updateSecurityTypes() {
        try {
            this.mTargetSecurityTypes.clear();
            WifiInfo wifiInfo = this.mWifiInfo;
            if (wifiInfo != null && wifiInfo.getCurrentSecurityType() != -1) {
                this.mTargetSecurityTypes.add(
                        Integer.valueOf(this.mWifiInfo.getCurrentSecurityType()));
            }
            Set keySet = this.mMatchingWifiConfigs.keySet();
            if (this.mTargetSecurityTypes.isEmpty() && this.mKey.mIsTargetingNewNetworks) {
                Set keySet2 = this.mMatchingScanResults.keySet();
                Iterator it = keySet.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        this.mTargetSecurityTypes.addAll(keySet2);
                        break;
                    }
                    Integer num = (Integer) it.next();
                    num.intValue();
                    if (keySet2.contains(num)) {
                        break;
                    }
                }
            }
            if (this.mTargetSecurityTypes.isEmpty()) {
                this.mTargetSecurityTypes.addAll(keySet);
            }
            if (this.mTargetSecurityTypes.isEmpty()) {
                this.mTargetSecurityTypes.addAll(this.mKey.mScanResultKey.mSecurityTypes);
            }
            this.mTargetWifiConfig =
                    (WifiConfiguration)
                            this.mMatchingWifiConfigs.get(
                                    Integer.valueOf(
                                            Utils.getSingleSecurityTypeFromMultipleSecurityTypes(
                                                    this.mTargetSecurityTypes)));
            ArraySet arraySet = new ArraySet();
            for (Integer num2 : this.mTargetSecurityTypes) {
                num2.intValue();
                if (this.mMatchingScanResults.containsKey(num2)) {
                    arraySet.addAll((Collection) this.mMatchingScanResults.get(num2));
                }
            }
            this.mTargetScanResults.clear();
            this.mTargetScanResults.addAll(arraySet);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void updateTargetScanResultInfo() {
        try {
            ScanResult bestScanResultByLevel =
                    Utils.getBestScanResultByLevel(this.mTargetScanResults);
            if (bestScanResultByLevel != null) {
                updateBestRssi(bestScanResultByLevel);
                this.mBssid = bestScanResultByLevel.BSSID;
            }
            if (getConnectedState() == 0) {
                int calculateSignalLevel =
                        bestScanResultByLevel != null
                                ? SemWifiUtils.calculateSignalLevel(this.mRssi)
                                : -1;
                this.mLevel = calculateSignalLevel;
                if (calculateSignalLevel == -1) {
                    this.mRssi = -127;
                }
            }
            semUpdateQoSInformation();
        } catch (Throwable th) {
            throw th;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StandardWifiEntryKey {
        public boolean mIsNetworkRequest;
        public boolean mIsTargetingNewNetworks;
        public ScanResultKey mScanResultKey;
        public String mSuggestionProfileKey;

        public StandardWifiEntryKey(ScanResultKey scanResultKey, boolean z) {
            this.mScanResultKey = scanResultKey;
            this.mIsTargetingNewNetworks = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || StandardWifiEntryKey.class != obj.getClass()) {
                return false;
            }
            StandardWifiEntryKey standardWifiEntryKey = (StandardWifiEntryKey) obj;
            return Objects.equals(this.mScanResultKey, standardWifiEntryKey.mScanResultKey)
                    && TextUtils.equals(
                            this.mSuggestionProfileKey, standardWifiEntryKey.mSuggestionProfileKey)
                    && this.mIsNetworkRequest == standardWifiEntryKey.mIsNetworkRequest;
        }

        public final int hashCode() {
            return Objects.hash(
                    this.mScanResultKey,
                    this.mSuggestionProfileKey,
                    Boolean.valueOf(this.mIsNetworkRequest));
        }

        public final String toString() {
            JSONObject jSONObject = new JSONObject();
            try {
                ScanResultKey scanResultKey = this.mScanResultKey;
                if (scanResultKey != null) {
                    jSONObject.put("SCAN_RESULT_KEY", scanResultKey.toString());
                }
                String str = this.mSuggestionProfileKey;
                if (str != null) {
                    jSONObject.put("SUGGESTION_PROFILE_KEY", str);
                }
                boolean z = this.mIsNetworkRequest;
                if (z) {
                    jSONObject.put("IS_NETWORK_REQUEST", z);
                }
                boolean z2 = this.mIsTargetingNewNetworks;
                if (z2) {
                    jSONObject.put("IS_TARGETING_NEW_NETWORKS", z2);
                }
            } catch (JSONException e) {
                Log.wtf(
                        "StandardWifiEntry",
                        "JSONException while converting StandardWifiEntryKey to string: " + e);
            }
            return "StandardWifiEntry:" + jSONObject.toString();
        }

        public StandardWifiEntryKey(WifiConfiguration wifiConfiguration, boolean z) {
            this.mIsTargetingNewNetworks = false;
            this.mScanResultKey =
                    new ScanResultKey(
                            WifiInfo.sanitizeSsid(wifiConfiguration.SSID),
                            Utils.getSecurityTypesFromWifiConfiguration(wifiConfiguration));
            if (wifiConfiguration.fromWifiNetworkSuggestion) {
                this.mSuggestionProfileKey =
                        new StringJoiner(",")
                                .add(wifiConfiguration.creatorName)
                                .add(String.valueOf(wifiConfiguration.carrierId))
                                .add(String.valueOf(wifiConfiguration.subscriptionId))
                                .toString();
            } else if (wifiConfiguration.fromWifiNetworkSpecifier) {
                this.mIsNetworkRequest = true;
            }
            this.mIsTargetingNewNetworks = z;
        }
    }

    public final synchronized String getScanResultDescription(final int i, final int i2) {
        final int i3 = 0;
        List list =
                (List)
                        this.mTargetScanResults.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                int i4 = i;
                                                int i5 = i2;
                                                int i6 = ((ScanResult) obj).frequency;
                                                return i6 >= i4 && i6 <= i5;
                                            }
                                        })
                                .sorted(
                                        Comparator.comparingInt(
                                                new ToIntFunction() { // from class:
                                                                      // com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda3
                                                    @Override // java.util.function.ToIntFunction
                                                    public final int applyAsInt(Object obj) {
                                                        ScanResult scanResult = (ScanResult) obj;
                                                        switch (i3) {
                                                            case 0:
                                                                return scanResult.level * (-1);
                                                            default:
                                                                return scanResult.level;
                                                        }
                                                    }
                                                }))
                                .collect(Collectors.toList());
        int size = list.size();
        if (size == 0) {
            return ApnSettings.MVNO_NONE;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(size);
        sb.append(")");
        if (size > 4) {
            final int i4 = 1;
            int asInt =
                    list.stream()
                            .mapToInt(
                                    new ToIntFunction() { // from class:
                                                          // com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda3
                                        @Override // java.util.function.ToIntFunction
                                        public final int applyAsInt(Object obj) {
                                            ScanResult scanResult = (ScanResult) obj;
                                            switch (i4) {
                                                case 0:
                                                    return scanResult.level * (-1);
                                                default:
                                                    return scanResult.level;
                                            }
                                        }
                                    })
                            .max()
                            .getAsInt();
            sb.append("max=");
            sb.append(asInt);
            sb.append(",");
        }
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        list.forEach(
                new Consumer() { // from class:
                                 // com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String sb2;
                        StandardWifiEntry standardWifiEntry = StandardWifiEntry.this;
                        StringBuilder sb3 = sb;
                        long j = elapsedRealtime;
                        ScanResult scanResult = (ScanResult) obj;
                        synchronized (standardWifiEntry) {
                            try {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(" \n{");
                                sb4.append(scanResult.BSSID);
                                WifiInfo wifiInfo = standardWifiEntry.mWifiInfo;
                                if (wifiInfo != null
                                        && scanResult.BSSID.equals(wifiInfo.getBSSID())) {
                                    sb4.append("*");
                                }
                                sb4.append("=");
                                sb4.append(scanResult.frequency);
                                sb4.append(",");
                                sb4.append(scanResult.level);
                                int wifiStandard = scanResult.getWifiStandard();
                                sb4.append(",");
                                sb4.append(
                                        Utils.getStandardString(
                                                standardWifiEntry.mContext, wifiStandard));
                                int i5 = BuildCompat.$r8$clinit;
                                if (wifiStandard == 8) {
                                    sb4.append(",mldMac=");
                                    sb4.append(scanResult.getApMldMacAddress());
                                    sb4.append(",linkId=");
                                    sb4.append(scanResult.getApMloLinkId());
                                    sb4.append(",affLinks=");
                                    StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
                                    for (MloLink mloLink : scanResult.getAffiliatedMloLinks()) {
                                        int band = mloLink.getBand();
                                        int i6 = 1;
                                        if (band != 1) {
                                            i6 = 2;
                                            if (band != 2) {
                                                if (band != 8) {
                                                    i6 = 16;
                                                    if (band != 16) {
                                                        Log.e(
                                                                "StandardWifiEntry",
                                                                "Unknown MLO link band: "
                                                                        + mloLink.getBand());
                                                        i6 = -1;
                                                    }
                                                } else {
                                                    i6 = 8;
                                                }
                                            }
                                        }
                                        stringJoiner.add(
                                                new StringJoiner(",", "{", "}")
                                                        .add(
                                                                "apMacAddr="
                                                                        + mloLink.getApMacAddress())
                                                        .add(
                                                                "freq="
                                                                        + ScanResult
                                                                                .convertChannelToFrequencyMhzIfSupported(
                                                                                        mloLink
                                                                                                .getChannel(),
                                                                                        i6))
                                                        .toString());
                                    }
                                    sb4.append(stringJoiner.toString());
                                }
                                int i7 = ((int) (j - (scanResult.timestamp / 1000))) / 1000;
                                sb4.append(",");
                                sb4.append(i7);
                                sb4.append("s");
                                sb4.append("}");
                                sb2 = sb4.toString();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        sb3.append(sb2);
                    }
                });
        return sb.toString();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScanResultKey {
        public Set mSecurityTypes;
        public final String mSsid;

        public ScanResultKey(String str, List list) {
            this.mSecurityTypes = new ArraySet();
            this.mSsid = str;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                int intValue = num.intValue();
                if (intValue == 0) {
                    this.mSecurityTypes.add(6);
                } else if (intValue == 6) {
                    this.mSecurityTypes.add(0);
                } else if (intValue == 9) {
                    this.mSecurityTypes.add(3);
                } else if (intValue == 2) {
                    this.mSecurityTypes.add(4);
                } else if (intValue == 3) {
                    this.mSecurityTypes.add(9);
                } else if (intValue == 4) {
                    this.mSecurityTypes.add(2);
                } else if (intValue != 11 && intValue != 12) {
                }
                this.mSecurityTypes.add(num);
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || ScanResultKey.class != obj.getClass()) {
                return false;
            }
            ScanResultKey scanResultKey = (ScanResultKey) obj;
            return TextUtils.equals(this.mSsid, scanResultKey.mSsid)
                    && this.mSecurityTypes.equals(scanResultKey.mSecurityTypes);
        }

        public final int hashCode() {
            return Objects.hash(this.mSsid, this.mSecurityTypes);
        }

        public final String toString() {
            JSONObject jSONObject = new JSONObject();
            try {
                String str = this.mSsid;
                if (str != null) {
                    jSONObject.put("SSID", str);
                }
                if (!this.mSecurityTypes.isEmpty()) {
                    JSONArray jSONArray = new JSONArray();
                    Iterator it = this.mSecurityTypes.iterator();
                    while (it.hasNext()) {
                        jSONArray.put(((Integer) it.next()).intValue());
                    }
                    jSONObject.put("SECURITY_TYPES", jSONArray);
                }
            } catch (JSONException e) {
                Log.e(
                        "StandardWifiEntry",
                        "JSONException while converting ScanResultKey to string: " + e);
            }
            return jSONObject.toString();
        }

        public ScanResultKey(ScanResult scanResult) {
            this(scanResult.SSID, Utils.getSecurityTypesFromScanResult(scanResult));
        }

        public ScanResultKey(String str) {
            this.mSecurityTypes = new ArraySet();
            try {
                JSONObject jSONObject = new JSONObject(str);
                this.mSsid = jSONObject.getString("SSID");
                JSONArray jSONArray = jSONObject.getJSONArray("SECURITY_TYPES");
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.mSecurityTypes.add(Integer.valueOf(jSONArray.getInt(i)));
                }
            } catch (JSONException e) {
                Log.wtf(
                        "StandardWifiEntry",
                        "JSONException while constructing ScanResultKey from string: " + e);
            }
        }
    }

    public StandardWifiEntry(
            WifiTrackerInjector wifiTrackerInjector,
            Handler handler,
            StandardWifiEntryKey standardWifiEntryKey,
            List list,
            List list2,
            WifiManager wifiManager,
            boolean z) {
        this(wifiTrackerInjector, handler, standardWifiEntryKey, wifiManager, z);
        if (list != null && !list.isEmpty()) {
            updateConfig(list);
        }
        if (list2 == null || list2.isEmpty()) {
            return;
        }
        updateScanResultInfo(list2);
    }
}
