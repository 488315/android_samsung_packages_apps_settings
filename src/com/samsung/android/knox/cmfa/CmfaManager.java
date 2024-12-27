package com.samsung.android.knox.cmfa;

import android.content.Context;
import android.os.ServiceManager;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CmfaManager {
    public static ArrayList<String> CMFA_PERM =
            new ArrayList<>(
                    Collections.singletonList(
                            EnterpriseDeviceAdminInfo.USES_POLICY_MDM_SECURITY_TAG));
    public static final int RESULT_CODE_FAIL_PERMISSION_ERROR = 3;
    public static final int RESULT_CODE_FAIL_SERVICE_UNAVAILABLE = 1;
    public static final int RESULT_CODE_FAIL_WRONG_ARGUMENT = 2;
    public static final int RESULT_CODE_SUCCESS = 0;
    public static final String TAG = "CmfaManager";
    public final Context mContext;
    public final HashMap<IAuthEventListener, IEventListener> mListeners = new HashMap<>();

    public CmfaManager(Context context) {
        this.mContext = context;
    }

    public int check(final IAuthResultListener iAuthResultListener) {
        String str = TAG;
        Log.i(str, "Enter check()");
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave check() with 3");
            return 3;
        }
        int i = 1;
        try {
            ICmfaService service = getService();
            if (service != null) {
                i =
                        service.check(
                                new IResultListener
                                        .Stub() { // from class:
                                                  // com.samsung.android.knox.cmfa.CmfaManager.1
                                    @Override // com.samsung.android.knox.cmfa.IResultListener
                                    public void onFail(String str2) {
                                        iAuthResultListener.onFail(str2);
                                    }

                                    @Override // com.samsung.android.knox.cmfa.IResultListener
                                    public void onSuccess() {
                                        iAuthResultListener.onSuccess();
                                    }
                                });
            } else {
                Log.e(str, "check getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Leave check() with ", TAG);
        return i;
    }

    public final boolean checkPermission(ContextInfo contextInfo) {
        try {
            ContextInfo enforceActiveAdminPermissionByContext =
                    AccessController.enforceActiveAdminPermissionByContext(contextInfo, CMFA_PERM);
            Log.i(
                    TAG,
                    "checkPermission cxtInfo"
                            + enforceActiveAdminPermissionByContext
                            + " has permission: "
                            + CMFA_PERM);
            return true;
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException: " + e.getMessage());
            Log.e(TAG, "checkPermission: false is returned.");
            return false;
        } catch (Exception e2) {
            CloneBackend$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
            Log.e(TAG, "checkPermission: false is returned.");
            return false;
        }
    }

    public int disable() {
        String str = TAG;
        Log.i(str, "Enter disable()");
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave disable() with 3");
            return 3;
        }
        int i = 1;
        try {
            ICmfaService service = getService();
            if (service != null) {
                i = service.disable();
            } else {
                Log.e(str, "disable getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Leave disable() with ", TAG);
        return i;
    }

    public int enable(String str) {
        return enable(str, true);
    }

    public List<AuthFactorType> getFactorsToSetup() {
        String str = TAG;
        Log.i(str, "Enter getFactorsToSetup()");
        List<AuthFactorType> arrayList = new ArrayList<>();
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave getFactorsToSetup() with permission error!");
            return arrayList;
        }
        try {
            ICmfaService service = getService();
            if (service != null) {
                arrayList = service.getFactorsToSetup();
            } else {
                Log.e(str, "getFactorsToSetup getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        Log.i(TAG, "Leave getFactorsToSetup() with " + arrayList);
        return arrayList;
    }

    public final ICmfaService getService() {
        return ICmfaService.Stub.asInterface(ServiceManager.getService("cmfa"));
    }

    public List<AuthActionType> getValidActions() {
        String str = TAG;
        Log.i(str, "Enter getValidActions()");
        List<AuthActionType> arrayList = new ArrayList<>();
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave getValidActions() with permission error!");
            return arrayList;
        }
        try {
            ICmfaService service = getService();
            if (service != null) {
                arrayList = service.getValidActions();
            } else {
                Log.e(str, "getValidActions getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        Log.i(TAG, "Leave getValidActions() with " + arrayList);
        return arrayList;
    }

    public boolean isEnabled() {
        String str = TAG;
        Log.i(str, "Enter isEnabled()");
        boolean z = false;
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave isEnabled() with permission error!");
            return false;
        }
        try {
            ICmfaService service = getService();
            if (service != null) {
                z = service.isEnabled();
            } else {
                Log.e(str, "isEnabled getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        AbsAdapter$$ExternalSyntheticOutline0.m("Leave isEnabled() with ", TAG, z);
        return z;
    }

    public boolean isStarted() {
        String str = TAG;
        Log.i(str, "Enter isStarted()");
        boolean z = false;
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave isStarted() with permission error!");
            return false;
        }
        try {
            ICmfaService service = getService();
            if (service != null) {
                z = service.isStarted();
            } else {
                Log.e(str, "isStarted getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        AbsAdapter$$ExternalSyntheticOutline0.m("Leave isStarted() with ", TAG, z);
        return z;
    }

    public int notifyTestFactorScoreChange(String str, long j, boolean z) {
        String str2 = TAG;
        Log.i(str2, "Enter notifyTestFactorScoreChange()");
        if (!checkPermission(new ContextInfo())) {
            Log.e(str2, "Leave notifyTestFactorScoreChange() with 3");
            return 3;
        }
        int i = 1;
        try {
            ICmfaService service = getService();
            if (service != null) {
                i = service.notifyTestFactorScoreChange(str, j, z);
            } else {
                Log.e(str2, "notifyTestFactorScoreChange getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "Leave notifyTestFactorScoreChange() with ", TAG);
        return i;
    }

    public int registerListener(final IAuthEventListener iAuthEventListener) {
        String str = TAG;
        Log.i(str, "Enter registerListener()");
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave registerListener() with 3");
            return 3;
        }
        int i = 1;
        try {
            ICmfaService service = getService();
            if (service != null) {
                this.mListeners.put(
                        iAuthEventListener,
                        new IEventListener
                                .Stub() { // from class: com.samsung.android.knox.cmfa.CmfaManager.4
                            @Override // com.samsung.android.knox.cmfa.IEventListener
                            public void onFail(String str2) {
                                iAuthEventListener.onFail(str2);
                            }

                            @Override // com.samsung.android.knox.cmfa.IEventListener
                            public void onStateUpdate(boolean z, String str2) {
                                iAuthEventListener.onStateUpdate(z, str2);
                            }

                            @Override // com.samsung.android.knox.cmfa.IEventListener
                            public void onSuccess() {
                                iAuthEventListener.onSuccess();
                            }
                        });
                i = service.registerListener(this.mListeners.get(iAuthEventListener));
            } else {
                Log.e(str, "registerListener getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Leave registerListener() with ", TAG);
        return i;
    }

    public int start(final IAuthResultListener iAuthResultListener) {
        String str = TAG;
        Log.i(str, "Enter start()");
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave start() with 3");
            return 3;
        }
        int i = 1;
        try {
            ICmfaService service = getService();
            if (service != null) {
                i =
                        service.start(
                                new IResultListener
                                        .Stub() { // from class:
                                                  // com.samsung.android.knox.cmfa.CmfaManager.2
                                    @Override // com.samsung.android.knox.cmfa.IResultListener
                                    public void onFail(String str2) {
                                        iAuthResultListener.onFail(str2);
                                    }

                                    @Override // com.samsung.android.knox.cmfa.IResultListener
                                    public void onSuccess() {
                                        iAuthResultListener.onSuccess();
                                    }
                                });
            } else {
                Log.e(str, "start getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Leave start() with ", TAG);
        return i;
    }

    public int stop(final IAuthResultListener iAuthResultListener) {
        String str = TAG;
        Log.i(str, "Enter stop()");
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave stop() with 3");
            return 3;
        }
        int i = 1;
        try {
            ICmfaService service = getService();
            if (service != null) {
                i =
                        service.stop(
                                new IResultListener
                                        .Stub() { // from class:
                                                  // com.samsung.android.knox.cmfa.CmfaManager.3
                                    @Override // com.samsung.android.knox.cmfa.IResultListener
                                    public void onFail(String str2) {
                                        iAuthResultListener.onFail(str2);
                                    }

                                    @Override // com.samsung.android.knox.cmfa.IResultListener
                                    public void onSuccess() {
                                        iAuthResultListener.onSuccess();
                                    }
                                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Leave stop() with ", TAG);
        return i;
    }

    public int unregisterListener(IAuthEventListener iAuthEventListener) {
        String str = TAG;
        Log.i(str, "Enter unregisterListener()");
        if (!checkPermission(new ContextInfo())) {
            Log.e(str, "Leave unregisterListener() with 3");
            return 3;
        }
        int i = 1;
        try {
            ICmfaService service = getService();
            if (service != null) {
                i = service.unregisterListener(this.mListeners.get(iAuthEventListener));
                this.mListeners.remove(iAuthEventListener);
            } else {
                Log.e(str, "unregisterListener getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Leave unregisterListener() with ", TAG);
        return i;
    }

    public int enable(String str, boolean z) {
        String str2 = TAG;
        Log.i(str2, "Enter enable()");
        if (!checkPermission(new ContextInfo())) {
            Log.e(str2, "Leave enable() with 3");
            return 3;
        }
        int i = 1;
        try {
            ICmfaService service = getService();
            if (service != null) {
                i = service.enable(str, z);
            } else {
                Log.e(str2, "enable getService failed!");
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Leave enable() with ", TAG);
        return i;
    }
}
