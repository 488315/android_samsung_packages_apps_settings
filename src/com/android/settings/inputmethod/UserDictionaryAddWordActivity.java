package com.android.settings.inputmethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UserDictionaryAddWordActivity extends Activity {
    public UserDictionaryAddWordContents mContents;

    public void onClickCancel(View view) {
        reportBackToCaller(1, null);
        finish();
    }

    public void onClickConfirm(View view) {
        Bundle bundle = new Bundle();
        reportBackToCaller(this.mContents.apply(this, bundle), bundle);
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        setContentView(R.layout.user_dictionary_add_word);
        Intent intent = getIntent();
        String action = intent.getAction();
        if ("com.android.settings.USER_DICTIONARY_EDIT".equals(action)) {
            i = 0;
        } else {
            if (!"com.android.settings.USER_DICTIONARY_INSERT".equals(action)) {
                throw new RuntimeException(
                        AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "Unsupported action: ", action));
            }
            i = 1;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putInt("mode", i);
        if (bundle != null) {
            extras.putAll(bundle);
        }
        this.mContents = new UserDictionaryAddWordContents(getWindow().getDecorView(), extras);
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        this.mContents.saveStateIntoBundle(bundle);
    }

    public final void reportBackToCaller(int i, Bundle bundle) {
        Intent intent = getIntent();
        if (intent.getExtras() == null) {
            return;
        }
        Object obj = intent.getExtras().get("listener");
        if (obj instanceof Messenger) {
            Messenger messenger = (Messenger) obj;
            Message obtain = Message.obtain();
            obtain.obj = bundle;
            obtain.what = i;
            try {
                messenger.send(obtain);
            } catch (RemoteException unused) {
            }
        }
    }
}
