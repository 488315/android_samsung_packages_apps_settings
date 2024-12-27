package com.samsung.android.settings.asbase.work;

import android.content.Context;

import com.android.settings.R;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecSoundWorkSettingsController$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecSoundWorkSettingsController$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        String string;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                string =
                        ((SecSoundWorkSettingsController) obj)
                                .mContext.getString(R.string.work_sound_same_as_personal);
                return string;
            case 1:
                return ((Context) obj).getString(R.string.sec_work_sync_dialog_title);
            default:
                Context context = (Context) obj;
                return String.format(
                        context.getString(R.string.sec_work_sync_dialog_message),
                        context.getString(R.string.sec_sound_work_personal_profile));
        }
    }
}
