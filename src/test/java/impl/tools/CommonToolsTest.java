package impl.tools;

import org.junit.Assert;
import org.junit.Test;

public class CommonToolsTest {
    @Test
    public void test_IsEqual() throws Exception {
        // Arrange
        int value = 1;
        int sameValue = 1;

        // Act
        boolean result = CommonTools.isEqual(value, sameValue);

        // Assert
        Assert.assertTrue(result);
    }

}