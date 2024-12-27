package com.samsung.android.settings.theftprotection.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.settings.AirplaneModeEnabler;
import com.android.settings.R;
import com.android.settings.location.AppSettingsInjector;
import com.android.settings.location.LocationEnabler;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.theftprotection.logging.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NecessaryElementChecker {
    public final Context mContext;
    public Runnable mDoFail;
    public Runnable mDoSuccess;

    public NecessaryElementChecker(Context context) {
        this.mContext = context;
    }

    public final void processNecessaryElements(Sequence sequence) {
        String string;
        String string2;
        String string3;
        String string4;
        Log.d("NecessaryElementChecker", "processNecessaryElements : " + sequence.ordinal());
        for (Sequence sequence2 : Sequence.values()) {
            if (sequence.ordinal() <= sequence2.ordinal() && sequence2.check(this.mContext)) {
                String string5 = this.mContext.getString(R.string.turn_on_button);
                int ordinal = sequence2.ordinal();
                if (ordinal != 1) {
                    if (ordinal == 2) {
                        string =
                                this.mContext.getString(
                                        R.string
                                                .mandatory_biometric_onboarding_Inactivate_airplane_mode_dialog_title);
                        string3 =
                                this.mContext.getString(
                                        R.string
                                                .mandatory_biometric_onboarding_Inactivate_airplane_mode_dialog_message);
                        string4 = this.mContext.getString(R.string.turn_off_button);
                    } else if (ordinal != 3) {
                        string = ApnSettings.MVNO_NONE;
                        string2 = ApnSettings.MVNO_NONE;
                    } else {
                        string =
                                this.mContext.getString(
                                        R.string
                                                .mandatory_biometric_onboarding_activate_location_accuracy_dialog_title);
                        string3 =
                                this.mContext.getString(
                                        R.string
                                                .mandatory_biometric_onboarding_activate_location_accuracy_dialog_message);
                        string4 =
                                this.mContext.getString(
                                        R.string
                                                .mandatory_biometric_activate_go_to_settings_button_text);
                    }
                    String str = string4;
                    string2 = string3;
                    string5 = str;
                } else {
                    string =
                            this.mContext.getString(
                                    R.string
                                            .mandatory_biometric_onboarding_activate_location_dialog_title);
                    string2 =
                            this.mContext.getString(
                                    R.string
                                            .mandatory_biometric_onboarding_activate_location_dialog_message);
                }
                Sequence[] values = Sequence.values();
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                AlertController.AlertParams alertParams = builder.P;
                alertParams.mTitle = string;
                alertParams.mMessage = string2;
                builder.setPositiveButton(
                        string5,
                        new NecessaryElementChecker$$ExternalSyntheticLambda0(
                                this, sequence2, values));
                builder.setNegativeButton(
                        android.R.string.cancel,
                        new NecessaryElementChecker$$ExternalSyntheticLambda0(
                                this, values, sequence2));
                builder.create().show();
                return;
            }
        }
        if (Sequence.OFF_LOCATION.check(this.mContext)
                || Sequence.OFF_LOCATION_ACCURACY.check(this.mContext)
                || Sequence.ON_AIRPLANE_MODE.check(this.mContext)) {
            Runnable runnable = this.mDoFail;
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        Runnable runnable2 = this.mDoSuccess;
        if (runnable2 != null) {
            runnable2.run();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class Sequence {
        public static final /* synthetic */ Sequence[] $VALUES;
        public static final Sequence OFF_LOCATION;
        public static final Sequence OFF_LOCATION_ACCURACY;
        public static final Sequence ON_AIRPLANE_MODE;
        public static final Sequence START_CHECKING;

        static {
            Sequence sequence = new Sequence("START_CHECKING", 0);
            START_CHECKING = sequence;
            Sequence sequence2 =
                    new Sequence() { // from class:
                                     // com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker.Sequence.1
                        @Override // com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker.Sequence
                        public final boolean check(Context context) {
                            return Settings.Secure.getInt(
                                            context.getContentResolver(), "location_mode", 0)
                                    == 0;
                        }

                        @Override // com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker.Sequence
                        public final void resolveCondition(Context context) {
                            new LocationEnabler(context).setLocationEnabled(true);
                        }
                    };
            OFF_LOCATION = sequence2;
            Sequence sequence3 =
                    new Sequence() { // from class:
                                     // com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker.Sequence.2
                        @Override // com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker.Sequence
                        public final boolean check(Context context) {
                            return Settings.Global.getInt(
                                            context.getContentResolver(), "airplane_mode_on", 0)
                                    == 1;
                        }

                        @Override // com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker.Sequence
                        public final void resolveCondition(Context context) {
                            new AirplaneModeEnabler(context, null).setAirplaneMode(false);
                        }
                    };
            ON_AIRPLANE_MODE = sequence3;
            Sequence sequence4 = new Sequence() { // from class:
                        // com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker.Sequence.3
                        @Override // com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker.Sequence
                        public final boolean check(Context context) {
                            return AppSettingsInjector.supportGoogleLocationAccuracyService()
                                    && Settings.Secure.getInt(
                                                    context.getContentResolver(),
                                                    "assisted_gps_enabled",
                                                    0)
                                            == 0;
                        }

                        @Override // com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker.Sequence
                        public final void resolveCondition(Context context) {
                            Intent intent =
                                    new Intent(
                                            "com.google.android.gms.location.settings.LOCATION_ACCURACY");
                            intent.setClassName(
                                    "com.google.android.gms",
                                    "com.google.android.gms.location.settings.LocationAccuracyV31Activity");
                            try {
                                context.startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Log.e(
                                        "NecessaryElementChecker",
                                        "ActivityNotFoundException: " + e.getMessage());
                            }
                        }
                    };
            OFF_LOCATION_ACCURACY = sequence4;
            $VALUES =
                    new Sequence[] {
                        sequence, sequence2, sequence3, sequence4, new Sequence("END_CHECKING", 4)
                    };
        }

        public static Sequence valueOf(String str) {
            return (Sequence) Enum.valueOf(Sequence.class, str);
        }

        public static Sequence[] values() {
            return (Sequence[]) $VALUES.clone();
        }

        public boolean check(Context context) {
            return false;
        }

        public void resolveCondition(Context context) {}
    }
}
