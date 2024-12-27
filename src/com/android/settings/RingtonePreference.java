package com.android.settings;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.preference.Preference;

import com.android.internal.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.utils.SimUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class RingtonePreference extends Preference {
    public int mRingtoneType;
    public final boolean mShowDefault;
    public final boolean mShowSilent;
    public boolean mSim2FirstInDualMode;
    public Context mUserContext;
    public int mUserId;

    public RingtonePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSim2FirstInDualMode = false;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.RingtonePreference, 0, 0);
        this.mRingtoneType = obtainStyledAttributes.getInt(0, 1);
        this.mShowDefault = obtainStyledAttributes.getBoolean(1, true);
        this.mShowSilent = obtainStyledAttributes.getBoolean(2, true);
        setIntent(
                new Intent("android.intent.action.RINGTONE_PICKER")
                        .setPackage(context.getString(R.string.config_sound_picker_package_name)));
        this.mUserId = UserHandle.myUserId();
        this.mUserContext = Utils.createPackageContextAsUser(getContext(), this.mUserId);
        obtainStyledAttributes.recycle();
    }

    public final boolean isValidRingtoneUri(Uri uri) {
        int i;
        if (uri == null
                || RingtoneManager.isDefault(uri)
                || "android.resource".equals(uri.getScheme())) {
            return true;
        }
        String type = this.mUserContext.getContentResolver().getType(uri);
        if (type == null) {
            Log.e(
                    "RingtonePreference",
                    "isValidRingtoneUri for URI:"
                            + uri
                            + " failed: failure to find mimeType (no access from this context?)");
            return false;
        }
        if (!type.startsWith("audio/")
                && !type.equals("application/ogg")
                && !type.equals("application/x-flac")) {
            Log.e(
                    "RingtonePreference",
                    "isValidRingtoneUri for URI:"
                            + uri
                            + " failed: associated mimeType:"
                            + type
                            + " is not an audio type");
            return false;
        }
        int userIdFromUri = ContentProvider.getUserIdFromUri(uri, this.mUserId);
        if (userIdFromUri != this.mUserId) {
            UserManager userManager =
                    (UserManager) this.mUserContext.getSystemService(UserManager.class);
            if (!userManager.isSameProfileGroup(this.mUserId, userIdFromUri)) {
                Log.e(
                        "RingtonePreference",
                        "isValidRingtoneUri for URI:"
                                + uri
                                + " failed: user "
                                + userIdFromUri
                                + " and user "
                                + this.mUserId
                                + " are not in the same profile group");
                return false;
            }
            if (userManager.isProfile()) {
                userIdFromUri = this.mUserId;
                i = userIdFromUri;
            } else {
                i = this.mUserId;
            }
            UserHandle profileParent = userManager.getProfileParent(UserHandle.of(userIdFromUri));
            if (profileParent == null || profileParent.getIdentifier() != i) {
                Log.e(
                        "RingtonePreference",
                        "isValidRingtoneUri for URI:"
                                + uri
                                + " failed: user "
                                + userIdFromUri
                                + " is not a profile of user "
                                + i);
                return false;
            }
            if (userManager.hasUserRestrictionForUser(
                    "no_sharing_into_profile", UserHandle.of(i))) {
                Log.e(
                        "RingtonePreference",
                        "isValidRingtoneUri for URI:"
                                + uri
                                + " failed: user "
                                + i
                                + " has restriction: no_sharing_into_profile");
                return false;
            }
            if (!userManager.isManagedProfile(userIdFromUri)
                    && !userManager
                            .getUserProperties(UserHandle.of(userIdFromUri))
                            .isMediaSharedWithParent()) {
                Log.e(
                        "RingtonePreference",
                        "isValidRingtoneUri for URI:"
                                + uri
                                + " failed: user "
                                + userIdFromUri
                                + " is not a cloned or managed profile");
                return false;
            }
        }
        return true;
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        int i3;
        if (intent == null) {
            return true;
        }
        setUseCallBackGroundSound(
                this.mRingtoneType,
                intent.getIntExtra(
                        "android.samsung.intent.extra.ringtone.CALL_BACKGROUND_SOUND", -1));
        if (SimUtils.isMultiSimEnabled(getContext())) {
            setUseCallBackGroundSound(
                    this.mRingtoneType == 2 ? 256 : 128,
                    intent.getIntExtra(
                            "android.samsung.intent.extra.ringtone.CALL_BACKGROUND_SOUND_2", -1));
        }
        Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
        boolean z = AudioRune.SUPPORT_SOUND_THEME;
        String str = ApnSettings.MVNO_NONE;
        if (!z) {
            if (callChangeListener(uri != null ? uri.toString() : ApnSettings.MVNO_NONE)) {
                onSaveRingtone(uri);
            }
            if (!SimUtils.isMultiSimEnabled(getContext())) {
                return true;
            }
            i3 = this.mRingtoneType != 2 ? 128 : 256;
            Uri uri2 =
                    (Uri)
                            intent.getParcelableExtra(
                                    "android.samsung.intent.extra.ringtone.PICKED_URI_2");
            if (uri != null) {
                str = uri.toString();
            }
            if (!callChangeListener(str)) {
                return true;
            }
            onSaveRingtone(uri2, i3);
            return true;
        }
        if (this.mRingtoneType == 512) {
            String stringExtra =
                    intent.getStringExtra(
                            "android.samsung.intent.extra.ringtone.PICKED_SOUND_THEME");
            if (stringExtra != null) {
                str = stringExtra;
            }
            if (!callChangeListener(str)) {
                return true;
            }
            onSaveSystemSound(stringExtra);
            return true;
        }
        if (callChangeListener(uri != null ? uri.toString() : ApnSettings.MVNO_NONE)) {
            onSaveRingtone(uri);
        }
        if (!SimUtils.isMultiSimEnabled(getContext())) {
            return true;
        }
        i3 = this.mRingtoneType != 2 ? 128 : 256;
        Uri uri3 =
                (Uri)
                        intent.getParcelableExtra(
                                "android.samsung.intent.extra.ringtone.PICKED_URI_2");
        if (uri != null) {
            str = uri.toString();
        }
        if (!callChangeListener(str)) {
            return true;
        }
        onSaveRingtone(uri3, i3);
        return true;
    }

    @Override // androidx.preference.Preference
    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    public void onPrepareRingtonePickerIntent(Intent intent) {
        intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", onRestoreRingtone());
        intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", this.mShowDefault);
        if (this.mShowDefault) {
            intent.putExtra(
                    "android.intent.extra.ringtone.DEFAULT_URI",
                    RingtoneManager.getDefaultUri(this.mRingtoneType));
        }
        intent.putExtra("android.intent.extra.ringtone.SHOW_SILENT", this.mShowSilent);
        intent.putExtra("android.intent.extra.ringtone.TYPE", this.mRingtoneType);
        intent.putExtra("android.intent.extra.ringtone.TITLE", getTitle());
    }

    public Uri onRestoreRingtone() {
        String persistedString = getPersistedString(null);
        if (TextUtils.isEmpty(persistedString)) {
            return null;
        }
        return Uri.parse(persistedString);
    }

    public void onSaveRingtone(Uri uri) {
        persistString(uri != null ? uri.toString() : ApnSettings.MVNO_NONE);
    }

    public void onSaveSystemSound(String str) {
        if (str == null) {
            str = ApnSettings.MVNO_NONE;
        }
        persistString(str);
    }

    @Override // androidx.preference.Preference
    public final void onSetInitialValue(Object obj, boolean z) {
        String str = (String) obj;
        if (z || TextUtils.isEmpty(str)) {
            return;
        }
        onSaveRingtone(Uri.parse(str));
    }

    public final void setUseCallBackGroundSound(int i, int i2) {
        if (i2 == 1) {
            if (i == 1) {
                Settings.Global.putInt(
                        this.mUserContext.getContentResolver(), "use_video_sound_for_ringtone", 1);
                return;
            } else {
                if (i == 128) {
                    Settings.Global.putInt(
                            this.mUserContext.getContentResolver(),
                            "use_video_sound_for_ringtone_sim2",
                            1);
                    return;
                }
                return;
            }
        }
        if (i2 == 0) {
            if (i == 1) {
                Settings.Global.putInt(
                        this.mUserContext.getContentResolver(), "use_video_sound_for_ringtone", 0);
            } else if (i == 128) {
                Settings.Global.putInt(
                        this.mUserContext.getContentResolver(),
                        "use_video_sound_for_ringtone_sim2",
                        0);
            }
        }
    }

    public void onSaveRingtone(Uri uri, int i) {
        persistString(uri != null ? uri.toString() : ApnSettings.MVNO_NONE);
    }
}
