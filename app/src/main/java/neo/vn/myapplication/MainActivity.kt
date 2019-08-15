package neo.vn.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ArrayBlockingQueue

class MainActivity : AppCompatActivity(), MainView {

    var adapter: ListAdapter? = null

    var list = arrayListOf<CrunchierMessage>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ListAdapter(list, this)
        lv_result.adapter = adapter
        System.out.println("Let's get started. Producer / Consumer Test Started.\n")
        // val crunchProducer = CrunchifyBlockingProducer(crunchQueue)

//        // starting producer to produce messages in queue
//         Thread(crunchProducer).start()

        // starting consumer to consume messages from queue

    }

    var hashMapQueue = hashMapOf<Int, ArrayBlockingQueue<CrunchierMessage>>()
    var hashMapConsumerQueue = hashMapOf<Int, BlockingThread>()
    var i: Int = 1
    var j: Int = -1
    var consumerName= 1
    fun clickAddA(view: View) {
        // save local
        j = -1
        val time = System.currentTimeMillis()
        list.add(CrunchierMessage("longnvneo", "$i", -1, j, time))
        adapter?.notifyDataSetChanged()
        if (hashMapQueue.containsKey(j)) {
            hashMapQueue[j]?.put(CrunchierMessage("longnvneo", "$i", -1, j, time))
        } else {
            var crunchQueue = ArrayBlockingQueue<CrunchierMessage>(100)
            val crunchConsumer = CrunchifyBlockingConsumer("$consumerName",this, crunchQueue)
            consumerName++
            val consumer = BlockingThread(crunchConsumer)
            consumer.start()
            hashMapConsumerQueue[j] = consumer
            crunchQueue.put(CrunchierMessage("longnvneo", "$i", -1, j, time))
            hashMapQueue[j] = crunchQueue
        }
        println("$i")
        i++
        // j--
    }

    fun clickUpdateA(view: View) {
        j = -1
        // save local
        val time = System.currentTimeMillis()
        list.firstOrNull { it.idLocal == j }?.let {
            it.password = "$i"
            it.requestId = time
        }
        adapter?.notifyDataSetChanged()
        if (hashMapQueue.containsKey(j)) {
            hashMapQueue[j]?.put(CrunchierMessage("longnvneo", "$i", -1, j, time))
        } else {
            var crunchQueue = ArrayBlockingQueue<CrunchierMessage>(100)
            val crunchConsumer = CrunchifyBlockingConsumer("$consumerName",this, crunchQueue)
            consumerName++
            val consumer = BlockingThread(crunchConsumer)
            consumer.start()
            hashMapConsumerQueue[j] = consumer
            crunchQueue.put(CrunchierMessage("longnvneo", "$i", -1, j, time))
            hashMapQueue[j] = crunchQueue
        }
        println("$i")
        i++
    }

    fun clickAddB(view: View) {
        // save local
        j = -2
        val time = System.currentTimeMillis()
        list.add(CrunchierMessage("longnvneo", "$i", -1, j, time))
        adapter?.notifyDataSetChanged()
        if (hashMapQueue.containsKey(j)) {
            hashMapQueue[j]?.put(CrunchierMessage("longnvneo", "$i", -1, j, time))
        } else {
            var crunchQueue = ArrayBlockingQueue<CrunchierMessage>(100)
            val crunchConsumer = CrunchifyBlockingConsumer("$consumerName",this, crunchQueue)
            consumerName++
            val consumer = BlockingThread(crunchConsumer)
            consumer.start()
            hashMapConsumerQueue[j] = consumer
            crunchQueue.put(CrunchierMessage("longnvneo", "$i", -1, j, time))
            hashMapQueue[j] = crunchQueue
        }
        println("$i")
        i++
        // j--
    }

    fun clickUpdateB(view: View) {
        // save local
        val time = System.currentTimeMillis()
        j = -2
        list.firstOrNull { it.idLocal == j }?.let {
            it.password = "$i"
            it.requestId = time
        }
        adapter?.notifyDataSetChanged()
        if (hashMapQueue.containsKey(j)) {
            hashMapQueue[j]?.put(CrunchierMessage("longnvneo", "$i", -1, j, time))
        } else {
            var crunchQueue = ArrayBlockingQueue<CrunchierMessage>(100)
            val crunchConsumer = CrunchifyBlockingConsumer("$consumerName",this, crunchQueue)
            consumerName++
            val consumer = BlockingThread(crunchConsumer)
            consumer.start()
            hashMapConsumerQueue[j] = consumer
            crunchQueue.put(CrunchierMessage("longnvneo", "$i", -1, j, time))
            hashMapQueue[j] = crunchQueue
        }
        println("$i")
        i++
    }

