package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class TapDurationExperienceFragment$$ExternalSyntheticLambda1
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TapDurationExperienceFragment f$0;

    public /* synthetic */ TapDurationExperienceFragment$$ExternalSyntheticLambda1(
            TapDurationExperienceFragment tapDurationExperienceFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = tapDurationExperienceFragment;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x008c  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onClick(android.view.View r11) {
        /*
            r10 = this;
            int r11 = r10.$r8$classId
            com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment r10 = r10.f$0
            switch(r11) {
                case 0: goto L37;
                case 1: goto L1c;
                default: goto L7;
            }
        L7:
            android.os.Handler r11 = r10.mHandler
            com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$1 r0 = r10.mResetCircle
            boolean r11 = r11.hasCallbacks(r0)
            if (r11 == 0) goto L18
            android.os.Handler r11 = r10.mHandler
            com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$1 r0 = r10.mResetCircle
            r11.removeCallbacks(r0)
        L18:
            r10.resetCircles$1()
            return
        L1c:
            r10.onBackPressed()
            com.android.settings.overlay.FeatureFactoryImpl r10 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r10 == 0) goto L2f
            com.android.settingslib.core.instrumentation.MetricsFeatureProvider r10 = r10.getA11ySettingsMetricsFeatureProvider()
            r11 = 5016(0x1398, float:7.029E-42)
            java.lang.String r0 = "A11Y5035"
            r10.clicked(r11, r0)
            return
        L2f:
            java.lang.UnsupportedOperationException r10 = new java.lang.UnsupportedOperationException
            java.lang.String r11 = "No feature factory configured"
            r10.<init>(r11)
            throw r10
        L37:
            android.content.Context r11 = r10.mContext
            android.content.SharedPreferences r11 = com.samsung.android.settings.accessibility.SecAccessibilityUtils.getAccessibilitySharedPreferences(r11)
            android.content.SharedPreferences$Editor r11 = r11.edit()
            java.lang.String r0 = "tap_duration_tutorial"
            r1 = 1
            r11.putBoolean(r0, r1)
            r11.apply()
            java.lang.Class<com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment> r11 = com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment.class
            java.lang.String r11 = r11.getName()
            r0 = 2132028935(0x7f142e07, float:1.9696473E38)
            r10.launchFragment$1(r0, r11)
            com.android.settings.overlay.FeatureFactoryImpl r11 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r11 == 0) goto Laf
            com.android.settingslib.core.instrumentation.MetricsFeatureProvider r11 = r11.getA11ySettingsMetricsFeatureProvider()
            com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceCircleView r0 = r10.mCircleView1
            int r0 = r0.mState
            r2 = 0
            if (r0 != 0) goto L6a
            r0 = r1
            r3 = r2
            r2 = r0
            goto L71
        L6a:
            if (r0 != r1) goto L6f
            r0 = r1
        L6d:
            r3 = r0
            goto L71
        L6f:
            r0 = r2
            goto L6d
        L71:
            com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceCircleView r4 = r10.mCircleView2
            int r4 = r4.mState
            if (r4 != 0) goto L7c
            int r2 = r2 + 1
        L79:
            int r0 = r0 + 1
            goto L81
        L7c:
            if (r4 != r1) goto L81
            int r3 = r3 + 1
            goto L79
        L81:
            com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceCircleView r10 = r10.mCircleView3
            int r10 = r10.mState
            if (r10 != 0) goto L8c
            int r2 = r2 + 1
        L89:
            int r0 = r0 + 1
            goto L91
        L8c:
            if (r10 != r1) goto L91
            int r3 = r3 + 1
            goto L89
        L91:
            java.lang.String r5 = java.lang.Integer.toString(r0)
            java.lang.String r7 = java.lang.Integer.toString(r3)
            java.lang.String r9 = java.lang.Integer.toString(r2)
            java.lang.String r4 = "Trials"
            java.lang.String r6 = "Success"
            java.lang.String r8 = "Fails"
            java.util.Map r10 = java.util.Map.of(r4, r5, r6, r7, r8, r9)
            r0 = 5016(0x1398, float:7.029E-42)
            java.lang.String r1 = "A11Y5036"
            r11.action(r0, r1, r10)
            return
        Laf:
            java.lang.UnsupportedOperationException r10 = new java.lang.UnsupportedOperationException
            java.lang.String r11 = "No feature factory configured"
            r10.<init>(r11)
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$$ExternalSyntheticLambda1.onClick(android.view.View):void");
    }
}
