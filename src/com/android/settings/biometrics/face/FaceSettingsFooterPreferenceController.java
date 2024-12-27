package com.android.settings.biometrics.face;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.utils.AnnotationSpan;
import com.android.settingslib.HelpUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSettingsFooterPreferenceController extends BasePreferenceController {
    private static final String ANNOTATION_URL = "url";
    private static final String KEY = "security_face_footer";
    private static final String TAG = "FaceSettingsFooterPreferenceController";
    private boolean mIsFaceStrong;
    private Preference mPreference;
    private final FaceFeatureProvider mProvider;
    private int mUserId;

    public FaceSettingsFooterPreferenceController(Context context) {
        this(context, KEY);
    }

    private void addAuthenticatorsRegisteredCallback(Context context) {
        ((FaceManager) context.getSystemService(FaceManager.class))
                .addAuthenticatorsRegisteredCallback(
                        new IFaceAuthenticatorsRegisteredCallback
                                .Stub() { // from class:
                                          // com.android.settings.biometrics.face.FaceSettingsFooterPreferenceController.1
                            public final void onAllAuthenticatorsRegistered(List list) {
                                if (list.isEmpty()) {
                                    Log.e(FaceSettingsFooterPreferenceController.TAG, "No sensors");
                                    return;
                                }
                                boolean z =
                                        ((FaceSensorPropertiesInternal) list.get(0)).sensorStrength
                                                == 2;
                                if (FaceSettingsFooterPreferenceController.this.mIsFaceStrong
                                        == z) {
                                    return;
                                }
                                FaceSettingsFooterPreferenceController.this.mIsFaceStrong = z;
                                FaceSettingsFooterPreferenceController
                                        faceSettingsFooterPreferenceController =
                                                FaceSettingsFooterPreferenceController.this;
                                faceSettingsFooterPreferenceController.updateState(
                                        faceSettingsFooterPreferenceController.mPreference);
                            }
                        });
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(this.mPreferenceKey);
        if (preferenceScreen
                .getContext()
                .getPackageManager()
                .hasSystemFeature("android.hardware.biometrics.face")) {
            addAuthenticatorsRegisteredCallback(preferenceScreen.getContext());
        } else {
            Log.w(TAG, "Not support FEATURE_FACE");
        }
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
        Context context = this.mContext;
        AnnotationSpan.LinkInfo linkInfo =
                new AnnotationSpan.LinkInfo(
                        this.mContext,
                        HelpUtils.getHelpIntent(
                                context,
                                context.getString(R.string.help_url_face),
                                getClass().getName()));
        this.mProvider.getClass();
        preference.setTitle(
                AnnotationSpan.linkify(
                        this.mContext.getText(
                                Utils.isPrivateProfile(this.mContext, this.mUserId)
                                        ? R.string.private_space_face_settings_footer
                                        : this.mIsFaceStrong
                                                ? R.string
                                                        .security_settings_face_settings_footer_class3
                                                : R.string.security_settings_face_settings_footer),
                        linkInfo));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FaceSettingsFooterPreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mProvider = featureFactoryImpl.getFaceFeatureProvider();
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
