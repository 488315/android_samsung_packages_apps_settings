package com.android.settings.notification.history;

import android.app.INotificationManager;
import android.app.NotificationHistory;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSwitchBar;

import com.android.internal.logging.UiEventLogger;
import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class HistoryLoader$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ NotificationHistoryActivity$$ExternalSyntheticLambda0 f$1;

    public /* synthetic */ HistoryLoader$$ExternalSyntheticLambda0(
            HistoryLoader historyLoader,
            NotificationHistoryActivity$$ExternalSyntheticLambda0
                    notificationHistoryActivity$$ExternalSyntheticLambda0) {
        this.f$0 = historyLoader;
        this.f$1 = notificationHistoryActivity$$ExternalSyntheticLambda0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotificationHistory notificationHistory;
        int i;
        switch (this.$r8$classId) {
            case 0:
                HistoryLoader historyLoader = (HistoryLoader) this.f$0;
                NotificationHistoryActivity$$ExternalSyntheticLambda0
                        notificationHistoryActivity$$ExternalSyntheticLambda0 = this.f$1;
                historyLoader.getClass();
                try {
                    HashMap hashMap = new HashMap();
                    NotificationBackend notificationBackend = historyLoader.mBackend;
                    String packageName = historyLoader.mContext.getPackageName();
                    String attributionTag = historyLoader.mContext.getAttributionTag();
                    notificationBackend.getClass();
                    try {
                        notificationHistory =
                                NotificationBackend.sINM.getNotificationHistory(
                                        packageName, attributionTag);
                    } catch (Exception e) {
                        Log.w("NotificationBackend", "Error calling NoMan", e);
                        notificationHistory = new NotificationHistory();
                    }
                    while (notificationHistory.hasNextNotification()) {
                        NotificationHistory.HistoricalNotification nextNotification =
                                notificationHistory.getNextNotification();
                        String str =
                                nextNotification.getPackage() + "|" + nextNotification.getUid();
                        String str2 = nextNotification.getPackage();
                        int uid = nextNotification.getUid();
                        NotificationHistoryPackage notificationHistoryPackage =
                                new NotificationHistoryPackage();
                        notificationHistoryPackage.pkgName = str2;
                        notificationHistoryPackage.uid = uid;
                        notificationHistoryPackage.notifications =
                                new TreeSet(
                                        new NotificationHistoryPackage$$ExternalSyntheticLambda0());
                        NotificationHistoryPackage notificationHistoryPackage2 =
                                (NotificationHistoryPackage)
                                        hashMap.getOrDefault(str, notificationHistoryPackage);
                        notificationHistoryPackage2.notifications.add(nextNotification);
                        hashMap.put(str, notificationHistoryPackage2);
                    }
                    ArrayList arrayList = new ArrayList(hashMap.values());
                    Collections.sort(arrayList, new HistoryLoader$$ExternalSyntheticLambda1());
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        NotificationHistoryPackage notificationHistoryPackage3 =
                                (NotificationHistoryPackage) it.next();
                        try {
                            PackageManager packageManager = historyLoader.mPm;
                            String str3 = notificationHistoryPackage3.pkgName;
                            int i2 = notificationHistoryPackage3.uid;
                            ApplicationInfo applicationInfoAsUser =
                                    packageManager.getApplicationInfoAsUser(
                                            str3, 795136, UserHandle.getUserId(i2));
                            if (applicationInfoAsUser != null) {
                                notificationHistoryPackage3.label =
                                        String.valueOf(
                                                historyLoader.mPm.getApplicationLabel(
                                                        applicationInfoAsUser));
                                PackageManager packageManager2 = historyLoader.mPm;
                                notificationHistoryPackage3.icon =
                                        packageManager2.getUserBadgedIcon(
                                                packageManager2.getApplicationIcon(
                                                        applicationInfoAsUser),
                                                UserHandle.of(UserHandle.getUserId(i2)));
                            }
                        } catch (PackageManager.NameNotFoundException unused) {
                            notificationHistoryPackage3.icon =
                                    historyLoader.mPm.getDefaultActivityIcon();
                        }
                    }
                    ThreadUtils.postOnMainThread(
                            new HistoryLoader$$ExternalSyntheticLambda0(
                                    notificationHistoryActivity$$ExternalSyntheticLambda0,
                                    arrayList));
                    break;
                } catch (Exception e2) {
                    Slog.e("HistoryLoader", "Error loading history", e2);
                    return;
                }
            default:
                NotificationHistoryActivity$$ExternalSyntheticLambda0
                        notificationHistoryActivity$$ExternalSyntheticLambda02 = this.f$1;
                List list = (List) this.f$0;
                notificationHistoryActivity$$ExternalSyntheticLambda02.getClass();
                StatusLogger$StatusLoggingProvider statusLogger$StatusLoggingProvider =
                        NotificationHistoryActivity.STATUS_LOGGING_PROVIDER;
                final NotificationHistoryActivity notificationHistoryActivity =
                        notificationHistoryActivity$$ExternalSyntheticLambda02.f$0;
                int i3 = 8;
                notificationHistoryActivity
                        .findViewById(R.id.today_list)
                        .setVisibility(list.isEmpty() ? 8 : 0);
                notificationHistoryActivity.mCountdownLatch.countDown();
                notificationHistoryActivity.mTodayView.findViewById(R.id.apps);
                notificationHistoryActivity.mTodayAppCount = list.size();
                Log.d(
                        "NotifHistory",
                        "mTodayAppCount : " + notificationHistoryActivity.mTodayAppCount);
                int size = list.size();
                int i4 = 0;
                while (i4 < size) {
                    final NotificationHistoryPackage notificationHistoryPackage4 =
                            (NotificationHistoryPackage) list.get(i4);
                    View inflate =
                            LayoutInflater.from(notificationHistoryActivity)
                                    .inflate(
                                            R.layout.notification_history_app_layout,
                                            (ViewGroup) null);
                    final View findViewById = inflate.findViewById(R.id.notification_list);
                    findViewById.setVisibility(i3);
                    final View findViewById2 = inflate.findViewById(R.id.app_header);
                    final ImageView imageView = (ImageView) inflate.findViewById(R.id.expand);
                    findViewById2.setStateDescription(
                            findViewById.getVisibility() == 0
                                    ? notificationHistoryActivity.getString(
                                            R.string.condition_expand_hide)
                                    : notificationHistoryActivity.getString(
                                            R.string.condition_expand_show));
                    final int i5 = i4;
                    imageView.setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.notification.history.NotificationHistoryActivity$$ExternalSyntheticLambda6
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    NotificationHistoryActivity notificationHistoryActivity2 =
                                            NotificationHistoryActivity.this;
                                    View view2 = findViewById;
                                    ImageView imageView2 = imageView;
                                    View view3 = findViewById2;
                                    NotificationHistoryPackage notificationHistoryPackage5 =
                                            notificationHistoryPackage4;
                                    int i6 = i5;
                                    StatusLogger$StatusLoggingProvider
                                            statusLogger$StatusLoggingProvider2 =
                                                    NotificationHistoryActivity
                                                            .STATUS_LOGGING_PROVIDER;
                                    notificationHistoryActivity2.getClass();
                                    view2.setVisibility(view2.getVisibility() == 0 ? 8 : 0);
                                    imageView2.setImageResource(
                                            view2.getVisibility() == 0
                                                    ? R.drawable.icon_noti_arrow_up
                                                    : R.drawable.icon_noti_arrow_down);
                                    view3.setStateDescription(
                                            view2.getVisibility() == 0
                                                    ? notificationHistoryActivity2.getString(
                                                            R.string.condition_expand_hide)
                                                    : notificationHistoryActivity2.getString(
                                                            R.string.condition_expand_show));
                                    view3.sendAccessibilityEvent(
                                            NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                    notificationHistoryActivity2.mUiEventLogger.logWithPosition(
                                            view2.getVisibility() == 0
                                                    ? NotificationHistoryActivity
                                                            .NotificationHistoryEvent
                                                            .NOTIFICATION_HISTORY_PACKAGE_HISTORY_OPEN
                                                    : NotificationHistoryActivity
                                                            .NotificationHistoryEvent
                                                            .NOTIFICATION_HISTORY_PACKAGE_HISTORY_CLOSE,
                                            notificationHistoryPackage5.uid,
                                            notificationHistoryPackage5.pkgName,
                                            i6);
                                }
                            });
                    View findViewById3 = inflate.findViewById(R.id.history_app_divider);
                    if (i4 == size - 1) {
                        i = 8;
                        findViewById3.setVisibility(8);
                    } else {
                        i = 8;
                    }
                    TextView textView = (TextView) inflate.findViewById(R.id.label);
                    CharSequence charSequence = notificationHistoryPackage4.label;
                    if (charSequence == null) {
                        charSequence = notificationHistoryPackage4.pkgName;
                    }
                    textView.setText(charSequence);
                    textView.setContentDescription(
                            notificationHistoryActivity.mUm.getBadgedLabelForUser(
                                    textView.getText(),
                                    UserHandle.getUserHandleForUid(
                                            notificationHistoryPackage4.uid)));
                    ((ImageView) inflate.findViewById(R.id.icon))
                            .setImageDrawable(notificationHistoryPackage4.icon);
                    TextView textView2 = (TextView) inflate.findViewById(R.id.count);
                    textView2.setText(
                            notificationHistoryActivity
                                    .getResources()
                                    .getQuantityString(
                                            R.plurals.notification_history_count,
                                            notificationHistoryPackage4.notifications.size(),
                                            Integer.valueOf(
                                                    notificationHistoryPackage4.notifications
                                                            .size())));
                    NotificationHistoryRecyclerView notificationHistoryRecyclerView =
                            (NotificationHistoryRecyclerView)
                                    inflate.findViewById(R.id.notification_list);
                    INotificationManager iNotificationManager = notificationHistoryActivity.mNm;
                    NotificationHistoryActivity$$ExternalSyntheticLambda7
                            notificationHistoryActivity$$ExternalSyntheticLambda7 =
                                    new NotificationHistoryActivity$$ExternalSyntheticLambda7(
                                            notificationHistoryActivity, textView2, inflate);
                    UiEventLogger uiEventLogger = notificationHistoryActivity.mUiEventLogger;
                    NotificationHistoryAdapter notificationHistoryAdapter =
                            new NotificationHistoryAdapter();
                    notificationHistoryAdapter.mValues = new ArrayList();
                    notificationHistoryAdapter.setHasStableIds(true);
                    notificationHistoryRecyclerView.setOnItemSwipeDeleteListener(
                            notificationHistoryAdapter);
                    notificationHistoryAdapter.mNm = iNotificationManager;
                    notificationHistoryAdapter.mListener =
                            notificationHistoryActivity$$ExternalSyntheticLambda7;
                    notificationHistoryAdapter.mUiEventLogger = uiEventLogger;
                    notificationHistoryRecyclerView.setAdapter(notificationHistoryAdapter);
                    NotificationHistoryAdapter notificationHistoryAdapter2 =
                            (NotificationHistoryAdapter)
                                    notificationHistoryRecyclerView.getAdapter();
                    ArrayList arrayList2 = new ArrayList(notificationHistoryPackage4.notifications);
                    notificationHistoryAdapter2.mValues = arrayList2;
                    arrayList2.sort(new NotificationHistoryAdapter$$ExternalSyntheticLambda0());
                    notificationHistoryAdapter2.notifyDataSetChanged();
                    notificationHistoryRecyclerView.seslSetFastScrollerEnabled(false);
                    notificationHistoryRecyclerView.setNestedScrollingEnabled(false);
                    notificationHistoryActivity.mTodayView.addView(inflate);
                    i4++;
                    i3 = i;
                }
                if (((SeslSwitchBar) notificationHistoryActivity.mSwitchBar).mSwitch.isChecked()) {
                    notificationHistoryActivity.mLoadingViewController.showContent(true);
                    break;
                }
                break;
        }
    }

    public /* synthetic */ HistoryLoader$$ExternalSyntheticLambda0(
            NotificationHistoryActivity$$ExternalSyntheticLambda0
                    notificationHistoryActivity$$ExternalSyntheticLambda0,
            List list) {
        this.f$1 = notificationHistoryActivity$$ExternalSyntheticLambda0;
        this.f$0 = list;
    }
}
