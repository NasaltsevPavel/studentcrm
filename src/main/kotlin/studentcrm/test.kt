package studentcrm

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

class test {


}  fun main(args: Array<String>) {
    val jsonS = "{\n" +
            "    \"Cat1\" : 108,\n" +
            "    \"Cat2\" : 56\n" +
            "} "
    val mapper = ObjectMapper()
    val node: JsonNode = mapper.readTree(jsonS)
    val json: String = mapper.writeValueAsString(node)
    val data: MutableMap<String, Int> = mutableMapOf()
    data.put("CS", 1);
    data.put("Linux", 2);
    data.put("Kotlin", 3);
   // map["Cat1"] = node.get("Cat1").asText().toInt()
    val jsonNode = mapper.valueToTree<JsonNode>(data)

    println(jsonNode)
}