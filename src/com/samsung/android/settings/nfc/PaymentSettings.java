package com.samsung.android.settings.nfc;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecSwitchPreference;

import com.android.internal.content.PackageMonitor;
import com.android.settings.SettingsPreferenceFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PaymentSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public static final boolean DBG = Debug.semIsProductDev();
    public NfcAdapter mNfcAdapter;
    public PaymentBackend mPaymentBackend;
    public PreferenceScreen mScreen;
    public boolean isRegistered = false;
    public final SettingsPackageMonitor mSettingsPackageMonitor = new SettingsPackageMonitor();
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.nfc.PaymentSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    boolean z = PaymentSettings.DBG;
                    if (z) {
                        Log.d("PaymentSettings", "-------------------------------------------");
                    }
                    if (z) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                " mReceiver.onReceive / action : ", action, "PaymentSettings");
                    }
                    if (z) {
                        Log.d("PaymentSettings", "-------------------------------------------");
                    }
                    if (!"android.nfc.action.ADAPTER_STATE_CHANGED".equals(action)) {
                        if (action.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")
                                || action.equals(
                                        "android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                            sendEmptyMessage(0);
                            return;
                        }
                        return;
                    }
                    int intExtra = intent.getIntExtra("android.nfc.extra.ADAPTER_STATE", -1);
                    if (intExtra == 1) {
                        PaymentSettings.this.getActivity().onBackPressed();
                    } else if (intExtra == 3) {
                        sendEmptyMessage(0);
                    }
                }
            };
    public final AnonymousClass2 onClickListener = new AnonymousClass2();
    public final AnonymousClass3 mUIHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.settings.nfc.PaymentSettings.3
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    LottieAnimationView lottieAnimationView;
                    LinearLayout linearLayout;
                    LinearLayout linearLayout2;
                    if (message == null) {
                        Log.e("PaymentSettings", "handleMessage: message is null");
                        return;
                    }
                    int i = message.what;
                    if (i != 0) {
                        Log.e("PaymentSettings", "msg is = " + message + " what is = " + i);
                        return;
                    }
                    PaymentSettings paymentSettings = PaymentSettings.this;
                    paymentSettings.mScreen.removeAll();
                    List paymentAppInfos = paymentSettings.mPaymentBackend.getPaymentAppInfos();
                    if (paymentSettings.getView() == null) {
                        return;
                    }
                    paymentSettings
                            .getView()
                            .setBackgroundColor(
                                    paymentSettings.getResources().getColor(R.color.transparent));
                    ArrayList arrayList = (ArrayList) paymentAppInfos;
                    if (arrayList.size() > 0) {
                        SecUnclickablePreference secUnclickablePreference =
                                new SecUnclickablePreference(
                                        paymentSettings.getPrefContext(), null);
                        secUnclickablePreference.mPositionMode = 1;
                        secUnclickablePreference.setTitle(
                                paymentSettings.getString(
                                        com.android.settings.R.string.nfc_payment_enable_summary));
                        paymentSettings.mScreen.addPreference(secUnclickablePreference);
                        SecPreferenceCategory secPreferenceCategory =
                                new SecPreferenceCategory(paymentSettings.getPrefContext(), null);
                        paymentSettings.mScreen.addPreference(secPreferenceCategory);
                        SecInsetCategoryPreference secInsetCategoryPreference =
                                new SecInsetCategoryPreference(paymentSettings.getPrefContext());
                        secInsetCategoryPreference.seslSetSubheaderRoundedBackground(15);
                        paymentSettings.mScreen.addPreference(secInsetCategoryPreference);
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            PaymentBackend.PaymentAppInfo paymentAppInfo =
                                    (PaymentBackend.PaymentAppInfo) it.next();
                            PaymentAppPreference paymentAppPreference =
                                    new PaymentAppPreference(
                                            paymentSettings.getPrefContext(),
                                            paymentAppInfo,
                                            paymentSettings.onClickListener);
                            paymentAppPreference.setTitle(paymentAppInfo.label);
                            paymentAppPreference.setKey(
                                    paymentAppInfo.componentName.flattenToString()
                                            + paymentAppInfo.userHandle.getIdentifier());
                            if (paymentAppInfo.icon != null) {
                                secPreferenceCategory.addPreference(paymentAppPreference);
                            } else {
                                Log.e(
                                        "PaymentSettings",
                                        "Couldn't load icon drawable of service "
                                                + paymentAppInfo.componentName);
                            }
                        }
                    }
                    Log.d(
                            "PaymentSettings",
                            "mScreen.getPreferenceCount() = "
                                    + paymentSettings.mScreen.getPreferenceCount());
                    if (paymentSettings.mScreen.getPreferenceCount() == 0) {
                        ViewGroup viewGroup = (ViewGroup) paymentSettings.getView();
                        View inflate =
                                paymentSettings
                                        .getActivity()
                                        .getLayoutInflater()
                                        .inflate(
                                                com.android.settings.R.layout.sec_nfc_payment_empty,
                                                viewGroup,
                                                false);
                        if (paymentSettings.getResources().getConfiguration().orientation == 2) {
                            lottieAnimationView =
                                    (LottieAnimationView)
                                            inflate.findViewById(
                                                    com.android.settings.R.id
                                                            .nfc_payment_tap_image_land);
                            linearLayout =
                                    (LinearLayout)
                                            inflate.findViewById(
                                                    com.android.settings.R.id.nfc_payment_land);
                            linearLayout2 =
                                    (LinearLayout)
                                            inflate.findViewById(
                                                    com.android.settings.R.id
                                                            .nfc_payment_land_layout);
                            ((LinearLayout)
                                            inflate.findViewById(
                                                    com.android.settings.R.id.nfc_payment_vert))
                                    .setVisibility(8);
                        } else {
                            lottieAnimationView =
                                    (LottieAnimationView)
                                            inflate.findViewById(
                                                    com.android.settings.R.id
                                                            .nfc_payment_tap_image_vert);
                            linearLayout =
                                    (LinearLayout)
                                            inflate.findViewById(
                                                    com.android.settings.R.id.nfc_payment_vert);
                            LinearLayout linearLayout3 =
                                    (LinearLayout)
                                            inflate.findViewById(
                                                    com.android.settings.R.id
                                                            .nfc_payment_vert_layout);
                            ((LinearLayout)
                                            inflate.findViewById(
                                                    com.android.settings.R.id.nfc_payment_land))
                                    .setVisibility(8);
                            linearLayout2 = linearLayout3;
                        }
                        lottieAnimationView.setAnimation(
                                com.android.settings.R.raw.sec_nfc_contactless_payments_default);
                        lottieAnimationView.playAnimation$1();
                        if (linearLayout2 != null) {
                            linearLayout2.semSetRoundedCorners(15);
                            linearLayout2.semSetRoundedCornerColor(
                                    15,
                                    paymentSettings
                                            .getActivity()
                                            .getResources()
                                            .getColor(
                                                    com.android.settings.R.color
                                                            .sec_widget_round_and_bgcolor));
                        }
                        linearLayout.setVisibility(0);
                        viewGroup.addView(inflate);
                        paymentSettings.setEmptyView(inflate);
                    } else {
                        paymentSettings.mScreen.addPreference(
                                new NfcForegroundPreference(
                                        paymentSettings.getPrefContext(),
                                        paymentSettings.mPaymentBackend));
                        SecInsetCategoryPreference secInsetCategoryPreference2 =
                                new SecInsetCategoryPreference(paymentSettings.getPrefContext());
                        secInsetCategoryPreference2.setHeight(0);
                        secInsetCategoryPreference2.seslSetSubheaderRoundedBackground(12);
                        paymentSettings.mScreen.addPreference(secInsetCategoryPreference2);
                    }
                    if (paymentSettings.mNfcAdapter.getAdapterState() == 1) {
                        paymentSettings.mScreen.setEnabled(false);
                    } else {
                        paymentSettings.mScreen.setEnabled(true);
                    }
                    paymentSettings.mScreen.setEnabled(true);
                    paymentSettings.setPreferenceScreen(paymentSettings.mScreen);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.nfc.PaymentSettings$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PaymentAppPreference extends SecPreference {
        public final PaymentBackend.PaymentAppInfo appInfo;
        public final AnonymousClass2 listener;
        public RadioButton radioButton;

        public PaymentAppPreference(
                Context context,
                PaymentBackend.PaymentAppInfo paymentAppInfo,
                AnonymousClass2 anonymousClass2) {
            super(context, null);
            this.listener = null;
            setLayoutResource(com.android.settings.R.layout.nfc_payment_option);
            this.appInfo = paymentAppInfo;
            this.listener = anonymousClass2;
        }

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            RadioButton radioButton = (RadioButton) preferenceViewHolder.findViewById(R.id.button1);
            this.radioButton = radioButton;
            radioButton.setChecked(this.appInfo.isDefault);
            this.radioButton.setTag(this.appInfo);
            ((LinearLayout)
                            preferenceViewHolder.findViewById(
                                    com.android.settings.R.id.linearlayout))
                    .setTag(this.appInfo);
            ImageView imageView =
                    (ImageView) preferenceViewHolder.findViewById(com.android.settings.R.id.banner);
            imageView.setImageDrawable(this.appInfo.icon);
            imageView.setTag(this.appInfo);
            imageView.setClipToOutline(true);
            TextView textView =
                    (TextView)
                            preferenceViewHolder.findViewById(
                                    com.android.settings.R.id.serviceDescription);
            TextView textView2 =
                    (TextView)
                            preferenceViewHolder.findViewById(
                                    com.android.settings.R.id.workProfileText);
            if (SemPersonaManager.isSecureFolderId(this.appInfo.userHandle.getIdentifier())) {
                textView2.setText(com.android.settings.R.string.secure_folder_title);
            } else if (SemDualAppManager.isDualAppId(this.appInfo.userHandle.getIdentifier())) {
                textView2.setText(com.android.settings.R.string.dual_app_title);
            } else {
                PaymentBackend.PaymentAppInfo paymentAppInfo = this.appInfo;
                if (paymentAppInfo.isManagedProfile) {
                    textView2.setText(com.android.settings.R.string.work_title);
                } else {
                    textView2.setText(paymentAppInfo.userName);
                }
            }
            textView2.setVisibility(!this.appInfo.isCurrentUser ? 0 : 8);
            PaymentBackend.PaymentAppInfo paymentAppInfo2 = this.appInfo;
            CharSequence charSequence = paymentAppInfo2.description;
            if (charSequence == null) {
                charSequence = paymentAppInfo2.label;
            }
            textView.setText(charSequence);
            textView.setTag(this.appInfo);
        }

        @Override // androidx.preference.Preference
        public final void onClick() {
            ComponentName componentName;
            AnonymousClass2 anonymousClass2 = this.listener;
            if (anonymousClass2 != null) {
                anonymousClass2.getClass();
                PaymentBackend.PaymentAppInfo paymentAppInfo = this.appInfo;
                if (paymentAppInfo == null) {
                    Log.e("PaymentSettings", "appinfo null, dropping click event");
                    return;
                }
                if (paymentAppInfo.isDefault || paymentAppInfo.componentName == null) {
                    return;
                }
                PaymentSettings paymentSettings = PaymentSettings.this;
                PaymentBackend.PaymentAppInfo paymentAppInfo2 =
                        paymentSettings.mPaymentBackend.mDefaultAppInfo;
                if (paymentAppInfo2 != null) {
                    paymentAppInfo2.isDefault = false;
                    componentName = paymentAppInfo2.componentName;
                    try {
                        RadioButton radioButton =
                                ((PaymentAppPreference)
                                                paymentSettings.mScreen.findPreference(
                                                        componentName.flattenToString()
                                                                + paymentAppInfo2.userHandle
                                                                        .getIdentifier()))
                                        .radioButton;
                        if (radioButton != null) {
                            radioButton.setChecked(false);
                        }
                    } catch (NullPointerException unused) {
                        Log.d("PaymentSettings", "prevPaymentPreference is null");
                    }
                } else {
                    componentName = null;
                }
                ComponentName componentName2 = paymentAppInfo.componentName;
                PaymentAppPreference paymentAppPreference =
                        (PaymentAppPreference)
                                paymentSettings.mScreen.findPreference(
                                        componentName2.flattenToString()
                                                + paymentAppInfo.userHandle.getIdentifier());
                paymentAppInfo.isDefault = true;
                paymentSettings.mPaymentBackend.mDefaultAppInfo = paymentAppInfo;
                try {
                    RadioButton radioButton2 = paymentAppPreference.radioButton;
                    if (radioButton2 != null) {
                        radioButton2.setChecked(true);
                    }
                } catch (NullPointerException unused2) {
                    Log.d("PaymentSettings", "changedPaymentPreference is null");
                }
                paymentSettings.mPaymentBackend.setDefaultPaymentApp(
                        componentName2, paymentAppInfo.userHandle.getIdentifier());
                if (PaymentSettings.DBG) {
                    Log.d("PaymentSettings", "Previous Default Payment = " + componentName);
                    Log.d("PaymentSettings", "Changed Default Payment = " + componentName2);
                }
                paymentSettings.mPaymentBackend.setDefaultPaymentApp(
                        paymentAppInfo.componentName, paymentAppInfo.userHandle.getIdentifier());
                if (componentName == null || !componentName.equals(componentName2)) {
                    LoggingHelper.insertEventLogging(3653, 3653, 3654L);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsPackageMonitor extends PackageMonitor {
        public SettingsPackageMonitor() {}

        public final void onPackageAdded(String str, int i) {
            obtainMessage().sendToTarget();
        }

        public final void onPackageAppeared(String str, int i) {
            obtainMessage().sendToTarget();
        }

        public final void onPackageDisappeared(String str, int i) {
            obtainMessage().sendToTarget();
        }

        public final void onPackageRemoved(String str, int i) {
            obtainMessage().sendToTarget();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3653;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        sendEmptyMessage(0);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        this.mPaymentBackend = new PaymentBackend(getActivity());
        this.mScreen = getPreferenceManager().createPreferenceScreen(getActivity());
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (DBG) {
            Log.d("PaymentSettings", "onCreateOptionsMenu");
        }
        super.onCreateOptionsMenu(menu, menuInflater);
        String string =
                Settings.Secure.getString(getContentResolver(), "payment_service_search_uri");
        if (TextUtils.isEmpty(string)) {
            return;
        }
        MenuItem add = menu.add(com.android.settings.R.string.nfc_payment_menu_item_add_service);
        add.setShowAsActionFlags(1);
        add.setIntent(new Intent("android.intent.action.VIEW", Uri.parse(string)));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        onCreateView.setBackgroundColor(
                getResources().getColor(com.android.settings.R.color.sec_widget_round_and_bgcolor));
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        try {
            if (this.isRegistered) {
                Log.d("PaymentSettings", getActivity() + "unRegister Receiver");
                getActivity().unregisterReceiver(this.mReceiver);
                this.mSettingsPackageMonitor.unregister();
                this.isRegistered = false;
            }
        } catch (IllegalStateException unused) {
            Log.d("PaymentSettings", getActivity() + "already unregistered");
            this.isRegistered = false;
        }
        super.onDestroy();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(preference instanceof SecSwitchPreference)) {
            return false;
        }
        this.mPaymentBackend.setForegroundMode(((Boolean) obj).booleanValue());
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        setHasOptionsMenu(false);
        try {
            if (!this.isRegistered) {
                if (DBG) {
                    Log.d("PaymentSettings", getActivity() + "register Receiver");
                }
                FragmentActivity activity = getActivity();
                AnonymousClass1 anonymousClass1 = this.mReceiver;
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.nfc.action.ADAPTER_STATE_CHANGED");
                activity.registerReceiver(anonymousClass1, intentFilter);
                FragmentActivity activity2 = getActivity();
                AnonymousClass1 anonymousClass12 = this.mReceiver;
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
                intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
                activity2.registerReceiver(anonymousClass12, intentFilter2);
                this.mSettingsPackageMonitor.register(
                        getActivity(), getActivity().getMainLooper(), false);
                this.isRegistered = true;
            }
        } catch (IllegalStateException unused) {
            if (DBG) {
                Log.d("PaymentSettings", getActivity() + "already registered");
            }
            this.isRegistered = true;
        }
        if (DBG) {
            Log.d(
                    "PaymentSettings",
                    "onResume() backup component : " + this.mPaymentBackend.getDefaultPaymentApp());
        }
        sendEmptyMessage(0);
        setHasOptionsMenu(false);
    }
}
