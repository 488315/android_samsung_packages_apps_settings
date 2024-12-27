package com.android.settings;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final /* synthetic */ class SettingsPreferenceFragment$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingsPreferenceFragment f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SettingsPreferenceFragment$$ExternalSyntheticLambda0(
            SettingsPreferenceFragment settingsPreferenceFragment, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = settingsPreferenceFragment;
        this.f$1 = i;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
        }
        return this.f$0.getString(this.f$1);
    }
}
