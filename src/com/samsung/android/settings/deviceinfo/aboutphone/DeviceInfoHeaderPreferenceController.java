package com.samsung.android.settings.deviceinfo.aboutphone;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DeviceInfoHeaderPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                OnStart,
                OnStop,
                OnResume,
                DeviceNameEditor.ConfirmationDialogFragmentListener {
    public static final Uri SINGLE_URI =
            Uri.parse(
                    "content://com.samsung.android.mobileservice.profileProvider/new_profile_single");
    public final Activity mActivity;
    public final Context mContext;
    public DeviceInfoHeader mDeviceInfoHeader;
    public Button mDeviceNameEditBtn;
    public DeviceNameEditor mDeviceNameEditor;
    public AnonymousClass2 mDeviceNameObserver;
    public TextView mDeviceNameView;
    public final Fragment mFragment;
    public boolean mHasEditDeviceNameExtra;

    public DeviceInfoHeaderPreferenceController(
            Context context,
            FragmentActivity fragmentActivity,
            Fragment fragment,
            Lifecycle lifecycle) {
        super(context);
        this.mHasEditDeviceNameExtra = false;
        this.mContext = context;
        this.mActivity = fragmentActivity;
        this.mFragment = fragment;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (((LayoutPreference) preferenceScreen.findPreference("sec_device_info_header"))
                == null) {
            SecInsetCategoryPreference secInsetCategoryPreference =
                    new SecInsetCategoryPreference(this.mContext);
            secInsetCategoryPreference.setHeight(1);
            secInsetCategoryPreference.seslSetSubheaderRoundedBackground(0);
            secInsetCategoryPreference.setOrder(-2);
            preferenceScreen.addPreference(secInsetCategoryPreference);
            Context context = this.mContext;
            Context context2 = this.mContext;
            DeviceInfoHeader deviceInfoHeader = new DeviceInfoHeader(context2);
            this.mDeviceInfoHeader = deviceInfoHeader;
            deviceInfoHeader.initInfoChart();
            deviceInfoHeader.mDeviceName =
                    (TextView) deviceInfoHeader.mView.findViewById(R.id.device_name);
            Button button =
                    (Button) deviceInfoHeader.mView.findViewById(R.id.device_name_edit_button);
            deviceInfoHeader.mDeviceNameEdit = button;
            button.setEnabled(true);
            if (SystemProperties.getInt("persist.sys.iss.flag_altermodel", 0) == 1) {
                deviceInfoHeader.mDeviceName.setVisibility(8);
                deviceInfoHeader.mDeviceNameEdit.setVisibility(8);
            }
            if (KnoxUtils.isApplicationRestricted(context2, "device_name_edit", "grayout")) {
                deviceInfoHeader.mDeviceNameEdit.setEnabled(false);
                deviceInfoHeader.mDeviceNameEdit.setAlpha(0.4f);
            }
            deviceInfoHeader.initDeviceImage();
            View view = deviceInfoHeader.mView;
            DeviceInfoHeader deviceInfoHeader2 = this.mDeviceInfoHeader;
            TextView textView = deviceInfoHeader2.mDeviceName;
            this.mDeviceNameView = textView;
            textView.setText(deviceInfoHeader2.getDeviceName());
            Button button2 = this.mDeviceInfoHeader.mDeviceNameEdit;
            this.mDeviceNameEditBtn = button2;
            button2.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeaderPreferenceController.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            if (DeviceInfoHeaderPreferenceController.this.mFragment.isResumed()) {
                                DeviceInfoHeaderPreferenceController.this
                                        .showDeviceNameEditorDialog();
                            }
                        }
                    });
            Preference layoutPreference = new LayoutPreference(context, view, 262144);
            layoutPreference.setKey("sec_device_info_header");
            layoutPreference.setSelectable(false);
            layoutPreference.setOrder(-1);
            preferenceScreen.addPreference(layoutPreference);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "sec_device_info_header";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        DeviceInfoHeader deviceInfoHeader;
        Activity activity = this.mActivity;
        if (activity != null
                && activity.getIntent() != null
                && activity.getIntent().getExtras() != null) {
            boolean z =
                    activity.getIntent().getBooleanExtra("edit_device_name", false)
                            || "edit_device_name"
                                    .equals(
                                            activity.getIntent()
                                                    .getExtras()
                                                    .getString(
                                                            ":settings:fragment_args_key",
                                                            ApnSettings.MVNO_NONE));
            this.mHasEditDeviceNameExtra = z;
            if (z) {
                showDeviceNameEditorDialog();
            }
        }
        TextView textView = this.mDeviceNameView;
        if (textView == null || (deviceInfoHeader = this.mDeviceInfoHeader) == null) {
            return;
        }
        textView.setText(deviceInfoHeader.getDeviceName());
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x008a, code lost:

       if (r10 != null) goto L25;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x008c, code lost:

       r10.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x009f, code lost:

       if (android.text.TextUtils.isEmpty(r3) != false) goto L39;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00a1, code lost:

       android.provider.Settings.Global.putString(r0, "synced_account_name", r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0098, code lost:

       if (0 == 0) goto L29;
    */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeaderPreferenceController$2] */
    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStart() {
        /*
            r11 = this;
            android.content.Context r0 = r11.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "device_name"
            android.net.Uri r1 = android.provider.Settings.Global.getUriFor(r1)
            com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeaderPreferenceController$2 r2 = r11.mDeviceNameObserver
            if (r2 != 0) goto L20
            com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeaderPreferenceController$2 r2 = new com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeaderPreferenceController$2
            android.os.Handler r3 = new android.os.Handler
            android.os.Looper r4 = android.os.Looper.getMainLooper()
            r3.<init>(r4)
            r2.<init>(r3)
            r11.mDeviceNameObserver = r2
        L20:
            com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeaderPreferenceController$2 r2 = r11.mDeviceNameObserver
            r3 = -2
            r4 = 1
            r0.registerContentObserver(r1, r4, r2, r3)
            android.content.Context r0 = r11.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "synced_account_name"
            java.lang.String r2 = android.provider.Settings.Global.getString(r0, r1)
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto Lab
            android.content.Context r2 = r11.mContext
            android.accounts.AccountManager r2 = android.accounts.AccountManager.get(r2)
            java.lang.String r3 = "com.osp.app.signin"
            android.accounts.Account[] r2 = r2.getAccountsByType(r3)
            if (r2 == 0) goto Lab
            int r2 = r2.length
            if (r2 <= 0) goto Lab
            android.content.Context r11 = r11.mContext
            java.lang.String r2 = "PrefControllerMixin"
            java.lang.String r3 = ""
            android.content.ContentResolver r4 = r11.getContentResolver()
            java.lang.String r11 = "account_given_name"
            java.lang.String[] r6 = new java.lang.String[]{r11}
            r10 = 0
            android.net.Uri r5 = com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeaderPreferenceController.SINGLE_URI     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            r7 = 0
            r8 = 0
            r9 = 0
            android.database.Cursor r10 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            if (r10 == 0) goto L85
            int r4 = r10.getCount()     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            if (r4 == 0) goto L85
            boolean r4 = r10.moveToFirst()     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            if (r4 == 0) goto L85
            java.lang.String r4 = "Success Getting SA Names"
            android.util.Log.i(r2, r4)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            int r11 = r10.getColumnIndex(r11)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            java.lang.String r3 = r10.getString(r11)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            goto L8a
        L81:
            r11 = move-exception
            goto La5
        L83:
            r11 = move-exception
            goto L90
        L85:
            java.lang.String r11 = "Fail Getting SA Names"
            android.util.Log.i(r2, r11)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
        L8a:
            if (r10 == 0) goto L9b
        L8c:
            r10.close()
            goto L9b
        L90:
            java.lang.String r4 = "Query Failed!"
            android.util.Log.d(r2, r4)     // Catch: java.lang.Throwable -> L81
            r11.printStackTrace()     // Catch: java.lang.Throwable -> L81
            if (r10 == 0) goto L9b
            goto L8c
        L9b:
            boolean r11 = android.text.TextUtils.isEmpty(r3)
            if (r11 != 0) goto Lab
            android.provider.Settings.Global.putString(r0, r1, r3)
            goto Lab
        La5:
            if (r10 == 0) goto Laa
            r10.close()
        Laa:
            throw r11
        Lab:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeaderPreferenceController.onStart():void");
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        if (this.mDeviceNameObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mDeviceNameObserver);
            this.mDeviceNameObserver = null;
        }
    }

    public final void showDeviceNameEditorDialog() {
        if (this.mDeviceNameEditor == null) {
            DeviceNameEditor deviceNameEditor = new DeviceNameEditor();
            Bundle bundle = new Bundle();
            bundle.putInt(UniversalCredentialUtil.AGENT_TITLE, R.string.phone_name_title);
            deviceNameEditor.setArguments(bundle);
            this.mDeviceNameEditor = deviceNameEditor;
            deviceNameEditor.listener = this;
            if (this.mHasEditDeviceNameExtra) {
                this.mDeviceNameEditBtn
                        .getViewTreeObserver()
                        .addOnGlobalLayoutListener(
                                new ViewTreeObserver
                                        .OnGlobalLayoutListener() { // from class:
                                                                    // com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeaderPreferenceController.3
                                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                                    public final void onGlobalLayout() {
                                        DeviceInfoHeaderPreferenceController.this
                                                .mDeviceNameEditBtn
                                                .getViewTreeObserver()
                                                .removeOnGlobalLayoutListener(this);
                                        DeviceInfoHeaderPreferenceController
                                                deviceInfoHeaderPreferenceController =
                                                        DeviceInfoHeaderPreferenceController.this;
                                        DeviceNameEditor deviceNameEditor2 =
                                                deviceInfoHeaderPreferenceController
                                                        .mDeviceNameEditor;
                                        deviceNameEditor2.mAnchorView =
                                                deviceInfoHeaderPreferenceController
                                                        .mDeviceNameEditBtn;
                                        deviceNameEditor2.show(
                                                deviceInfoHeaderPreferenceController.mFragment
                                                        .getFragmentManager(),
                                                "dialog");
                                    }
                                });
            } else {
                deviceNameEditor.mAnchorView = this.mDeviceNameEditBtn;
                deviceNameEditor.show(this.mFragment.getFragmentManager(), "dialog");
            }
        }
    }
}
