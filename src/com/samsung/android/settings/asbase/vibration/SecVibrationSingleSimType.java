package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.Intent;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.asbase.utils.SimUtils;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecVibrationSingleSimType implements IVibSimType {
    public Context mContext;
    public int mType;

    @Override // com.samsung.android.settings.asbase.vibration.IVibSimType
    public final Intent getIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.putExtra("vibration_type", this.mType);
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.samsung.android.settings.asbase.vibration.VibPickerContainer");
        return intent;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibSimType
    public final ArrayList getSepIndexDbName() {
        ArrayList arrayList = new ArrayList();
        if (this.mType != 0) {
            arrayList.add("notification_vibration_sep_index");
        } else if (SimUtils.isEnabledSIM2Only()) {
            arrayList.add("ringtone_vibration_sep_index_2");
        } else {
            arrayList.add("ringtone_vibration_sep_index");
        }
        return arrayList;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibSimType
    public final ArrayList getSoundType() {
        ArrayList arrayList = new ArrayList();
        if (this.mType != 0) {
            arrayList.add(2);
        } else if (SimUtils.isEnabledSIM2Only()) {
            arrayList.add(128);
        } else {
            arrayList.add(1);
        }
        return arrayList;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibSimType
    public final ArrayList getSyncDbName() {
        ArrayList arrayList = new ArrayList();
        if (this.mType != 0) {
            arrayList.add("sync_vibration_with_notification");
        } else if (SimUtils.isEnabledSIM2Only()) {
            arrayList.add("sync_vibration_with_ringtone_2");
        } else {
            arrayList.add("sync_vibration_with_ringtone");
        }
        return arrayList;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibSimType
    public final int getVibrationSimCount() {
        return 1;
    }
}
