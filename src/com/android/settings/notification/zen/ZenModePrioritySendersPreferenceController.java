package com.android.settings.notification.zen;

import android.app.Flags;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModePrioritySendersPreferenceController
        extends AbstractZenModePreferenceController {
    public final ZenPrioritySendersHelper mHelper;
    public final boolean mIsMessages;
    public PreferenceCategory mPreferenceCategory;
    SelectorWithWidgetPreference.OnClickListener mSelectorClickListener;

    public ZenModePrioritySendersPreferenceController(
            Context context,
            String str,
            Lifecycle lifecycle,
            boolean z,
            NotificationBackend notificationBackend) {
        super(context, str, lifecycle);
        this.mSelectorClickListener =
                new SelectorWithWidgetPreference
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.zen.ZenModePrioritySendersPreferenceController.2
                    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
                    public final void onRadioButtonClicked(
                            SelectorWithWidgetPreference selectorWithWidgetPreference) {
                        ZenModePrioritySendersPreferenceController
                                zenModePrioritySendersPreferenceController =
                                        ZenModePrioritySendersPreferenceController.this;
                        ZenPrioritySendersHelper zenPrioritySendersHelper =
                                zenModePrioritySendersPreferenceController.mHelper;
                        ZenModeBackend zenModeBackend =
                                zenModePrioritySendersPreferenceController.mBackend;
                        boolean z2 = zenModePrioritySendersPreferenceController.mIsMessages;
                        int[] iArr =
                                zenPrioritySendersHelper.settingsToSaveOnClick(
                                        selectorWithWidgetPreference,
                                        z2
                                                ? zenModeBackend.getPriorityMessageSenders()
                                                : zenModeBackend.getPriorityCallSenders(),
                                        z2
                                                ? zenModeBackend.isPriorityCategoryEnabled(256)
                                                        ? zenModeBackend
                                                                .mPolicy
                                                                .priorityConversationSenders
                                                        : 3
                                                : -10);
                        int i = iArr[0];
                        int i2 = iArr[1];
                        if (i != -10) {
                            zenModeBackend.saveSenders(z2 ? 4 : 8, i);
                        }
                        if (!z2 || i2 == -10) {
                            return;
                        }
                        zenModeBackend.getClass();
                        boolean z3 = i2 != 3;
                        NotificationManager.Policy policy = zenModeBackend.mPolicy;
                        int i3 = policy.priorityCategories;
                        zenModeBackend.mPolicy =
                                new NotificationManager.Policy(
                                        z3 ? i3 | 256 : i3 & (-257),
                                        policy.priorityCallSenders,
                                        policy.priorityMessageSenders,
                                        policy.suppressedVisualEffects,
                                        i2);
                        if (Flags.modesApi()) {
                            zenModeBackend.mNotificationManager.setNotificationPolicy(
                                    zenModeBackend.mPolicy, true);
                        } else {
                            zenModeBackend.mNotificationManager.setNotificationPolicy(
                                    zenModeBackend.mPolicy);
                        }
                    }
                };
        this.mIsMessages = z;
        this.mHelper =
                new ZenPrioritySendersHelper(
                        context,
                        z,
                        this.mBackend,
                        notificationBackend,
                        this.mSelectorClickListener);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(this.KEY);
        this.mPreferenceCategory = preferenceCategory;
        this.mHelper.displayPreference(preferenceCategory);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.KEY;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        super.onResume();
        if (this.mIsMessages) {
            new AsyncTask() { // from class:
                              // com.android.settings.notification.zen.ZenModePrioritySendersPreferenceController.1
                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    ZenModePrioritySendersPreferenceController.this.mHelper.updateChannelCounts();
                    return null;
                }

                @Override // android.os.AsyncTask
                public final void onPostExecute(Object obj) {
                    if (((AbstractPreferenceController)
                                            ZenModePrioritySendersPreferenceController.this)
                                    .mContext
                            == null) {
                        return;
                    }
                    ZenModePrioritySendersPreferenceController
                            zenModePrioritySendersPreferenceController =
                                    ZenModePrioritySendersPreferenceController.this;
                    zenModePrioritySendersPreferenceController.updateState(
                            zenModePrioritySendersPreferenceController.mPreferenceCategory);
                }
            }.execute(new Void[0]);
        }
        this.mHelper.updateSummaries();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ZenModeBackend zenModeBackend = this.mBackend;
        boolean z = this.mIsMessages;
        this.mHelper.updateState(
                z
                        ? zenModeBackend.getPriorityMessageSenders()
                        : zenModeBackend.getPriorityCallSenders(),
                z
                        ? zenModeBackend.isPriorityCategoryEnabled(256)
                                ? zenModeBackend.mPolicy.priorityConversationSenders
                                : 3
                        : -10);
    }
}
