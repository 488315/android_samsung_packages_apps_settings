package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.accessibility.rtt.TelecomUtil$$ExternalSyntheticLambda0;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RTTSettingPreferenceController extends BasePreferenceController {
    private static final String DIALER_RTT_CONFIGURATION = "dialer_rtt_configuration";
    private static final String TAG = "RTTSettingsCtr";
    private final CarrierConfigManager mCarrierConfigManager;
    private final Context mContext;
    private final String mDialerPackage;
    private final CharSequence[] mModes;
    private final PackageManager mPackageManager;
    Intent mRTTIntent;

    public RTTSettingPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
        this.mModes = context.getResources().getTextArray(R.array.rtt_setting_mode);
        String string = context.getString(R.string.config_rtt_setting_package_name);
        this.mDialerPackage = string;
        this.mPackageManager = context.getPackageManager();
        this.mCarrierConfigManager =
                (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        this.mRTTIntent =
                new Intent(context.getString(R.string.config_rtt_setting_intent_action))
                        .setPackage(string);
        Log.d(TAG, "init controller");
    }

    private boolean getBooleanCarrierConfig(String str) {
        if (this.mCarrierConfigManager == null) {
            return CarrierConfigManager.getDefaultConfig().getBoolean(str);
        }
        PersistableBundle configForSubId =
                this.mCarrierConfigManager.getConfigForSubId(
                        SubscriptionManager.getDefaultVoiceSubscriptionId());
        return configForSubId != null
                ? configForSubId.getBoolean(str)
                : CarrierConfigManager.getDefaultConfig().getBoolean(str);
    }

    private static boolean isDefaultDialerSupportedRTT(Context context) {
        return TextUtils.equals(
                context.getString(R.string.config_rtt_setting_package_name),
                ((TelecomManager)
                                context.getApplicationContext()
                                        .getSystemService(TelecomManager.class))
                        .getDefaultDialerPackage());
    }

    private boolean isRttSupportedByTelecom(PhoneAccountHandle phoneAccountHandle) {
        PhoneAccount phoneAccount =
                ((TelecomManager)
                                this.mContext
                                        .getApplicationContext()
                                        .getSystemService(TelecomManager.class))
                        .getPhoneAccount(phoneAccountHandle);
        if (phoneAccount == null || !phoneAccount.hasCapabilities(4096)) {
            return false;
        }
        Log.d(TAG, "Phone account has RTT capability.");
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        preferenceScreen.findPreference(getPreferenceKey()).setIntent(this.mRTTIntent);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        List<ResolveInfo> queryIntentActivities =
                this.mPackageManager.queryIntentActivities(this.mRTTIntent, 0);
        return (queryIntentActivities == null
                        || queryIntentActivities.isEmpty()
                        || !isRttSettingSupported())
                ? 3
                : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), DIALER_RTT_CONFIGURATION, 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "DIALER_RTT_CONFIGURATION value =  ", TAG);
        return this.mModes[i];
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public boolean isRttSettingSupported() {
        Optional empty;
        Log.d(TAG, "isRttSettingSupported [start]");
        if (!isDefaultDialerSupportedRTT(this.mContext)) {
            Log.d(TAG, "Dialer doesn't support RTT.");
            return false;
        }
        for (PhoneAccountHandle phoneAccountHandle :
                (List)
                        Optional.ofNullable(
                                        ((TelecomManager)
                                                        this.mContext
                                                                .getApplicationContext()
                                                                .getSystemService(
                                                                        TelecomManager.class))
                                                .getCallCapablePhoneAccounts())
                                .orElse(new ArrayList())) {
            Context context = this.mContext;
            if (!TextUtils.isEmpty(phoneAccountHandle.getId())) {
                List<SubscriptionInfo> activeSubscriptionInfoList =
                        ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                                .createForAllUserProfiles()
                                .getActiveSubscriptionInfoList();
                if (activeSubscriptionInfoList != null) {
                    Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            Log.d(
                                    "TelecomUtil",
                                    "Failed to find SubscriptionInfo for phoneAccountHandle");
                            empty = Optional.empty();
                            break;
                        }
                        SubscriptionInfo next = it.next();
                        if (phoneAccountHandle.getId().startsWith(next.getIccId())) {
                            empty = Optional.of(next);
                            break;
                        }
                    }
                } else {
                    empty = Optional.empty();
                }
            } else {
                empty = Optional.empty();
            }
            int intValue =
                    ((Integer) empty.map(new TelecomUtil$$ExternalSyntheticLambda0()).orElse(-1))
                            .intValue();
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    intValue, "subscription id for the device: ", TAG);
            boolean isRttSupportedByTelecom = isRttSupportedByTelecom(phoneAccountHandle);
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "rtt calling supported by telecom:: ", TAG, isRttSupportedByTelecom);
            if (isRttSupportedByTelecom) {
                if (this.mCarrierConfigManager.getConfigForSubId(intValue) != null
                        && getBooleanCarrierConfig("ignore_rtt_mode_setting_bool")) {
                    Log.d(TAG, "RTT visibility setting is supported.");
                    return true;
                }
                Log.d(TAG, "IGNORE_RTT_MODE_SETTING_BOOL is false.");
            }
        }
        Log.d(TAG, "isRttSettingSupported [Not support]");
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
