package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

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
public final class ConversationHeaderPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver {
    public final DashboardFragment mFragment;
    public boolean mStarted;

    public ConversationHeaderPreferenceController(
            Context context, DashboardFragment dashboardFragment) {
        super(context, null);
        this.mStarted = false;
        this.mFragment = dashboardFragment;
    }

    public CharSequence getLabel() {
        ShortcutInfo shortcutInfo = this.mConversationInfo;
        if (shortcutInfo != null) {
            return shortcutInfo.getLabel();
        }
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel != null) {
            return notificationChannel.getName();
        }
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "pref_app_header";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        if (this.mChannel == null || isDefaultChannel()) {
            return this.mChannelGroup != null
                    ? this.mAppRow.label.toString()
                    : ApnSettings.MVNO_NONE;
        }
        NotificationChannelGroup notificationChannelGroup = this.mChannelGroup;
        if (notificationChannelGroup == null
                || TextUtils.isEmpty(notificationChannelGroup.getName())) {
            return this.mAppRow.label.toString();
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        spannableStringBuilder.append(
                (CharSequence) bidiFormatter.unicodeWrap(this.mAppRow.label.toString()));
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
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(
                        activity,
                        dashboardFragment,
                        ((LayoutPreference) preference).mRootView.findViewById(R.id.entity_header));
        entityHeaderController.setIcon(this.mConversationDrawable);
        entityHeaderController.mLabel = getLabel();
        entityHeaderController.mSummary = getSummary();
        NotificationBackend.AppRow appRow = this.mAppRow;
        entityHeaderController.mPackageName = appRow.pkg;
        entityHeaderController.mUid = appRow.uid;
        entityHeaderController.mAction1 = 1;
        entityHeaderController.mAction2 = 0;
        entityHeaderController.mHasAppInfoLink = true;
        LayoutPreference done =
                entityHeaderController.done(((NotificationPreferenceController) this).mContext);
        done.mRootView.findViewById(R.id.entity_header).setVisibility(0);
        done.mRootView.findViewById(R.id.entity_header).setBackground(null);
    }
}
