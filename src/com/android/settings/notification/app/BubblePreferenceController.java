package com.android.settings.notification.app;

import android.app.NotificationChannel;

import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BubblePreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    static final int SYSTEM_WIDE_OFF = 0;
    static final int SYSTEM_WIDE_ON = 1;
    public FragmentManager mFragmentManager;
    public boolean mHasSentInvalidMsg;
    public boolean mIsAppPage;
    public NotificationSettings.DependentFieldListener mListener;
    public int mNumConversations;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bubble_pref";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        if (!this.mIsAppPage && !isFloatingIconBubble()) {
            return false;
        }
        if (this.mChannel == null || isDefaultChannel()) {
            return true;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        return (appRow == null || appRow.bubblePreference == 0) ? false : true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        FragmentManager fragmentManager;
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel != null) {
            notificationChannel.setAllowBubbles(((Boolean) obj).booleanValue());
            saveChannel();
            return true;
        }
        if (!this.mIsAppPage) {
            return true;
        }
        BubblePreference bubblePreference = (BubblePreference) preference;
        if (this.mAppRow != null && (fragmentManager = this.mFragmentManager) != null) {
            int intValue = ((Integer) obj).intValue();
            if (!isFloatingIconBubble() && bubblePreference.mSelectedPreference == 0) {
                BubbleWarningDialogFragment bubbleWarningDialogFragment =
                        new BubbleWarningDialogFragment();
                NotificationBackend.AppRow appRow = this.mAppRow;
                bubbleWarningDialogFragment.setPkgPrefInfo(appRow.uid, intValue, appRow.pkg);
                bubbleWarningDialogFragment.show(fragmentManager, "dialog");
                return false;
            }
            NotificationBackend.AppRow appRow2 = this.mAppRow;
            appRow2.bubblePreference = intValue;
            String str = appRow2.pkg;
            int i = appRow2.uid;
            this.mBackend.getClass();
            NotificationBackend.setAllowBubbles(i, intValue, str);
        }
        NotificationSettings.DependentFieldListener dependentFieldListener = this.mListener;
        if (dependentFieldListener == null) {
            return true;
        }
        dependentFieldListener.onFieldValueChanged();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        NotificationBackend.AppRow appRow;
        if (!this.mIsAppPage || (appRow = this.mAppRow) == null) {
            if (this.mChannel != null) {
                SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                        (SecRestrictedSwitchPreference) preference;
                secRestrictedSwitchPreference.setDisabledByAdmin(this.mAdmin);
                secRestrictedSwitchPreference.setChecked(
                        (this.mChannel.canBubble()
                                        || (this.mAppRow.bubblePreference == 1
                                                && this.mChannel.getAllowBubbles() == -1))
                                && isFloatingIconBubble());
                return;
            }
            return;
        }
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        this.mHasSentInvalidMsg = NotificationBackend.isInInvalidMsgState(i, str);
        NotificationBackend.AppRow appRow2 = this.mAppRow;
        this.mNumConversations =
                NotificationBackend.getConversations(appRow2.uid, appRow2.pkg).getList().size();
        int i2 = this.mAppRow.bubblePreference;
        BubblePreference bubblePreference = (BubblePreference) preference;
        if (bubblePreference.mHelper.setDisabledByAdmin(this.mAdmin)) {
            bubblePreference.notifyChanged();
        }
        if (this.mHasSentInvalidMsg && this.mNumConversations <= 0) {
            r1 = false;
        }
        if (bubblePreference.mSelectedVisible != r1) {
            bubblePreference.mSelectedVisible = r1;
            bubblePreference.notifyChanged();
        }
        if (isFloatingIconBubble()) {
            bubblePreference.mSelectedPreference = i2;
            bubblePreference.notifyChanged();
        } else {
            bubblePreference.mSelectedPreference = 0;
            bubblePreference.notifyChanged();
        }
    }
}
