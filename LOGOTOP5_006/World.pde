import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class World - Store all the breeds
 */
class World {
	HashMap<String, Breed> world;

	World() {
		world = new HashMap<String, Breed>();
	}

	void addBreed(String breedName, Breed breed) {
		world.put(breedName, breed);
	}

	Breed getBreed(String breed) {
		return world.get(breed);
	}

	HashMap<String, Breed> getBreedMap() {
		return world;
	}

	Set<String> getBreedNames() {
		return world.keySet();
	}

	Set<Breed> getBreeds() {
		return new HashSet<Breed>(world.values());
	}
}
