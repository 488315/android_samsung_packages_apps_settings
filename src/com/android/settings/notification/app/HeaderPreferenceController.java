package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HeaderPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver {
    public final DashboardFragment mFragment;
    public final boolean mFromChannel;
    public boolean mStarted;

    public HeaderPreferenceController(Context context, DashboardFragment dashboardFragment) {
        super(context, null);
        this.mStarted = false;
        this.mFromChannel = false;
        this.mFragment = dashboardFragment;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "pref_app_header";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        if (this.mChannel == null) {
            return ApnSettings.MVNO_NONE;
        }
        NotificationChannelGroup notificationChannelGroup = this.mChannelGroup;
        if (notificationChannelGroup == null
                || TextUtils.isEmpty(notificationChannelGroup.getName())) {
            return this.mAppRow.label.toString();
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        spannableStringBuilder.append(bidiFormatter.unicodeWrap(this.mAppRow.label));
        spannableStringBuilder.append(
                bidiFormatter.unicodeWrap(
                        ((NotificationPreferenceController) this)
                                .mContext.getText(
                                        R.string.notification_header_divider_symbol_with_spaces)));
        spannableStringBuilder.append(
                (CharSequence) bidiFormatter.unicodeWrap(this.mChannelGroup.getName().toString()));
        return spannableStringBuilder.toString();
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mAppRow != null;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mStarted = true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        DashboardFragment dashboardFragment;
        if (this.mAppRow == null || (dashboardFragment = this.mFragment) == null) {
            return;
        }
        FragmentActivity activity = this.mStarted ? dashboardFragment.getActivity() : null;
        if (activity == null) {
            return;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        LayoutPreference layoutPreference = (LayoutPreference) preference;
        boolean notificationAlertsEnabledForPackage =
                this.mBackend != null
                        ? NotificationBackend.getNotificationAlertsEnabledForPackage(
                                appRow.uid, appRow.pkg)
                        : true;
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(
                        activity,
                        dashboardFragment,
                        layoutPreference.mRootView.findViewById(R.id.entity_header));
        entityHeaderController.setIcon(this.mAppRow.icon);
        entityHeaderController.mLabel =
                (this.mChannel == null || !this.mFromChannel || isDefaultChannel())
                        ? this.mAppRow.label
                        : this.mChannel.getName();
        entityHeaderController.mSummary = getSummary();
        NotificationChannel notificationChannel = this.mChannel;
        entityHeaderController.mSecondSummary =
                notificationChannel != null ? notificationChannel.getDescription() : null;
        NotificationBackend.AppRow appRow2 = this.mAppRow;
        entityHeaderController.mPackageName = appRow2.pkg;
        entityHeaderController.mTitleIcon =
                !notificationAlertsEnabledForPackage ? R.drawable.ic_silent : 0;
        entityHeaderController.mUid = appRow2.uid;
        entityHeaderController.mAction1 = 1;
        entityHeaderController.mAction2 = 0;
        entityHeaderController.mHasAppInfoLink = true;
        LayoutPreference done =
                entityHeaderController.done(((NotificationPreferenceController) this).mContext);
        View findViewById = done.mRootView.findViewById(R.id.entity_header_icon);
        ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
        layoutParams.height =
                (int)
                        ((NotificationPreferenceController) this)
                                .mContext
                                .getResources()
                                .getDimension(R.dimen.sec_app_noti_icon_size);
        layoutParams.width =
                (int)
                        ((NotificationPreferenceController) this)
                                .mContext
                                .getResources()
                                .getDimension(R.dimen.sec_app_noti_icon_size);
        findViewById.setLayoutParams(layoutParams);
        done.mRootView.findViewById(R.id.entity_header).setVisibility(0);
    }

    public HeaderPreferenceController(Context context, DashboardFragment dashboardFragment, int i) {
        super(context, null);
        this.mStarted = false;
        this.mFragment = dashboardFragment;
        this.mFromChannel = true;
    }

    public HeaderPreferenceController(
            Context context,
            DashboardFragment dashboardFragment,
            NotificationBackend notificationBackend) {
        super(context, notificationBackend);
        this.mStarted = false;
        this.mFromChannel = false;
        this.mFragment = dashboardFragment;
    }
}
