package com.android.settingslib.spa.framework.common;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsPageWithEntry {
    public final List entries;
    public final SettingsEntry injectEntry;
    public final SettingsPage page;

    public SettingsPageWithEntry(SettingsPage page, List entries, SettingsEntry settingsEntry) {
        Intrinsics.checkNotNullParameter(page, "page");
        Intrinsics.checkNotNullParameter(entries, "entries");
        this.page = page;
        this.entries = entries;
        this.injectEntry = settingsEntry;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SettingsPageWithEntry)) {
            return false;
        }
        SettingsPageWithEntry settingsPageWithEntry = (SettingsPageWithEntry) obj;
        return Intrinsics.areEqual(this.page, settingsPageWithEntry.page)
                && Intrinsics.areEqual(this.entries, settingsPageWithEntry.entries)
                && Intrinsics.areEqual(this.injectEntry, settingsPageWithEntry.injectEntry);
    }

    public final int hashCode() {
        return this.injectEntry.hashCode()
                + ((this.entries.hashCode() + (this.page.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "SettingsPageWithEntry(page="
                + this.page
                + ", entries="
                + this.entries
                + ", injectEntry="
                + this.injectEntry
                + ")";
    }
}
