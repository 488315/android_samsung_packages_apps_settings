package com.android.settings.security.trustagent;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TrustAgentInfo implements Comparable {
    public final ComponentName mComponentName;
    public final Drawable mIcon;
    public final CharSequence mLabel;

    public TrustAgentInfo(
            CharSequence charSequence, ComponentName componentName, Drawable drawable) {
        this.mLabel = charSequence;
        this.mComponentName = componentName;
        this.mIcon = drawable;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.mComponentName.compareTo(((TrustAgentInfo) obj).mComponentName);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TrustAgentInfo) {
            return this.mComponentName.equals(((TrustAgentInfo) obj).mComponentName);
        }
        return false;
    }
}
