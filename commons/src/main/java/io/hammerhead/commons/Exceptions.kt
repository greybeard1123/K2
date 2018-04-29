package io.hammerhead.commons

sealed class ServiceException: Exception() {
    object BindException : ServiceException()
    object DisconnectedException : ServiceException()
    object NotInstalledException : ServiceException()
}