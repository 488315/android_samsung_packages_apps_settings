package com.samsung.android.settings.bixby.target;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.display.SecDisplayUtils;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class WhiteBalanceAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doChangeAction() {
        Bundle bundle = new Bundle();
        Context context = this.mContext;
        int intValue = Integer.valueOf(getValue()).intValue();
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        if (intValue < 1 || intValue > 5) {
            bundle.putString("result", "fail");
        } else {
            Settings.System.putIntForUser(
                    context.getContentResolver(), "sec_display_preset_index", 5 - intValue, 0);
            bundle.putString("result", "success");
            bundle.putString(
                    "value",
                    String.valueOf(
                            5
                                    - Settings.System.getIntForUser(
                                            this.mContext.getContentResolver(),
                                            "sec_display_preset_index",
                                            2,
                                            0)));
        }
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "screen_mode_automatic_setting", 1);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "doGetAction: adaptiveValue = ", "WhiteBalanceAction");
        if (i != 1) {
            return Action.createResult("fail");
        }
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        int intForUser =
                5
                        - Settings.System.getIntForUser(
                                context.getContentResolver(), "sec_display_preset_index", 2, 0);
        Bundle bundle = new Bundle();
        bundle.putString("result", String.valueOf(intForUser));
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetEntryListAction() {
        Bundle bundle = new Bundle();
        HashMap hashMap = new HashMap();
        hashMap.put(1, 1);
        hashMap.put(2, 2);
        hashMap.put(3, 3);
        hashMap.put(4, 4);
        hashMap.put(5, 5);
        bundle.putSerializable("entryList", hashMap);
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        if (!Rune.supportScreenMode()) {
            return Action.createResult("whiteBalance_disabled_by_not_support_screen_mode");
        }
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "screen_mode_setting", -1);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "doGetAction: curScreenMode = ", "WhiteBalanceAction");
        if (i == 3) {
            return Action.createResult(
                    "whiteBalance_can_not_be_changed_by_natural_screen_selection");
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = Settings.System.getInt(contentResolver, "blue_light_filter", 0) != 0;
        boolean z2 = Settings.System.getInt(contentResolver, "ead_enabled", 0) != 0;
        if (!z && !z2) {
            return Action.createResult("true");
        }
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append("eye_comfort_shield");
        }
        if (z2) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append("adaptive_color_tone");
        }
        Bundle bundle = new Bundle();
        bundle.putString("result", sb.toString());
        return bundle;
    }
}
