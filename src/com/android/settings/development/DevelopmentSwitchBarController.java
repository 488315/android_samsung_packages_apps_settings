package com.android.settings.development;

import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DevelopmentSwitchBarController implements LifecycleObserver, OnStart, OnStop {
    public final DevelopmentSettingsDashboardFragment mSettings;
    public final SettingsMainSwitchBar mSwitchBar;

    /* JADX WARN: Removed duplicated region for block: B:11:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DevelopmentSwitchBarController(
            com.android.settings.development.DevelopmentSettingsDashboardFragment r2,
            com.android.settings.widget.SettingsMainSwitchBar r3,
            boolean r4,
            com.android.settingslib.core.lifecycle.Lifecycle r5) {
        /*
            r1 = this;
            r1.<init>()
            r1.mSwitchBar = r3
            r0 = 0
            if (r4 == 0) goto L12
            java.lang.StringBuilder r4 = com.android.settings.Utils.sBuilder
            boolean r4 = android.app.ActivityManager.isUserAMonkey()
            if (r4 != 0) goto L12
            r4 = 1
            goto L13
        L12:
            r4 = r0
        L13:
            r1.mSettings = r2
            if (r4 == 0) goto L1b
            r5.addObserver(r1)
            goto L1e
        L1b:
            r3.setEnabled(r0)
        L1e:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.development.DevelopmentSwitchBarController.<init>(com.android.settings.development.DevelopmentSettingsDashboardFragment,"
                    + " com.android.settings.widget.SettingsMainSwitchBar, boolean,"
                    + " com.android.settingslib.core.lifecycle.Lifecycle):void");
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment = this.mSettings;
        boolean isDevelopmentSettingsEnabled =
                DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(
                        developmentSettingsDashboardFragment.getContext());
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        settingsMainSwitchBar.setChecked(isDevelopmentSettingsEnabled);
        settingsMainSwitchBar.addOnSwitchChangeListener(developmentSettingsDashboardFragment);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mSwitchBar.removeOnSwitchChangeListener(this.mSettings);
    }
}
