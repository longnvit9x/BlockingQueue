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
    var consumerName = 1
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
            val crunchConsumer = CrunchifyBlockingConsumer("$consumerName", this, crunchQueue)
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
            val crunchConsumer = CrunchifyBlockingConsumer("$consumerName", this, crunchQueue)
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
            val crunchConsumer = CrunchifyBlockingConsumer("$consumerName", this, crunchQueue)
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
            val crunchConsumer = CrunchifyBlockingConsumer("$consumerName", this, crunchQueue)
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
            try {
                hashMapConsumerQueue[user.idLocal]?.interrupt()
            } catch (ex: Exception) {
                println(ex.message)
            }
            hashMapConsumerQueue.remove(user.idLocal)
        }
        adapter?.notifyDataSetChanged()
        lv_result.smoothScrollToPosition(list.size - 1)
    }

    fun clickStart(view: View) {
        hashMapConsumerQueue.map {
            it.value.start()
             it.value.runnable?.start()
        }
    }

    fun clickStop(view: View) {
        hashMapConsumerQueue.map {
            it.value.runnable?.stop()
            try {
                it.value.interrupt()
            } catch (ex: Exception) {
                println(ex.message)
            }
        }

    }

    override fun getDataError(msg: String?) {
        Toast.makeText(this, "$msg", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val API_BRANCH = "a2/api"
    }
}
