package com.android.settings.accounts;

import com.android.settings.R;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AccountPreferenceController$$ExternalSyntheticLambda2
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AccountPreferenceController f$0;

    public /* synthetic */ AccountPreferenceController$$ExternalSyntheticLambda2(
            AccountPreferenceController accountPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = accountPreferenceController;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        String string;
        String string2;
        String string3;
        String string4;
        int i = this.$r8$classId;
        AccountPreferenceController accountPreferenceController = this.f$0;
        switch (i) {
            case 0:
                string =
                        accountPreferenceController.mContext.getString(
                                R.string.managed_profile_not_available_label);
                return string;
            case 1:
                string2 =
                        accountPreferenceController.mContext.getString(
                                R.string.remove_managed_profile_label);
                return string2;
            case 2:
                string3 =
                        accountPreferenceController.mContext.getString(
                                R.string.managed_profile_settings_title);
                return string3;
            default:
                string4 =
                        accountPreferenceController.mContext.getString(
                                R.string.managed_profile_settings_title);
                return string4;
        }
    }
}
