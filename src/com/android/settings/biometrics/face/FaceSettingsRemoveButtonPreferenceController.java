package com.android.settings.biometrics.face;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.LayoutPreference;

import com.google.android.setupdesign.util.ButtonStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSettingsRemoveButtonPreferenceController extends BasePreferenceController
        implements View.OnClickListener {
    static final String KEY = "security_settings_face_delete_faces_container";
    private static final String TAG = "FaceSettings/Remove";
    private SettingsActivity mActivity;
    private Button mButton;
    private final Context mContext;
    private final FaceManager mFaceManager;
    private final FaceUpdater mFaceUpdater;
    private Listener mListener;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private final DialogInterface.OnClickListener mOnConfirmDialogClickListener;
    private Preference mPreference;
    private final FaceManager.RemovalCallback mRemovalCallback;
    boolean mRemoving;
    private int mUserId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ConfirmRemoveDialog extends InstrumentedDialogFragment
            implements OnBackInvokedCallback {
        public AlertDialog mDialog = null;
        public Preference mFaceUnlockPreference = null;
        public DialogInterface.OnClickListener mOnClickListener;

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 1693;
        }

        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            Button button;
            AlertDialog alertDialog = this.mDialog;
            if (alertDialog != null) {
                alertDialog.cancel();
            }
            AlertDialog alertDialog2 = this.mDialog;
            if (alertDialog2 != null) {
                alertDialog2.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this);
            }
            Preference preference = this.mFaceUnlockPreference;
            if (preference == null
                    || (button =
                                    (Button)
                                            ((LayoutPreference) preference)
                                                    .mRootView.findViewById(
                                                            R.id
                                                                    .security_settings_face_settings_remove_button))
                            == null) {
                return;
            }
            button.setEnabled(true);
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            boolean z = getArguments().getBoolean("is_convenience");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.security_settings_face_settings_remove_dialog_title)
                    .setMessage(
                            getContext()
                                            .getPackageManager()
                                            .hasSystemFeature("android.hardware.fingerprint")
                                    ? z
                                            ? R.string
                                                    .security_settings_face_remove_dialog_details_fingerprint_conv
                                            : R.string
                                                    .security_settings_face_remove_dialog_details_fingerprint
                                    : z
                                            ? R.string
                                                    .security_settings_face_settings_remove_dialog_details_convenience
                                            : R.string
                                                    .security_settings_face_settings_remove_dialog_details)
                    .setPositiveButton(R.string.delete, this.mOnClickListener)
                    .setNegativeButton(R.string.cancel, this.mOnClickListener);
            AlertDialog create = builder.create();
            this.mDialog = create;
            create.setCanceledOnTouchOutside(false);
            this.mDialog.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this);
            return this.mDialog;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {}

    public FaceSettingsRemoveButtonPreferenceController(Context context, String str) {
        super(context, str);
        this.mRemovalCallback =
                new FaceManager
                        .RemovalCallback() { // from class:
                                             // com.android.settings.biometrics.face.FaceSettingsRemoveButtonPreferenceController.1
                    public final void onRemovalError(Face face, int i, CharSequence charSequence) {
                        Log.e(
                                FaceSettingsRemoveButtonPreferenceController.TAG,
                                "Unable to remove face: "
                                        + face.getBiometricId()
                                        + " error: "
                                        + i
                                        + " "
                                        + ((Object) charSequence));
                        Toast.makeText(
                                        FaceSettingsRemoveButtonPreferenceController.this.mContext,
                                        charSequence,
                                        0)
                                .show();
                        FaceSettingsRemoveButtonPreferenceController.this.mRemoving = false;
                    }

                    public final void onRemovalSucceeded(Face face, int i) {
                        if (i != 0) {
                            Log.v(
                                    FaceSettingsRemoveButtonPreferenceController.TAG,
                                    "Remaining: " + i);
                        } else {
                            if (!FaceSettingsRemoveButtonPreferenceController.this
                                    .mFaceManager
                                    .getEnrolledFaces(
                                            FaceSettingsRemoveButtonPreferenceController.this
                                                    .mUserId)
                                    .isEmpty()) {
                                FaceSettingsRemoveButtonPreferenceController.this.mButton
                                        .setEnabled(true);
                                return;
                            }
                            FaceSettingsRemoveButtonPreferenceController
                                    faceSettingsRemoveButtonPreferenceController =
                                            FaceSettingsRemoveButtonPreferenceController.this;
                            faceSettingsRemoveButtonPreferenceController.mRemoving = false;
                            FaceSettings faceSettings =
                                    ((FaceSettings$$ExternalSyntheticLambda0)
                                                    faceSettingsRemoveButtonPreferenceController
                                                            .mListener)
                                            .f$0;
                            Iterator it = ((ArrayList) faceSettings.mTogglePreferences).iterator();
                            while (it.hasNext()) {
                                ((Preference) it.next()).setEnabled(false);
                            }
                            faceSettings.mRemoveButton.setVisible(false);
                            faceSettings.mEnrollButton.setVisible(true);
                        }
                    }
                };
        this.mOnConfirmDialogClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.biometrics.face.FaceSettingsRemoveButtonPreferenceController.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog alertDialog;
                        if (i == -1) {
                            FaceSettingsRemoveButtonPreferenceController.this.mButton.setEnabled(
                                    false);
                            List enrolledFaces =
                                    FaceSettingsRemoveButtonPreferenceController.this.mFaceManager
                                            .getEnrolledFaces(
                                                    FaceSettingsRemoveButtonPreferenceController
                                                            .this
                                                            .mUserId);
                            if (enrolledFaces.isEmpty()) {
                                Log.e(FaceSettingsRemoveButtonPreferenceController.TAG, "No faces");
                                return;
                            }
                            if (enrolledFaces.size() > 1) {
                                Log.e(
                                        FaceSettingsRemoveButtonPreferenceController.TAG,
                                        "Multiple enrollments: " + enrolledFaces.size());
                            }
                            FaceUpdater faceUpdater =
                                    FaceSettingsRemoveButtonPreferenceController.this.mFaceUpdater;
                            faceUpdater.mFaceManager.remove(
                                    (Face) enrolledFaces.get(0),
                                    FaceSettingsRemoveButtonPreferenceController.this.mUserId,
                                    new FaceUpdater.NotifyingRemovalCallback(
                                            faceUpdater.mContext,
                                            FaceSettingsRemoveButtonPreferenceController.this
                                                    .mRemovalCallback));
                        } else {
                            FaceSettingsRemoveButtonPreferenceController.this.mButton.setEnabled(
                                    true);
                            FaceSettingsRemoveButtonPreferenceController.this.mRemoving = false;
                        }
                        ConfirmRemoveDialog confirmRemoveDialog =
                                (ConfirmRemoveDialog)
                                        FaceSettingsRemoveButtonPreferenceController.this
                                                .mActivity
                                                .getSupportFragmentManager()
                                                .findFragmentByTag(
                                                        ConfirmRemoveDialog.class.getName());
                        if (confirmRemoveDialog == null
                                || (alertDialog = confirmRemoveDialog.mDialog) == null) {
                            return;
                        }
                        alertDialog
                                .getOnBackInvokedDispatcher()
                                .unregisterOnBackInvokedCallback(confirmRemoveDialog);
                    }
                };
        this.mContext = context;
        FaceManager faceManager = (FaceManager) context.getSystemService(FaceManager.class);
        this.mFaceManager = faceManager;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mFaceUpdater = new FaceUpdater(context, faceManager);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
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

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mButton) {
            this.mMetricsFeatureProvider.logClickedPreference(
                    this.mPreference, getMetricsCategory());
            boolean z = true;
            this.mRemoving = true;
            Iterator it = this.mFaceManager.getSensorPropertiesInternal().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                } else if (((FaceSensorPropertiesInternal) it.next()).sensorStrength == 0) {
                    break;
                }
            }
            ConfirmRemoveDialog confirmRemoveDialog = new ConfirmRemoveDialog();
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_convenience", z);
            confirmRemoveDialog.setArguments(bundle);
            confirmRemoveDialog.mOnClickListener = this.mOnConfirmDialogClickListener;
            confirmRemoveDialog.show(
                    this.mActivity.getSupportFragmentManager(),
                    ConfirmRemoveDialog.class.getName());
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setActivity(SettingsActivity settingsActivity) {
        this.mActivity = settingsActivity;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference = preference;
        Button button =
                (Button)
                        ((LayoutPreference) preference)
                                .mRootView.findViewById(
                                        R.id.security_settings_face_settings_remove_button);
        this.mButton = button;
        if (PartnerStyleHelper.shouldApplyPartnerResource(button)) {
            ButtonStyler.applyPartnerCustomizationPrimaryButtonStyle(this.mContext, this.mButton);
        }
        this.mButton.setOnClickListener(this);
        ConfirmRemoveDialog confirmRemoveDialog =
                (ConfirmRemoveDialog)
                        this.mActivity
                                .getSupportFragmentManager()
                                .findFragmentByTag(ConfirmRemoveDialog.class.getName());
        if (confirmRemoveDialog != null) {
            confirmRemoveDialog.mFaceUnlockPreference = this.mPreference;
            this.mRemoving = true;
            confirmRemoveDialog.mOnClickListener = this.mOnConfirmDialogClickListener;
        }
        if (FaceSettings.isFaceHardwareDetected(this.mContext)) {
            this.mButton.setEnabled(!this.mRemoving);
        } else {
            this.mButton.setEnabled(false);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FaceSettingsRemoveButtonPreferenceController(Context context) {
        this(context, KEY);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
