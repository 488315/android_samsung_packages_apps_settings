package com.android.settings.notification.modes;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class IconOptionsProvider$IconInfo extends Record {
    public final String description;
    public final int resId;

    public IconOptionsProvider$IconInfo(int i, String str) {
        this.resId = i;
        this.description = str;
    }

    @Override // java.lang.Record
    public final boolean equals(Object obj) {
        return (boolean)
                ObjectMethods.bootstrap(
                                MethodHandles.lookup(),
                                "equals",
                                MethodType.methodType(
                                        Boolean.TYPE,
                                        IconOptionsProvider$IconInfo.class,
                                        Object.class),
                                IconOptionsProvider$IconInfo.class,
                                "resId;description",
                                "FIELD:Lcom/android/settings/notification/modes/IconOptionsProvider$IconInfo;->resId:I",
                                "FIELD:Lcom/android/settings/notification/modes/IconOptionsProvider$IconInfo;->description:Ljava/lang/String;")
                        .dynamicInvoker()
                        .invoke(this, obj) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int)
                ObjectMethods.bootstrap(
                                MethodHandles.lookup(),
                                "hashCode",
                                MethodType.methodType(
                                        Integer.TYPE, IconOptionsProvider$IconInfo.class),
                                IconOptionsProvider$IconInfo.class,
                                "resId;description",
                                "FIELD:Lcom/android/settings/notification/modes/IconOptionsProvider$IconInfo;->resId:I",
                                "FIELD:Lcom/android/settings/notification/modes/IconOptionsProvider$IconInfo;->description:Ljava/lang/String;")
                        .dynamicInvoker()
                        .invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String)
                ObjectMethods.bootstrap(
                                MethodHandles.lookup(),
                                "toString",
                                MethodType.methodType(
                                        String.class, IconOptionsProvider$IconInfo.class),
                                IconOptionsProvider$IconInfo.class,
                                "resId;description",
                                "FIELD:Lcom/android/settings/notification/modes/IconOptionsProvider$IconInfo;->resId:I",
                                "FIELD:Lcom/android/settings/notification/modes/IconOptionsProvider$IconInfo;->description:Ljava/lang/String;")
                        .dynamicInvoker()
                        .invoke(this) /* invoke-custom */;
    }
}
