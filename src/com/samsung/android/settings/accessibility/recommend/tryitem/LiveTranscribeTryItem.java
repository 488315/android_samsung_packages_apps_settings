package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LiveTranscribeTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem,
              // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Intent getLaunchIntent() {
        ComponentName componentName =
                AccessibilityConstant.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT;
        if (Utils.hasPackage(this.mContext, componentName.getPackageName())) {
            return new Intent("android.intent.action.MAIN").setComponent(componentName);
        }
        return new Intent("android.intent.action.VIEW")
                .setData(
                        Uri.parse(
                                "https://play.google.com/store/apps/details?id="
                                        + componentName.getPackageName()))
                .setPackage("com.android.vending");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of("all_sound_off_key", "amplify_ambient_sound");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        AccessibilityShortcutInfo accessibilityShortcutInfo =
                SecAccessibilityUtils.getAccessibilityShortcutInfo(
                        this.mContext,
                        AccessibilityConstant.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT);
        return accessibilityShortcutInfo != null
                ? accessibilityShortcutInfo
                        .getActivityInfo()
                        .loadLabel(this.mContext.getPackageManager())
                : this.mContext.getText(R.string.live_transcribe_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return !Rune.isChinaModel()
                && SecAccessibilityUtils.getValuesInSettings(
                                this.mContext,
                                AccessibilityConstant.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT
                                        .flattenToString())
                        == 0;
    }
}
