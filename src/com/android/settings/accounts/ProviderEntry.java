package com.android.settings.accounts;

import com.android.internal.util.CharSequences;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProviderEntry implements Comparable {
    public final CharSequence name;
    public final String type;

    public ProviderEntry(String str, CharSequence charSequence) {
        this.name = charSequence;
        this.type = str;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        ProviderEntry providerEntry = (ProviderEntry) obj;
        if ("com.osp.app.signin".equals(this.type)) {
            return -1;
        }
        if (!"com.osp.app.signin".equals(providerEntry.type)) {
            CharSequence charSequence = this.name;
            if (charSequence == null) {
                return -1;
            }
            CharSequence charSequence2 = providerEntry.name;
            if (charSequence2 != null) {
                return CharSequences.compareToIgnoreCase(charSequence, charSequence2);
            }
        }
        return 1;
    }
}
