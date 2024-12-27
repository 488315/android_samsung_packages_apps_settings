package com.android.settings.development.bluetooth;

import android.bluetooth.BluetoothCodecConfig;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settingslib.CustomDialogPreferenceCompat;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BaseBluetoothDialogPreference extends CustomDialogPreferenceCompat
        implements RadioGroup.OnCheckedChangeListener {
    public AbstractBluetoothDialogPreferenceController mCallback;
    public final List mRadioButtonIds;
    public final List mRadioButtonStrings;
    public final List mSummaryStrings;

    public BaseBluetoothDialogPreference(Context context) {
        super(context);
        this.mRadioButtonIds = new ArrayList();
        this.mRadioButtonStrings = new ArrayList();
        this.mSummaryStrings = new ArrayList();
    }

    public final String generateSummary(int i) {
        if (i > ((ArrayList) this.mSummaryStrings).size()) {
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i, "Unable to get summary of ", ". Size is ");
            m.append(((ArrayList) this.mSummaryStrings).size());
            Log.e("BaseBluetoothDlgPref", m.toString());
            return null;
        }
        if (i != getDefaultIndex()) {
            return String.format(
                    getContext()
                            .getResources()
                            .getString(R.string.bluetooth_select_a2dp_codec_streaming_label),
                    ((ArrayList) this.mSummaryStrings).get(i));
        }
        return (String) ((ArrayList) this.mSummaryStrings).get(getDefaultIndex());
    }

    public int getDefaultIndex() {
        return 0;
    }

    public abstract int getRadioButtonGroupId();

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onBindDialogView(View view) {
        int currentIndexByConfig;
        if (this.mCallback == null) {
            Log.e("BaseBluetoothDlgPref", "Unable to show dialog by the callback is null");
            return;
        }
        if (((ArrayList) this.mRadioButtonStrings).size()
                != ((ArrayList) this.mRadioButtonIds).size()) {
            Log.e(
                    "BaseBluetoothDlgPref",
                    "Unable to show dialog by the view and string size are not matched");
            return;
        }
        AbstractBluetoothDialogPreferenceController abstractBluetoothDialogPreferenceController =
                this.mCallback;
        BluetoothCodecConfig currentCodecConfig =
                abstractBluetoothDialogPreferenceController.getCurrentCodecConfig();
        if (currentCodecConfig == null) {
            Log.d(
                    "AbstractBtDlgCtr",
                    "Unable to get current config index. Current codec Config is null.");
            currentIndexByConfig = abstractBluetoothDialogPreferenceController.getDefaultIndex();
        } else {
            currentIndexByConfig =
                    abstractBluetoothDialogPreferenceController.getCurrentIndexByConfig(
                            currentCodecConfig);
        }
        if (currentIndexByConfig < 0
                || currentIndexByConfig >= ((ArrayList) this.mRadioButtonIds).size()) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(
                            currentIndexByConfig,
                            "Unable to show dialog by the incorrect index: ",
                            "BaseBluetoothDlgPref");
            return;
        }
        RadioGroup radioGroup = (RadioGroup) view.findViewById(getRadioButtonGroupId());
        if (radioGroup == null) {
            Log.e(
                    "BaseBluetoothDlgPref",
                    "Unable to show dialog by no radio button group: " + getRadioButtonGroupId());
            return;
        }
        radioGroup.check(
                ((Integer) ((ArrayList) this.mRadioButtonIds).get(currentIndexByConfig))
                        .intValue());
        radioGroup.setOnCheckedChangeListener(this);
        List selectableIndex = this.mCallback.getSelectableIndex();
        for (int i = 0; i < ((ArrayList) this.mRadioButtonStrings).size(); i++) {
            RadioButton radioButton =
                    (RadioButton)
                            view.findViewById(
                                    ((Integer) ((ArrayList) this.mRadioButtonIds).get(i))
                                            .intValue());
            if (radioButton == null) {
                Log.e(
                        "BaseBluetoothDlgPref",
                        "Unable to show dialog by no radio button:"
                                + ((ArrayList) this.mRadioButtonIds).get(i));
                return;
            } else {
                radioButton.setText((CharSequence) ((ArrayList) this.mRadioButtonStrings).get(i));
                radioButton.setEnabled(((ArrayList) selectableIndex).contains(Integer.valueOf(i)));
            }
        }
        TextView textView = (TextView) view.findViewById(R.id.bluetooth_audio_codec_help_info);
        if (((ArrayList) selectableIndex).size() == ((ArrayList) this.mRadioButtonIds).size()) {
            textView.setVisibility(8);
        } else {
            textView.setText(R.string.bluetooth_select_a2dp_codec_type_help_info);
            textView.setVisibility(0);
        }
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public final void onCheckedChanged(RadioGroup radioGroup, int i) {
        AbstractBluetoothDialogPreferenceController abstractBluetoothDialogPreferenceController =
                this.mCallback;
        if (abstractBluetoothDialogPreferenceController == null) {
            Log.e("BaseBluetoothDlgPref", "Callback is null");
            return;
        }
        abstractBluetoothDialogPreferenceController.onIndexUpdated(
                ((ArrayList) this.mRadioButtonIds).indexOf(Integer.valueOf(i)));
        getDialog().dismiss();
    }

    public BaseBluetoothDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRadioButtonIds = new ArrayList();
        this.mRadioButtonStrings = new ArrayList();
        this.mSummaryStrings = new ArrayList();
    }

    public BaseBluetoothDialogPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRadioButtonIds = new ArrayList();
        this.mRadioButtonStrings = new ArrayList();
        this.mSummaryStrings = new ArrayList();
    }

    public BaseBluetoothDialogPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRadioButtonIds = new ArrayList();
        this.mRadioButtonStrings = new ArrayList();
        this.mSummaryStrings = new ArrayList();
    }
}
