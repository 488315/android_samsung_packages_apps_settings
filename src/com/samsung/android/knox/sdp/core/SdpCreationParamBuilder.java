package com.samsung.android.knox.sdp.core;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SdpCreationParamBuilder {
    private String mAlias;
    private int mFlags;
    private ArrayList<SdpDomain> mPrivilegedApps;

    public SdpCreationParamBuilder(String str, int i) {
        this.mAlias = str == null ? ApnSettings.MVNO_NONE : str;
        this.mFlags = validateFlags(i);
        this.mPrivilegedApps = new ArrayList<>();
    }

    private int validateFlags(int i) {
        if (i < 0 || i > 1) {
            return 0;
        }
        return i;
    }

    public void addPrivilegedApp(SdpDomain sdpDomain) {
        this.mPrivilegedApps.add(sdpDomain);
    }

    public SdpCreationParam getParam() {
        if (this.mAlias == null) {
            return null;
        }
        return new SdpCreationParam(this.mAlias, this.mFlags, this.mPrivilegedApps);
    }

    public void setMdfpp() {
        this.mFlags &= -2;
    }

    public void setMinor() {
        this.mFlags |= 1;
    }
}
