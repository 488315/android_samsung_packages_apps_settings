package com.samsung.android.settings.accessibility.bixby.action;

import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HowToUseAccessibilityAction extends BixbyActionTarget {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.bixby.assistanthome.CAPSULE_DETAIL");
        intent.putExtra("intent_extra_capsule_id", "viv.accessibilityApp");
        return intent;
    }
}
