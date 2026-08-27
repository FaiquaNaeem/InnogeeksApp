package com.example.innogeeks.feature_onboarding.presentation.auth.verifycode

import com.example.innogeeks.core.presentation.UiText

data class VerifyCodeState(
    val collegeEmail: String = "",
    // Kept as a String so a leading zero survives — §2 is explicit about this.
    val code: String = "",
    val codeError: UiText? = null,
    val formError: UiText? = null,
    val isSubmitting: Boolean = false,
    val isRequestingCode: Boolean = false,
    // Counts down from 60 only after a 202, per §5.
    val resendSecondsLeft: Int = 0
) {
    val canResend: Boolean get() = resendSecondsLeft == 0 && !isRequestingCode && !isSubmitting
    val isCodeComplete: Boolean get() = code.length == CODE_LENGTH

    companion object {
        const val CODE_LENGTH = 6
    }
}
