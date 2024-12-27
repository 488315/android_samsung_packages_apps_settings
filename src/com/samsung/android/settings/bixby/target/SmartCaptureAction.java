package com.samsung.android.settings.bixby.target;

import android.content.Intent;
import android.os.Bundle;

import com.android.settings.Utils;

import com.samsung.android.settings.bixby.target.actions.Action;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SmartCaptureAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent =
                new Intent("com.samsung.android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY");
        intent.addFlags(268468224);
        Intent intent2 = new Intent();
        intent2.setClassName(
                "com.samsung.android.app.smartcapture",
                "com.samsung.android.app.settings.PreferenceSettingsActivity");
        intent2.setAction("key_screenshot_toolbar");
        intent.putExtra(
                "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI", intent2.toUri(1));
        intent.putExtra(
                "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY",
                "top_level_advanced_features");
        Utils.setTaskIdToIntent(intent, getTaskId());
        launchSettings(intent, null);
        return Action.createResult("success");
    }
}
