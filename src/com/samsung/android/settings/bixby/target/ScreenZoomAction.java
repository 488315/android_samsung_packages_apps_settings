package com.samsung.android.settings.bixby.target;

import android.content.Context;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.display.SecScreenZoomPreferenceFragment;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ScreenZoomAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doChangeAction() {
        Bundle bundle = new Bundle();
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        int intValue = Integer.valueOf(getValue()).intValue();
        int[] properDensities =
                SecDisplayUtils.getProperDensities(
                        context, SecScreenZoomPreferenceFragment.DENSITY_BASE_PIXEL);
        if (intValue < 1 || intValue > properDensities.length) {
            bundle.putString("result", "fail");
        } else {
            SecDisplayUtils.applyForcedDisplayDensity(-1, -1, properDensities[intValue - 1]);
            bundle.putString("result", "success");
            bundle.putString("value", getValue());
        }
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        int[] properDensities =
                SecDisplayUtils.getProperDensities(
                        context, SecScreenZoomPreferenceFragment.DENSITY_BASE_PIXEL);
        int currentDensity = SecDisplayUtils.getCurrentDensity(context);
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= properDensities.length) {
                break;
            }
            if (currentDensity == properDensities[i2]) {
                i = i2;
                break;
            }
            i2++;
        }
        Bundle bundle = new Bundle();
        bundle.putString("result", String.valueOf(i + 1));
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetEntryListAction() {
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        int length =
                SecDisplayUtils.getProperDensities(
                                context, SecScreenZoomPreferenceFragment.DENSITY_BASE_PIXEL)
                        .length;
        Bundle bundle = new Bundle();
        HashMap hashMap = new HashMap();
        for (int i = 1; i <= length; i++) {
            hashMap.put(Integer.valueOf(i), Integer.valueOf(i));
        }
        bundle.putSerializable("entryList", hashMap);
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        return (SemPersonaManager.isKnoxId(UserHandle.myUserId())
                        || (UserHandle.myUserId() != 0)
                        || (Settings.System.getInt(
                                        this.mContext.getContentResolver(), "easy_mode_switch", 1)
                                == 0))
                ? Action.createResult("false")
                : Action.createResult("true");
    }
}
