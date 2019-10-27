package kotlins.module.labyrintos.RetrofitForRXJava

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Labyrintos on 2019-10-27
 */
interface GithubService {

    @GET("/send")
    fun getRepoList(@Query("msg") query: String): Single<GithubResponseModel>
}