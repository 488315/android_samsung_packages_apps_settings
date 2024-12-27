package com.samsung.android.settings.bixby.control.commands;

import android.os.Bundle;
import android.util.Log;

import com.samsung.android.sdk.command.action.BooleanAction;
import com.samsung.android.sdk.command.action.FloatAction;
import com.samsung.android.sdk.command.action.JSONStringAction;
import com.samsung.android.sdk.command.action.ModeAction;
import com.samsung.android.sdk.command.action.StringAction;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.samsung.android.settings.bixby.converter.ValueConverter;
import com.sec.ims.IMSParameter;

import org.json.JSONArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SetCommand extends BaseCommand {
    @Override // com.samsung.android.settings.bixby.control.commands.BaseCommand
    public Bundle makeExtra(String str, boolean z) {
        boolean z2;
        float f;
        int i;
        int i2 = this.mTemplateType;
        BaseActionParam baseActionParam = this.mActionParam;
        Bundle bundle = null;
        if (i2 == 1) {
            JSONArray convertParameterToJSONArray =
                    ValueConverter.convertParameterToJSONArray(baseActionParam.mParamList);
            if (convertParameterToJSONArray != null) {
                bundle =
                        getDataBundle(
                                z, new JSONStringAction(convertParameterToJSONArray.toString()));
            }
        } else if (i2 == 2) {
            try {
                z2 = Boolean.parseBoolean(baseActionParam.getValue());
            } catch (Exception e) {
                Log.e("SetCommand", e.getMessage());
                z2 = false;
            }
            bundle = getDataBundle(z, new BooleanAction(z2));
        } else if (i2 == 3) {
            try {
                f = Float.parseFloat(baseActionParam.getValue());
            } catch (NumberFormatException e2) {
                Log.e("SetCommand", e2.getMessage());
                f = 0.0f;
            }
            bundle = getDataBundle(z, new FloatAction(f));
        } else if (i2 == 5) {
            bundle = getDataBundle(z, new StringAction(baseActionParam.getValue()));
        } else if (i2 == 6) {
            bundle = getDataBundle(z, new JSONStringAction(baseActionParam.getValue()));
        } else if (i2 == 7) {
            try {
                i = Integer.parseInt(baseActionParam.getParameterValue("mode"));
            } catch (NumberFormatException e3) {
                Log.e("SetCommand", e3.getMessage());
                i = -1;
            }
            JSONArray convertParameterToJSONArray2 =
                    ValueConverter.convertParameterToJSONArray(baseActionParam.mParamList);
            if (convertParameterToJSONArray2 != null) {
                String jSONArray = convertParameterToJSONArray2.toString();
                ModeAction modeAction = new ModeAction();
                modeAction.mNewMode = i;
                modeAction.mExtraValue = jSONArray;
                bundle = getDataBundle(z, modeAction);
            }
        }
        Bundle bundle2 = new Bundle();
        bundle2.putBundle(IMSParameter.CALL.ACTION, bundle);
        return bundle2;
    }
}
