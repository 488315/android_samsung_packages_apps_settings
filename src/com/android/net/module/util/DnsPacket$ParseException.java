package com.android.net.module.util;

import com.android.net.module.annotation.NonNull;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class DnsPacket$ParseException extends RuntimeException {
    public String reason;

    public DnsPacket$ParseException(@NonNull String str) {
        super(str);
        this.reason = str;
    }
}
