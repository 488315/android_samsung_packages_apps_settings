package com.samsung.android.scs.ai.sdkcommon.feature;

import androidx.annotation.Keep;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Keep
@Metadata(
        d1 = {
            "\u0000$\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010$\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\t\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0005\b\u0087\b\u0018\u0000"
                + " \u00142\u00020\u0001:\u0001\u0014B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J"
                + "\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u0015\u0010\r"
                + "\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J)\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J"
                + "\t\u0010\u0012\u001a\u00020\u0006HÖ\u0001J"
                + "\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\b\u0010"
                + "\tR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\n"
                + "\u0010\u000b¨\u0006\u0015"
        },
        d2 = {
            "Lcom/samsung/android/scs/ai/sdkcommon/feature/FeatureConfig;",
            ApnSettings.MVNO_NONE,
            "appVersion",
            ApnSettings.MVNO_NONE,
            FeatureConfig.JSON_KEY_FEATURES,
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;Ljava/util/Map;)V",
            "getAppVersion",
            "()Ljava/lang/String;",
            "getFeatures",
            "()Ljava/util/Map;",
            "component1",
            "component2",
            "copy",
            "equals",
            ApnSettings.MVNO_NONE,
            "other",
            "hashCode",
            "toString",
            "Companion",
            "scs-ai-3.1.23_release"
        },
        k = 1,
        mv = {1, 7, 1},
        xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class FeatureConfig {
    public static final String JSON_KEY_APP_VERSION = "app_version";
    public static final String JSON_KEY_FEATURES = "features";
    private final String appVersion;
    private final Map<String, Integer> features;

    public FeatureConfig(String appVersion, Map<String, Integer> features) {
        Intrinsics.checkNotNullParameter(appVersion, "appVersion");
        Intrinsics.checkNotNullParameter(features, "features");
        this.appVersion = appVersion;
        this.features = features;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FeatureConfig copy$default(
            FeatureConfig featureConfig, String str, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            str = featureConfig.appVersion;
        }
        if ((i & 2) != 0) {
            map = featureConfig.features;
        }
        return featureConfig.copy(str, map);
    }

    /* renamed from: component1, reason: from getter */
    public final String getAppVersion() {
        return this.appVersion;
    }

    public final Map<String, Integer> component2() {
        return this.features;
    }

    public final FeatureConfig copy(String appVersion, Map<String, Integer> features) {
        Intrinsics.checkNotNullParameter(appVersion, "appVersion");
        Intrinsics.checkNotNullParameter(features, "features");
        return new FeatureConfig(appVersion, features);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FeatureConfig)) {
            return false;
        }
        FeatureConfig featureConfig = (FeatureConfig) other;
        return Intrinsics.areEqual(this.appVersion, featureConfig.appVersion)
                && Intrinsics.areEqual(this.features, featureConfig.features);
    }

    public final String getAppVersion() {
        return this.appVersion;
    }

    public final Map<String, Integer> getFeatures() {
        return this.features;
    }

    public int hashCode() {
        return this.features.hashCode() + (this.appVersion.hashCode() * 31);
    }

    public String toString() {
        return "FeatureConfig(appVersion=" + this.appVersion + ", features=" + this.features + ')';
    }
}
