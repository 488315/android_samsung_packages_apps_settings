package com.samsung.android.settings.voiceinput;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

import com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker;
import com.samsung.android.util.SemLog;

import io.reactivex.ObservableEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class VoiceInputSettingsFragment$$ExternalSyntheticLambda3 {
    public final /* synthetic */ VoiceInputSettingsFragment f$0;

    public final void subscribe(final ObservableEmitter observableEmitter) {
        VoiceInputSettingsFragment voiceInputSettingsFragment = this.f$0;
        voiceInputSettingsFragment.getClass();
        SemLog.d("VoiceInputSettingsFragment", "emitted : " + observableEmitter);
        final Context context = voiceInputSettingsFragment.getContext();
        final boolean isLangPackRequestNeeded = VoiceInputUtils.isLangPackRequestNeeded(context);
        SemLog.i(
                "@VoiceIn: OfflinePackageChecker",
                "updateLanguagePackage::isLangPackRequestNeeded = " + isLangPackRequestNeeded);
        CompletableFuture.runAsync(
                new Runnable() { // from class:
                    // com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        final boolean z = isLangPackRequestNeeded;
                        final Context context2 = context;
                        final ObservableEmitter observableEmitter2 = observableEmitter;
                        List<Language> languageList = OnDeviceLanguageList.getLanguageList();
                        final ArrayList arrayList = new ArrayList();
                        for (Language language : languageList) {
                            final Locale locale = language.getLocale();
                            SemLog.i("@VoiceIn: OfflinePackageChecker", "locale " + locale);
                            final String langPackage = language.getLangPackage();
                            SemLog.i(
                                    "@VoiceIn: OfflinePackageChecker",
                                    "langPackageName " + langPackage);
                            if (z) {
                                SemLog.i(
                                        "@VoiceIn: OfflinePackageChecker",
                                        "checking for " + locale);
                                final int i = 0;
                                arrayList.add(
                                        CompletableFuture.supplyAsync(
                                                        new Supplier() { // from class:
                                                            // com.samsung.android.settings.voiceinput.LanguagePackStubUtils$$ExternalSyntheticLambda0
                                                            @Override // java.util.function.Supplier
                                                            public final Object get() {
                                                                return LanguagePackStubUtils
                                                                        .$r8$lambda$X16msSBPMFGzZNoZSHFH9nY1Nz4(
                                                                                context2,
                                                                                langPackage);
                                                            }
                                                        })
                                                .thenApply(
                                                        new Function() { // from class:
                                                            // com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker$$ExternalSyntheticLambda1
                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                Locale locale2 = locale;
                                                                String str = langPackage;
                                                                AppUpdateData appUpdateData =
                                                                        (AppUpdateData) obj;
                                                                SemLog.i(
                                                                        "@VoiceIn:"
                                                                            + " OfflinePackageChecker",
                                                                        "appUpdateData = "
                                                                                + appUpdateData);
                                                                if (appUpdateData != null
                                                                        && !appUpdateData
                                                                                .getmResultCode()
                                                                                .isEmpty()) {
                                                                    return new OfflinePackageChecker
                                                                            .CustomAppUpdateData(
                                                                            str,
                                                                            locale2,
                                                                            appUpdateData);
                                                                }
                                                                throw new RuntimeException(
                                                                        "Error occurred while"
                                                                            + " fetching the"
                                                                            + " language status for"
                                                                            + " locale = "
                                                                                + RecognizerUtils
                                                                                        .getLocaleCode(
                                                                                                locale2));
                                                            }
                                                        })
                                                .exceptionally(
                                                        new Function() { // from class:
                                                            // com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker$$ExternalSyntheticLambda2
                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                int i2 = i;
                                                                Object obj2 = locale;
                                                                switch (i2) {
                                                                    case 0:
                                                                        SemLog.i(
                                                                                "@VoiceIn:"
                                                                                    + " OfflinePackageChecker",
                                                                                "Async Exception = "
                                                                                        + ((Locale)
                                                                                                obj2));
                                                                        return null;
                                                                    default:
                                                                        return (List)
                                                                                ((List) obj2)
                                                                                        .stream()
                                                                                                .map(
                                                                                                        new OfflinePackageChecker$$ExternalSyntheticLambda9())
                                                                                                .collect(
                                                                                                        Collectors
                                                                                                                .toList());
                                                                }
                                                            }
                                                        }));
                            }
                        }
                        final int i2 = 1;
                        CompletableFuture.allOf(
                                        (CompletableFuture[])
                                                arrayList.toArray(
                                                        new CompletableFuture[arrayList.size()]))
                                .thenApply(
                                        new Function() { // from class:
                                            // com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                int i22 = i2;
                                                Object obj2 = arrayList;
                                                switch (i22) {
                                                    case 0:
                                                        SemLog.i(
                                                                "@VoiceIn: OfflinePackageChecker",
                                                                "Async Exception = "
                                                                        + ((Locale) obj2));
                                                        return null;
                                                    default:
                                                        return (List)
                                                                ((List) obj2)
                                                                        .stream()
                                                                                .map(
                                                                                        new OfflinePackageChecker$$ExternalSyntheticLambda9())
                                                                                .collect(
                                                                                        Collectors
                                                                                                .toList());
                                                }
                                            }
                                        })
                                .thenAcceptAsync(
                                        (Consumer<? super U>)
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker$$ExternalSyntheticLambda4
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        final Context context3 = context2;
                                                        ObservableEmitter observableEmitter3 =
                                                                observableEmitter2;
                                                        boolean z2 = z;
                                                        List list = (List) obj;
                                                        SemLog.i(
                                                                "@VoiceIn: OfflinePackageChecker",
                                                                "listCustomAppUpdateData size = "
                                                                        + list.size());
                                                        final int i3 = 0;
                                                        boolean allMatch =
                                                                list.stream()
                                                                        .allMatch(
                                                                                new Predicate() { // from class: com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker$$ExternalSyntheticLambda6
                                                                                    @Override // java.util.function.Predicate
                                                                                    public final
                                                                                    boolean test(
                                                                                            Object
                                                                                                    obj2) {
                                                                                        OfflinePackageChecker
                                                                                                        .CustomAppUpdateData
                                                                                                customAppUpdateData =
                                                                                                        (OfflinePackageChecker
                                                                                                                        .CustomAppUpdateData)
                                                                                                                obj2;
                                                                                        switch (i3) {
                                                                                            case 0:
                                                                                                if (customAppUpdateData
                                                                                                        != null) {}
                                                                                                break;
                                                                                            default:
                                                                                                if (customAppUpdateData
                                                                                                        != null) {}
                                                                                                break;
                                                                                        }
                                                                                        return false;
                                                                                    }
                                                                                });
                                                        SemLog.i(
                                                                "@VoiceIn: OfflinePackageChecker",
                                                                "isAllSuccess = " + allMatch);
                                                        if (allMatch) {
                                                            boolean z3 =
                                                                    PreferenceManager
                                                                            .getDefaultSharedPreferences(
                                                                                    context3)
                                                                            .getBoolean(
                                                                                    Constants
                                                                                            .IS_FIRST_FETCH_FROM_SERVER,
                                                                                    true);
                                                            SemLog.i(
                                                                    "@VoiceIn: OfflineLanguageUtil",
                                                                    "getFirstFetchStatus: " + z3);
                                                            if (z3) {
                                                                SemLog.i(
                                                                        "@VoiceIn:"
                                                                            + " OfflinePackageChecker",
                                                                        "Updating First Fetch"
                                                                            + " Status");
                                                                observableEmitter3.onNext(
                                                                        Boolean.TRUE);
                                                                SemLog.i(
                                                                        "@VoiceIn:"
                                                                            + " OfflineLanguageUtil",
                                                                        "setFirstFetchStatus "
                                                                            + " Status:false");
                                                                PreferenceManager
                                                                        .getDefaultSharedPreferences(
                                                                                context3)
                                                                        .edit()
                                                                        .putBoolean(
                                                                                Constants
                                                                                        .IS_FIRST_FETCH_FROM_SERVER,
                                                                                false)
                                                                        .apply();
                                                            }
                                                            if (z2) {
                                                                long currentTimeMillis =
                                                                        System.currentTimeMillis();
                                                                SemLog.i(
                                                                        "@VoiceIn:"
                                                                            + " OfflineLanguageUtil",
                                                                        "setLastUpdateTime "
                                                                                + Long.valueOf(
                                                                                        currentTimeMillis));
                                                                PreferenceManager
                                                                        .getDefaultSharedPreferences(
                                                                                context3)
                                                                        .edit()
                                                                        .putLong(
                                                                                Constants
                                                                                        .DICTATION_LANGPACK_REQUEST_TIME,
                                                                                currentTimeMillis)
                                                                        .apply();
                                                            }
                                                        }
                                                        final int i4 = 1;
                                                        list.stream()
                                                                .filter(
                                                                        new Predicate() { // from
                                                                            // class:
                                                                            // com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker$$ExternalSyntheticLambda6
                                                                            @Override // java.util.function.Predicate
                                                                            public final boolean
                                                                                    test(
                                                                                            Object
                                                                                                    obj2) {
                                                                                OfflinePackageChecker
                                                                                                .CustomAppUpdateData
                                                                                        customAppUpdateData =
                                                                                                (OfflinePackageChecker
                                                                                                                .CustomAppUpdateData)
                                                                                                        obj2;
                                                                                switch (i4) {
                                                                                    case 0:
                                                                                        if (customAppUpdateData
                                                                                                != null) {}
                                                                                        break;
                                                                                    default:
                                                                                        if (customAppUpdateData
                                                                                                != null) {}
                                                                                        break;
                                                                                }
                                                                                return false;
                                                                            }
                                                                        })
                                                                .forEach(
                                                                        new Consumer() { // from
                                                                            // class:
                                                                            // com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker$$ExternalSyntheticLambda8
                                                                            @Override // java.util.function.Consumer
                                                                            public final void
                                                                                    accept(
                                                                                            Object
                                                                                                    obj2) {
                                                                                AppUpdateData
                                                                                        appUpdateData;
                                                                                Locale locale2;
                                                                                String str;
                                                                                String str2;
                                                                                Context context4 =
                                                                                        context3;
                                                                                OfflinePackageChecker
                                                                                                .CustomAppUpdateData
                                                                                        customAppUpdateData =
                                                                                                (OfflinePackageChecker
                                                                                                                .CustomAppUpdateData)
                                                                                                        obj2;
                                                                                try {
                                                                                    appUpdateData =
                                                                                            customAppUpdateData
                                                                                                    .appUpdateData;
                                                                                    locale2 =
                                                                                            customAppUpdateData
                                                                                                    .locale;
                                                                                    String
                                                                                            localeCode =
                                                                                                    RecognizerUtils
                                                                                                            .getLocaleCode(
                                                                                                                    locale2);
                                                                                    String str3 =
                                                                                            appUpdateData
                                                                                                    .getmResultCode();
                                                                                    boolean z4 =
                                                                                            str3
                                                                                                            != null
                                                                                                    && !str3
                                                                                                            .isEmpty()
                                                                                                    && str3.chars()
                                                                                                            .allMatch(
                                                                                                                    new VoiceInputUtils$$ExternalSyntheticLambda0())
                                                                                                    && Integer
                                                                                                                    .parseInt(
                                                                                                                            str3)
                                                                                                            == 2;
                                                                                    SemLog.i(
                                                                                            "@VoiceIn:"
                                                                                                + " OfflinePackageChecker",
                                                                                            "localeCode"
                                                                                                + " "
                                                                                                    + localeCode
                                                                                                    + " isupdateavailable"
                                                                                                    + " "
                                                                                                    + z4);
                                                                                    if (!z4) {
                                                                                        str =
                                                                                                customAppUpdateData
                                                                                                        .langPackName;
                                                                                        if (PackageUtils
                                                                                                .isPackageInstalled(
                                                                                                        context4,
                                                                                                        str)) {
                                                                                            SemLog
                                                                                                    .i(
                                                                                                            "@VoiceIn:"
                                                                                                                + " OfflinePackageChecker",
                                                                                                            "Updated"
                                                                                                                + " Package"
                                                                                                                + " downloaded"
                                                                                                                + " :: "
                                                                                                                    + localeCode);
                                                                                            OfflineLanguageUtil
                                                                                                    .setLanguagePackageStatus(
                                                                                                            context4,
                                                                                                            3,
                                                                                                            localeCode);
                                                                                            return;
                                                                                        }
                                                                                        SemLog.i(
                                                                                                "@VoiceIn:"
                                                                                                    + " OfflinePackageChecker",
                                                                                                "No Package"
                                                                                                    + " Available"
                                                                                                    + " for localeCode"
                                                                                                    + " :: "
                                                                                                        + localeCode);
                                                                                        OfflineLanguageUtil
                                                                                                .setLanguagePackageStatus(
                                                                                                        context4,
                                                                                                        0,
                                                                                                        localeCode);
                                                                                        return;
                                                                                    }
                                                                                    String str4 =
                                                                                            appUpdateData
                                                                                                    .getmVersionCode();
                                                                                    SemLog.i(
                                                                                            "@VoiceIn:"
                                                                                                + " OfflinePackageChecker",
                                                                                            "versionFromServer"
                                                                                                + " : "
                                                                                                    + str4);
                                                                                    str2 =
                                                                                            customAppUpdateData
                                                                                                    .langPackName;
                                                                                    if (PackageUtils
                                                                                            .isPackageInstalled(
                                                                                                    context4,
                                                                                                    str2)) {
                                                                                        SemLog.i(
                                                                                                "@VoiceIn:"
                                                                                                    + " OfflinePackageChecker",
                                                                                                "Need to"
                                                                                                    + " update"
                                                                                                    + " pack"
                                                                                                    + " for localeCode"
                                                                                                    + " :: "
                                                                                                        + localeCode);
                                                                                        OfflineLanguageUtil
                                                                                                .setLanguagePackageStatus(
                                                                                                        context4,
                                                                                                        2,
                                                                                                        localeCode);
                                                                                        OfflineLanguageUtil
                                                                                                .setLanguagePackageVersion(
                                                                                                        context4,
                                                                                                        localeCode,
                                                                                                        str4);
                                                                                        return;
                                                                                    }
                                                                                    SemLog.i(
                                                                                            "@VoiceIn:"
                                                                                                + " OfflinePackageChecker",
                                                                                            "Need to"
                                                                                                + " download"
                                                                                                + " pack"
                                                                                                + " for localeCode"
                                                                                                + " :: "
                                                                                                    + localeCode);
                                                                                    OfflineLanguageUtil
                                                                                            .setLanguagePackageStatus(
                                                                                                    context4,
                                                                                                    1,
                                                                                                    localeCode);
                                                                                    OfflineLanguageUtil
                                                                                            .setLanguagePackageVersion(
                                                                                                    context4,
                                                                                                    localeCode,
                                                                                                    str4);
                                                                                } catch (
                                                                                        Exception
                                                                                                e) {
                                                                                    SemLog.i(
                                                                                            "@VoiceIn:"
                                                                                                + " OfflinePackageChecker",
                                                                                            " exception"
                                                                                                + " caught"
                                                                                                    + e
                                                                                                            .getMessage());
                                                                                    e
                                                                                            .printStackTrace();
                                                                                }
                                                                            }
                                                                        });
                                                    }
                                                },
                                        (Executor)
                                                new OfflinePackageChecker.HandlerExecutor(
                                                        new Handler(Looper.getMainLooper())))
                                .whenComplete(
                                        new BiConsumer() { // from class:
                                            // com.samsung.android.settings.voiceinput.offline.OfflinePackageChecker$$ExternalSyntheticLambda5
                                            @Override // java.util.function.BiConsumer
                                            public final void accept(Object obj, Object obj2) {
                                                ObservableEmitter observableEmitter3 =
                                                        ObservableEmitter.this;
                                                SemLog.i(
                                                        "@VoiceIn: OfflinePackageChecker",
                                                        "whenComplete "
                                                                + Thread.currentThread().getName());
                                                observableEmitter3.onNext(Boolean.TRUE);
                                                observableEmitter3.onComplete();
                                            }
                                        });
                    }
                });
    }
}
