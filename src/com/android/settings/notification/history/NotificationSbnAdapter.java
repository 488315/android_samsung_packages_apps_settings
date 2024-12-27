package com.android.settings.notification.history;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.view.ViewGroup;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationSbnAdapter extends RecyclerView.Adapter {
    public final int mBackgroundColor;
    public final Context mContext;
    public final int mCurrentUser;
    public final boolean mInNightMode;
    public final boolean mIsSnoozed;
    public final PackageManager mPm;
    public final UiEventLogger mUiEventLogger;
    public final List mEnabledProfiles = new ArrayList();
    public final Map mUserBadgeCache = new HashMap();
    public List mValues = new ArrayList();

    public NotificationSbnAdapter(
            Context context,
            PackageManager packageManager,
            UserManager userManager,
            boolean z,
            UiEventLogger uiEventLogger) {
        this.mContext = context;
        this.mPm = packageManager;
        this.mBackgroundColor = Utils.getColorAttrDefaultColor(context, R.attr.colorBackground);
        this.mInNightMode = (context.getResources().getConfiguration().uiMode & 48) == 32;
        int currentUser = ActivityManager.getCurrentUser();
        this.mCurrentUser = currentUser;
        for (int i : userManager.getEnabledProfileIds(currentUser)) {
            if (!userManager.isQuietModeEnabled(UserHandle.of(i))) {
                ((ArrayList) this.mEnabledProfiles).add(Integer.valueOf(i));
            }
        }
        setHasStableIds(true);
        this.mIsSnoozed = z;
        this.mUiEventLogger = uiEventLogger;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mValues.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00aa A[Catch: NameNotFoundException -> 0x00b1, TRY_LEAVE, TryCatch #2 {NameNotFoundException -> 0x00b1, blocks: (B:21:0x00a0, B:23:0x00aa), top: B:20:0x00a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00cf  */
    /* JADX WARN: Type inference failed for: r7v17, types: [java.lang.CharSequence] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBindViewHolder(
            androidx.recyclerview.widget.RecyclerView.ViewHolder r19, final int r20) {
        /*
            Method dump skipped, instructions count: 586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.history.NotificationSbnAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder,"
                    + " int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NotificationSbnViewHolder(
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        com.android.settings.R.layout.notification_sbn_log_row,
                        viewGroup,
                        false));
    }

    public final void onRebuildComplete(List list) {
        ArrayList arrayList = (ArrayList) list;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (!shouldShowSbn((StatusBarNotification) arrayList.get(size))) {
                arrayList.remove(size);
            }
        }
        this.mValues = list;
        notifyDataSetChanged();
    }

    public final boolean shouldShowSbn(StatusBarNotification statusBarNotification) {
        if (statusBarNotification.isGroup()
                && statusBarNotification.getNotification().isGroupSummary()) {
            return false;
        }
        List list = this.mEnabledProfiles;
        int userId = statusBarNotification.getUserId();
        if (userId == -1) {
            userId = this.mCurrentUser;
        }
        return ((ArrayList) list).contains(Integer.valueOf(userId));
    }
}
