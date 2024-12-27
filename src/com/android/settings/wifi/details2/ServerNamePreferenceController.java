package com.android.settings.wifi.details2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settings.spa.preference.ComposePreferenceController;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000.\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r"
                + "\u0010\t\u001a\u00020\n"
                + "H\u0017¢\u0006\u0002\u0010\u000bJ\r"
                + "\u0010\f\u001a\u00020\n"
                + "H\u0007¢\u0006\u0002\u0010\u000bJ\b\u0010\r"
                + "\u001a\u00020\u000eH\u0016J\u000e\u0010\u000f\u001a\u00020\n"
                + "2\u0006\u0010\u0010\u001a\u00020\bR\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0011"
        },
        d2 = {
            "Lcom/android/settings/wifi/details2/ServerNamePreferenceController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "wifiEntry",
            "Lcom/android/wifitrackerlib/WifiEntry;",
            "Content",
            ApnSettings.MVNO_NONE,
            "(Landroidx/compose/runtime/Composer;I)V",
            "ServerName",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "setWifiEntry",
            "entry",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class ServerNamePreferenceController extends ComposePreferenceController {
    public static final int $stable = 8;
    private WifiEntry wifiEntry;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ServerNamePreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public void Content(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(864580143);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        ServerName(composerImpl, 8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.wifi.details2.ServerNamePreferenceController$Content$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ServerNamePreferenceController.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void ServerName(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-630663234);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        this) { // from class:
                                // com.android.settings.wifi.details2.ServerNamePreferenceController$ServerName$1
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.server_name_title);
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.wifi.details2.ServerNamePreferenceController$ServerName$1$summary$1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        WifiEntry wifiEntry;
                                        String str;
                                        wifiEntry = ServerNamePreferenceController.this.wifiEntry;
                                        if (wifiEntry != null) {
                                            WifiEntry.CertificateInfo certificateInfo =
                                                    wifiEntry.getCertificateInfo();
                                            return (certificateInfo == null
                                                            || (str = certificateInfo.domain)
                                                                    == null)
                                                    ? ApnSettings.MVNO_NONE
                                                    : str;
                                        }
                                        Intrinsics.throwUninitializedPropertyAccessException(
                                                "wifiEntry");
                                        throw null;
                                    }
                                };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummary() {
                        return this.summary;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                },
                false,
                composerImpl,
                0,
                2);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.wifi.details2.ServerNamePreferenceController$ServerName$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ServerNamePreferenceController.this.ServerName(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        WifiEntry wifiEntry = this.wifiEntry;
        if (wifiEntry != null) {
            WifiEntry.CertificateInfo certificateInfo = wifiEntry.getCertificateInfo();
            return (certificateInfo != null ? certificateInfo.domain : null) != null ? 0 : 2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("wifiEntry");
        throw null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    public final void setWifiEntry(WifiEntry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        this.wifiEntry = entry;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
