package cool_guys.walkcity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.craft_list_item.*
import cool_guys.walkcity.DataBase.Manager

class CraftFragment : Fragment(){
    var imageResource: Int = 0
    var text1: String? = null
    var text2: String? = null
    var craft_wood : Int = 0
    var craft_stone : Int = 0
    var craft_iron : Int = 0
    var craft_type : Int = 0
    var craft_id : Int = 0
    private val manager by lazy { Manager(requireContext()) }

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
        return inflater!!.inflate(R.layout.craft_list_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        craft_list_button.setOnClickListener{
            if(craft_type == 0){
                manager.craftWeapon(0,craft_id)
            }
            if(craft_type == 1){
                manager.craftProtection(0, craft_id)
            }
        }
    }
}