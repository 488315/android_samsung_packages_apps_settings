package com.android.settings.inputmethod;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.textservice.SpellCheckerInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.CustomListPreference;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SpellCheckerPreference extends CustomListPreference {
    Intent mIntent;
    public final SpellCheckerInfo[] mScis;

    public SpellCheckerPreference(Context context, SpellCheckerInfo[] spellCheckerInfoArr) {
        super(context, null);
        this.mScis = spellCheckerInfoArr;
        setLayoutResource(R.layout.preference_two_target);
        setWidgetLayoutResource(R.layout.sec_widget_preference_gear);
        if (spellCheckerInfoArr == null) {
            return;
        }
        CharSequence[] charSequenceArr = new CharSequence[spellCheckerInfoArr.length];
        CharSequence[] charSequenceArr2 = new CharSequence[spellCheckerInfoArr.length];
        for (int i = 0; i < spellCheckerInfoArr.length; i++) {
            charSequenceArr[i] = spellCheckerInfoArr[i].loadLabel(context.getPackageManager());
            charSequenceArr2[i] = String.valueOf(i);
        }
        this.mEntries = charSequenceArr;
        this.mEntryValues = charSequenceArr2;
    }

    @Override // androidx.preference.Preference
    public final boolean callChangeListener(Object obj) {
        return super.callChangeListener(
                obj != null ? this.mScis[Integer.parseInt((String) obj)] : null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.two_target_divider);
        View findViewById2 = preferenceViewHolder.findViewById(android.R.id.widget_frame);
        if (findViewById != null) {
            findViewById.setVisibility(this.mIntent != null ? 0 : 8);
        }
        if (findViewById2 != null) {
            findViewById2.setVisibility(this.mIntent != null ? 0 : 8);
        }
        View findViewById3 = preferenceViewHolder.findViewById(R.id.settings_button);
        if (findViewById3 != null) {
            findViewById3.setVisibility(this.mIntent == null ? 4 : 0);
            findViewById3.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.inputmethod.SpellCheckerPreference.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SpellCheckerPreference spellCheckerPreference =
                                    SpellCheckerPreference.this;
                            Context context = spellCheckerPreference.getContext();
                            try {
                                Intent intent = spellCheckerPreference.mIntent;
                                if (intent != null) {
                                    context.startActivity(intent);
                                }
                            } catch (ActivityNotFoundException unused) {
                            }
                        }
                    });
        }
    }

    @Override // com.android.settings.CustomListPreference
    public final void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        builder.setTitle(R.string.choose_spell_checker);
        builder.setSingleChoiceItems(this.mEntries, findIndexOfValue(this.mValue), onClickListener);
    }

    @Override // androidx.preference.ListPreference
    public final void setValue(String str) {
        super.setValue(str);
        int parseInt = str != null ? Integer.parseInt(str) : -1;
        if (parseInt == -1) {
            this.mIntent = null;
            return;
        }
        SpellCheckerInfo spellCheckerInfo = this.mScis[parseInt];
        String settingsActivity = spellCheckerInfo.getSettingsActivity();
        if (TextUtils.isEmpty(settingsActivity)) {
            this.mIntent = null;
            return;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        this.mIntent = intent;
        intent.setClassName(spellCheckerInfo.getPackageName(), settingsActivity);
    }
}
