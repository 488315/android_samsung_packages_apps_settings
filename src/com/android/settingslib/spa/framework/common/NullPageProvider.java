package com.android.settingslib.spa.framework.common;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NullPageProvider implements SettingsPageProvider {
    public static final NullPageProvider INSTANCE = new NullPageProvider();

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "NULL";
    }
}
