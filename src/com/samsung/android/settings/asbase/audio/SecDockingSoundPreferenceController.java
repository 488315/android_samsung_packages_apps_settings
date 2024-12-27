package com.samsung.android.settings.asbase.audio;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.notification.SettingPref;
import com.android.settings.notification.SettingPrefController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecDockingSoundPreferenceController extends SettingPrefController {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.audio.SecDockingSoundPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends SettingPref {
        @Override // com.android.settings.notification.SettingPref
        public final boolean isApplicable(Context context) {
            return context.getResources().getBoolean(R.bool.has_dock_settings);
        }
    }
}
