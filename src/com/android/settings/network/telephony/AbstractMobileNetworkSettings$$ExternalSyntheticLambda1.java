package com.android.settings.network.telephony;

import androidx.preference.PreferenceScreen;

import com.android.settingslib.core.AbstractPreferenceController;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AbstractMobileNetworkSettings$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AbstractMobileNetworkSettings f$0;
    public final /* synthetic */ PreferenceScreen f$1;

    public /* synthetic */ AbstractMobileNetworkSettings$$ExternalSyntheticLambda1(
            AbstractMobileNetworkSettings abstractMobileNetworkSettings,
            PreferenceScreen preferenceScreen,
            int i) {
        this.$r8$classId = i;
        this.f$0 = abstractMobileNetworkSettings;
        this.f$1 = preferenceScreen;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.updateVisiblePreferenceControllers(
                        this.f$1, (AbstractPreferenceController) obj);
                break;
            default:
                AbstractMobileNetworkSettings abstractMobileNetworkSettings = this.f$0;
                PreferenceScreen preferenceScreen = this.f$1;
                AbstractPreferenceController abstractPreferenceController =
                        (AbstractPreferenceController) obj;
                abstractMobileNetworkSettings.getClass();
                abstractPreferenceController.displayPreference(preferenceScreen);
                abstractMobileNetworkSettings.updateVisiblePreferenceControllers(
                        preferenceScreen, abstractPreferenceController);
                break;
        }
    }
}
