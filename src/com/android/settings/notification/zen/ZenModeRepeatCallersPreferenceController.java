package com.android.settings.notification.zen;

import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeRepeatCallersPreferenceController
        extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public boolean mIsFromDnd;
    public Preference mPreference;
    public final int mRepeatCallersThreshold;
    public final BixbyRoutineActionHandler mSAHandler;

    public ZenModeRepeatCallersPreferenceController(Context context, Lifecycle lifecycle, int i) {
        super(context, "zen_mode_repeat_callers", lifecycle);
        BixbyRoutineActionHandler bixbyRoutineActionHandler =
                BixbyRoutineActionHandler.getInstance();
        this.mSAHandler = bixbyRoutineActionHandler;
        bixbyRoutineActionHandler.zenModeRepeatCallersPreferenceController = this;
        this.mRepeatCallersThreshold = i;
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        preferenceScreen
                .findPreference("zen_mode_repeat_callers")
                .setSummary(
                        this.mContext.getString(
                                R.string.zen_mode_repeat_callers_summary,
                                Integer.valueOf(this.mRepeatCallersThreshold)));
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "zen_mode_repeat_callers";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (ZenModeSettingsBase.DEBUG) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "onPrefChange allowRepeatCallers=", "PrefControllerMixin", booleanValue);
        }
        this.mMetricsFeatureProvider.action(this.mContext, 171, booleanValue);
        if (this.mIsFromDnd) {
            ZenModeBackend.getInstance(this.mContext).saveSoundPolicy(16, booleanValue);
            return true;
        }
        BixbyRoutineActionHandler.repeatedCaller = booleanValue;
        updateState(preference);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002f, code lost:

       if (com.android.settings.notification.zen.ZenModeBackend.getInstance(r5.mContext).getPriorityCallSenders() == 0) goto L15;
    */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x003e, code lost:

       r6.setEnabled(false);
       r6.setChecked(true);
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003c, code lost:

       if (((int) com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler.getCallOption()) == 0) goto L15;
    */
    @Override // com.android.settingslib.core.AbstractPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateState(androidx.preference.Preference r6) {
        /*
            r5 = this;
            super.updateState(r6)
            r5.mPreference = r6
            androidx.preference.SwitchPreference r6 = (androidx.preference.SwitchPreference) r6
            int r0 = r5.getZenMode()
            r1 = 2
            r2 = 0
            if (r0 == r1) goto L62
            r1 = 3
            if (r0 == r1) goto L62
            boolean r0 = r5.mIsFromDnd
            r1 = 1
            if (r0 == 0) goto L32
            android.content.Context r3 = r5.mContext
            com.android.settings.notification.zen.ZenModeBackend r3 = com.android.settings.notification.zen.ZenModeBackend.getInstance(r3)
            r4 = 8
            boolean r3 = r3.isPriorityCategoryEnabled(r4)
            if (r3 == 0) goto L45
            android.content.Context r3 = r5.mContext
            com.android.settings.notification.zen.ZenModeBackend r3 = com.android.settings.notification.zen.ZenModeBackend.getInstance(r3)
            int r3 = r3.getPriorityCallSenders()
            if (r3 != 0) goto L45
            goto L3e
        L32:
            com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler r3 = r5.mSAHandler
            r3.getClass()
            float r3 = com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler.getCallOption()
            int r3 = (int) r3
            if (r3 != 0) goto L45
        L3e:
            r6.setEnabled(r2)
            r6.setChecked(r1)
            goto L5c
        L45:
            r6.setEnabled(r1)
            if (r0 == 0) goto L57
            android.content.Context r0 = r5.mContext
            com.android.settings.notification.zen.ZenModeBackend r0 = com.android.settings.notification.zen.ZenModeBackend.getInstance(r0)
            r1 = 16
            boolean r0 = r0.isPriorityCategoryEnabled(r1)
            goto L59
        L57:
            boolean r0 = com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler.repeatedCaller
        L59:
            r6.setChecked(r0)
        L5c:
            android.content.Context r5 = r5.mContext
            com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler.setPeoplesummary(r5)
            goto L68
        L62:
            r6.setEnabled(r2)
            r6.setChecked(r2)
        L68:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.zen.ZenModeRepeatCallersPreferenceController.updateState(androidx.preference.Preference):void");
    }
}
