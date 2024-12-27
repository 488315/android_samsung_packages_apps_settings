package com.android.settingslib.applications;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.IconDrawableFactory;

import java.time.Clock;
import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RecentAppOpsAccess {
    public static final String ANDROID_SYSTEM_PACKAGE_NAME = "android";
    static final int[] LOCATION_OPS = {1, 0};
    public final Clock mClock;
    public final Context mContext;
    public final IconDrawableFactory mDrawableFactory;
    public final int[] mOps;
    public final PackageManager mPackageManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.applications.RecentAppOpsAccess$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Long.compare(((Access) obj).accessFinishTime, ((Access) obj2).accessFinishTime);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Access {
        public final long accessFinishTime;
        public final Drawable icon;
        public final CharSequence label;
        public final String packageName;
        public final UserHandle userHandle;

        public Access(
                String str,
                UserHandle userHandle,
                Drawable drawable,
                CharSequence charSequence,
                long j) {
            this.packageName = str;
            this.userHandle = userHandle;
            this.icon = drawable;
            this.label = charSequence;
            this.accessFinishTime = j;
        }
    }

    public RecentAppOpsAccess(Context context, int[] iArr) {
        this(context, Clock.systemDefaultZone(), iArr);
    }

    public static RecentAppOpsAccess createForLocation(Context context) {
        return new RecentAppOpsAccess(context, LOCATION_OPS);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0070, code lost:

       if (r8.getUserProperties(r15).getShowInQuietMode() == 1) goto L21;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.android.settingslib.applications.RecentAppOpsAccess.Access>
            getAppList(boolean r29) {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.applications.RecentAppOpsAccess.getAppList(boolean):java.util.List");
    }

    public RecentAppOpsAccess(Context context, Clock clock, int[] iArr) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mOps = iArr;
        this.mDrawableFactory = IconDrawableFactory.newInstance(context);
        this.mClock = clock;
    }
}
