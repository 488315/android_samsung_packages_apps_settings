package com.samsung.android.settings.encryption;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.security.SemSdCardEncryption;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SDCardEncryptStatusPreferenceController extends BasePreferenceController {
    public static final String PREF_KEY_SDCARD_ENCRYPT_DETAIL_PAGE = "sdcard_encrypt_status";
    private static final String TAG = "SDCardEncryptStatusPreferenceController";
    private DevicePolicyManager mDPM;
    private SemSdCardEncryption mSse;
    private final UserManager mUserManager;

    public SDCardEncryptStatusPreferenceController(Context context, String str) {
        super(context, str);
        this.mSse = null;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mDPM = (DevicePolicyManager) context.getSystemService("device_policy");
        this.mSse = new SemSdCardEncryption(this.mContext);
    }

    private boolean isSdcardBlockedByMdm() {
        Cursor query =
                this.mContext
                        .getContentResolver()
                        .query(
                                Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3"),
                                null,
                                "isSdCardEnabled",
                                new String[] {"false"},
                                null);
        if (query == null) {
            return false;
        }
        try {
            query.moveToFirst();
            if (!query.getString(query.getColumnIndex("isSdCardEnabled")).equals("false")) {
                return false;
            }
            Log.i(TAG, "SDCARD_ENABLED is false");
            query.close();
            return true;
        } finally {
            query.close();
        }
    }

    private boolean isSupportBlockEncryption() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!TextUtils.equals(getPreferenceKey(), PREF_KEY_SDCARD_ENCRYPT_DETAIL_PAGE)
                || this.mSse.isEncryptionSupported()) {
            return this.mUserManager.isAdminUser() ? 0 : 4;
        }
        return 3;
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
        if (this.mSse.isEncryptionSupported()) {
            int currentStatus = this.mSse.getCurrentStatus();
            String volumeState = this.mSse.getVolumeState();
            if (isSupportBlockEncryption()) {
                preference.setTitle(R.string.sdcard_encrypt_title);
            } else {
                preference.setTitle(R.string.sdcard_encrypt_or_decrypt_title);
            }
            if (volumeState == null
                    || (isSupportBlockEncryption() && "unmountable".equals(volumeState))) {
                preference.setSummary(
                        volumeState == null
                                ? R.string.sdcard_no_title_summary
                                : R.string.sdcard_mount_failed_summary_block);
                preference.setEnabled(true);
            } else {
                boolean samsungSDcardEncryptionStatus =
                        this.mDPM.getSamsungSDcardEncryptionStatus(null);
                int i = R.string.sdcard_encrypt_title_summary;
                if (!samsungSDcardEncryptionStatus) {
                    if (isSupportBlockEncryption()) {
                        i = R.string.sdcard_encrypt_title_summary_block;
                    }
                    preference.setSummary(i);
                    preference.setEnabled(true);
                } else if (currentStatus == 0 && this.mSse.isSdCardEncryped()) {
                    if (this.mDPM.semGetRequireStorageCardEncryption(null)) {
                        preference.setSummary(R.string.sdcard_restrict_title_summary);
                        preference.setEnabled(false);
                    } else {
                        preference.setSummary(R.string.sdcard_decrypt_title_summary);
                        preference.setEnabled(true);
                    }
                } else if (this.mDPM.semGetRequireStorageCardEncryption(null)) {
                    if (isSupportBlockEncryption()) {
                        i = R.string.sdcard_encrypt_title_summary_block;
                    }
                    preference.setSummary(i);
                    preference.setEnabled(true);
                } else {
                    preference.setSummary(R.string.sdcard_decrypt_title_summary);
                    preference.setEnabled(true);
                }
            }
            if (isSdcardBlockedByMdm()) {
                Log.i(TAG, "SD card was disabled by MDM policy");
                preference.setEnabled(false);
            }
            preference.setFragment(CryptSDCardSettings.class.getName());
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
