package com.samsung.android.settings.wifi.details;

import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiModifyPreferenceController$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiModifyPreferenceController f$0;

    public /* synthetic */ WifiModifyPreferenceController$$ExternalSyntheticLambda0(
            WifiModifyPreferenceController wifiModifyPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiModifyPreferenceController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        EditText editText;
        int i = this.$r8$classId;
        WifiModifyPreferenceController wifiModifyPreferenceController = this.f$0;
        switch (i) {
            case 0:
                SALogging.insertSALog(
                        !wifiModifyPreferenceController.mPasswordImageButton.checked ? 1 : 0,
                        wifiModifyPreferenceController.mSAScreenId,
                        "1021",
                        (String) null);
                if (wifiModifyPreferenceController.mPasswordImageButton.checked) {
                    wifiModifyPreferenceController.mPasswordView.setTransformationMethod(
                            PasswordTransformationMethod.getInstance());
                    editText = wifiModifyPreferenceController.mPasswordView;
                } else {
                    wifiModifyPreferenceController.mPasswordView.setTransformationMethod(null);
                    editText = wifiModifyPreferenceController.mPasswordView;
                }
                SeslColorPicker$16$$ExternalSyntheticOutline0.m(editText);
                break;
            case 1:
                wifiModifyPreferenceController.save$1();
                break;
            default:
                wifiModifyPreferenceController.mImeHelper.hideSoftKeyboard(
                        wifiModifyPreferenceController.mFragment.getActivity().getCurrentFocus());
                SALogging.insertSALog(wifiModifyPreferenceController.mSAScreenId, "1045");
                wifiModifyPreferenceController.finishActivity$2();
                break;
        }
    }
}
