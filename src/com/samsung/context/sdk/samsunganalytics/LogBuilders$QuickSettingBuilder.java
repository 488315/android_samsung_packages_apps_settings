package com.samsung.context.sdk.samsunganalytics;

import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LogBuilders$QuickSettingBuilder {
    public final Map map = new HashMap();

    public final Map build() {
        if (!((HashMap) this.map).isEmpty()) {
            String makeDelimiterString =
                    Delimiter.makeDelimiterString(this.map, Delimiter.Depth.TWO_DEPTH);
            ((HashMap) this.map).clear();
            ((HashMap) this.map).put("sti", makeDelimiterString);
            ((HashMap) this.map).put("ts", String.valueOf(System.currentTimeMillis()));
            ((HashMap) this.map).put("t", "st");
        }
        return this.map;
    }
}
