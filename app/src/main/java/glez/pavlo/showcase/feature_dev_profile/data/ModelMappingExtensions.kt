package glez.pavlo.showcase.feature_dev_profile.data

import glez.pavlo.showcase.feature_dev_profile.data.remote.RemoteDevProfile
import glez.pavlo.showcase.feature_tech_stack.data.remote.RemoteTech

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

fun RemoteTech.toTech(): Tech {
    return Tech(
        name = name,
        isImplemented = is_implemented
    )
}