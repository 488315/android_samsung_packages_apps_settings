package com.samsung.android.settings.eternal.provider.items;

import android.content.Context;

import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.util.SemLog;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class RecoverableItemFactory {
    public static final HashMap mRecoverableItemMap = new HashMap();

    public static Recoverable getItem(Context context, String str) {
        HashMap hashMap = mRecoverableItemMap;
        Recoverable recoverable = (Recoverable) hashMap.get(str);
        if (recoverable == null) {
            str.getClass();
            switch (str) {
                case "Accounts":
                    recoverable = new AccountsItem();
                    break;
                case "LockScreen":
                    LockScreenItem lockScreenItem = new LockScreenItem();
                    lockScreenItem.mLockPatternUtils = new LockPatternUtils(context);
                    recoverable = lockScreenItem;
                    break;
                case "Display":
                    recoverable = new DisplayItem();
                    break;
                case "ManageStorage":
                    recoverable = new ManageStorageItem();
                    break;
                case "Connections":
                    recoverable = new ConnectionsItem();
                    break;
                case "Advanced":
                    recoverable = new AdvancedFeatureItem();
                    break;
                case "Sound":
                    recoverable = new SoundItem();
                    break;
                case "Biometrics":
                    recoverable = new BiometricsItem();
                    break;
                case "Accessibility":
                    recoverable = new AccessibilityItem(context);
                    break;
                case "Security":
                    recoverable = new SecurityItem();
                    break;
                case "General":
                    recoverable = new GeneralItem();
                    break;
                case "Notifications":
                    recoverable = new NotificationsItem();
                    break;
                default:
                    SemLog.d("RecoverableItemFactory", "unknown submodule : ".concat(str));
                    recoverable = recoverable;
                    break;
            }
            if (recoverable != null) {
                hashMap.put(str, recoverable);
            }
        }
        return recoverable;
    }
}
