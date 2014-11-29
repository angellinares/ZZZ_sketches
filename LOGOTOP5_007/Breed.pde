import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Breed - Store all the agents of a breed
 */
class Breed {
	HashMap<Integer, Agent> breeds;

	Breed() {
		breeds = new HashMap<Integer, Agent>();
	}

	void addAgent(Integer id, Agent agent) {
		breeds.put(id, agent);
	}

	Agent getAgent(Integer id) {
		return breeds.get(id);
	}

	HashMap<Integer, Agent> getBreedMap() {
		return breeds;
	}

	Set<Agent> getAgents() {
		return new HashSet<Agent>(breeds.values());
	}
}
