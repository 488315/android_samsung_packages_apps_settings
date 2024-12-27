package com.android.settings;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.AttributeSet;

import androidx.preference.Preference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class BrightnessPreference extends Preference {
    public BrightnessPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        getContext()
                .startActivityAsUser(
                        new Intent("com.android.intent.action.SHOW_BRIGHTNESS_DIALOG")
                                .setPackage("com.android.systemui"),
                        UserHandle.CURRENT_OR_SELF);
    }
}
