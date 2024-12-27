package com.android.settings.connecteddevice.usb;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbManager;
import android.net.TetheringManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.util.Log;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.development.DeveloperOptionAwareMixin;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.google.android.collect.Lists;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UsbDefaultFragment extends RadioButtonPickerFragment
        implements DeveloperOptionAwareMixin {
    long mCurrentFunctions;
    Handler mHandler;
    long mPreviousFunctions;
    TetheringManager mTetheringManager;
    UsbBackend mUsbBackend;
    public UsbConnectionBroadcastReceiver mUsbReceiver;
    OnStartTetheringCallback mOnStartTetheringCallback = new OnStartTetheringCallback();
    boolean mIsStartTethering = false;
    public boolean mIsConnected = false;
    UsbConnectionBroadcastReceiver.UsbConnectionListener mUsbConnectionListener =
            new UsbConnectionBroadcastReceiver.UsbConnectionListener() { // from class:
                // com.android.settings.connecteddevice.usb.UsbDefaultFragment$$ExternalSyntheticLambda0
                @Override // com.android.settings.connecteddevice.usb.UsbConnectionBroadcastReceiver.UsbConnectionListener
                public final void onUsbConnectionChanged(
                        boolean z, long j, int i, int i2, boolean z2) {
                    UsbDefaultFragment usbDefaultFragment = UsbDefaultFragment.this;
                    long screenUnlockedFunctions =
                            usbDefaultFragment.mUsbBackend.mUsbManager.getScreenUnlockedFunctions();
                    Log.d(
                            "UsbDefaultFragment",
                            "UsbConnectionListener() connected : "
                                    + z
                                    + ", functions : "
                                    + j
                                    + ", defaultFunctions : "
                                    + screenUnlockedFunctions
                                    + ", mIsStartTethering : "
                                    + usbDefaultFragment.mIsStartTethering
                                    + ", isUsbConfigured : "
                                    + z2);
                    long j2 = 1024;
                    if (z
                            && !usbDefaultFragment.mIsConnected
                            && ((screenUnlockedFunctions == 32 || screenUnlockedFunctions == 1024)
                                    && screenUnlockedFunctions == j
                                    && !usbDefaultFragment.mIsStartTethering)) {
                        usbDefaultFragment.mCurrentFunctions = screenUnlockedFunctions;
                        Log.d("UsbDefaultFragment", "startTethering()");
                        usbDefaultFragment.mIsStartTethering = true;
                        usbDefaultFragment.mTetheringManager.startTethering(
                                1,
                                new HandlerExecutor(usbDefaultFragment.mHandler),
                                usbDefaultFragment.mOnStartTetheringCallback);
                    }
                    if ((usbDefaultFragment.mIsStartTethering || z2) && z) {
                        usbDefaultFragment.mCurrentFunctions = j;
                        PreferenceScreen preferenceScreen =
                                usbDefaultFragment.getPreferenceScreen();
                        FragmentActivity activity = usbDefaultFragment.getActivity();
                        int i3 = ConnectionsUtils.$r8$clinit;
                        boolean z3 =
                                Utils.getEnterprisePolicyEnabled(
                                                activity,
                                                "content://com.sec.knox.provider/RestrictionPolicy4",
                                                "isUsbTetheringEnabled")
                                        == 1;
                        Log.i(
                                "UsbDefaultFragment",
                                "refresh() current functions : "
                                        + j
                                        + ", TetheringEnabled : "
                                        + z3);
                        Iterator it =
                                ((LinkedHashMap) UsbDetailsFunctionsController.FUNCTIONS_MAP)
                                        .keySet()
                                        .iterator();
                        while (it.hasNext()) {
                            long longValue = ((Long) it.next()).longValue();
                            SelectorWithWidgetPreference selectorWithWidgetPreference =
                                    (SelectorWithWidgetPreference)
                                            preferenceScreen.findPreference(
                                                    Long.toBinaryString(longValue));
                            if (selectorWithWidgetPreference != null) {
                                boolean areFunctionsSupported =
                                        usbDefaultFragment.mUsbBackend.areFunctionsSupported(
                                                longValue);
                                selectorWithWidgetPreference.setEnabled(areFunctionsSupported);
                                if (areFunctionsSupported) {
                                    if (j == j2) {
                                        selectorWithWidgetPreference.setChecked(32 == longValue);
                                    } else {
                                        selectorWithWidgetPreference.setChecked(j == longValue);
                                    }
                                }
                                if (longValue == 4
                                        && Utils.hasPackage(
                                                usbDefaultFragment.getActivity(),
                                                "com.google.android.projection.gearhead")) {
                                    selectorWithWidgetPreference.setTitle(
                                            R.string.usb_use_file_transfers_android_auto);
                                }
                                if (longValue == 32 && !z3) {
                                    Log.i(
                                            "UsbDefaultFragment",
                                            "refresh() UsbTethering disabled : " + z3);
                                    selectorWithWidgetPreference.setEnabled(false);
                                    j2 = 1024;
                                }
                            }
                            j2 = 1024;
                        }
                        usbDefaultFragment.mIsStartTethering = false;
                    }
                    usbDefaultFragment.mIsConnected = z;
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OnStartTetheringCallback implements TetheringManager.StartTetheringCallback {
        public OnStartTetheringCallback() {}

        public final void onTetheringFailed(int i) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    i, "onTetheringFailed() error : ", "UsbDefaultFragment");
            UsbDefaultFragment usbDefaultFragment = UsbDefaultFragment.this;
            UsbBackend usbBackend = usbDefaultFragment.mUsbBackend;
            usbBackend.mUsbManager.setScreenUnlockedFunctions(
                    usbDefaultFragment.mPreviousFunctions);
            UsbDefaultFragment.this.updateCandidates();
        }

        public final void onTetheringStarted() {
            UsbDefaultFragment usbDefaultFragment = UsbDefaultFragment.this;
            usbDefaultFragment.mCurrentFunctions =
                    usbDefaultFragment.mUsbBackend.mUsbManager.getCurrentFunctions();
            Log.d(
                    "UsbDefaultFragment",
                    "onTetheringStarted() : mCurrentFunctions "
                            + UsbDefaultFragment.this.mCurrentFunctions);
            UsbDefaultFragment usbDefaultFragment2 = UsbDefaultFragment.this;
            usbDefaultFragment2.mUsbBackend.mUsbManager.setScreenUnlockedFunctions(
                    usbDefaultFragment2.mCurrentFunctions);
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList newArrayList = Lists.newArrayList();
        for (Long l : ((LinkedHashMap) UsbDetailsFunctionsController.FUNCTIONS_MAP).keySet()) {
            long longValue = l.longValue();
            final String string =
                    getContext()
                            .getString(
                                    ((Integer)
                                                    ((LinkedHashMap)
                                                                    UsbDetailsFunctionsController
                                                                            .FUNCTIONS_MAP)
                                                            .get(l))
                                            .intValue());
            final String binaryString = Long.toBinaryString(longValue);
            if (longValue == 0 && !this.mUsbBackend.supportUSBDebuggingMenu(getActivity())) {
                Log.d("UsbDefaultFragment", "Skip Debugging only");
            } else if (this.mUsbBackend.areFunctionsSupported(longValue)) {
                newArrayList.add(
                        new CandidateInfo() { // from class:
                                              // com.android.settings.connecteddevice.usb.UsbDefaultFragment.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(true);
                            }

                            @Override // com.android.settingslib.widget.CandidateInfo
                            public final String getKey() {
                                return binaryString;
                            }

                            @Override // com.android.settingslib.widget.CandidateInfo
                            public final Drawable loadIcon() {
                                return null;
                            }

                            @Override // com.android.settingslib.widget.CandidateInfo
                            public final CharSequence loadLabel() {
                                return string;
                            }
                        });
            }
        }
        return newArrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        long screenUnlockedFunctions = this.mUsbBackend.mUsbManager.getScreenUnlockedFunctions();
        if (screenUnlockedFunctions == 4194308) {
            screenUnlockedFunctions = 4;
        }
        Log.i("UsbDefaultFragment", "getDefaultKey : " + screenUnlockedFunctions);
        if (screenUnlockedFunctions == 1024) {
            screenUnlockedFunctions = 32;
        }
        return Long.toBinaryString(screenUnlockedFunctions);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1312;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.usb_default_fragment;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mUsbBackend = new UsbBackend(context);
        this.mTetheringManager =
                (TetheringManager) context.getSystemService(TetheringManager.class);
        this.mUsbReceiver =
                new UsbConnectionBroadcastReceiver(
                        context, this.mUsbConnectionListener, this.mUsbBackend);
        this.mHandler = new Handler(context.getMainLooper());
        getSettingsLifecycle().addObserver(this.mUsbReceiver);
        this.mCurrentFunctions = this.mUsbBackend.mUsbManager.getScreenUnlockedFunctions();
        Log.d("UsbDefaultFragment", "onAttach mCurrentFunctions : " + this.mCurrentFunctions);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        SecUnclickablePreference secUnclickablePreference =
                new SecUnclickablePreference(getActivity());
        secUnclickablePreference.setTitle(R.string.usb_default_info);
        getPreferenceScreen().addPreference(secUnclickablePreference);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        long currentFunctions = this.mUsbBackend.mUsbManager.getCurrentFunctions();
        this.mUsbBackend.getClass();
        if (UsbManager.areSettableFunctions(currentFunctions)) {
            this.mCurrentFunctions = currentFunctions;
        }
        Log.d(
                "UsbDefaultFragment",
                "onPause() : current functions : "
                        + this.mCurrentFunctions
                        + " / temp current functions : "
                        + currentFunctions);
        this.mUsbBackend.mUsbManager.setScreenUnlockedFunctions(this.mCurrentFunctions);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        long parseLong = Long.parseLong(str, 2);
        Log.i("UsbDefaultFragment", "setDefaultKey start : " + parseLong);
        this.mPreviousFunctions = this.mUsbBackend.mUsbManager.getCurrentFunctions();
        StringBuilder sb = Utils.sBuilder;
        if (!ActivityManager.isUserAMonkey()) {
            if (parseLong == 32 || parseLong == 1024) {
                this.mCurrentFunctions = parseLong;
                Log.d("UsbDefaultFragment", "startTethering()");
                this.mIsStartTethering = true;
                this.mTetheringManager.startTethering(
                        1, new HandlerExecutor(this.mHandler), this.mOnStartTetheringCallback);
            } else {
                this.mIsStartTethering = false;
                this.mCurrentFunctions = parseLong;
                Log.i("UsbDefaultFragment", "setDefault function : " + parseLong);
                if (parseLong == 4) {
                    Settings.System.putInt(
                            getActivity().getContentResolver(), "enable_mtp_settings", 1);
                }
                this.mUsbBackend.mUsbManager.setScreenUnlockedFunctions(parseLong);
            }
        }
        return true;
    }
}
