package com.samsung.android.settings.analyzestorage.presenter.constant;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.function.Predicate;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001a\n"
                + "\u0000\n"
                + "\u0002\u0010\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\r"
                + "R\u0017\u0010\u0004\u001a\u00020\u00038\u0006¢\u0006\f\n"
                + "\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010"
                + "\t\u001a\u00020\b8\u0006¢\u0006\f\n"
                + "\u0004\b\t\u0010\n"
                + "\u001a\u0004\b\u000b\u0010\f¨\u0006\u000e"
        },
        d2 = {
            "com/samsung/android/settings/analyzestorage/presenter/constant/CloudConstants$CloudType",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/settings/analyzestorage/presenter/constant/CloudConstants$CloudType;",
            ApnSettings.MVNO_NONE,
            "value",
            ImsProfile.TIMER_NAME_I,
            "getValue",
            "()I",
            ApnSettings.MVNO_NONE,
            "accountType",
            "Ljava/lang/String;",
            "getAccountType",
            "()Ljava/lang/String;",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class CloudConstants$CloudType {
    public static final /* synthetic */ CloudConstants$CloudType[] $VALUES;
    public static final Companion Companion;
    public static final CloudConstants$CloudType GOOGLE_DRIVE;
    public static final CloudConstants$CloudType NONE;
    private final String accountType;
    private final int value;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
        public static CloudConstants$CloudType fromDomainType(final int i) {
            Object orElse =
                    Arrays.stream(CloudConstants$CloudType.values())
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.settings.analyzestorage.presenter.constant.CloudConstants$CloudType$Companion$fromDomainType$1
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            CloudConstants$CloudType cloudType =
                                                    (CloudConstants$CloudType) obj;
                                            Intrinsics.checkNotNullParameter(
                                                    cloudType, "cloudType");
                                            return cloudType.getValue() == i;
                                        }
                                    })
                            .findAny()
                            .orElse(CloudConstants$CloudType.NONE);
            Intrinsics.checkNotNullExpressionValue(orElse, "orElse(...)");
            return (CloudConstants$CloudType) orElse;
        }
    }

    static {
        CloudConstants$CloudType cloudConstants$CloudType =
                new CloudConstants$CloudType(0, 102, "ONE_DRIVE", "com.microsoft.skydrive");
        CloudConstants$CloudType cloudConstants$CloudType2 =
                new CloudConstants$CloudType(
                        1, 101, "GOOGLE_DRIVE", RestrictionPolicy.GOOGLE_ACCOUNT_TYPE);
        GOOGLE_DRIVE = cloudConstants$CloudType2;
        CloudConstants$CloudType cloudConstants$CloudType3 =
                new CloudConstants$CloudType(2, -1, "NONE", ApnSettings.MVNO_NONE);
        NONE = cloudConstants$CloudType3;
        CloudConstants$CloudType[] cloudConstants$CloudTypeArr = {
            cloudConstants$CloudType, cloudConstants$CloudType2, cloudConstants$CloudType3
        };
        $VALUES = cloudConstants$CloudTypeArr;
        EnumEntriesKt.enumEntries(cloudConstants$CloudTypeArr);
        Companion = new Companion();
    }

    public CloudConstants$CloudType(int i, int i2, String str, String str2) {
        this.value = i2;
        this.accountType = str2;
    }

    public static CloudConstants$CloudType valueOf(String str) {
        return (CloudConstants$CloudType) Enum.valueOf(CloudConstants$CloudType.class, str);
    }

    public static CloudConstants$CloudType[] values() {
        return (CloudConstants$CloudType[]) $VALUES.clone();
    }

    public final String getAccountType() {
        return this.accountType;
    }

    public final int getValue() {
        return this.value;
    }
}
