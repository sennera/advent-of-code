public class BracketMatcher {

    /**
     * The input is a String containing round and square brackets. Determine if the input has a mismatched closing bracket somewhere in it.
     */
    /**
     * Matching:
     * ()
     * []
     * ([])
     * ([][[()]])
     *
     * Not matching:
     * (]
     * [()()())
     */
    private static Boolean hasMismatchBracket(String in) {
        return true;
    }

    public static void main(String[] args) {
        assertFalse(hasMismatchBracket("()"));
        assertFalse(hasMismatchBracket("[]"));
        assertFalse(hasMismatchBracket("([])"));
        assertFalse(hasMismatchBracket("([][[()]])"));

        assertTrue(hasMismatchBracket("(]"));
        assertTrue(hasMismatchBracket("[()()())"));

        System.out.println("All tests passed!");
    }

    private static void assertTrue(boolean b) {
        if (!b) {
            throw new AssertionError(b + " was expected to be true");
        }
    }

    private static void assertFalse(boolean b) {
        if (b) {
            throw new AssertionError(b + " was expected to be false");
        }
    }

}

