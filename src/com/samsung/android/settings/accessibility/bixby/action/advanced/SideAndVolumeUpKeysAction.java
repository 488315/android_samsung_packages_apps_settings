package com.samsung.android.settings.accessibility.bixby.action.advanced;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SideAndVolumeUpKeysAction extends BixbyActionTarget {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.advanced.shortcut.SideAndVolumeUpSwitchPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final List getTargetRestrictionKeys() {
        return List.of("accessibility_advanced_settings", "accessibility_power_and_volume_up_keys");
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.side_volume_up_shortcut_title;
    }
}
