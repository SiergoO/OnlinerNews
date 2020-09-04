package siergo_o.onlinernews.data.rest

import retrofit2.Retrofit
import siergo_o.onlinernews.App
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class OnlinerApiFactory {

    init {
        App.component.inject(this)
    }

    @Inject
    @field:[Named("tech")]
    lateinit var retrofitTech: Retrofit

    @Inject
    @field:[Named("people")]
    lateinit var retrofitPeople: Retrofit

    @Inject
    @field:[Named("auto")]
    lateinit var retrofitAuto: Retrofit

    fun getApi(site: String): OnlinerApi {
        return when (site) {
            "tech" -> retrofitTech.create(OnlinerApi::class.java)
            "people" -> retrofitPeople.create(OnlinerApi::class.java)
            "auto" -> retrofitAuto.create(OnlinerApi::class.java)
            else -> retrofitTech.create(OnlinerApi::class.java)
        }
    }
}