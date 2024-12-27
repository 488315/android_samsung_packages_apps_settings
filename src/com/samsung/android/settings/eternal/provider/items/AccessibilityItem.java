package com.samsung.android.settings.eternal.provider.items;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AccessibilityItem implements Recoverable {
    public final Context mContext;
    public String mPreviousEngine;
    public TextToSpeech mTts;
    public final AnonymousClass1 mUpdateListener =
            new TextToSpeech
                    .OnInitListener() { // from class:
                                        // com.samsung.android.settings.eternal.provider.items.AccessibilityItem.1
                @Override // android.speech.tts.TextToSpeech.OnInitListener
                public final void onInit(int i) {
                    AccessibilityItem accessibilityItem = AccessibilityItem.this;
                    if (i == 0) {
                        if (accessibilityItem.mTts != null) {
                            Log.d(
                                    "AccessibilityItem",
                                    "Updating engine: Successfully bound to the engine: "
                                            + accessibilityItem.mTts.getCurrentEngine());
                            return;
                        }
                        return;
                    }
                    accessibilityItem.getClass();
                    Log.d(
                            "AccessibilityItem",
                            "Updating engine: Failed to bind to engine, reverting.");
                    if (accessibilityItem.mPreviousEngine != null) {
                        accessibilityItem.mTts =
                                new TextToSpeech(
                                        accessibilityItem.mContext.getApplicationContext(),
                                        null,
                                        accessibilityItem.mPreviousEngine);
                    }
                    accessibilityItem.mPreviousEngine = null;
                }
            };

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settings.eternal.provider.items.AccessibilityItem$1] */
    public AccessibilityItem(Context context) {
        this.mTts = null;
        this.mTts = new TextToSpeech(context.getApplicationContext(), null);
        this.mContext = context;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0074 A[Catch: Exception -> 0x002f, TRY_LEAVE, TryCatch #0 {Exception -> 0x002f, blocks: (B:3:0x000f, B:15:0x004e, B:18:0x0056, B:19:0x0065, B:20:0x0074, B:21:0x0025, B:24:0x0031, B:27:0x003b), top: B:2:0x000f }] */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.Scene.Builder getValue(
            android.content.Context r7,
            com.samsung.android.lib.episode.SourceInfo r8,
            java.lang.String r9) {
        /*
            r6 = this;
            java.lang.String r6 = "AccessibilityItem"
            java.lang.String r8 = "unknown key : "
            com.samsung.android.lib.episode.Scene$Builder r0 = new com.samsung.android.lib.episode.Scene$Builder
            r0.<init>(r9)
            android.content.ContentResolver r7 = r7.getContentResolver()
            r1 = 0
            int r2 = r9.hashCode()     // Catch: java.lang.Exception -> L2f
            r3 = -279699742(0xffffffffef541ee2, float:-6.5648157E28)
            r4 = 2
            r5 = 1
            if (r2 == r3) goto L3b
            r3 = 1436714821(0x55a28745, float:2.23377327E13)
            if (r2 == r3) goto L31
            r3 = 1792144638(0x6ad1f4fe, float:1.2691122E26)
            if (r2 == r3) goto L25
            goto L45
        L25:
            java.lang.String r2 = "/Settings/Accessibility/Rate"
            boolean r2 = r9.equals(r2)     // Catch: java.lang.Exception -> L2f
            if (r2 == 0) goto L45
            r2 = r5
            goto L46
        L2f:
            r7 = move-exception
            goto L7f
        L31:
            java.lang.String r2 = "/Settings/Accessibility/PreferredEngine"
            boolean r2 = r9.equals(r2)     // Catch: java.lang.Exception -> L2f
            if (r2 == 0) goto L45
            r2 = r1
            goto L46
        L3b:
            java.lang.String r2 = "/Settings/Accessibility/Pitch"
            boolean r2 = r9.equals(r2)     // Catch: java.lang.Exception -> L2f
            if (r2 == 0) goto L45
            r2 = r4
            goto L46
        L45:
            r2 = -1
        L46:
            if (r2 == 0) goto L74
            r3 = 100
            if (r2 == r5) goto L65
            if (r2 == r4) goto L56
            java.lang.String r7 = r8.concat(r9)     // Catch: java.lang.Exception -> L2f
            android.util.Log.d(r6, r7)     // Catch: java.lang.Exception -> L2f
            goto L8c
        L56:
            java.lang.String r8 = "tts_default_pitch"
            int r7 = android.provider.Settings.Secure.getInt(r7, r8, r3)     // Catch: java.lang.Exception -> L2f
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Exception -> L2f
            r0.setValue(r7, r1)     // Catch: java.lang.Exception -> L2f
            goto L8c
        L65:
            java.lang.String r8 = "tts_default_rate"
            int r7 = android.provider.Settings.Secure.getInt(r7, r8, r3)     // Catch: java.lang.Exception -> L2f
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Exception -> L2f
            r0.setValue(r7, r1)     // Catch: java.lang.Exception -> L2f
            goto L8c
        L74:
            java.lang.String r8 = "tts_default_synth"
            java.lang.String r7 = android.provider.Settings.Secure.getString(r7, r8)     // Catch: java.lang.Exception -> L2f
            r0.setValue(r7, r1)     // Catch: java.lang.Exception -> L2f
            goto L8c
        L7f:
            java.lang.StackTraceElement[] r7 = r7.getStackTrace()
            r7 = r7[r1]
            java.lang.String r7 = r7.toString()
            android.util.Log.e(r6, r7)
        L8c:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.AccessibilityItem.getValue(android.content.Context,"
                    + " com.samsung.android.lib.episode.SourceInfo,"
                    + " java.lang.String):com.samsung.android.lib.episode.Scene$Builder");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007d A[Catch: Exception -> 0x0033, TryCatch #0 {Exception -> 0x0033, blocks: (B:3:0x0013, B:19:0x0053, B:22:0x0061, B:24:0x0068, B:27:0x0076, B:29:0x007d, B:31:0x0085, B:33:0x008b, B:34:0x0029, B:37:0x0035, B:40:0x003f), top: B:2:0x0013 }] */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.SceneResult setValue(
            android.content.Context r7,
            java.lang.String r8,
            com.samsung.android.lib.episode.Scene r9,
            com.samsung.android.lib.episode.SourceInfo r10) {
        /*
            r6 = this;
            android.content.ContentResolver r10 = r7.getContentResolver()
            r0 = 0
            r1 = 0
            java.lang.String r9 = r9.getValue(r0, r1)
            com.samsung.android.lib.episode.SceneResult$Builder r0 = new com.samsung.android.lib.episode.SceneResult$Builder
            r0.<init>(r8)
            com.samsung.android.lib.episode.SceneResult$ResultType r2 = com.samsung.android.lib.episode.SceneResult.ResultType.RESULT_OK
            r0.mResultType = r2
            int r2 = r8.hashCode()     // Catch: java.lang.Exception -> L33
            r3 = -279699742(0xffffffffef541ee2, float:-6.5648157E28)
            r4 = 2
            r5 = 1
            if (r2 == r3) goto L3f
            r3 = 1436714821(0x55a28745, float:2.23377327E13)
            if (r2 == r3) goto L35
            r3 = 1792144638(0x6ad1f4fe, float:1.2691122E26)
            if (r2 == r3) goto L29
            goto L49
        L29:
            java.lang.String r2 = "/Settings/Accessibility/Rate"
            boolean r8 = r8.equals(r2)     // Catch: java.lang.Exception -> L33
            if (r8 == 0) goto L49
            r8 = r5
            goto L4a
        L33:
            r6 = move-exception
            goto L8f
        L35:
            java.lang.String r2 = "/Settings/Accessibility/PreferredEngine"
            boolean r8 = r8.equals(r2)     // Catch: java.lang.Exception -> L33
            if (r8 == 0) goto L49
            r8 = r1
            goto L4a
        L3f:
            java.lang.String r2 = "/Settings/Accessibility/Pitch"
            boolean r8 = r8.equals(r2)     // Catch: java.lang.Exception -> L33
            if (r8 == 0) goto L49
            r8 = r4
            goto L4a
        L49:
            r8 = -1
        L4a:
            if (r8 == 0) goto L7d
            r6 = 100
            if (r8 == r5) goto L68
            if (r8 == r4) goto L53
            goto L9e
        L53:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Exception -> L33
            int r7 = r7.intValue()     // Catch: java.lang.Exception -> L33
            r8 = 25
            if (r7 >= r8) goto L60
            goto L61
        L60:
            r6 = r7
        L61:
            java.lang.String r7 = "tts_default_pitch"
            android.provider.Settings.Secure.putInt(r10, r7, r6)     // Catch: java.lang.Exception -> L33
            goto L9e
        L68:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Exception -> L33
            int r7 = r7.intValue()     // Catch: java.lang.Exception -> L33
            r8 = 10
            if (r7 >= r8) goto L75
            goto L76
        L75:
            r6 = r7
        L76:
            java.lang.String r7 = "tts_default_rate"
            android.provider.Settings.Secure.putInt(r10, r7, r6)     // Catch: java.lang.Exception -> L33
            goto L9e
        L7d:
            java.lang.String r8 = "tts_default_synth"
            android.provider.Settings.Secure.putString(r10, r8, r9)     // Catch: java.lang.Exception -> L33
            if (r9 == 0) goto L9e
            boolean r8 = r9.isEmpty()     // Catch: java.lang.Exception -> L33
            if (r8 != 0) goto L9e
            r6.updateDefaultEngine(r7, r9)     // Catch: java.lang.Exception -> L33
            goto L9e
        L8f:
            java.lang.StackTraceElement[] r6 = r6.getStackTrace()
            r6 = r6[r1]
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = "AccessibilityItem"
            android.util.Log.e(r7, r6)
        L9e:
            com.samsung.android.lib.episode.SceneResult r6 = r0.build()
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.AccessibilityItem.setValue(android.content.Context,"
                    + " java.lang.String, com.samsung.android.lib.episode.Scene,"
                    + " com.samsung.android.lib.episode.SourceInfo):com.samsung.android.lib.episode.SceneResult");
    }

    public final synchronized void updateDefaultEngine(Context context, String str) {
        Log.i("AccessibilityItem", "Shutting down current tts engine");
        TextToSpeech textToSpeech = this.mTts;
        if (textToSpeech != null) {
            this.mPreviousEngine = textToSpeech.getCurrentEngine();
            try {
                this.mTts.shutdown();
                this.mTts = null;
            } catch (Exception e) {
                Log.e("AccessibilityItem", "Error shutting down TTS engine" + e);
            }
        }
        Log.i(
                "AccessibilityItem",
                "Updating engine : Attempting to connect to engine: ".concat(str));
        this.mTts = new TextToSpeech(context.getApplicationContext(), this.mUpdateListener, str);
        Log.i("AccessibilityItem", "Success");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
