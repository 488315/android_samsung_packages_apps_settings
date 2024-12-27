package com.android.settings.remoteauth;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.FeatureFlagUtils;

import com.android.settings.R;
import com.android.settings.biometrics.BiometricStatusPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\b\u0007\u0018\u0000 \r"
                + "2\u00020\u0001:\u0001\r"
                + "B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\n"
                + " \b*\u0004\u0018\u00010\u00050\u0005H\u0014J\b\u0010"
                + "\t\u001a\u00020\u0005H\u0014J\b\u0010\n"
                + "\u001a\u00020\u000bH\u0014J\b\u0010\f\u001a\u00020\u000bH\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\u000e"
        },
        d2 = {
            "Lcom/android/settings/remoteauth/RemoteAuthStatusPreferenceController;",
            "Lcom/android/settings/biometrics/BiometricStatusPreferenceController;",
            "context",
            "Landroid/content/Context;",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "getSettingsClassName",
            "kotlin.jvm.PlatformType",
            "getSummaryText",
            "isDeviceSupported",
            ApnSettings.MVNO_NONE,
            "isHardwareSupported",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class RemoteAuthStatusPreferenceController
        extends BiometricStatusPreferenceController {
    public static final int $stable = 8;
    private static final Companion Companion = new Companion();

    @Deprecated
    public static final String KEY_REMOTE_AUTHENTICATOR_SETTINGS =
            "biometric_remote_authenticator_settings";

    private final Context context;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    public /* synthetic */ RemoteAuthStatusPreferenceController(
            Context context, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? KEY_REMOTE_AUTHENTICATOR_SETTINGS : str);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public String getSettingsClassName() {
        return RemoteAuthActivityInternal.class.getName();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public String getSummaryText() {
        Context context = this.context;
        Intrinsics.checkNotNullParameter(context, "context");
        String string =
                context.getResources()
                        .getString(R.string.security_settings_remoteauth_preference_summary);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isDeviceSupported() {
        return FeatureFlagUtils.isEnabled(this.context, "settings_remoteauth_enrollment");
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isHardwareSupported() {
        return FeatureFlagUtils.isEnabled(this.context, "settings_remoteauth_enrollment");
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemoteAuthStatusPreferenceController(Context context, String key) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        this.context = context;
    }
}
