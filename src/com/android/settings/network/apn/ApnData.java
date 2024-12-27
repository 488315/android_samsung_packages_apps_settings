package com.android.settings.network.apn;

import android.content.Context;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;

import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ApnData {
    public final String apn;
    public final boolean apnEnable;
    public final boolean apnEnableEnabled;
    public final int apnProtocol;
    public final int apnRoaming;
    public final String apnType;
    public final int authType;
    public final CustomizedConfig customizedConfig;
    public final int edited;
    public final int id;
    public final String mmsPort;
    public final String mmsProxy;
    public final String mmsc;
    public final String name;
    public final long networkType;
    public final boolean newApn;
    public final String passWord;
    public final String port;
    public final String proxy;
    public final String server;
    public final int subId;
    public final int userEditable;
    public final String userName;
    public final boolean validEnabled;

    public /* synthetic */ ApnData(
            int i,
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            String str6,
            String str7,
            String str8,
            String str9,
            String str10,
            int i2,
            String str11,
            int i3,
            int i4,
            boolean z,
            long j,
            int i5,
            int i6,
            int i7) {
        this(
                (i7 & 1) != 0 ? -1 : i,
                (i7 & 2) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str,
                (i7 & 4) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str2,
                (i7 & 8) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str3,
                (i7 & 16) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str4,
                (i7 & 32) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str5,
                (i7 & 64) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str6,
                (i7 & 128) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str7,
                (i7 & 256) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str8,
                (i7 & 512) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str9,
                (i7 & 1024) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str10,
                (i7 & 2048) != 0 ? -1 : i2,
                (i7 & 4096) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str11,
                (i7 & 8192) != 0 ? -1 : i3,
                (i7 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? -1 : i4,
                (32768 & i7) != 0 ? true : z,
                (65536 & i7) != 0 ? 0L : j,
                (131072 & i7) != 0 ? 1 : i5,
                (i7 & 262144) != 0 ? 1 : i6,
                true,
                false,
                -1,
                false,
                new CustomizedConfig(
                        false,
                        (List) null,
                        (List) null,
                        (List) null,
                        (String) null,
                        (String) null,
                        127));
    }

    public static ApnData copy$default(
            ApnData apnData,
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            String str6,
            String str7,
            String str8,
            String str9,
            String str10,
            int i,
            String str11,
            int i2,
            int i3,
            boolean z,
            long j,
            boolean z2,
            boolean z3,
            int i4,
            boolean z4,
            CustomizedConfig customizedConfig,
            int i5) {
        String apnType;
        long j2;
        int i6;
        boolean z5;
        int i7 = apnData.id;
        String name = (i5 & 2) != 0 ? apnData.name : str;
        String apn = (i5 & 4) != 0 ? apnData.apn : str2;
        String proxy = (i5 & 8) != 0 ? apnData.proxy : str3;
        String port = (i5 & 16) != 0 ? apnData.port : str4;
        String userName = (i5 & 32) != 0 ? apnData.userName : str5;
        String passWord = (i5 & 64) != 0 ? apnData.passWord : str6;
        String server = (i5 & 128) != 0 ? apnData.server : str7;
        String mmsc = (i5 & 256) != 0 ? apnData.mmsc : str8;
        String mmsProxy = (i5 & 512) != 0 ? apnData.mmsProxy : str9;
        String mmsPort = (i5 & 1024) != 0 ? apnData.mmsPort : str10;
        int i8 = (i5 & 2048) != 0 ? apnData.authType : i;
        String str12 = (i5 & 4096) != 0 ? apnData.apnType : str11;
        int i9 = (i5 & 8192) != 0 ? apnData.apnProtocol : i2;
        int i10 =
                (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0
                        ? apnData.apnRoaming
                        : i3;
        boolean z6 =
                (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? apnData.apnEnable : z;
        int i11 = i8;
        if ((65536 & i5) != 0) {
            apnType = str12;
            j2 = apnData.networkType;
        } else {
            apnType = str12;
            j2 = j;
        }
        int i12 = apnData.edited;
        int i13 = apnData.userEditable;
        if ((i5 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
            i6 = i13;
            z5 = apnData.apnEnableEnabled;
        } else {
            i6 = i13;
            z5 = z2;
        }
        boolean z7 = (1048576 & i5) != 0 ? apnData.newApn : z3;
        int i14 = (2097152 & i5) != 0 ? apnData.subId : i4;
        boolean z8 = (4194304 & i5) != 0 ? apnData.validEnabled : z4;
        CustomizedConfig customizedConfig2 =
                (i5 & 8388608) != 0 ? apnData.customizedConfig : customizedConfig;
        apnData.getClass();
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(apn, "apn");
        Intrinsics.checkNotNullParameter(proxy, "proxy");
        Intrinsics.checkNotNullParameter(port, "port");
        Intrinsics.checkNotNullParameter(userName, "userName");
        Intrinsics.checkNotNullParameter(passWord, "passWord");
        Intrinsics.checkNotNullParameter(server, "server");
        Intrinsics.checkNotNullParameter(mmsc, "mmsc");
        Intrinsics.checkNotNullParameter(mmsProxy, "mmsProxy");
        Intrinsics.checkNotNullParameter(mmsPort, "mmsPort");
        Intrinsics.checkNotNullParameter(apnType, "apnType");
        Intrinsics.checkNotNullParameter(customizedConfig2, "customizedConfig");
        return new ApnData(
                i7,
                name,
                apn,
                proxy,
                port,
                userName,
                passWord,
                server,
                mmsc,
                mmsProxy,
                mmsPort,
                i11,
                apnType,
                i9,
                i10,
                z6,
                j2,
                i12,
                i6,
                z5,
                z7,
                i14,
                z8,
                customizedConfig2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ApnData)) {
            return false;
        }
        ApnData apnData = (ApnData) obj;
        return this.id == apnData.id
                && Intrinsics.areEqual(this.name, apnData.name)
                && Intrinsics.areEqual(this.apn, apnData.apn)
                && Intrinsics.areEqual(this.proxy, apnData.proxy)
                && Intrinsics.areEqual(this.port, apnData.port)
                && Intrinsics.areEqual(this.userName, apnData.userName)
                && Intrinsics.areEqual(this.passWord, apnData.passWord)
                && Intrinsics.areEqual(this.server, apnData.server)
                && Intrinsics.areEqual(this.mmsc, apnData.mmsc)
                && Intrinsics.areEqual(this.mmsProxy, apnData.mmsProxy)
                && Intrinsics.areEqual(this.mmsPort, apnData.mmsPort)
                && this.authType == apnData.authType
                && Intrinsics.areEqual(this.apnType, apnData.apnType)
                && this.apnProtocol == apnData.apnProtocol
                && this.apnRoaming == apnData.apnRoaming
                && this.apnEnable == apnData.apnEnable
                && this.networkType == apnData.networkType
                && this.edited == apnData.edited
                && this.userEditable == apnData.userEditable
                && this.apnEnableEnabled == apnData.apnEnableEnabled
                && this.newApn == apnData.newApn
                && this.subId == apnData.subId
                && this.validEnabled == apnData.validEnabled
                && Intrinsics.areEqual(this.customizedConfig, apnData.customizedConfig);
    }

    public final Map getContentValueMap(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Pair pair = new Pair("name", this.name);
        Pair pair2 = new Pair("apn", this.apn);
        Pair pair3 = new Pair("proxy", this.proxy);
        Pair pair4 = new Pair(HostAuth.PORT, this.port);
        Pair pair5 = new Pair("user", this.userName);
        Pair pair6 = new Pair("server", this.server);
        Pair pair7 = new Pair(HostAuth.PASSWORD, this.passWord);
        Pair pair8 = new Pair("mmsc", this.mmsc);
        Pair pair9 = new Pair("mmsproxy", this.mmsProxy);
        Pair pair10 = new Pair("mmsport", this.mmsPort);
        Pair pair11 = new Pair("authtype", Integer.valueOf(this.authType));
        Pair pair12 =
                new Pair(
                        "protocol",
                        ApnRepositoryKt.convertOptions2Protocol(context, this.apnProtocol));
        Pair pair13 =
                new Pair(
                        "roaming_protocol",
                        ApnRepositoryKt.convertOptions2Protocol(context, this.apnRoaming));
        Pair pair14 = new Pair("type", this.apnType);
        long j = this.networkType;
        return MapsKt__MapsKt.mapOf(
                pair,
                pair2,
                pair3,
                pair4,
                pair5,
                pair6,
                pair7,
                pair8,
                pair9,
                pair10,
                pair11,
                pair12,
                pair13,
                pair14,
                new Pair("network_type_bitmask", Long.valueOf(j)),
                new Pair("lingering_network_type_bitmask", Long.valueOf(j)),
                new Pair("carrier_enabled", Boolean.valueOf(this.apnEnable)),
                new Pair("edited", 1));
    }

    public final int hashCode() {
        return this.customizedConfig.hashCode()
                + TransitionData$$ExternalSyntheticOutline0.m(
                        KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                this.subId,
                                TransitionData$$ExternalSyntheticOutline0.m(
                                        TransitionData$$ExternalSyntheticOutline0.m(
                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                        .m(
                                                                this.userEditable,
                                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                this.edited,
                                                                                Scale$$ExternalSyntheticOutline0
                                                                                        .m(
                                                                                                TransitionData$$ExternalSyntheticOutline0
                                                                                                        .m(
                                                                                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                                                                                        .m(
                                                                                                                                this
                                                                                                                                        .apnRoaming,
                                                                                                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                                                                                                        .m(
                                                                                                                                                this
                                                                                                                                                        .apnProtocol,
                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                        .m(
                                                                                                                                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                                                                                                                                        .m(
                                                                                                                                                                                this
                                                                                                                                                                                        .authType,
                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                        .m(
                                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                                        .m(
                                                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                                                        .m(
                                                                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                        .m(
                                                                                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                        .m(
                                                                                                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                        .m(
                                                                                                                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                        .m(
                                                                                                                                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                        .m(
                                                                                                                                                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                        .m(
                                                                                                                                                                                                                                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                                                                                                                                                                                                                                        .m(
                                                                                                                                                                                                                                                                                                                                                Integer
                                                                                                                                                                                                                                                                                                                                                                .hashCode(
                                                                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                                                                .id)
                                                                                                                                                                                                                                                                                                                                                        * 31,
                                                                                                                                                                                                                                                                                                                                                31,
                                                                                                                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                                                                                                                        .name),
                                                                                                                                                                                                                                                                                                                                31,
                                                                                                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                                                                                                        .apn),
                                                                                                                                                                                                                                                                                                                31,
                                                                                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                                                                                        .proxy),
                                                                                                                                                                                                                                                                                                31,
                                                                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                                                                        .port),
                                                                                                                                                                                                                                                                                31,
                                                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                                                        .userName),
                                                                                                                                                                                                                                                                31,
                                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                                        .passWord),
                                                                                                                                                                                                                                                31,
                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                        .server),
                                                                                                                                                                                                                                31,
                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                        .mmsc),
                                                                                                                                                                                                                31,
                                                                                                                                                                                                                this
                                                                                                                                                                                                                        .mmsProxy),
                                                                                                                                                                                                31,
                                                                                                                                                                                                this
                                                                                                                                                                                                        .mmsPort),
                                                                                                                                                                                31),
                                                                                                                                                                31,
                                                                                                                                                                this
                                                                                                                                                                        .apnType),
                                                                                                                                                31),
                                                                                                                                31),
                                                                                                                31,
                                                                                                                this
                                                                                                                        .apnEnable),
                                                                                                31,
                                                                                                this
                                                                                                        .networkType),
                                                                                31),
                                                                31),
                                                31,
                                                this.apnEnableEnabled),
                                        31,
                                        this.newApn),
                                31),
                        31,
                        this.validEnabled);
    }

    public final boolean isFieldEnabled(String... strArr) {
        CustomizedConfig customizedConfig = this.customizedConfig;
        if (customizedConfig.readOnlyApn) {
            return false;
        }
        for (String str : strArr) {
            if (!(true ^ customizedConfig.readOnlyApnFields.contains(str))) {
                return false;
            }
        }
        return true;
    }

    public final String toString() {
        return "ApnData(id="
                + this.id
                + ", name="
                + this.name
                + ", apn="
                + this.apn
                + ", proxy="
                + this.proxy
                + ", port="
                + this.port
                + ", userName="
                + this.userName
                + ", passWord="
                + this.passWord
                + ", server="
                + this.server
                + ", mmsc="
                + this.mmsc
                + ", mmsProxy="
                + this.mmsProxy
                + ", mmsPort="
                + this.mmsPort
                + ", authType="
                + this.authType
                + ", apnType="
                + this.apnType
                + ", apnProtocol="
                + this.apnProtocol
                + ", apnRoaming="
                + this.apnRoaming
                + ", apnEnable="
                + this.apnEnable
                + ", networkType="
                + this.networkType
                + ", edited="
                + this.edited
                + ", userEditable="
                + this.userEditable
                + ", apnEnableEnabled="
                + this.apnEnableEnabled
                + ", newApn="
                + this.newApn
                + ", subId="
                + this.subId
                + ", validEnabled="
                + this.validEnabled
                + ", customizedConfig="
                + this.customizedConfig
                + ")";
    }

    public ApnData(
            int i,
            String name,
            String apn,
            String proxy,
            String port,
            String userName,
            String passWord,
            String server,
            String mmsc,
            String mmsProxy,
            String mmsPort,
            int i2,
            String apnType,
            int i3,
            int i4,
            boolean z,
            long j,
            int i5,
            int i6,
            boolean z2,
            boolean z3,
            int i7,
            boolean z4,
            CustomizedConfig customizedConfig) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(apn, "apn");
        Intrinsics.checkNotNullParameter(proxy, "proxy");
        Intrinsics.checkNotNullParameter(port, "port");
        Intrinsics.checkNotNullParameter(userName, "userName");
        Intrinsics.checkNotNullParameter(passWord, "passWord");
        Intrinsics.checkNotNullParameter(server, "server");
        Intrinsics.checkNotNullParameter(mmsc, "mmsc");
        Intrinsics.checkNotNullParameter(mmsProxy, "mmsProxy");
        Intrinsics.checkNotNullParameter(mmsPort, "mmsPort");
        Intrinsics.checkNotNullParameter(apnType, "apnType");
        Intrinsics.checkNotNullParameter(customizedConfig, "customizedConfig");
        this.id = i;
        this.name = name;
        this.apn = apn;
        this.proxy = proxy;
        this.port = port;
        this.userName = userName;
        this.passWord = passWord;
        this.server = server;
        this.mmsc = mmsc;
        this.mmsProxy = mmsProxy;
        this.mmsPort = mmsPort;
        this.authType = i2;
        this.apnType = apnType;
        this.apnProtocol = i3;
        this.apnRoaming = i4;
        this.apnEnable = z;
        this.networkType = j;
        this.edited = i5;
        this.userEditable = i6;
        this.apnEnableEnabled = z2;
        this.newApn = z3;
        this.subId = i7;
        this.validEnabled = z4;
        this.customizedConfig = customizedConfig;
    }
}
