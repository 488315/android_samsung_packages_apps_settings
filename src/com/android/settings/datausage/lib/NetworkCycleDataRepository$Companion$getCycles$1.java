package com.android.settings.datausage.lib;

import android.util.Range;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

import java.time.ZonedDateTime;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0014\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\t\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\u0010\u0000\u001a\u0010\u0012\f\u0012\n"
                + " \u0003*\u0004\u0018\u00010\u00020\u00020\u00012*\u0010\u0004\u001a&\u0012\f\u0012\n"
                + " \u0003*\u0004\u0018\u00010\u00050\u0005 \u0003*\u0012\u0012\f\u0012\n"
                + " \u0003*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00010\u0001H\n"
                + "¢\u0006\u0002\b\u0006"
        },
        d2 = {
            "<anonymous>",
            "Landroid/util/Range;",
            ApnSettings.MVNO_NONE,
            "kotlin.jvm.PlatformType",
            "it",
            "Ljava/time/ZonedDateTime;",
            "invoke"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class NetworkCycleDataRepository$Companion$getCycles$1 extends Lambda implements Function1 {
    public static final NetworkCycleDataRepository$Companion$getCycles$1 INSTANCE =
            new NetworkCycleDataRepository$Companion$getCycles$1();

    public NetworkCycleDataRepository$Companion$getCycles$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Range range = (Range) obj;
        return new Range(
                Long.valueOf(((ZonedDateTime) range.getLower()).toInstant().toEpochMilli()),
                Long.valueOf(((ZonedDateTime) range.getUpper()).toInstant().toEpochMilli()));
    }
}
