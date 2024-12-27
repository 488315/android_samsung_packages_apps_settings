package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.SecNotificationBlockManager;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class NotificationPreferenceController extends AbstractPreferenceController {
    public static final NotificationPreferenceController$$ExternalSyntheticLambda0
            CHANNEL_COMPARATOR;
    public static final NotificationPreferenceController$$ExternalSyntheticLambda0
            CHANNEL_GROUP_COMPARATOR;
    public static final int SYSTEM_WIDE_OFF = 0;
    public static final int SYSTEM_WIDE_ON = 1;
    public RestrictedLockUtils.EnforcedAdmin mAdmin;
    public NotificationBackend.AppRow mAppRow;
    public final NotificationBackend mBackend;
    public NotificationChannel mChannel;
    public NotificationChannelGroup mChannelGroup;
    public final Context mContext;
    public Drawable mConversationDrawable;
    public ShortcutInfo mConversationInfo;
    public final PackageManager mPm;
    public List mPreferenceFilter;
    public final UserManager mUm;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.notification.app.NotificationPreferenceController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.notification.app.NotificationPreferenceController$$ExternalSyntheticLambda0] */
    static {
        final int i = 0;
        CHANNEL_GROUP_COMPARATOR =
                new Comparator() { // from class:
                                   // com.android.settings.notification.app.NotificationPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i) {
                            case 0:
                                NotificationChannelGroup notificationChannelGroup =
                                        (NotificationChannelGroup) obj;
                                NotificationChannelGroup notificationChannelGroup2 =
                                        (NotificationChannelGroup) obj2;
                                if (notificationChannelGroup.getId() == null
                                        && notificationChannelGroup2.getId() != null) {
                                    return 1;
                                }
                                if (notificationChannelGroup2.getId() != null
                                        || notificationChannelGroup.getId() == null) {
                                    return notificationChannelGroup
                                            .getId()
                                            .compareTo(notificationChannelGroup2.getId());
                                }
                                return -1;
                            default:
                                NotificationChannel notificationChannel = (NotificationChannel) obj;
                                NotificationChannel notificationChannel2 =
                                        (NotificationChannel) obj2;
                                if (notificationChannel.isDeleted()
                                        != notificationChannel2.isDeleted()) {
                                    return Boolean.compare(
                                            notificationChannel.isDeleted(),
                                            notificationChannel2.isDeleted());
                                }
                                if (notificationChannel.getId().equals("miscellaneous")) {
                                    return 1;
                                }
                                if (notificationChannel2.getId().equals("miscellaneous")) {
                                    return -1;
                                }
                                return notificationChannel
                                        .getId()
                                        .compareTo(notificationChannel2.getId());
                        }
                    }
                };
        final int i2 = 1;
        CHANNEL_COMPARATOR =
                new Comparator() { // from class:
                                   // com.android.settings.notification.app.NotificationPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i2) {
                            case 0:
                                NotificationChannelGroup notificationChannelGroup =
                                        (NotificationChannelGroup) obj;
                                NotificationChannelGroup notificationChannelGroup2 =
                                        (NotificationChannelGroup) obj2;
                                if (notificationChannelGroup.getId() == null
                                        && notificationChannelGroup2.getId() != null) {
                                    return 1;
                                }
                                if (notificationChannelGroup2.getId() != null
                                        || notificationChannelGroup.getId() == null) {
                                    return notificationChannelGroup
                                            .getId()
                                            .compareTo(notificationChannelGroup2.getId());
                                }
                                return -1;
                            default:
                                NotificationChannel notificationChannel = (NotificationChannel) obj;
                                NotificationChannel notificationChannel2 =
                                        (NotificationChannel) obj2;
                                if (notificationChannel.isDeleted()
                                        != notificationChannel2.isDeleted()) {
                                    return Boolean.compare(
                                            notificationChannel.isDeleted(),
                                            notificationChannel2.isDeleted());
                                }
                                if (notificationChannel.getId().equals("miscellaneous")) {
                                    return 1;
                                }
                                if (notificationChannel2.getId().equals("miscellaneous")) {
                                    return -1;
                                }
                                return notificationChannel
                                        .getId()
                                        .compareTo(notificationChannel2.getId());
                        }
                    }
                };
    }

    public NotificationPreferenceController(
            Context context, NotificationBackend notificationBackend) {
        super(context);
        this.mContext = context;
        this.mBackend = notificationBackend;
        this.mUm = (UserManager) context.getSystemService("user");
        this.mPm = context.getPackageManager();
    }

    public final boolean checkCanBeVisible() {
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel == null) {
            Log.w("ChannelPrefContr", "No channel");
            return false;
        }
        int importance = notificationChannel.getImportance();
        return importance == -1000 || importance >= 3;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow == null || appRow.banned) {
            return false;
        }
        NotificationChannelGroup notificationChannelGroup = this.mChannelGroup;
        if (notificationChannelGroup != null && notificationChannelGroup.isBlocked()) {
            return false;
        }
        if (this.mChannel != null) {
            return (this.mPreferenceFilter == null || isIncludedInFilter())
                    && this.mChannel.getImportance() != 0;
        }
        NotificationBackend.AppRow appRow2 = this.mAppRow;
        return !AppUtils.isDeepSleepingEnable(appRow2.uid, appRow2.pkg);
    }

    public final boolean isChannelConfigurable(NotificationChannel notificationChannel) {
        if (notificationChannel == null || this.mAppRow == null) {
            return false;
        }
        notificationChannel.getId();
        this.mAppRow.getClass();
        Log.secD(
                "ChannelPrefContr",
                "channel configurable : " + notificationChannel.getId() + " ![null]");
        this.mAppRow.getClass();
        return true;
    }

    public final boolean isChannelGroupBlockable(
            NotificationChannelGroup notificationChannelGroup) {
        if (notificationChannelGroup == null || this.mAppRow == null) {
            return false;
        }
        Iterator<NotificationChannel> it = notificationChannelGroup.getChannels().iterator();
        while (it.hasNext()) {
            if (!SecNotificationBlockManager.isBlockableNotificationChannel(
                    this.mContext, this.mAppRow.pkg, it.next())) {
                return false;
            }
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow.systemApp || appRow.lockedImportance) {
            return notificationChannelGroup.isBlocked();
        }
        return true;
    }

    public final boolean isChannelPreferenceEnabled(NotificationChannel notificationChannel) {
        return this.mAdmin == null
                && (notificationChannel.isBlockable()
                        || !(notificationChannel.isImportanceLockedByOEM()
                                || notificationChannel
                                        .isImportanceLockedByCriticalDeviceFunction()));
    }

    public final boolean isDefaultChannel() {
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel == null) {
            return false;
        }
        return "miscellaneous".equals(notificationChannel.getId());
    }

    public final boolean isFloatingIconBubble() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "notification_bubbles", 1)
                == 1;
    }

    public boolean isIncludedInFilter() {
        return this instanceof ConversationHeaderPreferenceController;
    }

    public void onResume(
            NotificationBackend.AppRow appRow,
            NotificationChannel notificationChannel,
            NotificationChannelGroup notificationChannelGroup,
            Drawable drawable,
            ShortcutInfo shortcutInfo,
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin,
            List list) {
        this.mAppRow = appRow;
        this.mChannel = notificationChannel;
        this.mChannelGroup = notificationChannelGroup;
        this.mAdmin = enforcedAdmin;
        this.mConversationDrawable = drawable;
        this.mConversationInfo = shortcutInfo;
        this.mPreferenceFilter = list;
    }

    public final void saveChannel() {
        NotificationBackend.AppRow appRow;
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel == null || (appRow = this.mAppRow) == null) {
            return;
        }
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        NotificationBackend.updateChannel(str, i, notificationChannel);
    }
}
