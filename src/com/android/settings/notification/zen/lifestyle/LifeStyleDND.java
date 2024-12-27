package com.android.settings.notification.zen.lifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.SettingsActivity;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LifeStyleDND extends SettingsActivity {
    public BixbyRoutineActionHandler RSHandler;

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = super.getIntent();
        intent.putExtra("settings_homekey_mode", "mode_invisible");
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return LifeStyleZenFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.RSHandler = BixbyRoutineActionHandler.getInstance();
        ParameterValues.fromIntent(intent);
        if (intent.getStringExtra("source") != "settings") {
            ParameterValues fromIntent = ParameterValues.fromIntent(getIntent());
            float m =
                    LifeStyleDND$$ExternalSyntheticOutline0.m(
                            -1.0f, fromIntent, "messages_options");
            float m2 = LifeStyleDND$$ExternalSyntheticOutline0.m(-1.0f, fromIntent, "call_options");
            String string = fromIntent.getString("app_options", ApnSettings.MVNO_NONE);
            boolean booleanValue =
                    fromIntent.getBoolean("repeat_options", Boolean.FALSE).booleanValue();
            String string2 = fromIntent.getString("contact_options", ApnSettings.MVNO_NONE);
            float m3 =
                    LifeStyleDND$$ExternalSyntheticOutline0.m(0.0f, fromIntent, "appBypass_flag");
            float m4 =
                    LifeStyleDND$$ExternalSyntheticOutline0.m(
                            0.0f, fromIntent, "exception_contactflag");
            this.RSHandler.getClass();
            BixbyRoutineActionHandler.setMessageOption((int) m);
            BixbyRoutineActionHandler.setCallOption((int) m2);
            BixbyRoutineActionHandler.repeatedCaller = booleanValue;
            BixbyRoutineActionHandler.setAppFlag((int) m3);
            BixbyRoutineActionHandler.setContactFlag((int) m4);
            this.RSHandler.getClass();
            HashSet hashSet =
                    new HashSet(
                            Arrays.asList(BixbyRoutineActionHandler.setAppListMigration(string)));
            this.RSHandler.getClass();
            BixbyRoutineActionHandler.setApp_exist_list(hashSet);
            ArrayList arrayList = new ArrayList(Arrays.asList(string2));
            this.RSHandler.getClass();
            BixbyRoutineActionHandler.setContact_exist_list(arrayList);
            Log.d("LifeStyleDND", "onPerformAction: before_message_option " + m);
            Log.d("LifeStyleDND", "onPerformAction: before_call_option " + m2);
            Log.d("LifeStyleDND", "onPerformAction: before_repeat_option " + booleanValue);
            Log.d("LifeStyleDND", "onPerformAction: before_app_list ".concat(string));
            Log.d("LifeStyleDND", "onPerformAction: before_contact_list " + string2);
            Log.d("LifeStyleDND", "onPerformAction: before_app_flag " + m3);
            Log.d("LifeStyleDND", "onPerformAction: before_contact_flag " + m4);
        }
    }
}
