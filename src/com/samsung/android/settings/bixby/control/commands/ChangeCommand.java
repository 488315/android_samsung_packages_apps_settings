package com.samsung.android.settings.bixby.control.commands;

import android.os.Bundle;
import android.util.Log;

import com.samsung.android.sdk.command.action.FloatAction;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.sec.ims.IMSParameter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ChangeCommand extends SetCommand {
    @Override // com.samsung.android.settings.bixby.control.commands.SetCommand,
              // com.samsung.android.settings.bixby.control.commands.BaseCommand
    public final Bundle makeExtra(String str, boolean z) {
        float f;
        BaseActionParam baseActionParam = this.mActionParam;
        String value = baseActionParam.getValue();
        if (value != null) {
            float f2 = this.mStepValue;
            float f3 = this.mMinValue;
            float f4 = this.mMaxValue;
            f = this.mCurrentValue;
            if (!"MAX".equalsIgnoreCase(value)) {
                if (!"MIN".equalsIgnoreCase(value)) {
                    if ("MID".equalsIgnoreCase(value)) {
                        f3 = f4 / 2.0f;
                    } else {
                        try {
                            float abs = Math.abs(Float.parseFloat(value));
                            if (value.startsWith("+")) {
                                f3 = (abs * f2) + f;
                                if (f3 > f4) {}
                            } else if (value.startsWith("-")) {
                                float f5 = f - (abs * f2);
                                if (f5 >= f3) {
                                    f3 = f5;
                                }
                            } else {
                                f3 = abs;
                            }
                        } catch (NumberFormatException e) {
                            Log.e("FloatCommand", "fail to parse level : " + e.getMessage());
                        }
                    }
                }
                Log.i("FloatCommand", "step : " + f2 + ", current : " + f + ", target : " + f3);
                f = f3;
            }
            f3 = f4;
            Log.i("FloatCommand", "step : " + f2 + ", current : " + f + ", target : " + f3);
            f = f3;
        } else {
            String parameterValue = baseActionParam.getParameterValue("percentage");
            if (parameterValue != null) {
                try {
                    f = (this.mMaxValue * Float.parseFloat(parameterValue)) / 100.0f;
                } catch (NumberFormatException e2) {
                    Log.e("FloatCommand", "fail to parse percent : " + e2.getMessage());
                    f = (float) this.mCurrentValue;
                }
            } else {
                f = this.mCurrentValue + this.mStepValue;
            }
        }
        if (f == -1.0f) {
            return null;
        }
        Bundle dataBundle = getDataBundle(z, new FloatAction(f));
        Bundle bundle = new Bundle();
        bundle.putBundle(IMSParameter.CALL.ACTION, dataBundle);
        return bundle;
    }
}
