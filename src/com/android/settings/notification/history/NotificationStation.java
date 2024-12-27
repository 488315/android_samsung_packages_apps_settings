package com.android.settings.notification.history;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationStation extends SettingsPreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public INotificationManager mNoMan;
    public LinkedList mNotificationInfos;
    public PackageManager mPm;
    public NotificationListenerService.RankingMap mRanking;
    public final AnonymousClass1 mListener =
            new NotificationListenerService() { // from class:
                                                // com.android.settings.notification.history.NotificationStation.1
                @Override // android.service.notification.NotificationListenerService
                public final void onListenerConnected() {
                    NotificationStation.this.mRanking = getCurrentRanking();
                    NotificationListenerService.RankingMap rankingMap =
                            NotificationStation.this.mRanking;
                    if (rankingMap != null) {
                        int length = rankingMap.getOrderedKeys().length;
                    }
                    int i = NotificationStation.$r8$clinit;
                    NotificationStation notificationStation = NotificationStation.this;
                    notificationStation.getClass();
                    try {
                        StatusBarNotification[] activeNotificationsWithAttribution =
                                notificationStation.mNoMan.getActiveNotificationsWithAttribution(
                                        notificationStation.mContext.getPackageName(),
                                        notificationStation.mContext.getAttributionTag());
                        StatusBarNotification[] historicalNotificationsWithAttribution =
                                notificationStation.mNoMan
                                        .getHistoricalNotificationsWithAttribution(
                                                notificationStation.mContext.getPackageName(),
                                                notificationStation.mContext.getAttributionTag(),
                                                50,
                                                false);
                        ArrayList arrayList =
                                new ArrayList(
                                        activeNotificationsWithAttribution.length
                                                + historicalNotificationsWithAttribution.length);
                        StatusBarNotification[][] statusBarNotificationArr = {
                            activeNotificationsWithAttribution,
                            historicalNotificationsWithAttribution
                        };
                        for (int i2 = 0; i2 < 2; i2++) {
                            StatusBarNotification[] statusBarNotificationArr2 =
                                    statusBarNotificationArr[i2];
                            for (StatusBarNotification statusBarNotification :
                                    statusBarNotificationArr2) {
                                if (!statusBarNotification.getNotification().isGroupSummary()) {
                                    arrayList.add(
                                            notificationStation.createFromSbn(
                                                    statusBarNotification,
                                                    statusBarNotificationArr2
                                                            == activeNotificationsWithAttribution));
                                }
                            }
                        }
                        arrayList.sort(notificationStation.mNotificationSorter);
                        notificationStation.mNotificationInfos = new LinkedList(arrayList);
                    } catch (RemoteException e) {
                        Log.e("NotificationStation", "Cannot load Notifications: ", e);
                    }
                    int size = notificationStation.mNotificationInfos.size();
                    if (notificationStation.getPreferenceScreen() == null) {
                        notificationStation.setPreferenceScreen(
                                notificationStation
                                        .getPreferenceManager()
                                        .createPreferenceScreen(notificationStation.getContext()));
                    }
                    notificationStation.getPreferenceScreen().removeAll();
                    for (int i3 = 0; i3 < size; i3++) {
                        notificationStation
                                .getPreferenceScreen()
                                .addPreference(
                                        new HistoricalNotificationPreference(
                                                notificationStation.getPrefContext(),
                                                (HistoricalNotificationInfo)
                                                        notificationStation.mNotificationInfos.get(
                                                                i3),
                                                i3));
                    }
                }

                @Override // android.service.notification.NotificationListenerService
                public final void onNotificationPosted(
                        StatusBarNotification statusBarNotification,
                        NotificationListenerService.RankingMap rankingMap) {
                    boolean z;
                    boolean z2;
                    statusBarNotification.getNotification();
                    if (rankingMap != null) {
                        int length = rankingMap.getOrderedKeys().length;
                    }
                    int i = NotificationStation.$r8$clinit;
                    NotificationStation.this.mRanking = rankingMap;
                    if (statusBarNotification.getNotification().isGroupSummary()) {
                        return;
                    }
                    NotificationStation notificationStation = NotificationStation.this;
                    HistoricalNotificationInfo createFromSbn =
                            notificationStation.createFromSbn(statusBarNotification, true);
                    int size = notificationStation.mNotificationInfos.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        HistoricalNotificationInfo historicalNotificationInfo =
                                (HistoricalNotificationInfo)
                                        notificationStation.mNotificationInfos.get(i2);
                        if (TextUtils.equals(
                                        historicalNotificationInfo.key,
                                        statusBarNotification.getKey())
                                && historicalNotificationInfo.active
                                && !(z = createFromSbn.alerted)
                                && !(z2 = createFromSbn.visuallyInterruptive)) {
                            historicalNotificationInfo.channel = createFromSbn.channel;
                            historicalNotificationInfo.icon = createFromSbn.icon;
                            historicalNotificationInfo.title = createFromSbn.title;
                            historicalNotificationInfo.text = createFromSbn.text;
                            historicalNotificationInfo.timestamp = createFromSbn.timestamp;
                            historicalNotificationInfo.active = createFromSbn.active;
                            historicalNotificationInfo.alerted = z;
                            historicalNotificationInfo.visuallyInterruptive = z2;
                            historicalNotificationInfo.notificationExtra =
                                    createFromSbn.notificationExtra;
                            historicalNotificationInfo.rankingExtra = createFromSbn.rankingExtra;
                            ((HistoricalNotificationPreference)
                                            notificationStation
                                                    .getPreferenceScreen()
                                                    .findPreference(statusBarNotification.getKey()))
                                    .updatePreference(historicalNotificationInfo);
                            return;
                        }
                    }
                    notificationStation.mNotificationInfos.addFirst(createFromSbn);
                    notificationStation
                            .getPreferenceScreen()
                            .addPreference(
                                    new HistoricalNotificationPreference(
                                            notificationStation.getPrefContext(),
                                            (HistoricalNotificationInfo)
                                                    notificationStation.mNotificationInfos
                                                            .peekFirst(),
                                            notificationStation.mNotificationInfos.size() * (-1)));
                }

                @Override // android.service.notification.NotificationListenerService
                public final void onNotificationRankingUpdate(
                        NotificationListenerService.RankingMap rankingMap) {
                    if (rankingMap != null) {
                        int length = rankingMap.getOrderedKeys().length;
                    }
                    int i = NotificationStation.$r8$clinit;
                    NotificationStation notificationStation = NotificationStation.this;
                    notificationStation.mRanking = rankingMap;
                    notificationStation.getClass();
                    NotificationListenerService.Ranking ranking =
                            new NotificationListenerService.Ranking();
                    for (int i2 = 0;
                            i2 < notificationStation.getPreferenceScreen().getPreferenceCount();
                            i2++) {
                        HistoricalNotificationPreference historicalNotificationPreference =
                                (HistoricalNotificationPreference)
                                        notificationStation.getPreferenceScreen().getPreference(i2);
                        HistoricalNotificationInfo historicalNotificationInfo =
                                (HistoricalNotificationInfo)
                                        notificationStation.mNotificationInfos.get(i2);
                        notificationStation.mRanking.getRanking(
                                historicalNotificationPreference.getKey(), ranking);
                        notificationStation.updateFromRanking(historicalNotificationInfo);
                        ((HistoricalNotificationPreference)
                                        notificationStation
                                                .getPreferenceScreen()
                                                .findPreference(historicalNotificationInfo.key))
                                .updatePreference(historicalNotificationInfo);
                    }
                }

                @Override // android.service.notification.NotificationListenerService
                public final void onNotificationRemoved(
                        StatusBarNotification statusBarNotification,
                        NotificationListenerService.RankingMap rankingMap) {
                    if (rankingMap != null) {
                        int length = rankingMap.getOrderedKeys().length;
                    }
                    int i = NotificationStation.$r8$clinit;
                    NotificationStation.this.mRanking = rankingMap;
                    if (statusBarNotification.getNotification().isGroupSummary()) {
                        return;
                    }
                    NotificationStation notificationStation = NotificationStation.this;
                    int size = notificationStation.mNotificationInfos.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        HistoricalNotificationInfo historicalNotificationInfo =
                                (HistoricalNotificationInfo)
                                        notificationStation.mNotificationInfos.get(i2);
                        if (TextUtils.equals(
                                historicalNotificationInfo.key, statusBarNotification.getKey())) {
                            historicalNotificationInfo.active = false;
                            ((HistoricalNotificationPreference)
                                            notificationStation
                                                    .getPreferenceScreen()
                                                    .findPreference(statusBarNotification.getKey()))
                                    .updatePreference(historicalNotificationInfo);
                            return;
                        }
                    }
                }
            };
    public final NotificationStation$$ExternalSyntheticLambda0 mNotificationSorter =
            new NotificationStation$$ExternalSyntheticLambda0();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HistoricalNotificationInfo {
        public boolean active;
        public boolean alerted;
        public boolean badged;
        public NotificationChannel channel;
        public String channelId;
        public Drawable icon;
        public String key;
        public CharSequence notificationExtra;
        public String pkg;
        public CharSequence pkgname;
        public CharSequence rankingExtra;
        public CharSequence text;
        public long timestamp;
        public CharSequence title;
        public int user;
        public boolean visuallyInterruptive;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HistoricalNotificationPreference extends Preference {
        public static long sLastExpandedTimestamp;
        public final Context mContext;
        public final HistoricalNotificationInfo mInfo;
        public ViewGroup mItemView;

        public HistoricalNotificationPreference(
                Context context, HistoricalNotificationInfo historicalNotificationInfo, int i) {
            super(context);
            setLayoutResource(R.layout.notification_log_row);
            setOrder(i);
            setKey(historicalNotificationInfo.key);
            this.mInfo = historicalNotificationInfo;
            this.mContext = context;
        }

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(final PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            this.mItemView = (ViewGroup) preferenceViewHolder.itemView;
            updatePreference(this.mInfo);
            preferenceViewHolder
                    .findViewById(R.id.timestamp)
                    .setOnLongClickListener(
                            new View
                                    .OnLongClickListener() { // from class:
                                                             // com.android.settings.notification.history.NotificationStation$HistoricalNotificationPreference$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnLongClickListener
                                public final boolean onLongClick(View view) {
                                    NotificationStation.HistoricalNotificationPreference
                                            historicalNotificationPreference =
                                                    NotificationStation
                                                            .HistoricalNotificationPreference.this;
                                    PreferenceViewHolder preferenceViewHolder2 =
                                            preferenceViewHolder;
                                    historicalNotificationPreference.getClass();
                                    View findViewById =
                                            preferenceViewHolder2.findViewById(R.id.extra);
                                    findViewById.setVisibility(
                                            findViewById.getVisibility() == 0 ? 8 : 0);
                                    NotificationStation.HistoricalNotificationPreference
                                                    .sLastExpandedTimestamp =
                                            historicalNotificationPreference.mInfo.timestamp;
                                    return false;
                                }
                            });
        }

        @Override // androidx.preference.Preference
        public final void performClick() {
            Intent putExtra =
                    new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS")
                            .setPackage(this.mContext.getPackageName())
                            .putExtra("android.provider.extra.APP_PACKAGE", this.mInfo.pkg);
            HistoricalNotificationInfo historicalNotificationInfo = this.mInfo;
            NotificationChannel notificationChannel = historicalNotificationInfo.channel;
            Intent putExtra2 =
                    putExtra.putExtra(
                            "android.provider.extra.CHANNEL_ID",
                            notificationChannel != null
                                    ? notificationChannel.getId()
                                    : historicalNotificationInfo.channelId);
            putExtra2.addFlags(268435456);
            getContext().startActivity(putExtra2);
        }

        public final void updatePreference(HistoricalNotificationInfo historicalNotificationInfo) {
            ViewGroup viewGroup = this.mItemView;
            if (viewGroup == null) {
                return;
            }
            if (historicalNotificationInfo.icon != null) {
                ((ImageView) viewGroup.findViewById(R.id.icon)).setImageDrawable(this.mInfo.icon);
            }
            ((TextView) this.mItemView.findViewById(R.id.pkgname)).setText(this.mInfo.pkgname);
            this.mItemView
                    .findViewById(R.id.timestamp)
                    .setTime(historicalNotificationInfo.timestamp);
            if (TextUtils.isEmpty(historicalNotificationInfo.title)) {
                this.mItemView.findViewById(R.id.title).setVisibility(8);
            } else {
                ((TextView) this.mItemView.findViewById(R.id.title))
                        .setText(historicalNotificationInfo.title);
                this.mItemView.findViewById(R.id.title).setVisibility(0);
            }
            if (TextUtils.isEmpty(historicalNotificationInfo.text)) {
                this.mItemView.findViewById(R.id.text).setVisibility(8);
            } else {
                ((TextView) this.mItemView.findViewById(R.id.text))
                        .setText(historicalNotificationInfo.text);
                this.mItemView.findViewById(R.id.text).setVisibility(0);
            }
            if (historicalNotificationInfo.icon != null) {
                ((ImageView) this.mItemView.findViewById(R.id.icon))
                        .setImageDrawable(historicalNotificationInfo.icon);
            }
            ImageView imageView = (ImageView) this.mItemView.findViewById(R.id.profile_badge);
            imageView.setImageDrawable(
                    this.mContext
                            .getPackageManager()
                            .getUserBadgeForDensity(
                                    UserHandle.of(historicalNotificationInfo.user), -1));
            imageView.setVisibility(historicalNotificationInfo.badged ? 0 : 8);
            this.mItemView.findViewById(R.id.timestamp).setTime(this.mInfo.timestamp);
            ((TextView) this.mItemView.findViewById(R.id.notification_extra))
                    .setText(this.mInfo.notificationExtra);
            ((TextView) this.mItemView.findViewById(R.id.ranking_extra))
                    .setText(this.mInfo.rankingExtra);
            this.mItemView
                    .findViewById(R.id.extra)
                    .setVisibility(this.mInfo.timestamp == sLastExpandedTimestamp ? 0 : 8);
            this.mItemView.setAlpha(this.mInfo.active ? 1.0f : 0.5f);
            this.mItemView
                    .findViewById(R.id.alerted_icon)
                    .setVisibility(this.mInfo.alerted ? 0 : 8);
        }
    }

    public static CharSequence bold(CharSequence charSequence) {
        if (charSequence.length() == 0) {
            return charSequence;
        }
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new StyleSpan(1), 0, charSequence.length(), 0);
        return spannableString;
    }

    public static String formatPendingIntent(PendingIntent pendingIntent) {
        StringBuilder sb = new StringBuilder("Intent(pkg=");
        IntentSender intentSender = pendingIntent.getIntentSender();
        sb.append(intentSender.getCreatorPackage());
        try {
            if (ActivityManager.getService().isIntentSenderAnActivity(intentSender.getTarget())) {
                sb.append(" (activity)");
            }
        } catch (RemoteException unused) {
        }
        sb.append(")");
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v34, types: [java.lang.CharSequence] */
    public final HistoricalNotificationInfo createFromSbn(
            StatusBarNotification statusBarNotification, boolean z) {
        String str;
        CharSequence charSequence;
        List<Notification.MessagingStyle.Message> messages;
        Drawable drawable;
        Notification notification = statusBarNotification.getNotification();
        HistoricalNotificationInfo historicalNotificationInfo = new HistoricalNotificationInfo();
        historicalNotificationInfo.pkg = statusBarNotification.getPackageName();
        int userId =
                statusBarNotification.getUserId() == -1 ? 0 : statusBarNotification.getUserId();
        historicalNotificationInfo.user = userId;
        historicalNotificationInfo.badged = userId != ActivityManager.getCurrentUser();
        Drawable loadDrawableAsUser =
                statusBarNotification
                        .getNotification()
                        .getSmallIcon()
                        .loadDrawableAsUser(
                                statusBarNotification.getPackageContext(this.mContext),
                                historicalNotificationInfo.user);
        if (loadDrawableAsUser == null) {
            loadDrawableAsUser = null;
        } else {
            loadDrawableAsUser.mutate();
            loadDrawableAsUser.setColorFilter(
                    statusBarNotification.getNotification().color, PorterDuff.Mode.SRC_ATOP);
        }
        historicalNotificationInfo.icon = loadDrawableAsUser;
        if (loadDrawableAsUser == null) {
            try {
                drawable = this.mPm.getApplicationIcon(historicalNotificationInfo.pkg);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("NotificationStation", "Cannot get application icon", e);
                drawable = null;
            }
            historicalNotificationInfo.icon = drawable;
        }
        String str2 = historicalNotificationInfo.pkg;
        try {
            ApplicationInfo applicationInfo = this.mPm.getApplicationInfo(str2, 4194304);
            str = str2;
            if (applicationInfo != null) {
                str = this.mPm.getApplicationLabel(applicationInfo);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("NotificationStation", "Cannot load package name", e2);
            str = str2;
        }
        historicalNotificationInfo.pkgname = str;
        Bundle bundle = notification.extras;
        CharSequence charSequence2 =
                bundle != null ? bundle.getCharSequence("android.title") : null;
        historicalNotificationInfo.title =
                charSequence2 == null ? ApnSettings.MVNO_NONE : String.valueOf(charSequence2);
        Context packageContext = statusBarNotification.getPackageContext(this.mContext);
        Bundle bundle2 = notification.extras;
        if (bundle2 != null) {
            charSequence = bundle2.getCharSequence("android.text");
            Notification.Builder recoverBuilder =
                    Notification.Builder.recoverBuilder(packageContext, notification);
            if (recoverBuilder.getStyle() instanceof Notification.BigTextStyle) {
                charSequence = ((Notification.BigTextStyle) recoverBuilder.getStyle()).getBigText();
            } else if ((recoverBuilder.getStyle() instanceof Notification.MessagingStyle)
                    && (messages =
                                    ((Notification.MessagingStyle) recoverBuilder.getStyle())
                                            .getMessages())
                            != null
                    && messages.size() > 0) {
                charSequence =
                        ((Notification.MessagingStyle.Message)
                                        PrioritySet$$ExternalSyntheticOutline0.m(1, messages))
                                .getText();
            }
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notification.extras.getCharSequence("android.text");
            }
        } else {
            charSequence = null;
        }
        historicalNotificationInfo.text =
                charSequence == null ? ApnSettings.MVNO_NONE : String.valueOf(charSequence);
        historicalNotificationInfo.timestamp = statusBarNotification.getPostTime();
        historicalNotificationInfo.key = statusBarNotification.getKey();
        historicalNotificationInfo.channelId =
                statusBarNotification.getNotification().getChannelId();
        historicalNotificationInfo.active = z;
        Notification notification2 = statusBarNotification.getNotification();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String string = getString(R.string.notification_log_details_delimiter);
        spannableStringBuilder
                .append(bold(getString(R.string.notification_log_details_package)))
                .append((CharSequence) string)
                .append((CharSequence) historicalNotificationInfo.pkg)
                .append((CharSequence) "\n")
                .append(bold(getString(R.string.notification_log_details_key)))
                .append((CharSequence) string)
                .append((CharSequence) statusBarNotification.getKey());
        spannableStringBuilder
                .append((CharSequence) "\n")
                .append(bold(getString(R.string.notification_log_details_icon)))
                .append((CharSequence) string)
                .append((CharSequence) String.valueOf(notification2.getSmallIcon()));
        spannableStringBuilder
                .append((CharSequence) "\n")
                .append(bold("postTime"))
                .append((CharSequence) string)
                .append((CharSequence) String.valueOf(statusBarNotification.getPostTime()));
        if (notification2.getTimeoutAfter() != 0) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold("timeoutAfter"))
                    .append((CharSequence) string)
                    .append((CharSequence) String.valueOf(notification2.getTimeoutAfter()));
        }
        if (statusBarNotification.isGroup()) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_details_group)))
                    .append((CharSequence) string)
                    .append((CharSequence) String.valueOf(statusBarNotification.getGroupKey()));
            if (notification2.isGroupSummary()) {
                spannableStringBuilder.append(
                        bold(getString(R.string.notification_log_details_group_summary)));
            }
        }
        if (notification2.publicVersion != null) {
            SpannableStringBuilder append =
                    spannableStringBuilder
                            .append((CharSequence) "\n")
                            .append(
                                    bold(
                                            getString(
                                                    R.string
                                                            .notification_log_details_public_version)))
                            .append((CharSequence) string);
            Bundle bundle3 = notification2.publicVersion.extras;
            CharSequence charSequence3 =
                    bundle3 != null ? bundle3.getCharSequence("android.title") : null;
            append.append(
                    (CharSequence)
                            (charSequence3 == null
                                    ? ApnSettings.MVNO_NONE
                                    : String.valueOf(charSequence3)));
        }
        if (notification2.contentIntent != null) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_details_content_intent)))
                    .append((CharSequence) string)
                    .append((CharSequence) formatPendingIntent(notification2.contentIntent));
        }
        if (notification2.deleteIntent != null) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_details_delete_intent)))
                    .append((CharSequence) string)
                    .append((CharSequence) formatPendingIntent(notification2.deleteIntent));
        }
        if (notification2.fullScreenIntent != null) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_details_full_screen_intent)))
                    .append((CharSequence) string)
                    .append((CharSequence) formatPendingIntent(notification2.fullScreenIntent));
        }
        Notification.Action[] actionArr = notification2.actions;
        if (actionArr != null && actionArr.length > 0) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_details_actions)));
            int i = 0;
            while (true) {
                Notification.Action[] actionArr2 = notification2.actions;
                if (i >= actionArr2.length) {
                    break;
                }
                Notification.Action action = actionArr2[i];
                SpannableStringBuilder append2 =
                        spannableStringBuilder
                                .append((CharSequence) "\n  ")
                                .append((CharSequence) String.valueOf(i))
                                .append(' ')
                                .append(bold(getString(R.string.notification_log_details_title)))
                                .append((CharSequence) string);
                CharSequence charSequence4 = action.title;
                if (charSequence4 == null) {
                    charSequence4 = ApnSettings.MVNO_NONE;
                }
                append2.append(charSequence4);
                if (action.actionIntent != null) {
                    spannableStringBuilder
                            .append((CharSequence) "\n    ")
                            .append(
                                    bold(
                                            getString(
                                                    R.string
                                                            .notification_log_details_content_intent)))
                            .append((CharSequence) string)
                            .append((CharSequence) formatPendingIntent(action.actionIntent));
                }
                if (action.getRemoteInputs() != null) {
                    spannableStringBuilder
                            .append((CharSequence) "\n    ")
                            .append(bold(getString(R.string.notification_log_details_remoteinput)))
                            .append((CharSequence) string)
                            .append((CharSequence) String.valueOf(action.getRemoteInputs().length));
                }
                i++;
            }
        }
        if (notification2.contentView != null) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_details_content_view)))
                    .append((CharSequence) string)
                    .append((CharSequence) notification2.contentView.toString());
        }
        if (notification2.getBubbleMetadata() != null) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold("bubbleMetadata"))
                    .append((CharSequence) string)
                    .append((CharSequence) String.valueOf(notification2.getBubbleMetadata()));
        }
        if (notification2.getShortcutId() != null) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold("shortcutId"))
                    .append((CharSequence) string)
                    .append((CharSequence) String.valueOf(notification2.getShortcutId()));
        }
        Bundle bundle4 = notification2.extras;
        if (bundle4 != null && bundle4.size() > 0) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_details_extras)));
            for (String str3 : notification2.extras.keySet()) {
                String valueOf = String.valueOf(notification2.extras.get(str3));
                if (valueOf.length() > 100) {
                    valueOf = valueOf.substring(0, 100) + "...";
                }
                spannableStringBuilder
                        .append((CharSequence) "\n  ")
                        .append((CharSequence) str3)
                        .append((CharSequence) string)
                        .append((CharSequence) valueOf);
            }
        }
        Parcel obtain = Parcel.obtain();
        notification2.writeToParcel(obtain, 0);
        spannableStringBuilder
                .append((CharSequence) "\n")
                .append(bold(getString(R.string.notification_log_details_parcel)))
                .append((CharSequence) string)
                .append((CharSequence) String.valueOf(obtain.dataPosition()))
                .append(' ')
                .append(bold(getString(R.string.notification_log_details_ashmem)))
                .append((CharSequence) string)
                .append((CharSequence) String.valueOf(obtain.getOpenAshmemSize()))
                .append((CharSequence) "\n");
        historicalNotificationInfo.notificationExtra = spannableStringBuilder;
        updateFromRanking(historicalNotificationInfo);
        return historicalNotificationInfo;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 75;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        RecyclerView listView = getListView();
        StringBuilder sb = Utils.sBuilder;
        listView.setPaddingRelative(
                0, 0, 0, listView.getResources().getDimensionPixelSize(17105767));
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        activity.getClass();
        super.onAttach(activity);
        this.mContext = activity;
        this.mPm = activity.getPackageManager();
        this.mNoMan =
                INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        this.mNotificationInfos = new LinkedList();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        try {
            unregisterAsSystemService();
        } catch (RemoteException e) {
            Log.e("NotificationStation", "Cannot unregister listener", e);
        }
        super.onPause();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        try {
            registerAsSystemService(
                    this.mContext,
                    new ComponentName(
                            this.mContext.getPackageName(), getClass().getCanonicalName()),
                    ActivityManager.getCurrentUser());
        } catch (RemoteException e) {
            Log.e("NotificationStation", "Cannot register listener", e);
        }
    }

    public final void updateFromRanking(HistoricalNotificationInfo historicalNotificationInfo) {
        NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
        NotificationListenerService.RankingMap rankingMap = this.mRanking;
        if (rankingMap == null) {
            return;
        }
        rankingMap.getRanking(historicalNotificationInfo.key, ranking);
        historicalNotificationInfo.alerted = ranking.getLastAudiblyAlertedMillis() > 0;
        historicalNotificationInfo.visuallyInterruptive = ranking.isTextChanged();
        historicalNotificationInfo.channel = ranking.getChannel();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String string = getString(R.string.notification_log_details_delimiter);
        NotificationListenerService.Ranking ranking2 = new NotificationListenerService.Ranking();
        NotificationListenerService.RankingMap rankingMap2 = this.mRanking;
        if (rankingMap2 != null
                && rankingMap2.getRanking(historicalNotificationInfo.key, ranking2)) {
            if (historicalNotificationInfo.active && historicalNotificationInfo.alerted) {
                spannableStringBuilder
                        .append((CharSequence) "\n")
                        .append(bold(getString(R.string.notification_log_details_alerted)));
            }
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_channel)))
                    .append((CharSequence) string)
                    .append((CharSequence) historicalNotificationInfo.channel.toString());
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold("getShortcutInfo"))
                    .append((CharSequence) string)
                    .append((CharSequence) String.valueOf(ranking2.getConversationShortcutInfo()));
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold("isConversation"))
                    .append((CharSequence) string)
                    .append(ranking2.isConversation() ? "true" : "false");
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold("isBubble"))
                    .append((CharSequence) string)
                    .append((CharSequence) (ranking2.isBubble() ? "true" : "false"));
            if (historicalNotificationInfo.active) {
                spannableStringBuilder
                        .append((CharSequence) "\n")
                        .append(bold(getString(R.string.notification_log_details_importance)))
                        .append((CharSequence) string)
                        .append(
                                (CharSequence)
                                        NotificationListenerService.Ranking.importanceToString(
                                                ranking2.getImportance()));
                if (ranking2.getImportanceExplanation() != null) {
                    spannableStringBuilder
                            .append((CharSequence) "\n")
                            .append(bold(getString(R.string.notification_log_details_explanation)))
                            .append((CharSequence) string)
                            .append(ranking2.getImportanceExplanation());
                }
                spannableStringBuilder
                        .append((CharSequence) "\n")
                        .append(bold(getString(R.string.notification_log_details_badge)))
                        .append((CharSequence) string)
                        .append((CharSequence) Boolean.toString(ranking2.canShowBadge()));
            }
        } else if (this.mRanking == null) {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_details_ranking_null)));
        } else {
            spannableStringBuilder
                    .append((CharSequence) "\n")
                    .append(bold(getString(R.string.notification_log_details_ranking_none)));
        }
        historicalNotificationInfo.rankingExtra = spannableStringBuilder;
    }
}
