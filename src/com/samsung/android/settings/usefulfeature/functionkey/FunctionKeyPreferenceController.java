package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.core.SecCustomPreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.eternal.BackupFeatureProviderImpl;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u00006\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010"
                + "\t\u001a\n"
                + "\u0012\u0004\u0012\u00020\u0005\u0018\u00010\n"
                + "H\u0016J\n"
                + "\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\n"
                + "\u0010\r"
                + "\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyPreferenceController;",
            "Lcom/android/settings/core/SecCustomPreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getBackupKeys",
            ApnSettings.MVNO_NONE,
            "getLaunchIntent",
            "Landroid/content/Intent;",
            "getValue",
            "Lcom/samsung/android/settings/cube/ControlValue;",
            "isControllable",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class FunctionKeyPreferenceController extends SecCustomPreferenceController {
    public static final int $stable = 0;

    public FunctionKeyPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!Rune.supportFunctionKey() || Rune.isMaintenanceMode()) ? 3 : 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public List<String> getBackupKeys() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("/Settings/Advanced/SideKeyDoublePress");
        arrayList.add("/Settings/Advanced/SideKeyLongPressType");
        return arrayList;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = FunctionKeySettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 7613;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher, null, R.string.sec_function_key_setting_title, 268468224);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        List<String> backupKeys = getBackupKeys();
        List<String> list = backupKeys;
        if (list == null || list.isEmpty()) {
            return null;
        }
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        Bundle bundle = new Bundle();
        for (String str : backupKeys) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            Bundle value =
                    ((BackupFeatureProviderImpl)
                                    featureFactoryImpl.backupFeatureProvider$delegate.getValue())
                            .getValue(this.mContext, str);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            bundle.putBundle(str, value);
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (Settings.Global.getInt(
                        this.mContext.getContentResolver(), "function_key_config_doublepress", 1)
                == 1) {
            stringBuffer.append(
                    this.mContext.getString(R.string.sec_function_key_double_press_title));
            stringBuffer.append(" - ");
            int i =
                    Settings.Global.getInt(
                            this.mContext.getContentResolver(),
                            "function_key_config_doublepress_type",
                            0);
            if (i == 0) {
                stringBuffer.append(this.mContext.getString(R.string.sec_quick_launch_camera));
            } else if (i == 2) {
                stringBuffer.append(this.mContext.getString(R.string.sec_open_apps_title));
            } else if (i == 3) {
                stringBuffer.append(
                        this.mContext.getString(R.string.sec_function_key_quick_switch_text));
            } else if (i == 4) {
                stringBuffer.append(UsefulfeatureUtils.getPaywithSamsungPayTitle(this.mContext));
            }
            stringBuffer.append("\n");
        }
        stringBuffer.append(this.mContext.getString(R.string.sec_function_key_long_press_title));
        stringBuffer.append(" - ");
        if (Settings.Global.getInt(
                        this.mContext.getContentResolver(), "function_key_config_longpress_type", 0)
                == 0) {
            stringBuffer.append(this.mContext.getString(R.string.sec_long_press_wake_bixby));
        } else {
            stringBuffer.append(this.mContext.getString(R.string.sec_long_press_power_off));
        }
        builder.mBundle = bundle;
        builder.setValue(Integer.valueOf(bundle.size()));
        builder.mSummary = stringBuffer.toString();
        return builder.build();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
