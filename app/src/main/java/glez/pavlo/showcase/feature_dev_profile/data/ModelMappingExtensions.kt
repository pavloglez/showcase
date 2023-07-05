package glez.pavlo.showcase.feature_dev_profile.data

import glez.pavlo.showcase.feature_dev_profile.data.remote.RemoteDevProfile

fun RemoteDevProfile.toDevProfile(): DevProfile {
    return DevProfile(
        name = name,
        lastNames = last_names,
        latestProject = latest_project,
        description = description,
        role = role,
        profilePhotoUrl = profile_photo_url
    )
}