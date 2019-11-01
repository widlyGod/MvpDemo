package com.example.safe.utils

import com.xmssx.common.mvp.CIView
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 绑定到生命周期
 */
fun Disposable.bindToLifecycle(lifecycleProvider: IRxLifecycleProvider): Disposable {
    lifecycleProvider.bindToLifecycle(this)
    return this
}


fun <T> Observable<T>.subscribeOnMain(): Observable<T> {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.subscribeOnIO(): Observable<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.observeOnMain(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.observeOnIO(): Observable<T> {
    return observeOn(Schedulers.io())
}


fun <T> Flowable<T>.subscribeOnMain(): Flowable<T> {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.subscribeOnIO(): Flowable<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Flowable<T>.observeOnMain(): Flowable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.observeOnIO(): Flowable<T> {
    return observeOn(Schedulers.io())
}


fun <T> Single<T>.subscribeOnMain(): Single<T> {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.subscribeOnIO(): Single<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Single<T>.observeOnMain(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.observeOnIO(): Single<T> {
    return observeOn(Schedulers.io())
}


fun <T> Maybe<T>.subscribeOnMain(): Maybe<T> {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.subscribeOnIO(): Maybe<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Maybe<T>.observeOnMain(): Maybe<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.observeOnIO(): Maybe<T> {
    return observeOn(Schedulers.io())
}


fun Completable.subscribeOnMain(): Completable {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun Completable.subscribeOnIO(): Completable {
    return subscribeOn(Schedulers.io())
}

fun Completable.observeOnMain(): Completable {
    return observeOn(AndroidSchedulers.mainThread())
}

fun Completable.observeOnIO(): Completable {
    return observeOn(Schedulers.io())
}

@CheckReturnValue
fun <T> Single<T>.subscribeWithView(
    onNext: (t: T) -> Unit,
    view: CIView?,
    defaultMsg: String,
    onError: (e: Throwable) -> Boolean = { false }
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    })
}

@CheckReturnValue
fun <T> Single<T>.subscribeWithView(
    view: CIView?,
    defaultMsg: String,
    onError: (e: Throwable) -> Boolean = { false },
    onNext: (t: T) -> Unit
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    })
}

@CheckReturnValue
fun <T> Maybe<T>.subscribeWithView(
    view: CIView?,
    defaultMsg: String,
    onError: (e: Throwable) -> Boolean = { false },
    onNext: (t: T) -> Unit
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    })
}

@CheckReturnValue
fun <T> Observable<T>.subscribeWithView(
    onNext: (t: T) -> Unit,
    view: CIView?,
    defaultMsg: String,
    onError: (e: Throwable) -> Boolean = { false },
    onComplete: () -> Unit = {}
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    }, onComplete)
}

@CheckReturnValue
fun <T> Observable<T>.subscribeWithView(
    view: CIView?,
    defaultMsg: String,
    onError: (e: Throwable) -> Boolean = { false },
    onNext: (t: T) -> Unit
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    })
}

@CheckReturnValue
fun <T> Flowable<T>.subscribeWithView(
    onNext: (t: T) -> Unit,
    view: CIView?,
    defaultMsg: String,
    onError: (e: Throwable) -> Boolean = { false },
    onComplete: () -> Unit = {}
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    }, onComplete)
}

