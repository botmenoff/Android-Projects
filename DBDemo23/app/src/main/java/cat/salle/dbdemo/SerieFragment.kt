package cat.salle.dbdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A fragment representing a list of Items.
 */
class SerieFragment : Fragment() {

    private var columnCount = 1
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        // Obtener instancia de la base de datos
        db = AppDatabase.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                // Fill paralel a la vista
                GlobalScope.launch {
                    // Utilizar el contexto para indicar que estamos en el fragment
                    val series = withContext(Dispatchers.IO) {
                        // Hacer la consulta para obtener los datos y pasarlos al adaptador
                        db.SerieDAO().loadAllSeries()
                    }
                    withContext(Dispatchers.Main) {
                        // Transformar el tipo List a una mutableList
                        val mutableSeries = series.toMutableList()
                        // Se lo paso al adapter
                        adapter = MySerieRecyclerViewAdapter(mutableSeries)

                    }
                }
            }


        }

        // Hacer la consulta para obtener los datos
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            SerieFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}