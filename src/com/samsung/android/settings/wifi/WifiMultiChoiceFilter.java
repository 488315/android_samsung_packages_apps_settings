package com.samsung.android.settings.wifi;

import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.wifi.WifiPolicy;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiMultiChoiceFilter {
    public static final String[] items = {
        WifiPolicy.SECURITY_TYPE_OPEN,
        "Secured",
        "2GHz",
        "5GHz",
        "6GHz",
        "Wi-Fi 5",
        "Wi-Fi 6",
        "Wi-Fi 7"
    };
    public final ArrayList filters = new ArrayList();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"
            },
            d2 = {
                "com/samsung/android/settings/wifi/WifiMultiChoiceFilter$MultiChoiceFilter",
                ApnSettings.MVNO_NONE,
                "Lcom/samsung/android/settings/wifi/WifiMultiChoiceFilter$MultiChoiceFilter;",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0})
    public final class MultiChoiceFilter {
        public static final /* synthetic */ MultiChoiceFilter[] $VALUES;
        public static final MultiChoiceFilter FREQUENCY_2_4;
        public static final MultiChoiceFilter FREQUENCY_5;
        public static final MultiChoiceFilter FREQUENCY_6;
        public static final MultiChoiceFilter OPEN;
        public static final MultiChoiceFilter SECURED;
        public static final MultiChoiceFilter WIFI_5;
        public static final MultiChoiceFilter WIFI_6;
        public static final MultiChoiceFilter WIFI_7;

        static {
            MultiChoiceFilter multiChoiceFilter = new MultiChoiceFilter("OPEN", 0);
            OPEN = multiChoiceFilter;
            MultiChoiceFilter multiChoiceFilter2 = new MultiChoiceFilter("SECURED", 1);
            SECURED = multiChoiceFilter2;
            MultiChoiceFilter multiChoiceFilter3 = new MultiChoiceFilter("FREQUENCY_2_4", 2);
            FREQUENCY_2_4 = multiChoiceFilter3;
            MultiChoiceFilter multiChoiceFilter4 = new MultiChoiceFilter("FREQUENCY_5", 3);
            FREQUENCY_5 = multiChoiceFilter4;
            MultiChoiceFilter multiChoiceFilter5 = new MultiChoiceFilter("FREQUENCY_6", 4);
            FREQUENCY_6 = multiChoiceFilter5;
            MultiChoiceFilter multiChoiceFilter6 = new MultiChoiceFilter("WIFI_5", 5);
            WIFI_5 = multiChoiceFilter6;
            MultiChoiceFilter multiChoiceFilter7 = new MultiChoiceFilter("WIFI_6", 6);
            WIFI_6 = multiChoiceFilter7;
            MultiChoiceFilter multiChoiceFilter8 = new MultiChoiceFilter("WIFI_7", 7);
            WIFI_7 = multiChoiceFilter8;
            MultiChoiceFilter[] multiChoiceFilterArr = {
                multiChoiceFilter,
                multiChoiceFilter2,
                multiChoiceFilter3,
                multiChoiceFilter4,
                multiChoiceFilter5,
                multiChoiceFilter6,
                multiChoiceFilter7,
                multiChoiceFilter8
            };
            $VALUES = multiChoiceFilterArr;
            EnumEntriesKt.enumEntries(multiChoiceFilterArr);
        }

        public static MultiChoiceFilter valueOf(String str) {
            return (MultiChoiceFilter) Enum.valueOf(MultiChoiceFilter.class, str);
        }

        public static MultiChoiceFilter[] values() {
            return (MultiChoiceFilter[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MultiChoiceFilter.values().length];
            try {
                MultiChoiceFilter multiChoiceFilter = MultiChoiceFilter.OPEN;
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                MultiChoiceFilter multiChoiceFilter2 = MultiChoiceFilter.OPEN;
                iArr[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                MultiChoiceFilter multiChoiceFilter3 = MultiChoiceFilter.OPEN;
                iArr[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                MultiChoiceFilter multiChoiceFilter4 = MultiChoiceFilter.OPEN;
                iArr[3] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                MultiChoiceFilter multiChoiceFilter5 = MultiChoiceFilter.OPEN;
                iArr[4] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                MultiChoiceFilter multiChoiceFilter6 = MultiChoiceFilter.OPEN;
                iArr[5] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                MultiChoiceFilter multiChoiceFilter7 = MultiChoiceFilter.OPEN;
                iArr[6] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                MultiChoiceFilter multiChoiceFilter8 = MultiChoiceFilter.OPEN;
                iArr[7] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static boolean isBandMatching(MultiChoiceFilter filter, WifiEntry entry) {
        Intrinsics.checkNotNullParameter(filter, "filter");
        Intrinsics.checkNotNullParameter(entry, "entry");
        int ordinal = filter.ordinal();
        if (ordinal == 2) {
            return new ArrayList(entry.semGetAllScanResult())
                    .stream().anyMatch(WifiMultiChoiceFilter$isBandMatching$1.INSTANCE);
        }
        if (ordinal == 3) {
            return new ArrayList(entry.semGetAllScanResult())
                    .stream().anyMatch(WifiMultiChoiceFilter$isBandMatching$1.INSTANCE$1);
        }
        if (ordinal != 4) {
            return true;
        }
        return new ArrayList(entry.semGetAllScanResult())
                .stream().anyMatch(WifiMultiChoiceFilter$isBandMatching$1.INSTANCE$2);
    }
}
