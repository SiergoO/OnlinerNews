package siergo_o.onlinernews.presentation.screen

interface SearchNewsProvider {
    fun stateChanged(query: String)
}