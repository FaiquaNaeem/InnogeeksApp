package com.example.innogeeks.feature_profile.data.remote

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_profile.data.remote.dto.ProfileDto
import kotlinx.coroutines.delay

class FakeProfileRemoteDataSource : ProfileRemoteDataSource {

    override suspend fun getProfile(): Result<ProfileDto, DataError.Network> {
        delay(800)

        return Result.Success(
            ProfileDto(
                collegeEmail = "setup@kiet.edu",
                fullName = "Atul Kumar",
                phone = "+91 98765 43210",
                // Phase 1 only recruits first-years, so role is always FIRST_YEAR_STUDENT
                // (APP_API_CONTRACT.md §12) — batch/year must describe an actual first-year.
                batch = "2025-29",
                year = 1,
                role = "FIRST_YEAR_STUDENT"
            )
        )
    }
}
