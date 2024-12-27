package com.google.android.material.util;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0010\n"
                + "\u0002\u0010\u0007\n"
                + "\u0002\b\u0005\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n"
                + "\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
        },
        d2 = {
            "Lcom/google/android/material/util/MaxFontScaleRatio;",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "ratio",
            ImsProfile.TIMER_NAME_F,
            "getRatio",
            "()F",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0})
/* loaded from: classes3.dex */
public enum MaxFontScaleRatio {
    SMALL(1.0f),
    MEDIUM(1.15f),
    /* JADX INFO: Fake field, exist only in values array */
    LARGE(1.3f),
    /* JADX INFO: Fake field, exist only in values array */
    EXTRA_LARGE(1.5f),
    /* JADX INFO: Fake field, exist only in values array */
    HUGE(1.7f),
    /* JADX INFO: Fake field, exist only in values array */
    EXTRA_HUGE(2.0f);

    private final float ratio;

    MaxFontScaleRatio(float f) {
        this.ratio = f;
    }

    public final float getRatio() {
        return this.ratio;
    }
}
