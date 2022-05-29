import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.databinding.VoteItemBinding
import com.example.mannayoclient.databinding.WriteItemBinding
import com.example.mannayoclient.writelist.WriteModel

class WriteRVAdapter(private val viewModel: WriteModel) :
    RecyclerView.Adapter<WriteRVAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: WriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            val item = viewModel.getItem(pos)
            binding.vote1.text = item.contents
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WriteItemBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //
        holder.setContents(position)
    }

    override fun getItemCount() = viewModel.itemsSize
}

