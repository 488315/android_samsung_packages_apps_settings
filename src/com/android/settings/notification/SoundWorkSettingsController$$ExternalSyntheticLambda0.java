package com.android.settings.notification;

import android.content.Context;

import com.android.settings.R;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SoundWorkSettingsController$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SoundWorkSettingsController$$ExternalSyntheticLambda0(
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
                        ((SoundWorkSettingsController) obj)
                                .mContext.getString(R.string.work_sound_same_as_personal);
                return string;
            case 1:
                return ((Context) obj).getString(R.string.work_sync_dialog_title);
            default:
                return ((Context) obj).getString(R.string.work_sync_dialog_message);
        }
    }
}
