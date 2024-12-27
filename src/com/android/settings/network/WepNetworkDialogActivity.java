package com.android.settings.network;

import android.content.Context;
import android.net.wifi.WifiManager;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.style.TextAlign;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.wifi.ConfigureWifiSettings;
import com.android.settingslib.spa.SpaDialogWindowTypeActivity;
import com.android.settingslib.spa.widget.dialog.AlertDialogButton;
import com.android.settingslib.spa.widget.dialog.SettingsAlterDialogContentKt;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/network/WepNetworkDialogActivity;",
            "Lcom/android/settingslib/spa/SpaDialogWindowTypeActivity;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class WepNetworkDialogActivity extends SpaDialogWindowTypeActivity {
    /* JADX WARN: Type inference failed for: r3v9, types: [com.android.settings.network.WepNetworkDialogActivity$Content$3, kotlin.jvm.internal.Lambda] */
    @Override // com.android.settingslib.spa.SpaDialogWindowTypeActivity
    public final void Content(Composer composer, final int i) {
        int i2;
        AlertDialogButton alertDialogButton;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-977543158);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(this) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            final WifiManager wifiManager =
                    (WifiManager) context.getSystemService(WifiManager.class);
            String string = getString(R.string.wifi_settings_ssid_block_button_close);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            composerImpl.startReplaceGroup(205183573);
            boolean z = (i2 & 14) == 4;
            Object rememberedValue = composerImpl.rememberedValue();
            if (z || rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        new Function0() { // from class:
                                          // com.android.settings.network.WepNetworkDialogActivity$Content$1$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                WepNetworkDialogActivity.this.finish();
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            AlertDialogButton alertDialogButton2 =
                    new AlertDialogButton(string, (Function0) rememberedValue, 2);
            if (wifiManager == null || !wifiManager.isWepSupported()) {
                alertDialogButton = null;
            } else {
                String string2 = getString(R.string.wifi_settings_wep_networks_button_allow);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                alertDialogButton =
                        new AlertDialogButton(
                                string2,
                                new Function0() { // from class:
                                                  // com.android.settings.network.WepNetworkDialogActivity$Content$2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        SubSettingLauncher subSettingLauncher =
                                                new SubSettingLauncher(context);
                                        CharSequence text =
                                                context.getText(
                                                        R.string
                                                                .network_and_internet_preferences_title);
                                        SubSettingLauncher.LaunchRequest launchRequest =
                                                subSettingLauncher.mLaunchRequest;
                                        launchRequest.mTitle = text;
                                        launchRequest.mSourceMetricsCategory = FileType.XLSB;
                                        launchRequest.mDestinationName =
                                                ConfigureWifiSettings.class.getName();
                                        subSettingLauncher.launch();
                                        this.finish();
                                        return Unit.INSTANCE;
                                    }
                                },
                                2);
            }
            String string3 = getString(R.string.wifi_settings_wep_networks_blocked_title);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            String stringExtra = getIntent().getStringExtra("ssid");
            SettingsAlterDialogContentKt.SettingsAlertDialogContent(
                    alertDialogButton2,
                    alertDialogButton,
                    String.format(
                            string3,
                            Arrays.copyOf(
                                    new Object[] {stringExtra != null ? stringExtra : "ssid"}, 1)),
                    (Function2) null,
                    ComposableLambdaKt.rememberComposableLambda(
                            -248822463,
                            new Function2() { // from class:
                                              // com.android.settings.network.WepNetworkDialogActivity$Content$3
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 11) == 2) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    WifiManager wifiManager2 = wifiManager;
                                    String string4 =
                                            (wifiManager2 == null || !wifiManager2.isWepSupported())
                                                    ? this.getString(
                                                            R.string
                                                                    .wifi_settings_wep_networks_summary_blocked_by_carrier)
                                                    : this.getString(
                                                            R.string
                                                                    .wifi_settings_wep_networks_summary_toggle_off);
                                    Intrinsics.checkNotNull(string4);
                                    TextKt.m210Text4IGK_g(
                                            string4,
                                            SizeKt.FillWholeMaxWidth,
                                            0L,
                                            0L,
                                            null,
                                            null,
                                            null,
                                            0L,
                                            null,
                                            new TextAlign(3),
                                            0L,
                                            0,
                                            false,
                                            0,
                                            0,
                                            null,
                                            null,
                                            composer2,
                                            48,
                                            0,
                                            130556);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    24576,
                    8);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.WepNetworkDialogActivity$Content$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            WepNetworkDialogActivity.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
