package com.samsung.android.settings.asbase.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;

import com.samsung.android.settings.asbase.audio.SecSoundEffectSoundController$$ExternalSyntheticLambda0;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SystemBooleanSettingObserver extends ContentObserver {
    public final Context context;
    public final Function1 onChanged;
    public boolean settingValue;
    public final String systemSettingName;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SystemBooleanSettingObserver(
            Context context,
            SecSoundEffectSoundController$$ExternalSyntheticLambda0
                    secSoundEffectSoundController$$ExternalSyntheticLambda0) {
        super(new Handler(Looper.getMainLooper()));
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.systemSettingName = "buds_enable";
        this.onChanged = secSoundEffectSoundController$$ExternalSyntheticLambda0;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        super.onChange(z);
        updateValue();
    }

    public final void updateValue() {
        boolean z =
                Settings.System.getInt(this.context.getContentResolver(), this.systemSettingName, 0)
                        == 1;
        if (this.settingValue != z) {
            this.onChanged.invoke(Boolean.valueOf(z));
        }
        this.settingValue = z;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "updateValue : settingValue=", "SoundCraft.SystemBooleanSettingObserver", z);
    }
}
