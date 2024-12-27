package com.samsung.android.settings.bixby.control.commands;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.template.MediaControlTemplate;
import com.samsung.android.sdk.command.template.SingleChoiceTemplate;
import com.samsung.android.sdk.command.template.SliderTemplate;
import com.samsung.android.sdk.command.template.ToggleTemplate;
import com.samsung.android.sdk.command.template.UnformattedTemplate;
import com.samsung.android.settings.bixby.utils.BixbyUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GetCommand extends BaseCommand {
    @Override // com.samsung.android.settings.bixby.control.commands.BaseCommand
    public final Bundle executeInternal(String str) {
        String str2;
        Bundle call =
                call(
                        str,
                        "method_LOAD",
                        makeExtra(
                                "method_LOAD",
                                "com.android.settings.command".equalsIgnoreCase(str)));
        parsingCommand(call);
        Command command = this.mCommand;
        if (command == null) {
            return BixbyUtils.buildActionResult("fail");
        }
        try {
            int i = this.mTemplateType;
            if (i != 2) {
                if (i != 3) {
                    if (i != 5) {
                        if (i != 6) {
                            if (i != 7) {
                                str2 = null;
                            } else {
                                if (((MediaControlTemplate) command.mTemplate) == null) {
                                    throw new IllegalStateException();
                                }
                                str2 = String.valueOf(this.mCurrentActiveMode);
                            }
                        } else {
                            if (((UnformattedTemplate) command.mTemplate) == null) {
                                throw new IllegalStateException();
                            }
                            str2 = this.mJSONValue;
                        }
                    } else {
                        if (((SingleChoiceTemplate) command.mTemplate) == null) {
                            throw new IllegalStateException();
                        }
                        str2 = String.valueOf(this.mCurrentActiveValue);
                    }
                } else {
                    if (((SliderTemplate) command.mTemplate) == null) {
                        throw new IllegalStateException();
                    }
                    str2 =
                            !TextUtils.equals(this.mStatusCode, "success")
                                    ? this.mStatusCode
                                    : String.valueOf(this.mCurrentValue);
                }
            } else {
                if (((ToggleTemplate) command.mTemplate) == null) {
                    throw new IllegalStateException();
                }
                str2 = this.mIsChecked ? "on" : "off";
            }
            return BaseCommand.buildResult(call, str2);
        } catch (IllegalStateException unused) {
            Log.w("GetCommand", "Fail to get current state");
            return BixbyUtils.buildActionResult("fail");
        }
    }
}
