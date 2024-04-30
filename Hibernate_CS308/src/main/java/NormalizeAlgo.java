/* A class involving Normalization Theoretic Algorithms.
 * No need to review it but if it peeks your interest...
 * Go right ahead. */

import java.util.*;

public class NormalizeAlgo {
    public static void main(String[] args) {
        // Example sets of functional dependencies
        Set<FunctionalDependency> F = new HashSet<>();
        Set<FunctionalDependency> E = new HashSet<>();

        F.add(new FunctionalDependency(new HashSet<>(Collections.singletonList("A")),
                new HashSet<>(Collections.singletonList("C"))));
        F.add(new FunctionalDependency(new HashSet<>(Arrays.asList("A", "C")),
                new HashSet<>(Collections.singletonList("D"))));
        F.add(new FunctionalDependency(new HashSet<>(Collections.singletonList("E")),
                new HashSet<>(Arrays.asList("A", "D"))));
        F.add(new FunctionalDependency(new HashSet<>(Collections.singletonList("E")),
                new HashSet<>(Collections.singletonList("H"))));
        E.add(new FunctionalDependency(new HashSet<>(Collections.singletonList("A")),
                new HashSet<>(Arrays.asList("C", "D"))));
        E.add(new FunctionalDependency(new HashSet<>(Collections.singletonList("E")),
                new HashSet<>(Arrays.asList("A", "H"))));

        // Check if F covers E
        boolean coversE = FunctionalDependencyCoverage.covers(F, E);
        System.out.println("Does F cover E? " + coversE);

        // Check if E covers F
        boolean coversF = FunctionalDependencyCoverage.covers(E, F);
        System.out.println("Does E cover F? " + coversF);

        // Check if F and E are equivalent
        boolean areEquivalent = coversE && coversF;
        System.out.println("Are F and E equivalent? " + areEquivalent);
    }
}

class FunctionalDependency {
    Set<String> leftSide;
    Set<String> rightSide;

    public FunctionalDependency(Set<String> leftSide, Set<String> rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        return leftSide + " -> " + rightSide;
    }
}

class ClosureFinder {
    Set<String> closure(Set<String> attributes, Set<FunctionalDependency> fds) {
        Set<String> closure = new HashSet<>(attributes);
        boolean changed;
        do {
            changed = false;
            for (FunctionalDependency fd : fds) {
                if (closure.containsAll(fd.leftSide) && !closure.containsAll(fd.rightSide)) {
                    closure.addAll(fd.rightSide);
                    changed = true;
                }
            }
        } while (changed);
        System.out.println("Attributes: " + attributes + ", Closure: " + closure);
        return closure;
    }
}

class KeyFinder {
    Set<String> findKey(Set<String> attributes, Set<FunctionalDependency> fds) {
        ClosureFinder closureFinder = new ClosureFinder();
        Set<String> candidateKey = new HashSet<>(attributes);
        for (String attr : attributes) {
            Set<String> temp = new HashSet<>(candidateKey);
            temp.remove(attr);
            if (closureFinder.closure(temp, fds).equals(attributes)) {
                candidateKey.remove(attr);
            }
        }
        return candidateKey;
    }
}

class MinimalCoverFinder {
    Set<FunctionalDependency> findMinimalCover(Set<FunctionalDependency> fds) {
        Set<FunctionalDependency> minimalCover = new HashSet<>(fds);

        // Step 1: Remove extraneous attributes from right side of each FD
        for (FunctionalDependency fd : fds) {
            Set<String> rightSide = new HashSet<>(fd.rightSide);
            for (String attr : rightSide) {
                Set<String> modifiedRightSide = new HashSet<>(rightSide);
                modifiedRightSide.remove(attr);
                Set<FunctionalDependency> temp = new HashSet<>(minimalCover);
                temp.remove(fd);
                temp.add(new FunctionalDependency(fd.leftSide, modifiedRightSide));
                if (new ClosureFinder().closure(fd.leftSide, temp).containsAll(fd.rightSide)) {
                    minimalCover.remove(fd);
                    minimalCover.add(new FunctionalDependency(fd.leftSide, modifiedRightSide));
                }
            }
        }

        // Step 2: Remove redundant FDs
        Set<FunctionalDependency> removed = new HashSet<>();
        for (FunctionalDependency fd1 : minimalCover) {
            Set<FunctionalDependency> temp = new HashSet<>(minimalCover);
            temp.remove(fd1);
            if (new ClosureFinder().closure(fd1.leftSide, temp).containsAll(fd1.rightSide)) {
                removed.add(fd1);
            }
        }
        minimalCover.removeAll(removed);

        return minimalCover;
    }
}


class FunctionalDependencyCoverage {
    public static boolean covers(Set<FunctionalDependency> F, Set<FunctionalDependency> E) {
        for (FunctionalDependency fd : E) {
            Set<String> X = fd.leftSide;
            @SuppressWarnings("SuspiciousNameCombination")
            Set<String> Y = fd.rightSide;
            Set<String> closure = calculateClosure(X, F);
            if (!closure.containsAll(Y)) {
                return false;
            }
        }
        return true;
    }

    private static Set<String> calculateClosure(Set<String> X, Set<FunctionalDependency> F) {
        Set<String> closure = new HashSet<>(X);
        boolean changed;
        do {
            changed = false;
            for (FunctionalDependency fd : F) {
                if (closure.containsAll(fd.leftSide) && !closure.containsAll(fd.rightSide)) {
                    closure.addAll(fd.rightSide);
                    changed = true;
                }
            }
        } while (changed);
        return closure;
    }
}