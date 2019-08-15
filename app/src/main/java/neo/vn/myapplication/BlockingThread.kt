package neo.vn.myapplication

class BlockingThread : Thread {
    var runnable: CrunchifyBlockingConsumer? = null

    constructor(runnable: CrunchifyBlockingConsumer) : super(runnable) {
        this.runnable = runnable
    }

}