package com.samsung.android.settings.datausage;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.internal.logging.MetricsLogger;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.network.MobileDataEnabledListener;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecMobileDataPreferenceController extends TogglePreferenceController
        implements MobileDataEnabledListener.Client,
                LifecycleObserver,
                OnStart,
                OnStop,
                OnResume,
                OnPause {
    private static final String AIRPLANE_MODE = "airplane_mode";
    private static final String NO_SIM_CARD = "no_sim_card";
    private static final String TAG = "SecMobileDataPreferenceController";
    private static final String WIFI_ONLY_MODEL = "wifi_only_model";
    private EnterpriseDeviceManager mEDM;
    private MobileDataEnabledListener mListener;
    private SecSwitchPreference mSecSwitchPreference;
    private int mSubscriptionId;
    private boolean mSupportDisableDialog;
    private boolean mSupportEnableDialog;
    private PhoneCallStateTelephonyCallback mTelephonyCallback;
    private TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.datausage.SecMobileDataPreferenceController$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass2(int i) {
            this.$r8$classId = i;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    dialogInterface.dismiss();
                    break;
                default:
                    dialogInterface.dismiss();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PhoneCallStateTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.CallStateListener {
        public PhoneCallStateTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            Log.i(SecMobileDataPreferenceController.TAG, "onCallStateChanged");
            SecMobileDataPreferenceController secMobileDataPreferenceController =
                    SecMobileDataPreferenceController.this;
            secMobileDataPreferenceController.updateState(
                    secMobileDataPreferenceController.mSecSwitchPreference);
        }
    }

    public SecMobileDataPreferenceController(Context context, String str) {
        super(context, str);
        this.mEDM = null;
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        this.mSubscriptionId = telephonyManager.getSubscriptionId();
        if (this.mTelephonyCallback == null) {
            this.mTelephonyCallback = new PhoneCallStateTelephonyCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableDeviceProvisionMobileData() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (contentResolver == null
                || Settings.Global.getInt(contentResolver, "device_provisioned", 0) != 0) {
            return;
        }
        Settings.Global.putInt(contentResolver, "device_provisioning_mobile_data", 1);
    }

    private void showDisableDialog() {
        View inflate =
                ((LayoutInflater) this.mContext.getSystemService("layout_inflater"))
                        .inflate(R.layout.sec_mobile_data_dont_show_layout, (ViewGroup) null);
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.do_not_show_again);
        TextView textView = (TextView) inflate.findViewById(R.id.dialog_message_text_view);
        checkBox.setOnClickListener(new AnonymousClass3());
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.P.mTitle = null;
        builder.setPositiveButton(
                R.string.data_usage_turn_off_btn,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.datausage.SecMobileDataPreferenceController.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        if (Rune.isAllNAVendor()) {
                            Settings.System.putInt(
                                    ((AbstractPreferenceController)
                                                    SecMobileDataPreferenceController.this)
                                            .mContext.getContentResolver(),
                                    "mobile_data_off_popup_show_again",
                                    checkBox.isChecked() ? 1 : 0);
                        }
                        SecMobileDataPreferenceController.this.mTelephonyManager.setDataEnabled(
                                false);
                        SecMobileDataPreferenceController.this.mSecSwitchPreference.setChecked(
                                false);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new AnonymousClass2(1));
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        String salesCode = Utils.getSalesCode();
        builder.setTitle(R.string.mobile_data_off_dialog_title);
        if (Rune.isDomesticModel() || Rune.isJapanModel()) {
            builder.setTitle(R.string.data_usage_enable_mobile);
        }
        if (Rune.isDomesticSKTModel() || "KOO".equals(Utils.getSalesCode())) {
            builder.setMessage(R.string.data_usage_disable_mobile_warning_skt);
        } else if (Rune.isDomesticKTTModel()) {
            builder.setMessage(R.string.data_usage_disable_mobile_warning_ktt);
        } else if (Rune.isDomesticLGTModel()) {
            builder.setMessage(R.string.data_usage_disable_mobile_warning_lgt);
        } else if (SemCscFeature.getInstance()
                .getBoolean("CscFeature_Setting_EnablePromptPopupWhenActivatingDataConnection")) {
            builder.setMessage(R.string.data_usage_disable_mobile);
        } else if ("DCM".equals(salesCode)) {
            builder.setMessage(R.string.data_usage_disable_mobile_warning_dcm);
        } else if (Rune.isAllNAVendor()) {
            builder.setView(inflate);
            textView.setText(R.string.mobile_data_off_dialog_text_vzw);
            checkBox.setVisibility(0);
        } else {
            builder.setMessage(R.string.mobile_data_off_dialog_text_common);
        }
        builder.create().show();
    }

    private void showEnableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(R.string.data_usage_enable_mobile);
        builder.setPositiveButton(
                android.R.string.ok,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.datausage.SecMobileDataPreferenceController.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SecMobileDataPreferenceController.this.enableDeviceProvisionMobileData();
                        SecMobileDataPreferenceController.this.mTelephonyManager.setDataEnabled(
                                true);
                        SecMobileDataPreferenceController.this.mSecSwitchPreference.setChecked(
                                true);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new AnonymousClass2(0));
        if (Rune.isDomesticSKTModel() || "KOO".equals(Utils.getSalesCode())) {
            builder.setMessage(R.string.data_usage_enable_mobile_warning_skt);
        } else if (Rune.isDomesticKTTModel()) {
            builder.setMessage(R.string.data_usage_enable_mobile_warning_ktt);
        } else if (Rune.isDomesticLGTModel()) {
            builder.setMessage(R.string.data_usage_enable_mobile_warning_lgt);
        } else {
            builder.setMessage(R.string.data_usage_enable_mobile_warning);
        }
        builder.create().show();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSecSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean z =
                Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0)
                        == 1;
        boolean isAdminUser = ((UserManager) this.mContext.getSystemService("user")).isAdminUser();
        if (!DataUsageUtils.hasMobile(this.mContext)
                || DataUsageUtils.isSupportATTMobileDataAndRoaming()
                || !isAdminUser
                || ConnectionsUtils.isOverseaArea(this.mContext)) {
            if (!DataUsageUtils.hasActiveSim(this.mContext)) {
                setStatusCode(NO_SIM_CARD);
            }
            Log.i(
                    TAG,
                    "hasMobile : "
                            + DataUsageUtils.hasMobile(this.mContext)
                            + ", isAtt : "
                            + DataUsageUtils.isSupportATTMobileDataAndRoaming()
                            + ", isAdmin : "
                            + isAdminUser
                            + ", isOversea : "
                            + ConnectionsUtils.isOverseaArea(this.mContext));
            return 2;
        }
        if (com.android.settingslib.Utils.isWifiOnly(this.mContext)) {
            setStatusCode(WIFI_ONLY_MODEL);
            Log.i(TAG, "Wifi Only Model");
            return 3;
        }
        if (z) {
            setStatusCode(AIRPLANE_MODE);
            Log.i(TAG, "Airplane Mode On");
            return 5;
        }
        if (Rune.isVzwConceptModel() && Utils.isOnCall(this.mContext)) {
            Log.i(TAG, "Vzw model and on Call");
            return 5;
        }
        TooltipPopup$$ExternalSyntheticOutline0.m(
                new StringBuilder("mSubscriptionId : "), this.mSubscriptionId, TAG);
        return this.mSubscriptionId != -1 ? 0 : 2;
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        Intent intent = new Intent();
        intent.setAction("android.settings.DATA_USAGE_SETTINGS");
        intent.addFlags(268468224);
        return intent;
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
        return this.mTelephonyManager.isDataEnabled();
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

    @Override // com.android.settings.network.MobileDataEnabledListener.Client
    public void onMobileDataEnabledChange() {
        SecSwitchPreference secSwitchPreference = this.mSecSwitchPreference;
        if (secSwitchPreference != null) {
            updateState(secSwitchPreference);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mTelephonyCallback != null) {
            Log.i(TAG, "unregister");
            PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback =
                    this.mTelephonyCallback;
            if (SecMobileDataPreferenceController.this.mTelephonyManager != null) {
                SecMobileDataPreferenceController.this.mTelephonyManager
                        .unregisterTelephonyCallback(phoneCallStateTelephonyCallback);
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mTelephonyCallback != null) {
            Log.i(TAG, "register");
            PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback =
                    this.mTelephonyCallback;
            SecMobileDataPreferenceController.this.mTelephonyManager.registerTelephonyCallback(
                    this.mContext.getMainExecutor(), phoneCallStateTelephonyCallback);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (this.mListener == null) {
            this.mListener = new MobileDataEnabledListener(this.mContext, this);
        }
        this.mListener.start(SubscriptionManager.getDefaultDataSubscriptionId());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mListener.stop();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("set Mobile Data : ", TAG, z);
        MetricsLogger.action(this.mContext, 178, z);
        LoggingHelper.insertEventLogging(37, 3502, z ? 1L : 0L);
        int i = ConnectionsUtils.$r8$clinit;
        this.mSupportEnableDialog =
                Rune.isDomesticModel()
                        || SemCscFeature.getInstance()
                                .getBoolean(
                                        "CscFeature_Setting_EnablePromptPopupWhenActivatingDataConnection");
        this.mSupportDisableDialog =
                !(Rune.isAllNAVendor()
                                && (Settings.System.getInt(
                                                this.mContext.getContentResolver(),
                                                "mobile_data_off_popup_show_again",
                                                0)
                                        != 0))
                        && SemCscFeature.getInstance()
                                .getBoolean("CscFeature_VoiceCall_SupportPopupForDataOff");
        StringBuilder sb = new StringBuilder("Enable Dialog : ");
        sb.append(this.mSupportEnableDialog);
        sb.append(" , Disable Dialog : ");
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, this.mSupportDisableDialog, TAG);
        if (z) {
            if (!this.mSupportEnableDialog || this.mSecSwitchPreference == null) {
                enableDeviceProvisionMobileData();
                this.mTelephonyManager.setDataEnabled(z);
                return true;
            }
            showEnableDialog();
        } else {
            if (!this.mSupportDisableDialog || this.mSecSwitchPreference == null) {
                this.mTelephonyManager.setDataEnabled(z);
                return true;
            }
            showDisableDialog();
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        boolean z;
        boolean z2;
        boolean z3;
        super.updateState(preference);
        if (this.mSecSwitchPreference == null || !isAvailable()) {
            return;
        }
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        int currentUser = ActivityManager.getCurrentUser();
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager != null) {
            z2 =
                    !enterpriseDeviceManager
                            .getPhoneRestrictionPolicy()
                            .checkEnableUseOfPacketData(false);
            z3 = !DataUsageUtils.isDataAllowed(this.mContext);
            z =
                    userManager.hasUserRestriction(
                            "no_config_mobile_networks", UserHandle.of(currentUser));
        } else {
            z = false;
            z2 = false;
            z3 = false;
        }
        if (ConnectionsUtils.isAirplaneModeEnabled(this.mContext)
                || z2
                || z3
                || z
                || (Rune.isVzwConceptModel() && Utils.isOnCall(this.mContext))) {
            this.mSecSwitchPreference.setEnabled(false);
            this.mSecSwitchPreference.setChecked(getThreadEnabled());
        } else {
            this.mSecSwitchPreference.setEnabled(true);
            this.mSecSwitchPreference.setChecked(getThreadEnabled());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.datausage.SecMobileDataPreferenceController$3, reason: invalid class name */
    public final class AnonymousClass3 implements View.OnClickListener {
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {}
    }
}
