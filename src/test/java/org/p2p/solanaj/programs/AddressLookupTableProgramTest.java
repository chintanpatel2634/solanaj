package org.p2p.solanaj.programs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.TransactionInstruction;

import java.util.Collections;

public class AddressLookupTableProgramTest {

    private static final PublicKey AUTHORITY = new PublicKey("BPFLoaderUpgradeab1e11111111111111111111111");
    private static final PublicKey PAYER = new PublicKey("11111111111111111111111111111111");
    private static final PublicKey LOOKUP_TABLE = new PublicKey("AddressLookupTab1e1111111111111111111111111");
    private static final long RECENT_SLOT = 123456;

    /**
     * Test for creating a lookup table.
     */
    @Test
    public void testCreateLookupTable() {
        TransactionInstruction instruction = AddressLookupTableProgram.createLookupTable(AUTHORITY, PAYER, RECENT_SLOT);
        assertNotNull(instruction);
        assertEquals(AddressLookupTableProgram.PROGRAM_ID, instruction.getProgramId());
        assertEquals(4, instruction.getKeys().size()); // Check number of keys
    }

    /**
     * Test for freezing a lookup table.
     */
    @Test
    public void testFreezeLookupTable() {
        TransactionInstruction instruction = AddressLookupTableProgram.freezeLookupTable(LOOKUP_TABLE, AUTHORITY);
        assertNotNull(instruction);
        assertEquals(AddressLookupTableProgram.PROGRAM_ID, instruction.getProgramId());
        assertEquals(2, instruction.getKeys().size()); // Check number of keys
    }

    /**
     * Test for extending a lookup table.
     */
    @Test
    public void testExtendLookupTable() {
        PublicKey addressToAdd = new PublicKey("SysvarC1ock11111111111111111111111111111111");
        TransactionInstruction instruction = AddressLookupTableProgram.extendLookupTable(LOOKUP_TABLE, AUTHORITY, PAYER, Collections.singletonList(addressToAdd));
        assertNotNull(instruction);
        assertEquals(AddressLookupTableProgram.PROGRAM_ID, instruction.getProgramId());
        assertEquals(4, instruction.getKeys().size()); // Check number of keys
    }

    /**
     * Test for deactivating a lookup table.
     */
    @Test
    public void testDeactivateLookupTable() {
        TransactionInstruction instruction = AddressLookupTableProgram.deactivateLookupTable(LOOKUP_TABLE, AUTHORITY);
        assertNotNull(instruction);
        assertEquals(AddressLookupTableProgram.PROGRAM_ID, instruction.getProgramId());
        assertEquals(2, instruction.getKeys().size()); // Check number of keys
    }

    /**
     * Test for closing a lookup table.
     */
    @Test
    public void testCloseLookupTable() {
        PublicKey recipient = new PublicKey("SysvarRent111111111111111111111111111111111");
        TransactionInstruction instruction = AddressLookupTableProgram.closeLookupTable(LOOKUP_TABLE, AUTHORITY, recipient);
        assertNotNull(instruction);
        assertEquals(AddressLookupTableProgram.PROGRAM_ID, instruction.getProgramId());
        assertEquals(3, instruction.getKeys().size()); // Check number of keys
    }
}