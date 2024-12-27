package com.samsung.android.settings.bluetooth.lebroadcast;

import android.bluetooth.BluetoothDump;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothLeBroadcastSourceCompatibilityController extends BasePreferenceController
        implements Preference.OnPreferenceClickListener {
    private static final String BDUMP_TAG = "BSCC-";
    private static final boolean DEBUG = Debug.semIsProductDev();
    private static final String TAG = "SecBluetoothLeBroadcastSourceCompatibilityController";
    private float LAYOUT_DIMMING_SCALE_OFF;
    private float LAYOUT_DIMMING_SCALE_ON;
    private String autoSwitchKey;
    private Context gcontext;
    private LayoutPreference mLayoutPreference;
    private LeAudioProfile mLeAudioProfile;
    private LocalBluetoothLeBroadcastAssistant mLocalLeAssistant;
    private LocalBluetoothManager mLocalManager;
    private String mPrefKey;
    private int mPreferredQuality;
    private Switch mQualitySwitch;
    private TextView mSubTextView;
    private LinearLayout mSwitchHeaderLayout;
    private TextView mTitleTextView;
    private String qualityPrefKey;
    private SharedPreferences sharedPrefs;
    private String talkbackOff;
    private String talkbackOn;

    public SecBluetoothLeBroadcastSourceCompatibilityController(Context context, String str) {
        super(context, str);
        this.qualityPrefKey = "CompatibilitySwitchState";
        this.autoSwitchKey = "CompatibilityAutoSwitch";
        this.LAYOUT_DIMMING_SCALE_ON = 1.0f;
        this.LAYOUT_DIMMING_SCALE_OFF = 0.4f;
        this.mPreferredQuality = 1;
        this.gcontext = context;
        this.mPrefKey = str;
        this.talkbackOn = context.getResources().getString(R.string.switch_on_text);
        this.talkbackOff = this.gcontext.getResources().getString(R.string.switch_off_text);
    }

    private boolean getBroadcastQualityAutoSwitch() {
        return this.sharedPrefs.getBoolean(this.autoSwitchKey, false);
    }

    private boolean getImproveCompatibilitySwitchState() {
        return this.sharedPrefs.getBoolean(this.qualityPrefKey, false);
    }

    private boolean isValidHearingAid() {
        LeAudioProfile leAudioProfile = this.mLeAudioProfile;
        if (leAudioProfile == null) {
            return false;
        }
        List activeDevices = leAudioProfile.getActiveDevices();
        Iterator it =
                ((ArrayList) this.mLocalManager.mCachedDeviceManager.getCachedDevicesCopy())
                        .iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
            if (activeDevices.contains(cachedBluetoothDevice.mDevice)
                    && cachedBluetoothDevice.isHearingAidDevice()) {
                if (cachedBluetoothDevice.getProfileConnectionState(this.mLocalLeAssistant) == 2) {
                    Log.d(
                            TAG,
                            "Hearing aid assistant is connected with "
                                    + cachedBluetoothDevice.getIdentityAddressForLog());
                    return true;
                }
                Iterator it2 = cachedBluetoothDevice.mMemberDevices.iterator();
                while (it2.hasNext()) {
                    if (((CachedBluetoothDevice) it2.next())
                                    .getProfileConnectionState(this.mLocalLeAssistant)
                            == 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeTalkback(String str) {
        AccessibilityEvent obtain;
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) this.gcontext.getSystemService("accessibility");
        if (accessibilityManager == null
                || !accessibilityManager.isEnabled()
                || (obtain = AccessibilityEvent.obtain()) == null) {
            return;
        }
        obtain.setEventType(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        obtain.getText().add(str);
        accessibilityManager.sendAccessibilityEvent(obtain);
        obtain.recycle();
    }

    private void saveBroadcastQualityAutoSwitch(boolean z) {
        SharedPreferences.Editor edit = this.sharedPrefs.edit();
        edit.putBoolean(this.autoSwitchKey, z);
        edit.commit();
        BluetoothDump.BtLog("BSCC-autoswitch : " + z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImproveCompatibilitySwitchState(boolean z) {
        SharedPreferences.Editor edit = this.sharedPrefs.edit();
        edit.putBoolean(this.qualityPrefKey, z);
        edit.commit();
        BluetoothDump.BtLog("BSCC-switch state : " + z);
    }

    private void updateImproveCompatibilitySwitchState() {
        if (this.mPreferredQuality == 0) {
            this.mQualitySwitch.setChecked(true);
        } else {
            this.mQualitySwitch.setChecked(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePreferredQuality() {
        if (getBroadcastQualityAutoSwitch() || getImproveCompatibilitySwitchState()) {
            this.mPreferredQuality = 0;
        } else {
            this.mPreferredQuality = 1;
        }
        StringBuilder sb = new StringBuilder("updatePreferredQuality : auto switch = ");
        sb.append(getBroadcastQualityAutoSwitch());
        sb.append(", improve compatibility switch = ");
        sb.append(getImproveCompatibilitySwitchState());
        sb.append(", preferred quality = ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                sb, this.mPreferredQuality == 0 ? "QUALITY_STANDARD" : "QUALITY_HIGH", TAG);
    }

    public void disableAutoSwitchIfNeeded() {
        if (getBroadcastQualityAutoSwitch()) {
            Log.d(TAG, "auto switch is disabled");
            saveBroadcastQualityAutoSwitch(false);
        }
        updatePreferredQuality();
        updateImproveCompatibilitySwitchState();
    }

    public void disableQualityButton() {
        this.mSwitchHeaderLayout.setAlpha(this.LAYOUT_DIMMING_SCALE_OFF);
        this.mQualitySwitch.setClickable(false);
        this.mSwitchHeaderLayout.setClickable(false);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(this.mPrefKey);
        this.mLayoutPreference = layoutPreference;
        this.mQualitySwitch =
                (Switch)
                        layoutPreference.mRootView.findViewById(
                                R.id.broadcast_compatibility_switch);
        this.mSwitchHeaderLayout =
                (LinearLayout) this.mLayoutPreference.mRootView.findViewById(R.id.entity_header);
        LinearLayout linearLayout =
                (LinearLayout) this.mLayoutPreference.mRootView.findViewById(R.id.main_layout);
        linearLayout.semSetRoundedCorners(15);
        linearLayout.semSetRoundedCornerColor(
                15,
                this.mContext
                        .getResources()
                        .getColor(R.color.sec_bluetooth_auracast_background_color));
    }

    public void enableAutoSwitchIfNeeded() {
        if (isValidHearingAid() && !getImproveCompatibilitySwitchState()) {
            Log.d(TAG, "HA device connected already, turn on improve compatibility switch");
            saveBroadcastQualityAutoSwitch(true);
        }
        updatePreferredQuality();
        updateImproveCompatibilitySwitchState();
    }

    public void enableQualityButton() {
        this.mSwitchHeaderLayout.setAlpha(this.LAYOUT_DIMMING_SCALE_ON);
        this.mQualitySwitch.setClickable(true);
        final int i = 0;
        this.mSwitchHeaderLayout.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceCompatibilityController.2
                    public final /* synthetic */
                    SecBluetoothLeBroadcastSourceCompatibilityController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                if (this.this$0.mQualitySwitch.isChecked()) {
                                    this.this$0.saveImproveCompatibilitySwitchState(false);
                                    this.this$0.mQualitySwitch.setChecked(false);
                                } else {
                                    this.this$0.saveImproveCompatibilitySwitchState(true);
                                    this.this$0.mQualitySwitch.setChecked(true);
                                }
                                this.this$0.updatePreferredQuality();
                                break;
                            default:
                                if (this.this$0.mQualitySwitch.isChecked()) {
                                    this.this$0.saveImproveCompatibilitySwitchState(true);
                                } else {
                                    this.this$0.saveImproveCompatibilitySwitchState(false);
                                }
                                this.this$0.updatePreferredQuality();
                                break;
                        }
                    }
                });
        final int i2 = 1;
        this.mQualitySwitch.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceCompatibilityController.2
                    public final /* synthetic */
                    SecBluetoothLeBroadcastSourceCompatibilityController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                if (this.this$0.mQualitySwitch.isChecked()) {
                                    this.this$0.saveImproveCompatibilitySwitchState(false);
                                    this.this$0.mQualitySwitch.setChecked(false);
                                } else {
                                    this.this$0.saveImproveCompatibilitySwitchState(true);
                                    this.this$0.mQualitySwitch.setChecked(true);
                                }
                                this.this$0.updatePreferredQuality();
                                break;
                            default:
                                if (this.this$0.mQualitySwitch.isChecked()) {
                                    this.this$0.saveImproveCompatibilitySwitchState(true);
                                } else {
                                    this.this$0.saveImproveCompatibilitySwitchState(false);
                                }
                                this.this$0.updatePreferredQuality();
                                break;
                        }
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
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

    public int getPreferredQuality() {
        return this.mPreferredQuality;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(
            SharedPreferences sharedPreferences, LocalBluetoothManager localBluetoothManager) {
        this.sharedPrefs = sharedPreferences;
        this.mLocalManager = localBluetoothManager;
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager.mProfileManager;
        this.mLeAudioProfile = localBluetoothProfileManager.mLeAudioProfile;
        this.mLocalLeAssistant = localBluetoothProfileManager.mLeAudioBroadcastAssistant;
        this.mTitleTextView =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(R.id.bluetooth_header_title);
        this.mSubTextView =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(
                                R.id.bluetooth_header_summary);
        updatePreferredQuality();
        this.mQualitySwitch.setOnCheckedChangeListener(
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceCompatibilityController.1
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (z) {
                            SecBluetoothLeBroadcastSourceCompatibilityController
                                    secBluetoothLeBroadcastSourceCompatibilityController =
                                            SecBluetoothLeBroadcastSourceCompatibilityController
                                                    .this;
                            secBluetoothLeBroadcastSourceCompatibilityController.makeTalkback(
                                    secBluetoothLeBroadcastSourceCompatibilityController
                                            .talkbackOn);
                        } else {
                            SecBluetoothLeBroadcastSourceCompatibilityController
                                    secBluetoothLeBroadcastSourceCompatibilityController2 =
                                            SecBluetoothLeBroadcastSourceCompatibilityController
                                                    .this;
                            secBluetoothLeBroadcastSourceCompatibilityController2.makeTalkback(
                                    secBluetoothLeBroadcastSourceCompatibilityController2
                                            .talkbackOff);
                        }
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
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

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        Log.d(TAG, "onPreferenceClick :: key = " + preference.getKey());
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setSubText(String str) {
        TextView textView = this.mSubTextView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setTitle(String str) {
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setText(str);
        }
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
