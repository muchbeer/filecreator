package raum.muchbeer.filecreator.ui.clients

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.filecreator.data.Client
import raum.muchbeer.filecreator.databinding.ClientItemBinding
import java.util.*

class ClientsAdapter(val clickListener : (Int)-> Unit) :
        ListAdapter<Client, ClientsAdapter.ClientVH>(clientDifUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientVH {
       val bind = ClientItemBinding.inflate(LayoutInflater.from(parent.context),
                                        parent, false)

        return ClientVH(bind)
    }

    override fun onBindViewHolder(holder: ClientVH, position: Int) {
        holder.bindData(getItem(position))
    }

  inner  class ClientVH(val binding: ClientItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(client: Client) {
            binding.apply {
                card.setOnClickListener { clickListener(client.id) }

                name.text = client.name

                val calendar = Calendar.getInstance()
                val dateFormat = DateFormat.getDateFormat(itemView.context)
                calendar.timeInMillis = client.date
                date.text = dateFormat.format(calendar.time)
            }
        }
    }

    companion object clientDifUtil : DiffUtil.ItemCallback<Client>() {
        override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
            TODO("Not yet implemented")
        }

    }

}
interface OnClientClickListener {
    fun onClick(id: Int)
}