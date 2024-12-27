package com.samsung.android.settings.asbase.routine.action.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.ParameterValues$$ExternalSyntheticLambda0;
import com.samsung.android.sdk.routines.v3.internal.ExtraKey;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.util.SemLog;
import com.sec.ims.im.ImIntent;

import org.json.JSONObject;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RingtoneActionActivity extends AppCompatActivity {
    public String mAbsolutePath;
    public Context mContext;
    public Uri mRingtoneUri;
    public CharSequence mTitle;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SemLog.d("RingtoneActionActivity", "OnCreate");
        this.mContext = getApplicationContext();
        ActivityResultLauncher registerForActivityResult =
                registerForActivityResult(
                        new ActivityResultContracts$StartActivityForResult(0),
                        new ActivityResultCallback() { // from class:
                            // com.samsung.android.settings.asbase.routine.action.ui.RingtoneActionActivity.1
                            @Override // androidx.activity.result.ActivityResultCallback
                            public final void onActivityResult(Object obj) {
                                ActivityResult activityResult = (ActivityResult) obj;
                                int i = activityResult.mResultCode;
                                RingtoneActionActivity ringtoneActionActivity =
                                        RingtoneActionActivity.this;
                                if (i == -1) {
                                    HashMap hashMap = new HashMap();
                                    Uri uri =
                                            (Uri)
                                                    activityResult.mData.getParcelableExtra(
                                                            "android.intent.extra.ringtone.PICKED_URI",
                                                            Uri.class);
                                    ringtoneActionActivity.mRingtoneUri = uri;
                                    if (uri != null) {
                                        ringtoneActionActivity.mTitle =
                                                Ringtone.getTitleWithSoundTheme(
                                                        ringtoneActionActivity.mContext,
                                                        uri,
                                                        false,
                                                        true);
                                        if (ringtoneActionActivity
                                                .mRingtoneUri
                                                .toString()
                                                .contains(
                                                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                                                                .toString())) {
                                            try {
                                                Cursor query =
                                                        ringtoneActionActivity
                                                                .mContext
                                                                .getContentResolver()
                                                                .query(
                                                                        ringtoneActionActivity
                                                                                .mRingtoneUri,
                                                                        new String[] {"_data"},
                                                                        null,
                                                                        null,
                                                                        null);
                                                if (query != null) {
                                                    try {
                                                        if (query.getCount() >= 1) {
                                                            query.moveToFirst();
                                                            ringtoneActionActivity.mAbsolutePath =
                                                                    query.getString(
                                                                            query
                                                                                    .getColumnIndexOrThrow(
                                                                                            "_data"));
                                                        }
                                                    } finally {
                                                    }
                                                }
                                                if (query != null) {
                                                    query.close();
                                                }
                                            } catch (Exception e) {
                                                Log.i(
                                                        "RingtoneActionActivity",
                                                        "saveAbsolutePath " + e);
                                            }
                                        } else {
                                            ringtoneActionActivity.mAbsolutePath =
                                                    MediaStore.Audio.Media.INTERNAL_CONTENT_URI
                                                            .toString();
                                        }
                                        hashMap.put(
                                                "routines_ringtone_uri",
                                                new ParameterValues.ParameterValue(
                                                        ringtoneActionActivity.mRingtoneUri
                                                                .toString()));
                                        hashMap.put(
                                                "routines_ringtone_path",
                                                new ParameterValues.ParameterValue(
                                                        ringtoneActionActivity.mAbsolutePath));
                                        hashMap.put(
                                                "routines_ringtone_title",
                                                new ParameterValues.ParameterValue(
                                                        ringtoneActionActivity.mTitle.toString()));
                                    } else {
                                        hashMap.put(
                                                "routines_ringtone_title",
                                                new ParameterValues.ParameterValue("silent"));
                                    }
                                    Intent intent = new Intent();
                                    HashMap hashMap2 = new HashMap();
                                    hashMap.entrySet()
                                            .forEach(
                                                    new ParameterValues$$ExternalSyntheticLambda0(
                                                            hashMap2));
                                    intent.putExtra(
                                            "intent_params", new JSONObject(hashMap2).toString());
                                    ringtoneActionActivity.setResult(-1, intent);
                                }
                                ringtoneActionActivity.finish();
                            }
                        });
        Intent intent = getIntent();
        Intent intent2 = new Intent("android.intent.action.RINGTONE_PICKER");
        intent2.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", false);
        intent2.putExtra("android.intent.extra.ringtone.SHOW_SILENT", true);
        intent2.putExtra("android.samsung.intent.extra.ringtone.SELECT_AND_SAVE", true);
        Bundle extras = intent.getExtras();
        ParameterValues parameterValues = new ParameterValues();
        if (extras != null) {
            parameterValues =
                    ParameterValues.fromJsonString(
                            extras.getString(ExtraKey.PARAMETER_VALUES.a, ApnSettings.MVNO_NONE));
        }
        String string = parameterValues.getString("routines_ringtone_uri", ApnSettings.MVNO_NONE);
        String string2 = parameterValues.getString("routines_ringtone_title", null);
        if (TextUtils.isEmpty(string) && TextUtils.isEmpty(string2)) {
            Uri actualDefaultRingtoneUri =
                    RingtoneManager.getActualDefaultRingtoneUri(
                            this.mContext,
                            (SimUtils.isMultiSimModel() && SimUtils.isEnabledSIM2Only()) ? 128 : 1);
            if (actualDefaultRingtoneUri != null) {
                string = actualDefaultRingtoneUri.toString();
            }
        }
        if (!TextUtils.isEmpty(string)) {
            intent2.putExtra("android.intent.extra.ringtone.EXISTING_URI", Uri.parse(string));
        }
        intent2.putExtra("android.intent.extra.ringtone.TYPE", 1);
        intent2.putExtra(
                "android.intent.extra.ringtone.TITLE",
                this.mContext.getString(R.string.incoming_call_volume_title));
        intent2.putExtra("android.samsung.intent.extra.ringtone.SHOW_VOLUME_SEEKBAR", false);
        intent2.putExtra(ImIntent.Extras.EXTRA_FROM, "com.samsung.android.sdk.routines.v3");
        intent2.setComponent(
                new ComponentName(
                        "com.samsung.android.secsoundpicker",
                        "com.samsung.android.secsoundpicker.SecSoundPickerActivity"));
        registerForActivityResult.launch(intent2);
    }
}
