package com.samsung.android.knox.net.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.samsung.android.knox.net.AuthConfig;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class WifiAdminProfile implements Parcelable {
    public static final Parcelable.Creator<WifiAdminProfile> CREATOR = new AnonymousClass1();
    public static final int ENGINE_ANDROID_KEYSTORE = 0;
    public static final int ENGINE_SECPKCS11 = 1;
    public static final int ENGINE_UCM = 2;
    public static final int PROXY_STATE_AUTO_CONFIGURE = 2;
    public static final int PROXY_STATE_MANUAL = 1;
    public static final int PROXY_STATE_NONE = 0;
    public PolicyState allowDynamicTrust;
    public String anonymousIdentity;
    public String caCertificate;
    public String clientCertification;
    public String cnMatchList;
    public String fingerprintMatchList;
    public int mEngineId;
    public String mStorageName;
    public String password;
    public String phase1;
    public String phase2;
    public String privateKey;
    public List<AuthConfig> proxyAuthConfigList;
    public List<String> proxyBypassList;
    public String proxyHostname;
    public String proxyPacUrl;
    public String proxyPassword;
    public int proxyPort;
    public int proxyState;
    public String proxyUsername;
    public String psk;
    public String security;
    public String ssid;
    public String staticGateway;
    public String staticIp;
    public boolean staticIpEnabled;
    public String staticPrimaryDns;
    public String staticSecondaryDns;
    public String staticSubnetMask;
    public String userIdentity;
    public String wepKey1;
    public String wepKey2;
    public String wepKey3;
    public String wepKey4;
    public int wepKeyId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.net.wifi.WifiAdminProfile$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<WifiAdminProfile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiAdminProfile createFromParcel(Parcel parcel) {
            return new WifiAdminProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiAdminProfile[] newArray(int i) {
            return new WifiAdminProfile[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum PolicyState {
        FALSE,
        TRUE,
        DEFAULT;

        public static PolicyState valueOf(int i) {
            PolicyState policyState = FALSE;
            if (policyState.ordinal() == i) {
                return policyState;
            }
            PolicyState policyState2 = TRUE;
            return policyState2.ordinal() == i ? policyState2 : DEFAULT;
        }

        public static PolicyState valueOf(boolean z) {
            if (true == z) {
                return TRUE;
            }
            return FALSE;
        }

        public boolean valueOf() {
            return this == TRUE;
        }
    }

    public WifiAdminProfile(int i) {
        this.ssid = null;
        this.psk = null;
        this.password = null;
        this.security = null;
        this.wepKeyId = -1;
        this.wepKey1 = null;
        this.wepKey2 = null;
        this.wepKey3 = null;
        this.wepKey4 = null;
        this.userIdentity = null;
        this.anonymousIdentity = null;
        this.phase2 = null;
        this.clientCertification = null;
        this.caCertificate = null;
        this.privateKey = null;
        this.cnMatchList = null;
        this.staticIp = null;
        this.staticGateway = null;
        this.staticPrimaryDns = null;
        this.staticSecondaryDns = null;
        this.staticSubnetMask = null;
        this.proxyHostname = null;
        this.proxyBypassList = null;
        this.proxyUsername = null;
        this.proxyPassword = null;
        this.proxyAuthConfigList = new ArrayList();
        this.phase1 = null;
        this.proxyState = 0;
        this.proxyPacUrl = null;
        this.mEngineId = 0;
        this.mStorageName = null;
        this.allowDynamicTrust = PolicyState.DEFAULT;
        this.fingerprintMatchList = null;
        if (i != 2) {
            this.mEngineId = i;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getEngineId() {
        return this.mEngineId;
    }

    public String getStorageName() {
        return this.mStorageName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.ssid);
        parcel.writeString(this.psk);
        parcel.writeString(this.password);
        parcel.writeString(this.security);
        parcel.writeInt(this.wepKeyId);
        parcel.writeString(this.wepKey1);
        parcel.writeString(this.wepKey2);
        parcel.writeString(this.wepKey3);
        parcel.writeString(this.wepKey4);
        parcel.writeString(this.userIdentity);
        parcel.writeString(this.anonymousIdentity);
        parcel.writeString(this.phase2);
        parcel.writeString(this.clientCertification);
        parcel.writeString(this.caCertificate);
        parcel.writeString(this.privateKey);
        parcel.writeString(this.cnMatchList);
        parcel.writeInt(this.staticIpEnabled ? 1 : 0);
        parcel.writeString(this.staticIp);
        parcel.writeString(this.staticGateway);
        parcel.writeString(this.staticPrimaryDns);
        parcel.writeString(this.staticSecondaryDns);
        parcel.writeString(this.staticSubnetMask);
        parcel.writeInt(this.mEngineId);
        parcel.writeString(this.proxyHostname);
        parcel.writeInt(this.proxyPort);
        parcel.writeStringList(this.proxyBypassList);
        parcel.writeString(this.proxyUsername);
        parcel.writeString(this.proxyPassword);
        parcel.writeList(this.proxyAuthConfigList);
        parcel.writeString(this.phase1);
        parcel.writeInt(this.proxyState);
        parcel.writeString(this.proxyPacUrl);
        parcel.writeString(this.mStorageName);
    }

    public WifiAdminProfile(String str) {
        this.ssid = null;
        this.psk = null;
        this.password = null;
        this.security = null;
        this.wepKeyId = -1;
        this.wepKey1 = null;
        this.wepKey2 = null;
        this.wepKey3 = null;
        this.wepKey4 = null;
        this.userIdentity = null;
        this.anonymousIdentity = null;
        this.phase2 = null;
        this.clientCertification = null;
        this.caCertificate = null;
        this.privateKey = null;
        this.cnMatchList = null;
        this.staticIp = null;
        this.staticGateway = null;
        this.staticPrimaryDns = null;
        this.staticSecondaryDns = null;
        this.staticSubnetMask = null;
        this.proxyHostname = null;
        this.proxyBypassList = null;
        this.proxyUsername = null;
        this.proxyPassword = null;
        this.proxyAuthConfigList = new ArrayList();
        this.phase1 = null;
        this.proxyState = 0;
        this.proxyPacUrl = null;
        this.mEngineId = 0;
        this.mStorageName = null;
        this.allowDynamicTrust = PolicyState.DEFAULT;
        this.fingerprintMatchList = null;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mEngineId = 2;
        this.mStorageName = str;
    }

    public WifiAdminProfile() {
        this.ssid = null;
        this.psk = null;
        this.password = null;
        this.security = null;
        this.wepKeyId = -1;
        this.wepKey1 = null;
        this.wepKey2 = null;
        this.wepKey3 = null;
        this.wepKey4 = null;
        this.userIdentity = null;
        this.anonymousIdentity = null;
        this.phase2 = null;
        this.clientCertification = null;
        this.caCertificate = null;
        this.privateKey = null;
        this.cnMatchList = null;
        this.staticIp = null;
        this.staticGateway = null;
        this.staticPrimaryDns = null;
        this.staticSecondaryDns = null;
        this.staticSubnetMask = null;
        this.proxyHostname = null;
        this.proxyBypassList = null;
        this.proxyUsername = null;
        this.proxyPassword = null;
        this.proxyAuthConfigList = new ArrayList();
        this.phase1 = null;
        this.proxyState = 0;
        this.proxyPacUrl = null;
        this.mEngineId = 0;
        this.mStorageName = null;
        this.allowDynamicTrust = PolicyState.DEFAULT;
        this.fingerprintMatchList = null;
    }

    public WifiAdminProfile(Parcel parcel) {
        this.ssid = null;
        this.psk = null;
        this.password = null;
        this.security = null;
        this.wepKeyId = -1;
        this.wepKey1 = null;
        this.wepKey2 = null;
        this.wepKey3 = null;
        this.wepKey4 = null;
        this.userIdentity = null;
        this.anonymousIdentity = null;
        this.phase2 = null;
        this.clientCertification = null;
        this.caCertificate = null;
        this.privateKey = null;
        this.cnMatchList = null;
        this.staticIp = null;
        this.staticGateway = null;
        this.staticPrimaryDns = null;
        this.staticSecondaryDns = null;
        this.staticSubnetMask = null;
        this.proxyHostname = null;
        this.proxyBypassList = null;
        this.proxyUsername = null;
        this.proxyPassword = null;
        this.proxyAuthConfigList = new ArrayList();
        this.phase1 = null;
        this.proxyState = 0;
        this.proxyPacUrl = null;
        this.mEngineId = 0;
        this.mStorageName = null;
        this.allowDynamicTrust = PolicyState.DEFAULT;
        this.fingerprintMatchList = null;
        this.ssid = parcel.readString();
        this.psk = parcel.readString();
        this.password = parcel.readString();
        this.security = parcel.readString();
        this.wepKeyId = parcel.readInt();
        this.wepKey1 = parcel.readString();
        this.wepKey2 = parcel.readString();
        this.wepKey3 = parcel.readString();
        this.wepKey4 = parcel.readString();
        this.userIdentity = parcel.readString();
        this.anonymousIdentity = parcel.readString();
        this.phase2 = parcel.readString();
        this.clientCertification = parcel.readString();
        this.caCertificate = parcel.readString();
        this.privateKey = parcel.readString();
        this.cnMatchList = parcel.readString();
        this.staticIpEnabled = parcel.readInt() != 0;
        this.staticIp = parcel.readString();
        this.staticGateway = parcel.readString();
        this.staticPrimaryDns = parcel.readString();
        this.staticSecondaryDns = parcel.readString();
        this.staticSubnetMask = parcel.readString();
        this.mEngineId = parcel.readInt();
        this.proxyHostname = parcel.readString();
        this.proxyPort = parcel.readInt();
        this.proxyBypassList = parcel.createStringArrayList();
        this.proxyUsername = parcel.readString();
        this.proxyPassword = parcel.readString();
        parcel.readList(this.proxyAuthConfigList, AuthConfig.class.getClassLoader());
        this.phase1 = parcel.readString();
        this.proxyState = parcel.readInt();
        this.proxyPacUrl = parcel.readString();
        this.mStorageName = parcel.readString();
    }
}
