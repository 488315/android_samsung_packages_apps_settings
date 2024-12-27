package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.core.TogglePreferenceController;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000R\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0004\b\u0007\u0018\u0000"
                + " \"2\u00020\u0001:\u0001\"B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020"
                + "\t\u0012\u0006\u0010\n"
                + "\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\n"
                + "\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010"
                + " \u001a\u00020\u001fH\u0016J\u0010\u0010!\u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0013\u0010\r"
                + "\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\n"
                + "\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006#"
        },
        d2 = {
            "Lcom/samsung/android/settings/wifi/details/WifiDhcpNameSharingPreferenceController;",
            "Lcom/android/settings/core/TogglePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "wifiEntry",
            "Lcom/android/wifitrackerlib/WifiEntry;",
            "fragment",
            "Landroidx/fragment/app/Fragment;",
            "wifiManager",
            "Landroid/net/wifi/WifiManager;",
            "screenId",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Lcom/android/wifitrackerlib/WifiEntry;Landroidx/fragment/app/Fragment;Landroid/net/wifi/WifiManager;Ljava/lang/String;)V",
            "entry",
            "getEntry",
            "()Lcom/android/wifitrackerlib/WifiEntry;",
            "preference",
            "Landroidx/preference/SwitchPreferenceCompat;",
            "getPreference",
            "()Landroidx/preference/SwitchPreferenceCompat;",
            "setPreference",
            "(Landroidx/preference/SwitchPreferenceCompat;)V",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getConfig",
            "Landroid/net/wifi/WifiConfiguration;",
            "isChecked",
            ApnSettings.MVNO_NONE,
            "isControllable",
            "setChecked",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class WifiDhcpNameSharingPreferenceController extends TogglePreferenceController {
    public static final int $stable = 8;
    private static final String KEY_DHCP_NAME_SHARING = "dhcp_name_sharing";
    private final Context context;
    private final WifiEntry entry;
    private final Fragment fragment;
    private SwitchPreferenceCompat preference;
    private final String screenId;
    private final WifiEntry wifiEntry;
    private final WifiManager wifiManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiDhcpNameSharingPreferenceController(
            Context context,
            WifiEntry wifiEntry,
            Fragment fragment,
            WifiManager wifiManager,
            String screenId) {
        super(context, KEY_DHCP_NAME_SHARING);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(wifiManager, "wifiManager");
        Intrinsics.checkNotNullParameter(screenId, "screenId");
        this.context = context;
        this.wifiEntry = wifiEntry;
        this.fragment = fragment;
        this.wifiManager = wifiManager;
        this.screenId = screenId;
        this.entry = wifiEntry;
    }

    private final WifiConfiguration getConfig() {
        WifiEntry wifiEntry = this.entry;
        if (wifiEntry != null) {
            return wifiEntry.getWifiConfiguration();
        }
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.preference = (SwitchPreferenceCompat) screen.findPreference(KEY_DHCP_NAME_SHARING);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public final WifiEntry getEntry() {
        return this.entry;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public final SwitchPreferenceCompat getPreference() {
        return this.preference;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        WifiConfiguration config = getConfig();
        if (config != null) {
            return config.isSendDhcpHostnameEnabled();
        }
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean isChecked) {
        WifiConfiguration config = getConfig();
        if (config == null) {
            return true;
        }
        config.setSendDhcpHostnameEnabled(isChecked);
        this.wifiManager.save(config, null);
        WifiEntry wifiEntry = this.wifiEntry;
        if (wifiEntry == null || wifiEntry.getConnectedState() != 2) {
            return true;
        }
        Context context = this.context;
        String packageName = context.getPackageName();
        ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                .reportIssue(
                        100,
                        WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                -1,
                                "disconnect",
                                context.getPackageManager().getNameForUid(context.getUserId()),
                                packageName));
        this.wifiManager.disconnect();
        FragmentActivity activity = this.fragment.getActivity();
        if (activity == null) {
            return true;
        }
        activity.finish();
        return true;
    }

    public final void setPreference(SwitchPreferenceCompat switchPreferenceCompat) {
        this.preference = switchPreferenceCompat;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