//    var j: Int = 1
//    var key = 1
//    fun clickAddData(view: View) {
//        if (j % key == 0) {
//            key++
//        }
//        println("$j")
//        if (hashMapQueue.containsKey(key)) {
//            hashMapQueue[key]?.add(CrunchierMessage("longnv", "$j", key))
//            if (hashMapQueue[key]?.size == 1) {
//                pushData(CrunchierMessage("longnv", "$j", key))
//            }
//        } else {
//            hashMapQueue[key] = arrayListOf(CrunchierMessage("longnv", "$j", key))
//            if (hashMapQueue[key]?.size == 1) {
//                pushData(CrunchierMessage("longnv", "$j", key))
//            }
//        }
//
//        j++
//    }
//
//    var hashMapQueue = hashMapOf<Int, ArrayList<CrunchierMessage>>()
//
//    fun pushData(message: CrunchierMessage) {
//        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ").serializeNulls()
//            .setLenient().create()
//        val httpClient = OkHttpClient.Builder()
//        httpClient.connectTimeout(1, TimeUnit.MINUTES)
//        httpClient.readTimeout(30, TimeUnit.SECONDS)
//        httpClient.writeTimeout(20, TimeUnit.SECONDS)
//        httpClient.connectTimeout(60, TimeUnit.SECONDS)
//        if (BuildConfig.DEBUG) {
//            val logging = HttpLoggingInterceptor()
//            logging.level = HttpLoggingInterceptor.Level.BODY
//            httpClient.addInterceptor(logging)
//        }
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://ksan.neo.vn/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(httpClient.build())
//            .build()
//
//        var respone: String? = null
//        val api = retrofit.create(ApiService::class.java)
//        val timePush = System.currentTimeMillis()
//        api.getAuthority(
//            user = message.user,
//            pass = message.password,
//            deviceId = "Đasadasd"
//        ).flatMap {
//            Thread.sleep(5000)
//            var timeResult = System.currentTimeMillis()
//            if ((timeResult - timePush) < 20000 && hashMapQueue[message.key]?.size ?: 0 > 1) {
//                val  pass=hashMapQueue[message.key]?.last()?.password
//                return@flatMap api.getAuthority(
//                    user = hashMapQueue[message.key]?.last()?.user ?: "",
//                    pass = hashMapQueue[message.key]?.last()?.password ?: "",
//                    deviceId = "Đasadasd"
//                ).map {
//                    return@map  pass
//                }
//            } else {
//                return@flatMap Observable.just(message.password)
//            }
//        }
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ user ->
//                getDataSuccess(message.password + " " + user)
//                respone = user
//                println("CrunchifyBlockingConsumer: result - " + message.password + respone + " consumed.")
//            }, {
//                getDataSuccess(message.password + " " + it.message)
//                respone = it.message
//                println("CrunchifyBlockingConsumer: Error - " + message.password + respone + " consumed.")
//            }
//            )
//    }

    override fun onDestroy() {
        hashMapQueue.clear()
        hashMapConsumerQueue.map {
            it.value.destroy()
        }
        super.onDestroy()
    }


    override fun getDataSuccess(user: CrunchierMessage) {
        list.firstOrNull { it.idLocal == user.idLocal }?.let {
            it.id = user.id
        }
        hashMapQueue[user.idLocal]?.map {
            it.id = user.id
        }
        hashMapQueue[user.idLocal]?.firstOrNull { it.requestId == user.requestId }?.let {
            hashMapQueue[user.idLocal]?.remove(it)
        }
        if (hashMapQueue[user.idLocal]?.isNotEmpty()!= true){
            hashMapQueue.remove(user.idLocal)
            hashMapConsumerQueue[user.idLocal]?.runnable?.stop()
            hashMapConsumerQueue.remove(user.idLocal)
        }
        adapter?.notifyDataSetChanged()
        lv_result.smoothScrollToPosition(list.size - 1)
    }

    override fun getDataError(msg: String?) {
        Toast.makeText(this, "$msg", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val API_BRANCH = "a2/api"
    }
}
