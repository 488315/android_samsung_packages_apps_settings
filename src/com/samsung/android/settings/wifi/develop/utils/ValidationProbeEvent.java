package com.samsung.android.settings.wifi.develop.utils;

import com.android.internal.util.MessageUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ValidationProbeEvent {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Decoder {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            MessageUtils.findMessageNames(
                    new Class[] {ValidationProbeEvent.class},
                    new String[] {"PROBE_", "FIRST_", "REVALIDATION"});
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class probeName {
        public static final /* synthetic */ probeName[] $VALUES;
        public static final probeName PROBE_DNS;
        public static final probeName PROBE_HTTP;
        public static final probeName PROBE_HTTPS;
        public static final probeName UNKNOWN;

        static {
            probeName probename = new probeName("PROBE_DNS", 0);
            PROBE_DNS = probename;
            probeName probename2 = new probeName("PROBE_HTTP", 1);
            PROBE_HTTP = probename2;
            probeName probename3 = new probeName("PROBE_HTTPS", 2);
            PROBE_HTTPS = probename3;
            probeName probename4 = new probeName("UNKNOWN", 3);
            UNKNOWN = probename4;
            $VALUES = new probeName[] {probename, probename2, probename3, probename4};
        }

        public static probeName valueOf(String str) {
            return (probeName) Enum.valueOf(probeName.class, str);
        }

        public static probeName[] values() {
            return (probeName[]) $VALUES.clone();
        }
    }
}
