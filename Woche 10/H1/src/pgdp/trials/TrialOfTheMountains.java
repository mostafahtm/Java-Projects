package pgdp.trials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class TrialOfTheMountains {

	protected TrialOfTheMountains() {
		throw new UnsupportedOperationException();
	}

	public static void assignBeasts(Mountain[] mountains) {
		Beast[] beasts = Beast.values();
		Beast chosenBeast;
		for (int i = 0; i < mountains.length; i++) {
			Set<Beast> availabeBeasts = new HashSet<>(Arrays.asList(beasts));
			if (mountains[i].getBeast() != null) {
				continue;
			}

			List<Beast> beastsOfNeighbourMountains = mountains[i].getNeighbours().stream()
					.map(neighbour -> neighbour.getBeast()).collect(Collectors.toList());
			availabeBeasts.removeAll(beastsOfNeighbourMountains);

			chosenBeast = availabeBeasts.stream().findAny().get();
			mountains[i].setBeast(chosenBeast);
		}
	}

	public static class Mountain {
		private static long id = 0;
		private final long label;
		private Beast beast;
		private final Set<Mountain> neighbours;

		public Mountain() {
			label = id++;
			neighbours = new HashSet<>();
		}

		public long getLabel() {
			return label;
		}

		public void addNeighbour(Mountain m) {
			neighbours.add(m);
		}

		public Set<Mountain> getNeighbours() {
			return neighbours;
		}

		public void setBeast(Beast b) {
			beast = b;
		}

		public Beast getBeast() {
			return beast;
		}

		@Override
		public String toString() {
			return "("
					+ label + ", " + beast.name() + ") -> {" + neighbours.stream()
							.map(m -> "(" + m.label + ", " + m.beast.name() + ")").collect(Collectors.joining(", "))
					+ "}";
		}
	}

	public enum Beast {
		Alghoul, Alp, Archespore, Armored_Hound, Barghest, Basilisk, BeannShie, Bloedzuiger, Bruxa, Cemetaur,
		Coccacidium, Cockatrice, Corpus_Custodia, Dragon, Dragon_Worshipper, Devourer, Dog, Draconid, Drowned_Dead,
		Drowner, Echinops, Fleder, Frightener, Garkain, Ghost, Ghoul, Giant_Centipede, Golem, Gravier, Greater_Brother,
		Greater_Cockatrice, Greater_Mutatnt, Grigg, Hellhound, Ifrit, Insectoid, Kikimore, Kikimore_Queen,
		Kikimore_Warrior, Kikimore_Worker, Koshchey, Mamune, Mutant, Necrophage, Nightwraith, Noonwraith, Ozzrel,
		Royal_Wyvern, Skullhead, Striga, ureus, Vedymin, Vesper, Vodyanoi_Priest, Vodyanoi_Warrior, Voref, Warg,
		Werewolf, Wraith, Wyvern, Zephyr, Zeugl;
	}

	public static void main(String... args) {
		Mountain[] mountains = { new Mountain(), new Mountain(), new Mountain() };
		mountains[0].addNeighbour(mountains[1]);
		mountains[0].addNeighbour(mountains[2]);
		mountains[1].addNeighbour(mountains[0]);
		mountains[2].addNeighbour(mountains[0]);
		mountains[2].addNeighbour(mountains[1]);

		assignBeasts(mountains);
		System.out.println(Arrays.toString(mountains));

	}

}
