package com.samsung.android.sdk.scs.ai.asr;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.android.settings.homepage.contextualcards.ContextualCardManager$$ExternalSyntheticLambda3;
import com.samsung.android.sdk.scs.ai.asr.Repository;
import com.samsung.android.sdk.scs.base.feature.Feature;
import com.samsung.android.sivs.ai.sdkcommon.asr.ServerFeature;
import com.samsung.android.sivs.ai.sdkcommon.asr.ServerType;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;
import java.time.Duration;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Environment {
    public static final ExpiringData langpackConfig;

    static {
        new ExpiringData(SpeechRecognitionConst.Key.LOCALE, new ContextualCardManager$$ExternalSyntheticLambda3()).checkValidate = new Environment$$ExternalSyntheticLambda0(0);
        new ExpiringData("btc_locale", new ContextualCardManager$$ExternalSyntheticLambda3()).checkValidate = new Environment$$ExternalSyntheticLambda0(1);
        new ExpiringData("asrServerInfo", new ContextualCardManager$$ExternalSyntheticLambda3()).checkValidate = new Environment$$ExternalSyntheticLambda0(2);
        Environment$$ExternalSyntheticLambda3 environment$$ExternalSyntheticLambda3 = new Environment$$ExternalSyntheticLambda3();
        Environment$$ExternalSyntheticLambda0 environment$$ExternalSyntheticLambda0 = new Environment$$ExternalSyntheticLambda0(3);
        ExpiringData expiringData = new ExpiringData("langpackConfig", environment$$ExternalSyntheticLambda3);
        expiringData.checkValidate = environment$$ExternalSyntheticLambda0;
        langpackConfig = expiringData;
    }

    public static Bundle callContentProvider(Context context, final String str, final Bundle bundle) {
        String str2;
        try {
            Log.d(com.samsung.android.sdk.scs.base.utils.Log.concatPrefixTag("Environment"), "Call cp ".concat(str));
            if (((Integer) Optional.ofNullable(context).map(new Environment$$ExternalSyntheticLambda0(5)).orElse(-1)).intValue() == 0) {
                str2 = "content://com.samsung.android.intellivoiceservice.ai.speech2";
            } else {
                Log.d(com.samsung.android.sdk.scs.base.utils.Log.concatPrefixTag("Environment"), "System permission doesn't have granted.");
                str2 = "content://com.samsung.android.intellivoiceservice.ai.speech";
            }
            final Uri parse = Uri.parse(str2);
            return (Bundle) Optional.ofNullable(context).map(new Environment$$ExternalSyntheticLambda0(4)).map(new Function() { // from class: com.samsung.android.sdk.scs.ai.asr.Environment$$ExternalSyntheticLambda7
                public final /* synthetic */ String f$2 = null;

                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((ContentResolver) obj).call(parse, str, this.f$2, bundle);
                }
            }).orElse(Bundle.EMPTY);
        } catch (Exception e) {
            Log.e(com.samsung.android.sdk.scs.base.utils.Log.concatPrefixTag("Environment"), "Failed to call cp ".concat(str), e);
            return Bundle.EMPTY;
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.sdk.scs.ai.asr.Environment$$ExternalSyntheticLambda5] */
    public static String getLangpackConfigInfo(final Context context) {
        try {
            if (Feature.checkFeature(context) != 0) {
                return null;
            }
            final ExpiringData expiringData = langpackConfig;
            final ?? r1 = new Supplier() { // from class: com.samsung.android.sdk.scs.ai.asr.Environment$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    Context context2 = context;
                    try {
                        Bundle bundle = new Bundle();
                        Repository.SharedPrefRepository sharedPrefRepository = Repository.settings;
                        final ServerFeature serverFeature = ServerFeature.LANGPACK_CONFIG;
                        sharedPrefRepository.getClass();
                        final String str = SpeechRecognitionConst.Key.SERVER_TYPE + serverFeature.ordinal();
                        bundle.putParcelable(SpeechRecognitionConst.Key.SERVER_TYPE, (ServerType) Optional.ofNullable(sharedPrefRepository.sharedPrefSupplier).map(new Function() { // from class: com.samsung.android.sdk.scs.ai.asr.Repository$SharedPrefRepository$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                final String str2 = str;
                                final ServerFeature serverFeature2 = serverFeature;
                                final SharedPreferences sharedPreferences = (SharedPreferences) obj;
                                return (ServerType) Optional.ofNullable(sharedPreferences.getString(str2, null)).map(new Function() { // from class: com.samsung.android.sdk.scs.ai.asr.Repository$SharedPrefRepository$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj2) {
                                        return new ServerType(ServerFeature.this, (String) obj2, sharedPreferences.getBoolean(str2 + "_is_default", false));
                                    }
                                }).orElse(null);
                            }
                        }).orElse(null));
                        return Environment.callContentProvider(context2, SpeechRecognitionConst.Method.GET_LANGPACK_CONFIG, bundle).getString(SpeechRecognitionConst.Key.LANGPACK_CONFIG_JSON);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
            return (String) Optional.ofNullable(expiringData.data.updateAndGet(new UnaryOperator() { // from class: com.samsung.android.sdk.scs.ai.asr.ExpiringData$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Object orElse;
                    ExpiringData expiringData2 = ExpiringData.this;
                    Supplier supplier = r1;
                    expiringData2.getClass();
                    long currentTimeMillis = System.currentTimeMillis() - expiringData2.lastTimeUpdate;
                    if ((obj != null && currentTimeMillis <= expiringData2.timeout.toMillis()) || (orElse = Optional.ofNullable(supplier).map(new ExpiringData$$ExternalSyntheticLambda3(1)).orElse(null)) == null || !((Boolean) expiringData2.checkValidate.apply(orElse)).booleanValue()) {
                        return obj;
                    }
                    expiringData2.lastTimeUpdate = System.currentTimeMillis();
                    StringBuilder sb = new StringBuilder();
                    sb.append(expiringData2.name);
                    sb.append(" has updated with ");
                    sb.append(Duration.ofMillis(currentTimeMillis));
                    sb.append(", ");
                    sb.append(obj == null ? "NEW" : "EXPIRED");
                    Log.i(com.samsung.android.sdk.scs.base.utils.Log.concatPrefixTag(expiringData2.TAG), sb.toString());
                    return orElse;
                }
            })).filter(new Predicate() { // from class: com.samsung.android.sdk.scs.ai.asr.ExpiringData$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    ExpiringData expiringData2 = ExpiringData.this;
                    com.samsung.android.sdk.scs.base.utils.Log.w(expiringData2.TAG, expiringData2.name + " return current value : " + Duration.ofMillis(System.currentTimeMillis() - expiringData2.lastTimeUpdate));
                    return true;
                }
            }).orElseGet(new Supplier() { // from class: com.samsung.android.sdk.scs.ai.asr.ExpiringData$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    ExpiringData expiringData2 = ExpiringData.this;
                    com.samsung.android.sdk.scs.base.utils.Log.w(expiringData2.TAG, expiringData2.name + " return default value : " + Duration.ofMillis(System.currentTimeMillis() - expiringData2.lastTimeUpdate));
                    return expiringData2.defaultSupplier.get();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
