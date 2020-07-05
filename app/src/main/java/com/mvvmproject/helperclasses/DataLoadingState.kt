package com.mvvmproject.helperclasses

sealed class DataLoadingState {
    object Idle : DataLoadingState()
    object Loading : DataLoadingState()
    class Error(val throwable: Throwable) : DataLoadingState()
}