package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;

import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LogBuilders$EventBuilder extends LogBuilders$LogBuilder {
    public final Map build() {
        if (!((HashMap) this.logs).containsKey("en")) {
            Utils.throwException("Failure to build Log : Event name cannot be null");
        }
        set("t", "ev");
        set("ts", String.valueOf(System.currentTimeMillis()));
        return this.logs;
    }

    public final void setEventName(String str) {
        if (TextUtils.isEmpty(str)) {
            Utils.throwException("Failure to build Log : Event name cannot be null");
        }
        set("en", str);
    }

    @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders$LogBuilder
    public final LogBuilders$LogBuilder getThis() {
        return this;
    }
}
