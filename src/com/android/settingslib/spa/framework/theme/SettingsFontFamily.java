package com.android.settingslib.spa.framework.theme;

import androidx.compose.ui.text.font.FontFamily;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsFontFamily {
    public final FontFamily brand;
    public final FontFamily plain;

    public SettingsFontFamily(FontFamily fontFamily, FontFamily fontFamily2) {
        this.brand = fontFamily;
        this.plain = fontFamily2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SettingsFontFamily)) {
            return false;
        }
        SettingsFontFamily settingsFontFamily = (SettingsFontFamily) obj;
        return Intrinsics.areEqual(this.brand, settingsFontFamily.brand)
                && Intrinsics.areEqual(this.plain, settingsFontFamily.plain);
    }

    public final int hashCode() {
        return this.plain.hashCode() + (this.brand.hashCode() * 31);
    }

    public final String toString() {
        return "SettingsFontFamily(brand=" + this.brand + ", plain=" + this.plain + ")";
    }
}
