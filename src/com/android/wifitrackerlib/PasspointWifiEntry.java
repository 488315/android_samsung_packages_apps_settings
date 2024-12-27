package com.android.wifitrackerlib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.os.BuildCompat;
import androidx.core.util.Preconditions;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PasspointWifiEntry extends WifiEntry implements WifiEntry.WifiEntryCallback {
    public final List mCurrentHomeScanResults;
    public final List mCurrentRoamingScanResults;
    public final String mFqdn;
    public final String mFriendlyName;
    public final String mKey;
    public int mMeteredOverride;
    public OsuWifiEntry mOsuWifiEntry;
    public PasspointConfiguration mPasspointConfig;
    public SemWifiManager mSemWifiManager;
    public boolean mShouldAutoOpenCaptivePortal;
    public final boolean mShowBandSummary;
    public long mSubscriptionExpirationTimeInMillis;
    public final List mTargetScanResults;
    public List mTargetSecurityTypes;
    public final String mUniqueId;
    public WifiConfiguration mWifiConfig;

    public PasspointWifiEntry(
            WifiTrackerInjector wifiTrackerInjector,
            Handler handler,
            PasspointConfiguration passpointConfiguration,
            WifiManager wifiManager,
            boolean z) {
        super(wifiTrackerInjector, handler, wifiManager);
        boolean z2;
        Context context;
        this.mCurrentHomeScanResults = new ArrayList();
        this.mCurrentRoamingScanResults = new ArrayList();
        this.mTargetSecurityTypes = Arrays.asList(11, 12);
        boolean z3 = false;
        this.mShouldAutoOpenCaptivePortal = false;
        this.mShowBandSummary = false;
        this.mTargetScanResults = new ArrayList();
        this.mMeteredOverride = 0;
        Preconditions.checkNotNull(
                passpointConfiguration, "Cannot construct with null PasspointConfiguration!");
        this.mPasspointConfig = passpointConfiguration;
        String uniqueId = passpointConfiguration.getUniqueId();
        this.mUniqueId = uniqueId;
        this.mKey = uniqueIdToPasspointWifiEntryKey(uniqueId);
        String fqdn = passpointConfiguration.getHomeSp().getFqdn();
        this.mFqdn = fqdn;
        Preconditions.checkNotNull(fqdn, "Cannot construct with null PasspointConfiguration FQDN!");
        this.mFriendlyName = passpointConfiguration.getHomeSp().getFriendlyName();
        this.mSubscriptionExpirationTimeInMillis =
                passpointConfiguration.getSubscriptionExpirationTimeMillis();
        this.mMeteredOverride = this.mPasspointConfig.getMeteredOverride();
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
                semUpdateFlags(this.mPasspointConfig);
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
        semUpdateFlags(this.mPasspointConfig);
    }

    public static String uniqueIdToPasspointWifiEntryKey(String str) {
        Preconditions.checkNotNull(str, "Cannot create key with null unique id!");
        return "PasspointWifiEntry:".concat(str);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canConnect() {
        boolean z = false;
        if (isExpired()) {
            OsuWifiEntry osuWifiEntry = this.mOsuWifiEntry;
            if (osuWifiEntry != null && osuWifiEntry.canConnect()) {
                z = true;
            }
            return z;
        }
        if (this.mLevel != -1 && getConnectedState() == 0 && this.mWifiConfig != null) {
            z = true;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean canDisconnect() {
        return getConnectedState() == 2;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canForget() {
        boolean z;
        if (!isSuggestion()) {
            z = this.mPasspointConfig != null;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canSetAutoJoinEnabled() {
        boolean z;
        if (this.mPasspointConfig == null) {
            z = this.mWifiConfig != null;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canSetMeteredChoice() {
        boolean z;
        if (!isSuggestion()) {
            z = this.mPasspointConfig != null;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canSetPrivacy() {
        boolean z;
        if (!isSuggestion()) {
            z = this.mPasspointConfig != null;
        }
        return z;
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

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
        OsuWifiEntry osuWifiEntry;
        if (isExpired() && (osuWifiEntry = this.mOsuWifiEntry) != null) {
            osuWifiEntry.connect(connectCallback);
            return;
        }
        this.mShouldAutoOpenCaptivePortal = true;
        this.mConnectCallback = connectCallback;
        if (this.mWifiConfig == null) {
            new WifiEntry.ForgetActionListener(this, 1).onFailure(0);
        }
        this.mWifiManager.stopRestrictingAutoJoinToSubscriptionId();
        new WifiIssueDetectorUtil(this.mContext).reportConnectNetwork(this.mWifiConfig);
        this.mWifiManager.connect(this.mWifiConfig, new WifiEntry.ForgetActionListener(this, 1));
        if (this.mWifiConfig != null) {
            if (this.mSemWifiManager == null) {
                this.mSemWifiManager =
                        (SemWifiManager)
                                this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            }
            SemWifiManager semWifiManager = this.mSemWifiManager;
            WifiConfiguration wifiConfiguration = this.mWifiConfig;
            semWifiManager.notifyConnect(wifiConfiguration.networkId, wifiConfiguration.getKey());
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean connectionInfoMatches(WifiInfo wifiInfo) {
        if (!wifiInfo.isPasspointAp()) {
            return false;
        }
        int i = BuildCompat.$r8$clinit;
        String CODENAME = Build.VERSION.CODENAME;
        Intrinsics.checkNotNullExpressionValue(CODENAME, "CODENAME");
        if (!"REL".equals(CODENAME)) {
            Locale locale = Locale.ROOT;
            String upperCase = CODENAME.toUpperCase(locale);
            Intrinsics.checkNotNullExpressionValue(
                    upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            String upperCase2 = "VanillaIceCream".toUpperCase(locale);
            Intrinsics.checkNotNullExpressionValue(
                    upperCase2, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            if (upperCase.compareTo(upperCase2) >= 0) {
                return TextUtils.equals(this.mUniqueId, wifiInfo.getPasspointUniqueId());
            }
        }
        return TextUtils.equals(wifiInfo.getPasspointFqdn(), this.mFqdn);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void disconnect(
            final WifiEntry.DisconnectCallback disconnectCallback) {
        if (canDisconnect()) {
            this.mCalledDisconnect = true;
            this.mDisconnectCallback = disconnectCallback;
            this.mCallbackHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.wifitrackerlib.PasspointWifiEntry$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PasspointWifiEntry passpointWifiEntry = PasspointWifiEntry.this;
                            WifiEntry.DisconnectCallback disconnectCallback2 = disconnectCallback;
                            if (disconnectCallback2 == null) {
                                passpointWifiEntry.getClass();
                            } else if (passpointWifiEntry.mCalledDisconnect) {
                                disconnectCallback2.onDisconnectResult(1);
                            }
                        }
                    },
                    10000L);
            Context context = this.mContext;
            String packageName = context.getPackageName();
            ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                    .reportIssue(
                            100,
                            WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                    -1,
                                    "disconnect",
                                    context.getPackageManager().getNameForUid(context.getUserId()),
                                    packageName));
            this.mWifiManager.disableEphemeralNetwork(this.mFqdn);
            this.mWifiManager.disconnect();
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void forget(WifiEntry.ForgetCallback forgetCallback) {
        if (canForget()) {
            this.mForgetCallback = forgetCallback;
            this.mWifiManager.removePasspointConfiguration(
                    this.mPasspointConfig.getHomeSp().getFqdn());
            new WifiEntry.ForgetActionListener(this, 0).onSuccess();
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getBandString() {
        return Utils.getBandString(this.mContext, this.mBand);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized int getConnectedState() {
        OsuWifiEntry osuWifiEntry;
        return (isExpired()
                        && super.getConnectedState() == 0
                        && (osuWifiEntry = this.mOsuWifiEntry) != null)
                ? osuWifiEntry.getConnectedState()
                : super.getConnectedState();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey;
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
        if (this.mWifiConfig != null && getPrivacy() == 1) {
            return this.mWifiConfig.getRandomizedMacAddress().toString();
        }
        String[] factoryMacAddresses = this.mWifiManager.getFactoryMacAddresses();
        if (factoryMacAddresses.length <= 0) {
            return null;
        }
        return factoryMacAddresses[0];
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized int getMeteredChoice() {
        int i = this.mMeteredOverride;
        if (i == 1) {
            return 1;
        }
        return i == 2 ? 2 : 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getNetworkSelectionDescription() {
        return Utils.getNetworkSelectionDescription(this.mWifiConfig);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized int getPrivacy() {
        PasspointConfiguration passpointConfiguration = this.mPasspointConfig;
        if (passpointConfiguration == null) {
            return 1;
        }
        return passpointConfiguration.isMacRandomizationEnabled() ? 1 : 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getScanResultDescription() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final int getSecurity$1() {
        return 3;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getSecurityString(boolean z) {
        return z
                ? this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_eap)
                : this.mContext.getString(R.string.wifitrackerlib_wifi_security_eap);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized List getSecurityTypes() {
        return new ArrayList(this.mTargetSecurityTypes);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSsid() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo != null) {
            return WifiInfo.sanitizeSsid(wifiInfo.getSSID());
        }
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        return wifiConfiguration != null ? WifiInfo.sanitizeSsid(wifiConfiguration.SSID) : null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getStandardString() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo != null) {
            return Utils.getStandardString(this.mContext, wifiInfo.getWifiStandard());
        }
        if (!this.mCurrentHomeScanResults.isEmpty()) {
            return Utils.getStandardString(
                    this.mContext,
                    ((ScanResult) this.mCurrentHomeScanResults.get(0)).getWifiStandard());
        }
        if (!this.mCurrentRoamingScanResults.isEmpty()) {
            return Utils.getStandardString(
                    this.mContext,
                    ((ScanResult) this.mCurrentRoamingScanResults.get(0)).getWifiStandard());
        }
        this.mTargetScanResults.clear();
        this.mTargetScanResults.addAll(this.mCurrentHomeScanResults);
        this.mTargetScanResults.addAll(this.mCurrentRoamingScanResults);
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSummary(boolean z) {
        StringJoiner stringJoiner;
        String disconnectedDescription;
        try {
            stringJoiner =
                    new StringJoiner(
                            this.mContext.getString(R.string.wifitrackerlib_summary_separator));
            int connectedState = getConnectedState();
            if (isExpired()) {
                if (this.mShowBandSummary) {
                    WifiInfo wifiInfo = this.mWifiInfo;
                    if (connectedState == 2) {
                        setBand(wifiInfo.getFrequency());
                    }
                    stringJoiner.add(getBandString());
                }
                OsuWifiEntry osuWifiEntry = this.mOsuWifiEntry;
                if (osuWifiEntry != null) {
                    stringJoiner.add(osuWifiEntry.getSummary(z));
                } else {
                    stringJoiner.add(
                            this.mContext.getString(
                                    R.string.wifitrackerlib_wifi_passpoint_expired));
                }
            } else {
                if (connectedState == 0) {
                    disconnectedDescription =
                            Utils.getDisconnectedDescription(
                                    this.mContext, this.mWifiConfig, this.mSemFlags);
                } else if (connectedState == 1) {
                    disconnectedDescription =
                            Utils.getConnectingDescription(this.mContext, this.mNetworkInfo);
                } else if (connectedState != 2) {
                    Log.e(
                            "PasspointWifiEntry",
                            "getConnectedState() returned unknown state: " + connectedState);
                    disconnectedDescription = null;
                } else {
                    disconnectedDescription =
                            Utils.getConnectedDescription(
                                    this.mContext,
                                    this.mWifiInfo,
                                    this.mWifiConfig,
                                    this.mNetworkCapabilities);
                }
                if (this.mShowBandSummary) {
                    WifiInfo wifiInfo2 = this.mWifiInfo;
                    if (connectedState == 2) {
                        setBand(wifiInfo2.getFrequency());
                    }
                    stringJoiner.add(getBandString());
                }
                if (!TextUtils.isEmpty(disconnectedDescription)) {
                    stringJoiner.add(disconnectedDescription);
                    if (isSubscription() || semIsEphemeral()) {
                        String verboseLoggingDescription =
                                Utils.getVerboseLoggingDescription(this, this.mSemFlags);
                        if (!TextUtils.isEmpty(verboseLoggingDescription)) {
                            stringJoiner.add(verboseLoggingDescription);
                        }
                    }
                }
            }
            if (getConnectedState() == 2) {
                String warningDescription = Utils.getWarningDescription(this.mContext, this);
                if (!TextUtils.isEmpty(warningDescription)) {
                    stringJoiner.add(warningDescription);
                }
            }
            if (getConnectedState() == 0) {
                String autoConnectDescription =
                        Utils.getAutoConnectDescription(this.mContext, this);
                if (this.mSemFlags.isCarrierNetwork) {
                    String carrierNetworkOffloadDescription =
                            Utils.getCarrierNetworkOffloadDescription(
                                    this.mContext, this, this.mWifiManager);
                    if (!TextUtils.isEmpty(carrierNetworkOffloadDescription)) {
                        stringJoiner.add(carrierNetworkOffloadDescription);
                    } else if (!TextUtils.isEmpty(autoConnectDescription)) {
                        stringJoiner.add(autoConnectDescription);
                    }
                } else if (!TextUtils.isEmpty(autoConnectDescription)) {
                    stringJoiner.add(autoConnectDescription);
                }
            }
            String verboseLoggingDescription2 =
                    Utils.getVerboseLoggingDescription(this, this.mSemFlags);
            if (!TextUtils.isEmpty(verboseLoggingDescription2)) {
                stringJoiner.add(verboseLoggingDescription2);
            }
        } catch (Throwable th) {
            throw th;
        }
        return stringJoiner.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getTitle() {
        return this.mFriendlyName;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized WifiConfiguration getWifiConfiguration() {
        return this.mWifiConfig;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isAutoJoinEnabled() {
        PasspointConfiguration passpointConfiguration = this.mPasspointConfig;
        if (passpointConfiguration != null) {
            return passpointConfiguration.isAutojoinEnabled();
        }
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration == null) {
            return true;
        }
        return wifiConfiguration.allowAutojoin;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isExpired() {
        if (this.mSubscriptionExpirationTimeInMillis <= 0) {
            return false;
        }
        return System.currentTimeMillis() >= this.mSubscriptionExpirationTimeInMillis;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000e, code lost:

       if (r0.meteredHint != false) goto L13;
    */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean isMetered() {
        /*
            r2 = this;
            monitor-enter(r2)
            int r0 = r2.getMeteredChoice()     // Catch: java.lang.Throwable -> L11
            r1 = 1
            if (r0 == r1) goto L14
            android.net.wifi.WifiConfiguration r0 = r2.mWifiConfig     // Catch: java.lang.Throwable -> L11
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
                    + " com.android.wifitrackerlib.PasspointWifiEntry.isMetered():boolean");
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSaved() {
        return this.mWifiConfig != null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSubscription() {
        return this.mPasspointConfig != null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSuggestion() {
        boolean z;
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration != null) {
            z = wifiConfiguration.fromWifiNetworkSuggestion;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void onNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        super.onNetworkCapabilitiesChanged(network, networkCapabilities);
        if (canSignIn() && this.mShouldAutoOpenCaptivePortal) {
            this.mShouldAutoOpenCaptivePortal = false;
            signIn();
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final List semGetAllScanResult() {
        return this.mTargetScanResults;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean semIsAdminRestricted() {
        OsuWifiEntry osuWifiEntry = this.mOsuWifiEntry;
        if (osuWifiEntry == null) {
            return false;
        }
        return osuWifiEntry.hasAdminRestrictions();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean semMatches(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.getKey().equals(this.mWifiConfig.getKey());
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void semUpdateConfig(WifiConfiguration wifiConfiguration) {
        this.mWifiConfig = wifiConfiguration;
        notifyOnUpdated();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void setAutoJoinEnabled(boolean z) {
        try {
            PasspointConfiguration passpointConfiguration = this.mPasspointConfig;
            if (passpointConfiguration != null) {
                this.mWifiManager.allowAutojoinPasspoint(
                        passpointConfiguration.getHomeSp().getFqdn(), z);
            } else {
                WifiConfiguration wifiConfiguration = this.mWifiConfig;
                if (wifiConfiguration != null) {
                    this.mWifiManager.allowAutojoin(wifiConfiguration.networkId, z);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void setMeteredChoice(int i) {
        try {
            if (this.mPasspointConfig == null || !canSetMeteredChoice()) {
                return;
            }
            if (i == 0) {
                this.mMeteredOverride = 0;
            } else if (i == 1) {
                this.mMeteredOverride = 1;
            } else if (i != 2) {
                return;
            } else {
                this.mMeteredOverride = 2;
            }
            this.mWifiManager.setPasspointMeteredOverride(
                    this.mPasspointConfig.getHomeSp().getFqdn(), this.mMeteredOverride);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void setPrivacy(int i) {
        if (this.mPasspointConfig != null && canSetPrivacy()) {
            this.mWifiManager.setMacRandomizationSettingPasspointEnabled(
                    this.mPasspointConfig.getHomeSp().getFqdn(), i != 0);
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final void signIn() {
        if (canSignIn()) {
            ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class))
                    .startCaptivePortalApp(this.mNetwork);
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner("][", "[", "]");
        stringJoiner.add("FQDN:" + this.mFqdn);
        stringJoiner.add("FriendlyName:" + this.mFriendlyName);
        if (this.mPasspointConfig != null) {
            stringJoiner.add("UniqueId:" + this.mPasspointConfig.getUniqueId());
        } else if (this.mWifiConfig != null) {
            stringJoiner.add("UniqueId:" + this.mWifiConfig.getKey());
        }
        return super.toString() + stringJoiner;
    }

    public final synchronized void updatePasspointConfig(
            PasspointConfiguration passpointConfiguration) {
        try {
            this.mPasspointConfig = passpointConfiguration;
            if (passpointConfiguration != null) {
                this.mSubscriptionExpirationTimeInMillis =
                        passpointConfiguration.getSubscriptionExpirationTimeMillis();
                this.mMeteredOverride = passpointConfiguration.getMeteredOverride();
            }
            semUpdateFlags(this.mPasspointConfig);
            notifyOnUpdated();
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void updateScanResultInfo(
            WifiConfiguration wifiConfiguration, List list, List list2) {
        try {
            this.mWifiConfig = wifiConfiguration;
            ((ArrayList) this.mCurrentHomeScanResults).clear();
            ((ArrayList) this.mCurrentRoamingScanResults).clear();
            if (list != null) {
                ((ArrayList) this.mCurrentHomeScanResults).addAll(list);
            }
            if (list2 != null) {
                ((ArrayList) this.mCurrentRoamingScanResults).addAll(list2);
            }
            if (this.mWifiConfig != null) {
                SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
                int i = 0;
                semWifiEntryFlags.wifiStandard = 0;
                semWifiEntryFlags.has6EStandard = false;
                semWifiEntryFlags.staCount = -1;
                updateSecurityTypes();
                ArrayList arrayList = new ArrayList();
                if (list != null && !list.isEmpty()) {
                    arrayList.addAll(list);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ScanResult scanResult = (ScanResult) it.next();
                        semUpdateFlags(scanResult);
                        int i2 = scanResult.frequency;
                        if (i < i2) {
                            setBand(i2);
                            this.mFrequency = scanResult.frequency;
                            i = i2;
                        }
                    }
                } else if (list2 != null && !list2.isEmpty()) {
                    arrayList.addAll(list2);
                    Iterator it2 = list2.iterator();
                    while (it2.hasNext()) {
                        ScanResult scanResult2 = (ScanResult) it2.next();
                        semUpdateFlags(scanResult2);
                        int i3 = scanResult2.frequency;
                        if (i < i3) {
                            setBand(i3);
                            this.mFrequency = i3;
                            i = i3;
                        }
                    }
                }
                ScanResult bestScanResultByLevel = Utils.getBestScanResultByLevel(arrayList);
                if (bestScanResultByLevel != null) {
                    this.mWifiConfig.SSID = "\"" + bestScanResultByLevel.SSID + "\"";
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
            } else {
                this.mLevel = -1;
            }
            notifyOnUpdated();
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void updateSecurityTypes() {
        int currentSecurityType;
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo == null || (currentSecurityType = wifiInfo.getCurrentSecurityType()) == -1) {
            return;
        }
        this.mTargetSecurityTypes = Collections.singletonList(Integer.valueOf(currentSecurityType));
    }

    public PasspointWifiEntry(
            WifiTrackerInjector wifiTrackerInjector,
            Handler handler,
            WifiConfiguration wifiConfiguration,
            WifiManager wifiManager) {
        super(wifiTrackerInjector, handler, wifiManager);
        this.mCurrentHomeScanResults = new ArrayList();
        this.mCurrentRoamingScanResults = new ArrayList();
        this.mTargetSecurityTypes = Arrays.asList(11, 12);
        boolean z = false;
        this.mShouldAutoOpenCaptivePortal = false;
        this.mShowBandSummary = false;
        this.mTargetScanResults = new ArrayList();
        this.mMeteredOverride = 0;
        Preconditions.checkNotNull(
                wifiConfiguration, "Cannot construct with null WifiConfiguration!");
        if (wifiConfiguration.isPasspoint()) {
            this.mWifiConfig = wifiConfiguration;
            String key = wifiConfiguration.getKey();
            this.mUniqueId = key;
            this.mKey = uniqueIdToPasspointWifiEntryKey(key);
            String str = wifiConfiguration.FQDN;
            this.mFqdn = str;
            Preconditions.checkNotNull(str, "Cannot construct with null WifiConfiguration FQDN!");
            WifiConfiguration wifiConfiguration2 = this.mWifiConfig;
            this.mFriendlyName = wifiConfiguration2.providerFriendlyName;
            if (wifiConfiguration2.carrierId != -1
                    && wifiConfiguration2.isEphemeral()
                    && wifiConfiguration2.fromWifiNetworkSuggestion) {
                z = true;
            }
            this.mSemFlags.isCarrierNetwork = z;
            return;
        }
        throw new IllegalArgumentException("Given WifiConfiguration is not for Passpoint!");
    }
}
