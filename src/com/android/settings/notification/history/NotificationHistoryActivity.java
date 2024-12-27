package com.android.settings.notification.history;

import android.app.ActivityManager;
import android.app.INotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.widget.LoadingViewController;
import com.android.settingslib.utils.ThreadUtils;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.common.util.concurrent.ListenableFuture;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.notification.NotificationHistoryWarningFragment;
import com.samsung.android.settings.widget.SecMainSwitchBar;
import com.sec.ims.configuration.DATA;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationHistoryActivity extends AppCompatActivity
        implements NotificationHistoryWarningFragment.DialogFragmentListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public ListenableFuture mCountdownFuture;
    public CountDownLatch mCountdownLatch;
    public ViewGroup mDismissView;
    public ViewGroup mHistoryEmpty;
    public ViewGroup mHistoryOff;
    public ViewGroup mHistoryOn;
    public View mLoadingContainer;
    public LoadingViewController mLoadingViewController;
    public INotificationManager mNm;
    public PackageManager mPm;
    public ViewGroup mSnoozeView;
    public SecMainSwitchBar mSwitchBar;
    public int mTodayAppCount;
    public ViewGroup mTodayView;
    public UserManager mUm;
    public final UiEventLogger mUiEventLogger = new UiEventLoggerImpl();
    public final NotificationHistoryActivity$$ExternalSyntheticLambda0 mOnHistoryLoaderListener =
            new NotificationHistoryActivity$$ExternalSyntheticLambda0(this);
    public final NotificationHistoryActivity$$ExternalSyntheticLambda1 mOnSwitchBarClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.android.settings.notification.history.NotificationHistoryActivity$$ExternalSyntheticLambda1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NotificationHistoryActivity notificationHistoryActivity =
                            NotificationHistoryActivity.this;
                    boolean isChecked =
                            ((SeslSwitchBar) notificationHistoryActivity.mSwitchBar)
                                    .mSwitch.isChecked();
                    int i = isChecked ? 1 : 0;
                    if (i != 0 && notificationHistoryActivity.mHistoryOn.getVisibility() == 0) {
                        new NotificationHistoryWarningFragment()
                                .show(
                                        notificationHistoryActivity.getSupportFragmentManager(),
                                        "warningDialog");
                        return;
                    }
                    boolean z = i ^ 1;
                    if (isChecked != z) {
                        Settings.Secure.putInt(
                                notificationHistoryActivity.getContentResolver(),
                                "notification_history_enabled",
                                z ? 1 : 0);
                        notificationHistoryActivity.mUiEventLogger.log(
                                i == 0
                                        ? NotificationHistoryActivity.NotificationHistoryEvent
                                                .NOTIFICATION_HISTORY_ON
                                        : NotificationHistoryActivity.NotificationHistoryEvent
                                                .NOTIFICATION_HISTORY_OFF);
                        Log.d("NotifHistory", "onSwitchChange history to " + z);
                    }
                    notificationHistoryActivity.mHistoryOn.setVisibility(8);
                    if (i == 0) {
                        notificationHistoryActivity.mHistoryEmpty.setVisibility(0);
                        notificationHistoryActivity.mHistoryOff.setVisibility(8);
                    } else {
                        notificationHistoryActivity.mHistoryOff.setVisibility(0);
                        notificationHistoryActivity.mHistoryEmpty.setVisibility(8);
                    }
                    notificationHistoryActivity.mTodayView.removeAllViews();
                    notificationHistoryActivity.mSwitchBar.setChecked(
                            !((SeslSwitchBar) r5).mSwitch.isChecked());
                }
            };
    public final AnonymousClass1 mListener = new NotificationListenerService() { // from class:
                // com.android.settings.notification.history.NotificationHistoryActivity.1
                public RecyclerView mDismissedRv;
                public RecyclerView mSnoozedRv;

                /* JADX WARN: Removed duplicated region for block: B:18:0x00e0  */
                @Override // android.service.notification.NotificationListenerService
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onListenerConnected() {
                    /*
                        Method dump skipped, instructions count: 239
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settings.notification.history.NotificationHistoryActivity.AnonymousClass1.onListenerConnected():void");
                }

                @Override // android.service.notification.NotificationListenerService
                public final void onNotificationRemoved(
                        StatusBarNotification statusBarNotification,
                        NotificationListenerService.RankingMap rankingMap,
                        int i) {
                    if (i == 18) {
                        NotificationSbnAdapter notificationSbnAdapter =
                                (NotificationSbnAdapter) this.mSnoozedRv.getAdapter();
                        if (notificationSbnAdapter.shouldShowSbn(statusBarNotification)) {
                            notificationSbnAdapter.mValues.add(0, statusBarNotification);
                            notificationSbnAdapter.notifyDataSetChanged();
                        }
                        NotificationHistoryActivity.this.mSnoozeView.setVisibility(0);
                        return;
                    }
                    NotificationSbnAdapter notificationSbnAdapter2 =
                            (NotificationSbnAdapter) this.mDismissedRv.getAdapter();
                    if (notificationSbnAdapter2.shouldShowSbn(statusBarNotification)) {
                        notificationSbnAdapter2.mValues.add(0, statusBarNotification);
                        notificationSbnAdapter2.notifyDataSetChanged();
                    }
                    NotificationHistoryActivity.this.mDismissView.setVisibility(0);
                }

                @Override // android.service.notification.NotificationListenerService
                public final void onNotificationPosted(
                        StatusBarNotification statusBarNotification) {}
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.history.NotificationHistoryActivity$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            boolean z =
                    Settings.Secure.getInt(
                                    context.getContentResolver(), "notification_history_enabled", 1)
                            == 1;
            String valueOf = String.valueOf(36403);
            String str = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum NotificationHistoryEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_HISTORY_ON(504),
        NOTIFICATION_HISTORY_OFF(505),
        NOTIFICATION_HISTORY_OPEN(FileType.VCF),
        NOTIFICATION_HISTORY_CLOSE(FileType.VNT),
        NOTIFICATION_HISTORY_RECENT_ITEM_CLICK(FileType.VTS),
        NOTIFICATION_HISTORY_SNOOZED_ITEM_CLICK(FileType.XHTML),
        NOTIFICATION_HISTORY_PACKAGE_HISTORY_OPEN(FileType.EML),
        NOTIFICATION_HISTORY_PACKAGE_HISTORY_CLOSE(FileType.SASF),
        NOTIFICATION_HISTORY_OLDER_ITEM_CLICK(512),
        NOTIFICATION_HISTORY_OLDER_ITEM_DELETE(FileType.SSF);

        private int mId;

        NotificationHistoryEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final void measureContentFrame$1() {
        float contentFrameWidthRatio = Utils.getContentFrameWidthRatio(this);
        BigDecimal bigDecimal =
                new BigDecimal(
                        String.format(Locale.US, "%.3f", Float.valueOf(contentFrameWidthRatio)));
        float floatValue =
                new BigDecimal("1.0")
                        .subtract(bigDecimal)
                        .divide(new BigDecimal(KnoxVpnPolicyConstants.NEW_FW))
                        .floatValue();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_start_pane);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.content_end_pane);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        if (linearLayout == null || linearLayout2 == null || frameLayout == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 =
                (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.weight = floatValue;
        layoutParams2.weight = floatValue;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout2.setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 =
                (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams3.weight = contentFrameWidthRatio;
        frameLayout.setLayoutParams(layoutParams3);
        Log.d(
                "NotifHistory",
                "measureContentFrame : " + contentFrameWidthRatio + " : " + floatValue);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        measureContentFrame$1();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle(R.string.notification_history);
        setContentView(R.layout.sec_notification_history);
        this.mTodayView = (ViewGroup) findViewById(R.id.apps);
        this.mSnoozeView = (ViewGroup) findViewById(R.id.snoozed_list);
        this.mDismissView = (ViewGroup) findViewById(R.id.recently_dismissed_list);
        this.mHistoryOff = (ViewGroup) findViewById(R.id.history_off);
        this.mHistoryOn = (ViewGroup) findViewById(R.id.history_on);
        this.mHistoryEmpty = (ViewGroup) findViewById(R.id.history_on_empty);
        this.mSwitchBar = (SecMainSwitchBar) findViewById(R.id.switch_bar);
        View findViewById = findViewById(R.id.loading_container);
        this.mLoadingContainer = findViewById;
        findViewById.semSetRoundedCorners(15);
        this.mLoadingContainer.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mLoadingViewController =
                new LoadingViewController(this.mLoadingContainer, this.mHistoryOn, null);
        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.scroll);
        nestedScrollView.semSetRoundedCorners(15);
        nestedScrollView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setExpanded(false);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_app_bar);
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(getTitle());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appBarLayout.addOnOffsetChangedListener(
                new AppBarLayout
                        .OnOffsetChangedListener() { // from class:
                                                     // com.android.settings.notification.history.NotificationHistoryActivity$$ExternalSyntheticLambda3
                    @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
                    public final void onOffsetChanged(AppBarLayout appBarLayout2, int i) {
                        StatusLogger$StatusLoggingProvider statusLogger$StatusLoggingProvider =
                                NotificationHistoryActivity.STATUS_LOGGING_PROVIDER;
                        final NotificationHistoryActivity notificationHistoryActivity =
                                NotificationHistoryActivity.this;
                        notificationHistoryActivity.getClass();
                        Rect rect = new Rect();
                        appBarLayout2.getWindowVisibleDisplayFrame(rect);
                        ViewGroup viewGroup = notificationHistoryActivity.mHistoryEmpty;
                        if (viewGroup != null) {
                            final ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                            int dimensionPixelSize =
                                    Math.abs(i) == appBarLayout2.getTotalScrollRange()
                                            ? -1
                                            : ((rect.bottom - rect.top)
                                                            - notificationHistoryActivity
                                                                    .getResources()
                                                                    .getDimensionPixelSize(
                                                                            R.dimen.toolbar_height))
                                                    - (appBarLayout2.getTotalScrollRange()
                                                            - Math.abs(i));
                            if (layoutParams.height != dimensionPixelSize) {
                                layoutParams.height = dimensionPixelSize;
                                notificationHistoryActivity.mHistoryEmpty.post(
                                        new Runnable() { // from class:
                                                         // com.android.settings.notification.history.NotificationHistoryActivity$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                NotificationHistoryActivity
                                                        notificationHistoryActivity2 =
                                                                NotificationHistoryActivity.this;
                                                notificationHistoryActivity2.mHistoryEmpty
                                                        .setLayoutParams(layoutParams);
                                                notificationHistoryActivity2.mHistoryEmpty
                                                        .requestLayout();
                                            }
                                        });
                            }
                        }
                    }
                });
        measureContentFrame$1();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        try {
            unregisterAsSystemService();
        } catch (RemoteException e) {
            Log.e("NotifHistory", "Cannot unregister listener", e);
        }
        ListenableFuture listenableFuture = this.mCountdownFuture;
        if (listenableFuture != null) {
            listenableFuture.cancel(true);
        }
        this.mUiEventLogger.log(NotificationHistoryEvent.NOTIFICATION_HISTORY_CLOSE);
        super.onPause();
    }

    public final void onPositiveClick() {
        Settings.Secure.putInt(getContentResolver(), "notification_history_enabled", 0);
        this.mUiEventLogger.log(NotificationHistoryEvent.NOTIFICATION_HISTORY_OFF);
        Log.d("NotifHistory", "onSwitchChange history to false");
        this.mLoadingContainer.setVisibility(8);
        this.mHistoryOn.setVisibility(8);
        this.mHistoryOff.setVisibility(0);
        this.mHistoryEmpty.setVisibility(8);
        this.mTodayView.removeAllViews();
        this.mSwitchBar.setChecked(false);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mPm = getPackageManager();
        this.mUm = (UserManager) getSystemService(UserManager.class);
        this.mCountdownLatch = new CountDownLatch(2);
        this.mTodayView.removeAllViews();
        ThreadUtils.postOnBackgroundThread(
                new HistoryLoader$$ExternalSyntheticLambda0(
                        new HistoryLoader(this, new NotificationBackend(), this.mPm),
                        this.mOnHistoryLoaderListener));
        this.mNm = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        try {
            registerAsSystemService(
                    this,
                    new ComponentName(getPackageName(), getClass().getCanonicalName()),
                    ActivityManager.getCurrentUser());
        } catch (RemoteException e) {
            Log.e("NotifHistory", "Cannot register listener", e);
        }
        SecMainSwitchBar secMainSwitchBar = this.mSwitchBar;
        if (secMainSwitchBar != null) {
            secMainSwitchBar.setSwitchBarText(R.string.switch_on_text, R.string.switch_off_text);
            this.mSwitchBar.show();
            try {
                ((LinearLayout) this.mSwitchBar.findViewById(R.id.sesl_switchbar_container))
                        .setOnClickListener(this.mOnSwitchBarClickListener);
            } catch (IllegalStateException e2) {
                Log.d("NotifHistory", e2.toString());
            }
            this.mSwitchBar.setChecked(
                    Settings.Secure.getInt(getContentResolver(), "notification_history_enabled", 1)
                            == 1);
            if (((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked()) {
                this.mHistoryOff.setVisibility(8);
                this.mHistoryOn.setVisibility(0);
            } else {
                this.mHistoryOn.setVisibility(8);
                this.mHistoryOff.setVisibility(0);
                this.mTodayView.removeAllViews();
            }
            this.mHistoryEmpty.setVisibility(8);
        }
        this.mCountdownFuture =
                ThreadUtils.postOnBackgroundThread(
                        new NotificationHistoryActivity$$ExternalSyntheticLambda2(this, 0));
        if (((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked()
                && this.mHistoryOn.getVisibility() == 0) {
            this.mLoadingViewController.handleLoadingContainer(false, false, false);
        }
        this.mUiEventLogger.log(NotificationHistoryEvent.NOTIFICATION_HISTORY_OPEN);
        SALogging.insertSALog(Integer.toString(36042), "NSTE0402");
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public final boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
