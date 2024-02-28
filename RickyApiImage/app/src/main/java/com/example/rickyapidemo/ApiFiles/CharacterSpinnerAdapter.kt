import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myapplication.ApiFiles.Character

class CharacterSpinnerAdapter(context: Context, characters: List<Character>) :
    ArrayAdapter<Character>(context, 0, characters) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_item, parent, false)

        val characterNameTextView = view.findViewById<TextView>(android.R.id.text1)
        val character = getItem(position)
        characterNameTextView.text = character?.name

        return view
    }
}
