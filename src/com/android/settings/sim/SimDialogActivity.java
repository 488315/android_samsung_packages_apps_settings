package com.android.settings.sim;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsException;
import android.telephony.ims.ImsManager;
import android.telephony.ims.ImsMmTelManager;
import android.util.Log;
import android.widget.Toast;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.network.CarrierConfigCache;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.ims.WifiCallingQueryImsState;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SimDialogActivity extends FragmentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;

    public final SimDialogFragment createFragment(int i) {
        if (i == 0) {
            if (SubscriptionManager.getDefaultDataSubscriptionId() == -1) {
                return SimListDialogFragment.newInstance(
                        0, R.string.select_sim_for_data, false, true);
            }
            SelectSpecificDataSimDialogFragment selectSpecificDataSimDialogFragment =
                    new SelectSpecificDataSimDialogFragment();
            selectSpecificDataSimDialogFragment.setArguments(
                    SimDialogFragment.initArguments(
                            0, R.string.select_specific_sim_for_data_title));
            return selectSpecificDataSimDialogFragment;
        }
        if (i == 1) {
            return SimListDialogFragment.newInstance(i, R.string.select_sim_for_calls, true, false);
        }
        if (i == 2) {
            return SimListDialogFragment.newInstance(i, R.string.select_sim_for_sms, true, false);
        }
        if (i == 3) {
            if (!getIntent().hasExtra("preferred_sim")) {
                throw new IllegalArgumentException("Missing required extra preferred_sim");
            }
            PreferredSimDialogFragment preferredSimDialogFragment =
                    new PreferredSimDialogFragment();
            preferredSimDialogFragment.setArguments(
                    SimDialogFragment.initArguments(3, R.string.sim_preferred_title));
            return preferredSimDialogFragment;
        }
        if (i == 4) {
            return SimListDialogFragment.newInstance(i, R.string.select_sim_for_sms, false, false);
        }
        if (i != 6) {
            throw new IllegalArgumentException(
                    LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                            i, "Invalid dialog type ", " sent."));
        }
        EnableAutoDataSwitchDialogFragment enableAutoDataSwitchDialogFragment =
                new EnableAutoDataSwitchDialogFragment();
        enableAutoDataSwitchDialogFragment.setArguments(
                SimDialogFragment.initArguments(6, R.string.enable_auto_data_switch_dialog_title));
        return enableAutoDataSwitchDialogFragment;
    }

    public final void forceClose() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        Log.d("SimDialogActivity", "Dismissed by Service");
        finishAndRemoveTask();
    }

    @VisibleForTesting
    public boolean isUiRestricted() {
        if (!MobileNetworkUtils.isMobileNetworkUserRestricted(getApplicationContext())) {
            return false;
        }
        Log.e("SimDialogActivity", "This setting isn't available due to user restriction.");
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isUiRestricted()) {
            finish();
            return;
        }
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        if (!getResources().getBoolean(R.bool.config_show_sim_info)) {
            Log.d("SimDialogActivity", "Not support on device without SIM.");
            finish();
            return;
        }
        SimDialogProhibitService.sSimDialogActivity = new WeakReference(this);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        showOrUpdateDialog();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        showOrUpdateDialog();
    }

    public final void onSubscriptionSelected(int i, int i2) {
        if (getSupportFragmentManager().findFragmentByTag(Integer.toString(i)) == null) {
            Log.w(
                    "SimDialogActivity",
                    "onSubscriptionSelected ignored because stored fragment was null");
            return;
        }
        if (i == 0) {
            setDefaultDataSubId(i2);
            return;
        }
        if (i == 1) {
            setDefaultCallsSubId(i2);
            return;
        }
        if (i == 2) {
            ((SubscriptionManager) getSystemService(SubscriptionManager.class))
                    .setDefaultSmsSubId(i2);
            return;
        }
        if (i == 3) {
            setDefaultDataSubId(i2);
            ((SubscriptionManager) getSystemService(SubscriptionManager.class))
                    .setDefaultSmsSubId(i2);
            setDefaultCallsSubId(i2);
        } else if (i == 4) {
            Intent intent = new Intent();
            intent.putExtra("result_sub_id", i2);
            setResult(-1, intent);
        } else {
            if (i != 6) {
                throw new IllegalArgumentException(
                        LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                                i, "Invalid dialog type ", " sent."));
            }
            Log.d("SimDialogActivity", "onEnableAutoDataSwitch subId:" + i2);
            ((TelephonyManager) getSystemService(TelephonyManager.class))
                    .createForSubscriptionId(i2)
                    .setMobileDataPolicyEnabled(3, true);
            if (getResources()
                    .getBoolean(R.bool.config_auto_data_switch_enables_cross_sim_calling)) {
                trySetCrossSimCalling(
                        true,
                        ((SubscriptionManager) getSystemService(SubscriptionManager.class))
                                .getActiveSubscriptionIdList());
            }
        }
    }

    public final void setDefaultCallsSubId(int i) {
        PhoneAccountHandle phoneAccountHandle;
        TelecomManager telecomManager = (TelecomManager) getSystemService(TelecomManager.class);
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(TelephonyManager.class);
        Iterator<PhoneAccountHandle> it = telecomManager.getCallCapablePhoneAccounts().iterator();
        while (true) {
            if (!it.hasNext()) {
                phoneAccountHandle = null;
                break;
            } else {
                phoneAccountHandle = it.next();
                if (i == telephonyManager.getSubscriptionId(phoneAccountHandle)) {
                    break;
                }
            }
        }
        ((TelecomManager) getSystemService(TelecomManager.class))
                .setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
    }

    public final void setDefaultDataSubId(int i) {
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) getSystemService(SubscriptionManager.class);
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(i);
        subscriptionManager.setDefaultDataSubId(i);
        if (i != -1) {
            Log.d("SimDialogActivity", "setDataEnabledForReason true");
            createForSubscriptionId.setDataEnabledForReason(0, true);
            Toast.makeText(this, R.string.data_switch_started, 1).show();
        }
    }

    public final void showEnableAutoDataSwitchDialog() {
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        SimDialogFragment createFragment = createFragment(6);
        if (supportFragmentManager.isStateSaved()) {
            Log.w(
                    "SimDialogActivity",
                    "Failed to show EnableAutoDataSwitchDialog. The fragmentManager is"
                        + " StateSaved.");
            forceClose();
            return;
        }
        try {
            createFragment.show(supportFragmentManager, Integer.toString(6));
            if (getResources()
                    .getBoolean(R.bool.config_auto_data_switch_enables_cross_sim_calling)) {
                int[] activeSubscriptionIdList =
                        ((SubscriptionManager) getSystemService(SubscriptionManager.class))
                                .getActiveSubscriptionIdList();
                int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
                boolean z = true;
                if (activeSubscriptionIdList.length > 1) {
                    int length = activeSubscriptionIdList.length;
                    int i = 0;
                    while (true) {
                        if (i < length) {
                            int i2 = activeSubscriptionIdList[i];
                            if (i2 != defaultDataSubscriptionId
                                    && ((TelephonyManager) getSystemService(TelephonyManager.class))
                                            .createForSubscriptionId(i2)
                                            .isMobileDataPolicyEnabled(3)) {
                                break;
                            } else {
                                i++;
                            }
                        } else {
                            z = false;
                            break;
                        }
                    }
                    trySetCrossSimCalling(z, activeSubscriptionIdList);
                }
            }
        } catch (Exception e) {
            Log.e("SimDialogActivity", "Failed to show EnableAutoDataSwitchDialog.", e);
            forceClose();
        }
    }

    public final void showOrUpdateDialog() {
        int intExtra = getIntent().getIntExtra("dialog_type", -1);
        if (intExtra == 5 || intExtra == -1) {
            finishAndRemoveTask();
            return;
        }
        if (intExtra == 3
                && getSharedPreferences("sim_action_dialog_prefs", 0).getInt("progress_state", 0)
                        == 1) {
            Log.d(
                    "SimDialogActivity",
                    "Finish the sim dialog since the sim action dialog is showing the progress");
            finish();
            return;
        }
        if (intExtra == 0 || intExtra == 1 || intExtra == 2) {
            Log.d("SimDialogActivity", "Finish the sim dialog since the sim onboarding is shown");
            finish();
            return;
        }
        String num = Integer.toString(intExtra);
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        SimDialogFragment simDialogFragment =
                (SimDialogFragment) supportFragmentManager.findFragmentByTag(num);
        if (simDialogFragment == null) {
            createFragment(intExtra).show(supportFragmentManager, num);
        } else {
            simDialogFragment.updateDialog();
        }
    }

    public final void trySetCrossSimCalling(boolean z, int[] iArr) {
        PersistableBundle configForSubId;
        this.mMetricsFeatureProvider.action(this, 1825, z);
        for (int i : iArr) {
            if (new WifiCallingQueryImsState(this, i).isWifiCallingSupported()) {
                ImsMmTelManager imsMmTelManager = null;
                if (SubscriptionManager.isValidSubscriptionId(i)) {
                    CarrierConfigCache.getInstance(this).getClass();
                    configForSubId = CarrierConfigCache.getConfigForSubId(i);
                } else {
                    configForSubId = null;
                }
                if (configForSubId != null
                        && configForSubId.getBoolean(
                                "carrier_cross_sim_ims_available_bool", false)) {
                    try {
                        ImsManager imsManager = (ImsManager) getSystemService(ImsManager.class);
                        if (imsManager != null) {
                            imsMmTelManager = imsManager.getImsMmTelManager(i);
                        }
                        imsMmTelManager.setCrossSimCallingEnabled(z);
                    } catch (ImsException | IllegalArgumentException | NullPointerException e) {
                        Log.w(
                                "SimDialogActivity",
                                "failed to change cross SIM calling configuration to "
                                        + z
                                        + " for subID "
                                        + i
                                        + "with exception: ",
                                e);
                    }
                }
            }
        }
    }
}
