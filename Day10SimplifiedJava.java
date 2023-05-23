import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * bracket matching -- to be used for an interview question
 */
public class Day10SimplifiedJava {

    private static final Map<String, String> openToValidClose = Map.of(
        "[", "]",
        "(", ")"
    );

    private static final Map<String, String> matchingOpen = Map.of(
        "]", "[",
        ")", "("
    );

    /**
     * Easiest: Input is a single character string, either a square or round paren. Return the matching bracket.
     */
    private static String getCorrespondingBracket(String in) {
        if (in.equals("[")) {
            return "]";
        } else if (in.equals("]")) {
            return "[";
        } else if (in.equals("(")) {
            return ")";
        } else {
            return "(";
        }
    }
    private static String getCorrespondingBracket2(String in) {
        switch(in) {
            case "(":
                return ")";
            case ")":
                return "(";
            case "]":
                return "[";
            case "[":
                return "]";
        }
    }

    /**
     * Easier: The input is a String containing round and square brackets. Return a String with all the corresponding brackets, in order.
     */
    private static String getCorrespondingBrackets(String in) {
        String out = "";

        for (int i = 0; i < in.length(); i++) {
            String thisChar = "" + in.charAt(i);
            out += getCorrespondingBracket(thisChar);
        }
        return out;
    }

    /**
     * The input is a String containing round and square brackets. Determine if the input has a mismatched closing bracket somewhere in it.
     *
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
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < in.length(); i++) {
            String thisChar = "" + in.charAt(i);
            if (openToValidClose.containsKey(thisChar)) {
                stack.push(thisChar);
            } else if (!stack.isEmpty() & openToValidClose.get(stack.peek()).equals(thisChar)) {
                stack.pop();
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Now determine if it's an incomplete String, so it's just missing some close brackets at the end
     *
     * Incomplete examples:
     * (
     * [[
     * ([](
     */
    private static Boolean isIncomplete(String in) {
        if (hasMismatchBracket(in)) {
            return false;
        }

        Stack<String> stack = new Stack<>();

        for (int i = 0; i < in.length(); i++) {
            String thisChar = "" + in.charAt(i);
            if (openToValidClose.containsKey(thisChar)) {
                stack.push(thisChar);
            } else if (!stack.isEmpty() & openToValidClose.get(stack.peek()).equals(thisChar)) {
                stack.pop();
            }
        }
        return !stack.isEmpty();
    }

    /**
     * If it's an incomplete String, return the rest of the String that would make it complete. Otherwise throw IllegalArgumentException.
     *
     * Examples:
     * Input: `(`      Output: `)`
     * Input: `[[`     Output: `]]`
     * Input: `([](`   Output: `))`
     */
    private static String getCompletedLine(String in) {
        if (hasMismatchBracket(in) || !isIncomplete(in)) {
            throw new IllegalArgumentException();
        }

        Stack<String> stack = new Stack<>();
        for (int i = 0; i < in.length(); i++) {
            String thisChar = "" + in.charAt(i);
            if (openToValidClose.containsKey(thisChar)) {
                stack.push(thisChar);
            } else if (!stack.isEmpty() & openToValidClose.get(stack.peek()).equals(thisChar)) {
                stack.pop();
            }
        }

        return stack.stream()
            .map(openToValidClose::get)
            .collect(Collectors.joining());
    }

    public static void main(String[] args) {
        assertFalse(hasMismatchBracket("()"));
        assertFalse(hasMismatchBracket("[]"));
        assertFalse(hasMismatchBracket("([])"));
        assertFalse(hasMismatchBracket("([][[()]])"));

        assertTrue(hasMismatchBracket("(]"));
        assertTrue(hasMismatchBracket("[()()())"));

        assertFalse(isIncomplete("()"));
        assertFalse(isIncomplete("[]"));
        assertFalse(isIncomplete("([])"));
        assertFalse(isIncomplete("([][[()]])"));
        assertFalse(isIncomplete("(]"));
        assertFalse(isIncomplete("[()()())"));

        assertTrue(isIncomplete("("));
        assertTrue(isIncomplete("[()()()"));

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

//  def main(args: Array[String]): Unit = {
//    println("hasMismatchBracket: Expect FALSE")
//    println("Test input: ()            Actual answer: " + hasMismatchBracket("()"))
//    println("Test input: []            Actual answer: " + hasMismatchBracket("[]"))
//    println("Test input: ([])          Actual answer: " + hasMismatchBracket("([])"))
//    println("Test input: ([][[()]])    Actual answer: " + hasMismatchBracket("([][[()]])"))
//    println()
//
//    println("hasMismatchBracket: Expect TRUE")
//    println("Test input: (]            Actual answer: " + hasMismatchBracket("(]"))
//    println("Test input: [()()())      Actual answer: " + hasMismatchBracket("[()()())"))
//    println()
//
//    println("isIncomplete: Expect FALSE")
//    println("Test input: ()            Actual answer: " + isIncomplete("()"))
//    println("Test input: []            Actual answer: " + isIncomplete("[]"))
//    println("Test input: ([])          Actual answer: " + isIncomplete("([])"))
//    println("Test input: ([][[()]])    Actual answer: " + isIncomplete("([][[()]])"))
//    println("Test input: (]            Actual answer: " + isIncomplete("(]"))
//    println("Test input: [()()())      Actual answer: " + isIncomplete("[()()())"))
//    println()
//
//    println("isIncomplete: Expect TRUE")
//    println("Test input: (            Actual answer: " + isIncomplete("("))
//    println("Test input: [()()()      Actual answer: " + isIncomplete("[()()()"))
//    println()
//
//    println("getCompletedLine: Expect exception")
//    try {
//      val result = getCompletedLine("()")
//      println("Test input: ()            Did not find expected exception. Result: " + result)
//    } catch {
//      case _: IllegalArgumentException => println("Test input: ()           Found exception")
//    }
//    try {
//      val result = getCompletedLine("[)")
//      println("Test input: [)           Did not find expected exception. Result: " + result)
//    } catch {
//      case _: IllegalArgumentException => println("Test input: [)           Found exception")
//    }
//    println()
//
//    println("getCompletedLine:")
//    println("Test input: (     Expected/Actual: ) " + getCompletedLine("("))
//    println("Test input: [[    Expected/Actual: ]] " + getCompletedLine("[["))
//    println("Test input: ([](  Expected/Actual: )) " + getCompletedLine("([]("))
//  }

}
