package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.content.Context;
import android.content.Intent;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibrationPatternTryItem extends AbstractTryItem {
    public final BasePreferenceController controller;

    public VibrationPatternTryItem(Context context) {
        super(context);
        this.controller =
                SecAccessibilityUtils.getPreferenceController(
                        context,
                        "dummy",
                        "com.samsung.android.settings.asbase.vibration.SecSoundDeviceVibrationController");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem,
              // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Intent getLaunchIntent() {
        return new Intent("android.intent.action.MAIN")
                .putExtra("vibration_type", 0)
                .setClassName(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.samsung.android.settings.asbase.vibration.VibPickerContainer");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of(
                "connected_audio_balance",
                "speaker_audio_balance",
                "all_sound_off_key",
                "call_hearing_aid",
                "call_hearing_aid_in_hearing",
                "mono_audio_support_dual_spk_key",
                "mono_audio_key",
                "amplify_ambient_sound");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.sec_vibration_call_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        BasePreferenceController basePreferenceController = this.controller;
        if (basePreferenceController == null) {
            return false;
        }
        int availabilityStatus = basePreferenceController.getAvailabilityStatus();
        return availabilityStatus == 0 || availabilityStatus == 1;
    }
}
