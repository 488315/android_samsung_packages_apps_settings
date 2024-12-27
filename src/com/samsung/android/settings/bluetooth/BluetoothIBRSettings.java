package com.samsung.android.settings.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDump;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothIBRSettings extends BluetoothFunctionSettingsBase {
    public final AnonymousClass1 mDialogListener = new AnonymousClass1();
    public LocalBluetoothAdapter mLocalBluetoothAdapter;
    public String mScreenId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.BluetoothIBRSettings$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    static {
        Debug.semIsProductDev();
    }

    public static boolean isInBandRingtoneEnabled$1() {
        boolean z = !SystemProperties.getBoolean("persist.bluetooth.disableinbandringing", false);
        BluetoothDump.BtLog("HFIBR-" + z);
        return z;
    }

    public static boolean isSupportSoftPhone() {
        if (!SystemProperties.get("ro.build.characteristics", ApnSettings.MVNO_NONE).contains("tablet")) {
            return false;
        }
        String str = SystemProperties.get("persist.omc.sales_code", ApnSettings.MVNO_NONE);
        if (TextUtils.isEmpty(str)) {
            str = SystemProperties.get("ro.csc.sales_code", ApnSettings.MVNO_NONE);
        }
        return TextUtils.equals("ATT", str) || TextUtils.equals("APP", str);
    }

    @Override // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("BluetoothIBRSettings", "onConfigurationChanged :: ");
    }

    @Override // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            this.mLocalBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        } else {
            Log.e("BluetoothIBRSettings", "onCreate :: Can't get Local Bluetooth Manager instance");
        }
        setHasOptionsMenu(true);
        this.mScreenId = getResources().getString(R.string.screen_in_band_ringtone_setting);
        final int i = Utils.isTablet() ? R.anim.sec_bluetooth_ibr_tablet : R.anim.sec_bluetooth_ibr;
        new Thread(
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x003c: INVOKE 
              (wrap:java.lang.Thread:0x0039: CONSTRUCTOR 
              (wrap:java.lang.Runnable:0x0036: CONSTRUCTOR 
              (r2v0 'this' com.samsung.android.settings.bluetooth.BluetoothIBRSettings A[DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r3v8 'i' int A[DONT_INLINE])
             A[MD:(com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase, int):void (m), WRAPPED] (LINE:55) call: com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase.1.<init>(com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase, int):void type: CONSTRUCTOR)
             A[MD:(java.lang.Runnable):void (c), WRAPPED] (LINE:58) call: java.lang.Thread.<init>(java.lang.Runnable):void type: CONSTRUCTOR)
             VIRTUAL call: java.lang.Thread.start():void A[MD:():void (c)] (LINE:61) in method: com.samsung.android.settings.bluetooth.BluetoothIBRSettings.onCreate(android.os.Bundle):void, file: classes4.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase, state: NOT_LOADED
            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:782)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:97)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:878)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
            	... 15 more
            */
        /*
            this = this;
            super.onCreate(r3)
            com.android.settingslib.bluetooth.LocalBluetoothManager r3 = r2.mLocalBluetoothManager
            if (r3 == 0) goto Lc
            com.android.settingslib.bluetooth.LocalBluetoothAdapter r3 = r3.mLocalAdapter
            r2.mLocalBluetoothAdapter = r3
            goto L14
        Lc:
            java.lang.String r3 = "BluetoothIBRSettings"
            java.lang.String r0 = "onCreate :: Can't get Local Bluetooth Manager instance"
            android.util.Log.e(r3, r0)
        L14:
            r3 = 1
            r2.setHasOptionsMenu(r3)
            android.content.res.Resources r3 = r2.getResources()
            r0 = 2132025272(0x7f141fb8, float:1.9689044E38)
            java.lang.String r3 = r3.getString(r0)
            r2.mScreenId = r3
            boolean r3 = com.android.settings.Utils.isTablet()
            if (r3 == 0) goto L2f
            r3 = 2130772036(0x7f010044, float:1.714718E38)
            goto L32
        L2f:
            r3 = 2130772035(0x7f010043, float:1.7147177E38)
        L32:
            java.lang.Thread r0 = new java.lang.Thread
            com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase$1 r1 = new com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase$1
            r1.<init>(r2, r3)
            r0.<init>(r1)
            r0.start()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.bluetooth.BluetoothIBRSettings.onCreate(android.os.Bundle):void");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            SALogging.insertSALog(this.mScreenId, getResources().getString(R.string.event_in_band_ringtone_setting_navigate_button));
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        BluetoothFunctionSettingsBase.FunctionEnabler functionEnabler = this.mFunctionEnabler;
        SettingsMainSwitchBar settingsMainSwitchBar = functionEnabler.mSwitchBar;
        if (settingsMainSwitchBar == null) {
            Log.e("BluetoothFunctionSettingsBase", "pause :: mSwitchBar is null");
        } else if (!functionEnabler.isSwitchListenerRegistered) {
            Log.e("BluetoothFunctionSettingsBase", "pause :: Switch change listener is not registered");
        } else {
            settingsMainSwitchBar.removeOnSwitchChangeListener(functionEnabler);
            functionEnabler.isSwitchListenerRegistered = false;
        }
    }

    @Override // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateContentsView();
        this.mFunctionEnabler.resume();
        SALogging.insertSALog(this.mScreenId);
    }

    public final void setInBandRingtone(boolean z) {
        Log.e("BluetoothIBRSettings", "setInBandRingtone :: enable = " + z);
        SystemProperties.set("persist.bluetooth.disableinbandringing", !z ? "true" : "false");
        BluetoothDump.BtLog("HFIBR-set " + z);
        BluetoothFunctionSettingsBase.FunctionEnabler functionEnabler = this.mFunctionEnabler;
        SettingsMainSwitchBar settingsMainSwitchBar = functionEnabler.mSwitchBar;
        if (settingsMainSwitchBar == null) {
            Log.e("BluetoothFunctionSettingsBase", "pause :: mSwitchBar is null");
        } else if (functionEnabler.isSwitchListenerRegistered) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(functionEnabler);
            functionEnabler.isSwitchListenerRegistered = false;
        } else {
            Log.e("BluetoothFunctionSettingsBase", "pause :: Switch change listener is not registered");
        }
        SettingsMainSwitchBar settingsMainSwitchBar2 = functionEnabler.mSwitchBar;
        if (settingsMainSwitchBar2 != null) {
            settingsMainSwitchBar2.setChecked(z);
        } else {
            Log.e("BluetoothFunctionSettingsBase", "setChecked :: mSwitchBar is null");
        }
        functionEnabler.resume();
    }

    @Override // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase
    public final void switchStateChange(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("switchStateChange :: isChecked = ", "BluetoothIBRSettings", z);
        if ((z && isInBandRingtoneEnabled$1()) || (!z && !isInBandRingtoneEnabled$1())) {
            Log.e("BluetoothIBRSettings", "switchStateChange :: It is not user interaction");
            return;
        }
        SALogging.insertSALog(this.mScreenId, getResources().getString(R.string.event_in_band_ringtone_setting_on_off_switch), getResources().getString(z ? R.string.detail_switch_on : R.string.detail_switch_off));
        int connectionState = this.mLocalBluetoothAdapter.mAdapter.getConnectionState();
        if (connectionState != 2 && connectionState != 1) {
            setInBandRingtone(z);
            return;
        }
        boolean z2 = !z;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mFunctionEnabler.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(z2);
        } else {
            Log.e("BluetoothFunctionSettingsBase", "setChecked :: mSwitchBar is null");
        }
        Log.d("BluetoothIBRSettings", "launchIBRAskPopup :: ");
        Bundle bundle = new Bundle();
        bundle.putString(UniversalCredentialUtil.AGENT_TITLE, getResources().getString(R.string.sec_bluetooth_ibr_ask_popup_title));
        bundle.putString(PhoneRestrictionPolicy.BODY, getResources().getString(R.string.sec_bluetooth_ibr_ask_popup_content));
        bundle.putString("pos_button", getResources().getString(R.string.common_ok));
        bundle.putString("neg_button", getResources().getString(R.string.common_cancel));
        this.mDialogCallback = this.mDialogListener;
        getDialogBuilder().setTitle(bundle.getString(UniversalCredentialUtil.AGENT_TITLE, ApnSettings.MVNO_NONE)).setMessage(bundle.getString(PhoneRestrictionPolicy.BODY, ApnSettings.MVNO_NONE));
        if (bundle.containsKey("pos_button")) {
            getDialogBuilder().setPositiveButton(bundle.getString("pos_button", " "), this.mClickListener);
        }
        if (bundle.containsKey("neg_button")) {
            getDialogBuilder().setNegativeButton(bundle.getString("neg_button", " "), this.mClickListener);
        }
        getDialogBuilder().setOnCancelListener(this.mCancelListener).setOnDismissListener(this.mDismissListener);
        AlertDialog create = getDialogBuilder().create();
        this.mAlertDialog = create;
        create.setCanceledOnTouchOutside(true);
        this.mAlertDialog.show();
    }

    @Override // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase
    public final void updateContentsView() {
        Log.d("BluetoothIBRSettings", "updateContentsView :: refresh contents");
        boolean isInBandRingtoneEnabled$1 = isInBandRingtoneEnabled$1();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mFunctionEnabler.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(isInBandRingtoneEnabled$1);
        } else {
            Log.e("BluetoothFunctionSettingsBase", "setChecked :: mSwitchBar is null");
        }
        boolean isTablet = Utils.isTablet();
        ImageView imageView = (ImageView) getView().findViewById(R.id.image);
        Drawable drawable = this.mAnimResource;
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
            AnimationDrawable animationDrawable = (AnimationDrawable) this.mAnimResource;
            if (animationDrawable != null) {
                animationDrawable.start();
            } else {
                Log.e("BluetoothIBRSettings", "updateContentsView :: failed to get animation drawble..");
            }
        } else if (isTablet) {
            imageView.setImageResource(R.drawable.sec_bluetooth_ibr_img_tablet_01);
        } else {
            imageView.setImageResource(R.drawable.sec_bluetooth_ibr_img_01);
        }
        TextView textView = (TextView) getView().findViewById(R.id.content);
        StringBuilder sb = new StringBuilder();
        if (isTablet) {
            sb.append(getText(R.string.sec_bluetooth_ibr_description_tablet));
        } else {
            sb.append(getText(R.string.sec_bluetooth_ibr_description));
        }
        textView.setText(sb.toString());
    }
}
