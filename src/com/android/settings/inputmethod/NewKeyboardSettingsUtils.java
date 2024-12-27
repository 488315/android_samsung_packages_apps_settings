package com.android.settings.inputmethod;

import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.view.InputDevice;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class NewKeyboardSettingsUtils {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeyboardInfo {
        public final InputMethodInfo mInputMethodInfo;
        public final InputMethodSubtype mInputMethodSubtype;
        public final String mLayout;
        public final CharSequence mSubtypeLabel;

        public KeyboardInfo(
                CharSequence charSequence,
                String str,
                InputMethodInfo inputMethodInfo,
                InputMethodSubtype inputMethodSubtype) {
            this.mSubtypeLabel = charSequence;
            this.mLayout = str;
            this.mInputMethodInfo = inputMethodInfo;
            this.mInputMethodSubtype = inputMethodSubtype;
        }
    }

    public static InputDevice getInputDevice(
            InputManager inputManager, InputDeviceIdentifier inputDeviceIdentifier) {
        if (inputDeviceIdentifier == null) {
            return null;
        }
        return inputManager.getInputDeviceByDescriptor(inputDeviceIdentifier.getDescriptor());
    }
}
