package com.samsung.android.knox.net.vpn;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class VpnAdminProfile implements Parcelable {
    public static final Parcelable.Creator<VpnAdminProfile> CREATOR = new AnonymousClass1();
    public static final String VPN_TYPE_IPSEC_HYBRID_RSA = "IPSEC_HYBRID_RSA";
    public static final String VPN_TYPE_IPSEC_IKEV2_PSK = "IPSEC_IKEV2_PSK";
    public static final String VPN_TYPE_IPSEC_IKEV2_RSA = "IPSEC_IKEV2_RSA";
    public static final String VPN_TYPE_IPSEC_XAUTH_PSK = "IPSEC_XAUTH_PSK";
    public static final String VPN_TYPE_IPSEC_XAUTH_RSA = "IPSEC_XAUTH_RSA";
    public static final String VPN_TYPE_L2TP = "L2TP";
    public static final String VPN_TYPE_L2TP_IPSEC_CRT = "L2TP_IPSEC";
    public static final String VPN_TYPE_L2TP_IPSEC_PSK = "L2TP_IPSEC_PSK";
    public static final String VPN_TYPE_PPTP = "PPTP";
    public List<String> dnsServers;
    public boolean enableL2TPSecret;
    public boolean enablePPTPEncryption;
    public List<String> forwardRoutes;
    public String ipsecCaCertificate;
    public String ipsecIdentifier;
    public String ipsecPreSharedKey;
    public String ipsecUserCertificate;
    public String l2tpSecret;
    public String ocspServerUrl;
    public String profileName;
    public List<String> searchDomains;
    public String serverName;
    public String userName;
    public String userPassword;
    public String vpnType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.net.vpn.VpnAdminProfile$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<VpnAdminProfile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VpnAdminProfile createFromParcel(Parcel parcel) {
            return new VpnAdminProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VpnAdminProfile[] newArray(int i) {
            return new VpnAdminProfile[i];
        }
    }

    public VpnAdminProfile() {
        this.profileName = null;
        this.serverName = null;
        this.vpnType = null;
        this.userName = ApnSettings.MVNO_NONE;
        this.userPassword = ApnSettings.MVNO_NONE;
        this.enablePPTPEncryption = false;
        this.enableL2TPSecret = false;
        this.l2tpSecret = ApnSettings.MVNO_NONE;
        this.ipsecPreSharedKey = ApnSettings.MVNO_NONE;
        this.ipsecCaCertificate = ApnSettings.MVNO_NONE;
        this.ipsecUserCertificate = ApnSettings.MVNO_NONE;
        this.dnsServers = new ArrayList();
        this.searchDomains = new ArrayList();
        this.forwardRoutes = new ArrayList();
        this.ipsecIdentifier = ApnSettings.MVNO_NONE;
        this.ocspServerUrl = ApnSettings.MVNO_NONE;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.profileName);
        parcel.writeString(this.serverName);
        parcel.writeString(this.vpnType);
        parcel.writeString(this.userName);
        parcel.writeString(this.userPassword);
        if (this.enablePPTPEncryption) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.enableL2TPSecret) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeString(this.l2tpSecret);
        parcel.writeString(this.ipsecPreSharedKey);
        parcel.writeString(this.ipsecCaCertificate);
        parcel.writeString(this.ipsecUserCertificate);
        parcel.writeStringList(this.dnsServers);
        parcel.writeStringList(this.searchDomains);
        parcel.writeStringList(this.forwardRoutes);
        parcel.writeString(this.ipsecIdentifier);
        parcel.writeString(this.ocspServerUrl);
    }

    public VpnAdminProfile(Parcel parcel) {
        this.profileName = null;
        this.serverName = null;
        this.vpnType = null;
        this.userName = ApnSettings.MVNO_NONE;
        this.userPassword = ApnSettings.MVNO_NONE;
        this.enablePPTPEncryption = false;
        this.enableL2TPSecret = false;
        this.l2tpSecret = ApnSettings.MVNO_NONE;
        this.ipsecPreSharedKey = ApnSettings.MVNO_NONE;
        this.ipsecCaCertificate = ApnSettings.MVNO_NONE;
        this.ipsecUserCertificate = ApnSettings.MVNO_NONE;
        this.dnsServers = new ArrayList();
        this.searchDomains = new ArrayList();
        this.forwardRoutes = new ArrayList();
        this.ipsecIdentifier = ApnSettings.MVNO_NONE;
        this.ocspServerUrl = ApnSettings.MVNO_NONE;
        this.profileName = parcel.readString();
        this.serverName = parcel.readString();
        this.vpnType = parcel.readString();
        this.userName = parcel.readString();
        this.userPassword = parcel.readString();
        this.enablePPTPEncryption = parcel.readByte() == 1;
        this.enableL2TPSecret = parcel.readByte() == 1;
        this.l2tpSecret = parcel.readString();
        this.ipsecPreSharedKey = parcel.readString();
        this.ipsecCaCertificate = parcel.readString();
        this.ipsecUserCertificate = parcel.readString();
        parcel.readStringList(this.dnsServers);
        parcel.readStringList(this.searchDomains);
        parcel.readStringList(this.forwardRoutes);
        this.ipsecIdentifier = parcel.readString();
        this.ocspServerUrl = parcel.readString();
    }
}
