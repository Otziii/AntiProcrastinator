package com.jorfald.antiprocrastinator.antiprocrastinator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_things_list.*
import java.util.*




class ThingsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ThingsToDoAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mainActivity: MainActivity

    private var arrayOfThingToDo = listOf<ThingToDo>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_things_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity

        mainActivity.setRandomButton(View.OnClickListener {
            val random = Random()
            val itemId = random.nextInt(arrayOfThingToDo.size - 1)
            val item = arrayOfThingToDo[itemId]
            val minutes = random.nextInt(60) + 1
            item.minutes = minutes

            mainActivity.showFragment(ThingsDetailsFragment(item))
        })

        mainActivity.setDeleteButton(null)

        viewManager = LinearLayoutManager(this.context)
        val clickListener = object : ThingsToDoAdapter.OnItemClickListener {
            override fun onItemClick(item: ThingToDo) {
                mainActivity.showFragment(ThingsDetailsFragment(item))
            }
        }

        viewAdapter = ThingsToDoAdapter(arrayOfThingToDo, clickListener)

        recyclerView = things_to_do_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        fab.setOnClickListener {
            mainActivity.showFragment(AddNewThingFragment())
        }

        Thread {
            arrayOfThingToDo = mainActivity.db.userDao().getAll()
            viewAdapter.updateData(arrayOfThingToDo)
        }.start()
    }

    private class ThingsToDoAdapter(private var thingsToDo: List<ThingToDo>, val listener: OnItemClickListener) :
        RecyclerView.Adapter<ThingsToDoAdapter.ViewHolder>() {

        interface OnItemClickListener {

            fun onItemClick(item: ThingToDo)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item,
                    parent,
                    false
                ) as LinearLayout
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(thingsToDo[position], listener)
        }

        fun updateData(items: List<ThingToDo>) {
            thingsToDo = items
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = thingsToDo.size

        private class ViewHolder(val listItemView: LinearLayout) :
            RecyclerView.ViewHolder(listItemView) {

            fun bind(item: ThingToDo, listener: OnItemClickListener) {
                val textView = this.listItemView.findViewById<TextView>(R.id.item_text_view)
                textView.text = item.toDoName
                itemView.setOnClickListener { listener.onItemClick(item) }
            }
        }
    }
}
