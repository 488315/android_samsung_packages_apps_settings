package com.samsung.android.settings.bluetooth.controller;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothBroadcastAssistantController extends AbstractPreferenceController
        implements Preference.OnPreferenceClickListener, LifecycleObserver {
    public final CachedBluetoothDevice mCachedDevice;
    public PreferenceGroup mContainer;
    public final BluetoothDevice mDevice;
    public Preference mFindBroadcastPreference;
    public final PreferenceFragmentCompat mFragment;
    public final LocalBluetoothManager mManager;
    public final String mScreenId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.controller.SecBluetoothBroadcastAssistantController$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$bluetooth$SmepTag;

        static {
            int[] iArr = new int[SmepTag.values().length];
            $SwitchMap$com$samsung$android$bluetooth$SmepTag = iArr;
            try {
                iArr[SmepTag.FEATURE_AURACAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public SecBluetoothBroadcastAssistantController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            LocalBluetoothManager localBluetoothManager,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context);
        this.mManager = localBluetoothManager;
        this.mCachedDevice = cachedBluetoothDevice;
        this.mDevice = cachedBluetoothDevice.mDevice;
        this.mFragment = preferenceFragmentCompat;
        this.mScreenId =
                SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                        preferenceFragmentCompat, R.string.screen_find_a_broadcast);
        lifecycle.addObserver(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mContainer = (PreferenceGroup) preferenceScreen.findPreference("bluetooth_assistant");
        Preference findPreference =
                preferenceScreen.findPreference("bluetooth_pref_find_broadcast");
        this.mFindBroadcastPreference = findPreference;
        Drawable drawable = this.mContext.getDrawable(R.drawable.sec_bluetooth_2d_auracast);
        if (!TextUtils.isEmpty(null)) {
            findPreference.setTitle((CharSequence) null);
        }
        if (!TextUtils.isEmpty(null)) {
            findPreference.setSummary((CharSequence) null);
        }
        if (drawable != null) {
            drawable.setTint(
                    this.mContext.getResources().getColor(R.color.bt_device_icon_tint_color));
            findPreference.setIcon(drawable);
        }
        findPreference.setOnPreferenceClickListener(this);
        if (isAvailable()) {
            this.mContainer.setVisible(true);
        } else {
            this.mContainer.setVisible(false);
        }
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_assistant";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mManager.mProfileManager.mLeAudioBroadcastAssistant;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (!cachedBluetoothDevice.hasProfile(localBluetoothLeBroadcastAssistant)) {
            return false;
        }
        if (!cachedBluetoothDevice.isHearableUsingWearableManager()
                || !this.mDevice.isLeAudioDualMode()) {
            return true;
        }
        BluetoothDevice bluetoothDevice = this.mDevice;
        SmepTag smepTag = SmepTag.SUPPORTED_FEATURES;
        byte[] m = BluetoothUtils$$ExternalSyntheticOutline0.m(smepTag, bluetoothDevice);
        if (m == null || m.length < 5) {
            Log.e(
                    "SecBluetoothBroadcastAssistantController",
                    "isSupportAssistant: DataPacket is too short.");
            return false;
        }
        if ((((m[0] & 255) | ((m[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL)
                != smepTag.getTag()) {
            return false;
        }
        int i = 2;
        while (i < m.length) {
            int i2 = ((m[i] & 255) | ((m[i + 1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL;
            int i3 = m[i + 2] & 255;
            byte[] bArr = new byte[i3];
            System.arraycopy(m, i + 3, bArr, 0, i3);
            i += i3 + 3;
            if (AnonymousClass1.$SwitchMap$com$samsung$android$bluetooth$SmepTag[
                            SmepTag.getSmepKey(i2).ordinal()]
                    != 1) {
                Log.e(
                        "SecBluetoothBroadcastAssistantController",
                        "isSupportAssistant: NOT SUPPORTED FEATURE : "
                                .concat(String.format("%x", Integer.valueOf(i2))));
            } else {
                if (i3 < 1) {
                    Log.d(
                            "SecBluetoothBroadcastAssistantController",
                            "isSupportAssistant: wrong data");
                    return false;
                }
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("isSupportAssistant: data = "),
                        bArr[0],
                        "SecBluetoothBroadcastAssistantController");
                if (bArr[0] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.d("SecBluetoothBroadcastAssistantController", "onDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        isAvailable();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        Log.d(
                "SecBluetoothBroadcastAssistantController",
                "onPreferenceClick :: key = " + preference.getKey());
        if (!"bluetooth_pref_find_broadcast".equals(preference.getKey())) {
            return true;
        }
        SALogging.insertSALog(
                this.mScreenId,
                this.mContext
                        .getResources()
                        .getString(R.string.event_device_profiles_setting_find_broadcast_button));
        Intent intent = new Intent();
        intent.setAction("android.settings.LE_ASSISTANT_SETTINGS");
        intent.putExtra("device_address", this.mDevice.getAddress());
        this.mFragment.getActivity().startActivity(intent);
        return true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Log.d("SecBluetoothBroadcastAssistantController", "onResume");
        if (!isAvailable()) {
            this.mContainer.setVisible(false);
            return;
        }
        if (!this.mContainer.isVisible()) {
            this.mContainer.setVisible(true);
        }
        refresh();
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0046, code lost:

       if (r1.getConnectionStatus(r5.mDevice) == 2) goto L23;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refresh() {
        /*
            r5 = this;
            androidx.preference.Preference r0 = r5.mFindBroadcastPreference
            if (r0 == 0) goto L4d
            com.android.settingslib.bluetooth.LocalBluetoothManager r1 = r5.mManager
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r1 = r1.mProfileManager
            com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant r1 = r1.mLeAudioBroadcastAssistant
            if (r1 == 0) goto L49
            com.android.settingslib.bluetooth.CachedBluetoothDevice r5 = r5.mCachedDevice
            android.bluetooth.BluetoothDevice r2 = r5.mDevice
            int r2 = r1.getConnectionStatus(r2)
            r3 = 1
            r4 = 2
            if (r2 != r4) goto L19
            goto L4a
        L19:
            java.util.Set r2 = r5.mMemberDevices
            int r2 = r2.size()
            if (r2 <= 0) goto L3c
            java.util.Set r5 = r5.mMemberDevices
            java.util.Iterator r5 = r5.iterator()
        L27:
            boolean r2 = r5.hasNext()
            if (r2 == 0) goto L49
            java.lang.Object r2 = r5.next()
            com.android.settingslib.bluetooth.CachedBluetoothDevice r2 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r2
            android.bluetooth.BluetoothDevice r2 = r2.mDevice
            int r2 = r1.getConnectionStatus(r2)
            if (r2 != r4) goto L27
            goto L4a
        L3c:
            com.android.settingslib.bluetooth.CachedBluetoothDevice r5 = r5.mLeadDevice
            if (r5 == 0) goto L49
            android.bluetooth.BluetoothDevice r5 = r5.mDevice
            int r5 = r1.getConnectionStatus(r5)
            if (r5 != r4) goto L49
            goto L4a
        L49:
            r3 = 0
        L4a:
            r0.setEnabled(r3)
        L4d:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.controller.SecBluetoothBroadcastAssistantController.refresh():void");
    }
}
