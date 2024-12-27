package com.android.settings.development.autofill;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutofillMaxPartitionsPreference extends AbstractGlobalSettingsPreference {
    public AutofillMaxPartitionsPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, "autofill_max_partitions_size", 10);
    }
}
