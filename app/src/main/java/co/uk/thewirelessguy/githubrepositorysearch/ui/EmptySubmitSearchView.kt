package co.uk.thewirelessguy.githubrepositorysearch.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView

/**
 * EmptySubmitSearchView extends SearchView so that query submit gets triggered even when empty
 */

class EmptySubmitSearchView : SearchView {
    
    lateinit var mSearchSrcTextView: SearchAutoComplete
    var listener: OnQueryTextListener? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun setOnQueryTextListener(listener: OnQueryTextListener?) {
        super.setOnQueryTextListener(listener)
        this.listener = listener
        mSearchSrcTextView = this.findViewById(androidx.appcompat.R.id.search_src_text)
        mSearchSrcTextView.setOnEditorActionListener { textView, i, keyEvent ->
            listener?.onQueryTextSubmit(query.toString())
            true
        }
    }
}