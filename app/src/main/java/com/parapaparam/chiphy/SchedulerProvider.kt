package com.parapaparam.chiphy

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SchedulersProvider @Inject constructor() {

    fun ui() = AndroidSchedulers.mainThread()
    fun io() = Schedulers.io()
}