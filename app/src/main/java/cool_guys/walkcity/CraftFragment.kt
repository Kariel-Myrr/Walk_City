package cool_guys.walkcity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.craft_list_item.*
import cool_guys.walkcity.database.Manager

class CraftFragment : Fragment() {
    var imageResource: Int = R.drawable.stone
    var text1: String = ""
    var text2: String = ""
    var craftWood: Int = 0
    var craftStone: Int = 0
    var craftIron: Int = 0
    var craftType: Int = 0
    var craftId: Int = 0

    //val manager by lazy { Manager(requireContext()) }
    lateinit var manager: Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments
        if (arguments != null) {
            imageResource = arguments.getInt(IRKey)
            text1 = arguments.getString(t1).orEmpty()
            text2 = arguments.getString(t2).orEmpty()
            craftWood = arguments.getInt(cw)
            craftStone = arguments.getInt(cs)
            craftIron = arguments.getInt(ci)
            craftType = arguments.getInt(ct)
            craftId = arguments.getInt(cid)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = layoutInflater.inflate(R.layout.craft_list_item, null)
        val img = inflate.findViewById<ImageView>(R.id.imageView)
        val txt1 = inflate.findViewById<TextView>(R.id.craftTextView1)
        val txt2 = inflate.findViewById<TextView>(R.id.craftTextView2)

        val draw = ContextCompat.getDrawable(requireContext(), imageResource)
        img.setImageDrawable(draw)

        txt1.text = text1
        txt2.text = text2

        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        craft_list_button.setOnClickListener {
            Log.d(TAG, "Fragment test 1")
            if (craftType == 0) {
                manager.craftWeapon(0, craftId)
            }
            if (craftType == 1) {
                manager.craftProtection(0, craftId)
            }
            Log.d(TAG, "Fragment test 2")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        manager = Manager(context)
        manager.download()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "CraftFragment onStart()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "CraftFragment onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "CraftFragment onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "CraftFragment onDestroy()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "CraftFragment onResume()")
    }

    companion object {
        private const val IRKey = "imageResource"
        private const val t1 = "text1"
        private const val t2 = "text2"
        private const val cw = "craft_wood"
        private const val cs = "craft_stone"
        private const val ci = "craft_iron"
        private const val ct = "craft_type"
        private const val cid = "craft_id"

        private const val TAG = "CraftFragment"
    }
}