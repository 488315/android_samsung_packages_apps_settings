package com.samsung.android.settings.bixby.target;

import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.util.SemLog;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FontAction extends Action {
    public final Bundle createFailResult() {
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("result", "fail");
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        m.putString(
                "value",
                String.valueOf(
                        Settings.Global.getInt(context.getContentResolver(), "font_size", 2) + 1));
        return m;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doChangeAction() {
        int i;
        try {
            int parseInt = Integer.parseInt(getValue()) - 1;
            SemLog.d("FontAction", "index = " + parseInt);
            Context context = this.mContext;
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            Settings.Global.getInt(context.getContentResolver(), "font_size", 2);
            Log.i(
                    "SecDisplayUtils",
                    "changeFontSizeDbAndNotifyChanged() targetIndex : " + parseInt);
            if (parseInt < 0 || parseInt > 7) {
                Log.i("SecDisplayUtils", "changeFontSizeDbAndNotifyChanged() invalid index");
                i = -1;
            } else {
                Settings.Global.putInt(context.getContentResolver(), "font_size", parseInt);
                context.sendBroadcast(SecDisplayUtils.getFontSizeChangedIntent());
                Log.secD("SecDisplayUtils", "com.samsung.settings.FONT_SIZE_CHANGED broadcast.");
                SecDisplayUtils.writeFontScaleDBAllUser(
                        context, SecDisplayUtils.getFontScale(context, parseInt));
                i = parseInt;
            }
            if (i != parseInt) {
                return createFailResult();
            }
            Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("result", "success");
            m.putString(
                    "value",
                    String.valueOf(
                            Settings.Global.getInt(
                                            this.mContext.getContentResolver(), "font_size", 2)
                                    + 1));
            return m;
        } catch (NumberFormatException e) {
            SemLog.e("FontAction", e.getMessage());
            return createFailResult();
        }
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        boolean z = UserHandle.myUserId() >= 100;
        boolean z2 = UserHandle.myUserId() != 0;
        if (z || z2) {
            return Action.createResult("not_supported_device");
        }
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        int i = Settings.Global.getInt(context.getContentResolver(), "font_size", 2);
        Bundle bundle = new Bundle();
        bundle.putString("result", String.valueOf(i));
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetEntryListAction() {
        Bundle bundle = new Bundle();
        HashMap hashMap = new HashMap();
        int i = 0;
        while (i <= 7) {
            i++;
            hashMap.put(Integer.valueOf(i), Integer.valueOf(i));
        }
        bundle.putSerializable("entryList", hashMap);
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent = new Intent("com.samsung.settings.FontPreview");
        intent.addFlags(268468224);
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        Utils.setTaskIdToIntent(intent, getTaskId());
        launchSettings(intent, null);
        return Action.createResult("success");
    }
}
