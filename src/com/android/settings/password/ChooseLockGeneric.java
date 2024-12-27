package com.android.settings.password;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PasswordMetrics;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.security.AndroidKeyStoreMaintenance;
import android.security.GateKeeper;
import android.security.KeyStoreException;
import android.service.persistentdata.PersistentDataBlockManager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;
import androidx.reflect.view.SeslViewReflector;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.BiometricEnrollActivity;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.biometrics.combination.CombinedBiometricSearchIndexProvider;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.notification.RedactionInterstitial;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockPassword;
import com.android.settings.password.ChooseLockPattern;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.biometrics.BiometricsAboutUnlockPreferenceController;
import com.samsung.android.settings.biometrics.Wallet24Service;
import com.samsung.android.settings.biometrics.face.FaceLockSettings;
import com.samsung.android.settings.biometrics.face.FaceSettings;
import com.samsung.android.settings.biometrics.face.FaceSettingsHelper;
import com.samsung.android.settings.biometrics.face.FaceSettingsPreferenceController;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettings;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils;
import com.samsung.android.settings.knox.KnoxChooseLockTwoFactor;
import com.samsung.android.settings.knox.KnoxSamsungAnalyticsLogger;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.lockscreen.LockScreenSettings;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.lockscreen.SecLockTypePreference;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.password.LockTypePolicy;
import com.samsung.android.settings.password.SetupChooseLockGenericForNoneGoogle;
import com.samsung.android.settings.security.SecurityUtils;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChooseLockGeneric extends SettingsActivity {
    public boolean mForFingerprint = false;
    public boolean mForFace = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ChooseLockGenericFragment extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {
        static final int CHOOSE_LOCK_BEFORE_BIOMETRIC_REQUEST = 103;
        static final int CHOOSE_LOCK_REQUEST = 102;
        static final int CONFIRM_EXISTING_REQUEST = 100;
        public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
        static final int SKIP_FINGERPRINT_REQUEST = 104;
        public static Wallet24Service mWallet24Service;
        public ChooseLockSettingsHelper.Builder builder;
        public int mChooseLockRequestCode;
        public ChooseLockGenericController mController;
        public DevicePolicyManager mDpm;
        public int mExtraLockScreenTitleResId;
        public FaceManager mFaceManager;
        public FingerprintManager mFingerprintManager;
        public boolean mFromPwdNotification;
        public long mGkPwHandle;
        public String mHierarchicalParent;
        public boolean mIsEnforcedPwdChanged;
        public boolean mIsFromDualDarDo;
        public boolean mIsFromSecureFolderReset;
        public boolean mIsManagedProfile;
        public boolean mIsOnelockOngoing;
        public LockPatternUtils mLockPatternUtils;
        public LockTypePolicy mLockTypePolicy;
        public ManagedLockPasswordProvider mManagedPasswordProvider;
        public int mNightModeIsSet;
        public int mRequestedMinComplexity;
        public LockscreenCredential mUnificationProfileCredential;
        public int mUserId;
        public LockscreenCredential mUserPassword;
        public boolean mRequestGatekeeperPasswordHandle = false;
        public boolean mPasswordConfirmed = false;
        public boolean mWaitingForConfirmation = false;
        public boolean mIsSetNewPassword = false;
        public int mUnificationProfileId = -10000;
        public String mCallerAppName = null;
        public boolean mForFingerprint = false;
        public boolean mForFace = false;
        public boolean mForBiometrics = false;
        public boolean mOnlyEnforceDevicePasswordRequirement = false;
        public boolean mEnrollFingerPrintOnly = false;
        public boolean mSetPrevious = false;
        public boolean mIsSetNewCredentialFromPrevious = false;
        public boolean mFromSetupWizard = false;
        public boolean mForAppLockBackupKey = false;
        public String mAppLockBackupKey = null;
        public boolean mFromBioScreenLock = false;
        public boolean mFromBioSettings = false;
        public boolean mUseBioSession = false;
        public ArrayList mKnoxEventList = null;
        public int mChoosePref = 0;
        public boolean mIsDualDarDoEnforcePwdChangeOngoing = false;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment$2, reason: invalid class name */
        public final class AnonymousClass2 extends BaseSearchIndexProvider {
            @Override // com.android.settings.search.BaseSearchIndexProvider, com.android.settingslib.search.Indexable$SearchIndexProvider
            public final List getNonIndexableKeys(Context context) {
                List nonIndexableKeys = super.getNonIndexableKeys(context);
                int i = Settings.System.getInt(context.getContentResolver(), "wmanager_connected", 0);
                int i2 = Settings.System.getInt(context.getContentResolver(), "db_smartlock_supported", 0);
                StringBuilder sb = Utils.sBuilder;
                SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
                if (semDesktopModeManager != null ? semDesktopModeManager.isDeviceConnected() : false) {
                    ArrayList arrayList = (ArrayList) nonIndexableKeys;
                    arrayList.add("lock_settings_picker");
                    arrayList.add("unlock_set_fingerprint");
                }
                if (i2 == 1 || i == 0) {
                    ((ArrayList) nonIndexableKeys).add("unlock_set_smart");
                }
                if (!SecurityUtils.hasFingerprintFeature(context) || Utils.isGuestUser(context)) {
                    ((ArrayList) nonIndexableKeys).add("unlock_set_fingerprint");
                }
                ArrayList arrayList2 = (ArrayList) nonIndexableKeys;
                AccessibilitySettings$$ExternalSyntheticOutline0.m(arrayList2, "lock_settings_picker", "inform_text", "warning_text", "pref_locktype_description");
                arrayList2.add("pref_biometrics_description");
                if (SemPersonaManager.isKnoxId(UserHandle.myUserId())) {
                    arrayList2.add("switch_fingerprint");
                } else {
                    arrayList2.add("unlock_set_fingerprint");
                }
                if (!SecurityUtils.hasFingerprintFeature(context) || Utils.isGuestUser(context)) {
                    arrayList2.add("switch_fingerprint");
                }
                if (SecurityUtils.isFaceDisabled(context, UserHandle.myUserId()) || Utils.isGuestUser(context)) {
                    arrayList2.add("switch_face");
                    arrayList2.add("unlock_set_face");
                }
                if (SecurityUtils.isFingerprintDisabled(context, UserHandle.myUserId())) {
                    arrayList2.add("switch_fingerprint");
                }
                AccessibilitySettings$$ExternalSyntheticOutline0.m(arrayList2, "pref_lock_type", "pref_biometrics_description", "pref_locktype_description", "unlock_set_two_factor");
                arrayList2.add("switch_reset_with_sa");
                return nonIndexableKeys;
            }

            @Override // com.android.settings.search.BaseSearchIndexProvider, com.android.settingslib.search.Indexable$SearchIndexProvider
            public final List getRawDataToIndex(Context context) {
                ArrayList arrayList = new ArrayList();
                if (new FaceSettingsPreferenceController(context).isAvailable()) {
                    SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw).key = "key_face_menu";
                    searchIndexableRaw.title = context.getString(R.string.bio_face_recognition_title);
                    searchIndexableRaw.screenTitle = context.getString(R.string.sec_unlock_set_unlock_launch_picker_title);
                    arrayList.add(searchIndexableRaw);
                }
                if (new FingerprintSettingsPreferenceController(context).isAvailable()) {
                    SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw2).key = "key_fingerprint_menu";
                    searchIndexableRaw2.title = context.getString(R.string.sec_fingerprint);
                    searchIndexableRaw2.screenTitle = context.getString(R.string.sec_unlock_set_unlock_launch_picker_title);
                    arrayList.add(searchIndexableRaw2);
                }
                return arrayList;
            }

            @Override // com.android.settings.search.BaseSearchIndexProvider
            public final boolean isPageSearchEnabled(Context context) {
                if (Rune.isShopDemo(context) || Rune.isVzwDemoMode(context) || Rune.isLDUModel() || LockUtils.isLockMenuDisabledByEdm(context)) {
                    return false;
                }
                return !(this instanceof CombinedBiometricSearchIndexProvider);
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class FactoryResetProtectionWarningDialog extends InstrumentedDialogFragment {
            @Override // com.android.settingslib.core.instrumentation.Instrumentable
            public final int getMetricsCategory() {
                return FileType.PNK;
            }

            @Override // androidx.fragment.app.DialogFragment
            public final Dialog onCreateDialog(Bundle bundle) {
                final Bundle arguments = getArguments();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(arguments.getInt("titleRes"));
                builder.setMessage(arguments.getInt("messageRes"));
                builder.setPositiveButton(R.string.unlock_disable_frp_warning_ok, new DialogInterface.OnClickListener() { // from class: com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment$FactoryResetProtectionWarningDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetProtectionWarningDialog factoryResetProtectionWarningDialog = ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetProtectionWarningDialog.this;
                        Bundle bundle2 = arguments;
                        factoryResetProtectionWarningDialog.getClass();
                        String string = bundle2.getString("unlockMethodToSet");
                        ChooseLockGeneric.ChooseLockGenericFragment chooseLockGenericFragment = (ChooseLockGeneric.ChooseLockGenericFragment) factoryResetProtectionWarningDialog.getParentFragment();
                        Indexable$SearchIndexProvider indexable$SearchIndexProvider = ChooseLockGeneric.ChooseLockGenericFragment.SEARCH_INDEX_DATA_PROVIDER;
                        chooseLockGenericFragment.setUnlockMethod(string);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment$FactoryResetProtectionWarningDialog$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetProtectionWarningDialog.this.dismissInternal(false, false);
                    }
                });
                return builder.create();
            }

            @Override // androidx.fragment.app.DialogFragment
            public final void show(FragmentManager fragmentManager, String str) {
                if (fragmentManager.findFragmentByTag(str) == null) {
                    super.show(fragmentManager, str);
                }
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class FactoryResetprotectionWarningDialogActivity extends AppCompatActivity implements View.OnClickListener {
            public static int sUserId = -1;
            public Button mCancelButton;
            public TextView mDescriptionTextView;
            public Button mRemoveButton;
            public ViewGroup mRootView;
            public TextView mTitle;
            public Toolbar mToolBar;
            public String mUnlockMethodToSet;
            public FaceManager mFaceManager = null;
            public FingerprintManager mFingerprintManager = null;
            public boolean mHasFingerprints = false;
            public boolean mHasFace = false;
            public final AnonymousClass3 mFaceRemovalCallback = new FaceManager.RemovalCallback() { // from class: com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.3
                public final void onRemovalError(Face face, int i, CharSequence charSequence) {
                    Log.e("ChooseLockGenericFragment", "Remove Error : " + i + ", " + ((Object) charSequence));
                }

                public final void onRemovalSucceeded(Face face, int i) {
                    Log.d("ChooseLockGenericFragment", "onRemovalSucceeded");
                    Settings.Secure.putInt(FactoryResetprotectionWarningDialogActivity.this.getContentResolver(), "face_screen_lock", 0);
                }
            };

            static {
                new PathInterpolator(0.22f, 0.025f, 0.0f, 1.0f);
            }

            public final void deleteAllFingerPrintForUser(final int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "deleteAllFingerPrintForUser : ", "ChooseLockGenericFragment");
                FingerprintManager fingerprintManager = this.mFingerprintManager;
                List enrolledFingerprints = fingerprintManager != null ? fingerprintManager.getEnrolledFingerprints(i) : null;
                if (enrolledFingerprints != null && !enrolledFingerprints.isEmpty()) {
                    for (int i2 = 0; i2 < enrolledFingerprints.size(); i2++) {
                        this.mFingerprintManager.remove((Fingerprint) enrolledFingerprints.get(i2), i, new FingerprintManager.RemovalCallback() { // from class: com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.2
                            public final void onRemovalError(Fingerprint fingerprint, int i3, CharSequence charSequence) {
                                Log.e("ChooseLockGenericFragment", "Remove Error : " + i3 + ", " + ((Object) charSequence) + ", activity=" + this);
                                Activity activity = this;
                                if (activity != null) {
                                    FingerprintSettingsUtils.showSensorErrorDialog(activity, activity.getString(R.string.sec_fingerprint_error_message_sensor_error), true);
                                }
                            }

                            public final void onRemovalSucceeded(Fingerprint fingerprint, int i3) {
                                FactoryResetprotectionWarningDialogActivity factoryResetprotectionWarningDialogActivity = FactoryResetprotectionWarningDialogActivity.this;
                                int i4 = i;
                                int i5 = FactoryResetprotectionWarningDialogActivity.sUserId;
                                factoryResetprotectionWarningDialogActivity.deleteAllFingerPrintForUser(i4);
                            }
                        });
                    }
                    return;
                }
                Log.d("ChooseLockGenericFragment", "deleteAllFingerPrintForUser items : " + enrolledFingerprints);
                if (enrolledFingerprints != null) {
                    Log.d("ChooseLockGenericFragment", "deleteAllFingerPrintForUser items.isEmpty() : " + enrolledFingerprints.isEmpty());
                }
                setResult(10009, new Intent().putExtra("unlockMethodToSet", this.mUnlockMethodToSet));
                finish();
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (view.getId() != R.id.removeButton) {
                    finish();
                    return;
                }
                this.mToolBar.setVisibility(8);
                boolean z = this.mHasFingerprints;
                this.mDescriptionTextView.setText((!z || this.mHasFace) ? (!this.mHasFace || z) ? getString(R.string.sec_screen_locktype_change_nonsecure_biometric_dialog_biometric) : getString(R.string.sec_screen_locktype_change_nonsecure_biometric_dialog_face) : getString(R.string.sec_screen_locktype_change_nonsecure_biometric_dialog_fingerprint));
                SeslViewReflector.requestAccessibilityFocus(this.mDescriptionTextView);
                this.mRemoveButton.setText(getString(R.string.common_remove));
                this.mRemoveButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment$FactoryResetprotectionWarningDialogActivity$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        FaceManager faceManager;
                        List enrolledFaces;
                        final ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity factoryResetprotectionWarningDialogActivity = ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.this;
                        final Activity activity = this;
                        if (factoryResetprotectionWarningDialogActivity.mHasFingerprints && factoryResetprotectionWarningDialogActivity.mFingerprintManager != null) {
                            if (SemPersonaManager.isKnoxId(ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.sUserId)) {
                                factoryResetprotectionWarningDialogActivity.deleteAllFingerPrintForUser(ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.sUserId);
                            } else {
                                factoryResetprotectionWarningDialogActivity.mFingerprintManager.removeAll(ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.sUserId, new FingerprintManager.RemovalCallback() { // from class: com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.1
                                    public final void onRemovalError(Fingerprint fingerprint, int i, CharSequence charSequence) {
                                        Log.w("ChooseLockGenericFragment", "deleteAllFingerprints Error:" + i + ", " + ((Object) charSequence));
                                        FingerprintSettingsUtils.showSensorErrorDialog(activity, FactoryResetprotectionWarningDialogActivity.this.getString(R.string.sec_fingerprint_error_message_sensor_error), true);
                                    }

                                    public final void onRemovalSucceeded(Fingerprint fingerprint, int i) {
                                        Log.d("ChooseLockGenericFragment", "deleteAllFingerprints Success");
                                    }
                                });
                            }
                        }
                        if (factoryResetprotectionWarningDialogActivity.mHasFace && (faceManager = factoryResetprotectionWarningDialogActivity.mFaceManager) != null && (enrolledFaces = faceManager.getEnrolledFaces(ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.sUserId)) != null) {
                            Log.d("ChooseLockGenericFragment", "swipe/none remove registered face");
                            if (enrolledFaces.size() > 0) {
                                Log.d("ChooseLockGenericFragment", "Remove all face data");
                                factoryResetprotectionWarningDialogActivity.mFaceManager.removeAll(ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.sUserId, factoryResetprotectionWarningDialogActivity.mFaceRemovalCallback);
                            } else {
                                Log.e("ChooseLockGenericFragment", "removeFace : no enrolled face");
                            }
                        }
                        int i = ChooseLockGeneric.ChooseLockGenericFragment.FactoryResetprotectionWarningDialogActivity.sUserId;
                        Log.d("ChooseLockGenericFragment", "removeManagedProfileFingerprints : " + i);
                        UserManager userManager = UserManager.get(factoryResetprotectionWarningDialogActivity);
                        if (!userManager.getUserInfo(i).isManagedProfile()) {
                            List profiles = userManager.getProfiles(i);
                            int size = profiles.size();
                            int i2 = 0;
                            while (true) {
                                if (i2 >= size) {
                                    break;
                                }
                                UserInfo userInfo = (UserInfo) profiles.get(i2);
                                LockPatternUtils lockPatternUtils = new LockPatternUtils(factoryResetprotectionWarningDialogActivity);
                                if (userInfo.isManagedProfile() && !lockPatternUtils.isSeparateProfileChallengeEnabled(userInfo.id)) {
                                    factoryResetprotectionWarningDialogActivity.deleteAllFingerPrintForUser(userInfo.id);
                                    break;
                                }
                                i2++;
                            }
                        }
                        factoryResetprotectionWarningDialogActivity.setResult(10009, new Intent().putExtra("unlockMethodToSet", factoryResetprotectionWarningDialogActivity.mUnlockMethodToSet));
                        factoryResetprotectionWarningDialogActivity.finish();
                    }
                });
            }

            @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
            public final void onCreate(Bundle bundle) {
                String string;
                ArrayList arrayList;
                ArrayList arrayList2;
                Intent intent = getIntent();
                if (intent == null) {
                    Log.e("ChooseLockGenericFragment", "Intent is null");
                    return;
                }
                super.onCreate(bundle);
                intent.getIntExtra("titleRes", 0);
                intent.getIntExtra("messageRes", 0);
                this.mUnlockMethodToSet = intent.getStringExtra("unlockMethodToSet");
                sUserId = intent.getIntExtra("userId", 0);
                this.mFingerprintManager = Utils.getFingerprintManagerOrNull(this);
                this.mFaceManager = Utils.getFaceManagerOrNull(this);
                FingerprintManager fingerprintManager = this.mFingerprintManager;
                boolean z = true;
                this.mHasFingerprints = (fingerprintManager != null && fingerprintManager.hasEnrolledFingerprints(sUserId)) || ChooseLockGenericFragment.hasManagedProfileFingerprints(this, sUserId);
                FaceManager faceManager = this.mFaceManager;
                if ((faceManager == null || !faceManager.hasEnrolledTemplates(sUserId)) && !ChooseLockGenericFragment.hasManagedProfileFace(this, sUserId)) {
                    z = false;
                }
                this.mHasFace = z;
                this.mRootView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.sec_lock_remove_all_biometics_dialog, (ViewGroup) null);
                setFinishOnTouchOutside(false);
                getWindow().setGravity(80);
                setContentView(this.mRootView);
                Toolbar toolbar = (Toolbar) this.mRootView.findViewById(R.id.action_bar);
                this.mToolBar = toolbar;
                this.mTitle = (TextView) toolbar.findViewById(R.id.title);
                setActionBar(this.mToolBar);
                boolean z2 = this.mHasFingerprints;
                if (z2 && !this.mHasFace) {
                    string = getString(R.string.sec_screen_locktype_change_nonsecure_biometric_remove_fingerprint);
                } else if (!this.mHasFace || z2) {
                    Wallet24Service wallet24Service = ChooseLockGenericFragment.mWallet24Service;
                    string = (wallet24Service == null || (arrayList = wallet24Service.mAppList) == null || arrayList.size() <= 0) ? getString(R.string.sec_screen_locktype_change_nonsecure_biometric_remove_biometric) : getString(R.string.sec_screen_locktype_change_nonsecure_remove_biometric_mobile_id);
                } else {
                    Wallet24Service wallet24Service2 = ChooseLockGenericFragment.mWallet24Service;
                    string = (wallet24Service2 == null || (arrayList2 = wallet24Service2.mAppList) == null || arrayList2.size() <= 0) ? getString(R.string.sec_screen_locktype_change_nonsecure_biometric_remove_face) : getString(R.string.sec_screen_locktype_change_nonsecure_remove_face_mobile_id);
                }
                if (SemPersonaManager.isKnoxId(sUserId)) {
                    String knoxName = KnoxUtils.getKnoxName(this, sUserId);
                    string = this.mHasFingerprints ? getString(R.string.sec_workspace_locktype_change_nonsecure_biometric_remove_workspace_finger, new Object[]{knoxName}) : getString(R.string.sec_workspace_locktype_change_nonsecure_biometric_remove_workspace_bio, new Object[]{knoxName});
                    LoggingHelper.insertEventLogging(27, 4428, "a");
                } else {
                    LoggingHelper.insertEventLogging(27, 4428, "b");
                }
                this.mDescriptionTextView = (TextView) findViewById(R.id.dialog_description);
                this.mCancelButton = (Button) findViewById(R.id.cancelButton);
                this.mRemoveButton = (Button) findViewById(R.id.removeButton);
                this.mTitle.setText(SemPersonaManager.isKnoxId(sUserId) ? R.string.sec_screen_locktype_change_nonsecure_title_workspace : R.string.sec_screen_locktype_change_nonsecure_title);
                this.mDescriptionTextView.setText(string);
                this.mCancelButton.setOnClickListener(this);
                this.mRemoveButton.setOnClickListener(this);
            }

            @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
            public final void onPause() {
                super.onPause();
                if (!isFinishing()) {
                    Intent intent = new Intent();
                    intent.putExtra("screen_lock_force_destroy", true);
                    setResult(0, intent);
                }
                finish();
            }
        }

        public static void addPreferenceswithOrder(PreferenceScreen preferenceScreen, String str, String str2, int i) {
            Preference findPreference = preferenceScreen.findPreference(str);
            if (findPreference != null) {
                findPreference.setOrder(i);
                if (str2 != null) {
                    findPreference.setTitle(str2);
                }
                preferenceScreen.addPreference(findPreference);
            }
        }

        public static boolean hasManagedProfileFace(Context context, int i) {
            Log.d("ChooseLockGenericFragment", "hasManagedProfileFace : " + i);
            FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(context);
            UserManager userManager = UserManager.get(context);
            if (userManager != null && !userManager.getUserInfo(i).isManagedProfile()) {
                List profiles = userManager.getProfiles(i);
                int size = profiles.size();
                for (int i2 = 0; i2 < size; i2++) {
                    UserInfo userInfo = (UserInfo) profiles.get(i2);
                    LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
                    if (userInfo.isManagedProfile() && !lockPatternUtils.isSeparateProfileChallengeEnabled(userInfo.id) && faceManagerOrNull != null && faceManagerOrNull.hasEnrolledTemplates(userInfo.id)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public static boolean hasManagedProfileFingerprints(Context context, int i) {
            Log.d("ChooseLockGenericFragment", "hasManagedProfileFingerprints : " + i);
            FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(context);
            UserManager userManager = UserManager.get(context);
            if (!userManager.getUserInfo(i).isManagedProfile()) {
                List profiles = userManager.getProfiles(i);
                int size = profiles.size();
                for (int i2 = 0; i2 < size; i2++) {
                    UserInfo userInfo = (UserInfo) profiles.get(i2);
                    LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
                    if (userInfo.isManagedProfile() && !lockPatternUtils.isSeparateProfileChallengeEnabled(userInfo.id) && fingerprintManagerOrNull != null && fingerprintManagerOrNull.hasEnrolledFingerprints(userInfo.id)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public static void removePreferenceByKey(PreferenceScreen preferenceScreen, String str) {
            if (preferenceScreen == null || preferenceScreen.findPreference(str) == null) {
                return;
            }
            preferenceScreen.removePreference(preferenceScreen.findPreference(str));
        }

        public static void resetUcmKeyguard(int i) {
            if (UCMUtils.getKeyguardStorageForUser(i) != null) {
                IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
                if (asInterface == null) {
                    Log.d("com.samsung.android.settings.knox.UCMUtils", "mUcmBinder == null");
                    return;
                }
                String uCMKeyguardStorageForUser = UCMUtils.getUCMKeyguardStorageForUser(i);
                if (uCMKeyguardStorageForUser == null || uCMKeyguardStorageForUser.isEmpty() || uCMKeyguardStorageForUser.equalsIgnoreCase("None")) {
                    Log.d("com.samsung.android.settings.knox.UCMUtils", "UCM keyguard is not enabled");
                    return;
                }
                try {
                    Log.d("com.samsung.android.settings.knox.UCMUtils", "resetUcmKeyguardSettings for the user : " + i);
                    if (UCMUtils.isEnforcedCredentialStorageExistAsUser(i)) {
                        Log.d("com.samsung.android.settings.knox.UCMUtils", "resetUcmKeyguardSettings is skipped, Keyguard is enforced.");
                    } else if (asInterface.configureKeyguardSettings(i, null)) {
                        UCMUtils.sendKeyguardConfiguredEvent(i, null);
                        Log.d("com.samsung.android.settings.knox.UCMUtils", "success to ucm keyguard configure disable");
                    } else {
                        Log.d("com.samsung.android.settings.knox.UCMUtils", "Failed to ucm keyguard configure disable");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void addPreferences$2() {
            addPreferencesFromResource(R.xml.sec_security_settings_picker);
            if (this.mSetPrevious || ((this.mForFingerprint || this.mForFace) && !this.mIsSetNewPassword)) {
                removePreference(BiometricsAboutUnlockPreferenceController.KEY_SCREEN_TRANSITION_EFFECT);
                removePreference("key_face_menu");
                removePreference("key_fingerprint_menu");
            }
            removePreference("inform_text");
            removePreference("warning_text");
            removePreference("switch_face");
            if (!this.mLockTypePolicy.mIsSecureFolderId) {
                removePreference("switch_fingerprint");
            }
            if (SecurityUtils.hasFingerprintFeature(getActivity())) {
                return;
            }
            removePreference("key_fingerprint_menu");
        }

        public boolean alwaysHideInsecureScreenLockTypes() {
            return this instanceof SetupChooseLockGenericForNoneGoogle.SetupChooseLockGenericForNoneGoogleFragment;
        }

        public final SpannableStringBuilder appendCurrentLockTypeToSummary(CharSequence charSequence) {
            String str = getString(R.string.comma) + " ";
            int color = getResources().getColor(R.color.sec_lockscreen_current_lock_type_text_color);
            SpannableString spannableString = new SpannableString(getString(R.string.sec_current_screen_lock));
            spannableString.setSpan(new ForegroundColorSpan(color), 0, spannableString.length(), 33);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            if (charSequence != null) {
                spannableStringBuilder.append((CharSequence) charSequence.toString());
                spannableStringBuilder.append((CharSequence) str);
            }
            spannableStringBuilder.append((CharSequence) spannableString);
            return spannableStringBuilder;
        }

        public boolean canRunBeforeDeviceProvisioned() {
            PersistentDataBlockManager persistentDataBlockManager;
            return !LockUtils.readGoogleFRPFlag(getActivity()) || (persistentDataBlockManager = (PersistentDataBlockManager) getSystemService("persistent_data_block")) == null || persistentDataBlockManager.getDataBlockSize() == 0;
        }

        @Override // com.android.settings.SettingsPreferenceFragment
        public final void finish() {
            if (interceptFinishIfNeeded()) {
                return;
            }
            super.finish();
        }

        public final void finishIfRequired(FragmentActivity fragmentActivity) {
            if (Rune.isSamsungDexMode(fragmentActivity) || !LockUtils.isInMultiWindow(fragmentActivity)) {
                return;
            }
            Toast.makeText(fragmentActivity, fragmentActivity.getResources().getString(R.string.lock_screen_doesnt_support_multi_window_text), 1).show();
            finish();
        }

        public final void finishWithResult(int i, Intent intent) {
            getActivity().setResult(i, intent);
            finish();
        }

        public Intent getBiometricEnrollIntent(FragmentActivity fragmentActivity) {
            Intent intent = new Intent(fragmentActivity, (Class<?>) BiometricEnrollActivity.InternalActivity.class);
            intent.putExtra("skip_intro", true);
            if (this.mEnrollFingerPrintOnly) {
                intent.putExtra("fingerprint_enrollment_only", true);
            }
            return intent;
        }

        public String getBiometricsPreferenceTitle(ScreenLockType screenLockType) {
            boolean z;
            boolean hasFingerprintHardware = Utils.hasFingerprintHardware(getContext());
            boolean hasFaceHardware = Utils.hasFaceHardware(getContext());
            boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
            if (hasFaceHardware) {
                if (isAnySetupWizard) {
                    getContext();
                    FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                    if (featureFactoryImpl == null) {
                        throw new UnsupportedOperationException("No feature factory configured");
                    }
                    featureFactoryImpl.getFaceFeatureProvider().getClass();
                }
                z = true;
            } else {
                z = false;
            }
            if (this.mController != null) {
                return BiometricUtils.getCombinedScreenLockOptions(getContext(), this.mController.getTitle(screenLockType), hasFingerprintHardware, z);
            }
            Log.e("ChooseLockGenericFragment", "ChooseLockGenericController is null!");
            return getResources().getString(R.string.error_title);
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment
        public final String getHierarchicalParentFragment(Context context) {
            return TextUtils.isEmpty(this.mHierarchicalParent) ? LockScreenSettings.class.getName() : this.mHierarchicalParent;
        }

        public Class getInternalActivityClass() {
            return InternalActivity.class;
        }

        public Intent getLockPasswordIntent(int i) {
            FaceManager faceManager;
            Intent intent = new Intent(getContext(), (Class<?>) (this.mSetPrevious ? ChooseLockPassword.RecoveryActivity.class : ChooseLockPassword.class));
            intent.putExtra("confirm_credentials", false);
            intent.putExtra("lockscreen.password_type", i);
            int aggregatedPasswordComplexity = this.mController.getAggregatedPasswordComplexity();
            ChooseLockGenericController chooseLockGenericController = this.mController;
            PasswordMetrics requestedPasswordMetrics = chooseLockGenericController.mLockPatternUtils.getRequestedPasswordMetrics(chooseLockGenericController.mUserId, chooseLockGenericController.mDevicePasswordRequirementOnly);
            int i2 = chooseLockGenericController.mUnificationProfileId;
            if (i2 != -10000) {
                requestedPasswordMetrics.maxWith(chooseLockGenericController.mLockPatternUtils.getRequestedPasswordMetrics(i2));
            }
            intent.putExtra("min_complexity", aggregatedPasswordComplexity);
            intent.putExtra("min_metrics", (Parcelable) requestedPasswordMetrics);
            intent.putExtra("for_fingerprint", this.mForFingerprint);
            intent.putExtra("for_face", this.mForFace);
            intent.putExtra("for_biometrics", this.mForBiometrics);
            intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
            FingerprintManager fingerprintManager = this.mFingerprintManager;
            if ((fingerprintManager != null && fingerprintManager.isHardwareDetected()) || ((faceManager = this.mFaceManager) != null && faceManager.isHardwareDetected())) {
                intent.putExtra("request_gk_pw_handle", this.mRequestGatekeeperPasswordHandle);
            }
            if (this.mUserPassword != null) {
                if (this.mLockTypePolicy.isEnterpriseUser()) {
                    Preference$$ExternalSyntheticOutline0.m(new StringBuilder("getIntentForUnlock - Secured password required for user "), this.mUserId, "ChooseLockGenericFragment.SDP");
                    intent.putExtra(HostAuth.PASSWORD, (Parcelable) KnoxUtils.getCipher(this.mUserPassword, KnoxUtils.getCipherPublicHandle()));
                    intent.putExtra("is_knox_password_secured", true);
                } else {
                    intent.putExtra(HostAuth.PASSWORD, (Parcelable) this.mUserPassword);
                }
            }
            int i3 = this.mUnificationProfileId;
            if (i3 != -10000) {
                LockscreenCredential lockscreenCredential = this.mUnificationProfileCredential;
                intent.putExtra("unification_profile_id", i3);
                intent.putExtra("unification_profile_credential", (Parcelable) lockscreenCredential);
            }
            if (this.mFromSetupWizard) {
                intent.setClass(getActivity(), SetupChooseLockPassword.class);
                intent.putExtra("extra_prefs_show_button_bar", false);
                intent.putExtra("fromSetupWizard", true);
            }
            if (this.mSetPrevious) {
                intent.putExtra("previous_credential", true);
            }
            int max = Math.max(this.mDpm.getPasswordMinimumLength(null), 4);
            int passwordMaximumLength = this.mDpm.getPasswordMaximumLength(i);
            intent.putExtra("lockscreen.password_min", max);
            intent.putExtra("lockscreen.password_max", passwordMaximumLength);
            intent.putExtra("knox_userId", this.mLockTypePolicy.mIsKnoxId ? this.mUserId : getActivity().getIntent().getIntExtra("knox_userId", 0));
            if (!this.mLockTypePolicy.isWorkDeviceOrProfile()) {
                intent.putExtra("forAppLockBackupKey", this.mAppLockBackupKey);
                intent.putExtra("fromScreenLock", this.mUseBioSession);
                intent.putExtra("screen_lock_bio", this.mFromBioScreenLock);
                intent.putExtra("settings_bio", this.mFromBioSettings);
            }
            return intent;
        }

        public Intent getLockPatternIntent() {
            FaceManager faceManager;
            Intent intent = new Intent(getContext(), (Class<?>) (this.mSetPrevious ? ChooseLockPattern.RecoveryActivity.class : ChooseLockPattern.class));
            intent.putExtra("confirm_credentials", false);
            intent.putExtra("for_fingerprint", this.mForFingerprint);
            intent.putExtra("for_face", this.mForFace);
            intent.putExtra("for_biometrics", this.mForBiometrics);
            intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
            FingerprintManager fingerprintManager = this.mFingerprintManager;
            if ((fingerprintManager != null && fingerprintManager.isHardwareDetected()) || ((faceManager = this.mFaceManager) != null && faceManager.isHardwareDetected())) {
                intent.putExtra("request_gk_pw_handle", this.mRequestGatekeeperPasswordHandle);
            }
            if (this.mUserPassword != null) {
                if (this.mLockTypePolicy.isEnterpriseUser()) {
                    Preference$$ExternalSyntheticOutline0.m(new StringBuilder("getIntentForUnlock - Secured password required for user "), this.mUserId, "ChooseLockGenericFragment.SDP");
                    intent.putExtra(HostAuth.PASSWORD, (Parcelable) KnoxUtils.getCipher(this.mUserPassword, KnoxUtils.getCipherPublicHandle()));
                    intent.putExtra("is_knox_password_secured", true);
                } else {
                    intent.putExtra(HostAuth.PASSWORD, (Parcelable) this.mUserPassword);
                }
            }
            int i = this.mUnificationProfileId;
            if (i != -10000) {
                LockscreenCredential lockscreenCredential = this.mUnificationProfileCredential;
                intent.putExtra("unification_profile_id", i);
                intent.putExtra("unification_profile_credential", (Parcelable) lockscreenCredential);
            }
            if (this.mFromSetupWizard) {
                intent.setClass(getActivity(), SetupChooseLockPattern.class);
                intent.putExtra("extra_prefs_show_button_bar", false);
                intent.putExtra("fromSetupWizard", true);
            }
            if (this.mSetPrevious) {
                intent.putExtra("previous_credential", true);
            }
            intent.putExtra("knox_userId", this.mLockTypePolicy.mIsKnoxId ? this.mUserId : getActivity().getIntent().getIntExtra("knox_userId", 0));
            if (!this.mLockTypePolicy.isWorkDeviceOrProfile()) {
                intent.putExtra("forAppLockBackupKey", this.mAppLockBackupKey);
                intent.putExtra("fromScreenLock", this.mUseBioSession);
                intent.putExtra("screen_lock_bio", this.mFromBioScreenLock);
                intent.putExtra("settings_bio", this.mFromBioSettings);
            }
            return intent;
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 27;
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment
        public final String getTopLevelPreferenceKey(Context context) {
            return "top_level_lockscreen";
        }

        public final boolean hasEnrolledFaces() {
            FaceManager faceManager = this.mFaceManager;
            return faceManager != null && faceManager.hasEnrolledTemplates(this.mUserId);
        }

        public final boolean hasEnrolledFingerprints(int i) {
            FingerprintManager fingerprintManager = this.mFingerprintManager;
            return fingerprintManager != null && fingerprintManager.hasEnrolledFingerprints(i);
        }

        public final boolean interceptFinishIfNeeded() {
            if (this.mIsFromDualDarDo) {
                return KnoxUtils.isChangeRequestedForInner(getActivity()) >= 1;
            }
            LockTypePolicy lockTypePolicy = this.mLockTypePolicy;
            if (lockTypePolicy != null && ((lockTypePolicy.isEnterpriseUser() && !this.mLockTypePolicy.mIsDualDarDoUser) || this.mLockTypePolicy.isDevicePasswordAdminEnabled())) {
                int isChangeRequested = KnoxUtils.isChangeRequested(getActivity(), this.mUserId);
                PasswordPolicy passwordPolicy = KnoxUtils.getPasswordPolicy(getActivity(), this.mUserId);
                int passwordChangeTimeout = passwordPolicy != null ? passwordPolicy.getPasswordChangeTimeout() : 0;
                boolean z = (passwordChangeTimeout <= 0 && isChangeRequested == 1) || isChangeRequested >= 2;
                if (isChangeRequested >= 1 && z && this.mLockTypePolicy.isDevicePasswordAdminEnabled() && !this.mIsDualDarDoEnforcePwdChangeOngoing) {
                    return true;
                }
                if (isChangeRequested == 1 && !z) {
                    int i = this.mUserId;
                    ((AlarmManager) getSystemService("alarm")).set(2, (passwordChangeTimeout * 60000) + SystemClock.elapsedRealtime(), PendingIntent.getBroadcastAsUser(getActivity().getApplicationContext(), 0, new Intent(PasswordPolicy.ACTION_PWD_CHANGE_TIMEOUT_INTERNAL), 335544320, new UserHandle(i)));
                    PasswordPolicy passwordPolicy2 = KnoxUtils.getPasswordPolicy(getActivity(), i);
                    if (passwordPolicy2 != null) {
                        passwordPolicy2.setPwdChangeRequested(-1);
                    }
                    setResult(100);
                }
            }
            return false;
        }

        @Override // androidx.fragment.app.Fragment
        public void onActivityResult(int i, int i2, Intent intent) {
            int i3 = i2;
            super.onActivityResult(i, i2, intent);
            MainClearConfirm$$ExternalSyntheticOutline0.m("onActivityResult : requestCode : ", " resultCode : ", i, i3, "ChooseLockGenericFragment");
            boolean z = false;
            this.mWaitingForConfirmation = false;
            if (i == 100 && i3 == -1) {
                this.mGkPwHandle = BiometricUtils.getGatekeeperPasswordHandle(intent);
                this.mPasswordConfirmed = true;
                this.mUserPassword = intent.getParcelableExtra(HostAuth.PASSWORD);
                if (this.mLockTypePolicy.isEnterpriseUser() || (UCMUtils.isEnforcedCredentialStorageExistAsUser(this.mUserId) && UCMUtils.isSupportChangePin(this.mUserId))) {
                    boolean booleanExtra = intent.getBooleanExtra("is_knox_password_secured", false);
                    Log.d("ChooseLockGenericFragment.SDP", "onActivityResult - Password secured for user " + this.mUserId + " : " + booleanExtra);
                    if (booleanExtra) {
                        this.mUserPassword = KnoxUtils.restoreCipherPassword(this.mUserPassword, KnoxUtils.getCipherPublicHandle());
                    }
                }
                updatePreferencesOrFinish(false);
                if (this.mLockTypePolicy.mIsKnoxId) {
                    setExtraTitle();
                    return;
                }
                return;
            }
            if (i == 105) {
                if (i3 != -1) {
                    finish();
                    return;
                }
                if ((!this.mIsManagedProfile || this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId) || this.mIsSetNewPassword) && this.builder.show()) {
                    this.mWaitingForConfirmation = true;
                    return;
                } else {
                    this.mPasswordConfirmed = true;
                    updatePreferencesOrFinish(false);
                    return;
                }
            }
            if (i == 102) {
                if (i3 != 1) {
                    if (this.mSetPrevious) {
                        this.mIsSetNewCredentialFromPrevious = false;
                    }
                    this.mChoosePref = 0;
                    if (intent != null && intent.getBooleanExtra("screen_lock_force_destroy", false)) {
                        finishWithResult(i3, intent);
                    }
                    if (getIntent().getIntExtra("lockscreen.password_type", -1) != -1) {
                        getActivity().setResult(0, intent);
                        finish();
                        return;
                    }
                    return;
                }
                if (this.mSetPrevious) {
                    Log.d("ChooseLockGenericFragment", "result finished");
                    this.mIsSetNewCredentialFromPrevious = false;
                    finish();
                }
                if (this.mLockTypePolicy.isWorkDeviceOrProfile()) {
                    LockUtils.updateSetUpCredentialIfNeeded(getActivity(), this.mUserId);
                } else if (this.mUserId == 0) {
                    Settings.System.putIntForUser(getContentResolver(), "enable_one_lock_ongoing", 0, 0);
                    if (this.mIsOnelockOngoing) {
                        ConfirmDeviceCredentialUtils.checkForPendingIntent(getActivity());
                    }
                    if (this.mLockTypePolicy.isDevicePasswordAdminEnabled()) {
                        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(getActivity().getApplicationContext());
                        PasswordPolicy passwordPolicy = enterpriseDeviceManager != null ? enterpriseDeviceManager.getPasswordPolicy() : null;
                        if (passwordPolicy != null && passwordPolicy.isChangeRequested() == -1) {
                            ((AlarmManager) getSystemService("alarm")).cancel(PendingIntent.getBroadcastAsUser(getActivity().getApplicationContext(), 0, new Intent(PasswordPolicy.ACTION_PWD_CHANGE_TIMEOUT_INTERNAL), 268435456, new UserHandle(this.mUserId)));
                        }
                        if (passwordPolicy != null && passwordPolicy.isChangeRequested() > 0) {
                            passwordPolicy.setPwdChangeRequested(0);
                        }
                    }
                }
                resetUcmKeyguard(this.mUserId);
                if (intent != null && intent.getBooleanExtra("need_to_launch_ls_notification", false)) {
                    z = true;
                }
                if (z) {
                    try {
                        Intent createStartIntent = RedactionInterstitial.createStartIntent(getActivity(), this.mUserId);
                        if (createStartIntent != null) {
                            startActivity(createStartIntent);
                        }
                    } catch (Exception e) {
                        Log.d("ChooseLockGenericFragment", "Exception occurs when excuting RedactionIntent : " + e.getMessage());
                    }
                }
                if (!z && !this.mForFace && !this.mForFingerprint && !this.mIsFromDualDarDo && this.mUserId == 0 && !this.mFromSetupWizard && !this.mSetPrevious) {
                    try {
                        Intent fmmDialogIntent = LockUtils.getFmmDialogIntent(getContext());
                        if (fmmDialogIntent != null) {
                            startActivity(fmmDialogIntent);
                        }
                    } catch (Exception e2) {
                        Log.d("ChooseLockGenericFragment", "Exception occurs when excuting fmmIntent : " + e2.getMessage());
                    }
                }
                finishWithResult(i3, intent);
                return;
            }
            if (i == 10008) {
                if (i3 == -1) {
                    finishWithResult(i3, intent);
                    return;
                }
                this.mChoosePref = 0;
                if (this.mUserPassword == null && this.mLockPatternUtils.isSecure(this.mUserId)) {
                    finishWithResult(-1, intent);
                    return;
                }
                SwitchPreference switchPreference = (SwitchPreference) findPreference("switch_face");
                if (switchPreference != null) {
                    switchPreference.setEnabled(!SecurityUtils.isFaceDisabled(getContext(), UserHandle.myUserId()));
                }
                SwitchPreference switchPreference2 = (SwitchPreference) findPreference("switch_fingerprint");
                if (switchPreference2 != null) {
                    switchPreference2.setEnabled(!SecurityUtils.isFingerprintDisabled(getContext(), UserHandle.myUserId()));
                    return;
                }
                return;
            }
            if (i == 10010) {
                this.mChoosePref = 0;
                if (GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
                    this.mPasswordConfirmed = true;
                    this.mGkPwHandle = GatekeeperPasswordProvider.getGatekeeperPasswordHandle(intent);
                }
                Log.d("ChooseLockGenericFragment", "GkPwHandle : " + this.mGkPwHandle);
                if ((intent == null || !intent.getBooleanExtra("screen_lock_force_destroy", false)) && !(this.mLockPatternUtils.isSecure(this.mUserId) && this.mGkPwHandle == 0)) {
                    updatePreferencesOrFinish(false);
                    return;
                } else {
                    finishWithResult(i3, intent);
                    return;
                }
            }
            if (i == 10011) {
                this.mChoosePref = 0;
                if (intent == null || !intent.getBooleanExtra("screen_lock_force_destroy", false)) {
                    return;
                }
                finishWithResult(i3, intent);
                return;
            }
            if (!this.mLockTypePolicy.isWorkDeviceOrProfile() && !this.mLockTypePolicy.isDevicePasswordAdminEnabled()) {
                if (i == 103 && i3 == 1) {
                    Intent biometricEnrollIntent = getBiometricEnrollIntent(getActivity());
                    if (intent != null) {
                        biometricEnrollIntent.putExtras(intent.getExtras());
                    }
                    biometricEnrollIntent.putExtra("android.intent.extra.USER_ID", this.mUserId);
                    startActivity(biometricEnrollIntent);
                    finish();
                    return;
                }
                if (i == 104) {
                    if (i3 != 0) {
                        FragmentActivity activity = getActivity();
                        if (i3 == 1) {
                            i3 = -1;
                        }
                        activity.setResult(i3, intent);
                        finish();
                        return;
                    }
                    return;
                }
                if (i == 501) {
                    return;
                }
                if (i != 10009) {
                    getActivity().setResult(0);
                    finish();
                    return;
                }
                if (i3 != 0) {
                    String stringExtra = intent != null ? intent.getStringExtra("unlockMethodToSet") : null;
                    if (stringExtra != null) {
                        setUnlockMethod(stringExtra);
                        return;
                    }
                    return;
                }
                this.mChoosePref = 0;
                if (intent == null || !intent.getBooleanExtra("screen_lock_force_destroy", false)) {
                    return;
                }
                finishWithResult(i3, intent);
                return;
            }
            MainClearConfirm$$ExternalSyntheticOutline0.m("onActivityResult When Knox reqCode : ", ", resultCode : ", i, i3, "ChooseLockGenericFragment");
            if (i == 10001 || i == 10002) {
                String str = KnoxUtils.mDeviceType;
                if (DualDarManager.isOnDeviceOwnerEnabled() && this.mIsEnforcedPwdChanged && !this.mIsFromDualDarDo && i3 != 0) {
                    this.mIsDualDarDoEnforcePwdChangeOngoing = true;
                    FragmentActivity activity2 = getActivity();
                    Intent intent2 = new Intent(activity2, (Class<?>) InternalActivity.class);
                    intent2.putExtra("android.intent.extra.USER_ID", this.mUserId);
                    intent2.putExtra("isEnforcedPwdChanged", true);
                    intent2.putExtra("isEnforcedPwdChangedForDualDarDo", true);
                    activity2.startActivity(intent2);
                    activity2.finish();
                }
            }
            if (i3 == 1) {
                LockUtils.updateSetUpCredentialIfNeeded(getContext(), this.mUserId);
            }
            setResult(i3);
            if (i == 10001) {
                if (i3 == 1 || i3 != 0) {
                    String str2 = KnoxUtils.mDeviceType;
                    if (!DualDarManager.isOnDeviceOwnerEnabled() || !this.mIsEnforcedPwdChanged) {
                        FragmentActivity activity3 = getActivity();
                        StringBuilder sb = Utils.sBuilder;
                        Uri parse = Uri.parse("content://com.sec.knox.provider/PasswordPolicy2");
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("API", "setPwdChangeRequested");
                        contentValues.put("flag", (Integer) 0);
                        activity3.getContentResolver().insert(parse, contentValues);
                        KnoxUtils.setTwoFactorValue(getContext(), this.mUserId, 0);
                    }
                    resetUcmKeyguard(this.mUserId);
                }
                if (this.mLockTypePolicy.isEnterpriseUser()) {
                    KnoxUtils.mHasChooseLockResult = true;
                    StringBuilder sb2 = new StringBuilder("onActivityResult() - ChooseLock result updated(");
                    sb2.append(KnoxUtils.mHasChooseLockResult);
                    sb2.append(") for user ");
                    Preference$$ExternalSyntheticOutline0.m(sb2, this.mUserId, "ChooseLockGenericFragment.SDP");
                }
                finish();
                if (this.mIsEnforcedPwdChanged && this.mLockTypePolicy.mIsKnoxId) {
                    ConfirmDeviceCredentialUtils.checkForPendingIntent(getActivity());
                    return;
                }
                return;
            }
            if (i == 10002) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(i3, "two factor result : code : ", "ChooseLockGenericFragment");
                if (i3 == -1) {
                    KnoxUtils.setTwoFactorValue(getContext(), this.mUserId, 1);
                    LockUtils.updateSetUpCredentialIfNeeded(getContext(), this.mUserId);
                    Log.d("ChooseLockGenericFragment", "two factor : SET");
                    getActivity().setResult(1);
                    finish();
                    return;
                }
                if (i3 == 1) {
                    Settings.Secure.putIntForUser(getContentResolver(), "knox_finger_print_plus", 0, this.mUserId);
                    Log.d("ChooseLockGenericFragment", "two factor : LATER");
                    getActivity().setResult(1);
                    finish();
                    return;
                }
                Log.d("ChooseLockGenericFragment", "two factor Not set successfully");
                SwitchPreference switchPreference3 = (SwitchPreference) findPreference("switch_fingerprint");
                if (switchPreference3 != null && hasEnrolledFingerprints(this.mUserId)) {
                    switchPreference3.setSummary(R.string.status_registered);
                }
                finish();
                return;
            }
            if (i == 10003) {
                SwitchPreference switchPreference4 = (SwitchPreference) findPreference("switch_fingerprint");
                if (switchPreference4 != null) {
                    switchPreference4.setEnabled(true);
                    if (i3 == -1) {
                        switchPreference4.setChecked(true);
                        switchPreference4.setSummary(R.string.status_registered);
                        SecurityUtils.setBiometricLock(getActivity(), this.mLockPatternUtils, 1, this.mUserId);
                        if (this.mLockTypePolicy.mIsSecureFolderId) {
                            getContext();
                            if (!FingerprintSettingsUtils.getFingerprintUnlockEnabled(this.mLockPatternUtils, 0)) {
                                SecurityUtils.setBiometricLock(getActivity(), this.mLockPatternUtils, 1, 0);
                            }
                        }
                    }
                    if (KnoxUtils.isPwdSetupOrChangeRequested(getContext(), this.mUserId)) {
                        return;
                    }
                    if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId) || this.mLockTypePolicy.isDevicePasswordAdminEnabled()) {
                        if (i3 == -1) {
                            getActivity().setResult(1);
                        }
                        finish();
                        return;
                    }
                    return;
                }
                return;
            }
            if (i != 1004) {
                if (i != 10009) {
                    finish();
                    return;
                }
                String stringExtra2 = intent != null ? intent.getStringExtra("unlockMethodToSet") : null;
                if (stringExtra2 != null) {
                    setUnlockMethod(stringExtra2);
                    return;
                }
                return;
            }
            SwitchPreference switchPreference5 = (SwitchPreference) findPreference("switch_face");
            if (switchPreference5 != null) {
                switchPreference5.setEnabled(true);
                if (i3 == -1) {
                    switchPreference5.setSummary(R.string.status_registered);
                    switchPreference5.setChecked(true);
                    FaceSettingsHelper.setFaceLock(this.mUserId, getActivity(), this.mLockPatternUtils);
                }
                if (KnoxUtils.isPwdSetupOrChangeRequested(getContext(), this.mUserId)) {
                    return;
                }
                if (i3 == -1) {
                    getActivity().setResult(1);
                }
                finish();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:108:0x03c2  */
        /* JADX WARN: Removed duplicated region for block: B:114:0x03cc  */
        @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onCreate(android.os.Bundle r17) {
            /*
                Method dump skipped, instructions count: 1413
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment.onCreate(android.os.Bundle):void");
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
        public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
            super.onCreateOptionsMenu(menu, menuInflater);
            if ((this.mForFingerprint || this.mForFace) && !this.mIsSetNewPassword && this.mFromSetupWizard && ((SettingsActivity) getActivity()).getSupportActionBar() != null) {
                ((SettingsActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }

        @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
            if (lockPatternUtils != null) {
                if (this.mIsManagedProfile) {
                    if (lockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId)) {
                        final int i = 0;
                        getActivity().setTitle(this.mDpm.getResources().getString("Settings.LOCK_SETTINGS_UPDATE_PROFILE_LOCK_TITLE", new Supplier(this) { // from class: com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment$$ExternalSyntheticLambda0
                            public final /* synthetic */ ChooseLockGeneric.ChooseLockGenericFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i2 = i;
                                ChooseLockGeneric.ChooseLockGenericFragment chooseLockGenericFragment = this.f$0;
                                switch (i2) {
                                    case 0:
                                        int i3 = chooseLockGenericFragment.mExtraLockScreenTitleResId;
                                        if (i3 == -1) {
                                            i3 = R.string.lock_settings_picker_update_profile_lock_title;
                                        }
                                        return chooseLockGenericFragment.getString(i3);
                                    default:
                                        int i4 = chooseLockGenericFragment.mExtraLockScreenTitleResId;
                                        if (i4 == -1) {
                                            i4 = R.string.lock_settings_picker_new_profile_lock_title;
                                        }
                                        return chooseLockGenericFragment.getString(i4);
                                }
                            }
                        }));
                    } else {
                        final int i2 = 1;
                        getActivity().setTitle(this.mDpm.getResources().getString("Settings.LOCK_SETTINGS_NEW_PROFILE_LOCK_TITLE", new Supplier(this) { // from class: com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment$$ExternalSyntheticLambda0
                            public final /* synthetic */ ChooseLockGeneric.ChooseLockGenericFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i22 = i2;
                                ChooseLockGeneric.ChooseLockGenericFragment chooseLockGenericFragment = this.f$0;
                                switch (i22) {
                                    case 0:
                                        int i3 = chooseLockGenericFragment.mExtraLockScreenTitleResId;
                                        if (i3 == -1) {
                                            i3 = R.string.lock_settings_picker_update_profile_lock_title;
                                        }
                                        return chooseLockGenericFragment.getString(i3);
                                    default:
                                        int i4 = chooseLockGenericFragment.mExtraLockScreenTitleResId;
                                        if (i4 == -1) {
                                            i4 = R.string.lock_settings_picker_new_profile_lock_title;
                                        }
                                        return chooseLockGenericFragment.getString(i4);
                                }
                            }
                        }));
                    }
                } else if (this.mExtraLockScreenTitleResId != -1) {
                    getActivity().setTitle(this.mExtraLockScreenTitleResId);
                }
                if (this.mLockTypePolicy.mIsKnoxId) {
                    setExtraTitle();
                }
            }
            return super.onCreateView(layoutInflater, viewGroup, bundle);
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
        public final void onDestroy() {
            super.onDestroy();
            LockscreenCredential lockscreenCredential = this.mUserPassword;
            if (lockscreenCredential != null) {
                lockscreenCredential.zeroize();
                this.mUserPassword = null;
            }
            LockTypePolicy lockTypePolicy = this.mLockTypePolicy;
            if (lockTypePolicy != null && this.mKnoxEventList != null && lockTypePolicy.mIsSecureFolderId) {
                SwitchPreference switchPreference = (SwitchPreference) findPreference("switch_fingerprint");
                boolean z = switchPreference != null ? switchPreference.mChecked : false;
                ArrayList arrayList = this.mKnoxEventList;
                int integer = getResources().getInteger(R.integer.sec_secure_folder_status_lock_type);
                FragmentActivity activity = getActivity();
                int i = this.mUserId;
                String str = KnoxUtils.mDeviceType;
                int keyguardStoredPasswordQuality = new LockPatternUtils(activity).getKeyguardStoredPasswordQuality(i);
                arrayList.add(KnoxSamsungAnalyticsLogger.addStatus(integer, keyguardStoredPasswordQuality != 65536 ? (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) ? "pin" : (keyguardStoredPasswordQuality == 262144 || keyguardStoredPasswordQuality == 327680 || keyguardStoredPasswordQuality == 393216) ? HostAuth.PASSWORD : ApnSettings.MVNO_NONE : "pattern"));
                this.mKnoxEventList.add(KnoxSamsungAnalyticsLogger.addStatus(getResources().getInteger(R.integer.sec_secure_folder_status_fingerprint), z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
                KnoxSamsungAnalyticsLogger.send(getActivity(), this.mKnoxEventList, this.mUserId);
                this.mKnoxEventList.clear();
            }
            System.gc();
            System.runFinalization();
            System.gc();
        }

        @Override // androidx.fragment.app.Fragment
        public final void onMultiWindowModeChanged(boolean z) {
            super.onMultiWindowModeChanged(z);
            if (z && isResumed()) {
                if (Rune.isSamsungDexMode(getActivity())) {
                    Log.d("ChooseLockGenericFragment", "If Dex stand alone mode, enable multi window");
                } else {
                    finishIfRequired(getActivity());
                }
            }
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
        public final void onPause() {
            super.onPause();
            int intForUser = Settings.System.getIntForUser(getContentResolver(), "display_night_theme", 0, 0);
            if (this.mSetPrevious && !this.mIsSetNewCredentialFromPrevious) {
                Log.d("ChooseLockGenericFragment", "onPause previous lock was set but it is not setNewCredentialFromPrevious finish!");
                finish();
            }
            if (this.mLockPatternUtils.isSecure(this.mUserId) && this.mPasswordConfirmed && this.mUseBioSession) {
                if (this.mChoosePref == 0) {
                    if (intForUser != this.mNightModeIsSet) {
                        this.mNightModeIsSet = intForUser;
                    } else if (isAdded()) {
                        finish();
                    }
                }
                Preference$$ExternalSyntheticOutline0.m(new StringBuilder("UseBioPref : "), this.mChoosePref, "ChooseLockGenericFragment");
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(final Preference preference, Object obj) {
            ScreenLockType screenLockType;
            byte b;
            byte b2;
            String key = preference.getKey();
            ScreenLockType[] values = ScreenLockType.values();
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    screenLockType = null;
                    break;
                }
                screenLockType = values[i];
                if (screenLockType.preferenceKey.equals(key)) {
                    break;
                }
                i++;
            }
            final boolean booleanValue = ((Boolean) obj).booleanValue();
            int ordinal = screenLockType.ordinal();
            if (ordinal == 10) {
                LockTypePolicy lockTypePolicy = this.mLockTypePolicy;
                if (!lockTypePolicy.mIsKnoxId || lockTypePolicy.mIsSecureFolderId) {
                    if (booleanValue) {
                        FaceManager faceManager = this.mFaceManager;
                        if (faceManager == null) {
                            Log.e("ChooseLockGenericFragment", "mFaceManager is null!");
                        } else {
                            if (!faceManager.hasEnrolledTemplates(this.mUserId) || !this.mLockPatternUtils.isSecure(this.mUserId)) {
                                if (SemPersonaManager.isDoEnabled(this.mUserId) && KnoxUtils.isPwdChangeEnforced(getActivity().getApplicationContext(), this.mUserId)) {
                                    startFaceLockSettings("knox_lock_screen_face", true);
                                } else {
                                    startFaceLockSettings("lock_screen_face", false);
                                }
                                ((SwitchPreference) preference).setEnabled(false);
                                return false;
                            }
                            ((SwitchPreference) preference).setChecked(true);
                            FaceSettingsHelper.setFaceLock(this.mUserId, getActivity(), this.mLockPatternUtils);
                        }
                    } else {
                        ((SwitchPreference) preference).setChecked(false);
                        FaceSettingsHelper.removeFaceLock(this.mUserId, getActivity(), this.mLockPatternUtils);
                    }
                } else if (booleanValue) {
                    boolean z = !this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId);
                    if (hasEnrolledFaces()) {
                        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                        LockTypePolicy lockTypePolicy2 = this.mLockTypePolicy;
                        if (lockPatternUtils.isSecure(lockTypePolicy2.mIsSecureFolderId ? 0 : lockTypePolicy2.mUserId)) {
                            b = false;
                            if (!z || b == true) {
                                startFaceLockSettings("knox_lock_screen_face", true);
                            } else {
                                FaceSettingsHelper.setFaceLock(this.mUserId, getActivity(), this.mLockPatternUtils);
                                ((SwitchPreference) preference).setChecked(true);
                            }
                        }
                    }
                    b = true;
                    if (z) {
                    }
                    startFaceLockSettings("knox_lock_screen_face", true);
                } else {
                    ((SwitchPreference) preference).setChecked(false);
                    FaceSettingsHelper.removeFaceLock(this.mUserId, getActivity(), this.mLockPatternUtils);
                }
            } else if (ordinal != 11) {
                if (ordinal == 15) {
                    if (booleanValue) {
                        ((SwitchPreference) preference).setChecked(booleanValue);
                        Settings.System.putIntForUser(getContentResolver(), "sf_reset_with_samsung_account", booleanValue ? 1 : 0, this.mUserId);
                    } else {
                        FragmentActivity activity = getActivity();
                        final int i2 = booleanValue ? 1 : 0;
                        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i3) {
                                ((SwitchPreference) preference).setChecked(booleanValue);
                                ChooseLockGenericFragment chooseLockGenericFragment = ChooseLockGenericFragment.this;
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider = ChooseLockGenericFragment.SEARCH_INDEX_DATA_PROVIDER;
                                Settings.System.putIntForUser(chooseLockGenericFragment.getContentResolver(), "sf_reset_with_samsung_account", i2, ChooseLockGenericFragment.this.mUserId);
                            }
                        };
                        String str = KnoxUtils.mDeviceType;
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle(R.string.reset_dialog_title);
                        builder.setMessage(R.string.reset_dialog_message).setCancelable(true).setPositiveButton(R.string.common_ok, onClickListener);
                        builder.create().show();
                    }
                }
            } else if (this.mLockTypePolicy.mIsKnoxId) {
                if (booleanValue) {
                    boolean z2 = !this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId);
                    if (hasEnrolledFingerprints(this.mUserId)) {
                        LockPatternUtils lockPatternUtils2 = this.mLockPatternUtils;
                        LockTypePolicy lockTypePolicy3 = this.mLockTypePolicy;
                        if (lockPatternUtils2.isSecure(lockTypePolicy3.mIsSecureFolderId ? 0 : lockTypePolicy3.mUserId)) {
                            b2 = false;
                            if (!z2 || b2 == true) {
                                startFingerprintLockSettings("knox_fingerprint_entry", true);
                            } else {
                                FingerprintSettingsUtils.setFingerprintLock(this.mUserId, getActivity(), this.mLockPatternUtils);
                                ((SwitchPreference) preference).setChecked(true);
                            }
                        }
                    }
                    b2 = true;
                    if (z2) {
                    }
                    startFingerprintLockSettings("knox_fingerprint_entry", true);
                } else {
                    ((SwitchPreference) preference).setChecked(false);
                    FingerprintSettingsUtils.removeFingerprintLock(this.mUserId, getActivity(), this.mLockPatternUtils);
                }
            } else if (!booleanValue) {
                ((SwitchPreference) preference).setChecked(false);
                FingerprintSettingsUtils.removeFingerprintLock(this.mUserId, getActivity(), this.mLockPatternUtils);
            } else {
                if (!hasEnrolledFingerprints(this.mUserId) || !this.mLockPatternUtils.isSecure(this.mUserId)) {
                    if (SemPersonaManager.isDoEnabled(this.mUserId) && KnoxUtils.isPwdChangeEnforced(getActivity().getApplicationContext(), this.mUserId)) {
                        startFingerprintLockSettings("knox_fingerprint_entry", true);
                    } else {
                        startFingerprintLockSettings("lock_screen_fingerprint", false);
                    }
                    preference.setEnabled(false);
                    return false;
                }
                ((SwitchPreference) preference).setChecked(true);
                FingerprintSettingsUtils.setFingerprintLock(this.mUserId, getActivity(), this.mLockPatternUtils);
            }
            return false;
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
        public boolean onPreferenceTreeClick(Preference preference) {
            writePreferenceClickMetric(preference);
            String key = preference.getKey();
            if (!isResumed()) {
                return true;
            }
            if (key != null && (key.equals("unlock_set_none") || key.equals("unlock_set_off") || key.equals("unlock_set_pattern") || key.equals("unlock_set_pin") || key.equals("unlock_set_password"))) {
                this.mChoosePref |= 1;
            }
            boolean z = false;
            boolean z2 = hasEnrolledFingerprints(this.mUserId) || hasEnrolledFaces();
            boolean z3 = hasManagedProfileFingerprints(getActivity(), this.mUserId) || hasManagedProfileFace(getActivity(), this.mUserId);
            if ((ScreenLockType.SWIPE.preferenceKey.equals(key) || ScreenLockType.NONE.preferenceKey.equals(key)) && ((this.mLockPatternUtils.isSecure(this.mUserId) || this.mLockTypePolicy.mIsKnoxId || !(this.mLockPatternUtils.isSecure(this.mUserId) || this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId))) && (z2 || z3))) {
                long secureUserId = GateKeeper.getSecureUserId(this.mUserId);
                try {
                    int length = AndroidKeyStoreMaintenance.getAllAppUidsAffectedBySid(this.mUserId, secureUserId).length;
                } catch (KeyStoreException e) {
                    Log.w("ChooseLockGenericFragment", String.format("Failed to get list of apps affected by SID %d removal", Long.valueOf(secureUserId)), e);
                }
                int i = this.mIsManagedProfile ? R.string.unlock_disable_frp_warning_title_profile : R.string.unlock_disable_frp_warning_title;
                FingerprintManager fingerprintManager = this.mFingerprintManager;
                if (fingerprintManager != null && fingerprintManager.isHardwareDetected()) {
                    z = this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId);
                }
                boolean hasEnrolledFaces = hasEnrolledFaces();
                int i2 = (!z || hasEnrolledFaces) ? (!hasEnrolledFaces || z) ? this.mLockTypePolicy.mIsKnoxId ? R.string.sec_change_locktype_non_secure_bio_workspace : R.string.sec_screen_locktype_change_nonsecure_biometric : R.string.sec_screen_locktype_change_nonsecure_face : this.mLockTypePolicy.mIsKnoxId ? R.string.sec_change_locktype_non_secure_fp_workspace : R.string.sec_screen_locktype_change_nonsecure_fingerprint;
                Intent intent = new Intent(getActivity(), (Class<?>) FactoryResetprotectionWarningDialogActivity.class);
                intent.putExtra("titleRes", i);
                intent.putExtra("messageRes", i2);
                intent.putExtra("unlockMethodToSet", key);
                intent.putExtra("userId", this.mUserId);
                startActivityForResult(intent, 10009);
                return true;
            }
            if ("unlock_skip_fingerprint".equals(key) || "unlock_skip_face".equals(key) || "unlock_skip_biometrics".equals(key)) {
                Intent intent2 = new Intent(getActivity(), (Class<?>) getInternalActivityClass());
                intent2.setAction(getIntent().getAction());
                if (WizardManagerHelper.isAnySetupWizard(getIntent())) {
                    WizardManagerHelper.copyWizardManagerExtras(getIntent(), intent2);
                }
                intent2.putExtra("android.intent.extra.USER_ID", this.mUserId);
                intent2.putExtra("choose_lock_setup_screen_title", this.mExtraLockScreenTitleResId);
                intent2.putExtra("confirm_credentials", !this.mPasswordConfirmed);
                intent2.putExtra("requested_min_complexity", this.mRequestedMinComplexity);
                intent2.putExtra("device_password_requirement_only", this.mOnlyEnforceDevicePasswordRequirement);
                intent2.putExtra("caller_app_name", this.mCallerAppName);
                LockscreenCredential lockscreenCredential = this.mUserPassword;
                if (lockscreenCredential != null) {
                    intent2.putExtra(HostAuth.PASSWORD, (Parcelable) lockscreenCredential);
                }
                startActivityForResult(intent2, 104);
                return true;
            }
            if ("unlock_set_fingerprint".equals(key) && this.mFromSetupWizard) {
                return true;
            }
            if (BiometricsAboutUnlockPreferenceController.KEY_SCREEN_TRANSITION_EFFECT.equals(key)) {
                this.mChoosePref |= 2;
                LoggingHelper.insertFlowLogging(8521);
                Intent intent3 = new Intent();
                intent3.setClassName(getContext().getPackageName(), "com.samsung.android.settings.biometrics.BiometricsCommonSecurityNoticeActivity");
                startActivityForResult(intent3, 10011);
                return true;
            }
            if ("key_face_menu".equals(key)) {
                this.mChoosePref |= 2;
                Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("previousStage", "lock_screen_face_menu");
                m.putLong("gk_pw_handle", this.mGkPwHandle);
                m.putInt("android.intent.extra.USER_ID", this.mUserId);
                m.putBoolean("is_knox", SemPersonaManager.isKnoxId(this.mUserId));
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getActivity());
                String name = FaceSettings.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName = name;
                launchRequest.mArguments = m;
                launchRequest.mSourceMetricsCategory = 27;
                subSettingLauncher.addFlags(65536);
                subSettingLauncher.setResultListener(this, 10010);
                subSettingLauncher.launch();
                return true;
            }
            if (!"key_fingerprint_menu".equals(key)) {
                return setUnlockMethod(key);
            }
            this.mChoosePref |= 2;
            Bundle m2 = AbsAdapter$1$$ExternalSyntheticOutline0.m("previousStage", "lock_screen_fingerprint_menu");
            m2.putLong("gk_pw_handle", this.mGkPwHandle);
            m2.putInt("android.intent.extra.USER_ID", this.mUserId);
            m2.putBoolean("is_knox", SemPersonaManager.isKnoxId(this.mUserId));
            SubSettingLauncher subSettingLauncher2 = new SubSettingLauncher(getActivity());
            String name2 = FingerprintSettings.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest2 = subSettingLauncher2.mLaunchRequest;
            launchRequest2.mDestinationName = name2;
            launchRequest2.mArguments = m2;
            launchRequest2.mSourceMetricsCategory = 27;
            subSettingLauncher2.addFlags(65536);
            subSettingLauncher2.setResultListener(this, 10010);
            subSettingLauncher2.launch();
            return true;
        }

        @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
        public final void onResume() {
            super.onResume();
            this.mNightModeIsSet = Settings.System.getIntForUser(getContentResolver(), "display_night_theme", 0, 0);
            if (this.mLockPatternUtils == null) {
                this.mLockPatternUtils = new LockPatternUtils(getActivity());
            }
            if (this.mLockTypePolicy.mIsKnoxId) {
                return;
            }
            if (!LockUtils.isLockSettingsBlockonDexMode(getActivity())) {
                finishIfRequired(getActivity());
            } else {
                Toast.makeText(getActivity(), getString(R.string.sec_lock_screen_disable_by_samsung_dex_waring_text), 0).show();
                finish();
            }
        }

        @Override // com.android.settings.SettingsPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean("password_confirmed", this.mPasswordConfirmed);
            bundle.putBoolean("waiting_for_confirmation", this.mWaitingForConfirmation);
            if (this.mUserPassword != null) {
                if (this.mLockTypePolicy.isEnterpriseUser() || (UCMUtils.isEnforcedCredentialStorageExistAsUser(this.mUserId) && UCMUtils.isSupportChangePin(this.mUserId))) {
                    Preference$$ExternalSyntheticOutline0.m(new StringBuilder("onSaveInstanceState - Secured password required for user "), this.mUserId, "ChooseLockGenericFragment.SDP");
                    bundle.putParcelable(HostAuth.PASSWORD, KnoxUtils.getCipher(this.mUserPassword, KnoxUtils.getCipherPublicHandle()));
                    bundle.putBoolean("is_knox_password_secured", true);
                } else {
                    bundle.putParcelable(HostAuth.PASSWORD, this.mUserPassword.duplicate());
                }
            }
            bundle.putLong("gk_pw_handle", this.mGkPwHandle);
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onStop() {
            super.onStop();
            Wallet24Service wallet24Service = mWallet24Service;
            if (wallet24Service != null) {
                if (wallet24Service.mBound) {
                    wallet24Service.unbindService();
                }
                mWallet24Service = null;
            }
        }

        public final void setExtraTitle() {
            String stringExtra = getActivity().getIntent().getStringExtra("android.intent.extra.TITLE");
            if (stringExtra != null) {
                getActivity().setTitle(stringExtra);
                return;
            }
            LockTypePolicy lockTypePolicy = this.mLockTypePolicy;
            String string = lockTypePolicy.mIsKnoxId ? lockTypePolicy.mIsSecureFolderId ? getString(R.string.sec_lockpassword_choose_lock_generic_sf_header_default) : getString(R.string.sec_lockpassword_choose_lock_generic_work_profile_header_default) : this.mIsFromDualDarDo ? getString(R.string.unlock_set_unlock_launch_picker_title_ddado) : null;
            if (string != null) {
                if (((SettingsActivity) getActivity()).getSupportActionBar() != null) {
                    ((SettingsActivity) getActivity()).getSupportActionBar().setTitle(string);
                }
                getActivity().setTitle(string);
            }
        }

        public final boolean setUnlockMethod(String str) {
            ScreenLockType screenLockType;
            EventLog.writeEvent(90200, str);
            ScreenLockType[] values = ScreenLockType.values();
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    screenLockType = null;
                    break;
                }
                screenLockType = values[i];
                if (screenLockType.preferenceKey.equals(str)) {
                    break;
                }
                i++;
            }
            if (screenLockType != null) {
                int ordinal = screenLockType.ordinal();
                if (ordinal == 0 || ordinal == 1 || ordinal == 2 || ordinal == 3 || ordinal == 4 || ordinal == 5 || ordinal == 7) {
                    updateUnlockMethodAndFinish(screenLockType.defaultQuality, screenLockType == ScreenLockType.NONE, false);
                    return true;
                }
                if (ordinal == 14 && this.mLockTypePolicy.isWorkDeviceOrProfile()) {
                    Log.d("ChooseLockGenericFragment", "set two-factor for knox");
                    Intent intent = new Intent().setClass(getActivity(), KnoxChooseLockTwoFactor.class);
                    intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
                    intent.putExtra(HostAuth.PASSWORD, (Parcelable) this.mUserPassword);
                    intent.putExtra("request_gk_pw_handle", true);
                    intent.putExtra("requested_min_complexity", this.mRequestedMinComplexity);
                    intent.putExtra("device_password_requirement_only", this.mOnlyEnforceDevicePasswordRequirement);
                    this.mChoosePref |= 2;
                    startActivityForResult(intent, 10002);
                    return true;
                }
            }
            Log.e("ChooseLockGenericFragment", "Encountered unknown unlock method to set: " + str);
            return false;
        }

        public final void showSensorErrorDialog(int i, int i2) {
            if (getActivity() == null) {
                Log.e("ChooseLockGenericFragment", "Activity is null. Skip showSensorErrorDialog for type = ".concat(i2 == 0 ? "Fingerprint" : "Face"));
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(i2 == 0 ? R.string.sec_fingerprint_attention : R.string.sec_face_attention);
            builder.setMessage(i);
            builder.setPositiveButton(android.R.string.ok, new ChooseLockGeneric$ChooseLockGenericFragment$$ExternalSyntheticLambda2());
            androidx.appcompat.app.AlertDialog create = builder.create();
            create.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    ChooseLockGeneric.ChooseLockGenericFragment chooseLockGenericFragment = ChooseLockGeneric.ChooseLockGenericFragment.this;
                    Indexable$SearchIndexProvider indexable$SearchIndexProvider = ChooseLockGeneric.ChooseLockGenericFragment.SEARCH_INDEX_DATA_PROVIDER;
                    chooseLockGenericFragment.finish();
                }
            });
            create.show();
        }

        public final void startFaceLockSettings(String str, boolean z) {
            Log.d("ChooseLockGenericFragment", "startFaceLockSettings()");
            FaceManager faceManager = this.mFaceManager;
            if (faceManager != null && !faceManager.isHardwareDetected()) {
                Log.d("ChooseLockGenericFragment", "isHardwareDetected() of Face is FALSE.");
                showSensorErrorDialog(R.string.sec_face_error_message_sensor_error, 1);
                return;
            }
            Intent intent = new Intent().setClass(getActivity(), FaceLockSettings.class);
            if ("lock_screen_face".equals(str) || "knox_lock_screen_face".equals(str)) {
                intent.putExtra(HostAuth.PASSWORD, (Parcelable) this.mUserPassword);
            }
            intent.putExtra("android.intent.extra.USER_ID", this.mUserId);
            intent.putExtra("requested_min_complexity", this.mRequestedMinComplexity);
            intent.putExtra("previousStage", str);
            intent.putExtra("gk_pw_handle", this.mGkPwHandle);
            int i = this.mUnificationProfileId;
            if (i != -10000) {
                intent.putExtra("unification_profile_id", i);
                intent.putExtra("unification_profile_credential", (Parcelable) this.mUnificationProfileCredential);
            }
            this.mChoosePref |= 2;
            if (z || (this.mLockTypePolicy.isDevicePasswordAdminEnabled() && this.mIsEnforcedPwdChanged)) {
                startActivityForResult(intent, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI);
                return;
            }
            SwitchPreference switchPreference = (SwitchPreference) findPreference("switch_fingerprint");
            if (switchPreference != null) {
                switchPreference.setEnabled(false);
            }
            startActivityForResult(intent, 10008);
        }

        public final void startFingerprintLockSettings(String str, boolean z) {
            Log.d("ChooseLockGenericFragment", "startFingerprintLockSettings() " + this.mUserId);
            if (SecurityUtils.isFingerprintDisabled(getActivity().getApplicationContext(), this.mUserId)) {
                Log.d("ChooseLockGenericFragment", "isFingerprintDisabled() = TRUE");
                finish();
                return;
            }
            FingerprintManager fingerprintManager = this.mFingerprintManager;
            if (fingerprintManager != null && !fingerprintManager.isHardwareDetected()) {
                Log.d("ChooseLockGenericFragment", "isHardwareDetected() of Fingerprint is FALSE.");
                showSensorErrorDialog(R.string.sec_fingerprint_error_message_sensor_error, 0);
                return;
            }
            Intent intent = new Intent().setClass(getActivity(), FingerprintLockSettings.class);
            if ("knox_fingerprint_entry".equals(str)) {
                intent.putExtra(HostAuth.PASSWORD, this.mLockTypePolicy.mIsSecureFolderId ? null : this.mUserPassword);
            }
            intent.putExtra("fromSetupWizard", this.mFromSetupWizard);
            LockTypePolicy lockTypePolicy = this.mLockTypePolicy;
            intent.putExtra("android.intent.extra.USER_ID", lockTypePolicy.mIsSecureFolderId ? 0 : lockTypePolicy.mUserId);
            intent.putExtra("requested_min_complexity", this.mRequestedMinComplexity);
            intent.putExtra("previousStage", str);
            intent.putExtra("gk_pw_handle", this.mGkPwHandle);
            int i = this.mUnificationProfileId;
            if (i != -10000) {
                intent.putExtra("unification_profile_id", i);
                intent.putExtra("unification_profile_credential", (Parcelable) this.mUnificationProfileCredential);
            }
            this.mChoosePref |= 2;
            if (z || (this.mLockTypePolicy.isDevicePasswordAdminEnabled() && this.mIsEnforcedPwdChanged)) {
                intent.putExtra("mKnoxIdentifyOnly", z);
                startActivityForResult(intent, 10003);
                return;
            }
            SwitchPreference switchPreference = (SwitchPreference) findPreference("switch_face");
            if (switchPreference != null) {
                switchPreference.setEnabled(false);
            }
            startActivityForResult(intent, 10008);
            if (!Utils.isTablet() || this.mFromBioScreenLock || "lock_screen_fingerprint".equals(str)) {
                return;
            }
            getActivity().overridePendingTransition(0, 0);
        }

        public final void updateBiometricsMenuPreference(String str) {
            String string;
            boolean z;
            Preference findPreference = findPreference(str);
            if (findPreference == null || !findPreference.isEnabled()) {
                return;
            }
            if ("key_face_menu".equals(str)) {
                if (hasEnrolledFaces()) {
                    getContext();
                    z = FaceSettingsHelper.getFaceUnlockEnabled(this.mLockPatternUtils, this.mUserId) && !SecurityUtils.isFaceDisabled(getContext(), this.mUserId);
                    string = getString(R.string.sec_face_register_summary);
                } else {
                    string = getString(R.string.sec_face_add_summary);
                    z = false;
                }
            } else {
                if (!"key_fingerprint_menu".equals(str)) {
                    return;
                }
                int enrolledFingerNumber = FingerprintSettingsUtils.getEnrolledFingerNumber(this.mFingerprintManager, this.mUserId);
                if (enrolledFingerNumber > 0) {
                    getContext();
                    z = FingerprintSettingsUtils.getFingerprintUnlockEnabled(this.mLockPatternUtils, this.mUserId) && !SecurityUtils.isFingerprintDisabled(getContext(), this.mUserId);
                    string = getResources().getQuantityString(R.plurals.sec_fingerprint_added_summary, enrolledFingerNumber, Integer.valueOf(enrolledFingerNumber));
                } else {
                    string = getString(R.string.sec_fingerprint_add_summary);
                    z = false;
                }
            }
            findPreference.setSummary(string);
            if (z) {
                findPreference.setSummary(appendCurrentLockTypeToSummary(findPreference.getSummary()));
                if (findPreference instanceof SecLockTypePreference) {
                    ((SecLockTypePreference) findPreference).mIconVisibility = 0;
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:179:0x0560  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x035b  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x055b  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x0566  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x056b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void updatePreferencesOrFinish(boolean r24) {
            /*
                Method dump skipped, instructions count: 2216
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment.updatePreferencesOrFinish(boolean):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x005a  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x00bb  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateUnlockMethodAndFinish(int r9, boolean r10, boolean r11) {
            /*
                Method dump skipped, instructions count: 512
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment.updateUnlockMethodAndFinish(int, boolean, boolean):void");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class InternalActivity extends ChooseLockGeneric {
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class RecoveryActivity extends ChooseLockGeneric {
        public AnonymousClass1 mReceiver;

        @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
        public final void onPause() {
            super.onPause();
            AnonymousClass1 anonymousClass1 = this.mReceiver;
            if (anonymousClass1 == null) {
                return;
            }
            try {
                unregisterReceiver(anonymousClass1);
                this.mReceiver = null;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.android.settings.password.ChooseLockGeneric$RecoveryActivity$1] */
        @Override // com.android.settings.SettingsActivity, com.samsung.android.settings.core.SecSettingsBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
        public final void onResume() {
            super.onResume();
            if (this.mReceiver != null) {
                return;
            }
            IntentFilter m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.intent.action.USER_PRESENT");
            ?? r1 = new BroadcastReceiver() { // from class: com.android.settings.password.ChooseLockGeneric.RecoveryActivity.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals("android.intent.action.USER_PRESENT")) {
                        RecoveryActivity.this.finish();
                    }
                }
            };
            this.mReceiver = r1;
            registerReceiver(r1, m);
        }
    }

    @Override // android.app.Activity
    public final void finish() {
        ChooseLockGenericFragment chooseLockGenericFragment = (ChooseLockGenericFragment) getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (chooseLockGenericFragment != null) {
            Indexable$SearchIndexProvider indexable$SearchIndexProvider = ChooseLockGenericFragment.SEARCH_INDEX_DATA_PROVIDER;
            if (chooseLockGenericFragment.interceptFinishIfNeeded()) {
                return;
            }
        }
        super.finish();
    }

    public Class getFragmentClass() {
        return ChooseLockGenericFragment.class;
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", getFragmentClass().getName());
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public boolean isValidFragment(String str) {
        return ChooseLockGenericFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity, com.android.settings.core.SettingsBaseActivity, com.samsung.android.settings.core.SecSettingsBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.mForFingerprint = intent.getBooleanExtra("for_fingerprint", false);
        this.mForFace = intent.getBooleanExtra("for_face", false);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if ((!this.mForFingerprint && !this.mForFace) || menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
