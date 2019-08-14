package neo.vn.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ArrayBlockingQueue

class MainActivity : AppCompatActivity() {
    var crunchQueue = ArrayBlockingQueue<CrunchierMessage>(5)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val crunchProducer = CrunchifyBlockingProducer(crunchQueue)
        val crunchConsumer = CrunchifyBlockingConsumer(crunchQueue)

//        // starting producer to produce messages in queue
//         Thread(crunchProducer).start()

        // starting consumer to consume messages from queue
        Thread(crunchConsumer).start()

        System.out.println("Let's get started. Producer / Consumer Test Started.\n")
    }

    var i: Int = 1
    fun clickAdd(view: View) {
        crunchQueue.put(CrunchierMessage("longnvneo","12345678"))
        i++
    }

    companion object {
        const val API_BRANCH = "a2/api"
    }
}
