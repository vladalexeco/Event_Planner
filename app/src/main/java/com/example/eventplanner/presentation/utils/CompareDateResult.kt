package com.example.eventplanner.presentation.utils

sealed class CompareDateResult {
    data class Success(val isValidDateTime: Boolean) : CompareDateResult()
    data class Error(val errorMessage: String) : CompareDateResult()
}