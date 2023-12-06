package com.soligdag.filmdrawer.data

import com.soligdag.filmdrawer.data.repositories.MediaRepository

interface AppContainer {
    val mediaRepository : MediaRepository
}