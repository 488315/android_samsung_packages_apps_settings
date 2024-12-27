package com.samsung.android.knox.kpm;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
class RequestInfo {
    public static final int CMD_IS_REGISTERED = 3;
    public static final int CMD_REGISTER = 1;
    public static final int CMD_UNREGISTER = 2;
    public int mCmd;
    public boolean mForce;

    public RequestInfo(int i) {
        this.mCmd = i;
        this.mForce = false;
    }

    public int getCmd() {
        return this.mCmd;
    }

    public boolean isForce() {
        return this.mForce;
    }

    public RequestInfo(int i, boolean z) {
        this.mCmd = i;
        this.mForce = z;
    }
}
