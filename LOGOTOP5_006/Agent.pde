import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Agent - Store all the variables of an agent
 */
class Agent {
	Integer id;
	HashMap<String, Object> variables;

	Agent(Integer id) {
		variables = new HashMap<String, Object>();
		this.id = id;
	}
	
	Agent(Integer id, String varName, Object varValue) {
		variables = new HashMap<String, Object>();
		this.id = id;
		
		variables.put(varName, varValue);
	}

	Integer getId() {
		return id;
	}

	void setId(Integer ident) {
		id = ident;
	}

	void add(String name, Object variable) {
		variables.put(name, variable);
	}

	Object get(String name) {
		return variables.get(name);
	}

	HashMap<String, Object> getAgentMap() {
		return variables;
	}

	Set<String> getVariableNames() {
		return variables.keySet();
	}

	Set<Object> getVariables() {
		return new HashSet<Object>(variables.values());
	}
}
