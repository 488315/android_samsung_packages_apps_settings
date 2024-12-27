package com.samsung.android.settings.biometrics.face;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockSettingsHelper;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.biometrics.BiometricsRestrictedSwitchPreference;
import com.samsung.android.settings.biometrics.Wallet24Service;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FaceSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass3();
    public static boolean mIsKeepEnrollSession = false;
    public final AnonymousClass1 mAlternativeRemovalCallback;
    public Preference mAnchorPreference;
    public SwitchPreference mBrightenScreen;
    public View mEmptyView;
    public BiometricsRestrictedSwitchPreference mFaceUnlock;
    public GatekeeperPasswordProvider mGkPwProvider;
    public boolean mHasEnrolled;
    public boolean mLaunchedConfirm;
    public boolean mLockConfirmed;
    public LockPatternUtils mLockPatternUtils;
    public SwitchPreference mOpenEyes;
    public SwitchPreference mRecognizeWithMask;
    public final AnonymousClass1 mRemovalCallback;
    public SwitchPreference mStayOnLockScreen;
    public int mUserId;
    public Wallet24Service mWallet24Service;
    public FaceManager mFaceManager = null;
    public AlertDialog mDeleteDialog = null;
    public int mSensorId = -1;
    public byte[] mToken = null;
    public long mChallenge = 0;
    public long mGkPwHandle = 0;
    public boolean mIdentifyFace = false;
    public boolean mIsRunRegister = false;
    public boolean mIsRemoveAndRegister = false;
    public boolean mIsBiometricsSettingsDestroy = false;
    public boolean mIsAfw = false;
    public boolean mNeedFmmPopup = false;
    public String mPreviousStage = null;
    public Intent mIntent = null;
    public SecRelativeLinkView mRelativeLinkView = null;
    public boolean mIsKnox = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.biometrics.face.FaceSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseStatusLoggingProvider {
        /* JADX WARN: Removed duplicated region for block: B:14:0x0097  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x00c9  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00f9  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00db  */
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List getStatusLoggingData(android.content.Context r8) {
            /*
                Method dump skipped, instructions count: 286
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.biometrics.face.FaceSettings.AnonymousClass3.getStatusLoggingData(android.content.Context):java.util.List");
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.biometrics.face.FaceSettings$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.biometrics.face.FaceSettings$1] */
    public FaceSettings() {
        final int i = 0;
        this.mAlternativeRemovalCallback =
                new FaceManager.RemovalCallback(this) { // from class:
                    // com.samsung.android.settings.biometrics.face.FaceSettings.1
                    public final /* synthetic */ FaceSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onRemovalError(Face face, int i2, CharSequence charSequence) {
                        switch (i) {
                            case 0:
                                FragmentActivity activity = this.this$0.getActivity();
                                Log.e(
                                        "FcstFaceSettings",
                                        "mAlternativeRemovalCallback : Remove Error : "
                                                + i2
                                                + ", "
                                                + ((Object) charSequence)
                                                + ", activity="
                                                + activity);
                                if (activity != null) {
                                    FaceSettingsHelper.showFaceSensorErrorDialog(
                                            activity,
                                            null,
                                            activity.getString(
                                                    R.string.sec_face_error_message_sensor_error),
                                            false);
                                    break;
                                }
                                break;
                            default:
                                FragmentActivity activity2 = this.this$0.getActivity();
                                Log.e(
                                        "FcstFaceSettings",
                                        "mRemovalCallback : Remove Error : "
                                                + i2
                                                + ", "
                                                + ((Object) charSequence)
                                                + ", activity="
                                                + activity2);
                                if (activity2 != null) {
                                    FaceSettingsHelper.showFaceSensorErrorDialog(
                                            activity2,
                                            null,
                                            activity2.getString(
                                                    R.string.sec_face_error_message_sensor_error),
                                            false);
                                }
                                this.this$0.mIsRemoveAndRegister = false;
                                break;
                        }
                    }

                    public final void onRemovalSucceeded(Face face, int i2) {
                        SwitchPreference switchPreference;
                        SwitchPreference switchPreference2;
                        switch (i) {
                            case 0:
                                Log.d(
                                        "FcstFaceSettings",
                                        "mAlternativeRemovalCallback : onRemovalSucceeded");
                                Context context = this.this$0.getContext();
                                RecyclerView listView = this.this$0.getListView();
                                if (context != null && listView != null) {
                                    listView.announceForAccessibility(
                                            context.getString(
                                                    R.string.sec_biometrics_common_removed));
                                }
                                this.this$0.updatePreferences$5();
                                break;
                            default:
                                Log.d("FcstFaceSettings", "mRemovalCallback : onRemovalSucceeded");
                                Context context2 = this.this$0.getContext();
                                RecyclerView listView2 = this.this$0.getListView();
                                if (context2 != null && listView2 != null) {
                                    listView2.announceForAccessibility(
                                            context2.getString(
                                                    R.string.sec_biometrics_common_removed));
                                }
                                FragmentActivity activity = this.this$0.getActivity();
                                if (activity != null) {
                                    FaceSettings faceSettings = this.this$0;
                                    FaceSettingsHelper.removeFaceLock(
                                            faceSettings.mUserId,
                                            activity,
                                            faceSettings.mLockPatternUtils);
                                } else {
                                    Log.e(
                                            "FcstFaceSettings",
                                            "onRemovalSucceeded : context is null");
                                }
                                if (!this.this$0.isResumed()) {
                                    Log.d(
                                            "FcstFaceSettings",
                                            "onRemovalSucceeded : skip update screen after"
                                                + " destory");
                                    break;
                                } else {
                                    boolean z =
                                            FaceSettingsHelper
                                                    .IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
                                    SwitchPreference switchPreference3 =
                                            this.this$0.mStayOnLockScreen;
                                    if (switchPreference3 != null) {
                                        switchPreference3.setEnabled(false);
                                    }
                                    if (FaceSettingsHelper.isSupportBioFaceRecognizeWithMask(
                                                    activity)
                                            && (switchPreference2 = this.this$0.mRecognizeWithMask)
                                                    != null) {
                                        switchPreference2.setEnabled(false);
                                    }
                                    if (FaceSettingsHelper.isSupportBioFaceOpenEyes()
                                            && (switchPreference = this.this$0.mOpenEyes) != null) {
                                        switchPreference.setEnabled(false);
                                    }
                                    SwitchPreference switchPreference4 =
                                            this.this$0.mBrightenScreen;
                                    if (switchPreference4 != null) {
                                        switchPreference4.setEnabled(false);
                                    }
                                    this.this$0.updatePreferences$5();
                                    FaceSettings faceSettings2 = this.this$0;
                                    if (faceSettings2.mIsRemoveAndRegister) {
                                        faceSettings2.runRegister("FaceSettings_register");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mRemovalCallback =
                new FaceManager.RemovalCallback(this) { // from class:
                    // com.samsung.android.settings.biometrics.face.FaceSettings.1
                    public final /* synthetic */ FaceSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onRemovalError(
                            Face face, int i22, CharSequence charSequence) {
                        switch (i2) {
                            case 0:
                                FragmentActivity activity = this.this$0.getActivity();
                                Log.e(
                                        "FcstFaceSettings",
                                        "mAlternativeRemovalCallback : Remove Error : "
                                                + i22
                                                + ", "
                                                + ((Object) charSequence)
                                                + ", activity="
                                                + activity);
                                if (activity != null) {
                                    FaceSettingsHelper.showFaceSensorErrorDialog(
                                            activity,
                                            null,
                                            activity.getString(
                                                    R.string.sec_face_error_message_sensor_error),
                                            false);
                                    break;
                                }
                                break;
                            default:
                                FragmentActivity activity2 = this.this$0.getActivity();
                                Log.e(
                                        "FcstFaceSettings",
                                        "mRemovalCallback : Remove Error : "
                                                + i22
                                                + ", "
                                                + ((Object) charSequence)
                                                + ", activity="
                                                + activity2);
                                if (activity2 != null) {
                                    FaceSettingsHelper.showFaceSensorErrorDialog(
                                            activity2,
                                            null,
                                            activity2.getString(
                                                    R.string.sec_face_error_message_sensor_error),
                                            false);
                                }
                                this.this$0.mIsRemoveAndRegister = false;
                                break;
                        }
                    }

                    public final void onRemovalSucceeded(Face face, int i22) {
                        SwitchPreference switchPreference;
                        SwitchPreference switchPreference2;
                        switch (i2) {
                            case 0:
                                Log.d(
                                        "FcstFaceSettings",
                                        "mAlternativeRemovalCallback : onRemovalSucceeded");
                                Context context = this.this$0.getContext();
                                RecyclerView listView = this.this$0.getListView();
                                if (context != null && listView != null) {
                                    listView.announceForAccessibility(
                                            context.getString(
                                                    R.string.sec_biometrics_common_removed));
                                }
                                this.this$0.updatePreferences$5();
                                break;
                            default:
                                Log.d("FcstFaceSettings", "mRemovalCallback : onRemovalSucceeded");
                                Context context2 = this.this$0.getContext();
                                RecyclerView listView2 = this.this$0.getListView();
                                if (context2 != null && listView2 != null) {
                                    listView2.announceForAccessibility(
                                            context2.getString(
                                                    R.string.sec_biometrics_common_removed));
                                }
                                FragmentActivity activity = this.this$0.getActivity();
                                if (activity != null) {
                                    FaceSettings faceSettings = this.this$0;
                                    FaceSettingsHelper.removeFaceLock(
                                            faceSettings.mUserId,
                                            activity,
                                            faceSettings.mLockPatternUtils);
                                } else {
                                    Log.e(
                                            "FcstFaceSettings",
                                            "onRemovalSucceeded : context is null");
                                }
                                if (!this.this$0.isResumed()) {
                                    Log.d(
                                            "FcstFaceSettings",
                                            "onRemovalSucceeded : skip update screen after"
                                                + " destory");
                                    break;
                                } else {
                                    boolean z =
                                            FaceSettingsHelper
                                                    .IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
                                    SwitchPreference switchPreference3 =
                                            this.this$0.mStayOnLockScreen;
                                    if (switchPreference3 != null) {
                                        switchPreference3.setEnabled(false);
                                    }
                                    if (FaceSettingsHelper.isSupportBioFaceRecognizeWithMask(
                                                    activity)
                                            && (switchPreference2 = this.this$0.mRecognizeWithMask)
                                                    != null) {
                                        switchPreference2.setEnabled(false);
                                    }
                                    if (FaceSettingsHelper.isSupportBioFaceOpenEyes()
                                            && (switchPreference = this.this$0.mOpenEyes) != null) {
                                        switchPreference.setEnabled(false);
                                    }
                                    SwitchPreference switchPreference4 =
                                            this.this$0.mBrightenScreen;
                                    if (switchPreference4 != null) {
                                        switchPreference4.setEnabled(false);
                                    }
                                    this.this$0.updatePreferences$5();
                                    FaceSettings faceSettings2 = this.this$0;
                                    if (faceSettings2.mIsRemoveAndRegister) {
                                        faceSettings2.runRegister("FaceSettings_register");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public final void cancelAndSessionEnd$1() {
        Log.d("FcstFaceSettings", "cancelAndSessionEnd() ");
        if (mIsKeepEnrollSession) {
            Log.d("FcstFaceSettings", "Keep the session and activity");
            return;
        }
        Log.d("FcstFaceSettings", "Close the session and finish the activity");
        Log.d("FcstFaceSettings", "finishFaceSettings()");
        Wallet24Service wallet24Service = this.mWallet24Service;
        if (wallet24Service != null) {
            if (wallet24Service.mBound) {
                wallet24Service.unbindService();
            }
            this.mWallet24Service = null;
        }
        if (this.mFaceManager != null && this.mChallenge != 0) {
            Log.d("FcstFaceSettings", "onDestroy: revokeChallenge");
            this.mFaceManager.revokeChallenge(this.mSensorId, this.mUserId, this.mChallenge);
        }
        if (this.mIsBiometricsSettingsDestroy
                && "lock_screen_face_menu".equals(this.mPreviousStage)) {
            Intent intent = this.mIntent;
            if (intent == null) {
                intent = new Intent();
            }
            this.mIntent = intent;
            intent.putExtra("screen_lock_force_destroy", true);
            this.mIntent.putExtra("gk_pw_handle", this.mGkPwHandle);
            setResult(0, this.mIntent);
        }
        getActivity().finish();
        AlertDialog alertDialog = this.mDeleteDialog;
        if (alertDialog != null) {
            alertDialog.cancel();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void createPreferenceHierarchy$3() {
        /*
            Method dump skipped, instructions count: 508
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.face.FaceSettings.createPreferenceHierarchy$3():void");
    }

    public final void deleteFace(final boolean z, final boolean z2) {
        int i;
        int i2;
        ArrayList arrayList;
        if (this.mDeleteDialog != null) {
            return;
        }
        FaceManager faceManager = this.mFaceManager;
        if (faceManager != null && !faceManager.hasEnrolledTemplates(this.mUserId)) {
            Log.e("FcstFaceSettings", "There is no face data to remove !!");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String string = getString(R.string.sec_face_remove_face_popup_message_2);
        if (z2) {
            string = getString(R.string.sec_face_remove_and_register_your_face);
            i2 = R.string.sec_face_register_your_face_again;
            i = R.string.sec_face_remove_and_register;
        } else {
            i = R.string.common_remove;
            if (z) {
                string = getString(R.string.sec_face_remove_alternative_face_popup_message);
                i2 = R.string.sec_face_remove_alternative_face_popup_title;
            } else {
                Wallet24Service wallet24Service = this.mWallet24Service;
                if (wallet24Service != null
                        && (arrayList = wallet24Service.mAppList) != null
                        && arrayList.size() > 0) {
                    ArrayList arrayList2 = this.mWallet24Service.mAppList;
                    String str =
                            getString(R.string.sec_face_remove_face_popup_mobile_id)
                                    + "\n\n"
                                    + getString(R.string.sec_face_remove_face_popup_apps);
                    for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                        str =
                                ((Object) str)
                                        + "\n"
                                        + getString(R.string.sec_biometrics_disclaimer_guide_bullet)
                                        + ((Object)
                                                Utils.getApplicationLabel(
                                                        getContext(), (String) arrayList2.get(i3)));
                    }
                    string = str;
                }
                i2 = R.string.sec_face_remove_face_popup_title;
            }
        }
        builder.setTitle(i2);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string;
        builder.setPositiveButton(
                i,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.biometrics.face.FaceSettings$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i4) {
                        List enrolledFaces;
                        FaceSettings faceSettings = FaceSettings.this;
                        boolean z3 = z;
                        boolean z4 = z2;
                        BiometricsGenericHelper.insertSaLog(
                                faceSettings.getContext(), z3 ? 8449 : 8406, 8408);
                        FaceManager faceManager2 = faceSettings.mFaceManager;
                        if (faceManager2 == null
                                || (enrolledFaces =
                                                faceManager2.getEnrolledFaces(faceSettings.mUserId))
                                        == null) {
                            Log.e("FcstFaceSettings", "deleteFace : error occurred");
                            return;
                        }
                        int size = enrolledFaces.size();
                        if (size <= 0) {
                            Log.e("FcstFaceSettings", "deleteFace : faceList size is 0");
                            return;
                        }
                        if (!z3) {
                            Log.d("FcstFaceSettings", "Remove all face data");
                            faceSettings.mIsRemoveAndRegister = z4;
                            faceSettings.mFaceManager.removeAll(
                                    faceSettings.mUserId, faceSettings.mRemovalCallback);
                        } else if (size <= 1) {
                            Log.e("FcstFaceSettings", "deleteFace : faceList size is 1");
                        } else {
                            Log.d("FcstFaceSettings", "Remove only Alternative face data");
                            faceSettings.mFaceManager.remove(
                                    (Face) enrolledFaces.get(1),
                                    faceSettings.mUserId,
                                    faceSettings.mAlternativeRemovalCallback);
                        }
                    }
                });
        builder.setNegativeButton(
                R.string.common_cancel,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.biometrics.face.FaceSettings$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i4) {
                        BiometricsGenericHelper.insertSaLog(
                                FaceSettings.this.getContext(), z ? 8449 : 8406, 8407);
                        dialogInterface.dismiss();
                    }
                });
        alertParams.mOnDismissListener =
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.biometrics.face.FaceSettings$$ExternalSyntheticLambda3
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        FaceSettings.this.mDeleteDialog = null;
                    }
                };
        AlertDialog create = builder.create();
        this.mDeleteDialog = create;
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.samsung.android.settings.biometrics.face.FaceSettings$$ExternalSyntheticLambda4
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        FaceSettings faceSettings = FaceSettings.this;
                        faceSettings.getClass();
                        ((AlertDialog) dialogInterface)
                                .getButton(-1)
                                .setTextColor(
                                        faceSettings
                                                .getActivity()
                                                .getColor(
                                                        R.color
                                                                .sec_biometrics_dialog_remove_btn_color));
                    }
                });
        final Preference preference = this.mAnchorPreference;
        final AlertDialog alertDialog = this.mDeleteDialog;
        if (preference != null && alertDialog != null) {
            Rect rect = new Rect();
            preference.seslGetPreferenceBounds(rect);
            alertDialog.semSetAnchor((rect.left + rect.right) / 2, rect.bottom);
            View rootView = alertDialog.findViewById(android.R.id.content).getRootView();
            if (rootView != null) {
                rootView.addOnLayoutChangeListener(
                        new View
                                .OnLayoutChangeListener() { // from class:
                                                            // com.samsung.android.settings.biometrics.face.FaceSettings$$ExternalSyntheticLambda5
                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(
                                    View view,
                                    int i4,
                                    int i5,
                                    int i6,
                                    int i7,
                                    int i8,
                                    int i9,
                                    int i10,
                                    int i11) {
                                FaceSettings faceSettings = FaceSettings.this;
                                Dialog dialog = alertDialog;
                                Preference preference2 = preference;
                                faceSettings.getClass();
                                if (!dialog.isShowing() || preference2 == null) {
                                    return;
                                }
                                Rect rect2 = new Rect();
                                preference2.seslGetPreferenceBounds(rect2);
                                dialog.semSetAnchor((rect2.left + rect2.right) / 2, rect2.bottom);
                            }
                        });
            }
        }
        this.mDeleteDialog.show();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        if (this.mPreviousStage == null) {
            return null;
        }
        return ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8400;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_face_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_lockscreen";
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "==onActivityResult requestCode : ", " resultCode : ", i, i2, "FcstFaceSettings");
        mIsKeepEnrollSession = false;
        if (intent != null) {
            if (GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
                long longExtra = intent.getLongExtra("gk_pw_handle", 0L);
                if (longExtra != 0) {
                    this.mIntent = new Intent(intent);
                    this.mGkPwHandle = longExtra;
                }
                Log.d("FcstFaceSettings", "GkPwHandle : " + this.mGkPwHandle);
            }
            byte[] byteArrayExtra = intent.getByteArrayExtra("hw_auth_token");
            if (byteArrayExtra != null) {
                this.mToken = byteArrayExtra;
                this.mChallenge = intent.getLongExtra("challenge", this.mChallenge);
                this.mSensorId = intent.getIntExtra("sensor_id", this.mSensorId);
            }
            boolean booleanExtra = intent.getBooleanExtra("biometrics_settings_destroy", false);
            this.mIsBiometricsSettingsDestroy = booleanExtra;
            if (booleanExtra) {
                Log.d("FcstFaceSettings", "onActivityResult: Finish Settings");
                cancelAndSessionEnd$1();
            }
        }
        if (i == 201) {
            this.mLaunchedConfirm = false;
            if (i2 != -1 || this.mGkPwHandle == 0) {
                finish();
                return;
            }
            Log.d("FcstFaceSettings", "onActivityResult : CONFIRM_REQUEST");
            this.mLockConfirmed = true;
            highlightPreferenceIfNeeded(true);
            requestToken(false);
            return;
        }
        if (i == 3001) {
            Log.d("FcstFaceSettings", "onActivityResult : KNOX_CHOOSE_LOCK_GENERIC_REQUEST");
            if (this.mFaceManager.hasEnrolledTemplates(this.mUserId)
                    && this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId)) {
                FaceSettingsHelper.setFaceLock(this.mUserId, getActivity(), this.mLockPatternUtils);
                this.mFaceUnlock.setChecked(true);
                return;
            }
            return;
        }
        switch (i) {
            case 1000:
                if (this.mIsRunRegister) {
                    this.mIsRunRegister = false;
                }
                if (i2 == -1) {
                    if (this.mIsRemoveAndRegister) {
                        FragmentActivity activity = getActivity();
                        int i3 = this.mUserId;
                        boolean z = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
                        Settings.Secure.putIntForUser(
                                activity.getContentResolver(), "face_recognize_mask", 1, i3);
                    }
                    this.mLockConfirmed = true;
                    this.mHasEnrolled = this.mFaceManager.hasEnrolledTemplates(this.mUserId);
                } else {
                    Log.d("FcstFaceSettings", "Registration fail!");
                    if (i2 == -3
                            || (!this.mHasEnrolled
                                    && "lock_screen_face_menu".equals(this.mPreviousStage))) {
                        setResult(0, intent);
                        cancelAndSessionEnd$1();
                    }
                }
                this.mIsRemoveAndRegister = false;
                break;
            case 1001:
                Log.d("FcstFaceSettings", "Biometrics disclaimer finished!");
                break;
            case 1002:
                Log.d("FcstFaceSettings", "Stay on Lock screen finished!");
                break;
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("FcstFaceSettings", "==onConfigurationChanged");
        if (BiometricsGenericHelper.isBlockedInMultiWindowMode(getActivity(), configuration)) {
            Toast.makeText(
                            getActivity(),
                            getString(R.string.sec_biometrics_common_not_use_multi_window_view),
                            0)
                    .show();
            finish();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        Log.d("FcstFaceSettings", "==onCreate()");
        if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                getActivity(), R.string.bio_face_recognition_title, "FcstFaceSettings")) {
            finish();
            return;
        }
        if (this.mFaceManager == null) {
            this.mFaceManager = Utils.getFaceManagerOrNull(getActivity());
        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mPreviousStage = arguments.getString("previousStage");
            this.mGkPwHandle = arguments.getLong("gk_pw_handle", 0L);
            this.mIdentifyFace = arguments.getBoolean("identifyFace");
            this.mIsAfw = arguments.getBoolean("isAfw");
            int i = arguments.getInt("android.intent.extra.USER_ID");
            this.mUserId = i;
            this.mIsKnox = SemPersonaManager.isKnoxId(i);
            this.mNeedFmmPopup = arguments.getBoolean("need_fmm_popup");
            StringBuilder m =
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            new StringBuilder("mIsAfw : "),
                            this.mIsAfw,
                            "FcstFaceSettings",
                            "mUserId : ");
            m.append(this.mUserId);
            Log.d("FcstFaceSettings", m.toString());
            StringBuilder m2 =
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            new StringBuilder("mNeedFmmPopup : "),
                            this.mNeedFmmPopup,
                            "FcstFaceSettings",
                            "GkPwHandle : ");
            m2.append(this.mGkPwHandle);
            Log.d("FcstFaceSettings", m2.toString());
            byte[] byteArray = arguments.getByteArray("hw_auth_token");
            if (byteArray != null) {
                this.mToken = byteArray;
                this.mChallenge = arguments.getLong("challenge", this.mChallenge);
                this.mSensorId = arguments.getInt("sensor_id", this.mSensorId);
            }
        } else {
            Log.secD("FcstFaceSettings", "args is null");
        }
        if (bundle != null) {
            this.mToken = bundle.getByteArray("hw_auth_token");
            this.mChallenge = bundle.getLong("challenge", this.mChallenge);
            this.mSensorId = bundle.getInt("sensor_id", this.mSensorId);
            this.mGkPwHandle = bundle.getLong("gk_pw_handle", 0L);
            z = bundle.getBoolean("is_change_configuration");
            this.mLaunchedConfirm = bundle.getBoolean("waitingForLockConfirm");
        } else {
            z = false;
        }
        setHasOptionsMenu(true);
        this.mLockPatternUtils = new LockPatternUtils(getActivity());
        this.mWallet24Service = new Wallet24Service(getContext());
        this.mGkPwProvider = new GatekeeperPasswordProvider(this.mLockPatternUtils);
        this.mHasEnrolled = this.mFaceManager.hasEnrolledTemplates(this.mUserId);
        createPreferenceHierarchy$3();
        if (z) {
            return;
        }
        if (!this.mHasEnrolled) {
            if (!this.mLockPatternUtils.isSecure(this.mUserId) || this.mGkPwHandle == 0) {
                runRegister("FaceSettings_unlock_switch");
                return;
            } else {
                requestToken(true);
                return;
            }
        }
        if (this.mGkPwHandle != 0 || this.mLaunchedConfirm) {
            requestToken(false);
            this.mLockConfirmed = true;
            return;
        }
        Log.d("FcstFaceSettings", "Launch ConfirmLock");
        this.mLaunchedConfirm = true;
        mIsKeepEnrollSession = true;
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(getActivity(), this);
        builder.mRequestCode = 201;
        builder.mHeader = BiometricsGenericHelper.getConfirmLockHeader(getActivity(), this.mUserId);
        builder.mRequestGatekeeperPasswordHandle = true;
        builder.mForegroundOnly = true;
        builder.mReturnCredentials = true;
        int i2 = this.mUserId;
        if (i2 != -10000) {
            builder.mUserId = i2;
        }
        if (SemPersonaManager.isKnoxId(i2) && !SemPersonaManager.isSecureFolderId(this.mUserId)) {
            builder.mKnoxWorkProfileSecurity = true;
        }
        boolean show = builder.show();
        getActivity().overridePendingTransition(0, 0);
        if (show) {
            return;
        }
        Log.e("FcstFaceSettings", "Launch ConfirmLock - Fail");
        this.mLaunchedConfirm = false;
        mIsKeepEnrollSession = false;
        finish();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        this.mPreferenceHighlighted = true;
        return super.onCreateAdapter(preferenceScreen);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        Log.d("FcstFaceSettings", "==onDestroy()");
        if (this.mIsRunRegister) {
            Log.d("FcstFaceSettings", "reset runRegister");
            this.mIsRunRegister = false;
        }
        if ("lock_screen_face_menu".equals(this.mPreviousStage)) {
            mIsKeepEnrollSession = true;
        }
        if (this.mGkPwProvider != null && !mIsKeepEnrollSession) {
            Log.d("FcstFaceSettings", "onDestroy: remove GatekeeperPasswordHandle");
            this.mGkPwProvider.removeGatekeeperPasswordHandle(this.mGkPwHandle);
        }
        super.onDestroy();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            BiometricsGenericHelper.insertSaLog(getContext(), 8400, 8401);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("FcstFaceSettings", "==onPause()");
        if (getActivity().isChangingConfigurations()) {
            return;
        }
        this.mIsBiometricsSettingsDestroy = !mIsKeepEnrollSession;
        cancelAndSessionEnd$1();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        FaceManager faceManagerOrNull;
        String key = preference.getKey();
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean equals = preference.equals(this.mFaceUnlock);
        String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        if (equals) {
            if (UserManager.get(getActivity()) != null
                    && UserManager.get(getActivity()).isManagedProfile(this.mUserId)
                    && !this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId)) {
                Log.d("FcstFaceSettings", "launchChooseLock");
                Intent biometricsChooseLockGenericIntent =
                        BiometricsGenericHelper.getBiometricsChooseLockGenericIntent(
                                getContext(), false);
                biometricsChooseLockGenericIntent.putExtra("request_gk_pw_handle", true);
                biometricsChooseLockGenericIntent.putExtra("for_face", true);
                biometricsChooseLockGenericIntent.putExtra("hide_insecure_options", true);
                biometricsChooseLockGenericIntent.putExtra("fromSetupWizard", false);
                biometricsChooseLockGenericIntent.putExtra(
                        "set_biometric_lock",
                        !"face_register_external".equals(this.mPreviousStage));
                biometricsChooseLockGenericIntent.addFlags(65536);
                try {
                    startActivityForResultWrapper(
                            VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE,
                            biometricsChooseLockGenericIntent);
                } catch (ActivityNotFoundException unused) {
                    Log.d("FcstFaceSettings", "Activity Not Found !");
                }
                return false;
            }
            getContext();
            if (booleanValue) {
                str = "1";
            }
            LoggingHelper.insertEventLogging(8400, 8404, 0L, str);
            if (this.mFaceManager.hasEnrolledTemplates(this.mUserId)) {
                if (!booleanValue) {
                    FaceSettingsHelper.removeFaceLock(
                            this.mUserId, getActivity(), this.mLockPatternUtils);
                    SwitchPreference switchPreference = this.mStayOnLockScreen;
                    if (switchPreference != null) {
                        switchPreference.setEnabled(false);
                    }
                } else if (this.mLockPatternUtils.isSecure(this.mUserId)) {
                    FaceSettingsHelper.setFaceLock(
                            this.mUserId, getActivity(), this.mLockPatternUtils);
                    SwitchPreference switchPreference2 = this.mStayOnLockScreen;
                    if (switchPreference2 != null) {
                        switchPreference2.setEnabled(true);
                    }
                } else {
                    runRegister("FaceSettings_unlock_switch");
                }
            } else if (booleanValue) {
                runRegister("FaceSettings_unlock_switch");
                return false;
            }
        } else if (preference.equals(this.mStayOnLockScreen)) {
            getContext();
            if (booleanValue) {
                str = "1";
            }
            LoggingHelper.insertEventLogging(8400, 8437, 0L, str);
            FaceSettingsHelper.setFaceStayOnLockScreen(getActivity(), this.mUserId, booleanValue);
        } else if (preference.equals(this.mRecognizeWithMask)) {
            if (booleanValue) {
                FragmentActivity activity = getActivity();
                boolean z = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
                if ((activity == null
                                || (faceManagerOrNull = Utils.getFaceManagerOrNull(activity))
                                        == null)
                        ? false
                        : faceManagerOrNull.semShouldRemoveTemplate()) {
                    deleteFace(false, true);
                    return false;
                }
            }
            FragmentActivity activity2 = getActivity();
            int i = this.mUserId;
            boolean z2 = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
            Settings.Secure.putIntForUser(
                    activity2.getContentResolver(), "face_recognize_mask", booleanValue ? 1 : 0, i);
            getContext();
            if (booleanValue) {
                str = "1";
            }
            LoggingHelper.insertEventLogging(8400, 8456, 0L, str);
        } else if (preference.equals(this.mOpenEyes)) {
            FaceSettingsHelper.setFaceOpenEyes(getActivity(), this.mUserId, booleanValue);
            getContext();
            if (booleanValue) {
                str = "1";
            }
            LoggingHelper.insertEventLogging(8400, 8447, 0L, str);
        } else if (preference.equals(this.mBrightenScreen)) {
            FaceSettingsHelper.setFaceBrightenScreen(getActivity(), this.mUserId, booleanValue);
            getContext();
            if (booleanValue) {
                str = "1";
            }
            LoggingHelper.insertEventLogging(8400, 8430, 0L, str);
        } else {
            DialogFragment$$ExternalSyntheticOutline0.m("key:", key, "FcstFaceSettings");
        }
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        Log.d("FcstFaceSettings", "==onPreferenceTreeClick : " + preference);
        String key = preference.getKey();
        key.getClass();
        switch (key) {
            case "key_facelock_register":
                runRegister("FaceSettings_register");
                return true;
            case "key_facelock_remove":
                BiometricsGenericHelper.insertSaLog(getContext(), 8400, 8403);
                BiometricsGenericHelper.insertSaLog(getContext(), 8406);
                this.mAnchorPreference = preference;
                deleteFace(false, false);
                return true;
            case "key_facelock_remove_alternative":
                BiometricsGenericHelper.insertSaLog(getContext(), 8400, 8446);
                BiometricsGenericHelper.insertSaLog(getContext(), 8449);
                this.mAnchorPreference = preference;
                deleteFace(true, false);
                return true;
            case "key_face_about_face_recognition":
                BiometricsGenericHelper.insertSaLog(getContext(), 8400, 8402);
                Log.d("FcstFaceSettings", "startBiometricsDisclaimer");
                Intent intent = new Intent();
                intent.setClassName(
                        getActivity().getPackageName(),
                        "com.samsung.android.settings.biometrics.BiometricsDisclaimerSettingsActivity");
                intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
                intent.putExtra("BIOMETRICS_LOCK_TYPE", 256);
                intent.putExtra("fromSettingsOption", true);
                try {
                    startActivityForResultWrapper(1001, intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Log.d("FcstFaceSettings", "startBiometricsDisclaimer : Activity Not Found !");
                }
                return true;
            case "key_facelock_register_alternative":
                BiometricsGenericHelper.insertSaLog(getContext(), 8400, 8445);
                runRegister("FaceSettings_register_alternative");
                return true;
            default:
                return super.onPreferenceTreeClick(preference);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("FcstFaceSettings", "==onResume()");
        if (!this.mLaunchedConfirm && !this.mIsRunRegister) {
            mIsKeepEnrollSession = false;
        }
        if (getActivity() != null
                && ((Rune.isSamsungDexMode(getActivity())
                                || LockUtils.isInMultiWindow(getActivity()))
                        && !BiometricsGenericHelper.isEnabledBiometricsMenuInDexMode(
                                getActivity()))) {
            Log.d("FcstFaceSettings", "isDesktopMode or isInMultiWindowMode is TRUE.");
            if (isRemoving()) {
                return;
            }
            finish();
            return;
        }
        updatePreferences$5();
        if (this.mNeedFmmPopup) {
            this.mNeedFmmPopup = false;
            Log.d("FcstFaceSettings", "startFmmBackupPasswordPopup");
            Intent fmmPopupIntent = BiometricsGenericHelper.getFmmPopupIntent();
            fmmPopupIntent.addFlags(65536);
            try {
                startActivityForResultWrapper(2000, fmmPopupIntent);
            } catch (Exception e) {
                Log.e("FcstFaceSettings", "Exception occured!");
                e.printStackTrace();
            }
        }
        highlightPreferenceIfNeeded(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putByteArray("hw_auth_token", this.mToken);
        bundle.putLong("challenge", this.mChallenge);
        bundle.putInt("sensor_id", this.mSensorId);
        bundle.putLong("gk_pw_handle", this.mGkPwHandle);
        bundle.putBoolean("is_change_configuration", !getActivity().isChangingConfigurations());
        bundle.putBoolean("waitingForLockConfirm", this.mLaunchedConfirm);
    }

    public final void requestToken(final boolean z) {
        FaceManager faceManager = this.mFaceManager;
        if (faceManager == null || this.mGkPwHandle == 0 || this.mToken != null) {
            return;
        }
        faceManager.generateChallenge(
                this.mUserId,
                new FaceManager
                        .GenerateChallengeCallback() { // from class:
                                                       // com.samsung.android.settings.biometrics.face.FaceSettings$$ExternalSyntheticLambda0
                    public final void onGenerateChallengeResult(int i, int i2, long j) {
                        FaceSettings faceSettings = FaceSettings.this;
                        boolean z2 = z;
                        faceSettings.mSensorId = i;
                        faceSettings.mChallenge = j;
                        GatekeeperPasswordProvider gatekeeperPasswordProvider =
                                faceSettings.mGkPwProvider;
                        if (gatekeeperPasswordProvider != null) {
                            faceSettings.mToken =
                                    gatekeeperPasswordProvider.requestGatekeeperHat(
                                            faceSettings.mGkPwHandle, j, faceSettings.mUserId);
                        }
                        if (faceSettings.mToken == null) {
                            Log.e("FcstFaceSettings", "token is NULL!");
                            FaceSettingsHelper.showFaceSensorErrorDialog(
                                    (Activity) faceSettings.getContext(),
                                    null,
                                    faceSettings.getString(R.string.sec_biometrics_error_timed_out),
                                    true);
                        } else if (z2) {
                            faceSettings.runRegister("FaceSettings_unlock_switch");
                        }
                    }
                });
    }

    public final void runRegister(String str) {
        if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                getActivity(), R.string.bio_face_recognition_title, "FcstFaceSettings")) {
            return;
        }
        Intent intent = new Intent().setClass(getContext(), FaceLockSettings.class);
        intent.putExtra("previousStage", str);
        intent.putExtra("gk_pw_handle", this.mGkPwHandle);
        intent.putExtra("identifyFace", this.mIdentifyFace);
        intent.addFlags(536870912);
        byte[] bArr = this.mToken;
        if (bArr != null) {
            intent.putExtra("hw_auth_token", bArr);
            intent.putExtra("challenge", this.mChallenge);
            intent.putExtra("sensor_id", this.mSensorId);
        }
        if (this.mIsRunRegister) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("runRegister already called : "),
                    this.mIsRunRegister,
                    "FcstFaceSettings");
            return;
        }
        this.mIsRunRegister = true;
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("runRegister called : "),
                this.mIsRunRegister,
                "FcstFaceSettings");
        try {
            startActivityForResultWrapper(1000, intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public final void startActivityForResultWrapper(int i, Intent intent) {
        Log.d("FcstFaceSettings", "startActivityForResultWrapper:" + intent);
        intent.putExtra("isAfw", this.mIsAfw);
        intent.putExtra("is_knox", this.mIsKnox);
        intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
        try {
            startActivityForResult(intent, i);
            mIsKeepEnrollSession = true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public final void updatePreferences$5() {
        Intent launchIntentForPackage;
        createPreferenceHierarchy$3();
        if (!this.mIsKnox) {
            getActivity();
            if (Rune.supportRelativeLink()) {
                if (this.mRelativeLinkView == null) {
                    this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
                    if (SecurityUtils.isEnabledSamsungPass(getActivity())
                            && (launchIntentForPackage =
                                            getPackageManager()
                                                    .getLaunchIntentForPackage(
                                                            "com.samsung.android.samsungpass"))
                                    != null) {
                        SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                                new SettingsPreferenceFragmentLinkData();
                        settingsPreferenceFragmentLinkData.flowId = "8420";
                        settingsPreferenceFragmentLinkData.callerMetric = 8400;
                        settingsPreferenceFragmentLinkData.intent = launchIntentForPackage;
                        settingsPreferenceFragmentLinkData.titleRes =
                                R.string.iris_use_samsung_pass;
                        if (UserHandle.myUserId() == 0
                                || (SemPersonaManager.isSecureFolderId(this.mUserId)
                                        && !this.mIsAfw)) {
                            this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
                        }
                    }
                }
                this.mRelativeLinkView.create(this);
            }
        }
        if (this.mEmptyView == null) {
            this.mEmptyView = new View(getContext());
        }
        BiometricsGenericHelper.hideMenuList(getActivity(), this.mEmptyView, !this.mLockConfirmed);
    }
}
