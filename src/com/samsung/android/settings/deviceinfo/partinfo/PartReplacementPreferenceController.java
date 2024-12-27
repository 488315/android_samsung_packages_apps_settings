package com.samsung.android.settings.deviceinfo.partinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.picker.widget.SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.configuration.DATA;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PartReplacementPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnPause {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String TAG = "PartReplacementPreferenceController";
    private static final HashMap<String, String> mCameraPartTypeMap = new HashMap<>();
    String partReplacementFileName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements FilenameFilter {
        @Override // java.io.FilenameFilter
        public final boolean accept(File file, String str) {
            return str.startsWith("SVC_");
        }
    }

    public PartReplacementPreferenceController(Context context, String str) {
        super(context, str);
        this.partReplacementFileName = getPartReplacementFileName();
    }

    private String getFormattedReplacementDate(String str) {
        try {
            Date parse = new SimpleDateFormat("yyyyMMdd").parse(str);
            return this.mContext.getString(
                    R.string.part_info_replacement_date,
                    new SimpleDateFormat(
                                    DateFormat.getBestDateTimePattern(
                                            Locale.getDefault(), "dd MMMM,yyyy"),
                                    Locale.getDefault())
                            .format(parse));
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Date value of EFS is invalid : "), TAG);
            return this.mContext.getString(R.string.unknown);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getReplacementInfoFromEFS(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r4 = "PartReplacementPreferenceController"
            java.lang.String r0 = ""
            java.lang.String r5 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r5, r6)
            java.io.FileReader r6 = new java.io.FileReader     // Catch: java.lang.Exception -> L36
            r6.<init>(r5)     // Catch: java.lang.Exception -> L36
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L2b
            r1.<init>(r6)     // Catch: java.lang.Throwable -> L2b
            java.lang.String r2 = r1.readLine()     // Catch: java.lang.Throwable -> L21
            r1.close()     // Catch: java.lang.Throwable -> L1f
            r6.close()     // Catch: java.lang.Exception -> L1d
            goto L4d
        L1d:
            r6 = move-exception
            goto L38
        L1f:
            r1 = move-exception
            goto L2d
        L21:
            r2 = move-exception
            r1.close()     // Catch: java.lang.Throwable -> L26
            goto L2a
        L26:
            r1 = move-exception
            r2.addSuppressed(r1)     // Catch: java.lang.Throwable -> L2b
        L2a:
            throw r2     // Catch: java.lang.Throwable -> L2b
        L2b:
            r1 = move-exception
            r2 = r0
        L2d:
            r6.close()     // Catch: java.lang.Throwable -> L31
            goto L35
        L31:
            r6 = move-exception
            r1.addSuppressed(r6)     // Catch: java.lang.Exception -> L1d
        L35:
            throw r1     // Catch: java.lang.Exception -> L1d
        L36:
            r6 = move-exception
            r2 = r0
        L38:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "Fail to get value from EFS : "
            r1.<init>(r3)
            java.lang.String r6 = r6.getMessage()
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            android.util.Log.w(r4, r6)
        L4d:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r5)
            java.lang.String r5 = " : "
            r6.append(r5)
            r6.append(r2)
            java.lang.String r5 = r6.toString()
            android.util.Log.i(r4, r5)
            if (r2 != 0) goto L67
            goto L6b
        L67:
            java.lang.String r0 = r2.trim()
        L6b:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController.getReplacementInfoFromEFS(java.lang.String,"
                    + " java.lang.String):java.lang.String");
    }

    private String getSummaryForPartType(String str) {
        str.getClass();
        if (str.equals("00")) {
            return SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                    .m(this.mContext, R.string.part_info_genuine_part, new StringBuilder("\n"));
        }
        if (str.equals(DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF)) {
            return SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                    .m(this.mContext, R.string.part_info_unknown_part, new StringBuilder("\n"));
        }
        return SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                .m(this.mContext, R.string.unknown, new StringBuilder("\n"));
    }

    private boolean isReplacementFileExist(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(
                "/efs/sec_efs/PRH/genuine/".concat(str));
    }

    private boolean isValidReplacementInfo(String str) {
        return !TextUtils.isEmpty(str) && str.contains(",") && str.split(",").length >= 2;
    }

    private void makeMapForCameraModuleId() {
        String[] strArr;
        try {
            strArr = new File("/efs/sec_efs/PRH/genuine/").list(new AnonymousClass1());
        } catch (SecurityException e) {
            Log.i(TAG, "Failed to get the part info files : " + e.getMessage());
            strArr = null;
        }
        if (strArr == null) {
            Log.d(TAG, "parts module type directory is not exist");
            mCameraPartTypeMap.put("empty", "empty");
        } else if (strArr.length == 0) {
            mCameraPartTypeMap.put("empty", "empty");
        } else {
            for (String str : strArr) {
                String replacementInfoFromEFS =
                        getReplacementInfoFromEFS("/efs/sec_efs/PRH/genuine/", str);
                if (isValidReplacementInfo(replacementInfoFromEFS)
                        && replacementInfoFromEFS.split(",").length >= 3) {
                    String str2 = replacementInfoFromEFS.split(",")[2];
                    if (str2 == null || str2.isEmpty()) {
                        Log.w(TAG, "type name is empty");
                    } else {
                        mCameraPartTypeMap.put(str2, str);
                    }
                }
            }
        }
        Log.d(TAG, "Map for Parts Module Type : " + mCameraPartTypeMap);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isReplacementFileExist(this.partReplacementFileName) ? 0 : 2;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public HashMap<String, String> getMapForCameraModuleId() {
        HashMap<String, String> hashMap = mCameraPartTypeMap;
        if (hashMap.isEmpty()) {
            makeMapForCameraModuleId();
        }
        return hashMap;
    }

    public abstract String getPartReplacementFileName();

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String string = this.mContext.getString(R.string.unknown);
        String replacementInfoFromEFS =
                getReplacementInfoFromEFS(
                        "/efs/sec_efs/PRH/genuine/", this.partReplacementFileName);
        if (!isValidReplacementInfo(replacementInfoFromEFS)) {
            return string;
        }
        String[] split = replacementInfoFromEFS.split(",");
        return getFormattedReplacementDate(split[0]) + getSummaryForPartType(split[1]);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        HashMap<String, String> hashMap = mCameraPartTypeMap;
        if (hashMap.isEmpty()) {
            return;
        }
        hashMap.clear();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
