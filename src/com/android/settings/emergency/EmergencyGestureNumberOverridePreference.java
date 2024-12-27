package com.android.settings.emergency;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settingslib.CustomDialogPreferenceCompat;
import com.android.settingslib.emergencynumber.EmergencyNumberUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EmergencyGestureNumberOverridePreference extends CustomDialogPreferenceCompat {
    EditText mEditText;
    public final EmergencyNumberUtils mEmergencyNumberUtils;

    public EmergencyGestureNumberOverridePreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEmergencyNumberUtils = new EmergencyNumberUtils(context);
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onBindDialogView(View view) {
        this.mEditText = (EditText) view.findViewById(R.id.emergency_gesture_number_override);
        String defaultPoliceNumber = this.mEmergencyNumberUtils.getDefaultPoliceNumber();
        this.mEditText.setHint(defaultPoliceNumber);
        String policeNumber = this.mEmergencyNumberUtils.getPoliceNumber();
        if (TextUtils.equals(policeNumber, defaultPoliceNumber)) {
            return;
        }
        this.mEditText.setText(policeNumber);
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            String editable = this.mEditText.getText().toString();
            if (TextUtils.isEmpty(editable)) {
                EmergencyNumberUtils emergencyNumberUtils = this.mEmergencyNumberUtils;
                emergencyNumberUtils
                        .mContext
                        .getContentResolver()
                        .call(
                                EmergencyNumberUtils.EMERGENCY_NUMBER_OVERRIDE_AUTHORITY,
                                "SET_EMERGENCY_NUMBER_OVERRIDE",
                                (String) null,
                                AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                        "emergency_gesture_call_number",
                                        emergencyNumberUtils.getDefaultPoliceNumber()));
            } else {
                EmergencyNumberUtils emergencyNumberUtils2 = this.mEmergencyNumberUtils;
                emergencyNumberUtils2.getClass();
                Bundle bundle = new Bundle();
                bundle.putString("emergency_gesture_call_number", editable);
                emergencyNumberUtils2
                        .mContext
                        .getContentResolver()
                        .call(
                                EmergencyNumberUtils.EMERGENCY_NUMBER_OVERRIDE_AUTHORITY,
                                "SET_EMERGENCY_NUMBER_OVERRIDE",
                                (String) null,
                                bundle);
            }
        }
    }

    public EmergencyGestureNumberOverridePreference(
            Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEmergencyNumberUtils = new EmergencyNumberUtils(context);
    }

    public EmergencyGestureNumberOverridePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEmergencyNumberUtils = new EmergencyNumberUtils(context);
    }

    public EmergencyGestureNumberOverridePreference(Context context) {
        super(context);
        this.mEmergencyNumberUtils = new EmergencyNumberUtils(context);
    }
}
