package com.samsung.android.settings.bixby.target;

import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.android.settings.Utils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.display.SecDisplayUtils;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ScreenTimeoutAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetEntryListAction() {
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        CharSequence[] screenTimeoutEntryandValue =
                SecDisplayUtils.getScreenTimeoutEntryandValue(
                        context,
                        Long.valueOf(
                                Settings.System.getLong(
                                        context.getContentResolver(),
                                        "screen_off_timeout",
                                        30000L)),
                        2);
        ArrayList arrayList = new ArrayList();
        if (screenTimeoutEntryandValue != null) {
            for (CharSequence charSequence : screenTimeoutEntryandValue) {
                arrayList.add(Long.valueOf(charSequence.toString()));
            }
        }
        boolean isEmpty = arrayList.isEmpty();
        String str = ApnSettings.MVNO_NONE;
        if (!isEmpty) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                StringBuilder m =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                str);
                m.append(String.valueOf(arrayList.get(i)));
                str = m.toString();
                if (i != size - 1) {
                    str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ",");
                }
            }
        }
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        String str;
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_config_screen_timeout");
        if (!(Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext))
                && checkIfRestrictionEnforced == null) {
            Intent intent = new Intent("com.samsung.settings.ScreenTimeout");
            intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
            intent.addFlags(268468224);
            Utils.setTaskIdToIntent(intent, getTaskId());
            launchSettings(intent, null);
            str = "success";
        } else {
            str = "temporary_unavailable";
        }
        return Action.createResult(str);
    }
}
