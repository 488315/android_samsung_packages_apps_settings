package com.samsung.android.settings.bixby.control.commands;

import android.content.ContentProviderClient;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.CommandParam;
import com.samsung.android.sdk.command.action.DefaultAction;
import com.samsung.android.sdk.command.action.JSONStringAction;
import com.samsung.android.sdk.command.template.CommandTemplate;
import com.samsung.android.sdk.command.template.MediaControlTemplate;
import com.samsung.android.sdk.command.template.SingleChoiceTemplate;
import com.samsung.android.sdk.command.template.SliderTemplate;
import com.samsung.android.sdk.command.template.ToggleTemplate;
import com.samsung.android.sdk.command.template.UnformattedTemplate;
import com.samsung.android.settings.bixby.control.Control;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.samsung.android.settings.bixby.converter.ValueConverter;
import com.samsung.android.settings.bixby.utils.BixbyUtils;
import com.sec.ims.IMSParameter;

import org.json.JSONArray;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BaseCommand implements Control {
    public final BaseActionParam mActionParam;
    public Command mCommand;
    public final String mCommandId;
    public int mTemplateType = 0;
    public int mStatus = 0;
    public String mStatusCode = null;
    public boolean mIsChecked = false;
    public int mCurrentValue = 0;
    public float mMaxValue = -1.0f;
    public float mMinValue = -1.0f;
    public float mStepValue = -1.0f;
    public String mCurrentActiveValue = null;
    public int mCurrentActiveMode = 2;
    public String mMediaInfo = null;
    public int mMediaFlag = -1;
    public String mJSONValue = null;

    public BaseCommand(BaseActionParam baseActionParam, String str) {
        this.mActionParam = baseActionParam;
        this.mCommandId = str;
    }

    public static Bundle buildResult(Bundle bundle, String str) {
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("result", str);
        int i = bundle.getInt("response_code", 0);
        String string = bundle.getString("response_message", ApnSettings.MVNO_NONE);
        Command command =
                bundle.containsKey("command") ? new Command(bundle.getBundle("command")) : null;
        m.putInt("response_code", i);
        m.putString("response_message", string);
        if (command != null) {
            m.putInt(IMSParameter.CALL.STATUS, command.mStatus);
            m.putString("status_code", command.mStatusCode);
            CommandTemplate commandTemplate = command.mTemplate;
            if (commandTemplate != null) {
                m.putBundle("template", commandTemplate.getDataBundle());
            }
        }
        return m;
    }

    public final Bundle call(String str, String str2, Bundle bundle) {
        Bundle callProvider = callProvider(str, bundle, str2);
        if (callProvider != null) {
            return callProvider;
        }
        Bundle callProvider2 = callProvider(str, bundle, str2);
        return callProvider2 == null ? BixbyUtils.buildActionResult("fail") : callProvider2;
    }

    public final Bundle callProvider(String str, Bundle bundle, String str2) {
        Bundle bundle2 = null;
        try {
            ContentProviderClient acquireUnstableContentProviderClient =
                    this.mActionParam
                            .mContext
                            .getContentResolver()
                            .acquireUnstableContentProviderClient(str);
            try {
                bundle2 =
                        acquireUnstableContentProviderClient.call(
                                str, str2, "command://" + str + "/" + this.mCommandId, bundle);
                acquireUnstableContentProviderClient.close();
            } catch (Throwable th) {
                if (acquireUnstableContentProviderClient != null) {
                    try {
                        acquireUnstableContentProviderClient.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException | NullPointerException unused) {
            Log.e("BaseCommand", "fail to call controller, authority : " + str);
        }
        return bundle2;
    }

    @Override // com.samsung.android.settings.bixby.control.Control
    public final Bundle execute(String str) {
        Bundle bundle;
        String str2;
        boolean equalsIgnoreCase = "com.android.settings.command".equalsIgnoreCase(str);
        BaseActionParam baseActionParam = this.mActionParam;
        if (equalsIgnoreCase) {
            DefaultAction defaultAction = new DefaultAction();
            defaultAction.mCommandParam.mDexMode = baseActionParam.isNewDex();
            bundle = new Bundle();
            bundle.putBundle(IMSParameter.CALL.ACTION, defaultAction.getDataBundle());
        } else {
            bundle = null;
        }
        parsingCommand(call(str, "method_LOAD", bundle));
        if (!baseActionParam.mIsCheckSupport) {
            return executeInternal(str);
        }
        if (TextUtils.isEmpty(this.mStatusCode)) {
            int i = this.mStatus;
            str2 = i != 1 ? i != 2 ? "false" : "not_supported_device" : "true";
        } else {
            str2 = this.mStatusCode;
        }
        return BixbyUtils.buildActionResult(str2);
    }

    public Bundle executeInternal(String str) {
        return buildResult(
                call(
                        str,
                        "method_ACTION",
                        makeExtra(
                                "method_ACTION",
                                "com.android.settings.command".equalsIgnoreCase(str))));
    }

    public final Bundle getDataBundle(boolean z, CommandAction commandAction) {
        if (!z) {
            return commandAction.getDataBundle();
        }
        CommandParam commandParam = commandAction.mCommandParam;
        BaseActionParam baseActionParam = this.mActionParam;
        commandParam.mDexMode = baseActionParam.isNewDex();
        commandAction.mCommandParam.mDexMode = baseActionParam.isNewDex();
        return commandAction.getDataBundle();
    }

    public Bundle makeExtra(String str, boolean z) {
        Bundle dataBundle;
        boolean equals = str.equals("method_LOAD");
        BaseActionParam baseActionParam = this.mActionParam;
        if (equals && ((ArrayList) baseActionParam.mParamList).isEmpty()) {
            Log.i("BaseCommand", "it doesn't need to make extra");
            return null;
        }
        int i = this.mTemplateType;
        if (i == 1 || i == 6) {
            JSONArray jSONArray = new JSONArray();
            ArrayList arrayList = (ArrayList) baseActionParam.mParamList;
            if (!arrayList.isEmpty()) {
                jSONArray = ValueConverter.convertParameterToJSONArray(arrayList);
            }
            JSONStringAction jSONStringAction = new JSONStringAction(jSONArray.toString());
            if (z) {
                jSONStringAction.mCommandParam.mDexMode = baseActionParam.isNewDex();
                jSONStringAction.mCommandParam.mDexMode = baseActionParam.isNewDex();
                dataBundle = jSONStringAction.getDataBundle();
            } else {
                dataBundle = jSONStringAction.getDataBundle();
            }
        } else {
            dataBundle = null;
        }
        if (dataBundle == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putBundle(IMSParameter.CALL.ACTION, dataBundle);
        return bundle;
    }

    public final void parsingCommand(Bundle bundle) {
        Bundle bundle2 = (Bundle) bundle.getParcelable("command");
        Command command = bundle2 == null ? null : new Command(bundle2);
        this.mCommand = command;
        if (command == null) {
            return;
        }
        CommandTemplate commandTemplate = command.mTemplate;
        if (commandTemplate != null) {
            int templateType = commandTemplate.getTemplateType();
            this.mTemplateType = templateType;
            if (templateType == 2) {
                this.mIsChecked = ((ToggleTemplate) commandTemplate).mIsChecked;
                SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                        new StringBuilder("parsingToggleCommand() isChecked : "),
                        this.mIsChecked,
                        "BaseCommand");
            } else if (templateType == 3) {
                SliderTemplate sliderTemplate = (SliderTemplate) commandTemplate;
                this.mMaxValue = sliderTemplate.mMaxValue;
                this.mMinValue = sliderTemplate.mMinValue;
                this.mStepValue = sliderTemplate.mStepValue;
                this.mCurrentValue = (int) sliderTemplate.mCurrentValue;
                StringBuilder sb = new StringBuilder("parsingSliderCommand() max : ");
                sb.append(this.mMaxValue);
                sb.append(", min : ");
                sb.append(this.mMinValue);
                sb.append(", step : ");
                sb.append(this.mStepValue);
                sb.append(", current : ");
                TooltipPopup$$ExternalSyntheticOutline0.m(sb, this.mCurrentValue, "BaseCommand");
            } else if (templateType == 5) {
                this.mCurrentActiveValue =
                        ((SingleChoiceTemplate) commandTemplate).mCurrentActiveValue;
                Utils$$ExternalSyntheticOutline0.m(
                        new StringBuilder("parsingSingleChoiceCommand() current active : "),
                        this.mCurrentActiveValue,
                        "BaseCommand");
            } else if (templateType == 6) {
                this.mJSONValue = ((UnformattedTemplate) commandTemplate).mJSONString;
                Utils$$ExternalSyntheticOutline0.m(
                        new StringBuilder("parsingJSONCommand() json : "),
                        this.mJSONValue,
                        "BaseCommand");
            } else if (templateType == 7) {
                MediaControlTemplate mediaControlTemplate = (MediaControlTemplate) commandTemplate;
                this.mCurrentActiveMode = mediaControlTemplate.mCurrentActiveMode;
                this.mMediaInfo = mediaControlTemplate.mMediaInfo;
                this.mMediaFlag = mediaControlTemplate.mModeFlags;
                StringBuilder sb2 =
                        new StringBuilder("parsingMediaControlCommand() current mode : ");
                sb2.append(this.mCurrentActiveMode);
                sb2.append(", media info : ");
                sb2.append(this.mMediaInfo);
                sb2.append(", media flag : ");
                TooltipPopup$$ExternalSyntheticOutline0.m(sb2, this.mMediaFlag, "BaseCommand");
            }
        }
        Command command2 = this.mCommand;
        this.mStatus = command2.mStatus;
        this.mStatusCode = command2.mStatusCode;
        StringBuilder sb3 = new StringBuilder("parsingCommand() template type : ");
        sb3.append(this.mTemplateType);
        sb3.append(", status : ");
        sb3.append(this.mStatus);
        sb3.append(", status code : ");
        Utils$$ExternalSyntheticOutline0.m(sb3, this.mStatusCode, "BaseCommand");
    }

    public final Bundle buildResult(Bundle bundle) {
        String str;
        int i = bundle.getInt("response_code", 0);
        String string = bundle.getString("response_message", ApnSettings.MVNO_NONE);
        StringBuilder sb = new StringBuilder("getResult() command [");
        sb.append(this.mCommandId);
        sb.append("] response code : ");
        sb.append(i);
        sb.append(", response message : ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, string, "BaseCommand");
        if (i != 1) {
            str = "fail";
            if (i == 2 && TextUtils.equals(string, "already_set")) {
                str = "already_set";
            }
        } else {
            str = "success";
        }
        return buildResult(bundle, str);
    }
}
