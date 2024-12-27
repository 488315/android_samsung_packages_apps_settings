package com.samsung.android.settings.widget;

import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SecRadioButtonGearPreferenceControllersHandler
        implements SecRadioButtonGearPreferenceController.OnChangeListener {
    public OnChangeListener mOnChangeListener;
    public final List mChildren = new ArrayList();
    public String mSelectedKey = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnChangeListener {
        void onCheckedChanged(
                SecRadioButtonGearPreferenceControllersHandler
                        secRadioButtonGearPreferenceControllersHandler);
    }

    public final void updateStates(final boolean z) {
        ((ArrayList) this.mChildren)
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceControllersHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SecRadioButtonGearPreferenceControllersHandler
                                        secRadioButtonGearPreferenceControllersHandler =
                                                SecRadioButtonGearPreferenceControllersHandler.this;
                                boolean z2 = z;
                                AbstractPreferenceController abstractPreferenceController =
                                        (AbstractPreferenceController) obj;
                                secRadioButtonGearPreferenceControllersHandler.getClass();
                                if (abstractPreferenceController
                                        instanceof SecRadioButtonGearPreferenceController) {
                                    ((SecRadioButtonGearPreferenceController)
                                                    abstractPreferenceController)
                                            .setChecked(
                                                    secRadioButtonGearPreferenceControllersHandler
                                                            .mSelectedKey,
                                                    z2);
                                }
                            }
                        });
    }
}
