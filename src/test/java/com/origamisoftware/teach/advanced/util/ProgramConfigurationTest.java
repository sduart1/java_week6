
package com.origamisoftware.teach.advanced.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * Unit tests for Program Configuration
 */
public class ProgramConfigurationTest {
    
    /**
     * test the getApplicationProperties() method in ProgramConfiguration
     * Should come back as null
     */
    @Test
    public void testConfig(){
        
        assertEquals("Make sure null is returned", ProgramConfiguration.getApplicationProperties(), null);
        
    }
    
}
