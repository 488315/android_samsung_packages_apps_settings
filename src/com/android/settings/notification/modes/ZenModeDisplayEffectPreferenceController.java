package com.android.settings.notification.modes;

import android.service.notification.ZenDeviceEffects;

import androidx.preference.Preference;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeDisplayEffectPreferenceController
        extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        final boolean booleanValue = ((Boolean) obj).booleanValue();
        return saveMode(
                new Function() { // from class:
                                 // com.android.settings.notification.modes.ZenModeDisplayEffectPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        boolean z;
                        ZenDeviceEffects.Builder builder;
                        ZenModeDisplayEffectPreferenceController
                                zenModeDisplayEffectPreferenceController =
                                        ZenModeDisplayEffectPreferenceController.this;
                        z = booleanValue;
                        ZenMode zenMode = (ZenMode) obj2;
                        zenModeDisplayEffectPreferenceController.getClass();
                        builder =
                                new ZenDeviceEffects.Builder(
                                        zenMode.mRule.getDeviceEffects() != null
                                                ? zenMode.mRule.getDeviceEffects()
                                                : new ZenDeviceEffects.Builder().build());
                        String str = zenModeDisplayEffectPreferenceController.mKey;
                        str.getClass();
                        switch (str) {
                            case "effect_greyscale":
                                builder.setShouldDisplayGrayscale(z);
                                break;
                            case "effect_dark_theme":
                                builder.setShouldUseNightMode(z);
                                break;
                            case "effect_wallpaper":
                                builder.setShouldDimWallpaper(z);
                                break;
                            case "effect_aod":
                                builder.setShouldSuppressAmbientDisplay(z);
                                break;
                        }
                        zenMode.mRule.setDeviceEffects(builder.build());
                        return zenMode;
                    }
                });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0045, code lost:

       if (r3.equals("effect_greyscale") == false) goto L7;
    */
    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateState(
            androidx.preference.Preference r4, com.android.settings.notification.modes.ZenMode r5) {
        /*
            r3 = this;
            r0 = 0
            androidx.preference.TwoStatePreference r4 = (androidx.preference.TwoStatePreference) r4
            android.app.AutomaticZenRule r5 = r5.mRule
            android.service.notification.ZenDeviceEffects r5 = r5.getDeviceEffects()
            if (r5 != 0) goto Lf
            r4.setChecked(r0)
            goto L6b
        Lf:
            java.lang.String r3 = r3.mKey
            r3.getClass()
            r1 = -1
            int r2 = r3.hashCode()
            switch(r2) {
                case -1707137891: goto L3f;
                case 954695470: goto L34;
                case 1417920724: goto L29;
                case 1716138568: goto L1e;
                default: goto L1c;
            }
        L1c:
            r0 = r1
            goto L48
        L1e:
            java.lang.String r0 = "effect_aod"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L27
            goto L1c
        L27:
            r0 = 3
            goto L48
        L29:
            java.lang.String r0 = "effect_wallpaper"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L32
            goto L1c
        L32:
            r0 = 2
            goto L48
        L34:
            java.lang.String r0 = "effect_dark_theme"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L3d
            goto L1c
        L3d:
            r0 = 1
            goto L48
        L3f:
            java.lang.String r2 = "effect_greyscale"
            boolean r3 = r3.equals(r2)
            if (r3 != 0) goto L48
            goto L1c
        L48:
            switch(r0) {
                case 0: goto L64;
                case 1: goto L5c;
                case 2: goto L54;
                case 3: goto L4c;
                default: goto L4b;
            }
        L4b:
            goto L6b
        L4c:
            boolean r3 = r5.shouldSuppressAmbientDisplay()
            r4.setChecked(r3)
            goto L6b
        L54:
            boolean r3 = r5.shouldDimWallpaper()
            r4.setChecked(r3)
            goto L6b
        L5c:
            boolean r3 = r5.shouldUseNightMode()
            r4.setChecked(r3)
            goto L6b
        L64:
            boolean r3 = r5.shouldDisplayGrayscale()
            r4.setChecked(r3)
        L6b:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.modes.ZenModeDisplayEffectPreferenceController.updateState(androidx.preference.Preference,"
                    + " com.android.settings.notification.modes.ZenMode):void");
    }
}
