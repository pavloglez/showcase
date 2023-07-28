package glez.pavlo.showcase.feature_jokes.data.remote

class JokeResponse : ArrayList<RemoteJoke>()

data class RemoteJoke(
    val id: Int,
    val punchline: String,
    val setup: String,
    val type: String
)