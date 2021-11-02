package raum.muchbeer.filecreator.ui.client

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.filecreator.databinding.FileItemBinding
import raum.muchbeer.filecreator.databinding.FragmentClientBinding
import java.io.File
import java.util.*

class ClientAdapter(val onClickListener : (File) -> Unit) : ListAdapter<File, ClientAdapter.ClientVH>(fileDiff){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientVH {
       val bind = FileItemBinding.inflate(
           LayoutInflater.from(parent.context), parent, false
       )
        return ClientVH(bind)
    }

    override fun onBindViewHolder(holder: ClientVH, position: Int) {
       holder.bindData(getItem(position))
    }


    companion object fileDiff : DiffUtil.ItemCallback<File>() {
        override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
          return oldItem.absolutePath == newItem.absolutePath
        }

        override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem == newItem
        }

    }
   inner class ClientVH(val binding: FileItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bindData(file : File) {
                binding.apply {

                    name.text = file.name
                    val calendar = Calendar.getInstance()
                    val dateFormat = DateFormat.getDateFormat(itemView.context)
                    calendar.timeInMillis = file.lastModified()
                    date.text = dateFormat.format(calendar.time)
                    card.setOnClickListener {
                        onClickListener(file)
                    }
                }
            }
    }


}