package com.android.settings.biometrics.activeunlock;

import android.content.Context;

import com.google.android.setupdesign.util.DeviceHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActiveUnlockDeviceNameListener {
    public final ActiveUnlockContentListener mActiveUnlockContentListener;

    public ActiveUnlockDeviceNameListener(
            Context context,
            ActiveUnlockContentListener.OnContentChangedListener onContentChangedListener) {
        this.mActiveUnlockContentListener =
                new ActiveUnlockContentListener(
                        context,
                        onContentChangedListener,
                        "ActiveUnlockDeviceNameListener",
                        DeviceHelper.GET_DEVICE_NAME_METHOD,
                        "com.android.settings.active_unlock.device_name");
    }
}
