package io.hammerhead.commons

sealed class ServiceException: Exception() {
    object BindException : ServiceException()
    object NotInstalledException : ServiceException()
}