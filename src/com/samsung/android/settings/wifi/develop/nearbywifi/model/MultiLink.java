package com.samsung.android.settings.wifi.develop.nearbywifi.model;

import android.net.wifi.ScanResult;

import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.util.SemLog;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MultiLink {
    public final List mAffiliatedLinks;
    public final short mEmlCapabilities;
    public final int mLinkId;
    public final short mMldCapabilities;
    public final String mMldMacAddress;
    public final boolean mPresent;

    public MultiLink(ByteBuffer byteBuffer, String str, int i) {
        int i2 = 0;
        this.mPresent = false;
        this.mLinkId = -1;
        this.mMldMacAddress = null;
        ArrayList arrayList = new ArrayList();
        this.mAffiliatedLinks = arrayList;
        if (byteBuffer.capacity() < 2) {
            SemLog.w("NearbyWifi.MultiLink", "Invalid Multi-Link IE len: " + byteBuffer.capacity());
            return;
        }
        int i3 = byteBuffer.getShort() & 7;
        if (i3 != 0) {
            SemLog.w("NearbyWifi.MultiLink", "Invalid/Unsupported Multi-Link IE type: " + i3);
            return;
        }
        int i4 = 9;
        if (byteBuffer.capacity() < 9) {
            SemLog.w(
                    "NearbyWifi.MultiLink",
                    "Invalid Multi-Link IE len: " + byteBuffer.capacity() + " " + str);
            return;
        }
        int i5 = byteBuffer.get(2) & 255;
        if (i5 < 7) {
            SemLog.w("NearbyWifi.MultiLink", "Invalid Common Info field length: " + i5);
        } else {
            this.mMldMacAddress = Repository.getMacAddressString(3, byteBuffer);
            if ((byteBuffer.get(0) & HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED) != 0) {
                if (byteBuffer.capacity() <= 9 || i5 < 8) {
                    SemLog.w(
                            "NearbyWifi.MultiLink",
                            "Invalid Multi-Link(1) IE len: "
                                    + byteBuffer.capacity()
                                    + " Common info len: "
                                    + i5);
                } else {
                    this.mLinkId = byteBuffer.get(9) & 15;
                    i4 = 10;
                }
            }
            i4 = (byteBuffer.get(0) & HwConstants.IQ_CONFIG_POS_WIFI_ENABLED) != 0 ? i4 + 1 : i4;
            i4 = (byteBuffer.get(0) & 64) != 0 ? i4 + 2 : i4;
            if ((byteBuffer.get(0) & 128) != 0) {
                if (byteBuffer.capacity() > i4) {
                    int i6 = i4 + 2;
                    if (i5 >= i4) {
                        this.mEmlCapabilities = byteBuffer.getShort(i4);
                        i4 = i6;
                    }
                }
                SemLog.w(
                        "NearbyWifi.MultiLink",
                        "Invalid Multi-Link(2) IE len: "
                                + byteBuffer.capacity()
                                + " Common info len: "
                                + i5);
            }
            if ((byteBuffer.get(1) & 1) != 0) {
                if (byteBuffer.capacity() <= i4 || i5 < i4) {
                    SemLog.w(
                            "NearbyWifi.MultiLink",
                            "Invalid Multi-Link(3) IE len: "
                                    + byteBuffer.capacity()
                                    + " Common info len: "
                                    + i5);
                } else {
                    this.mMldCapabilities = byteBuffer.getShort(i4);
                }
            }
            i2 = i5;
        }
        if (i2 == 0) {
            return;
        }
        if (arrayList.size() == 0) {
            MloLink mloLink = new MloLink();
            mloLink.linkId = this.mLinkId;
            mloLink.bssid = str;
            mloLink.freq = i;
            ScanResult.convertFrequencyMhzToChannelIfSupported(i);
            arrayList.add(mloLink);
        }
        this.mPresent = true;
    }
}
