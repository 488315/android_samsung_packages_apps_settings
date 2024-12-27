package com.android.settings.users;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutoSyncDataPreferenceController extends AbstractPreferenceController
        implements LifecycleObserver,
                OnPause,
                OnResume,
                PreferenceControllerMixin,
                Preference.OnPreferenceChangeListener {
    public final AutoSyncBroadcastReceiver mAutoSyncBroadcastReceiver;
    public final PreferenceFragmentCompat mParentFragment;
    public SecSwitchPreference mSwitchPreference;
    public UserHandle mUserHandle;
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutoSyncBroadcastReceiver extends BroadcastReceiver {
        public AutoSyncBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.v("AutoSyncDataController", "Received broadcast: " + action);
            if (!action.equals(ContentResolver.ACTION_SYNC_CONN_STATUS_CHANGED.getAction())
                    && !action.equals(
                            "com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS")) {
                Log.w(
                        "AutoSyncDataController",
                        "Cannot handle received broadcast: " + intent.getAction());
                return;
            }
            AutoSyncDataPreferenceController autoSyncDataPreferenceController =
                    AutoSyncDataPreferenceController.this;
            if (autoSyncDataPreferenceController.mUserHandle == null) {
                autoSyncDataPreferenceController.mUserHandle = Process.myUserHandle();
            }
            AutoSyncDataPreferenceController autoSyncDataPreferenceController2 =
                    AutoSyncDataPreferenceController.this;
            SecSwitchPreference secSwitchPreference =
                    autoSyncDataPreferenceController2.mSwitchPreference;
            if (secSwitchPreference != null) {
                secSwitchPreference.setChecked(
                        ContentResolver.getMasterSyncAutomaticallyAsUser(
                                autoSyncDataPreferenceController2.mUserHandle.getIdentifier()));
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConfirmAutoSyncChangeFragment extends InstrumentedDialogFragment
            implements DialogInterface.OnClickListener {
        public boolean mChangeState = false;
        public AlertDialog mDialog;
        public PreferenceFragmentCompat mFragment;
        public SecSwitchPreference mPreference;

        public static void updateDialogAnchorView$4(Dialog dialog, Preference preference) {
            Log.d("AutoSyncDataController", "updateDialogAnchorView ");
            if (preference == null || dialog == null) {
                return;
            }
            Rect rect = new Rect();
            preference.seslGetPreferenceBounds(rect);
            int width = (rect.width() / 2) + rect.left;
            int i = rect.bottom;
            if (width <= 0 || i <= 0) {
                return;
            }
            dialog.semSetAnchor(width, i);
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 535;
        }

        @Override // androidx.fragment.app.DialogFragment,
                  // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            SecSwitchPreference secSwitchPreference = this.mPreference;
            if (secSwitchPreference != null) {
                secSwitchPreference.setChecked(!requireArguments().getBoolean("enabling"));
            }
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                this.mChangeState = true;
                Bundle requireArguments = requireArguments();
                boolean z = requireArguments.getBoolean("enabling");
                ContentResolver.setMasterSyncAutomaticallyAsUser(
                        z, requireArguments.getInt("userId"));
                Fragment targetFragment = getTargetFragment();
                if (targetFragment instanceof PreferenceFragmentCompat) {
                    Preference findPreference =
                            ((PreferenceFragmentCompat) targetFragment)
                                    .findPreference(
                                            requireArguments.getString(
                                                    GoodSettingsContract.EXTRA_NAME.POLICY_KEY));
                    if (findPreference instanceof SecSwitchPreference) {
                        ((SecSwitchPreference) findPreference).setChecked(z);
                    }
                }
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            FragmentActivity activity = getActivity();
            PreferenceFragmentCompat preferenceFragmentCompat =
                    (PreferenceFragmentCompat) getTargetFragment();
            this.mFragment = preferenceFragmentCompat;
            if (preferenceFragmentCompat != null) {
                this.mPreference =
                        (SecSwitchPreference)
                                preferenceFragmentCompat.findPreference(
                                        requireArguments()
                                                .getString(
                                                        GoodSettingsContract.EXTRA_NAME
                                                                .POLICY_KEY));
                if (this.mFragment.getView() != null) {
                    this.mFragment
                            .getView()
                            .addOnLayoutChangeListener(
                                    new View
                                            .OnLayoutChangeListener() { // from class:
                                                                        // com.android.settings.users.AutoSyncDataPreferenceController.ConfirmAutoSyncChangeFragment.1
                                        @Override // android.view.View.OnLayoutChangeListener
                                        public final void onLayoutChange(
                                                View view,
                                                int i,
                                                int i2,
                                                int i3,
                                                int i4,
                                                int i5,
                                                int i6,
                                                int i7,
                                                int i8) {
                                            AlertDialog alertDialog =
                                                    ConfirmAutoSyncChangeFragment.this.mDialog;
                                            if (alertDialog == null || !alertDialog.isShowing()) {
                                                return;
                                            }
                                            ConfirmAutoSyncChangeFragment
                                                    confirmAutoSyncChangeFragment =
                                                            ConfirmAutoSyncChangeFragment.this;
                                            ConfirmAutoSyncChangeFragment.updateDialogAnchorView$4(
                                                    confirmAutoSyncChangeFragment.mDialog,
                                                    confirmAutoSyncChangeFragment.mPreference);
                                        }
                                    });
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            if (requireArguments().getBoolean("enabling")) {
                builder.setTitle(R.string.data_usage_auto_sync_on_dialog_title);
                builder.setMessage(R.string.data_usage_auto_sync_on_dialog);
            } else {
                builder.setTitle(R.string.data_usage_auto_sync_off_dialog_title);
                builder.setMessage(R.string.data_usage_auto_sync_off_dialog);
            }
            builder.setPositiveButton(R.string.ok, this);
            builder.setNegativeButton(R.string.cancel, this);
            AlertDialog create = builder.create();
            this.mDialog = create;
            updateDialogAnchorView$4(create, this.mPreference);
            return this.mDialog;
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            SecSwitchPreference secSwitchPreference;
            super.onPause();
            if (this.mChangeState || (secSwitchPreference = this.mPreference) == null) {
                return;
            }
            secSwitchPreference.setChecked(!requireArguments().getBoolean("enabling"));
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            SecSwitchPreference secSwitchPreference;
            super.onResume();
            if (this.mChangeState || (secSwitchPreference = this.mPreference) == null) {
                return;
            }
            secSwitchPreference.setChecked(requireArguments().getBoolean("enabling"));
        }
    }

    public AutoSyncDataPreferenceController(
            Context context, PreferenceFragmentCompat preferenceFragmentCompat) {
        super(context);
        this.mAutoSyncBroadcastReceiver = new AutoSyncBroadcastReceiver();
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mParentFragment = preferenceFragmentCompat;
        this.mUserHandle = Process.myUserHandle();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mSwitchPreference = secSwitchPreference;
        secSwitchPreference.setOnPreferenceChangeListener(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return "auto_sync_account_data";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        List<UserHandle> userProfiles = this.mUserManager.getUserProfiles();
        AccountUtils.removeDualAppManagedProfiles(userProfiles);
        return !this.mUserManager.isManagedProfile()
                && (this.mUserManager.isRestrictedProfile() || userProfiles.size() == 1);
    }

    public final boolean isBlockedByEDM() {
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RoamingPolicy",
                        "isRoamingSyncEnabled",
                        new String[] {"false"});
        boolean z = false;
        boolean z2 = enterprisePolicyEnabled != -1 && enterprisePolicyEnabled == 0;
        boolean isNetworkRoaming = telephonyManager.isNetworkRoaming();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        if (((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                .isMultiSimModel()) {
            String semGetTelephonyProperty =
                    TelephonyManager.semGetTelephonyProperty(
                            SubscriptionManager.getPhoneId(
                                    SubscriptionManager.getDefaultDataSubscriptionId()),
                            "gsm.operator.isroaming",
                            "false");
            isNetworkRoaming =
                    !TextUtils.isEmpty(semGetTelephonyProperty)
                            && semGetTelephonyProperty.equals("true");
        }
        if (z2 && isNetworkRoaming) {
            z = true;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isBlockedByEDM: ", "AutoSyncDataController", z);
        return z;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        Context context = this.mContext;
        AutoSyncBroadcastReceiver autoSyncBroadcastReceiver = this.mAutoSyncBroadcastReceiver;
        autoSyncBroadcastReceiver.getClass();
        context.unregisterReceiver(autoSyncBroadcastReceiver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        LoggingHelper.insertEventLogging(8, 4651, booleanValue);
        if (ActivityManager.isUserAMonkey() || isBlockedByEDM()) {
            Log.d("AutoSyncDataController", "ignoring monkey's attempt to flip sync state");
            return true;
        }
        int identifier = this.mUserHandle.getIdentifier();
        String preferenceKey = getPreferenceKey();
        ConfirmAutoSyncChangeFragment confirmAutoSyncChangeFragment =
                new ConfirmAutoSyncChangeFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("enabling", booleanValue);
        bundle.putInt("userId", identifier);
        bundle.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, preferenceKey);
        confirmAutoSyncChangeFragment.setArguments(bundle);
        PreferenceFragmentCompat preferenceFragmentCompat = this.mParentFragment;
        if (!preferenceFragmentCompat.isAdded()) {
            return true;
        }
        confirmAutoSyncChangeFragment.setTargetFragment(preferenceFragmentCompat, 0);
        confirmAutoSyncChangeFragment.show(
                preferenceFragmentCompat.getParentFragmentManager(), "confirmAutoSyncChange");
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        Context context = this.mContext;
        AutoSyncBroadcastReceiver autoSyncBroadcastReceiver = this.mAutoSyncBroadcastReceiver;
        autoSyncBroadcastReceiver.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ContentResolver.ACTION_SYNC_CONN_STATUS_CHANGED.getAction());
        intentFilter.addAction(
                "com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS");
        context.registerReceiver(autoSyncBroadcastReceiver, intentFilter);
        if (this.mSwitchPreference != null) {
            if (!isBlockedByEDM()) {
                this.mSwitchPreference.setEnabled(true);
                return;
            }
            UserHandle userHandle = this.mUserHandle;
            if (userHandle != null) {
                ContentResolver.setMasterSyncAutomaticallyAsUser(false, userHandle.getIdentifier());
            } else {
                ContentResolver.setMasterSyncAutomaticallyAsUser(
                        false, UserHandle.getCallingUserId());
            }
            this.mSwitchPreference.setChecked(false);
            this.mSwitchPreference.setEnabled(false);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        SecSwitchPreference secSwitchPreference = (SecSwitchPreference) preference;
        if (this.mUserHandle == null) {
            this.mUserHandle = Process.myUserHandle();
        }
        secSwitchPreference.setChecked(
                ContentResolver.getMasterSyncAutomaticallyAsUser(this.mUserHandle.getIdentifier()));
    }
}
