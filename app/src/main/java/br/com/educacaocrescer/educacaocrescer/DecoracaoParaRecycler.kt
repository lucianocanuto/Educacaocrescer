package br.com.educacaocrescer.educacaocrescer

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DecoracaoParaRecycler(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: android.graphics.Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = space
    }
}
