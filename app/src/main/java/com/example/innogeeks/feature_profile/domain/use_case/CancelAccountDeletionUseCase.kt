package com.example.innogeeks.feature_profile.domain.use_case

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_profile.domain.repository.ProfileRepository

// Not called anywhere yet — no pending-deletion/cancel screen exists in the app. Added now
// for symmetry with the repository contract; wire it up once that screen is built.
class CancelAccountDeletionUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(): Result<Unit, DataError.Network> =
        profileRepository.cancelAccountDeletion()
}
