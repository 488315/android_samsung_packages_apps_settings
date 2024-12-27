package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.deviceinfo.SecDeviceInfoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ActivationStatusPreferenceController extends BasePreferenceController {
    private static final String QUERY_URL =
            new String(
                    Base64.decode(
                            "aHR0cHM6Ly9xdWVyeS5hY3RpdmF0aW9uLnNhbXN1bmcuY29tLmNuL2FwaS92MS9kZXZpY2UvYWN0aXZhdGlvbkRhdGU=",
                            2));
    private static final String TAG = "ActivationStatusPreferenceController";
    private static final int TIMEOUT_MS = 10000;
    private boolean isClicked;
    private long lastTime;
    private ActivationPreference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class GetActivationDateRunnable implements Runnable {
        public GetActivationDateRunnable() {}

        @Override // java.lang.Runnable
        public final void run() {
            String activationDateFromServer =
                    ActivationStatusPreferenceController.this.getActivationDateFromServer();
            MotionLayout$$ExternalSyntheticOutline0.m(
                    "response: ",
                    activationDateFromServer,
                    ActivationStatusPreferenceController.TAG);
            if (activationDateFromServer != null) {
                try {
                    JSONObject jSONObject = new JSONObject(activationDateFromServer);
                    if (jSONObject.has("date")) {
                        ActivationStatusPreferenceController.this.parseActivationDate(jSONObject);
                    } else if (jSONObject.has(
                            KnoxContainerManager.CONTAINER_CREATION_STATUS_CODE)) {
                        ActivationStatusPreferenceController.this.showErrorMessage(jSONObject);
                    }
                } catch (JSONException e) {
                    Log.w(
                            ActivationStatusPreferenceController.TAG,
                            "update Exception e: " + e.getMessage());
                    e.printStackTrace();
                }
            } else if (((AbstractPreferenceController) ActivationStatusPreferenceController.this)
                            .mContext
                    == null) {
                return;
            } else {
                ThreadUtils.postOnMainThread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.deviceinfo.statusinfo.ActivationStatusPreferenceController$GetActivationDateRunnable$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                boolean z;
                                ActivationPreference activationPreference;
                                ActivationPreference activationPreference2;
                                Context context;
                                Context context2;
                                Context context3;
                                ActivationStatusPreferenceController.GetActivationDateRunnable
                                        getActivationDateRunnable =
                                                ActivationStatusPreferenceController
                                                        .GetActivationDateRunnable.this;
                                z = ActivationStatusPreferenceController.this.isClicked;
                                if (z) {
                                    context2 =
                                            ((AbstractPreferenceController)
                                                            ActivationStatusPreferenceController
                                                                    .this)
                                                    .mContext;
                                    context3 =
                                            ((AbstractPreferenceController)
                                                            ActivationStatusPreferenceController
                                                                    .this)
                                                    .mContext;
                                    Toast.makeText(
                                                    context2,
                                                    context3.getString(
                                                            R.string.activation_status_fail_toast),
                                                    0)
                                            .show();
                                }
                                activationPreference =
                                        ActivationStatusPreferenceController.this.mPreference;
                                if (activationPreference != null) {
                                    activationPreference2 =
                                            ActivationStatusPreferenceController.this.mPreference;
                                    context =
                                            ((AbstractPreferenceController)
                                                            ActivationStatusPreferenceController
                                                                    .this)
                                                    .mContext;
                                    activationPreference2.makeSummary(
                                            context.getString(
                                                    R.string.activation_status_fail_sub_summary));
                                }
                            }
                        });
            }
            ActivationStatusPreferenceController.this.isClicked = false;
        }
    }

    public ActivationStatusPreferenceController(Context context, String str) {
        super(context, str);
        this.isClicked = false;
    }

    private String formatDate(String str) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(simpleDateFormat.parse(str));
            calendar.set(10, calendar.get(10) + 8);
        } catch (ParseException e) {
            Log.w(TAG, "formatDate Exception e: " + e.getMessage());
            e.printStackTrace();
        }
        return simpleDateFormat.format(calendar.getTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:39:0x010b A[Catch: IOException -> 0x010e, TRY_LEAVE, TryCatch #2 {IOException -> 0x010e, blocks: (B:46:0x0106, B:39:0x010b), top: B:45:0x0106 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0106 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x011d A[Catch: IOException -> 0x0120, TRY_LEAVE, TryCatch #10 {IOException -> 0x0120, blocks: (B:60:0x0118, B:52:0x011d), top: B:59:0x0118 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0118 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getActivationDateFromServer() {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.statusinfo.ActivationStatusPreferenceController.getActivationDateFromServer():java.lang.String");
    }

    private String getMessageBody() throws JSONException {
        String imei = SecDeviceInfoUtils.getImei(this.mContext, 0, true);
        String serialNumber = SecDeviceInfoUtils.getSerialNumber(this.mContext);
        if (Utils.isWifiOnly(this.mContext)) {
            imei = serialNumber;
        }
        if (TextUtils.isEmpty(imei) || "000000000000000".equals(imei)) {
            Log.w(TAG, "imei is empty");
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("deviceID", imei);
        jSONObject.put("serialNumber", serialNumber);
        return jSONObject.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$parseActivationDate$1(String str) {
        Settings.System.putString(this.mContext.getContentResolver(), "chn_activation_date", str);
        ActivationPreference activationPreference = this.mPreference;
        if (activationPreference != null) {
            activationPreference.makeSummary(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showErrorMessage$2(JSONObject jSONObject) {
        char c;
        try {
            if (jSONObject.has(KnoxContainerManager.CONTAINER_CREATION_STATUS_CODE)) {
                String string =
                        jSONObject.getString(KnoxContainerManager.CONTAINER_CREATION_STATUS_CODE);
                String string2 = jSONObject.getString("message");
                switch (string.hashCode()) {
                    case 1052111058:
                        if (string.equals("DIR_1111")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1052111059:
                        if (string.equals("DIR_1112")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1052111061:
                        if (string.equals("DIR_1114")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1052229229:
                        if (string.equals("DIR_5000")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                if (c == 0 || c == 1) {
                    string2 = this.mContext.getString(R.string.activation_not_complete);
                } else if (c == 2) {
                    string2 = this.mContext.getString(R.string.activation_not_support);
                } else if (c == 3) {
                    string2 = this.mContext.getString(R.string.activation_status_fail_sub_summary);
                }
                ActivationPreference activationPreference = this.mPreference;
                if (activationPreference != null) {
                    activationPreference.makeSummary(string2);
                }
            }
        } catch (JSONException e) {
            Log.w(TAG, "toastErrorMessage Exception e: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$0() {
        this.isClicked = true;
        updateActivationTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseActivationDate(JSONObject jSONObject) throws JSONException {
        ThreadUtils.postOnMainThread(
                new ActivationStatusPreferenceController$$ExternalSyntheticLambda1(
                        this, formatDate(jSONObject.getString("date")), 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showErrorMessage(JSONObject jSONObject) {
        ThreadUtils.postOnMainThread(
                new ActivationStatusPreferenceController$$ExternalSyntheticLambda1(
                        this, jSONObject, 0));
    }

    private void updateActivationTime() {
        if (System.currentTimeMillis() - this.lastTime < 2000) {
            return;
        }
        this.lastTime = System.currentTimeMillis();
        if (com.android.settings.Utils.isNetworkAvailable(this.mContext)) {
            ThreadUtils.postOnBackgroundThread(new GetActivationDateRunnable());
            return;
        }
        ActivationPreference activationPreference = this.mPreference;
        if (activationPreference != null) {
            activationPreference.makeSummary(
                    this.mContext.getString(R.string.activation_status_fail_sub_summary));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Rune.isChinaModel()
                        && com.android.settings.Utils.hasPackage(
                                this.mContext.getApplicationContext(),
                                "com.samsung.android.activation"))
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
        ActivationPreference activationPreference = (ActivationPreference) preference;
        this.mPreference = activationPreference;
        activationPreference.mOnUpdateListener =
                new ActivationStatusPreferenceController$$ExternalSyntheticLambda0(this);
        if (TextUtils.isEmpty(
                Settings.System.getString(
                        this.mContext.getContentResolver(), "chn_activation_date"))) {
            updateActivationTime();
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
