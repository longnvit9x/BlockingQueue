package neo.vn.myapplication

interface MainView {
    fun getDataSuccess(user: CrunchierMessage)
     fun getDataError(msg: String?)
}