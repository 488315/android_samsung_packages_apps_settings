package com.android.settingslib.spaprivileged.settingsprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsGlobalBooleanDelegate implements ReadWriteProperty {
    public final ContentResolver contentResolver;
    public final boolean defaultValue;
    public final String name;

    public SettingsGlobalBooleanDelegate(Context context, String str, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.name = str;
        this.defaultValue = z;
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        this.contentResolver = contentResolver;
    }

    @Override // kotlin.properties.ReadOnlyProperty
    public final Object getValue(Object obj, KProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        return Boolean.valueOf(
                Settings.Global.getInt(this.contentResolver, this.name, this.defaultValue ? 1 : 0)
                        != 0);
    }
}
