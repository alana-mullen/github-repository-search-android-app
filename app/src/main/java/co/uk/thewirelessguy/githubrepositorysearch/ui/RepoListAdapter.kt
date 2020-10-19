package co.uk.thewirelessguy.githubrepositorysearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.uk.thewirelessguy.githubrepositorysearch.BR
import co.uk.thewirelessguy.githubrepositorysearch.R
import co.uk.thewirelessguy.githubrepositorysearch.model.Repositories

class RepoListAdapter(private var items: MutableList<Repositories.Item>, private val listener: (Repositories.Item) -> Unit) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.repo_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount() = items.size

    internal fun setData(data: MutableList<Repositories.Item>?) {
        // Assign the list to the RecyclerView. If data is null, assign an empty list to the adapter.
        this.items = data ?: mutableListOf()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repositories.Item, listener: (Repositories.Item) -> Unit) = with(binding.root) {
            // Use data binding to display data in the views:
            binding.setVariable(BR.viewModel, item)
            binding.executePendingBindings()
            setOnClickListener { listener(item) }
        }

    }
}
