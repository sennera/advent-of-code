
public class BracketMatchingPart2And3 {

    /********************** PART 2 **********************/
    /**
     * Now determine if it's an incomplete String, so it's just missing some close brackets at the end
     * <p>
     * Incomplete examples: ( [[ ([](
     */
    private static Boolean isIncomplete(String in) {
        return true;
    }

    /********************** PART 3 **********************/
    /**
     * If it's an incomplete String, return the rest of the String that would make it complete. Otherwise throw IllegalArgumentException.
     * <p>
     * Examples: Input: `(`      Output: `)` Input: `[[`     Output: `]]` Input: `([](`   Output: `))`
     */
    private static String getCompletedLine(String in) {
        return "";
    }

    public static void main(String[] args) {
    /********************** PART 2 **********************/
        assertFalse(isIncomplete("()"));
        assertFalse(isIncomplete("[]"));
        assertFalse(isIncomplete("([])"));
        assertFalse(isIncomplete("([][[()]])"));
        assertFalse(isIncomplete("(]"));
        assertFalse(isIncomplete("[()()())"));

        assertTrue(isIncomplete("("));
        assertTrue(isIncomplete("[()()()"));

        /********************** PART 3 **********************/
        try {
            String result = getCompletedLine("()");
            throw new RuntimeException("Did not find expected exception. Result: " + result);
        } catch (IllegalArgumentException e) {
            // success
        } catch (Exception e) {
            throw new RuntimeException("Did not find expected exception. Exception: " + e);
        }
        try {
            String result = getCompletedLine("[)");
            throw new RuntimeException("Did not find expected exception. Result: " + result);
        } catch (IllegalArgumentException e) {
            // success
        } catch (Exception e) {
            throw new RuntimeException("Did not find expected exception. Exception: " + e);
        }

        assertTrue(getCompletedLine("(").equals(")"));
        assertTrue(getCompletedLine("[[").equals("]]"));
        assertTrue(getCompletedLine("([](").equals("))"));
    }

    // **** Below is given in part 1 ****

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