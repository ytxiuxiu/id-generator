package in.yutou.idgenerator;

/**
 * Distributed Unique Id Generator
 *
 * @author Yingchen Liu
 */
public class IdGenerator {

    /**
     * The basic number that the millisecond part of the id will count from
     */
    public static final long MILLIS_STARTS_FROM = 1462060800000l;  // 2016-05-01

    /**
     * How many digits that the millisecond part will use
     */
    public static final int DIGITS_MILLIS = 13;

    /**
     * How many digits that the node part will use
     */
    public static final int DIGITS_NODE = 3;

    /**
     * How many digits that the sequence part will use
     */
    public static final int DIGITS_SEQUENCE = 3;

    private static long currentIdMillis = 0;

    private static int currentSequence = 1;

    public IdGenerator() {

    }

    /**
     * Generate a new unique id
     *
     * @return the string format of the new unique id
     */
    public static String generateIdString() {
        long currentMillis = System.currentTimeMillis();

        String partNode = generateNode();
        String partSequence = generateSequence(currentMillis);
        String partMillis = generateMillis(currentMillis);

        return partMillis + partNode + partSequence;
    }

    /**
     * Generate a new unique id
     *
     * @return the long format of the new unique id
     */
    public static long generateId() {
        return Long.parseLong(generateIdString());
    }

    private static String generateMillis(long currentMillis) {
        return String.format("%0" + DIGITS_MILLIS + "d", currentMillis - MILLIS_STARTS_FROM);
    }

    private static String generateSequence(long currentMillis) {
        if (currentIdMillis == currentMillis) {
            currentSequence++;
            if (String.valueOf(currentSequence).length() > DIGITS_SEQUENCE) {
                throw new RuntimeException("Cannot generate that much id within one millisecond");
            }
        } else {
            currentSequence = 1;
        }
        currentIdMillis = currentMillis;

        return String.format("%0" + DIGITS_SEQUENCE + "d", currentSequence);
    }

    private static String generateNode() {
        String idNode = System.getenv().get("ID_NODE");

        if (idNode == null || idNode.trim().equals("")) {
            throw new RuntimeException("Environment variable ID_NODE has not been set");
        }
        idNode = idNode.trim();
        if (idNode.length() > DIGITS_NODE) {
            throw new RuntimeException("The length of ID_NODE has exceed the limitation");
        }

        int node;
        try {
            node = Integer.parseInt(idNode);
        } catch (Exception e) {
            throw new RuntimeException("ID_NODE is not a number");
        }

        return String.format("%0" + DIGITS_NODE + "d", node);
    }

}