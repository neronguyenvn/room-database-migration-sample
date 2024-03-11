package com.example.appmusic.core.common.coroutine

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: AmDispatcher)

enum class AmDispatcher {
    IO
}