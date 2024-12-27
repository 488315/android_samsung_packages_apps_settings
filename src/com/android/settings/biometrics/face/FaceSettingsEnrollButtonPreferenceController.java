package com.android.settings.biometrics.face;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.widget.LayoutPreference;

import com.google.android.setupdesign.util.ButtonStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSettingsEnrollButtonPreferenceController extends BasePreferenceController
        implements View.OnClickListener {
    static final String KEY = "security_settings_face_enroll_faces_container";
    private static final String TAG = "FaceSettings/Remove";
    private Button mButton;
    private final Context mContext;
    private boolean mIsClicked;
    private Listener mListener;
    private byte[] mToken;
    private int mUserId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {}

    public FaceSettingsEnrollButtonPreferenceController(Context context) {
        this(context, KEY);
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

    public boolean isClicked() {
        boolean z = this.mIsClicked;
        this.mIsClicked = false;
        return z;
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
        this.mIsClicked = true;
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                FaceEnrollIntroduction.class.getName());
        intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
        intent.putExtra("hw_auth_token", this.mToken);
        Listener listener = this.mListener;
        if (listener == null) {
            this.mContext.startActivity(intent);
            return;
        }
        FaceSettings$$ExternalSyntheticLambda0 faceSettings$$ExternalSyntheticLambda0 =
                (FaceSettings$$ExternalSyntheticLambda0) listener;
        faceSettings$$ExternalSyntheticLambda0.getClass();
        BaseSearchIndexProvider baseSearchIndexProvider = FaceSettings.SEARCH_INDEX_DATA_PROVIDER;
        faceSettings$$ExternalSyntheticLambda0.f$0.startActivityForResult(intent, 5);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void setToken(byte[] bArr) {
        this.mToken = bArr;
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
        Button button =
                (Button)
                        ((LayoutPreference) preference)
                                .mRootView.findViewById(
                                        R.id.security_settings_face_settings_enroll_button);
        this.mButton = button;
        if (PartnerStyleHelper.shouldApplyPartnerResource(button)) {
            ButtonStyler.applyPartnerCustomizationPrimaryButtonStyle(this.mContext, this.mButton);
        }
        this.mButton.setOnClickListener(this);
        this.mButton.setEnabled(
                !(RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                                this.mContext, 128, this.mUserId)
                        != null));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FaceSettingsEnrollButtonPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
