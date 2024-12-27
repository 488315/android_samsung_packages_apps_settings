package com.samsung.android.settings.accessibility.bixby;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyActionData;
import com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget;
import com.samsung.android.settings.accessibility.bixby.action.visibility.BaseColorAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.ColorAdjustmentAction;
import com.samsung.android.settings.accessibility.bixby.action.visibility.ColorCorrectionAction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BixbyActionFactory {
    public static BixbyActionTarget findTarget(Context context, Intent intent) {
        Uri data = intent.getData();
        if (data == null || data.getPath() == null) {
            return null;
        }
        String replace = data.getPath().replace("/", ApnSettings.MVNO_NONE);
        Log.d("BixbyActionFactory", "getPath : " + replace);
        BixbyActionTarget targetMenu = BixbyActionData.getTargetMenu(replace);
        if (!(targetMenu instanceof BaseColorAction)) {
            return targetMenu;
        }
        ((BaseColorAction) targetMenu).getClass();
        return SecAccessibilityUtils.isSupportColorAdjustment(context)
                ? new ColorAdjustmentAction()
                : new ColorCorrectionAction();
    }
}
