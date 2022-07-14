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

class CraftFragment() : Fragment(){
    var imageResource: Int = cool_guys.walkcity.R.drawable.stone
    var text1: String = ""
    var text2: String = ""
    var craft_wood : Int = 0
    var craft_stone : Int = 0
    var craft_iron : Int = 0
    var craft_type : Int = 0
    var craft_id : Int = 0
    //val manager by lazy { Manager(requireContext()) }
    lateinit var manager : Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments
        if (arguments != null) {
            imageResource = arguments.getInt(IRKey)
            text1 = arguments.getString(t1)
            text2 = arguments.getString(t2)
            craft_wood = arguments.getInt(cw)
            craft_stone = arguments.getInt(cs)
            craft_iron = arguments.getInt(ci)
            craft_type = arguments.getInt(ct)
            craft_id = arguments.getInt(cid)
        }
    }

    companion object {
        private const val IRKey = "imageResource"
        private const val t1 = "text1"
        private const val t2 = "text2"
        private const val cw = "craft_wood"
        private const val cs = "carft_stone"
        private const val ci = "craft_iron"
        private const val ct = "craft_type"
        private const val cid = "craft_id"

        fun create(imageResource: Int, text1 : String, text2: String, craft_wood : Int, craft_stone : Int, craft_iron : Int, craft_type : Int, craft_id : Int) = CraftFragment().apply {
            var bundle = Bundle()

            bundle.putInt(IRKey, imageResource)
            bundle.putString(t1, text1)
            bundle.putString(t2, text2)
            bundle.putInt(cw, craft_wood)
            bundle.putInt(cs, craft_stone)
            bundle.putInt(ci, craft_iron)
            bundle.putInt(ct, craft_type)
            bundle.putInt(cid, craft_id)
            arguments = bundle
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = layoutInflater.inflate(R.layout.craft_list_item, null)
        var img = inflate.findViewById<ImageView>(R.id.imageView)
        var txt1 = inflate.findViewById<TextView>(R.id.craftTextView1)
        var txt2 = inflate.findViewById<TextView>(R.id.craftTextView2)

        var draw = ContextCompat.getDrawable(requireContext(), imageResource)
        img.setImageDrawable(draw)

        txt1.setText(text1)
        txt2.setText(text2)

        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        craft_list_button.setOnClickListener{
            Log.d("FLAG_TAG", "Fragment test 1")
            if(craft_type == 0){
                manager.craftWeapon(0,craft_id)
            }
            if(craft_type == 1){
                manager.craftProtection(0, craft_id)
            }
            Log.d("FLAG_TAG", "Fragment test 2")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        manager = Manager(context)
        manager.download()
    }

    override fun onStart() {
        super.onStart()
        Log.d("FLAG_TAG", "CraftFragment onStart()")

    }

    override fun onPause() {
        super.onPause()
        Log.d("FLAG_TAG", "CraftFragment onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FLAG_TAG", "CraftFragment onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FLAG_TAG", "CraftFragment onDestroy()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FLAG_TAG", "CraftFragment onResume()")
    }
}