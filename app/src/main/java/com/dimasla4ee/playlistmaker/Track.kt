package com.dimasla4ee.playlistmaker

data class Track(
    val title: String,
    val artist: String,
    val duration: String,
    val artworkUrl: String
)

val placeholderTracks = listOf(
    Track(
        title = "Smells Like Teen Spirit",
        artist = "Nirvana",
        duration = "5:01",
        artworkUrl = "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
    ),
    Track(
        title = "Billie Jean",
        artist = "Michael Jackson",
        duration = "4:35",
        artworkUrl = "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
    ),
    Track(
        title = "Stayin' Alive",
        artist = "Bee Gees",
        duration = "4:10",
        artworkUrl = "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
    ),
    Track(
        title = "Whole Lotta Love",
        artist = "Led Zeppelin",
        duration = "5:33",
        artworkUrl = "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
    ),
    Track(
        title = "Sweet Child O'Mine",
        artist = "Guns N' Roses",
        duration = "5:03",
        artworkUrl = "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
    ),
    Track(
        title = "Колобок",
        artist = "Хаски",
        duration = "3:04",
        artworkUrl = "invalidUrl"
    ),
    Track(
        title = "Death by Dishonor",
        artist = "Ghostemane, Shakewell, Pouya, Erich the Architect",
        duration = "3:12",
        artworkUrl = "long author"
    ),
    Track(
        title = "Outside [Calvin Harris Ft. Ellie Goulding over] (Acoustic Version)",
        artist = "Acoustic Guitar Music",
        duration = "3:25",
        artworkUrl = "long title"
    ),
    Track(
        title = "В синем море, в белой пене (Оставайся, мальчик, с нами) (Geoffrey Day Remix)",
        artist = "Юлия Коган, Frenetic Virtual Orchestra, GeoffPlaysGuitar, Atomic Heart",
        duration = "5:10",
        artworkUrl = "long author and title"
    ),
    Track(
        title = "Бей",
        artist = "Монгол Шуудан",
        duration = "4:16",
        artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/50/56/66/5056663f-d237-ec4e-601c-bdf7963d8a18/cover.jpg/296x296bb.webp"
    ),
    Track(
        title = "Stayin' Alive",
        artist = "Bee Gees",
        duration = "4:10",
        artworkUrl = "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
    ),
    Track(
        title = "Whole Lotta Love",
        artist = "Led Zeppelin",
        duration = "5:33",
        artworkUrl = "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
    ),
    Track(
        title = "Sweet Child O'Mine",
        artist = "Guns N' Roses",
        duration = "5:03",
        artworkUrl = "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
    ),
    Track(
        title = "Колобок",
        artist = "Хаски",
        duration = "3:04",
        artworkUrl = "invalidUrl"
    ),
    Track(
        title = "Death by Dishonor",
        artist = "Ghostemane, Shakewell, Pouya, Erich the Architect",
        duration = "3:12",
        artworkUrl = "long author"
    ),
    Track(
        title = "Outside [Calvin Harris Ft. Ellie Goulding over] (Acoustic Version)",
        artist = "Acoustic Guitar Music",
        duration = "3:25",
        artworkUrl = "long title"
    ),
    Track(
        title = "В синем море, в белой пене (Оставайся, мальчик, с нами) (Geoffrey Day Remix)",
        artist = "Юлия Коган, Frenetic Virtual Orchestra, GeoffPlaysGuitar, Atomic Heart",
        duration = "5:10",
        artworkUrl = "long author and title"
    ),
    Track(
        title = "Бей",
        artist = "Монгол Шуудан",
        duration = "4:16",
        artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/50/56/66/5056663f-d237-ec4e-601c-bdf7963d8a18/cover.jpg/296x296bb.webp"
    )
)