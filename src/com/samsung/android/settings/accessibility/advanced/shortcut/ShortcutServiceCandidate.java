package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.android.settings.accessibility.AccessibilityUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
class ShortcutServiceCandidate extends ShortcutCandidate {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class Builder extends ShortcutCandidate.Builder {
        public AccessibilityServiceInfo info;

        @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutCandidate.Builder
        public final ShortcutSimpleEntry buildEntry(Context context) {
            String str = this.componentName;
            if (str == null) {
                return null;
            }
            return new ShortcutSimpleEntry(
                    this.priority,
                    str,
                    (String) this.info.getResolveInfo().loadLabel(context.getPackageManager()));
        }

        public Drawable getIcon(Context context) {
            return this.info.getResolveInfo().loadIcon(context.getPackageManager());
        }

        @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutCandidate.Builder
        public final ShortcutServiceCandidate build(Context context, int i) {
            ShortcutSimpleEntry buildEntry;
            if ((i == 1 && AccessibilityUtil.getAccessibilityServiceFragmentType(this.info) == 0)
                    || (buildEntry = buildEntry(context)) == null) {
                return null;
            }
            return new ShortcutServiceCandidate(
                    buildEntry, getIcon(context), buildEntry.getKey(), this.category);
        }
    }
}
