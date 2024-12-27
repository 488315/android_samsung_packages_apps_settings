package com.android.settingslib.spa.widget.illustration;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0010\n"
                + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"
        },
        d2 = {
            "Lcom/android/settingslib/spa/widget/illustration/ResourceType;",
            ApnSettings.MVNO_NONE,
            "frameworks__base__packages__SettingsLib__Spa__spa__android_common__SeslSpaLib"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class ResourceType {
    public static final /* synthetic */ ResourceType[] $VALUES;
    public static final ResourceType LOTTIE;

    static {
        ResourceType resourceType = new ResourceType("IMAGE", 0);
        ResourceType resourceType2 = new ResourceType("LOTTIE", 1);
        LOTTIE = resourceType2;
        ResourceType[] resourceTypeArr = {resourceType, resourceType2};
        $VALUES = resourceTypeArr;
        EnumEntriesKt.enumEntries(resourceTypeArr);
    }

    public static ResourceType valueOf(String str) {
        return (ResourceType) Enum.valueOf(ResourceType.class, str);
    }

    public static ResourceType[] values() {
        return (ResourceType[]) $VALUES.clone();
    }
}
