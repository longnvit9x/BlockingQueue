package neo.vn.myapplication

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit


class CrunchifyBlockingConsumer(
    var name: String,
    var mainView: MainView? = null,
    val queue: BlockingQueue<CrunchierMessage>
) : Runnable {
    var isRunning = true
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
                var h = Integer.toHexString((0xFF and aMessageDigest.toInt()))
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

    var idServer = 1
    var disposable = CompositeDisposable()
    override fun run() {
        try {
            var msg: CrunchierMessage

            // consuming messages until exit message is received

            while (isRunning) {
                println("Consumer: $name")
                msg = queue.take()
                var isResponse: Boolean? = null
                var dis: Disposable? = null
                dis = Observable.just(msg.apply {
                    this.id = idServer
                    idServer++
                })   .timeout ( 5000, TimeUnit.MILLISECONDS)
                    .delay(3000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        mainView?.getDataSuccess(user)
                        isResponse = true
                        println("Consumer: succes - " + msg.idLocal)
                        dis?.let {
                            disposable.remove(it)
                        }
                    }, {
                        mainView?.getDataError(it.message)
                        isResponse = false
                        println("Consumer: Error task- " + it.message + msg.password)
                        dis?.let {
                            disposable.remove(it)
                        }
                    }
                    )
                disposable.add(dis)
                while (isResponse == null) {
                    println("Task running ${msg.idLocal} ${msg.password}")
                }
                println("Task done ${msg.idLocal} ${msg.password}")
            }
            println("Exist consumed. $name")
        } catch (e: InterruptedException) {
            println("Consumer: Error - " + e.message + " consumed.")
        }
    }

    fun stop() {
        isRunning = false
    }
}