package data

import utils.ScreenType

data class Settings (
    var screen: ScreenType = ScreenType.AUTHORIZATION,
    var username: String? = null,
    var password: String? = null,
    var department: String? = null,
)
