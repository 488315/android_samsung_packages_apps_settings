package com.android.settings.biometrics.face;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.widget.Button;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.security_settings_face);
    public FaceSettingsAttentionPreferenceController mAttentionController;
    public long mChallenge;
    public boolean mConfirmingPassword;
    public List mControllers;
    public DevicePolicyManager mDevicePolicyManager;
    public Preference mEnrollButton;
    public FaceSettingsEnrollButtonPreferenceController mEnrollController;
    public FaceFeatureProvider mFaceFeatureProvider;
    public FaceManager mFaceManager;
    public FaceSettingsLockscreenBypassPreferenceController mLockscreenController;
    public Preference mRemoveButton;
    public FaceSettingsRemoveButtonPreferenceController mRemoveController;
    public int mSensorId;
    public List mTogglePreferences;
    public byte[] mToken;
    public int mUserId;
    public UserManager mUserManager;
    public final FaceSettings$$ExternalSyntheticLambda0 mRemovalListener =
            new FaceSettings$$ExternalSyntheticLambda0(this);
    public final FaceSettings$$ExternalSyntheticLambda0 mEnrollListener =
            new FaceSettings$$ExternalSyntheticLambda0(this);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.face.FaceSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            if (FaceSettings.isFaceHardwareDetected(context)) {
                return FaceSettings.buildPreferenceControllers(context);
            }
            return null;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            boolean isFaceHardwareDetected = FaceSettings.isFaceHardwareDetected(context);
            StringBuilder m =
                    RowView$$ExternalSyntheticOutline0.m(
                            "Get non indexable keys. isFaceHardwareDetected: ",
                            ", size:",
                            isFaceHardwareDetected);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            m.append(arrayList.size());
            Log.d("FaceSettings", m.toString());
            if (isFaceHardwareDetected) {
                FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(context);
                arrayList.add(
                        faceManagerOrNull != null
                                ? faceManagerOrNull.hasEnrolledTemplates(UserHandle.myUserId())
                                : false
                                        ? "security_settings_face_enroll_faces_container"
                                        : "security_settings_face_delete_faces_container");
            }
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getFaceFeatureProvider().getClass();
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return false;
        }
    }

    public static List buildPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new FaceSettingsKeyguardPreferenceController(context));
        arrayList.add(new FaceSettingsAppPreferenceController(context));
        arrayList.add(new FaceSettingsAttentionPreferenceController(context));
        arrayList.add(new FaceSettingsRemoveButtonPreferenceController(context));
        arrayList.add(new FaceSettingsConfirmPreferenceController(context));
        arrayList.add(new FaceSettingsEnrollButtonPreferenceController(context));
        arrayList.add(new FaceSettingsFooterPreferenceController(context));
        return arrayList;
    }

    public static boolean isFaceHardwareDetected(Context context) {
        boolean isHardwareDetected;
        FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(context);
        if (faceManagerOrNull == null) {
            Log.d("FaceSettings", "FaceManager is null");
            isHardwareDetected = false;
        } else {
            isHardwareDetected = faceManagerOrNull.isHardwareDetected();
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "FaceManager is not null. Hardware detected: ",
                    "FaceSettings",
                    isHardwareDetected);
        }
        return faceManagerOrNull != null && isHardwareDetected;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        if (!isFaceHardwareDetected(context)) {
            return null;
        }
        List buildPreferenceControllers = buildPreferenceControllers(context);
        this.mControllers = buildPreferenceControllers;
        Iterator it = ((ArrayList) buildPreferenceControllers).iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if (abstractPreferenceController instanceof FaceSettingsAttentionPreferenceController) {
                this.mAttentionController =
                        (FaceSettingsAttentionPreferenceController) abstractPreferenceController;
            } else if (abstractPreferenceController
                    instanceof FaceSettingsRemoveButtonPreferenceController) {
                FaceSettingsRemoveButtonPreferenceController
                        faceSettingsRemoveButtonPreferenceController =
                                (FaceSettingsRemoveButtonPreferenceController)
                                        abstractPreferenceController;
                this.mRemoveController = faceSettingsRemoveButtonPreferenceController;
                faceSettingsRemoveButtonPreferenceController.setListener(this.mRemovalListener);
                this.mRemoveController.setActivity((SettingsActivity) getActivity());
            } else if (abstractPreferenceController
                    instanceof FaceSettingsEnrollButtonPreferenceController) {
                FaceSettingsEnrollButtonPreferenceController
                        faceSettingsEnrollButtonPreferenceController =
                                (FaceSettingsEnrollButtonPreferenceController)
                                        abstractPreferenceController;
                this.mEnrollController = faceSettingsEnrollButtonPreferenceController;
                faceSettingsEnrollButtonPreferenceController.setListener(this.mEnrollListener);
            }
        }
        return this.mControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "FaceSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1511;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.security_settings_face;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, final Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.mToken == null && !BiometricUtils.containsGatekeeperPasswordHandle(intent)) {
            Log.e("FaceSettings", "No credential");
            finish();
        }
        if (i != 4) {
            if (i == 5 && i2 == 3) {
                setResult(i2, intent);
                finish();
                return;
            }
            return;
        }
        if (i2 == 1 || i2 == -1) {
            this.mFaceManager.generateChallenge(
                    this.mUserId,
                    new FaceManager
                            .GenerateChallengeCallback() { // from class:
                                                           // com.android.settings.biometrics.face.FaceSettings$$ExternalSyntheticLambda3
                        public final void onGenerateChallengeResult(int i3, int i4, long j) {
                            FaceSettings faceSettings = FaceSettings.this;
                            Intent intent2 = intent;
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    FaceSettings.SEARCH_INDEX_DATA_PROVIDER;
                            faceSettings.mToken =
                                    BiometricUtils.requestGatekeeperHat(
                                            faceSettings.getPrefContext(),
                                            intent2,
                                            faceSettings.mUserId,
                                            j);
                            faceSettings.mSensorId = i3;
                            faceSettings.mChallenge = j;
                            BiometricUtils.removeGatekeeperPasswordHandle(
                                    faceSettings.getPrefContext(), intent2);
                            faceSettings.mAttentionController.setToken(faceSettings.mToken);
                            faceSettings.mEnrollController.setToken(faceSettings.mToken);
                            faceSettings.mConfirmingPassword = false;
                        }
                    });
            boolean hasEnrolledTemplates = this.mFaceManager.hasEnrolledTemplates(this.mUserId);
            this.mEnrollButton.setVisible(!hasEnrolledTemplates);
            this.mRemoveButton.setVisible(hasEnrolledTemplates);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context prefContext = getPrefContext();
        if (!isFaceHardwareDetected(prefContext)) {
            Log.w("FaceSettings", "no faceManager, finish this");
            finish();
            return;
        }
        this.mUserManager = (UserManager) prefContext.getSystemService(UserManager.class);
        this.mFaceManager = (FaceManager) prefContext.getSystemService(FaceManager.class);
        this.mDevicePolicyManager =
                (DevicePolicyManager) prefContext.getSystemService(DevicePolicyManager.class);
        this.mToken = getIntent().getByteArrayExtra("hw_auth_token");
        this.mSensorId = getIntent().getIntExtra("sensor_id", -1);
        this.mChallenge = getIntent().getLongExtra("challenge", 0L);
        this.mUserId =
                getActivity()
                        .getIntent()
                        .getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFaceFeatureProvider = featureFactoryImpl.getFaceFeatureProvider();
        if (this.mUserManager.getUserInfo(this.mUserId).isManagedProfile()) {
            getActivity()
                    .setTitle(
                            this.mDevicePolicyManager
                                    .getResources()
                                    .getString(
                                            "Settings.FACE_SETTINGS_FOR_WORK_TITLE",
                                            new Supplier() { // from class:
                                                             // com.android.settings.biometrics.face.FaceSettings$$ExternalSyntheticLambda2
                                                @Override // java.util.function.Supplier
                                                public final Object get() {
                                                    FaceSettings faceSettings = FaceSettings.this;
                                                    BaseSearchIndexProvider
                                                            baseSearchIndexProvider =
                                                                    FaceSettings
                                                                            .SEARCH_INDEX_DATA_PROVIDER;
                                                    return faceSettings
                                                            .getActivity()
                                                            .getResources()
                                                            .getString(
                                                                    R.string
                                                                            .security_settings_face_profile_preference_title);
                                                }
                                            }));
        } else {
            if (Utils.isPrivateProfile(getContext(), this.mUserId)) {
                getActivity()
                        .setTitle(
                                getActivity()
                                        .getResources()
                                        .getString(R.string.private_space_face_unlock_title));
            }
        }
        FaceSettingsLockscreenBypassPreferenceController
                faceSettingsLockscreenBypassPreferenceController =
                        Utils.isMultipleBiometricsSupported(prefContext)
                                ? (FaceSettingsLockscreenBypassPreferenceController)
                                        use(BiometricLockscreenBypassPreferenceController.class)
                                : (FaceSettingsLockscreenBypassPreferenceController)
                                        use(FaceSettingsLockscreenBypassPreferenceController.class);
        this.mLockscreenController = faceSettingsLockscreenBypassPreferenceController;
        faceSettingsLockscreenBypassPreferenceController.setUserId(this.mUserId);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) findPreference("security_settings_face_manage_category");
        this.mTogglePreferences =
                new ArrayList(
                        Arrays.asList(
                                findPreference("security_settings_face_keyguard"),
                                findPreference("security_settings_face_app"),
                                findPreference(FaceSettingsAttentionPreferenceController.KEY),
                                findPreference("security_settings_face_require_confirmation"),
                                findPreference(this.mLockscreenController.getPreferenceKey())));
        if (RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                        getContext(), 128, this.mUserId)
                != null) {
            preferenceCategory.setTitle(getString(R.string.disabled_by_admin));
        } else {
            preferenceCategory.setTitle(
                    R.string.security_settings_face_settings_preferences_category);
        }
        this.mRemoveButton = findPreference("security_settings_face_delete_faces_container");
        this.mEnrollButton = findPreference("security_settings_face_enroll_faces_container");
        boolean hasEnrolledTemplates = this.mFaceManager.hasEnrolledTemplates(this.mUserId);
        this.mEnrollButton.setVisible(!hasEnrolledTemplates);
        this.mRemoveButton.setVisible(hasEnrolledTemplates);
        Iterator it = ((ArrayList) this.mControllers).iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if (abstractPreferenceController instanceof FaceSettingsPreferenceController) {
                ((FaceSettingsPreferenceController) abstractPreferenceController)
                        .setUserId(this.mUserId);
            } else if (abstractPreferenceController
                    instanceof FaceSettingsEnrollButtonPreferenceController) {
                ((FaceSettingsEnrollButtonPreferenceController) abstractPreferenceController)
                        .setUserId(this.mUserId);
            } else if (abstractPreferenceController
                    instanceof FaceSettingsFooterPreferenceController) {
                ((FaceSettingsFooterPreferenceController) abstractPreferenceController)
                        .setUserId(this.mUserId);
            }
        }
        this.mRemoveController.setUserId(this.mUserId);
        if (this.mUserManager.isManagedProfile(this.mUserId)
                || this.mUserManager.getUserInfo(this.mUserId).isPrivateProfile()) {
            removePreference("security_settings_face_keyguard");
            removePreference(this.mLockscreenController.getPreferenceKey());
        }
        if (bundle != null) {
            this.mToken = bundle.getByteArray("hw_auth_token");
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        byte[] bArr = this.mToken;
        if (bArr != null || this.mConfirmingPassword) {
            this.mAttentionController.setToken(bArr);
            this.mEnrollController.setToken(this.mToken);
        } else {
            ChooseLockSettingsHelper.Builder builder =
                    new ChooseLockSettingsHelper.Builder(getActivity(), this);
            builder.mRequestCode = 4;
            builder.mTitle = getString(R.string.security_settings_face_preference_title);
            builder.mRequestGatekeeperPasswordHandle = true;
            builder.mUserId = this.mUserId;
            builder.mForegroundOnly = true;
            builder.mReturnCredentials = true;
            boolean show = builder.show();
            this.mConfirmingPassword = true;
            if (!show) {
                Log.e("FaceSettings", "Password not set");
                finish();
            }
        }
        FaceFeatureProvider faceFeatureProvider = this.mFaceFeatureProvider;
        getContext();
        faceFeatureProvider.getClass();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putByteArray("hw_auth_token", this.mToken);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        Button button;
        super.onStart();
        boolean hasEnrolledTemplates = this.mFaceManager.hasEnrolledTemplates(this.mUserId);
        this.mEnrollButton.setVisible(!hasEnrolledTemplates);
        this.mRemoveButton.setVisible(hasEnrolledTemplates);
        if (hasEnrolledTemplates
                && getIntent().getBooleanExtra("re_enroll_face_unlock", false)
                && (button =
                                (Button)
                                        ((LayoutPreference) this.mRemoveButton)
                                                .mRootView.findViewById(
                                                        R.id
                                                                .security_settings_face_settings_remove_button))
                        != null
                && button.isEnabled()) {
            this.mRemoveController.onClick(button);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        if (this.mEnrollController.isClicked()
                || getActivity().isChangingConfigurations()
                || this.mConfirmingPassword) {
            return;
        }
        if (this.mToken != null) {
            this.mFaceManager.revokeChallenge(this.mSensorId, this.mUserId, this.mChallenge);
            this.mToken = null;
        }
        setResult(3);
        finish();
    }
}
