package com.soligdag.filmdrawer.data.models

import androidx.room.PrimaryKey

val dummyMovie = MediaItem(
    id = 912916,
    title = "The Other Zoey",
    voteAverage = 7.65,
    releaseDate = "13 Oct 2023",
    posterPath = "/rh9fwqA98ufdx9vP7V6lLhfpfk1.jpg"
)

val dummyListOfMovie = listOf(
    MediaItem(
        id = 951491,
        title = "Saw X",
        voteAverage = 7.224,
        releaseDate = "2023-09-26",
        posterPath = "/b16RAVwj2QN6RAs752UJNzQ9Of0.jpg"
    ),
    MediaItem(
        id = 912916,
        title = "The Other Zoey",
        voteAverage = 7.4,
        releaseDate = "2023-10-19",
        posterPath = "/rh9fwqA98ufdx9vP7V6lLhfpfk1.jpg"
    ),
    MediaItem(
        id = 466420,
        title = "Killers of the Flower Moon",
        voteAverage = 7.838,
        releaseDate = "2023-10-18",
        posterPath = "/dB6Krk806zeqd0YNp2ngQ9zXteH.jpg"
    ),
    MediaItem(
        id = 987917,
        title = "Old Dads",
        voteAverage = 9.0,
        releaseDate = "2023-10-28",
        posterPath = "/ax1rpxHCcXVwwfn0zSte1udoJwV.jpg"
    ),
    MediaItem(
        id = 299054,
        title = "Expend4bles",
        voteAverage = 6.39,
        releaseDate = "2023-09-15",
        posterPath = "/mOX5O6JjCUWtlYp5D8wajuQRVgy.jpg"
    ),
)



val dummyWishlist = listOf(
    WishlistItem(
        id = 951491,
        title = "Saw X",
        voteAverage = 7.224,
        releaseDate = "2023-09-26",
        mediaType = "movie",
        posterPath = "/b16RAVwj2QN6RAs752UJNzQ9Of0.jpg",
        overview = "this is an overview about the detail of media item"
    )
)

val dummyListOfSeries = listOf(
    MediaItem(
        id = 233629,
        name = "Saw X",
        voteAverage = 6.4,
        releaseDate = "2023-10-19",
        mediaType = "series",
        posterPath = "/qWX71nLvoLsBNPEjddZMC75lq6I.jpg"
    ),
    MediaItem(
        id = 84958,
        name = "Loki",
        voteAverage = 8.163,
        releaseDate = "2021-06-09",
        mediaType = "series",
        posterPath = "/voHUmluYmKyleFkTu3lOXQG702u.jpg"
    ),
    MediaItem(
        id = 194797,
        name = "Doona!",
        voteAverage = 9.75,
        releaseDate = "2023-10-20",
        mediaType = "series",
        posterPath = "/eOCZIaGSUZzCAiz70G7kEQzuC0U.jpg"
    ),
    MediaItem(
        id = 76669,
        name = "Elite",
        voteAverage = 9.0,
        releaseDate = "2023-10-28",
        mediaType = "series",
        posterPath = "/3NTAbAiao4JLzFQw6YxP1YZppM8.jpg"
    ),
    MediaItem(
        id = 86248,
        name = "Upload",
        voteAverage = 8.0,
        releaseDate = "2020-04-30",
        mediaType = "series",
        posterPath = "/xHA48xeftf7hZmjXNZqDghCZM1a.jpg"
    ),
)


val dummyMovieRecommendation = Recommendation(
    id = 2312,
    recommenderId = 2334,
    recommenderName = "Uzair Ahmad",
    receiverId = 234,
    receiverName = "Hammad Ahmed",
    mediaItem = dummyMovie,
    date = "2023-04-24",
    recommendationText = "This is a 100 word text to describe how I feel about this movie and why you should watch it. This is additional text to see how the overflow recommendation text will look like. I hope you like it"
)

