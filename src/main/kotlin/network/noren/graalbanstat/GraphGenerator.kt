package network.noren.graalbanstat

class GraphGenerator (mg: MessageGenerator) {
    val calcMonthWarns: MutableMap<String, Int> = mg.calcMonthWarns
    val calcMonthBans: MutableMap<String, Int> = mg.calcMonthBans
    val mY: String = mg.mY
}