package com.samsung.android.settings.notification.brief.policy;

import android.net.Uri;
import android.provider.BaseColumns;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PolicyClientContract {
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.samsung.android.sm.policy");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class PolicyItems implements BaseColumns {
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(PolicyClientContract.AUTHORITY_URI, "policy_item");
    }
}
