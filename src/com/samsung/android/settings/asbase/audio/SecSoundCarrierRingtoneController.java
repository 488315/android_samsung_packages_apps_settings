package com.samsung.android.settings.asbase.audio;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.development.OnActivityResultListener;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundCarrierRingtoneController extends BasePreferenceController
        implements OnActivityResultListener {
    private static final String KEY = "carrierringtone";
    private static final int REQUEST_CODE_WIFI_SETUP_ACTIVITY = 3;
    private static final String TAG = "SecSoundCarrierRingtoneController";
    private SettingsPreferenceFragment mFragment;

    public SecSoundCarrierRingtoneController(
            Context context, SettingsPreferenceFragment settingsPreferenceFragment) {
        super(context, KEY);
        this.mFragment = settingsPreferenceFragment;
    }

    private String getCarrierRingtoneURI() {
        if (AudioRune.SUPPORT_SKT_RINGTONE) {
            return "http://www.tcoloring.com";
        }
        if (AudioRune.SUPPORT_KT_RINGTONE) {
            return "http://ringtoyou.kt.com";
        }
        if (AudioRune.SUPPORT_LGU_RINGTONE) {
            return "http://www.musicbellring.com";
        }
        return null;
    }

    private boolean isSupportCarrierRingtone() {
        return (AudioRune.SUPPORT_SKT_RINGTONE
                        || AudioRune.SUPPORT_KT_RINGTONE
                        || AudioRune.SUPPORT_LGU_RINGTONE)
                && !Rune.isLDUModel();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!isSupportCarrierRingtone()
                        || new Intent("android.intent.action.VIEW")
                                        .resolveActivity(this.mContext.getPackageManager())
                                == null)
                ? 3
                : 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        if (SoundUtils.isNetworkConnected(this.mContext)) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(getCarrierRingtoneURI()));
            if (intent.resolveActivity(this.mContext.getPackageManager()) != null) {
                try {
                    this.mFragment.startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    return false;
                }
            }
        } else {
            Intent intent2 = new Intent("com.samsung.settings.PICK_WIFI_NETWORK");
            intent2.putExtra("extra_prefs_show_button_bar", true);
            intent2.putExtra("wifi_enable_next_on_connect", true);
            intent2.putExtra(
                    "extra_prefs_set_back_text",
                    this.mContext.getString(R.string.back_button_label));
            try {
                this.mFragment.startActivityForResult(intent2, 3);
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "handlePreferenceTreeClick", e);
                return false;
            }
        }
        return true;
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

    @Override // com.android.settings.development.OnActivityResultListener
    public boolean onActivityResult(int i, int i2, Intent intent) {
        if (i == 3 && i2 == -1 && SoundUtils.isNetworkConnected(this.mContext)) {
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setData(Uri.parse(getCarrierRingtoneURI()));
            try {
                this.mFragment.startActivity(intent2);
                return true;
            } catch (ActivityNotFoundException unused) {
            }
        }
        return false;
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
        String str;
        super.updateState(preference);
        if (AudioRune.SUPPORT_SKT_RINGTONE) {
            str = this.mContext.getString(R.string.sec_tcoloring);
        } else if (AudioRune.SUPPORT_KT_RINGTONE) {
            str = this.mContext.getString(R.string.sec_ringtoyou);
        } else if (AudioRune.SUPPORT_LGU_RINGTONE) {
            str = this.mContext.getString(R.string.sec_musicbellring);
            preference.setSummary(R.string.sec_musicbellring_summary);
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        preference.setTitle(str);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