val dummyMovieDetail = MovieDetail(
    adult = false,
    backdropPath = "/fTrrmsGYgR4VkiiD0aI3YXtJv2w.jpg",
    //belongsToCollection = null,
    budget = 0,
    genres = arrayListOf(Genres(10749, "Romance"), Genres(18, "Drama"), Genres(35, "Comedy")),
    homepage = "",
    id = 912916,
    imdbId = "tt119512761",
    originalLanguage = "en",
    originalTitle = "The Other Zoey",
    overview = "Zoey Miller, a super smart computer major uninterested in romantic love, has her life turned upside down when Zack, the school’s soccer star, gets amnesia and mistakes Zoey for his girlfriend.",
    popularity = 75.148,
    posterPath = "/rh9fwqA98ufdx9vP7V6lLhfpfk1.jpg",
    productionCompanies = arrayListOf(
        ProductionCompanies(
            id = 20788,
            logoPath = "/7hBlD2RPUo6LHgSDSSnyYuXdigU.png",
            name = "GulfStream Pictures",
            originCountry = "US"
        )
    ),
    productionCountries = arrayListOf(
        ProductionCountries(
            iso31661 = "US",
            name = "United States of America"
        )
    ),
    releaseDate = "2023-10-19",
    revenue = 0,
    runtime = 91,
    spokenLanguages = arrayListOf(
        SpokenLanguages(
            englishName = "English",
            iso6391 = "en",
            name = "English"
        ),
        SpokenLanguages(englishName = "Italian", iso6391 = "it", name = "Italiano"),
    ),
    status = "Released",
    tagline = "Love is the one thing she can't outsmart.",
    title = "The Other Zoey",
    video = false,
    voteAverage = 7.7,
    voteCount = 15

)

val dummyCastMember = CastMember(
    id = 1753914,
    name = "Josephine Langford",
    profilePath = "/8Fj1UIFRJA0B5Zo22KwML5d3Mr3.jpg",
    character = "Zoey Miller",
    creditId = "61afbeab33ad8f008a552f47"
)
val dummyCrewMemberDirector = CrewMember(id = 2127723, name = "Sara Zandieh", job = "Director")

val dummyListOfCastMembers = listOf(
    CastMember(
        id = 1753914,
        name = "Josephine Langford",
        profilePath = "/8Fj1UIFRJA0B5Zo22KwML5d3Mr3.jpg",
        character = "Zoey Miller",
        creditId = "61afbeab33ad8f008a552f47"
    ),
    CastMember(
        id = 1939676,
        name = "Drew Starkey",
        profilePath = "/vKDIk7HNOnDQs4uJoHwbkJPD9ua.jpg",
        character = "Zack MacLaren",
        creditId = "61afbec0904f6d00446f0262"
    ),
    CastMember(
        id = 2164506,
        name = "Archie Renaux",
        profilePath = "/uTd18t2VJovN2jSJyhuG8Yy3PV6.jpg",
        character = "Miles",
        creditId = "61afbecfa2423200412fe74b"
    ),
    CastMember(
        id = 69122,
        name = "Heather Graham",
        profilePath = "/avYdNkeg1oTvmrNJbFDcTlBCkKs.jpg",
        character = "Paula Miller",
        creditId = "61bb7fb2397df0001c8ab4b0"
    ),
)


val dummyListOfCrewMembers = listOf(
    CrewMember(id = 27097, name = "Matt Luber", job = "Producer"),
    CrewMember(id = 1004120, name = "Matt Luber", job = "Writer"),
    CrewMember(id = 2127723, name = "Sara Zandieh", job = "Director"),
)

