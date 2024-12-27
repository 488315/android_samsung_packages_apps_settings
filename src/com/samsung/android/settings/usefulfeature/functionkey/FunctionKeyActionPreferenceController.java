package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyAction;
import com.sec.ims.IMSParameter;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001e\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0017\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "¨\u0006\u000b"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyActionPreferenceController;",
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyItemPreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            IMSParameter.CALL.ACTION,
            "Lcom/samsung/android/settings/usefulfeature/functionkey/item/FunctionKeyAction;",
            "(Landroid/content/Context;Ljava/lang/String;Lcom/samsung/android/settings/usefulfeature/functionkey/item/FunctionKeyAction;)V",
            "getAction",
            "()Lcom/samsung/android/settings/usefulfeature/functionkey/item/FunctionKeyAction;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public class FunctionKeyActionPreferenceController extends FunctionKeyItemPreferenceController {
    public static final int $stable = 8;
    private final FunctionKeyAction action;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionKeyActionPreferenceController(
            Context context, String str, FunctionKeyAction action) {
        super(context, str, action);
        Intrinsics.checkNotNullParameter(action, "action");
        this.action = action;
    }

    public final FunctionKeyAction getAction() {
        return this.action;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
