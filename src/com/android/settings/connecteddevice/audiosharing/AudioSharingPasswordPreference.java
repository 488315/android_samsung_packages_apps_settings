package com.android.settings.connecteddevice.audiosharing;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.widget.ValidatedEditTextPreference;
import com.android.settingslib.utils.ColorUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingPasswordPreference extends ValidatedEditTextPreference {
    public CheckBox mCheckBox;
    public View mDialogMessage;
    public EditText mEditText;
    public boolean mEditable;
    public AudioSharingPasswordPreferenceController mOnDialogEventListener;

    public AudioSharingPasswordPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEditable = true;
    }

    @Override // com.android.settings.widget.ValidatedEditTextPreference,
              // com.android.settingslib.CustomEditTextPreferenceCompat
    public final void onBindDialogView(View view) {
        CheckBox checkBox;
        super.onBindDialogView(view);
        this.mEditText = (EditText) view.findViewById(R.id.edit);
        this.mCheckBox =
                (CheckBox)
                        view.findViewById(
                                com.android.settings.R.id.audio_sharing_stream_password_checkbox);
        View findViewById = view.findViewById(R.id.message);
        this.mDialogMessage = findViewById;
        if (this.mEditText == null || (checkBox = this.mCheckBox) == null || findViewById == null) {
            Log.w("AudioSharingPasswordPreference", "onBindDialogView() : Invalid layout");
            return;
        }
        checkBox.setOnCheckedChangeListener(
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.android.settings.connecteddevice.audiosharing.AudioSharingPasswordPreference$$ExternalSyntheticLambda0
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        AudioSharingPasswordPreference.this.setEditTextEnabled(!z);
                    }
                });
        AudioSharingPasswordPreferenceController audioSharingPasswordPreferenceController =
                this.mOnDialogEventListener;
        if (audioSharingPasswordPreferenceController != null) {
            audioSharingPasswordPreferenceController.onBindDialogView();
        }
    }

    @Override // com.android.settingslib.CustomEditTextPreferenceCompat
    public final void onClick(DialogInterface dialogInterface, int i) {
        EditText editText = this.mEditText;
        if (editText == null || this.mCheckBox == null) {
            Log.w("AudioSharingPasswordPreference", "onClick() : Invalid layout");
        } else {
            if (this.mOnDialogEventListener == null || i != -1 || editText.getText() == null) {
                return;
            }
            this.mOnDialogEventListener.onPreferenceDataChanged(
                    this.mEditText.getText().toString(), this.mCheckBox.isChecked());
        }
    }

    @Override // com.android.settingslib.CustomEditTextPreferenceCompat
    public final void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        if (this.mEditable) {
            return;
        }
        builder.setPositiveButton((CharSequence) null, (DialogInterface.OnClickListener) null);
    }

    public final void setEditTextEnabled(boolean z) {
        EditText editText = this.mEditText;
        if (editText == null) {
            Log.w("AudioSharingPasswordPreference", "setEditTextEnabled() : Invalid layout");
        } else {
            editText.setEnabled(z);
            this.mEditText.setAlpha(z ? 1.0f : ColorUtil.getDisabledAlpha(getContext()));
        }
    }

    public AudioSharingPasswordPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEditable = true;
    }

    public AudioSharingPasswordPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEditable = true;
    }

    public AudioSharingPasswordPreference(Context context) {
        super(context);
        this.mEditable = true;
    }
}
