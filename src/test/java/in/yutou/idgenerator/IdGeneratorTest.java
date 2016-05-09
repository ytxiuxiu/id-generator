package in.yutou.idgenerator;

import org.junit.Test;

/**
 * Id Generator Test
 *
 * @author Yingchen Liu
 */
public class IdGeneratorTest {

    @Test
    public void testGenerateId() {

        for (int i = 0; i < 100; i++) {
            System.out.println(IdGenerator.generateId());
        }

    }

}
