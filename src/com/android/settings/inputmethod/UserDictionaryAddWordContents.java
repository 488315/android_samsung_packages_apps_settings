package com.android.settings.inputmethod;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserDictionaryAddWordContents {
    public static final String[] HAS_WORD_PROJECTION = {"word"};
    public final String mLocale;
    public final int mMode;
    public final String mOldShortcut;
    public final String mOldWord;
    public String mSavedShortcut;
    public String mSavedWord;
    public final EditText mShortcutEditText;
    public final EditText mWordEditText;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LocaleRenderer {
        public final String mDescription;

        public LocaleRenderer(FragmentActivity fragmentActivity, String str) {
            if (str == null) {
                this.mDescription =
                        fragmentActivity.getString(R.string.user_dict_settings_more_languages);
            } else if (ApnSettings.MVNO_NONE.equals(str)) {
                this.mDescription =
                        fragmentActivity.getString(R.string.user_dict_settings_all_languages);
            } else {
                this.mDescription = Utils.createLocaleFromString(str).getDisplayName();
            }
        }

        public final String toString() {
            return this.mDescription;
        }
    }

    public UserDictionaryAddWordContents(View view, Bundle bundle) {
        EditText editText = (EditText) view.findViewById(R.id.user_dictionary_add_word_text);
        this.mWordEditText = editText;
        EditText editText2 = (EditText) view.findViewById(R.id.user_dictionary_add_shortcut);
        this.mShortcutEditText = editText2;
        String string = bundle.getString("word");
        if (string != null) {
            editText.setText(string);
            editText.setSelection(editText.getText().length());
        }
        String string2 = bundle.getString("shortcut");
        if (string2 != null && editText2 != null) {
            editText2.setText(string2);
        }
        this.mMode = bundle.getInt("mode");
        this.mOldWord = bundle.getString("word");
        this.mOldShortcut = bundle.getString("shortcut");
        String string3 = bundle.getString(SpeechRecognitionConst.Key.LOCALE);
        this.mLocale = string3 == null ? Locale.getDefault().toString() : string3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0037, code lost:

       if (android.text.TextUtils.isEmpty(r1) != false) goto L11;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int apply(android.content.Context r13, android.os.Bundle r14) {
        /*
            r12 = this;
            if (r14 == 0) goto L5
            r12.saveStateIntoBundle(r14)
        L5:
            android.content.ContentResolver r14 = r13.getContentResolver()
            int r0 = r12.mMode
            if (r0 != 0) goto L1a
            java.lang.String r0 = r12.mOldWord
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L1a
            java.lang.String r1 = r12.mOldShortcut
            com.android.settings.inputmethod.UserDictionarySettings.deleteWord(r14, r0, r1)
        L1a:
            android.widget.EditText r0 = r12.mWordEditText
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            android.widget.EditText r1 = r12.mShortcutEditText
            r2 = 0
            if (r1 != 0) goto L2b
        L29:
            r1 = r2
            goto L3a
        L2b:
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 == 0) goto L3a
            goto L29
        L3a:
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            r4 = 1
            if (r3 == 0) goto L42
            return r4
        L42:
            r12.mSavedWord = r0
            r12.mSavedShortcut = r1
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            r5 = 0
            if (r3 == 0) goto L9b
            java.lang.String r3 = ""
            java.lang.String r6 = r12.mLocale
            boolean r3 = r3.equals(r6)
            java.lang.String[] r8 = com.android.settings.inputmethod.UserDictionaryAddWordContents.HAS_WORD_PROJECTION
            if (r3 == 0) goto L6b
            android.content.ContentResolver r6 = r13.getContentResolver()
            android.net.Uri r7 = android.provider.UserDictionary.Words.CONTENT_URI
            java.lang.String[] r10 = new java.lang.String[]{r0}
            r11 = 0
            java.lang.String r9 = "word=? AND locale is null"
            android.database.Cursor r3 = r6.query(r7, r8, r9, r10, r11)
            goto L7e
        L6b:
            android.content.ContentResolver r6 = r13.getContentResolver()
            android.net.Uri r7 = android.provider.UserDictionary.Words.CONTENT_URI
            java.lang.String r3 = r12.mLocale
            java.lang.String[] r10 = new java.lang.String[]{r0, r3}
            r11 = 0
            java.lang.String r9 = "word=? AND locale=?"
            android.database.Cursor r3 = r6.query(r7, r8, r9, r10, r11)
        L7e:
            if (r3 != 0) goto L87
            if (r3 == 0) goto L85
            r3.close()
        L85:
            r4 = r5
            goto L92
        L87:
            int r6 = r3.getCount()     // Catch: java.lang.Throwable -> L96
            if (r6 <= 0) goto L8e
            goto L8f
        L8e:
            r4 = r5
        L8f:
            r3.close()
        L92:
            if (r4 == 0) goto L9b
            r12 = 2
            return r12
        L96:
            r12 = move-exception
            r3.close()
            throw r12
        L9b:
            com.android.settings.inputmethod.UserDictionarySettings.deleteWord(r14, r0, r2)
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto La7
            com.android.settings.inputmethod.UserDictionarySettings.deleteWord(r14, r0, r1)
        La7:
            java.lang.String r14 = r0.toString()
            java.lang.String r0 = r12.mLocale
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto Lb4
            goto Lba
        Lb4:
            java.lang.String r12 = r12.mLocale
            java.util.Locale r2 = com.android.settings.Utils.createLocaleFromString(r12)
        Lba:
            r12 = 250(0xfa, float:3.5E-43)
            android.provider.UserDictionary.Words.addWord(r13, r14, r12, r1, r2)
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.inputmethod.UserDictionaryAddWordContents.apply(android.content.Context,"
                    + " android.os.Bundle):int");
    }

    public final void saveStateIntoBundle(Bundle bundle) {
        bundle.putString("word", this.mWordEditText.getText().toString());
        bundle.putString("originalWord", this.mOldWord);
        EditText editText = this.mShortcutEditText;
        if (editText != null) {
            bundle.putString("shortcut", editText.getText().toString());
        }
        String str = this.mOldShortcut;
        if (str != null) {
            bundle.putString("originalShortcut", str);
        }
        bundle.putString(SpeechRecognitionConst.Key.LOCALE, this.mLocale);
    }

    public UserDictionaryAddWordContents(
            View view, UserDictionaryAddWordContents userDictionaryAddWordContents) {
        this.mWordEditText = (EditText) view.findViewById(R.id.user_dictionary_add_word_text);
        this.mShortcutEditText = (EditText) view.findViewById(R.id.user_dictionary_add_shortcut);
        this.mMode = 0;
        this.mOldWord = userDictionaryAddWordContents.mSavedWord;
        this.mOldShortcut = userDictionaryAddWordContents.mSavedShortcut;
        String str = userDictionaryAddWordContents.mLocale;
        this.mLocale = str == null ? Locale.getDefault().toString() : str;
    }
}
