package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.sec_platform_library.FactoryPhone;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FirstCallPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStop {
    private static final String LOG_TAG = "FirstCallPreferenceController";
    private static final int OEM_FUNCTION_ID_HIDDENMENU = 81;
    private static final int OEM_HIDDEN_GET_ACTIVATIONDATE = 13;
    private FactoryPhone mFactoryPhone;
    private PhoneFirstCallHandler mPhoneFirstCallHandler;
    private Preference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PhoneFirstCallHandler extends Handler {
        public WeakReference mStatus;

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.getData().getInt("error");
            if (i != 0) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(
                        i,
                        "AsyncResult exception occur!!! - ",
                        FirstCallPreferenceController.LOG_TAG);
                return;
            }
            byte[] byteArray = message.getData().getByteArray("response");
            if (byteArray == null) {
                return;
            }
            Log.d(
                    FirstCallPreferenceController.LOG_TAG,
                    "response for first call date : " + Arrays.toString(byteArray));
            if (message.what != 13) {
                return;
            }
            ((FirstCallPreferenceController) this.mStatus.get())
                    .updateFirstCallDate(
                            FirstCallPreferenceController.getFirstCallDateValue(byteArray));
        }
    }

    public FirstCallPreferenceController(Context context, String str) {
        super(context, str);
        this.mFactoryPhone = null;
        this.mPhoneFirstCallHandler = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getFirstCallDateValue(byte[] bArr) {
        byte[] bArr2 = {bArr[1], bArr[0]};
        int i = 0;
        for (int i2 = 0; i2 < 2; i2++) {
            i = (i << 8) + (bArr2[i2] & 255);
        }
        return getFormatedDate(i, bArr[2], bArr[3]);
    }

    private static String getFormatedDate(int i, int i2, int i3) {
        return Rune.isDomesticModel()
                ? String.format(
                        "%04d.%02d.%02d",
                        Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3))
                : String.format(
                        "%02d/%02d/%04d",
                        Integer.valueOf(i3), Integer.valueOf(i2), Integer.valueOf(i));
    }

    private void setFirstCallstatus() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            try {
                dataOutputStream.writeByte(81);
                dataOutputStream.writeByte(13);
                dataOutputStream.writeShort(4);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PhoneFirstCallHandler phoneFirstCallHandler = new PhoneFirstCallHandler();
            phoneFirstCallHandler.mStatus = new WeakReference(this);
            this.mPhoneFirstCallHandler = phoneFirstCallHandler;
            FactoryPhone factoryPhone = new FactoryPhone(this.mContext);
            this.mFactoryPhone = factoryPhone;
            factoryPhone.invokeOemRilRequestRaw(
                    byteArrayOutputStream.toByteArray(),
                    this.mPhoneFirstCallHandler.obtainMessage(13));
        } finally {
            try {
                dataOutputStream.close();
            } catch (IOException unused) {
                Log.d(LOG_TAG, "IOException in getOemData!!!");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFirstCallDate(String str) {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setSummary(str);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return ((Rune.isDomesticModel() || "PK".equalsIgnoreCase(Utils.readCountryCode()))
                        && Utils.isVoiceCapable(this.mContext))
                ? 0
                : 3;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        FactoryPhone factoryPhone = this.mFactoryPhone;
        if (factoryPhone != null) {
            factoryPhone.disconnectFromRilService();
            this.mFactoryPhone = null;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        Preference preference2 = this.mPreference;
        if (preference2 == null) {
            return;
        }
        preference2.setSummary(this.mContext.getString(R.string.status_unavailable));
        setFirstCallstatus();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
