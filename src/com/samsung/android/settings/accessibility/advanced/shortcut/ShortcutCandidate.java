package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ShortcutCandidate extends ShortcutSimpleEntry {
    private final String category;
    private final String exclusiveFeature;
    private final Drawable icon;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Builder {
        public String category;
        public final String componentName;
        public int priority = 0;

        public Builder(String str) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
            if (unflattenFromString != null) {
                this.componentName = unflattenFromString.flattenToString();
            } else {
                this.componentName = str;
            }
        }

        public abstract ShortcutCandidate build(Context context, int i);

        public abstract ShortcutSimpleEntry buildEntry(Context context);
    }

    public ShortcutCandidate(
            ShortcutSimpleEntry shortcutSimpleEntry, Drawable drawable, String str, String str2) {
        super(shortcutSimpleEntry);
        this.icon = drawable;
        this.exclusiveFeature = str;
        this.category = str2;
    }

    public final String getCategory() {
        return this.category;
    }

    public final String getExclusiveFeature() {
        return this.exclusiveFeature;
    }

    public final Drawable getIcon() {
        return this.icon;
    }
}
