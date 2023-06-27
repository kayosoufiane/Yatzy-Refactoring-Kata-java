package com.yatzy.domain.service;

import java.util.Arrays;

/**
 * The game of Yatzy is a simple dice game.
 */
public class Yatzy {

	private final int[] dice;

	public Yatzy(int d1, int d2, int d3, int d4, int d5) {
		dice = new int[] { d1, d2, d3, d4, d5 };
	}

	public int chance() {
		return Arrays.stream(dice).sum();
	}

	public int yatzy() {
		return Arrays.stream(dice).distinct().count() == 1 ? 50 : 0;
	}

	public int ones() {
		return countSpecificValue(1);
	}

	public int twos() {
		return countSpecificValue(2) * 2;
	}

	public int threes() {
		return countSpecificValue(3) * 3;
	}

	public int fours() {
		return countSpecificValue(4) * 4;
	}

	public int fives() {
		return countSpecificValue(5) * 5;
	}

	public int sixes() {
		return countSpecificValue(6) * 6;
	}

	public int scorePair() {
		return scoreMultiples(2);
	}

	public int twoPair() {
		return scoreTwoPairs(2);
	}

	public int fourOfAKind() {
		return scoreMultiples(4);
	}

	public int threeOfAKind() {
		return scoreMultiples(3);
	}

	public int smallStraight() {
		return Arrays.stream(dice).distinct().count() == 5 && Arrays.stream(dice).min().getAsInt() == 1
				&& Arrays.stream(dice).max().getAsInt() == 5 ? 15 : 0;
	}

	public int largeStraight() {
		return Arrays.stream(dice).distinct().count() == 5 && Arrays.stream(dice).min().getAsInt() == 2
				&& Arrays.stream(dice).max().getAsInt() == 6 ? 20 : 0;
	}

	public int fullHouse() {
		int[] counts = countOccurrences();
		boolean hasTwo = false;
		boolean hasThree = false;

		for (int count : counts) {
			if (count == 2) {
				hasTwo = true;
			}
			if (count == 3) {
				hasThree = true;
			}
		}

		if (hasTwo && hasThree) {
			return Arrays.stream(dice).sum();
		} else {
			return 0;
		}
	}

	private int[] countOccurrences() {
		int[] counts = new int[6];
		for (int die : dice) {
			counts[die - 1]++;
		}
		return counts;
	}

	private int countSpecificValue(int value) {
		return (int) Arrays.stream(dice).filter(die -> die == value).count();
	}

	private int scoreMultiples(int targetCount) {
		int[] counts = countOccurrences();
		for (int i = 5; i >= 0; i--) {
			if (counts[i] >= targetCount) {
				return (i + 1) * targetCount;
			}
		}
		return 0;
	}

	private int scoreTwoPairs(int targetCount) {
		int[] counts = countOccurrences();
		int pairCount = 0;
		int score = 0;
		for (int i = 5; i >= 0; i--) {
			if (counts[i] >= targetCount) {
				pairCount++;
				score += (i + 1) * targetCount;
			}
		}
		if (pairCount == 2) {
			return score;
		} else {
			return 0;
		}
	}
}
