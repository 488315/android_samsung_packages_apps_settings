package com.android.settings;

import android.R;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.CarrierConfigManager;
import android.telephony.PinResult;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.TwoStatePreference;

import com.android.settings.network.ActiveSubscriptionsListener;
import com.android.settings.network.ProxySubscriptionManager;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settingslib.CustomEditTextPreferenceCompat;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class IccLockSettings extends SettingsPreferenceFragment {
    public String mError;
    public String mNewPin;
    public String mOldPin;
    public String mPin;
    public EditPinPreference mPinDialog;
    public TwoStatePreference mPinToggle;
    public ProxySubscriptionManager mProxySubscriptionMgr;
    public Resources mRes;
    public int mSubId;
    public TabHost mTabHost;
    public TelephonyManager mTelephonyManager;
    public boolean mToState;
    public int mDialogState = 0;
    public int mSlotId = -1;
    public final AnonymousClass1 mHandler =
            new Handler() { // from class: com.android.settings.IccLockSettings.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 102) {
                        return;
                    }
                    IccLockSettings.this.updatePreferences$4();
                }
            };
    public final AnonymousClass2 mSimStateReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.IccLockSettings.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
                        AnonymousClass1 anonymousClass1 = IccLockSettings.this.mHandler;
                        anonymousClass1.sendMessage(anonymousClass1.obtainMessage(102));
                    }
                }
            };
    public final AnonymousClass4 mTabListener =
            new TabHost
                    .OnTabChangeListener() { // from class: com.android.settings.IccLockSettings.4
                @Override // android.widget.TabHost.OnTabChangeListener
                public final void onTabChanged(String str) {
                    int i;
                    IccLockSettings iccLockSettings = IccLockSettings.this;
                    iccLockSettings.getClass();
                    try {
                        i = Integer.parseInt(str);
                    } catch (NumberFormatException unused) {
                        i = -1;
                    }
                    iccLockSettings.mSlotId = i;
                    IccLockSettings.this.updatePreferences$4();
                }
            };
    public final AnonymousClass5 mEmptyTabContent =
            new TabHost.TabContentFactory() { // from class: com.android.settings.IccLockSettings.5
                @Override // android.widget.TabHost.TabContentFactory
                public final View createTabContent(String str) {
                    return new View(IccLockSettings.this.mTabHost.getContext());
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ChangeIccLockPin extends AsyncTask {
        public final String mNewPin;
        public final String mOldPin;

        public ChangeIccLockPin(String str, String str2) {
            this.mOldPin = str;
            this.mNewPin = str2;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            IccLockSettings iccLockSettings = IccLockSettings.this;
            iccLockSettings.mTelephonyManager =
                    iccLockSettings.mTelephonyManager.createForSubscriptionId(
                            iccLockSettings.mSubId);
            return IccLockSettings.this.mTelephonyManager.changeIccLockPin(
                    this.mOldPin, this.mNewPin);
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            PinResult pinResult = (PinResult) obj;
            IccLockSettings iccLockSettings = IccLockSettings.this;
            boolean z = pinResult.getResult() == 0;
            int attemptsRemaining = pinResult.getAttemptsRemaining();
            iccLockSettings.getClass();
            Log.d("IccLockSettings", "iccPinChanged: success = " + z);
            if (z) {
                Toast.makeText(
                                iccLockSettings.getContext(),
                                iccLockSettings.mRes.getString(R.string.sim_change_succeeded),
                                0)
                        .show();
            } else {
                iccLockSettings.createCustomTextToast(
                        iccLockSettings.getPinPasswordErrorMessage(attemptsRemaining));
            }
            iccLockSettings.resetDialogState();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SetIccLockEnabled extends AsyncTask {
        public final String mPin;
        public final boolean mState;

        public SetIccLockEnabled(boolean z, String str) {
            this.mState = z;
            this.mPin = str;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            IccLockSettings iccLockSettings = IccLockSettings.this;
            iccLockSettings.mTelephonyManager =
                    iccLockSettings.mTelephonyManager.createForSubscriptionId(
                            iccLockSettings.mSubId);
            return IccLockSettings.this.mTelephonyManager.setIccLockEnabled(this.mState, this.mPin);
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            PinResult pinResult = (PinResult) obj;
            IccLockSettings iccLockSettings = IccLockSettings.this;
            boolean z = pinResult.getResult() == 0;
            int attemptsRemaining = pinResult.getAttemptsRemaining();
            iccLockSettings.getClass();
            Log.d("IccLockSettings", "iccLockChanged: success = " + z);
            if (z) {
                iccLockSettings.mPinToggle.setChecked(iccLockSettings.mToState);
            } else if (attemptsRemaining >= 0) {
                iccLockSettings.createCustomTextToast(
                        iccLockSettings.getPinPasswordErrorMessage(attemptsRemaining));
            } else if (iccLockSettings.mToState) {
                Toast.makeText(
                                iccLockSettings.getContext(),
                                iccLockSettings.mRes.getString(R.string.sim_pin_enable_failed),
                                1)
                        .show();
            } else {
                Toast.makeText(
                                iccLockSettings.getContext(),
                                iccLockSettings.mRes.getString(R.string.sim_pin_disable_failed),
                                1)
                        .show();
            }
            iccLockSettings.mPinToggle.setEnabled(true);
            iccLockSettings.resetDialogState();
        }
    }

    public final void createCustomTextToast(CharSequence charSequence) {
        final View inflate =
                ((LayoutInflater) getSystemService("layout_inflater"))
                        .inflate(17367475, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.message);
        textView.setText(charSequence);
        textView.setSingleLine(false);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        int absoluteGravity =
                Gravity.getAbsoluteGravity(
                        getContext()
                                .getResources()
                                .getInteger(
                                        R.integer.device_idle_max_temp_app_allowlist_duration_ms),
                        inflate.getContext()
                                .getResources()
                                .getConfiguration()
                                .getLayoutDirection());
        layoutParams.gravity = absoluteGravity;
        if ((absoluteGravity & 7) == 7) {
            layoutParams.horizontalWeight = 1.0f;
        }
        if ((absoluteGravity & 112) == 112) {
            layoutParams.verticalWeight = 1.0f;
        }
        layoutParams.y = getContext().getResources().getDimensionPixelSize(17106355);
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R.style.Animation.Toast;
        layoutParams.type = 2017;
        layoutParams.setFitInsetsTypes(
                layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        layoutParams.setTitle(charSequence);
        layoutParams.flags = 152;
        final WindowManager windowManager = (WindowManager) getSystemService("window");
        windowManager.addView(inflate, layoutParams);
        postDelayed(
                new Runnable() { // from class: com.android.settings.IccLockSettings.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        windowManager.removeViewImmediate(inflate);
                    }
                },
                7000L);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 56;
    }

    public final String getPinPasswordErrorMessage(int i) {
        return i == 0
                ? this.mRes.getString(R.string.wrong_pin_code_pukked)
                : i == 1
                        ? this.mRes.getString(R.string.wrong_pin_code_one, Integer.valueOf(i))
                        : i > 1
                                ? StringUtil.getIcuPluralsString(
                                        getPrefContext(), i, R.string.wrong_pin_code)
                                : this.mRes.getString(R.string.pin_failed);
    }

    public final SubscriptionInfo getVisibleSubscriptionInfoForSimSlotIndex(int i) {
        Context context;
        List<SubscriptionInfo> activeSubscriptionsInfo =
                this.mProxySubscriptionMgr.mSubscriptionMonitor.getActiveSubscriptionsInfo();
        if (activeSubscriptionsInfo == null || (context = getContext()) == null) {
            return null;
        }
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        for (SubscriptionInfo subscriptionInfo : activeSubscriptionsInfo) {
            if ((carrierConfigManager.getConfigForSubId(subscriptionInfo.getSubscriptionId())
                                    == null
                            ? false
                            : !r3.getBoolean("hide_sim_lock_settings_bool"))
                    && subscriptionInfo.getSimSlotIndex() == i) {
                return subscriptionInfo;
            }
        }
        return null;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        SubscriptionInfo visibleSubscriptionInfoForSimSlotIndex;
        super.onCreate(bundle);
        StringBuilder sb = Utils.sBuilder;
        if (ActivityManager.isUserAMonkey()
                || !SubscriptionUtil.isSimHardwareVisible(getContext())
                || MobileNetworkUtils.isMobileNetworkUserRestricted(getContext())) {
            finish();
            return;
        }
        ProxySubscriptionManager proxySubscriptionManager =
                ProxySubscriptionManager.getInstance(getContext());
        this.mProxySubscriptionMgr = proxySubscriptionManager;
        proxySubscriptionManager.setLifecycle(getLifecycle());
        this.mTelephonyManager =
                (TelephonyManager) getContext().getSystemService(TelephonyManager.class);
        addPreferencesFromResource(R.xml.sim_lock_settings);
        this.mPinDialog = (EditPinPreference) findPreference("sim_pin");
        this.mPinToggle = (TwoStatePreference) findPreference("sim_toggle");
        if (bundle != null) {
            if (bundle.containsKey("dialogState")) {
                SubscriptionInfo activeSubscriptionInfo =
                        this.mProxySubscriptionMgr.mSubscriptionMonitor.getActiveSubscriptionInfo(
                                bundle.getInt("dialogSubId"));
                if (activeSubscriptionInfo != null
                        && (visibleSubscriptionInfoForSimSlotIndex =
                                        getVisibleSubscriptionInfoForSimSlotIndex(
                                                activeSubscriptionInfo.getSimSlotIndex()))
                                != null
                        && visibleSubscriptionInfoForSimSlotIndex.getSubscriptionId()
                                == activeSubscriptionInfo.getSubscriptionId()) {
                    this.mSlotId = activeSubscriptionInfo.getSimSlotIndex();
                    this.mSubId = activeSubscriptionInfo.getSubscriptionId();
                    this.mDialogState = bundle.getInt("dialogState");
                    this.mPin = bundle.getString("dialogPin");
                    this.mError = bundle.getString("dialogError");
                    this.mToState = bundle.getBoolean("enableState");
                    int i = this.mDialogState;
                    if (i == 3) {
                        this.mOldPin = bundle.getString("oldPinCode");
                    } else if (i == 4) {
                        this.mOldPin = bundle.getString("oldPinCode");
                        this.mNewPin = bundle.getString("newPinCode");
                    }
                    StringBuilder sb2 = new StringBuilder("onCreate: restore dialog for slotId=");
                    sb2.append(this.mSlotId);
                    sb2.append(", subId=");
                    Preference$$ExternalSyntheticOutline0.m(sb2, this.mSubId, "IccLockSettings");
                }
            }
            if (bundle.containsKey("currentTab")) {
                try {
                    SubscriptionInfo visibleSubscriptionInfoForSimSlotIndex2 =
                            getVisibleSubscriptionInfoForSimSlotIndex(
                                    Integer.parseInt(bundle.getString("currentTab")));
                    if (visibleSubscriptionInfoForSimSlotIndex2 != null) {
                        this.mSlotId = visibleSubscriptionInfoForSimSlotIndex2.getSimSlotIndex();
                        this.mSubId = visibleSubscriptionInfoForSimSlotIndex2.getSubscriptionId();
                        TabHost tabHost = this.mTabHost;
                        if (tabHost != null) {
                            tabHost.setCurrentTabByTag(String.valueOf(this.mSlotId));
                        }
                        StringBuilder sb3 = new StringBuilder("onCreate: restore focus on slotId=");
                        sb3.append(this.mSlotId);
                        sb3.append(", subId=");
                        Preference$$ExternalSyntheticOutline0.m(
                                sb3, this.mSubId, "IccLockSettings");
                    }
                } catch (NumberFormatException unused) {
                }
            }
        }
        this.mPinDialog.mPinListener = this;
        getPreferenceScreen().setPersistent();
        this.mRes = getResources();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        ActiveSubscriptionsListener activeSubscriptionsListener =
                this.mProxySubscriptionMgr.mSubscriptionMonitor;
        if (activeSubscriptionsListener.mCacheState.get() < 3) {
            i =
                    activeSubscriptionsListener
                            .getSubscriptionManager()
                            .getActiveSubscriptionInfoCountMax();
        } else {
            activeSubscriptionsListener.mMaxActiveSubscriptionInfos.compareAndSet(
                    -1,
                    activeSubscriptionsListener
                            .getSubscriptionManager()
                            .getActiveSubscriptionInfoCountMax());
            i = activeSubscriptionsListener.mMaxActiveSubscriptionInfos.get();
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            SubscriptionInfo visibleSubscriptionInfoForSimSlotIndex =
                    getVisibleSubscriptionInfoForSimSlotIndex(i2);
            if (visibleSubscriptionInfoForSimSlotIndex != null) {
                arrayList.add(visibleSubscriptionInfoForSimSlotIndex);
            }
        }
        if (arrayList.size() == 0) {
            Log.e("IccLockSettings", "onCreateView: no sim info");
            return super.onCreateView(layoutInflater, viewGroup, bundle);
        }
        if (this.mSlotId < 0) {
            this.mSlotId = ((SubscriptionInfo) arrayList.get(0)).getSimSlotIndex();
            this.mSubId = ((SubscriptionInfo) arrayList.get(0)).getSubscriptionId();
            StringBuilder sb = new StringBuilder("onCreateView: default slotId=");
            sb.append(this.mSlotId);
            sb.append(", subId=");
            Preference$$ExternalSyntheticOutline0.m(sb, this.mSubId, "IccLockSettings");
        }
        if (arrayList.size() <= 1) {
            return super.onCreateView(layoutInflater, viewGroup, bundle);
        }
        View inflate = layoutInflater.inflate(R.layout.icc_lock_tabs, viewGroup, false);
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.prefs_container);
        Utils.prepareCustomPreferencesList(viewGroup, inflate, viewGroup2);
        viewGroup2.addView(super.onCreateView(layoutInflater, viewGroup2, bundle));
        this.mTabHost = (TabHost) inflate.findViewById(R.id.tabhost);
        this.mTabHost.setup();
        this.mTabHost.clearAllTabs();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
            this.mTabHost.addTab(
                    this.mTabHost
                            .newTabSpec(String.valueOf(subscriptionInfo.getSimSlotIndex()))
                            .setIndicator(
                                    String.valueOf(
                                            SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                                    getContext(), subscriptionInfo)))
                            .setContent(this.mEmptyTabContent));
        }
        this.mTabHost.setCurrentTabByTag(String.valueOf(this.mSlotId));
        this.mTabHost.setOnTabChangedListener(this.mTabListener);
        return inflate;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getContext().unregisterReceiver(this.mSimStateReceiver);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        TwoStatePreference twoStatePreference = this.mPinToggle;
        if (preference == twoStatePreference) {
            boolean isChecked = twoStatePreference.isChecked();
            this.mToState = isChecked;
            this.mPinToggle.setChecked(!isChecked);
            this.mDialogState = 1;
            showPinDialog$1();
        } else if (preference == this.mPinDialog) {
            this.mDialogState = 2;
            return false;
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getContext()
                .registerReceiver(
                        this.mSimStateReceiver,
                        new IntentFilter("android.intent.action.SIM_STATE_CHANGED"));
        if (this.mDialogState != 0) {
            showPinDialog$1();
        } else {
            resetDialogState();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        CustomEditTextPreferenceCompat.CustomPreferenceDialogFragment
                customPreferenceDialogFragment =
                        ((CustomEditTextPreferenceCompat) this.mPinDialog).mFragment;
        Dialog dialog =
                customPreferenceDialogFragment != null
                        ? customPreferenceDialogFragment.mDialog
                        : null;
        if (dialog == null || !dialog.isShowing()) {
            super.onSaveInstanceState(bundle);
        } else {
            bundle.putInt("dialogSubId", this.mSubId);
            bundle.putInt("dialogState", this.mDialogState);
            bundle.putString("dialogPin", this.mPinDialog.getEditText().getText().toString());
            bundle.putString("dialogError", this.mError);
            bundle.putBoolean("enableState", this.mToState);
            int i = this.mDialogState;
            if (i == 3) {
                bundle.putString("oldPinCode", this.mOldPin);
            } else if (i == 4) {
                bundle.putString("oldPinCode", this.mOldPin);
                bundle.putString("newPinCode", this.mNewPin);
            }
        }
        TabHost tabHost = this.mTabHost;
        if (tabHost != null) {
            bundle.putString("currentTab", tabHost.getCurrentTabTag());
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        updatePreferences$4();
    }

    public final void resetDialogState() {
        this.mError = null;
        this.mDialogState = 2;
        this.mPin = ApnSettings.MVNO_NONE;
        setDialogValues();
        this.mDialogState = 0;
    }

    public final void setDialogValues() {
        String string;
        this.mPinDialog.setText(this.mPin);
        int i = this.mDialogState;
        if (i == 1) {
            string = this.mRes.getString(R.string.sim_enter_pin);
            this.mPinDialog.mDialogTitle =
                    this.mToState
                            ? this.mRes.getString(R.string.sim_enable_sim_lock)
                            : this.mRes.getString(R.string.sim_disable_sim_lock);
        } else if (i == 2) {
            string = this.mRes.getString(R.string.sim_enter_old);
            this.mPinDialog.mDialogTitle = this.mRes.getString(R.string.sim_change_pin);
        } else if (i == 3) {
            string = this.mRes.getString(R.string.sim_enter_new);
            this.mPinDialog.mDialogTitle = this.mRes.getString(R.string.sim_change_pin);
        } else if (i != 4) {
            string = ApnSettings.MVNO_NONE;
        } else {
            string = this.mRes.getString(R.string.sim_reenter_new);
            this.mPinDialog.mDialogTitle = this.mRes.getString(R.string.sim_change_pin);
        }
        if (this.mError != null) {
            string =
                    BackStackRecord$$ExternalSyntheticOutline0.m(
                            new StringBuilder(), this.mError, "\n", string);
            this.mError = null;
        }
        this.mPinDialog.mDialogMessage = string;
    }

    public final void showPinDialog$1() {
        if (this.mDialogState == 0) {
            return;
        }
        setDialogValues();
        EditPinPreference editPinPreference = this.mPinDialog;
        CustomEditTextPreferenceCompat.CustomPreferenceDialogFragment
                customPreferenceDialogFragment =
                        ((CustomEditTextPreferenceCompat) editPinPreference).mFragment;
        Dialog dialog =
                customPreferenceDialogFragment != null
                        ? customPreferenceDialogFragment.mDialog
                        : null;
        if (dialog == null || !dialog.isShowing()) {
            editPinPreference.onClick();
        }
        EditText editText = this.mPinDialog.getEditText();
        if (TextUtils.isEmpty(this.mPin) || editText == null) {
            return;
        }
        editText.setSelection(this.mPin.length());
    }

    public final void updatePreferences$4() {
        SubscriptionInfo visibleSubscriptionInfoForSimSlotIndex =
                getVisibleSubscriptionInfoForSimSlotIndex(this.mSlotId);
        int subscriptionId =
                visibleSubscriptionInfoForSimSlotIndex != null
                        ? visibleSubscriptionInfoForSimSlotIndex.getSubscriptionId()
                        : -1;
        if (this.mSubId != subscriptionId) {
            this.mSubId = subscriptionId;
            resetDialogState();
            EditPinPreference editPinPreference = this.mPinDialog;
            if (editPinPreference != null) {
                CustomEditTextPreferenceCompat.CustomPreferenceDialogFragment
                        customPreferenceDialogFragment =
                                ((CustomEditTextPreferenceCompat) editPinPreference).mFragment;
                Dialog dialog =
                        customPreferenceDialogFragment != null
                                ? customPreferenceDialogFragment.mDialog
                                : null;
                if (dialog != null && dialog.isShowing()) {
                    CustomEditTextPreferenceCompat.CustomPreferenceDialogFragment
                            customPreferenceDialogFragment2 =
                                    ((CustomEditTextPreferenceCompat) this.mPinDialog).mFragment;
                    (customPreferenceDialogFragment2 != null
                                    ? customPreferenceDialogFragment2.mDialog
                                    : null)
                            .dismiss();
                }
            }
        }
        EditPinPreference editPinPreference2 = this.mPinDialog;
        if (editPinPreference2 != null) {
            editPinPreference2.setEnabled(visibleSubscriptionInfoForSimSlotIndex != null);
        }
        TwoStatePreference twoStatePreference = this.mPinToggle;
        if (twoStatePreference != null) {
            twoStatePreference.setEnabled(visibleSubscriptionInfoForSimSlotIndex != null);
            if (visibleSubscriptionInfoForSimSlotIndex != null) {
                TwoStatePreference twoStatePreference2 = this.mPinToggle;
                TelephonyManager createForSubscriptionId =
                        this.mTelephonyManager.createForSubscriptionId(this.mSubId);
                this.mTelephonyManager = createForSubscriptionId;
                twoStatePreference2.setChecked(createForSubscriptionId.isIccLockEnabled());
            }
        }
    }
}
