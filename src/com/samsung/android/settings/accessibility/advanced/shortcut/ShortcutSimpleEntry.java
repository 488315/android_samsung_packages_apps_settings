package com.samsung.android.settings.accessibility.advanced.shortcut;

import java.util.AbstractMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShortcutSimpleEntry extends AbstractMap.SimpleEntry<String, String>
        implements Comparable<ShortcutSimpleEntry> {
    private final int priority;

    public ShortcutSimpleEntry(int i, String str, String str2) {
        super(str, str2);
        this.priority = i;
    }

    @Override // java.lang.Comparable
    public final int compareTo(ShortcutSimpleEntry shortcutSimpleEntry) {
        return this.priority - shortcutSimpleEntry.priority;
    }

    public ShortcutSimpleEntry(ShortcutSimpleEntry shortcutSimpleEntry) {
        super(shortcutSimpleEntry.getKey(), shortcutSimpleEntry.getValue());
        this.priority = shortcutSimpleEntry.priority;
    }
}
