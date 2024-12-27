package com.android.settings.notification;

import android.content.Context;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DockingSoundPreferenceController extends SettingPrefController {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.DockingSoundPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends SettingPref {
        @Override // com.android.settings.notification.SettingPref
        public final boolean isApplicable(Context context) {
            return context.getResources().getBoolean(R.bool.has_dock_settings);
        }
    }
}
