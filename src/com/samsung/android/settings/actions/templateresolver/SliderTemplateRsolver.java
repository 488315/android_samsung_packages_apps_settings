package com.samsung.android.settings.actions.templateresolver;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SliderPreferenceController;

import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.FloatAction;
import com.samsung.android.settings.actions.SettingsCommandActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SliderTemplateRsolver implements CommandTemplateResolver {
    public static void setSliderValue(BasePreferenceController basePreferenceController, int i) {
        if (!(basePreferenceController instanceof SliderPreferenceController)) {
            throw new CommandTemplateResolver.InvalidActionException("invalid_action");
        }
        SliderPreferenceController sliderPreferenceController =
                (SliderPreferenceController) basePreferenceController;
        int min = sliderPreferenceController.getMin();
        int max = sliderPreferenceController.getMax();
        int sliderPosition = sliderPreferenceController.getSliderPosition();
        if (min > i || max < i) {
            throw new CommandTemplateResolver.InvalidActionException("out_of_range");
        }
        if (sliderPosition == i) {
            throw new CommandTemplateResolver.InvalidActionException("already_set");
        }
        if (!sliderPreferenceController.setSliderPosition(i)) {
            throw new CommandTemplateResolver.InvalidActionException("unknown");
        }
    }

    @Override // com.samsung.android.settings.actions.templateresolver.CommandTemplateResolver
    public final void resolve(
            CommandAction commandAction,
            BasePreferenceController basePreferenceController,
            SettingsCommandActionHandler.AnonymousClass1 anonymousClass1) {
        String message;
        int i;
        try {
            setSliderValue(basePreferenceController, (int) ((FloatAction) commandAction).mNewValue);
            i = 1;
            message = null;
        } catch (CommandTemplateResolver.InvalidActionException e) {
            message = e.getMessage();
            i = 2;
        }
        anonymousClass1.onResolved(i, message);
    }
}
