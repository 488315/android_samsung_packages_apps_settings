package com.samsung.android.settings.nfc;

import android.R;
import android.app.AlertDialog;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.internal.content.PackageMonitor;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class OtherSettings extends SettingsPreferenceFragment {
    public static final boolean DBG = PaymentSettings.DBG;
    public NfcAdapter mNfcAdapter;
    public OtherBackend mOtherBackend;
    public PreferenceScreen mScreen;
    public RoleManager roleManager;
    public AlertDialog mAlertDialog = null;
    public String mSavedConflctDialogMsg = null;
    public boolean mDoNotFinishDialog = false;
    public boolean isRegistered = false;
    public final SettingsPackageMonitor mSettingsPackageMonitor = new SettingsPackageMonitor();
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.nfc.OtherSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (OtherSettings.DBG) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "mReceiver.onReceive / action : ", action, "OtherSettings");
                    }
                    sendEmptyMessage(0);
                }
            };
    public final AnonymousClass2 otherAppPreferenceCallback = new AnonymousClass2();
    public final AnonymousClass3 mUIHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.settings.nfc.OtherSettings.3
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    LottieAnimationView lottieAnimationView;
                    NestedScrollView nestedScrollView;
                    LinearLayout linearLayout;
                    if (message == null) {
                        Log.e("OtherSettings", "handleMessage: message is null");
                        return;
                    }
                    int i = message.what;
                    if (i != 0) {
                        Log.e("OtherSettings", "msg is = " + message + " what is = " + i);
                        return;
                    }
                    OtherSettings otherSettings = OtherSettings.this;
                    otherSettings.mScreen.removeAll();
                    if (otherSettings.getView() == null) {
                        return;
                    }
                    otherSettings
                            .getView()
                            .setBackgroundColor(
                                    otherSettings.getResources().getColor(R.color.transparent));
                    ArrayList arrayList = (ArrayList) otherSettings.getOtherApps(true);
                    if (!arrayList.isEmpty()) {
                        SecUnclickablePreference secUnclickablePreference =
                                new SecUnclickablePreference(otherSettings.getPrefContext());
                        secUnclickablePreference.setTitle(
                                com.android.settings.R.string.default_wallet_app);
                        secUnclickablePreference.mPositionMode = 1;
                        otherSettings.mScreen.addPreference(secUnclickablePreference);
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            OtherBackend.OtherAppInfo otherAppInfo =
                                    (OtherBackend.OtherAppInfo) it.next();
                            OtherAppPreference otherAppPreference =
                                    new OtherAppPreference(
                                            otherSettings.getPrefContext(),
                                            otherAppInfo,
                                            otherSettings.otherAppPreferenceCallback);
                            otherAppPreference.setTitle(otherAppInfo.caption);
                            otherSettings.mScreen.addPreference(otherAppPreference);
                        }
                        SecUnclickablePreference secUnclickablePreference2 =
                                new SecUnclickablePreference(otherSettings.getPrefContext());
                        secUnclickablePreference2.setTitle(
                                com.android.settings.R.string.the_default_wallet_apps_services);
                        otherSettings.mScreen.addPreference(secUnclickablePreference2);
                        SecUnclickablePreference secUnclickablePreference3 =
                                new SecUnclickablePreference(otherSettings.getPrefContext());
                        secUnclickablePreference3.setTitle(
                                com.android.settings.R.string.other_apps_title);
                        otherSettings.mScreen.addPreference(secUnclickablePreference3);
                    }
                    Iterator it2 = ((ArrayList) otherSettings.getOtherApps(false)).iterator();
                    while (it2.hasNext()) {
                        OtherBackend.OtherAppInfo otherAppInfo2 =
                                (OtherBackend.OtherAppInfo) it2.next();
                        OtherAppPreference otherAppPreference2 =
                                new OtherAppPreference(
                                        otherSettings.getPrefContext(),
                                        otherAppInfo2,
                                        otherSettings.otherAppPreferenceCallback);
                        otherAppPreference2.setTitle(otherAppInfo2.caption);
                        otherSettings.mScreen.addPreference(otherAppPreference2);
                    }
                    Log.d(
                            "OtherSettings",
                            "mScreen.getPreferenceCount() = "
                                    + otherSettings.mScreen.getPreferenceCount());
                    if (otherSettings.mScreen.getPreferenceCount() == 0) {
                        ViewGroup viewGroup = (ViewGroup) otherSettings.getListView().getParent();
                        View inflate =
                                otherSettings
                                        .getActivity()
                                        .getLayoutInflater()
                                        .inflate(
                                                com.android.settings.R.layout.sec_nfc_other_empty,
                                                viewGroup,
                                                false);
                        if (otherSettings.getResources().getConfiguration().orientation == 2) {
                            lottieAnimationView =
                                    (LottieAnimationView)
                                            inflate.findViewById(
                                                    com.android.settings.R.id
                                                            .nfc_other_tap_image_land);
                            nestedScrollView =
                                    (NestedScrollView)
                                            inflate.findViewById(
                                                    com.android.settings.R.id.nfc_other_land);
                            linearLayout =
                                    (LinearLayout)
                                            inflate.findViewById(
                                                    com.android.settings.R.id
                                                            .nfc_other_land_layout);
                            ((NestedScrollView)
                                            inflate.findViewById(
                                                    com.android.settings.R.id.nfc_other_vert))
                                    .setVisibility(8);
                        } else {
                            lottieAnimationView =
                                    (LottieAnimationView)
                                            inflate.findViewById(
                                                    com.android.settings.R.id
                                                            .nfc_other_tap_image_vert);
                            nestedScrollView =
                                    (NestedScrollView)
                                            inflate.findViewById(
                                                    com.android.settings.R.id.nfc_other_vert);
                            LinearLayout linearLayout2 =
                                    (LinearLayout)
                                            inflate.findViewById(
                                                    com.android.settings.R.id
                                                            .nfc_other_vert_layout);
                            ((NestedScrollView)
                                            inflate.findViewById(
                                                    com.android.settings.R.id.nfc_other_land))
                                    .setVisibility(8);
                            linearLayout = linearLayout2;
                        }
                        lottieAnimationView.setAnimation(
                                Utils.isTablet()
                                        ? com.android.settings.R.raw
                                                .sec_nfc_contactless_payments_tablet_default
                                        : com.android.settings.R.raw
                                                .sec_nfc_contactless_payments_default);
                        lottieAnimationView.playAnimation$1();
                        if (linearLayout != null) {
                            linearLayout.semSetRoundedCorners(15);
                            linearLayout.semSetRoundedCornerColor(
                                    15,
                                    otherSettings
                                            .getActivity()
                                            .getResources()
                                            .getColor(
                                                    com.android.settings.R.color
                                                            .sec_widget_round_and_bgcolor));
                        }
                        nestedScrollView.setVisibility(0);
                        viewGroup.addView(inflate);
                        otherSettings.setEmptyView(inflate);
                    }
                    if (otherSettings.mNfcAdapter.getAdapterState() == 1) {
                        otherSettings.mScreen.setEnabled(false);
                    } else {
                        otherSettings.mScreen.setEnabled(true);
                    }
                    otherSettings.mScreen.setEnabled(true);
                    otherSettings.setPreferenceScreen(otherSettings.mScreen);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.nfc.OtherSettings$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OtherAppPreference extends SecPreference {
        public final OtherBackend.OtherAppInfo appInfo;
        public final AnonymousClass2 callback;
        public CheckBox checkbox;

        public OtherAppPreference(
                Context context,
                OtherBackend.OtherAppInfo otherAppInfo,
                AnonymousClass2 anonymousClass2) {
            super(context, null);
            setLayoutResource(com.android.settings.R.layout.sec_nfc_other_option);
            this.appInfo = otherAppInfo;
            this.callback = anonymousClass2;
        }

        @Override // androidx.preference.Preference
        public final boolean isEnabled() {
            return true;
        }

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            CheckBox checkBox = (CheckBox) preferenceViewHolder.findViewById(R.id.checkbox);
            this.checkbox = checkBox;
            checkBox.setChecked(this.appInfo.isSelected);
            this.checkbox.setTag(this.appInfo);
            ImageView imageView =
                    (ImageView) preferenceViewHolder.findViewById(com.android.settings.R.id.banner);
            ((LinearLayout)
                            preferenceViewHolder.findViewById(
                                    com.android.settings.R.id.linearlayout))
                    .setTag(this.appInfo);
            TextView textView =
                    (TextView)
                            preferenceViewHolder.findViewById(
                                    com.android.settings.R.id.serviceDescription);
            textView.setText(this.appInfo.caption);
            textView.setTag(this.appInfo);
            TextView textView2 =
                    (TextView)
                            preferenceViewHolder.findViewById(
                                    com.android.settings.R.id.workProfileText);
            if (SemPersonaManager.isSecureFolderId(this.appInfo.userHandle.getIdentifier())) {
                textView2.setText(com.android.settings.R.string.secure_folder_title);
            } else if (SemDualAppManager.isDualAppId(this.appInfo.userHandle.getIdentifier())) {
                textView2.setText(com.android.settings.R.string.dual_app_title);
            } else {
                OtherBackend.OtherAppInfo otherAppInfo = this.appInfo;
                if (otherAppInfo.isManagedProfile) {
                    textView2.setText(com.android.settings.R.string.work_title);
                } else {
                    textView2.setText(otherAppInfo.userName);
                }
            }
            imageView.setImageDrawable(this.appInfo.icon);
            imageView.setTag(this.appInfo);
            imageView.setVisibility(0);
            imageView.setClipToOutline(true);
            textView2.setVisibility(this.appInfo.isCurrentUser ? 8 : 0);
        }

        @Override // androidx.preference.Preference
        public final void onClick() {
            Log.d("OtherSettings", "onClick()");
            if (this.checkbox == null) {
                Log.e("OtherSettings", "onClick dropped, cb null");
                return;
            }
            if (this.callback == null && OtherSettings.DBG) {
                Log.d("OtherSettings", "other payment callback is null");
            }
            AnonymousClass2 anonymousClass2 = this.callback;
            if (anonymousClass2 != null) {
                anonymousClass2.getClass();
                OtherBackend.OtherAppInfo otherAppInfo = this.appInfo;
                if (otherAppInfo == null) {
                    Log.e("OtherSettings", "appinfo null, dropping click event");
                    return;
                }
                ComponentName componentName = otherAppInfo.componentName;
                OtherSettings otherSettings = OtherSettings.this;
                if (componentName != null) {
                    if (otherAppInfo.isSelected) {
                        OtherBackend otherBackend = otherSettings.mOtherBackend;
                        int identifier = otherAppInfo.userHandle.getIdentifier();
                        if (OtherBackend.DBG) {
                            otherBackend.getClass();
                            Log.d(
                                    "OtherBackend",
                                    "setServiceEnabledForCategoryOther disable: " + componentName);
                        }
                        otherBackend.setServiceEnabledForCategoryOther(
                                componentName, false, identifier);
                    } else {
                        OtherBackend otherBackend2 = otherSettings.mOtherBackend;
                        int identifier2 = otherAppInfo.userHandle.getIdentifier();
                        if (OtherBackend.DBG) {
                            otherBackend2.getClass();
                            Log.d(
                                    "OtherBackend",
                                    "setServiceEnabledForCategoryOther enable: " + componentName);
                        }
                        otherBackend2.setServiceEnabledForCategoryOther(
                                componentName, true, identifier2);
                    }
                    boolean z = !otherAppInfo.isSelected;
                    otherAppInfo.isSelected = z;
                    this.checkbox.setChecked(z);
                }
                otherSettings.getClass();
                LoggingHelper.insertEventLogging(3659, 3656, otherAppInfo.isSelected ? 1L : 0L);
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
        return 3659;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x010d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getOtherApps(boolean r18) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.nfc.OtherSettings.getOtherApps(boolean):java.util.List");
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
        this.mOtherBackend = new OtherBackend(getActivity());
        new PaymentBackend(getActivity());
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        this.roleManager = (RoleManager) getSystemService(RoleManager.class);
        this.mScreen = getPreferenceManager().createPreferenceScreen(getActivity());
        SystemProperties.getBoolean("ro.vendor.nfc.support.nonaid", false);
        if (bundle != null) {
            this.mDoNotFinishDialog = bundle.getBoolean("do_not_finish_dialog");
        }
        if (this.mDoNotFinishDialog) {
            String string = bundle.getString("saved_conflict_dialog_msg");
            AlertDialog alertDialog = this.mAlertDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 5);
                builder.setTitle(com.android.settings.R.string.nfc_unable_service_title);
                if (TextUtils.isEmpty(string)) {
                    builder.setMessage(com.android.settings.R.string.nfc_unable_service_retry);
                } else {
                    builder.setMessage(
                            getString(
                                    com.android.settings.R.string.nfc_unable_service_retry3,
                                    string));
                }
                builder.setPositiveButton(
                        com.android.settings.R.string.btn_ok,
                        (DialogInterface.OnClickListener) null);
                AlertDialog create = builder.create();
                this.mAlertDialog = create;
                create.setCanceledOnTouchOutside(false);
                this.mAlertDialog.show();
                this.mSavedConflctDialogMsg = string;
            }
            this.mDoNotFinishDialog = false;
        }
        Log.d("OtherSettings", "onCreate() -- ");
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
                if (DBG) {
                    Log.d("OtherSettings", getActivity() + "unRegister Receiver");
                }
                getActivity().unregisterReceiver(this.mReceiver);
                this.mSettingsPackageMonitor.unregister();
                this.isRegistered = false;
            }
        } catch (IllegalStateException unused) {
            if (DBG) {
                Log.d("OtherSettings", getActivity() + "already unregistered");
            }
            this.isRegistered = false;
        }
        super.onDestroy();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        try {
            if (!this.isRegistered) {
                Log.d("OtherSettings", getActivity() + "register Receiver");
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
                activity2.registerReceiver(anonymousClass12, intentFilter2, 2);
                this.mSettingsPackageMonitor.register(
                        getActivity(), getActivity().getMainLooper(), false);
                this.isRegistered = true;
            }
        } catch (IllegalStateException unused) {
            Log.d("OtherSettings", getActivity() + "already registered");
            this.isRegistered = true;
        }
        sendEmptyMessage(0);
        setHasOptionsMenu(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mAlertDialog.dismiss();
        bundle.putBoolean("do_not_finish_dialog", true);
        bundle.putString("saved_conflict_dialog_msg", this.mSavedConflctDialogMsg);
    }
}
