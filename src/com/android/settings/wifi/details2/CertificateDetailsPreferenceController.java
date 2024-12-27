package com.android.settings.wifi.details2;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.http.SslCertificate;
import android.security.KeyChain;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.spa.preference.ComposePreferenceController;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000<\n"
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
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0004\b\u0007\u0018\u0000"
                + " \u00172\u00020\u0001:\u0001\u0017B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r"
                + "\u0010\t\u001a\u00020\n"
                + "H\u0007¢\u0006\u0002\u0010\u000bJ\r"
                + "\u0010\f\u001a\u00020\n"
                + "H\u0017¢\u0006\u0002\u0010\u000bJ\u0018\u0010\r"
                + "\u001a\u00020\n"
                + "2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\u0015\u001a\u00020\n"
                + "2\u0006\u0010\u0016\u001a\u00020\bR\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0018"
        },
        d2 = {
            "Lcom/android/settings/wifi/details2/CertificateDetailsPreferenceController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "wifiEntry",
            "Lcom/android/wifitrackerlib/WifiEntry;",
            "CertificateDetails",
            ApnSettings.MVNO_NONE,
            "(Landroidx/compose/runtime/Composer;I)V",
            "Content",
            "createCertificateDetailsDialog",
            "certX509",
            "Ljava/security/cert/X509Certificate;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getCertX509",
            "isCertificateDetailsAvailable",
            ApnSettings.MVNO_NONE,
            "setWifiEntry",
            "entry",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class CertificateDetailsPreferenceController extends ComposePreferenceController {
    public static final int $stable = 8;
    public static final String TAG = "CertificateDetailsPreferenceController";
    private WifiEntry wifiEntry;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CertificateDetailsPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void createCertificateDetailsDialog(Context context, X509Certificate certX509) {
        CertificateDetailsPreferenceController$createCertificateDetailsDialog$listener$1
                certificateDetailsPreferenceController$createCertificateDetailsDialog$listener$1 =
                        CertificateDetailsPreferenceController$createCertificateDetailsDialog$listener$1
                                .INSTANCE;
        ArrayList arrayList = new ArrayList();
        SslCertificate sslCertificate = new SslCertificate(certX509);
        arrayList.add(sslCertificate.getIssuedTo().getCName());
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(context, R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        Spinner spinner = new Spinner(context);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setClipChildren(true);
        linearLayout.addView(spinner);
        View inflateCertificateView = sslCertificate.inflateCertificateView(context);
        inflateCertificateView.setVisibility(0);
        linearLayout.addView(inflateCertificateView);
        linearLayout.setVisibility(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(linearLayout);
        builder.setTitle(17043110);
        builder.setPositiveButton(
                com.android.settings.R.string.wifi_settings_ssid_block_button_close,
                (DialogInterface.OnClickListener) null);
        builder.setNegativeButton(
                com.android.settings.R.string.trusted_credentials_remove_label,
                certificateDetailsPreferenceController$createCertificateDetailsDialog$listener$1);
        AlertDialog create = builder.create();
        create.show();
        create.getButton(-2).setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final X509Certificate getCertX509(WifiEntry wifiEntry) {
        String[] strArr;
        WifiEntry.CertificateInfo certificateInfo = wifiEntry.getCertificateInfo();
        String str =
                (certificateInfo == null || (strArr = certificateInfo.caCertificateAliases) == null)
                        ? null
                        : strArr[0];
        if (str == null) {
            return null;
        }
        try {
            KeyStore keyStore = KeyStore.getInstance(CertProvisionProfile.PROVIDER_ANDROID);
            keyStore.load(new AndroidKeyStoreLoadStoreParameter(102));
            return KeyChain.toCertificate(keyStore.getCertificate(str).getEncoded());
        } catch (Exception e) {
            Log.e(TAG, "Failed to open Android Keystore.", e);
            return null;
        }
    }

    private final boolean isCertificateDetailsAvailable(WifiEntry wifiEntry) {
        WifiEntry.CertificateInfo certificateInfo = wifiEntry.getCertificateInfo();
        return CollectionsKt___CollectionsKt.contains(
                CollectionsKt__CollectionsKt.listOf((Object[]) new Integer[] {2, 1, 3}),
                certificateInfo != null ? Integer.valueOf(certificateInfo.validationMethod) : null);
    }

    public final void CertificateDetails(Composer composer, final int i) {
        String format;
        String[] strArr;
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-856025535);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        WifiEntry wifiEntry = this.wifiEntry;
        Integer num = null;
        if (wifiEntry == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wifiEntry");
            throw null;
        }
        WifiEntry.CertificateInfo certificateInfo = wifiEntry.getCertificateInfo();
        Intrinsics.checkNotNull(certificateInfo);
        final int i2 = certificateInfo.validationMethod;
        if (i2 == 1) {
            composerImpl.startReplaceGroup(-1787137477);
            WifiEntry wifiEntry2 = this.wifiEntry;
            if (wifiEntry2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("wifiEntry");
                throw null;
            }
            WifiEntry.CertificateInfo certificateInfo2 = wifiEntry2.getCertificateInfo();
            if (certificateInfo2 != null
                    && (strArr = certificateInfo2.caCertificateAliases) != null) {
                num = Integer.valueOf(strArr.length);
            }
            if (num != null && num.intValue() == 1) {
                composerImpl.startReplaceGroup(-1787137371);
                format =
                        StringResources_androidKt.stringResource(
                                composerImpl, com.android.settings.R.string.one_cacrt);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-1787137288);
                format =
                        String.format(
                                StringResources_androidKt.stringResource(
                                        composerImpl,
                                        com.android.settings.R.string
                                                .wifi_certificate_summary_Certificates),
                                Arrays.copyOf(new Object[] {num}, 1));
                composerImpl.end(false);
            }
            composerImpl.end(false);
        } else if (i2 != 2) {
            composerImpl.startReplaceGroup(-1787137111);
            format =
                    StringResources_androidKt.stringResource(
                            composerImpl,
                            com.android.settings.R.string.wifi_certificate_summary_pinning);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(-1787137625);
            format =
                    StringResources_androidKt.stringResource(
                            composerImpl,
                            com.android.settings.R.string.wifi_certificate_summary_system);
            composerImpl.end(false);
        }
        final String str = format;
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        str,
                        i2,
                        this,
                        context) { // from class:
                                   // com.android.settings.wifi.details2.CertificateDetailsPreferenceController$CertificateDetails$1
                    public final Function0 onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(composerImpl, 17043110);
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.wifi.details2.CertificateDetailsPreferenceController$CertificateDetails$1$summary$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return str;
                                    }
                                };
                        this.onClick =
                                new Function0() { // from class:
                                                  // com.android.settings.wifi.details2.CertificateDetailsPreferenceController$CertificateDetails$1$onClick$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        WifiEntry wifiEntry3;
                                        X509Certificate certX509;
                                        if (i2 == 1) {
                                            CertificateDetailsPreferenceController
                                                    certificateDetailsPreferenceController = this;
                                            wifiEntry3 =
                                                    certificateDetailsPreferenceController
                                                            .wifiEntry;
                                            if (wifiEntry3 == null) {
                                                Intrinsics
                                                        .throwUninitializedPropertyAccessException(
                                                                "wifiEntry");
                                                throw null;
                                            }
                                            certX509 =
                                                    certificateDetailsPreferenceController
                                                            .getCertX509(wifiEntry3);
                                            if (certX509 != null) {
                                                this.createCertificateDetailsDialog(
                                                        context, certX509);
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getOnClick() {
                        return this.onClick;
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
                                      // com.android.settings.wifi.details2.CertificateDetailsPreferenceController$CertificateDetails$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            CertificateDetailsPreferenceController.this.CertificateDetails(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public void Content(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-277219249);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        CertificateDetails(composerImpl, 8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.wifi.details2.CertificateDetailsPreferenceController$Content$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            CertificateDetailsPreferenceController.this.Content(
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
            return isCertificateDetailsAvailable(wifiEntry) ? 0 : 2;
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
