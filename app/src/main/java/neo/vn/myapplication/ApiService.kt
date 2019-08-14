package neo.vn.myapplication

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET(MainActivity.API_BRANCH)
    fun getAuthority(@Query("ServiceType") response: String = "value",
                     @Query("Service") service: String = "login_service_mobile",
                     @Query("Provider") provider: String = "default",
                     @Query("ParamSize") paramSize: Int = 3,
                     @Query("P1") user: String="",
                     @Query("P2") pass: String="",
                     @Query("P3") deviceId: String=""): Observable<Response<String>>
}