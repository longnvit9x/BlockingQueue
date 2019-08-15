package neo.vn.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class ListAdapter : BaseAdapter {
    constructor(list: ArrayList<CrunchierMessage>, context: Context) : super() {
        this.listData = list
        this.context = context
        layoutInflater = LayoutInflater.from(context)
    }

    var layoutInflater: LayoutInflater
    var listData: ArrayList<CrunchierMessage>
    var context: Context
    override fun getCount(): Int {
        return listData.size
    }

    override fun getItem(position: Int): CrunchierMessage {
        return listData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null)
            holder = ViewHolder()
            holder.tvDetail = convertView.findViewById<View>(R.id.tv_detail) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val detail = this.listData.get(position)
        holder.tvDetail!!.text = "${detail.id}- ${detail.user} - ${detail.password}-${detail.requestId} - ${detail.idLocal}"
        return convertView!!
    }

    internal class ViewHolder {
        var tvDetail: TextView? = null
    }
}