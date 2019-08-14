package neo.vn.myapplication

import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit


class CrunchifyBlockingConsumer(val queue: BlockingQueue<CrunchierMessage>) : Runnable {
    fun toMd5(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString( (0xFF and aMessageDigest.toInt()))
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
    override fun run() {
        try {
            var msg: CrunchierMessage

            // consuming messages until exit message is received

            do {
                msg = queue.take()
                val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ").serializeNulls()
                    .setLenient().create()
                val httpClient = OkHttpClient.Builder()
                httpClient.connectTimeout(1, TimeUnit.MINUTES)
                httpClient.readTimeout(30, TimeUnit.SECONDS)
                httpClient.writeTimeout(20, TimeUnit.SECONDS)
                httpClient.connectTimeout(60, TimeUnit.SECONDS)
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://ksan.neo.vn/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    httpClient.addInterceptor(logging)
                }
                var respone: String?= null
                val api = retrofit.create(ApiService::class.java)
                api.getAuthority(
                    user = msg.user,
                    pass = toMd5(msg.password),
                    deviceId = "Đasadasd"
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        respone= user.body()
                    }, {
                        respone= it.message
                    }
                    )
                while (respone.isNullOrEmpty()) {
                }
                println("CrunchifyBlockingConsumer: Message - " + msg.user+ respone + " consumed.")
            } while (queue.isEmpty())
            println(msg)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}