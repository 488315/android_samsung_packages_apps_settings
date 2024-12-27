package com.android.settings.spa.notification;

import android.app.INotificationManager;
import android.app.usage.IUsageStatsManager;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.IUserManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.spa.framework.util.MessageFormatsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.model.app.IPackageManagers;
import com.android.settingslib.spaprivileged.model.app.PackageManagers;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.math.MathKt;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppNotificationRepository implements IAppNotificationRepository {
    public static final Companion Companion = null;
    public final Context context;
    public final INotificationManager notificationManager;
    public final IPackageManagers packageManagers;
    public final IUsageStatsManager usageStatsManager;
    public final IUserManager userManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    public AppNotificationRepository(Context context) {
        PackageManagers packageManagers = PackageManagers.INSTANCE;
        IUsageStatsManager asInterface =
                IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));
        Intrinsics.checkNotNullExpressionValue(asInterface, "asInterface(...)");
        INotificationManager asInterface2 =
                INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        Intrinsics.checkNotNullExpressionValue(asInterface2, "asInterface(...)");
        IUserManager asInterface3 =
                IUserManager.Stub.asInterface(ServiceManager.getService("user"));
        Intrinsics.checkNotNullExpressionValue(asInterface3, "asInterface(...)");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageManagers, "packageManagers");
        this.context = context;
        this.packageManagers = packageManagers;
        this.usageStatsManager = asInterface;
        this.notificationManager = asInterface2;
        this.userManager = asInterface3;
    }

    public final String calculateFrequencySummary(int i) {
        int roundToInt = MathKt.roundToInt(i / 7);
        return roundToInt > 0
                ? MessageFormatsKt.formatString(
                        this.context,
                        R.string.notifications_sent_daily,
                        new Pair("count", Integer.valueOf(roundToInt)))
                : MessageFormatsKt.formatString(
                        this.context,
                        R.string.notifications_sent_weekly,
                        new Pair("count", Integer.valueOf(i)));
    }

    public final int getSentCount(ApplicationInfo applicationInfo) {
        UsageEvents usageEvents;
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        long currentTimeMillis = System.currentTimeMillis();
        try {
            usageEvents =
                    this.usageStatsManager.queryEventsForPackageForUser(
                            currentTimeMillis - TimeUnit.DAYS.toMillis(7L),
                            currentTimeMillis,
                            ApplicationInfosKt.getUserId(applicationInfo),
                            applicationInfo.packageName,
                            this.context.getPackageName());
        } catch (RemoteException e) {
            Log.e(
                    "AppNotificationsRepo",
                    "Failed IUsageStatsManager.queryEventsForPackageForUser(): ",
                    e);
            usageEvents = null;
        }
        Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settings.spa.notification.AppNotificationRepository$getSentCount$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        UsageEvents.Event it = (UsageEvents.Event) obj;
                        Intrinsics.checkNotNullParameter(it, "it");
                        Ref$IntRef.this.element++;
                        return Unit.INSTANCE;
                    }
                };
        if (usageEvents != null) {
            UsageEvents.Event event = new UsageEvents.Event();
            while (usageEvents.getNextEvent(event)) {
                if (event.getEventType() == 12) {
                    function1.invoke(event);
                }
            }
        }
        return ref$IntRef.element;
    }
}
