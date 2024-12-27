package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.base.zaq;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@KeepName
/* loaded from: classes3.dex */
public class GoogleApiActivity extends Activity implements DialogInterface.OnCancelListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    protected int zaa = 0;

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            boolean booleanExtra = getIntent().getBooleanExtra("notify_manager", true);
            this.zaa = 0;
            setResult(i2, intent);
            if (booleanExtra) {
                GoogleApiManager zam = GoogleApiManager.zam(this);
                if (i2 == -1) {
                    zaq zaqVar = zam.zat;
                    zaqVar.sendMessage(zaqVar.obtainMessage(3));
                } else if (i2 == 0) {
                    zam.zaz(
                            new ConnectionResult(13, null),
                            getIntent().getIntExtra("failing_client_id", -1));
                }
            }
        } else if (i == 2) {
            this.zaa = 0;
            setResult(i2, intent);
        }
        finish();
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        this.zaa = 0;
        setResult(0);
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zaa = bundle.getInt("resolution");
        }
        if (this.zaa != 1) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Log.e("GoogleApiActivity", "Activity started without extras");
                finish();
                return;
            }
            PendingIntent pendingIntent = (PendingIntent) extras.get("pending_intent");
            Integer num = (Integer) extras.get("error_code");
            if (pendingIntent == null && num == null) {
                Log.e("GoogleApiActivity", "Activity started without resolution");
                finish();
                return;
            }
            if (pendingIntent == null) {
                Preconditions.checkNotNull(num);
                GoogleApiAvailability.zab.showErrorDialogFragment(this, num.intValue(), this);
                this.zaa = 1;
                return;
            }
            try {
                startIntentSenderForResult(pendingIntent.getIntentSender(), 1, null, 0, 0, 0);
                this.zaa = 1;
            } catch (ActivityNotFoundException e) {
                if (extras.getBoolean("notify_manager", true)) {
                    GoogleApiManager.zam(this)
                            .zaz(
                                    new ConnectionResult(22, null),
                                    getIntent().getIntExtra("failing_client_id", -1));
                } else {
                    String obj = pendingIntent.toString();
                    String m =
                            BackStackRecord$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(obj.length() + 36),
                                    "Activity not found while launching ",
                                    obj,
                                    ".");
                    if (Build.FINGERPRINT.contains("generic")) {
                        m =
                                m.concat(
                                        " This may occur when resolving Google Play services"
                                            + " connection issues on emulators with Google APIs but"
                                            + " not Google Play Store.");
                    }
                    Log.e("GoogleApiActivity", m, e);
                }
                this.zaa = 1;
                finish();
            } catch (IntentSender.SendIntentException e2) {
                Log.e("GoogleApiActivity", "Failed to launch pendingIntent", e2);
                finish();
            }
        }
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("resolution", this.zaa);
        super.onSaveInstanceState(bundle);
    }
}
