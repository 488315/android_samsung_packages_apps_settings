package com.samsung.android.settings.accessibility.bixby.action.talkback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TalkbackHelpAction extends BixbyActionTarget {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        Intent putExtra =
                new Intent("com.samsung.android.intent.action.TALKBACK_TUTORIAL")
                        .putExtra("fromA11y", true);
        if (putExtra.resolveActivity(context.getPackageManager()) != null) {
            return putExtra;
        }
        Log.w(
                "TalkbackHelpAction",
                "getTalkbackTutorialIntent - tutorial activity does not exist. try old tutorial"
                    + " activity");
        Intent className =
                new Intent()
                        .setClassName(
                                "com.samsung.android.accessibility.talkback",
                                "com.samsung.android.accessibility.talkback.tutorial.AccessibilityTutorialActivity");
        if (className.resolveActivity(context.getPackageManager()) != null) {
            return className;
        }
        Log.w("TalkbackHelpAction", "getTalkbackTutorialIntent - No tutorial activity exists");
        return null;
    }
}
