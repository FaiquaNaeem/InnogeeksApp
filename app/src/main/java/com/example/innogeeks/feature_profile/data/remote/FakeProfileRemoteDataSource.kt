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
                batch = "2023-27",
                year = 3,
                role = "FIRST_YEAR_STUDENT"
            )
        )
    }
}
