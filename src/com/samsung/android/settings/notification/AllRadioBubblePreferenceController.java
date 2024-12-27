package com.samsung.android.settings.notification;

import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.BubbleWarningDialogFragment;
import com.android.settings.notification.app.NotificationPreferenceController;
import com.android.settings.notification.app.NotificationSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AllRadioBubblePreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public FragmentManager mFragmentManager;
    public boolean mIsAppPage;
    public NotificationSettings.DependentFieldListener mListener;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bubble_all";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        if (this.mIsAppPage || isFloatingIconBubble()) {
            return this.mChannel == null || isDefaultChannel() || this.mAppRow != null;
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        FragmentManager fragmentManager;
        if (this.mAppRow != null && (fragmentManager = this.mFragmentManager) != null) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            if (!isFloatingIconBubble()) {
                BubbleWarningDialogFragment bubbleWarningDialogFragment =
                        new BubbleWarningDialogFragment();
                NotificationBackend.AppRow appRow = this.mAppRow;
                bubbleWarningDialogFragment.setPkgPrefInfo(appRow.uid, 1, appRow.pkg);
                bubbleWarningDialogFragment.show(fragmentManager, "dialog");
                ((RadioPreference) preference).setChecked(false);
                return false;
            }
            if (booleanValue) {
                NotificationBackend.AppRow appRow2 = this.mAppRow;
                appRow2.bubblePreference = 1;
                String str = appRow2.pkg;
                int i = appRow2.uid;
                this.mBackend.getClass();
                NotificationBackend.setAllowBubbles(i, 1, str);
            }
        }
        NotificationSettings.DependentFieldListener dependentFieldListener = this.mListener;
        if (dependentFieldListener != null) {
            dependentFieldListener.onFieldValueChanged();
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x001a, code lost:

       if (r0 == 1) goto L10;
    */
    @Override // com.android.settingslib.core.AbstractPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateState(androidx.preference.Preference r4) {
        /*
            r3 = this;
            com.android.settings.notification.NotificationBackend$AppRow r0 = r3.mAppRow
            if (r0 == 0) goto L21
            java.lang.String r1 = r0.pkg
            int r0 = r0.uid
            com.android.settings.notification.NotificationBackend r2 = r3.mBackend
            r2.getClass()
            int r0 = com.android.settings.notification.NotificationBackend.getBubblePreference(r0, r1)
            com.samsung.android.settings.notification.RadioPreference r4 = (com.samsung.android.settings.notification.RadioPreference) r4
            boolean r3 = r3.isFloatingIconBubble()
            if (r3 == 0) goto L1d
            r3 = 1
            if (r0 != r3) goto L1d
            goto L1e
        L1d:
            r3 = 0
        L1e:
            r4.setChecked(r3)
        L21:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.notification.AllRadioBubblePreferenceController.updateState(androidx.preference.Preference):void");
    }
}
