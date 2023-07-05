package glez.pavlo.showcase.feature_dev_profile.data.remote

data class RemoteDevProfile(
    var name: String = "",
    var last_names: String  = "",
    var latest_project: String = "",
    var description: String = "",
    var role: String = "",
    var profile_photo_url: String = ""
)