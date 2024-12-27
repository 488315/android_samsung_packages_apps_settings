package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Api;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TelemetryLoggingOptions implements Api.ApiOptions {
    public static final TelemetryLoggingOptions zaa = new TelemetryLoggingOptions();

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TelemetryLoggingOptions)) {
            return false;
        }
        ((TelemetryLoggingOptions) obj).getClass();
        return Objects.equal(null, null);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[] {null});
    }
}