val dummySeriesDetail = SeriesDetail(
    backdropPath = "/q3jHCb4dMfYF6ojikKuHd6LscxC.jpg",
    createdBy = arrayListOf(
        CreatedBy(
            id = 2094567,
            creditId = "x6001713e7390c0003df730af",
            name = "Michael Waldron",
            gender = 2,
            profilePath = "/5d6wkYnJgkVAzThqnnwOLNDzACM.jpg"
        )
    ),
    firstAirDate = "2021-06-09",
    genres = arrayListOf(Genres(10749, "Romance"), Genres(18, "Drama"), Genres(35, "Comedy")),
    id = 84958,
    inProduction = true,
    lastAirDate = "2023-10-19",
    lastEpisodeToAir = LastEpisodeToAir(
        id = 4447780,
        name = "1893",
        overview = "Loki & Mobius go on the hunt to find everyone’s favorite cartoon clock as they try to save the TVA.",
        voteAverage = 6.895,
        voteCount = 19,
        runtime = 56,
        seasonNumber = 2,
        airDate = "2023-10-19",
        episodeNumber = 3,
        episodeType = "standard",
        showId = 84958,
        stillPath = "/pwAniwa715V556WQSmoJiOvTchW.jpg"
    ),
    name = "Loki",
    nextEpisodeToAir = NextEpisodeToAir(
        id = 4447781,
        name = "Episode 4",
        voteAverage = 5.5,
        voteCount = 2,
        airDate = "2023-10-26",
        episodeNumber = 4,
        episodeType = "standard",
        seasonNumber = 2,
        showId = 84958,
        stillPath = "/4BqPWUCsyaf8LCMEjduvdLaF78c.jpg"
    ),
    networks = arrayListOf(
        Networks(
            id = 2739,
            logoPath = "/uzKjVDmQ1WRMvGBb7UNRE0wTn1H.png",
            name = "Disney+",
            originCountry = ""
        )
    ),
    numberOfEpisodes = 12,
    numberOfSeasons = 2,
    originCountry = arrayListOf("US"),
    originalLanguage = "en",
    originalName = "Loki",
    overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” an alternate version of Loki is brought to the mysterious Time Variance Authority, a bureaucratic organization that exists outside of time and space and monitors the timeline. They give Loki a choice: face being erased from existence due to being a “time variant” or help fix the timeline and stop a greater threat.",
    popularity = 2375.015,
    posterPath = "/voHUmluYmKyleFkTu3lOXQG702u.jpg",
    productionCompanies = arrayListOf(
        ProductionCompanies(
            id = 420,
            logoPath = "/hUzeosd33nzE5MCNsZxCGEKTXaQ.png",
            name = "Marvel Studios",
            originCountry = "US"
        ),
        ProductionCompanies(
            id = 176762,
            logoPath = null,
            name = "Kevin Feige Productions",
            originCountry = "US"
        )
    ),
    seasons = arrayListOf(
        Seasons(
            airDate = "2021-06-09",
            episodeCount = 6,
            id = 114355,
            name = "Season 1",
            overview = "Loki, the God of Mischief, steps out of his brother's shadow to embark on an adventure that takes place after the events of Avengers: Endgame.",
            seasonNumber = 1,
            voteAverage = 7.8,
            posterPath = "/8uVqe9ThcuYVNdh4O0kuijIWMLL.jpg"
        ),
        Seasons(
            airDate = "2023-10-05",
            episodeCount = 6,
            id = 341180,
            name = "Season 2",
            overview = "In the aftermath of Season 1, Loki finds himself in a battle for the soul of the Time Variance Authority. Along with Mobius, Hunter B-15 and a team of new and returning characters, Loki navigates an ever-expanding and increasingly dangerous multiverse in search of Sylvie, Judge Renslayer, Miss Minutes and the truth of what it means to possess free will and glorious purpose.",
            seasonNumber = 2,
            voteAverage = 6.8,
            posterPath = "/8uVqe9ThcuYVNdh4O0kuijIWMLL.jpg"
        )
    ),
    spokenLanguages = arrayListOf(SpokenLanguages("English", iso6391 = "en", name = "English")),
    status = "Returning Series",
    tagline = "Loki's time has come.",
    voteAverage = 8.164,
    voteCount = 10348,
    type = "Scripted"
)


val dummyMediaPerson = MediaPerson(
    gender = 2,
    id = 85,
    knownForDepartment = "Acting",
    name = "Johnny Depp",
    originalName = "Johnny Depp",
    popularity = 57.373,
    profilePath = "/wcI594cwM4ArPwvRd2IU0Z0yLuh.jpg"
)


val dummyListOfMediaPerson = listOf<MediaPerson>(
    MediaPerson(
        gender = 2,
        id = 85,
        knownForDepartment = "Acting",
        name = "Johnny Depp",
        originalName = "Johnny Depp",
        popularity = 57.373,
        profilePath = "/wcI594cwM4ArPwvRd2IU0Z0yLuh.jpg"
    ),
    MediaPerson(
        gender = 2,
        id = 9656,
        knownForDepartment = "Acting",
        name = "Johnny Knoxville",
        originalName = "Johnny Knoxville",
        popularity = 28.22,
        profilePath = "/7XDKsHsLC4uNYaGsuWG1tQXWRnu.jpg"
    )
)


val dummySearchMoviesResponse =
    SearchMediaResult(page = 1, results = dummyListOfMovie, totalPages = 3, totalResults = 60)
val dummySearchSeriesResponse =
    SearchMediaResult(page = 1, results = dummyListOfSeries, totalPages = 3, totalResults = 60)
val dummySearchMediaPersonResponse =
    SearchActorResult(page = 1, results = dummyListOfMediaPerson, totalPages = 3, totalResults = 60)







