package neo.vn.myapplication

import java.util.concurrent.BlockingQueue


class CrunchifyBlockingProducer(val queue: BlockingQueue<CrunchierMessage>) : Runnable {

    override fun run() {
        // producing CrunchierMessage messages
        for (i in 1..5) {
            val msg = CrunchierMessage("i'm msg $i","1324")
            try {
                queue.put(msg)
                System.out.println("CrunchifyBlockingProducer: Message - " + msg.user + " produced.")
            } catch (e: Exception) {
                println("Exception:$e")
            }

        }

    }
}