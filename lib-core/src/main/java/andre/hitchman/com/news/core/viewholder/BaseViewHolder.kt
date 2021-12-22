package andre.hitchman.com.news.core.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<in T, B : ViewBinding> private constructor(
    val binding: B
) : RecyclerView.ViewHolder(binding.root) {

    protected val context: Context by lazy { binding.root.context }

    constructor(
        parent: ViewGroup,
        creator: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> B
    ) : this(creator(LayoutInflater.from(parent.context), parent, false))

    abstract fun bind(item: T)
}