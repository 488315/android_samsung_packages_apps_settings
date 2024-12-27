package com.android.settings.development.autofill;

import android.R;
import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;

import com.android.settings.Utils;
import com.android.settingslib.CustomEditTextPreferenceCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractGlobalSettingsPreference extends CustomEditTextPreferenceCompat {
    public final int mDefaultValue;
    public final String mKey;
    public final AutofillDeveloperSettingsObserver mObserver;

    public AbstractGlobalSettingsPreference(
            Context context, AttributeSet attributeSet, String str, int i) {
        super(context, attributeSet);
        this.mKey = str;
        this.mDefaultValue = i;
        this.mObserver =
                new AutofillDeveloperSettingsObserver(
                        context,
                        new Runnable() { // from class:
                                         // com.android.settings.development.autofill.AbstractGlobalSettingsPreference$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                AbstractGlobalSettingsPreference abstractGlobalSettingsPreference =
                                        AbstractGlobalSettingsPreference.this;
                                abstractGlobalSettingsPreference.setSummary(
                                        Integer.toString(
                                                Settings.Global.getInt(
                                                        abstractGlobalSettingsPreference
                                                                .getContext()
                                                                .getContentResolver(),
                                                        abstractGlobalSettingsPreference.mKey,
                                                        abstractGlobalSettingsPreference
                                                                .mDefaultValue)));
                            }
                        });
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        registerDependency();
        this.mObserver.register();
        setSummary(
                Integer.toString(
                        Settings.Global.getInt(
                                getContext().getContentResolver(), this.mKey, this.mDefaultValue)));
    }

    @Override // com.android.settingslib.CustomEditTextPreferenceCompat
    public final void onBindDialogView(View view) {
        super.onBindDialogView(view);
        EditText editText = (EditText) view.findViewById(R.id.edit);
        if (editText != null) {
            editText.setInputType(2);
            editText.setText(
                    Integer.toString(
                            Settings.Global.getInt(
                                    getContext().getContentResolver(),
                                    this.mKey,
                                    this.mDefaultValue)));
            StringBuilder sb = Utils.sBuilder;
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(editText);
        }
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        AutofillDeveloperSettingsObserver autofillDeveloperSettingsObserver = this.mObserver;
        autofillDeveloperSettingsObserver.mResolver.unregisterContentObserver(
                autofillDeveloperSettingsObserver);
        super.onDetached();
    }

    @Override // com.android.settingslib.CustomEditTextPreferenceCompat
    public final void onDialogClosed(boolean z) {
        if (z) {
            String str = this.mText;
            int i = this.mDefaultValue;
            try {
                i = Integer.parseInt(str);
            } catch (Exception unused) {
                StringBuilder m =
                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                "Error converting '", str, "' to integer. Using ");
                m.append(this.mDefaultValue);
                m.append(" instead");
                Log.e("AbstractGlobalSettingsPreference", m.toString());
            }
            Settings.Global.putInt(getContext().getContentResolver(), this.mKey, i);
        }
    }
}
