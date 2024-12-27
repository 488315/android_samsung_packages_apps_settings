package com.android.wifitrackerlib;

import android.content.Context;
import android.net.ConnectivityDiagnosticsManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.RouteInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.os.BuildCompat;
import androidx.core.util.Preconditions;

import com.android.settings.wifi.details2.WifiDetailPreferenceController2$$ExternalSyntheticLambda8;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiScoredNetwork;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class WifiEntry {
    public static final Comparator TITLE_COMPARATOR;
    public static final Comparator WIFI_PICKER_COMPARATOR;
    public static final Comparator WIFI_PICKER_COMPARATOR_ALPHABETICAL;
    public static final Comparator WIFI_PICKER_COMPARATOR_HIGH_FREQUENCY;
    public static final Comparator WIFI_PICKER_COMPARATOR_RSSI;
    public int mBand;
    public final Handler mCallbackHandler;
    public ConnectCallback mConnectCallback;
    public ConnectedInfo mConnectedInfo;
    public ConnectivityDiagnosticsManager.ConnectivityReport mConnectivityReport;
    public final Context mContext;
    public Network mDefaultNetwork;
    public NetworkCapabilities mDefaultNetworkCapabilities;
    public DisconnectCallback mDisconnectCallback;
    public ForgetCallback mForgetCallback;
    public final WifiTrackerInjector mInjector;
    public WifiEntryCallback mListener;
    public final LogUtils mLog;
    public Network mNetwork;
    public NetworkCapabilities mNetworkCapabilities;
    public NetworkInfo mNetworkInfo;
    public final SemWifiEntryFlags mSemFlags;
    public WifiInfo mWifiInfo;
    public final WifiManager mWifiManager;
    public int mRssi = -127;
    public int mLevel = -1;
    public boolean mCalledConnect = false;
    public boolean mCalledDisconnect = false;
    public final Optional mManageSubscriptionAction = Optional.empty();
    public int mSpeed = 0;
    public int mFrequency = 0;
    public String mBssid = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.wifitrackerlib.WifiEntry$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$DetailedState;

        static {
            int[] iArr = new int[NetworkInfo.DetailedState.values().length];
            $SwitchMap$android$net$NetworkInfo$DetailedState = iArr;
            try {
                iArr[NetworkInfo.DetailedState.SCANNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.CONNECTING.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.AUTHENTICATING.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.OBTAINING_IPADDR.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.VERIFYING_POOR_LINK.ordinal()] =
                        5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.CAPTIVE_PORTAL_CHECK.ordinal()] =
                        6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[
                                NetworkInfo.DetailedState.CONNECTED.ordinal()] =
                        7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CertificateInfo {
        public String[] caCertificateAliases;
        public String domain;
        public int validationMethod;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ConnectCallback {
        void onConnectResult(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DisconnectCallback {
        void onDisconnectResult(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ForgetActionListener implements WifiManager.ActionListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiEntry this$0;

        public /* synthetic */ ForgetActionListener(WifiEntry wifiEntry, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiEntry;
        }

        public final void onFailure(final int i) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mCallbackHandler.post(
                            new WifiEntry$ForgetActionListener$$ExternalSyntheticLambda0(this, 0));
                    break;
                default:
                    this.this$0.mCallbackHandler.post(
                            new Runnable() { // from class:
                                             // com.android.wifitrackerlib.WifiEntry$ConnectActionListener$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    WifiEntry.ForgetActionListener forgetActionListener =
                                            WifiEntry.ForgetActionListener.this;
                                    int i2 = i;
                                    WifiEntry.ConnectCallback connectCallback =
                                            forgetActionListener.this$0.mConnectCallback;
                                    if (connectCallback != null) {
                                        connectCallback.onConnectResult(i2 == 0 ? 4 : 2);
                                    }
                                }
                            });
                    break;
            }
        }

        public final void onSuccess() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mCallbackHandler.post(
                            new WifiEntry$ForgetActionListener$$ExternalSyntheticLambda0(this, 1));
                    return;
                default:
                    synchronized (this.this$0) {
                        this.this$0.mCalledConnect = true;
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ForgetCallback {
        void onForgetResult(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface WifiEntryCallback {
        void onUpdated();
    }

    static {
        Comparator thenComparing =
                Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda4(0))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(3))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(4))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(5))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(6))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(7));
        WifiEntry$$ExternalSyntheticLambda4 wifiEntry$$ExternalSyntheticLambda4 =
                new WifiEntry$$ExternalSyntheticLambda4(2);
        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
        WIFI_PICKER_COMPARATOR =
                thenComparing
                        .thenComparing(wifiEntry$$ExternalSyntheticLambda4, comparator)
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(8));
        WIFI_PICKER_COMPARATOR_ALPHABETICAL =
                Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda4(9))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(2), comparator);
        WIFI_PICKER_COMPARATOR_RSSI =
                Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda4(10))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(11))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(12));
        WIFI_PICKER_COMPARATOR_HIGH_FREQUENCY =
                Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda4(13))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(14))
                        .thenComparing(new WifiEntry$$ExternalSyntheticLambda4(1));
        TITLE_COMPARATOR =
                Comparator.comparing(new WifiEntry$$ExternalSyntheticLambda4(2), comparator);
    }

    public WifiEntry(
            WifiTrackerInjector wifiTrackerInjector, Handler handler, WifiManager wifiManager) {
        Preconditions.checkNotNull(wifiTrackerInjector, "Cannot construct with null injector!");
        Preconditions.checkNotNull(handler, "Cannot construct with null handler!");
        Preconditions.checkNotNull(wifiManager, "Cannot construct with null WifiManager!");
        this.mInjector = wifiTrackerInjector;
        this.mContext = wifiTrackerInjector.mContext;
        this.mCallbackHandler = handler;
        this.mWifiManager = wifiManager;
        this.mSemFlags = new SemWifiEntryFlags();
        this.mLog = new LogUtils();
    }

    public abstract boolean canConnect();

    public boolean canDisconnect() {
        return false;
    }

    public boolean canEasyConnect() {
        return false;
    }

    public boolean canForget() {
        return false;
    }

    public boolean canSetAutoJoinEnabled() {
        return false;
    }

    public boolean canSetMeteredChoice() {
        return false;
    }

    public boolean canSetPrivacy() {
        return false;
    }

    public boolean canShare() {
        return false;
    }

    public boolean canSignIn() {
        return false;
    }

    public final boolean checkWifi6EStandard(int i, int i2) {
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        if (i < 5925 || i > 7125) {
            return false;
        }
        if (i2 >= 6) {
            return true;
        }
        Log.e("WifiEntry.", "invalid Wi-Fi 6E network " + getKey() + " standard:" + i2);
        return false;
    }

    public final synchronized void clearConnectionInfo() {
        try {
            updateWifiInfo(null);
            this.mNetworkInfo = null;
            this.mNetworkCapabilities = null;
            this.mConnectivityReport = null;
            if (this.mCalledDisconnect) {
                this.mCalledDisconnect = false;
                this.mCallbackHandler.post(new WifiEntry$$ExternalSyntheticLambda0(this, 2));
            }
            notifyOnUpdated();
        } catch (Throwable th) {
            throw th;
        }
    }

    public abstract void connect(ConnectCallback connectCallback);

    public abstract boolean connectionInfoMatches(WifiInfo wifiInfo);

    public final boolean equals(Object obj) {
        if (obj instanceof WifiEntry) {
            return getKey().equals(((WifiEntry) obj).getKey());
        }
        return false;
    }

    public final synchronized void forceUpdateNetworkInfo(
            WifiInfo wifiInfo, NetworkInfo networkInfo) {
        if (wifiInfo != null) {
            if (connectionInfoMatches(wifiInfo)) {
                this.mNetworkInfo = networkInfo;
                this.mWifiInfo = wifiInfo;
                notifyOnUpdated();
                return;
            }
        }
        if (this.mNetworkInfo != null) {
            this.mNetworkInfo = null;
        }
    }

    public String getBandString() {
        return ApnSettings.MVNO_NONE;
    }

    public CertificateInfo getCertificateInfo() {
        return null;
    }

    public final synchronized ConnectedInfo getConnectedInfo() {
        ConnectedInfo connectedInfo;
        if (getConnectedState() == 2 && (connectedInfo = this.mConnectedInfo) != null) {
            return new ConnectedInfo(connectedInfo);
        }
        return null;
    }

    public synchronized int getConnectedState() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo == null) {
            return 0;
        }
        switch (AnonymousClass1.$SwitchMap$android$net$NetworkInfo$DetailedState[
                networkInfo.getDetailedState().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return 1;
            case 7:
                return 2;
            default:
                return 0;
        }
    }

    public abstract String getKey();

    public int getLevel() {
        return this.mLevel;
    }

    public abstract String getMacAddress();

    public int getMeteredChoice() {
        return 0;
    }

    public String getNetworkSelectionDescription() {
        return ApnSettings.MVNO_NONE;
    }

    public int getPrivacy() {
        return 2;
    }

    public String getScanResultDescription() {
        return ApnSettings.MVNO_NONE;
    }

    public int getSecurity$1() {
        switch (Utils.getSingleSecurityTypeFromMultipleSecurityTypes(getSecurityTypes())) {
        }
        return 3;
    }

    public String getSecurityString(boolean z) {
        return ApnSettings.MVNO_NONE;
    }

    public List getSecurityTypes() {
        return Collections.emptyList();
    }

    public abstract String getSsid();

    public String getStandardString() {
        return ApnSettings.MVNO_NONE;
    }

    public abstract String getSummary(boolean z);

    public String getTitle() {
        return ApnSettings.MVNO_NONE;
    }

    public WifiConfiguration getWifiConfiguration() {
        return null;
    }

    public boolean hasAdminRestrictions() {
        return false;
    }

    public final synchronized boolean hasInternetAccess() {
        boolean z;
        NetworkCapabilities networkCapabilities = this.mNetworkCapabilities;
        if (networkCapabilities != null) {
            z = networkCapabilities.hasCapability(16);
        }
        return z;
    }

    public final int hashCode() {
        return getKey().hashCode();
    }

    public boolean isAutoJoinEnabled() {
        return false;
    }

    public final synchronized boolean isDefaultNetwork() {
        Network network = this.mNetwork;
        if (network != null && network.equals(this.mDefaultNetwork)) {
            return true;
        }
        NetworkCapabilities networkCapabilities = this.mDefaultNetworkCapabilities;
        if (networkCapabilities == null) {
            return false;
        }
        WifiInfo wifiInfo = Utils.getWifiInfo(networkCapabilities);
        if (wifiInfo != null) {
            return connectionInfoMatches(wifiInfo);
        }
        int i = BuildCompat.$r8$clinit;
        List underlyingNetworks = this.mDefaultNetworkCapabilities.getUnderlyingNetworks();
        return underlyingNetworks != null && underlyingNetworks.contains(this.mNetwork);
    }

    public boolean isExpired() {
        return false;
    }

    public final synchronized boolean isLowQuality() {
        boolean z;
        NetworkCapabilities networkCapabilities;
        z = false;
        if (isPrimaryNetwork()
                && hasInternetAccess()
                && !isDefaultNetwork()
                && (networkCapabilities = this.mDefaultNetworkCapabilities) != null
                && networkCapabilities.hasTransport(0)
                && !this.mDefaultNetworkCapabilities.hasTransport(4)) {
            if (this.mDefaultNetworkCapabilities.hasCapability(13)) {
                z = true;
            }
        }
        return z;
    }

    public boolean isMetered() {
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0016, code lost:

       if (r0.isPrimary() != false) goto L17;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean isPrimaryNetwork() {
        /*
            r2 = this;
            monitor-enter(r2)
            int r0 = r2.getConnectedState()     // Catch: java.lang.Throwable -> L19
            r1 = 0
            if (r0 != 0) goto La
            monitor-exit(r2)
            return r1
        La:
            android.net.NetworkInfo r0 = r2.mNetworkInfo     // Catch: java.lang.Throwable -> L19
            if (r0 != 0) goto L1b
            android.net.wifi.WifiInfo r0 = r2.mWifiInfo     // Catch: java.lang.Throwable -> L19
            if (r0 == 0) goto L1c
            boolean r0 = r0.isPrimary()     // Catch: java.lang.Throwable -> L19
            if (r0 == 0) goto L1c
            goto L1b
        L19:
            r0 = move-exception
            goto L1e
        L1b:
            r1 = 1
        L1c:
            monitor-exit(r2)
            return r1
        L1e:
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.WifiEntry.isPrimaryNetwork():boolean");
    }

    public boolean isSaved() {
        return false;
    }

    public boolean isSubscription() {
        return false;
    }

    public boolean isSuggestion() {
        return false;
    }

    public final void notifyOnUpdated() {
        if (this.mListener != null) {
            this.mCallbackHandler.post(new WifiEntry$$ExternalSyntheticLambda0(this, 0));
        }
    }

    public final synchronized void onDefaultNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        this.mDefaultNetwork = network;
        this.mDefaultNetworkCapabilities = networkCapabilities;
        notifyOnUpdated();
    }

    public final synchronized void onDefaultNetworkLost() {
        this.mDefaultNetwork = null;
        this.mDefaultNetworkCapabilities = null;
        notifyOnUpdated();
    }

    public synchronized void onNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        WifiInfo wifiInfo = Utils.getWifiInfo(networkCapabilities);
        if (wifiInfo == null) {
            return;
        }
        if (!connectionInfoMatches(wifiInfo)) {
            if (network.equals(this.mNetwork)) {
                onNetworkLost(network);
            }
        } else {
            this.mNetwork = network;
            this.mNetworkCapabilities = networkCapabilities;
            updateWifiInfo(wifiInfo);
            notifyOnUpdated();
        }
    }

    public final synchronized void onNetworkLost(Network network) {
        if (network.equals(this.mNetwork)) {
            clearConnectionInfo();
        }
    }

    public final synchronized void onPrimaryWifiInfoChanged(
            WifiInfo wifiInfo, NetworkInfo networkInfo) {
        if (wifiInfo != null) {
            try {
                if (connectionInfoMatches(wifiInfo)) {
                    if (networkInfo != null) {
                        this.mNetworkInfo = networkInfo;
                    }
                    updateWifiInfo(wifiInfo);
                    notifyOnUpdated();
                    return;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mNetworkInfo != null) {
            this.mNetworkInfo = null;
            notifyOnUpdated();
        }
    }

    public void onUpdated() {
        notifyOnUpdated();
    }

    public boolean semCanShowPassword() {
        return false;
    }

    public List semGetAllScanResult() {
        return Collections.emptyList();
    }

    public final String semGetBssid() {
        String str = getWifiConfiguration() == null ? this.mBssid : getWifiConfiguration().BSSID;
        return str == null ? this.mBssid : str;
    }

    public int semGetFrequency(String str) {
        return 0;
    }

    public final int semGetHighestSecurity() {
        int i;
        List securityTypes = getSecurityTypes();
        List list = Utils.defaultSsidList;
        if (securityTypes.size() == 1) {
            i = ((Integer) securityTypes.get(0)).intValue();
        } else {
            if (securityTypes.size() == 2) {
                if (securityTypes.contains(6)) {
                    i = 6;
                } else if (securityTypes.contains(4)) {
                    i = 4;
                } else if (securityTypes.contains(9)) {
                    i = 9;
                }
            }
            i = -1;
        }
        switch (i) {
            case 0:
            case 7:
            case 8:
            case 10:
            default:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
            case 11:
            case 12:
                return 3;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 4;
            case 9:
                return 7;
        }
    }

    public String semGetSecurityString(String str) {
        return getSecurityString(false);
    }

    public boolean semIs6GHzOnly() {
        return false;
    }

    public boolean semIsAdminRestricted() {
        return false;
    }

    public final boolean semIsEphemeral() {
        NetworkInfo networkInfo;
        WifiInfo wifiInfo = this.mWifiInfo;
        return (wifiInfo == null
                        || !wifiInfo.isEphemeral()
                        || (networkInfo = this.mNetworkInfo) == null
                        || networkInfo.getState() == NetworkInfo.State.DISCONNECTED)
                ? false
                : true;
    }

    public boolean semIsMobileHotspot() {
        return false;
    }

    public final boolean semIsWifi6ENetwork() {
        WifiInfo wifiInfo = this.mWifiInfo;
        return wifiInfo != null
                ? checkWifi6EStandard(wifiInfo.getFrequency(), wifiInfo.getWifiStandard())
                : this.mSemFlags.has6EStandard;
    }

    public final boolean semIsWifi6Network() {
        WifiInfo wifiInfo = this.mWifiInfo;
        return wifiInfo != null
                ? wifiInfo.getWifiStandard() == 6
                : this.mSemFlags.wifiStandard >= 6;
    }

    public final boolean semIsWifi7Network() {
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        if (!semWifiEntryFlags.isSupportedWifi7) {
            return false;
        }
        WifiInfo wifiInfo = this.mWifiInfo;
        return wifiInfo != null
                ? wifiInfo.getWifiStandard() == 8
                : semWifiEntryFlags.wifiStandard >= 8;
    }

    public boolean semMatches(WifiConfiguration wifiConfiguration) {
        return false;
    }

    public final void semUpdateFlags(SemWifiConfiguration semWifiConfiguration) {
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        if (semWifiConfiguration != null) {
            semWifiEntryFlags.isLocked = semWifiConfiguration.isLockDown();
            semWifiEntryFlags.isCaptivePortal = semWifiConfiguration.isCaptivePortal();
            semWifiEntryFlags.semConfig = semWifiConfiguration;
        } else {
            semWifiEntryFlags.isLocked = false;
            semWifiEntryFlags.isCaptivePortal = false;
        }
        notifyOnUpdated();
    }

    public final void semUpdateQoSInformation() {
        int i = this.mSpeed;
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        int i2 = semWifiEntryFlags.networkType;
        if (((HashMap) semWifiEntryFlags.qosScoredNetworkCache).isEmpty()) {
            return;
        }
        Iterator it = ((HashMap) semWifiEntryFlags.qosScoredNetworkCache).values().iterator();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WifiScoredNetwork wifiScoredNetwork = (WifiScoredNetwork) it.next();
            int i5 = this.mRssi;
            int[] iArr = wifiScoredNetwork.levels;
            int i6 =
                    i5 >= -55
                            ? iArr[2]
                            : (i5 >= -55 || i5 < -65)
                                    ? (i5 >= -65 || i5 < -75) ? -1 : iArr[0]
                                    : iArr[1];
            int i7 = (i6 != 10 || i5 > -71) ? i6 : 5;
            if (i7 > 0) {
                i3++;
                i4 += i7;
            }
        }
        int i8 = i3 == 0 ? 0 : i4 / i3;
        for (WifiScoredNetwork wifiScoredNetwork2 :
                ((HashMap) semWifiEntryFlags.qosScoredNetworkCache).values()) {
            if (wifiScoredNetwork2.bssid.equals(this.mBssid)) {
                semWifiEntryFlags.networkType = wifiScoredNetwork2.networkType;
            }
        }
        if (i8 != 0) {
            Log.i(
                    "WifiEntry.",
                    String.format(
                            "%s generated fallback speed is: %d networkType: %s",
                            getSsid(),
                            Integer.valueOf(i8),
                            Integer.valueOf(semWifiEntryFlags.networkType)));
        }
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        int i9 = i8 != 0 ? i8 < 7 ? 5 : i8 < 15 ? 10 : i8 < 25 ? 20 : 30 : 0;
        this.mSpeed = i9;
        if (i == i9 && i2 == semWifiEntryFlags.networkType) {
            return;
        }
        Log.i(
                "WifiEntry.",
                String.format(
                        "%s: Set speed to %d and NetworkType %s. set by Network Samsung QoS.",
                        getSsid(),
                        Integer.valueOf(this.mSpeed),
                        Integer.valueOf(semWifiEntryFlags.networkType)));
    }

    public final void setBand(int i) {
        this.mBand = 0;
        if (i >= 4900 && i < 5900) {
            this.mBand = 1;
            return;
        }
        if (i >= 5925 && i < 7125) {
            this.mBand = 2;
        } else {
            if (i < 58320 || i >= 70200) {
                return;
            }
            this.mBand = 3;
        }
    }

    public final synchronized void setListener(WifiEntryCallback wifiEntryCallback) {
        this.mListener = wifiEntryCallback;
    }

    public boolean shouldEditBeforeConnect() {
        return false;
    }

    public final boolean shouldShowXLevelIcon() {
        return (getConnectedState() == 0
                        || this.mConnectivityReport == null
                        || (hasInternetAccess() && !isLowQuality())
                        || canSignIn()
                        || !isPrimaryNetwork())
                ? false
                : true;
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("][", "[", "]");
        stringJoiner.add(getClass().getSimpleName());
        stringJoiner.add(getTitle());
        stringJoiner.add(getSummary(true));
        StringBuilder sb = new StringBuilder("Level:");
        sb.append(getLevel());
        sb.append(shouldShowXLevelIcon() ? "!" : ApnSettings.MVNO_NONE);
        stringJoiner.add(sb.toString());
        String securityString = getSecurityString(true);
        if (!TextUtils.isEmpty(securityString)) {
            stringJoiner.add(securityString);
        }
        int connectedState = getConnectedState();
        if (connectedState == 2) {
            stringJoiner.add("Connected");
        } else if (connectedState == 1) {
            stringJoiner.add("Connecting...");
        }
        if (hasInternetAccess()) {
            stringJoiner.add("Internet");
        }
        if (isDefaultNetwork()) {
            stringJoiner.add("Default");
        }
        if (isPrimaryNetwork()) {
            stringJoiner.add("Primary");
        }
        if (isLowQuality()) {
            stringJoiner.add("LowQuality");
        }
        if (isSaved()) {
            stringJoiner.add("Saved");
        }
        if (isSubscription()) {
            stringJoiner.add("Subscription");
        }
        if (isSuggestion()) {
            stringJoiner.add("Suggestion");
        }
        if (isMetered()) {
            stringJoiner.add("Metered");
        }
        if ((isSaved() || isSuggestion() || isSubscription()) && !isAutoJoinEnabled()) {
            stringJoiner.add("AutoJoinDisabled");
        }
        if (isExpired()) {
            stringJoiner.add("Expired");
        }
        if (canSignIn()) {
            stringJoiner.add("SignIn");
        }
        if (shouldEditBeforeConnect()) {
            stringJoiner.add("EditBeforeConnect");
        }
        if (hasAdminRestrictions()) {
            stringJoiner.add("AdminRestricted");
        }
        return stringJoiner.toString();
    }

    public final void updateBestRssi(ScanResult scanResult) {
        int i;
        int i2 = scanResult.level;
        if (i2 == -127 || (i = this.mRssi) == -127) {
            this.mRssi = i2;
        } else {
            this.mRssi = (i + i2) / 2;
        }
    }

    public final synchronized void updateConnectivityReport(
            ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
        if (connectivityReport.getNetwork().equals(this.mNetwork)) {
            this.mConnectivityReport = connectivityReport;
            notifyOnUpdated();
        }
    }

    public final synchronized void updateLinkProperties(
            Network network, LinkProperties linkProperties) {
        try {
            if (network.equals(this.mNetwork)) {
                if (this.mConnectedInfo == null) {
                    this.mConnectedInfo = new ConnectedInfo();
                }
                ArrayList arrayList = new ArrayList();
                for (LinkAddress linkAddress : linkProperties.getLinkAddresses()) {
                    if (linkAddress.getAddress() instanceof Inet4Address) {
                        ConnectedInfo connectedInfo = this.mConnectedInfo;
                        linkAddress.getAddress().getHostAddress();
                        connectedInfo.getClass();
                        try {
                            InetAddress byAddress =
                                    InetAddress.getByAddress(new byte[] {-1, -1, -1, -1});
                            ConnectedInfo connectedInfo2 = this.mConnectedInfo;
                            Utils.getNetworkPart(byAddress, linkAddress.getPrefixLength())
                                    .getHostAddress();
                            connectedInfo2.getClass();
                        } catch (IllegalArgumentException | UnknownHostException unused) {
                        }
                    } else if (linkAddress.getAddress() instanceof Inet6Address) {
                        arrayList.add(linkAddress.getAddress().getHostAddress());
                    }
                }
                this.mConnectedInfo.ipv6Addresses = arrayList;
                Iterator<RouteInfo> it = linkProperties.getRoutes().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RouteInfo next = it.next();
                    if (next.isDefaultRoute()
                            && (next.getDestination().getAddress() instanceof Inet4Address)
                            && next.hasGateway()) {
                        ConnectedInfo connectedInfo3 = this.mConnectedInfo;
                        next.getGateway().getHostAddress();
                        connectedInfo3.getClass();
                        break;
                    }
                }
                this.mConnectedInfo.dnsServers =
                        (List)
                                linkProperties.getDnsServers().stream()
                                        .map(
                                                new WifiDetailPreferenceController2$$ExternalSyntheticLambda8())
                                        .collect(Collectors.toList());
                notifyOnUpdated();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void updateWifiInfo(WifiInfo wifiInfo) {
        if (wifiInfo == null) {
            this.mWifiInfo = null;
            this.mConnectedInfo = null;
            updateSecurityTypes();
            return;
        }
        this.mWifiInfo = wifiInfo;
        int rssi = wifiInfo.getRssi();
        this.mRssi = rssi;
        if (rssi != -127) {
            this.mLevel = SemWifiUtils.calculateSignalLevel(rssi);
        }
        if (getConnectedState() == 2) {
            if (this.mCalledConnect) {
                this.mCalledConnect = false;
                this.mCallbackHandler.post(new WifiEntry$$ExternalSyntheticLambda0(this, 1));
            }
            if (this.mConnectedInfo == null) {
                this.mConnectedInfo = new ConnectedInfo();
            }
            this.mConnectedInfo.frequencyMhz = this.mWifiInfo.getFrequency();
            this.mConnectedInfo.linkSpeedMbps = this.mWifiInfo.getLinkSpeed();
            ConnectedInfo connectedInfo = this.mConnectedInfo;
            this.mWifiInfo.getWifiStandard();
            connectedInfo.getClass();
        }
        updateSecurityTypes();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectedInfo {
        public List dnsServers;
        public int frequencyMhz;
        public List ipv6Addresses;
        public int linkSpeedMbps;

        public ConnectedInfo() {
            this.dnsServers = new ArrayList();
            this.ipv6Addresses = new ArrayList();
        }

        public ConnectedInfo(ConnectedInfo connectedInfo) {
            this.dnsServers = new ArrayList();
            this.ipv6Addresses = new ArrayList();
            this.frequencyMhz = connectedInfo.frequencyMhz;
            this.dnsServers = new ArrayList(this.dnsServers);
            this.linkSpeedMbps = connectedInfo.linkSpeedMbps;
            this.ipv6Addresses = new ArrayList(connectedInfo.ipv6Addresses);
        }
    }

    public final void semUpdateFlags(PasspointConfiguration passpointConfiguration) {
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        if (passpointConfiguration != null) {
            HomeSp homeSp = passpointConfiguration.getHomeSp();
            semWifiEntryFlags.isOpenRoamingNetwork =
                    "samsung.openroaming.net".equals(homeSp.getFqdn())
                            && "OpenRoaming".equals(homeSp.getFriendlyName());
        }
        semWifiEntryFlags.passpointConfiguration = passpointConfiguration;
    }

    public void signIn() {}

    public void updateSecurityTypes() {}

    public final void semUpdateFlags(ScanResult scanResult) {
        if (scanResult == null) {
            return;
        }
        int wifiStandard = scanResult.getWifiStandard();
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        int max = Math.max(semWifiEntryFlags.wifiStandard, wifiStandard);
        semWifiEntryFlags.wifiStandard = max;
        if (!semWifiEntryFlags.has6EStandard) {
            semWifiEntryFlags.has6EStandard = checkWifi6EStandard(scanResult.frequency, max);
        }
        for (ScanResult.InformationElement informationElement :
                scanResult.getInformationElements()) {
            int id = informationElement.getId();
            int i = 0;
            LogUtils logUtils = this.mLog;
            if (id == 11) {
                try {
                    semWifiEntryFlags.staCount =
                            Math.max(semWifiEntryFlags.staCount, 0)
                                    + (informationElement
                                                    .getBytes()
                                                    .order(ByteOrder.LITTLE_ENDIAN)
                                                    .getShort()
                                            & 65535);
                } catch (BufferUnderflowException unused) {
                    Log.e(
                            "WifiEntry.",
                            logUtils.getPrintableLog(scanResult.BSSID)
                                    + " BufferUnderflowException ie:"
                                    + id);
                }
            } else if (id == 50) {
                if (informationElement.getBytes() == null) {
                    return;
                }
                int remaining = informationElement.getBytes().remaining();
                byte[] bArr = new byte[remaining];
                if (remaining > 8 || remaining < 1) {
                    return;
                }
                try {
                    ByteBuffer order = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN);
                    while (true) {
                        if (i >= remaining) {
                            break;
                        }
                        if ((order.get() & Byte.MAX_VALUE) == 123) {
                            semWifiEntryFlags.isH2EOnlyNetwork = true;
                            break;
                        }
                        i++;
                    }
                } catch (BufferUnderflowException unused2) {
                    Log.e(
                            "WifiEntry.",
                            logUtils.getPrintableLog(scanResult.BSSID)
                                    + " BufferUnderflowException ie:"
                                    + id);
                }
            } else if (id == 221) {
                try {
                    int i2 = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN).getInt();
                    if ((16777215 & i2) == 3282432 && (i2 >>> 24) == 128) {
                        semWifiEntryFlags.isSamsungMobileHotspot = true;
                    }
                } catch (BufferUnderflowException unused3) {
                    Log.e(
                            "WifiEntry.",
                            logUtils.getPrintableLog(scanResult.BSSID)
                                    + " BufferUnderflowException ie:"
                                    + id);
                }
            } else if (id != 244) {
                continue;
            } else {
                if (informationElement.getBytes() == null) {
                    return;
                }
                int remaining2 = informationElement.getBytes().remaining();
                byte[] bArr2 = new byte[remaining2];
                if (remaining2 > 8 || remaining2 < 1) {
                    return;
                }
                try {
                    informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN).get();
                } catch (BufferUnderflowException unused4) {
                    Log.e(
                            "WifiEntry.",
                            logUtils.getPrintableLog(scanResult.BSSID)
                                    + " BufferUnderflowException ie:"
                                    + id);
                }
            }
        }
    }

    public void disconnect(DisconnectCallback disconnectCallback) {}

    public void forget(ForgetCallback forgetCallback) {}

    public void semUpdateConfig(WifiConfiguration wifiConfiguration) {}

    public void setAutoJoinEnabled(boolean z) {}

    public void setMeteredChoice(int i) {}

    public void setPrivacy(int i) {}
}
