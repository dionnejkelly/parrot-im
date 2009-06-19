package model;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsForModel {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for model");
        //$JUnit-BEGIN$
        suite.addTestSuite(TestBlocking.class);
        //$JUnit-END$
        return suite;
    }

}
