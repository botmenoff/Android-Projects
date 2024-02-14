package cat.salle.dbdemo

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import cat.salle.dbdemo.placeholder.PlaceholderContent.PlaceholderItem
import cat.salle.dbdemo.databinding.FragmentItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MySerieRecyclerViewAdapter(
    private val values: MutableList<Serie>
) : RecyclerView.Adapter<MySerieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.firstName.toString()
        holder.contentView.text = item.lastName.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.firstName
        val contentView: TextView = binding.lastName

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}