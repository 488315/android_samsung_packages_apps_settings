package com.samsung.android.knox.zt.config.securelog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SignalConfig {
    public static final String AGGRESSIVE = "Aggressive";
    public static final String CONFIDENCE_SCORE = "confidenceScore";
    public static final String MORE_AGGRESSIVE = "MoreAggressive";
    public static final String MOST_AGGRESSIVE = "MostAggressive";
    public static final String PHISHING = "phishing";
    public static final String STANDARD = "Standard";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ConfidenceScore {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ConfigSignalType {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PhishingConfig {}
}
