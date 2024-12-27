package com.android.settings.development;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.IWindowManager;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TransitionAnimationScalePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final float DEFAULT_VALUE = 1.0f;
    static final int TRANSITION_ANIMATION_SCALE_SELECTOR = 1;
    public final String[] mListSummaries;
    public final String[] mListValues;
    public final IWindowManager mWindowManager;

    public TransitionAnimationScalePreferenceController(Context context) {
        super(context);
        this.mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        this.mListValues =
                context.getResources().getStringArray(R.array.transition_animation_scale_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.transition_animation_scale_entries);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "transition_animation_scale";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        writeAnimationScaleOption$1(null);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        writeAnimationScaleOption$1(obj);
        return true;
    }

    public final void updateAnimationScaleValue$1() {
        String[] strArr = this.mListValues;
        try {
            float animationScale = this.mWindowManager.getAnimationScale(1);
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= strArr.length) {
                    break;
                }
                if (animationScale <= Float.parseFloat(strArr[i2])) {
                    i = i2;
                    break;
                }
                i2++;
            }
            ListPreference listPreference = (ListPreference) this.mPreference;
            listPreference.setValue(strArr[i]);
            listPreference.setSummary(this.mListSummaries[i]);
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateAnimationScaleValue$1();
    }

    public final void writeAnimationScaleOption$1(Object obj) {
        float parseFloat;
        if (obj != null) {
            try {
                parseFloat = Float.parseFloat(obj.toString());
            } catch (RemoteException unused) {
                return;
            }
        } else {
            parseFloat = 1.0f;
        }
        this.mWindowManager.setAnimationScale(1, parseFloat);
        updateAnimationScaleValue$1();
    }
}
